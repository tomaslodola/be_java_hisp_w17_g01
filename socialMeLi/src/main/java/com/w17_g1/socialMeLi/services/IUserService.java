package com.w17_g1.socialMeLi.services;

import com.w17_g1.socialMeLi.dto.output.MessageResponseDTO;
import com.w17_g1.socialMeLi.dto.output.User.UserCountFollowersDTO;
import com.w17_g1.socialMeLi.dto.output.User.UserFollowedOutputListDTO;
import com.w17_g1.socialMeLi.dto.output.User.UserFollowersOutputListDTO;
import com.w17_g1.socialMeLi.dto.output.User.UserOutputDTO;

import java.util.List;

public interface IUserService {
    MessageResponseDTO followUser(Integer userId, Integer userIdToFollow);
    UserCountFollowersDTO countNumberOfFollowers(Integer id);
    UserFollowersOutputListDTO getFollowersList(Integer userId, String order);
    UserFollowedOutputListDTO getFollowedList(Integer userId, String order);
    MessageResponseDTO unfollowUser(Integer userId, Integer userIdToUnfollow);

}
