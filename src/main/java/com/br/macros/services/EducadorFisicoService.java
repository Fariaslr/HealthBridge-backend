package com.br.macros.services;

import com.br.macros.models.EducadorFisico;
import com.br.macros.records.EducadorFisicoRecordDto;
import com.br.macros.repositories.PessoaRepository; 
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EducadorFisicoService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public EducadorFisico criarEducadorFisico(EducadorFisicoRecordDto dto) {
        if (pessoaRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este email.");
        }

        EducadorFisico educadorFisico = new EducadorFisico();
        BeanUtils.copyProperties(dto, educadorFisico);
        return pessoaRepository.save(educadorFisico);
    }

    public List<EducadorFisico> findAllEducadoresFisicos() {

        return pessoaRepository.findAllEducadoresFisicos();
    }

    public EducadorFisico findById(UUID id) {

        return pessoaRepository.findById(id)
                .map(pessoa -> {
                    if (pessoa instanceof EducadorFisico) {
                        return (EducadorFisico) pessoa;
                    }
                    throw new RuntimeException("O ID fornecido não pertence a um Educador Físico.");
                })
                .orElseThrow(() -> new RuntimeException("Educador Físico não encontrado."));
    }

    public EducadorFisico atualizarEducadorFisico(UUID id, EducadorFisicoRecordDto dto) {
        EducadorFisico educadorExistente = findById(id); 
        BeanUtils.copyProperties(dto, educadorExistente, "id"); 
        return pessoaRepository.save(educadorExistente);
    }

    public void deletarEducadorFisico(UUID id) {
        pessoaRepository.deleteById(id);
    }
}