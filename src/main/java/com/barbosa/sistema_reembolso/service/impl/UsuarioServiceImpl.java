package com.barbosa.sistema_reembolso.service.impl;

import com.barbosa.sistema_reembolso.Exception.business.EmailJaCadastradoException;
import com.barbosa.sistema_reembolso.Exception.business.EmailNaoEncontradoException;
import com.barbosa.sistema_reembolso.Exception.business.UsuarioNaoEncontradoException;
import com.barbosa.sistema_reembolso.domain.model.Usuario;
import com.barbosa.sistema_reembolso.dto.UsuarioRequestDTO;
import com.barbosa.sistema_reembolso.dto.UsuarioResponseDTO;
import com.barbosa.sistema_reembolso.dto.UsuarioUpdateDTO;
import com.barbosa.sistema_reembolso.repository.UsuarioRepository;
import com.barbosa.sistema_reembolso.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO dto)  {
        if(usuarioRepository.existsByEmail(dto.email())) {
            throw new EmailJaCadastradoException(dto.email());
        }
        Usuario newUsuario = dto.toEntity();
        var senhaCriptografada = passwordEncoder.encode(newUsuario.getSenha());
        newUsuario.setSenha(senhaCriptografada);

        Usuario usuarioSalvo = usuarioRepository.save(newUsuario);

        return UsuarioResponseDTO.fromEntity(usuarioSalvo);
    }

    @Override
    @PreAuthorize("hasRole('GESTOR')")
    public List<UsuarioResponseDTO> buscarTodosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(UsuarioResponseDTO::fromEntity)
                .toList();
    }

    @Override
    @PreAuthorize("hasRole('GESTOR')")
    public UsuarioResponseDTO buscarUsuarioPorId(UUID usuarioId) {
        var usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));

       return UsuarioResponseDTO.fromEntity(usuario);
    }

    @Override
    @PreAuthorize("hasRole('GESTOR')")
    public UsuarioResponseDTO buscarUsuarioPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new EmailNaoEncontradoException(email));
        return UsuarioResponseDTO.fromEntity(usuario);
    }

    @Override
    public UsuarioResponseDTO atualizarUsuario(UUID usuarioId, UsuarioUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() ->  new UsuarioNaoEncontradoException(usuarioId));

        if(StringUtils.hasText(dto.email())){
            if(usuarioRepository.existsByEmail(dto.email())){
                throw new EmailJaCadastradoException(dto.email());
            }
            usuario.setEmail(dto.email());
        }
        if(StringUtils.hasText(dto.nome())) {
            usuario.setNome(dto.nome());
        }

        usuarioRepository.save(usuario);

        return UsuarioResponseDTO.fromEntity(usuario);
    }

    @Override
    public void deletarUsuarioPorId(UUID usuarioId) {
        if(!usuarioRepository.existsById(usuarioId)) {
            throw new UsuarioNaoEncontradoException(usuarioId);
        }

        usuarioRepository.deleteById(usuarioId);
    }
}
