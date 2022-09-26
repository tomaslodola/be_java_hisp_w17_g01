package com.w17_g1.socialMeLi.exceptions;

public class UserCantUnfollowItselfException extends RuntimeException{

    public UserCantUnfollowItselfException() {}

    public UserCantUnfollowItselfException(String message) {
        super(message);
    }

}
