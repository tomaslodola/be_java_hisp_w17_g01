package com.w17_g1.socialMeLi.repository.user;

import com.w17_g1.socialMeLi.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface IUserRepository {
    public List<User> getAllUsers();
    public User getUserById(Integer id);
}
