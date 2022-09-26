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

    // Requerimiento US-0001: Seguir a un usuario determinado.
    @PostMapping("/users/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<MessageResponseDTO> followUser(@Valid @PathVariable Integer userId,
                                                         @PathVariable Integer userIdToFollow) {
        return new ResponseEntity<>(service.followUser(userId, userIdToFollow), HttpStatus.valueOf(201));
    }

    // Requerimiento US 0002: Obtener numero de seguidores de un usuario
    @GetMapping("/users/{userId}/followers/count")
    public ResponseEntity<?> numberOfFollowers(@Valid @PathVariable Integer userId) {
        return new ResponseEntity<>(service.countNumberOfFollowers(userId), HttpStatus.OK);
    }

    // Requerimiento US-0003 y US-0008: Obtener un listado de todos los usuarios que siguen un determinado vendedor
    @GetMapping("users/{userId}/followers/list")
    public ResponseEntity<?> getFollowersList(@Valid @PathVariable Integer userId, @RequestParam(value = "order") String order) {
        return new ResponseEntity<>(service.sortFollowersList(userId, order), HttpStatus.OK);
    }

    // Requerimiento US-0004 y US-008: Obtener un listado de todos los vendedores a los cuales sigue un determinado usuario
    @GetMapping("users/{userId}/followed/list")
    public ResponseEntity<?> getFollowedList(@Valid @PathVariable Integer userId, @RequestParam(value = "order", defaultValue = "name_asc") String order) {
        if (order.equals("name_asc")) {
            return new ResponseEntity<>(service.sortFollowedList(userId, order), HttpStatus.OK);
        }
        if (order.equals("name_desc")) {
            return new ResponseEntity<>(service.sortFollowedList(userId, order), HttpStatus.OK);
        }
        throw new ElementNotFoundException("Parametro no correspondiente");
    }


    // Requerimiento US-0007: Dejar de seguir a determinado usuario.
    @PostMapping("/users/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> UnfollowUser(@Valid @PathVariable Integer userId,
                                          @PathVariable Integer userIdToUnfollow) {
        return new ResponseEntity<>(service.unfollowUser(userId, userIdToUnfollow), HttpStatus.OK);
    }

}


