package com.w17_g1.socialMeLi.dto.output;

import com.w17_g1.socialMeLi.dto.input.PublicationDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromoPublicationOutDTO {
    private Integer user_id;
    private String user_name;
    List<PublicationDTO> posts;
}
