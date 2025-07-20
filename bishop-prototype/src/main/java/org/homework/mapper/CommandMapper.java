package org.homework.mapper;

import org.example.command.Command;
import org.example.command.Priority;
import org.homework.request.CommandRequest;
import org.springframework.stereotype.Component;

@Component
public class CommandMapper {
    public CommandMapper() {}
    public Command toEntity(CommandRequest commandRequest) {
        Command command = new Command(
                commandRequest.getDescription(),
                Priority.valueOf(commandRequest.getPriority()),
                commandRequest.getAuthor(),
                commandRequest.getTime()
        );
        return command;
    }
}
