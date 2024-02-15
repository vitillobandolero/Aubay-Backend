package com.prueba.tecnica.models;

import java.io.Serializable;
import java.sql.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

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

	@Schema(description = "Identificador clave primaria héroes numérico")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Schema(example = "Spiderman", description = "Nombre del superhéroe")
	@NotEmpty
	private String name;

	@Schema(example = "2003-08-04", description = "Fecha de nacimiento del superhéroe")
	@Column(name = "birth_date")
	private Date birthDate;

	@Schema(example = "Insecticida", description = "Vulnerabilidad del superhéroe")
	@NotEmpty
	private String vulnerability;

	public HeroEntity() {
	}

	public HeroEntity(Long id, String name, Date birthDate, String vulnerability) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.vulnerability = vulnerability;
	}

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
