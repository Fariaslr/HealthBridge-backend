package com.br.macros.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.br.macros.models.Consulta;
import com.br.macros.models.Paciente; 
import com.br.macros.models.ProfissionalSaude;
import com.br.macros.records.ConsultaRecordDto;
import com.br.macros.services.ConsultaService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/consultas") 
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    public ResponseEntity<?> saveConsulta(@RequestBody @Validated ConsultaRecordDto consultaRecordDto) {
        try {
            Consulta consulta = consultaService.saveConsulta(consultaRecordDto);
            consulta.add(linkTo(methodOn(ConsultaController.class).getOneConsulta(consulta.getId())).withSelfRel());
            return ResponseEntity.status(HttpStatus.CREATED).body(consulta);
        } catch (IllegalArgumentException e) {
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating consultation: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> getAllConsultas() {
        List<Consulta> consultaList = consultaService.getAllConsultas();
        if (!consultaList.isEmpty()) {
            for (Consulta consulta : consultaList) {
                UUID id = consulta.getId();
                // Add HATEOAS link to each consultation
                consulta.add(linkTo(methodOn(ConsultaController.class).getOneConsulta(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(consultaList);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneConsulta(@PathVariable(value = "id") UUID id) {
        Optional<Consulta> consultaOptional = consultaService.getOneConsulta(id);
        if (consultaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta não encontrada com ID: " + id);
        }
        Consulta consulta = consultaOptional.get();
        consulta.add(linkTo(methodOn(ConsultaController.class).getAllConsultas()).withRel("todasConsultas"));
        return ResponseEntity.status(HttpStatus.OK).body(consulta);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Consulta>> getConsultasByPacienteId(@PathVariable(value = "pacienteId") UUID pacienteId) {
        List<Consulta> consultaList = consultaService.findConsultasByPacienteId(pacienteId);
        if (!consultaList.isEmpty()) {
            for (Consulta consulta : consultaList) {
                UUID id = consulta.getId();
                consulta.add(linkTo(methodOn(ConsultaController.class).getOneConsulta(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(consultaList);
    }

    @GetMapping("/profissional/{profissionalSaudeId}")
    public ResponseEntity<List<Consulta>> getConsultasByProfissionalSaudeId(@PathVariable(value = "profissionalSaudeId") UUID profissionalSaudeId) {
        List<Consulta> consultaList = consultaService.findConsultasByProfissionalSaudeId(profissionalSaudeId);
        if (!consultaList.isEmpty()) {
            for (Consulta consulta : consultaList) {
                UUID id = consulta.getId();
                // Add HATEOAS link to each consultation
                consulta.add(linkTo(methodOn(ConsultaController.class).getOneConsulta(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(consultaList);
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> updateConsulta(@PathVariable(value = "id") UUID id, @RequestBody @Valid ConsultaRecordDto consultaRecordDto) {
        try {
            Consulta consulta = consultaService.updateConsulta(id, consultaRecordDto);
            if (consulta == null) { // Should be handled by service throwing an exception ideally
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta não encontrada com ID: " + id + " para atualização.");
            }
            // Add HATEOAS link
            consulta.add(linkTo(methodOn(ConsultaController.class).getOneConsulta(consulta.getId())).withSelfRel());
            return ResponseEntity.status(HttpStatus.OK).body(consulta);
        } catch (IllegalArgumentException e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) { // Catching a more generic exception if findById in service returns null
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteConsulta(@PathVariable(value = "id") UUID id) {
        boolean deleted = consultaService.deleteConsulta(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta não encontrada com ID: " + id + " para exclusão.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Consulta deletada com sucesso.");
    }
}
