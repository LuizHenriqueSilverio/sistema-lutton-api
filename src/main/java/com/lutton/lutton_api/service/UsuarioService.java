package com.lutton.lutton_api.service;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.lutton.lutton_api.model.Usuario;

@Service
@Validated
public class UsuarioService {

  private final Firestore firestore;
  private final FirebaseAuth firebaseAuth;

  public UsuarioService(Firestore firestore, FirebaseAuth firebaseAuth) {
    this.firestore = firestore;
    this.firebaseAuth = firebaseAuth;
  }
  
  public void criarNovoUsuario(String nome, String email, String senha) throws FirebaseAuthException, InterruptedException, ExecutionException {
    UserRecord.CreateRequest request = new UserRecord.CreateRequest()
        .setEmail(email)
        .setPassword(senha)
        .setDisplayName(nome);

    UserRecord userRecord = firebaseAuth.createUser(request);
    String uid = userRecord.getUid();

    Usuario perfilUsuario = new Usuario();
    perfilUsuario.setNome(nome);
    perfilUsuario.setEmail(email);

    ApiFuture<WriteResult> future = firestore.collection("usuarios")
      .document(uid)
      .set(perfilUsuario);

    future.get();
  }
}
