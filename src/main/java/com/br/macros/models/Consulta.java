package com.br.macros.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "consultas")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Consulta extends RepresentationModel<Consulta> implements Serializable  {
	 private static final long serialVersionUID = 1L;

	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private UUID id;

	    @ManyToOne
	    @JoinColumn(name = "plano_id", nullable = false)
	    private Plano plano;

	    @OneToOne(mappedBy = "consulta")
	    @JsonIgnore
	    private Treino treino;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "dataconsulta")
	    private Date dataConsulta;

	    @ManyToOne
	    @JoinColumn(name = "profissional_saude_id", nullable = false)
	    private ProfissionalSaude profissionalSaude;

	    @Column(nullable = false)
	    private float peso;

	    @Column(nullable = false)
	    private float altura;

	    @Column(name = "numero_refeicoes")
	    private int numeroRefeicoes;
	    
	    private String observacoes;

	    @Column(name = "medida_torax", nullable = true)
	    private float torax;

	    @Column(name = "medida_abdomen", nullable = true)
	    private float abdomen;

	    @Column(name = "medida_cintura", nullable = true)
	    private float cintura;

	    @Column(name = "medida_quadril", nullable = true)
	    private float quadril;

	    @Column(name = "medida_braco_esquerdo", nullable = true)
	    private Float bracoEsquerdo;

	    @Column(name = "medida_braco_direito", nullable = true)
	    private Float bracoDireito;

	    @Column(name = "medida_antibraco_esquerdo", nullable = true)
	    private Float antibracoEsquerdo;

	    @Column(name = "medida_antibraco_direito", nullable = true)
	    private Float antibracoDireito;

	    @Column(name = "medida_coxa_esquerda", nullable = true)
	    private Float coxaEsquerda;

	    @Column(name = "medida_coxa_direita", nullable = true)
	    private Float coxaDireita;

	    @Column(name = "medida_panturrilha_esquerda", nullable = true)
	    private Float panturrilhaEsquerda;

	    @Column(name = "medida_panturrilha_direita", nullable = true)
	    private Float panturrilhaDireita;

	    @Column(name = "medida_pescoco", nullable = true)
	    private Float pescoco;


	    private float calcularTaxaMetabolicaBasal() {
	        return (float) (switch (plano.getPaciente().getSexo()) {
	            case MASCULINO ->
	                66.5 + (13.75 * getPeso()) + (5.003 * getAltura()) - (6.75 * plano.getPaciente().calcularIdade());
	            case FEMININO ->
	                655.1 + (9.563 * getPeso()) + (1.85 * getAltura()) - (4.676 * plano.getPaciente().calcularIdade());
	            default ->
	                0;
	        });
	    }

	    private float calcularGastoEnergeticoTotal() {
	        return calcularTaxaMetabolicaBasal() * plano.getNivelAtividadeFisica().getFATOR();
	    }

	    public float calcularCaloriasDieta() {
	        return switch (plano.getObjetivo()) {
	            case EMAGRECIMENTO ->
	                calcularGastoEnergeticoTotal() * 0.865f;
	            case HIPERTROFIA ->
	                calcularGastoEnergeticoTotal() * 1.223f;
	            default ->
	                calcularGastoEnergeticoTotal();
	        };
	    }

	    public double calcularPercentualGordura() {
	        if (cintura == 0 || pescoco == 0 || quadril == 0) {
	            return 0;
	        } else {
	            return (switch (plano.getPaciente().getSexo()) {
	                case MASCULINO ->
	                    8 + (495 / (1.033 - 0.191 * Math.log10(cintura - pescoco)
	                    + 0.155 * Math.log10(this.altura))) - 450;
	                case FEMININO ->
	                    (495 / (1.296 - 0.350 * Math.log10(quadril + cintura - pescoco)
	                    + 0.221 * Math.log10(this.altura))) - 450;
	                default ->
	                    0;
	            });
	        }
	    }

	    public float calcularAguaDiaria() {
	        return peso * 35;
	    }

	    public int calcularProteinas() {
	        return (int) (calcularGastoEnergeticoTotal() * 0.2f / 4);
	    }

	    public int calcularCarboidratos() {
	        return (int) (calcularGastoEnergeticoTotal() * 0.5f / 4);
	    }

	    public int calcularGorduras() {
	        return (int) (calcularGastoEnergeticoTotal() * 0.3f / 9);
	    }

	    public String getValorComMensagem(Float valor) {
	        return (valor == null || valor == 0) ? " - " : valor + " cm";
	    }

	    public String getMedidaBraço() {
	        if (this.bracoDireito == null && bracoEsquerdo == null) {
	            return " - ";
	        }
	        return (bracoDireito != null ? bracoDireito + " cm (D) " : "") + (bracoEsquerdo != null ? bracoEsquerdo + " cm (E)" : "");
	    }

	    public String getMedidaAntibraço() {
	        if (antibracoDireito == null && antibracoEsquerdo == null) {
	            return " - ";
	        }
	        return (antibracoDireito != null ? antibracoDireito + " cm (D) " : "") + (antibracoEsquerdo != null ? antibracoEsquerdo + " cm (E)" : "");
	    }

	    public String getMedidaCoxa() {
	        if (coxaDireita == null && coxaEsquerda == null) {
	            return " - ";
	        }
	        return (coxaDireita != null ? coxaDireita + " cm (D) " : "") + (coxaEsquerda != null ? coxaEsquerda + " cm (E)" : "");
	    }

	    public String getMedidaPanturrilha() {
	        if (panturrilhaDireita == null && panturrilhaEsquerda == null) {
	            return " - ";
	        }
	        return (panturrilhaDireita != null ? panturrilhaDireita + " cm (D) " : "") + (panturrilhaEsquerda != null ? panturrilhaEsquerda + " cm (E)" : "");
	    }

}
