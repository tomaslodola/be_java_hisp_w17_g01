package com.w17_g1.socialMeLi.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuantityProductInPromotionDTO {
    Integer user_id;
    String user_name;
    Integer promo_products_count;
}
