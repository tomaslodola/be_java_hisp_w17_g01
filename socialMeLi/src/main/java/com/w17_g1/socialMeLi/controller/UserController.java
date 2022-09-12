package com.w17_g1.socialMeLi.controller;

import com.w17_g1.socialMeLi.dto.output.UserCountFollowersDTO;
import com.w17_g1.socialMeLi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users/{userId}/followers/count")
    public ResponseEntity<?> numberOfFollowers(@PathVariable Integer userId) {
        UserCountFollowersDTO result = userService.countNumberOfFollowers(userId);
        return ResponseEntity.ok(result);
    }
}
