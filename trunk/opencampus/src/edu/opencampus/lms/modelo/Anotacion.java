package edu.opencampus.lms.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CV_ANOTACION")
//@SequenceGenerator(name = "SEQANOTACION", sequenceName = "SEQANOTACION", allocationSize = 1)
public class Anotacion implements Serializable {

	private static final long serialVersionUID = -8259043651420523707L;

	private int id;

	private int usuario;

	private String contenido;

	private String titulo;

	private Date fecha_creacion;

	private Date fecha_modificacion;

	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQANOTACION")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDANOTACION")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREADO_EN")
	public Date getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	@Column(name = "IDUSUARIO", updatable = false)
	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFICADO_EN")
	public Date getFecha_modificacion() {
		return fecha_modificacion;
	}

	public void setFecha_modificacion(Date fecha_modificacion) {
		this.fecha_modificacion = fecha_modificacion;
	}

}
