package com.br.macros.records;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.br.macros.enums.TempoProjeto;
import com.br.macros.models.*;

public record TreinoRecordDto(Date dataTreino, Consulta consulta, UUID educadorFisico, List<ExecucaoExercicio> treinoExercicios, TempoProjeto tempo) {

}
