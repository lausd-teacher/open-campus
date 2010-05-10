package edu.opencampus.lms.modelo;

import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;

public class Periodo extends BaseModelo {

	private static final long serialVersionUID = -6982851136008908465L;

	private Integer idPeriodo;

	private String nombre;

	private GregorianCalendar fechaInicio;

	private String fechaInicioToString;

	private GregorianCalendar fechaFin;

	private String fechaFinToString;

	private Integer estado; 
	
	private Integer personalizado;

	private Integer diasRevision;

	private Integer diasEdicion;
	
	private GregorianCalendar fechaEdicion;

	private String fechaEdicionToString;

	private GregorianCalendar fechaRevision;

	private String fechaRevisionToString;

	private Integer fechaInicioAnio;
	private Integer fechaInicioMes;
	private Integer fechaInicioDia;
	private Integer fechaFinAnio;
	private Integer fechaFinMes;
	private Integer fechaFinDia;
	
	public Periodo() {
	}
	
	public Periodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	
	public Integer getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public GregorianCalendar getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(GregorianCalendar fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaInicioToString() {
		setFechaInicioToString(Formato.calendarToString(fechaInicio, Constante.FECHA_DIA_MES_ANO));
		return fechaInicioToString;
	}

	private void setFechaInicioToString(String fechaInicioToString) {
		this.fechaInicioToString = fechaInicioToString;
	}

	public GregorianCalendar getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(GregorianCalendar fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFechaFinToString() {
		setFechaFinToString(Formato.calendarToString(fechaFin, Constante.FECHA_DIA_MES_ANO));
		return fechaFinToString;
	}

	private void setFechaFinToString(String fechaFinToString) {
		this.fechaFinToString = fechaFinToString;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getPersonalizado() {
		return personalizado;
	}

	public void setPersonalizado(Integer personalizado) {
		this.personalizado = personalizado;
	}

	public Integer getDiasRevision() {
		return diasRevision;
	}

	public void setDiasRevision(Integer diasRevision) {
		this.diasRevision = diasRevision;
	}

	public Integer getDiasEdicion() {
		return diasEdicion;
	}

	public void setDiasEdicion(Integer diasEdicion) {
		this.diasEdicion = diasEdicion;
	}

	public GregorianCalendar getFechaEdicion() {
		if(diasEdicion != null){
			if(fechaEdicion == null) setFechaEdicion(new GregorianCalendar());
			fechaEdicion.setTime(fechaInicio.getTime());
			fechaEdicion.add(Calendar.DATE, -diasEdicion);
		}
		return fechaEdicion;
	}

	private void setFechaEdicion(GregorianCalendar fechaEdicion) {
		this.fechaEdicion = fechaEdicion;
	}

	public String getFechaEdicionToString() {
		setFechaEdicionToString(Formato.calendarToString(getFechaEdicion(), Constante.FECHA_DIA_MES_ANO));
		return fechaEdicionToString;
	}

	private void setFechaEdicionToString(String fechaEdicionToString) {
		this.fechaEdicionToString = fechaEdicionToString;
	}

	public GregorianCalendar getFechaRevision() {
		if(diasRevision != null){
			if(fechaRevision == null )setFechaRevision(new GregorianCalendar());
			fechaRevision.setTime(fechaFin.getTime());
			fechaRevision.add(Calendar.DATE, diasRevision);
		}
		return fechaRevision;
	}

	private void setFechaRevision(GregorianCalendar fechaRevision) {
		this.fechaRevision = fechaRevision;
	}

	public String getFechaRevisionToString() {
		setFechaRevisionToString(Formato.calendarToString(getFechaRevision(), Constante.FECHA_DIA_MES_ANO));
		return fechaRevisionToString;
	}

	private void setFechaRevisionToString(String fechaRevisionToString) {
		this.fechaRevisionToString = fechaRevisionToString;
	}

	public Integer getFechaInicioAnio() {
		try{
			setFechaInicioAnio(Integer.parseInt(Formato.calendarToString(fechaInicio, Constante.FECHA_ANIO)));
		}catch (Exception e) {}
		return fechaInicioAnio;
	}
	
	public Integer getFechaFinAnio() {
		try{
			setFechaFinAnio(Integer.parseInt(Formato.calendarToString(fechaFin, Constante.FECHA_ANIO)));
		}catch (Exception e) {}
		return fechaFinAnio;
	}
	
	public Integer getFechaInicioMes() {
		try{
			setFechaInicioMes(Integer.parseInt(Formato.calendarToString(fechaInicio, Constante.FECHA_MES)));
		}catch (Exception e) {}
		return fechaInicioMes;
	}
	
	public Integer getFechaFinMes() {
		try{
			setFechaFinMes(Integer.parseInt(Formato.calendarToString(fechaFin, Constante.FECHA_MES)));
		}catch (Exception e) {}
		return fechaFinMes;
	}
	
	public Integer getFechaInicioDia() {
		try{
			setFechaInicioDia(Integer.parseInt(Formato.calendarToString(fechaInicio, Constante.FECHA_DIA)));
		}catch (Exception e) {}
		return fechaInicioDia;
	}
	
	public Integer getFechaFinDia() {
		try{
			setFechaFinDia(Integer.parseInt(Formato.calendarToString(fechaFin, Constante.FECHA_DIA)));
		}catch (Exception e) {}
		return fechaFinDia;
	}
	
	private void setFechaInicioAnio(Integer fechaInicioAnio) {
		this.fechaInicioAnio = fechaInicioAnio;
	}

	private void setFechaInicioMes(Integer fechaInicioMes) {
		this.fechaInicioMes = fechaInicioMes;
	}

	private void setFechaInicioDia(Integer fechaInicioDia) {
		this.fechaInicioDia = fechaInicioDia;
	}

	private void setFechaFinAnio(Integer fechaFinAnio) {
		this.fechaFinAnio = fechaFinAnio;
	}

	private void setFechaFinMes(Integer fechaFinMes) {
		this.fechaFinMes = fechaFinMes;
	}

	private void setFechaFinDia(Integer fechaFinDia) {
		this.fechaFinDia = fechaFinDia;
	}

	public boolean isDisponibleParaEstudiante(){
		if(this.fechaInicio != null && this.fechaFin != null){
			GregorianCalendar now = new GregorianCalendar();
			return !(this.fechaInicio.after(now) || this.fechaFin.before(now));
		}
		return false;
	}
	
	public boolean isDisponibleParaDocente(){
		if(this.getFechaRevision() != null && this.getFechaEdicion() != null){
			GregorianCalendar now = new GregorianCalendar();
			return !(this.getFechaEdicion().after(now) || this.getFechaRevision().before(now));
		}
		return false;
	}
	
	@Override
	public String toString() {
		String ret = "id: " + idPeriodo + " nombre: " + nombre;
		return ret;
	}

	@Override
	public boolean equals(Object pIndef) {
		if(pIndef != null && pIndef instanceof Periodo){
			Periodo p = (Periodo)pIndef;
			if(p.getIdPeriodo() == this.idPeriodo)
				return true;
		}
		return false;
	}
	
}