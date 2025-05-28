package com.br.macros.records;

public record PacienteRecordDto(
    String nome,
    String sobrenome,
    String email,
    String senha
) {}