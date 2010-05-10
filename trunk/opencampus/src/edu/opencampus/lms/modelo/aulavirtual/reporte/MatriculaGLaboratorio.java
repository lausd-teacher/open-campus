package edu.opencampus.lms.modelo.aulavirtual.reporte;

public class MatriculaGLaboratorio {

	private int peso;
	
	private int estado;
	
	private String nota;
	
	//Auditoria
	
	private int total;
	
	private int entregados;
	
	private int calificados;
	
	private int expirados;
	
	private String publicado;
	
	private String fechaActivacion;
	
	private String fechaEntrega;
	
	public MatriculaGLaboratorio() {
		super();
	}

	public int getCalificados() {
		return calificados;
	}

	public void setCalificados(int calificados) {
		this.calificados = calificados;
	}

	public int getEntregados() {
		return entregados;
	}

	public void setEntregados(int entregados) {
		this.entregados = entregados;
	}

	public int getExpirados() {
		return expirados;
	}

	public void setExpirados(int expirados) {
		this.expirados = expirados;
	}

	public String getFechaActivacion() {
		return fechaActivacion;
	}

	public void setFechaActivacion(String fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getPublicado() {
		return publicado;
	}

	public void setPublicado(String publicado) {
		this.publicado = publicado;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getEstadoString() {
		if (estado == 1) 
			return	"Ext";
		else if(estado == 2)
			return "Pub";
		else if(estado == 3)
			return "Ent";
		else if(estado == 4)
			return "NP";
		else if(estado == 5)
			return "Apr";
		else if(estado == 6)
			return "Desapr";
		return "";
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
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
