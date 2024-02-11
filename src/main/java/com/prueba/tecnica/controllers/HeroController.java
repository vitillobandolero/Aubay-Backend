package com.prueba.tecnica.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.tecnica.models.HeroEntity;
import com.prueba.tecnica.repositories.HeroRepository;

import Services.HeroService;

@RestController
@RequestMapping("/heroes")
public class HeroController {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	private HeroService service;
	
	public HeroController(HeroService service) {
        this.service = service;
    }

	
	@GetMapping // example http://localhost:8080/heroes?page=0&size=10
	public ResponseEntity<?> getHeros(Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getHeroById(@PathVariable Long id) {
		Optional<HeroEntity> optional = service.findById(id);
		HeroEntity hero = optional.orElse(null);

		return (hero != null) ? ResponseEntity.ok(hero)
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hero not found in the database");
	}

	@PostMapping("/create")
	public ResponseEntity<?> createHero(@RequestBody HeroEntity hero) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.create(hero));

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteHero(@PathVariable Long id) {

		Optional<HeroEntity> optional = service.findById(id);
		HeroEntity hero = optional.orElse(null);

		if (hero == null)
			return ResponseEntity.status(HttpStatus.OK).body("Hero not found in the database");

		service.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted hero with ID: " + id);

	}

	@GetMapping("/search") // http://localhost:8080/heroes/search?keyword=super&page=0&size=5
	public ResponseEntity<?> searchHeroesByName(@RequestParam String name) {
		
		List<HeroEntity> heroesList = service.search(name);
		
		return ResponseEntity.status(HttpStatus.OK).body(heroesList);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateHero(@RequestBody HeroEntity hero, @PathVariable Long id) {		
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.update(id, hero));
	}

}
