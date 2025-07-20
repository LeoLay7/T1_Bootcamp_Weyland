package org.homework.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.audit.aspect.WeylandWatchingYou;
import org.example.command.Command;
import org.homework.request.CommandRequest;
import org.homework.service.CommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/commands")
@AllArgsConstructor
public class CommandController {
    private CommandService commandService;

    @PostMapping
    @WeylandWatchingYou
    public ResponseEntity<?> addCommand(@RequestBody CommandRequest command) {
        commandService.addCommand(command);
        return ResponseEntity.ok().build();
    }
}
