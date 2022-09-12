package com.w17_g1.socialMeLi.repository.user;


import java.util.List;

public interface IUserRepository {
  public List<Integer> usersFollowedIds(Integer userId);
}
