package com.barbosa.sistema_reembolso.dto;

import com.barbosa.sistema_reembolso.domain.model.Usuario;
import com.barbosa.sistema_reembolso.domain.enums.Role;

import java.util.UUID;

public record UsuarioResponseDTO(
                                UUID id,
                                String nome,
                                 String email,
                                 Role role) {
    public static UsuarioResponseDTO fromEntity(Usuario usuario){
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole()
        );
    }
}
