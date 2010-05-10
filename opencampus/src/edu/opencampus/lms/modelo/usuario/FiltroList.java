package edu.opencampus.lms.modelo.usuario;

import java.io.Serializable;

public class FiltroList implements Serializable{

	private static final long serialVersionUID = 4905544578321367016L;

	private UsuarioFiltro filtroDirectorio = null;
	
	private UsuarioFiltro filtroUsuarios = null;

	public UsuarioFiltro getFiltroDirectorio() {
		return filtroDirectorio;
	}

	public void setFiltroDirectorio(UsuarioFiltro filtroDirectorio) {
		this.filtroDirectorio = filtroDirectorio;
	}

	public UsuarioFiltro getFiltroUsuarios() {
		return filtroUsuarios;
	}

	public void setFiltroUsuarios(UsuarioFiltro filtroUsuarios) {
		this.filtroUsuarios = filtroUsuarios;
	}
	
	
	
}
