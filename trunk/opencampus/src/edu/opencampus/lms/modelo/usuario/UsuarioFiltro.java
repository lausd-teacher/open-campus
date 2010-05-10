package edu.opencampus.lms.modelo.usuario;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import edu.opencampus.lms.modelo.Filtro;
import edu.opencampus.lms.util.Formato;

public class UsuarioFiltro extends Filtro{

	private static final long serialVersionUID = 7442445653673152572L;

	private String usuario;

	private String nombre1;

	private String nombre2;

	private String paterno;

	private String materno;

	private Integer rol;

	public UsuarioFiltro() {
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = (usuario!=null && usuario.trim().length()>0)?Formato.matizarFrace(usuario):null;
	}

	public String getNombre1() {
		return nombre1;
	}

	public void setNombre1(String nombre1) {
		this.nombre1 = (nombre1!=null && nombre1.trim().length()>0)?Formato.matizarFrace(nombre1):null;
	}

	public String getNombre2() {
		return nombre2;
	}

	public void setNombre2(String nombre2) {
		this.nombre2 = (nombre2!=null && nombre2.trim().length()>0)?Formato.matizarFrace(nombre2):null;
	}

	public String getPaterno() {
		return paterno;
	}

	public void setPaterno(String paterno) {
		this.paterno = (paterno!=null && paterno.trim().length()>0)?Formato.matizarFrace(paterno):null;
	}

	public String getMaterno() {
		return materno;
	}

	public void setMaterno(String materno) {
		this.materno = (materno!=null && materno.trim().length()>0)?Formato.matizarFrace(materno):null;
	}

	public Integer getRol() {
		return rol;
	}

	public void setRol(Integer rol) {
		this.rol = (rol!=null && rol != 0)?rol:null;
	}

}
