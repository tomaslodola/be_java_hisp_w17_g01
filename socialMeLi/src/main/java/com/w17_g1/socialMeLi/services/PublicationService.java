package com.w17_g1.socialMeLi.services;


import com.w17_g1.socialMeLi.repository.publication.IPublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PublicationService {

    @Autowired
    IPublicationRepository publicationRepository;
}
