package com.br.macros.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.macros.models.Plano;

public interface PlanoRepository extends JpaRepository<Plano, UUID> {
	
	List<Plano> findByProfissionalSaudeId(UUID profissionalSaudeId);

	boolean existsByPacienteId(UUID pacienteId);

}
