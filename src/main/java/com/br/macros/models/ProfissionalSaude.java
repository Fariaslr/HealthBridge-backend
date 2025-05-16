package com.br.macros.models;

import java.util.Date;
import java.util.List;

import com.br.macros.enums.Sexo;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProfissionalSaude extends Pessoa {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "profissionalSaude", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Plano> planos;

	@OneToMany(mappedBy = "profissionalSaude", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Consulta> consultas;

	public ProfissionalSaude() {

	}

	public ProfissionalSaude(Date dataNascimento, String nome, String sobrenome, Sexo sexo) {
		super(dataNascimento, nome, sobrenome, sexo);
	}

	public List<Plano> getPlanos() {
		return planos;
	}

	public void setPlanos(List<Plano> planos) {
		this.planos = planos;
	}

	public List<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}	

}
