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
import com.pichincha.movimientos.model.Cuenta;
import com.pichincha.movimientos.service.ICuentaService;
import com.pichincha.movimientos.util.MessageUtil;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

	@Autowired
	private ICuentaService service;

	@GetMapping
	public ResponseEntity<List<Cuenta>> listar() throws Exception {
		return new ResponseEntity<>(service.listar(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cuenta> listarPorId(@PathVariable("id") String id) throws Exception {
		Cuenta obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException(MessageUtil.generarMensaje("CUENTA NO ENCONTRADA", id));
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Cuenta> registrar(@Valid @RequestBody Cuenta cuenta) throws Exception {

		try {
			Cuenta obj = service.listarPorId(cuenta.getNumero());
			if (obj != null) {
				throw new PKDuplicadaException(MessageUtil.generarMensaje("CUENTA YA REGISTRADA", cuenta.getNumero()));
			}

			obj = service.registrar(cuenta);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(obj.getNumero()).toUri();
			return ResponseEntity.created(location).build();
		} 
		catch (PKDuplicadaException ex) {
			throw ex;
		}
		catch (Exception e) {
			throw new Exception(MessageUtil.generarMensaje("ERROR AL REGISTRAR CUENTA", cuenta.getNumero()));
		}
	}

	@PutMapping
	public ResponseEntity<Cuenta> modificar(@Valid @RequestBody Cuenta cuenta) throws Exception {
		try {

			Cuenta obj = service.listarPorId(cuenta.getNumero());

			if (obj == null) {
				throw new ModeloNotFoundException(
						MessageUtil.generarMensaje("CUENTA NO ENCONTRADA", cuenta.getNumero()));
			}

			return new ResponseEntity<>(service.modificar(cuenta), HttpStatus.OK);
		} catch (Exception e) {
			throw new Exception(MessageUtil.generarMensaje("ERROR AL ACTUALIZAR CUENTA", cuenta.getNumero()));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") String id) throws Exception {
		try {
			service.eliminar(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			throw new Exception(MessageUtil.generarMensaje("ERROR AL ELIMINAR CUENTA", id));
		}
	}

}
