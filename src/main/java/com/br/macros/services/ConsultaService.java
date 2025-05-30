package com.br.macros.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList; // Mantenha para o caso de não encontrar plano
import java.util.Collections; // Para retornar lista vazia de forma segura
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.macros.models.Consulta;
import com.br.macros.models.Paciente;
import com.br.macros.models.Pessoa;
import com.br.macros.models.Plano;
import com.br.macros.models.ProfissionalSaude;
import com.br.macros.records.ConsultaRecordDto;
import com.br.macros.repositories.ConsultaRepository;
import com.br.macros.repositories.PessoaRepository;
import com.br.macros.repositories.PlanoRepository;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PlanoRepository planoRepository;

    private Date parseDataConsultaString(String dataString) {
        if (dataString == null || dataString.trim().isEmpty()) {
            throw new IllegalArgumentException("A string da data da consulta não pode ser nula ou vazia.");
        }
        String[] possibleDateFormats = {
            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", "yyyy-MM-dd'T'HH:mm:ssXXX",
            "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm",
            "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"
        };
        for (String format : possibleDateFormats) {
            try {
                return new SimpleDateFormat(format).parse(dataString);
            } catch (ParseException e) { /* Try next format */ }
        }
        throw new IllegalArgumentException("Formato de data inválido para dataConsulta: '" + dataString + "'. Use um formato ISO.");
    }

    @Transactional
    public Consulta saveConsulta(ConsultaRecordDto consultaRecordDto) {
        Consulta consulta = new Consulta();
        BeanUtils.copyProperties(consultaRecordDto, consulta, "planoId", "profissionalSaudeId", "dataConsulta");
        
        consulta.setDataConsulta(parseDataConsultaString(consultaRecordDto.dataConsulta()));

        Plano plano = planoRepository.findById(consultaRecordDto.planoId())
                .orElseThrow(() -> new IllegalArgumentException("Plano não encontrado com ID: " + consultaRecordDto.planoId()));
        consulta.setPlano(plano);

        Pessoa pessoaAsProfissional = pessoaRepository.findById(consultaRecordDto.profissionalSaudeId())
                .orElseThrow(() -> new IllegalArgumentException("Profissional de Saúde (Pessoa) não encontrado com ID: " + consultaRecordDto.profissionalSaudeId()));
        if (!(pessoaAsProfissional instanceof ProfissionalSaude)) {
             throw new IllegalArgumentException("A pessoa com ID " + consultaRecordDto.profissionalSaudeId() +
                                             " não é um Profissional de Saúde válido (tipo esperado: ProfissionalSaude).");
        }
        consulta.setProfissionalSaude((ProfissionalSaude) pessoaAsProfissional);

        return consultaRepository.save(consulta);
    }

    public List<Consulta> getAllConsultas() {
        return consultaRepository.findAll();
    }

    public Optional<Consulta> getOneConsulta(UUID id) {
        return consultaRepository.findById(id);
    }

    public List<Consulta> findConsultasByPlanoId(UUID planoId) {
        // Verifica se o plano existe antes de buscar as consultas
        if (!planoRepository.existsById(planoId)) {
            // Você pode lançar uma exceção ou retornar lista vazia
            // throw new RuntimeException("Plano não encontrado com ID: " + planoId);
            return Collections.emptyList(); // Retorna lista vazia se o plano não existe
        }
        return consultaRepository.findByPlano_Id(planoId);
    }
    
    @Transactional(readOnly = true)
    public List<Consulta> findConsultasByPacienteId(UUID pacienteId) {
        // 1. Buscar o Paciente (que é uma Pessoa)
        Pessoa pessoa = pessoaRepository.findById(pacienteId)
            .orElseThrow(() -> new RuntimeException("Pessoa (para busca de paciente) não encontrada com ID: " + pacienteId));
        
        if (!(pessoa instanceof Paciente)) {
            throw new RuntimeException("Pessoa com ID " + pacienteId + " não é um paciente.");
        }
        Paciente paciente = (Paciente) pessoa;

      
        Optional<Plano> optionalPlano = planoRepository.findByPacienteId(paciente.getId());

        // 3. Se o paciente não tiver um plano, ele não terá consultas associadas a um plano.
        if (optionalPlano.isEmpty()) {
            return Collections.emptyList(); // Ou new ArrayList<>()
        }

        Plano planoDoPaciente = optionalPlano.get();
        return consultaRepository.findByPlano_Id(planoDoPaciente.getId());
    }

    public List<Consulta> findConsultasByProfissionalSaudeId(UUID profissionalSaudeId) {
        Pessoa pessoaAsProfissional = pessoaRepository.findById(profissionalSaudeId)
                .orElseThrow(() -> new IllegalArgumentException("Profissional de Saúde (Pessoa) não encontrado com ID: " + profissionalSaudeId + " para busca de consultas."));
        if (!(pessoaAsProfissional instanceof ProfissionalSaude)) {
             throw new IllegalArgumentException("A pessoa com ID " + profissionalSaudeId + " não é um Profissional de Saúde (tipo esperado: ProfissionalSaude).");
        }
        return consultaRepository.findByProfissionalSaude_Id(profissionalSaudeId);
    }

    @Transactional
    public Consulta updateConsulta(UUID id, ConsultaRecordDto consultaRecordDto) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada com ID: " + id + " para atualização."));

        BeanUtils.copyProperties(consultaRecordDto, consulta, "id", "planoId", "profissionalSaudeId", "dataConsulta");
        consulta.setDataConsulta(parseDataConsultaString(consultaRecordDto.dataConsulta()));

        if (consultaRecordDto.planoId() != null && !consultaRecordDto.planoId().equals(consulta.getPlano().getId())) {
            Plano novoPlano = planoRepository.findById(consultaRecordDto.planoId())
                    .orElseThrow(() -> new IllegalArgumentException("Novo Plano não encontrado com ID: " + consultaRecordDto.planoId()));
            consulta.setPlano(novoPlano);
        }

        if (consultaRecordDto.profissionalSaudeId() != null && !consultaRecordDto.profissionalSaudeId().equals(consulta.getProfissionalSaude().getId())) {
            Pessoa novaPessoaAsProfissional = pessoaRepository.findById(consultaRecordDto.profissionalSaudeId())
                    .orElseThrow(() -> new IllegalArgumentException("Novo Profissional de Saúde (Pessoa) não encontrado com ID: " + consultaRecordDto.profissionalSaudeId()));
            if (!(novaPessoaAsProfissional instanceof ProfissionalSaude)) {
                 throw new IllegalArgumentException("A nova pessoa com ID " + consultaRecordDto.profissionalSaudeId() + " não é um Profissional de Saúde válido (tipo esperado: ProfissionalSaude).");
            }
            consulta.setProfissionalSaude((ProfissionalSaude) novaPessoaAsProfissional);
        }

        return consultaRepository.save(consulta);
    }

    @Transactional
    public boolean deleteConsulta(UUID id) {
        if (consultaRepository.existsById(id)) {
            consultaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
