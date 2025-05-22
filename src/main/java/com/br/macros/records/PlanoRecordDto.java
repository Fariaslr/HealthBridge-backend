package com.br.macros.records;


import java.util.UUID;

import com.br.macros.enums.*;

public record PlanoRecordDto(
		UUID pacienteId,
		Objetivo objetivo,
		NivelAtividadeFisica nivelAtividadeFisica, 
		UUID profissionalSaudeId
		) {

}
