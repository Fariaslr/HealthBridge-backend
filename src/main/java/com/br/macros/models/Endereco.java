package com.br.macros.models;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cep;

	private String complemento;

	private int numero;

	public Endereco() {
	}

	public Endereco(String cep, String complemento, int numero) {
		this.cep = cep;
		this.complemento = complemento;
		this.numero = numero;
	}

	public Endereco(String cep) {
		this.cep = cep;

	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

}
