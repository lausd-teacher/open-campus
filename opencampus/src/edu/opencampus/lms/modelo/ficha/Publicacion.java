package edu.opencampus.lms.modelo.ficha;

import java.util.GregorianCalendar;

import edu.opencampus.lms.modelo.BaseModelo;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.util.Formato;

public class Publicacion extends BaseModelo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Integer idPublicacion;
	Integer idHerramienta;
	int idFicha;
	String titulo;
	String contenido;
	GregorianCalendar fecha;
	String archivoNombre;
	long archivoTamanio;
	int estado;
	int tipo;
	String creador;
	String fechaToString;
	Usuario usuario;
	String nombreCompleto;
	
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getCreador() {
		return creador;
	}
	public void setCreador(String creador) {
		this.creador = creador;
	}
	public String getArchivoNombre() {
		return archivoNombre;
	}
	public void setArchivoNombre(String archivoNombre) {		
		this.archivoNombre = archivoNombre;
	}
	public long getArchivoTamanio() {
		return archivoTamanio;
	}
	public void setArchivoTamanio(long archivoTamanio) {
		this.archivoTamanio = archivoTamanio;
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
	public GregorianCalendar getFecha() {
		return fecha;
	}
	public void setFecha(GregorianCalendar fecha) {
		this.fecha = fecha;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Integer getIdPublicacion() {
		return idPublicacion;
	}
	public int getIdFicha() {
		return idFicha;
	}
	public void setIdFicha(int idFicha) {
		this.idFicha = idFicha;
	}
	public Integer getIdHerramienta() {
		return idHerramienta;
	}
	public void setIdHerramienta(Integer idHerramienta) {
		this.idHerramienta = idHerramienta;
	}
	public void setIdPublicacion(Integer idPublicacion) {
		this.idPublicacion = idPublicacion;
	}
	
//	public String getFechaToString(){
//		return Formato.getStringDeDate(fecha);
//	}
	
	public String getFechaToString(){
		return Formato.setBaseDatosDeDateCompleto(fecha);
	}
	@Override
	public String toString() {
		return "ID:"+ idPublicacion+" TITULO:"+titulo;
	}
	
	

}