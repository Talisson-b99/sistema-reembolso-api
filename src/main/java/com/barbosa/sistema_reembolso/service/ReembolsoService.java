package com.barbosa.sistema_reembolso.service;

import com.barbosa.sistema_reembolso.domain.enums.StatusReembolso;
import com.barbosa.sistema_reembolso.dto.ReembolsoRequestDTO;
import com.barbosa.sistema_reembolso.dto.ReembolsoResponseDTO;
import com.barbosa.sistema_reembolso.dto.UpdateReembolsoDTO;

import java.util.List;
import java.util.UUID;

public interface ReembolsoService {
    ReembolsoResponseDTO cadastrarReembolso(ReembolsoRequestDTO dto);

    List<ReembolsoResponseDTO> buscarTodosReembolsos();
    ReembolsoResponseDTO buscarReembolsoPorId(UUID reembolsoId);
    List<ReembolsoResponseDTO> buscarReembolsoPorStatus(StatusReembolso role);
    void atualizarReembolso(UUID reembolsoId, UpdateReembolsoDTO dto);
    void deletarReembolso(UUID reembolsoId);
}
