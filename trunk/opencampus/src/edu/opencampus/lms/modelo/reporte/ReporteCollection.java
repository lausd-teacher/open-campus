package edu.opencampus.lms.modelo.reporte;

import java.util.ArrayList;
import java.util.Collection;

public class ReporteCollection {

	private String cantidad = "0";

	private Collection<UsuarioReporte> usuarios = new ArrayList<UsuarioReporte>();

	public ReporteCollection() {
		super();
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public Collection<UsuarioReporte> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Collection<UsuarioReporte> usuarios) {
		this.usuarios = usuarios;
	}

}
