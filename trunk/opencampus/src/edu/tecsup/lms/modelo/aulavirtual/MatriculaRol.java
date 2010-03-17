package edu.tecsup.lms.modelo.aulavirtual;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.tecsup.lms.modelo.Matricula;

public class MatriculaRol implements Serializable {

	private static final long serialVersionUID = 6637693798916687760L;

	private String nombre;

	private Integer idRol;

	private List<Matricula> matriculas = new ArrayList<Matricula>();

	public MatriculaRol(Integer idRol) {
		this.idRol = idRol;
	}

	public MatriculaRol(Integer idRol, String nombre) {
		this.nombre = nombre;
		this.idRol = idRol;
	}
	
	public MatriculaRol() {
	}

	public List<Matricula> getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getIdRol() {
		return idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}
	
	@Override
	public String toString() {
		String ret = idRol + " - " + nombre;
		return ret;
	}

}
