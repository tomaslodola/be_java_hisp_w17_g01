package com.w17_g1.socialMeLi.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationDTO {
    private Integer id;
    private Integer userId;
    private LocalDate publishDate;
    private Double price;
    private ProductDto product;
    private Integer category;
}
