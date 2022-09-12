package com.w17_g1.socialMeLi.controller;

import com.w17_g1.socialMeLi.services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicationController {
  @Autowired
  PublicationService publicationService;

  @GetMapping("/products/followed/{userId}/list")
  public ResponseEntity<?> getPublicationsFromUser(@PathVariable Integer userId){

    return new ResponseEntity<>(publicationService.getLatestPublicationsFromUser(userId),HttpStatus.OK);
  }

}
