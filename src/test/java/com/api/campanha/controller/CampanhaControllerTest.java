package com.api.campanha.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.api.campanha.model.Campanha;
import com.api.campanha.model.to.CampanhaTO;
import com.api.campanha.service.CampanhaService;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class CampanhaControllerTest {

static final String CAMPANHA_API = "/campanhas";
	
	@Autowired
	private MockMvc mock;
	
	@MockBean
	private CampanhaService campanhaService;

	@Test
	@DisplayName("Deve buscar todas as campanhas paginadas")
	public void buscaTodasCampanhas() throws Exception {
		
		List<Campanha> campanhas = new ArrayList<Campanha>();
		Page<Campanha> campanhasPaginadas = new PageImpl<>(campanhas);
		Pageable pageable = PageRequest.of(0, 10);
		BDDMockito.given(campanhaService.buscaTodasCampanhas(pageable)).willReturn(campanhasPaginadas);
		String json = new ObjectMapper().writeValueAsString(pageable);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get(CAMPANHA_API)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);
			
			mock
				.perform(request)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
			
	}
	
	@Test
	@DisplayName("Deve buscar a campanha por id")
	public void buscaCampanhaPorId() throws Exception {
		
		BDDMockito.given(campanhaService.buscarCampanhaPorId(1L)).willReturn(Mockito.any(Campanha.class));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get(CAMPANHA_API.concat("/" + 1))
				.accept(MediaType.APPLICATION_JSON);
			
			mock
				.perform(request)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
			
	}
	
	@Test
	@DisplayName("Deve buscar campanhas por idTimeCoracao")
	public void buscaCampanhasPorIdTimeCoracao() throws Exception {
		
		BDDMockito.given(campanhaService.buscarCampanhasPorIdTimeCoracao(1L)).willReturn(Mockito.anyList());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get(CAMPANHA_API.concat("/" + 1))
				.accept(MediaType.APPLICATION_JSON);
			
			mock
				.perform(request)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
			
	}
	
	@Test
	@DisplayName("Deve criar nova campanha")
	public void criaCampanha() throws Exception {
		
		CampanhaTO campanhaTO = CampanhaTO.builder().idTimeCoracao(1L).nomeCampanha("Campanha 1").build();
		BDDMockito.given(campanhaService.criaCampanha(campanhaTO)).willReturn(Mockito.anyList());
		String json = new ObjectMapper().writeValueAsString(campanhaTO);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(CAMPANHA_API)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);
			
			mock
				.perform(request)
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn();
			
	}
	
	@Test
	@DisplayName("Deve deletar campanha")
	public void deletaCampanha() throws Exception {
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.delete(CAMPANHA_API.concat("/" + 1))
				.accept(MediaType.APPLICATION_JSON);
			
			mock
				.perform(request)
				.andExpect(MockMvcResultMatchers.status().isNoContent())
				.andReturn();
			
	}
	
}
