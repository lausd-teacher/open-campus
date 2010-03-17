package edu.tecsup.lms.modelo;

import java.io.Serializable;

import edu.tecsup.lms.modelo.aulavirtual.MatriculaRol;
import edu.tecsup.lms.util.Constante;

public class Matricula extends BaseModelo implements Serializable {

	private static final long serialVersionUID = -3766454514281111253L;

	private Integer idMatricula;

	private Usuario usuario;
	
	private MatriculaRol rol;
	
	private AulaVirtual aula;

	private Integer estado;
	
	private Integer eliminado;

	private Integer principal;
	
	private Integer constancia;

	//private Empresa auspicidor;
	
	public Integer getIdMatricula() {
		return idMatricula;
	}

	public AulaVirtual getAula() {
		return aula;
	}

	public void setAula(AulaVirtual aula) {
		this.aula = aula;
	}

	public void setIdMatricula(Integer idMatricula) {
		this.idMatricula = idMatricula;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getEliminado() {
		return eliminado;
	}

	public void setEliminado(Integer eliminado) {
		this.eliminado = eliminado;
	}

	public MatriculaRol getRol() {
		return rol;
	}

	public void setRol(MatriculaRol rol) {
		this.rol = rol;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getPrincipal() {
		return principal;
	}

	public void setPrincipal(Integer principal) {
		this.principal = principal;
	}

	public Integer getConstancia() {
		return constancia;
	}

	public void setConstancia(Integer constancia) {
		this.constancia = constancia;
	}
	
	public boolean isDocenteResponsable(){
		if(rol != null){
			return (Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE == this.rol.getIdRol()
					|| Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE == this.rol.getIdRol()
					|| Constante.ROL_CAMPUS_AULAVIRTUAL_MONITOR_DOCENTE == this.rol.getIdRol());
		}
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if(o != null && o instanceof Matricula){
			Matricula m = (Matricula) o;
			if (this.idMatricula == m.getIdMatricula()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuffer cadena = new StringBuffer("\n\tID: "+this.idMatricula);
		cadena.append("\n\tUsuario: "+usuario);
		cadena.append("\n\tRol: "+rol);
		cadena.append("\n\tPrincipal: "+principal);
		return cadena.toString();
	}

}
