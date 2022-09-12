package com.w17_g1.socialMeLi.repository.publication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.w17_g1.socialMeLi.dto.output.PublicationListDTO;
import com.w17_g1.socialMeLi.exceptions.DuplicateElementException;
import com.w17_g1.socialMeLi.model.Publication;
import com.w17_g1.socialMeLi.model.User;
import com.w17_g1.socialMeLi.repository.user.UserRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
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

  @Override
  public List<Publication> sortPublicationList(Integer userId,String order)
  {
      List<Publication> publicationByUser = this.getPublicationByUserId(userId);

      if(publicationByUser.size()<2)
          return publicationByUser;

      if(order.equals("date_desc"))
      {
          return publicationByUser.stream().sorted(Comparator.comparing(Publication::getPublishDate).reversed()).collect(Collectors.toList());
      }
      else
      {
          return publicationByUser.stream().sorted(Comparator.comparing(Publication::getPublishDate)).collect(Collectors.toList());
      }
  }

  /**
   * Obtiene las Publicaciones de un usuario en base a id, y una fecha de tope minimo
   **/
  public List<Publication> getPublicationsFromUser(Integer userId, LocalDate searchAfterDate) {
    var publicationList1 = publicationList.stream().filter(p -> p.getUserId() == userId && p.getPublishDate().isAfter(searchAfterDate)).collect(Collectors.toList());
    return publicationList1;
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
    // Controlamos que el usuario exista y buscamos en la lista de publicaciones del mismo el mayor numerador
    Optional<Publication> optionalPublication = Optional.empty();
    User user = userRepository.getUserById(publication.getUserId());
    List<Publication> userPublications = publicationList.stream().filter(p -> p.getUserId() == user.getId()).toList();
    Integer autoNumber = userPublications.stream().map(p -> p.getId()).max(Integer::compare).get() + 1;
    publication.setId(autoNumber);
    // Si existe el usuario y no se ingresa un producto duplicado
    if (user == null || userPublications.stream().anyMatch(p -> p.getProduct().getId() == publication.getProduct().getId())) {
      throw new DuplicateElementException(String.format("Publicacion duplicada para el producto con id:%s", publication.getProduct().getId()));
    }
    optionalPublication = Optional.of(publication);
    publicationList.add(publication);
    return optionalPublication;

  }
}


