package edu.opencampus.lms.modelo.tindividual;

import java.util.Collection;
import java.util.GregorianCalendar;

import edu.opencampus.lms.modelo.BaseModelo;
import edu.opencampus.lms.modelo.Matricula;

public class TrabajoIndividualMatricula extends BaseModelo {

	private static final long serialVersionUID = -7288128806704664616L;

	private int idTrabajo;
	
	private int idFicha;

	private Integer nota;

	private int estado;

	private int expirado;

	private Matricula usuarioReceptor;

	private Collection<TrabajoIndividualInteraccion> interacciones;

	public TrabajoIndividualMatricula() {
	}

	public TrabajoIndividualMatricula(GregorianCalendar fechaCreacion,
			String usuarioCreacion, GregorianCalendar fechaModificacion,
			String usuarioModificacion, int idTrabajo, Integer nota,
			int estado, int expirado, Matricula usuarioReceptor,
			Collection<TrabajoIndividualInteraccion> interacciones) {
		super(fechaCreacion, usuarioCreacion, fechaModificacion,
				usuarioModificacion);
		this.idTrabajo = idTrabajo;
		this.nota = nota;
		this.estado = estado;
		this.expirado = expirado;
		this.usuarioReceptor = usuarioReceptor;
		this.interacciones = interacciones;
	}

	public int getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(int idFicha) {
		this.idFicha = idFicha;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getExpirado() {
		return expirado;
	}

	public void setExpirado(int expirado) {
		this.expirado = expirado;
	}

	public int getIdTrabajo() {
		return idTrabajo;
	}

	public void setIdTrabajo(int idTrabajo) {
		this.idTrabajo = idTrabajo;
	}

	public Collection<TrabajoIndividualInteraccion> getInteracciones() {
		return interacciones;
	}

	public void setInteracciones(
			Collection<TrabajoIndividualInteraccion> interacciones) {
		this.interacciones = interacciones;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

	public Matricula getUsuarioReceptor() {
		return usuarioReceptor;
	}

	public void setUsuarioReceptor(Matricula usuarioReceptor) {
		this.usuarioReceptor = usuarioReceptor;
	}
}
