package com.barbosa.sistema_reembolso.security;

import com.barbosa.sistema_reembolso.domain.enums.Role;
import com.barbosa.sistema_reembolso.repository.ReembolsoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("gerenciamenteAcesso")
public class GerenciamentoAcesso {
    @Autowired
    ReembolsoRepository reembolsoRepository;

    public boolean podeVerReembolso(UUID reembolsoID){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UsuarioAutenticado usuario = (UsuarioAutenticado) auth.getPrincipal();

        if(usuario.getRole() == Role.GESTOR) return true;

        return reembolsoRepository.findById(reembolsoID)
                .map(r -> r.getUsuario().getId().equals(usuario.getId())).orElse(false);
    }

}
