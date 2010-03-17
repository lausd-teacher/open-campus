package edu.tecsup.lms.modelo.correo;

import java.util.GregorianCalendar;

import edu.tecsup.lms.util.Formato;


public class Adjunto {
	
	String idAdjunto;
	String nombre;
	String idMensaje;
	long tamanio;
	GregorianCalendar fecha;
	String fechaAdjunto;

	public long getTamanio() {
		return tamanio;
	}
	public void setTamanio(long tamanio) {
		this.tamanio = tamanio;
	}
	public String getIdAdjunto() {
		return idAdjunto;
	}
	public void setIdAdjunto(String idAdjunto) {
		this.idAdjunto = idAdjunto;
	}	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getIdMensaje() {
		return idMensaje;
	}
	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje;
	}
	public GregorianCalendar getFecha() {
		return fecha;
	}
	public void setFecha(GregorianCalendar fecha) {		
		this.fecha = fecha;
	}
	public String getFechaAdjunto() {
		if(fecha == null)
				return "0000";
		fechaAdjunto = Formato.setBaseDatosDeDateCompleto(fecha);
		int mes=Integer.parseInt(fechaAdjunto.substring(3,5));
		String semestre;
		if(mes<=3){
			semestre="01";
		}else if(mes<=6){
			semestre="02";
		}else if(mes<=9){
			semestre="03";
		}else 
			semestre="04";
		return semestre+fechaAdjunto.substring(8,10);
	}
	public void setFechaAdjunto(String fechaAdjunto) {
		this.fechaAdjunto = fechaAdjunto;
	}

}
