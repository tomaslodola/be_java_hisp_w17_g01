package com.w17_g1.socialMeLi.dto.output;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.w17_g1.socialMeLi.dto.input.PublicationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsInPromotionDTO {
    Integer user_id;
    String user_name;
    List<PublicationDTO> posts;
}
