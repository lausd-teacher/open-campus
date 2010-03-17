package edu.tecsup.lms.service;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tecsup.lms.dao.JerarquiaDAO;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.Jerarquia;

public class JerarquiaService {

	Log log = LogFactory.getLog(getClass());

	private JerarquiaDAO jerarquiaDAO;

	public void setJerarquiaDAO(JerarquiaDAO jerarquiaDAO) {
		this.jerarquiaDAO = jerarquiaDAO;
	}
	
	public Collection<Jerarquia> listarJerarquias() throws ServiceException {
		log.info("listarJerarquias()");
		Collection<Jerarquia> jerarquias = new ArrayList<Jerarquia>();
		try {
			
			Collection<Jerarquia> c = jerarquiaDAO.obtenerHijos(0);
			for (Jerarquia patriarca : c) {
				jerarquias.addAll(patriarca.getLista());
			}
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return jerarquias;

	}
	
	public void crear(Jerarquia jerarquia) throws ServiceException {
		log.info("crear(Jerarquia jerarquia)");
		try {
			jerarquiaDAO.crear(jerarquia);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}
	
	public Jerarquia obtener(Integer idJerarquia) throws ServiceException {
		log.info("obtener(Integer idJerarquia)");
		Jerarquia j = null;
		try {
			j = jerarquiaDAO.obtener(idJerarquia);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return j;
	}
	
	public void modificar(Jerarquia jerarquia) throws ServiceException {
		log.info("modificar(Jerarquia jerarquia)");
		try {
			jerarquiaDAO.modificar(jerarquia);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}
	
	public void eliminar(Integer idJerarquia) throws ServiceException {
		log.info("eliminar(Integer idJerarquia)");
		try {
			jerarquiaDAO.eliminar(idJerarquia);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}
	
	/*
	public Collection<Jerarquia> listarJerarquiasPorProfundidad() throws ServiceException {
		log.info("listarJerarquias()");
		Collection<Jerarquia> jerarquias = new ArrayList<Jerarquia>();
		//Si es desde buscar todas las jerarquias de nivel x y obtener sus hijos y luego hacer lista
		//Si es hasta traer obtenerHijos(0) y hacer un getListaPorNivel(nivel)
		try {
			Collection<Jerarquia> c = jerarquiaDAO.obtenerHijos(0);
			for (Jerarquia patriarca : c) {
				jerarquias = patriarca.getLista();
			}
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return jerarquias;

	}*/

}
