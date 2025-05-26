package com.br.macros.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.macros.models.Paciente;
import com.br.macros.models.Plano;
import com.br.macros.models.ProfissionalSaude;
import com.br.macros.records.PlanoRecordDto;
import com.br.macros.repositories.PessoaRepository; // Ainda necessário para buscar Paciente e ProfissionalSaude
import com.br.macros.repositories.PlanoRepository;

import java.util.List;
import java.util.UUID;
import java.util.Optional; // Importe Optional

@Service
public class PlanoService {
	
	@Autowired
	private PlanoRepository planoRepository;

	@Autowired
	private PessoaRepository pessoaRepository; // Mantido para buscar Paciente e ProfissionalSaude

	public Plano adicionarPlano(PlanoRecordDto planoDto) {
	    if (planoDto.pacienteId() == null || planoDto.profissionalSaudeId() == null) {
	        throw new IllegalArgumentException("IDs de paciente e profissional de saúde são obrigatórios.");
	    }

	    // 1. VERIFICAÇÃO DE UNICIDADE:
	    Optional<Plano> planoExistente = planoRepository.findByPacienteId(planoDto.pacienteId());
	    if (planoExistente.isPresent()) {
	        throw new IllegalArgumentException("Este paciente já possui um plano cadastrado. Por favor, atualize o plano existente em vez de criar um novo.");
	    }

	    // 2. BUSCA DO PACIENTE E PROFISSIONAL (mantido o código anterior, com a necessidade de casting)
	    // Idealmente, se você tiver repositórios dedicados para Paciente e ProfissionalSaude, use-os aqui
	    // para evitar o cast e garantir a correta instanciação do tipo.
	    Paciente paciente = (Paciente) pessoaRepository.findById(planoDto.pacienteId())
	            .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
	    
	    ProfissionalSaude profissional = (ProfissionalSaude) pessoaRepository.findById(planoDto.profissionalSaudeId())
	            .orElseThrow(() -> new RuntimeException("Profissional de saúde não encontrado"));

	    // 3. CRIAÇÃO E SALVAMENTO DO NOVO PLANO
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
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));
    }

    public Plano atualizarPlano(UUID id, PlanoRecordDto planoDto) {
        // Você pode querer verificar aqui se o pacienteId do planoDto
        // corresponde ao pacienteId do planoExistente para evitar que um
        // plano seja atualizado para outro paciente.
        Plano planoExistente = buscarPlanoPorId(id);

        // Se você não for mudar o paciente associado ao plano na atualização,
        // pode manter o pacienteExistente e apenas copiar os campos do DTO.
        // Se puder mudar o paciente, a lógica abaixo precisaria ser ajustada.
        Paciente paciente = (Paciente) pessoaRepository.findById(planoDto.pacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado para atualização"));
        
        ProfissionalSaude profissional = (ProfissionalSaude) pessoaRepository.findById(planoDto.profissionalSaudeId())
                .orElseThrow(() -> new RuntimeException("Profissional de saúde não encontrado para atualização"));

        planoExistente.setPaciente(paciente); // Garante que o paciente é o do DTO
        planoExistente.setProfissionalSaude(profissional); // Garante que o profissional é o do DTO

        // Copia as propriedades que vêm no DTO
        BeanUtils.copyProperties(planoDto, planoExistente, "id", "paciente", "profissionalSaude"); // Exclua campos que são objetos/entidades

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