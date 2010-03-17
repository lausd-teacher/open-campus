package edu.tecsup.lms.action.tgrupal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.AulaVirtual;
import edu.tecsup.lms.modelo.TrabajoGrupal;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.modelo.Matricula;
import edu.tecsup.lms.modelo.tgrupal.TrabajoGrupalGrupo;
import edu.tecsup.lms.modelo.tgrupal.TrabajoGrupalIntegrante;
import edu.tecsup.lms.modelo.tgrupal.TrabajoGrupalMensaje;
import edu.tecsup.lms.service.TrabajoGrupalService;
import edu.tecsup.lms.util.Archivo;
import edu.tecsup.lms.util.Constante;
import edu.tecsup.lms.util.Formato;
import edu.tecsup.lms.util.Util;

public class TrabajoGrupalAction extends BaseAction {

	private static final long serialVersionUID = 6987695165251537057L;
	private TrabajoGrupalService tGrupalService;

	public void setTrabajoGrupalService(TrabajoGrupalService tGrupalService) {
		this.tGrupalService = tGrupalService;
	}

	public File file;
	public String contentType;
	public String filename;
	
	public String idUnidad;
	public String idMatricula;
	public String idGrupo;
	
	public String notaOportunidad;
	public String notaCoherencia;
	public String notaInnovacion;
	public String notaParticipacion;
	public String notaFinal;
	
	public String descripcion;
	public String fechaActivacion;
	public String fechaEntrega;
	public Integer ngrupos;
	
	public String cmd;

    public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public Integer getNgrupos() {
		return ngrupos;
	}

	public void setNgrupos(Integer ngrupos) {
		this.ngrupos = ngrupos;
	}

	public String getIdUnidad() {
		return idUnidad;
	}

	public void setNotaCoherencia(String notaCoherencia) {
		this.notaCoherencia = notaCoherencia;
	}

	public void setNotaFinal(String notaFinal) {
		this.notaFinal = notaFinal;
	}

	public void setNotaInnovacion(String notaInnovacion) {
		this.notaInnovacion = notaInnovacion;
	}

	public void setNotaOportunidad(String notaOportunidad) {
		this.notaOportunidad = notaOportunidad;
	}

	public void setNotaParticipacion(String notaParticipacion) {
		this.notaParticipacion = notaParticipacion;
	}

