package com.w17_g1.socialMeLi.repository.publication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.w17_g1.socialMeLi.exceptions.DuplicateElementException;
import com.w17_g1.socialMeLi.model.Publication;
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

@Repository
public class PublicationRepositoryImp implements IPublicationRepository {
  List<Publication> publicationList;

  @Autowired
  UserRepositoryImp userRepository;

  public PublicationRepositoryImp() {
    this.publicationList = loadDataBase();
  }

  /**
   * Obtiene las Publicaciones de un usuario en base a id, y una fecha de tope minimo
   **/
  public List<Publication> getPublicationsFromUser(Integer userId, LocalDate searchAfterDate) {
    return publicationList.stream()
            .filter(p -> Objects.equals(p.getUser_id(), userId) && p.getDate().isAfter(searchAfterDate))
            .collect(Collectors.toList());
  }

  /**
   * Se obtienen las publicaciones almacenadas en el archivo Json
   */
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

  /**
   * Implementacion de creacion de una publicacion
   */
  @Override
  public Integer createPublication(Publication publication) {
    List<Publication> userPublications = publicationList.stream()
            .filter(p -> Objects.equals(p.getUser_id(), publication.getUser_id()))
            .toList();

    if (userPublications.isEmpty()) { publication.setId(0); }
    else {
      publication.setId(userPublications.stream()
              .map(Publication::getId)
              .max(Integer::compare).get() + 1);
    }

    // Si existe el usuario y no se ingresa un producto duplicado
    if (userPublications.stream().anyMatch(p -> Objects.equals(p.getProduct().getProduct_id(), publication.getProduct().getProduct_id()))) {
      throw new DuplicateElementException(String.format("Publicacion duplicada para el producto con id:%s", publication.getProduct().getProduct_id()));
    }

    publicationList.add(publication);
    return publication.getId();
  }
}


