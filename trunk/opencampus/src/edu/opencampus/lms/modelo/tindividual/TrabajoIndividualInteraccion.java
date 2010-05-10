package edu.opencampus.lms.modelo.tindividual;

import java.util.GregorianCalendar;

import edu.opencampus.lms.modelo.BaseModelo;
import edu.opencampus.lms.modelo.Matricula;

public class TrabajoIndividualInteraccion extends BaseModelo {

	private static final long serialVersionUID = -6881708375168888538L;

	private int idMensaje;

	private String archivoNombre;

	private String archivoTamanio;

	private String descripcion;

	private Matricula usuarioEmisor;

	public TrabajoIndividualInteraccion() {
	}

	public TrabajoIndividualInteraccion(GregorianCalendar fechaCreacion,
			String usuarioCreacion, GregorianCalendar fechaModificacion,
			String usuarioModificacion, int idMensaje, String archivoNombre,
			String archivoTamanio, String descripcion,
			Matricula usuarioEmisor) {
		super(fechaCreacion, usuarioCreacion, fechaModificacion,
				usuarioModificacion);
		this.idMensaje = idMensaje;
		this.archivoNombre = archivoNombre;
		this.archivoTamanio = archivoTamanio;
		this.descripcion = descripcion;
		this.usuarioEmisor = usuarioEmisor;
	}

	public String getArchivoNombre() {
		return archivoNombre;
	}

	public void setArchivoNombre(String archivoNombre) {
		this.archivoNombre = archivoNombre;
	}

	public String getArchivoTamanio() {
		return archivoTamanio;
	}

	public void setArchivoTamanio(String archivoTamanio) {
		this.archivoTamanio = archivoTamanio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(int idMensaje) {
		this.idMensaje = idMensaje;
	}

	public Matricula getUsuarioEmisor() {
		return usuarioEmisor;
	}

	public void setUsuarioEmisor(Matricula usuarioEmisor) {
		this.usuarioEmisor = usuarioEmisor;
	}
}
