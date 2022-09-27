package com.w17_g1.socialMeLi.factory;

import com.w17_g1.socialMeLi.dto.output.User.UserFollowedOutputListDTO;
import com.w17_g1.socialMeLi.dto.output.User.UserFollowersOutputListDTO;
import com.w17_g1.socialMeLi.dto.output.User.UserOutputDTO;
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
                .followedId(List.of(1,2,3))
                .build();
    }

    public static UserFollowersOutputListDTO createUserFollowersOutputListDTOSortAsc() {
        List<UserOutputDTO> userOutputDTOList =
                List.of(userOutputDto1(),userOutputDto3(),userOutputDto2());

        return UserFollowersOutputListDTO.builder()
                .id(6)
                .name("Taylor Tillman")
                .followers(userOutputDTOList).build();
    }

    public static UserFollowersOutputListDTO createUserFollowersOutputListDTOSortDesc() {
        List<UserOutputDTO> userOutputDTOList =
                List.of(userOutputDto2(),userOutputDto3(),userOutputDto1());

        return UserFollowersOutputListDTO.builder()
                .id(6)
                .name("Taylor Tillman")
                .followers(userOutputDTOList).build();
    }

    public static UserFollowedOutputListDTO createUserFollowedOutputListDTOSortDesc() {
        List<UserOutputDTO> userOutputDTOList =
                List.of(userOutputDto2(),userOutputDto3(),userOutputDto1());

        return UserFollowedOutputListDTO.builder()
                .id(6)
                .name("Taylor Tillman")
                .followed(userOutputDTOList).build();
    }

    public static UserFollowedOutputListDTO createUserFollowedOutputListDTOSortAsc() {
        List<UserOutputDTO> userOutputDTOList =
                List.of(userOutputDto1(),userOutputDto3(),userOutputDto2());

        return UserFollowedOutputListDTO.builder()
                .id(6)
                .name("Taylor Tillman")
                .followed(userOutputDTOList).build();
    }

    public static UserOutputDTO userOutputDto1() {
        return UserOutputDTO.builder().id(1).name("Barr Owen").build();
    }
    public static UserOutputDTO userOutputDto2() {
        return UserOutputDTO.builder().id(2).name("Maxine Weiss").build();
    }

    public static UserOutputDTO userOutputDto3() {
        return UserOutputDTO.builder().id(3).name("Brady Donovan").build();
    }
    public static User user1() {
        return User.builder().id(1).name("Barr Owen").build();
    }

    public static User user2() {
        return User.builder().id(2).name("Maxine Weiss").build();
    }

    public static User user3() {
        return User.builder().id(3).name("Brady Donovan").build();
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
