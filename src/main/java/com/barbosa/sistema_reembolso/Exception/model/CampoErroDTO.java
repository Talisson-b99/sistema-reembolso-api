package com.barbosa.sistema_reembolso.Exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CampoErroDTO {
    private String campo;
    private String mensagem;
}
