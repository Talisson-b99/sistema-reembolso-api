package com.barbosa.sistema_reembolso.Exception.business;

public class EmailOuSenhaInvalidosException extends RuntimeException {
    public EmailOuSenhaInvalidosException() {
        super(
                "E-mail ou senha inválidos"
        );
    }
}
