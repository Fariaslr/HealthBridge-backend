package com.br.macros.models;

import java.io.Serializable;
import java.time.*;
import java.util.*;

import org.springframework.hateoas.RepresentationModel;

import com.br.macros.enums.Sexo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pessoas")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Pessoa extends RepresentationModel<Pessoa> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String cpf;
	private String nome;
	private String sobrenome;
	private String telefone;
	private String usuario;
	private String email;
	
	private String senha;
	
	@Column(name = "dtype", insertable = false, updatable = false)
	private String tipoUsuario;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;

	@Enumerated(EnumType.STRING)
	private Sexo sexo;

	@Embedded
	private Endereco endereco;
	
	public Pessoa() {
	}

	public Pessoa(String nome, String sobrenome) {
		this.nome = nome;
		this.sobrenome = sobrenome;
	}

	@JsonProperty("idade")
	public int calcularIdade() {
		if (this.dataNascimento == null) {
	        return 0; 
	    }         

	    LocalDate dataAtual = LocalDate.now();
	    return Period.between(this.dataNascimento, dataAtual).getYears();
	}

	@Override
	public String toString() {
		return "Paciente [dataNascimento=" + dataNascimento + ", getId()=" + getId() + ", getCpf()=" + getCpf()
				+ ", getNome()=" + getNome() + ", getSobrenome()=" + getSobrenome() + ", getTelefone()=" + getTelefone()
				+ ", getEmail()=" + getEmail() + ", getSexo()=" + getSexo() + ", getEndereco()=" + getEndereco() + "]";
	}

}
