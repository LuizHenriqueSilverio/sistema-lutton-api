package com.lutton.lutton_api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Card {
  @NotBlank
  private String nome;

  private String descricao;

  private String categoriaId; 

  private String usuarioId;
}