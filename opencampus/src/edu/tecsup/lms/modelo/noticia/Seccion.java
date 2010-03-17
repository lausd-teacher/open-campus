package edu.tecsup.lms.modelo.noticia;

import java.io.Serializable;
import java.util.Collection;

import edu.tecsup.lms.modelo.Noticia;

public class Seccion implements Serializable {

	private static final long serialVersionUID = -4681398208930076163L;
	
	private int idSeccion;
	
	private String nombre;
	
	private int indice;
	
	private Collection<Noticia> noticias;
	
	public Seccion(){
	}
	
	public Seccion(int idSeccion) {
		this.idSeccion = idSeccion;
	}
	
	public Seccion(int idSeccion, String nombre, int indice) {
		this.idSeccion = idSeccion;
		this.nombre = nombre;
		this.indice = indice;
	}

	public int getIdSeccion() {
		return idSeccion;
	}

	public void setIdSeccion(int idSeccion) {
		this.idSeccion = idSeccion;
	}

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Collection<Noticia> getNoticias() {
		return noticias;
	}

	public void setNoticias(Collection<Noticia> noticias) {
		this.noticias = noticias;
	}
	
}
