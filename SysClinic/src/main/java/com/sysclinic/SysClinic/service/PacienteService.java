package com.sysclinic.SysClinic.service;

import com.sysclinic.SysClinic.model.Paciente;
import com.sysclinic.SysClinic.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    private final  PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente salvar(Paciente paciente){
        return pacienteRepository.save(paciente);
    }

    public List<Paciente> listarTodos(){
        return pacienteRepository.findAll();
    }

    public Paciente buscarPorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
    }

    public Paciente atualizar(Long id, Paciente pacienteAtualizado) {

        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        pacienteExistente.setNome(pacienteAtualizado.getNome());
        pacienteExistente.setCpf(pacienteAtualizado.getCpf());
        pacienteExistente.setTelefone(pacienteAtualizado.getTelefone());
        pacienteExistente.setEmail(pacienteAtualizado.getEmail());
        pacienteExistente.setDataNascimento(pacienteAtualizado.getDataNascimento());

        return pacienteRepository.save(pacienteExistente);
    }

    public void deletar(Long id) {

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        pacienteRepository.delete(paciente);
    }

}