	public void setFechaActivacion(String fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	public void setFile(File file) {
       this.file = file;
    }

    public void setFileContentType(String contentType) {
       this.contentType = contentType;
    }

    public void setFileFileName(String filename) {
       this.filename = filename;
    }
	
	public void setIdUnidad(String idUnidad) {
		this.idUnidad = idUnidad;
	}

	public void setIdMatricula(String idMatricula) {
		this.idMatricula = idMatricula;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String verTrabajoGrupal(){
		log.info("verTrabajoGrupal()");
		
		AulaVirtual aula = (AulaVirtual) getSession().get(Constante.AULA_ACTUAL);
		try{
			if(aula != null && aula.getIdFicha() != 0){
				
				TrabajoGrupal tg = new TrabajoGrupal();
				if(cmd == null){
					tg.setIdFicha(aula.getIdFicha());
					tg.setIdUnidad(Integer.parseInt(idUnidad));
					tg = tGrupalService.obtenerTrabajoGrupal(tg);
				}else{
					tg.setIdTrabajo(aula.getTrabajoGrupalActual());
					tg = tGrupalService.obtenerTrabajoGrupalPorID(tg);
				}
				
				if(tg.getFechaActivacion() != null){
					if(tg.getFechaActivacion().before(new GregorianCalendar())){
						
						TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
						grupo.setIdTrabajo(tg.getIdTrabajo());
						
						//IDGRUPO
						int idGrupo = tGrupalService.obtenerIdGrupo(tg.getIdTrabajo(),aula.getIdMatricula());
						grupo.setIdGrupo(idGrupo);
						
						if(idGrupo != -1){
							grupo = tGrupalService.verMensajes(grupo);
							
							//DEBATE
							grupo.setBanderaDebate(tGrupalService.obtenerEstadoDebate(tg.getIdTrabajo(), idGrupo, aula.getIdMatricula()));
							
							aula.setTrabajoGrupalActual(tg.getIdTrabajo());
						}
						getRequest().setAttribute("TRABAJO_GRUPAL_MENSAJES", grupo);
					
					}else{
						getRequest().setAttribute("NoActivate", "1");
					}
				}
				getRequest().setAttribute("TRABAJO_GRUPAL", tg);
				
				getSession().put("UNIDAD_NOMBRE_TEMP",Util.getNombreUnidad(aula.getUnidades(), idUnidad)); // (solo para nombres de unidades en jsp) esto eliminar y trabajar con objetos de cada recurso en aula
			}
			
		} catch (ServiceException e) {
			log.error(e);
		} catch (NumberFormatException e){
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		
		return SUCCESS;
	}
	
	public String responderMensaje(){
		log.info("responderMensaje()");
		
		Usuario usuario = (Usuario)getSession().get(Constante.USUARIO_ACTUAL);
		AulaVirtual aula = (AulaVirtual) getSession().get(Constante.AULA_ACTUAL);
		
		try {
			if(aula != null && usuario != null && aula.getTrabajoGrupalActual() != 0 && aula.getIdMatricula() != 0 && aula.getPeriodoMaestro() != 0){
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				int idGrupo = tGrupalService.obtenerIdGrupo(aula.getTrabajoGrupalActual(),aula.getIdMatricula());
				
				if(idGrupo != -1){
					grupo.setIdTrabajo(aula.getTrabajoGrupalActual());
					grupo.setIdGrupo(idGrupo);
					grupo.setEstado(aula.getIdMatricula());
					grupo.setBandera(Constante.FLAG_PENDIENTE_DOCENTE);
					
					int[] idMensajeEstado = tGrupalService.obtenerUltimoIdMensaje(grupo);
					
					TrabajoGrupalMensaje mensaje = new TrabajoGrupalMensaje();
					mensaje.setDescripcion(descripcion);
					Matricula matricula = new Matricula();
					matricula.setIdMatricula(String.valueOf(aula.getIdMatricula()));
					mensaje.setUsuarioEmisor(matricula);
					mensaje.setUsuarioCreacion(usuario.getIdUsuario());
					mensaje.setUsuarioModificacion(usuario.getIdUsuario());
					
					//File **********************************************************
					
					if(file != null && file.exists()){
						
						log.info("Envío de Mensaje con adjunto: "+filename+" - "+file.length()+" bytes");
						
						String extension = filename.substring(filename.lastIndexOf("."));		
						String origen = file.getAbsolutePath();
						if(!"/".equals(Constante.SLASH))
							origen = origen.replaceAll("/", Constante.SLASH);
						String directorioDestino = Constante.HOME_TRABAJOS_GRUPALES + Constante.SLASH + aula.getPeriodoMaestro() +
							Constante.SLASH + aula.getIdFicha() + Constante.SLASH + aula.getTrabajoGrupalActual() + Constante.SLASH + idGrupo;
						String nombreDestino = "TG" +"_" + idGrupo + "_" + idMensajeEstado[0] + extension;
						if(idMensajeEstado[1] == 0)
							nombreDestino = "TG" +"_" + idGrupo + "_" + (idMensajeEstado[0]+1) + extension;
						
						Archivo.copiarArchivo(origen, directorioDestino + Constante.SLASH + nombreDestino);
						
						mensaje.setArchivoNombre(nombreDestino);
						mensaje.setArchivoTamanio(String.valueOf(file.length()));
						
					}else{
						log.info("Envío de Mensaje sin adjunto");
					}
					
					//**************************************************************
					
					Collection<TrabajoGrupalMensaje> mensajes = new ArrayList<TrabajoGrupalMensaje>();
					
					if(idMensajeEstado[1] == 0){
						mensaje.setIdMensaje(idMensajeEstado[0]+1);
						mensajes.add(mensaje);
						grupo.setMensajes(mensajes);
						log.info("Mensaje Nuevo");
						tGrupalService.nuevoMensaje(grupo);
					}else{
						mensaje.setIdMensaje(idMensajeEstado[0]);
						mensajes.add(mensaje);
						grupo.setMensajes(mensajes);
						log.info("Modificar Mensaje");
						tGrupalService.modificarMensaje(grupo);
					}
				}
			}
		
		} catch (ServiceException e) {
			log.error(e);
		} catch (NumberFormatException e){
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
			
		return SUCCESS;
	}
	
	public String descargarTrabajoGrupal(){
		log.info("descargarTrabajoGrupal()");
		
		AulaVirtual aula = (AulaVirtual) getSession().get(Constante.AULA_ACTUAL);
		
		try {
			if(aula != null && aula.getTrabajoGrupalActual() != 0 && aula.getIdMatricula() != 0 && aula.getPeriodoMaestro() != 0){
				int idGrupo = tGrupalService.obtenerIdGrupo(aula.getTrabajoGrupalActual(),aula.getIdMatricula());
				String source = Constante.HOME_TRABAJOS_GRUPALES + Constante.SLASH + aula.getPeriodoMaestro() +
				Constante.SLASH + aula.getIdFicha() + Constante.SLASH + aula.getTrabajoGrupalActual() + 
				Constante.SLASH + idGrupo + Constante.SLASH + filename;
				
				Archivo.downloadFile(filename, source, getResponse());
			}else{
				log.error("Error al leer parametros requeridos para la descarga.");
			}
		} catch (ServiceException e) {
			log.error(e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	public String verIntegrantes(){
		log.info("verIntegrantes()");
		
		AulaVirtual aula = (AulaVirtual) getSession().get(Constante.AULA_ACTUAL);
		
		try {
			if(aula != null && aula.getTrabajoGrupalActual() != 0 && aula.getIdMatricula() != 0){
				Collection<Matricula> integrantes = tGrupalService.verIntegrantes(aula.getTrabajoGrupalActual(),aula.getIdMatricula());
				
				if(!integrantes.isEmpty()){
					getResponse().setContentType("text/xml");		
					getResponse().setHeader("Cache-Control", "no-cache");
					PrintWriter out = getResponse().getWriter();
					out.write("<?xml version='1.0' encoding='ISO-8859-1'?>");
					out.write("<integrantes>");
					
					for (Matricula colega : integrantes) {
						getResponse().getWriter().write("<integrante>"+ colega.getNombreCompletoJsp()+ "</integrante>");
					}
					out.write("</integrantes>");
					out.close();
				}
			}
		} catch (ServiceException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		return NONE;
	}
	
	public String renombrarMiGrupo(){
		log.info("renombrarMiGrupo()"+descripcion);
		try {
			AulaVirtual aula = getAulaVirtualSession();
			
			getResponse().setContentType("text/html; charset=ISO-8859-1");
			PrintWriter pw = getResponse().getWriter();
			
			if(aula.getTrabajoGrupalActual() != 0){
				int idGrupo = tGrupalService.obtenerIdGrupo(aula.getTrabajoGrupalActual(),aula.getIdMatricula());
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(idGrupo);
				grupo.setIdTrabajo(aula.getTrabajoGrupalActual());
				grupo.setNombre(descripcion);
				
				tGrupalService.renombrarGrupo(grupo);
				pw.print("OK");
			}
			
		}catch (ServiceException e) {
			log.error(e);
		}catch (NumberFormatException e){
			log.error(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage());
		}
		return NONE;
		
	}
	
//	********************************* CONTROL ***********************************
	
	public String verMensajes(){
		log.info("verMensajes()");
		
		AulaVirtual aula = (AulaVirtual) getSession().get(Constante.AULA_ACTUAL);
		
		try {
			if(aula != null && aula.getTrabajoGrupalActual() != 0){
				
				TrabajoGrupal tg = new TrabajoGrupal();
				tg.setIdTrabajo(aula.getTrabajoGrupalActual());
				tg = tGrupalService.obtenerTrabajoGrupalPorID(tg);
				
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdTrabajo(aula.getTrabajoGrupalActual());
				grupo.setIdGrupo(Integer.parseInt(idGrupo));
				grupo = tGrupalService.verMensajes(grupo);
				
				getRequest().setAttribute("TRABAJO_GRUPAL", tg);
				getRequest().setAttribute("TRABAJO_GRUPAL_MENSAJES", grupo);
				
			}
			
		} catch (ServiceException e) {
			log.error(e);
		} catch (NumberFormatException e){
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return SUCCESS;
	}
	

	public String enviarMensaje(){
		log.info("enviarMensaje()");
		
		Usuario usuario = (Usuario)getSession().get(Constante.USUARIO_ACTUAL);
		AulaVirtual aula = (AulaVirtual) getSession().get(Constante.AULA_ACTUAL);
		
		try {
			if(aula != null && usuario != null && aula.getTrabajoGrupalActual() != 0 && aula.getIdMatricula() != 0 && aula.getPeriodoMaestro() != 0){
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdTrabajo(aula.getTrabajoGrupalActual());
				grupo.setIdGrupo(Integer.parseInt(idGrupo));
				grupo.setEstado(aula.getIdMatricula());
				grupo.setBandera(Constante.FLAG_PENDIENTE_ESTUDIANTE);
				
				int[] idMensajeEstado = tGrupalService.obtenerUltimoIdMensaje(grupo);
				
				TrabajoGrupalMensaje mensaje = new TrabajoGrupalMensaje();
				mensaje.setDescripcion(descripcion);
				Matricula matricula = new Matricula();
				matricula.setIdMatricula(String.valueOf(aula.getIdMatricula()));
				mensaje.setUsuarioEmisor(matricula);
				mensaje.setUsuarioCreacion(usuario.getIdUsuario());
				mensaje.setUsuarioModificacion(usuario.getIdUsuario());
				
				//File **********************************************************
				
				if(file != null && file.exists()){
					log.info("Envío de Mensaje con adjunto: "+filename+" - "+file.length()+" bytes");
					
					String extension = filename.substring(filename.lastIndexOf("."));		
					String origen = file.getAbsolutePath();
					if(!"/".equals(Constante.SLASH))
						origen = origen.replaceAll("/", Constante.SLASH);
					String directorioDestino = Constante.HOME_TRABAJOS_GRUPALES + Constante.SLASH + aula.getPeriodoMaestro() +
						Constante.SLASH + aula.getIdFicha() + Constante.SLASH + aula.getTrabajoGrupalActual() + Constante.SLASH + idGrupo;
					String nombreDestino = "TG" +"_" + idGrupo + "_" + idMensajeEstado[0] + extension;
					if(idMensajeEstado[1] == 0)
						nombreDestino = "TG" +"_" + idGrupo + "_" + (idMensajeEstado[0]+1) + extension;
					
					Archivo.copiarArchivo(origen, directorioDestino + Constante.SLASH + nombreDestino);
					
					mensaje.setArchivoNombre(nombreDestino);
					mensaje.setArchivoTamanio(String.valueOf(file.length()));
					
				}else{
					log.info("Envío de Mensaje sin adjunto");
				}
				
				//**************************************************************
				
				Collection<TrabajoGrupalMensaje> mensajes = new ArrayList<TrabajoGrupalMensaje>();
				
				if(idMensajeEstado[1] == 0){
					mensaje.setIdMensaje(idMensajeEstado[0]+1);
					mensajes.add(mensaje);
					grupo.setMensajes(mensajes);
					log.info("Mensaje Nuevo");
					tGrupalService.nuevoMensaje(grupo);
				}else{
					mensaje.setIdMensaje(idMensajeEstado[0]);
					mensajes.add(mensaje);
					grupo.setMensajes(mensajes);
					log.info("Modificar Mensaje");
					tGrupalService.modificarMensaje(grupo);
				}
			}
		
		} catch (ServiceException e) {
			log.error(e);
		} catch (NumberFormatException e){
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
			
		return SUCCESS;
	}
	
//	********************************* CONTROL ***********************************
	
	public String cambiarModo() {
		log.info("cambiarModo()"+cmd);
		try {
			if("1".equals(cmd))
				getSession().put("MODO_GRUPAL", 1);
			else
				getSession().put("MODO_GRUPAL", 0);
				
		}catch (Exception e) {
			log.error(e);
		}
		return SUCCESS;
	}
	
	public String cargar() {
		log.info("cargar()");
		
		AulaVirtual aula = getAulaVirtualSession();
		
		try {
			if(idUnidad != null || cmd != null){
				TrabajoGrupal tg = new TrabajoGrupal();
				if(idUnidad != null && idUnidad.trim().length()>0){
					tg.setIdFicha(aula.getIdFicha());
					tg.setIdUnidad(Integer.parseInt(idUnidad));
					tg = tGrupalService.obtenerTrabajoGrupal(tg);
				}else if(aula.getTrabajoGrupalActual() != 0){
					tg.setIdTrabajo(aula.getTrabajoGrupalActual());
					tg = tGrupalService.obtenerTrabajoGrupalPorID(tg);
				} else{
					log.error("Faltan parametros requeridos");
					throw new Exception("Faltan parametros requeridos");
				}
				
				aula.setTrabajoGrupalActual(tg.getIdTrabajo());
				
				if(null == getSession().get("MODO_GRUPAL") && aula.getEstadoEjecucion() != 2 && tg.getFechaActivacion() == null)
					return SUCCESS;
				
				getRequest().setAttribute("TRABAJO_GRUPAL", tg);
				
				if(tg.getFechaActivacion() != null){
					Collection<TrabajoGrupalGrupo> grupos = tGrupalService.obtenerGruposParaEvaluacion(tg.getIdTrabajo(), aula.getIdMatricula());
					getRequest().setAttribute("GRUPOS", grupos);
					//*** 
					for (TrabajoGrupalGrupo grupo : grupos) {
						getRequest().setAttribute("GRUPO", grupo);
						break;
					}
				}else{
					//***
					ngrupos = tGrupalService.obtenerEstudiantes(aula.getIdFicha()).size();
				}
			}
			getSession().put("UNIDAD_NOMBRE_TEMP",Util.getNombreUnidad(aula.getUnidades(), idUnidad)); // (solo para nombres de unidades en jsp) esto eliminar y trabajar con objetos de cada recurso en aula
			
		}catch (ServiceException e) {
			log.error(e);
		}catch (NumberFormatException e){
			log.error(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage());
		}
		if(null != getSession().get("MODO_GRUPAL") && 1 == (Integer)getSession().get("MODO_GRUPAL"))
			return "success_extended";
		return SUCCESS;
	}
	
	public String publicarTrabajo(){
		log.info("publicarTrabajo()");
		try {
			Usuario usuario = getUsuarioSession();
			AulaVirtual aula = getAulaVirtualSession();
			if(aula.getTrabajoGrupalActual() != 0 && aula.getIdMatricula() != 0){
				
				if(file != null && file.exists()){
					Collection<TrabajoGrupalGrupo> grupos = tGrupalService.listarGrupos(aula.getTrabajoGrupalActual());
					for (TrabajoGrupalGrupo grupo : grupos) {
						this.idGrupo = String.valueOf(grupo.getIdGrupo()); 
						subirTrabajo();
					}
				}
				
				TrabajoGrupal tg = new TrabajoGrupal();
				tg.setIdTrabajo(aula.getTrabajoGrupalActual());
				tg.setFechaActivacion(Formato.setDateDeJSPCompleto(fechaActivacion));
				tg.setFechaEntrega(Formato.setDateDeJSPCompleto(fechaEntrega));
				tg.setDescripcion(descripcion);
				tg.setUsuarioModificacion(usuario.getIdUsuario());
				
				Matricula publicador = new Matricula();
				publicador.setIdMatricula(String.valueOf(aula.getIdMatricula()));
				tg.setPublicador(publicador);
				
				tGrupalService.publicarTrabajo(tg);
			}
		}catch (ServiceException e) {
			log.error(e);
		}catch (Exception e) {
			log.error(e);
		}
		return SUCCESS;
	}
	
	public String publicarTrabajoCompacto(){
		log.info("publicarTrabajoCompacto()");
		//if(true)return SUCCESS;
		try {
			Usuario usuario = getUsuarioSession();
			AulaVirtual aula = getAulaVirtualSession();
			if(aula.getTrabajoGrupalActual() != 0 && aula.getIdMatricula() != 0 && aula.getPeriodoMaestro() != 0){
				
				//*** Grupos
				
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(1);
				grupo.setIdTrabajo(aula.getTrabajoGrupalActual());
				grupo.setUsuarioCreacion(usuario.getIdUsuario());
				grupo.setEstado(Constante.ESTADO_ACTIVO);
				
				Collection<Matricula> es = tGrupalService.obtenerEstudiantes(aula.getIdFicha());
				
				tGrupalService.eliminarGrupos(aula.getTrabajoGrupalActual());
				
				int gt=0,g=0;
				for (int i = 1; i <= ngrupos; i++) {
					log.info("Crear grupo: "+i);
					grupo.setNombre("Grupo "+i);
					grupo = tGrupalService.crearGrupo(grupo);
					gt = g = grupo.getIdGrupo();
					
					// *** Subida de Archivo
					log.info("Subida de Archivo por grupo - Archivo("+i+"): "+filename+" - "+file+" bytes");
					
					String extension = filename.substring(filename.lastIndexOf("."));
					String origen = file.getAbsolutePath();
					if(!"/".equals(Constante.SLASH))
						origen = origen.replaceAll("/", Constante.SLASH);
					String directorioDestino = Constante.HOME_TRABAJOS_GRUPALES + Constante.SLASH + aula.getPeriodoMaestro() +
						Constante.SLASH + aula.getIdFicha() + Constante.SLASH + aula.getTrabajoGrupalActual() + Constante.SLASH + grupo.getIdGrupo();
					String nombreDestino = "TG" + aula.getTrabajoGrupalActual() + "_" + grupo.getIdGrupo() + extension;
					Archivo.copiarArchivo(origen, directorioDestino + Constante.SLASH + nombreDestino);
					grupo.setArchivoNombre(nombreDestino);
					grupo.setArchivoTamanio(String.valueOf(file.length()));
					tGrupalService.subirTrabajo(grupo);
					// *********************
				}
				
				for (Matricula m : es) {
					log.info("Asignar Estudiante a grupo Grupo: Estudiante "+m.getIdMatricula()+" a Grupo "+g);
					grupo.setIdGrupo(g--);
					tGrupalService.asignarGrupo(grupo, Integer.parseInt(m.getIdMatricula()));
					if(gt-ngrupos==g)g=gt;
				}
				
				//*** ******
				log.info("Publicar Trabajo");
				TrabajoGrupal tg = new TrabajoGrupal();
				tg.setIdTrabajo(aula.getTrabajoGrupalActual());
				tg.setFechaActivacion(Formato.setDateDeJSPCompleto(fechaActivacion));
				tg.setFechaEntrega(Formato.setDateDeJSPCompleto(fechaEntrega));
				tg.setDescripcion(descripcion);
				tg.setUsuarioModificacion(usuario.getIdUsuario());
				
				Matricula publicador = new Matricula();
				publicador.setIdMatricula(String.valueOf(aula.getIdMatricula()));
				tg.setPublicador(publicador);
				
				tGrupalService.publicarTrabajo(tg);
			}else{
				log.error("Aula virtual corrupta");
			}
		}catch (Exception e) {
			log.error(e);
			try {
				tGrupalService.eliminarGrupos(getAulaVirtualSession().getTrabajoGrupalActual());
			} catch (ServiceException e1) {
				log.error(e1);
			}
		}
		return SUCCESS;
	}
	
	public String verTrabajo(){
		log.info("verTrabajo()");
		try {
			AulaVirtual aula = (AulaVirtual) getSession().get(Constante.AULA_ACTUAL);
			if(aula != null && aula.getTrabajoGrupalActual() != 0){
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdTrabajo(aula.getTrabajoGrupalActual());
				grupo.setIdGrupo(Integer.parseInt(idGrupo));
				grupo = tGrupalService.verTrabajo(grupo);
				getRequest().setAttribute("GRUPO", grupo);
			}
		}catch (ServiceException e) {
			log.error(e);
		}catch (NumberFormatException e){
			log.error(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage());
		}
		return SUCCESS;
	}
	
	public String subirTrabajo(){
		log.info("subirTrabajo() - Archivo: "+filename+" - "+file.length()+" bytes");
		
		AulaVirtual aula = getAulaVirtualSession();
		
		try {
			if(file != null && file.exists() && aula.getTrabajoGrupalActual() != 0 && aula.getPeriodoMaestro() != 0){
				String extension = filename.substring(filename.lastIndexOf("."));
				String origen = file.getAbsolutePath();
				if(!"/".equals(Constante.SLASH))
					origen = origen.replaceAll("/", Constante.SLASH);
				String directorioDestino = Constante.HOME_TRABAJOS_GRUPALES + Constante.SLASH + aula.getPeriodoMaestro() +
					Constante.SLASH + aula.getIdFicha() + Constante.SLASH + aula.getTrabajoGrupalActual() + Constante.SLASH + idGrupo;
				String nombreDestino = "TG" + aula.getTrabajoGrupalActual() + "_" + idGrupo + extension;
				Archivo.copiarArchivo(origen, directorioDestino + Constante.SLASH + nombreDestino);
				
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(Integer.parseInt(idGrupo));
				grupo.setIdTrabajo(aula.getTrabajoGrupalActual());
				grupo.setArchivoNombre(nombreDestino);
				grupo.setArchivoTamanio(String.valueOf(file.length()));
				tGrupalService.subirTrabajo(grupo);
			}
		} catch (ServiceException e) {
			log.error(e);
		} catch (NumberFormatException e){
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return SUCCESS;
	}
	
	public String descargarTrabajo(){
		log.info("descargarTrabajo()");
		
		AulaVirtual aula = (AulaVirtual) getSession().get(Constante.AULA_ACTUAL);
		
		try {
			if(aula != null  && aula.getTrabajoGrupalActual() != 0 && aula.getPeriodoMaestro() != 0 && idGrupo != null
					&& aula.getIdFicha() != 0 && filename != null){
				String source = Constante.HOME_TRABAJOS_GRUPALES + Constante.SLASH + aula.getPeriodoMaestro() +
				Constante.SLASH + aula.getIdFicha() + Constante.SLASH + aula.getTrabajoGrupalActual() + 
				Constante.SLASH + idGrupo + Constante.SLASH + filename;
				
				Archivo.downloadFile(filename, source, getResponse());
			}else{
				log.error("Error al leer parametros requeridos para la descarga.");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

	public String calificar(){
		log.info("calificar()");
		
		Usuario usuario = (Usuario)getSession().get(Constante.USUARIO_ACTUAL);
		AulaVirtual aula = (AulaVirtual) getSession().get(Constante.AULA_ACTUAL);
		
		try {
			if(usuario != null && aula != null && aula.getTrabajoGrupalActual() != 0){
				TrabajoGrupalIntegrante integrante = new TrabajoGrupalIntegrante();
				Matricula matricula = new Matricula();
				matricula.setIdMatricula(idMatricula);
				integrante.setUsuarioMatricula(matricula);
				integrante.setUsuarioModificacion(usuario.getIdUsuario());
				integrante.setNotaOportunidad(notaOportunidad);
				integrante.setNotaCoherencia(notaCoherencia);
				integrante.setNotaInnovacion(notaInnovacion);
				integrante.setNotaParticipacion(notaParticipacion);
				integrante.setNotaPromedio(notaFinal);
				
				tGrupalService.calificarTrabajo(aula.getTrabajoGrupalActual(),integrante);
				//System.out.println(integrante.getNotaOportunidad());
				PrintWriter out = getResponse().getWriter();
				out.print("OK");
				out.close();
			}
			
		} catch (ServiceException e) {
			log.error(e);
		} catch (NumberFormatException e){
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return NONE;
	}
	
//	**************************************** GRUPOS ************************************************
	
	public String gestionDeGrupos() {
		log.info("gestionDeGrupos()");
		try {
			AulaVirtual aula = (AulaVirtual) getSession().get(Constante.AULA_ACTUAL);
			if(aula != null && aula.getTrabajoGrupalActual() != 0){
				Collection<TrabajoGrupalGrupo> grupos = tGrupalService.obtenerGrupos(aula.getIdFicha(),aula.getTrabajoGrupalActual());
				getRequest().setAttribute("GRUPOS", grupos);
			}
			
		}catch (ServiceException e) {
			log.error(e);
		}catch (NumberFormatException e){
			log.error(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage());
		}
		return SUCCESS;
	}
	
	public String crearGrupo(){
		log.info("crearGrupo()");
		try {
			AulaVirtual aula = (AulaVirtual) getSession().get(Constante.AULA_ACTUAL);
			
			getResponse().setContentType("text/html; charset=ISO-8859-1");
			PrintWriter pw = getResponse().getWriter();
			
			if(aula != null){
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(1);
				grupo.setIdTrabajo(aula.getTrabajoGrupalActual());
				grupo.setNombre(descripcion);
				grupo.setEstado(Constante.ESTADO_ACTIVO);
				
				grupo = tGrupalService.crearGrupo(grupo);
				pw.print(grupo.getIdGrupo());
			}
			
		}catch (ServiceException e) {
			log.error(e);
		}catch (NumberFormatException e){
			log.error(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage());
		}
		return NONE;
		
	}
	
	public String asignarGrupo(){
		log.info("asignarGrupo()");
		try {
			Usuario usuario = (Usuario)getSession().get(Constante.USUARIO_ACTUAL);
			AulaVirtual aula = (AulaVirtual) getSession().get(Constante.AULA_ACTUAL);
			
			getResponse().setContentType("text/html; charset=ISO-8859-1");
			PrintWriter pw = getResponse().getWriter();
			
			if(aula != null && aula.getTrabajoGrupalActual() != 0){
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(Integer.parseInt(idGrupo));
				grupo.setIdTrabajo(aula.getTrabajoGrupalActual());
				grupo.setUsuarioCreacion(usuario.getIdUsuario());
				
				tGrupalService.asignarGrupo(grupo, Integer.parseInt(idMatricula));
				pw.print("OK");
			}
			
		}catch (ServiceException e) {
			log.error(e);
		}catch (NumberFormatException e){
			log.error(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage());
		}
		return NONE;
		
	}
	
	public String desasignarGrupo(){
		log.info("desasignarGrupo()");
		try {
			AulaVirtual aula = (AulaVirtual) getSession().get(Constante.AULA_ACTUAL);
			
			getResponse().setContentType("text/html; charset=ISO-8859-1");
			PrintWriter pw = getResponse().getWriter();
			
			if(aula != null && aula.getTrabajoGrupalActual() != 0){
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(Integer.parseInt(idGrupo));
				grupo.setIdTrabajo(aula.getTrabajoGrupalActual());
				
				tGrupalService.desasignarGrupo(grupo, Integer.parseInt(idMatricula));
				pw.print("OK");
			}
			
		}catch (ServiceException e) {
			log.error(e);
		}catch (NumberFormatException e){
			log.error(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage());
		}
		return NONE;
		
	}
	
	public String renombrarGrupo(){
		log.info("renombrarGrupo()");
		try {
			AulaVirtual aula = (AulaVirtual) getSession().get(Constante.AULA_ACTUAL);
			
			getResponse().setContentType("text/html; charset=ISO-8859-1");
			PrintWriter pw = getResponse().getWriter();
			
			if(aula != null && aula.getTrabajoGrupalActual() != 0){
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(Integer.parseInt(idGrupo));
				grupo.setIdTrabajo(aula.getTrabajoGrupalActual());
				grupo.setNombre(descripcion);
				
				tGrupalService.renombrarGrupo(grupo);
				pw.print("OK");
			}
			
		}catch (ServiceException e) {
			log.error(e);
		}catch (NumberFormatException e){
			log.error(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage());
		}
		return NONE;
		
	}
	
	public String eliminarGrupo(){
		log.info("eliminarGrupo()");
		try {
			AulaVirtual aula = (AulaVirtual) getSession().get(Constante.AULA_ACTUAL);
			
			getResponse().setContentType("text/html; charset=ISO-8859-1");
			PrintWriter pw = getResponse().getWriter();
			
			if(aula != null && aula.getTrabajoGrupalActual() != 0){
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(Integer.parseInt(idGrupo));
				grupo.setIdTrabajo(aula.getTrabajoGrupalActual());
				grupo.setEstado(Constante.ESTADO_INACTIVO);
				
				tGrupalService.eliminarGrupo(grupo);
				pw.print("OK");
			}
			
		}catch (ServiceException e) {
			log.error(e);
		}catch (NumberFormatException e){
			log.error(e);
		}catch (Exception e) {
			log.error(e);
		}
		return NONE;
		
	}
	
}
