package edu.tecsup.lms.action.inicio;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.excepcion.ActionException;
import edu.tecsup.lms.modelo.portal.Servicio;
import edu.tecsup.lms.service.PortalService;

public class PortalAction extends BaseAction {

	private static final long serialVersionUID = 2895336110634130811L;

	private PortalService portalService;
	
	private String[] principal_col_0;

	private String[] principal_col_1;

	private String[] principal_col_2;

	private String[] principal_col_3;

	private String stringServicio;

	private String estadoServicio;
	
	private Collection<Servicio> portal;

	public Collection<Servicio> getPortal() {
		return portal;
	}

	public void setPortal(Collection<Servicio> portal) {
		this.portal = portal;
	}

	public void setPortalService(PortalService portalService) {
		this.portalService = portalService;
	}

	public String portal() throws ActionException{
		log.info("portal() usuario:"+getUsuarioSession());
		try{
			//portal = portalService.obtenerServiciosUsuario(getUsuarioSession().getId());
		}catch (Exception e) {
			log.error(e);
			throw new ActionException(e.getMessage());
		}
		return SUCCESS;
	}
	
	public String configurarPortal() throws ActionException{
		log.info("configurarPortal() "+getUsuarioSession().getId());
		try{
			portal = portalService.obtenerServiciosUsuarioConfig(getUsuarioSession().getId());
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

}
