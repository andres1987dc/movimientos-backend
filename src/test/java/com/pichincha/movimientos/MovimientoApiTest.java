package com.pichincha.movimientos;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.pichincha.movimientos.model.Cliente;
import com.pichincha.movimientos.model.Cuenta;
import com.pichincha.movimientos.model.Movimiento;

public class MovimientoApiTest extends AbstractTest {

	private static final String ENDPOINT_CLIENTES = "/api/clientes";
	private static final String ENDPOINT_CUENTAS = "/api/cuentas";
	private static final String ENDPOINT_MOVIMIENTOS = "/api/movimientos";

	private Cliente cliente;
	private Cuenta cuenta;
	private Movimiento movimiento;
	

	private void initData() {
		cliente = new Cliente();
		cliente.setIdentificacion("11111");
		cliente.setNombre("Jose Lema");
		cliente.setGenero("Masculino");
		cliente.setEdad(Short.valueOf("20"));
		cliente.setDireccion("Otavalo sn y principal");
		cliente.setTelefono("098254785");
		cliente.setContrasena("1234");
		cliente.setEstado(true);

		cuenta = new Cuenta();
		cuenta.setCliente(cliente);
		cuenta.setEstado(true);
		cuenta.setNumero("9999999");
		cuenta.setSaldoInicial(new BigDecimal(500));
		cuenta.setTipo("AHORROS");

		movimiento = new Movimiento();
		movimiento.setCuenta(cuenta);
		movimiento.setFecha(LocalDate.now());
		movimiento.setTipo("DEBITO");
		movimiento.setValor(new BigDecimal(-200));

	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		initData();
	}

	@Test()
	public void test1() throws Exception {
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.get(ENDPOINT_CLIENTES).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

	}

	@Test
	public void test2() throws Exception {

		String inputJson = super.mapToJson(cliente);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ENDPOINT_CLIENTES)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
	}

	@Test
	public void test3() throws Exception {
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.get(ENDPOINT_CUENTAS).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

	}

	@Test
	public void test4() throws Exception {

		String inputJson = super.mapToJson(cuenta);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ENDPOINT_CUENTAS)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);

	}

	@Test
	public void test5() throws Exception {

		String inputJson = super.mapToJson(movimiento);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ENDPOINT_MOVIMIENTOS)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();

		String content = mvcResult.getResponse().getHeader("Location");
		String idMovimiento = content.substring(content.lastIndexOf('/') + 1);
		assertEquals(201, status);
		
		String fecha = LocalDate.now().toString();
		String endpoint = ENDPOINT_MOVIMIENTOS + "/buscar?idCliente=" + cliente.getIdentificacion() + "&fechaInicio="
				+ fecha + "&fechaFin=" + fecha;
		mvcResult = mvc.perform(MockMvcRequestBuilders.get(endpoint).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.delete(ENDPOINT_MOVIMIENTOS.concat("/").concat(idMovimiento))
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(204, status);
		
		

	}


	@Test
	public void test6() throws Exception {

		

		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(ENDPOINT_CUENTAS.concat("/").concat(cuenta.getNumero()))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(204, status);

		mvcResult = mvc.perform(
				MockMvcRequestBuilders.delete(ENDPOINT_CLIENTES.concat("/").concat(cliente.getIdentificacion()))
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		status = mvcResult.getResponse().getStatus();
		assertEquals(204, status);

	}

}