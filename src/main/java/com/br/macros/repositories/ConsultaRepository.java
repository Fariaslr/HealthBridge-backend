package com.br.macros.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.macros.models.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, UUID> {

	List<Consulta> findByPlano_Id(UUID planoId);
	List<Consulta> findByProfissionalSaude_Id(UUID profissionalSaudeId);
}
