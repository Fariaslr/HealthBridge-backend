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

@Service
public class PlanoService {
	
	@Autowired
	private PlanoRepository planoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	public Plano adicionarPlano(PlanoRecordDto planoDto) {
	    Plano plano = new Plano();

	    Paciente paciente = (Paciente) pessoaRepository.findById(planoDto.pacienteId())
	        .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

	    ProfissionalSaude profissional = (ProfissionalSaude) pessoaRepository.findById(planoDto.profissionalSaudeId())
	        .orElseThrow(() -> new RuntimeException("Profissional de saúde não encontrado"));
	    
	    plano.setPaciente(paciente);
	    plano.setProfissionalSaude(profissional);
	    plano.setObjetivo(planoDto.objetivo());
	    plano.setNivelAtividadeFisica(planoDto.nivelAtividadeFisica());

	    return planoRepository.save(plano);
	}

    public List<Plano> listarTodosOsPlanos() {
        return planoRepository.findAll();
    }

    public Plano buscarPlanoPorId(UUID id) {
        return planoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));
    }

    public Plano atualizarPlano(UUID id, PlanoRecordDto planoDto) {
        Plano planoExistente = buscarPlanoPorId(id);
        BeanUtils.copyProperties(planoDto, planoExistente, "id");
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
            .orElseThrow(() -> new RuntimeException("Plano do paciente não encontrado"));
    }

}
