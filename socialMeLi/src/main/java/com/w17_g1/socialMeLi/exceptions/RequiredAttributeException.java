package com.w17_g1.socialMeLi.exceptions;

public class RequiredAttributeException extends RuntimeException{
    public RequiredAttributeException() {}

    public RequiredAttributeException(String message) {
        super(message);
    }

}
