package com.br.macros.models;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import com.br.macros.enums.TempoProjeto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

	@Column(name = "data_treino")
	private OffsetDateTime dataTreino;
	
	@Column
	private String nome;

	@OneToOne
	@JoinColumn(name = "consulta_id", nullable = false)
	private Consulta consulta;
	
	@Column(name = "data_criacao")
	private OffsetDateTime dataCriacao;
	
	@Column(name = "data_atualizacao")
	private OffsetDateTime dataAtualizacao;
	
	@Column(name = "profissional_saude_id", nullable = false)
    private UUID educadorFisico;

	@OneToMany(mappedBy = "treino", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ExecucaoExercicio> treinoExercicios;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "tempo_projeto", length = 30)
    private TempoProjeto tempo;
	
	@PrePersist
    protected void onCreate() {
        if (this.dataCriacao == null) {
            this.dataCriacao = OffsetDateTime.now(); 
        }
        if (this.dataAtualizacao == null) {
            this.dataAtualizacao = this.getDataCriacao();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = OffsetDateTime.now(); 
    }
	
    @JsonProperty("validadeProjeto")
    public OffsetDateTime calcularValidadeProjeto () {
    	if (this.dataTreino == null && this.tempo == null) {
    		return null;
    	} else {
    		return this.dataTreino.plusDays(tempo.getDIAS_PROJETO());
    	}	
    }
    
}
