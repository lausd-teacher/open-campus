package edu.tecsup.lms.modelo.usuario;

import java.util.ArrayList;
import java.util.Collection;

import edu.tecsup.lms.modelo.Usuario;

@Deprecated
public class CollectionUsuario {

	private String cantidad = "0";

	private Collection<Usuario> array = new ArrayList<Usuario>();

	public Collection<Usuario> getArray() {
		return array;
	}

	public void setArray(Collection<Usuario> array) {
		this.array = array;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

}
