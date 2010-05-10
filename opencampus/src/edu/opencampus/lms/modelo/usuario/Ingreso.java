package edu.opencampus.lms.modelo.usuario;

import java.io.Serializable;
import java.util.GregorianCalendar;

import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;

public class Ingreso implements Serializable {

	private static final long serialVersionUID = -3624955332685034226L;

	private Integer idIngreso;

	private Integer elemento;

	private String valor;
	
	private Usuario usuario;

	private GregorianCalendar fechaIngreso;

	private GregorianCalendar fechaSalida;
	
	public Ingreso(int elemento, Usuario usuario) {
		super();
		this.elemento = elemento;
		this.usuario = usuario;
	}
	
	public Ingreso(int elemento, Usuario usuario, String valor) {
		super();
		this.elemento = elemento;
		this.usuario = usuario;
		this.valor = valor;
	}

	public Integer getIdIngreso() {
		return idIngreso;
	}

	public void setIdIngreso(Integer idIngreso) {
		this.idIngreso = idIngreso;
	}

	public Integer getElemento() {
		return elemento;
	}

	public void setElemento(Integer elemento) {
		this.elemento = elemento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getFechaIngresoAsString() {
		return Formato.calendarToString(fechaIngreso, Constante.FECHA_DIA_MES_ANO_HORA_MI_SEG);
	}
	
	public GregorianCalendar getFechaIngreso() {
		return fechaIngreso;
	}
	
	public void setFechaIngreso(GregorianCalendar fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getFechaSalidaAsString() {
		return Formato.calendarToString(fechaSalida, Constante.FECHA_DIA_MES_ANO_HORA_MI_SEG);
	}
	
	public GregorianCalendar getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(GregorianCalendar fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "ID:"+idIngreso+" - ELEMENTO:"+elemento+" - VALOR:"+valor+" - USUARIO:"+usuario;
	}


}
