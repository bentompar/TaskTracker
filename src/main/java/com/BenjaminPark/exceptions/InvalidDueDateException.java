package com.BenjaminPark.exceptions;

public class InvalidDueDateException extends RuntimeException {
    public InvalidDueDateException(String message) {
        super(message);
    }
}
