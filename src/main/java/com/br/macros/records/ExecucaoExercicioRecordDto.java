package com.br.macros.records;

import java.util.UUID;

import com.br.macros.enums.DivisaoTreino;

public record ExecucaoExercicioRecordDto(
		UUID exercicioId,
	    DivisaoTreino divisao,
	    int series,
	    Integer repeticoes,
	    float carga,
	    Integer intervaloSerie,
	    String observacao,
	    int ordem    
		) {}
