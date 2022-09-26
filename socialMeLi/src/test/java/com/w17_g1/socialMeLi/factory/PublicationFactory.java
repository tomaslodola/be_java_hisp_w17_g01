package com.w17_g1.socialMeLi.factory;

import com.w17_g1.socialMeLi.model.Product;
import com.w17_g1.socialMeLi.model.Publication;

import java.time.LocalDate;

public class PublicationFactory {
    public  static Publication createPublication(){

        return Publication.builder()
                .id(99)
                .userId(3)
                .publishDate(LocalDate.parse("12-10-2022"))
                .product(Product.builder().id(47).name("MaxSteel").type("Music").brand("MechManiacos").color("Yellow").notes("Test").build())
                .category(11)
                .price(90000D)
                .build();
    }

    public static Publication createJsonPublication(){
        return Publication.builder()
                .id(8)
                .userId(3)
                .publishDate(LocalDate.parse("09-10-2022"))
                .product(Product.builder().id(0).name("DoraTheVideogame").type("Electronic").brand("MechManiacos").color("Reg").notes("Test").build())
                .category(17)
                .price(3305.77)
                .build();
    }
}