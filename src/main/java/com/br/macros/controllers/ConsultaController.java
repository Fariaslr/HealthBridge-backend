package com.br.macros.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.br.macros.models.Consulta;
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
	    public ResponseEntity<Consulta> saveConsulta(@RequestBody @Valid ConsultaRecordDto consultaRecordDto) {
	        Consulta consulta = consultaService.saveConsulta(consultaRecordDto);
	        return ResponseEntity.status(HttpStatus.CREATED).body(consulta);
	    }

	    @GetMapping
	    public ResponseEntity<List<Consulta>> getAllConsultas() {
	        List<Consulta> consultaList = consultaService.getAllConsultas();

	        if (!consultaList.isEmpty()) {
	            for (Consulta consulta : consultaList) {
	                UUID id = consulta.getId();
	                consulta.add(linkTo(methodOn(ConsultaController.class).getOneConsulta(id)).withSelfRel());
	            }
	        }

	        return ResponseEntity.status(HttpStatus.OK).body(consultaList);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Object> getOneConsulta(@PathVariable(value = "id") UUID id) {
	        Optional<Consulta> consultaOptional = consultaService.getOneConsulta(id);
	        if (consultaOptional.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta não encontrada");
	        }
	        consultaOptional.get().add(linkTo(methodOn(ConsultaController.class).getAllConsultas()).withRel("Todas Consultas"));
	        return ResponseEntity.status(HttpStatus.OK).body(consultaOptional.get());
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Object> updateConsulta(@PathVariable(value = "id") UUID id, @RequestBody @Valid ConsultaRecordDto consultaRecordDto) {
	        Consulta consulta = consultaService.updateConsulta(id, consultaRecordDto);
	        if (consulta == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta não encontrada");
	        }
	        return ResponseEntity.status(HttpStatus.OK).body(consulta);
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Object> deleteConsulta(@PathVariable(value = "id") UUID id) {
	        if (!consultaService.deleteConsulta(id)) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta não encontrada");
	        }
	        return ResponseEntity.status(HttpStatus.OK).body("Consulta deletada com sucesso");
	    }
}
