package com.pichincha.movimientos.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pichincha.movimientos.model.Movimiento;

public interface IMovimientoRepo extends IGenericRepo<Movimiento, Long> {

	@Query(value = "SELECT SUM(valor * -1) FROM movimiento WHERE id_cuenta = :numeroCuenta and fecha = :fechaMovimiento and upper(tipo) = 'DEBITO' and valor < 0", nativeQuery = true)
	Double consultarTotalDebitosDiarios(@Param("numeroCuenta") String numeroCuenta,
			@Param("fechaMovimiento") LocalDate fechaMovimiento);

	@Query(value = "select mo.fecha, cl.nombre, cu.numero, cu.tipo, (mo.saldo - mo.valor) saldo_inicial, cu.estado, mo.valor movimiento, mo.saldo saldo_disponible "
			+ "from cliente cl " + "inner join cuenta cu on cl.identificacion = cu.id_cliente "
			+ "inner join movimiento mo on cu.numero = mo.id_cuenta " + "where cl.identificacion = :idCliente "
			+ "and mo.fecha >= :fechaInicio " + "and mo.fecha <= :fechaFin "
			+ "order by mo.fecha desc ", nativeQuery = true)
	List<Object[]> listarReporteMovimientos(@Param("idCliente") String idCliente,
			@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

}
