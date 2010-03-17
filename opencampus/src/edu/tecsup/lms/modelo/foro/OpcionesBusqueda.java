package edu.tecsup.lms.modelo.foro;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Set;

import edu.tecsup.lms.util.Formato;

public class OpcionesBusqueda  implements Serializable{

	private static final long serialVersionUID = -1501583008433187892L;
	
	private Set<Integer> misForos = null;
	private String[] tema;
	private String usuario;
	private boolean claveCompleta;
	private boolean soloTema;
	private GregorianCalendar fechaLimite;
	public Set<Integer> getMisForos() {
		return misForos;
	}
	public void setMisForos(Set<Integer> misForos) {
		this.misForos = misForos;
	}
	public String[] getTema() {
		return tema;
	}
	public void setTema(String[] tema) {
		this.tema = tema;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public boolean isClaveCompleta() {
		return claveCompleta;
	}
	public void setClaveCompleta(boolean claveCompleta) {
		this.claveCompleta = claveCompleta;
	}
	public boolean isSoloTema() {
		return soloTema;
	}
	public void setSoloTema(boolean soloTema) {
		this.soloTema = soloTema;
	}
	public GregorianCalendar getFechaLimite() {
		return fechaLimite;
	}
	public void setFechaLimite(GregorianCalendar fechaLimite) {
		this.fechaLimite = fechaLimite;
	}
	@Override
	public String toString() {
		String cadena = "Foros permitidos: \n";
		for (Integer n : misForos) {
			cadena += "\t"+ n + " - ";
		}
		cadena += "\n";
		cadena += "Por Tema: " +this.tema+ "\n";
		cadena += "Clave Completa: " +this.claveCompleta+ "\n";
		cadena += "Por Usuario: " +this.usuario+ "\n";
		cadena += "Solo sus temas: " +this.soloTema+ "\n";
		cadena += "Desde la fecha: " +Formato.setBaseDatosDeDate(this.fechaLimite) + "\n";
		
		return cadena;
	}
}