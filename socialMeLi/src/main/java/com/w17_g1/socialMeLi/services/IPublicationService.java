package com.w17_g1.socialMeLi.services;

import com.w17_g1.socialMeLi.dto.input.PublicationDTO;
import com.w17_g1.socialMeLi.dto.output.Publication.PublicationIdDTO;
import com.w17_g1.socialMeLi.dto.output.Publication.PublicationListDTO;

public interface IPublicationService {
    PublicationListDTO getLatestPublicationsFromUser(Integer userId, String order);
    PublicationIdDTO createPublication(PublicationDTO publicationDTO);
}
