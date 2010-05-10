package edu.opencampus.lms.service;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.dao.NoticiaDAO;
import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.Noticia;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.modelo.noticia.Seccion;

public class NoticiaService {

	Log log = LogFactory.getLog(getClass());
	
	private NoticiaDAO noticiaDAO;

	public void setNoticiaDAO(NoticiaDAO noticiaDAO) {
		this.noticiaDAO = noticiaDAO;
	}
	
	public Collection<Noticia> cargarPortada(Usuario user) throws ServiceException{
		Collection<Noticia> noticias;
		try{
			noticias = noticiaDAO.cargarPortada(user);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return noticias;
	}

	public Collection<Seccion> cargarTitulares(Usuario user) throws ServiceException{
		Collection<Seccion> noticias;
		try{
			noticias = noticiaDAO.cargarTitulares(user);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return noticias;
	}

	
	// ****************************** NOTICIA *******************************
	
	public Collection<Noticia> listar() throws ServiceException{
		Collection<Noticia> noticias;
		try{
			noticias = noticiaDAO.listar();
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return noticias;
	}
	
	public Noticia verNoticia(int idnoticia) throws ServiceException{
		Noticia noticia;
		try{
			noticia = noticiaDAO.verNoticia(idnoticia);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return noticia;
	}
	
	public Noticia obtenerNoticia(int idnoticia) throws ServiceException{
		Noticia noticia;
		try{
			noticia = noticiaDAO.obtenerNoticia(idnoticia);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return noticia;
	}
	
	public void crearNoticia(Noticia noticia) throws ServiceException{
		try{
			noticiaDAO.crearNoticia(noticia);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public void modificarNoticia(Noticia noticia) throws ServiceException{
		try{
			noticiaDAO.modificarNoticia(noticia);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public void eliminarNoticia(int idNoticia) throws ServiceException{
		try{
			noticiaDAO.eliminarNoticia(idNoticia);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public void cambiarEstado(int idNoticia, int estado) throws ServiceException{
		try{
			noticiaDAO.cambiarEstado(idNoticia, estado);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	// ************************* SECCIONES *****************************************//
	
	public Collection<Seccion> listarSecciones() throws ServiceException{
		Collection<Seccion> secciones;
		try{
			secciones = noticiaDAO.listarSecciones();
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return secciones;
	}
	
	public void crearSeccion(Seccion seccion) throws ServiceException{
		try{
			noticiaDAO.crearSeccion(seccion);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public void eliminarSeccion(int idSeccion) throws ServiceException{
		try{
			noticiaDAO.eliminarSeccion(idSeccion);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public void modificarIndiceSeccion(boolean cmd, int idSeccion) throws ServiceException{
		try{
			noticiaDAO.modificarIndiceSeccion(cmd, idSeccion);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public void modificarSeccion(Seccion seccion) throws ServiceException{
		try{
			noticiaDAO.modificarSeccion(seccion);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	//******************************** NOTICIAS INTRANET *********************************	
	public Collection<Noticia> listarNoticiasIntranet() throws ServiceException {
		Collection<Noticia> noticias = new ArrayList<Noticia>();
		try {
			noticias = noticiaDAO.listarNoticiasIntranet();
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return noticias;
	}
	
	public Noticia obtenerNoticiaDetalle(String idNoticia) throws ServiceException {
		Noticia noticia = null;
		try {
			noticia = noticiaDAO.obtenerNoticiaDetalle(idNoticia);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return noticia;
	}
	
	public boolean esPublica(Integer idnoticia) throws ServiceException {
		boolean espublica = false;
		try {
			espublica = noticiaDAO.esPublica(idnoticia);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return espublica;
	}

}
