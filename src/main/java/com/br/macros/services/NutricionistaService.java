// src/main/java/com/br/macros/services/NutricionistaService.java
package com.br.macros.services;

import com.br.macros.models.Nutricionista;
import com.br.macros.records.NutricionistaRecordDto;
import com.br.macros.repositories.PessoaRepository; // Ainda usa o repositório da classe base
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId; // Para conversão de LocalDate para Date, se sua entidade Pessoa ainda usar Date
import java.util.Date;   // Para conversão de LocalDate para Date
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NutricionistaService {

    @Autowired
    private PessoaRepository pessoaRepository; // O repositório da classe base Pessoa

    public Nutricionista criarNutricionista(NutricionistaRecordDto dto) {
        // Validação: Verifique se o email já existe
        if (pessoaRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este email.");
        }
        
        // Validação específica: Verificar se o CRN já existe (se essa for uma regra de negócio)
        if (pessoaRepository.findByCrn(dto.crn()).isPresent()) {
            throw new IllegalArgumentException("Já existe um nutricionista cadastrado com este CRN.");
        }

        Nutricionista nutricionista = new Nutricionista();
        
        // Copia as propriedades do DTO para a entidade.
        // Garanta que os nomes dos campos no DTO e na entidade correspondam.
        BeanUtils.copyProperties(dto, nutricionista);

        // Se a entidade Pessoa/Nutricionista usa java.util.Date para dataNascimento,
        // e o DTO usa LocalDate, faça a conversão:
        // nutricionista.setDataNascimento(Date.from(dto.dataNascimento().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        // Garante que CREF seja nulo para Nutricionista (se a coluna existir devido à herança de ProfissionalSaude)
        // Isso depende da sua estrutura de herança e como o Hibernate mapeia.
        // Se 'cref' for um campo em ProfissionalSaude e você não quer que Nutricionista o tenha,
        // pode ser necessário sobrescrever ou garantir que seja nulo aqui.
        // nutricionista.setCref(null);

        // Ao salvar um Nutricionista, o Hibernate preencherá o 'dtype' automaticamente
        return pessoaRepository.save(nutricionista);
    }

    public List<Nutricionista> findAllNutricionistas() {
        // Usa o método do PessoaRepository que busca e retorna diretamente Nutricionistas
        // Certifique-se de adicionar @Query("SELECT n FROM Nutricionista n") List<Nutricionista> findAllNutricionistas(); no PessoaRepository
        return pessoaRepository.findAllNutricionistas();
    }

    public Nutricionista findById(UUID id) {
        // Busca a pessoa pelo ID e verifica se é um Nutricionista antes de retornar
        return pessoaRepository.findById(id)
                .map(pessoa -> {
                    if (pessoa instanceof Nutricionista) {
                        return (Nutricionista) pessoa;
                    }
                    throw new RuntimeException("O ID fornecido não pertence a um Nutricionista.");
                })
                .orElseThrow(() -> new RuntimeException("Nutricionista não encontrado."));
    }

    public Nutricionista atualizarNutricionista(UUID id, NutricionistaRecordDto dto) {
        Nutricionista nutricionistaExistente = findById(id); // Reusa o findById que já valida o tipo

        // Se você permitir a atualização do CRN, adicione validação de unicidade aqui também
        if (!nutricionistaExistente.getCrn().equals(dto.crn()) && pessoaRepository.findByCrn(dto.crn()).isPresent()) {
            throw new IllegalArgumentException("Já existe um nutricionista com este CRN.");
        }

        BeanUtils.copyProperties(dto, nutricionistaExistente, "id"); // Copia propriedades, excluindo o ID
        // Se sua entidade usa java.util.Date, converta novamente:
        // nutricionistaExistente.setDataNascimento(Date.from(dto.dataNascimento().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        return pessoaRepository.save(nutricionistaExistente);
    }

    public void deletarNutricionista(UUID id) {
        // Opcional: Adicione uma verificação findById para garantir que está deletando o tipo correto
        // findById(id); // Isso lançaria uma exceção se não fosse um Nutricionista
        pessoaRepository.deleteById(id);
    }
}