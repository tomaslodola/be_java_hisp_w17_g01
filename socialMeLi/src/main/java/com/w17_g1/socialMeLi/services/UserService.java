package com.w17_g1.socialMeLi.services;


import com.w17_g1.socialMeLi.repository.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class
UserService {

    @Autowired
    IUserRepository userRepository;
}
