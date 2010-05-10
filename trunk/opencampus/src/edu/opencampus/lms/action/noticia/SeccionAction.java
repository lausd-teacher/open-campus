package edu.opencampus.lms.action.noticia;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import edu.opencampus.lms.action.BaseAction;
import edu.opencampus.lms.excepcion.ActionException;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.noticia.Seccion;
import edu.opencampus.lms.service.NoticiaService;

public class SeccionAction extends BaseAction{

	private static final long serialVersionUID = -4649824156112980714L;

	private NoticiaService noticiaService;
	
	public void setNoticiaService(NoticiaService noticiaService) {
		this.noticiaService = noticiaService;
	}
	
	private Collection<Seccion> secciones;

	public Collection<Seccion> getSecciones() {
		return secciones;
	}
	
	private String idSeccion;
	private String nombre;
	private String cmd;
	
	public void setIdSeccion(String idSeccion) {
		this.idSeccion = idSeccion;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String cargar() throws ActionException{
		log.info("cargar()");
		try {
			secciones = noticiaService.listarSecciones();
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}
	
	public String cargarXML() {
		log.info("cargarXML()");
		try {
			Collection<Seccion> secciones = noticiaService.listarSecciones();
			
			if (!secciones.isEmpty()) {
				getResponse().setContentType("text/xml");
				getResponse().setHeader("Cache-Control", "no-cache");
				PrintWriter out = getResponse().getWriter();
				out.write("<?xml version='1.0' encoding='ISO-8859-1'?>");
				out.write("<secciones>");

				for (Seccion seccion: secciones) {
					out.write("<seccion>");

					out.write("<id>" + seccion.getIdSeccion()+ "</id>");
					out.write("<nombre>" + seccion.getNombre()+ "</nombre>");
					
					out.write("</seccion>");
				}
				out.write("</secciones>");
				out.close();
			}
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
		}
		return SUCCESS;
	}
	
	public String crear() {
		log.info("crear()");
		try {
			if(nombre != null && nombre.trim().length() != 0){
				Seccion seccion = new Seccion();
				seccion.setNombre(nombre);
				noticiaService.crearSeccion(seccion);
			}
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}
		return SUCCESS;
	}
	
	public String eliminar() {
		log.info("eliminar()");
		try {
			noticiaService.eliminarSeccion(Integer.parseInt(idSeccion));
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (NumberFormatException e) {
			log.error(e.toString());
		}
		return SUCCESS;
	}
	
	public String reordenar() {
		log.info("reordenar()");
		try {
			if(idSeccion!=null && cmd!=null){
				if("up".equals(this.cmd))
					noticiaService.modificarIndiceSeccion(true, Integer.parseInt(idSeccion));
				else
					noticiaService.modificarIndiceSeccion(false, Integer.parseInt(idSeccion));
			}
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (NumberFormatException e) {
			log.error(e.toString());
		}
		return SUCCESS;
	}
	
	public String modificar() {
		log.info("modificar()");
		try {
			if(idSeccion != null && nombre != null && nombre.trim().length() != 0){
				Seccion seccion = new Seccion();
				seccion.setIdSeccion(Integer.parseInt(idSeccion));
				seccion.setNombre(nombre);
				noticiaService.modificarSeccion(seccion);
				
				PrintWriter out = getResponse().getWriter();
				out.print("OK");
				out.close();
			}
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (NumberFormatException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
		return NONE;
	}
	
}