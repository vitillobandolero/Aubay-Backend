package com.prueba.tecnica.models;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "superheroes")
@SequenceGenerator(name = "superheroes_id_seq", sequenceName = "superheroes_id_seq", initialValue = 4)
public class HeroEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "superheroes_id_seq")
	private Long id;

	@Column(unique = true)
	private String name;

	@Column(name = "birth_date")
	private Date birthDate;

	private String vulnerability;
	
	public Long getId() {
		return id;
	}	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getVulnerability() {
		return vulnerability;
	}

	public void setVulnerability(String vulnerability) {
		this.vulnerability = vulnerability;
	}

}
