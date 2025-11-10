package com.br.macros.records;

import java.time.LocalDate;

import com.br.macros.enums.Sexo;
import com.fasterxml.jackson.annotation.JsonCreator;

public record PessoaRecordDto(
		String cpf,
	    String nome,
	    String sobrenome,
	    String telefone,
	    String email,
	    LocalDate dataNascimento,
	    Sexo sexo
	) {
	
	@JsonCreator
    public PessoaRecordDto(String cpf, String nome, String sobrenome, String telefone, String email, LocalDate dataNascimento, Sexo sexo) {
        this.cpf = cpf;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
    }
}
