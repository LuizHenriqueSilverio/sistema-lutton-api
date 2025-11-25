package com.lutton.lutton_api.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.lutton.lutton_api.dto.CategoriaCreateDTO;
import com.lutton.lutton_api.dto.CategoriaResponseDTO;
import com.lutton.lutton_api.model.Categoria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ExecutionException;

@Service
public class CategoriaService {
  private final Firestore firestore;

  public CategoriaService(Firestore firestore) {
    this.firestore = firestore;
  }

  public CategoriaResponseDTO criar(CategoriaCreateDTO dto, String usuarioId) 
    throws InterruptedException, ExecutionException {

    Categoria novaCategoria = new Categoria();
    novaCategoria.setNome(dto.getNome());
    novaCategoria.setUsuarioId(usuarioId);

    ApiFuture<DocumentReference> future = firestore.collection("categorias").add(novaCategoria);

    DocumentReference docRef = future.get();

    CategoriaResponseDTO response = new CategoriaResponseDTO();
    response.setId(docRef.getId());
    response.setNome(novaCategoria.getNome());

    return response;
  }

  public List<CategoriaResponseDTO> listar(String usuarioId) 
    throws ExecutionException, InterruptedException {

    ApiFuture<QuerySnapshot> future = firestore.collection("categorias")
      .whereEqualTo("usuarioId", usuarioId)
      .get();

    List<QueryDocumentSnapshot> documents = future.get().getDocuments();
    List<CategoriaResponseDTO> listaResposta = new ArrayList<>();

    for (QueryDocumentSnapshot document : documents) {
        Categoria categoria = document.toObject(Categoria.class);
        
        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setId(document.getId());
        dto.setNome(categoria.getNome());
        
        listaResposta.add(dto);
    }
    return listaResposta;
  }
}