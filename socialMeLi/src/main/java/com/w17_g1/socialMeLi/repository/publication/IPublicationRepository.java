package com.w17_g1.socialMeLi.repository.publication;

import com.w17_g1.socialMeLi.model.Publication;

import java.util.List;

public interface IPublicationRepository {
    List<Publication> loadDataBase();
    List<Publication> sortPublicationList(Integer userId,String order);

    List<Publication> getPublicationByUserId(Integer uid);
}
