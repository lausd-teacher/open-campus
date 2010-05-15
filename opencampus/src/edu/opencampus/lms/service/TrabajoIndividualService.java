package edu.opencampus.lms.service;

import java.util.Collection;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.dao.TrabajoIndividualDAO;
import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.TrabajoIndividual;
import edu.opencampus.lms.modelo.tindividual.TrabajoIndividualMatricula;
import edu.opencampus.lms.util.Constante;

public class TrabajoIndividualService {

	Log log = LogFactory.getLog(getClass());
	
	private TrabajoIndividualDAO trabajoIndividualDAO;
 
	public void setTrabajoIndividualDAO(TrabajoIndividualDAO trabajoIndividualDAO) {
		this.trabajoIndividualDAO = trabajoIndividualDAO;
	}
	
	public TrabajoIndividual obtenerTrabajoIndividual(TrabajoIndividual ti) throws ServiceException{
		try {
			ti = trabajoIndividualDAO.obtenerTrabajoIndividual(ti);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return ti;
	}

	public void publicarTrabajo(TrabajoIndividual tindividual) throws ServiceException{
		try {
			trabajoIndividualDAO.publicarTrabajo(tindividual);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

	public void modificarTrabajo(TrabajoIndividual tindividual) throws ServiceException{
		try {
			trabajoIndividualDAO.modificarTrabajo(tindividual);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
	
	public void eliminarTrabajo(TrabajoIndividual tindividual) throws ServiceException{
		try {
			trabajoIndividualDAO.eliminarTrabajo(tindividual);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
	
	public Collection<TrabajoIndividualMatricula> listarTrabajoPorMatricula(TrabajoIndividual idTrabajo) throws ServiceException{
		Collection<TrabajoIndividualMatricula> matriculas = null;
		try {
			matriculas = trabajoIndividualDAO.listarTrabajoPorMatricula(idTrabajo);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return matriculas;
	}

	public void calificarTrabajo(TrabajoIndividualMatricula matricula) throws ServiceException{
		try {
			trabajoIndividualDAO.calificarTrabajo(matricula);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

	public TrabajoIndividualMatricula obtenerInteracciones(TrabajoIndividualMatricula matricula) throws ServiceException{
		try {
			matricula = trabajoIndividualDAO.obtenerInteracciones(matricula);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return matricula;
	}
	
	public void enviarMensajeDeDocente(TrabajoIndividualMatricula matricula) throws ServiceException{
		try {
			matricula = trabajoIndividualDAO.obtenerMensajeEstadoYExpiro(matricula);
			if(matricula.getEstado() == Constante.FLAG_PENDIENTE_DOCENTE){
				trabajoIndividualDAO.nuevoMensajeDeDocente(matricula);
			}else{
				trabajoIndividualDAO.modificarMensaje(matricula);
			}
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
	
	public void enviarMensajeDeEstudiante(TrabajoIndividualMatricula matricula) throws ServiceException{
		try {
			matricula = trabajoIndividualDAO.obtenerMensajeEstadoYExpiro(matricula);
			if(matricula.getEstado() != Constante.FLAG_PENDIENTE_DOCENTE){
				if(matricula.getExpirado() == 0){
					if(((new GregorianCalendar()).before(matricula.getFechaCreacion())))
						matricula.setExpirado(1);
					else
						matricula.setExpirado(2);
				}
				trabajoIndividualDAO.nuevoMensajeDeEstudiante(matricula);
			}else{
				trabajoIndividualDAO.modificarMensaje(matricula);
			}
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
	
	//*****
	public int obtenerMensajeEstado(TrabajoIndividualMatricula matricula) throws ServiceException{
		try {
			matricula = trabajoIndividualDAO.obtenerMensajeEstadoYExpiro(matricula);
			
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return matricula.getEstado();
	}

}
