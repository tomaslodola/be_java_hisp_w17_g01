package com.w17_g1.socialMeLi.repository.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import com.w17_g1.socialMeLi.model.User;
import lombok.Data;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
@Repository
public class UserRepositoryImp implements IUserRepository{

    private final List<User> users;

    public UserRepositoryImp() {

        this.users = loadDataBase();
    }

    public UserRepositoryImp(List<User> users) {
        this.users = loadDataBase();
    }

    private List<User> loadDataBase(){
        List<User> jsonUsers = null;
        File file;
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .registerModule(new JavaTimeModule());
        TypeReference<List<User>> typeRef = new TypeReference<>() {};
        try {
            file = ResourceUtils.getFile("classpath:Users.json");
            jsonUsers = objectMapper.readValue(file, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonUsers;
    }

    /*
     Dado el ID de un usuario, lo buscamos en nuestra base de datos.
     Devolvemos un Optional con el resultado de la busqueda
     */
    public Optional<User> getUser(Integer id){
        return users.stream()
                .filter(anUser -> Objects.equals(anUser.getId(), id))
                .findFirst();
    }

    public boolean userExist(Integer id){
        return users.stream()
                .anyMatch(anUser -> Objects.equals(anUser.getId(), id));
    }

    @Override
    public void unfollowUser(Integer userId, Integer userIdToFollow) {
        Optional<User> user = getUser(userId);
        if(user.isEmpty()){
            throw new ElementNotFoundException("No se encuentra el usuario con el id "+userId+" que quiere seguir al usuario " + userIdToFollow);
        }
        Optional<User> userToFollow = getUser(userIdToFollow);
        if(userToFollow.isEmpty()){
            throw new ElementNotFoundException("No se encuentra al usuario con el id "+ userToFollow + " al que quiere seguir el usuario de id " + userId);
        }
        user.get().getFollowersId().remove(userToFollow.get().getId());
    }

}
