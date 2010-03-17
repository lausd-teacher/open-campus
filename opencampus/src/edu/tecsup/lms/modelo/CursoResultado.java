package edu.tecsup.lms.modelo;

public class CursoResultado extends BaseModelo {

	private static final long serialVersionUID = 3804696593312706011L;

	private String idPlanCurricular;

	private String idCurso;

	private String idResultado;

	private String resultado;

	private String indice;

	public String getIndice() {
		return indice;
	}

	public void setIndice(String indice) {
		this.indice = indice;
	}

	public String getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(String idCurso) {
		this.idCurso = idCurso;
	}

	public String getIdPlanCurricular() {
		return idPlanCurricular;
	}

	public void setIdPlanCurricular(String idPlanCurricular) {
		this.idPlanCurricular = idPlanCurricular;
	}

	public String getIdResultado() {
		return idResultado;
	}

	public void setIdResultado(String idResultado) {
		this.idResultado = idResultado;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String toString() {
		String ret = "idPlanCurricular: " + idPlanCurricular;
		ret += "idCurso: " + idCurso;
		ret += "idResultado: " + idResultado;
		ret += "resultado: " + resultado;
		return ret;
	}
}