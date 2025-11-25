package com.lutton.lutton_api.controller;

import java.security.Principal;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lutton.lutton_api.dto.CategoriaCreateDTO;
import com.lutton.lutton_api.dto.CategoriaResponseDTO;
import com.lutton.lutton_api.service.CategoriaService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

  private final CategoriaService categoriaService;

  public CategoriaController(CategoriaService categoriaService) {
    this.categoriaService = categoriaService;
  }

  @PostMapping("/criar")
  public ResponseEntity<CategoriaResponseDTO> criar(
    @Valid @RequestBody CategoriaCreateDTO dto, 
    Principal principal
  ) throws ExecutionException, InterruptedException {
    String usuarioId = principal.getName();
    CategoriaResponseDTO novaCategoria = categoriaService.criar(dto, usuarioId);

    return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
  }

  @GetMapping()
  public ResponseEntity<List<CategoriaResponseDTO>> listar(Principal principal) 
    throws ExecutionException, InterruptedException {
    String usuarioId = principal.getName();
    List<CategoriaResponseDTO> categorias = categoriaService.listar(usuarioId);

    return ResponseEntity.ok(categorias);
  }
}
