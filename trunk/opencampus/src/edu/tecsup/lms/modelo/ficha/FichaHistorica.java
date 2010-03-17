package edu.tecsup.lms.modelo.ficha;

import java.util.GregorianCalendar;

import edu.tecsup.lms.util.Formato;

public class FichaHistorica {

	private String idMatricula;

	private int idFicha;

	private String nombreFicha;

	private String nombrePeriodo;

	private GregorianCalendar fechaInicio;

	private GregorianCalendar fechaFin;

	private int idRol;

	private int ciclo;

	private String secciones;

	private String formacion;

	private String familia;

	public FichaHistorica() {
		super();
	}

	public String getFamilia() {
		return familia;
	}

	public void setFamilia(String familia) {
		this.familia = familia;
	}

	public String getFormacion() {
		return formacion;
	}

	public void setFormacion(String formacion) {
		this.formacion = formacion;
	}

	public int getCiclo() {
		return ciclo;
	}

	public void setCiclo(int ciclo) {
		this.ciclo = ciclo;
	}

	public String getSecciones() {
		return secciones;
	}

	public void setSecciones(String secciones) {
		this.secciones = secciones;
	}

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public String getIdMatricula() {
		return idMatricula;
	}

	public void setIdMatricula(String idMatricula) {
		this.idMatricula = idMatricula;
	}

	public String getFechaFin() {
		return Formato.getStringDeDate(fechaFin);
	}

	public void setFechaFin(GregorianCalendar fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFechaInicio() {
		return Formato.getStringDeDate(fechaInicio);
	}

	public void setFechaInicio(GregorianCalendar fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public int getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(int idFicha) {
		this.idFicha = idFicha;
	}

	public String getNombreFicha() {
		return nombreFicha;
	}

	public void setNombreFicha(String nombreFicha) {
		this.nombreFicha = nombreFicha;
	}

	public String getNombrePeriodo() {
		return nombrePeriodo;
	}

	public void setNombrePeriodo(String nombrePeriodo) {
		this.nombrePeriodo = nombrePeriodo;
	}

}
