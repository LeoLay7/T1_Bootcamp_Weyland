package org.example.exception;

public class CommandQueueOverflowException extends RuntimeException {
    public CommandQueueOverflowException(String message) {
        super(message);
    }
}
