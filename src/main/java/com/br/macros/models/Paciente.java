package com.br.macros.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("Paciente")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Paciente extends Pessoa {

	private static final long serialVersionUID = 1L;
	
	@OneToOne(mappedBy = "paciente")
    private Plano plano;

	public Paciente() {
		super();
	}

	public Paciente( String nome, String sobrenome) {
		super( nome, sobrenome);
	}


}
