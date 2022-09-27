package com.w17_g1.socialMeLi.services;

import com.w17_g1.socialMeLi.dto.output.MessageResponseDTO;

import com.w17_g1.socialMeLi.dto.output.User.UserCountFollowersDTO;
import com.w17_g1.socialMeLi.dto.output.User.UserFollowedOutputListDTO;
import com.w17_g1.socialMeLi.dto.output.User.UserFollowersOutputListDTO;
import com.w17_g1.socialMeLi.dto.output.User.UserOutputDTO;
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
public class UserService implements IUserService{

    @Autowired
    IUserRepository userRepository;

    /**
     * Se implementa el seguimiento de un usuario
    */
    @Override
    public MessageResponseDTO followUser(Integer userId, Integer userIdToFollow) {

        if(Objects.equals(userId, userIdToFollow))
            throw new UserCantFollowItselfException("El usuario no puede seguirse a si mismo");

        User user = userRepository.getUser(userId);
        User userToFollow = userRepository.getUser(userIdToFollow);

        // Excepcion: El usuario no puede seguir a alguien que ya sigue
        if(user.getFollowedId().contains(userIdToFollow))
            throw new UserAlreadyFollowedException("El usuario de id " + userId + " ya esta siguiendo al usuario de id " + userIdToFollow);

        // Actualizamos las listas correspondientes de ambos usuarios
        user.getFollowedId().add(userIdToFollow);
        userToFollow.getFollowersId().add(userId);

        // Devolvemos un mensaje de exito
        return new MessageResponseDTO("Se ha seguido al usuario "+ userIdToFollow + " con exito.");
    }

    /**
     *  Se devuelve la cantidad de usuarios seguidos
    */
    @Override
    public UserCountFollowersDTO countNumberOfFollowers(Integer id) {
        User user = userRepository.getUser(id);
        return new UserCountFollowersDTO(user.getFollowersId().size());
    }

    /**
    *   Dado el ID de un usuario, devuelve un DTO con una lista de los seguidores de ese usuario
    */
    @Override
    public UserFollowersOutputListDTO getFollowersList(Integer userId, String order) {
        User user = userRepository.getUser(userId);

        return UserFollowersOutputListDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .followers(buildUserOutputList(user.getFollowersId(), order))
                .build();
    }

    /**
     * Dado el ID de un usuario, devuelve un DTO con una lista de usuarios a los que sigue
     */
    @Override
    public UserFollowedOutputListDTO getFollowedList(Integer userId, String order) {
        User user = userRepository.getUser(userId);

        return UserFollowedOutputListDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .followed(buildUserOutputList(user.getFollowedId(),order))
                .build();
    }

    /**
     *  Dada una lista de IDs, devuelve la lista de sus respectivos usuarios (OutputDTO)
    */
    private List<UserOutputDTO> buildUserOutputList(List<Integer> idList, String order){
        List<User> userList = idList.stream()
                .map(anId -> userRepository
                        .getUser(anId))
                .toList();

        List<UserOutputDTO> userOutputList = userList.stream()
                .map(anUser -> UserOutputDTO.builder()
                        .id(anUser.getId())
                        .name(anUser.getName())
                        .build())
                .toList();

        return sortList(userOutputList,order);
    }

    /**
     * Implementacion para dejar de seguir a un usuario.
     *
     */
    @Override
    public MessageResponseDTO unfollowUser(Integer userId, Integer userIdToUnfollow) {

        if(Objects.equals(userId, userIdToUnfollow))
            throw new UserCantFollowItselfException("El usuario no puede dejar de seguirse a si mismo");

        User user = userRepository.getUser(userId);
        User userToUnfollow = userRepository.getUser(userIdToUnfollow);

        if (!user.getFollowedId().contains(userIdToUnfollow))
            throw new UserIsNotFollowedException("El usuario de id " + userId + " no esta siguiendo al usuario de id " + userIdToUnfollow);

        userToUnfollow.getFollowersId().remove(userId);
        user.getFollowedId().remove(userIdToUnfollow);

        return new MessageResponseDTO("Se ha dejado de seguir al usuario " + userIdToUnfollow + " con exito.");
    }

    /**
     * Se ordena la lista de seguidores por nombre y en cierto orden requerido
     */
    private List<UserOutputDTO> sortList(List<UserOutputDTO> userList, String order) {
        return switch (order) {
            case "name_asc" -> userList.stream()
                    .sorted(Comparator.comparing(UserOutputDTO::getName))
                    .toList();
            case "name_desc" -> userList.stream()
                    .sorted(Comparator.comparing(UserOutputDTO::getName).reversed())
                    .toList();
            default -> throw new ElementNotFoundException("Parametro no correspondiente");
        };
    }

}
