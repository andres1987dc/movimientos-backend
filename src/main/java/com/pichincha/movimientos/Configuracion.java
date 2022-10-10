package com.pichincha.movimientos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configuracion {

	@Value("${app.movimientos.clave-cupo-diario}")
	private String claveCupoDiario;
	
	@Value("${app.movimientos.debito}")
	private String debito;
	@Value("${app.movimientos.credito}")
	private String credito;

	public String getClaveCupoDiario() {
		return claveCupoDiario;
	}

	public void setClaveCupoDiario(String claveCupoDiario) {
		this.claveCupoDiario = claveCupoDiario;
	}

	public String getDebito() {
		return debito;
	}

	public void setDebito(String debito) {
		this.debito = debito;
	}

	public String getCredito() {
		return credito;
	}

	public void setCredito(String credito) {
		this.credito = credito;
	}
	
	

}
