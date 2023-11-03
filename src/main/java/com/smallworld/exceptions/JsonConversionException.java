package com.smallworld.exceptions;

public class JsonConversionException extends RuntimeException {

    public JsonConversionException(String message, Exception e) {
        super(message, e);
    }
}
