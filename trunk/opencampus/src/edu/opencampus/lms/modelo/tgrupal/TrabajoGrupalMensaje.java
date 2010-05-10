package edu.opencampus.lms.modelo.tgrupal;

import edu.opencampus.lms.modelo.BaseModelo;
import edu.opencampus.lms.modelo.Matricula;

public class TrabajoGrupalMensaje extends BaseModelo {

	private static final long serialVersionUID = 809835873221438027L;

	private int idMensaje;

	private String archivoNombre;

	private String archivoTamanio;

	private String descripcion;

	private Matricula usuarioEmisor;

	public TrabajoGrupalMensaje() {
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
