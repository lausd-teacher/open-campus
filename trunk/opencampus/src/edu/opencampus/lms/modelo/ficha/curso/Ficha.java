package edu.opencampus.lms.modelo.ficha.curso;

import java.util.GregorianCalendar;

import edu.opencampus.lms.util.Formato;

public class Ficha {

	private String idFicha;

	private GregorianCalendar fechaInicio;

	private GregorianCalendar fechaFin;

	private GregorianCalendar fechaRevision;

	private GregorianCalendar fechaEdicion;

	private String nombre = "";

	private int dialogos;

	private int trabajoIndividual;

	private int trabajoGrupal;

	private int debate;

	private int laboratorio;

	private String seccion = "";

	private String turno = "";

	private String formacion = "";

	private int rol;

	private String responsableUsuario;

	private String responsableRol;

	private String responsableNombre;

	public Ficha() {
		super();
	}

	public String getResponsableNombre() {
		return responsableNombre;
	}

	public void setResponsableNombre(String responsableNombre) {
		this.responsableNombre = responsableNombre;
	}

	public String getResponsableRol() {
		return responsableRol;
	}

	public void setResponsableRol(String responsableRol) {
		this.responsableRol = responsableRol;
	}

	public String getResponsableUsuario() {
		return responsableUsuario;
	}

	public void setResponsableUsuario(String responsableUsuario) {
		this.responsableUsuario = responsableUsuario;
	}

	public int getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(int laboratorio) {
		this.laboratorio = laboratorio;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public GregorianCalendar getFechaEdicion() {
		return fechaEdicion;
	}

	public String getStringFechaEdicion() {
		return Formato.getStringDeDate(getFechaEdicion());
	}

	public void setFechaEdicion(GregorianCalendar fechaEdicion) {
		this.fechaEdicion = fechaEdicion;
	}

	public GregorianCalendar getFechaFin() {
		return fechaFin;
	}

	public String getStringFechaFin() {
		return Formato.getStringDeDate(getFechaFin());
	}

	public void setFechaFin(GregorianCalendar fechaFin) {
		this.fechaFin = fechaFin;
	}

	public GregorianCalendar getFechaInicio() {
		return fechaInicio;
	}

	public String getStringFechaInicio() {
		return Formato.getStringDeDate(getFechaInicio());
	}

	public void setFechaInicio(GregorianCalendar fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public GregorianCalendar getFechaRevision() {
		return fechaRevision;
	}

	public String getStringFechaRevision() {
		return Formato.getStringDeDate(getFechaRevision());
	}

	public void setFechaRevision(GregorianCalendar fechaRevision) {
		this.fechaRevision = fechaRevision;
	}

	public String getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(String idFicha) {
		this.idFicha = idFicha;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDebate() {
		return debate;
	}

	public void setDebate(int debate) {
		this.debate = debate;
	}

	public int getDialogos() {
		return dialogos;
	}

	public void setDialogos(int dialogos) {
		this.dialogos = dialogos;
	}

	public String getFormacion() {
		return formacion;
	}

	public void setFormacion(String formacion) {
		this.formacion = formacion;
	}

	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}

	public int getTrabajoGrupal() {
		return trabajoGrupal;
	}

	public void setTrabajoGrupal(int trabajoGrupal) {
		this.trabajoGrupal = trabajoGrupal;
	}

	public int getTrabajoIndividual() {
		return trabajoIndividual;
	}

	public void setTrabajoIndividual(int trabajoIndividual) {
		this.trabajoIndividual = trabajoIndividual;
	}

}
