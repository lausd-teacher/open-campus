package edu.opencampus.lms.modelo.aulavirtual.reporte;

import java.util.GregorianCalendar;

public class MatriculaIngreso {

	private int cantidad;

	private GregorianCalendar fecha;

	public MatriculaIngreso() {
		super();
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public GregorianCalendar getFecha() {
		return fecha;
	}

	public void setFecha(GregorianCalendar fecha) {
		this.fecha = fecha;
	}

}