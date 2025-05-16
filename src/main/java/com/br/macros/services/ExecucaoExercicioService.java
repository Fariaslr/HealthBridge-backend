package com.br.macros.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.macros.models.ExecucaoExercicio;
import com.br.macros.records.ExecucaoExercicioRecordDto;
import com.br.macros.repositories.ExecucaoExercicioRepository;

@Service
public class ExecucaoExercicioService {

	@Autowired
	private ExecucaoExercicioRepository execucaoExercicioRepository;

	public ExecucaoExercicio create(ExecucaoExercicioRecordDto execucaoExercicioRecordDto) {
		ExecucaoExercicio execucaoExercicio = new ExecucaoExercicio();
		BeanUtils.copyProperties(execucaoExercicioRecordDto, execucaoExercicio);
		return execucaoExercicioRepository.save(execucaoExercicio);
	}

	public List<ExecucaoExercicio> read(UUID treinoId, String termoBusca) {
		return execucaoExercicioRepository.findByTreinoIdAndTermoBusca(treinoId, termoBusca);
	}

	public ExecucaoExercicio update(UUID id, ExecucaoExercicioRecordDto execucaoExercicioRecordDto) {
		Optional<ExecucaoExercicio> optionalExecucao = execucaoExercicioRepository.findById(id);
		if (optionalExecucao.isPresent()) {
			ExecucaoExercicio execucao = optionalExecucao.get();
			BeanUtils.copyProperties(execucaoExercicioRecordDto, execucao);
			return execucaoExercicioRepository.save(execucao);
		}
		return null;
	}

	public void delete(UUID id) {
		execucaoExercicioRepository.deleteById(id);
	}
}
