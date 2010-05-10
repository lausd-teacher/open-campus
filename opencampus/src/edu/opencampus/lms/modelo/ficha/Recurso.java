package edu.opencampus.lms.modelo.ficha;

import edu.opencampus.lms.modelo.BaseModelo;

public class Recurso extends BaseModelo {

	private static final long serialVersionUID = 6909709914712993434L;

	private Integer idRecurso;

	private String nombre;
	
	private String nombreCorto;

	private int estado;

	private Integer peso;

	private int calificado;

	private int estadoDocente;

	private int estadoAlumno;

	public Recurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
	}

	public Recurso() {
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public Integer getIdRecurso() {
		return idRecurso;
	}

	public void setIdRecurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public int getCalificado() {
		return calificado;
	}

	public void setCalificado(int calificado) {
		this.calificado = calificado;
	}

	public int getEstadoDocente() {
		return estadoDocente;
	}

	public void setEstadoDocente(int estadoDocente) {
		this.estadoDocente = estadoDocente;
	}

	public int getEstadoAlumno() {
		return estadoAlumno;
	}

	public void setEstadoAlumno(int estadoAlumno) {
		this.estadoAlumno = estadoAlumno;
	}

	@Override
	public String toString() {
		String ret = "idRecurso: " + idRecurso;
		ret += "nombre: " + nombre;
		ret += "estado: " + estado;
		ret += "peso: " + peso;
		ret += "calificado: " + calificado;
		ret += "estadoDocente: " + estadoDocente;
		ret += "estadoAlumno: " + estadoAlumno;
		return ret;
	}

	@Override
	public boolean equals(Object o) {
		if(o != null && o instanceof Recurso){
			Recurso u = (Recurso) o;
			if (this.idRecurso == u.getIdRecurso()) {
				return true;
			}
		}
		return false;
	}
	
}
