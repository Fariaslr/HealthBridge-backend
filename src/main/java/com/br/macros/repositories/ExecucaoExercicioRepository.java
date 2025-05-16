package com.br.macros.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.macros.models.ExecucaoExercicio;

public interface ExecucaoExercicioRepository extends JpaRepository<ExecucaoExercicio, UUID>{

	@Query("SELECT ee FROM ExecucaoExercicio ee JOIN ee.treino t " +
            "WHERE t.id = :treinoId AND (" +
            "LOWER(ee.exercicio.nome) LIKE LOWER(CONCAT('%', :termoBusca, '%')) " +
            "OR LOWER(ee.exercicio.descricao) LIKE LOWER(CONCAT('%', :termoBusca, '%')) " +
            "OR LOWER(ee.exercicio.grupoMuscular) LIKE LOWER(CONCAT('%', :termoBusca, '%')))" +
            "ORDER BY ee.ordem")
    List<ExecucaoExercicio> findByTreinoIdAndTermoBusca(@Param("treinoId") UUID treinoId, @Param("termoBusca") String termoBusca);

}
