package com.example.accessingdatamysql;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Pais {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String pais, moneda;

	public Pais() {//requerido por JPA
	}

	public Pais(String Pais, String Moneda) {
		this.pais = Pais;
		this.moneda = Moneda;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String Pais) {
		this.pais = Pais;
	}

	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String Moneda) {
		this.moneda = Moneda;
	}
}
