package edu.tecsup.lms.modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.GregorianCalendar;

import edu.tecsup.lms.modelo.noticia.Seccion;

public class Noticia extends BaseModelo implements Serializable {

	private static final long serialVersionUID = 4091102569494504482L;

	private Integer idnoticia;
	
	private String titular;
	
	private String cuerpo;
	
	private String sumilla;
	
	private GregorianCalendar fecha;
	
	private String imagen;
	
	private int formato;
	
	private Seccion seccion;
	
	private int estado;
	
	private Collection<ReglaDeServicio> reglaDeServicio;

	public Noticia() {
		super();
	}

	public Collection<ReglaDeServicio> getReglaDeServicio() {
		return reglaDeServicio;
	}

	public void setReglaDeServicio(Collection<ReglaDeServicio> reglaDeServicio) {
		this.reglaDeServicio = reglaDeServicio;
	}

	public String getSumilla() {
		return sumilla;
	}

	public void setSumilla(String sumilla) {
		this.sumilla = sumilla;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public GregorianCalendar getFecha() {
		return fecha;
	}

	public void setFecha(GregorianCalendar fecha) {
		this.fecha = fecha;
	}

	public int getFormato() {
		return formato;
	}

	public void setFormato(int formato) {
		this.formato = formato;
	}

	public Integer getIdnoticia() {
		return idnoticia;
	}

	public void setIdnoticia(Integer idnoticia) {
		this.idnoticia = idnoticia;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Seccion getSeccion() {
		return seccion;
	}

	public void setSeccion(Seccion seccion) {
		this.seccion = seccion;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	
	
}
