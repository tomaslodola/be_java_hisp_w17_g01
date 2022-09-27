package com.w17_g1.socialMeLi.dto.output.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOutputDTO {
    private Integer id;
    private String name;
}
