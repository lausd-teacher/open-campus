package edu.opencampus.lms.action.tgrupal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;

import edu.opencampus.lms.action.BaseAction;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.AulaVirtual;
import edu.opencampus.lms.modelo.Matricula;
import edu.opencampus.lms.modelo.TrabajoGrupal;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.modelo.tgrupal.TrabajoGrupalGrupo;
import edu.opencampus.lms.modelo.tgrupal.TrabajoGrupalIntegrante;
import edu.opencampus.lms.modelo.tgrupal.TrabajoGrupalMensaje;
import edu.opencampus.lms.service.TrabajoGrupalService;
import edu.opencampus.lms.util.Archivo;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;
import edu.opencampus.lms.util.Util;

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
		
		try{
			if(aula != null && aula.getIdFicha() != 0){
				
				TrabajoGrupal tg = new TrabajoGrupal();
				if(cmd == null){
					tg.setIdFicha(aula.getIdFicha());
					tg.setIdUnidad(Integer.parseInt(idUnidad));
					tg.setIdTrabajo(Integer.parseInt(idUnidad));
					tg = tGrupalService.obtenerTrabajoGrupal(tg);
				}else{
					//tg.setIdTrabajo((Integer)getSession().get("GRUPAL"));
					tg = (TrabajoGrupal)getSession().get("GRUPAL");
				}
				
				if(tg.getFechaActivacion() != null){
					if(tg.getFechaActivacion().before(new GregorianCalendar())){
						
						TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
						grupo.setIdTrabajo(tg.getIdTrabajo());
						grupo.setIdFicha(tg.getIdFicha());
						
						//IDGRUPO
						int idGrupo = tGrupalService.obtenerIdGrupo(tg,aula.getMatriculaActual().getIdMatricula());
						grupo.setIdGrupo(idGrupo);
						
						if(idGrupo != -1){
							grupo = tGrupalService.verMensajes(grupo);
							
							//DEBATE
							grupo.setBanderaDebate(tGrupalService.obtenerEstadoDebate(tg, idGrupo, aula.getMatriculaActual().getIdMatricula()));
							
							getSession().put("GRUPAL", tg);
						}
						getRequest().setAttribute("TRABAJO_GRUPAL_MENSAJES", grupo);
					
					}else{
						getRequest().setAttribute("NoActivate", "1");
					}
				}
				getRequest().setAttribute("TRABAJO_GRUPAL", tg);
				
				getSession().put("UNIDAD_NOMBRE_TEMP",Util.getNombreUnidad(aula.getSilabo().getUnidades(), idUnidad)); // (solo para nombres de unidades en jsp) esto eliminar y trabajar con objetos de cada recurso en aula
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
				
		try {
			if(aula != null &&  tg != null && aula.getMatriculaActual().getIdMatricula() != 0){
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				int idGrupo = tGrupalService.obtenerIdGrupo(tg,aula.getMatriculaActual().getIdMatricula());
				
				if(idGrupo != -1){
					grupo.setIdTrabajo(tg.getIdTrabajo());
					grupo.setIdFicha(tg.getIdFicha());
					grupo.setIdGrupo(idGrupo);
					grupo.setEstado(aula.getMatriculaActual().getIdMatricula());
					grupo.setBandera(Constante.FLAG_PENDIENTE_DOCENTE);
					
					int[] idMensajeEstado = tGrupalService.obtenerUltimoIdMensaje(grupo);
					
					TrabajoGrupalMensaje mensaje = new TrabajoGrupalMensaje();
					mensaje.setDescripcion(descripcion);
					Matricula matricula = new Matricula();
					matricula.setIdMatricula(aula.getMatriculaActual().getIdMatricula());
					mensaje.setUsuarioEmisor(matricula);
					mensaje.setUsuarioCreacion(""+getUsuarioSession().getId());
					mensaje.setUsuarioModificacion(""+getUsuarioSession().getId());
					
					//File **********************************************************
					
					if(file != null && file.exists()){
						
						log.info("Envío de Mensaje con adjunto: "+filename+" - "+file.length()+" bytes");
						
						String extension = filename.substring(filename.lastIndexOf("."));		
						String origen = file.getAbsolutePath();
						if(!"/".equals(Constante.SLASH))
							origen = origen.replaceAll("/", Constante.SLASH);
						String directorioDestino = Constante.RUTA_GRUPALES + Constante.SLASH + aula.getIdFicha() + Constante.SLASH + tg.getIdTrabajo() + Constante.SLASH + idGrupo;
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
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
			
		return SUCCESS;
	} 
	
	public String descargarTrabajoGrupal(){
		log.info("descargarTrabajoGrupal()");
		
		AulaVirtual aula = getUsuarioSession().getAulaActual();
		TrabajoGrupal tg = (TrabajoGrupal)getSession().get("GRUPAL");
		
		try {
			if(aula != null && tg != null && aula.getMatriculaActual().getIdMatricula() != 0){
				int idGrupo = tGrupalService.obtenerIdGrupo(tg,aula.getMatriculaActual().getIdMatricula());
				String source = Constante.RUTA_GRUPALES + 
				Constante.SLASH + aula.getIdFicha() + Constante.SLASH + tg.getIdTrabajo() + 
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
		
		AulaVirtual aula = getUsuarioSession().getAulaActual();
		TrabajoGrupal tg = (TrabajoGrupal)getSession().get("GRUPAL");
		
		try {
			if(aula != null && tg != null && aula.getMatriculaActual().getIdMatricula() != 0){
				Collection<Matricula> integrantes = tGrupalService.verIntegrantes(tg,aula.getMatriculaActual().getIdMatricula());
				
				if(!integrantes.isEmpty()){
					getResponse().setContentType("text/xml");		
					getResponse().setHeader("Cache-Control", "no-cache");
					PrintWriter out = getResponse().getWriter();
					out.write("<?xml version='1.0' encoding='ISO-8859-1'?>");
					out.write("<integrantes>");
					
					for (Matricula colega : integrantes) {
						getResponse().getWriter().write("<integrante>"+ colega.getUsuario().getNombreCompleto()+ "</integrante>");
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
			AulaVirtual aula = getUsuarioSession().getAulaActual();
			TrabajoGrupal tg = (TrabajoGrupal)getSession().get("GRUPAL");
			
			getResponse().setContentType("text/html; charset=ISO-8859-1");
			PrintWriter pw = getResponse().getWriter();
			
			if(tg != null){
				int idGrupo = tGrupalService.obtenerIdGrupo(tg,aula.getMatriculaActual().getIdMatricula());
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(idGrupo);
				grupo.setIdTrabajo(tg.getIdTrabajo());
				grupo.setIdFicha(tg.getIdFicha());
				grupo.setNombre(descripcion);
				
				tGrupalService.renombrarGrupo(grupo);
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
	
//	********************************* CONTROL ***********************************
	
	public String verMensajes(){
		log.info("verMensajes()");
		
		try {
			if(aula != null && tg != null){
				
//				TrabajoGrupal tg = new TrabajoGrupal();
//				tg.setIdTrabajo(aula.getTrabajoGrupalActual());
//				tg = tGrupalService.obtenerTrabajoGrupalPorID(tg);
				
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdTrabajo(tg.getIdTrabajo());
				grupo.setIdFicha(tg.getIdFicha());
				grupo.setIdGrupo(Integer.parseInt(idGrupo));
				grupo = tGrupalService.verMensajes(grupo);
				
				getRequest().setAttribute("TRABAJO_GRUPAL", tg);
				getRequest().setAttribute("TRABAJO_GRUPAL_MENSAJES", grupo);
				
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
	
	TrabajoGrupal tg = null;
	AulaVirtual aula = null;
	@Override
	public void prepare() throws Exception {
		super.prepare();
		tg = (TrabajoGrupal)getSession().get("GRUPAL");
		aula = getUsuarioSession().getAulaActual();
	}

	public String enviarMensaje(){
		log.info("enviarMensaje()");
		
		Usuario usuario = (Usuario)getSession().get(Constante.USUARIO_ACTUAL);
		
		try {
			if(aula != null && usuario != null && tg != null && aula.getMatriculaActual().getIdMatricula() != null ){
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdTrabajo(tg.getIdTrabajo());
				grupo.setIdFicha(tg.getIdFicha());
				grupo.setIdGrupo(Integer.parseInt(idGrupo));
				grupo.setEstado(aula.getMatriculaActual().getIdMatricula());
				grupo.setBandera(Constante.FLAG_PENDIENTE_ESTUDIANTE);
				
				int[] idMensajeEstado = tGrupalService.obtenerUltimoIdMensaje(grupo);
				
				TrabajoGrupalMensaje mensaje = new TrabajoGrupalMensaje();
				mensaje.setDescripcion(descripcion);
				Matricula matricula = new Matricula();
				matricula.setIdMatricula(aula.getMatriculaActual().getIdMatricula());
				mensaje.setUsuarioEmisor(matricula);
				mensaje.setUsuarioCreacion(""+usuario.getId());
				mensaje.setUsuarioModificacion(""+usuario.getId());
				
				//File **********************************************************
				
				if(file != null && file.exists()){
					log.info("Envío de Mensaje con adjunto: "+filename+" - "+file.length()+" bytes");
					
					String extension = filename.substring(filename.lastIndexOf("."));		
					String origen = file.getAbsolutePath();
					if(!"/".equals(Constante.SLASH))
						origen = origen.replaceAll("/", Constante.SLASH);
					String directorioDestino = Constante.RUTA_GRUPALES + 
						Constante.SLASH + aula.getIdFicha() + Constante.SLASH + tg.getIdTrabajo() + Constante.SLASH + idGrupo;
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
			log.error(e);
		} catch (Exception e) {
			log.error(e);
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
		log.info("cargar()"+idUnidad);
		getSession().put("MODO_GRUPAL", 1);
		
		try {
			if(idUnidad != null || cmd != null){
				if(idUnidad != null && idUnidad.trim().length()>0){
					tg = new TrabajoGrupal();
					tg.setIdFicha(aula.getIdFicha());
					tg.setIdUnidad(Integer.parseInt(idUnidad));
					tg.setIdTrabajo(Integer.parseInt(idUnidad));
					tg = tGrupalService.obtenerTrabajoGrupal(tg);
					getSession().put("GRUPAL", tg);
				}else if(tg.getIdTrabajo() != 0){
					//tg = tGrupalService.obtenerTrabajoGrupalPorID(tg);
				} else{
					log.error("Faltan parametros requeridos");
					throw new Exception("Faltan parametros requeridos");
				}
				
				//aula.setTrabajoGrupalActual(tg.getIdTrabajo());
				
				if(null == getSession().get("MODO_GRUPAL") && aula.getEstadoEjecucion() != 2 && tg.getFechaActivacion() == null)
					return SUCCESS;
				
				getRequest().setAttribute("TRABAJO_GRUPAL", tg);
				
				if(tg.getFechaActivacion() != null){
					Collection<TrabajoGrupalGrupo> grupos = tGrupalService.obtenerGruposParaEvaluacion(tg, (aula.getMatriculaActual().getIdMatricula()!=null)?aula.getMatriculaActual().getIdMatricula():0);
					getRequest().setAttribute("GRUPOS", grupos);
					//*** 
					for (TrabajoGrupalGrupo grupo : grupos) {
						getRequest().setAttribute("GRUPO", grupo);
						break;
					}
				}else{
					//***
					System.out.println("integrentes");
					ngrupos = tGrupalService.obtenerEstudiantes(aula.getIdFicha()).size();
				}
			}
			getSession().put("UNIDAD_NOMBRE_TEMP",Util.getNombreUnidad(aula.getSilabo().getUnidades(), idUnidad)); // (solo para nombres de unidades en jsp) esto eliminar y trabajar con objetos de cada recurso en aula
			
		}catch (ServiceException e) {
			log.error(e);
		}catch (NumberFormatException e){
			log.error(e);
		}catch (Exception e) {
			log.error(e);
		}
		if(null != getSession().get("MODO_GRUPAL") && 1 == (Integer)getSession().get("MODO_GRUPAL"))
			return "success_extended";
		return SUCCESS;
	}
	// PRIMERO SE PUBLICA LUEGO SE PUEDE FORMAR GRUPOS (O FORMAR AUTOMATICAMENTE)
	public String publicarTrabajo(){
		log.info("publicarTrabajo()");
		try {
			Usuario usuario = getUsuarioSession();
			if(tg.getIdTrabajo() != 0 && aula.getMatriculaActual().getIdMatricula() != 0){
				
				if(file != null && file.exists()){
					Collection<TrabajoGrupalGrupo> grupos = tGrupalService.listarGrupos(tg);
					for (TrabajoGrupalGrupo grupo : grupos) {
						this.idGrupo = String.valueOf(grupo.getIdGrupo()); 
						subirTrabajo();
					}
				}
				
				//TrabajoGrupal tg = new TrabajoGrupal();
				//tg.setIdTrabajo(tg.getIdTrabajo());
				tg.setFechaActivacion(Formato.stringToCalendar(fechaActivacion, Constante.FECHA_DIA_MES_ANO_HORA_MI));
				tg.setFechaEntrega(Formato.stringToCalendar(fechaEntrega, Constante.FECHA_DIA_MES_ANO_HORA_MI));
				tg.setDescripcion(descripcion);
				tg.setUsuarioModificacion(""+usuario.getId());
				
				Matricula publicador = new Matricula();
				publicador.setIdMatricula(aula.getMatriculaActual().getIdMatricula());
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
			if(tg.getIdTrabajo() != 0 && aula.getMatriculaActual().getIdMatricula() != 0 ){
				
				//*** Grupos
				
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(1);
				grupo.setIdTrabajo(tg.getIdTrabajo());
				grupo.setUsuarioCreacion(""+usuario.getId());
				grupo.setEstado(Constante.ESTADO_ACTIVO);
				
				Collection<Matricula> es = tGrupalService.obtenerEstudiantes(aula.getIdFicha());
				
				tGrupalService.eliminarGrupos(tg);
				
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
					String directorioDestino = Constante.RUTA_GRUPALES + 
						Constante.SLASH + aula.getIdFicha() + Constante.SLASH + tg.getIdTrabajo() + Constante.SLASH + grupo.getIdGrupo();
					String nombreDestino = "TG" + tg.getIdTrabajo() + "_" + grupo.getIdGrupo() + extension;
					Archivo.copiarArchivo(origen, directorioDestino + Constante.SLASH + nombreDestino);
					grupo.setArchivoNombre(nombreDestino);
					grupo.setArchivoTamanio(String.valueOf(file.length()));
					tGrupalService.subirTrabajo(grupo);
					// *********************
				}
				
				for (Matricula m : es) {
					log.info("Asignar Estudiante a grupo Grupo: Estudiante "+m.getIdMatricula()+" a Grupo "+g);
					grupo.setIdGrupo(g--);
					tGrupalService.asignarGrupo(grupo, m.getIdMatricula());
					if(gt-ngrupos==g)g=gt;
				}
				
				//*** ******
				log.info("Publicar Trabajo");
				TrabajoGrupal tg = new TrabajoGrupal();
				tg.setIdTrabajo(tg.getIdTrabajo());
				tg.setFechaActivacion(Formato.stringToCalendar(fechaActivacion, Constante.FECHA_DIA_MES_ANO_HORA_MI));
				tg.setFechaEntrega(Formato.stringToCalendar(fechaActivacion, Constante.FECHA_DIA_MES_ANO_HORA_MI));
				tg.setDescripcion(descripcion);
				tg.setUsuarioModificacion(""+usuario.getId());
				
				Matricula publicador = new Matricula();
				publicador.setIdMatricula(aula.getMatriculaActual().getIdMatricula());
				tg.setPublicador(publicador);
				
				tGrupalService.publicarTrabajo(tg);
			}else{
				log.error("Aula virtual corrupta");
			}
		}catch (Exception e) {
			log.error(e);
			try {
				tGrupalService.eliminarGrupos(tg);
			} catch (ServiceException e1) {
				log.error(e1);
			}
		}
		return SUCCESS;
	}
	
	public String verTrabajo(){
		log.info("verTrabajo()");
		try {
			if(aula != null && tg != null && tg.getIdTrabajo() != 0){
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdTrabajo(tg.getIdTrabajo());
				grupo.setIdFicha(tg.getIdFicha());
				grupo.setIdGrupo(Integer.parseInt(idGrupo));
				grupo = tGrupalService.verTrabajo(grupo);
				getRequest().setAttribute("GRUPO", grupo);
			}
		}catch (ServiceException e) {
			log.error(e);
		}catch (NumberFormatException e){
			log.error(e);
		}catch (Exception e) {
			log.error(e);
		}
		return SUCCESS;
	}
	
	public String subirTrabajo(){
		log.info("subirTrabajo() - Archivo: "+filename+" - "+file.length()+" bytes");
		
		try {
			if(file != null && file.exists() && tg.getIdTrabajo() != 0 ){
				String extension = filename.substring(filename.lastIndexOf("."));
				String origen = file.getAbsolutePath();
				if(!"/".equals(Constante.SLASH))
					origen = origen.replaceAll("/", Constante.SLASH);
				String directorioDestino = Constante.RUTA_GRUPALES + 
					Constante.SLASH + aula.getIdFicha() + Constante.SLASH + tg.getIdTrabajo() + Constante.SLASH + idGrupo;
				String nombreDestino = "TG" + tg.getIdTrabajo() + "_" + idGrupo + extension;
				Archivo.copiarArchivo(origen, directorioDestino + Constante.SLASH + nombreDestino);
				
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(Integer.parseInt(idGrupo));
				grupo.setIdTrabajo(tg.getIdTrabajo());
				grupo.setIdFicha(tg.getIdFicha());
				grupo.setArchivoNombre(nombreDestino);
				grupo.setArchivoTamanio(String.valueOf(file.length()));
				tGrupalService.subirTrabajo(grupo);
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
	
	public String descargarTrabajo(){
		log.info("descargarTrabajo()");
		
		
		try {
			if(aula != null  && tg.getIdTrabajo() != 0 && idGrupo != null
					&& aula.getIdFicha() != 0 && filename != null){
				String source = Constante.RUTA_GRUPALES + 
				Constante.SLASH + aula.getIdFicha() + Constante.SLASH + tg.getIdTrabajo() + 
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
		
		try {
			if(usuario != null && aula != null && tg.getIdTrabajo() != 0){
				TrabajoGrupalIntegrante integrante = new TrabajoGrupalIntegrante();
				Matricula matricula = new Matricula();
				matricula.setIdMatricula(Integer.parseInt(idMatricula));
				integrante.setUsuarioMatricula(matricula);
				integrante.setUsuarioModificacion(""+usuario.getId());
				integrante.setNotaOportunidad(notaOportunidad);
				integrante.setNotaCoherencia(notaCoherencia);
				integrante.setNotaInnovacion(notaInnovacion);
				integrante.setNotaParticipacion(notaParticipacion);
				integrante.setNotaPromedio(notaFinal);
				
				tGrupalService.calificarTrabajo(tg,integrante);
				//System.out.println(integrante.getNotaOportunidad());
				PrintWriter out = getResponse().getWriter();
				out.print("OK");
				out.close();
			}
			
		} catch (ServiceException e) {
			log.error(e);
		} catch (NumberFormatException e){
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		return NONE;
	}
	
//	**************************************** GRUPOS ************************************************
	
	public String gestionDeGrupos() {
		log.info("gestionDeGrupos()");
		try {
			if(aula != null && tg.getIdTrabajo() != 0){
				Collection<TrabajoGrupalGrupo> grupos = tGrupalService.obtenerGrupos(aula.getIdFicha(),tg.getIdTrabajo());
				getRequest().setAttribute("GRUPOS", grupos);
			}
			
		}catch (ServiceException e) {
			log.error(e);
		}catch (NumberFormatException e){
			log.error(e);
		}catch (Exception e) {
			log.error(e);
		}
		return SUCCESS;
	}
	
	public String crearGrupo(){
		log.info("crearGrupo()");
		try {
			
			getResponse().setContentType("text/html; charset=ISO-8859-1");
			PrintWriter pw = getResponse().getWriter();
			
			if(aula != null){
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(1);
				grupo.setIdFicha(tg.getIdFicha());
				grupo.setIdTrabajo(tg.getIdTrabajo());
				grupo.setNombre(descripcion);
				grupo.setEstado(Constante.ESTADO_ACTIVO);
				
				grupo = tGrupalService.crearGrupo(grupo);
				pw.print(grupo.getIdGrupo());
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
	
	public String asignarGrupo(){
		log.info("asignarGrupo()");
		try {
			Usuario usuario = (Usuario)getSession().get(Constante.USUARIO_ACTUAL);
			
			getResponse().setContentType("text/html; charset=ISO-8859-1");
			PrintWriter pw = getResponse().getWriter();
			
			if(aula != null && tg.getIdTrabajo() != 0){
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(Integer.parseInt(idGrupo));
				grupo.setIdTrabajo(tg.getIdTrabajo());
				grupo.setIdFicha(tg.getIdFicha());
				grupo.setUsuarioCreacion(""+usuario.getId());
				
				tGrupalService.asignarGrupo(grupo, Integer.parseInt(idMatricula));
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
	
	public String desasignarGrupo(){
		log.info("desasignarGrupo()");
		try {
			
			getResponse().setContentType("text/html; charset=ISO-8859-1");
			PrintWriter pw = getResponse().getWriter();
			
			if(aula != null && tg.getIdTrabajo() != 0){
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(Integer.parseInt(idGrupo));
				grupo.setIdTrabajo(tg.getIdTrabajo());
				grupo.setIdFicha(tg.getIdFicha());
				
				tGrupalService.desasignarGrupo(grupo, Integer.parseInt(idMatricula));
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
	
	public String renombrarGrupo(){
		log.info("renombrarGrupo()");
		try {
			
			getResponse().setContentType("text/html; charset=ISO-8859-1");
			PrintWriter pw = getResponse().getWriter();
			
			if(aula != null && tg.getIdTrabajo() != 0){
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(Integer.parseInt(idGrupo));
				grupo.setIdTrabajo(tg.getIdTrabajo());
				grupo.setIdFicha(tg.getIdFicha());
				grupo.setNombre(descripcion);
				
				tGrupalService.renombrarGrupo(grupo);
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
	
	public String eliminarGrupo(){
		log.info("eliminarGrupo()");
		try {
			
			getResponse().setContentType("text/html; charset=ISO-8859-1");
			PrintWriter pw = getResponse().getWriter();
			
			if(aula != null && tg.getIdTrabajo() != 0){
				TrabajoGrupalGrupo grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(Integer.parseInt(idGrupo));
				grupo.setIdTrabajo(tg.getIdTrabajo());
				grupo.setIdFicha(tg.getIdFicha());
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
