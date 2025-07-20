package org.example.command;

import lombok.Getter;
import lombok.Setter;
import org.example.exception.InvalidCommandException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
public class Command {
    private final String description;
    private final Priority priority;
    private final String author;
    private final String time;

    public Command(String description, Priority priority, String author, String time) {
        if (!validateDescription(description)) {
            throw new InvalidCommandException("Invalid description");
        }
        if (!validateAuthor(author)) {
            throw new InvalidCommandException("Invalid author");
        }
        if (!validateTime(time)) {
            throw new InvalidCommandException("Invalid time");
        }
        this.description = description;
        this.priority = priority;
        this.author = author;
        this.time = time;
    }

    public Command(String description, String priority, String author, String time) {
        if (!validatePriority(priority)) {
            throw new InvalidCommandException("Invalid priority");
        }
        if (!validateDescription(description)) {
            throw new InvalidCommandException("Invalid description");
        }
        if (!validateAuthor(author)) {
            throw new InvalidCommandException("Invalid author");
        }
        if (!validateTime(time)) {
            throw new InvalidCommandException("Invalid time");
        }
        this.description = description;
        this.priority = Priority.valueOf(priority.toUpperCase());
        this.author = author;
        this.time = time;
    }

    public boolean isCritical() {
        return priority == Priority.CRITICAL;
    }

    private boolean validateDescription(String description) {
        return description != null && description.length() <= 1000;
    }
    private boolean validateAuthor(String author) {
        return author != null && author.length() <= 100;
    }
    private boolean validatePriority(String priority) {
        return priority != null && !priority.isEmpty();
    }
    private boolean validateTime(String time) {
        if (time == null || time.isEmpty()) {
            return false;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;

        try {
            LocalTime.parse(time, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
