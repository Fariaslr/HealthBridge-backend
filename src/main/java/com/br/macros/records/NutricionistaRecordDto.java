package com.br.macros.records;

import jakarta.validation.constraints.NotBlank;

public record NutricionistaRecordDto(
    @NotBlank String nome,
    @NotBlank String sobrenome,
    @NotBlank String email,
    @NotBlank String senha,
    @NotBlank String crn
) {}