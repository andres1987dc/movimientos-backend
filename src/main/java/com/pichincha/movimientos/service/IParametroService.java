package com.pichincha.movimientos.service;

import com.pichincha.movimientos.model.Parametro;

public interface IParametroService extends ICRUD<Parametro, Integer> {
	public Parametro findByClave(String clave) throws Exception;
}
