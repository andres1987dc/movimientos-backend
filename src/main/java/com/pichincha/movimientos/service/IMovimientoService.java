package com.pichincha.movimientos.service;

import java.util.List;

import com.pichincha.movimientos.dto.FiltroReporteMovimientosDTO;
import com.pichincha.movimientos.dto.ReporteMovimientosDTO;
import com.pichincha.movimientos.exception.CuentaNotFoundException;
import com.pichincha.movimientos.exception.CupoDiarioException;
import com.pichincha.movimientos.exception.SaldoNoDisponibleException;
import com.pichincha.movimientos.exception.ValidacionMovimientoException;
import com.pichincha.movimientos.model.Movimiento;

public interface IMovimientoService extends ICRUD<Movimiento, Long> {

	public void validarMovimiento(Movimiento movimiento)
			throws ValidacionMovimientoException, Exception;
	
	public void verificarSaldo(Movimiento movimiento)
			throws CuentaNotFoundException, SaldoNoDisponibleException, Exception;

	public void verificarCupoDiario(Movimiento movimiento)
			throws CuentaNotFoundException, CupoDiarioException, Exception;

	public Movimiento ingresarMovimiento(Movimiento movimiento) throws CuentaNotFoundException, Exception;

	public List<ReporteMovimientosDTO> listarReporteMovimientos(FiltroReporteMovimientosDTO filtro) throws Exception;

}
