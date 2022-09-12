package com.w17_g1.socialMeLi.repository.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.w17_g1.socialMeLi.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserRepositoryImp implements IUserRepository {
    private final List<User> users;

    public UserRepositoryImp() {

        this.users = loadDataBase();
    }

    @Override
    public void follorUser(Integer userId, Integer userIdToFollow) {
        Optional<User> user = users.stream().filter(us -> Objects.equals(us.getId(), userId)).findFirst();
        Optional<User> userToFollow = users.stream().filter(usToFollow -> Objects.equals(usToFollow.getId(), userIdToFollow)).findFirst();

        user.get().getFollowersId().add(userToFollow.get().getId());
    }

    private List<User> loadDataBase() {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:Users.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<User>> typeRef = new TypeReference<>() {};
        List<User> users = null;
        try {
            users = objectMapper.readValue(file, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

}
