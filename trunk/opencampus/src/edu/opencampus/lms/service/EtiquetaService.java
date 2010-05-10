package edu.opencampus.lms.service;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.dao.EtiquetaDAO;
import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.ficha.Etiqueta;

public class EtiquetaService {

	private EtiquetaDAO etiquetaDAO;

	Log log = LogFactory.getLog(getClass());

	public void setEtiquetaDAO(EtiquetaDAO etiquetaDAO) {
		this.etiquetaDAO = etiquetaDAO;
	}

	public Collection<Etiqueta> obtenerEtiquetasPerdeterminadas()
			throws ServiceException {
		Collection<Etiqueta> col = new ArrayList<Etiqueta>();
		try {
			col = etiquetaDAO.obtenerEtiquetasPredeterminadas();
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return col;
	}

	public Collection<Etiqueta> obtenerEtiquetasPersonalizadas()
			throws ServiceException {
		Collection<Etiqueta> col = new ArrayList<Etiqueta>();
		try {
			col = etiquetaDAO.obtenerEtiquetasPersonalizadas();
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return col;
	}

}
