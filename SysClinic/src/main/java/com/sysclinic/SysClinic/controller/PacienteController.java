package com.sysclinic.SysClinic.controller;

import com.sysclinic.SysClinic.dto.PacienteRequestDTO;
import com.sysclinic.SysClinic.dto.PacienteResponseDTO;
import com.sysclinic.SysClinic.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/pacientes", "/api/pacientes"})
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping({"", "/"})
    public ResponseEntity<PacienteResponseDTO> salvar(
            @Valid @RequestBody PacienteRequestDTO dto) {

        PacienteResponseDTO response = pacienteService.salvar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<PacienteResponseDTO>> listarTodos() {

        return ResponseEntity.ok(pacienteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(pacienteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PacienteRequestDTO dto) {

        return ResponseEntity.ok(pacienteService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        pacienteService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
