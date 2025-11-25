package com.lutton.lutton_api.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.lutton.lutton_api.dto.CardCreateDTO;
import com.lutton.lutton_api.dto.CardResponseDTO; // (Crie este DTO simples com id, nome, etc)
import com.lutton.lutton_api.model.Card;
import org.springframework.stereotype.Service;
import com.google.cloud.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CardService {

  private final Firestore firestore;

  public CardService(Firestore firestore) {
      this.firestore = firestore;
  }

  public CardResponseDTO criar(CardCreateDTO dto, String usuarioId) 
    throws InterruptedException, ExecutionException 
  {  
    Card card = new Card();
    card.setNome(dto.getNome());
    card.setDescricao(dto.getDescricao());
    card.setCategoriaId(dto.getCategoriaId());
    card.setUsuarioId(usuarioId);

    ApiFuture<DocumentReference> future = firestore.collection("cards").add(card);
    DocumentReference docRef = future.get();

    CardResponseDTO response = new CardResponseDTO();
    response.setId(docRef.getId());
    response.setNome(card.getNome());
    response.setDescricao(card.getDescricao());
    response.setCategoriaId(card.getCategoriaId());
    
    return response;
  }

  public List<CardResponseDTO> listar(String usuarioId, String categoriaId) 
    throws ExecutionException, InterruptedException {    
    
      Query query = firestore.collection("cards");

      query = query.whereEqualTo("usuarioId", usuarioId);

      if (categoriaId != null && !categoriaId.isEmpty()) {
          query = query.whereEqualTo("categoriaId", categoriaId);
      }

      ApiFuture<QuerySnapshot> future = query.get();
      List<QueryDocumentSnapshot> documents = future.get().getDocuments();

      List<CardResponseDTO> listaResposta = new ArrayList<>();

      for (QueryDocumentSnapshot document : documents) {
          Card card = document.toObject(Card.class);
          
          CardResponseDTO dto = new CardResponseDTO();
          dto.setId(document.getId());
          dto.setNome(card.getNome());
          dto.setDescricao(card.getDescricao());
          dto.setCategoriaId(card.getCategoriaId());
          
          listaResposta.add(dto);
      }

      return listaResposta;
  }
}