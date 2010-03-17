package edu.tecsup.lms.modelo.aulavirtual.reporte;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;

import edu.tecsup.lms.util.Formato;

public class ReporteDetalle {

	private String nombreUsuarioReporte = "";

	private String idUsuario = "";

	private String nombreCompleto = "";

	private String nombreEspecialidad = "";

	private String nombreCurso = "";

	private String formacionSeccion = "";

	private int cantidadIngreso = 0;

	private int cantidadClases = 0;

	private int cantidadUnidades = 0;

	private GregorianCalendar fecha1 = null;

	private GregorianCalendar fecha2 = null;

	private boolean grafico1 = false;

	private boolean grafico2 = false;

	private boolean usuarioSession = false;

	private Collection<MatriculaIngreso> numeroIngreso = new ArrayList<MatriculaIngreso>();

	private Collection<MatriculaRecurso> numeroRecurso = new ArrayList<MatriculaRecurso>();

	private Collection<MatriculaGLaboratorio> recursoLaboratorio = new ArrayList<MatriculaGLaboratorio>();

	private Collection<MatriculaGDialogo> recursoDialogo = new ArrayList<MatriculaGDialogo>();

	private Collection<MatriculaGTrabajo> recursoTrabajo = new ArrayList<MatriculaGTrabajo>();

	private Collection<MatriculaGTrabajoGrupal> recursoTrabajoGrupal = new ArrayList<MatriculaGTrabajoGrupal>();

	private Collection<MatriculaGTest> recursoTest = new ArrayList<MatriculaGTest>();

	private RecursoNotaParcial notaTest = null;

	private RecursoNotaParcial notaTrabajo = null;

	private RecursoNotaParcial notaLaboratorio = null;

	private RecursoNotaParcial notaGrupal = null;

	private String notaFinal = null;

	private int alerta = 0;

	private int totalAlerta = 0;

	private int lectura = 0;

	private int totalLectura = 0;

	public String getNombreUsuarioReporte() {
		return nombreUsuarioReporte;
	}

	public void setNombreUsuarioReporte(String nombreUsuarioReporte) {
		this.nombreUsuarioReporte = nombreUsuarioReporte;
	}

	public ReporteDetalle() {
		super();
	}

	public int getAlerta() {
		return alerta;
	}

	public void setAlerta(int alerta) {
		this.alerta = alerta;
	}

	public int getLectura() {
		return lectura;
	}

	public void setLectura(int lectura) {
		this.lectura = lectura;
	}

	public int getTotalAlerta() {
		return totalAlerta;
	}

	public void setTotalAlerta(int totalAlerta) {
		this.totalAlerta = totalAlerta;
	}

	public int getTotalLectura() {
		return totalLectura;
	}

	public void setTotalLectura(int totalLectura) {
		this.totalLectura = totalLectura;
	}

	public String getNotaFinal() {
		return notaFinal;
	}

	public void setNotaFinal(String notaFinal) {
		this.notaFinal = notaFinal;
	}

	public RecursoNotaParcial getNotaGrupal() {
		return notaGrupal;
	}

	public void setNotaGrupal(RecursoNotaParcial notaGrupal) {
		this.notaGrupal = notaGrupal;
	}

	public RecursoNotaParcial getNotaLaboratorio() {
		return notaLaboratorio;
	}

	public void setNotaLaboratorio(RecursoNotaParcial notaLaboratorio) {
		this.notaLaboratorio = notaLaboratorio;
	}

	public RecursoNotaParcial getNotaTest() {
		return notaTest;
	}

	public void setNotaTest(RecursoNotaParcial notaTest) {
		this.notaTest = notaTest;
	}

	public RecursoNotaParcial getNotaTrabajo() {
		return notaTrabajo;
	}

	public void setNotaTrabajo(RecursoNotaParcial notaTrabajo) {
		this.notaTrabajo = notaTrabajo;
	}

	public int getCantidadUnidades() {
		return cantidadUnidades;
	}

	public void setCantidadUnidades(int cantidadUnidades) {
		this.cantidadUnidades = cantidadUnidades;
	}

	public String getFormacionSeccion() {
		return formacionSeccion;
	}

