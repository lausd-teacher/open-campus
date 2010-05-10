package edu.opencampus.lms.modelo.reporte;

import java.io.Serializable;
import java.util.Collection;
import java.util.GregorianCalendar;

import edu.opencampus.lms.util.Formato;

public class opencampusVirtualReporte implements Serializable{

	private static final long serialVersionUID = -7596680570265575206L;

	private Integer idFicha;
	
	private String curso;
	
	private Integer idDocente;
	
	private String docente;
	
	private String deparatamento;
	
	private int debates;
	
	private boolean estado;
	
	private Collection<GregorianCalendar> ingresos;

	public Integer getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(Integer idFicha) {
		this.idFicha = idFicha;
	}

	public Integer getIdDocente() {
		return idDocente;
	}

	public void setIdDocente(Integer idDocente) {
		this.idDocente = idDocente;
	}

	public String getDeparatamento() {
		return deparatamento;
	}

	public void setDeparatamento(String deparatamento) {
		this.deparatamento = deparatamento;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getDocente() {
		return docente;
	}

	public void setDocente(String docente) {
		this.docente = docente;
	}

	public int getDebates() {
		return debates;
	}

	public void setDebates(int debates) {
		this.debates = debates;
	}

	public Collection<GregorianCalendar> getIngresos() {
		return ingresos;
	}

	public void setIngresos(Collection<GregorianCalendar> ingresos) {
		this.ingresos = ingresos;
	}
	
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public boolean isDiaDeIngreso(String fecha){
		if(fecha != null){
			for (GregorianCalendar ingreso : ingresos) {
				if(fecha.equals(Formato.setBaseDatosDeDate(ingreso))){
					return true;
				}
			}
		}
		return false;
	}

}
