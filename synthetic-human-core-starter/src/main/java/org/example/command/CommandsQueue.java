package org.example.command;

import org.example.audit.aspect.WeylandWatchingYou;
import org.example.exception.CommandQueueOverflowException;
import org.example.metrics.CommandExecutedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
public class CommandsQueue {
    private ThreadPoolExecutor executor;
    private List<Command> executedCommands;
    private CommandExecutor commandExecutor;
    private final ApplicationEventPublisher eventPublisher;

    public CommandsQueue(
            CommandExecutor commandExecutor,
            ApplicationEventPublisher eventPublisher,
            @Value("${command.pool-size}") String poolSize,
            @Value("${command.queue-size}") String queueSize
    ) {
        this.executedCommands = new ArrayList<>();
        this.commandExecutor = commandExecutor;
        this.eventPublisher = eventPublisher;

        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(Integer.parseInt(queueSize));
        int corePoolSize = Integer.parseInt(poolSize);
        executor = new ThreadPoolExecutor(
                corePoolSize,
                corePoolSize,
                0L,
                TimeUnit.MILLISECONDS,
                queue,
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    @WeylandWatchingYou
    public void addCommand(Command command) {
        if (command == null) {
            throw new NullPointerException("Given command is null");
        }
        if (command.isCritical()) {
            executeRightNow(command);
        } else {
            try {
                addToQueue(command);
            } catch (RejectedExecutionException e) {
                throw new CommandQueueOverflowException("Command " + command.getDescription() + " rejected. Queue is full");
            }
        }
        executedCommands.add(command);
    }

    @WeylandWatchingYou
    private void addToQueue(Command command) {
        executor.execute(() -> {
            executeAndNotify(command);
        });
    }

    @WeylandWatchingYou
    private void executeRightNow(Command command) {
        new Thread(() -> executeAndNotify(command)).start();
    }

    @WeylandWatchingYou
    private void executeAndNotify(Command command) {
        commandExecutor.execute(command);
        // Публикуем событие о выполнении команды
        eventPublisher.publishEvent(new CommandExecutedEvent(this, command));
    }

    public int getQueueSize() {
        return executor.getQueue().size();
    }
}
