package edu.tecsup.lms.action.noticia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.excepcion.ActionException;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.Jerarquia;
import edu.tecsup.lms.modelo.Noticia;
import edu.tecsup.lms.modelo.Periodo;
import edu.tecsup.lms.modelo.ReglaDeServicio;
import edu.tecsup.lms.modelo.noticia.Seccion;
import edu.tecsup.lms.modelo.usuario.Rol;
import edu.tecsup.lms.service.JerarquiaService;
import edu.tecsup.lms.service.NoticiaService;
import edu.tecsup.lms.service.PeriodoService;
import edu.tecsup.lms.service.UsuarioService;
import edu.tecsup.lms.util.Archivo;
import edu.tecsup.lms.util.Constante;
import edu.tecsup.lms.util.Formato;

public class NoticiaAction extends BaseAction{

	private static final long serialVersionUID = -4152989311696301336L;
	
	private NoticiaService noticiaService;
	
	private UsuarioService usuarioService;
	
	private JerarquiaService jerarquiaService;
	
	private PeriodoService periodoService; 
	
	public void setPeriodoService(PeriodoService periodoService) {
		this.periodoService = periodoService;
	}

	public void setNoticiaService(NoticiaService noticiaService) {
		this.noticiaService = noticiaService;
	}
	
	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public void setJerarquiaService(JerarquiaService jerarquiaService) {
		this.jerarquiaService = jerarquiaService;
	}

	private Collection<Seccion> secciones;

	public Collection<Seccion> getSecciones() {
		return secciones;
	}
	
	private Collection<Noticia> noticias;

	public Collection<Noticia> getNoticias() {
		return noticias;
	}
	
	private Noticia noticia;
	
	public Noticia getNoticia() {
		return noticia;
	}
	
	private Collection<Rol> roles;
	private Collection<Jerarquia> jerarquias;
	private Collection<Periodo> periodos;
	
	public Collection<Periodo> getPeriodos() {
		return periodos;
	}

	public Collection<Rol> getRoles() {
		return roles;
	}

	public Collection<Jerarquia> getJerarquias() {
		return jerarquias;
	}

	public File imagen;
	public String contentType;
	public String filename;
	
	public void setImagen(File imagen) {
		this.imagen = imagen;
	}

	public void setImagenContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setImagenFileName(String filename) {
		this.filename = filename;
	}
	
	private String idnoticia;
	private String cmd;
	private String titular;
	private String sumilla;
	private String fecha;
	private String contenido;
	private String idseccion;
	private String formato;
	private String reglas;
	
	public String getIdnoticia() {
		return idnoticia;
	}

