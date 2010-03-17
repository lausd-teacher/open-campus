package edu.tecsup.lms.modelo.ficha.informe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "CV_INFORME")
@IdClass(FichaInformePK.class)
public class FichaInforme implements Serializable {

	private static final long serialVersionUID = 1935489204669505935L;
	
	@Id
	@Column(name = "CODCURSO", updatable=false, insertable = false)
	private Integer codcurso;
	
	@Id
	@ForeignKey(name="FK_INFORME_PERIODO")
	@Column(name = "CODPERIODO", updatable=false, insertable = false)
	private Integer codperiodo;
	
	@Column(name = "OBSERVACION",nullable=false,length=2048)
	private String observacion;
	
	@OneToMany(mappedBy = "fichaInforme", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Collection<FichaInformeDetalle> detalles = new ArrayList<FichaInformeDetalle>();
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_CREACION",updatable=false)
	private Date fechaCreacion;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_MOD")
	private Date fechaMod;
	
	@Column(name = "USUARIO_CREACION",updatable=false)
	private String usuarioCreacion;
	
	@Column(name = "USUARIO_MOD")
	private String usuarioMod;

	
	public Integer getCodcurso() {
		return codcurso;
	}

	public void setCodcurso(Integer codcurso) {
		this.codcurso = codcurso;
	}

	public Integer getCodperiodo() {
		return codperiodo;
	}

	public void setCodperiodo(Integer codperiodo) {
		this.codperiodo = codperiodo;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	
	public Date getFechaMod() {
		return fechaMod;
	}

	public void setFechaMod(Date fechaMod) {
		this.fechaMod = fechaMod;
	}

	
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	
	public String getUsuarioMod() {
		return usuarioMod;
	}

	public void setUsuarioMod(String usuarioMod) {
		this.usuarioMod = usuarioMod;
	}

	
	public Collection<FichaInformeDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(Collection<FichaInformeDetalle> detalles) {
		this.detalles = detalles;
	}

}
