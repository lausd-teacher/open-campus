package edu.opencampus.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.modelo.portal.Servicio;

public class PortalDAO extends BaseDAO {

	protected DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void guardarIdioma(int idUsuario, String idioma) throws DAOException {
		log.info("guardarIdioma(int idUsuario, String idioma)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "UPDATE CV_USUARIO SET IDIOMA=? WHERE IDUSUARIO=?";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setString(1, idioma);
			stmt.setInt(2, idUsuario);
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

	public Collection<Servicio> obtenerServiciosGestionar() throws DAOException {
		log.info("obtenerServiciosGestionar()");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Collection<Servicio> servicios = new ArrayList<Servicio>();
		Servicio servicio = null;
		try {
			String query = "SELECT idservicio,nombre,columna,posicion,permiso_minimizar,permiso_eliminar,estado " +
					"FROM cv_servicio_maestro ORDER BY columna,posicion";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			result = stmt.executeQuery();
			while (result.next()) {
				servicio = new Servicio();
				servicio.setId(result.getString("idservicio"));
				servicio.setNombre(result.getString("nombre"));
				servicio.setColumna(result.getInt("columna"));
				servicio.setPosicion(result.getInt("posicion"));
				servicio.setPermisoMinimizar(result.getInt("permiso_minimizar"));
				servicio.setPermisoEliminar(result.getInt("permiso_eliminar"));
				servicio.setEstado(result.getInt("estado"));
				servicios.add(servicio);
			}
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		} catch (Exception e) {
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return servicios;
	}
	
	public Collection<Servicio> obtenerServiciosUsuarioConfig(Integer usuario) throws DAOException {
		log.info("obtenerServiciosUsuarioConfig("+usuario+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Collection<Servicio> servicios = new ArrayList<Servicio>();
		Servicio servicio = null;
		try {
			
			String query = "SELECT cvus.idservicio,cvus.columna,cvus.posicion,cvus.estado,cvus.visible," +
					"cvsm.nombre,cvsm.permiso_minimizar,cvsm.permiso_eliminar " +
					"FROM cv_servicio_maestro cvsm, cv_servicio_usuario cvus " +
					"WHERE cvsm.idservicio=cvus.idservicio AND cvsm.estado='1' AND cvus.idusuario=? " +
					"ORDER BY cvus.columna,cvus.posicion";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, usuario);
			result = stmt.executeQuery();
			while (result.next()) {
				servicio = new Servicio();
				servicio.setId(result.getString("idservicio"));
				servicio.setNombre(result.getString("nombre"));
				servicio.setColumna(result.getInt("columna"));
				servicio.setPosicion(result.getInt("posicion"));
				servicio.setEstado(result.getInt("estado"));
				servicio.setVisible(result.getInt("visible"));
				servicio.setPermisoMinimizar(result.getInt("permiso_minimizar"));
				servicio.setPermisoEliminar(result.getInt("permiso_eliminar"));
				
				servicios.add(servicio);
			}
		} catch (SQLException e) {
			log.info(e.toString());
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.info(e.toString());
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return servicios;
	}
	
	public boolean ocultarServicio(Integer idusuario, String servicio, Integer estado) throws DAOException {
		log.info("ocultarServicio("+idusuario+", "+servicio+","+estado+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String query = "select permiso_minimizar  from cv_servicio_maestro where idservicio=?";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setString(1, servicio);
			result = stmt.executeQuery();
			
			if(result.next()){
				query = "update cv_servicio_usuario set visible=? " +
						"where idusuario=? and idservicio=?";
				stmt = cons.prepareStatement(query);
				stmt.setInt(1, estado);
				stmt.setInt(2, idusuario);
				stmt.setString(3, servicio);
				if(stmt.executeUpdate() == 1){
					return true;
				}
			}		
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return false;
	}
	
	public boolean eliminarServicio(Integer idusuario, String servicio, Integer estado) throws DAOException {
		log.info("eliminarServicio("+idusuario+", "+servicio+","+estado+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String query = "select permiso_eliminar  from cv_servicio_maestro where idservicio=?";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setString(1, servicio);
			result = stmt.executeQuery();
			
			if(result.next()){
				query = "update cv_servicio_usuario set estado=? " +
						"where idusuario=? and idservicio=?";
				stmt = cons.prepareStatement(query);
				stmt.setInt(1, estado);
				stmt.setInt(2, idusuario);
				stmt.setString(3, servicio);
				if(stmt.executeUpdate() == 1){
					return true;
				}
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return false;
	}
	
	public void grabarPortal(Integer idusuario, Collection<Servicio> servicios) throws DAOException {
		log.info("grabarPortal("+idusuario+", "+servicios+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "update cv_servicio_usuario set columna=?, posicion=? where idusuario=? and idservicio=?";
			cons = dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt = cons.prepareStatement(query);
			stmt.setInt(3, idusuario);
			
			for (Servicio servicio : servicios) {
				stmt.setInt(1, servicio.getColumna());
				stmt.setInt(2, servicio.getPosicion());
				stmt.setString(4, servicio.getId());
				if(stmt.executeUpdate() != 1){
					throw new DAOException("No se pudo actualizar: "+servicio.getId()+"-"+servicio.getPosicion());
				}
			}
			cons.commit();
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			try {
				cons.rollback();
			} catch (SQLException e) {}
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	public Collection<Servicio> obtenerServiciosUsuario(Integer idusuario) throws DAOException {
		log.info("obtenerServiciosUsuario("+idusuario+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet result = null;
		Collection<Servicio> servicios = new ArrayList<Servicio>();
		Servicio servicio = null;
		try {
			String query = "SELECT cvus.idservicio,cvus.columna,cvus.posicion,cvus.estado,cvus.visible," +
					"cvsm.nombre,cvsm.permiso_minimizar,cvsm.permiso_eliminar " +
					"FROM cv_servicio_maestro cvsm, cv_servicio_usuario cvus " +
					"WHERE cvsm.idservicio=cvus.idservicio AND cvsm.estado='1' AND cvus.estado='1' " +
					"AND cvus.idusuario=? " +
					"ORDER BY cvus.columna,cvus.posicion";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, idusuario);
			result = stmt.executeQuery();
			
			if(!result.isBeforeFirst()){
				query = "INSERT INTO cv_servicio_usuario(idusuario,idservicio,columna,posicion,visible,estado) " +
						"SELECT ?,idservicio,columna,posicion,1,estado " +
						"FROM cv_servicio_maestro;";
				stmt2 = cons.prepareStatement(query);
				stmt2.setInt(1, idusuario);
				stmt2.executeUpdate();
				result = stmt.executeQuery();
			}
			
			while (result.next()) {
				servicio = new Servicio();
				servicio.setId(result.getString("idservicio"));
				servicio.setNombre(result.getString("nombre"));
				servicio.setColumna(result.getInt("columna"));
				servicio.setPosicion(result.getInt("posicion"));
				servicio.setEstado(result.getInt("estado"));
				servicio.setVisible(result.getInt("visible"));
				servicio.setPermisoMinimizar(result.getInt("permiso_minimizar"));
				servicio.setPermisoEliminar(result.getInt("permiso_eliminar"));
				
				servicios.add(servicio);
			}
			
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		} catch (Exception e) {
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt2);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return servicios;
	}
	

	
	//*****************************************************************************************//
	
	public void eliminarConfiguracion(int usuario) throws DAOException {
		log.info("eliminarConfiguracion(int usuario)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "DELETE FROM CV_SERVICIO_USUARIO WHERE IDUSUARIO=?";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, usuario);
			stmt.executeUpdate();
			
			query = "insert into cv_servicio_usuario(idusuario,idservicio,columna,posicion,visible,estado) " +
					"select ?,idservicio,columna,posicion,usuario_visible,usuario_estado " +
					"from cv_servicio_maestro";
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, usuario);
			stmt.executeUpdate();
		} catch (SQLException e) {
			log.info(e.toString());
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.info(e.toString());
			throw new DAOException(e.toString());
		} finally {
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	

	//////Renombrar a grabarPortalMaestro()
	public void guardarPortalGestionar(Collection<String[]> array) throws DAOException {
		log.info("guardarPortalGestionar(Collection<String[]> array)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "update cv_servicio_maestro set columna=? , posicion=? where idservicio=?";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			int posicion = 0;
			for (String[] item : array) {
				stmt.setInt(1, posicion++);
				for (int u = 0; u < item.length; u++) {
					stmt.setInt(2, u);
					stmt.setString(3, item[u]);
					stmt.executeUpdate();
				}
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

	public int guardarPortalEliminadoGestionar(String servicio, String estado)
			throws DAOException {
		return guardarPropiedadGestionar("usuario_estado", servicio, estado);
	}

	public int guardarPortalVisibleGestionar(String servicio, String estado)
			throws DAOException {
		return guardarPropiedadGestionar("usuario_visible", servicio, estado);
	}

	public int guardarPortalUsuarioMinimizarGestionar(String servicio,
			String estado) throws DAOException {
		return guardarPropiedadGestionar("usuario_minimizado", servicio, estado);
	}

	public int guardarPortalUsuarioEliminarGestionar(String servicio,
			String estado) throws DAOException {
		return guardarPropiedadGestionar("usuario_eliminado", servicio, estado);
	}

	public int guardarPortalIngresarGestionar(String servicio, String estado)
			throws DAOException {
		return guardarPropiedadGestionar("ver_ingresar", servicio, estado);
	}

	public int guardarPortalDescripcionGestionar(String servicio, String estado)
			throws DAOException {
		return guardarPropiedadGestionar("ver_descripcion", servicio, estado);
	}

	public int guardarPortalComentarioGestionar(String servicio, String estado)
			throws DAOException {
		return guardarPropiedadGestionar("comentario", servicio, estado);
	}

	public int guardarPortalEstadoGestionar(String servicio, String estado)
			throws DAOException {
		return guardarPropiedadGestionar("estado", servicio, estado);
	}

	/*
	 * OJOOOOOO: CUANDO QUITES LA OPCION DE QUE EL USUARIO PUEDA MINIMIZAR DEBES PONER A TODOS QUE TENGAN EL SERVICIO
	 * OCULTO (visible=0) A VISIBLE (visible=1) (en cv_servicio_usuario), por que sino cuando quites la opcion los 
	 * usuarios ya no podran mostrar nuevamente
	 */
	private int guardarPropiedadGestionar(String tipo, String servicio,
			String estado) throws DAOException {
		log.info("guardarPropiedadGestionar(String servicio, String estado)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "update cv_servicio_maestro set " + tipo + "=? where idservicio=? ";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setString(1, estado);
			stmt.setString(2, servicio);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeStatement(stmt);
			closeConnection(cons);
		}
	}


}