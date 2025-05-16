package com.br.macros.records;

import com.br.macros.enums.GrupoMuscular;

public record ExercicioRecordDto(String nome, String descricao, GrupoMuscular grupoMuscular, String urlFoto) {

}
