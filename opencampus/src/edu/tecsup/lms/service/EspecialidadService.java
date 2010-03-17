package edu.tecsup.lms.service;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tecsup.lms.dao.EspecialidadDAO;
import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.Especialidad;

public class EspecialidadService {

	private Log log = LogFactory.getLog(getClass());

	private EspecialidadDAO especialidadDAO;

	public void setEspecialidadDAO(EspecialidadDAO especialidadDAO) {
		this.especialidadDAO = especialidadDAO;
	}

	public Collection<Especialidad> obtenerTodos() throws ServiceException {
		log.info("obtenerTodos()");
		Collection<Especialidad> espe = new ArrayList<Especialidad>();
		try {
			espe = especialidadDAO.obtenerTodos();
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return espe;
	}

}
