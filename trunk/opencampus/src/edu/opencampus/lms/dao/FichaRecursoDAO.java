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
import edu.opencampus.lms.modelo.ficha.FichaRecurso;
import edu.opencampus.lms.util.Formato;

public class FichaRecursoDAO extends BaseDAO {
	
	Log log = LogFactory.getLog(getClass());
	protected DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void modificar(FichaRecurso fr) throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		log.info("modificar(FichaRecurso fr)");
		try {
			String query = "UPDATE CV_FICHA_RECURSO SET PESO=?, USUARIO_MOD=?, FECHA_MOD=sysdate "
					+ " WHERE IDFICHA=? AND IDRECURSO=?";		
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, fr.getPeso());
			stmt.setString(2, fr.getUsuarioModificacion());		
			stmt.setString(3, fr.getIdFicha());			
			stmt.setString(4, fr.getIdRecurso());
			if (1 != stmt.executeUpdate()) {
				log.error("Error en modificar(FichaRecurso fr)");
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

	public Collection<FichaRecurso> obtener(String idFicha) throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		log.info("obtener(String idFicha)");
		Collection<FichaRecurso> col = new ArrayList<FichaRecurso>();
		FichaRecurso fr = new FichaRecurso();
		try {
			String query = "SELECT IDFICHA, IDRECURSO, PESO, USUARIO_MOD, FECHA_MOD "
					+ "FROM CV_FICHA_RECURSO WHERE IDFICHA=?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, idFicha);
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				fr = new FichaRecurso();
				fr.setIdFicha(result.getString("IDFICHA"));
				fr.setIdRecurso(result.getString("IDRECURSO"));
				fr.setPeso(result.getString("PESO"));
				fr.setUsuarioModificacion(result.getString("USUARIO_MOD"));
				fr.setFechaModificacion(Formato.getDateDeBaseDatos(result.getString("FECHA_MOD")));
				col.add(fr);
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

}
