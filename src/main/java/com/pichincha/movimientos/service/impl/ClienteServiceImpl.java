package com.pichincha.movimientos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pichincha.movimientos.model.Cliente;
import com.pichincha.movimientos.repo.IClienteRepo;
import com.pichincha.movimientos.repo.IGenericRepo;
import com.pichincha.movimientos.service.IClienteService;

@Service
public class ClienteServiceImpl extends CRUDImpl<Cliente, String> implements IClienteService {

	@Autowired
	private IClienteRepo repo;

	@Override
	protected IGenericRepo<Cliente, String> getRepo() {
		return repo;
	}

}
