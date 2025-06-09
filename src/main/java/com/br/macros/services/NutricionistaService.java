package com.br.macros.services;

import com.br.macros.models.Nutricionista;
import com.br.macros.records.NutricionistaRecordDto;
import com.br.macros.repositories.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NutricionistaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Nutricionista criarNutricionista(NutricionistaRecordDto dto) {
		if (pessoaRepository.findByEmail(dto.email()).isPresent()) {
			throw new IllegalArgumentException("Já existe um usuário cadastrado com este email.");
		}

		if (pessoaRepository.findByCrn(dto.crn()).isPresent()) {
			throw new IllegalArgumentException("Já existe um nutricionista cadastrado com este CRN.");
		}

		Nutricionista nutricionista = new Nutricionista();
		BeanUtils.copyProperties(dto, nutricionista);
		return pessoaRepository.save(nutricionista);
	}

	public List<Nutricionista> findAllNutricionistas() {
		return pessoaRepository.findAllNutricionistas();
	}

	public Nutricionista findById(UUID id) {
		return pessoaRepository.findById(id).map(pessoa -> {
			if (pessoa instanceof Nutricionista) {
				return (Nutricionista) pessoa;
			}
			throw new RuntimeException("O ID fornecido não pertence a um Nutricionista.");
		}).orElseThrow(() -> new RuntimeException("Nutricionista não encontrado."));
	}

	public Nutricionista atualizarNutricionista(UUID id, NutricionistaRecordDto dto) {
		Nutricionista nutricionistaExistente = findById(id);
		if (!nutricionistaExistente.getCrn().equals(dto.crn()) && pessoaRepository.findByCrn(dto.crn()).isPresent()) {
			throw new IllegalArgumentException("Já existe um nutricionista com este CRN.");
		}
		BeanUtils.copyProperties(dto, nutricionistaExistente, "id");
		return pessoaRepository.save(nutricionistaExistente);
	}

	public void deletarNutricionista(UUID id) {
		pessoaRepository.deleteById(id);
	}
}