package edu.opencampus.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.modelo.Ciclo;

public class CicloDAO extends BaseDAO {
		
	protected DataSource dataSource;
	
	public Collection<Ciclo> listar(String tipo) throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		log.info("listar(String tipo)");
		Collection<Ciclo> col = new ArrayList<Ciclo>();
		Ciclo temp = null;
		try {
			String query = "SELECT IDCICLO,NOMBRE,TIPO FROM CV_CICLO WHERE TIPO=?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, tipo);
			result = (ResultSet) stmt.executeQuery();

			while (result.next()) {
				temp = new Ciclo();
				temp.setIdCiclo(result.getString("IDNODOFORMACION"));
				temp.setNombre(result.getString("IDFORMACION"));
				temp.setTipo(result.getString("DESCRIPCION"));
				col.add(temp);
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

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
