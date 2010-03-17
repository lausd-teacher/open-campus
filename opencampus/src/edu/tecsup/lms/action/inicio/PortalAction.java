package edu.tecsup.lms.action.inicio;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.modelo.Noticia;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.modelo.correo.Mensaje;
import edu.tecsup.lms.modelo.ficha.curso.Ficha;
import edu.tecsup.lms.modelo.portal.Servicio;
import edu.tecsup.lms.service.BuzonService;
import edu.tecsup.lms.service.FichaService;
import edu.tecsup.lms.service.NoticiaService;
import edu.tecsup.lms.service.PortalService;
import edu.tecsup.lms.service.UsuarioService;
import edu.tecsup.lms.util.Constante;

public class PortalAction extends BaseAction {

	private static final long serialVersionUID = 2895336110634130811L;

	private PortalService portalService;
	
	private FichaService fichaService;
	
	private BuzonService buzonService;
	
	private NoticiaService noticiaService;
	
	private UsuarioService usuarioService;
	
	private Collection<Usuario> cumples;
	
	private Collection<Noticia> noticias;
	
	private Collection<Mensaje> mensajes;
	
	private Collection<Ficha> cursos;

	private String[] principal_col_0;

	private String[] principal_col_1;

	private String[] principal_col_2;

	private String[] principal_col_3;

	private String stringServicio;

	private String estadoServicio;
	
	private Collection<Servicio> portal;

	public Collection<Usuario> getCumples() {
		return cumples;
	}

	public void setCumples(Collection<Usuario> cumples) {
		this.cumples = cumples;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public Collection<Servicio> getPortal() {
		return portal;
	}

	public void setPortal(Collection<Servicio> portal) {
		this.portal = portal;
	}

	public void setPortalService(PortalService portalService) {
		this.portalService = portalService;
	}

	public String portal() throws Exception{
		log.info("portal() usuario:"+getUsuarioSession());
				
		//Eliminar cuando soluciones ajax cerrar aula
//		if (null != getAulaVirtualSession() && (getAulaVirtualSession().getTestActual() == null 
//				|| getAulaVirtualSession().getTestActual().getIdNotaTest() == 0)) {
//			getSession().remove(Constante.AULA_ACTUAL);
//		}
		
		// SERVICIO *************************************************
		if(getUsuarioSession().getCompacto() == Constante.ESTADO_ACTIVO){ //CAMBIAR DE POSCICION
//			cursos = fichaService.obtenerFichasUsuarioServicio(getUsuarioSession());
			mensajes = buzonService.cargarPortada(getUsuarioSession().getId());
			cumples = usuarioService.verCumpleanieros();
			noticias = noticiaService.cargarPortada(getUsuarioSession());

			return "success_min";
		}else{
			portal = portalService.obtenerServicioUsuario(getUsuarioSession().getId());
			return SUCCESS;
		}
			
	}

	public String guardarIdioma() throws Exception{
		log.info("guardarIdioma()"+request_locale);
		portalService.guardarIdioma(getUsuarioSession().getId(), request_locale);
		//getSession().setAttribute("WW_TRANS_I18N_LOCALE",request_locale); 
		return SUCCESS;
	}
	
	public String grabarPortal() throws Exception{
		log.info("grabarPortal()");
		if (null == principal_col_0) {
			principal_col_0 = new String[0];
		}
		if (null == principal_col_1) {
			principal_col_1 = new String[0];
		}
		if (null == principal_col_2) {
			principal_col_2 = new String[0];
		}
		if (null == principal_col_3) {
			principal_col_3 = new String[0];
		}
		Collection<String[]> col_final = new ArrayList<String[]>();
		col_final.add(principal_col_0);
		col_final.add(principal_col_1);
		col_final.add(principal_col_2);
		col_final.add(principal_col_3);
		
		portalService.guardar(getUsuarioSession().getId(), col_final);
	
		return NONE;
	}
	
	

	public String grabarPortalVisible() throws Exception{
		log.info("grabarPortalVisible()");
		
		PrintWriter out = getResponse().getWriter();
		int valor = portalService.guardarPortalVisible(getUsuarioSession().getId(), stringServicio, estadoServicio);
		out.print(valor);
		out.close();
			
		return NONE;
	}

	public String grabarPortalEliminado() throws Exception{
		log.info("grabarPortalEliminado()");
		
		PrintWriter out = getResponse().getWriter();
		int valor = portalService.guardarPortalEliminado(getUsuarioSession().getId(), stringServicio,estadoServicio);
		out.print(valor);
		out.close();
		
		return NONE;
	}

	public String configuracionPortal() throws Exception{
		log.info("configuracionPortal()");
		
		portal = portalService.obtenerServicios(getUsuarioSession().getId());
		
		return SUCCESS;
	}

	public String configuracionDefault() throws Exception{
		log.info("configuracionDefault()");
		
		portalService.eliminarConfiguracion(getUsuarioSession().getId());
		
		return SUCCESS;
	}
	
	public void setPrincipal_col_0(String[] principal_col_0) {
		this.principal_col_0 = principal_col_0;
	}

	public void setPrincipal_col_1(String[] principal_col_1) {
		this.principal_col_1 = principal_col_1;
	}

	public void setPrincipal_col_2(String[] principal_col_2) {
		this.principal_col_2 = principal_col_2;
	}

	public void setPrincipal_col_3(String[] principal_col_3) {
		this.principal_col_3 = principal_col_3;
	}

	public void setStringServicio(String stringServicio) {
		this.stringServicio = stringServicio;
	}

	public void setEstadoServicio(String estadoServicio) {
		this.estadoServicio = estadoServicio;
	}

	public void setFichaService(FichaService fichaService) {
		this.fichaService = fichaService;
	}

	public void setCursos(Collection<Ficha> cursos) {
		this.cursos = cursos;
	}

	public void setBuzonService(BuzonService buzonService) {
		this.buzonService = buzonService;
	}

	public void setMensajes(Collection<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	public Collection<Mensaje> getMensajes() {
		return mensajes;
	}

	public Collection<Ficha> getCursos() {
		return cursos;
	}

	public void setNoticiaService(NoticiaService noticiaService) {
		this.noticiaService = noticiaService;
	}

	public void setNoticias(Collection<Noticia> noticias) {
		this.noticias = noticias;
	}

	public Collection<Noticia> getNoticias() {
		return noticias;
	}

}
