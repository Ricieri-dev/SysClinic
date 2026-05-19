package com.sysclinic.SysClinic.service;

import com.sysclinic.SysClinic.dto.MedicoRequestDTO;
import com.sysclinic.SysClinic.dto.MedicoResponseDTO;
import com.sysclinic.SysClinic.model.Medico;
import com.sysclinic.SysClinic.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public MedicoResponseDTO salvar(MedicoRequestDTO dto) {

        Medico medico = Medico.builder()
                .nome(dto.getNome())
                .especialidade(dto.getEspecialidade())
                .crm(dto.getCrm())
                .telefone(dto.getTelefone())
                .email(dto.getEmail())
                .build();

        Medico medicoSalvo = medicoRepository.save(medico);

        return converterParaResponseDTO(medicoSalvo);
    }

    public List<MedicoResponseDTO> listarTodos() {

        return medicoRepository.findAll()
                .stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    public MedicoResponseDTO buscarPorId(Long id) {

        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));

        return converterParaResponseDTO(medico);
    }

    public MedicoResponseDTO atualizar(Long id, MedicoRequestDTO dto) {

        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));

        medico.setNome(dto.getNome());
        medico.setEspecialidade(dto.getEspecialidade());
        medico.setCrm(dto.getCrm());
        medico.setTelefone(dto.getTelefone());
        medico.setEmail(dto.getEmail());

        Medico medicoAtualizado = medicoRepository.save(medico);

        return converterParaResponseDTO(medicoAtualizado);
    }

    public void deletar(Long id) {

        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));

        medicoRepository.delete(medico);
    }

    private MedicoResponseDTO converterParaResponseDTO(Medico medico) {

        return MedicoResponseDTO.builder()
                .id(medico.getId())
                .nome(medico.getNome())
                .especialidade(medico.getEspecialidade())
                .crm(medico.getCrm())
                .telefone(medico.getTelefone())
                .email(medico.getEmail())
                .build();
    }
}