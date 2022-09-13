package com.w17_g1.socialMeLi.dto.input;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromoPublicationDTO extends PublicationDTO{
    private Boolean has_promo;
    private Double discount;
}
