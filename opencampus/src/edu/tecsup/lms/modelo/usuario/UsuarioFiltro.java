package edu.tecsup.lms.modelo.usuario;
@Deprecated
public class UsuarioFiltro {

	private String usuario;

	private String nombre1;

	private String nombre2;

	private String paterno;

	private String materno;

	private String rol;

	private String registroPrimero;

	private String registroUltimo;

	public UsuarioFiltro() {
		super();
	}

	@Override
	public String toString() {
		return usuario+"-"+nombre1+" "+nombre2+" "+paterno+" "+materno+"-"+rol+"-"+registroPrimero+" "+registroUltimo;
	}

	public String getMaterno() {
		return materno;
	}

	public void setMaterno(String materno) {
		this.materno = materno;
	}

	public String getNombre1() {
		return nombre1;
	}

	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}

	public String getNombre2() {
		return nombre2;
	}

	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}

	public String getPaterno() {
		return paterno;
	}

	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}

	public String getRegistroPrimero() {
		return registroPrimero;
	}

	public void setRegistroPrimero(String registroPrimero) {
		this.registroPrimero = registroPrimero;
	}

	public String getRegistroUltimo() {
		return registroUltimo;
	}

	public void setRegistroUltimo(String registroUltimo) {
		this.registroUltimo = registroUltimo;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
