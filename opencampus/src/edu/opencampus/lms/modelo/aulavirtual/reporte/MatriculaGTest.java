package edu.opencampus.lms.modelo.aulavirtual.reporte;

public class MatriculaGTest {
	
	private String notaMinima;
	
	private String notaMaxima;
	
	private String notaPromedio;
	
	private int veces;

	public MatriculaGTest() {
		super();
	}

	public String getNotaMaxima() {
		return notaMaxima;
	}

	public void setNotaMaxima(String notaMaxima) {
		this.notaMaxima = notaMaxima;
	}

	public String getNotaMinima() {
		return notaMinima;
	}

	public void setNotaMinima(String notaMinima) {
		this.notaMinima = notaMinima;
	}

	public String getNotaPromedio() {
		return notaPromedio;
	}

	public void setNotaPromedio(String notaPromedio) {
		this.notaPromedio = notaPromedio;
	}

	public int getVeces() {
		return veces;
	}

	public void setVeces(int veces) {
		this.veces = veces;
	}
	
}
