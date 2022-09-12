package com.w17_g1.socialMeLi.services;

import com.w17_g1.socialMeLi.dto.output.MessageResponseDTO;
import com.w17_g1.socialMeLi.repository.user.IUserRepository;
import com.w17_g1.socialMeLi.dto.output.UserOutputListDTO;
import com.w17_g1.socialMeLi.dto.output.UserOutputDTO;
import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import com.w17_g1.socialMeLi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class
UserService {

    @Autowired
    IUserRepository userRepository;

    public MessageResponseDTO followUser(Integer userId, Integer userIdToFollow) {
        Optional<User> user = userRepository.getUser(userId);
        if(user.get().getFollowersId().contains(userIdToFollow)){
            return new MessageResponseDTO("El usuario de id " + userId + " ya esta siguiendo al usuario de id " + userIdToFollow);
        }
        if(!userRepository.userExist(userId)){
            throw new ElementNotFoundException("No se encuentra el usuario con el id "+userId+" que quiere seguir al usuario " + userIdToFollow);
        }
        Optional<User> userToFollow = userRepository.getUser(userIdToFollow);
        if(!userRepository.userExist(userIdToFollow)){
            throw new ElementNotFoundException("No se encuentra al usuario con el id "+ userToFollow + " al que quiere seguir el usuario de id " + userId);
        }
        user.get().getFollowersId().add(userToFollow.get().getId());
        return new MessageResponseDTO("Se ha seguido al usuario "+ userIdToFollow + " con exito.");
    }

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

    public MessageResponseDTO unfollowUser(Integer userId, Integer userIdToUnfollow) {
        Optional<User> user = userRepository.getUser(userId);
        if(!user.get().getFollowersId().contains(userIdToUnfollow)){
            return new MessageResponseDTO("El usuario de id " + userId + " no esta siguiendo al usuario de id " + userIdToUnfollow);
        }
        if(!userRepository.userExist(userId)){
            throw new ElementNotFoundException("No se encuentra el usuario con el id "+userId+" que quiere seguir al usuario " + userIdToUnfollow);
        }
        Optional<User> userToFollow = userRepository.getUser(userIdToUnfollow);
        if(!userRepository.userExist(userIdToUnfollow)){
            throw new ElementNotFoundException("No se encuentra al usuario con el id "+ userToFollow + " al que quiere seguir el usuario de id " + userId);
        }

        user.get().getFollowersId().remove(userToFollow.get().getId());

        return new MessageResponseDTO("Se ha dejado de seguir al usuario "+ userIdToUnfollow + " con exito.");
    }
}
