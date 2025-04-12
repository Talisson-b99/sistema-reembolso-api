package com.barbosa.sistema_reembolso.dto;

import jakarta.validation.constraints.Email;


public record UsuarioUpdateDTO(
         String nome,
        @Email String email)
{}
