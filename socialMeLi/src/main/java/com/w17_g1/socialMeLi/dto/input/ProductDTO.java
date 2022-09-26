package com.w17_g1.socialMeLi.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductDTO {
    private Integer product_id;
    private String product_name;
    private String type;
    private String brand;
    private String color;
    @NotBlank(message = "El campo no puede estar vacío.")
    @Size(max = 80, message = "La longitud no puede superar los 80 caracteres.")
    @Pattern(regexp="([a-zA-ZñÑáéíóúÁÉÍÓÚ]+\\s*\\.*\\s*)+", message = "El campo no puede poseer caracteres especiales.")
    private String notes;
}
