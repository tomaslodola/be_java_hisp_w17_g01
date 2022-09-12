package com.w17_g1.socialMeLi.repository.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.w17_g1.socialMeLi.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Repository
public class UserRepositoryImp implements IUserRepository {

  List<User> userList;

  public UserRepositoryImp() {
    this.userList = loadDataBase();
  }

  public List<Integer> usersFollowedIds(Integer userId) {
    User u = userList.stream().filter(p -> p.getId() == userId).findAny().get();
    return u.getFollowedId();
  }

  private List<User> loadDataBase() {
    List<User> userList = null;
    File file;
    ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
            .registerModule(new JavaTimeModule());
    TypeReference<List<User>> typeRef = new TypeReference<>() {
    };
    try {
      file = ResourceUtils.getFile("classpath:Users.json");
      userList = objectMapper.readValue(file, typeRef);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return userList;
  }

}
