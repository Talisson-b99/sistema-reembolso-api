package com.barbosa.sistema_reembolso.service.impl;

import com.barbosa.sistema_reembolso.Exception.business.*;
import com.barbosa.sistema_reembolso.domain.enums.StatusReembolso;
import com.barbosa.sistema_reembolso.domain.enums.TipoDespesa;
import com.barbosa.sistema_reembolso.domain.model.Reembolso;
import com.barbosa.sistema_reembolso.domain.model.Usuario;
import com.barbosa.sistema_reembolso.dto.JustificativaRecusaDTO;
import com.barbosa.sistema_reembolso.dto.ReembolsoRequestDTO;
import com.barbosa.sistema_reembolso.dto.ReembolsoResponseDTO;
import com.barbosa.sistema_reembolso.dto.UpdateReembolsoDTO;
import com.barbosa.sistema_reembolso.repository.ReembolsoRepository;
import com.barbosa.sistema_reembolso.repository.UsuarioRepository;
import com.barbosa.sistema_reembolso.security.UsuarioAutenticado;
import com.barbosa.sistema_reembolso.service.ReembolsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UsuarioAutenticado usuarioAutenticado = (UsuarioAutenticado) auth.getPrincipal();

        Usuario usuario = usuarioRepository.findById(usuarioAutenticado.getId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioAutenticado.getId()));

        usuario.setId(usuarioAutenticado.getId());
        Reembolso reembolso = dto.toEntity(usuario);

        validarData(dto.dataDespesa());
        validarValorMaiorZero(dto.valor());
        validarLimiteDespesa(reembolso.getTipoDespesa(), reembolso.getValor());

        Reembolso reembolsoSalvo = reembolsoRepository.save(reembolso);

        return ReembolsoResponseDTO.fromEntity(reembolso, usuario.getNome());
    }


    @Override
    @PreAuthorize("hasRole('GESTOR')")
    public List<ReembolsoResponseDTO> buscarTodosReembolsos() {
        var reembolsos = reembolsoRepository.findAll();
        return reembolsos.stream()
                .map(reembolso -> ReembolsoResponseDTO.fromEntity(reembolso, reembolso.getUsuario().getNome())).toList();
    }

    @Override
    @PreAuthorize("@gerenciamenteAcesso.podeVerReembolso(#reembolsoId)")
    public ReembolsoResponseDTO buscarReembolsoPorId(UUID reembolsoId) {
        Reembolso reembolso = reembolsoRepository.findById(reembolsoId).orElseThrow(() -> new ReembolsoNaoEncontradoException(reembolsoId));

        return ReembolsoResponseDTO.fromEntity(reembolso, reembolso.getUsuario().getNome());
    }

    @Override
    @PreAuthorize("hasRole('GESTOR')")
    public List<ReembolsoResponseDTO> buscarReembolsoPorStatus(StatusReembolso status) {
        var reembolsos = reembolsoRepository.findAllByStatus(status);

        return reembolsos.stream()
                .map(reemboso -> ReembolsoResponseDTO.fromEntity(reemboso, reemboso.getUsuario().getNome())).toList();
    }

    @Override
    @PreAuthorize("@gerenciamenteAcesso.podeVerReembolso(#reembolsoId)")
    public void atualizarReembolso(UUID reembolsoId , UpdateReembolsoDTO dto) {
        Reembolso reembolso = reembolsoRepository.findById(reembolsoId).orElseThrow(() -> new ReembolsoNaoEncontradoException(reembolsoId));

        verificarStatusPendente(reembolsoId, reembolso);

        if(dto.valor() != null) {
            validarValorMaiorZero(dto.valor());
            validarLimiteDespesa(reembolso.getTipoDespesa(), dto.valor());
            reembolso.setValor(dto.valor());
        }

        if (dto.tipoDespesa() != null && dto.valor() != null) {
            validarLimiteDespesa(dto.tipoDespesa(), dto.valor());
            reembolso.setTipoDespesa(dto.tipoDespesa());
        }

        if(dto.tipoDespesa() != null) {
            validarLimiteDespesa(dto.tipoDespesa(), reembolso.getValor());
            reembolso.setTipoDespesa(dto.tipoDespesa());
        }

        if (dto.dataDespesa() != null) {
            validarData(dto.dataDespesa());
            reembolso.setDataDespesa(dto.dataDespesa());
        }

        if(dto.descricao() != null){
            reembolso.setDescricao(dto.descricao());
        }

        if (dto.comprovanteUrl() != null) {
            reembolso.setComprovanteUrl(dto.comprovanteUrl());
        }

        reembolsoRepository.save(reembolso);
    }

    @Override
    public void deletarReembolso(UUID reembolsoId) {
        var reembolso = reembolsoRepository.findById(reembolsoId).orElseThrow(() -> new ReembolsoNaoEncontradoException(reembolsoId));

        verificarStatusPendente(reembolsoId, reembolso);
        reembolsoRepository.deleteById(reembolsoId);
    }


    @Override
    @PreAuthorize("hasRole('GESTOR')")
    public ReembolsoResponseDTO aprovarReembolso(UUID reembolsoId) {
        //TODO VERIFICAR SE É UM GESTOR
        var reembolso = reembolsoRepository.findById(reembolsoId).orElseThrow(() -> new ReembolsoNaoEncontradoException(reembolsoId));

        verificarStatusPendente(reembolsoId, reembolso);

        reembolso.setStatus(StatusReembolso.APROVADO);
        var reembolsoSalvo = reembolsoRepository.save(reembolso);

        return  ReembolsoResponseDTO.fromEntity(reembolsoSalvo, reembolso.getUsuario().getNome());
    }

    @Override
    @PreAuthorize("hasRole('GESTOR')")
    public ReembolsoResponseDTO recusarReembolso(UUID reembolsoId, JustificativaRecusaDTO justificativaRecusaDTO) {
        var reembolso = reembolsoRepository.findById(reembolsoId).orElseThrow(() ->  new ReembolsoNaoEncontradoException(reembolsoId));

        verificarStatusPendente(reembolsoId, reembolso);

        reembolso.setJustificativaRecusa(justificativaRecusaDTO.justificativaDespesa());
        reembolso.setStatus(StatusReembolso.RECUSADO);

        var reembolsoSalvo = reembolsoRepository.save(reembolso);

        return  ReembolsoResponseDTO.fromEntity(reembolsoSalvo, reembolso.getUsuario().getNome());
    }

    private void validarLimiteDespesa(TipoDespesa tipoDespesa, BigDecimal valor) {
        Map<TipoDespesa, Double> limites = Map.of(
                TipoDespesa.REFEICAO, 100.00,
                TipoDespesa.TRANSPORTE, 150.00,
                TipoDespesa.MATERIAL, 300.00,
                TipoDespesa.OUTROS, 200.00
        );

        double limite = limites.getOrDefault(tipoDespesa, 0.0);

        if (valor.compareTo(BigDecimal.valueOf(limite)) > 0) {
            throw new ValorReembolsoInvalidoException(tipoDespesa.name(), limite);
        }

    }

    private static void validarValorMaiorZero(BigDecimal valor) {
        if(valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorDespesaInvalidoException();
        }
    }

    private static void validarData(LocalDate data) {
        if (data.isBefore(LocalDate.now().minusMonths(1)) || data.isAfter(LocalDate.now())) {
            throw new DataDespesaInvalidaException();
        }
    }

    private static void verificarStatusPendente(UUID reembolsoId, Reembolso reembolso) {
        if(!reembolso.getStatus().equals(StatusReembolso.PENDENTE)){
            throw new StatusReembolsoNaoAtualizavelException(reembolsoId);
        }
    }
}
