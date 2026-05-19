package com.sysclinic.SysClinic.service;

import com.sysclinic.SysClinic.dto.ConsultaRequestDTO;
import com.sysclinic.SysClinic.dto.ConsultaResponseDTO;
import com.sysclinic.SysClinic.model.Consulta;
import com.sysclinic.SysClinic.model.Medico;
import com.sysclinic.SysClinic.model.Paciente;
import com.sysclinic.SysClinic.repository.ConsultaRepository;
import com.sysclinic.SysClinic.repository.MedicoRepository;
import com.sysclinic.SysClinic.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public ConsultaService(ConsultaRepository consultaRepository,
                           PacienteRepository pacienteRepository,
                           MedicoRepository medicoRepository) {

        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    public ConsultaResponseDTO salvar(ConsultaRequestDTO dto) {

        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() ->
                        new RuntimeException("Paciente não encontrado"));

        Medico medico = medicoRepository.findById(dto.getMedicoId())
                .orElseThrow(() ->
                        new RuntimeException("Médico não encontrado"));

        Consulta consulta = Consulta.builder()
                .paciente(paciente)
                .medico(medico)
                .dataHora(dto.getDataHora())
                .observacao(dto.getObservacao())
                .status(dto.getStatus())
                .build();

        Consulta consultaSalva = consultaRepository.save(consulta);

        return converterParaResponseDTO(consultaSalva);
    }

    public List<ConsultaResponseDTO> listarTodos() {

        return consultaRepository.findAll()
                .stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    public ConsultaResponseDTO buscarPorId(Long id) {

        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Consulta não encontrada"));

        return converterParaResponseDTO(consulta);
    }

    public ConsultaResponseDTO atualizar(Long id,
                                         ConsultaRequestDTO dto) {

        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Consulta não encontrada"));

        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() ->
                        new RuntimeException("Paciente não encontrado"));

        Medico medico = medicoRepository.findById(dto.getMedicoId())
                .orElseThrow(() ->
                        new RuntimeException("Médico não encontrado"));

        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataHora(dto.getDataHora());
        consulta.setObservacao(dto.getObservacao());
        consulta.setStatus(dto.getStatus());

        Consulta consultaAtualizada = consultaRepository.save(consulta);

        return converterParaResponseDTO(consultaAtualizada);
    }

    public void deletar(Long id) {

        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Consulta não encontrada"));

        consultaRepository.delete(consulta);
    }

    private ConsultaResponseDTO converterParaResponseDTO(
            Consulta consulta) {

        return ConsultaResponseDTO.builder()
                .id(consulta.getId())

                .pacienteId(consulta.getPaciente().getId())
                .pacienteNome(consulta.getPaciente().getNome())

                .medicoId(consulta.getMedico().getId())
                .medicoNome(consulta.getMedico().getNome())

                .dataHora(consulta.getDataHora())
                .observacao(consulta.getObservacao())
                .status(consulta.getStatus())
                .build();
    }
}