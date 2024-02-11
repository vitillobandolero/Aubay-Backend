package com.prueba.tecnica.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.prueba.tecnica.models.HeroEntity;

import Services.HeroService;

public class HeroControllerTest {

	private HeroService heroService = mock(HeroService.class);
	private HeroController heroController = new HeroController(heroService);

	@Test
	public void getHeros() {
		
		List<HeroEntity> mockHeroes = Collections.singletonList(new HeroEntity());
		when(heroService.findAll(any(Pageable.class))).thenReturn(Page.empty());

		ResponseEntity<?> response = heroController.findAll(Pageable.unpaged());

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void getHeroByIdFound() {
		when(heroService.findById(1L)).thenReturn(Optional.of(new HeroEntity()));

		ResponseEntity<?> response = heroController.getHeroById(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void getHeroByIdNotFound() {
		
		when(heroService.findById(anyLong())).thenReturn(Optional.empty());

		ResponseEntity<?> response = heroController.getHeroById(1L);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void createHero() {
		
		HeroEntity heroToCreate = new HeroEntity();
		when(heroService.create(any())).thenReturn(heroToCreate);

		ResponseEntity<?> response = heroController.createHero(heroToCreate);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(heroToCreate, response.getBody());
	}

	@Test
	public void updateHero() {
		
		Long heroId = 1L;
		HeroEntity updatedHero = new HeroEntity();
		when(heroService.update(eq(heroId), any())).thenReturn(updatedHero);

		ResponseEntity<?> response = heroController.updateHero(updatedHero, heroId);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(updatedHero, response.getBody());
	}

	@Test
	public void deleteHero() {
		
		// Configurar el servicio para no devolver nada cuando se llame a delete
	    doNothing().when(heroService).delete(anyLong());

	    // Llamar al método que estás probando
	    ResponseEntity<?> response = heroController.deleteHero(1L);

	    // Imprimir el cuerpo de la respuesta para obtener más información
	    System.out.println("Response Body: " + response.getBody());

	    // Verificar que la respuesta sea un HttpStatus OK
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void searchHeroesByName() {
		
		String keyword = "super";
		List<HeroEntity> mockHeroes = Collections.singletonList(new HeroEntity());
		when(heroService.search(anyString())).thenReturn(mockHeroes);

		ResponseEntity<?> response = heroController.searchHeroesByName(keyword);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
