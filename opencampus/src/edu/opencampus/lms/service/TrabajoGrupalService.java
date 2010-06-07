package edu.opencampus.lms.service;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.dao.TrabajoGrupalDAO;
import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.TrabajoGrupal;
import edu.opencampus.lms.modelo.Matricula;
import edu.opencampus.lms.modelo.tgrupal.TrabajoGrupalGrupo;
import edu.opencampus.lms.modelo.tgrupal.TrabajoGrupalIntegrante;

public class TrabajoGrupalService {

	Log log = LogFactory.getLog(getClass());
	
	private TrabajoGrupalDAO trabajoGrupalDAO;

	public void setTrabajoGrupalDAO(TrabajoGrupalDAO trabajoGrupalDAO) {
		this.trabajoGrupalDAO = trabajoGrupalDAO;
	}

	public TrabajoGrupal obtenerTrabajoGrupal(TrabajoGrupal tg) throws ServiceException{
		try {
			tg = trabajoGrupalDAO.obtenerTrabajoGrupal(tg);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return tg;
	}
	
	public TrabajoGrupal obtenerTrabajoGrupalPorID(TrabajoGrupal tg) throws ServiceException{
		try {
			tg = trabajoGrupalDAO.obtenerTrabajoGrupalPorID(tg);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return tg;
	}
	
	public Collection<Matricula> verIntegrantes(TrabajoGrupal idTrabajo, int idMatricula) throws ServiceException{
		Collection<Matricula> integrantes = null;
		try {
			integrantes = trabajoGrupalDAO.verIntegrantes(idTrabajo, idMatricula);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return integrantes;
	}
	
	public TrabajoGrupalGrupo verTrabajo(TrabajoGrupalGrupo grupo) throws ServiceException{
		try {
			grupo = trabajoGrupalDAO.verTrabajo(grupo);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return grupo;
	}
	
	public void subirTrabajo(TrabajoGrupalGrupo grupo) throws ServiceException{
		try {
			trabajoGrupalDAO.subirTrabajo(grupo);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
	
	public void publicarTrabajo(TrabajoGrupal trabajo) throws ServiceException{
		try {
			trabajoGrupalDAO.publicarTrabajo(trabajo);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
	
	public Collection<Matricula> obtenerEstudiantes(int idFicha) throws ServiceException{
		Collection<Matricula> estudiantes = null;
		try {
			estudiantes = trabajoGrupalDAO.obtenerEstudiantes(idFicha);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return estudiantes;
	}
	
	public TrabajoGrupalGrupo crearGrupo(TrabajoGrupalGrupo grupo) throws ServiceException{
		try {
			grupo = trabajoGrupalDAO.crearGrupo(grupo);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return grupo;
	}

	public void asignarGrupo(TrabajoGrupalGrupo grupo, int idMatricula) throws ServiceException{
		try {
			trabajoGrupalDAO.asignarGrupo(grupo, idMatricula);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
	
	public void desasignarGrupo(TrabajoGrupalGrupo grupo, int idMatricula) throws ServiceException{
		try {
			trabajoGrupalDAO.desasignarGrupo(grupo, idMatricula);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

	public void renombrarGrupo(TrabajoGrupalGrupo grupo) throws ServiceException{
		try {
			trabajoGrupalDAO.renombrarGrupo(grupo);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
	
	public void eliminarGrupo(TrabajoGrupalGrupo grupo) throws ServiceException{
		try {
			trabajoGrupalDAO.eliminarGrupo(grupo);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
	
	public void eliminarGrupos(int idTrabajo) throws ServiceException {
		try {
			trabajoGrupalDAO.eliminarGrupos(idTrabajo);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
	
	public Collection<TrabajoGrupalGrupo> obtenerGrupos(int idFicha, int idTrabajo) throws ServiceException{
		Collection<TrabajoGrupalGrupo> grupos = null;
		try {
			grupos = trabajoGrupalDAO.obtenerGrupos(idFicha, idTrabajo);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return grupos;
	}
	
	public Collection<TrabajoGrupalGrupo> obtenerGruposParaEvaluacion(int idTrabajo, int idMatricula) throws ServiceException{
		Collection<TrabajoGrupalGrupo> grupos = null;
		try {
			grupos = trabajoGrupalDAO.obtenerGruposParaEvaluacion(idTrabajo, idMatricula);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return grupos;
	}
	
	public Collection<TrabajoGrupalGrupo> listarGrupos(int idTrabajo) throws ServiceException {
		Collection<TrabajoGrupalGrupo> grupos = null;
		try {
			grupos = trabajoGrupalDAO.listarGrupos(idTrabajo);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return grupos;
	}
	
	public void calificarTrabajo(int idTrabajoGrupal, TrabajoGrupalIntegrante integrante) throws ServiceException{
		try {
			trabajoGrupalDAO.calificarTrabajo(idTrabajoGrupal, integrante);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

	public TrabajoGrupalGrupo verMensajes(TrabajoGrupalGrupo grupo) throws ServiceException{
		try {
			grupo = trabajoGrupalDAO.verMensajes(grupo);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return grupo;
	}

	public int[] obtenerUltimoIdMensaje(TrabajoGrupalGrupo grupo)  throws ServiceException{
		int[] idMensajeEstado = null;
		try {
			idMensajeEstado = trabajoGrupalDAO.obtenerUltimoIdMensaje(grupo);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return idMensajeEstado;
	}

	public void nuevoMensaje(TrabajoGrupalGrupo grupo) throws ServiceException{
		try {
			trabajoGrupalDAO.nuevoMensaje(grupo);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

	public void modificarMensaje(TrabajoGrupalGrupo grupo) throws ServiceException{
		try {
			trabajoGrupalDAO.modificarMensaje(grupo);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

	public int obtenerIdGrupo(TrabajoGrupal idTrabajo, int idMatricula) throws ServiceException{
		int idGrupo;
		try {
			idGrupo = trabajoGrupalDAO.obtenerIdGrupo(idTrabajo, idMatricula);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return idGrupo;
	}
	
	public int obtenerEstadoDebate(int idTrabajo, int idGrupo, int idMatricula) throws ServiceException{
		int debate = 0;
		try {
			debate = trabajoGrupalDAO.obtenerEstadoDebate(idTrabajo, idGrupo, idMatricula);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return debate;
	}

}
