package edu.tecsup.lms.modelo;

public class Departamento {

	private String codigo;

	private String nombre;

	private Especialidad especialidad = new Especialidad();

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public Departamento(String codigo, String nombre, Especialidad especialidad) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.especialidad = especialidad;
	}

	public Departamento() {
		super();
	}

}
