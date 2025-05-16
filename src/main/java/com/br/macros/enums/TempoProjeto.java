package com.br.macros.enums;

public enum TempoProjeto {
	TRINTA(30), SESSENTA(60), CENTO_VINTE(120);

	private final int DIAS_PROJETO;

	private TempoProjeto(int DIAS_PROJETO) {
		this.DIAS_PROJETO = DIAS_PROJETO;
	}

	public int getDIAS_PROJETO() {
		return DIAS_PROJETO;
	}
}
