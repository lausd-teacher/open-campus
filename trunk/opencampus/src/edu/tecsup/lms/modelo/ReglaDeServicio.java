package edu.tecsup.lms.modelo;

import java.io.Serializable;

public class ReglaDeServicio implements Serializable {

	private static final long serialVersionUID = 4959771378774416974L;

	private Integer idRol;
	
	private Integer idPeriodo;
	
	private Integer idJerarquia;
	
	public ReglaDeServicio(Integer idRol, Integer idPeriodo, Integer idJerarquia) {
		this.idRol = idRol;
		this.idPeriodo = idPeriodo;
		this.idJerarquia = idJerarquia;
	}

	public ReglaDeServicio() {
	}

	@Override
	public String toString() {
		String cadena = "Regla de Servicio: ";
		 cadena += "\tidRol: "+this.idRol;
		 cadena += "\tidPeriodo: "+this.idPeriodo;
		 cadena += "\tidJerarquia: "+this.idJerarquia;
		return cadena;
	}

	@Override
	public boolean equals(Object reglaIndef) {
		if(reglaIndef != null && reglaIndef instanceof ReglaDeServicio){
			ReglaDeServicio regla = (ReglaDeServicio)reglaIndef;
			if(regla.getIdRol() == this.idRol && regla.getIdPeriodo() == this.idPeriodo 
				&& regla.getIdJerarquia() == this.idJerarquia){
				return true;
			}
		}
		return false;
		//return this.equals(reglaIndef);
	}

	public Integer getIdRol() {
		return idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public Integer getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public Integer getIdJerarquia() {
		return idJerarquia;
	}

	public void setIdJerarquia(Integer idJerarquia) {
		this.idJerarquia = idJerarquia;
	}

}
