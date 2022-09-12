package com.w17_g1.socialMeLi.exceptions;

public class UserAlreadyFollowedException extends RuntimeException{

    public UserAlreadyFollowedException() {}

    public UserAlreadyFollowedException(String message) {
        super(message);
    }

}
