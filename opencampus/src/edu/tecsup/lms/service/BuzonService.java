package edu.tecsup.lms.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tecsup.lms.dao.BuzonDAO;
import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.modelo.correo.Adjunto;
import edu.tecsup.lms.modelo.correo.Alerta;
import edu.tecsup.lms.modelo.correo.Carpeta;
import edu.tecsup.lms.modelo.correo.Mensaje;
import edu.tecsup.lms.modelo.correo.UsuarioCorreo;

public class BuzonService {

	Log log = LogFactory.getLog(getClass());

	private BuzonDAO buzonDAO;

	public void setBuzonDAO(BuzonDAO buzonDAO) {
		this.buzonDAO = buzonDAO;
	}

	public boolean banderita(String idUsuario) throws ServiceException {
		boolean banderita = false;
		try {
			banderita = buzonDAO.banderita(idUsuario);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return banderita;
	}

	public String crearCarpeta(Carpeta c) throws ServiceException {
		try {
			c.setIdCarpeta(buzonDAO.crearCarpeta(c));
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return c.getIdCarpeta();
	}

	public Collection<Mensaje> cargarPortada(int idUsuario) throws ServiceException {
		Collection<Mensaje> mensajes = null;
		try {
			mensajes = buzonDAO.cargarPortada(idUsuario);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return mensajes;
	}
	
	public Collection<Mensaje> buscarMesaje(String aBuscar, String idUsuario)
			throws ServiceException {
		Collection<Mensaje> mensajes = new ArrayList<Mensaje>();
		try {
			mensajes = buzonDAO.buscarMensaje(aBuscar, idUsuario);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		return mensajes;
	}

	public void eliminarCarpeta(Carpeta c) throws ServiceException {
		try {
			buzonDAO.eliminarCarpeta(c);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public List<Mensaje> mostrarFavoritos(int idUsuario, int start, int end)
			throws ServiceException {
		List<Mensaje> mensajes = new ArrayList<Mensaje>();
		try {
			mensajes = buzonDAO.mostrarFavoritos(idUsuario, start, end);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return mensajes;
	}

	public String guardarMensaje(Mensaje m) throws ServiceException {
		String id = null;
		try {
			id = buzonDAO.guardarMensaje(m);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return id;
	}

	public int guardarMensajeUsuario(Mensaje m) throws ServiceException {
		int resp = 0;
		try {
			resp = buzonDAO.guardarMensajeUsuario(m);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return resp;
	}
	
	public void enviarAlerta(Alerta a) throws ServiceException {
		log.info("enviarAlerta()");
		try {
			Mensaje m = new Mensaje();
			m.setContenido(a.getContenido());
			m.setTitulo(a.getTitulo());
			m.setUsuario(a.getEmisor());
			m.setEstado(0);
			m.setAdjunto(0);
			
			m.setIdMensaje(buzonDAO.guardarMensaje(m));
			
			m.setUsuario(a.getDestino());
			m.setCarpeta("R");
			m.setTipo("D");
			buzonDAO.guardarMensajeUsuario(m);
			
			m.setUsuario(a.getEmisor());
			m.setCarpeta("E");
			m.setTipo("R");
			buzonDAO.guardarMensajeUsuario(m);
				
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public UsuarioCorreo verificaUsuario(String idUsuario)
			throws ServiceException {
		UsuarioCorreo usuario = new UsuarioCorreo();
		try {
			usuario = buzonDAO.verificaUsuario(idUsuario);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return usuario;
	}

	public Adjunto guardarAdjunto(Adjunto a, Mensaje m) throws ServiceException {
		try {
			a.setIdAdjunto((buzonDAO.guardarAdjunto(a, m)).getIdAdjunto());
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return a;
	}

	public int moverCarpeta(Mensaje m) throws ServiceException {
		int state = 0;
		try {
			state = buzonDAO.moverCarpeta(m);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return state;
	}

	public List<Mensaje> traerMensajes(int idUsuario, String carpeta,
			String carpetaPersonal, int start, int end) throws ServiceException {
		List<Mensaje> mensajes = new ArrayList<Mensaje>();
		try {
			mensajes = buzonDAO.traerMensajes(idUsuario, carpeta,
					carpetaPersonal, start, end);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return mensajes;
	}

	public int totalMensajes(int idUsuario, String carpeta, String idCarpeta)
			throws ServiceException {
		int total = 0;
		try {
			total = buzonDAO.totalMensajes(idUsuario, carpeta, idCarpeta);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return total;
	}

	public Collection<UsuarioCorreo> buscarContactos(String aBuscar)
			throws ServiceException {
		Collection<UsuarioCorreo> usuarios = new ArrayList<UsuarioCorreo>();
		try {
			usuarios = buzonDAO.buscarContactos(aBuscar);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return usuarios;
	}

	public void enviarPapelera(Mensaje m) throws ServiceException {
		try {
			buzonDAO.enviarPapelera(m);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public int favorito(Mensaje m) throws ServiceException {
		int r = 0;
		try {
			r = buzonDAO.favorito(m);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return r;
	}

	public void eliminarForEver(Mensaje m) throws ServiceException {
		try {
			buzonDAO.eliminarForEver(m);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public void cambiarLeido(Mensaje m) throws ServiceException {
		try {
			buzonDAO.cambiarLeido(m);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public int cantidadNoLeidos(int idUsuario) throws ServiceException {
		int cantidad = 0;
		try {
			cantidad = buzonDAO.cantidadNoLeidos(idUsuario);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return cantidad;
	}

	public Collection<Carpeta> traerCarpeta(int idUsuario)
			throws ServiceException {
		Collection<Carpeta> carpetas = new ArrayList<Carpeta>();
		try {
			carpetas = buzonDAO.traerCarpeta(idUsuario);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return carpetas;
	}

	public int cambiarNombreCarpeta(Carpeta c) throws ServiceException {
		int nt = 0;
		try {
			nt = buzonDAO.cambiarNombreCarpeta(c);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return nt;
	}

	public int totalFavoritos(int idUsuario) throws ServiceException {
		int total = 0;
		try {
			total = buzonDAO.totalFavoritos(idUsuario);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return total;
	}

	public void cambiarNoLeido(Mensaje m) throws ServiceException {
		try {
			buzonDAO.cambiarNoLeido(m);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
	}

	public Float verUsoEnDisco() throws ServiceException {
		Float carga = 0f;
		try {
			carga = buzonDAO.verUsoEnDisco();
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return carga;
	}
	
	public int limpiarBuzon(String fecha) throws ServiceException {
		int total = 0;
		try {
			total = buzonDAO.limpiarBuzon(fecha);
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return total;
	}
	
	public Collection<Usuario> listarSoportes() throws ServiceException {
		Collection<Usuario> soportes = null;
		try {
			soportes = buzonDAO.listarSoportes();
		} catch (DAOException e) {
			throw new ServiceException(e.toString());
		}
		return soportes;
	}

}