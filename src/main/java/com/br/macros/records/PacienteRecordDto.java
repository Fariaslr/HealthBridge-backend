package com.br.macros.records;

import java.util.Date;
import com.br.macros.enums.Sexo;
public record PacienteRecordDto(
    String nome,
    String sobrenome,
    String email,
    String senha,
    String telefone,
    String cpf,
    Date dataNascimento,
    Sexo sexo
) {}