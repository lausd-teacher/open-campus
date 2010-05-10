package edu.opencampus.lms.modelo.ficha;

import edu.opencampus.lms.modelo.BaseModelo;

public class FichaUnidad extends BaseModelo {

	private static final long serialVersionUID = -4190240689776562116L;

	private String idFicha;

	private String idSilabo;

	private String idUnidad;

	private String estado;

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

	public String getIdSilabo() {
		return idSilabo;
	}

	public void setIdSilabo(String idSilabo) {
		this.idSilabo = idSilabo;
	}

	public String getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(String idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String toString() {
		String ret = "idFicha: " + idFicha;
		ret += "idSilabo: " + idSilabo;
		ret += "idUnidad: " + idUnidad;
		ret += "estado: " + estado;
		return ret;
	}
}
