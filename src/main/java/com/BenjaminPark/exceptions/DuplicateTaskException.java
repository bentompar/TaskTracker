package com.BenjaminPark.exceptions;

public class DuplicateTaskException extends RuntimeException{
    public DuplicateTaskException(String message){
        super(message);
    }
}
