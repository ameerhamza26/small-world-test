package com.smallworld.exceptions;

public class InvalidPropertyException extends RuntimeException {

    public InvalidPropertyException(String message, Exception e) {
        super(message, e);
    }
}
