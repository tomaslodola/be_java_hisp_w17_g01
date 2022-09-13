package com.w17_g1.socialMeLi.controller;

import com.w17_g1.socialMeLi.dto.input.PromoPublicationDTO;
import com.w17_g1.socialMeLi.services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.w17_g1.socialMeLi.dto.input.PublicationDTO;
import com.w17_g1.socialMeLi.dto.output.PublicationIdDTO;
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
  public ResponseEntity<?> getPublicationsFromUser(@PathVariable Integer userId, @RequestParam(value = "order",required = false, defaultValue = "date_asc") String order) {
    return new ResponseEntity<>(publicationService.getLatestPublicationsFromUser(userId,order), HttpStatus.OK);
  }

  @PostMapping("/products/promo-post")
  public ResponseEntity<?> createPromoPublication(@RequestBody PromoPublicationDTO promoPublicationDTO) {
    PublicationIdDTO publicationIdDTO = publicationService.createPromoPublication(promoPublicationDTO);
    return ResponseEntity.ok(publicationIdDTO);
  }
  @GetMapping("/products/promo-post/count")
  public ResponseEntity<?> getCountPromoPublicationsFromUser(@RequestParam Integer userId) {
    return new ResponseEntity<>(publicationService.getCountPromoPublicationsFromUser(userId), HttpStatus.OK);
  }

  @GetMapping("/products/promo-post/list")
  public ResponseEntity<?> getPromoPublicationsFromUser(@RequestParam Integer userId) {
    return new ResponseEntity<>(publicationService.getPromoPublicationsFromUser(userId), HttpStatus.OK);
  }
}
