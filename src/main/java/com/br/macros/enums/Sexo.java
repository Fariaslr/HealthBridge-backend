package com.br.macros.enums;

public enum Sexo {
	MASCULINO("Masculino"), FEMININO("Feminino"), NAO_INFORMADO("Não Informado"),OUTRO("Outro");

	private final String SEXO;

	private Sexo(String SEXO) {
		this.SEXO = SEXO;
	}

	public String getSEXO() {
		return SEXO;
	}
}
