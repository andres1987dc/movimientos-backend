package com.pichincha.movimientos.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pichincha.movimientos.exception.ModeloNotFoundException;
import com.pichincha.movimientos.exception.PKDuplicadaException;
import com.pichincha.movimientos.model.Cliente;
import com.pichincha.movimientos.service.IClienteService;
import com.pichincha.movimientos.util.MessageUtil;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	@Autowired
	private IClienteService service;

	@GetMapping
	public ResponseEntity<List<Cliente>> listar() throws Exception {
		return new ResponseEntity<>(service.listar(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> listarPorId(@PathVariable("id") String id) throws Exception {
		Cliente obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException(MessageUtil.generarMensaje("CLIENTE NO ENCONTRADO", id));
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Cliente> registrar(@Valid @RequestBody Cliente cliente) throws Exception {

		try {
			Cliente obj = service.listarPorId(cliente.getIdentificacion());
			if (obj != null) {
				throw new PKDuplicadaException(
						MessageUtil.generarMensaje("CLIENTE YA REGISTRADO", cliente.getIdentificacion()));
			}

			obj = service.registrar(cliente);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(obj.getIdentificacion()).toUri();
			return ResponseEntity.created(location).build();

		} catch (PKDuplicadaException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new Exception(MessageUtil.generarMensaje("ERROR AL REGISTAR CLIENTE", cliente.getIdentificacion()));
		}

	}

	@PutMapping
	public ResponseEntity<Cliente> modificar(@Valid @RequestBody Cliente cliente) throws Exception {

		try {

			Cliente obj = service.listarPorId(cliente.getIdentificacion());

			if (obj == null) {
				throw new ModeloNotFoundException(
						MessageUtil.generarMensaje("CLIENTE NO ENCONTRADO", cliente.getIdentificacion()));
			}

			return new ResponseEntity<>(service.modificar(cliente), HttpStatus.OK);
		} catch (Exception e) {
			throw new Exception(MessageUtil.generarMensaje("ERROR AL ACTUALIZAR CLIENTE", cliente.getIdentificacion()));

		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") String id) throws Exception {
		try {
			service.eliminar(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			throw new Exception(MessageUtil.generarMensaje("ERROR AL ACTUALIZAR CLIENTE", id));

		}
	}

}
