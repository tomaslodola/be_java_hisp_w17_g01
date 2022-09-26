package com.w17_g1.socialMeLi.factory;

import com.w17_g1.socialMeLi.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserFactory {
    public static User createUser(){
        return User.builder()
                .id(9)
                .name("Facundo")
                .followersId(new ArrayList<>())
                .followedId(new ArrayList<>())
                .build();
    }
    public static User createJsonUser(){
        return User.builder()
                .id(6)
                .name("Taylor Tillman")
                .followersId(List.of(1,2,3))
                .followedId(List.of(3))
                .build();
    }
}
