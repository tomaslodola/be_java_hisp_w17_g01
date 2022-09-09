package com.w17_g1.socialMeLi.exceptions;


public class ElementNotFoundException extends RuntimeException{

    public ElementNotFoundException() {
    }

    public ElementNotFoundException(String message) {
        super(message);
    }
}