package edu.tecsup.lms.action.inicio;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.excepcion.ActionException;
import edu.tecsup.lms.modelo.Anotacion;
import edu.tecsup.lms.modelo.AulaVirtual;
import edu.tecsup.lms.modelo.Noticia;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.modelo.correo.Mensaje;
import edu.tecsup.lms.modelo.portal.Servicio;
import edu.tecsup.lms.service.AnotacionService;
import edu.tecsup.lms.service.BuzonService;
import edu.tecsup.lms.service.FichaService;
import edu.tecsup.lms.service.NoticiaService;
import edu.tecsup.lms.service.PortalService;
import edu.tecsup.lms.service.UsuarioService;
import edu.tecsup.lms.util.Constante;
import edu.tecsup.lms.util.UsuariosConectados;

public class PortalAction extends BaseAction {

	private static final long serialVersionUID = 2895336110634130811L;

	private UsuarioService usuarioService;
	
	private NoticiaService noticiaService;
	
	private AnotacionService anotacionService;
	
	private PortalService portalService;
	
	private FichaService fichaService;
	
	private BuzonService buzonService;
	
	private String servicio;

	private Integer estado;
	
	private String cadena;
	
	private Collection<Servicio> portal;
	
	private Collection<AulaVirtual> cursos;
	
	private Collection<Mensaje> mensajes;
	
	private Collection<Usuario> usuarios;

	private Map<Integer,Usuario> conectados;
	
	private Collection<Noticia> noticias;
	
	private List<Anotacion> anotaciones;
	
	public List<Anotacion> getAnotaciones() {
		return anotaciones;
	}

	public void setAnotacionService(AnotacionService anotacionService) {
		this.anotacionService = anotacionService;
	}

	public Collection<Mensaje> getMensajes() {
		return mensajes;
	}

	public Collection<Noticia> getNoticias() {
		return noticias;
	}

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

	public void setBuzonService(BuzonService buzonService) {
		this.buzonService = buzonService;
	}

