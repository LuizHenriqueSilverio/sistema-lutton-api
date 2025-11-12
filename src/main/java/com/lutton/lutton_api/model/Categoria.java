package com.lutton.lutton_api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Categoria {

  @NotBlank
  private String nome;

  private String usuarioId;

}