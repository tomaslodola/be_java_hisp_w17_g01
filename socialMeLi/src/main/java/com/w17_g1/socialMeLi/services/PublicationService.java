package com.w17_g1.socialMeLi.services;

import com.w17_g1.socialMeLi.dto.input.PublicationDTO;
import com.w17_g1.socialMeLi.dto.output.Publication.ProductDTO;
import com.w17_g1.socialMeLi.dto.output.Publication.PublicationIdDTO;
import com.w17_g1.socialMeLi.dto.output.Publication.PublicationOutDTO;
import com.w17_g1.socialMeLi.dto.output.Publication.PublicationListDTO;
import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import com.w17_g1.socialMeLi.model.Product;
import com.w17_g1.socialMeLi.model.Publication;
import com.w17_g1.socialMeLi.repository.publication.IPublicationRepository;
import com.w17_g1.socialMeLi.repository.user.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicationService implements IPublicationService{

  @Autowired
  IPublicationRepository publicationRepository;
  @Autowired
  IUserRepository userRepository;

  private final ModelMapper mapper = new ModelMapper();

  /**US 0006: listado de las publicaciones realizadas por los vendedores que un usuario sigue en las últimas dos semanas
   * (ordenamiento por fecha, publicaciones más recientes primero) **/
  @Override
  public PublicationListDTO getLatestPublicationsFromUser(Integer userId,String order) {

    // La lista de publicaciones que màs adelante devolveremos
    List<PublicationOutDTO> posts = new ArrayList<>();

    // Se define la fecha limite antes de que las publicaciones expiren
    LocalDate searchAfterDate = LocalDate.now().minusDays(15);

    // Tomamos todos los usuarios seguidos por el usuario recibido
    List<Integer> follows = userRepository.getUser(userId).getFollowedId();

    // Excepcion: El usuario recibido no sigue a ningun otro usuario
    if(follows.isEmpty()) throw new ElementNotFoundException("Aún el usuario no sique a nadie");

    // Por cada usuario seguido, vamos a tomar sus publicaciones no expiradas
    for (Integer followedId : follows) {
      posts.addAll(publicationRepository.getPublicationsFromUser(followedId, searchAfterDate).stream()
              .map(p -> {
                PublicationOutDTO newPublication = mapper.map(p,PublicationOutDTO.class);
                newPublication.setProduct( mapper.map(p.getProduct(),ProductDTO.class) );
                return newPublication;})
              .toList());
    }

    // Excepcion: Los usuarios seguidos no tienen ninguna publicacion
    if(posts.isEmpty()) throw new ElementNotFoundException("No hay nuevas publicaciones");

    // Finalmente ordenamos la lista de publicaciones y la devolvemos
    posts = sortPublicationList(posts,order);
    return PublicationListDTO.builder().userId(userId).posts(posts).build();
  }

  /** * US 0005: Dar de alta una nueva publicación*/
  @Override
  public PublicationIdDTO createPublication(PublicationDTO publicationDTO) {
      userRepository.isValidUser(publicationDTO.getUser_id());

      Publication publication = mapper.map(publicationDTO,Publication.class);
      Product product = mapper.map(publicationDTO.getProduct(),Product.class);
      publication.setProduct(product);

      Integer publicationID = publicationRepository.createPublication(publication);
      return new PublicationIdDTO(publicationID);
  }

  /** US 0009: Ordenamiento por fecha ascendente y descendente **/
  private List<PublicationOutDTO> sortPublicationList(List<PublicationOutDTO> publications,String order){
    return switch (order) {
      case "date_asc" -> publications.stream()
              .sorted(Comparator.comparing(PublicationOutDTO::getDate).reversed())
              .collect(Collectors.toList());
      case "date_desc" -> publications.stream()
              .sorted(Comparator.comparing(PublicationOutDTO::getDate))
              .collect(Collectors.toList());
      default -> throw new ElementNotFoundException("Parametro no correspondiente");
    };
  }

}
