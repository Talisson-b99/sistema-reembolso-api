package com.barbosa.sistema_reembolso.service;

import com.barbosa.sistema_reembolso.Exception.business.EmailJaCadastradoException;
import com.barbosa.sistema_reembolso.domain.model.Usuario;
import com.barbosa.sistema_reembolso.dto.UsuarioRequestDTO;
import com.barbosa.sistema_reembolso.dto.UsuarioResponseDTO;
import com.barbosa.sistema_reembolso.dto.UsuarioUpdateDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioService {
    UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO dto) throws EmailJaCadastradoException;

    List<UsuarioResponseDTO> buscarTodosUsuarios();

    UsuarioResponseDTO buscarUsuarioPorId(UUID usuarioId);

    UsuarioResponseDTO buscarUsuarioPorEmail(String email);

    UsuarioResponseDTO atualizarUsuario(UUID usuarioId, UsuarioUpdateDTO dto);

    void deletarUsuarioPorId(UUID usuarioId);
}
