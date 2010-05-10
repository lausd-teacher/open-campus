package edu.opencampus.lms.modelo.foro;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import edu.opencampus.lms.modelo.BaseModelo;

@Entity
@Table(name="CV_FORO_MENSAJE")
@SequenceGenerator(name="SEQCVFOROMENSAJE",sequenceName="SEQCVFOROMENSAJE",allocationSize=1)
public class Mensaje extends BaseModelo implements Serializable {

	private static final long serialVersionUID = 7064716021996436505L;
		
	private int idForo;
		
	private int idTema;
		
	private int idMensaje;
	
	private String cuerpo;
	
	private int estado;
	
	private int moderado;
	
	private Integer idMensaje_cita;
	
	private Integer idForo_cita;
	
	private Integer idTema_cita;
	
	private String nombreUsuario;
	
	private String sexoUsuario;
	
	@Transient
	public String getSexoUsuario() {
		return sexoUsuario;
	}

	public void setSexoUsuario(String sexoUsuario) {
		this.sexoUsuario = sexoUsuario;
	}

	@Column(updatable=false,nullable=true)
	public Integer getIdForo_cita() {
		return idForo_cita;
	}
	
	public void setIdForo_cita(Integer idForo_cita) {
		this.idForo_cita = idForo_cita;
	}
	@Column(updatable=false,nullable=true)
	public Integer getIdTema_cita() {
		return idTema_cita;
	}
	
	public void setIdTema_cita(Integer idTema_cita) {
		this.idTema_cita = idTema_cita;
	}
	@Column(updatable=false,nullable=true)
	public Integer getIdMensaje_cita() {
		return idMensaje_cita;
	}
	
	public void setIdMensaje_cita(Integer idMensaje_cita) {
		this.idMensaje_cita = idMensaje_cita;
	}

	@Column(updatable=false)
	public int getIdForo() {
		return idForo;
	}

	public void setIdForo(int idForo) {
		this.idForo = idForo;
	}

	@Column(updatable=false)
	public int getIdTema() {
		return idTema;
	}

	public void setIdTema(int idTema) {
		this.idTema = idTema;
	}
	@Id	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQCVFOROMENSAJE")
	public int getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(int idMensaje) {
		this.idMensaje = idMensaje;
	}
	@Column(nullable=true)
	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public int getModerado() {
		return moderado;
	}

	public void setModerado(int moderado) {
		this.moderado = moderado;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	@Transient
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

}