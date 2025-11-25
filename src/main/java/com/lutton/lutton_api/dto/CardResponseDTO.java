package com.lutton.lutton_api.dto;

import lombok.Data;

@Data
public class CardResponseDTO {
  private String id;
  private String nome;
  private String descricao;
  private String categoriaId;
}
