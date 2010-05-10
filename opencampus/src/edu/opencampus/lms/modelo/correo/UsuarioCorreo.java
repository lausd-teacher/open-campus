package edu.opencampus.lms.modelo.correo;

public class UsuarioCorreo {
	
	String nombreCompleto;
	String idUsuario;
	String nombreCorto;
	String email;
	
	public String getNombreCorto() {
		return nombreCorto;
	}
	public void setNombreCorto(String nombreCorto) {		
		this.nombreCorto = nombreCorto.replaceAll("Null", "");
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario.trim();
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto.replaceAll("Null", "");
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
