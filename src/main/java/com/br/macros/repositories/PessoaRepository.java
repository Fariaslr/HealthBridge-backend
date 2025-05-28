package com.br.macros.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.macros.models.EducadorFisico;
import com.br.macros.models.Nutricionista;
import com.br.macros.models.Paciente;
import com.br.macros.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID>{
	Optional<Pessoa> findByEmail(String email);

	@Query("SELECT p FROM Paciente p")
    List<Paciente> findAllPacientes();

    @Query("SELECT n FROM Nutricionista n")
    List<Nutricionista> findAllNutricionistas();

    @Query("SELECT e FROM EducadorFisico e")
    List<EducadorFisico> findAllEducadoresFisicos(); 
    
    Optional<EducadorFisico> findEducadorFisicoById(UUID id);
    Optional<Nutricionista> findNutricionistaById(UUID id); 

    Optional<EducadorFisico> findByCref(String cref);
    Optional<Nutricionista> findByCrn(String crn);
}
