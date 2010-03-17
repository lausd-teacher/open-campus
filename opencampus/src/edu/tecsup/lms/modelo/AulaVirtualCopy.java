package edu.tecsup.lms.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;

import edu.tecsup.lms.modelo.ficha.Unidad;
import edu.tecsup.lms.modelo.usuario.Rol;
import edu.tecsup.lms.util.Constante;
import edu.tecsup.lms.util.Formato;

public class AulaVirtualCopy extends BaseModelo {

	private static final long serialVersionUID = 3195793225870852400L;

	private int idFicha;

	private GregorianCalendar fechaInicio;

	private String fechaInicioToString;

	private GregorianCalendar fechaFin;

	private String fechaFinToString;

	private GregorianCalendar fechaEdicion;

	private String fechaEdicionToString;

	private GregorianCalendar fechaRevision;

	private String fechaRevisionToString;

	private int diasRevision = 0;

	private int diasEdicion = 0;

	private String ciclo = "";

	private String nombreDepartamento;

	private int idDepartamento;

	private String nombreFamilia;

	private int idFamilia;

	private String nombreCurso;

	private int idCurso;

	private int idPeriodo;
	
	private String nombrePeriodo;

	private int idFormacion;

	private String nombreFormacion;
	
	private String docenteCodigo;
	
	private String docenteNombre;

	private Collection<Unidad> unidades = new ArrayList<Unidad>();

	private Rol rol;

	private String turno = "";

	private String aula = "";

	private int idMatricula;

	private String idSilabo;

	private int trabajoGrupalActual;
	
	private int dialogoActual;
	
	private int debateActual;
	
	private Test testActual;

	private String formacionSeccion;

	private int cantidadLaboratorios;
	
	private int cantidadLaboratoriosCalificados;

	private int cantidadDialogos;

	private int cantidadTrabajos;

	private int cantidadGrupales;

	private int cantidadTest;

	private int idEtiqueta;

	private String sede;

	private int periodoMaestro;
	
	private int estadoEjecucion;
	
	private boolean noAuditable;

	public AulaVirtualCopy() {
		super();
	}

	public int getEstadoEjecucion() {
		estadoEjecucion = -1;
		if(new java.util.GregorianCalendar().before(this.fechaInicio)){
			estadoEjecucion = Constante.AULA_ESTADO_NOINICIADO;
		}else if(new java.util.GregorianCalendar().after(this.fechaInicio) && new java.util.GregorianCalendar().before(this.fechaFin)){
			estadoEjecucion = Constante.AULA_ESTADO_EJECUCION;
		}else if(new java.util.GregorianCalendar().after(this.fechaFin)){
			estadoEjecucion = Constante.AULA_ESTADO_HISTORICO;
		}
		return estadoEjecucion;
	}

	public boolean isNoAuditable() {
		return noAuditable;
	}

	public void setNoAuditable(boolean noAuditable) {
		this.noAuditable = noAuditable;
	}

	public String getNombrePeriodo() {
		return nombrePeriodo;
	}

	public void setNombrePeriodo(String nombrePeriodo) {
		this.nombrePeriodo = nombrePeriodo;
	}

	public String getDocenteCodigo() {
		return docenteCodigo;
	}

	public void setDocenteCodigo(String docenteCodigo) {
		this.docenteCodigo = docenteCodigo;
	}

	public String getDocenteNombre() {
		return docenteNombre;
	}

	public void setDocenteNombre(String docenteNombre) {
		this.docenteNombre = docenteNombre;
	}

	public void setEstadoEjecucion(int estadoEjecucion) {
		this.estadoEjecucion = estadoEjecucion;
	}

	public Test getTestActual() {
		return testActual;
	}

	public void setTestActual(Test testActual) {
		this.testActual = testActual;
	}

	public int getDialogoActual() {
		return dialogoActual;
	}

	public void setDialogoActual(int dialogoActual) {
		this.dialogoActual = dialogoActual;
	}

	public int getDebateActual() {
		return debateActual;
	}

	public void setDebateActual(int debateActual) {
		this.debateActual = debateActual;
	}

	public int getPeriodoMaestro() {
		return periodoMaestro;
	}

