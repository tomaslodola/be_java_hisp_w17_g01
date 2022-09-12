package com.w17_g1.socialMeLi.services;


import com.w17_g1.socialMeLi.dto.output.UserCountFollowersDTO;
import com.w17_g1.socialMeLi.model.User;
import com.w17_g1.socialMeLi.repository.user.IUserRepository;
import com.w17_g1.socialMeLi.dto.output.UserOutputListDTO;
import com.w17_g1.socialMeLi.dto.output.UserOutputDTO;
import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import com.w17_g1.socialMeLi.model.User;
import com.w17_g1.socialMeLi.repository.user.UserRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepositoryImp userRepository;

    // Dado el ID de un usuario, devuelve un DTO con una lista de los seguidores de ese usuario
    public UserOutputListDTO getFollowersList(Integer userId) {

        // Encontramos al usuario según el ID que nos llega (al recibir un ID invalido devolvemos una excepcion)
        User user = userRepository
                .getUser(userId)
                .orElseThrow(() -> {return new ElementNotFoundException("No se encontro el ID solicitado");});

        // Devolvemos un DTO con la lista de sus seguidores
        // Observación: Construimos la lista en buildUserOutpustList
        return UserOutputListDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .followers(buildUserOutputList(user.getFollowersId()))
                .build();

    }

    // Dado el ID de un usuario, devuelve un DTO con una lista de usuarios a los que sigue
    public UserOutputListDTO getFollowedList(Integer userId) {

        // Encontramos al usuario según el ID que nos llega (al recibir un ID invalido devolvemos una excepcion)
        User user = userRepository
                .getUser(userId)
                .orElseThrow(() -> {return new ElementNotFoundException("No se encontro el ID solicitado");});

        // Devolvemos un DTO con la lista de usuarios a los que sigue
        // Observación: Construimos la lista en buildUserOutpustList
        return UserOutputListDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .followers(buildUserOutputList(user.getFollowedId()))
                .build();
    }

    // Dada una lista de IDs, devuelve la lista de sus respectivos usuarios (OutputDTO)
    private List<UserOutputDTO> buildUserOutputList(List<Integer> idList){
        // Por cada ID de la lista, tomamos el usuario al que corresponde ese ID para nuestra lista resultado
        // De no encontrarse uno de los ID de la lista, devolvemos una excepcion
        List<User> userList = idList.stream()
                .map(anId -> userRepository
                        .getUser(anId)
                        .orElseThrow(() -> { return new ElementNotFoundException("Error en la Base de Datos"); }))
                .toList();

        // Por cada usuario encontrado, creamos un UsuarioOutputDTO con sus datos y devolvemos todos en una lista
        return userList.stream()
                .map(anUser -> UserOutputDTO.builder()
                        .id(anUser.getId())
                        .name(anUser.getName())
                        .build())
                .toList();
    }

    public UserCountFollowersDTO countNumberOfFollowers(Integer id) {
        User user = userRepository.getUserById(id);
        return new UserCountFollowersDTO(user.getFollowersId().size());
    }
}
