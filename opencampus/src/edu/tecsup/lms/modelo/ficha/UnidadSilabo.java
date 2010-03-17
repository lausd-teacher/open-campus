package edu.tecsup.lms.modelo.ficha;

import edu.tecsup.lms.modelo.BaseModelo;
@Deprecated
public class UnidadSilabo extends BaseModelo {

	private static final long serialVersionUID = 5382782172433444478L;

	private Unidad unidad;

	private String indice;

	public UnidadSilabo() {
	}

	public String getIndice() {
		return indice;
	}

	public void setIndice(String indice) {
		this.indice = indice;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

}
