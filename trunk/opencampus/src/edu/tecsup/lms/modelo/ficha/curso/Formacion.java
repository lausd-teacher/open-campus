package edu.tecsup.lms.modelo.ficha.curso;

import java.util.ArrayList;
import java.util.Collection;

public class Formacion {

	private String nombre = "";

	private int codigo;

	private int codigoCurricula;

	private Collection<Ficha> fichas = new ArrayList<Ficha>();

	public Collection<Ficha> getFichas() {
		return fichas;
	}

	public int getCodigoCurricula() {
		return codigoCurricula;
	}

	public void setCodigoCurricula(int codigoCurricula) {
		this.codigoCurricula = codigoCurricula;
	}

	public void setFichas(Collection<Ficha> fichas) {
		this.fichas = fichas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

}
