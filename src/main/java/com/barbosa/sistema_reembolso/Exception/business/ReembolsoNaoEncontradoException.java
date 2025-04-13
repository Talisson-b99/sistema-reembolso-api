package com.barbosa.sistema_reembolso.Exception.business;

import java.util.UUID;

public class ReembolsoNaoEncontradoException extends RuntimeException {
    public ReembolsoNaoEncontradoException(UUID reembolsoId) {
        super(
                "Reembolso não encontrado " + reembolsoId
        );
    }
}
