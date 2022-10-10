package com.pichincha.movimientos.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pichincha.movimientos.Configuracion;
import com.pichincha.movimientos.dto.FiltroReporteMovimientosDTO;
import com.pichincha.movimientos.dto.ReporteMovimientosDTO;
import com.pichincha.movimientos.exception.CuentaNotFoundException;
import com.pichincha.movimientos.exception.CupoDiarioException;
import com.pichincha.movimientos.exception.SaldoNoDisponibleException;
import com.pichincha.movimientos.exception.ValidacionMovimientoException;
import com.pichincha.movimientos.model.Cuenta;
import com.pichincha.movimientos.model.Movimiento;
import com.pichincha.movimientos.model.Parametro;
import com.pichincha.movimientos.repo.ICuentaRepo;
import com.pichincha.movimientos.repo.IGenericRepo;
import com.pichincha.movimientos.repo.IMovimientoRepo;
import com.pichincha.movimientos.repo.IParametroRepo;
import com.pichincha.movimientos.service.IMovimientoService;
import com.pichincha.movimientos.util.MessageUtil;

@Service
public class MovimientoServiceImpl extends CRUDImpl<Movimiento, Long> implements IMovimientoService {

	@Autowired
	private IMovimientoRepo movimientoRepo;

	@Autowired
	private ICuentaRepo cuentaRepo;

	@Autowired
	private IParametroRepo parametroRepo;

	@Autowired
	private Configuracion configuracion;

	@Override
	protected IGenericRepo<Movimiento, Long> getRepo() {
		return movimientoRepo;
	}

	@Override
	public void validarMovimiento(Movimiento movimiento) throws ValidacionMovimientoException, Exception {
		Double monto = movimiento.getValor().doubleValue();
		if (0d == monto) {
			throw new ValidacionMovimientoException("EL VALOR DEL MOVIMIENTO NO PUEDE SER 0");
		}
	}

	@Override
	public void verificarSaldo(Movimiento movimiento)
			throws CuentaNotFoundException, SaldoNoDisponibleException, Exception {

		Cuenta cuenta = recuperarCuenta(movimiento.getCuenta().getNumero());
		Double saldoFinal = cuenta.getSaldoInicial().add(movimiento.getValor()).doubleValue();
		if (saldoFinal < 0) {
			throw new SaldoNoDisponibleException("SALDO NO DISPONIBLE");
		}
	}

	@Override
	public void verificarCupoDiario(Movimiento movimiento)
			throws CuentaNotFoundException, CupoDiarioException, Exception {

		Double monto = movimiento.getValor().doubleValue();
		if (monto < 0) {
			monto = monto * -1;			
			Cuenta cuenta = recuperarCuenta(movimiento.getCuenta().getNumero());
			Double totalDebitosDiarios = movimientoRepo.consultarTotalDebitosDiarios(cuenta.getNumero(),
					movimiento.getFecha());
			totalDebitosDiarios = totalDebitosDiarios != null ? totalDebitosDiarios : 0d;
			totalDebitosDiarios = totalDebitosDiarios + monto;
			Parametro parametro = parametroRepo.findOneByClave(configuracion.getClaveCupoDiario());
			Double maximoCupoDiario = Double.parseDouble(parametro.getValor());
			if (totalDebitosDiarios > maximoCupoDiario) {
				throw new CupoDiarioException("CUPO DIARIO EXCEDIDO");
			}
		}

	}

	@Transactional
	@Override
	public Movimiento ingresarMovimiento(Movimiento movimiento) throws CuentaNotFoundException, Exception {

		Cuenta cuenta = recuperarCuenta(movimiento.getCuenta().getNumero());
		BigDecimal saldoActualizado = cuenta.getSaldoInicial().add(movimiento.getValor());
		movimiento.setCuenta(cuenta);
		movimiento.setSaldo(saldoActualizado);
		String tipo = movimiento.getValor().doubleValue() > 0d ? configuracion.getCredito() : configuracion.getDebito();
		movimiento.setTipo(tipo);
		Movimiento movimientoIngresado = movimientoRepo.save(movimiento);
		cuenta.setSaldoInicial(saldoActualizado);
		cuentaRepo.save(cuenta);
		return movimientoIngresado;
	}

	@Override
	public List<ReporteMovimientosDTO> listarReporteMovimientos(FiltroReporteMovimientosDTO filtro) throws Exception {

		List<ReporteMovimientosDTO> movimientos = new ArrayList<>();
		movimientoRepo.listarReporteMovimientos(filtro.getIdUsuario(), filtro.getFechaInicio(), filtro.getFechaFin())
				.forEach(movimiento -> {
					ReporteMovimientosDTO reporteMovimientosDTO = new ReporteMovimientosDTO();
					reporteMovimientosDTO.setFecha(LocalDate.parse(String.valueOf(movimiento[0])));
					reporteMovimientosDTO.setCliente(String.valueOf(movimiento[1]));
					reporteMovimientosDTO.setNumeroCuenta(String.valueOf(movimiento[2]));
					reporteMovimientosDTO.setTipo(String.valueOf(movimiento[3]));
					reporteMovimientosDTO.setSaldoInicial(new BigDecimal(String.valueOf(movimiento[4])));
					reporteMovimientosDTO.setEstado(Boolean.valueOf(String.valueOf(movimiento[5])));
					reporteMovimientosDTO.setMovimiento(new BigDecimal(String.valueOf(movimiento[6])));
					reporteMovimientosDTO.setSaldoDisponible(new BigDecimal(String.valueOf(movimiento[7])));
					movimientos.add(reporteMovimientosDTO);
				});

		return movimientos;
	}

	private Cuenta recuperarCuenta(String numeroCuenta) throws CuentaNotFoundException {
		return cuentaRepo.findById(numeroCuenta).orElseThrow(
				() -> new CuentaNotFoundException(MessageUtil.generarMensaje("CUENTA NO ENCONTRADA ", numeroCuenta)));
	}

}
