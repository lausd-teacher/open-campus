package edu.opencampus.lms.modelo;

import java.util.GregorianCalendar;

import edu.opencampus.lms.modelo.tindividual.TrabajoIndividualInteraccion;

public class TrabajoIndividual extends BaseModelo {

	private static final long serialVersionUID = 3557104326962122560L;

	private int idTrabajo;

	private int idFicha;

	private int idUnidad;

	private String nombreUnidad;

	private GregorianCalendar fechaActivacion;

	private GregorianCalendar fechaEntrega;

	private TrabajoIndividualInteraccion interaccion;

	public TrabajoIndividual() {
	}

	public TrabajoIndividual(GregorianCalendar fechaCreacion,
			String usuarioCreacion, GregorianCalendar fechaModificacion,
			String usuarioModificacion, int idTrabajo, int idFicha,
			int idUnidad, String nombreUnidad,
			GregorianCalendar fechaActivacion, GregorianCalendar fechaEntrega,
			TrabajoIndividualInteraccion interaccion) {
		super(fechaCreacion, usuarioCreacion, fechaModificacion,
				usuarioModificacion);
		this.idTrabajo = idTrabajo;
		this.idFicha = idFicha;
		this.idUnidad = idUnidad;
		this.nombreUnidad = nombreUnidad;
		this.fechaActivacion = fechaActivacion;
		this.fechaEntrega = fechaEntrega;
		this.interaccion = interaccion;
	}

	public String getNombreUnidad() {
		return nombreUnidad;
	}

	public void setNombreUnidad(String nombreUnidad) {
		this.nombreUnidad = nombreUnidad;
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

	public TrabajoIndividualInteraccion getInteraccion() {
		return interaccion;
	}

	public void setInteraccion(TrabajoIndividualInteraccion interaccion) {
		this.interaccion = interaccion;
	}

}
