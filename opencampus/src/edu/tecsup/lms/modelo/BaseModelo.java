package edu.tecsup.lms.modelo;

import java.io.Serializable;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import edu.tecsup.lms.util.Constante;
import edu.tecsup.lms.util.Formato;

@MappedSuperclass
public abstract class BaseModelo implements Serializable {

	private static final long serialVersionUID = -1348653359037654775L;

	@Column(name="CREADO_EN")
	private GregorianCalendar fechaCreacion;	
	
	@Column(name="CREADO_POR")
	private String usuarioCreacion;
	
	@Transient
	private GregorianCalendar fechaModificacion;
	
	@Transient
	private String usuarioModificacion;
	
	public BaseModelo(GregorianCalendar fechaCreacion, String usuarioCreacion,
			GregorianCalendar fechaModificacion, String usuarioModificacion) {
		super();
		this.fechaCreacion = fechaCreacion;
		this.usuarioCreacion = usuarioCreacion;
		this.fechaModificacion = fechaModificacion;
		this.usuarioModificacion = usuarioModificacion;
	}	
	
	public GregorianCalendar getFechaCreacion() {
		return fechaCreacion;
	}
	@Transient
	public String getFechaCreacionToString() {
		return Formato.calendarToString(fechaCreacion, Constante.FECHA_DIA_MES_ANO_HORA_MI);
	}

	public void setFechaCreacion(GregorianCalendar fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public GregorianCalendar getFechaModificacion() {
		return fechaModificacion;
	}
	@Transient
	public String getFechaModificacionToString() {
		return Formato.calendarToString(fechaModificacion, Constante.FECHA_DIA_MES_ANO_HORA_MI);
	}

	public void setFechaModificacion(GregorianCalendar fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	
	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public BaseModelo() {
		super();
	}

}
