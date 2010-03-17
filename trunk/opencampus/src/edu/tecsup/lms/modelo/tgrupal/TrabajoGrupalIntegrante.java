package edu.tecsup.lms.modelo.tgrupal;

import edu.tecsup.lms.modelo.BaseModelo;
import edu.tecsup.lms.modelo.Matricula;

public class TrabajoGrupalIntegrante extends BaseModelo {
 
	private static final long serialVersionUID = -3387220610137778060L;
	
	private Matricula matricula;
	private String notaOportunidad;
	private String notaCoherencia;
	private String notaInnovacion;
	private String notaParticipacion;
	private String notaPromedio;
	private int debates;
	
	public TrabajoGrupalIntegrante() {
	}

	public int getDebates() {
		return debates;
	}

	public void setDebates(int debates) {
		this.debates = debates;
	}

	public Matricula getUsuarioMatricula() {
		return matricula;
	}

	public void setUsuarioMatricula(Matricula usuarioMatricula) {
		this.matricula = usuarioMatricula;
	}

	public String getNotaCoherencia() {
		return notaCoherencia;
	}

	public void setNotaCoherencia(String notaCoherencia) {
		this.notaCoherencia = notaCoherencia;
	}

	public String getNotaInnovacion() {
		return notaInnovacion;
	}

	public void setNotaInnovacion(String notaInnovacion) {
		this.notaInnovacion = notaInnovacion;
	}

	public String getNotaOportunidad() {
		return notaOportunidad;
	}

	public void setNotaOportunidad(String notaOportunidad) {
		this.notaOportunidad = notaOportunidad;
	}

	public String getNotaParticipacion() {
		return notaParticipacion;
	}

	public void setNotaParticipacion(String notaParticipacion) {
		this.notaParticipacion = notaParticipacion;
	}

	public String getNotaPromedio() {
		return notaPromedio;
	}

	public void setNotaPromedio(String notaPromedio) {
		this.notaPromedio = notaPromedio;
	}
}
