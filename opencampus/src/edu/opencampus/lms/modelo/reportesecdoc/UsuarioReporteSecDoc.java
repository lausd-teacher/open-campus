package edu.opencampus.lms.modelo.reportesecdoc;

import java.util.ArrayList;
import java.util.Collection;

import edu.opencampus.lms.util.Formato;

public class UsuarioReporteSecDoc {

	private String idUsuario;

	private String nombre1;

	private String nombre2;

	private String paterno;

	private String materno;

	private String codopencampus;

	private Collection<FichaReporteSecDoc> fichas = new ArrayList<FichaReporteSecDoc>();

	public UsuarioReporteSecDoc() {
		super();
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

	public String getPaterno() {
		return paterno;
	}

	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}

	public String getMaterno() {
		return materno;
	}

	public void setMaterno(String materno) {
		this.materno = materno;
	}

	public String getCodopencampus() {
		return codopencampus;
	}

	public void setCodopencampus(String codopencampus) {
		this.codopencampus = codopencampus;
	}

	public Collection<FichaReporteSecDoc> getFichas() {
		return fichas;
	}

	public void setFichas(Collection<FichaReporteSecDoc> fichas) {
		this.fichas = fichas;
	}
	
	public String getNombreCompleto() {
		return Formato.formatoNombreCompletoJSP(getNombre1(), getNombre2(),
				getPaterno(), getMaterno());
	}

}
