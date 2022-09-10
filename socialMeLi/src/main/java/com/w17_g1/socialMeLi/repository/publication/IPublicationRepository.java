package com.w17_g1.socialMeLi.repository.publication;

import com.w17_g1.socialMeLi.model.Publication;

import java.util.List;

public interface IPublicationRepository {

  public List<Publication> getPublicationsFromUser(Integer userId);

}
