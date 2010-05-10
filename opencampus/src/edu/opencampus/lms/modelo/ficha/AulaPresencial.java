package edu.opencampus.lms.modelo.ficha;

public class AulaPresencial {

	private String horario = "";

	private String id = "";

	public AulaPresencial() {
		super();
	}

	public AulaPresencial(String horario, String id) {
		super();
		this.horario = horario;
		this.id = id;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
