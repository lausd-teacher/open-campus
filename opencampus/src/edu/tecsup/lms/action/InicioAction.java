package edu.tecsup.lms.action;

import java.io.FileNotFoundException;
import java.util.Collection;

import edu.tecsup.lms.modelo.Noticia;
import edu.tecsup.lms.service.NoticiaService;
import edu.tecsup.lms.util.Archivo;
import edu.tecsup.lms.util.Constante;

public class InicioAction extends BaseAction {

	private static final long serialVersionUID = -5846600561754956550L;

	private String inconveniente;
	private NoticiaService noticiaService;

	private String idNoticia;
	private String filename;
	private Noticia noticia;
	private Collection<Noticia> noticias;
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Noticia getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}

	public Collection<Noticia> getNoticias() {
		return noticias;
	}

	public void setNoticias(Collection<Noticia> noticias) {
		this.noticias = noticias;
	}

	public void setNoticiaService(NoticiaService noticiaService) {
		this.noticiaService = noticiaService;
	}

	public String getIdNoticia() {
		return idNoticia;
	}

	public void setIdNoticia(String idNoticia) {
		this.idNoticia = idNoticia;
	}
	public String getInconveniente() {
		return inconveniente;
	}

	public void setInconveniente(String inconveniente) {
		this.inconveniente = inconveniente;
	}

	public String none() {
		log.info("KeepAlive: "+getUsuarioSession()+" - Msg: " + message);
		return NONE;
	}

	public String execute() throws Exception{
		log.info("OpenCampus Start");
		noticias = noticiaService.listarNoticiasIntranet();
		return SUCCESS;
	}
	
	public String noticiaDetalle() throws Exception{
		log.info("NoticiaDetalle");
		noticia = noticiaService.obtenerNoticiaDetalle(idNoticia);
		return SUCCESS;
	}
	
	public String verImagenPrevia() throws Exception{
		try {
			idNoticia = filename.substring(filename.indexOf('_')+1,filename.indexOf('.'));
			if(noticiaService.esPublica(Integer.parseInt(idNoticia))){
				String source = Constante.RUTA_NOTICIA + filename;
				Archivo.downloadResizedFile(filename, source, 180, 0, getResponse());
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
	
	public String verImagen() throws Exception{
		try {
			idNoticia = filename.substring(filename.indexOf('_')+1,filename.indexOf('.'));
			if(noticiaService.esPublica(Integer.parseInt(idNoticia))){
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

}
