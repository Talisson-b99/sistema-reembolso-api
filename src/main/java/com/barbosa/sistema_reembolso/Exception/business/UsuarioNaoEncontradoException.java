package com.barbosa.sistema_reembolso.Exception.business;

import java.util.UUID;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(UUID id) {
        super("Usuário com ID " + id + " não foi encontrado.");
    }
}
