package edu.opencampus.lms.action.foro;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import edu.opencampus.lms.action.BaseAction;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.Foro;
import edu.opencampus.lms.modelo.ReglaDeServicio;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.service.ForoService;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Util;

public class GestionarForoAction extends BaseAction {
	
	private static final long serialVersionUID = -2846591434717555724L;
	
	private ForoService foroService;
	
	public void setForoService(ForoService foroService) {
		this.foroService = foroService;
	}

	private Collection<Foro> foros;
	
	public Collection<Foro> getForos() {
		return foros;
	}

	private String idForo;
	
	private String cmd;
	
	private String titular;
	
	private String descripcion;
	
	private String icono;
	
	private String reglas;
	
	private String moderadores;
		
	public void setTitular(String titular) {
		this.titular = titular;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public void setReglas(String reglas) {
		this.reglas = reglas;
	}

	public void setModeradores(String moderadores) {
		this.moderadores = moderadores;
	}

	public String getIdForo() {
		return idForo;
	}

	public void setIdForo(String idForo) {
		this.idForo = idForo;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String cargar(){
		log.info("cargar()");
		
		try {
			foros = foroService.listarForos();
			getRequest().setAttribute("SEDES", Util.getSedes());
			getRequest().setAttribute("FAMILIAS", Util.getFamilias());
			getRequest().setAttribute("FORMACIONES", Util.getFormaciones());
			getRequest().setAttribute("SECCIONES", Util.getSecciones());
		} catch (ServiceException e) {
			log.error(e.toString());
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public String crear(){
		log.info("crear(): " + titular);
		
		try {
			
			Usuario usuario = (Usuario) getSession().get(Constante.USUARIO_ACTUAL);
			
			if(usuario != null && titular != null && titular.trim().length()>0 
					&& descripcion != null && descripcion.trim().length()>0){
			
				Foro foro = new Foro();
				foro.setTitulo(titular);
				foro.setDescripcion(descripcion);
				foro.setIcono(Integer.parseInt(icono));
				foro.setEstado(Constante.ESTADO_ACTIVO);
				foro.setCerrado(Constante.ESTADO_INACTIVO);
				foro.setUsuarioCreacion(usuario.getIdUsuario());
				
				// REGLA ********************************************************
				
				log.info("Regla de Servicio: "+reglas);
				Collection<ReglaDeServicio> reglaDeServicio = new ArrayList<ReglaDeServicio>();
				
				if(reglas != null && reglas.trim().length()>0){
				
					String[] rules = reglas.split("/");
					String[] rule = null;
				
					for (int i = 0; i < rules.length; i++) {
						rule = rules[i].split("-");
						ReglaDeServicio rs = new ReglaDeServicio(); 
						rs.setSede(rule[0].charAt(0));
						rs.setFamilia(Integer.parseInt(rule[1]));
						rs.setFormacion(Integer.parseInt(rule[2]));
						rs.setCiclo(Integer.parseInt(rule[3]));
						rs.setSeccion(Integer.parseInt(rule[4]));
						reglaDeServicio.add(rs);
					}
				
				}
				foro.setReglaDeServicio(reglaDeServicio);
				
				// MODERADOR ********************************************************
				
				log.info("Moderadores: "+moderadores);
				Collection<Usuario> modList = new ArrayList<Usuario>();
				
				if(moderadores != null && moderadores.trim().length()>0){
					
					String[] moderador = moderadores.split("/");
					
					for (int i = 0; i < moderador.length; i++) {
						usuario = new Usuario();
						usuario.setIdUsuario(moderador[i]);
						modList.add(usuario);
					}
					
				}
				foro.setModeradores(modList);
				
				//log.info(foro.toString());
				
				foroService.crearForo(foro);
				
			}
		} catch (ServiceException e) {
			log.error(e);
		} catch (NumberFormatException e) {
			log.error(e);
		}
		return SUCCESS;
	}
	
	public String modificar(){
		log.info("modificar(): " + idForo);
		
		try {
			
			Usuario usuario = (Usuario) getSession().get(Constante.USUARIO_ACTUAL);
			
			if(idForo != null && idForo.trim().length()>0 && usuario != null && titular != null && titular.trim().length()>0 
					&& descripcion != null && descripcion.trim().length()>0){
			
				Foro foro = new Foro();
				foro.setIdForo(Integer.parseInt(idForo));
				foro.setTitulo(titular);
				foro.setDescripcion(descripcion);
				foro.setIcono(Integer.parseInt(icono));
				
				// REGLA ********************************************************
				
				log.info("Regla de Servicio: "+reglas);
				Collection<ReglaDeServicio> reglaDeServicio = new ArrayList<ReglaDeServicio>();
				
				if(reglas != null && reglas.trim().length()>0){
				
					String[] rules = reglas.split("/");
					String[] rule = null;
				
					for (int i = 0; i < rules.length; i++) {
						rule = rules[i].split("-");
						ReglaDeServicio rs = new ReglaDeServicio(); 
						rs.setSede(rule[0].charAt(0));
						rs.setFamilia(Integer.parseInt(rule[1]));
						rs.setFormacion(Integer.parseInt(rule[2]));
						rs.setCiclo(Integer.parseInt(rule[3]));
						rs.setSeccion(Integer.parseInt(rule[4]));
						reglaDeServicio.add(rs);
					}
				
				}
				foro.setReglaDeServicio(reglaDeServicio);
				
				// MODERADOR ********************************************************
				
				log.info("Moderadores: "+moderadores);
				Collection<Usuario> modList = new ArrayList<Usuario>();
				
				if(moderadores != null && moderadores.trim().length()>0){
					
					String[] moderador = moderadores.split("/");
					
					for (int i = 0; i < moderador.length; i++) {
						usuario = new Usuario();
						usuario.setIdUsuario(moderador[i]);
						modList.add(usuario);
					}
					
				}
				foro.setModeradores(modList);
				
				//log.info(foro.toString());
				
				foroService.modificarForo(foro);
				
			}
		} catch (ServiceException e) {
			log.error(e);
		} catch (NumberFormatException e) {
			log.error(e);
		}
		return SUCCESS;
	}
	
	public String obtenerForo() {
		log.info("obtenerForo(): " + idForo);
		try {
			
			if(idForo != null){
				
				Foro foro = foroService.obtenerForoPaModificar(Integer.parseInt(idForo));
				//prueba poniendo null a foro a ver si existe una excepciob en tu javascript (si ocurre un error valida si hay la etiqueta foro)
				if(foro != null){
					getResponse().setContentType("text/xml");
					getResponse().setHeader("Cache-Control", "no-cache");
					PrintWriter out = getResponse().getWriter();
					out.write("<?xml version='1.0' encoding='ISO-8859-1'?>");
					out.write("<foro>");
		
					out.write("<id>" + foro.getIdForo()+ "</id>");
					out.write("<titulo><![CDATA[" + foro.getTitulo()+ "]]></titulo>");
					out.write("<descripcion><![CDATA[" + foro.getDescripcion()+ "]]></descripcion>");
					out.write("<icono>" + foro.getIcono()+ "</icono>");
					
					//RelgasDeServicio
					
					for (ReglaDeServicio regla : foro.getReglaDeServicio()) {
						out.write("<regla>");
						
						out.write("<idregla>" + regla.getIdRegla() + "</idregla>");
						out.write("<sede>" + regla.getSede() + "</sede>");
						out.write("<familia>" + regla.getFamilia() + "</familia>");
						out.write("<formacion>" + regla.getFormacion() + "</formacion>");
						out.write("<ciclo>" + regla.getCiclo() + "</ciclo>");
						out.write("<seccion>" + regla.getSeccion() + "</seccion>");
						
						out.write("</regla>");
					}
					
					//Moderadores
					
					for (Usuario usuario : foro.getModeradores()) {
						out.write("<moderador>");
						
						out.write("<idmoderador>" + usuario.getIdUsuario() + "</idmoderador>");
						out.write("<nombre>" + usuario.getUsuarioDato().getNombreCompleto()+ "</nombre>");
						
						out.write("</moderador>");
					}
					
					out.write("</foro>");
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
	
	public String listarCandidatos() {
		log.info("listarCandidatos(): Regla de Servicio = " + reglas);
		try {
			if(reglas != null){
				
				Collection<Usuario> usuarios = new ArrayList<Usuario>();
				
				if(reglas.trim().length()>0){
			
					// REGLA ********************************************************
					
					Collection<ReglaDeServicio> reglaDeServicio = new ArrayList<ReglaDeServicio>();
					
					String[] rules = reglas.split("/");
					String[] rule = null;
					
					for (int i = 0; i < rules.length; i++) {
						rule = rules[i].split("-");
						ReglaDeServicio rs = new ReglaDeServicio(); 
						rs.setSede(rule[0].charAt(0));
						rs.setFamilia(Integer.parseInt(rule[1]));
						rs.setFormacion(Integer.parseInt(rule[2]));
						rs.setCiclo(Integer.parseInt(rule[3]));
						rs.setSeccion(Integer.parseInt(rule[4]));
						reglaDeServicio.add(rs);
					}
					
					usuarios = foroService.listarPotencialesMod(reglaDeServicio);
				}
				
				getResponse().setContentType("text/xml");
				getResponse().setHeader("Cache-Control", "no-cache");
				PrintWriter out = getResponse().getWriter();
				out.write("<?xml version='1.0' encoding='ISO-8859-1'?>");
				out.write("<usuarios>");
				
				for (Usuario usuario : usuarios) {
					out.write("<usuario>");
					out.write("<id>" + usuario.getIdUsuario()+ "</id>");
					out.write("<nombre><![CDATA[" + usuario.getUsuarioDato().getNombreCompleto()+ "]]></nombre>");
					out.write("</usuario>");
					//log.info(usuario.getIdUsuario()+" - "+ usuario.getUsuarioDato().getNombreCompleto());
				}
				
				out.write("</usuarios>");
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
	
	public String cambiarEstado() {
		log.info("cambiarEstado(): " + cmd);
		try {
			if(idForo != null && cmd != null){
				
				if("active".equals(cmd))
					foroService.cambiarEstado(Integer.parseInt(idForo), 1);
				else
					foroService.cambiarEstado(Integer.parseInt(idForo), 0);
				
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
	
	public String cambiarCerrado() {
		log.info("cambiarCerrado(): " + cmd);
		try {
			if(idForo != null && cmd != null){
				
				if("active".equals(cmd))
					foroService.cambiarCerrado(Integer.parseInt(idForo), 1);
				else
					foroService.cambiarCerrado(Integer.parseInt(idForo), 0);
				
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
	
	public String eliminar() {
		log.info("eliminar(): " + idForo);
		try {
			if(idForo != null){
				foroService.eliminar(Integer.parseInt(idForo));
			}
		} catch (ServiceException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		return SUCCESS;
	}
}