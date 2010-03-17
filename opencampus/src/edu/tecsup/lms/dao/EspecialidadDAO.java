package edu.tecsup.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.modelo.Especialidad;

public class EspecialidadDAO extends BaseDAO {

	protected DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Collection<Especialidad> obtenerTodos() throws DAOException {
		log.info("obtenerFichasPorUsuario(Usuario usuario)");
		Collection<Especialidad> formacion = new ArrayList<Especialidad>();
		Especialidad temp = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = "select distinct fifo.codigo," +
					"pkg_cv_com_producto.fx_cv_com_producto_nombre_id(fifo.codigo) nombre " +
					"from cv_ficha_formacion fifo order by nombre";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				temp = new Especialidad();
				temp.setNombre(result.getString("nombre"));
				temp.setCodigo(result.getString("codigo"));
				formacion.add(temp);
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
		return formacion;
	}

}
