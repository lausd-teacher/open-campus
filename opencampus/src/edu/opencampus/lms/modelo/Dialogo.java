package edu.opencampus.lms.modelo;

import java.io.Serializable;

public class Dialogo implements Serializable {

	private static final long serialVersionUID = -3031382399443066794L;

	private int idDialogo;

	private int leido;

	private String nombreUsuario;

	private String texto;

	private String fecha;

	private int estado;

	private int owner;

	private int predecesor;

	private String asunto;

	private int cantidadSubPlactica = 0;

	private int cantidadNoSubPlacticas = 0;

	public Dialogo() {
	}

	public int getCantidadNoSubPlacticas() {
		return cantidadNoSubPlacticas;
	}

	public void setCantidadNoSubPlacticas(int cantidadNoSubPlacticas) {
		this.cantidadNoSubPlacticas = cantidadNoSubPlacticas;
	}

	public int getCantidadSubPlactica() {
		return cantidadSubPlactica;
	}

	public void setCantidadSubPlactica(int cantidadSubPlactica) {
		this.cantidadSubPlactica = cantidadSubPlactica;
	}

	public void updateCantidadSubPlactica() {
		this.cantidadSubPlactica++;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getIdDialogo() {
		return idDialogo;
	}

	public void setIdDialogo(int idDialogo) {
		this.idDialogo = idDialogo;
	}

	public int getLeido() {
		return leido;
	}

	public void setLeido(int leido) {
		this.leido = leido;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public int getPredecesor() {
		return predecesor;
	}

	public void setPredecesor(int predecesor) {
		this.predecesor = predecesor;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
