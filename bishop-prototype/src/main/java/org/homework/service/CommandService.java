package org.homework.service;

import lombok.AllArgsConstructor;
import org.example.audit.aspect.WeylandWatchingYou;
import org.example.command.CommandsQueue;
import org.homework.mapper.CommandMapper;
import org.homework.request.CommandRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommandService {
    CommandsQueue commandsQueue;
    CommandMapper commandMapper;

    @WeylandWatchingYou
    public void addCommand(CommandRequest command) {
        commandsQueue.addCommand(commandMapper.toEntity(command));
    }
}
