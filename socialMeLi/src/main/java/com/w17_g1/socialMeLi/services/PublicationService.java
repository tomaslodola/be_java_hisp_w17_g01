package com.w17_g1.socialMeLi.services;


import com.w17_g1.socialMeLi.dto.output.PublicationDTO;
import com.w17_g1.socialMeLi.dto.output.PublicationListDTO;
import com.w17_g1.socialMeLi.model.Publication;
import com.w17_g1.socialMeLi.repository.publication.IPublicationRepository;
import com.w17_g1.socialMeLi.repository.user.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicationService {
  private ModelMapper mapper = new ModelMapper();

  @Autowired
  IPublicationRepository publicationRepository;
  @Autowired
  IUserRepository userRepository;

  public PublicationListDTO getPublicationsFromUser(Integer userId) {
    List<Integer> follows = userRepository.userFollowing(userId);
    System.out.println(follows);
    List<PublicationDTO> posts = new ArrayList<>();
    for (Integer followedId : follows) {

      posts.addAll(publicationRepository.getPublicationsFromUser(followedId).stream()
              .map(p ->
                      mapper.map(p, PublicationDTO.class)
              )
              .collect(Collectors.toList()));
    }
    return PublicationListDTO.builder().userId(userId).posts(posts).build();

  }
}
