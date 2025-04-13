package com.barbosa.sistema_reembolso.controller;

import com.barbosa.sistema_reembolso.domain.enums.StatusReembolso;
import com.barbosa.sistema_reembolso.dto.JustificativaRecusaDTO;
import com.barbosa.sistema_reembolso.dto.ReembolsoRequestDTO;
import com.barbosa.sistema_reembolso.dto.ReembolsoResponseDTO;
import com.barbosa.sistema_reembolso.dto.UpdateReembolsoDTO;
import com.barbosa.sistema_reembolso.service.ReembolsoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<List<ReembolsoResponseDTO>> buscarTodosReembolsos(){
        return ResponseEntity.ok(reembolsoService.buscarTodosReembolsos());
    }

    @GetMapping("/{reembolsoId}")
    public ResponseEntity<ReembolsoResponseDTO> buscarReembolsoPorId(@PathVariable UUID reembolsoId){
        return ResponseEntity.ok(reembolsoService.buscarReembolsoPorId(reembolsoId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ReembolsoResponseDTO>> buscarReembolsoPorStatus(@RequestParam("status")StatusReembolso status){
        return ResponseEntity.ok(reembolsoService.buscarReembolsoPorStatus(status));
    }

    @PutMapping("/{reembolsoId}")
    public ResponseEntity<ReembolsoResponseDTO> atualizarReembolso(@PathVariable UUID reembolsoId, @RequestBody UpdateReembolsoDTO dto){
        reembolsoService.atualizarReembolso(reembolsoId, dto);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{reembolsoId}/aprovar")
    public ResponseEntity<ReembolsoResponseDTO> aprovarReembolso(@PathVariable UUID reembolsoId){
        ;
        return  ResponseEntity.ok().body(reembolsoService.aprovarReembolso(reembolsoId));
    }

    @PatchMapping("/{reembolsoId}/recusar")
    public ResponseEntity<ReembolsoResponseDTO> recusarReembolso(@PathVariable UUID reembolsoId, @Valid @RequestBody JustificativaRecusaDTO justificativaRecusaDTO){

        return ResponseEntity.ok(reembolsoService.recusarReembolso(reembolsoId, justificativaRecusaDTO));
    }

    @DeleteMapping("/{reembolsoId}")
    public ResponseEntity<Void> deletarReembolso(@PathVariable UUID reembolsoId) {
        reembolsoService.deletarReembolso(reembolsoId);

        return ResponseEntity.noContent().build();
    }

}
