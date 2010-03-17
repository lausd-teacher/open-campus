package edu.tecsup.lms.modelo;

public class FormacionCiclo extends BaseModelo {

	private static final long serialVersionUID = 6176075373269994930L;

	private String idNodoFormacion;

	private String idFormacion;

	private String idCiclo;

	public String getIdCiclo() {
		return idCiclo;
	}

	public void setIdCiclo(String idCiclo) {
		this.idCiclo = idCiclo;
	}

	public String getIdFormacion() {
		return idFormacion;
	}

	public void setIdFormacion(String idFormacion) {
		this.idFormacion = idFormacion;
	}

	public String getIdNodoFormacion() {
		return idNodoFormacion;
	}

	public void setIdNodoFormacion(String idNodoFormacion) {
		this.idNodoFormacion = idNodoFormacion;
	}

}
