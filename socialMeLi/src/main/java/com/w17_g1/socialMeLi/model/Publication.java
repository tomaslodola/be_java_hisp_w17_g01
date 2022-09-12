package com.w17_g1.socialMeLi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Publication {
    private Integer id;
    private Integer userId;
    private LocalDate publishDate;
    private Double price;
    private Product product;
    private Integer category;
}
