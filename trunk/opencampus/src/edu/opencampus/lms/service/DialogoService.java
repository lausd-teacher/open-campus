package edu.opencampus.lms.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.dao.DialogoDAO;
import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.AulaVirtual;
import edu.opencampus.lms.modelo.Dialogo;

public class DialogoService {

	Log log = LogFactory.getLog(getClass());

	private DialogoDAO dialogoDAO;

	public void setDialogoDAO(DialogoDAO dialogoDAO) {
		this.dialogoDAO = dialogoDAO;
	}

	public boolean isEnabledDialogo(AulaVirtual aula, int idUnidad) throws ServiceException {
		boolean isEnabled = false;
		try {
			isEnabled = dialogoDAO.isEnabledDialogo(aula, idUnidad);
		} catch (DAOException e) {
			log.debug(e);
			throw new ServiceException(e.toString());
		}
		return isEnabled;
	}


	public List<Dialogo> obtenerPlactica(AulaVirtual aula, int idUnidad)
			throws ServiceException {
		List<Dialogo> lista = null;
		try {
			lista = dialogoDAO.obtenerPlactica(aula, idUnidad);
		} catch (DAOException e) {
			log.debug(e.toString());
			throw new ServiceException(e.toString());
		}
		return lista;
	}

	public List<Dialogo> obtenerSubPlactica(AulaVirtual aula, int idDialogo) throws ServiceException {
		List<Dialogo> lista = null;
		try {
			lista = dialogoDAO.obtenerSubPlactica(aula, idDialogo);
		} catch (DAOException e) {
			log.debug(e);
			throw new ServiceException(e.toString());
		}
		return lista;
	}
	
	public Dialogo obtenerDialogo(AulaVirtual aula, int idDialogo, boolean soloTexto) throws ServiceException {
		Dialogo dialogo = null;
		try {
			marcarLeido(idDialogo, aula.getMatriculaActual().getIdMatricula(), 1);
			dialogo = dialogoDAO.obtenerDialogo(aula, idDialogo, soloTexto);
		} catch (DAOException e) {
			log.debug(e);
			throw new ServiceException(e.toString());
		}
		return dialogo;
	}
	
	public Dialogo crearPlactica(AulaVirtual aula, int idUnidad, Dialogo modelo) throws ServiceException {
		try {
			modelo = dialogoDAO.crearPlactica(aula, idUnidad, modelo);
		} catch (DAOException e) {
			log.debug(e.toString());
			throw new ServiceException(e.toString());
		}
		return modelo;
	}

	public void eliminarPlactica(String idUsuario, int idDialogo)
			throws ServiceException {
		try {
			dialogoDAO.eliminarPlactica(idUsuario, idDialogo);
		} catch (DAOException e) {
			log.debug(e.toString());
			throw new ServiceException(e.toString());
		}

	}

	public void marcarLeido(int idDialogo, int idMatricula, int estado) throws ServiceException {
		try {
			dialogoDAO.leerPlactica(idDialogo, idMatricula, estado);
		} catch (DAOException e) {
			log.debug(e.toString());
			throw new ServiceException(e.toString());
		}

	}
	


}
