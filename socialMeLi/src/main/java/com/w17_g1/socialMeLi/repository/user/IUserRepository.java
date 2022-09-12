package com.w17_g1.socialMeLi.repository.user;

import com.w17_g1.socialMeLi.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.w17_g1.socialMeLi.model.User;

import java.util.Optional;

public interface IUserRepository {
<<<<<<< HEAD
    Optional<User> getUser(Integer userId);

    void unfollowUser(Integer userId, Integer userIdToUnfollow);

    boolean userExist(Integer id);
=======
    public List<User> getAllUsers();
    public User getUserById(Integer id);
>>>>>>> 80917f486ea036aa2817da05dc49627c5687d569
}
