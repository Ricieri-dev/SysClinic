package com.sysclinic.SysClinic.controller;

import com.sysclinic.SysClinic.model.Paciente;
import com.sysclinic.SysClinic.service.PacienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public Paciente salvar(@RequestBody Paciente paciente){
        return pacienteService.salvar(paciente);
    }

    @GetMapping
    public List<Paciente> listarTodos(){
        return pacienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public Paciente buscarPorId(@PathVariable Long id) {
        return pacienteService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Paciente atualizar(@PathVariable Long id,
                              @RequestBody Paciente paciente) {

        return pacienteService.atualizar(id, paciente);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {

        pacienteService.deletar(id);
    }

}
