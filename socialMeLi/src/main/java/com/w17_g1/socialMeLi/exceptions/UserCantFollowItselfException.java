package com.w17_g1.socialMeLi.exceptions;

public class UserCantFollowItselfException extends RuntimeException{

    public UserCantFollowItselfException() {}

    public UserCantFollowItselfException(String message) {
        super(message);
    }

}
