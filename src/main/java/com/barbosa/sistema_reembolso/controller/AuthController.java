package com.barbosa.sistema_reembolso.controller;

import com.barbosa.sistema_reembolso.dto.LoginRequestDTO;
import com.barbosa.sistema_reembolso.security.Token;
import com.barbosa.sistema_reembolso.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@Valid @RequestBody LoginRequestDTO login) {
        return ResponseEntity.ok(authService.autenticar(login));
    }
}
