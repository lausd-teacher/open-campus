package edu.opencampus.lms.action.tindividual;

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
import edu.opencampus.lms.modelo.TrabajoIndividual;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.modelo.Matricula;
import edu.opencampus.lms.modelo.tindividual.TrabajoIndividualInteraccion;
import edu.opencampus.lms.modelo.tindividual.TrabajoIndividualMatricula;
import edu.opencampus.lms.service.TrabajoIndividualService;
import edu.opencampus.lms.util.Archivo;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;
import edu.opencampus.lms.util.Util;

public class TrabajoIndividualAction extends BaseAction {

	private static final long serialVersionUID = 6987695165251537057L;
	private TrabajoIndividualService tIndividualService;

	public void setTrabajoIndividualService(TrabajoIndividualService tIndividualService) {
		this.tIndividualService = tIndividualService;
	}

	public File file;
	public String contentType;
	public String filename;
	
	public String idUnidad;
	public String idMatricula;
	
	public String nota;
	public String descripcion;
	public String fechaActivacion;
	public String fechaEntrega;
	
	public String cmd;
	public String msg;
	
	public String idMensaje;

    public String getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCmd() {
		return cmd;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getFechaActivacion() {
		return fechaActivacion;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public String getIdMatricula() {
		return idMatricula;
	}

	public String getMsg() {
		return msg;
	}

	public String getNota() {
		return nota;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getIdUnidad() {
		return idUnidad;
	}

	public void setFechaActivacion(String fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
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

	public void setNota(String nota) {
		this.nota = nota;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String cargar() {
		log.info("cargar()");
		TrabajoIndividual ti = null;
		try {
			AulaVirtual aula = (AulaVirtual) getUsuarioSession().getAulaActual();
			
			if(cmd != null && "error".equals(cmd)){
				idUnidad = (String)getSession().get("IDUNIDAD");
				log.info("Upload Error");
				setMsg("error");
			}
			if(aula != null && idUnidad != null){
				if(!"".equals(idUnidad)){
					ti = new TrabajoIndividual();
					ti.setIdFicha(aula.getIdFicha());
					ti.setIdUnidad(Integer.parseInt(idUnidad));
					ti.setInteraccion(new TrabajoIndividualInteraccion());
					ti = tIndividualService.obtenerTrabajoIndividual(ti);
				}else{
					ti = (TrabajoIndividual)getSession().get(Constante.AULAVIRTUAL_TRABAJOINDIVIDUAL);
				}
				
				if(ti.getInteraccion() == null || ti.getInteraccion().getArchivoNombre() == null){//EN TRABAJO, CUANDO SE CREA EL TRABAJO CON EL DEFAUL, EL DEFAULT DEBERA COPIARSE A /HOME/OPENCAMPUS/TRABAJO/{IDPERIODO}/{IDFICHA}/{IDUNIDAD}/
					//por el momento
					log.info("No exste trabajo publicado");
					ti.setIdTrabajo(Integer.parseInt(idUnidad));
					
					String source = Constante.RUTA_UNIDADES+  Formato.completarCeros(idUnidad, 8) +
					Constante.SLASH + Constante.PATH_TRABAJO + Constante.TRABAJO_PDF;
					File trabajo = new File(source);
					if(trabajo.exists()){
						log.info("Con trabajo default.");
						ti.getInteraccion().setArchivoNombre("DEFAULT");
					}
				}
				
				getSession().put(Constante.AULAVIRTUAL_TRABAJOINDIVIDUAL, ti);
				
				getSession().put("IDUNIDAD", idUnidad);
				if(ti.getFechaActivacion() != null){
					Collection<TrabajoIndividualMatricula> matriculas 
					= tIndividualService.listarTrabajoPorMatricula(ti);
					getRequest().setAttribute("TRABAJO_MATRICULAS", matriculas);
				}
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
	
	public String calificar(){
		log.info("calificar() -> Nota: "+nota);
		
		Usuario usuario = (Usuario)getSession().get(Constante.USUARIO_ACTUAL);
		TrabajoIndividual trabajo= (TrabajoIndividual)getSession().get(Constante.AULAVIRTUAL_TRABAJOINDIVIDUAL);
		// Estado: Se Califica
		String estado = "1"; 
		
		try {
			
			TrabajoIndividualMatricula tMatricula = new TrabajoIndividualMatricula();
			if("".equals(nota)){
				// Estado: Se Quita Calificación
				tMatricula.setNota(null);
				estado = "0"; 
			}else{
				tMatricula.setNota(Integer.parseInt(nota));
			}
			tMatricula.setUsuarioModificacion(""+usuario.getId());
			tMatricula.setIdTrabajo(trabajo.getIdTrabajo());
			tMatricula.setIdFicha(trabajo.getIdFicha());
			Matricula usuarioReceptor = new Matricula();
			usuarioReceptor.setIdMatricula(Integer.parseInt(idMatricula));
			tMatricula.setUsuarioReceptor(usuarioReceptor);
			
			tIndividualService.calificarTrabajo(tMatricula);
			
			PrintWriter out = getResponse().getWriter();
			out.print(estado);
			out.close();
			
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (NumberFormatException e){
			log.error(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
		}
		
		return NONE;
	}
	
	public String publicarTrabajop(){
		log.info("publicarTrabajop()");
		Usuario usuario = (Usuario)getSession().get(Constante.USUARIO_ACTUAL);
		AulaVirtual aula = (AulaVirtual) getUsuarioSession().getAulaActual();
		TrabajoIndividual trabajo= (TrabajoIndividual)getSession().get(Constante.AULAVIRTUAL_TRABAJOINDIVIDUAL);
		log.info(cmd+"-"+trabajo.getIdTrabajo()+"-"+aula.getIdFicha()+"-"+descripcion+"-"+fechaActivacion+"-"+fechaEntrega+"-"+filename+"-"+((file != null )?file.length():null)); 
		
		try {
			if(aula != null && usuario != null && aula.getPeriodo().getIdPeriodo() != 0 
					&& fechaActivacion != null && Formato.stringToCalendar(fechaActivacion,Constante.FECHA_DIA_MES_ANO_HORA_MI) != null){
			
				TrabajoIndividual tindividual = new TrabajoIndividual();
				tindividual.setIdTrabajo(trabajo.getIdTrabajo());
				tindividual.setIdFicha(aula.getIdFicha());
				tindividual.setIdUnidad(trabajo.getIdTrabajo());
				tindividual.setFechaActivacion(Formato.stringToCalendar(fechaActivacion,Constante.FECHA_DIA_MES_ANO_HORA_MI));
				tindividual.setFechaEntrega(Formato.stringToCalendar(fechaEntrega,Constante.FECHA_DIA_MES_ANO_HORA_MI));
				tindividual.setUsuarioCreacion(""+usuario.getId());
				tindividual.setUsuarioModificacion(""+usuario.getId());
				
				TrabajoIndividualInteraccion tiInteraccion = new TrabajoIndividualInteraccion();
				Matricula usuarioEmisor = new Matricula();
				usuarioEmisor.setIdMatricula(aula.getMatriculaActual().getIdMatricula());
				tiInteraccion.setUsuarioEmisor(usuarioEmisor);
				tiInteraccion.setDescripcion(descripcion);
			
				if(file != null && file.exists()){
				
					tiInteraccion.setArchivoTamanio(String.valueOf(file.length()));
				
					log.info("Publicación de Trabajo - Archivo: "+filename+" - "+file.length()+" bytes");
					
					
					String extension = filename.substring(filename.lastIndexOf("."));		
					String origen = file.getAbsolutePath();
					if(!"/".equals(Constante.SLASH))
						origen = origen.replaceAll("/", Constante.SLASH);
					String directorioDestino = Constante.RUTA_TRABAJOS + aula.getPeriodo().getIdPeriodo() +
						Constante.SLASH + aula.getIdFicha() + Constante.SLASH + trabajo.getIdTrabajo();
					String nombreDestino = Constante.TRABAJO + "_" + idUnidad + extension;
					Archivo.copiarArchivo(origen, directorioDestino + Constante.SLASH + nombreDestino);
					
					tiInteraccion.setArchivoNombre(nombreDestino);
					
				}else{
					log.info("El trabajo no adjunta archivo");
				}
				
				tindividual.setInteraccion(tiInteraccion);
				
				if("mod".equals(cmd)){
					log.info("Modificar Trabajo");
					tIndividualService.modificarTrabajo(tindividual);
				}else{
					log.info("Publicar Trabajo Nuevo");
					tIndividualService.publicarTrabajo(tindividual);
				}
			}else{
				log.error("Problemas con los parámetros");
			}
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (NumberFormatException e){
			log.error(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
		}
		
		return SUCCESS;
	}
	
	public String eliminarTrabajo(){
		log.info("eliminarTrabajo()");
		Usuario usuario = (Usuario)getSession().get(Constante.USUARIO_ACTUAL);
		AulaVirtual aula = (AulaVirtual)getUsuarioSession().getAulaActual();
		TrabajoIndividual trabajo= (TrabajoIndividual)getSession().get(Constante.AULAVIRTUAL_TRABAJOINDIVIDUAL);
		try {
			if(trabajo != null && usuario != null && aula.getPeriodo().getIdPeriodo() != 0){
				TrabajoIndividual tindividual = new TrabajoIndividual();
				tindividual.setIdTrabajo(trabajo.getIdTrabajo());
				tindividual.setIdFicha(trabajo.getIdFicha());
				tindividual.setUsuarioModificacion(""+usuario.getId());
				
				// Renombrar Archivo
				idUnidad = String.valueOf(trabajo.getIdUnidad());
				String source = Constante.RUTA_UNIDADES+ Formato.completarCeros(idUnidad, 8) +
				Constante.SLASH + Constante.PATH_TRABAJO + Constante.TRABAJO_PDF;
				File doc = new File(source);
				if(doc.exists()){
					
					tIndividualService.eliminarTrabajo(tindividual);
					
					String archivoDestino = Constante.RUTA_TRABAJOS + aula.getPeriodo().getIdPeriodo() +
					Constante.SLASH + aula.getIdFicha() + Constante.SLASH + trabajo.getIdTrabajo() + Constante.SLASH + filename;
					//Archivo.copiarArchivo(archivoDestino, archivoDestino + "_" +Formato.getStringDeDatePaBKP()+".bkp");
					Archivo.eliminarArchivo(archivoDestino);
					
				}else{
					setMsg("No existe un documento predefinido.");
					log.error(msg);
				}
				
			}
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (NumberFormatException e){
			log.error(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
		}
		return SUCCESS;
	}

	public String verMensajesDeDocente(){
		
		TrabajoIndividual trabajo= (TrabajoIndividual)getSession().get(Constante.AULAVIRTUAL_TRABAJOINDIVIDUAL);
		
		try {
			TrabajoIndividualMatricula matricula = new TrabajoIndividualMatricula();
			matricula.setIdTrabajo(trabajo.getIdTrabajo());
			matricula.setIdFicha(trabajo.getIdFicha());
			
			Matricula usuarioReceptor = new Matricula();
			usuarioReceptor.setIdMatricula(Integer.parseInt(idMatricula));
			matricula.setUsuarioReceptor(usuarioReceptor);
			
			matricula = tIndividualService.obtenerInteracciones(matricula);
			
			getRequest().setAttribute("TRABAJO_MATRICULA_INTERACCIONES", matricula);
			
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (NumberFormatException e){
			log.error(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
		}
		
		return SUCCESS;
	}
	
	public String enviarMensajeDeDocente(){
		
		Usuario usuario = (Usuario)getSession().get(Constante.USUARIO_ACTUAL);
		AulaVirtual aula = (AulaVirtual) getUsuarioSession().getAulaActual();
		TrabajoIndividual trabajo= (TrabajoIndividual)getSession().get(Constante.AULAVIRTUAL_TRABAJOINDIVIDUAL);
		String idMensaje = getRequest().getParameter("idMensaje");
		
		try {
			TrabajoIndividualMatricula matricula = new TrabajoIndividualMatricula();
			matricula.setIdTrabajo(trabajo.getIdTrabajo());
			matricula.setIdFicha(aula.getIdFicha());
			Matricula usuarioReceptor = new Matricula();
			usuarioReceptor.setIdMatricula(Integer.parseInt(idMatricula));
			matricula.setUsuarioReceptor(usuarioReceptor);
			
			TrabajoIndividualInteraccion interaccion = new TrabajoIndividualInteraccion();
			interaccion.setIdMensaje(Integer.parseInt(idMensaje));
			interaccion.setDescripcion(descripcion);
			Matricula usuarioEmisor = new Matricula();
			usuarioEmisor.setIdMatricula((aula.getMatriculaActual().getIdMatricula()));
			interaccion.setUsuarioEmisor(usuarioEmisor);
			interaccion.setUsuarioCreacion(""+usuario.getId());
			interaccion.setUsuarioModificacion(""+usuario.getId());
			interaccion.setArchivoNombre(filename);
			
			//File
			
			//***/
			int version = Integer.parseInt(idMensaje);
			
			;
			if(tIndividualService.obtenerMensajeEstado(matricula) == Constante.FLAG_PENDIENTE_DOCENTE)
				version++;
			//***/

			if(file != null && file.exists()){
				log.info("Envío de Mensaje con adjunto: "+filename+" - "+file.length()+" bytes");
				
				String extension = filename.substring(filename.lastIndexOf("."));		
				String origen = file.getAbsolutePath();
				if(!"/".equals(Constante.SLASH))
					origen = origen.replaceAll("/", Constante.SLASH);
				String directorioDestino = Constante.RUTA_TRABAJOS + aula.getPeriodo().getIdPeriodo() +
				Constante.SLASH + aula.getIdFicha() + Constante.SLASH + trabajo.getIdTrabajo();
				String nombreDestino = Constante.TRABAJO + "_" + idMatricula + "_" + version + extension;
				Archivo.copiarArchivo(origen, directorioDestino + Constante.SLASH + nombreDestino);
				
				interaccion.setArchivoNombre(nombreDestino);
				interaccion.setArchivoTamanio(String.valueOf(file.length()));
				
			}else{
				log.info("Envío de Mensaje sin adjunto");
			}
			
			//****
			
			Collection<TrabajoIndividualInteraccion> interacciones = new ArrayList<TrabajoIndividualInteraccion>();
			interacciones.add(interaccion);
			
			matricula.setInteracciones(interacciones);
			
			tIndividualService.enviarMensajeDeDocente(matricula);
		
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (NumberFormatException e){
			log.error(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
		}
			
		return SUCCESS;
	}
	
	public String verMensajesDeEstudiante(){
		log.info("verMensajesDeEstudiante()"+idUnidad);
		AulaVirtual aula = (AulaVirtual) getUsuarioSession().getAulaActual();
		TrabajoIndividual ti = null;
		
		try {
			if(aula != null && idUnidad != null){
				ti = new TrabajoIndividual();
				ti.setIdFicha(aula.getIdFicha());
				ti.setIdUnidad(Integer.parseInt(idUnidad));
				ti = tIndividualService.obtenerTrabajoIndividual(ti);
				
				if(ti.getInteraccion().getArchivoNombre() == null){
					String source = Constante.RUTA_UNIDADES+ Formato.completarCeros(idUnidad, 8) +
					Constante.SLASH + Constante.PATH_TRABAJO + Constante.TRABAJO_PDF;
					File trabajo = new File(source);
					if(trabajo.exists()){
						ti.getInteraccion().setArchivoNombre("DEFAULT");
					}
				}
				
				getSession().put(Constante.AULAVIRTUAL_TRABAJOINDIVIDUAL, ti);
				
				if(ti.getFechaActivacion() != null && ti.getFechaActivacion().before(new GregorianCalendar())){
					
					TrabajoIndividualMatricula matricula = new TrabajoIndividualMatricula();
					matricula.setIdTrabajo(ti.getIdTrabajo());
					matricula.setIdFicha(aula.getIdFicha());
					Matricula usuarioReceptor = new Matricula();
					usuarioReceptor.setIdMatricula((aula.getMatriculaActual().getIdMatricula()));
					matricula.setUsuarioReceptor(usuarioReceptor);
					
					matricula = tIndividualService.obtenerInteracciones(matricula);
					
					getRequest().setAttribute("TRABAJO_MATRICULA_INTERACCIONES", matricula);
				}
			}
			
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (NumberFormatException e){
			log.error(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
		}
		
		return SUCCESS;
	}
	
	public String enviarMensajeDeEstudiante(){
		
		log.info("enviarMensajeDeEstudiante() "+getRequest().getParameter("idMensaje"));
		Usuario usuario = (Usuario)getSession().get(Constante.USUARIO_ACTUAL);
		AulaVirtual aula = (AulaVirtual) getUsuarioSession().getAulaActual();
		TrabajoIndividual trabajo= (TrabajoIndividual)getSession().get(Constante.AULAVIRTUAL_TRABAJOINDIVIDUAL);
		String idMensaje = getRequest().getParameter("idMensaje");
		
		try {
			TrabajoIndividualMatricula matricula = new TrabajoIndividualMatricula();
			matricula.setIdTrabajo(trabajo.getIdTrabajo());
			matricula.setIdFicha(aula.getIdFicha());
			Matricula usuarioReceptor = new Matricula();
			usuarioReceptor.setIdMatricula((aula.getMatriculaActual().getIdMatricula()));
			matricula.setUsuarioReceptor(usuarioReceptor);
			matricula.setFechaCreacion(trabajo.getFechaEntrega());
			
			TrabajoIndividualInteraccion interaccion = new TrabajoIndividualInteraccion();
			interaccion.setIdMensaje(Integer.parseInt(idMensaje));
			interaccion.setDescripcion(descripcion);
			Matricula usuarioEmisor = new Matricula();
			usuarioEmisor.setIdMatricula((aula.getMatriculaActual().getIdMatricula()));
			interaccion.setUsuarioEmisor(usuarioEmisor);
			interaccion.setUsuarioCreacion(""+usuario.getId());
			interaccion.setUsuarioModificacion(""+usuario.getId());
			interaccion.setArchivoNombre(filename);
			
			//File
			
			//***/
			int version = Integer.parseInt(idMensaje);
			
			;
			if(tIndividualService.obtenerMensajeEstado(matricula) == Constante.FLAG_PENDIENTE_ESTUDIANTE)
				version++;
			//***/
			
			if(file != null && file.exists()){
				log.info("Envío de Mensaje con adjunto: "+filename+" - "+file.length()+" bytes");
				
				String extension = filename.substring(filename.lastIndexOf("."));		
				String origen = file.getAbsolutePath();
				if(!"/".equals(Constante.SLASH))
					origen = origen.replaceAll("/", Constante.SLASH);
				String directorioDestino = Constante.RUTA_TRABAJOS + aula.getPeriodo().getIdPeriodo() +
				Constante.SLASH + aula.getIdFicha() + Constante.SLASH + trabajo.getIdTrabajo();
				String nombreDestino = Constante.TRABAJO + "_" + aula.getMatriculaActual().getIdMatricula() + "_" + version + extension;
				Archivo.copiarArchivo(origen, directorioDestino + Constante.SLASH + nombreDestino);
				
				interaccion.setArchivoNombre(nombreDestino);
				interaccion.setArchivoTamanio(String.valueOf(file.length()));
				
			}else{
				log.info("Envío de Mensaje sin adjunto");
			}
			
			//****
			
			Collection<TrabajoIndividualInteraccion> interacciones = new ArrayList<TrabajoIndividualInteraccion>();
			interacciones.add(interaccion);
			
			matricula.setInteracciones(interacciones);
			
			tIndividualService.enviarMensajeDeEstudiante(matricula);
		
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (NumberFormatException e){
			log.error(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
		}
			
		return SUCCESS;
	}
	
	public String descargarTrabajo(){
		log.info("descargarTrabajo() " + getUsuarioSession().getAulaActual().getMatriculaActual().getIdMatricula());
		
		AulaVirtual aula = (AulaVirtual) getUsuarioSession().getAulaActual();
		TrabajoIndividual trabajo= (TrabajoIndividual)getSession().get(Constante.AULAVIRTUAL_TRABAJOINDIVIDUAL);
		
		try {
			if(aula != null && trabajo != null && aula.getPeriodo().getIdPeriodo() != 0){
				String source = Constante.RUTA_TRABAJOS + aula.getPeriodo().getIdPeriodo() +
				Constante.SLASH + aula.getIdFicha() + Constante.SLASH + trabajo.getIdTrabajo() + Constante.SLASH + filename;
				//**** Anti Hack URL inyection
				if(aula.getMatriculaActual().getRol().getIdRol() == Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE 
						&& filename.indexOf(String.valueOf(aula.getMatriculaActual().getIdMatricula())) == -1 && filename.split("_").length==3){
					getResponse().getWriter().print("<h2>Este intento antietico y nada profesional ya ha sido reportado a la oficina de la Secretaría Docente,<br>Se aplicarán todas las medidas del caso. </h2>");
					return NONE;
				}
				//****
				Archivo.downloadFile(filename, source, getResponse());
			}else{
				log.error("Error al leer parametros requeridos para la descarga.");
			}
		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		return NONE;
	}
	
	public String descargarTrabajoPredeterminado(){
		log.info("descargarTrabajoPredeterminado()");
		
		AulaVirtual aula = (AulaVirtual) getUsuarioSession().getAulaActual();
		
		try{
			if(aula != null && !aula.getSilabo().getUnidades().isEmpty() && idUnidad != null && Util.esMiUnidad(aula.getSilabo().getUnidades(), idUnidad)){
				String source = Constante.RUTA_UNIDADES+ Formato.completarCeros(idUnidad, 8) +
				Constante.SLASH + Constante.PATH_TRABAJO + Constante.TRABAJO_PDF;
				String filenameOut = Constante.TRABAJO + "_" + idUnidad + Constante.EXTENSION_PDF;
				
				Archivo.downloadFile(filenameOut, source, getResponse());
				
			}else{
				log.error("Error al leer parametros requeridos para la descarga.");
			}
		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		return NONE;
	}
	
}
