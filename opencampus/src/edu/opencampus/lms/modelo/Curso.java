package edu.opencampus.lms.modelo;

import java.util.Collection;

public class Curso extends BaseModelo {

	private static final long serialVersionUID = 4082465449981861891L;

	private Integer idCurso;

	private String nombre;

	private Jerarquia jerarquia;

	private int estado;

	private Collection<Silabo> silabos;
	
	public Curso() {
	}

	public Curso(Integer idCurso) {
		this.idCurso = idCurso;
	}
	
	public Integer getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Jerarquia getJerarquia() {
		return jerarquia;
	}

	public void setJerarquia(Jerarquia jerarquia) {
		this.jerarquia = jerarquia;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Collection<Silabo> getSilabos() {
		return silabos;
	}

	public void setSilabos(Collection<Silabo> silabos) {
		this.silabos = silabos;
	}

	@Override
	public String toString() {
		StringBuffer cadena = new StringBuffer();
		cadena.append("\n\tID:"+this.idCurso);
		cadena.append("\n\tNombre:"+this.nombre);
		cadena.append("\n\tJerarquia:"+this.jerarquia);
		return cadena.toString();
	}

}