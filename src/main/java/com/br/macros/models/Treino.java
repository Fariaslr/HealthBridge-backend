package com.br.macros.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import com.br.macros.enums.TempoProjeto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@Table(name = "treinos")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Treino extends RepresentationModel<Treino> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datatreino")
	private Date dataTreino;

	@OneToOne
	@JoinColumn(name = "consulta_id", nullable = false)
	private Consulta consulta;
	
	@Column(name = "profissional_saude_id", nullable = false)
    private UUID educadorFisico;

	@OneToMany(mappedBy = "treino", cascade = CascadeType.ALL)
	private List<ExecucaoExercicio> treinoExercicios;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "tempo_projeto", length = 30)
    private TempoProjeto tempo;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getDataTreino() {
		return dataTreino;
	}

	public void setDataTreino(Date dataTreino) {
		this.dataTreino = dataTreino;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public List<ExecucaoExercicio> getTreinoExercicios() {
		return treinoExercicios;
	}

	public void setTreinoExercicios(List<ExecucaoExercicio> treinoExercicios) {
		this.treinoExercicios = treinoExercicios;
	}

	public UUID getEducadorFisico() {
		return educadorFisico;
	}

	public void setEducadorFisico(UUID educadorFisico) {
		this.educadorFisico = educadorFisico;
	}
	
}
