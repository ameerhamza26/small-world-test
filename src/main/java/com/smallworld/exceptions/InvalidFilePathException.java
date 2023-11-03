package com.smallworld.exceptions;

public class InvalidFilePathException extends RuntimeException {
    public InvalidFilePathException(String message, Exception e) {
        super(message, e);
    }
}
