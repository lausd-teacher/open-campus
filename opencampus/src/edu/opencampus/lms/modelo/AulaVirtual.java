package edu.opencampus.lms.modelo;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import edu.opencampus.lms.modelo.ficha.Unidad;
import edu.opencampus.lms.modelo.usuario.Ingreso;
import edu.opencampus.lms.util.Constante;

public class AulaVirtual extends BaseModelo {

	private static final long serialVersionUID = 3195793225870852400L;
 
	@Deprecated
	private Integer dialogoActual;
	
	@Deprecated
	private Test testActual;
	
	private Integer idFicha;
	
	private Periodo periodo;
	
	private Curso curso;
	
	private Silabo silabo;
	
	private Matricula matriculaActual;
	
	private List<Matricula> docentes = new ArrayList<Matricula>();

	private int estado;
	
	private Matricula docentePrincipal;
	
	private Usuario usuario;
	
	private Ingreso ingreso;
	
	private int prioridad;

	@Deprecated
	public Test getTestActual() {
		return testActual;
	}

	@Deprecated
	public void setTestActual(Test testActual) {
		this.testActual = testActual;
	}

	public Matricula getDocentePrincipal() {
		if(docentes!= null){
			for (Matricula docente : docentes) {
				if(docente.getPrincipal() == Constante.ESTADO_ACTIVO)
					docentePrincipal = docente;
			}
		}
		return docentePrincipal;
	}

	public Ingreso getIngreso() {
		return ingreso;
	}

	public void setIngreso(Ingreso ingreso) {
		this.ingreso = ingreso;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(Integer idFicha) {
		this.idFicha = idFicha;
	}

	public Silabo getSilabo() {
		return silabo;
	}

	public void setSilabo(Silabo silabo) {
		this.silabo = silabo;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Matricula getMatriculaActual() {
		return matriculaActual;
	}

	public void setMatriculaActual(Matricula matriculaActual) {
		this.matriculaActual = matriculaActual;
	}

	public List<Matricula> getDocentes() {
		return docentes;
	}

	public void setDocentes(List<Matricula> docentes) {
		this.docentes = docentes;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}
	
	public GregorianCalendar getFechaInicio(){
		if(this.periodo != null & this.matriculaActual != null){
			if (this.matriculaActual.isDocenteResponsable()) {
				return this.periodo.getFechaEdicion();
			}else{
				return this.periodo.getFechaInicio();
			}
		}
		return null;
	}
	
	public GregorianCalendar getFechaFin(){
		if(this.periodo != null & this.matriculaActual != null){
			if (this.matriculaActual.isDocenteResponsable()) {
				return this.periodo.getFechaRevision();
			}else{
				return this.periodo.getFechaFin();
			}
		}
		return null;
	}
	
	public String getFechaInicioToString(){
		if(this.periodo != null & this.matriculaActual != null){
			if (this.matriculaActual.isDocenteResponsable()) {
				return this.periodo.getFechaEdicionToString();
			}else{
				return this.periodo.getFechaInicioToString();
			}
		}
		return null;
	}
	
	public String getFechaFinToString(){
		if(this.periodo != null & this.matriculaActual != null){
			if (this.matriculaActual.isDocenteResponsable()) {
				return this.periodo.getFechaRevisionToString();
			}else{
				return this.periodo.getFechaFinToString();
			}
		}
		return null;
	}
	
	public boolean isDisponible(){
		if(this.periodo != null && this.matriculaActual != null){
			if (this.matriculaActual.isDocenteResponsable()) {
				return this.periodo.isDisponibleParaDocente();
			}else{
				return this.periodo.isDisponibleParaEstudiante();
			}
		}
		return false;
	}
	
	public int getEstadoEjecucion() {
		int estadoEjecucion = -1;
		GregorianCalendar ahora = new GregorianCalendar();
		if(ahora.before(this.getFechaInicio())){
			estadoEjecucion = Constante.AULA_ESTADO_NOINICIADO;
		}else if(!(ahora.before(this.getFechaInicio()) || ahora.after(this.getFechaFin()))){
			estadoEjecucion = Constante.AULA_ESTADO_EJECUCION;
		}else if(ahora.after(this.getFechaFin())){
			estadoEjecucion = Constante.AULA_ESTADO_HISTORICO;
		}
		return estadoEjecucion;
	}
	
	public int getCantidadTextos() {
		int cantidad = 0;
		for (Unidad u : this.silabo.getUnidades()) {
			if (u.hasTexto()) 
				cantidad++;
		}
		return cantidad;
	}
	
	public int getCantidadRepasos() {
		int cantidad = 0;
		for (Unidad u : this.silabo.getUnidades()) {
			if (u.hasRepaso()) 
				cantidad++;
		}
		return cantidad;
	}
	
	public int getCantidadLaboratorios() {
		int cantidad = 0;
		for (Unidad u : this.silabo.getUnidades()) {
			if (u.hasLaboratorio()) 
				cantidad++;
		}
		return cantidad;
	}

	public int getCantidadDialogos() {
		int cantidad = 0;
		for (Unidad u : this.silabo.getUnidades()) {
			if (u.hasDialogo()) 
				cantidad++;
		}
		return cantidad;
	}

	public int getCantidadGrupales() {
		int cantidad = 0;
		for (Unidad u : this.silabo.getUnidades()) {
			if (u.hasTrabajoGrupal()) 
				cantidad++;
		}
		return cantidad;
	}

	public int getCantidadTrabajos() {
		int cantidad = 0;
		for (Unidad u : this.silabo.getUnidades()) {
			if (u.hasTrabajoIndividual()) 
				cantidad++;
		}
		return cantidad;
	}

	public int getCantidadTest() {
		int cantidad = 0;
		for (Unidad u : this.silabo.getUnidades()) {
			if (u.hasTest()) 
				cantidad++;
		}
		return cantidad;
	}


	@Override
	public boolean equals(Object o) {
		if(o != null && o instanceof AulaVirtual){
			AulaVirtual m = (AulaVirtual) o;
			if (this.idFicha == m.getIdFicha()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuffer cadena = new StringBuffer("ID: "+this.idFicha);
		cadena.append("\n\tPeriodo:\n\t"+this.periodo);
		cadena.append("\n\tCurso:\n\t"+this.curso);
		cadena.append("\n\tSilabo:\n\t"+this.silabo);
		cadena.append("\n\tMatricula:\n\t"+this.matriculaActual);
		return cadena.toString();
	}

	@Deprecated
	public Integer getDialogoActual() {
		return dialogoActual;
	}
	
	@Deprecated
	public void setDialogoActual(Integer dialogoActual) {
		this.dialogoActual = dialogoActual;
	}
	
}