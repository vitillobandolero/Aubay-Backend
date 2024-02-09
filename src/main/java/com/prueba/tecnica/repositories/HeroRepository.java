package com.prueba.tecnica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba.tecnica.models.HeroEntity;

@Repository
public interface HeroRepository extends JpaRepository<HeroEntity, Long> {
	
	List<HeroEntity> findByNameContainingIgnoreCase(String keyword);

}
