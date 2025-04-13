package com.barbosa.sistema_reembolso.service.impl;

import com.barbosa.sistema_reembolso.Exception.business.*;
import com.barbosa.sistema_reembolso.domain.enums.Role;
import com.barbosa.sistema_reembolso.domain.enums.StatusReembolso;
import com.barbosa.sistema_reembolso.domain.enums.TipoDespesa;
import com.barbosa.sistema_reembolso.domain.model.Reembolso;
import com.barbosa.sistema_reembolso.domain.model.Usuario;
import com.barbosa.sistema_reembolso.dto.ReembolsoRequestDTO;
import com.barbosa.sistema_reembolso.dto.ReembolsoResponseDTO;
import com.barbosa.sistema_reembolso.repository.ReembolsoRepository;
import com.barbosa.sistema_reembolso.repository.UsuarioRepository;
import com.barbosa.sistema_reembolso.service.ReembolsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ReembolsoServiceImpl implements ReembolsoService {

    @Autowired
    private ReembolsoRepository reembolsoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public ReembolsoResponseDTO cadastrarReembolso(ReembolsoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(dto.usuarioId()));

        if (dto.dataDespesa().isBefore(LocalDate.now().minusMonths(1)) || dto.dataDespesa().isAfter(LocalDate.now())) {
            throw new DataDespesaInvalidaException();
        }


        if(dto.valor().signum() <= BigDecimal.ZERO.signum()) {
            throw new ValorDespesaInvalidoException();
        }

        validarLimiteDespesa(dto);

        Reembolso reembolso = dto.toEntity(usuario);

        Reembolso reembolsoSalvo = reembolsoRepository.save(reembolso);

        return ReembolsoResponseDTO.fromEntity(reembolso, usuario.getNome());
    }

    @Override
    public List<ReembolsoResponseDTO> buscarTodosReembolsos() {
        var reembolsos = reembolsoRepository.findAll();
        return reembolsos.stream()
                .map(reembolso -> ReembolsoResponseDTO.fromEntity(reembolso, reembolso.getUsuario().getNome())).toList();
    }

    @Override
    public ReembolsoResponseDTO buscarReembolsoPorId(UUID reembolsoId) {
        Reembolso reembolso = reembolsoRepository.findById(reembolsoId).orElseThrow(() -> new ReembolsoNaoEncontradoException(reembolsoId));

        return ReembolsoResponseDTO.fromEntity(reembolso, reembolso.getUsuario().getNome());
    }

    @Override
    public List<ReembolsoResponseDTO> buscarReembolsoPorStatus(StatusReembolso status) {
        var reembolsos = reembolsoRepository.findAllByStatus(status);

        return reembolsos.stream()
                .map(reemboso -> ReembolsoResponseDTO.fromEntity(reemboso, reemboso.getUsuario().getNome())).toList();
    }

    private void validarLimiteDespesa(ReembolsoRequestDTO dto) {
        Map<TipoDespesa, Double> limites = Map.of(
                TipoDespesa.REFEICAO, 100.00,
                TipoDespesa.TRANSPORTE, 150.00,
                TipoDespesa.MATERIAL, 300.00,
                TipoDespesa.OUTROS, 200.00
        );

        double limite = limites.getOrDefault(dto.tipoDespesa(), 0.0);

        if (dto.valor().compareTo(BigDecimal.valueOf(limite)) > 0) {
            throw new ValorReembolsoInvalidoException(dto.tipoDespesa().name(), limite);
        }

    }
}
