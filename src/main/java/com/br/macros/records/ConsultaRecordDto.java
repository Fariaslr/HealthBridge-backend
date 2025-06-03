package com.br.macros.records;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;

public record ConsultaRecordDto(
    UUID planoId,
    UUID profissionalSaudeId,
    float peso, 
    float altura
) {
    
}
