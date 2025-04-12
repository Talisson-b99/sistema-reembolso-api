package com.barbosa.sistema_reembolso.controller;

import com.barbosa.sistema_reembolso.domain.model.Reembolso;
import com.barbosa.sistema_reembolso.dto.ReembolsoRequestDTO;
import com.barbosa.sistema_reembolso.dto.ReembolsoResponseDTO;
import com.barbosa.sistema_reembolso.service.ReembolsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/reembolsos")
public class ReembolsoController {

    @Autowired
    private ReembolsoService reembolsoService;

    @PostMapping
    public ResponseEntity<ReembolsoResponseDTO> cadastrarReembolso(@RequestBody ReembolsoRequestDTO dto) {
        var reembolso = reembolsoService.cadastrarReembolso(dto);

        return ResponseEntity.created(URI.create("/reembolsos/" + reembolso.id())).build();
    }

}
