package com.w17_g1.socialMeLi.services;


import com.w17_g1.socialMeLi.dto.input.ProductDTO;
import com.w17_g1.socialMeLi.dto.input.PromoPublicationDTO;
import com.w17_g1.socialMeLi.dto.input.PublicationDTO;
import com.w17_g1.socialMeLi.dto.output.*;
import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import com.w17_g1.socialMeLi.model.Product;
import com.w17_g1.socialMeLi.model.Publication;
import com.w17_g1.socialMeLi.model.User;
import com.w17_g1.socialMeLi.repository.publication.IPublicationRepository;
import com.w17_g1.socialMeLi.repository.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublicationService {

  @Autowired
  IPublicationRepository publicationRepository;
  @Autowired
  IUserRepository userRepository;

  /**Lista de seguidos de un user**/
  public List<Integer> getUsersFollowedId(Integer userId) { return userRepository.usersFollowedIds(userId); }

  /**US 0006: listado de las publicaciones realizadas por los vendedores que un usuario sigue en las últimas dos semanas
   * (ordenamiento por fecha, publicaciones más recientes primero) **/
  public PublicationListDTO getLatestPublicationsFromUser(Integer userId,String order) {

    List<PublicationOutDTO> posts = new ArrayList<>();

    LocalDate today = LocalDate.now();
    LocalDate searchAfterDate = today.minusDays(15);

    User user = userRepository.getUser(userId)
            .orElseThrow(() -> new ElementNotFoundException("No se encontro el ID solicitado"));

    List<Integer> follows = getUsersFollowedId(user.getId());

    if(follows.isEmpty()){
      throw new ElementNotFoundException("Aún el usuario no sique a nadie");
    }

    for (Integer followedId : follows) {
      posts.addAll(publicationRepository.getPublicationsFromUser(followedId, searchAfterDate).stream()
              .map(p -> PublicationOutDTO.builder()
                      .postId(p.getId())
                      .userId(p.getUserId())
                      .product(p.getProduct())
                      .price(p.getPrice())
                      .date(p.getPublishDate())
                      .category(p.getCategory())
                      .build()
              ).toList());
    }

    if(posts.isEmpty()){
      throw new ElementNotFoundException("No hay nuevas publicaciones");
    }

    posts = sortPublicationList(posts,order);

    return PublicationListDTO.builder().userId(userId).posts(posts).build();
  }

    // Mapeamos el prodcuto y la publicacion en el DTO que vamos a devolver con el id de la nueva publicacion
    public PublicationIdDTO createPublication(PublicationDTO publicationDTO) {

      Product product = Product.builder()
                .id(publicationDTO.getProduct().getProduct_id())
                .name(publicationDTO.getProduct().getProduct_name())
                .type(publicationDTO.getProduct().getType())
                .brand(publicationDTO.getProduct().getBrand())
                .color(publicationDTO.getProduct().getColor())
                .notes(publicationDTO.getProduct().getNotes())
                .build();

      Publication publication = Publication.builder()
              .userId(publicationDTO.getUser_id())
              .publishDate(publicationDTO.getDate())
              .price(publicationDTO.getPrice())
              .product(product)
              .category(publicationDTO.getCategory())
              .hasPromo(false)
              .discount(0.0)
              .build();

      Optional<Publication> result = publicationRepository.createPublication(publication);
        return new PublicationIdDTO(result.get().getId());
    }

  private List<PublicationOutDTO> sortPublicationList(List<PublicationOutDTO> publications,String order){
    if(order.equals("date_asc"))
      return publications.stream().sorted(Comparator.comparing(PublicationOutDTO::getDate).reversed()).collect(Collectors.toList());
    return publications.stream().sorted(Comparator.comparing(PublicationOutDTO::getDate)).collect(Collectors.toList());
  }

  public PublicationIdDTO createPromoPublication(PromoPublicationDTO publicationDTO) {

    Product product = Product.builder()
            .id(publicationDTO.getProduct().getProduct_id())
            .name(publicationDTO.getProduct().getProduct_name())
            .type(publicationDTO.getProduct().getType())
            .brand(publicationDTO.getProduct().getBrand())
            .color(publicationDTO.getProduct().getColor())
            .notes(publicationDTO.getProduct().getNotes())
            .build();

    Publication publication = Publication.builder()
            .userId(publicationDTO.getUser_id())
            .publishDate(publicationDTO.getDate())
            .price(publicationDTO.getPrice())
            .product(product)
            .category(publicationDTO.getCategory())
            .hasPromo(publicationDTO.getHas_promo())
            .discount(publicationDTO.getDiscount())
            .build();

    Optional<Publication> result = publicationRepository.createPublication(publication);
    return new PublicationIdDTO(result.get().getId());
  }
  public UserPromoPublicationCountDTO getCountPromoPublicationsFromUser(Integer userId) {
    List<Publication> userPublication = publicationRepository.getPromoPublicationsFromUser(userId);
    User user = userRepository.getUser(userId).orElseThrow(() -> new ElementNotFoundException("No se encuentra el usuario con el id " + userId));
    return new UserPromoPublicationCountDTO(userId,user.getName(),userPublication.size());
  }

  public PromoPublicationOutDTO getPromoPublicationsFromUser(Integer userId) {
    List<PublicationDTO> posts = new ArrayList<>();

    User user = userRepository.getUser(userId)
            .orElseThrow(() -> new ElementNotFoundException("No se encontro el ID solicitado"));
    List<Publication> userPublication = publicationRepository.getPromoPublicationsFromUser(userId);
    posts.addAll(userPublication.stream()
            .map(p -> PublicationDTO.builder()
                    .user_id(p.getUserId())
                    .product(new ProductDTO(p.getProduct().getId(),p.getProduct().getName(),p.getProduct().getType(),p.getProduct().getBrand(),p.getProduct().getColor(),p.getProduct().getNotes()))
                    .price(p.getPrice())
                    .date(p.getPublishDate())
                    .category(p.getCategory())
                    .has_promo(p.getHasPromo())
                    .discount(p.getDiscount())
                    .build()
            ).toList());
    return new PromoPublicationOutDTO(user.getId(),user.getName(),posts);
  }
}
