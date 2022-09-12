package com.w17_g1.socialMeLi.controller;

import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import com.w17_g1.socialMeLi.services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.w17_g1.socialMeLi.dto.input.PublicationDTO;
import com.w17_g1.socialMeLi.dto.output.PublicationIdDTO;
import com.w17_g1.socialMeLi.services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicationController {
  @Autowired
  PublicationService publicationService;

    @PostMapping("/products/post")
    public ResponseEntity<?> createPublication(@RequestBody PublicationDTO publicationDTO) {
        PublicationIdDTO result = publicationService.createPublication(publicationDTO);
        return ResponseEntity.ok(result);
    }

  @GetMapping("/products/followed/{userId}/list")
  public ResponseEntity<?> getPublicationsFromUser(@PathVariable Integer userId) {
    return new ResponseEntity<>(publicationService.getLatestPublicationsFromUser(userId), HttpStatus.OK);
  }
}
