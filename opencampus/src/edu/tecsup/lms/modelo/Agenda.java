package edu.tecsup.lms.modelo;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Agenda implements Serializable{

	private static final long serialVersionUID = -7274769366565452397L;

	private int usuario;
	
	private GregorianCalendar fechaHora;
	
	private String sumilla;
	
	private String detalle;
	
	private int notificado;
	
	private int minutoAntes;
	
	public Agenda() {
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

	public GregorianCalendar getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(GregorianCalendar fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getSumilla() {
		return sumilla;
	}

	public void setSumilla(String sumilla) {
		this.sumilla = sumilla;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public int getNotificado() {
		return notificado;
	}

	public void setNotificado(int notificado) {
		this.notificado = notificado;
	}

	public int getMinutoAntes() {
		return minutoAntes;
	}

	public void setMinutoAntes(int minutoAntes) {
		this.minutoAntes = minutoAntes;
	}
}
