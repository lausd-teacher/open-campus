package edu.opencampus.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.modelo.ficha.FichaUnidad;
import edu.opencampus.lms.util.Formato;

public class FichaUnidadDAO extends BaseDAO {
	
	Log log = LogFactory.getLog(getClass());
	protected DataSource dataSource;
	
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void modificar(FichaUnidad fichaUnidad) throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		log.info("modificar(FichaUnidad fichaUnidad)");
		try {
			String query = "UPDATE cv_ficha_unidad SET ESTADO=?, FECHA_MOD=sysdate, USUARIO_MOD=? "
					+ " WHERE IDFICHA=? AND IDSILABO=? AND IDUNIDAD=? ";
					
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, fichaUnidad.getEstado());
			stmt.setString(2, fichaUnidad.getUsuarioModificacion());			
			stmt.setString(3, fichaUnidad.getIdFicha());
			stmt.setString(4, fichaUnidad.getIdSilabo());
			stmt.setString(5, fichaUnidad.getIdUnidad());
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en modificar(FichaUnidad fichaUnidad)");
				throw new DAOException("No culmino");
			}

		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (DAOException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	public Collection<FichaUnidad> obtener(String idFicha, String idSilabo) throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		log.info("obtener(String idFicha, String idSilabo)");
		Collection<FichaUnidad> col = new ArrayList<FichaUnidad>();
		FichaUnidad vo = new FichaUnidad();
		try {
			String query = "SELECT IDFICHA, IDSILABO, IDUNIDAD, ESTADO, FECHA_MOD, USUARIO_MOD "
					+ "FROM cv_ficha_unidad WHERE IDFICHA=? AND IDSILABO=?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, idFicha);
			stmt.setString(2, idSilabo);
			
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				vo = new FichaUnidad();
				vo.setIdFicha(result.getString("IDFICHA"));
				vo.setIdSilabo(result.getString("IDSILABO"));
				vo.setIdUnidad(result.getString("IDUNIDAD"));
				vo.setEstado(result.getString("ESTADO"));
				vo.setUsuarioModificacion(result.getString("USUARIO_MOD"));
				vo.setFechaModificacion(Formato.getDateDeBaseDatos(result.getString("FECHA_MOD")));
				col.add(vo);
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
		return col;
	}

	public FichaUnidad obtener(String idFicha, String idSilabo, String idUnidad) throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		log.info("obtener(String idFicha="+idFicha+", String idSilabo="+idSilabo+", String idUnidad="+idUnidad+")");
		FichaUnidad vo = new FichaUnidad();
		try {
			String query = "SELECT IDFICHA, IDSILABO, IDUNIDAD, ESTADO, FECHA_MOD, USUARIO_MOD "
					+ "FROM cv_ficha_unidad WHERE IDFICHA=? AND IDSILABO=? AND IDUNIDAD=?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, idFicha);
			stmt.setString(2, idSilabo);
			stmt.setString(3, idUnidad);
			
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				vo = new FichaUnidad();
				vo.setIdFicha(result.getString("IDFICHA"));
				vo.setIdSilabo(result.getString("IDSILABO"));
				vo.setIdUnidad(result.getString("IDUNIDAD"));
				vo.setEstado(result.getString("ESTADO"));
				vo.setUsuarioModificacion(result.getString("USUARIO_MOD"));
				vo.setFechaModificacion(Formato.getDateDeBaseDatos(result.getString("FECHA_MOD")));
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
		return vo;
	}

}
