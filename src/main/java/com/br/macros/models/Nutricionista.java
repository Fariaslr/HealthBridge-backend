package com.br.macros.models;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Nutricionista")
public class Nutricionista extends ProfissionalSaude {

	private static final long serialVersionUID = 1L;

	private String crn;

	public Nutricionista() {
	}

	public Nutricionista(String crn, String nome, String sobrenome) {
		super(nome, sobrenome);
		this.crn = crn;
	}

	public String getCrn() {
		return crn;
	}

	public void setCrn(String crn) {
		this.crn = crn;
	}
}
