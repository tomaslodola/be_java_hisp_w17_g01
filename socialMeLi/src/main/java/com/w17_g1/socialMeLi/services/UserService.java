package com.w17_g1.socialMeLi.services;


import com.w17_g1.socialMeLi.dto.output.UserCountFollowersDTO;
import com.w17_g1.socialMeLi.model.User;
import com.w17_g1.socialMeLi.repository.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;

    public UserCountFollowersDTO countNumberOfFollowers(Integer id) {
        User user = userRepository.getUserById(id);
        return new UserCountFollowersDTO(user.getFollowersId().size());
    }
}
