package edu.tecsup.lms.modelo.correo;

import java.util.GregorianCalendar;

public class Carpeta {
	
	String idCarpeta;
	int idUsuario;
	String nombre;	
	GregorianCalendar fecha;
	String carpeta;
	
	public String getCarpeta() {
		return carpeta;
	}
	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}
	public GregorianCalendar getFecha() {
		return fecha;
	}
	public void setFecha(GregorianCalendar fecha) {
		this.fecha = fecha;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre.trim();
	}
	public String getIdCarpeta() {
		return idCarpeta;
	}
	public void setIdCarpeta(String idCarpeta) {
		this.idCarpeta = idCarpeta.trim();
	}

}
