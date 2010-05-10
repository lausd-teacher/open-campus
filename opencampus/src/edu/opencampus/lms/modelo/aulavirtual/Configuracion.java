package edu.opencampus.lms.modelo.aulavirtual;

import java.util.GregorianCalendar;

public class Configuracion {

	private int estadoFecha = 0;

	private GregorianCalendar fechaInicio;

	private GregorianCalendar fechaFin;

	private int diasEdicion;

	private int diasRevision;

	private int pesoDialogo;

	private int pesoTIndividual;

	private int pesoTGrupal;

	private int pesoEvaluacion;

	private int pesoPractica;

	private int estadoChat;

	private int estadoMensajes;

	private int estadoRecursos;

	public int getDiasEdicion() {
		return diasEdicion;
	}

	public void setDiasEdicion(int diasEdicion) {
		this.diasEdicion = diasEdicion;
	}

	public int getDiasRevision() {
		return diasRevision;
	}

	public void setDiasRevision(int diasRevision) {
		this.diasRevision = diasRevision;
	}

	public int getEstadoChat() {
		return estadoChat;
	}

	public void setEstadoChat(int estadoChat) {
		this.estadoChat = estadoChat;
	}

	public int getEstadoFecha() {
		return estadoFecha;
	}

	public void setEstadoFecha(int estadoFecha) {
		this.estadoFecha = estadoFecha;
	}

	public int getEstadoMensajes() {
		return estadoMensajes;
	}

	public void setEstadoMensajes(int estadoMensajes) {
		this.estadoMensajes = estadoMensajes;
	}

	public int getEstadoRecursos() {
		return estadoRecursos;
	}

	public void setEstadoRecursos(int estadoRecursos) {
		this.estadoRecursos = estadoRecursos;
	}

	public GregorianCalendar getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(GregorianCalendar fechaFin) {
		this.fechaFin = fechaFin;
	}

	public GregorianCalendar getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(GregorianCalendar fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public int getPesoDialogo() {
		return pesoDialogo;
	}

	public void setPesoDialogo(int pesoDialogo) {
		this.pesoDialogo = pesoDialogo;
	}

	public int getPesoEvaluacion() {
		return pesoEvaluacion;
	}

	public void setPesoEvaluacion(int pesoEvaluacion) {
		this.pesoEvaluacion = pesoEvaluacion;
	}

	public int getPesoPractica() {
		return pesoPractica;
	}

	public void setPesoPractica(int pesoPractica) {
		this.pesoPractica = pesoPractica;
	}

	public int getPesoTGrupal() {
		return pesoTGrupal;
	}

	public void setPesoTGrupal(int pesoTGrupal) {
		this.pesoTGrupal = pesoTGrupal;
	}

	public int getPesoTIndividual() {
		return pesoTIndividual;
	}

	public void setPesoTIndividual(int pesoTIndividual) {
		this.pesoTIndividual = pesoTIndividual;
	}

}
