package com.barbosa.sistema_reembolso.Exception.business;

public class DataDespesaInvalidaException extends RuntimeException {
    public DataDespesaInvalidaException() {
        super(
                "A data da despesa deve estar entre 30 dias atrás e a data atual."
        );
    }
}
