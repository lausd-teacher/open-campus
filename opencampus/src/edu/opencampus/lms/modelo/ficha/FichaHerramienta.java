package edu.opencampus.lms.modelo.ficha;

import edu.opencampus.lms.modelo.BaseModelo;

public class FichaHerramienta extends BaseModelo {

	private static final long serialVersionUID = 4114010576509087132L;

	private String idHerramienta;

	private String idFicha;

	private String estado;

	private String deshabilitado_doc;

	private String deshabilitado_estu;

	public String getDeshabilitado_doc() {
		return deshabilitado_doc;
	}

	public void setDeshabilitado_doc(String deshabilitado_doc) {
		this.deshabilitado_doc = deshabilitado_doc;
	}

	public String getDeshabilitado_estu() {
		return deshabilitado_estu;
	}

	public void setDeshabilitado_estu(String deshabilitado_estu) {
		this.deshabilitado_estu = deshabilitado_estu;
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

	public String getIdHerramienta() {
		return idHerramienta;
	}

	public void setIdHerramienta(String idHerramienta) {
		this.idHerramienta = idHerramienta;
	}

	public String toString() {
		String ret = "idHerramienta: " + idHerramienta;
		ret += "idFicha: " + idFicha;
		ret += "estado: " + estado;
		ret += "deshabilitado_doc: " + deshabilitado_doc;
		ret += "deshabilitado_estu: " + deshabilitado_estu;
		return ret;

	}
}
