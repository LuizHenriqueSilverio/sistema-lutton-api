package com.lutton.lutton_api.controller;

import com.lutton.lutton_api.dto.CardCreateDTO;
import com.lutton.lutton_api.dto.CardResponseDTO;
import com.lutton.lutton_api.service.CardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/cards")
public class CardController {

  private final CardService cardService;

  public CardController(CardService cardService) {
      this.cardService = cardService;
  }

  @PostMapping("/criar")
  public ResponseEntity<CardResponseDTO> criar(
    @Valid @RequestBody CardCreateDTO dto, 
    Principal principal
  ) throws ExecutionException, InterruptedException {      
    String usuarioId = principal.getName();

    CardResponseDTO novoCard = cardService.criar(dto, usuarioId);

    return ResponseEntity.status(HttpStatus.CREATED).body(novoCard);
  }

  @GetMapping()
  public ResponseEntity<List<CardResponseDTO>> listarCards(
    @RequestParam(required = false) String categoriaId,
    Principal principal
  ) throws ExecutionException, InterruptedException {
    String usuarioId = principal.getName();

    List<CardResponseDTO> cards = cardService.listar(usuarioId, categoriaId);

    return ResponseEntity.ok(cards);
  }
}