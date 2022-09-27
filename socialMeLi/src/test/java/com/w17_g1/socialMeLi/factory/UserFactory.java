package com.w17_g1.socialMeLi.factory;

import com.w17_g1.socialMeLi.dto.output.UserFollowersOutputListDTO;
import com.w17_g1.socialMeLi.dto.output.UserOutputDTO;
import com.w17_g1.socialMeLi.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserFactory {


    public static User createJsonUser() {
        return User.builder()
                .id(6)
                .name("Taylor Tillman")
                .followersId(List.of(1, 2, 3))
                .followedId(List.of(3))
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
}
