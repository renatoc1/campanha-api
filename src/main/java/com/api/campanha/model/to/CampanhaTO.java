package com.api.campanha.model.to;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CampanhaTO {

	private Long idTimeCoracao;
	
	private String nomeCampanha;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dtInicioVigencia;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dtFimVigencia;
	
}
