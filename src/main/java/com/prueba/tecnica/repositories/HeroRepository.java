package com.prueba.tecnica.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba.tecnica.models.HeroEntity;

@Repository
public interface HeroRepository extends JpaRepository<HeroEntity, Long> {
	
	Page<HeroEntity> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

}
