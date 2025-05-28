package com.br.macros.controllers;

import com.br.macros.models.Paciente;
import com.br.macros.records.PacienteRecordDto;
import com.br.macros.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<Paciente> createPaciente(@RequestBody PacienteRecordDto pacienteDto) {
        Paciente newPaciente = pacienteService.criarPaciente(pacienteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPaciente);
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> getAllPacientes() {
        List<Paciente> pacientes = pacienteService.findAllPacientes();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> getPacienteById(@PathVariable UUID id) {
        Paciente paciente = pacienteService.findById(id);
        return ResponseEntity.ok(paciente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> updatePaciente(@PathVariable UUID id, @RequestBody PacienteRecordDto pacienteDto) {
        Paciente updatedPaciente = pacienteService.atualizarPaciente(id, pacienteDto);
        return ResponseEntity.ok(updatedPaciente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable UUID id) {
        pacienteService.deletarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}