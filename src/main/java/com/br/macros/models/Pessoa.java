package com.br.macros.models;

import java.io.Serializable;
import java.time.*;
import java.util.*;

import org.springframework.hateoas.RepresentationModel;

import com.br.macros.enums.Sexo;


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

	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento")
	private Date dataNascimento;

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


	public int calcularIdade() {
		LocalDate birth = LocalDate.now();
		LocalDate nascimento = Instant.ofEpochMilli(dataNascimento.getTime()).atZone(ZoneId.systemDefault())
				.toLocalDate();
		Period periodo = Period.between(nascimento, birth);
		return periodo.getYears();
	}

	@Override
	public String toString() {
		return "Paciente [dataNascimento=" + dataNascimento + ", getId()=" + getId() + ", getCpf()=" + getCpf()
				+ ", getNome()=" + getNome() + ", getSobrenome()=" + getSobrenome() + ", getTelefone()=" + getTelefone()
				+ ", getEmail()=" + getEmail() + ", getSexo()=" + getSexo() + ", getEndereco()=" + getEndereco() + "]";
	}

}
