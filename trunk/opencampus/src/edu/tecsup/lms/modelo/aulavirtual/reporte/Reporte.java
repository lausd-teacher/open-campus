package edu.tecsup.lms.modelo.aulavirtual.reporte;

import java.util.ArrayList;
import java.util.Collection;

public class Reporte {

	private Collection<UsuarioReporte> usuarios = new ArrayList<UsuarioReporte>();

	private Collection<UsuarioReporte> usuariosResponsables = new ArrayList<UsuarioReporte>();

	public Reporte() {
		super();
	}

	public Collection<UsuarioReporte> getUsuariosResponsables() {
		return usuariosResponsables;
	}

	public void setUsuariosResponsables(
			Collection<UsuarioReporte> usuariosResponsables) {
		this.usuariosResponsables = usuariosResponsables;
	}

	public Collection<UsuarioReporte> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Collection<UsuarioReporte> usuarios) {
		this.usuarios = usuarios;
	}

}