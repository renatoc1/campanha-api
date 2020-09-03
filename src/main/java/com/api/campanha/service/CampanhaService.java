package com.api.campanha.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.api.campanha.exception.CampanhaException;
import com.api.campanha.model.Campanha;
import com.api.campanha.model.to.CampanhaTO;
import com.api.campanha.repository.CampanhaRepository;

@Service
public class CampanhaService {

	@Autowired
	private CampanhaRepository campanhaRepository;
	
	public Page<Campanha> buscaTodasCampanhas(Pageable pageable) {
		Page<Campanha> campanhas = campanhaRepository.findAll(pageable);
		if (null == campanhas || !campanhas.hasContent()) {
			throw new CampanhaException("Campanha nao encontrada", HttpStatus.NOT_FOUND);
		}
		List<Campanha> campanhasAtivas = campanhas.stream().filter(c -> c.getDtFimVigencia().compareTo(LocalDate.now()) > 0)
				.collect(Collectors.toList());
		return new PageImpl<>(campanhasAtivas);
	}

	public Campanha buscarCampanhaPorId(Long id) {
		return campanhaRepository.findById(id)
				.orElseThrow(() -> new CampanhaException("Campanha nao encontrada", HttpStatus.NOT_FOUND));
	}

	public List<Campanha> criaCampanha(CampanhaTO campanhaTO) {
		
		List<Campanha> campanhasAtualizadas = new ArrayList<Campanha>();
		List<Campanha> campanhas = campanhaRepository.buscarPorVigencia(campanhaTO.getDtInicioVigencia(),
				campanhaTO.getDtFimVigencia());
				
		if (null != campanhas && !campanhas.isEmpty()) {

			//Somar um dia no termino da vigencia de cada campanha ja existente
			campanhas.stream().forEach(c -> c.setDtFimVigencia(c.getDtFimVigencia().plusDays(1)));
			
			campanhasAtualizadas = atualizarVigencia(Campanha.parse(campanhaTO), campanhas, campanhasAtualizadas);
			campanhasAtualizadas.add(Campanha.parse(campanhaTO));
			campanhasAtualizadas.stream().forEach(c -> campanhaRepository.save(c));
			
			return campanhasAtualizadas;
			
		}
		
		campanhaRepository.save(Campanha.parse(campanhaTO));
		
		return campanhasAtualizadas;
		
	}
	
	private List<Campanha> atualizarVigencia(Campanha campanha, List<Campanha> campanhas, List<Campanha> campanhasAtualizadas) {
		for (Campanha c : campanhas) {
			if (c.getDtFimVigencia().compareTo(campanha.getDtFimVigencia()) == 0 && (null == campanha.getIdCampanha() || !campanha.getIdCampanha().equals(c.getIdCampanha()))) {
				c.setDtFimVigencia(c.getDtFimVigencia().plusDays(1));
				campanhasAtualizadas.add(c);
				atualizarVigencia(c, campanhas, campanhasAtualizadas);
			}
		}
		return campanhasAtualizadas;
	}
	
	public List<Campanha> atualizaCampanha(Long id, CampanhaTO campanhaTO) {
		
		List<Campanha> campanhasAtualizadas = new ArrayList<Campanha>();
		Campanha campanha = campanhaRepository.findById(id)
				.orElseThrow(() -> new CampanhaException("Campanha nao encontrada", HttpStatus.NOT_FOUND));
		campanha.atualizar(campanha, campanhaTO);
		List<Campanha> campanhas = campanhaRepository.buscarPorVigencia(campanhaTO.getDtInicioVigencia(),
				campanhaTO.getDtFimVigencia());

		if (null != campanhas && !campanhas.isEmpty()) {
			campanhasAtualizadas = atualizarVigencia(campanha, campanhas, campanhasAtualizadas);
			campanhasAtualizadas.add(campanha);
			campanhasAtualizadas.stream().forEach(c -> campanhaRepository.save(c));
			return campanhasAtualizadas;
		}
		
		campanhaRepository.save(Campanha.parse(campanhaTO));
		
		return campanhasAtualizadas;
	}

	public void deletaCampanha(Long id) {
		Campanha campanha = campanhaRepository.findById(id)
				.orElseThrow(() -> new CampanhaException("Campanha nao encontrada", HttpStatus.NOT_FOUND));
		campanhaRepository.delete(campanha);
	}

	public List<Campanha> buscarCampanhasPorIdTimeCoracao(Long id) {
		List<Campanha> campanhas = campanhaRepository.findByIdTimeCoracao(id);
		return campanhas.stream().filter(c -> c.getDtFimVigencia().compareTo(LocalDate.now()) > 0)
				.collect(Collectors.toList());
	}

}
