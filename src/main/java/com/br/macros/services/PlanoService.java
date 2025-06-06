package com.br.macros.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.macros.models.Paciente;
import com.br.macros.models.Plano;
import com.br.macros.models.ProfissionalSaude;
import com.br.macros.records.PlanoRecordDto;
import com.br.macros.repositories.PessoaRepository;
import com.br.macros.repositories.PlanoRepository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.util.NoSuchElementException; // <--- Certifique-se que este import está aqui

@Service
public class PlanoService {
	
	@Autowired
	private PlanoRepository planoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	public Plano adicionarPlano(PlanoRecordDto planoDto) {
	    if (planoDto.pacienteId() == null || planoDto.profissionalSaudeId() == null) {
	        throw new IllegalArgumentException("IDs de paciente e profissional de saúde são obrigatórios.");
	    }

	    Optional<Plano> planoExistente = planoRepository.findByPacienteId(planoDto.pacienteId());
	    if (planoExistente.isPresent()) {
	        throw new IllegalArgumentException("Este paciente já possui um plano cadastrado. Por favor, atualize o plano existente em vez de criar um novo.");
	    }

	    Paciente paciente = (Paciente) pessoaRepository.findById(planoDto.pacienteId())
	            .orElseThrow(() -> new NoSuchElementException("Paciente não encontrado")); // <-- Pode ser NoSuchElementException aqui também
	    
	    ProfissionalSaude profissional = (ProfissionalSaude) pessoaRepository.findById(planoDto.profissionalSaudeId())
	            .orElseThrow(() -> new NoSuchElementException("Profissional de saúde não encontrado")); // <-- Pode ser NoSuchElementException aqui também

	    Plano plano = new Plano();
	    plano.setPaciente(paciente);
	    plano.setObjetivo(planoDto.objetivo());
	    plano.setNivelAtividadeFisica(planoDto.nivelAtividadeFisica());
	    plano.setProfissionalSaude(profissional);

	    return planoRepository.save(plano);
	}

    public List<Plano> listarTodosOsPlanos() {
        return planoRepository.findAll();
    }

    public Plano buscarPlanoPorId(UUID id) {
        return planoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Plano não encontrado")); // <-- Pode ser NoSuchElementException aqui também
    }

    public Plano atualizarPlano(UUID id, PlanoRecordDto planoDto) {
        Plano planoExistente = buscarPlanoPorId(id);

        if (planoDto.objetivo() != null) {
            planoExistente.setObjetivo(planoDto.objetivo());
        }
        if (planoDto.nivelAtividadeFisica() != null) {
            planoExistente.setNivelAtividadeFisica(planoDto.nivelAtividadeFisica());
        }

        return planoRepository.save(planoExistente);
    }

    public void deletarPlano(UUID id) {
        planoRepository.deleteById(id);
    }

    public List<Plano> buscarPlanosPorProfissionalSaudeId(UUID profissionalSaudeId) {
        return planoRepository.findByProfissionalSaudeId(profissionalSaudeId);
    }
    
    public Plano buscarPlanoPorPacienteId(UUID pacienteId) {
        return planoRepository.findByPacienteId(pacienteId)
            .orElseThrow(() -> new NoSuchElementException("Plano do paciente não encontrado"));
    }

}