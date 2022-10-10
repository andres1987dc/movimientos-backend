package com.pichincha.movimientos.exception;

public class CuentaNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CuentaNotFoundException(String mensaje) {
		super(mensaje);
	}
}
