package com.br.macros.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.br.macros.models.ExecucaoExercicio;
import com.br.macros.records.ExecucaoExercicioRecordDto;
import com.br.macros.services.ExecucaoExercicioService;

public class ExecucaoExercicioController {

	@Autowired
	private ExecucaoExercicioService execucaoExercicioService;

	@PostMapping
	public ResponseEntity<ExecucaoExercicio> createExecucaoExercicio(
			@RequestBody ExecucaoExercicioRecordDto execucaoExercicioRecordDto) {
		ExecucaoExercicio createdExecucao = execucaoExercicioService.create(execucaoExercicioRecordDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdExecucao);
	}

	@GetMapping("/{treinoId}")
	public ResponseEntity<List<ExecucaoExercicio>> getExecucoesExercicio(@PathVariable UUID treinoId,
			@RequestParam String termoBusca) {
		List<ExecucaoExercicio> execucoes = execucaoExercicioService.read(treinoId, termoBusca);
		return ResponseEntity.ok(execucoes);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ExecucaoExercicio> updateExecucaoExercicio(@PathVariable UUID id,
			@RequestBody ExecucaoExercicioRecordDto execucaoExercicioRecordDto) {
		ExecucaoExercicio updatedExecucao = execucaoExercicioService.update(id, execucaoExercicioRecordDto);
		if (updatedExecucao == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(updatedExecucao);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteExecucaoExercicio(@PathVariable UUID id) {
		execucaoExercicioService.delete(id);
		return ResponseEntity.ok("Execução de exercício excluída com sucesso");
	}
}
