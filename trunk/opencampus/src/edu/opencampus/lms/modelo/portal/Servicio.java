package edu.opencampus.lms.modelo.portal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

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
		Integer columnTmp = 0; 
		for (Servicio servicio : portal) {
				if(columnTmp != servicio.getColumna()){
					if(columnTmp!=0)string.replace(string.length()-1, string.length(), "],");
					string.append("'column-");
					string.append(columnTmp = servicio.getColumna());
					string.append("':[");
				}
				string.append("'block-");
				string.append(servicio.getId());
				string.append("',");
		}
		string.replace(string.length()-1, string.length(),"]}");
		return string.toString();
	}
	
	public static Collection<Servicio> doJsonToServices(String cadena){
		Collection<Servicio> servicios = new ArrayList<Servicio>();
		Servicio servicio = null;
		if(cadena != null){
			String[] grupos = cadena.split(":");
			if(grupos.length == 2){
				Integer columna = Integer.parseInt(grupos[0].substring(grupos[0].indexOf('-')+1));
				String[] blocks = grupos[1].split(",");
				Integer fila = 1;
				for (String block : blocks) {
					servicio = new Servicio();
					servicio.setId(block.substring(block.indexOf('-')+1));
					servicio.setColumna(columna);
					servicio.setPosicion(fila++);
					servicios.add(servicio);
				}
			}
		}
		return servicios;
	}

}
