package com.w17_g1.socialMeLi.repository.user;

import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.w17_g1.socialMeLi.model.User;
import lombok.Data;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
@Repository
public class UserRepositoryImp implements IUserRepository {
  List<User> users;

  public UserRepositoryImp() {
    this.users = loadDataBase();
  }

  /**
   * Se obtienen los usuarios almacenados en el archivo Json
   */
  private List<User> loadDataBase() {
    List<User> users = null;
    File file;
    ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
            .registerModule(new JavaTimeModule());
    TypeReference<List<User>> typeRef = new TypeReference<>() {
    };
    try {
      file = ResourceUtils.getFile("classpath:Users.json");
      users = objectMapper.readValue(file, typeRef);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return users;
  }

  /**
   * Dado el ID de un usuario, lo buscamos en nuestra base de datos.
   * Devolvemos un Optional con el resultado de la busqueda
   */
  public User getUser(Integer id) {
    return users.stream()
            .filter(anUser -> Objects.equals(anUser.getId(), id))
            .findFirst()
            .orElseThrow(() -> new ElementNotFoundException("No se encontro el usuario con  id: " + id));
  }

  public Integer isValidUser(Integer id){
    if(users.stream().noneMatch(anUser -> Objects.equals(anUser.getId(), id)))
      throw new ElementNotFoundException("No se encontro el usuario con  id: " + id);
    return id;
  }

}
