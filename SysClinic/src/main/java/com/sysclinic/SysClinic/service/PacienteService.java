package com.sysclinic.SysClinic.service;

import com.sysclinic.SysClinic.dto.PacienteRequestDTO;
import com.sysclinic.SysClinic.dto.PacienteResponseDTO;
import com.sysclinic.SysClinic.model.Paciente;
import com.sysclinic.SysClinic.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public PacienteResponseDTO salvar(PacienteRequestDTO dto) {

        Paciente paciente = Paciente.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .telefone(dto.getTelefone())
                .email(dto.getEmail())
                .dataNascimento(dto.getDataNascimento())
                .build();

        Paciente pacienteSalvo = pacienteRepository.save(paciente);

        return converterParaResponseDTO(pacienteSalvo);
    }

    public List<PacienteResponseDTO> listarTodos() {

        return pacienteRepository.findAll()
                .stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    public PacienteResponseDTO buscarPorId(Long id) {

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        return converterParaResponseDTO(paciente);
    }

    public PacienteResponseDTO atualizar(Long id, PacienteRequestDTO dto) {

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        paciente.setNome(dto.getNome());
        paciente.setCpf(dto.getCpf());
        paciente.setTelefone(dto.getTelefone());
        paciente.setEmail(dto.getEmail());
        paciente.setDataNascimento(dto.getDataNascimento());

        Paciente pacienteAtualizado = pacienteRepository.save(paciente);

        return converterParaResponseDTO(pacienteAtualizado);
    }

    public void deletar(Long id) {

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        pacienteRepository.delete(paciente);
    }

    private PacienteResponseDTO converterParaResponseDTO(Paciente paciente) {

        return PacienteResponseDTO.builder()
                .id(paciente.getId())
                .nome(paciente.getNome())
                .cpf(paciente.getCpf())
                .telefone(paciente.getTelefone())
                .email(paciente.getEmail())
                .dataNascimento(paciente.getDataNascimento())
                .build();
    }
}