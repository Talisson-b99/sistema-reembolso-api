package com.barbosa.sistema_reembolso.Exception.business;

public class ValorDespesaInvalidoException extends RuntimeException {
    public ValorDespesaInvalidoException() {
        super("O valor da despesa deve ser maior que zero.");
    }
}
