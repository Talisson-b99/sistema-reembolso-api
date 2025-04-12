package com.barbosa.sistema_reembolso.domain.model;

import com.barbosa.sistema_reembolso.domain.enums.StatusReembolso;
import com.barbosa.sistema_reembolso.domain.enums.TipoDespesa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tbl_reembolso")
@Getter
@Setter
@NoArgsConstructor
public class Reembolso {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "tipo_despesa", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDespesa tipoDespesa;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "data_despesa", nullable = false)
    private LocalDate dataDespesa;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "comprovante_url")
    private String comprovanteUrl;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusReembolso status;

    @Column(name = "justificativa_recusa")
    private String justificativaRecusa;

    @Column(name = "data_solicitacao", nullable = false)
    private LocalDate dataSolicitacao;

    // relacionamento com usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
