package com.barbosa.sistema_reembolso.service;

import com.barbosa.sistema_reembolso.domain.model.Reembolso;
import com.barbosa.sistema_reembolso.dto.ReembolsoRequestDTO;
import com.barbosa.sistema_reembolso.dto.ReembolsoResponseDTO;

public interface ReembolsoService {
    ReembolsoResponseDTO cadastrarReembolso(ReembolsoRequestDTO dto);
}
