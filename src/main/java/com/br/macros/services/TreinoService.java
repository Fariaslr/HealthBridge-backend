package com.br.macros.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.macros.models.Consulta;
import com.br.macros.models.ExecucaoExercicio;
import com.br.macros.models.Exercicio;
import com.br.macros.models.Treino;
import com.br.macros.records.TreinoRecordDto;
import com.br.macros.repositories.ConsultaRepository;
import com.br.macros.repositories.ExercicioRepository;
import com.br.macros.repositories.TreinoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TreinoService {

    @Autowired
    private TreinoRepository treinoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ExercicioRepository exercicioRepository;

    public List<Treino> listarTodosOsTreinos() {
        return treinoRepository.findAll();
    }

    public Treino buscarTreinoPorId(UUID id) {
        return treinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Treino não encontrado"));
    }

    @Transactional
    public Treino criarTreino(TreinoRecordDto dto) {
        Consulta consulta = consultaRepository.findById(dto.consultaId())
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada"));

        if (consulta.getTreino() != null) {
            throw new IllegalStateException("Esta consulta já possui um treino cadastrado.");
        }

        Treino treino = new Treino();
        treino.setConsulta(consulta);
        treino.setEducadorFisico(dto.educadorFisico());
        treino.setTempo(dto.tempo());
        treino.setDataTreino(dto.dataTreino() != null
                ? dto.dataTreino()
                : OffsetDateTime.now());


        if (dto.treinoExercicios() != null && !dto.treinoExercicios().isEmpty()) {
            List<ExecucaoExercicio> execucoes = dto.treinoExercicios().stream().map(execDto -> {
                ExecucaoExercicio exec = new ExecucaoExercicio();

                Exercicio exercicio = exercicioRepository.findById(execDto.exercicioId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                "Exercício não encontrado: " + execDto.exercicioId()));

                exec.setTreino(treino);
                exec.setExercicio(exercicio);
                exec.setDivisao(execDto.divisao());
                exec.setSeries(execDto.series());
                exec.setRepeticoes(execDto.repeticoes());
                exec.setCarga(execDto.carga());
                exec.setIntervaloSerie(execDto.intervaloSerie());
                exec.setObservacao(execDto.observacao());
                exec.setOrdem(execDto.ordem());

                return exec;
            }).toList();

            treino.setTreinoExercicios(execucoes);
        }

        return treinoRepository.save(treino);
    }
    
    public List<Treino> buscarTreinosPorPacienteId(UUID pacienteId) {
        return treinoRepository.findByPacienteId(pacienteId);
    }

    @Transactional
    public Treino atualizarTreino(UUID id, TreinoRecordDto treinoDto) {
        Treino treinoExistente = buscarTreinoPorId(id);
        BeanUtils.copyProperties(treinoDto, treinoExistente, "id", "treinoExercicios", "consulta");
        return treinoRepository.save(treinoExistente);
    }

    public void deletarTreino(UUID id) {
        treinoRepository.deleteById(id);
    }

    public List<Treino> buscarTreinosPorProfissionalSaudeId(UUID profissionalSaudeId) {
        return treinoRepository.findByEducadorFisico(profissionalSaudeId);
    }
}
