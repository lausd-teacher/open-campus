package edu.tecsup.lms.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tecsup.lms.dao.DebateDAO;
import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.AulaVirtual;
import edu.tecsup.lms.modelo.Debate;

public class DebateService {

	Log log = LogFactory.getLog(getClass());

	private DebateDAO debateDAO;

	public void setDebateDAO(DebateDAO debateDAO) {
		this.debateDAO = debateDAO;
	}

	public List<Debate> obtenerPlactica(AulaVirtual aula, int idGrupo)
			throws ServiceException {
		List<Debate> lista = null;
		try {
			lista = debateDAO.obtenerPlactica(aula, idGrupo);
		} catch (DAOException e) {
			log.debug(e.toString());
			throw new ServiceException(e.toString());
		}
		return lista;
	}

	public List<Debate> obtenerSubPlactica(AulaVirtual aula, int idDebate) throws ServiceException {
		List<Debate> lista = null;
		try {
			lista = debateDAO.obtenerSubPlactica(aula, idDebate);
		} catch (DAOException e) {
			log.debug(e);
			throw new ServiceException(e.toString());
		}
		return lista;
	}
	
	public Debate obtenerDebate(AulaVirtual aula, int idDebate, boolean soloTexto) throws ServiceException {
		Debate debate = null;
		try {
			marcarLeido(idDebate, aula.getIdMatricula(), 1);
			debate = debateDAO.obtenerDebate(aula, idDebate, soloTexto);
		} catch (DAOException e) {
			log.debug(e);
			throw new ServiceException(e.toString());
		}
		return debate;
	}
	
	public Debate crearPlactica(AulaVirtual aula, int idUnidad, Debate modelo) throws ServiceException {
		try {
			modelo = debateDAO.crearPlactica(aula, idUnidad, modelo);
		} catch (DAOException e) {
			log.debug(e.toString());
			throw new ServiceException(e.toString());
		}
		return modelo;
	}

	public void eliminarPlactica(String idUsuario, int idDebate)
			throws ServiceException {
		try {
			debateDAO.eliminarPlactica(idUsuario, idDebate);
		} catch (DAOException e) {
			log.debug(e.toString());
			throw new ServiceException(e.toString());
		}

	}

	public void marcarLeido(int idDebate, int idMatricula, int estado) throws ServiceException {
		try {
			debateDAO.leerPlactica(idDebate, idMatricula, estado);
		} catch (DAOException e) {
			log.debug(e.toString());
			throw new ServiceException(e.toString());
		}

	}
	
}