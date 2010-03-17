package edu.tecsup.lms.service;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tecsup.lms.dao.FichaHerramientaDAO;
import edu.tecsup.lms.dao.FichaRecursoDAO;
import edu.tecsup.lms.dao.FichaUnidadDAO;
import edu.tecsup.lms.dao.FichaUnidadRecursoDAO;
import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.ficha.FichaHerramienta;
import edu.tecsup.lms.modelo.ficha.FichaRecurso;
import edu.tecsup.lms.modelo.ficha.FichaUnidad;
import edu.tecsup.lms.modelo.ficha.FichaUnidadRecurso;

public class FichaConfiguracionService {

	Log log = LogFactory.getLog(getClass());
	
	private FichaHerramientaDAO fichaHerramientaDAO;
	private FichaRecursoDAO fichaRecursoDAO;
	private FichaUnidadRecursoDAO fichaUnidadRecursoDAO;
	private FichaUnidadDAO fichaUnidadDAO;
		
	public FichaHerramientaDAO getFichaHerramientaDAO() {
		return fichaHerramientaDAO;
	}

	public void setFichaHerramientaDAO(FichaHerramientaDAO fichaHerramientaDAO) {
		this.fichaHerramientaDAO = fichaHerramientaDAO;
	}

	public FichaRecursoDAO getFichaRecursoDAO() {
		return fichaRecursoDAO;
	}

	public void setFichaRecursoDAO(FichaRecursoDAO fichaRecursoDAO) {
		this.fichaRecursoDAO = fichaRecursoDAO;
	}

	public FichaUnidadDAO getFichaUnidadDAO() {
		return fichaUnidadDAO;
	}

	public void setFichaUnidadDAO(FichaUnidadDAO fichaUnidadDAO) {
		this.fichaUnidadDAO = fichaUnidadDAO;
	}

	public FichaUnidadRecursoDAO getFichaUnidadRecursoDAO() {
		return fichaUnidadRecursoDAO;
	}

	public void setFichaUnidadRecursoDAO(FichaUnidadRecursoDAO fichaUnidadRecursoDAO) {
		this.fichaUnidadRecursoDAO = fichaUnidadRecursoDAO;
	}

	public void actualizarFichaRecurso(FichaRecurso fr) throws ServiceException {
		try {
			fichaRecursoDAO.modificar(fr);
		} catch (DAOException e) {
			log.debug(e.getMessage());
		}
	}
	
	public Collection<FichaRecurso> obtenerFichaRecurso(String idFicha) throws ServiceException {
		Collection<FichaRecurso> c = null;
		try {
			c = (ArrayList<FichaRecurso>)fichaRecursoDAO.obtener(idFicha);
		} catch (DAOException e) {
			log.debug(e.getMessage());
		}
		return c;		
	}
	
	public Collection<FichaHerramienta> obtenerFichaHerramienta(String idFicha) throws ServiceException {
		Collection<FichaHerramienta> c = null;
		try {
			c = (ArrayList<FichaHerramienta>)fichaHerramientaDAO.obtener(idFicha);
		} catch (DAOException e) {
			log.debug(e.getMessage());
		}
		return c;	
	}
	
	public void actualizarFichaHerramienta(FichaHerramienta fh) throws ServiceException {
		try {
			fichaHerramientaDAO.modificar(fh);
		} catch (DAOException e) {
			log.debug(e.getMessage());
		}
	}
	
	public Collection<FichaUnidad> obtenerFichaUnidad(String idFicha, String idSilabo) throws ServiceException {
		Collection<FichaUnidad> c = null;
		try {
			c = (ArrayList<FichaUnidad>)fichaUnidadDAO.obtener(idFicha, idSilabo);
		} catch (DAOException e) {
			log.debug(e.getMessage());
		}
		return c;	
	}
	
	public void actualizarFichaUnidad(FichaUnidad vo) throws ServiceException {
		try {
			fichaUnidadDAO.modificar(vo);
		} catch (DAOException e) {
			log.debug(e.getMessage());
		}
	}
	
	public Collection<FichaUnidadRecurso> obtenerFichaUnidadRecurso(String idFicha, String idUnidad) throws ServiceException {
		Collection<FichaUnidadRecurso> c = null;
		try {
			c = (ArrayList<FichaUnidadRecurso>)fichaUnidadRecursoDAO.obtener(idFicha, idUnidad);
		} catch (DAOException e) {
			log.debug(e.getMessage());
		}
		return c;	
	}
	
	public FichaUnidadRecurso obtenerFichaUnidadRecurso(String idFicha, String idUnidad, String idRecurso) throws ServiceException {
		FichaUnidadRecurso vo = null;
		try {
			vo = fichaUnidadRecursoDAO.obtener(idFicha, idUnidad, idRecurso);
		} catch (DAOException e) {
			log.debug(e.getMessage());
		}
		return vo;	
	}
	
	public void actualizarFichaUnidadRecurso(FichaUnidadRecurso vo) throws ServiceException {
		try {
			fichaUnidadRecursoDAO.modificar(vo);
		} catch (DAOException e) {
			log.debug(e.getMessage());
		}
	}
	
	public FichaUnidad obtenerFichaUnidad(String idFicha, String idUnidad, String idRecurso) throws ServiceException {
		FichaUnidad vo = null;
		try {
			vo = fichaUnidadDAO.obtener(idFicha, idUnidad, idRecurso);
		} catch (DAOException e) {
			log.debug(e.getMessage());
		}
		return vo;	
	}	
	
	public void actualizarFichaRecurso(FichaUnidadRecurso fichaUnidadRecurso, String tipo, int estado) throws ServiceException {
		try {
			fichaUnidadRecursoDAO.modificarRecursoFicha(fichaUnidadRecurso,tipo,estado);
		} catch (DAOException e) {
			log.debug(e.getMessage());
		}
	}	
}
