package edu.opencampus.lms.modelo;

import java.io.Serializable;

public class Sede  implements Serializable {

	private static final long serialVersionUID = 7842062072794690897L;

	private String codigo;

	private String nombre;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Sede() {
		super();
	}

}
