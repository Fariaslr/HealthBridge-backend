package com.br.macros.services;

import com.br.macros.models.Paciente;
import com.br.macros.models.Pessoa; // Importe Pessoa
import com.br.macros.records.PacienteRecordDto;
import com.br.macros.repositories.PessoaRepository; // Injete PessoaRepository
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Paciente criarPaciente(PacienteRecordDto pacienteDto) {
        Paciente paciente = new Paciente();
        BeanUtils.copyProperties(pacienteDto, paciente);
        return pessoaRepository.save(paciente);
    }
    public List<Paciente> findAllPacientes() {
        return pessoaRepository.findAll().stream()
                .filter(p -> p instanceof Paciente)
                .map(p -> (Paciente) p)             
                .collect(Collectors.toList());
    }

    public Paciente findById(UUID id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        if (pessoa instanceof Paciente) {
            return (Paciente) pessoa; 
        } else {
            throw new RuntimeException("A pessoa com o ID " + id + " não é um Paciente.");
        }
    }

    public Paciente atualizarPaciente(UUID id, PacienteRecordDto pacienteDto) {
        Paciente pacienteExistente = findById(id); 
        BeanUtils.copyProperties(pacienteDto, pacienteExistente, "id");
        return pessoaRepository.save(pacienteExistente);
    }

    public void deletarPaciente(UUID id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada para deleção"));
        if (!(pessoa instanceof Paciente)) {
            throw new RuntimeException("O ID fornecido não é de um Paciente.");
        }
        pessoaRepository.deleteById(id);
    }
    

}