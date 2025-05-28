package com.br.macros.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("EducadorFisico")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class EducadorFisico extends ProfissionalSaude {

	private static final long serialVersionUID = 1L;

	private String cref;

	public EducadorFisico() {

	}

	public EducadorFisico(String cref, String nome, String sobrenome) {
		super(nome, sobrenome);
		this.cref = cref;
	}

	public String getCref() {
		return cref;
	}

	public void setCref(String cref) {
		this.cref = cref;
	}

}
