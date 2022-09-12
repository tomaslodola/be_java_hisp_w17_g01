package com.w17_g1.socialMeLi.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Integer id;
    private String name;
    private String type;
    private String brand;
    private String color;
    private String notes;
}
