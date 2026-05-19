package com.sysclinic.SysClinic.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultaResponseDTO {

    private Long id;

    private Long pacienteId;
    private String pacienteNome;

    private Long medicoId;
    private String medicoNome;

    private LocalDateTime dataHora;

    private String observacao;

    private String status;
}