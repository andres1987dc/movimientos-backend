package com.pichincha.movimientos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Persona {

	@Id
	@Column(name = "identificacion", nullable = false, length = 13, unique = true)
	private String identificacion;
	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;
	@Column(name = "genero", nullable = false, length = 20)
	private String genero;
	@Column(name = "edad", nullable = false, length = 3)
	private Short edad;
	@Column(name = "direccion", nullable = false, length = 100)
	private String direccion;
	@Column(name = "telefono", nullable = false, length = 15)
	private String telefono;

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Short getEdad() {
		return edad;
	}

	public void setEdad(Short edad) {
		this.edad = edad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
