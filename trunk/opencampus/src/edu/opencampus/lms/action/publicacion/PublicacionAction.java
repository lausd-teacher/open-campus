package edu.opencampus.lms.action.publicacion;

import java.io.File;
import java.io.PrintWriter;
import java.util.Collection;

import edu.opencampus.lms.action.BaseAction;
import edu.opencampus.lms.excepcion.ActionException;
import edu.opencampus.lms.modelo.AulaVirtual;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.modelo.ficha.Publicacion;
import edu.opencampus.lms.service.PublicacionService;
import edu.opencampus.lms.util.Archivo;
import edu.opencampus.lms.util.Constante;

public class PublicacionAction extends BaseAction{

	private static final long serialVersionUID = -2512713832686101713L;
	
	public File doc;
	public String docContentType;
	public String docFileName;
	String titulo;
	String contenido;
	Integer idPublicacion;
	PublicacionService publicacionService;
	Collection<Publicacion> PUBLICACIONES;
	int size;
	String archivoNombre;
	String mensaje;

	public void setPublicacionService(PublicacionService publicacionService) {
		this.publicacionService = publicacionService;
	}
	
	
	/*
	 * Avisos
	 */
	
	public String listarMensajes()throws ActionException{	
		log.info("listarMensajes()");
		try {			
			PUBLICACIONES = publicacionService.listarMensajes(getUsuarioSession().getAulaActual().getIdFicha(),Constante.TIPO_PUBLICACION_AVISO);			
			size = PUBLICACIONES.size();			
		} catch (Exception e) {			
			log.error(e.getMessage());
			throw new ActionException(e.getMessage());
		}
		return SUCCESS;
	}
	
	public String publicarMensaje()throws ActionException{
		log.info("publicarMensaje()");
		try {
			if(titulo != null && titulo.trim().length()>0 && contenido != null && contenido.trim().length()>0){
				Publicacion pub = new Publicacion();
				pub.setIdHerramienta(Constante.TIPO_PUBLICACION_AVISO);
				pub.setIdFicha(getUsuarioSession().getAulaActual().getIdFicha());
				pub.setTitulo(titulo);
				pub.setContenido(contenido);		
				pub.setUsuario(new Usuario(getUsuarioSession().getId()));
				publicacionService.nuevaPublicacion(pub);				
			}else{
				throw new ActionException("Debe tener titulo y contenido");
			}
		} catch (Exception e) {			
			log.error(e.getMessage());
			throw new ActionException(e.getMessage());
		}		
		return SUCCESS;
	}
	
	/*
	 * Lecturas
	 */
	
	public String listarRecursos()throws ActionException{		
		log.info("listarRecursos()");
		try {			
			PUBLICACIONES = publicacionService.listarMensajes(getUsuarioSession().getAulaActual().getIdFicha(),Constante.TIPO_PUBLICACION_LECTURA);			
			size = PUBLICACIONES.size();			
		} catch (Exception e) {			
			log.error(e.getMessage());
			throw new ActionException(e.getMessage());
		}
		return SUCCESS;
	}
	
	public String publicarRecurso()throws ActionException{	
		log.info("publicarRecurso() T: "+titulo+" - F: "+docFileName);
		try {
			
			AulaVirtual aula = getUsuarioSession().getAulaActual();
			int idFicha = aula.getIdFicha();	
			
			Publicacion pub = new Publicacion();
			pub.setIdHerramienta(Constante.TIPO_PUBLICACION_LECTURA);
			pub.setArchivoNombre(docFileName);
			pub.setArchivoTamanio(doc.length());
			pub.setUsuario(new Usuario(getUsuarioSession().getId()));
			pub.setIdFicha(aula.getIdFicha());
			pub.setTitulo(titulo);
			
			String extension = docFileName.substring(docFileName.lastIndexOf("."));
			String name = doc.getAbsolutePath().replaceAll("/", Constante.SLASH);
		
			Integer idPublicacion = publicacionService.nuevaPublicacion(pub);			
		
			Archivo.crearDirectorio(Constante.RUTA_PUBLICACIONES + idFicha);
			Archivo.copiarArchivo(name,Constante.RUTA_PUBLICACIONES + idFicha + Constante.SLASH+idPublicacion+extension);
			
		} catch (Exception e) {			
			log.error(e.getMessage());
			throw new ActionException(e.getMessage());
		}		
		return SUCCESS;
	}
	
	public String descargarArchivo()throws ActionException{	
		log.info("descargarArchivo() id:"+idPublicacion+" file:"+archivoNombre);
		try {
			AulaVirtual aula = getUsuarioSession().getAulaActual();
			int idFicha = aula.getIdFicha();		
			String extension = archivoNombre.substring(archivoNombre.lastIndexOf("."));	
			String source = Constante.RUTA_PUBLICACIONES + idFicha + Constante.SLASH+idPublicacion+extension;
		
			Archivo.downloadFile(archivoNombre, source, getResponse());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return NONE;
	}	
	
	/*
	 * Mixto
	 */
	
	public String publicarAgain()throws ActionException{
		log.info("publicarAgain()");
		try {
			publicacionService.publicarAgain(idPublicacion);
		} catch (Exception e) {			
			log.error(e);
		}
		return NONE;
	}
	
	public String eliminar()throws ActionException{	
		log.info("eliminar()");
		try {
			publicacionService.eliminarPublicacion(idPublicacion);
		} catch (Exception e) {			
			log.error(e.getMessage());
			throw new ActionException(e.getMessage());
		}
		return SUCCESS;
	}
		
	public String cambiarEstado()throws ActionException{
		log.info("cambiarEstado() "+idPublicacion);
		try {
			publicacionService.publicacionLeida(idPublicacion, getUsuarioSession().getAulaActual().getMatriculaActual().getIdMatricula());						
			PUBLICACIONES = publicacionService.listarPublicacionesNoLeidas(getUsuarioSession().getAulaActual().getMatriculaActual().getIdMatricula(),getUsuarioSession().getAulaActual().getIdFicha());
			PrintWriter pw = getResponse().getWriter();
			pw.print(PUBLICACIONES.size());
			pw.flush();
		} catch (Exception e) {
			log.error(e);
		} 
		return NONE;
	}
	

	public File getDoc() {
		return doc;
	}

	public void setDoc(File doc) {
		this.doc = doc;
	}

	public String getDocContentType() {
		return docContentType;
	}

	public void setDocContentType(String docContentType) {
		this.docContentType = docContentType;
	}

	public String getDocFileName() {
		return docFileName;
	}

	public void setDocFileName(String docFileName) {
		docFileName = docFileName.replaceAll("#", " Nº");
		docFileName = docFileName.replaceAll("&", " y ");				
		this.docFileName = docFileName;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getArchivoNombre() {
		return archivoNombre;
	}

	public void setArchivoNombre(String archivoNombre) {
		this.archivoNombre = archivoNombre;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Integer getIdPublicacion() {
		return idPublicacion;
	}

	public void setIdPublicacion(Integer idPublicacion) {
		this.idPublicacion = idPublicacion;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Collection<Publicacion> getPUBLICACIONES() {
		return PUBLICACIONES;
	}

	public void setPUBLICACIONES(Collection<Publicacion> publicaciones) {
		PUBLICACIONES = publicaciones;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}