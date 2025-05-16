package com.br.macros.enums;

public enum GrupoMuscular {
    ABDOMEN("Abdômen"),
    BICEPS("Bíceps"),    
    COSTAS("Costas"),
    GLUTEOS("Glúteos"), 
    OMBROS("Ombros"),   
    PEITO("Peito"),    
    PERNAS("Pernas"),
    PANTURRILHAS("Panturrilhas"),    
    TRICEPS("Tríceps");

    private final String nome;

    GrupoMuscular(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return name();
    }

}
