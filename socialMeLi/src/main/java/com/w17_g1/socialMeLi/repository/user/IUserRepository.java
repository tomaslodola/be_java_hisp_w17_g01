package com.w17_g1.socialMeLi.repository.user;

import com.w17_g1.socialMeLi.model.User;
import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    Optional<User> getUser(Integer userId);

    boolean userExist(Integer id);

    public List<User> getAllUsers();

    public User getUserById(Integer id);
}
