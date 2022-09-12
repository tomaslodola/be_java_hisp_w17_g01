package com.w17_g1.socialMeLi.controller;

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
    private PublicationService publicationService;

    @PostMapping("/products/post")
    public ResponseEntity<?> createPublication(@RequestBody PublicationDTO publicationDTO) {
        PublicationIdDTO result = publicationService.createPublication(publicationDTO);
        return ResponseEntity.ok(result);
    }
}
