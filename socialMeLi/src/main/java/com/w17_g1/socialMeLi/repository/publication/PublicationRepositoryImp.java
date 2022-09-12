package com.w17_g1.socialMeLi.repository.publication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.w17_g1.socialMeLi.dto.output.PublicationListDTO;
import com.w17_g1.socialMeLi.model.Publication;
import com.w17_g1.socialMeLi.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PublicationRepositoryImp implements IPublicationRepository {
  List<Publication> publicationList;

  public PublicationRepositoryImp() {
    this.publicationList = loadDataBase();
    System.out.println(publicationList);
  }

  /**Obtiene las Publicaciones de un usuario en base a id, y una fecha de tope minimo**/
  public List<Publication> getPublicationsFromUser(Integer userId,LocalDate searchAfterDate) {
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

}
