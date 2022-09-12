package com.w17_g1.socialMeLi.repository.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import com.w17_g1.socialMeLi.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import java.util.Optional;

@Repository
public class UserRepositoryImp implements IUserRepository {
    List<User> userList;
    public UserRepositoryImp(){
        this.userList = loadDataBase();
    }

    @Override
    public List<User> getAllUsers() {
        return userList;
    }

    @Override
    public User getUserById(Integer id) {
        Optional<User> user = userList.stream().filter(u -> u.getId() == id).findFirst();
        if (!user.isPresent()) {
            String message = String.format("No se encontro el usuario con el id %s", id);
            throw new ElementNotFoundException(message);
        }
        return user.get();
    }

    private List<User> loadDataBase(){
        List<User> userList = null;
        File file;
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false) //nueva
                .registerModule(new JavaTimeModule()); // nueva
        TypeReference<List<User>> typeRef = new TypeReference<>() {};
        try {
            file = ResourceUtils.getFile("classpath:Users.json");
            userList = objectMapper.readValue(file, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }
    /*
     Dado el ID de un usuario, lo buscamos en nuestra base de datos.
     Devolvemos un Optional con el resultado de la busqueda
     */
    public Optional<User> getUser(Integer id){
        return userList.stream()
                .filter(anUser -> Objects.equals(anUser.getId(), id))
                .findFirst();
    }

}
