package edu.tecsup.lms.modelo.tgrupal;

import java.util.Collection;

import edu.tecsup.lms.modelo.BaseModelo;

public class TrabajoGrupalGrupo extends BaseModelo {

	private static final long serialVersionUID = 1L;
	
	private int idTrabajo;
	private int idGrupo;
	private String nombre;
	private String archivoNombre;
	private String archivoTamanio;
	private int bandera;
	private int banderaDebate;
	private int expirado;
	private int debates;
	private int estado;
	
	private Collection<TrabajoGrupalIntegrante> integrantes;
	private Collection<TrabajoGrupalMensaje> mensajes;
	
	public TrabajoGrupalGrupo() {
	}
	
	public int getBanderaDebate() {
		return banderaDebate;
	}

	public void setBanderaDebate(int banderaDebate) {
		this.banderaDebate = banderaDebate;
	}

	
	public int getDebates() {
		return debates;
	}

	public void setDebates(int debates) {
		this.debates = debates;
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

	public int getBandera() {
		return bandera;
	}

	public void setBandera(int bandera) {
		this.bandera = bandera;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getExpirado() {
		return expirado;
	}

	public void setExpirado(int expirado) {
		this.expirado = expirado;
	}

	public int getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}

	public int getIdTrabajo() {
		return idTrabajo;
	}

	public void setIdTrabajo(int idTrabajo) {
		this.idTrabajo = idTrabajo;
	}

	public Collection<TrabajoGrupalIntegrante> getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(Collection<TrabajoGrupalIntegrante> integrantes) {
		this.integrantes = integrantes;
	}

	public Collection<TrabajoGrupalMensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(Collection<TrabajoGrupalMensaje> mensajes) {
		this.mensajes = mensajes;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
