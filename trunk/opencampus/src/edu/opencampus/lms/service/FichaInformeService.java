package edu.opencampus.lms.service;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.dao.FichaInformeDAO;
import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.Periodo;
import edu.opencampus.lms.modelo.ficha.informe.FichaInforme;

public class FichaInformeService {

	private FichaInformeDAO fichaInformeDAO;
	protected Log log = LogFactory.getLog(getClass());

	public void persistir(FichaInforme modelo) throws ServiceException {
		try {
			fichaInformeDAO.persistir(modelo);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

	public FichaInforme obtener(int codcurso, int codperiodo) throws ServiceException {
		FichaInforme modelo = null;
		try {
			modelo = fichaInformeDAO.obtener(codcurso, codperiodo);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return modelo;
	}

	public void eliminar(int codcurso, int codperiodo) throws ServiceException {
		try {
			fichaInformeDAO.eliminar(codcurso, codperiodo);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

	public Collection<Periodo> listarHistoricos(Integer codcurso) throws ServiceException {
		Collection<Periodo> periodos = null;
		try {
			periodos = fichaInformeDAO.listarHistoricos(codcurso);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return periodos;
	}
	
	public void setFichaInformeDAO(FichaInformeDAO fichaInformeDAO) {
		this.fichaInformeDAO = fichaInformeDAO;
	}

}
