package edu.tecsup.lms.modelo.portal;

import java.io.Serializable;
import java.util.Collection;

import edu.tecsup.lms.util.Constante;

public class Servicio implements Serializable{
	
	private static final long serialVersionUID = 2799134248495895574L;

	private String id;

	private String nombre;
	
	private Integer columna;
	
	private Integer posicion;

	private Integer estado;
	
	private Integer visible;

	private Integer permisoEliminar;
	
	private Integer permisoMinimizar;

	public Servicio() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Integer getColumna() {
		return columna;
	}


	public void setColumna(Integer columna) {
		this.columna = columna;
	}


	public Integer getPosicion() {
		return posicion;
	}


	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}


	public Integer getEstado() {
		return estado;
	}


	public void setEstado(Integer estado) {
		this.estado = estado;
	}


	public Integer getVisible() {
		return visible;
	}


	public void setVisible(Integer visible) {
		this.visible = visible;
	}


	public Integer getPermisoEliminar() {
		return permisoEliminar;
	}


	public void setPermisoEliminar(Integer permisoEliminar) {
		this.permisoEliminar = permisoEliminar;
	}


	public Integer getPermisoMinimizar() {
		return permisoMinimizar;
	}


	public void setPermisoMinimizar(Integer permisoMinimizar) {
		this.permisoMinimizar = permisoMinimizar;
	}
	
	public static String doServicesToJson(Collection<Servicio> portal){
		StringBuffer string  = new StringBuffer("{");
		Integer columnTmp = -1; 
		int i = 1;
		for (Servicio servicio : portal) {
			if(servicio.getEstado() == Constante.ESTADO_ACTIVO){
				if(columnTmp != servicio.getColumna()){
					if(columnTmp!=-1)string.replace(string.length()-1, string.length(), "],");
					string.append("'column-");
					string.append(servicio.getColumna()+1);
					string.append("':[");
					columnTmp = servicio.getColumna();
				}
				string.append("'block-");
				string.append(i);
				string.append("',");
			}
			i++;
		}
		string.replace(string.length()-1, string.length(),"]}");
		return string.toString();
	}

}
