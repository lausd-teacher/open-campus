package edu.opencampus.lms.modelo.aulavirtual.reporte;

public class MatriculaGDialogo {
	
	private int peso;
	
	private String nota;
	
	private int intervencionTotal;
	
	private int intervencion;

	public MatriculaGDialogo() {
		super();
	}

	public int getIntervencion() {
		return intervencion;
	}

	public void setIntervencion(int intervencion) {
		this.intervencion = intervencion;
	}

	public int getIntervencionTotal() {
		return intervencionTotal;
	}

	public void setIntervencionTotal(int intervencionTotal) {
		this.intervencionTotal = intervencionTotal;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	

}