	public void setFormacionSeccion(String formacionSeccion) {
		this.formacionSeccion = formacionSeccion;
	}

	public int getCantidadClases() {
		return cantidadClases;
	}

	public void setCantidadClases(int cantidadClases) {
		this.cantidadClases = cantidadClases;
	}

	public int getCantidadIngreso() {
		return cantidadIngreso;
	}

	public void setCantidadIngreso(int cantidadIngreso) {
		this.cantidadIngreso = cantidadIngreso;
	}

	public String getNombreCurso() {
		return nombreCurso;
	}

	public void setNombreCurso(String nombreCurso) {
		this.nombreCurso = nombreCurso;
	}

	public String getNombreEspecialidad() {
		return nombreEspecialidad;
	}

	public void setNombreEspecialidad(String nombreEspecialidad) {
		this.nombreEspecialidad = nombreEspecialidad;
	}

	public GregorianCalendar getFecha1() {
		return fecha1;
	}

	public String getStringFecha1() {
		return Formato.getStringDeDate(fecha1);
	}

	public void setFecha1(GregorianCalendar fecha1) {
		this.fecha1 = fecha1;
	}

	public GregorianCalendar getFecha2() {
		return fecha2;
	}

	public String getStringFecha2() {
		return Formato.getStringDeDate(fecha2);
	}

	public void setFecha2(GregorianCalendar fecha2) {
		this.fecha2 = fecha2;
	}

	public boolean isGrafico1() {
		return grafico1;
	}

	public void setGrafico1(boolean grafico1) {
		this.grafico1 = grafico1;
	}

	public boolean isGrafico2() {
		return grafico2;
	}

	public void setGrafico2(boolean grafico2) {
		this.grafico2 = grafico2;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public Collection<MatriculaIngreso> getNumeroIngreso() {
		return numeroIngreso;
	}

	public void setNumeroIngreso(Collection<MatriculaIngreso> numeroIngreso) {
		this.numeroIngreso = numeroIngreso;
	}

	public Collection<MatriculaRecurso> getNumeroRecurso() {
		return numeroRecurso;
	}

	public void setNumeroRecurso(Collection<MatriculaRecurso> numeroRecurso) {
		this.numeroRecurso = numeroRecurso;
	}

	public Collection<MatriculaGDialogo> getRecursoDialogo() {
		return recursoDialogo;
	}

	public void setRecursoDialogo(Collection<MatriculaGDialogo> recursoDialogo) {
		this.recursoDialogo = recursoDialogo;
	}

	public Collection<MatriculaGLaboratorio> getRecursoLaboratorio() {
		return recursoLaboratorio;
	}

	public void setRecursoLaboratorio(
			Collection<MatriculaGLaboratorio> recursoLaboratorio) {
		this.recursoLaboratorio = recursoLaboratorio;
	}

	public Collection<MatriculaGTest> getRecursoTest() {
		return recursoTest;
	}

	public void setRecursoTest(Collection<MatriculaGTest> recursoTest) {
		this.recursoTest = recursoTest;
	}

	public Collection<MatriculaGTrabajo> getRecursoTrabajo() {
		return recursoTrabajo;
	}

	public void setRecursoTrabajo(Collection<MatriculaGTrabajo> recursoTrabajo) {
		this.recursoTrabajo = recursoTrabajo;
	}

	public Collection<MatriculaGTrabajoGrupal> getRecursoTrabajoGrupal() {
		return recursoTrabajoGrupal;
	}

	public void setRecursoTrabajoGrupal(
			Collection<MatriculaGTrabajoGrupal> recursoTrabajoGrupal) {
		this.recursoTrabajoGrupal = recursoTrabajoGrupal;
	}

	public boolean isUsuarioSession() {
		return usuarioSession;
	}

	public void setUsuarioSession(boolean usuarioSession) {
		this.usuarioSession = usuarioSession;
	}
	
	public boolean isDiaDeIngreso(String fecha){
		if(fecha != null){
			for (MatriculaIngreso ingreso : numeroIngreso) {
				if(fecha.equals(Formato.setBaseDatosDeDate(ingreso.getFecha()))){
					return true;
				}
			}
		}
		return false;
	}

}