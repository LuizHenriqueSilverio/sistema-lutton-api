package com.lutton.lutton_api.service;

import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.lutton.lutton_api.model.Categoria;

public class CategoriaService {

  private final Firestore firestore;

  public CategoriaService(Firestore firestore) {
    this.firestore = firestore;
  }

  public void criar(String nomeCategoria, String usuarioId) throws InterruptedException, ExecutionException {
    Categoria novaCategoria = new Categoria();
    novaCategoria.setNome(nomeCategoria);
    novaCategoria.setUsuarioId(usuarioId); 

    ApiFuture<DocumentReference> future = firestore.collection("categorias").add(novaCategoria);
    
    future.get();
}
  
}
