package edu.tecsup.lms.modelo.test;

import java.util.Map;

import edu.tecsup.lms.modelo.BaseModelo;
import edu.tecsup.lms.util.Util;

public class TestPregunta extends BaseModelo {

	private static final long serialVersionUID = 1744534742801590432L;

	private int idTest;

	private int idUnidad;

	private String pregunta;

	private char respuesta;

	private String explicacion;

	private int estado;

	private int tipo;

	private String tipoToString;

	private Map<String, TestAlternativa> alternativas;

	private int grafico;

	private String archivoNombre;

	private String archivoTamanio;

	public TestPregunta() {
	}

	public String getArchivoNombre() {
		return archivoNombre;
	}

	public void setArchivoNombre(String archivoNombre) {
		this.archivoNombre = archivoNombre;
	}

	public String getArchivoTamanio() {
		return archivoTamanio;
	}

	public void setArchivoTamanio(String archivoTamanio) {
		this.archivoTamanio = archivoTamanio;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getExplicacion() {
		return explicacion;
	}

	public void setExplicacion(String explicacion) {
		this.explicacion = explicacion;
	}

	public int getGrafico() {
		return grafico;
	}

	public void setGrafico(int grafico) {
		this.grafico = grafico;
	}

	public int getIdTest() {
		return idTest;
	}

	public void setIdTest(int idTest) {
		this.idTest = idTest;
	}

	public int getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(int idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
		setTipoToString(Util.getTestTipos().get(String.valueOf(tipo)));
	}

	public String getTipoToString() {
		return tipoToString;
	}

	public void setTipoToString(String tipoToString) {
		this.tipoToString = tipoToString;
	}

	public Map<String, TestAlternativa> getAlternativas() {
		return alternativas;
	}

	public void setAlternativas(Map<String, TestAlternativa> alternativas) {
		this.alternativas = alternativas;
	}

	public char getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(char respuesta) {
		this.respuesta = respuesta;
	}

}
