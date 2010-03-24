package edu.tecsup.lms.service;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tecsup.lms.dao.JerarquiaDAO;
import edu.tecsup.lms.dao.UsuarioDAO;
import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.Jerarquia;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.modelo.usuario.Rol;
import edu.tecsup.lms.modelo.usuario.Ubigeo;
import edu.tecsup.lms.modelo.usuario.UsuarioFiltro;
import edu.tecsup.lms.util.Constante;
import edu.tecsup.lms.util.Formato;

public class UsuarioService {

	Log log = LogFactory.getLog(getClass());

	private UsuarioDAO usuarioDAO;
	
	private JerarquiaDAO jerarquiaDAO;

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public void setJerarquiaDAO(JerarquiaDAO jerarquiaDAO) {
		this.jerarquiaDAO = jerarquiaDAO;
	}

	public Usuario obtenerUsuario(Integer id) throws ServiceException{
		log.info("obtenerUsuario("+id+")");
		try {
			Usuario user = usuarioDAO.obtenerUsuario(id);
			//Permisos obtener padres e hijos
			Collection<Jerarquia> js = user.getPermisos();
			for (Jerarquia j : js) {
				log.info("\tObteniendo hijos para el permiso "+j.getIdJerarquia());
				j.setHijos(jerarquiaDAO.obtenerHijos(j.getIdJerarquia()));
				log.info("\tObteniendo padre para el permiso "+j.getIdJerarquia());
				j.setPadre(jerarquiaDAO.obtenerPadre(j.getPadre().getIdJerarquia()));
			}
			//Jerarquias asociadas obtener solo padres
			js = user.getJerarquias();
			for (Jerarquia j : js) {
	//			log.info("\tObteniendo hijos para la jerarquia "+j.getIdJerarquia());
	//			j.setHijos(jerarquiaDAO.obtenerHijos(j.getIdJerarquia()));
				log.info("\tObteniendo padre para la jerarquia "+j.getIdJerarquia());
				j.setPadre(jerarquiaDAO.obtenerPadre(j.getPadre().getIdJerarquia()));
			}
			return user;
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public Usuario verificarUsuario(String username) throws ServiceException{
		try {
			return usuarioDAO.verificarUsuario(username);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public Usuario validarUsuario(Usuario user) throws ServiceException {
		log.info("validarUsuario("+user+")");
		try {
			user = usuarioDAO.validar(user);
			if (Constante.SEGURIDAD_USUARIO_ACEPTADA == user.getEstadoSeguridad()) {
				log.info("Usuario valido!");
				user = obtenerUsuario(user.getId());
			}
			//user.setClave("");
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return user;
	}
		
	public String violar(String usuario) throws ServiceException {
		log.info("obtenerUsuario(String usuario)");
		String clave = null;
		try {
			clave = usuarioDAO.violar(usuario);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return clave;
	}
	
	public Usuario obtenerSoloDatos(int id) throws ServiceException {
		log.info("obtenerUsuario(int id)");
		Usuario usuario = null;
		try {
			usuario = usuarioDAO.obtenerSoloDatos(id);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return usuario;
	}
	
	public int cambiarClaveUsuario(Usuario usuario, String clave, String claveNew) throws ServiceException {
		try {
			Usuario user = new Usuario(usuario.getUsuario(),clave);
			user = usuarioDAO.validar(user);
			if (Constante.SEGURIDAD_USUARIO_ACEPTADA == user.getEstadoSeguridad()) {
				usuario.setClave(claveNew);
				return usuarioDAO.cambiarClaveUsuario(usuario);
			}else{
				return Constante.PASSWORD_NO_COINCIDE;
			}
			
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.getMessage());
		}
	}
	
	public int cambiarEmail(int idusuario, String email) throws ServiceException {
		int rpt = 0;
		try{
			rpt = usuarioDAO.cambiarEmail(idusuario, email);
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return rpt;
	}
	
	public Collection<Usuario> verCumpleanieros() throws ServiceException {
		Collection<Usuario> usuarios;
		try {
			usuarios = usuarioDAO.verCumpleanieros();
		} catch (DAOException e) { 
			log.error(e.toString());
			throw new ServiceException(e.toString());
		}
		return usuarios;
	}
	
	public Collection<Rol> listarRoles() throws ServiceException {
		Collection<Rol> roles = null;
		try {
			roles = usuarioDAO.listarRoles();
		} catch (DAOException e) { 
			log.error(e.toString());
			throw new ServiceException(e.toString());
		}
		return roles;
	}
	
	public List<Usuario> buscar(UsuarioFiltro filtro)throws ServiceException {
		log.info("buscarSistema(UsuarioFiltro filtro)");
		List<Usuario> array = null;
		try {
			array = usuarioDAO.buscar(filtro);
		} catch (DAOException e) {
			log.error(e.toString());
			throw new ServiceException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return array;
	}
	
	public List<Usuario> buscarUsuarioPorRol(String nombre, Integer rol)throws ServiceException {
		log.info("buscarUsuarioPorRol(String nombre, Integer rol)");
		List<Usuario> array = null;
		try {
			array = usuarioDAO.buscarUsuarioPorRol(nombre, rol);
		} catch (DAOException e) {
			log.error(e.toString());
			throw new ServiceException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return array;
	}
	
	public int restablecerClave(Usuario user)throws ServiceException {
		log.info("restablecerClave(Usuario user)");
		try {
			return usuarioDAO.cambiarClaveUsuario(user);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.getMessage());
		}
	}
	
	public Collection<Ubigeo> listarPaises()throws ServiceException {
		log.info("listarPaises()");
		try {
			return usuarioDAO.listarPaises();
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public Collection<Ubigeo> listarDepartamentos(String idpais)throws ServiceException {
		log.info("listarDepartamentos(String idpais)");
		try {
			return usuarioDAO.listarDepartamentos(idpais);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public Collection<Ubigeo> listarProvincias(String idDepartamento)throws ServiceException {
		log.info("listarProvincias(String idDepartamento)");
		try {
			return usuarioDAO.listarProvincias(idDepartamento);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public Collection<Ubigeo> listarDistritos(String idProvincia)throws ServiceException {
		log.info("listarDistritos(String idProvincia)");
		try {
			return usuarioDAO.listarDistritos(idProvincia);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public String verificarUsuarioSecuencia(String username)throws ServiceException {
		log.info("verificarUsuarioSecuencia("+username+")");
		try {
			String usernamenew = username;
			Integer n = 1;
			do{
				Usuario usuario = usuarioDAO.verificarUsuario(usernamenew);
				if(usuario!=null){
					log.info("Nombre de usuario ya existe, provando sequencia: "+n);
					usernamenew = username + Formato.completarCeros(n++, 2);
				}else{
					log.info("Nombre de usuario generado: "+username);
					break;
				}
			}while (true);
			
			return usernamenew;
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public Integer crear(Usuario usuario)throws ServiceException {
		log.info("crear(Usuario usuario)");
		try {
			return usuarioDAO.crear(usuario);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public void modificar(Usuario usuario)throws ServiceException {
		log.info("modificar(Usuario usuario)");
		try {
			usuarioDAO.modificar(usuario);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public void cambiarEstado(Integer idusuario, Integer estado)throws ServiceException {
		log.info("modificar(Integer idusuario, Integer estado)");
		try {
			usuarioDAO.cambiarEstado(idusuario, estado);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public void eliminar(Integer idusuario)throws ServiceException {
		log.info("eliminar(Integer idusuario)");
		try {
			usuarioDAO.eliminar(idusuario);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public Collection<Usuario> listarUltimos()throws ServiceException {
		log.info("listarUltimos()");
		try {
			return usuarioDAO.listarUltimos();
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
}
