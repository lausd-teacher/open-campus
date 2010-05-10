package edu.opencampus.lms.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.dao.AnotacionDAO;
import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.Anotacion;

public class AnotacionService {

	Log log = LogFactory.getLog(getClass());

	private AnotacionDAO anotacionDAO;

	public void setAnotacionDAO(AnotacionDAO anotacionDAO) {
		this.anotacionDAO = anotacionDAO;
	}

	public List<Anotacion> cargarPortada(int usuario)
			throws ServiceException {
		List<Anotacion> anotaciones = null;
		try {
			anotaciones = anotacionDAO.cargarPortada(usuario);
		} catch (DAOException e) {
			log.error(e.toString());
		}
		return anotaciones;
	}

	public List<Anotacion> obtener(int usuario) throws ServiceException {
		List<Anotacion> anotaciones = null;
		try {
			anotaciones = anotacionDAO.obtener(usuario);
		} catch (DAOException e) {
			log.error(e.toString());
		}
		return anotaciones;
	}

	public Integer insertar(Anotacion anotacion) throws ServiceException {
		try {
			return anotacionDAO.insertar(anotacion);
		} catch (DAOException e) {
			log.error(e.toString());
		}
		return 0;
	}

	public int eliminar(Anotacion anotacion) throws ServiceException {
		int rpt = 0;
		try {
			rpt = anotacionDAO.eliminar(anotacion);
		} catch (DAOException e) {
			log.error(e.toString());
		}
		return rpt;
	}

	public Anotacion obtener(Anotacion anotacion) throws ServiceException {
		Anotacion a = new Anotacion();
		try {
			a = anotacionDAO.obtener(anotacion);
		} catch (DAOException e) {
			log.error(e.toString());
			throw new ServiceException("Anotacion no encontrada.");
		}
		return a;
	}

	public int actualizar(Anotacion anotacion) throws ServiceException {
		int rpt = 0;
		try {
			rpt = anotacionDAO.actualizar(anotacion);
		} catch (DAOException e) {
			log.error(e.toString());
		}
		return rpt;
	}

}