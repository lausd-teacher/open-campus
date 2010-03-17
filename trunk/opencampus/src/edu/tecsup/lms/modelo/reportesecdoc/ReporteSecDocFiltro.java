package edu.tecsup.lms.modelo.reportesecdoc;

public class ReporteSecDocFiltro {

	private String fechaFin1;

	private String fechaFin2;

	private String codigoFamilia;

	private String busquedaSede;

	private String busquedaPeriodo;

	public ReporteSecDocFiltro() {
		super();
	}

	public String getFechaFin1() {
		return fechaFin1;
	}

	public void setFechaFin1(String fechaFin1) {
		this.fechaFin1 = fechaFin1;
	}

	public String getFechaFin2() {
		return fechaFin2;
	}

	public void setFechaFin2(String fechaFin2) {
		this.fechaFin2 = fechaFin2;
	}

	public String getCodigoFamilia() {
		return codigoFamilia;
	}

	public void setCodigoFamilia(String codigoFamilia) {
		this.codigoFamilia = codigoFamilia;
	}

	public String getBusquedaSede() {
		return busquedaSede;
	}

	public void setBusquedaSede(String busquedaSede) {
		this.busquedaSede = busquedaSede;
	}

	public String getBusquedaPeriodo() {
		return busquedaPeriodo;
	}

	public void setBusquedaPeriodo(String busquedaPeriodo) {
		this.busquedaPeriodo = busquedaPeriodo;
	}

}
