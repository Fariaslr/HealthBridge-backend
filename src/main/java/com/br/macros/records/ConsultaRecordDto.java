package com.br.macros.records;

import java.util.UUID;

public record ConsultaRecordDto(
    UUID planoId,
    UUID profissionalSaudeId,
    float peso, 
    float altura
) {
    
}
