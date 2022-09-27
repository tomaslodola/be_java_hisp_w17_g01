package com.w17_g1.socialMeLi.controller;

import com.w17_g1.socialMeLi.dto.output.MessageResponseDTO;
import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import com.w17_g1.socialMeLi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    /**
     * Requerimiento US-0001: Seguir a un usuario determinado.
     * @param userId
     * @param userIdToFollow
     * @return ResponseEntity<MessageResponseDTO>
     * */
    @PostMapping("/users/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<MessageResponseDTO> followUser(@Valid @PathVariable Integer userId,
                                                         @PathVariable Integer userIdToFollow) {
        return new ResponseEntity<>(service.followUser(userId, userIdToFollow), HttpStatus.valueOf(201));
    }

    /**
     * Requerimiento US 0002: Obtener numero de seguidores de un usuario.
     * @param userId
     * @return ResponseEntity<?>
     * */
    @GetMapping("/users/{userId}/followers/count")
    public ResponseEntity<?> numberOfFollowers(@PathVariable Integer userId) {
        return new ResponseEntity<>(service.countNumberOfFollowers(userId), HttpStatus.OK);
    }

    /**
     * Requerimiento US-0003 y US-0008: Obtener un listado de todos los usuarios que siguen un determinado vendedor.
     * @param userId
     * @param order
     * @return ResponseEntity<?>
     * */
    @GetMapping("users/{userId}/followers/list")
    public ResponseEntity<?> getFollowersList(@PathVariable Integer userId, @RequestParam(value = "order") String order) {
        return new ResponseEntity<>(service.getFollowersList(userId, order), HttpStatus.OK);
    }

    /**
     * Requerimiento US-0004 y US-008: Obtener un listado de todos los vendedores a los cuales sigue un determinado usuario.
     * @param userId
     * @param order
     * @return ResponseEntity<?>
     * */
    @GetMapping("users/{userId}/followed/list")
    public ResponseEntity<?> getFollowedList(@PathVariable Integer userId, @RequestParam(value = "order", defaultValue = "name_asc") String order) {
        return new ResponseEntity<>(service.getFollowedList(userId, order), HttpStatus.OK);
    }

    /**
     * Requerimiento US-0007: Dejar de seguir a determinado usuario.
     * @param userId
     * @param userIdToUnfollow
     * @return ResponseEntity<?>
     * */
    @PostMapping("/users/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> UnfollowUser(@Valid @PathVariable Integer userId,
                                          @PathVariable Integer userIdToUnfollow) {
        return new ResponseEntity<>(service.unfollowUser(userId, userIdToUnfollow), HttpStatus.OK);
    }

}


