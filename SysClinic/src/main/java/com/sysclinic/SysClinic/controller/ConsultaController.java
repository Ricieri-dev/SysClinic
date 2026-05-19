package com.sysclinic.SysClinic.controller;

import com.sysclinic.SysClinic.dto.ConsultaRequestDTO;
import com.sysclinic.SysClinic.dto.ConsultaResponseDTO;
import com.sysclinic.SysClinic.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> salvar(
            @Valid @RequestBody ConsultaRequestDTO dto) {

        ConsultaResponseDTO response = consultaService.salvar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponseDTO>> listarTodos() {

        return ResponseEntity.ok(consultaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(consultaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ConsultaRequestDTO dto) {

        return ResponseEntity.ok(consultaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        consultaService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}