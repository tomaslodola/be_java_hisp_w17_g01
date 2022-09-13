package com.w17_g1.socialMeLi.dto.output;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPromoPublicationCountDTO {
    private Integer user_id;
    private String user_name;
    private Integer promo_products_count;
}
