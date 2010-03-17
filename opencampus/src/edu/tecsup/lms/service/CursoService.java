package edu.tecsup.lms.service;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tecsup.lms.dao.CursoDAO;
import edu.tecsup.lms.dao.JerarquiaDAO;
import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.Curso;

public class CursoService {

	Log log = LogFactory.getLog(getClass());

	private CursoDAO cursoDAO;
	
	private JerarquiaDAO jerarquiaDAO;

	public void setCursoDAO(CursoDAO cursoDAO) {
		this.cursoDAO = cursoDAO;
	}
	
	public void setJerarquiaDAO(JerarquiaDAO jerarquiaDAO) {
		this.jerarquiaDAO = jerarquiaDAO;
	}

	public Collection<Curso> buscar(String nombre, Integer idJerarquia) throws ServiceException {
		try {
			Collection<Curso> cursos = new ArrayList<Curso>();
			Collection<Curso> cursosTMP = cursoDAO.buscar(nombre);
			log.info("Total de cursos de nombre indicado: "+cursosTMP.size());
			for (Curso curso : cursosTMP) {
				curso.setJerarquia(jerarquiaDAO.obtenerPadre(curso.getJerarquia().getIdJerarquia()));
				if(curso.getJerarquia().isMyParent(idJerarquia)){
					cursos.add(curso);
				}
			}
			log.info("Total de unidades de ruta indicada: "+cursos.size());
			return cursos;
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}
	
	public Collection<Curso> buscarSinJerarquia(String nombre) throws ServiceException {
		try {
			return cursoDAO.buscar(nombre);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}

	public Collection<Curso> buscarPorID(int idCurso) throws ServiceException {
		try {
			return cursoDAO.buscarPorID(idCurso);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}
	
	public void crear(Curso curso) throws ServiceException {
		try {
			cursoDAO.crear(curso);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}
	
	public void eliminar(int idCurso) throws ServiceException {
		try {
			cursoDAO.eliminar(idCurso);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}
}
