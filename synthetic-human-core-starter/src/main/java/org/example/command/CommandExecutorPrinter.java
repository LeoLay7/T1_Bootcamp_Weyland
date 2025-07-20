package org.example.command;

import org.example.audit.aspect.WeylandWatchingYou;
import org.springframework.stereotype.Component;

@Component
public class CommandExecutorPrinter implements CommandExecutor {
    @WeylandWatchingYou
    public void execute(Command command) {
        String criticality = command.getPriority() == Priority.CRITICAL ? "critical" : "common";
        System.out.println("Executing command: " + command.getDescription() +
                ". Author: " + command.getAuthor() +
                ". Priority: " + command.getPriority() +
                ". Criticality: " + criticality
        );
    }
}
