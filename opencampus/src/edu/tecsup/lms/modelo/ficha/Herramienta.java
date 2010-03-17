package edu.tecsup.lms.modelo.ficha;

import java.util.GregorianCalendar;

public class Herramienta {
	
	String idHerramienta;
	String idFicha;
	GregorianCalendar fecha;
	
	public GregorianCalendar getFecha() {
		return fecha;
	}
	public void setFecha(GregorianCalendar fecha) {
		this.fecha = fecha;
	}
	public String getIdFicha() {
		return idFicha;
	}
	public void setIdFicha(String idFicha) {
		this.idFicha = idFicha;
	}
	public String getIdHerramienta() {
		return idHerramienta;
	}
	public void setIdHerramienta(String idHerramienta) {
		this.idHerramienta = idHerramienta;
	}
	
	
}