	public void setPeriodoMaestro(int periodoMaestro) {
		this.periodoMaestro = periodoMaestro;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public int getIdEtiqueta() {
		return idEtiqueta;
	}

	public void setIdEtiqueta(int idEtiqueta) {
		this.idEtiqueta = idEtiqueta;
	}

	public String getFormacionSeccion() {
		return formacionSeccion;
	}

	public void setFormacionSeccion(String formacionSeccion) {
		this.formacionSeccion = formacionSeccion;
	}

	public int getTrabajoGrupalActual() {
		return trabajoGrupalActual;
	}

	public void setTrabajoGrupalActual(int trabajoGrupalActual) {
		this.trabajoGrupalActual = trabajoGrupalActual;
	}

	public int getIdMatricula() {
		return idMatricula;
	}

	public void setIdMatricula(int idMatricula) {
		this.idMatricula = idMatricula;
	}

	public GregorianCalendar getFechaEdicion() {
		return fechaEdicion;
	}

	public void setFechaEdicion(GregorianCalendar fechaEdicion) {
		this.fechaEdicion = fechaEdicion;
	}

	public GregorianCalendar getFechaRevision() {
		return fechaRevision;
	}

	public String getFechaFinToString() {
		return Formato.getStringDeDate(fechaFin);
	}

	public void setFechaFinToString(String fechaFinToString) {
		this.fechaFinToString = fechaFinToString;
	}

	public String getFechaInicioToString() {
		return Formato.getStringDeDate(fechaInicio);
	}

	public void setFechaInicioToString(String fechaInicioToString) {
		this.fechaInicioToString = fechaInicioToString;
	}

	public void setFechaRevision(GregorianCalendar fechaRevision) {
		this.fechaRevision = fechaRevision;
	}

	public String getAula() {
		return aula;
	}

	public void setAula(String aula) {
		this.aula = aula;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public int getDiasEdicion() {
		return diasEdicion;
	}

	public void setDiasEdicion(int diasEdicion) {
		this.diasEdicion = diasEdicion;
	}

	public int getDiasRevision() {
		return diasRevision;
	}

	public void setDiasRevision(int diasRevision) {
		this.diasRevision = diasRevision;
	}

	public GregorianCalendar getFechaFin() {
		return fechaFin;
	}

	public String getStringFechaFin() {
		return Formato.getStringDeDate(fechaFin);
	}

	public void setFechaFin(GregorianCalendar fechaFin) {
		this.fechaFin = fechaFin;
	}

	public GregorianCalendar getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(GregorianCalendar fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getStringFechaInicio() {
		return Formato.getStringDeDate(fechaInicio);
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public int getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(int idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public int getIdFamilia() {
		return idFamilia;
	}

	public void setIdFamilia(int idFamilia) {
		this.idFamilia = idFamilia;
	}

	public int getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(int idFicha) {
		this.idFicha = idFicha;
	}

	public int getIdFormacion() {
		return idFormacion;
	}

	public void setIdFormacion(int idFormacion) {
		this.idFormacion = idFormacion;
	}

	public int getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getNombreCurso() {
		return nombreCurso;
	}

	public void setNombreCurso(String nombreCurso) {
		this.nombreCurso = nombreCurso;
	}

	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	public String getNombreFamilia() {
		return nombreFamilia;
	}

	public void setNombreFamilia(String nombreFamilia) {
		this.nombreFamilia = nombreFamilia;
	}

	public String getNombreFormacion() {
		return nombreFormacion;
	}

	public void setNombreFormacion(String nombreFormacion) {
		this.nombreFormacion = nombreFormacion;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public Collection<Unidad> getUnidades() {
		return unidades;
	}

	public void setUnidades(Collection<Unidad> unidades) {
		this.unidades = unidades;
	}

	public String getIdSilabo() {
		return idSilabo;
	}

	public void setIdSilabo(String idSilabo) {
		this.idSilabo = idSilabo;
	}

	public String getFechaEdicionToString() {
		return Formato.getStringDeDate(fechaEdicion);
	}

	public void setFechaEdicionToString(String fechaEdicionToString) {
		this.fechaEdicionToString = fechaEdicionToString;
	}

	public String getFechaRevisionToString() {
		return Formato.getStringDeDate(fechaRevision);
	}

	public void setFechaRevisionToString(String fechaRevisionToString) {
		this.fechaRevisionToString = fechaRevisionToString;
	}

	public int getCantidadLaboratorios() {
		cantidadLaboratorios = 0;
		for (Unidad u : this.unidades) {
			if (Constante.ESTADO_ACTIVO == u.getRecurso().get(
					Constante.RECURSO_ID_LABORATORIO).getEstado()) {
				cantidadLaboratorios++;
			}
		}
		return cantidadLaboratorios;
	}

	public void setCantidadLaboratorios(int cantidadLaboratorios) {
		this.cantidadLaboratorios = cantidadLaboratorios;
	}

	public int getCantidadDialogos() {
		cantidadDialogos = 0;
		for (Unidad u : this.unidades) {
			if (Constante.ESTADO_ACTIVO == u.getRecurso().get(
					Constante.RECURSO_ID_DIALOGO).getEstado()) {
				cantidadDialogos++;
			}
		}
		return cantidadDialogos;
	}

	public void setCantidadDialogos(int cantidadDialogos) {
		this.cantidadDialogos = cantidadDialogos;
	}

	public int getCantidadGrupales() {
		cantidadGrupales = 0;
		for (Unidad u : this.unidades) {
			if (Constante.ESTADO_ACTIVO == u.getRecurso().get(
					Constante.RECURSO_ID_ACTIVIDAD_GRUPAL).getEstado()) {
				cantidadGrupales++;
			}
		}
		return cantidadGrupales;
	}

	public void setCantidadGrupales(int cantidadGrupales) {
		this.cantidadGrupales = cantidadGrupales;
	}

	public int getCantidadTrabajos() {
		cantidadTrabajos = 0;
		for (Unidad u : this.unidades) {
			if (Constante.ESTADO_ACTIVO == u.getRecurso().get(
					Constante.RECURSO_ID_TRABAJO).getEstado()) {
				cantidadTrabajos++;
			}
		}
		return cantidadTrabajos;
	}

	public void setCantidadTrabajos(int cantidadTrabajos) {
		this.cantidadTrabajos = cantidadTrabajos;
	}

	public int getCantidadTest() {
		cantidadTest = 0;
		for (Unidad u : this.unidades) {
			if (Constante.ESTADO_ACTIVO == u.getRecurso().get(
					Constante.RECURSO_ID_TEST).getEstado()) {
				cantidadTest++;
			}
		}
		return cantidadTest;
	}

	public void setCantidadTest(int cantidadTest) {
		this.cantidadTest = cantidadTest;
	}

	public int getCantidadLaboratoriosCalificados() {
		cantidadLaboratoriosCalificados = 0;
		for (Unidad u : this.unidades) {
			if (Constante.ESTADO_ACTIVO == u.getRecurso().get(
					Constante.RECURSO_ID_LABORATORIO).getEstado() 
					&& u.getRecurso().get(
							Constante.RECURSO_ID_LABORATORIO).getCalifica() == Constante.ESTADO_ACTIVO) {
				cantidadLaboratoriosCalificados++;
			}
		}
		return cantidadLaboratoriosCalificados;
	}

	public void setCantidadLaboratoriosCalificados(
			int cantidadLaboratoriosCalificados) {
		this.cantidadLaboratoriosCalificados = cantidadLaboratoriosCalificados;
	}

	public String toString() {
		String cadena = "";
		cadena += "\n\tidFicha: " + idFicha;
		cadena += "\n\tidMatricula: " + idMatricula;
		cadena += "\n\tfechaInicio: " + Formato.setBaseDatosDeDate(fechaInicio);
		cadena += "\n\tfechaFin: " + Formato.setBaseDatosDeDate(fechaFin);
		cadena += "\n\tfechaEdicion: "
				+ Formato.setBaseDatosDeDate(fechaEdicion);
		cadena += "\n\tfechaRevision: "
				+ Formato.setBaseDatosDeDate(fechaRevision);
		cadena += "\n\tdiasRevision: " + diasRevision;
		cadena += "\n\tdiasEdicion: " + diasEdicion;
		cadena += "\n\tfechaInicioToString: " + fechaInicioToString;
		cadena += "\n\tfechaFinToString: " + fechaFinToString;
		cadena += "\n\tfechaEdicionToString: " + fechaEdicionToString;
		cadena += "\n\tfechaRevisionToString: " + fechaRevisionToString;
		cadena += "\n\tdocenteCodigo: " + docenteCodigo;
		cadena += "\n\tdocenteNombre: " + docenteNombre;
		cadena += "\n\tciclo: " + ciclo;
		cadena += "\n\tnombreDepartamento: " + nombreDepartamento;
		cadena += "\n\tidDepartamento: " + idDepartamento;
		cadena += "\n\tnombreFamilia: " + nombreFamilia;
		cadena += "\n\tidFamilia: " + idFamilia;
		cadena += "\n\tnombreCurso: " + nombreCurso;
		cadena += "\n\tidCurso: " + idCurso;
		cadena += "\n\tidPeriodo: " + idPeriodo;
		cadena += "\n\tnombrePeriodo: " + nombrePeriodo;
		cadena += "\n\tidFormacion: " + idFormacion;
		cadena += "\n\tnombreFormacion: " + nombreFormacion;
		cadena += "\n\tturno: " + turno;
		cadena += "\n\tidSilabo: " + idSilabo;
		cadena += "\n\taula: " + aula;
		cadena += "\n\tformacionSeccion: " + formacionSeccion;
		cadena += "\n\tcantidadLaboratorios: " + getCantidadLaboratorios();
		cadena += "\n\tcantidadDialogos: " + getCantidadDialogos();
		cadena += "\n\tcantidadTrabajos: " + getCantidadTrabajos();
		cadena += "\n\tcantidadGrupales: " + getCantidadGrupales();
		cadena += "\n\tcantidadTest: " + getCantidadTest();
		cadena += "\n\tperiodoMaestro: " + getPeriodoMaestro();
		cadena += "\n\ttestActual: " + this.testActual;
		cadena += "\n\tRol: " + rol;
		cadena += "\n\tTrabajoGrupalActual: " + trabajoGrupalActual;
		cadena += "\n\tUnidades: " + ((this.unidades!=null)?this.unidades.size():"null");
		if(unidades != null && !unidades.isEmpty()){
			for (Unidad u : unidades) {
				cadena += "\n\t\tUnidad: " + u;
			}
		}

		return cadena;
	}

}