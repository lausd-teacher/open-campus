package edu.opencampus.lms.modelo;

public class FormacionCicloCurso extends BaseModelo {

	private static final long serialVersionUID = 6654550373212047049L;

	private String idNodoFormacion;

	private String idFormacion;

	private String idCiclo;

	private String idCurso;

	public String getIdCiclo() {
		return idCiclo;
	}

	public void setIdCiclo(String idCiclo) {
		this.idCiclo = idCiclo;
	}

	public String getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(String idCurso) {
		this.idCurso = idCurso;
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
