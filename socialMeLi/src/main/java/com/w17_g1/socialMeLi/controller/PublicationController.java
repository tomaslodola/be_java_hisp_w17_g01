package com.w17_g1.socialMeLi.controller;

import com.w17_g1.socialMeLi.dto.output.PublicationDTO;
import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import com.w17_g1.socialMeLi.services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.w17_g1.socialMeLi.dto.input.PublicationDTO;
import com.w17_g1.socialMeLi.dto.output.PublicationIdDTO;
import com.w17_g1.socialMeLi.services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PublicationController {
  @Autowired
  PublicationService publicationService;

    @PostMapping("/products/post")
    public ResponseEntity<?> createPublication(@RequestBody PublicationDTO publicationDTO) {
        PublicationIdDTO result = publicationService.createPublication(publicationDTO);
        return ResponseEntity.ok(result);
    }

    @Autowired
    PublicationService publicationSrv;

    @GetMapping("/products/followed/{userId}/list")
    public ResponseEntity<List<PublicationDTO>> getProducts(@PathVariable Integer userId, @RequestParam(value = "order",required = false) String order){
        return new ResponseEntity<>(publicationSrv.getPublicationListSorted(userId,order),HttpStatus.OK);
    }

  @GetMapping("/products/followed/{userId}/list")
  public ResponseEntity<?> getPublicationsFromUser(@PathVariable Integer userId) {
    return new ResponseEntity<>(publicationService.getLatestPublicationsFromUser(userId), HttpStatus.OK);
  }
}
