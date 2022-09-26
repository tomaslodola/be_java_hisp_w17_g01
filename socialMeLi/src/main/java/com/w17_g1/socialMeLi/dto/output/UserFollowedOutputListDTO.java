package com.w17_g1.socialMeLi.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFollowedOutputListDTO  {
    private Integer id;
    private String name;
    private List<UserOutputDTO> followed;

}