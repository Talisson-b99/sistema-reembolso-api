package com.barbosa.sistema_reembolso.dto;

import com.barbosa.sistema_reembolso.domain.enums.StatusReembolso;
import com.barbosa.sistema_reembolso.domain.enums.TipoDespesa;
import com.barbosa.sistema_reembolso.domain.model.Reembolso;
import com.barbosa.sistema_reembolso.domain.model.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReembolsoRequestDTO(TipoDespesa tipoDespesa,
                                  BigDecimal valor,
                                  LocalDate dataDespesa,
                                  String descricao,
                                  String comprovanteUrl,
                                  String justificativa,
                                  UUID usuarioId){

    public Reembolso toEntity(Usuario usuario) {
        Reembolso reembolso = new Reembolso();
        reembolso.setTipoDespesa(tipoDespesa);
        reembolso.setValor(valor);
        reembolso.setDataDespesa(dataDespesa);
        reembolso.setDescricao(descricao);
        reembolso.setComprovanteUrl(comprovanteUrl);
        reembolso.setStatus(StatusReembolso.PENDENTE);
        reembolso.setJustificativaRecusa(justificativa);
        reembolso.setDataSolicitacao(LocalDate.now());
        reembolso.setUsuario(usuario);

        return reembolso;
    }
}
