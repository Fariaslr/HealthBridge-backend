package com.br.macros.models;

import java.io.Serializable;
import java.util.UUID;

import com.br.macros.enums.DivisaoTreino;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "execucao_exercicios")
public class ExecucaoExercicio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Enumerated(EnumType.STRING)
	private DivisaoTreino divisao;

	@ManyToOne
	@JoinColumn(name = "exercicio_id", nullable = false)
	private Exercicio exercicio;

	@ManyToOne
	@JoinColumn(name = "treino_id", nullable = false)
	private Treino treino;

	@Column(nullable = true)
    private int series;

    @Column(nullable = true)
    private Integer repeticoes;

    @Column(nullable = true)
    private float carga;

    @Column(nullable = true, name ="intervalo_serie")
    private Integer intervaloSerie;

    @Column(length = 300)
    private String observacao;

    private int ordem;

	public ExecucaoExercicio() {
	}

	public ExecucaoExercicio(Exercicio exercicio, DivisaoTreino divisao, int series, int repeticoes, float carga,
			int intervaloSerie) {
		this.exercicio = exercicio;
		this.divisao = divisao;
		this.series = series;
		this.repeticoes = repeticoes;
		this.carga = carga;
		this.intervaloSerie = intervaloSerie;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Exercicio getExercicio() {
		return exercicio;
	}

	public void setExercicio(Exercicio exercicio) {
		this.exercicio = exercicio;
	}

	public DivisaoTreino getDivisao() {
		return divisao;
	}

	public void setDivisao(DivisaoTreino divisao) {
		this.divisao = divisao;
	}

	public int getSeries() {
		return series;
	}

	public void setSeries(int series) {
		this.series = series;
	}

	public float getCarga() {
		return carga;
	}

	public void setCarga(float carga) {
		this.carga = carga;
	}

	public Integer getRepeticoes() {
		return repeticoes;
	}

	public void setRepeticoes(Integer repeticoes) {
		this.repeticoes = repeticoes;
	}

	public Integer getIntervaloSerie() {
		return intervaloSerie;
	}

	public void setIntervaloSerie(Integer intervaloSerie) {
		this.intervaloSerie = intervaloSerie;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Treino getTreino() {
		return treino;
	}

	public void setTreino(Treino treino) {
		this.treino = treino;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public String mostrarIntevalo() {
		return this.intervaloSerie / 60 + "min"
				+ (intervaloSerie % 60 == 0 ? "" : " e " + intervaloSerie % 60 + " seg");
	}

	@Override
	public String toString() {
		return "TreinoExercicio{" + "exercicio=" + exercicio + ", divisao=" + divisao + ", series=" + series
				+ ", repeticoes=" + repeticoes + ", carga=" + carga + ", intervaloSerie=" + intervaloSerie + '}';
	}
}
