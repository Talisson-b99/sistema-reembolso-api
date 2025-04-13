package com.barbosa.sistema_reembolso.dto;

import com.barbosa.sistema_reembolso.domain.enums.TipoDespesa;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateReembolsoDTO(TipoDespesa tipoDespesa,
                                 BigDecimal valor,
                                 LocalDate dataDespesa,
                                 String descricao,
                                 String comprovanteUrl) {
}
