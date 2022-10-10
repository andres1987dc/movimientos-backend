package com.pichincha.movimientos.dto;

import java.time.LocalDate;

public class FiltroReporteMovimientosDTO {
	private String idUsuario;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;

	public FiltroReporteMovimientosDTO() {
	}

	public FiltroReporteMovimientosDTO(String idUsuario, LocalDate fechaInicio, LocalDate fechaFin) {
		this.idUsuario = idUsuario;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

}
