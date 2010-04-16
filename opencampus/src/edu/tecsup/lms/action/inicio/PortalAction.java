package edu.tecsup.lms.action.inicio;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

import com.lowagie.tools.plugins.Concat;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.excepcion.ActionException;
import edu.tecsup.lms.modelo.AulaVirtual;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.modelo.portal.Servicio;
import edu.tecsup.lms.service.FichaService;
import edu.tecsup.lms.service.PortalService;
import edu.tecsup.lms.service.UsuarioService;
import edu.tecsup.lms.util.Constante;
import edu.tecsup.lms.util.UsuariosConectados;

public class PortalAction extends BaseAction {

	private static final long serialVersionUID = 2895336110634130811L;

	private UsuarioService usuarioService;
	
	private PortalService portalService;
	
	private FichaService fichaService;
	
	private String servicio;

	private Integer estado;
	
	private String cadena;
	
	private Collection<Servicio> portal;
	
	private Collection<AulaVirtual> cursos;
	
	private Collection<Usuario> usuarios;

	private Map<Integer,Usuario> conectados;
	
	public Collection<Usuario> getUsuarios() {
		return usuarios;
	}

	public Map<Integer, Usuario> getConectados() {
		return conectados;
	}

	public Collection<Servicio> getPortal() {
		return portal;
	}

	public Collection<AulaVirtual> getCursos() {
		return cursos;
	}

	public void setFichaService(FichaService fichaService) {
		this.fichaService = fichaService;
	}

	public void setPortal(Collection<Servicio> portal) {
		this.portal = portal;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public void setPortalService(PortalService portalService) {
		this.portalService = portalService;
	}

	public String portal() throws ActionException{
		log.info("portal() usuario:"+getUsuarioSession());
		try{
			portal = portalService.obtenerServiciosUsuario(getUsuarioSession().getId());
			
			for (Servicio servicio : portal) {
				if(servicio.getVisible() == Constante.ESTADO_ACTIVO){
					if(Constante.SERVICIO_CURSO.equals(servicio.getNombre())){
						cursos = fichaService.cargarPortada(getUsuarioSession().getId());
					}else if(Constante.SERVICIO_CHAT.equals(servicio.getNombre())){
						conectados = UsuariosConectados.c; 
					}else if(Constante.SERVICIO_NOTICIA.equals(servicio.getNombre())){
						
					}else if(Constante.SERVICIO_CUMPLEANOS.equals(servicio.getNombre())){
						usuarios = usuarioService.verCumpleanieros();
					}else if(Constante.SERVICIO_BUZON.equals(servicio.getNombre())){
						
					}else if(Constante.SERVICIO_FOROS.equals(servicio.getNombre())){
						
					}else if(Constante.SERVICIO_AGENDA.equals(servicio.getNombre())){
						
					}else if(Constante.SERVICIO_APUNTES.equals(servicio.getNombre())){
						
					}else if(Constante.SERVICIO_BIBLIOTECA.equals(servicio.getNombre())){
						
					}else{
						throw new ActionException("Servicio desconocido: "+servicio.getNombre());
					}
				}//para las salidas, usar includes de los jsp de portal
			}
			
		}catch (Exception e) {
			log.error(e);
			throw new ActionException(e.getMessage());
		}
		return SUCCESS;
	}
	
	public String cargarCursos() throws ActionException{
		log.info("cargarCursos()");
		try {
			cursos = fichaService.cargarPortada(getUsuarioSession().getId());
		} catch (Exception e) {
			log.error(e.toString());
			//throw new ActionException(e.getMessage());
		}
		return SUCCESS;
	}
	
	public String cargarHonomastico() throws Exception{
		log.info("cargarHonomastico()");
		try {
			usuarios = usuarioService.verCumpleanieros();
		} catch (Exception e) {
			log.error(e.toString());
			//throw new ActionException(e.getMessage());
		}
		return SUCCESS;
	}
	
	public String cargarConectados() throws Exception{
		log.info("cargarConectadosEnChat()");
		try {
			conectados = UsuariosConectados.c; 
		} catch (Exception e) {
			log.error(e.toString());
			//throw new ActionException(e.getMessage());
		}
		return SUCCESS;
	}
	
	/*
	 * Mi Configuración
	 */
	
	public String configurarPortal() throws ActionException{
		log.info("configurarPortal() "+getUsuarioSession().getId());
		try{
			portal = portalService.obtenerServiciosUsuarioConfig(getUsuarioSession().getId());
			cadena = Servicio.doServicesToJson(portal);
			log.info(cadena);
			
		}catch (Exception e) {
			log.error(e);
			throw new ActionException(e.getMessage());
		}
		return SUCCESS;
	}

	public String guardarIdioma() throws Exception{
		log.info("guardarIdioma()"+request_locale);
		portalService.guardarIdioma(getUsuarioSession().getId(), request_locale);
		//getSession().setAttribute("WW_TRANS_I18N_LOCALE",request_locale); 
		return SUCCESS;
	}
	
	public String ocultarServicio() throws Exception{
		log.info("ocultarServicio() "+servicio+"-"+estado);
		try{
			PrintWriter out = getResponse().getWriter();
			if(portalService.ocultarServicio(getUsuarioSession().getId(), servicio, estado));
				out.print("OK");
			out.close();
		}catch (Exception e) {
			log.error(e);
		}	
		return NONE;
	}
	
	public String eliminarServicio() throws Exception{
		log.info("eliminarServicio() "+servicio+"-"+estado);
		try{
			PrintWriter out = getResponse().getWriter();
			if(portalService.eliminarServicio(getUsuarioSession().getId(), servicio, estado));
				out.print("OK");
			out.close();
		}catch (Exception e) {
			log.error(e);
		}
		return NONE;
	}
	
	public String grabarPortal() throws Exception{
		log.info("grabarPortal(): "+cadena);
		try{
			
			portalService.grabarPortal(getUsuarioSession().getId(), Servicio.doJsonToServices(cadena));
			
		}catch (Exception e) {
			log.error(e);
		}
		return NONE;
	}
	
	
	
	
	
	
	
	
	
	public String configuracionDefault() throws Exception{
		log.info("configuracionDefault()");
		
		portalService.eliminarConfiguracion(getUsuarioSession().getId());
		
		return SUCCESS;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public String getCadena() {
		return cadena;
	}

	public void setCadena(String cadena) {
		this.cadena = cadena;
	}

}
