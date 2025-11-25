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

  @PostMapping()
  public ResponseEntity<CardResponseDTO> criar(
    @Valid @RequestBody CardCreateDTO dto, 
    Principal principal
  ) throws ExecutionException, InterruptedException {      
    String usuarioId = principal.getName();

    CardResponseDTO novoCard = cardService.criar(dto, usuarioId);

    return ResponseEntity.status(HttpStatus.CREATED).body(novoCard);
  }

  @GetMapping("/listar")
  public ResponseEntity<List<CardResponseDTO>> listarCards(
    @RequestParam(required = false) String categoriaId,
    Principal principal
  ) throws ExecutionException, InterruptedException {
    String usuarioId = principal.getName();

    List<CardResponseDTO> cards = cardService.listar(usuarioId, categoriaId);

    return ResponseEntity.ok(cards);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CardResponseDTO> buscarPorId(@PathVariable String id) 
          throws ExecutionException, InterruptedException {
      CardResponseDTO card = cardService.buscarPorId(id);
      if (card == null) return ResponseEntity.notFound().build();
      return ResponseEntity.ok(card);
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> atualizar(
          @PathVariable String id, 
          @RequestBody CardCreateDTO dto) throws ExecutionException, InterruptedException {
      
      cardService.atualizar(id, dto);
      return ResponseEntity.ok("Card atualizado!");
  }

  @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) 
      throws ExecutionException, InterruptedException {
        cardService.deletar(id);
        return ResponseEntity.noContent().build();
  }
}