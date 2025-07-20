package org.example.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.example.command.Command;
import org.example.command.CommandsQueue;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RobotMetricsService {
    private final MeterRegistry meterRegistry;
    private final CommandsQueue commandsQueue;
    private final Map<String, Counter> authorCommandCounters = new ConcurrentHashMap<>();

    public RobotMetricsService(MeterRegistry meterRegistry, CommandsQueue commandsQueue) {
        this.meterRegistry = meterRegistry;
        this.commandsQueue = commandsQueue;
    }

    @PostConstruct
    public void init() {
        Gauge.builder("robot.queue.size", commandsQueue, CommandsQueue::getQueueSize)
                .description("Current number of tasks in the robot's queue")
                .register(meterRegistry);
    }

    @EventListener
    public void handleCommandExecutedEvent(CommandExecutedEvent event) {
        Command command = event.getCommand();
        String author = command.getAuthor();
        if (author == null || author.isEmpty()) {
            author = "unknown";
        }

        Counter counter = authorCommandCounters.computeIfAbsent(author,
                name -> Counter.builder("robot.commands.executed")
                        .tag("author", name)
                        .description("Number of commands executed by author")
                        .register(meterRegistry));

        counter.increment();
    }
}

