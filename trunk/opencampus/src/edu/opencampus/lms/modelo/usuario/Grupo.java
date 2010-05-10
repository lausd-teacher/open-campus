package edu.opencampus.lms.modelo.usuario;

import java.util.ArrayList;
import java.util.Collection;

import edu.opencampus.lms.modelo.BaseModelo;
import edu.opencampus.lms.modelo.Jerarquia;
import edu.opencampus.lms.modelo.Usuario;
@Deprecated
public class Grupo extends BaseModelo {

	private static final long serialVersionUID = 4884191214941569183L;

	private int id;

	private String nombre;

	private int estado;

	private Jerarquia jerarquia = new Jerarquia();

	private Collection<Usuario> usuario = new ArrayList<Usuario>();;

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Collection<Usuario> getUsuario() {
		return usuario;
	}

	public void setUsuario(Collection<Usuario> usuario) {
		this.usuario = usuario;
	}

	public Grupo() {
		super();
	}

	public Grupo(int id, String nombre, int estado, Collection<Usuario> usuario) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.estado = estado;
		this.usuario = usuario;
	}

	public Grupo(int id, String nombre, int estado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.estado = estado;
	}

	public Jerarquia getJerarquia() {
		return jerarquia;
	}

	public void setJerarquia(Jerarquia jerarquia) {
		this.jerarquia = jerarquia;
	}

}
