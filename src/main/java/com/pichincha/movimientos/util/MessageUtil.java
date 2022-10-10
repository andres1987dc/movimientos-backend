package com.pichincha.movimientos.util;

public class MessageUtil {

	public static synchronized String generarMensaje(String mensaje, String id) {
		return new StringBuilder(mensaje).append(" ").append(id).toString();
	}

}
