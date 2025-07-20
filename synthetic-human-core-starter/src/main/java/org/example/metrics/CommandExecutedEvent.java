package org.example.metrics;

import org.example.command.Command;
import org.springframework.context.ApplicationEvent;

public class CommandExecutedEvent extends ApplicationEvent {
    private final Command command;

    public CommandExecutedEvent(Object source, Command command) {
        super(source);
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
