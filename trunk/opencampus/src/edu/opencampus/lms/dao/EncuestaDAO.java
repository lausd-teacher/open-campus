package edu.opencampus.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.modelo.Encuesta;
@Deprecated 
public class EncuestaDAO extends BaseDAO{

	protected DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public boolean esParticipante(int idMatricula) throws DAOException {
		log.info("esParticipante(int idMatricula)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String query = "SELECT COUNT(IDMATRICULA) CANTIDAD FROM cv_matricula M, cv_ficha F " +
					"WHERE F.IDFICHA=M.IDFICHA AND M.IDROL=4 AND M.ESTADO=1 AND M.ELIMINADO=0 AND F.CODIGO_FAMILIA='3002'" +
					"AND M.IDMATRICULA=?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idMatricula);
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				int count = result.getInt("CANTIDAD");
				if(count>0){
					return true;
				}
			}

		} catch (SQLException e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return false;

	}
	
	public boolean debeEncuesta(int idMatricula) throws DAOException {
		log.info("debeEncuesta(int idMatricula)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String query = "SELECT IDMATRICULA FROM CV_ENCUESTA WHERE IDMATRICULA=?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idMatricula);
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				return false;
			}

		} catch (SQLException e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return true;

	}
	
	public void insertarEncuesta(Encuesta encuesta) throws DAOException {
		log.info("debeEncuesta(int idMatricula)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "INSERT INTO CV_ENCUESTA (IDMATRICULA,P1_A,P1_B,P2_A,P2_B,P3_A,P3_B,P3_C,P4_A,P5_A,P6_A,P6_B,P6_C,P7_A,FECHA) " +
					"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, encuesta.getIdMatricula());
			stmt.setInt(2, encuesta.getP1A());
			stmt.setInt(3, encuesta.getP1B());
			stmt.setInt(4, encuesta.getP2A());
			stmt.setInt(5, encuesta.getP2B());
			stmt.setInt(6, encuesta.getP3A());
			stmt.setInt(7, encuesta.getP3B());
			stmt.setInt(8, encuesta.getP3C());
			stmt.setInt(9, encuesta.getP4A());
			stmt.setInt(10, encuesta.getP5A());
			stmt.setInt(11, encuesta.getP6A());
			stmt.setInt(12, encuesta.getP6B());
			stmt.setInt(13, encuesta.getP6C());
			stmt.setString(14, encuesta.getP7A());
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en insertarEncuesta(Encuesta encuesta)");
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
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
}
