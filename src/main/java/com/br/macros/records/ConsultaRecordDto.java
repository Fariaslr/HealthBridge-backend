package com.br.macros.records;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;

public record ConsultaRecordDto(
    UUID planoId,

    UUID profissionalSaudeId,
    @NotNull(message = "A data da consulta é obrigatória.")
    String dataConsulta, 

    float peso, 
    float altura, 
    int numeroRefeicoes, 

    Float torax,
    Float abdomen,
    Float cintura,
    Float quadril,
    Float bracoEsquerdo,
    Float bracoDireito,
    Float antibracoEsquerdo,
    Float antibracoDireito,
    Float coxaEsquerda,
    Float coxaDireita,
    Float panturrilhaEsquerda,
    Float panturrilhaDireita,
    Float pescoco
) {
    
}
