package com.api.campanha.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.campanha.model.Campanha;
import com.api.campanha.model.to.CampanhaTO;
import com.api.campanha.service.CampanhaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"Campanhas"})
@RestController
@RequestMapping("/campanhas")
public class CampanhaController {

	@Autowired
	private CampanhaService campanhaService;
	
	@ApiOperation(value = "Busca todas as campanhas paginadas")
	@GetMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Page<Campanha>> buscaTodasCampanhas(Pageable pageable) {
		
		Page<Campanha> campanhas = campanhaService.buscaTodasCampanhas(pageable);
		
		return new ResponseEntity<Page<Campanha>>(campanhas, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "Busca campanha por id")
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Campanha> buscaCampanhaPorId(@PathVariable("id") Long id) {
		
		Campanha campanha = campanhaService.buscarCampanhaPorId(id);
		
		return new ResponseEntity<Campanha>(campanha, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "Busca campanhas por idTimeCoracao")
	@GetMapping(value = "/timeCoracao/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Campanha>> buscaCampanhasPorIdTimeCoracao(@PathVariable("id") Long id) {
		
		List<Campanha> campanhas = campanhaService.buscarCampanhasPorIdTimeCoracao(id);
		
		return new ResponseEntity<List<Campanha>>(campanhas, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "Cria nova campanha")
	@PostMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Campanha>> criaCampanha(@RequestBody CampanhaTO campanhaTO) {
		
		List<Campanha> campanhas = campanhaService.criaCampanha(campanhaTO);
		
		return new ResponseEntity<List<Campanha>>(campanhas, HttpStatus.CREATED);
		
	}
	
	@ApiOperation(value = "Atualiza campanha")
	@PutMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Campanha>> atualizaCampanha(@PathVariable("id") Long id, @RequestBody CampanhaTO campanhaTO) {
		
		List<Campanha> campanhas = campanhaService.atualizaCampanha(id, campanhaTO);
		
		return new ResponseEntity<List<Campanha>>(campanhas, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "Deleta campanha")
	@DeleteMapping("/{id}")
	public ResponseEntity<Campanha> deletaCampanha(@PathVariable("id") Long id) {
		
		campanhaService.deletaCampanha(id);
		
		return new ResponseEntity<Campanha>(HttpStatus.NO_CONTENT);
		
	}
	
}
