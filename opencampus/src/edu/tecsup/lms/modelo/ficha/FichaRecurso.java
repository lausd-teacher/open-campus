package edu.tecsup.lms.modelo.ficha;

import edu.tecsup.lms.modelo.BaseModelo;

public class FichaRecurso extends BaseModelo {

	private static final long serialVersionUID = -3406368952842609054L;

	private String idFicha;

	private String idRecurso;

	private String peso;

	private String nombreRecurso;

	public String getNombreRecurso() {
		return nombreRecurso;
	}

	public void setNombreRecurso(String nombreRecurso) {
		this.nombreRecurso = nombreRecurso;
	}

	public String getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(String idFicha) {
		this.idFicha = idFicha;
	}

	public String getIdRecurso() {
		return idRecurso;
	}

	public void setIdRecurso(String idRecurso) {
		this.idRecurso = idRecurso;
	}

	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public String toString() {
		String ret = "idFicha: " + idFicha;
		ret += "idRecurso: " + idRecurso;
		ret += "peso: " + peso;
		return ret;
	}
}
