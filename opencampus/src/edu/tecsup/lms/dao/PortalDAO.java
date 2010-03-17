package edu.tecsup.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.modelo.portal.Servicio;

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

	public Collection<Servicio> obtenerServicioUsuario(int usuario)
			throws DAOException {
		log.info("obtenerServicioUsuario(int idUsuario)");
		Connection cons = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;
		ResultSet result = null;
		Collection<Servicio> col_servicios = new ArrayList<Servicio>();
		Servicio ser = null;
		try {
			String query = "select cvus.idservicio,cvus.columna,cvus.visible,cvsm.nombre,cvsm.imagen," +
					"cvsm.ver_descripcion,cvsm.ver_ingresar,cvsm.usuario_eliminado,cvsm.usuario_minimizado, cvsm.comentario " +
					"from cv_servicio_maestro cvsm, cv_servicio_usuario cvus " +
					"where cvsm.idservicio=cvus.idservicio and cvus.estado='1' and cvsm.estado='1' " +
					"and cvus.idusuario=? order by cvus.columna,cvus.posicion";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, usuario);
			result = stmt.executeQuery();
			
			if(!result.isBeforeFirst()){
				query = "insert into cv_servicio_usuario(idusuario,idservicio,columna,posicion,visible,estado) " +
						"select ?,idservicio,columna,posicion,usuario_visible,usuario_estado " +
						"from cv_servicio_maestro";
				stmt1 = (PreparedStatement) cons.prepareStatement(query);
				stmt1.setInt(1, usuario);
				stmt1.executeUpdate();
				result = stmt.executeQuery();
			}
			
			while (result.next()) {
				log.info("Servicio "+result.getString("nombre")+" cargado en el portal para "+usuario);
				ser = new Servicio();
				ser.setId(result.getString("idservicio"));
				ser.setColumna(result.getInt("columna"));
				ser.setVisible(result.getInt("visible"));
				ser.setImagen(result.getString("imagen"));
				ser.setNombre(result.getString("nombre"));
				ser.setVerIngreso(result.getInt("ver_ingresar"));
				ser.setVerDescripcion(result.getInt("ver_descripcion"));
				ser.setUsuarioEliminar(result.getInt("usuario_eliminado"));
				ser.setUsuarioMinimizar(result.getInt("usuario_minimizado"));
				ser.setComentario(result.getString("comentario"));
				col_servicios.add(ser);
			}
			
		
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		} catch (Exception e) {
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt1);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return col_servicios;
	}
	
	public void guardarPortal(int usuario, Collection<String[]> array)
			throws DAOException {
		log.info("guardarPortal(String usuario, Collection<String[]> array)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "update cv_servicio_usuario set columna=?, posicion=? where idusuario=? and idservicio=?";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setInt(3, usuario);
			int posicion = 0;
			for (String[] item : array) {
				stmt.setInt(1, posicion++);
				for (int u = 0; u < item.length; u++) {
					stmt.setInt(2, u);
					stmt.setString(4, item[u]);
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
	
	public int guardarPortalVisible(int usuario, String servicio,
			String estado) throws DAOException {
		log.info("guardarPortalVisible(String usuario, String servicio,String estado)");
		Connection cons = null;
		PreparedStatement stmt = null;
		int r = 0;
		try {
			String query = "update cv_servicio_usuario set visible=? " +
					"where idusuario=? and idservicio=? and '1'=(" +
					"select cvs.usuario_minimizado " +
					"from cv_servicio_maestro cvs where cvs.idservicio=?)";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setString(1, estado);
			stmt.setInt(2, usuario);
			stmt.setString(3, servicio);
			stmt.setString(4, servicio);
			r = stmt.executeUpdate();
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
		return r;
	}
	
	public int guardarPortalEliminado(int usuario, String servicio,
			String estado) throws DAOException {
		log.info("guardarPortalEliminado(String usuario, String servicio,String estado)");
		Connection cons = null;
		PreparedStatement stmt = null;
		int r = 0;
		try {
			String query = "update cv_servicio_usuario set estado=? " +
					"where idusuario=? and idservicio=? and '1'=(" +
					"select usuario_eliminado from cv_servicio_maestro " +
					"where idservicio=?)";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setString(1, estado);
			stmt.setInt(2, usuario);
			stmt.setString(3, servicio);
			stmt.setString(4, servicio);
			r = stmt.executeUpdate();
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
		return r;
	}
	
	public Collection<Servicio> obtenerServicios(int usuario) throws DAOException {
		log.info("obtenerServicios(String idUsuario)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Collection<Servicio> col_servicios = new ArrayList<Servicio>();
		Servicio ser = null;
		try {
			String query = "select cvus.idservicio,cvsm.nombre,cvsm.imagen,cvsm.comentario,cvus.estado,cvsm.usuario_eliminado " +
					"from cv_servicio_maestro cvsm,cv_servicio_usuario cvus " +
					"where cvsm.estado=1 and cvus.idservicio=cvsm.idservicio and cvus.idusuario=? " +
					"order by cvsm.orden";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, usuario);
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				ser = new Servicio();
				ser.setComentario(result.getString("comentario"));
				ser.setId(result.getString("idservicio"));
				ser.setEstado(result.getInt("estado"));
				ser.setUsuarioEliminar(result.getInt("usuario_eliminado"));
				ser.setNombre(result.getString("nombre"));
				ser.setImagen(result.getString("imagen"));
				col_servicios.add(ser);
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
		return col_servicios;
	}
	
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
	
	public Collection<Servicio> obtenerServicioGestionar() throws DAOException {
		log.info("obtenerServicioGestionar()");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Collection<Servicio> col_servicios = new ArrayList<Servicio>();
		Servicio ser = null;
		try {
			String query = "select idservicio,columna,usuario_estado,imagen,usuario_visible,usuario_minimizado," +
					"comentario,nombre,imagen,ver_ingresar,ver_descripcion,usuario_eliminado,estado " +
					"from cv_servicio_maestro order by columna,posicion";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				ser = new Servicio();
				ser.setImagen(result.getString("imagen"));
				ser.setId(result.getString("idservicio"));
				ser.setNombre(result.getString("nombre"));
				ser.setColumna(result.getInt("columna"));
				ser.setUsuarioEstado(result.getInt("usuario_estado"));
				ser.setVisible(result.getInt("usuario_visible"));
				ser.setUsuarioMinimizar(result.getInt("usuario_minimizado"));
				ser.setUsuarioEliminar(result.getInt("usuario_eliminado"));
				ser.setVerIngreso(result.getInt("ver_ingresar"));
				ser.setVerDescripcion(result.getInt("ver_descripcion"));
				ser.setComentario(result.getString("comentario"));
				ser.setEstado(result.getInt("estado"));
				col_servicios.add(ser);
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
		return col_servicios;
	}
	
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