	public void setIdnoticia(String idnoticia) {
		this.idnoticia = idnoticia;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public void setSumilla(String sumilla) {
		this.sumilla = sumilla;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public void setIdseccion(String idseccion) {
		this.idseccion = idseccion;
	}

	public void setReglas(String reglas) {
		this.reglas = reglas;
	}

	public String obtenerUltimasNoticias() {
		return SUCCESS;
	}
	
	public String cargarPortada(){
		log.info("cargarPortada()");
		try {
			noticias = noticiaService.cargarPortada(getUsuarioSession());
		} catch (Exception e) {
			log.error(e.toString());
			return NONE;
		}
		return SUCCESS;
	}
	
	public String cargarTitulares() throws Exception{
		log.info("cargarTitulares()");
		secciones = noticiaService.cargarTitulares(getUsuarioSession());
		return SUCCESS;
	}
	
	public String verNoticiaPublica() throws Exception{
		log.info("verNoticiaPublica(): "+idnoticia);
		if(esMinoticia(Integer.parseInt(idnoticia))){
			noticia = noticiaService.verNoticia(Integer.parseInt(idnoticia));
		}else{
			log.error("Intento de acceso a noticia no permitida.");
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String verImagen() {
		log.info("verImagen()");
		try {
			idnoticia = filename.substring(filename.indexOf('_')+1,filename.indexOf('.'));
			if(getUsuarioSession().getRolPredeterminado().getIdrol()==Constante.ROL_CAMPUS_ADMINISTRADOR 
					|| getUsuarioSession().getRolPredeterminado().getIdrol()==Constante.ROL_CAMPUS_SOPORTE 
					|| esMinoticia(Integer.parseInt(idnoticia))){
				String source = Constante.RUTA_NOTICIA + filename;
				Archivo.downloadImage(filename, source, getResponse());
			}else{
				log.error("Intento de acceso a noticia no permitida.");
			}
		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		return NONE;
	}
	
	public String verImagenPrevia() {
		//log.info("verImagenPrevia()");
		try {
			idnoticia = filename.substring(filename.indexOf('_')+1,filename.indexOf('.'));
			if(esMinoticia(Integer.parseInt(idnoticia))){
				String source = Constante.RUTA_NOTICIA + filename;
				Archivo.downloadResizedFile(filename, source, 64, 0, getResponse());
			}else{
				log.error("Intento de acceso a noticia no permitida.");
			}
		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		return NONE;
	}
	
	private boolean esMinoticia(int idnoticia){
		Set<Integer> noticias = getUsuarioSession().getMisNoticias();
		for (Integer idnoticiaTmp : noticias) {
			if(idnoticiaTmp.intValue() == idnoticia){
				return true;
			}
		}
		return false;
	}
	
	// ADMINISTRACION *****************************************************
	
	public String cargar() throws ActionException{
		log.info("cargar()");
		try {
			secciones = noticiaService.listarSecciones();
			noticias = noticiaService.listar();
			
			roles = usuarioService.listarRoles();
			jerarquias = jerarquiaService.listarJerarquias();
			periodos = periodoService.listarPeriodos();

		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}
	
	public String verNoticia() throws ActionException{
		log.info("verNoticia(): " + idnoticia);
		try {
			if(idnoticia != null){
				noticia = noticiaService.verNoticia(Integer.parseInt(idnoticia));
			}
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}
	
	public String obtenerNoticia() {
		log.info("obtenerNoticia(): " + idnoticia);
		try {
			
			if(idnoticia != null){
				
				Noticia noticia = noticiaService.obtenerNoticia(Integer.parseInt(idnoticia));
				
				if(noticia != null){
					getResponse().setContentType("text/xml");
					getResponse().setHeader("Cache-Control", "no-cache");
					PrintWriter out = getResponse().getWriter();
					out.write("<?xml version='1.0' encoding='ISO-8859-1'?>");
					out.write("<noticia>");
		
					out.write("<id>" + noticia.getIdnoticia()+ "</id>");
					out.write("<titular><![CDATA[" + noticia.getTitular()+ "]]></titular>");
					out.write("<cuerpo><![CDATA[" + noticia.getCuerpo()+ "]]></cuerpo>");
					out.write("<sumilla><![CDATA[" + noticia.getSumilla()+ "]]></sumilla>");
					out.write("<fecha>" + Formato.calendarToString(noticia.getFecha(),Constante.FECHA_DIA_MES_ANO)+ "</fecha>");
					out.write("<imagen>" + noticia.getImagen()+ "</imagen>");
					out.write("<formato>" + noticia.getFormato()+ "</formato>");
					out.write("<idseccion>" + noticia.getSeccion().getIdSeccion()+ "</idseccion>");
					
					//RelgasDeServicio
					
					for (ReglaDeServicio regla : noticia.getReglaDeServicio()) {
						out.write("<regla>");
						
						out.write("<idjerarquia>" + regla.getIdJerarquia() + "</idjerarquia>");
						out.write("<idperiodo>" + regla.getIdPeriodo() + "</idperiodo>");
						out.write("<idrol>" + regla.getIdRol() + "</idrol>");
						
						out.write("</regla>");
					}
					
					out.write("</noticia>");
					out.close();
				}
			}
				
		} catch (NumberFormatException e) {
			log.error(e.toString());
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
		return SUCCESS;
	}
	
	public String crear(){
		log.info("crear(): " + titular);
		
		try {
			if(titular != null && titular.trim().length()>0 
					&& contenido != null && contenido.trim().length()>0
					&& sumilla != null && sumilla.trim().length()>0){
			
				Noticia noticia = new Noticia();
				noticia.setTitular(titular);
				noticia.setFecha(Formato.stringToCalendar(fecha, Constante.FECHA_DIA_MES_ANO));
				noticia.setCuerpo(contenido);
				noticia.setSumilla(sumilla);
				noticia.setSeccion(new Seccion(Integer.parseInt(idseccion)));
				noticia.setFormato(Integer.parseInt(formato));
				noticia.setEstado(Constante.ESTADO_ACTIVO);
				noticia.setUsuarioCreacion(String.valueOf(getUsuarioSession().getId()));
				
				// REGLA ********************************************************
				
				log.info("Regla de Servicio: "+reglas);
				Collection<ReglaDeServicio> reglaDeServicio = new ArrayList<ReglaDeServicio>();
				String[] rules = reglas.split("/");
				String[] rule = null;
				
				for (int i = 0; i < rules.length; i++) {
					rule = rules[i].split("-");
					ReglaDeServicio rs = new ReglaDeServicio(); 
					rs.setIdJerarquia(("0".equals(rule[0]))?null:Integer.parseInt(rule[0]));
					rs.setIdPeriodo(("0".equals(rule[1]))?null:Integer.parseInt(rule[1]));
					rs.setIdRol(("0".equals(rule[2]))?null:Integer.parseInt(rule[2]));
					reglaDeServicio.add(rs);
				}
				
				noticia.setReglaDeServicio(reglaDeServicio);
				
				// IMAGEN *******************************************************
				
				if(imagen != null && imagen.exists()){
					log.info("Publicacion de noticia con imagen: "+filename+" - "+imagen.length()+" bytes");
					String extension = filename.substring(filename.lastIndexOf("."));
					noticia.setImagen(extension);
					
					noticiaService.crearNoticia(noticia);
					
					// Transferencia ****************************************
					
					log.info("Inicia transferencia de archivo... Nombre: "+noticia.getImagen()+" ...");
					String origen = imagen.getAbsolutePath();
					if(!"/".equals(Constante.SLASH))origen = origen.replaceAll("/", Constante.SLASH);
					
					String destino = Constante.RUTA_NOTICIA + noticia.getImagen();
					
					try {
						Archivo.copiarArchivo(origen, destino);
						log.info("Se completa la transferencia.");
					} catch (IOException e) {
						log.info("Error en la transferencia. Se procede a la eliminacion de registro.");
						noticiaService.eliminarNoticia(noticia.getIdnoticia());
						log.info("RollBack Satisfactorio.");
					}
					//********************************************************
					
				}else{
					log.info("Publicacion de noticia sin imagen.");
					noticiaService.crearNoticia(noticia);
				}
				
			}
		} catch (ServiceException e) {
			log.error(e);
		} catch (NumberFormatException e) {
			log.error(e);
		}
		return SUCCESS;
	}
	
	public String modificar(){
		log.info("modificar(): " + titular);
		try {
			
			if(titular != null && titular.trim().length()>0 
					&& contenido != null && contenido.trim().length()>0
					&& sumilla != null && sumilla.trim().length()>0){
			
				Noticia noticia = new Noticia();
				noticia.setIdnoticia(Integer.parseInt(idnoticia));
				noticia.setTitular(titular);
				noticia.setFecha(Formato.stringToCalendar(fecha, Constante.FECHA_DIA_MES_ANO));
				noticia.setCuerpo(contenido);
				noticia.setSumilla(sumilla);
				noticia.setSeccion(new Seccion(Integer.parseInt(idseccion)));
				noticia.setFormato(Integer.parseInt(formato));
				noticia.setUsuarioModificacion(String.valueOf(getUsuarioSession().getId()));
				
				// REGLA ********************************************************
				
				log.info("Regla de Servicio: "+reglas);
				Collection<ReglaDeServicio> reglaDeServicio = new ArrayList<ReglaDeServicio>();
				String[] rules = reglas.split("/");
				String[] rule = null;
				
				for (int i = 0; i < rules.length; i++) {
					rule = rules[i].split("-");
					ReglaDeServicio rs = new ReglaDeServicio(); 
					rs.setIdJerarquia(("0".equals(rule[0]))?null:Integer.parseInt(rule[0]));
					rs.setIdPeriodo(("0".equals(rule[1]))?null:Integer.parseInt(rule[1]));
					rs.setIdRol(("0".equals(rule[2]))?null:Integer.parseInt(rule[2]));
					reglaDeServicio.add(rs);
				}
				
				noticia.setReglaDeServicio(reglaDeServicio);
				
				// IMAGEN *******************************************************
				
				if(imagen != null && imagen.exists()){
					log.info("Modificacion de noticia con imagen: "+filename+" - "+imagen.length()+" bytes");
					String extension = filename.substring(filename.lastIndexOf("."));
					noticia.setImagen(Constante.IMAGEN +Constante.UNDERLINE + Formato.completarCeros(idnoticia, 10) + extension);
					
					// Transferencia ****************************************
					
					log.info("Inicia transferencia de archivo... ID: "+idnoticia+" ...");
					String origen = imagen.getAbsolutePath();
					if(!"/".equals(Constante.SLASH))
						origen = origen.replaceAll("/", Constante.SLASH);
					
					String destino = Constante.RUTA_NOTICIA + noticia.getImagen();
					
					try {
						Archivo.copiarArchivo(origen, destino);
						log.info("Se completa la transferencia.");
						
						noticiaService.modificarNoticia(noticia);
						
					} catch (IOException e) {
						log.info("Error en la transferencia. Se canceló la actualización del registro.");
					}
					//********************************************************
					
				}else{
					log.info("Modificacion de noticia sin imagen.");
					noticiaService.modificarNoticia(noticia);
				}
				
			}
		} catch (ServiceException e) {
			log.error(e);
		} catch (NumberFormatException e) {
			log.error(e);
		}
		return SUCCESS;
	}
	
	public String eliminar() {
		log.info("eliminar()");
		try {
			if(idnoticia != null){
				int id = Integer.parseInt(idnoticia);
				noticiaService.eliminarNoticia(id);
				// Eliminar Archivo *********************
				String nombre = Constante.IMAGEN +Constante.UNDERLINE + Formato.completarCeros(id, 10);
				Archivo.eliminarArchivoFiltrado(Constante.RUTA_NOTICIA , nombre, true);
			}
		} catch (ServiceException e) {
			log.error(e);
		} catch (NumberFormatException e) {
			log.error(e);
		}
		return SUCCESS;
	}
	
	public String cambiarEstado() {
		log.info("cambiarEstado(): " + cmd);
		try {
			if(idnoticia != null && cmd != null){
				
				if("active".equals(cmd))
					noticiaService.cambiarEstado(Integer.parseInt(idnoticia), 1);
				else
					noticiaService.cambiarEstado(Integer.parseInt(idnoticia), 0);
				
				PrintWriter out = getResponse().getWriter();
				out.print("OK");
				out.close();
			}
		} catch (ServiceException e) {
			log.error(e);
		} catch (NumberFormatException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		return NONE;
	}

}