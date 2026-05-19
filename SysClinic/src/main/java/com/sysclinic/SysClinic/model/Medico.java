package com.sysclinic.SysClinic.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_medico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 100)
    private String especialidade;

    @Column(nullable = false, unique = true, length = 20)
    private String crm;

    @Column(length = 20)
    private String telefone;

    @Column(length = 150)
    private String email;
}