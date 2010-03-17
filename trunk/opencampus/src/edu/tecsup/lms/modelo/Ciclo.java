package edu.tecsup.lms.modelo;

public class Ciclo extends BaseModelo {

	private static final long serialVersionUID = -2639878332502233232L;

	private String idCiclo;

	private String nombre;

	private String tipo;

	public String getIdCiclo() {
		return idCiclo;
	}

	public void setIdCiclo(String idCiclo) {
		this.idCiclo = idCiclo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
