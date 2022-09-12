package com.w17_g1.socialMeLi.services;


import com.w17_g1.socialMeLi.dto.input.PublicationDTO;
import com.w17_g1.socialMeLi.dto.output.PublicationIdDTO;
import com.w17_g1.socialMeLi.model.Product;
import com.w17_g1.socialMeLi.model.Publication;
import com.w17_g1.socialMeLi.repository.publication.IPublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublicationService {

    @Autowired
    IPublicationRepository publicationRepository;

    // Mapeamos el prodcuto y la publicacion en el DTO que vamos a devolver con el id de la nueva publicacion
    public PublicationIdDTO createPublication(PublicationDTO publicationDTO) {
        Product product = new Product(publicationDTO.getProduct().getProduct_id(),publicationDTO.getProduct().getProduct_name(),publicationDTO.getProduct().getType(),publicationDTO.getProduct().getBrand(),publicationDTO.getProduct().getColor(),publicationDTO.getProduct().getNotes());
        Publication publication = new Publication(0,publicationDTO.getUser_id(),publicationDTO.getDate(),publicationDTO.getPrice(),product,publicationDTO.getCategory());
        Optional<Publication> result = publicationRepository.createPublication(publication);
        return new PublicationIdDTO(result.get().getId());
    }
}
