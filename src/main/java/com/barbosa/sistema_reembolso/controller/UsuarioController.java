package com.barbosa.sistema_reembolso.controller;

import com.barbosa.sistema_reembolso.Exception.business.EmailJaCadastradoException;
import com.barbosa.sistema_reembolso.dto.UsuarioRequestDTO;
import com.barbosa.sistema_reembolso.dto.UsuarioResponseDTO;
import com.barbosa.sistema_reembolso.dto.UsuarioUpdateDTO;
import com.barbosa.sistema_reembolso.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDTO dto)  {
        var usuario = usuarioService.cadastrarUsuario(dto);

        return ResponseEntity.created(URI.create("/usuarios/" + usuario.id())).build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodosUsuarios() {
        return ResponseEntity.ok(usuarioService.buscarTodosUsuarios());
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorId(@PathVariable UUID usuarioId){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(usuarioId));
    }

    @GetMapping("/search")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable UUID usuarioId, @Valid @RequestBody UsuarioUpdateDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(usuarioId, dto));
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> deletarUsuarioPorId(@PathVariable UUID usuarioId) {
        usuarioService.deletarUsuarioPorId(usuarioId);
        return ResponseEntity.noContent().build();
    }
}
