package com.barbosa.sistema_reembolso.Exception.business;

import java.util.UUID;

public class StatusReembolsoNaoAtualizavelException extends RuntimeException {
    public StatusReembolsoNaoAtualizavelException(UUID id) {
        super(
                "O status do reembolso com ID " + id + " não pode ser alterada. Apenas reembolsos com status PENDENTE podem ser modificados"
        );
    }
}
