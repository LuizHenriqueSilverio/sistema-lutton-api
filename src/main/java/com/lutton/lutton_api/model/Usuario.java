package com.lutton.lutton_api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Usuario {
  @NotBlank(message = "O campo 'nome' é obrigatório.")
  private String nome;

  @Email(message = "O campo 'email' deve ser um endereço de email válido.")
  @NotBlank(message = "O campo 'email' é obrigatório.")
  private String email;
}