package edu.opencampus.lms.modelo;

import java.io.Serializable;
import java.util.Collection;

import edu.opencampus.lms.modelo.foro.Mensaje;
import edu.opencampus.lms.modelo.foro.Tema;

public class Foro extends BaseModelo implements Serializable {

	private static final long serialVersionUID = -4614680052493844363L;
	
	private int idForo;
	
	private String titulo;
	
	private String descripcion;
	
	private int icono;
	
	private int estado;
	
	private int cerrado;
	
	private boolean yoLoModero;
	
	private Collection<Usuario> moderadores;
	
	private Collection<ReglaDeServicio> reglaDeServicio;
	
	private int totalTemas;
	
	private int totalMensajes;
	
	private Tema ultimoTema;
	
	private Mensaje ultimoMensaje;
	
	private Collection<Mensaje> mensajes;

	public Mensaje getUltimoMensaje() {
		return ultimoMensaje;
	}

	public void setUltimoMensaje(Mensaje ultimoMensaje) {
		this.ultimoMensaje = ultimoMensaje;
	}

	@Override
	public String toString() {
		String cadena = "***** Foro *****\n";
		cadena += "\tIdForo: "+idForo+"\n";
		cadena += "\tTitulo: "+titulo+"\n";
		cadena += "\tDescripcion: "+descripcion+"\n";
		cadena += "\tIcono: "+icono+"\n";
		cadena += "\tEstado: "+estado+"\n";
		cadena += "\tCerrado: "+cerrado+"\n";
		cadena += "\tYoLoModero: "+yoLoModero+"\n";
		cadena += "\tTotalTemas: "+totalTemas+"\n";
		cadena += "\tTotalMensajes: "+totalMensajes+"\n";
		if(moderadores != null && !moderadores.isEmpty()){
			cadena += "\tModeradores: \n";
			for (Usuario usuario : moderadores) {
				cadena += "\t\tIdusuario: "+usuario.getIdUsuario()+"\n";
			}
		}
		if(reglaDeServicio != null && !reglaDeServicio.isEmpty()){
			cadena += "\tRegla de servicio: \n";
			for (ReglaDeServicio regla : reglaDeServicio) {
				cadena += "\t\tIdRegla: "+regla.getIdRegla()+"\n";
				cadena += "\t\tSede: "+regla.getSede()+"\n";
				cadena += "\t\tFamilia: "+regla.getFamilia()+"\n";
				cadena += "\t\tFormacion: "+regla.getFormacion()+"\n";
				cadena += "\t\tCiclo: "+regla.getCiclo()+"\n";
				cadena += "\t\tSeccion: "+regla.getSeccion()+"\n";
			}
		}
		cadena += "****************";
		return cadena;
	}

	public Collection<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(Collection<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	public int getIdForo() {
		return idForo;
	}

	public void setIdForo(int idForo) {
		this.idForo = idForo;
	}

	public boolean isYoLoModero() {
		return yoLoModero;
	}

	public void setYoLoModero(boolean yoLoModero) {
		this.yoLoModero = yoLoModero;
	}

	public Tema getUltimoTema() {
		return ultimoTema;
	}

	public void setUltimoTema(Tema ultimoTema) {
		this.ultimoTema = ultimoTema;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIcono() {
		return icono;
	}

	public void setIcono(int icono) {
		this.icono = icono;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getCerrado() {
		return cerrado;
	}

	public void setCerrado(int cerrado) {
		this.cerrado = cerrado;
	}

	public Collection<Usuario> getModeradores() {
		return moderadores;
	}

	public void setModeradores(Collection<Usuario> moderadores) {
		this.moderadores = moderadores;
	}

	public Collection<ReglaDeServicio> getReglaDeServicio() {
		return reglaDeServicio;
	}

	public void setReglaDeServicio(Collection<ReglaDeServicio> reglaDeServicio) {
		this.reglaDeServicio = reglaDeServicio;
	}

	public int getTotalTemas() {
		return totalTemas;
	}

	public void setTotalTemas(int totalTemas) {
		this.totalTemas = totalTemas;
	}

	public int getTotalMensajes() {
		return totalMensajes;
	}

	public void setTotalMensajes(int totalMensajes) {
		this.totalMensajes = totalMensajes;
	}
	
	

}
