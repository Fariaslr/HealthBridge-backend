package com.br.macros.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.macros.models.Treino;

public interface TreinoRepository extends JpaRepository<Treino, UUID>{
	List<Treino> findByEducadorFisico(UUID educadorFisicoId);
	
	@Query("SELECT t FROM Treino t WHERE t.consulta.plano.paciente.id = :pacienteId")
	List<Treino> findByPacienteId(@Param("pacienteId") UUID pacienteId);

}
