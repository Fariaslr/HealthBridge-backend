// src/main/java/com/br/macros/controllers/NutricionistaController.java
package com.br.macros.controllers;

import com.br.macros.models.Nutricionista;
import com.br.macros.records.NutricionistaRecordDto;
import com.br.macros.services.NutricionistaService;
import jakarta.validation.Valid; // Para usar as validações do DTO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/nutricionistas") // Este é o endpoint base para Nutricionistas
public class NutricionistaController {

    @Autowired
    private NutricionistaService nutricionistaService;

    @PostMapping // Responde a POST /nutricionistas
    public ResponseEntity<Nutricionista> createNutricionista(@RequestBody @Valid NutricionistaRecordDto nutricionistaDto) {
        Nutricionista newNutricionista = nutricionistaService.criarNutricionista(nutricionistaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newNutricionista);
    }

    @GetMapping // Responde a GET /nutricionistas
    public ResponseEntity<List<Nutricionista>> getAllNutricionistas() {
        List<Nutricionista> nutricionistas = nutricionistaService.findAllNutricionistas();
        return ResponseEntity.ok(nutricionistas);
    }

    @GetMapping("/{id}") // Responde a GET /nutricionistas/{id}
    public ResponseEntity<Nutricionista> getNutricionistaById(@PathVariable UUID id) {
        Nutricionista nutricionista = nutricionistaService.findById(id);
        return ResponseEntity.ok(nutricionista);
    }

    @PutMapping("/{id}") // Responde a PUT /nutricionistas/{id}
    public ResponseEntity<Nutricionista> updateNutricionista(@PathVariable UUID id, @RequestBody @Valid NutricionistaRecordDto nutricionistaDto) {
        Nutricionista updatedNutricionista = nutricionistaService.atualizarNutricionista(id, nutricionistaDto);
        return ResponseEntity.ok(updatedNutricionista);
    }

    @DeleteMapping("/{id}") // Responde a DELETE /nutricionistas/{id}
    public ResponseEntity<Void> deleteNutricionista(@PathVariable UUID id) {
        nutricionistaService.deletarNutricionista(id);
        return ResponseEntity.noContent().build();
    }
}