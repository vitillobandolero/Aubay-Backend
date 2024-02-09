package com.prueba.tecnica.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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

@RestController
@RequestMapping("/api")
public class HeroController {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HeroRepository service;

	@GetMapping("/heros") // example http://localhost:8080/api/heros?page=0&size=10
	public ResponseEntity<?> getHeros(Pageable pageable) {
		Page<HeroEntity> page = service.findAll(pageable);

		return ResponseEntity.status(HttpStatus.OK).body(page.getContent());

	}

	@GetMapping("/heros/{id}")
	public ResponseEntity<?> getHeroById(@PathVariable Long id) {

		Optional<HeroEntity> optional = service.findById(id);
		HeroEntity hero = optional.orElse(null);

		return (hero != null) ? ResponseEntity.ok(hero)
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hero not found in the database");

	}

	@PostMapping("heros/create")
	public ResponseEntity<?> createHero(@RequestBody HeroEntity hero) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(hero));

	}

	@DeleteMapping("/heros/delete/{id}")
	public ResponseEntity<?> deleteHero(@PathVariable Long id) {

		Optional<HeroEntity> optional = service.findById(id);
		HeroEntity hero = optional.orElse(null);

		if (hero == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hero not found in the database");

		service.delete(hero);
		return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted hero with ID: " + id);

	}

	@PutMapping("/heros/update/{id}")
	public ResponseEntity<?> updateHero(@RequestBody HeroEntity hero, @PathVariable Long id) {

		Optional<HeroEntity> optional = this.service.findById(id);

		if (optional.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hero not found in the database");

		HeroEntity heroDTO = optional.get();

		BeanUtils.copyProperties(hero, heroDTO, "id");

		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(heroDTO));
	}
 
	@GetMapping("/heroes/search") // http://localhost:8080/api/heroes/search?keyword=super&page=0&size=5
	public ResponseEntity<?> searchHeroesByName(@RequestParam String keyword, Pageable pageable) {
	    
		Page<HeroEntity> page = service.findByNameContainingIgnoreCase(keyword, pageable);

	    if (page.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Heroes not found in the database");
	    }

	    return ResponseEntity.ok(page.getContent());
	}

}
