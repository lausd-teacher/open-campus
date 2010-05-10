package edu.opencampus.lms.modelo.foro;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import edu.opencampus.lms.modelo.BaseModelo;

@Entity
@Table(name="CV_FORO_TEMA")
@SequenceGenerator(name="SEQCVFOROTEMA",sequenceName="SEQCVFOROTEMA",allocationSize=1)
public class Tema extends BaseModelo implements Serializable {

	private static final long serialVersionUID = -2637915997041485608L;
	
	private int idForo;
	
	private int idTema;
	
	private String titulo;
	
	private String cuerpo;
	
	private int icono;
	
	private int estado;
	
	private int cerrado;
	
	private int totalVotos;
	
	private int totalRespuestas;
	
	private int valoracion;
	
	private Mensaje ultimoMensaje;
	
	private Collection<Mensaje> mensajes;
	
	private String nombreUsuario;

	@Transient
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public int getIdForo() {
		return idForo;
	}

	public void setIdForo(int idForo) {
		this.idForo = idForo;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQCVFOROTEMA")
	public int getIdTema() {
		return idTema;
	}

	public void setIdTema(int idTema) {
		this.idTema = idTema;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	@Transient
	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public int getIcono() {
		return icono;
	}

	public void setIcono(int icono) {
		this.icono = icono;
	}

	public int getTotalVotos() {
		return totalVotos;
	}

	public void setTotalVotos(int totalVotos) {
		this.totalVotos = totalVotos;
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
	@Transient
	public int getTotalRespuestas() {
		return totalRespuestas;
	}

	public void setTotalRespuestas(int totalRespuestas) {
		this.totalRespuestas = totalRespuestas;
	}
	@Transient
	public int getValoracion() {
		return valoracion;
	}

	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}
	@Transient
	public Mensaje getUltimoMensaje() {
		return ultimoMensaje;
	}

	public void setUltimoMensaje(Mensaje ultimoMensaje) {
		this.ultimoMensaje = ultimoMensaje;
	}
	@Transient
	public Collection<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(Collection<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}
}