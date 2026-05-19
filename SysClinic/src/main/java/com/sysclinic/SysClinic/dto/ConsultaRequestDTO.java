package com.sysclinic.SysClinic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaRequestDTO {

    @NotNull(message = "Paciente é obrigatório")
    private Long pacienteId;

    @NotNull(message = "Médico é obrigatório")
    private Long medicoId;

    @NotNull(message = "Data e hora são obrigatórias")
    private LocalDateTime dataHora;

    private String observacao;

    @NotBlank(message = "Status é obrigatório")
    private String status;
}