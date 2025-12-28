package com.BenjaminPark.exceptions;

public class DuplicateUserException extends RuntimeException {
    protected DuplicateUserException(String message) {
        super(message);
    }
}
