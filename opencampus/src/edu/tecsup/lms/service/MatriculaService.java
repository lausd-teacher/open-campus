package edu.tecsup.lms.service;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tecsup.lms.dao.MatriculaDAO;
import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.modelo.Matricula;
import edu.tecsup.lms.modelo.aulavirtual.MatriculaRol;

public class MatriculaService {

	Log log = LogFactory.getLog(getClass());

	private MatriculaDAO matriculaDAO;

	public void setMatriculaDAO(MatriculaDAO matriculaDAO) {
		this.matriculaDAO = matriculaDAO;
	}

	public Collection<MatriculaRol> obtenerUsuarioMatriculaGestionar(
			int idFicha) throws ServiceException {
		Collection<MatriculaRol> usuarios = new ArrayList<MatriculaRol>();
		try {
			usuarios = matriculaDAO
					.obtenerUsuariosMatriculadosGestionar(idFicha);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return usuarios;
	}

	public Collection<MatriculaRol> obtenerUsuarioMatricula(int idFicha)
			throws ServiceException {
		Collection<MatriculaRol> usuarios = new ArrayList<MatriculaRol>();
		try {
			usuarios = matriculaDAO.obtenerUsuariosMatriculados(idFicha);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return usuarios;
	}

	public Collection<Matricula> obtenerUsuarioNoMatricula(
			String nombre, int idFicha) throws ServiceException {
		Collection<Matricula> usuarios = new ArrayList<Matricula>();
		try {
			usuarios = matriculaDAO.obtenerUsuarioNoMatriculados(nombre,
					idFicha);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return usuarios;
	}

	public void matricular(String formacion, String rol, String seccion,
			int idFicha, String idUsuario, Usuario usuario)
			throws ServiceException {
		try {
			matriculaDAO.matricularUsuario(formacion, rol, seccion, idFicha,
					idUsuario, usuario);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}

	}

	public void eliminar(String idMatricula, int idFicha, Usuario usuario)
			throws ServiceException {
		try {
			matriculaDAO.eliminarMatricula(idMatricula, idFicha, usuario);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}

	}

	public void modificar(String idMatricula, String estado, int idFicha,
			Usuario usuario) throws ServiceException {
		try {
			matriculaDAO.modificarMatricula(idMatricula, estado, idFicha,
					usuario);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}

	}

	public void principal(String idMatricula, String estado, Usuario usuario)
			throws ServiceException {
		try {
			matriculaDAO.principal(idMatricula, estado, usuario);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}

	}

	public boolean matriculaPerteneceAulaUsuario(String idMatricula, String idUsuario) throws ServiceException {
		try {
			return matriculaDAO.matriculaPerteneceAulaUsuario(idMatricula,idUsuario);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
	
	public boolean esMiMatricula(String idMatricula, String idUsuario) throws ServiceException {
		try {
			return matriculaDAO.esMiMatricula(idMatricula,idUsuario);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
	
	public boolean esMiEmpleado(String codigo, int idMatricula) throws ServiceException {
		try {
			return matriculaDAO.esMiEmpleado(codigo,idMatricula);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
	
	public int obtenerRolDeAula(String idMatricula, String idUsuario) throws ServiceException {
		try {
			return matriculaDAO.obtenerRolDeAula(idMatricula, idUsuario);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

}
