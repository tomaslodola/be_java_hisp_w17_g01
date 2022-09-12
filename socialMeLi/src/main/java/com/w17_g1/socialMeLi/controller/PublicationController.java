package com.w17_g1.socialMeLi.controller;

import com.w17_g1.socialMeLi.services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicationController {

    @Autowired
    PublicationService publicationSrv;

    @GetMapping("/products/followed/{userId}/list")
    public ResponseEntity<?> getProducts(@PathVariable Integer userId, @RequestParam(value = "order",required = false) String order){
        if(order!=null)
            return new ResponseEntity<>(publicationSrv)

    }

}
