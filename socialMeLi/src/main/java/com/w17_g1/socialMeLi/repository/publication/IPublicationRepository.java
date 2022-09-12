package com.w17_g1.socialMeLi.repository.publication;

import com.w17_g1.socialMeLi.model.Publication;
import java.util.List;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

public interface IPublicationRepository {

  public List<Publication> getPublicationsFromUser(Integer userId, LocalDate searchAfterDate);

  public Optional<Publication> createPublication(Publication publication);

  List<Publication> sortPublicationList(Integer userId,String order);

  List<Publication> getPublicationByUserId(Integer uid);

}
