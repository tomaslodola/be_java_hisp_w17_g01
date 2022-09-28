package com.w17_g1.socialMeLi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @JsonProperty("id")
    private Integer product_id;
    @JsonProperty("name")
    private String product_name;
    private String type;
    private String brand;
    private String color;
    private String notes;
}
