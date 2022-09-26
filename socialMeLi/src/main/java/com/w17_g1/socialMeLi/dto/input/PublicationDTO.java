package com.w17_g1.socialMeLi.dto.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.w17_g1.socialMeLi.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicationDTO {
    @Min(message = "El id debe ser mayor a cero",value = 1L)
    @NotNull(message = "El  id no puede estar vacío.")
    private Integer user_id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotNull(message = "La fecha no puede estar vacía.")
    private LocalDate date;

    @Valid
    private ProductDTO product;
    private Integer category;
    private Double price;
}
