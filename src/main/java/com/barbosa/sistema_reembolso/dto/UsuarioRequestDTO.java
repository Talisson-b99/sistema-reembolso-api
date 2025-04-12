package com.barbosa.sistema_reembolso.dto;


import com.barbosa.sistema_reembolso.domain.model.Usuario;
import com.barbosa.sistema_reembolso.domain.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @NotBlank  String nome,
        @Email @NotBlank String email,
        @Size(min = 6) @NotBlank String senha) {

    public Usuario toEntity(){
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setRole(Role.COLABORADOR);

        return usuario;
    }
}
