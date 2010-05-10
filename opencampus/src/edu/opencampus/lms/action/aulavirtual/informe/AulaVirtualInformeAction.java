package edu.opencampus.lms.action.aulavirtual.informe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import edu.opencampus.lms.action.BaseAction;
import edu.opencampus.lms.excepcion.ActionException;
import edu.opencampus.lms.modelo.AulaVirtual;
import edu.opencampus.lms.modelo.Periodo;
import edu.opencampus.lms.modelo.ficha.informe.FichaInforme;
import edu.opencampus.lms.modelo.ficha.informe.FichaInformeDetalle;
import edu.opencampus.lms.service.FichaInformeService;
import edu.opencampus.lms.service.PeriodoService;

public class AulaVirtualInformeAction extends BaseAction {

	private static final long serialVersionUID = -1452425602902354405L;

	private String mejora;
	
	private String propuesta;
	
	private String observacion;
	
	private String[] mejoraClone;
	
	private String[] propuestaClone;
	
	private Collection<Periodo> periodos;
	
	private PeriodoService  periodoService;
	
	private FichaInformeService fichaInformeService;
	
	private Collection<FichaInformeDetalle> propuestas;
	
	private Collection<FichaInformeDetalle> mejoras;
	
	private FichaInforme informe;

	private Integer idperiodo;
	
	private Periodo periodo;
	
	public Periodo getPeriodo() {
		return periodo;
	}

	public FichaInforme getInforme() {
		return informe;
	}

	public Integer getIdperiodo() {
		return idperiodo;
	}

	public PeriodoService getPeriodoService() {
		return periodoService;
	}

	public void setPeriodoService(PeriodoService periodoService) {
		this.periodoService = periodoService;
	}

	public void setIdperiodo(Integer idperiodo) {
		this.idperiodo = idperiodo;
	}

	public Collection<Periodo> getPeriodos() {
		return periodos;
	}

	public Collection<FichaInformeDetalle> getMejoras() {
		return mejoras;
	}

	public Collection<FichaInformeDetalle> getPropuestas() {
		return propuestas;
	}

	public void setFichaInformeService(FichaInformeService fichaInformeService) {
		this.fichaInformeService = fichaInformeService;
	}
	
	public String getMejora() {
		return mejora;
	}

	public void setMejora(String mejora) {
		this.mejora = mejora;
	}

	public String[] getMejoraClone() {
		return mejoraClone;
	}

	public void setMejoraClone(String[] mejoraClone) {
		this.mejoraClone = mejoraClone;
	}

	public String getPropuesta() {
		return propuesta;
	}

	public void setPropuesta(String propuesta) {
		this.propuesta = propuesta;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String[] getPropuestaClone() {
		return propuestaClone;
	}

	public void setPropuestaClone(String[] propuestaClone) {
		this.propuestaClone = propuestaClone;
	}

	public String informe() throws Exception {
		log.info("informe()");

		AulaVirtual aula = getAulaVirtualSession();

		informe = fichaInformeService.obtener(aula.getIdCurso(), aula.getIdPeriodo());
		
		mejoras = new ArrayList<FichaInformeDetalle>();
		if(null != informe){
			for (FichaInformeDetalle mejora : informe.getDetalles()) {
				if("M".equals(mejora.getTipo()))mejoras.add(mejora);
			}
		}
		
		propuestas = new ArrayList<FichaInformeDetalle>();
		if(null != informe){
			for (FichaInformeDetalle mejora : informe.getDetalles()) {
				if("P".equals(mejora.getTipo()))propuestas.add(mejora);
			}
		}
		
		return SUCCESS;
	}

	public String guardarInforme() throws Exception {
		log.info("guardarInforme()");
		
		//** validar comentario luego del wform, no hacerlo con wform pues primero valida luego actuliza el text area
		//** validar tamaño de comentario (4k)
		//Validar parametros e informar mediante fielderror -> return input
		//agregar aviso si el usuario intenta cerrar sin guardar cambios en el jsp
		
		//--ya esta
		//foto y datos de usuario no cargar del suaurio actual sino del aula buscar al docente
		//*** maxlength para cadainput
		
		AulaVirtual aula = getAulaVirtualSession();

		FichaInforme informe = new FichaInforme();
		informe.setCodcurso(aula.getIdCurso());
		informe.setCodperiodo(aula.getIdPeriodo());
		informe.setObservacion("<FONT face=Arial size=2>"+observacion+"</FONT>");
		informe.setUsuarioMod(getUsuarioSession().getIdUsuario());
		informe.setFechaMod(new Date());
		informe.setUsuarioCreacion(getUsuarioSession().getIdUsuario());
		informe.setFechaCreacion(new Date());
		
		 Collection<FichaInformeDetalle> detalles = new ArrayList<FichaInformeDetalle>();
		 
		 FichaInformeDetalle d = new FichaInformeDetalle();
		 d.setFichaInforme(informe);
		 d.setTexto(mejora);
		 d.setTipo("M");
		 detalles.add(d);
		
		 if(null != mejoraClone){
			 for (String mejora : mejoraClone) {
				 d = new FichaInformeDetalle();
				 d.setFichaInforme(informe);
				 d.setTexto(mejora);
				 d.setTipo("M");
				 detalles.add(d);
			 }
		 }
		 
		 d = new FichaInformeDetalle();
		 d.setFichaInforme(informe);
		 d.setTexto(propuesta);
		 d.setTipo("P");
		 detalles.add(d);
		 
		 if(null != propuestaClone){
			 for (String propuesta : propuestaClone) {
				 d = new FichaInformeDetalle();
				 d.setFichaInforme(informe);
				 d.setTexto(propuesta);
				 d.setTipo("P");
				 detalles.add(d);
			 }
		 }
		 
		 informe.setDetalles(detalles);
		
		fichaInformeService.eliminar(aula.getIdCurso(),aula.getIdPeriodo());
		 
		fichaInformeService.persistir(informe);
		
		return SUCCESS;
	}
	
	public String cargarHistoricos() throws Exception{
		log.info("cargarHistoricos() IDCURSO: " + getAulaVirtualSession().getIdCurso());
		periodos = fichaInformeService.listarHistoricos(getAulaVirtualSession().getIdCurso());
		return SUCCESS;
	}

	public String historico() throws Exception {
		log.info("historico()" + idperiodo);

		AulaVirtual aula = getAulaVirtualSession();

		if(idperiodo != null){
			informe = fichaInformeService.obtener(aula.getIdCurso(), idperiodo);
			periodo = periodoService.obtener(String.valueOf(idperiodo));
			
			mejoras = new ArrayList<FichaInformeDetalle>();
			if(null != informe){
				for (FichaInformeDetalle mejora : informe.getDetalles()) {
					if("M".equals(mejora.getTipo()))mejoras.add(mejora);
				}
			}
			
			propuestas = new ArrayList<FichaInformeDetalle>();
			if(null != informe){
				for (FichaInformeDetalle mejora : informe.getDetalles()) {
					if("P".equals(mejora.getTipo()))propuestas.add(mejora);
				}
			}
		}else{
			throw new ActionException("Parámetro idperiodo es requerido.");
		}
		
		return SUCCESS;
	}
	
}
