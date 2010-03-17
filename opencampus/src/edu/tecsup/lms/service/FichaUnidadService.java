package edu.tecsup.lms.service;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tecsup.lms.dao.FichaUnidadDAO;
import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.ficha.FichaUnidad;

public class FichaUnidadService {

	Log log = LogFactory.getLog(getClass());
	
	private FichaUnidadDAO fichaUnidadDAO;

	public void setFichaUnidadDAO(FichaUnidadDAO fichaUnidadDAO) {
		this.fichaUnidadDAO = fichaUnidadDAO;
	}
	
	public void modificar(FichaUnidad fichaUnidad) throws ServiceException {
		try {
			fichaUnidadDAO.modificar(fichaUnidad);
		} catch (DAOException e) {
			log.error(e.getMessage());
		}
	}
	
	public Collection<FichaUnidad> obtener(String idFicha, String idSilabo) throws ServiceException {
		Collection<FichaUnidad> c = null;
		try {
			c = fichaUnidadDAO.obtener(idFicha, idSilabo);
		} catch (DAOException e) {
			log.error(e.getMessage());
		}
		return c;
	}
	
	public FichaUnidad obtener(String idFicha, String idSilabo, String idUnidad) throws ServiceException {
		FichaUnidad vo = null;
		try {
			vo = fichaUnidadDAO.obtener(idFicha, idSilabo, idUnidad);
		} catch (DAOException e) {
			log.error(e.getMessage());
		}
		return vo;
	}
}
