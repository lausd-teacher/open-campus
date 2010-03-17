package edu.tecsup.lms.modelo;

import java.util.GregorianCalendar;


public class TrabajoGrupal extends BaseModelo {

	private static final long serialVersionUID = -7918393788944428524L;

	private int idTrabajo;

	private int idFicha;

	private int idUnidad;

	private String nombreUnidad;

	private GregorianCalendar fechaActivacion;

	private GregorianCalendar fechaEntrega;

	private String descripcion;

	private Matricula publicador;

	public TrabajoGrupal() {
	}

	public Matricula getPublicador() {
		return publicador;
	}

	public void setPublicador(Matricula publicador) {
		this.publicador = publicador;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public GregorianCalendar getFechaActivacion() {
		return fechaActivacion;
	}

	public void setFechaActivacion(GregorianCalendar fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	public GregorianCalendar getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(GregorianCalendar fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public int getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(int idFicha) {
		this.idFicha = idFicha;
	}

	public int getIdTrabajo() {
		return idTrabajo;
	}

	public void setIdTrabajo(int idTrabajo) {
		this.idTrabajo = idTrabajo;
	}

	public int getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(int idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getNombreUnidad() {
		return nombreUnidad;
	}

	public void setNombreUnidad(String nombreUnidad) {
		this.nombreUnidad = nombreUnidad;
	}

}
