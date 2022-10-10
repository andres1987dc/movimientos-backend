package com.pichincha.movimientos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pichincha.movimientos.model.Parametro;
import com.pichincha.movimientos.repo.IParametroRepo;
import com.pichincha.movimientos.repo.IGenericRepo;
import com.pichincha.movimientos.service.IParametroService;

@Service
public class ParametroServiceImpl extends CRUDImpl<Parametro, Integer> implements IParametroService {

	@Autowired
	private IParametroRepo repo;

	@Override
	protected IGenericRepo<Parametro, Integer> getRepo() {
		return repo;
	}

	@Override
	public Parametro findByClave(String clave) throws Exception {
		return repo.findOneByClave(clave);
	}

}
