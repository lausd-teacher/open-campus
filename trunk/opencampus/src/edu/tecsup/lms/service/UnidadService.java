package edu.tecsup.lms.service;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tecsup.lms.dao.JerarquiaDAO;
import edu.tecsup.lms.dao.UnidadDAO;
import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.ficha.Unidad;

public class UnidadService {

	Log log = LogFactory.getLog(getClass());

	private UnidadDAO unidadDAO;
	private JerarquiaDAO jerarquiaDAO;

	public void setUnidadDAO(UnidadDAO unidadDAO) {
		this.unidadDAO = unidadDAO;
	}

	public UnidadDAO getUnidadDAO() {
		return unidadDAO;
	}

	public void setJerarquiaDAO(JerarquiaDAO jerarquiaDAO) {
		this.jerarquiaDAO = jerarquiaDAO;
	}

	public Collection<Unidad> buscarUnidadPorNombre(String nombre,Integer jerarquia, String exacto) throws ServiceException {
		try {
			Collection<Unidad> unidades = new ArrayList<Unidad>();
			Collection<Unidad> unidadesTMP = unidadDAO.buscarPorNombre(nombre, exacto);
			log.info("Total de unidades de nombre indicado: "+unidadesTMP.size());
			for (Unidad unidad : unidadesTMP) {
				unidad.setJerarquia(jerarquiaDAO.obtenerPadre(unidad.getJerarquia().getIdJerarquia()));
				if(unidad.getJerarquia().isMyParent(jerarquia)){
					unidades.add(unidad);
				}
			}
			log.info("Total de unidades de ruta indicada: "+unidades.size());
			return unidades;
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}
	
	public void eliminar(Integer id) throws ServiceException {
		try {
			unidadDAO.eliminar(id);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}
	
	public boolean existeUnidad(String nombre) throws ServiceException {
		try {
			return unidadDAO.existeUnidad(nombre);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}
	
	public Integer crearUnidad(Unidad unidad) throws ServiceException {
		try {
			return unidadDAO.crear(unidad);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}
	
	public void modificarUnidad(Unidad unidad) throws ServiceException {
		try {
			unidadDAO.modificar(unidad);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}
	
	public void cambiarEstado(int id, int estado) throws ServiceException {
		try {
			unidadDAO.cambiarEstado(id, estado);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}
	
	public Collection<Unidad> listarUltimas() throws ServiceException {
		try {
			return unidadDAO.listarUltimas();
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}
	
/*
	

	

	public Collection<Ficha> listarFichas(int idUnidad) throws ServiceException {
		Collection<Ficha> array = null;
		try {
			array = unidadDAO.listarFichas(idUnidad);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return array;
	}
*/
	
}
