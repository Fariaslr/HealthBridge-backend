package com.br.macros.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.br.macros.models.Plano;
import com.br.macros.records.PlanoRecordDto;
import com.br.macros.services.PlanoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/planos")
public class PlanoController {

	@Autowired
	private PlanoService planoService;

	@PostMapping
	public ResponseEntity<Plano> adicionarPlano(@RequestBody PlanoRecordDto planoDto) {
		Plano novoPlano = planoService.adicionarPlano(planoDto);
		return ResponseEntity.ok(novoPlano);
	}

	@GetMapping
	public ResponseEntity<List<Plano>> listarTodosOsPlanos() {
		List<Plano> planos = planoService.listarTodosOsPlanos();
		return ResponseEntity.ok(planos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Plano> buscarPlanoPorId(@PathVariable UUID id) {
		Plano plano = planoService.buscarPlanoPorId(id);
		return ResponseEntity.ok(plano);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Plano> atualizarPlano(@PathVariable UUID id, @RequestBody PlanoRecordDto planoDto) {
		Plano planoAtualizado = planoService.atualizarPlano(id, planoDto);
		return ResponseEntity.ok(planoAtualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarPlano(@PathVariable UUID id) {
		planoService.deletarPlano(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/profissional/{profissionalSaudeId}")
	public ResponseEntity<List<Plano>> buscarPlanosPorProfissional(@PathVariable UUID profissionalSaudeId) {
		List<Plano> planos = planoService.buscarPlanosPorProfissionalSaudeId(profissionalSaudeId);
		return ResponseEntity.ok(planos);
	}
	
	@GetMapping("/paciente/{pacienteId}")
	public ResponseEntity<Plano> buscarPlanoPorPaciente(@PathVariable UUID pacienteId) {
	    Plano plano = planoService.buscarPlanoPorPacienteId(pacienteId);
	    return ResponseEntity.ok(plano);
	}

}
