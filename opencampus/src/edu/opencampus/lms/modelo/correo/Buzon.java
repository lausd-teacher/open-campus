package edu.opencampus.lms.modelo.correo;

public class Buzon {
	
	String idBuzon;
	int tipoCorreo;
	int leido;
	int estado;
	String idUsuario;
	String idMensaje;
	String idAdjunto;
	
	public String getIdAdjunto() {
		return idAdjunto;
	}
	public void setIdAdjunto(String idAdjunto) {
		this.idAdjunto = idAdjunto;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getIdBuzon() {
		return idBuzon;
	}
	public void setIdBuzon(String idBuzon) {
		this.idBuzon = idBuzon;
	}
	public String getIdMensaje() {
		return idMensaje;
	}
	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getLeido() {
		return leido;
	}
	public void setLeido(int leido) {
		this.leido = leido;
	}
	public int getTipoCorreo() {
		return tipoCorreo;
	}
	public void setTipoCorreo(int tipoCorreo) {
		this.tipoCorreo = tipoCorreo;
	}

}
