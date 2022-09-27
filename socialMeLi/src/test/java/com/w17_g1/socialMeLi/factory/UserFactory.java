package com.w17_g1.socialMeLi.factory;

import com.w17_g1.socialMeLi.dto.output.UserFollowersOutputListDTO;
import com.w17_g1.socialMeLi.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserFactory {
    //Crea un usuario generico

    public static User createUser(){
        return User.builder()
                .id(9)
                .name("Facundo")
                .followersId(new ArrayList<>())
                .followedId(new ArrayList<>())
                .build();
    }
    //Crea un JsonUser generico
    public static User createJsonUser(){
        return User.builder()
                .id(6)
                .name("Taylor Tillman")
                .followersId(List.of(1,2,3))
                .followedId(List.of(3))
                .build();
    }


    // Metodo que crea un usuario que no siga ningun otro usuario
    public static User createUserWhoFollowsNoOne(Integer userID){
        return User.builder()
                .id(userID)
                .name("User Without Followed")
                .followersId(new ArrayList<>())
                .followedId(new ArrayList<>())
                .build();
    }

    // Metodo que crea un usuario que siga a otro usuario que llega como parametro
    public static User createUserWhoFollowsOneUser(Integer userID, Integer followedID){
        return User.builder()
                .id(userID)
                .name("User With One Followed")
                .followersId(new ArrayList<>())
                .followedId(List.of(followedID))
                .build();
    }

    // Metodo que crea un usuario que siga otros 2 usuario que llegan como parametro
    public static User createUserWhoFollowsTwoUsers(Integer userID, Integer firstFollowedID, Integer secondFollowedID){
        return User.builder()
                .id(userID)
                .name("User With Two Followed")
                .followersId(new ArrayList<>())
                .followedId(List.of(firstFollowedID,secondFollowedID))
                .build();
    }

}
