package com.lutton.lutton_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriaCreateDTO {
  
  @NotBlank(message = "O nome da categoria é obrigatório")
  private String nome;

}
