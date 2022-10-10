package com.pichincha.movimientos.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pichincha.movimientos.dto.FiltroReporteMovimientosDTO;
import com.pichincha.movimientos.dto.ReporteMovimientosDTO;
import com.pichincha.movimientos.exception.CuentaNotFoundException;
import com.pichincha.movimientos.exception.CupoDiarioException;
import com.pichincha.movimientos.exception.ModeloNotFoundException;
import com.pichincha.movimientos.exception.SaldoNoDisponibleException;
import com.pichincha.movimientos.exception.ValidacionMovimientoException;
import com.pichincha.movimientos.model.Movimiento;
import com.pichincha.movimientos.service.IMovimientoService;
import com.pichincha.movimientos.util.MessageUtil;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

	@Autowired
	private IMovimientoService service;

	@GetMapping
	public ResponseEntity<List<Movimiento>> listar() throws Exception {
		return new ResponseEntity<>(service.listar(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Movimiento> listarPorId(@PathVariable("id") Long id) throws Exception {
		Movimiento obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException(MessageUtil.generarMensaje("MOVIMIENTO NO ENCONTRADO", id.toString()));
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Movimiento> registrar(@Valid @RequestBody Movimiento movimiento) throws Exception {

		try {
			service.validarMovimiento(movimiento);
			service.verificarSaldo(movimiento);
			service.verificarCupoDiario(movimiento);
			Movimiento obj = service.ingresarMovimiento(movimiento);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId())
					.toUri();
			return ResponseEntity.created(location).build();
		} catch (ValidacionMovimientoException | CuentaNotFoundException | SaldoNoDisponibleException
				| CupoDiarioException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(
					MessageUtil.generarMensaje("ERROR AL REGISTRAR MOVIMIENTO", movimiento.getCuenta().getNumero()));
		}
	}

	@PutMapping
	public ResponseEntity<Movimiento> modificar(@Valid @RequestBody Movimiento movimiento) throws Exception {
		try {
			return new ResponseEntity<>(service.modificar(movimiento), HttpStatus.OK);
		} catch (Exception e) {
			throw new Exception(
					MessageUtil.generarMensaje("ERROR AL ACTUALIZAR MOVIMIENTO", movimiento.getId().toString()));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) throws Exception {
		try {
			service.eliminar(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			throw new Exception(MessageUtil.generarMensaje("ERROR AL ELIMINAR MOVIMIENTO", id.toString()));
		}
	}

	@GetMapping("/buscar")
	public ResponseEntity<List<ReporteMovimientosDTO>> buscar(@RequestParam(value = "idCliente") String idCliente,
			@RequestParam(value = "fechaInicio") String fechaInicio, @RequestParam(value = "fechaFin") String fechaFin)
			throws Exception {

		try {
			List<ReporteMovimientosDTO> movimientos = new ArrayList<>();

			FiltroReporteMovimientosDTO filtro = new FiltroReporteMovimientosDTO(idCliente,
					LocalDate.parse(fechaInicio), LocalDate.parse(fechaFin));
			movimientos = service.listarReporteMovimientos(filtro);

			return new ResponseEntity<List<ReporteMovimientosDTO>>(movimientos, HttpStatus.OK);
		} catch (Exception e) {
			throw new Exception(MessageUtil.generarMensaje("ERROR AL GENERAR REPORTE DE MOVIMIENTOS", idCliente));
		}
	}

}
