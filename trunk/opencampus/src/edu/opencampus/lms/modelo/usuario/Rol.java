package edu.opencampus.lms.modelo.usuario;

import edu.opencampus.lms.modelo.BaseModelo;

public class Rol extends BaseModelo {

	private static final long serialVersionUID = -4049404545816075606L;

	private int idrol;

	private String nombre;

	private int estado;

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getIdrol() {
		return idrol;
	}

	public void setIdrol(int idrol) {
		this.idrol = idrol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Rol(int idrol, String nombre) {
		super();
		this.idrol = idrol;
		this.nombre = nombre;
	}

	public Rol(int idrol) {
		super();
		this.idrol = idrol;
	}
	
	public Rol() {
		super();
	}
	
	@Override
	public String toString() {
		String ret = idrol + " - " + nombre;
		return ret;
	}

	@Override
	public boolean equals(Object o) {
		if(o != null && o instanceof Rol && ((Rol)o).getIdrol()==this.idrol)
			return true;
		return false;
	}

	public int hashCode() {
		return this.idrol;
	}

}
