package com.barbosa.sistema_reembolso.Exception.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ApiError {
    private int status;
    private String mensagem;
    private LocalDateTime timestamp;
    private List<CampoErroDTO> erros;

}
