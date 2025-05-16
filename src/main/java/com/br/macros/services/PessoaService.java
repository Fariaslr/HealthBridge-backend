package com.br.macros.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.macros.models.Pessoa;
import com.br.macros.records.PessoaRecordDto;
import com.br.macros.repositories.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa adicionarPessoa(PessoaRecordDto pessoaDto) {
		Pessoa pessoa = new Pessoa();
		BeanUtils.copyProperties(pessoaDto, pessoa);
		return pessoaRepository.save(pessoa);
	}

	public List<Pessoa> listarTodasAsPessoas() {
		return pessoaRepository.findAll();
	}

	public Pessoa buscarPessoaPorId(UUID id) {
		return pessoaRepository.findById(id).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
	}

	public Pessoa buscarPorEmail(String email) {
	    return pessoaRepository.findByEmail(email)
	        .orElse(null);
	}

	public Pessoa atualizarPessoa(UUID id, PessoaRecordDto pessoaDto) {
		Pessoa pessoaExistente = buscarPessoaPorId(id);
		BeanUtils.copyProperties(pessoaDto, pessoaExistente, "id");
		return pessoaRepository.save(pessoaExistente);
	}

	public void deletarPessoa(UUID id) {
		pessoaRepository.deleteById(id);
	}
}
