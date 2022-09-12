package com.w17_g1.socialMeLi.services;

import com.w17_g1.socialMeLi.dto.output.MessageResponseDTO;

import com.w17_g1.socialMeLi.dto.output.*;
import com.w17_g1.socialMeLi.exceptions.UserAlreadyFollowedException;
import com.w17_g1.socialMeLi.exceptions.UserCantFollowItselfException;
import com.w17_g1.socialMeLi.exceptions.UserIsNotFollowedException;
import com.w17_g1.socialMeLi.model.User;
import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import com.w17_g1.socialMeLi.repository.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;

    public MessageResponseDTO followUser(Integer userId, Integer userIdToFollow) {

        if(Objects.equals(userId, userIdToFollow))
            throw new UserCantFollowItselfException("El usuario no puede seguirse a si mismo");

        // Obtenemos el primer usuario de la base de datos
        User user = userRepository
                .getUser(userId)
                .orElseThrow(() -> new ElementNotFoundException("No se encuentra el usuario con el id " + userId + " que quiere seguir al usuario " + userIdToFollow));

        // Obtenemos el usuario a seguir
        User userToFollow = userRepository
                .getUser(userIdToFollow)
                .orElseThrow(() -> new ElementNotFoundException("No se encuentra el usuario con el id " + userIdToFollow + " que quiere seguir el usuario " + userId));

        // Chequeo de exception
        if(user.getFollowedId().contains(userIdToFollow))
            throw new UserAlreadyFollowedException("El usuario de id " + userId + " ya esta siguiendo al usuario de id " + userIdToFollow);

        user.getFollowedId().add(userIdToFollow);
        userToFollow.getFollowersId().add(userId);

        return new MessageResponseDTO("Se ha seguido al usuario "+ userIdToFollow + " con exito.");
    }

    public UserCountFollowersDTO countNumberOfFollowers(Integer id) {
        User user = userRepository
                .getUser(id)
                .orElseThrow(() -> new ElementNotFoundException("No se encontro el ID solicitado"));

        return new UserCountFollowersDTO(user.getFollowersId().size());
    }

    // Dado el ID de un usuario, devuelve un DTO con una lista de los seguidores de ese usuario
    public UserFollowersOutputListDTO getFollowersList(Integer userId) {
        // Encontramos al usuario según el ID que nos llega (al recibir un ID invalido devolvemos una excepcion)
        User user = userRepository
                .getUser(userId)
                .orElseThrow(() -> new ElementNotFoundException("No se encontro el ID solicitado"));

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
                .orElseThrow(() -> new ElementNotFoundException("No se encontro el ID solicitado"));

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
                        .orElseThrow(() -> new ElementNotFoundException("Error en la Base de Datos")))
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

        if(Objects.equals(userId, userIdToUnfollow))
            throw new UserCantFollowItselfException("El usuario no puede seguirse a si mismo");

        User user = userRepository
                .getUser(userId)
                .orElseThrow(() -> new ElementNotFoundException("No se encuentra el usuario con el id " + userId + " que quiere dejar de seguir al usuario " + userIdToUnfollow));

        User userToUnfollow = userRepository
                .getUser(userIdToUnfollow)
                .orElseThrow(() -> new ElementNotFoundException("No se encuentra el usuario con el id " + userIdToUnfollow + " que quiere dejar de seguir el usuario " + userId));

        if (!user.getFollowedId().contains(userIdToUnfollow))
            throw new UserIsNotFollowedException("El usuario de id " + userId + " no esta siguiendo al usuario de id " + userIdToUnfollow);

        userToUnfollow.getFollowersId().remove(userId);
        user.getFollowedId().remove(userIdToUnfollow);

        return new MessageResponseDTO("Se ha dejado de seguir al usuario " + userIdToUnfollow + " con exito.");
    }

    public UserFollowersOutputListDTO sortFollowersList (Integer id,String option){

        UserFollowersOutputListDTO responseDTO =  getFollowersList(id);

        if (option.equals("name_asc"))
            responseDTO.setFollowers(
                    responseDTO.getFollowers().stream()
                            .sorted(Comparator.comparing(UserOutputDTO::getName))
                            .toList());
        else
            responseDTO.setFollowers(
                    responseDTO.getFollowers().stream()
                            .sorted(Comparator.comparing(UserOutputDTO::getName).reversed())
                            .toList());

        return responseDTO;
    }

    public  UserFollowedOutputListDTO sortFollowedList (Integer id, String option) {
        UserFollowedOutputListDTO responseDTO = getFollowedList(id);

        if (option.equals("name_asc"))
            responseDTO.setFollowed(
                    responseDTO.getFollowed().stream()
                            .sorted(Comparator.comparing(UserOutputDTO::getName))
                            .toList());
        else
            responseDTO.setFollowed(
                    responseDTO.getFollowed().stream()
                            .sorted(Comparator.comparing(UserOutputDTO::getName).reversed())
                            .toList());

        return responseDTO;
    }

}
