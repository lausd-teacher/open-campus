package edu.opencampus.lms.modelo.correo;

import java.util.GregorianCalendar;
import java.util.List;

import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;

public class Mensaje {

	String idMensaje;
	String usuario;
	String titulo;
	String contenido;
	GregorianCalendar fecha_envio;
	GregorianCalendar fecha_leido;
	int estado;// enviado, guardado
	String carpeta;
	int adjunto;
	String usuario_envio;
	String fechaToStringEnvio;
	String fechaToStringLeido;
	List<Adjunto> adjuntos;
	int favorito;
	List<UsuarioCorreo> destinos;
	String tipo;
	String idCarpeta;
	List<UsuarioCorreo> ccs;
	UsuarioCorreo remitente;
	String oldCarpeta;
	String oldIdCarpeta;
	int leido;// leido - no leido

	public int getLeido() {
		return leido;
	}

	public void setLeido(int leido) {
		this.leido = leido;
	}

	public String getOldCarpeta() {
		return oldCarpeta;
	}

	public void setOldCarpeta(String oldCarpeta) {
		this.oldCarpeta = oldCarpeta;
	}

	public UsuarioCorreo getRemitente() {
		return remitente;
	}

	public void setRemitente(UsuarioCorreo remitente) {
		this.remitente = remitente;
	}

	public List<UsuarioCorreo> getCcs() {
		return ccs;
	}

	public void setCcs(List<UsuarioCorreo> ccs) {
		this.ccs = ccs;
	}

	public String getIdCarpeta() {
		return idCarpeta;
	}

	public void setIdCarpeta(String idCarpeta) {
		this.idCarpeta = idCarpeta;
	}

	public List<UsuarioCorreo> getDestinos() {
		return destinos;
	}

	public void setDestinos(List<UsuarioCorreo> destinos) {
		this.destinos = destinos;
	}

	public int getFavorito() {
		return favorito;
	}

	public void setFavorito(int favorito) {
		this.favorito = favorito;
	}

	public List<Adjunto> getAdjuntos() {
		return adjuntos;
	}

	public void setAdjuntos(List<Adjunto> adjuntos) {
		this.adjuntos = adjuntos;
	}

	public String getUsuario_envio() {
		return usuario_envio;
	}

	public void setUsuario_envio(String usuario_envio) {
		this.usuario_envio = usuario_envio;
	}

	public int getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(int adjunto) {
		this.adjunto = adjunto;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public GregorianCalendar getFecha_envio() {
		return fecha_envio;
	}

	public void setFecha_envio(GregorianCalendar fecha_envio) {
		this.fecha_envio = fecha_envio;
	}

	public String getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje.trim();
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCarpeta() {
		return carpeta;
	}

	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}

	// hacer otro para mostrar en formato : 05-Jul
	public String getFechaToStringEnvio() {
		return Formato.calendarToString(fecha_envio,Constante.FECHA_DIA_MES_ANO);
	}

	public String getFechaToStringLeido() {
		return Formato.calendarToString(fecha_leido,Constante.FECHA_DIA_MES_ANO);
	}

	public GregorianCalendar getFecha_leido() {
		return fecha_leido;
	}

	public void setFecha_leido(GregorianCalendar fecha_leido) {
		this.fecha_leido = fecha_leido;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getOldIdCarpeta() {
		return oldIdCarpeta;
	}

	public void setOldIdCarpeta(String oldIdCarpeta) {
		this.oldIdCarpeta = oldIdCarpeta;
	}

}