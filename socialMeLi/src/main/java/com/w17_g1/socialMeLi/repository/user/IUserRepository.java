package com.w17_g1.socialMeLi.repository.user;


import com.w17_g1.socialMeLi.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
  public List<Integer> usersFollowedIds(Integer userId);

  public Optional<User> getUser(Integer id);
}
