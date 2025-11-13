package com.br.macros.records;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.br.macros.enums.TempoProjeto;

public record TreinoRecordDto(
    OffsetDateTime dataTreino,
    UUID consultaId,
    UUID educadorFisico,
    List<ExecucaoExercicioRecordDto> treinoExercicios,
    TempoProjeto tempo
) {}
