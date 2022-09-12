package com.w17_g1.socialMeLi.repository.publication;

import com.w17_g1.socialMeLi.model.Publication;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

public interface IPublicationRepository {
  List<Publication> getPublicationsFromUser(Integer userId, LocalDate searchAfterDate);
  Optional<Publication> createPublication(Publication publication);
}
