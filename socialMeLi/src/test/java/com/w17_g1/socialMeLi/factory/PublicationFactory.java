package com.w17_g1.socialMeLi.factory;

import com.w17_g1.socialMeLi.dto.input.PublicationDTO;
import com.w17_g1.socialMeLi.dto.output.Publication.ProductDTO;
import com.w17_g1.socialMeLi.dto.output.Publication.PublicationOutDTO;
import com.w17_g1.socialMeLi.model.Product;
import com.w17_g1.socialMeLi.model.Publication;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PublicationFactory {

    // Datos genericos que no son relevantes para la ejecución de los Test
    private static final Integer genereicPostID = 9999;
    private static final Integer genereicProductID = 99;
    private static final Integer genereicCategory = 0;
    private static final Double genereicPrice = 100D;

    public static Publication createJsonPublication(){
        Product product = Product.builder()
                .product_id(34)
                .product_name("DoraTheVideogame")
                .type("Gamer")
                .brand("MechManiacos")
                .color("Yellow")
                .build();

        return Publication.builder()
                .id(9)
                .user_id(2)
                .date(LocalDate.of(2022,10,10))
                .price(8395.75)
                .category(11)
                .product(product)
                .build();
    }

    // Metodo que crea publicaciones con el ID de usuario y la fecha de publicacion que recibe como parametros
    public static Publication createPublicationForUser(Integer userID, LocalDate publishDate){
        return Publication.builder()
                .user_id(userID)
                .id(genereicPostID)
                .date(publishDate)
                .product(ProductFactory.getProduct(genereicProductID))
                .category(genereicCategory)
                .price(genereicPrice)
                .build();
    }

    public static PublicationDTO createPublicationDto(){
       return PublicationDTO.builder()
                .user_id(2)
                .date(LocalDate.parse("29-04-2021", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .product(com.w17_g1.socialMeLi.dto.input.ProductDTO.builder().product_id(8)
                        .product_name("Silla Gamer")
                        .type("Gamer")
                        .brand("Racer")
                        .color("Red Black")
                        .notes("Special Edition").build())
                .category(100)
                .price(1500.50).build();
    }

    // Metodo que toma una publicacion y devuelve un DTO de salida con los mismos datos
    public static PublicationOutDTO outDTOFromPublication(Publication publication) {
        return PublicationOutDTO.builder()
                .user_id(publication.getUser_id())
                .id(publication.getId())
                .date(publication.getDate())
                .product(toProductDTO(publication.getProduct()))
                .category(publication.getCategory())
                .price(publication.getPrice())
                .build();
    }

    private static ProductDTO toProductDTO(Product product){
        return ProductDTO.builder()
                .product_id(product.getProduct_id())
                .product_name(product.getProduct_name())
                .type(product.getType())
                .brand(product.getBrand())
                .color(product.getColor())
                .notes(product.getNotes())
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