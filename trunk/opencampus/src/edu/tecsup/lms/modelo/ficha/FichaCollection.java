package edu.tecsup.lms.modelo.ficha;

import java.util.ArrayList;
import java.util.Collection;

import edu.tecsup.lms.modelo.Ficha;

public class FichaCollection {

	private String cantidad = "";

	private Collection<Ficha> fichas = new ArrayList<Ficha>();

	public FichaCollection() {
		super();
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public Collection<Ficha> getFichas() {
		return fichas;
	}

	public void setFichas(Collection<Ficha> fichas) {
		this.fichas = fichas;
	}

}
