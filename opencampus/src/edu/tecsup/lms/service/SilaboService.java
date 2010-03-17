package edu.tecsup.lms.service;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tecsup.lms.dao.SilaboDAO;
import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.Silabo;
import edu.tecsup.lms.modelo.ficha.Unidad;

public class SilaboService {

	Log log = LogFactory.getLog(getClass());

	private SilaboDAO silaboDAO;

	public void setSilaboDAO(SilaboDAO silaboDAO) {
		this.silaboDAO = silaboDAO;
	}

	public void modificarIndiceUnidad(boolean cmd, int idSilabo, int idUnidad)
			throws ServiceException {
		try {
			silaboDAO.modificarIndiceUnidad(cmd, idSilabo, idUnidad);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}

	}

	public Collection<Unidad> buscarNuevasUnidades(String nombre, int idSilabo) throws ServiceException {
		try {
			return silaboDAO.buscarNuevasUnidades(nombre, idSilabo);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}

	public void agregarUnidad(Integer idSilabo, Integer idUnidad) throws ServiceException {
		try {
			silaboDAO.agregarUnidad(idSilabo, idUnidad);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}

	public void eliminarUnidad(int idSilabo, int idUnidad)
			throws ServiceException {
		try {
			silaboDAO.eliminarUnidad(idSilabo, idUnidad);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}

	}

	public Silabo obtener(int idSilabo) throws ServiceException {
		try {
			return silaboDAO.obtener(idSilabo);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}

	public Collection<Silabo> buscarPorIDCurso(int idCurso) throws ServiceException {
		try {
			return silaboDAO.buscarPorIDCurso(idCurso);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}

	public void crear(Silabo silabo) throws ServiceException {
		try {
			silaboDAO.crear(silabo);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}

	public void modificar(Silabo silabo) throws ServiceException {
		try {
			silaboDAO.modificar(silabo);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}

	public void eliminar(int idSilabo) throws ServiceException {
		try {
			silaboDAO.eliminar(idSilabo);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}

}
