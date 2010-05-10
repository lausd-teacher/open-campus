package edu.opencampus.lms.action.jerarquia;

import java.util.Collection;

import edu.opencampus.lms.action.BaseAction;
import edu.opencampus.lms.excepcion.ActionException;
import edu.opencampus.lms.modelo.Jerarquia;
import edu.opencampus.lms.service.JerarquiaService;
import edu.opencampus.lms.util.Constante;

public class JerarquiaAction extends BaseAction {

	private static final long serialVersionUID = -1710487907555149544L;

	private JerarquiaService jerarquiaService;
 
	public void setJerarquiaService(JerarquiaService jerarquiaService) {
		this.jerarquiaService = jerarquiaService;
	}
    
	private Collection<Jerarquia> jerarquias;
		
	public Collection<Jerarquia> getJerarquias() {
		return jerarquias;
	}
	
	private Integer idJerarquia;
	private String nombre;
	private Integer idPredecesor;
	
	private Jerarquia jerarquia;

	public Jerarquia getJerarquia() {
		return jerarquia;
	}

	public void setJerarquia(Jerarquia jerarquia) {
		this.jerarquia = jerarquia;
	}

	public Integer getIdJerarquia() {
		return idJerarquia;
	}

	public void setIdJerarquia(Integer idJerarquia) {
		this.idJerarquia = idJerarquia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getIdPredecesor() {
		return idPredecesor;
	}

	public void setIdPredecesor(Integer idPredecesor) {
		this.idPredecesor = idPredecesor;
	}

	public String buscar() throws Exception{
		log.info("buscar()");
		try{
			
			jerarquias = jerarquiaService.listarJerarquias();
			return SUCCESS; 
			
		}catch(Exception e){
			log.error(e);
			throw new ActionException(e);
		}
	}

	public String nuevo() throws Exception{
		log.info("nuevo()");
		try {
			jerarquias = jerarquiaService.listarJerarquias();
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}
		
	public String crear()  throws Exception{
		log.info("crear()");
		try{
			if(nombre != null && nombre.trim().length()>0){
				Jerarquia jerarquia = new Jerarquia();
				jerarquia.setPadre(new Jerarquia(idPredecesor));
				jerarquia.setNombre(nombre);
				jerarquia.setTipo(Constante.JERARQUIA_TIPO_GENERAL);
		
				jerarquiaService.crear(jerarquia);
			}else{
				nuevo();
				addActionError("El nombre de para la jerarquía es incorrecta.");
				log.error("Nombre no definido.");
				return INPUT;
			}
			
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}


	public String editar() throws Exception{
		log.info("editar()");
		try {
			
			jerarquia = jerarquiaService.obtener(idJerarquia);
			jerarquias = jerarquiaService.listarJerarquias();
			
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}

	public String modificar() throws Exception{
		log.info("modificar()");
		try{
			if(nombre != null && nombre.trim().length()>0){
				Jerarquia jerarquia = new Jerarquia();
				jerarquia.setIdJerarquia(idJerarquia);
				jerarquia.setPadre(new Jerarquia(idPredecesor));
				jerarquia.setNombre(nombre);
		
				jerarquiaService.modificar(jerarquia);
			}else{
				editar();
				addActionError("El nombre de para la jerarquía es incorrecta.");
				log.error("Nombre no definido.");
				return INPUT;
			}
			
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}
	
	public String eliminar() throws Exception{
		log.info("eliminar()");
		try {
			jerarquiaService.eliminar(idJerarquia);
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}



}
