package edu.tecsup.lms.modelo.portal;

public class Servicio {
	private String id;

	private int columna;

	private int visible;

	private String nombre;

	private String comentario;

	private String imagen;

	private int usuarioEstado;

	private int estado;

	private int usuarioEliminar;

	private int usuarioMinimizar;

	private int verIngreso;

	private int verDescripcion;

	public Servicio() {
	}

	public Servicio(String id, int columna, int visible, String nombre,
			String comentario, String imagen) {
		super();
		this.id = id;
		this.columna = columna;
		this.visible = visible;
		this.nombre = nombre;
		this.comentario = comentario;
		this.imagen = imagen;
	}

	public int getUsuarioEstado() {
		return usuarioEstado;
	}

	public void setUsuarioEstado(int usuarioEstado) {
		this.usuarioEstado = usuarioEstado;
	}

	public int getUsuarioMinimizar() {
		return usuarioMinimizar;
	}

	public void setUsuarioMinimizar(int usuarioMinimizar) {
		this.usuarioMinimizar = usuarioMinimizar;
	}

	public int getVerIngreso() {
		return verIngreso;
	}

	public void setVerIngreso(int verIngreso) {
		this.verIngreso = verIngreso;
	}

	public int getVerDescripcion() {
		return verDescripcion;
	}

	public void setVerDescripcion(int verDescripcion) {
		this.verDescripcion = verDescripcion;
	}

	public int getUsuarioEliminar() {
		return usuarioEliminar;
	}

	public void setUsuarioEliminar(int usuarioEliminar) {
		this.usuarioEliminar = usuarioEliminar;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getColumna() {
		return columna;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

	public int getVisible() {
		return visible;
	}

	public void setVisible(int visible) {
		this.visible = visible;
	}

}
