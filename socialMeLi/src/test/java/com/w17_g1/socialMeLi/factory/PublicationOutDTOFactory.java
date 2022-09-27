package com.w17_g1.socialMeLi.factory;

import com.w17_g1.socialMeLi.dto.output.PublicationOutDTO;
import com.w17_g1.socialMeLi.model.Publication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PublicationOutDTOFactory {

    public static PublicationOutDTO createPublicationOutDTOForUser(Integer userID, Integer postID){
        return PublicationOutDTO.builder()
                .userId(userID)
                .postId(postID)
                .date(LocalDate.of(2022,9,20))
                .product(ProductFactory.getProduct(postID))
                .category(0)
                .price(100D)
                .build();
    }

    public static List<PublicationOutDTO> createPublicationOutDTOListForUser(Integer userID, Integer numberOfPosts){
        List<PublicationOutDTO> listOfPublications = new ArrayList<>();
        for (int i = 0; i< numberOfPosts; i++)
            listOfPublications.add(createPublicationOutDTOForUser(userID,i));
        return listOfPublications;
    }

}
