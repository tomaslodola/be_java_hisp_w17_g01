package com.w17_g1.socialMeLi.exceptions;

public class UserCantUnfollowItSelfException extends RuntimeException{

    public UserCantUnfollowItSelfException() {}

    public UserCantUnfollowItSelfException(String message) {
        super(message);
    }

}
