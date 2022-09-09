package com.w17_g1.socialMeLi.repository.publication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.w17_g1.socialMeLi.model.Publication;
import com.w17_g1.socialMeLi.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Repository
public class PublicationRepositoryImp implements IPublicationRepository{
  List<Publication> publicationList;

  public PublicationRepositoryImp(){
    this.publicationList = loadDataBase();
  }


  private List<Publication> loadDataBase(){
    List<Publication> publicationList = null;
    File file;
    ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.WRAP_ROOT_VALUE, false) //nueva
            .registerModule(new JavaTimeModule()); // nueva
    TypeReference<List<Publication>> typeRef = new TypeReference<>() {};
    try {
      file = ResourceUtils.getFile("classpath:Publications.json");
      publicationList = objectMapper.readValue(file, typeRef);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return publicationList;
  }

}
