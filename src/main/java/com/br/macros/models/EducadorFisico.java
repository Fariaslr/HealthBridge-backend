package com.br.macros.models;

import java.util.Date;

import com.br.macros.enums.Sexo;
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

	public EducadorFisico(String cref, Date dataNascimento, String nome, String sobrenome, Sexo sexo) {
        super(dataNascimento, nome, sobrenome, sexo);
        this.cref = cref;
    }

	public String getCref() {
		return cref;
	}

	public void setCref(String cref) {
		this.cref = cref;
	}

}
