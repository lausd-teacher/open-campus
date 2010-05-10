package edu.opencampus.lms.action.periodo;

import java.util.Collection;
import java.util.GregorianCalendar;

import edu.opencampus.lms.action.BaseAction;
import edu.opencampus.lms.excepcion.ActionException;
import edu.opencampus.lms.modelo.Periodo;
import edu.opencampus.lms.service.PeriodoService;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;

public class PeriodoAction extends BaseAction {

	private static final long serialVersionUID = -1710487907555149544L;

	private PeriodoService periodoService;
	
	private Integer idPeriodo;
	private String nombre;
	private Integer diasEdicion;
	private Integer diasRevision;
	private String fechaInicio;
	private String fechaFin;
	
	private Periodo periodo;
	private Collection<Periodo> periodos;

	public Integer getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public Collection<Periodo> getPeriodos() {
		return periodos;
	}

	public void setPeriodoService(PeriodoService periodoService) {
		this.periodoService = periodoService;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getDiasEdicion() {
		return diasEdicion;
	}

	public void setDiasEdicion(Integer diasEdicion) {
		this.diasEdicion = diasEdicion;
	}

	public Integer getDiasRevision() {
		return diasRevision;
	}

	public void setDiasRevision(Integer diasRevision) {
		this.diasRevision = diasRevision;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String buscar() throws ActionException{
		log.info("buscar()");
		try{
			periodos = periodoService.listarPeriodos();
		}catch(Exception e){
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS; 
	}
	
	public String nuevo() throws ActionException{
		log.info("nuevo()");
		return SUCCESS;
	}
	
	public String crear() throws ActionException{
		log.info("crear()");
		try{
			GregorianCalendar finicio = null;
			GregorianCalendar ffin = null;
			if(nombre != null && nombre.trim().length()>0
					&& fechaInicio != null && fechaInicio.trim().length()>0
					&& fechaFin != null && fechaFin.trim().length()>0
					&& (finicio = Formato.stringToCalendar(fechaInicio+" 00:00:00", Constante.FECHA_DIA_MES_ANO_HORA_MI_SEG)) != null
					&& (ffin = Formato.stringToCalendar(fechaFin+" 23:59:59", Constante.FECHA_DIA_MES_ANO_HORA_MI_SEG)) != null){
				
				if(!periodoService.esDuplicado(nombre)){
					
					if(finicio.before(ffin)){
					
						Periodo p = new Periodo();
						p.setNombre(nombre);
						p.setFechaInicio(finicio);
						p.setFechaFin(ffin);
						p.setDiasEdicion(diasEdicion);
						p.setDiasRevision(diasRevision);
						p.setPersonalizado(Constante.ESTADO_INACTIVO);
					
						periodoService.crear(p);
					
					}else{
						nuevo();
						addActionError("La fecha de inicio no puede ser después de de la fecha fin.");
						log.error("Fechas inconsistentes.");
						return INPUT;
					}
					
				}else{
					nuevo();
					addActionError("El nombre para el periodo ya existe en la base de datos.");
					log.error("Nombre duplicado.");
					return INPUT;
				}
				
			}else{
				nuevo();
				addActionError("Debe completar todo el formulario");
				log.error("Algunos de los parametros no fueron entregados.");
				return INPUT;
			}
				
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}
	
	public String editar() throws ActionException{
		log.info("editar()");
		try {
			periodo = periodoService.obtener(idPeriodo);
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}	
		return SUCCESS;
	}
	
	public String modificar()throws ActionException{
		log.info("editar()");
		try {
			
			GregorianCalendar finicio = null;
			GregorianCalendar ffin = null;
			if(idPeriodo != null && nombre != null && nombre.trim().length()>0
					&& fechaInicio != null && fechaInicio.trim().length()>0
					&& fechaFin != null && fechaFin.trim().length()>0
					&& (finicio = Formato.stringToCalendar(fechaInicio+" 00:00:00", Constante.FECHA_DIA_MES_ANO_HORA_MI_SEG)) != null
					&& (ffin = Formato.stringToCalendar(fechaFin+" 23:59:59", Constante.FECHA_DIA_MES_ANO_HORA_MI_SEG)) != null){
				
				if(!periodoService.esDuplicado(nombre)){
					
					if(finicio.before(ffin)){
		
						Periodo p = new Periodo();
						p.setIdPeriodo(idPeriodo);
						p.setNombre(nombre);
						p.setFechaInicio(finicio);
						p.setFechaFin(ffin);
						p.setDiasEdicion(diasEdicion);
						p.setDiasRevision(diasRevision);		
					
						periodoService.modificar(p);
		
					}else{
						nuevo();
						addActionError("La fecha de inicio no puede ser después de de la fecha fin.");
						log.error("Fechas inconsistentes.");
						return INPUT;
					}
					
				}else{
					nuevo();
					addActionError("El nombre para el periodo ya existe en la base de datos.");
					log.error("Nombre duplicado.");
					return INPUT;
				}
				
			}else{
				nuevo();
				addActionError("Debe completar todo el formulario");
				log.error("Algunos de los parametros no fueron entregados.");
				return INPUT;
			}				
							
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}	
		return SUCCESS;
	}
	
	public String eliminar()throws ActionException{
		log.info("editar()");
		try {
			if(idPeriodo != null)
				periodoService.eliminar(idPeriodo);
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}	
		return SUCCESS;
	}
}
