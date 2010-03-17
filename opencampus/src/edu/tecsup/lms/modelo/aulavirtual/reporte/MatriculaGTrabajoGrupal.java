package edu.tecsup.lms.modelo.aulavirtual.reporte;

public class MatriculaGTrabajoGrupal {

	private int peso;
	
	private int estado;
	
	private String nota;

	//Auditoria
	
	private int total;
	
	private int entregados;
	
	private int calificados;
	
	private int estudianteTotal;
	
	private int expirados;
	
	private String publicado;
	
	private String fechaActivacion;
	
	private String fechaEntrega;
	
	private int asignados;
	
	private int intervencionTotal;
	
	private int intervencion;
	
	public MatriculaGTrabajoGrupal() {
		super();
	}

	public int getEstudianteTotal() {
		return estudianteTotal;
	}

	public void setEstudianteTotal(int estudianteTotal) {
		this.estudianteTotal = estudianteTotal;
	}

	public int getAsignados() {
		return asignados;
	}

	public void setAsignados(int asignados) {
		this.asignados = asignados;
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