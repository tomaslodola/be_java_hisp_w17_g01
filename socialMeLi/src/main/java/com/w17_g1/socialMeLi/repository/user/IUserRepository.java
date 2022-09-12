package com.w17_g1.socialMeLi.repository.user;


import com.w17_g1.socialMeLi.model.User;

import java.util.Optional;

public interface IUserRepository {
    Optional<User> getUser(Integer userId);

    void unfollowUser(Integer userId, Integer userIdToUnfollow);

    boolean userExist(Integer id);
}
