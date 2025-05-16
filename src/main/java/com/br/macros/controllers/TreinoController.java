package com.br.macros.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.br.macros.models.Treino;
import com.br.macros.records.TreinoRecordDto;
import com.br.macros.services.TreinoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/treinos")
public class TreinoController {
	
	@Autowired
    private TreinoService treinoService;

    @PostMapping
    public ResponseEntity<Treino> adicionarTreino(@RequestBody TreinoRecordDto treinoDto) {
        Treino novoTreino = treinoService.adicionarTreino(treinoDto);
        return ResponseEntity.ok(novoTreino);
    }

    @GetMapping
    public ResponseEntity<List<Treino>> listarTodosOsTreinos() {
        List<Treino> treinos = treinoService.listarTodosOsTreinos();
        return ResponseEntity.ok(treinos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Treino> buscarTreinoPorId(@PathVariable UUID id) {
        Treino treino = treinoService.buscarTreinoPorId(id);
        return ResponseEntity.ok(treino);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Treino> atualizarTreino(@PathVariable UUID id, @RequestBody TreinoRecordDto treinoDto) {
        Treino treinoAtualizado = treinoService.atualizarTreino(id, treinoDto);
        return ResponseEntity.ok(treinoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTreino(@PathVariable UUID id) {
        treinoService.deletarTreino(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profissional/{profissionalSaudeId}")
    public ResponseEntity<List<Treino>> buscarTreinosPorProfissional(@PathVariable UUID profissionalSaudeId) {
        List<Treino> treinos = treinoService.buscarTreinosPorProfissionalSaudeId(profissionalSaudeId);
        return ResponseEntity.ok(treinos);
    }
}
