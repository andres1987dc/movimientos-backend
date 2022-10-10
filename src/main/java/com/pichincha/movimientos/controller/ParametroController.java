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
import com.pichincha.movimientos.model.Parametro;
import com.pichincha.movimientos.service.IParametroService;
import com.pichincha.movimientos.util.MessageUtil;

@RestController
@RequestMapping("/api/parametros")
public class ParametroController {

	@Autowired
	private IParametroService service;

	@GetMapping
	public ResponseEntity<List<Parametro>> listar() throws Exception {
		return new ResponseEntity<>(service.listar(), HttpStatus.OK);
	}

	@GetMapping("/{clave}")
	public ResponseEntity<Parametro> listarPorId(@PathVariable("clave") String clave) throws Exception {
		Parametro obj = service.findByClave(clave);

		if (obj == null) {
			throw new ModeloNotFoundException(
					MessageUtil.generarMensaje("PARAMETRO NO ENCONTRADO NO ENCONTRADO", clave));
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Parametro> registrar(@Valid @RequestBody Parametro parametro) throws Exception {

		try {
			Parametro obj = service.findByClave(parametro.getClave());

			if (obj != null) {
				throw new PKDuplicadaException(
						MessageUtil.generarMensaje("PARAMETRO YA REGISTRADO", parametro.getClave()));
			}

			obj = service.registrar(parametro);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId())
					.toUri();
			return ResponseEntity.created(location).build();
		} catch (PKDuplicadaException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(MessageUtil.generarMensaje("ERROR AL REGISTRAR PARAMETRO", parametro.getClave()));
		}
	}

	@PutMapping
	public ResponseEntity<Parametro> modificar(@Valid @RequestBody Parametro parametro) throws Exception {
		try {
			Parametro parametroBD = service.findByClave(parametro.getClave());

			if (parametroBD == null) {
				throw new ModeloNotFoundException(
						MessageUtil.generarMensaje("PARAMETRO NO ENCONTRADO", parametro.getClave()));
			}

			parametroBD.setValor(parametro.getValor());
			return new ResponseEntity<>(service.modificar(parametroBD), HttpStatus.OK);
		} catch (Exception e) {
			throw new Exception(MessageUtil.generarMensaje("ERROR AL ACTUAIZAR PARAMETRO", parametro.getClave()));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		try {
			service.eliminar(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			throw new Exception(MessageUtil.generarMensaje("ERROR AL EILIMINAR PARAMETRO", id.toString()));
		}
	}

}
