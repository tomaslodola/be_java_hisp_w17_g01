package com.w17_g1.socialMeLi.repository.user;

import com.w17_g1.socialMeLi.model.User;
import java.util.List;
import java.util.Optional;

import com.w17_g1.socialMeLi.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    User getUser(Integer id);
    Integer isValidUser(Integer id);
}
