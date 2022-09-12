package com.w17_g1.socialMeLi.exceptions;

public class UserCantFollowItSelfException extends RuntimeException{

    public UserCantFollowItSelfException() {}

    public UserCantFollowItSelfException(String message) {
        super(message);
    }

}
