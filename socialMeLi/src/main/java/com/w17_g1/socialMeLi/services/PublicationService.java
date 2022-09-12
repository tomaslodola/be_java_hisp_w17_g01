package com.w17_g1.socialMeLi.services;


import com.w17_g1.socialMeLi.dto.output.PublicationDTO;
import com.w17_g1.socialMeLi.dto.output.PublicationListDTO;
import com.w17_g1.socialMeLi.model.Publication;
import com.w17_g1.socialMeLi.repository.publication.IPublicationRepository;
import com.w17_g1.socialMeLi.repository.user.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicationService {
  private ModelMapper mapper = new ModelMapper();

  @Autowired
  IPublicationRepository publicationRepository;
  @Autowired
  IUserRepository userRepository;

  public List<Integer> getUsersFollowedId(Integer userId) {
    return userRepository.usersFollowedIds(userId);
  }

  public PublicationListDTO getLatestPublicationsFromUser(Integer userId) {
    List<Integer> follows = getUsersFollowedId(userId);
    List<PublicationDTO> posts = new ArrayList<>();
    LocalDate today = LocalDate.now();
    LocalDate searchAfterDate = today.minusDays(15);
    for (Integer followedId : follows) {
      posts.addAll(publicationRepository.getPublicationsFromUser(followedId,searchAfterDate).stream()
              .map(p -> PublicationDTO.builder().postId(p.getId()).userId(p.getUserId()).product(p.getProduct())
                              .price(p.getPrice()).date(p.getPublishDate()).category(p.getCategory()).build()
              )
              .collect(Collectors.toList()));
    }
    posts.sort(Comparator.comparing(PublicationDTO::getDate).reversed());
    return PublicationListDTO.builder().userId(userId).posts(posts).build();
  }
}
