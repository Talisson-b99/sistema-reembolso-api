package com.barbosa.sistema_reembolso.Exception.business;

import java.math.BigDecimal;

public class ValorReembolsoInvalidoException extends RuntimeException {
    public ValorReembolsoInvalidoException(String tipoDespesa, Double valor) {
        super(
                tipoDespesa + " não pode ultrapassar o valor R$: " + valor
        );
    }
}
