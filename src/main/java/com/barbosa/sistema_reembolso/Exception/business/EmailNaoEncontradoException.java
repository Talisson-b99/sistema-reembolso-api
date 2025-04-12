package com.barbosa.sistema_reembolso.Exception.business;

public class EmailNaoEncontradoException extends RuntimeException {
    public EmailNaoEncontradoException(String email) {
        super("E-mail não encontrado: " + email);
    }
}
