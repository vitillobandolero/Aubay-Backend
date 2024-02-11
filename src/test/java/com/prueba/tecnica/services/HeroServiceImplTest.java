package com.prueba.tecnica.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.prueba.tecnica.models.HeroEntity;
import com.prueba.tecnica.repositories.HeroRepository;

import Services.HeroService;
import Services.HeroServiceImpl;

@ExtendWith(MockitoExtension.class)
public class HeroServiceImplTest {

	@Mock
	private HeroRepository heroRepository;

	@InjectMocks
	private HeroService heroService = new HeroServiceImpl();

	@Test
	public void findAll() {

		Pageable pageable = PageRequest.of(0, 1);
		Page<HeroEntity> mockPage = mock(Page.class);
		when(heroRepository.findAll(pageable)).thenReturn(mockPage);

		Page<HeroEntity> result = heroService.findAll(pageable);

		assertEquals(mockPage, result);
		verify(heroRepository, times(1)).findAll(pageable);

	}

	@Test
	public void findByIdFound() {

		when(heroRepository.findById(1L))
				.thenReturn(Optional.of(new HeroEntity(1L, "Superman", Date.valueOf("1986-02-28"), "Kriptonita")));

		Optional<HeroEntity> optional = heroService.findById(1L);

		assertNotNull(optional);
		assertTrue(optional.isPresent());
		assertEquals("Superman", optional.get().getName());
		// ....

		verify(heroRepository, times(1)).findById(1L);

	}

	@Test
	public void findByIdNotFound() {

		when(heroRepository.findById(1L)).thenReturn(Optional.empty());

		Optional<HeroEntity> optional = heroService.findById(1L);

		assertFalse(optional.isPresent());
		// ....

		verify(heroRepository, times(1)).findById(1L);

	}

	@Test
	public void testCreate() {

		HeroEntity createdHero = new HeroEntity(1L, "Superman", Date.valueOf("1986-02-28"), "Kriptonita");

		when(heroRepository.save(createdHero)).thenReturn(createdHero);

		HeroEntity result = heroService.create(createdHero);

		assertEquals(createdHero, result);
	}

	@Test
	public void testDelete() {
		heroService.delete(1L);

		verify(heroRepository, times(1)).deleteById(1L);
	}

	@Test
	public void testSearch() {
		List<HeroEntity> mockHeroes = new ArrayList<>();
		when(heroRepository.findByNameContainingIgnoreCase("super")).thenReturn(mockHeroes);

		List<HeroEntity> result = heroService.search("super");

		assertEquals(mockHeroes, result);
		verify(heroRepository, times(1)).findByNameContainingIgnoreCase("super");
	}

}
