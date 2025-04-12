package com.barbosa.sistema_reembolso.dto;

import com.barbosa.sistema_reembolso.domain.enums.StatusReembolso;
import com.barbosa.sistema_reembolso.domain.enums.TipoDespesa;
import com.barbosa.sistema_reembolso.domain.model.Reembolso;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReembolsoResponseDTO(UUID id,
                                   BigDecimal valor,
                                   String descricao,
                                   LocalDate dataDespesa,
                                   LocalDate dataSolicitacao,
                                   String comprovanteUrl,
                                   StatusReembolso status,
                                   TipoDespesa tipoDespesa,
                                   String nome,
                                   UUID usuarioId){

    public static ReembolsoResponseDTO fromEntity(Reembolso reembolso, String nome) {
        return new ReembolsoResponseDTO(
                reembolso.getId(),
                reembolso.getValor(),
                reembolso.getDescricao(),
                reembolso.getDataDespesa(),
                reembolso.getDataSolicitacao(),
                reembolso.getComprovanteUrl(),
                reembolso.getStatus(),
                reembolso.getTipoDespesa(),
                nome,
                reembolso.getUsuario().getId()
        );
    }
}
