package com.pichincha.movimientos.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Cliente extends Persona {
	@Column(name = "contrasena", nullable = false, length = 20)
	private String contrasena;
	@Column(name = "estado", nullable = false)
	private Boolean estado;

	public Cliente() {
		super();
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

}
