package com.br.macros.models;

import java.io.Serializable;
import java.util.*;
import com.br.macros.enums.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "planos")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Plano implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@OneToOne
	private Paciente paciente;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_plano")
	private Date dataPlano;

	@Enumerated(EnumType.STRING)
	private Objetivo objetivo;

	@Enumerated(EnumType.STRING)
	@Column(name = "nivel_atividade_fisica")
	private NivelAtividadeFisica nivelAtividadeFisica;

	@ManyToOne
	@JoinColumn(name = "profissional_saude_id", nullable = false)
	private ProfissionalSaude profissionalSaude;

	@OneToMany(mappedBy = "plano", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Consulta> consultas;


}
