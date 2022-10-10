package com.pichincha.movimientos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pichincha.movimientos.model.Cuenta;
import com.pichincha.movimientos.repo.ICuentaRepo;
import com.pichincha.movimientos.repo.IGenericRepo;
import com.pichincha.movimientos.service.ICuentaService;

@Service
public class CuentaServiceImpl extends CRUDImpl<Cuenta, String> implements ICuentaService {

	@Autowired
	private ICuentaRepo repo;

	@Override
	protected IGenericRepo<Cuenta, String> getRepo() {
		return repo;
	}

}
