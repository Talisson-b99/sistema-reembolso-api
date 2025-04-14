package com.barbosa.sistema_reembolso.service;

import com.barbosa.sistema_reembolso.dto.LoginRequestDTO;

import com.barbosa.sistema_reembolso.security.Token;

public interface AuthService {
    Token autenticar(LoginRequestDTO dto);
}
