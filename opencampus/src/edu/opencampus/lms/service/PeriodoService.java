package edu.opencampus.lms.service;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.dao.PeriodoDAO;
import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.Periodo;

public class PeriodoService {

	Log log = LogFactory.getLog(getClass());

	private PeriodoDAO periodoDAO;

	public void setPeriodoDAO(PeriodoDAO periodoDAO) {
		this.periodoDAO = periodoDAO;
	}

	public Collection<Periodo> listarPeriodos() throws ServiceException {
		Collection<Periodo> c = null;
		try {
			c = periodoDAO.listarPeriodos();
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return c;
	}
	
	public Collection<Periodo> listarVigentes(boolean iniciado) throws ServiceException {
		Collection<Periodo> c = null;
		try {
			c = periodoDAO.listarVigentes(iniciado);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return c;
	}
	
	public boolean esDuplicado(String nombre) throws ServiceException {
		boolean esDuplicado = false;
		try {
			esDuplicado = periodoDAO.esDuplicado(nombre);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return esDuplicado;
	}

	public Integer crear(Periodo periodo) throws ServiceException {
		try {
			return periodoDAO.crear(periodo);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}
	
	public Periodo obtener(Integer idPeriodo) throws ServiceException {
		Periodo p = null;
		try {
			p = periodoDAO.obtener(idPeriodo);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return p;
	}

	public void modificar(Periodo periodo) throws ServiceException {
		try {
			periodoDAO.modificar(periodo);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}

	}
	
	public void eliminar(Integer idPeriodo) throws ServiceException {
		try {
			periodoDAO.eliminar(idPeriodo);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}
	
/*
	

	public Collection<Periodo> obtenerUDS() throws ServiceException {
		Collection<Periodo> c = new ArrayList<Periodo>();
		try {
			c = periodoDAO.obtenerUDS();
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return c;
	}
	
	public Collection<Periodo> obtenerTodo() throws ServiceException {
		Collection<Periodo> c = new ArrayList<Periodo>();
		try {
			c = periodoDAO.obtenerTodo();
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return c;
	}
	
	public Collection<Periodo> obtenerTodoDeUsuario(String idUsuario) throws ServiceException {
		Collection<Periodo> c = new ArrayList<Periodo>();
		try {
			c = periodoDAO.obtenerTodoDeUsuario(idUsuario);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return c;
	}
	
	public Collection<Periodo> obtenerVigentes() throws ServiceException {
		Collection<Periodo> c = new ArrayList<Periodo>();
		try {
			c = periodoDAO.obtenerVigentes();
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return c;
	}
	
	public Collection<Integer> obtenerFichasPorPeriodo(int idPeriodo) throws ServiceException {
		Collection<Integer> c = new ArrayList<Integer>();
		try {
			c = periodoDAO.obtenerFichasPorPeriodo(idPeriodo);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return c;
	}*/
}
