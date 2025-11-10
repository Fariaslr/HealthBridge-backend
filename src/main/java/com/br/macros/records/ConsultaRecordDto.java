package com.br.macros.records;

import java.util.Date;
import java.util.UUID;

public record ConsultaRecordDto(
    UUID planoId,
    UUID profissionalSaudeId,
    float peso, 
    float altura,
    int numeroRefeicoes, 
    Date dataCriacao,
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