package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RobotExceptionHandler {

    @ExceptionHandler(InvalidCommandException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCommandException(
            InvalidCommandException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                "INVALID_COMMAND",
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CommandQueueOverflowException.class)
    public ResponseEntity<ErrorResponse> handleCommandQueueOverflowException(
            CommandQueueOverflowException ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                "QUEUE_OVERFLOW",
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                "INTERNAL_ERROR",
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
