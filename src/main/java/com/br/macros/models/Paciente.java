package com.br.macros.models;

import java.util.Date;

import com.br.macros.enums.Sexo;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Paciente")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Paciente extends Pessoa {

	private static final long serialVersionUID = 1L;
	
	@OneToOne(mappedBy = "paciente")
	@JsonIgnore
    private Plano plano;

	public Paciente() {
		super();
	}

	public Paciente(Date dataNascimento, String nome, String sobrenome, Sexo sexo) {
		super(dataNascimento, nome, sobrenome, sexo);
	}

	public Plano getPlano() {
		return plano;
	}

	public void setPlano(Plano plano) {
		this.plano = plano;
	}

}
