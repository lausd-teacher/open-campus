package edu.opencampus.lms.modelo.reporte;

import java.util.ArrayList;
import java.util.Collection;

import edu.opencampus.lms.util.Formato;

public class UsuarioReporte {

	private String idUsuario;

	private String nombre1;

	private String nombre2;

	private String paterno;

	private String materno;

	private Collection<FichaReporte> fichas = new ArrayList<FichaReporte>();

	public UsuarioReporte() {
		super();
	}

	public String getMaterno() {
		return materno;
	}

	public void setMaterno(String materno) {
		this.materno = materno;
	}

	public String getPaterno() {
		return paterno;
	}

	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}

	public Collection<FichaReporte> getFichas() {
		return fichas;
	}

	public void setFichas(Collection<FichaReporte> fichas) {
		this.fichas = fichas;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre1() {
		return nombre1;
	}

	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}

	public String getNombre2() {
		return nombre2;
	}

	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}

	public String getNombreCompleto() {
		return Formato.formatoNombreCompletoJSP(getNombre1(), getNombre2(),
				getPaterno(), getMaterno());
	}

}
