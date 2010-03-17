package edu.tecsup.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.modelo.ficha.FichaHerramienta;
import edu.tecsup.lms.util.Formato;

public class FichaHerramientaDAO extends BaseDAO {
	
	Log log = LogFactory.getLog(getClass());
	protected DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Collection<FichaHerramienta> obtener(String idFicha) throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		log.info("obtener(String idFicha)");
		Collection<FichaHerramienta> col = new ArrayList<FichaHerramienta>();
		FichaHerramienta fh = new FichaHerramienta();
		try {
			String query = "SELECT IDHERRAMIENTA, IDFICHA, ESTADO, DESHABILITADO_DOC, DESHABILITADO_ESTU, USUARIO_MOD, FECHA_MOD "
					+ "FROM CV_FICHA_HERRAMIENTA WHERE IDFICHA=?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, idFicha);
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				fh = new FichaHerramienta();
				fh.setIdHerramienta(result.getString("IDHERRAMIENTA"));
				fh.setIdFicha(result.getString("IDFICHA"));
				fh.setEstado(result.getString("ESTADO"));
				fh.setDeshabilitado_doc(result.getString("DESHABILITADO_DOC"));
				fh.setDeshabilitado_estu(result.getString("DESHABILITADO_ESTU"));
				fh.setUsuarioModificacion(result.getString("USUARIO_MOD"));
				fh.setFechaModificacion(Formato.getDateDeBaseDatos(result.getString("FECHA_MOD")));
				col.add(fh);
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

	public void modificar(FichaHerramienta fr) throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		log.info("modificar(FichaHerramienta fr)");
		try {
			String query = "UPDATE CV_FICHA_HERRAMIENTA SET ESTADO=?, DESHABILITADO_DOC=?, DESHABILITADO_ESTU=?, USUARIO_MOD=?, FECHA_MOD=sysdate "
					+ " WHERE IDFICHA=? AND IDHERRAMIENTA=?";		
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, fr.getEstado());
			stmt.setString(2, fr.getDeshabilitado_doc());
			stmt.setString(3, fr.getDeshabilitado_estu());
			stmt.setString(4, fr.getUsuarioModificacion());		
			stmt.setString(5, fr.getIdFicha());			
			stmt.setString(6, fr.getIdHerramienta());
			if (1 != stmt.executeUpdate()) {
				log.error("Error en modificar(FichaHerramienta fr)");
//				System.out.println("Error en modificar(FichaHerramienta fr)");
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

}
