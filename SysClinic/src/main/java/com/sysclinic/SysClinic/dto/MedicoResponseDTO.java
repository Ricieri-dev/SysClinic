package com.sysclinic.SysClinic.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicoResponseDTO {

    private Long id;

    private String nome;

    private String especialidade;

    private String crm;

    private String telefone;

    private String email;
}