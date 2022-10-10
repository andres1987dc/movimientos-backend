package com.pichincha.movimientos.exception;

public class PKDuplicadaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PKDuplicadaException(String mensaje) {
		super(mensaje);
	}
}
