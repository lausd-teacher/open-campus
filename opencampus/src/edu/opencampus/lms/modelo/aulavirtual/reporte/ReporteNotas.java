package edu.opencampus.lms.modelo.aulavirtual.reporte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class ReporteNotas implements Serializable {

	private static final long serialVersionUID = 2288914370002750176L;
	
	private String estudiante;
	
	private Collection<MatriculaGTest> tests = new ArrayList<MatriculaGTest>();
	
	private MatriculaGTest promedio;

	public String getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(String estudiante) {
		this.estudiante = estudiante;
	}

	public Collection<MatriculaGTest> getTests() {
		return tests;
	}

	public void setTests(Collection<MatriculaGTest> tests) {
		this.tests = tests;
	}

	public MatriculaGTest getPromedio() {
		return promedio;
	}

	public void setPromedio(MatriculaGTest promedio) {
		this.promedio = promedio;
	}
	
	

}
