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
import edu.opencampus.lms.modelo.ficha.FichaUnidadRecurso;
import edu.opencampus.lms.util.Formato;

public class FichaUnidadRecursoDAO extends BaseDAO {
	
	Log log = LogFactory.getLog(getClass());
	protected DataSource dataSource;
	
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void modificar(FichaUnidadRecurso fichaUnidadRecurso) throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		log.info("modificar(FichaUnidadRecurso fichaUnidadRecurso)");
		try {
			String query = "UPDATE cv_ficha_unidad_RECURSO SET FECHA_MOD=sysdate, USUARIO_MOD=?, PESO=?, CALIFICA=?, DESHABILITADO_DOC=?, DESHABILITADO_ESTU=?, ESTADO=? "
					+ " WHERE IDFICHA=? AND IDUNIDAD=? AND IDRECURSO=? ";
					
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, fichaUnidadRecurso.getUsuarioModificacion());
			stmt.setString(2, fichaUnidadRecurso.getPeso());			
			stmt.setString(3, fichaUnidadRecurso.getCalifica());
			stmt.setString(4, fichaUnidadRecurso.getDeshabilitadoDoc());
			stmt.setString(5, fichaUnidadRecurso.getDeshabilitadoEstu());
			stmt.setString(6, fichaUnidadRecurso.getEstado());
			stmt.setString(7, fichaUnidadRecurso.getIdFicha());
			stmt.setString(8, fichaUnidadRecurso.getIdUnidad());
			stmt.setString(9, fichaUnidadRecurso.getIdRecurso());
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en modificar(FichaUnidadRecurso fichaUnidadRecurso)");
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
	
	public Collection<FichaUnidadRecurso> obtener(String idFicha, String idUnidad) throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		log.info("obtener(String idFicha, String idUnidad)");
		Collection<FichaUnidadRecurso> col = new ArrayList<FichaUnidadRecurso>();
		FichaUnidadRecurso vo = new FichaUnidadRecurso();
		try {
			String query = "SELECT IDFICHA, IDUNIDAD, IDRECURSO, FECHA_MOD, USUARIO_MOD, PESO, CALIFICA, DESHABILITADO_DOC, DESHABILITADO_ESTU, ESTADO "
					+ "FROM cv_ficha_unidad_RECURSO WHERE IDFICHA=? AND IDUNIDAD=?";
			cons = (Connection)dataSource.getConnection();
			
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, idFicha);
			stmt.setString(2, idUnidad);
			
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				vo = new FichaUnidadRecurso();
				vo.setIdFicha(result.getString("IDFICHA"));
				vo.setIdUnidad(result.getString("IDUNIDAD"));
				vo.setIdRecurso(result.getString("IDRECURSO"));
				vo.setFechaModificacion(Formato.getDateDeBaseDatos(result.getString("FECHA_MOD")));
				vo.setUsuarioModificacion(result.getString("USUARIO_MOD"));
				vo.setPeso(result.getString("PESO"));
				vo.setCalifica(result.getString("CALIFICA"));
				vo.setDeshabilitadoDoc(result.getString("DESHABILITADO_DOC"));
				vo.setDeshabilitadoEstu(result.getString("DESHABILITADO_ESTU"));
				vo.setEstado(result.getString("ESTADO"));
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
	
	public FichaUnidadRecurso obtener(String idFicha, String idUnidad, String idRecurso) throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		log.info("obtener(String idFicha, String idUnidad, String idRecurso)");
		FichaUnidadRecurso vo = new FichaUnidadRecurso();
		try {
			String query = "SELECT IDFICHA, IDUNIDAD, IDRECURSO, FECHA_MOD, USUARIO_MOD, PESO, CALIFICA, DESHABILITADO_DOC, DESHABILITADO_ESTU, ESTADO "
					+ "FROM cv_ficha_unidad_RECURSO WHERE IDFICHA=? AND IDUNIDAD=? AND IDRECURSO=?";
			cons = (Connection)dataSource.getConnection();
			
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, idFicha);
			stmt.setString(2, idUnidad);
			stmt.setString(3, idRecurso);
			
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				vo = new FichaUnidadRecurso();
				vo.setIdFicha(result.getString("IDFICHA"));
				vo.setIdUnidad(result.getString("IDUNIDAD"));
				vo.setIdRecurso(result.getString("IDRECURSO"));
				vo.setFechaModificacion(Formato.getDateDeBaseDatos(result.getString("FECHA_MOD")));
				vo.setUsuarioModificacion(result.getString("USUARIO_MOD"));
				vo.setPeso(result.getString("PESO"));
				vo.setCalifica(result.getString("CALIFICA"));
				vo.setDeshabilitadoDoc(result.getString("DESHABILITADO_DOC"));
				vo.setDeshabilitadoEstu(result.getString("DESHABILITADO_ESTU"));
				vo.setEstado(result.getString("ESTADO"));
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
	
	public void modificarRecursoFicha(FichaUnidadRecurso fichaUnidadRecurso, String tipo, int estado) throws DAOException {
		PreparedStatement stmt = null;		
		Connection cons = null;
		
		try {
			String query = "UPDATE cv_ficha_unidad_RECURSO SET FECHA_MOD=sysdate, USUARIO_MOD=? ";
					
			if(tipo.equals("D")){
				query += ", DESHABILITADO_DOC=? ";
			}else if(tipo.equals("E")){
				query += ", DESHABILITADO_ESTU=? ";
			}else if(tipo.equals("C")){
				query += ", CALIFICA=? ";
			}else if(tipo.equals("I")){				
				query += ", ESTADO =? ";
			}
			
			query += " WHERE IDFICHA=? AND IDRECURSO=?";
			
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, fichaUnidadRecurso.getUsuarioModificacion());
			stmt.setInt(2, estado);
			stmt.setString(3, fichaUnidadRecurso.getIdFicha());
			stmt.setString(4, fichaUnidadRecurso.getIdRecurso());
			
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

}
