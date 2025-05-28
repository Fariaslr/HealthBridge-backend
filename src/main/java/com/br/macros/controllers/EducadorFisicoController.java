// src/main/java/com/br/macros/controllers/EducadorFisicoController.java
package com.br.macros.controllers;

import com.br.macros.models.EducadorFisico;
import com.br.macros.records.EducadorFisicoRecordDto;
import com.br.macros.services.EducadorFisicoService;
import jakarta.validation.Valid; // Para validação do DTO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/educadoresfisicos") // Endpoint base para Educadores Físicos
public class EducadorFisicoController {

    @Autowired
    private EducadorFisicoService educadorFisicoService;

    @PostMapping // Responde a POST /educadoresfisicos
    public ResponseEntity<EducadorFisico> createEducadorFisico(@RequestBody @Valid EducadorFisicoRecordDto educadorFisicoDto) {
        EducadorFisico newEducadorFisico = educadorFisicoService.criarEducadorFisico(educadorFisicoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEducadorFisico);
    }

    @GetMapping // Responde a GET /educadoresfisicos
    public ResponseEntity<List<EducadorFisico>> getAllEducadoresFisicos() {
        List<EducadorFisico> educadores = educadorFisicoService.findAllEducadoresFisicos();
        return ResponseEntity.ok(educadores);
    }

    @GetMapping("/{id}") // Responde a GET /educadoresfisicos/{id}
    public ResponseEntity<EducadorFisico> getEducadorFisicoById(@PathVariable UUID id) {
        EducadorFisico educador = educadorFisicoService.findById(id);
        return ResponseEntity.ok(educador);
    }

    @PutMapping("/{id}") // Responde a PUT /educadoresfisicos/{id}
    public ResponseEntity<EducadorFisico> updateEducadorFisico(@PathVariable UUID id, @RequestBody @Valid EducadorFisicoRecordDto educadorFisicoDto) {
        EducadorFisico updatedEducadorFisico = educadorFisicoService.atualizarEducadorFisico(id, educadorFisicoDto);
        return ResponseEntity.ok(updatedEducadorFisico);
    }

    @DeleteMapping("/{id}") // Responde a DELETE /educadoresfisicos/{id}
    public ResponseEntity<Void> deleteEducadorFisico(@PathVariable UUID id) {
        educadorFisicoService.deletarEducadorFisico(id);
        return ResponseEntity.noContent().build();
    }
}