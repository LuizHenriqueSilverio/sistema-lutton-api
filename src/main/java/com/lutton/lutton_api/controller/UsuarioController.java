package com.lutton.lutton_api.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.lutton.lutton_api.dto.UsuarioCreateDTO;
import com.lutton.lutton_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping()
    public ResponseEntity<String> criarUsuario(@Valid @RequestBody UsuarioCreateDTO dto) {
        try {
            usuarioService.criarNovoUsuario(dto.getNome(), dto.getEmail(), dto.getSenha());
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso!");
        } catch (FirebaseAuthException e) {
            String mensagemErro = "Erro ao criar usuário no Firebase: " + e.getMessage();    
            if ("EMAIL_ALREADY_EXISTS".equals(e.getErrorCode())) {
                mensagemErro = "O email fornecido já está em uso.";
                return ResponseEntity.status(HttpStatus.CONFLICT).body(mensagemErro); // 409 Conflict
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemErro); // 400 Bad Request
        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar perfil do usuário no Firestore: " + e.getMessage()); // 500 Error
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado: " + e.getMessage()); // 500 Error
        }
    }
}