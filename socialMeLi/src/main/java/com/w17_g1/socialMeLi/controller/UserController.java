package com.w17_g1.socialMeLi.controller;

import com.w17_g1.socialMeLi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.w17_g1.socialMeLi.dto.output.UserCountFollowersDTO;
import com.w17_g1.socialMeLi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    // Requerimiento US 0002: Obtener numero de seguidores de un usuario
    @GetMapping("/users/{userId}/followers/count")
    public ResponseEntity<?> numberOfFollowers(@PathVariable Integer userId) {
        UserCountFollowersDTO result = service.countNumberOfFollowers(userId);
        return ResponseEntity.ok(result);
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

    // Requerimiento US-0008:Obtener lista de followers y followed de forma ordenada
    @GetMapping("sers/{userId}/followers/list?order")
    public  ResponseEntity<?> getFollowersSort(@PathVariable Integer id, @RequestParam String order){
        return  new ResponseEntity<>(service.sortFollowersList(id,order), HttpStatus.OK);
    }
    @GetMapping("sers/{userId}/followed/list?order")
    public  ResponseEntity<?> getFollowedSort(@PathVariable Integer id, @RequestParam String order){
        return  new ResponseEntity<>(service.sortFollowedList(id,order), HttpStatus.OK);
    }

}
