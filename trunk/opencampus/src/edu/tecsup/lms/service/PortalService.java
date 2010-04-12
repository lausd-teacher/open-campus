package edu.tecsup.lms.service;

import java.util.Collection;

import edu.tecsup.lms.dao.PortalDAO;
import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.portal.Servicio;

public class PortalService {

	private PortalDAO portalDAO;

	public PortalDAO getPortalDAO() {
		return portalDAO;
	}

	public void setPortalDAO(PortalDAO portalDAO) {
		this.portalDAO = portalDAO;
	}

	public void guardarIdioma(int idUsuario, String idioma) throws ServiceException {
		try {
			portalDAO.guardarIdioma(idUsuario, idioma);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public Collection<Servicio> obtenerServiciosGestionar() throws ServiceException {
		try {
			return portalDAO.obtenerServiciosGestionar();
		} catch (Exception e) {
			throw new ServiceException(e.toString());
		}
	}

	public Collection<Servicio> obtenerServiciosUsuario(int usuario)throws ServiceException {
		try {
			return portalDAO.obtenerServiciosUsuario(usuario);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public Collection<Servicio> obtenerServiciosUsuarioConfig(int usuario)throws ServiceException {
		try {
			return portalDAO.obtenerServiciosUsuarioConfig(usuario);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}
	
	public boolean ocultarServicio(Integer idusuario, String servicio, Integer estado) throws ServiceException {
		try {
			return portalDAO.ocultarServicio(idusuario, servicio, estado);
		} catch (Exception e) {
			throw new ServiceException(e.toString());
		}
	}
	
	public boolean eliminarServicio(Integer idusuario, String servicio, Integer estado) throws ServiceException {
		try {
			return portalDAO.eliminarServicio(idusuario, servicio, estado);
		} catch (Exception e) {
			throw new ServiceException(e.toString());
		}
	}
	
	//*****************************************************************************************//
	public void guardar(int usuario, Collection<String[]> array)
			throws ServiceException {
		try {
			portalDAO.guardarPortal(usuario, array);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public void eliminarConfiguracion(int usuario) throws ServiceException {
		try {
			portalDAO.eliminarConfiguracion(usuario);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public void guardarGestionar(Collection<String[]> array)
			throws ServiceException {
		try {
			portalDAO.guardarPortalGestionar(array);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public int guardarPortalEliminadoGestionar(String servicio, String estado)
			throws ServiceException {
		try {
			return portalDAO.guardarPortalEliminadoGestionar(servicio, estado);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public int guardarPortalVisibleGestionar(String servicio, String estado)
			throws ServiceException {
		try {
			return portalDAO.guardarPortalVisibleGestionar(servicio, estado);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public int guardarPortalUsuarioMinimizarGestionar(String servicio,
			String estado) throws ServiceException {
		try {
			return portalDAO.guardarPortalUsuarioMinimizarGestionar(servicio,
					estado);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public int guardarPortalUsuarioEliminarGestionar(String servicio,
			String estado) throws ServiceException {
		try {
			return portalDAO.guardarPortalUsuarioEliminarGestionar(servicio,
					estado);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public int guardarPortalIngresarGestionar(String servicio, String estado)
			throws ServiceException {
		try {
			return portalDAO.guardarPortalIngresarGestionar(servicio, estado);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public int guardarPortalDescripcionGestionar(String servicio, String estado)
			throws ServiceException {
		try {
			return portalDAO
					.guardarPortalDescripcionGestionar(servicio, estado);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public int guardarPortalComentarioGestionar(String servicio, String estado)
			throws ServiceException {
		try {
			return portalDAO.guardarPortalComentarioGestionar(servicio, estado);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public int guardarPortalEstadoGestionar(String servicio, String estado)
			throws ServiceException {
		try {
			return portalDAO.guardarPortalEstadoGestionar(servicio, estado);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

}
