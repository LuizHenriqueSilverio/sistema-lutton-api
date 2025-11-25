package com.lutton.lutton_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CardCreateDTO {

  @NotBlank(message = "O nome é obrigatório")
  private String nome;

  private String descricao;

  @NotBlank(message = "A categoria é obrigatória")
  private String categoriaId;
}