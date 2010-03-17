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

	public Collection<Servicio> obtenerServicioGestionar()
			throws ServiceException {
		Collection<Servicio> services = null;
		try {
			services = portalDAO.obtenerServicioGestionar();
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return services;
	}

	public Collection<Servicio> obtenerServicioUsuario(int usuario)
			throws ServiceException {
		Collection<Servicio> services = null;
		try {
			services = portalDAO.obtenerServicioUsuario(usuario);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return services;
	}

	public Collection<Servicio> obtenerServicios(int usuario)
			throws ServiceException {
		Collection<Servicio> services = null;
		try {
			services = portalDAO.obtenerServicios(usuario);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return services;
	}

	public void guardar(int usuario, Collection<String[]> array)
			throws ServiceException {
		try {
			portalDAO.guardarPortal(usuario, array);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public int guardarPortalVisible(int usuario, String servicio,
			String estado) throws ServiceException {
		try {
			return portalDAO.guardarPortalVisible(usuario, servicio, estado);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public int guardarPortalEliminado(int usuario, String servicio,
			String estado) throws ServiceException {
		try {
			return portalDAO.guardarPortalEliminado(usuario, servicio, estado);
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
