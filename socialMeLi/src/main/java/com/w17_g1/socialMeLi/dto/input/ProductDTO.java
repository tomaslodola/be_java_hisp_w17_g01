package com.w17_g1.socialMeLi.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductDTO {
    @NotNull(message = "La id no puede estar vacía.")
    @Min(message = "El id debe ser mayor a cero" , value = 1L)
    private Integer product_id;
    @NotBlank(message = "El campo no puede estar vacío.")
    @Size(max = 40, message = "La longitud no puede superar los 40 caracteres.")
    @Pattern(regexp = "^[^$%&|<>#@]*$", message = "El campo no puede poseer caracteres especiales.")
    private String product_name;
    @NotEmpty(message = "El campo no puede estar vacío.")
    @Size(min = 1, max = 15, message = "La longitud no puede superar los 15 caracteres.")
    @Pattern(regexp = "(^[^$%&|<>#@]*$)",message = "El campo no puede poseer caracteres especiales.")
    private String type;
    private String brand;
    @NotBlank(message = "El campo no puede estar vacío.")
    @Size(max = 15, message = "La longitud no puede superar los 15 caracteres.")
    @Pattern(regexp = "^[^$%&|<>#@]*$", message = "El campo no puede poseer caracteres especiales.")
    private String color;
    @NotBlank(message = "El campo no puede estar vacío.")
    @Size(max = 80, message = "La longitud no puede superar los 80 caracteres.")
    @Pattern(regexp="(^[^$%&|<>#@]*$)", message = "El campo no puede poseer caracteres especiales.")
    private String notes;
}
