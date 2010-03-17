package edu.tecsup.lms.modelo.test;

import java.io.Serializable;

public class TestAlternativa implements Serializable {

	private static final long serialVersionUID = 2139026460367526001L;

	private String texto;

	private String textoAux;

	private char respuesta;

	public TestAlternativa() {
	}

	public TestAlternativa(String textoAux, char respuesta, String texto) {
		this.texto = texto;
		this.textoAux = textoAux;
		this.respuesta = respuesta;
	}

	public TestAlternativa(String textoAux, char respuesta) {
		this.textoAux = textoAux;
		this.respuesta = respuesta;
	}

	public TestAlternativa(String texto) {
		this.texto = texto;
	}

	public String getTextoAux() {
		return textoAux;
	}

	public void setTextoAux(String textoAux) {
		this.textoAux = textoAux;
	}

	public char getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(char respuesta) {
		this.respuesta = respuesta;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
