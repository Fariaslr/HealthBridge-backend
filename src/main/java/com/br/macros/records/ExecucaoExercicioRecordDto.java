package com.br.macros.records;

import com.br.macros.enums.DivisaoTreino;
import com.br.macros.models.Exercicio;

public record ExecucaoExercicioRecordDto(Exercicio exercicio, DivisaoTreino divisao, int series, int repeticoes, float carga,
		int intervaloSerie) {

}
