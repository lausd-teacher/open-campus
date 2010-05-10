package edu.opencampus.lms.modelo.correo;

import java.io.Serializable;
import java.util.List;

public class Alerta implements Serializable{

	private static final long serialVersionUID = 1701130247315972953L;
	
	String idMensaje;
	String emisor;
	String destino;
	String titulo;
	String contenido;
	List<UsuarioCorreo> destinos;
	List<UsuarioCorreo> ccs;
	
	public String getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public List<UsuarioCorreo> getCcs() {
		return ccs;
	}

	public void setCcs(List<UsuarioCorreo> ccs) {
		this.ccs = ccs;
	}


	public List<UsuarioCorreo> getDestinos() {
		return destinos;
	}

	public void setDestinos(List<UsuarioCorreo> destinos) {
		this.destinos = destinos;
	}

	@Override
	public String toString() {
		return "Emisor: "+this.emisor+" Destino: "+this.destino+" Asunto: "+this.titulo;
	}

}