package com.w17_g1.socialMeLi.controller;

import com.w17_g1.socialMeLi.dto.output.MessageResponseDTO;
import com.w17_g1.socialMeLi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<MessageResponseDTO> followUser(@PathVariable Integer userId,
                                                        @PathVariable Integer userIdToFollow){

        return new ResponseEntity<>(userService.followUser(userId, userIdToFollow), HttpStatus.valueOf(201));
    }

}
