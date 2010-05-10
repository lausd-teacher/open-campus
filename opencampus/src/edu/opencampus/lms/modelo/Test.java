package edu.opencampus.lms.modelo;

import java.util.Collection;

import edu.opencampus.lms.modelo.test.TestPregunta;

public class Test extends BaseModelo  {

	private static final long serialVersionUID = 8647653487256583827L;

	private int idNotaTest;

	private String idUnidad;

	private int idFicha;

	private int idMatricula;

	private String nombreUnidad;

	private Collection<TestPregunta> tests;

	public Test() {
	}
	
	@Override
	public String toString() {
		return "idNotaTest: "+this.idNotaTest + " tests: "+((this.tests!=null)?this.tests.size():"null");
	}

	public int getIdNotaTest() {
		return idNotaTest;
	}

	public void setIdNotaTest(int idNotaTest) {
		this.idNotaTest = idNotaTest;
	}

	public String getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(String idUnidad) {
		this.idUnidad = idUnidad;
	}

	public int getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(int idFicha) {
		this.idFicha = idFicha;
	}

	public int getIdMatricula() {
		return idMatricula;
	}

	public void setIdMatricula(int idMatricula) {
		this.idMatricula = idMatricula;
	}

	public String getNombreUnidad() {
		return nombreUnidad;
	}

	public void setNombreUnidad(String nombreUnidad) {
		this.nombreUnidad = nombreUnidad;
	}

	public Collection<TestPregunta> getTests() {
		return tests;
	}

	public void setTests(Collection<TestPregunta> tests) {
		this.tests = tests;
	}

}
