package edu.opencampus.lms.service;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.dao.PublicacionDAO;
import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.ficha.Publicacion;

public class PublicacionService {

	Log log = LogFactory.getLog(getClass());
	
	private PublicacionDAO publicacionDAO;

	public void setPublicacionDAO(PublicacionDAO publicacionDAO) {
		this.publicacionDAO = publicacionDAO;
	}
	
	public void publicarAgain(Integer idPublicacion)throws ServiceException{
		try {
			publicacionDAO.publicarAgain(idPublicacion);
		} catch (DAOException e) {			
			throw new ServiceException(e.getMessage());
		}
	}
	
	public Integer nuevaPublicacion(Publicacion pub)throws ServiceException{	
		try {							
			return publicacionDAO.nuevaPublicacion(pub);			
		} catch (DAOException e) {			
			throw new ServiceException(e.getMessage());
		}	}
	
	public Collection<Publicacion> listarPublicacionesNoLeidas(Integer idMatricula, Integer idFicha)throws ServiceException{
		try {
			return publicacionDAO.listarPublicacionesNoLeidas(idMatricula,idFicha);
		} catch (Exception e) {			
			throw new ServiceException(e.getMessage());
		}
	}
	
	public Collection<Publicacion>listarMensajes(int idFicha, int idHerramienta)throws ServiceException{		
		Collection<Publicacion> publicaciones = null;
		try {
			publicaciones = publicacionDAO.listarMensajes(idFicha, idHerramienta);
		} catch (DAOException e) {			
			throw new ServiceException(e.getMessage());
		}
		return publicaciones;
	}	
	
	public void eliminarPublicacion(Integer id)throws ServiceException{
		try {
			publicacionDAO.eliminarPublicacion(id);
		} catch (DAOException e) {			
			log.error(e.getMessage());
		}
	}	
	
	public void publicacionLeida(Integer idPub, Integer idMatricula)throws ServiceException{
		try {
			publicacionDAO.publicacionLeida(idPub, idMatricula);
		} catch (DAOException e) {			
			log.error(e.getMessage());
		}
	}	
	
}