package edu.tecsup.lms.modelo;

import java.util.ArrayList;
import java.util.Collection;

public class Jerarquia extends BaseModelo {

	private static final long serialVersionUID = -7257729355152828165L;

	private Integer idJerarquia;

	private Jerarquia padre;
	
	private Collection<Jerarquia> hijos;

	private String nombre;
	
	private String tipo;

	private int estado;

	private String rutaCompleta;

	
	public String getRutaCompleta() {
		StringBuffer ruta = new StringBuffer("/"+this.nombre+"/");
		Jerarquia padre = this.padre;
		while(padre != null){
			ruta.insert(0,"/"+padre.getNombre());
			padre = padre.getPadre();
		}
		setRutaCompleta(ruta.toString());
		return rutaCompleta;
	}
	
	public Jerarquia getPatriarca() {
		Jerarquia padre = this;
		while(padre.getPadre() != null){
			padre = padre.getPadre();
		}
		return padre;
	}

	private void setRutaCompleta(String rutaCompleta) {
		this.rutaCompleta = rutaCompleta;
	}
	
	public boolean isMyChild(Integer idChild){
		if(idChild.equals(this.idJerarquia))
			return true;
		else{
			Collection<Jerarquia> hijos = this.hijos;
			for (Jerarquia j : hijos) {
				if(j.isMyChild(idChild))
					return true;
			}
		}
		return false;
	}
	
	public boolean isMyParent(Integer idParent){
		if(idParent.equals(this.idJerarquia))
			return true;
		else{
			Jerarquia p = this.padre;
			if(p != null && p.isMyParent(idParent))
				return true;
		}
		return false;
	}

	public Collection<Jerarquia> getLista(){
		Collection<Jerarquia> lista = new ArrayList<Jerarquia>();
		lista.add(this);
		for (Jerarquia j : this.hijos) {
			j.setPadre(this);
			lista.addAll(j.getLista());
		}
		return lista;
	}
	
	public Integer getIdJerarquia() {
		return idJerarquia;
	}

	public void setIdJerarquia(Integer idJerarquia) {
		this.idJerarquia = idJerarquia;
	}

	public Jerarquia getPadre() {
		return padre;
	}

	public void setPadre(Jerarquia padre) {
		this.padre = padre;
	}

	public Collection<Jerarquia> getHijos() {
		return hijos;
	}

	public void setHijos(Collection<Jerarquia> hijos) {
		this.hijos = hijos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Jerarquia() {
		super();
	}
	
	public Jerarquia(Integer idJerarquia) {
		super();
		this.idJerarquia = idJerarquia;
	}
	
	public Jerarquia(Integer idJerarquia, String nombre) {
		super();
		this.idJerarquia = idJerarquia;
		this.nombre = nombre;
	}

	@Override
	public boolean equals(Object o) {
		if(o != null && o instanceof Jerarquia && ((Jerarquia)o).getIdJerarquia()==this.idJerarquia)
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "ID: "+this.idJerarquia+" Nombre:"+this.nombre+"\tPadre:["+this.padre+"]";
	}

}
