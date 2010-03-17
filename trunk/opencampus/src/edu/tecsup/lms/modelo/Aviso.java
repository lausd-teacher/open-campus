package edu.tecsup.lms.modelo;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Aviso extends BaseModelo implements Serializable {

	private static final long serialVersionUID = 7562458197561090431L;

	private Integer idAviso;
	
	private String titular;
	
	private String tipo;
	
	private GregorianCalendar fecha;
	
	private Boolean estado;

	public Integer getIdAviso() {
		return idAviso;
	}

	public void setIdAviso(Integer idAviso) {
		this.idAviso = idAviso;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public GregorianCalendar getFecha() {
		return fecha;
	}

	public void setFecha(GregorianCalendar fecha) {
		this.fecha = fecha;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	
	
	
}
