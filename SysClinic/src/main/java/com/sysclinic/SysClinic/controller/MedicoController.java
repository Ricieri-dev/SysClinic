package com.sysclinic.SysClinic.controller;

import com.sysclinic.SysClinic.dto.MedicoRequestDTO;
import com.sysclinic.SysClinic.dto.MedicoResponseDTO;
import com.sysclinic.SysClinic.service.MedicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    public ResponseEntity<MedicoResponseDTO> salvar(
            @Valid @RequestBody MedicoRequestDTO dto) {

        MedicoResponseDTO response = medicoService.salvar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MedicoResponseDTO>> listarTodos() {

        return ResponseEntity.ok(medicoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(medicoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody MedicoRequestDTO dto) {

        return ResponseEntity.ok(medicoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        medicoService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}