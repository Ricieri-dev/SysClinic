package com.sysclinic.SysClinic.repository;

import com.sysclinic.SysClinic.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
}