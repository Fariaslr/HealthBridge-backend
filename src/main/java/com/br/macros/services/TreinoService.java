package com.br.macros.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.macros.models.Treino;
import com.br.macros.records.TreinoRecordDto;
import com.br.macros.repositories.TreinoRepository;

import java.util.List;
import java.util.UUID;

@Service
public class TreinoService {
	
	@Autowired
    private TreinoRepository treinoRepository;

    public Treino adicionarTreino(TreinoRecordDto treinoDto) {
        Treino treino = new Treino();
        BeanUtils.copyProperties(treinoDto, treino);
        return treinoRepository.save(treino);
    }
    
    public List<Treino> listarTodosOsTreinos() {
        return treinoRepository.findAll();
    }

    public Treino buscarTreinoPorId(UUID id) {
        return treinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Treino não encontrado"));
    }

    public Treino atualizarTreino(UUID id, TreinoRecordDto treinoDto) {
        Treino treinoExistente = buscarTreinoPorId(id);
        BeanUtils.copyProperties(treinoDto, treinoExistente, "id");
        return treinoRepository.save(treinoExistente);
    }

    public void deletarTreino(UUID id) {
        treinoRepository.deleteById(id);
    }

    public List<Treino> buscarTreinosPorProfissionalSaudeId(UUID profissionalSaudeId) {
        return treinoRepository.findByEducadorFisico(profissionalSaudeId);
    }
}
