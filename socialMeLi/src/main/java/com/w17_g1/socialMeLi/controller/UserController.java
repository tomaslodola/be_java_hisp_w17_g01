package com.w17_g1.socialMeLi.controller;

import com.w17_g1.socialMeLi.dto.output.MessageResponseDTO;
import com.w17_g1.socialMeLi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.PostMapping;
=======
import com.w17_g1.socialMeLi.dto.output.UserCountFollowersDTO;
import com.w17_g1.socialMeLi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
>>>>>>> 80917f486ea036aa2817da05dc49627c5687d569
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService service;

<<<<<<< HEAD
    // Requerimiento US-0001: Seguir a un usuario determinado.
    @PostMapping("/users/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<MessageResponseDTO> followUser(@PathVariable Integer userId,
                                                        @PathVariable Integer userIdToFollow){

        return new ResponseEntity<>(service.followUser(userId, userIdToFollow), HttpStatus.valueOf(201));
=======
    // Requerimiento US 0002: Obtener numero de seguidores de un usuario
    @GetMapping("/users/{userId}/followers/count")
    public ResponseEntity<?> numberOfFollowers(@PathVariable Integer userId) {
        UserCountFollowersDTO result = service.countNumberOfFollowers(userId);
        return ResponseEntity.ok(result);
>>>>>>> 80917f486ea036aa2817da05dc49627c5687d569
    }

    // Requerimiento US-0003: Obtener un listado de todos los usuarios que siguen un determinado vendedor
    @GetMapping("users/{userId}/followers/list")
    public ResponseEntity<?> getFollowersList(@PathVariable Integer userId){
        return new ResponseEntity<>(service.getFollowersList(userId), HttpStatus.OK);
    }

    // Requerimiento US-0004: Obtener un listado de todos los vendedores a los cuales sigue un determinado usuario
    @GetMapping("users/{userId}/followed/list")
    public ResponseEntity<?> getFollowedList(@PathVariable Integer userId){
        return new ResponseEntity<>(service.getFollowedList(userId), HttpStatus.OK);
    }

    // Requerimiento US-0007: Dejar de seguir a determinado usuario.
    @PostMapping("/users/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> UnfollowUser(@PathVariable Integer userId,
                                          @PathVariable Integer userIdToUnfollow){
        return new ResponseEntity<>(service.unfollowUser(userId,userIdToUnfollow), HttpStatus.OK);
    }

}
