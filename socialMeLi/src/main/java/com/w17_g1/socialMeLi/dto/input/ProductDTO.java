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
  @NotBlank(message = "El campo no puede estar vacío.")
  @Size(max = 40, message = "La longitud no puede superar los 40 caracteres.")
  @Pattern(regexp = "^[^$%&|<>#@]*$", message = "El campo no puede poseer caracteres especiales.")
  private String product_name;
  private String type;
  private String brand;
  @NotBlank(message = "El campo no puede estar vacío.")
  @Size(max = 15, message = "La longitud no puede superar los 15 caracteres.")
  @Pattern(regexp = "^[^$%&|<>#@]*$", message = "El campo no puede poseer caracteres especiales.")
  private String color;
  private String notes;
}