	public void setNoticiaService(NoticiaService noticiaService) {
		this.noticiaService = noticiaService;
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
			
			/*for (Servicio servicio : portal) {
				if(servicio.getVisible() == Constante.ESTADO_ACTIVO){
					if(Constante.SERVICIO_CURSO.equals(servicio.getId())){
						cursos = fichaService.cargarPortada(getUsuarioSession().getId());
					}else if(Constante.SERVICIO_CHAT.equals(servicio.getId())){
						conectados = UsuariosConectados.c; 
					}else if(Constante.SERVICIO_NOTICIA.equals(servicio.getId())){
						noticias = noticiaService.cargarPortada(getUsuarioSession());
					}else if(Constante.SERVICIO_CUMPLEANOS.equals(servicio.getId())){
						usuarios = usuarioService.verCumpleanieros();
					}else if(Constante.SERVICIO_BUZON.equals(servicio.getId())){
						mensajes = buzonService.cargarPortada(getUsuarioSession().getId());
					}else if(Constante.SERVICIO_FOROS.equals(servicio.getId())){
						
					}else if(Constante.SERVICIO_AGENDA.equals(servicio.getId())){
						
					}else if(Constante.SERVICIO_APUNTES.equals(servicio.getId())){
						
					}else if(Constante.SERVICIO_BIBLIOTECA.equals(servicio.getId())){
						
					}else if(Constante.SERVICIO_ENLACES.equals(servicio.getId())){
						
					}else{
						throw new ActionException("Servicio desconocido: "+servicio.getId());
					}
				}//para las salidas, usar includes de los jsp de portal
			}*/
		}catch (Exception e) {
			log.error(e);
			throw new ActionException(e.getMessage());
		}
		return SUCCESS;
	}
	
	public String cargarServicio() throws Exception{
		log.info("cargarServicio()"+servicio);
		try {
			if(Constante.SERVICIO_CURSO.equals(servicio)){
				cursos = fichaService.cargarPortada(getUsuarioSession().getId());
			}else if(Constante.SERVICIO_CHAT.equals(servicio)){
				conectados = UsuariosConectados.c; 
			}else if(Constante.SERVICIO_NOTICIA.equals(servicio)){
				noticias = noticiaService.cargarPortada(getUsuarioSession());
			}else if(Constante.SERVICIO_CUMPLEANOS.equals(servicio)){
				usuarios = usuarioService.verCumpleanieros();
			}else if(Constante.SERVICIO_BUZON.equals(servicio)){
				mensajes = buzonService.cargarPortada(getUsuarioSession().getId());
			}else if(Constante.SERVICIO_FOROS.equals(servicio)){
				
			}else if(Constante.SERVICIO_AGENDA.equals(servicio)){
				
			}else if(Constante.SERVICIO_APUNTES.equals(servicio)){
				anotaciones = anotacionService.cargarPortada(getUsuarioSession().getId());
			}else if(Constante.SERVICIO_BIBLIOTECA.equals(servicio)){
				
			}else if(Constante.SERVICIO_ENLACES.equals(servicio)){
				
			}else{
				throw new ActionException("Servicio desconocido: "+servicio);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			//response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return NONE;
		}
		return SUCCESS;
	}
	
	public String cargarCursos() throws Exception{
		log.info("cargarCursos()");
		try {
			cursos = fichaService.cargarPortada(getUsuarioSession().getId());
		} catch (Exception e) {
			log.error(e.toString());
			return NONE;
		}
		return SUCCESS;
	}
	
	public String cargarPortada() throws Exception{
		log.info("cargarPortada()");
		try {
			anotaciones = anotacionService.cargarPortada(getUsuarioSession().getId());
		} catch (Exception e) {
			log.error(e.toString());
			return NONE;
		}
		return SUCCESS;
	}
	
	public String cargarHonomasticos() throws Exception{
		log.info("cargarHonomasticos()");
		try {
			usuarios = usuarioService.verCumpleanieros();
		} catch (Exception e) {
			log.error(e.toString());
			return NONE;
		}
		return SUCCESS;
	}
	
	public String cargarConectados() throws ActionException{
		log.info("cargarConectadosEnChat()");
		try {
			conectados = UsuariosConectados.c; 
		} catch (Exception e) {
			log.error(e.toString());
			return NONE;
		}
		return SUCCESS;
	}
	
	public String cargarNoticias() throws ActionException{
		log.info("cargarNoticias()");
		try {
			noticias = noticiaService.cargarPortada(getUsuarioSession());
		} catch (Exception e) {
			log.error(e.toString());
			return NONE;
		}
		return SUCCESS;
	}
	
	public String cargarMensajes() throws ActionException{
		log.info("cargarMensajes()");
		try {
			mensajes = buzonService.cargarPortada(getUsuarioSession().getId());
		} catch (Exception e) {
			log.error(e.toString());
			return NONE;
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

	public String guardarIdioma() throws ActionException{
		log.info("guardarIdioma()"+request_locale);
		try{
			
			portalService.guardarIdioma(getUsuarioSession().getId(), request_locale);
			//getSession().setAttribute("WW_TRANS_I18N_LOCALE",request_locale); 
			
		}catch (Exception e) {
			log.error(e);
			throw new ActionException(e.getMessage());
		}
		return SUCCESS;
	}
	
	public String ocultarServicio() throws ActionException{
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
	
	public String eliminarServicio() throws ActionException{
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
	
	public String grabarPortal() throws ActionException{
		log.info("grabarPortal(): "+cadena);
		try{
			
			portalService.grabarPortal(getUsuarioSession().getId(), Servicio.doJsonToServices(cadena));
			
		}catch (Exception e) {
			log.error(e);
		}
		return NONE;
	}
	
	
	
	
	
	
	
	
	
	public String configuracionDefault() throws ActionException{
		log.info("configuracionDefault()");
		
//		portalService.eliminarConfiguracion(getUsuarioSession().getId());
		
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
