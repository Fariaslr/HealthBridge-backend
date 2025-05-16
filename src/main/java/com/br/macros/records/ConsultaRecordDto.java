package com.br.macros.records;

import java.util.Date;

import com.br.macros.models.Plano;
import com.br.macros.models.ProfissionalSaude;

public record ConsultaRecordDto(Plano plano, Date dataConsulta, float peso, float altura, ProfissionalSaude profissionalSaude)
{

}
