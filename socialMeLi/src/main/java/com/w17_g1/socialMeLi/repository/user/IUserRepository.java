package com.w17_g1.socialMeLi.repository.user;

import org.springframework.stereotype.Repository;

public interface IUserRepository {
    void follorUser(Integer userId, Integer userIdToFollow);
}
