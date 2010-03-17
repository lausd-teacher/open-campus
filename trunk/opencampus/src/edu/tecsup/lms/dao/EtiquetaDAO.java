package edu.tecsup.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.modelo.ficha.Etiqueta;

public class EtiquetaDAO extends BaseDAO {
	
	protected DataSource dataSource;

	public Collection<Etiqueta> obtenerEtiquetasPersonalizadas()
			throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;

		Collection<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
		Etiqueta etiqueta = null;
		try {
			String query = "select idetiqueta,nombre from cv_etiqueta"
					+ " where eliminar='1' and estado='1' order "
					+ "by idetiqueta";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				etiqueta = new Etiqueta();
				etiqueta.setIdEtiqueta(result.getString("idetiqueta"));
				etiqueta.setNombre(result.getString("nombre"));
				etiquetas.add(etiqueta);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return etiquetas;
	}

	public Collection<Etiqueta> obtenerEtiquetasPredeterminadas()
			throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		Collection<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
		Etiqueta etiqueta = null;
		try {
			String query = "select idetiqueta,nombre from cv_etiqueta"
					+ " where eliminar='0' and estado='1' order "
					+ "by idetiqueta";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				etiqueta = new Etiqueta();
				etiqueta.setIdEtiqueta(result.getString("idetiqueta"));
				etiqueta.setNombre(result.getString("nombre"));
				etiquetas.add(etiqueta);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return etiquetas;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
