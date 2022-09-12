package com.w17_g1.socialMeLi.services;


import com.w17_g1.socialMeLi.dto.output.PublicationDTO;
import com.w17_g1.socialMeLi.dto.output.PublicationListDTO;
import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import com.w17_g1.socialMeLi.model.User;
import com.w17_g1.socialMeLi.repository.publication.IPublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicationService {

  @Autowired
  IPublicationRepository publicationRepository;
  @Autowired
  IUserRepository userRepository;

  /**Lista de seguidos de un user**/
  public List<Integer> getUsersFollowedId(Integer userId) {
    return userRepository.usersFollowedIds(userId);
  }

  /**US 0006: listado de las publicaciones realizadas por los vendedores que un usuario sigue en las últimas dos semanas
   * (ordenamiento por fecha, publicaciones más recientes primero) **/
  public PublicationListDTO getLatestPublicationsFromUser(Integer userId) {

    User user = userRepository.getUser(userId)
            .orElseThrow(() -> new ElementNotFoundException("No se encontro el ID solicitado"));
    List<Integer> follows = getUsersFollowedId(user.getId());
    List<PublicationDTO> posts = new ArrayList<>();
    LocalDate today = LocalDate.now();
    LocalDate searchAfterDate = today.minusDays(15);
    if(follows.isEmpty()){
      throw new ElementNotFoundException("Aún el usuario no sique a nadie");
    }
    for (Integer followedId : follows) {
      posts.addAll(publicationRepository.getPublicationsFromUser(followedId,searchAfterDate).stream()
              .map(p -> PublicationDTO.builder().postId(p.getId()).userId(p.getUserId()).product(p.getProduct())
                              .price(p.getPrice()).date(p.getPublishDate()).category(p.getCategory()).build()
              )
              .collect(Collectors.toList()));
    }
    posts.sort(Comparator.comparing(PublicationDTO::getDate).reversed());

    if(posts.isEmpty()){
      throw new ElementNotFoundException("No hay nuevas publicaciones");
    }
    return PublicationListDTO.builder().userId(userId).posts(posts).build();
  }
    @Autowired
    IPublicationRepository publicationRepository;

    // Mapeamos el prodcuto y la publicacion en el DTO que vamos a devolver con el id de la nueva publicacion
    public PublicationIdDTO createPublication(PublicationDTO publicationDTO) {
        Product product = new Product(publicationDTO.getProduct().getProduct_id(),publicationDTO.getProduct().getProduct_name(),publicationDTO.getProduct().getType(),publicationDTO.getProduct().getBrand(),publicationDTO.getProduct().getColor(),publicationDTO.getProduct().getNotes());
        Publication publication = new Publication(0,publicationDTO.getUser_id(),publicationDTO.getDate(),publicationDTO.getPrice(),product,publicationDTO.getCategory());
        Optional<Publication> result = publicationRepository.createPublication(publication);
        return new PublicationIdDTO(result.get().getId());
    }
}
