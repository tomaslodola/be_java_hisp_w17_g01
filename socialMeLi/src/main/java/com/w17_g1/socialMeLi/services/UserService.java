package com.w17_g1.socialMeLi.services;


import com.w17_g1.socialMeLi.dto.output.*;
import com.w17_g1.socialMeLi.model.User;
import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import com.w17_g1.socialMeLi.repository.user.UserRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepositoryImp userRepository;

    // Dado el ID de un usuario, devuelve un DTO con una lista de los seguidores de ese usuario
    public UserFollowersOutputListDTO getFollowersList(Integer userId) {

        // Encontramos al usuario según el ID que nos llega (al recibir un ID invalido devolvemos una excepcion)
        User user = userRepository
                .getUser(userId)
                .orElseThrow(() -> {return new ElementNotFoundException("No se encontro el ID solicitado");});

        // Devolvemos un DTO con la lista de sus seguidores
        // Observación: Construimos la lista en buildUserOutpustList
        return UserFollowersOutputListDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .followers(buildUserOutputList(user.getFollowersId()))
                .build();

    }

    // Dado el ID de un usuario, devuelve un DTO con una lista de usuarios a los que sigue
    public UserFollowedOutputListDTO getFollowedList(Integer userId) {

        // Encontramos al usuario según el ID que nos llega (al recibir un ID invalido devolvemos una excepcion)
        User user = userRepository
                .getUser(userId)
                .orElseThrow(() -> {return new ElementNotFoundException("No se encontro el ID solicitado");});

        // Devolvemos un DTO con la lista de usuarios a los que sigue
        // Observación: Construimos la lista en buildUserOutpustList
        return UserFollowedOutputListDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .followed(buildUserOutputList(user.getFollowedId()))
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

    public UserFollowersOutputListDTO sortFollowersList (Integer id,String option){
        List list =  getFollowersList(id).getFollowers();
        User user = userRepository
                .getUser(id)
                .orElseThrow(() -> {return new ElementNotFoundException("No se encontro el ID solicitado");});

        List<UserOutputDTO>listSorted = null;
       if (option.equals("name_asc")) {
           listSorted =  list.stream().sorted(Comparator.comparing(UserOutputListDTO::getName)).toList();
        }
       if(option.equals("name_desc")){
          listSorted=  list.stream().sorted(Comparator.comparing(UserOutputListDTO::getName).reversed()).toList();

       }
        return UserFollowersOutputListDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .followers(listSorted)
                .build();

    }
    public  UserFollowedOutputListDTO sortFollowedList (Integer id, String option){
        List list = getFollowedList(id).getFollowed();
        User user = userRepository
                .getUser(id)
                .orElseThrow(() -> {return new ElementNotFoundException("No se encontro el ID solicitado");});

        List<UserOutputDTO>listSorted;
        listSorted = null;

        if (option.equals("name_asc")) {
            listSorted =  list.stream().sorted(Comparator.comparing(UserOutputListDTO::getName)).toList();
        }
        if(option.equals("name_desc")){
            listSorted=  list.stream().sorted(Comparator.comparing(UserOutputListDTO::getName).reversed()).toList();

        }
        return UserFollowedOutputListDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .followed(listSorted)
                .build();
    }
}
