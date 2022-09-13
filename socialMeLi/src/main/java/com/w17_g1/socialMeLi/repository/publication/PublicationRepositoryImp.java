package com.w17_g1.socialMeLi.repository.publication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.w17_g1.socialMeLi.dto.input.PublicationDTO;
import com.w17_g1.socialMeLi.exceptions.DuplicateElementException;
import com.w17_g1.socialMeLi.exceptions.ElementNotFoundException;
import com.w17_g1.socialMeLi.model.Publication;
import com.w17_g1.socialMeLi.model.User;
import com.w17_g1.socialMeLi.repository.user.UserRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public class PublicationRepositoryImp implements IPublicationRepository {
  List<Publication> publicationList;
  @Autowired
  UserRepositoryImp userRepository;

  public PublicationRepositoryImp() {
    this.publicationList = loadDataBase();
    System.out.println(publicationList);
  }

  /**
   * Obtiene las Publicaciones de un usuario en base a id, y una fecha de tope minimo
   **/
  public List<Publication> getPublicationsFromUser(Integer userId, LocalDate searchAfterDate) {
    return publicationList.stream()
            .filter(p -> Objects.equals(p.getUserId(), userId) && p.getPublishDate().isAfter(searchAfterDate))
            .collect(Collectors.toList());
  }

  private List<Publication> loadDataBase() {
    List<Publication> publicationList = null;
    File file;
    ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
            .registerModule(new JavaTimeModule());
    TypeReference<List<Publication>> typeRef = new TypeReference<>() {
    };
    try {
      file = ResourceUtils.getFile("classpath:Publications.json");
      publicationList = objectMapper.readValue(file, typeRef);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return publicationList;
  }

  @Override
  public Optional<Publication> createPublication(Publication publication) {
    Optional<Publication> optionalPublication = Optional.empty();
    create(publication);
    optionalPublication = Optional.of(publication);
    return optionalPublication;
  }

  @Override
  public Boolean createPromoPublication(Publication publication){
    return create(publication);
  }

  @Override
  public Boolean create(Publication publication){
    // Controlamos que el usuario exista y buscamos en la lista de publicaciones del mismo el mayor numerador

    userRepository.validateUser(publication.getUserId());

    List<Publication> userPublications = publicationList.stream()
            .filter(p -> Objects.equals(p.getUserId(), publication.getUserId()))
            .toList();

    if (userPublications.isEmpty()) { publication.setId(0); }
    else {
      publication.setId(userPublications.stream()
              .map(Publication::getId)
              .max(Integer::compare).get() + 1);
    }

    // Si existe el usuario y no se ingresa un producto duplicado
    if (userPublications.stream().anyMatch(p -> Objects.equals(p.getProduct().getId(), publication.getProduct().getId()))) {
      throw new DuplicateElementException(String.format("Publicacion duplicada para el producto con id:%s", publication.getProduct().getId()));
    }

    return publicationList.add(publication);
  }

  @Override
  public Integer getQuantityPublicationInPromoByUser(Integer userId){
    Long quantityOfPublication = publicationList.stream()
            .filter(p -> Objects.equals(p.getUserId(), userId) && p.getHas_promo() == true)
            .count();

    return quantityOfPublication.intValue();
  }
}


