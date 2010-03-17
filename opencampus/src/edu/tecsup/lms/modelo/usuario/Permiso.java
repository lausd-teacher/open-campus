package edu.tecsup.lms.modelo.usuario;

import java.io.Serializable;
@Deprecated
public class Permiso implements Serializable{

	private static final long serialVersionUID = -2520334052230686714L;

	public static final int TIPO_DEPARTAMENTO = 1;

	public static final int TIPO_FORMACION = 2;

	private String id;

	private String nombre;

	private String sede;

	private int tipo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Permiso(String id, String sede, String nombre, int tipo) {
		this.id = id;
		this.sede = sede;
		this.tipo = tipo;
		this.nombre = nombre;
	}
	
	public Permiso(int tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		String cadena = "";
		cadena += "\n\tID: " + id;
		cadena += "\n\tSEDE: " + sede;
		cadena += "\n\tNOMBRE: " + nombre;
		cadena += "\n\tTIPO: " + tipo;
		return cadena;
	}

}
