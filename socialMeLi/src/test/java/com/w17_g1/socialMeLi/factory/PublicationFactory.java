package com.w17_g1.socialMeLi.factory;

import com.w17_g1.socialMeLi.dto.output.PublicationOutDTO;
import com.w17_g1.socialMeLi.model.Product;
import com.w17_g1.socialMeLi.model.Publication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PublicationFactory {

    // Datos genericos que no son relevantes para la ejecución de los Test
    private static final Integer genereicPostID = 9999;
    private static final Integer genereicProductID = 99;
    private static final Integer genereicCategory = 0;
    private static final Double genereicPrice = 100D;

    // Metodo que crea publicaciones con el ID de usuario y la fecha de publicacion que recibe como parametros
    public static Publication createPublicationForUser(Integer userID, LocalDate publishDate){
        return Publication.builder()
                .userId(userID)
                .id(genereicPostID)
                .publishDate(publishDate)
                .product(ProductFactory.getProduct(genereicProductID))
                .category(genereicCategory)
                .price(genereicPrice)
                .build();
    }

    // Metodo que toma una publicacion y devuelve un DTO de salida con los mismos datos
    public static PublicationOutDTO outDTOFromPublication(Publication publication) {
        return PublicationOutDTO.builder()
                .userId(publication.getUserId())
                .postId(publication.getId())
                .date(publication.getPublishDate())
                .product(publication.getProduct())
                .category(publication.getCategory())
                .price(publication.getPrice())
                .build();
    }

    // Dado 3 publicaciones devuelve una lista ordenada de forma ascendente por fecha
    public static List<PublicationOutDTO> getAscendentList(Publication older, Publication recent, Publication newer){
        return List.of(
                outDTOFromPublication(newer),
                outDTOFromPublication(recent),
                outDTOFromPublication(older)
        );
    }

    // Dado 3 publicaciones devuelve una lista ordenada de forma descendente por fecha
    public static List<PublicationOutDTO> getDescendentList(Publication older, Publication recent, Publication newer){
        return List.of(
                outDTOFromPublication(older),
                outDTOFromPublication(recent),
                outDTOFromPublication(newer)
        );
    }

}