package com.br.macros.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.br.macros.models.Pessoa;
import com.br.macros.records.PessoaRecordDto;
import com.br.macros.services.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;

	@GetMapping
	public ResponseEntity<List<Pessoa>> listarPessoas() {
		List<Pessoa> pessoas = pessoaService.listarTodasAsPessoas();
		return ResponseEntity.ok(pessoas);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> buscarPessoaPorId(@PathVariable UUID id) {
		Pessoa pessoa = pessoaService.buscarPessoaPorId(id);
		return ResponseEntity.ok(pessoa);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> atualizarPessoa(@PathVariable UUID id, @RequestBody PessoaRecordDto pessoaDto) {
		Pessoa pessoaAtualizada = pessoaService.atualizarPessoa(id, pessoaDto);
		return ResponseEntity.ok(pessoaAtualizada);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletarPessoa(@PathVariable UUID id) {
		pessoaService.deletarPessoa(id);
		return ResponseEntity.ok("Pessoa excluída com sucesso");
	}
}