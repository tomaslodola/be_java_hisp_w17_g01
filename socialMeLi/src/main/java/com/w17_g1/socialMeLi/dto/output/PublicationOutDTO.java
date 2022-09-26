package com.w17_g1.socialMeLi.dto.output;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.w17_g1.socialMeLi.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PublicationOutDTO {
  private Integer userId;
  private Integer postId;
  private LocalDate date;
  private Product product;
  private Integer category;
  private Double price;

}
