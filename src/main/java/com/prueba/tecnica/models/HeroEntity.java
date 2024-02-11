package com.prueba.tecnica.models;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/*
{    
    "name": "Superman",
    "birthDate": "1986-02-28",
    "vulnerability": "Kriptonita"
}
 */

@Entity
@Table(name = "superheroes")
public class HeroEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "HeroEntity [id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", vulnerability="
				+ vulnerability + "]";
	}

}
