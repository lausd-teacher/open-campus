package edu.opencampus.lms.modelo.ficha;

import edu.opencampus.lms.modelo.BaseModelo;

public class Etiqueta extends BaseModelo {

	private static final long serialVersionUID = -5954047353594073849L;

	public String idEtiqueta;

	public String nombre;

	public Etiqueta() {
		super();
	}

	public String getIdEtiqueta() {
		return idEtiqueta;
	}

	public void setIdEtiqueta(String idEtiqueta) {
		this.idEtiqueta = idEtiqueta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
