package com.api.campanha.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.api.campanha.model.to.CampanhaTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CAMPANHA")
public class Campanha implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCampanha;

	@Column(name = "ID_TIME_CORACAO")
	private Long idTimeCoracao;

	@Column(name = "NOME_CAMPANHA")
	private String nomeCampanha;

	@Column(name = "DT_INICIO_VIGENCIA")
	private LocalDate dtInicioVigencia;

	@Column(name = "DT_FIM_VIGENCIA")
	private LocalDate dtFimVigencia;

	public static Campanha parse(CampanhaTO campanhaTO) {
		return Campanha.builder().idTimeCoracao(campanhaTO.getIdTimeCoracao())
				.nomeCampanha(campanhaTO.getNomeCampanha()).dtInicioVigencia(campanhaTO.getDtInicioVigencia())
				.dtFimVigencia(campanhaTO.getDtFimVigencia()).build();
	}
	
	public Campanha atualizar(Campanha campanha, CampanhaTO campanhaTO) {
		campanha.setIdTimeCoracao(campanhaTO.getIdTimeCoracao());
		campanha.setNomeCampanha(campanhaTO.getNomeCampanha());
		campanha.setDtInicioVigencia(campanhaTO.getDtInicioVigencia());
		campanha.setDtFimVigencia(campanhaTO.getDtFimVigencia());
		return campanha;
	}

}
