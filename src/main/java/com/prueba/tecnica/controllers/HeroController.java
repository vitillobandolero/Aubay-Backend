package com.prueba.tecnica.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import Services.HeroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/heroes")
@Tag(name = "Heroes", description = "Superheroes API REST with CRUD operations")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HeroController {

	Logger log = LoggerFactory.getLogger(this.getClass());

	private HeroService service;

	public HeroController(HeroService service) {
		this.service = service;
	}

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retrieved superheroes", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = HeroEntity.class))) }) })

	@Operation(summary = "findAll heroes", description = "It retrieves all superheroes from database with pagination")
	@GetMapping // example http://localhost:8080/heroes?page=0&size=10
	public ResponseEntity<?> findAll(Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));

	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retrieve the superhero with the specified ID.", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = HeroEntity.class))) }),
			@ApiResponse(responseCode = "404", description = "Retrieve the message 'Hero not found in the database'") })

	@Operation(summary = "get Hero by id", description = "Retrieve the superhero with the specified ID")
	@GetMapping("/{id}")
	public ResponseEntity<?> getHeroById(@PathVariable Long id) {
		Optional<HeroEntity> optional = service.findById(id);
		HeroEntity hero = optional.orElse(null);

		return (hero != null) ? ResponseEntity.ok(hero)
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hero not found in the database");
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Saves the record of a superhero in the DB", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = HeroEntity.class))) }) })

	@Operation(summary = "create a Hero", description = "Saves the record of a superhero in the database")
	@PostMapping("/create")
	public ResponseEntity<?> createHero(@Valid @RequestBody HeroEntity hero, BindingResult result) {
		if(result.hasErrors()) return validate(result);
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.create(hero));

	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Deletes the record of a superhero from the database"),
			@ApiResponse(responseCode = "404", description = "Retrieve the message 'Hero not found in the database'") })

	@Operation(summary = "Delete a Hero by id", description = "Deletes the record of a superhero from the database")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteHero(@PathVariable Long id) {

		Optional<HeroEntity> optional = service.findById(id);
		HeroEntity hero = optional.orElse(null);

		if (hero == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hero not found in the database");

		service.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted hero with ID: " + id);

	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Receives a list of heroes that contain the specified word in their name"), })

	@Operation(summary = "Search the keyword in the name", description = "Receives a list of heroes that contain the specified word in their name")
	@GetMapping("/search") // http://localhost:8080/heroes/search?keyword=super&page=0&size=5
	public ResponseEntity<?> searchHeroesByName(@RequestParam String keyword) {

		List<HeroEntity> heroesList = service.search(keyword);

		return ResponseEntity.status(HttpStatus.OK).body(heroesList);
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Updates the record of a superhero from the database"),
			@ApiResponse(responseCode = "404", description = "Retrieve the message 'Hero not found in the database'") })

	@Operation(summary = "Update a Hero by id", description = "Updates the record of a superhero from the database")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateHero(@RequestBody HeroEntity hero, @PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.update(id, hero));
	}

	private ResponseEntity<?> validate(BindingResult result) {

		Map<String, Object> errors = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errors.put(err.getField(), "The field" + err.getField() + " " + err.getDefaultMessage());
		});

		return ResponseEntity.badRequest().body(errors);

	}

}
