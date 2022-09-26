package com.w17_g1.socialMeLi.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductDTO {
    private Integer product_id;
    private String product_name;
    private String type;
    private String brand;
    private String color;
    private String notes;
}
