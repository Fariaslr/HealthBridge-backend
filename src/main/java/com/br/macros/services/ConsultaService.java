package com.br.macros.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.macros.models.Consulta;
import com.br.macros.records.ConsultaRecordDto;
import com.br.macros.repositories.ConsultaRepository;

@Service
public class ConsultaService {
	
	@Autowired
    private ConsultaRepository consultaRepository;

    public Consulta saveConsulta(ConsultaRecordDto consultaRecordDto) {
        Consulta consulta = new Consulta();
        BeanUtils.copyProperties(consultaRecordDto, consulta);
        return consultaRepository.save(consulta);
    }

    public List<Consulta> getAllConsultas() {
        return consultaRepository.findAll();
    }

    public Optional<Consulta> getOneConsulta(UUID id) {
        return consultaRepository.findById(id);
    }

    public Consulta updateConsulta(UUID id, ConsultaRecordDto consultaRecordDto) {
        Optional<Consulta> consultaOptional = consultaRepository.findById(id);
        if (consultaOptional.isPresent()) {
            Consulta consulta = consultaOptional.get();
            BeanUtils.copyProperties(consultaRecordDto, consulta);
            return consultaRepository.save(consulta);
        }
        return null;
    }

    public boolean deleteConsulta(UUID id) {
        Optional<Consulta> consultaOptional = consultaRepository.findById(id);
        if (consultaOptional.isPresent()) {
            consultaRepository.delete(consultaOptional.get());
            return true;
        }
        return false;
    }
}
