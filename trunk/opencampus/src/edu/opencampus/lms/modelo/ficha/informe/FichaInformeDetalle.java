package edu.opencampus.lms.modelo.ficha.informe;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "cv_informe_detalle")
@SequenceGenerator(name = "SEQFICHAINFORMEDETALLE", sequenceName = "SEQFICHAINFORMEDETALLE", allocationSize = 1)
public class FichaInformeDetalle implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQFICHAINFORMEDETALLE")
	@Column(name = "IDDETALLE", updatable = false)
	private int idDetalle;
	
	@Column(name = "TIPO")
	private String tipo;
	
	@Column(name = "TEXTO",nullable=false,length=512)
	private String texto;
	
	@ManyToOne
	@JoinColumns({ 
		@JoinColumn(name = "CODCURSO", updatable = false),
		@JoinColumn(name = "CODPERIODO", updatable = false) 
	})
	private FichaInforme fichaInforme;

	
	public int getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(int idDetalle) {
		this.idDetalle = idDetalle;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public FichaInforme getFichaInforme() {
		return fichaInforme;
	}

	public void setFichaInforme(FichaInforme fichaInforme) {
		this.fichaInforme = fichaInforme;
	}

}
