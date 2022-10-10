package com.pichincha.movimientos.exception;

public class SaldoNoDisponibleException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SaldoNoDisponibleException(String mensaje) {
		super(mensaje);
	}
}
