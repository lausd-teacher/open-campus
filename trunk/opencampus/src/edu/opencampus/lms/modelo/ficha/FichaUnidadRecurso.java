package edu.opencampus.lms.modelo.ficha;

import edu.opencampus.lms.modelo.BaseModelo;

public class FichaUnidadRecurso extends BaseModelo {

	private static final long serialVersionUID = -5586434428014842893L;

	private String idFicha;

	private String idUnidad;

	private String idRecurso;

	private String peso;

	private String califica;

	private String deshabilitadoDoc;

	private String deshabilitadoEstu;

	private String estado;

	public String getCalifica() {
		return califica;
	}

	public void setCalifica(String califica) {
		this.califica = califica;
	}

	public String getDeshabilitadoDoc() {
		return deshabilitadoDoc;
	}

	public void setDeshabilitadoDoc(String deshabilitadoDoc) {
		this.deshabilitadoDoc = deshabilitadoDoc;
	}

	public String getDeshabilitadoEstu() {
		return deshabilitadoEstu;
	}

	public void setDeshabilitadoEstu(String deshabilitadoEstu) {
		this.deshabilitadoEstu = deshabilitadoEstu;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public String getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(String idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public String toString() {
		String ret = "idFicha: " + idFicha;
		ret += "idUnidad: " + idUnidad;
		ret += "idRecurso: " + idRecurso;
		ret += "peso: " + peso;
		ret += "califica: " + califica;
		ret += "deshabilitadoDoc: " + deshabilitadoDoc;
		ret += "deshabilitadoEstu: " + deshabilitadoEstu;
		ret += "estado: " + estado;
		return ret;
	}

}
