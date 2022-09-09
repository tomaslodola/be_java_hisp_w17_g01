package com.w17_g1.socialMeLi.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Integer id;
    private String name;
    private List<Integer> followersId;
    private List<Integer> followedId;
}
