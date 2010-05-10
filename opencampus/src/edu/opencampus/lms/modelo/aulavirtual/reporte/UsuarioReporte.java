package edu.opencampus.lms.modelo.aulavirtual.reporte;

public class UsuarioReporte {

	private String idMatricula;

	private String nombre1;

	private String nombre2;

	private String apepaterno;

	private String apematerno;

	private String especialidad;

	private String seccion;

	private String notaLaboratorio;
	
	private int cantidadLaboratorio;
	
	private int cantidadCompletaLaboratorio;

	private String notaDialogo;
	
	private int cantidadDialogo;
	
	private int cantidadCompletaDialogo;

	private String notaTrabajo;
	
	private int cantidadTrabajo;
	
	private int cantidadCompletaTrabajo;

	private String notaGrupal;
	
	private int cantidadGrupal;
	
	private int cantidadCompletaGrupal;
	
	private String notaDebate;
	
	private int cantidadDebate;
	
	private int cantidadCompletaDebate;

	private String notaTest;
	
	private int cantidadTest;
	
	private int cantidadCompletaTest;
	
	private String promedio;
	
	private boolean aprobado;

	public String getPromedio() {
		return promedio;
	}

	public void setPromedio(String promedio) {
		this.promedio = promedio;
	}

	public int getCantidadCompletaDebate() {
		return cantidadCompletaDebate;
	}

	public void setCantidadCompletaDebate(int cantidadCompletaDebate) {
		this.cantidadCompletaDebate = cantidadCompletaDebate;
	}

	public int getCantidadCompletaDialogo() {
		return cantidadCompletaDialogo;
	}

	public void setCantidadCompletaDialogo(int cantidadCompletaDialogo) {
		this.cantidadCompletaDialogo = cantidadCompletaDialogo;
	}

	public int getCantidadCompletaGrupal() {
		return cantidadCompletaGrupal;
	}

	public void setCantidadCompletaGrupal(int cantidadCompletaGrupal) {
		this.cantidadCompletaGrupal = cantidadCompletaGrupal;
	}

	public int getCantidadCompletaLaboratorio() {
		return cantidadCompletaLaboratorio;
	}

	public void setCantidadCompletaLaboratorio(int cantidadCompletaLaboratorio) {
		this.cantidadCompletaLaboratorio = cantidadCompletaLaboratorio;
	}

	public int getCantidadCompletaTest() {
		return cantidadCompletaTest;
	}

	public void setCantidadCompletaTest(int cantidadCompletaTest) {
		this.cantidadCompletaTest = cantidadCompletaTest;
	}

	public int getCantidadCompletaTrabajo() {
		return cantidadCompletaTrabajo;
	}

	public void setCantidadCompletaTrabajo(int cantidadCompletaTrabajo) {
		this.cantidadCompletaTrabajo = cantidadCompletaTrabajo;
	}

	public int getCantidadGrupal() {
		return cantidadGrupal;
	}

	public void setCantidadGrupal(int cantidadGrupal) {
		this.cantidadGrupal = cantidadGrupal;
	}

	public int getCantidadLaboratorio() {
		return cantidadLaboratorio;
	}

	public void setCantidadLaboratorio(int cantidadLaboratorio) {
		this.cantidadLaboratorio = cantidadLaboratorio;
	}

	public int getCantidadTest() {
		return cantidadTest;
	}

	public void setCantidadTest(int cantidadTest) {
		this.cantidadTest = cantidadTest;
	}

	public int getCantidadTrabajo() {
		return cantidadTrabajo;
	}

	public void setCantidadTrabajo(int cantidadTrabajo) {
		this.cantidadTrabajo = cantidadTrabajo;
	}

	public UsuarioReporte() {
		super();
	}

	public int getCantidadDebate() {
		return cantidadDebate;
	}

	public void setCantidadDebate(int cantidadDebate) {
		this.cantidadDebate = cantidadDebate;
	}

	public int getCantidadDialogo() {
		return cantidadDialogo;
	}

	public void setCantidadDialogo(int cantidadDialogo) {
		this.cantidadDialogo = cantidadDialogo;
	}

	public String getNotaDebate() {
		return notaDebate;
	}

	public void setNotaDebate(String notaDebate) {
		this.notaDebate = notaDebate;
	}

	public String getApematerno() {
		return apematerno;
	}

	public void setApematerno(String apematerno) {
		this.apematerno = apematerno;
	}

	public String getApepaterno() {
		return apepaterno;
	}

	public void setApepaterno(String apepaterno) {
		this.apepaterno = apepaterno;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public String getIdMatricula() {
		return idMatricula;
	}

	public void setIdMatricula(String idMatricula) {
		this.idMatricula = idMatricula;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public String getNombre1() {
		return nombre1;
	}

	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}

	public String getNombre2() {
		return nombre2;
	}

	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}

	public String getNotaDialogo() {
		return notaDialogo;
	}

	public void setNotaDialogo(String notaDialogo) {
		this.notaDialogo = notaDialogo;
	}

	public String getNotaGrupal() {
		return notaGrupal;
	}

	public void setNotaGrupal(String notaGrupal) {
		this.notaGrupal = notaGrupal;
	}

	public String getNotaLaboratorio() {
		return notaLaboratorio;
	}

	public void setNotaLaboratorio(String notaLaboratorio) {
		this.notaLaboratorio = notaLaboratorio;
	}

	public String getNotaTest() {
		return notaTest;
	}

	public void setNotaTest(String notaTest) {
		this.notaTest = notaTest;
	}

	public String getNotaTrabajo() {
		return notaTrabajo;
	}

	public void setNotaTrabajo(String notaTrabajo) {
		this.notaTrabajo = notaTrabajo;
	}

	public String getStringnotaDialogo() {
		return String.valueOf(notaDialogo);
	}

	public String getStringnotaGrupal() {
		return String.valueOf(notaGrupal);
	}

	public String getStringnotaLaboratorio() {
		return String.valueOf(notaLaboratorio);
	}

	public String getStringnotaTest() {
		return String.valueOf(notaTest);
	}

	public String getStringnotaTrabajo() {
		return String.valueOf(notaTrabajo);
	}

	public boolean isAprobado() {
		return aprobado;
	}

	public void setAprobado(boolean aprobado) {
		this.aprobado = aprobado;
	}

}