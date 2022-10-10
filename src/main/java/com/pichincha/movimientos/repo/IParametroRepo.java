package com.pichincha.movimientos.repo;

import com.pichincha.movimientos.model.Parametro;

public interface IParametroRepo extends IGenericRepo<Parametro, Integer> {

	public Parametro findOneByClave(String clave);

}
