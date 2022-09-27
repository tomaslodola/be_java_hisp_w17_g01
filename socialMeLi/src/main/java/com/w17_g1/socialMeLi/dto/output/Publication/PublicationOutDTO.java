package com.w17_g1.socialMeLi.dto.output.Publication;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
  private Integer id;
  private Integer user_id;
  private LocalDate date;
  private ProductDTO product;
  private Integer category;
  private Double price;

}
