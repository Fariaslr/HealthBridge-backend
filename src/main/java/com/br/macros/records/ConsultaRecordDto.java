package com.br.macros.records;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.validation.constraints.Positive;

public record ConsultaRecordDto(
    UUID planoId,
    UUID profissionalSaudeId,
    @Positive Float peso, 
    @Positive Float altura,
    int numeroRefeicoes, 
    OffsetDateTime dataConsulta,
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
    Float pescoco,
    String observacoes
) {
}