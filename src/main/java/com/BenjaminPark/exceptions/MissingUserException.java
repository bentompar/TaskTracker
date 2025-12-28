package com.BenjaminPark.exceptions;

abstract class MissingUserException extends RuntimeException {
    protected MissingUserException(String message) {
        super(message);
    }
}
