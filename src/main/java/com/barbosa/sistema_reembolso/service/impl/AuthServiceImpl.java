package com.barbosa.sistema_reembolso.service.impl;

import com.barbosa.sistema_reembolso.Exception.business.EmailNaoEncontradoException;
import com.barbosa.sistema_reembolso.Exception.business.EmailOuSenhaInvalidosException;
import com.barbosa.sistema_reembolso.domain.model.Usuario;
import com.barbosa.sistema_reembolso.dto.LoginRequestDTO;
import com.barbosa.sistema_reembolso.repository.UsuarioRepository;
import com.barbosa.sistema_reembolso.security.Token;
import com.barbosa.sistema_reembolso.security.TokenUtil;
import com.barbosa.sistema_reembolso.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public Token autenticar(LoginRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.email()).orElseThrow(() -> new EmailNaoEncontradoException(dto.email()));

        boolean senhaCorreta = passwordEncoder.matches(dto.senha(), usuario.getSenha());

        if(!senhaCorreta) {
            throw new EmailOuSenhaInvalidosException();
        }

       return tokenUtil.encode(usuario);


    }

}
