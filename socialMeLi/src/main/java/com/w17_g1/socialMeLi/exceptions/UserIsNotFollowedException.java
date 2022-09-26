package com.w17_g1.socialMeLi.exceptions;

public class UserIsNotFollowedException extends RuntimeException{

    public UserIsNotFollowedException() {}

    public UserIsNotFollowedException(String message) {
        super(message);
    }

}
