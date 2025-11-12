package com.br.macros.models;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import com.br.macros.enums.TempoProjeto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "treinos")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Treino extends RepresentationModel<Treino> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(name = "datatreino")
	private OffsetDateTime dataTreino;

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
	
}
