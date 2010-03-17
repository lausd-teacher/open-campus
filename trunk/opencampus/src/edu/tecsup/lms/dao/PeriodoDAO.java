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
import edu.tecsup.lms.modelo.Periodo;
import edu.tecsup.lms.util.Formato;

public class PeriodoDAO extends BaseDAO {

	Log log = LogFactory.getLog(getClass());
	protected DataSource dataSource;
	

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Collection<Periodo> listarPeriodos() throws DAOException {
		log.info("listarPeriodos()");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Collection<Periodo> col = new ArrayList<Periodo>();
		Periodo p = null;
		try {
			String query = "select idperiodo, nombre, fecha_inicio, fecha_fin, dias_edicion, dias_revision " +
					"from cv_periodo where estado=1 and personalizado=0 order by  fecha_inicio desc";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			result =  stmt.executeQuery();
			while (result.next()) {
				p = new Periodo();
				p.setIdPeriodo(result.getInt("idperiodo"));
				p.setNombre(result.getString("nombre"));
				p.setFechaInicio(Formato.dateToCalendar(result.getString("fecha_inicio")));
				p.setFechaFin(Formato.dateToCalendar(result.getString("fecha_fin")));
				p.setDiasEdicion(result.getInt("dias_edicion"));
				p.setDiasRevision(result.getInt("dias_revision"));
				col.add(p);
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
	
	public Collection<Periodo> listarVigentes(boolean iniciado) throws DAOException {
		log.info("listarVigentes(boolean iniciado)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Periodo p = null;
		Collection<Periodo> col = new ArrayList<Periodo>();
		try {
			String query = "select idperiodo, nombre, fecha_inicio, fecha_fin, dias_edicion, dias_revision " +
					"from cv_periodo where estado=1 and personalizado=0 "+((iniciado)?"and fecha_inicio-dias_edicion<now()":"")+ "and fecha_fin-dias_revision>now() order by  fecha_inicio desc";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			result =  stmt.executeQuery();
			while (result.next()) {
				
				p = new Periodo();
				p.setIdPeriodo(result.getInt("idperiodo"));
				p.setNombre(result.getString("nombre"));
				p.setFechaInicio(Formato.dateToCalendar(result.getString("fecha_inicio")));
				p.setFechaFin(Formato.dateToCalendar(result.getString("fecha_fin")));
				p.setDiasEdicion(result.getInt("dias_edicion"));
				p.setDiasRevision(result.getInt("dias_revision"));
				col.add(p);
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
		
	public boolean esDuplicado(String nombre) throws DAOException {
		log.info("esDuplicado("+nombre+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		boolean esDuplicado = false;

		try {
			String query = "select idperiodo from cv_periodo where upper(nombre) like ?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, "%" + nombre.toUpperCase() + "%");
			result =  stmt.executeQuery();
			if (result.next()) {
				esDuplicado = true;
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
		return esDuplicado;
	}
	
	public Integer crear(Periodo periodo) throws DAOException {
		log.info("crear(Periodo periodo)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Integer id = null;
		
		try {
			String query = "insert into cv_periodo (nombre, fecha_inicio, fecha_fin, dias_edicion, dias_revision, personalizado, creado_en) " +
					"values(?, ?, ?, ?, ?, ?, now())";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, periodo.getNombre());
			stmt.setString(2, Formato.calendarToTimestamp(periodo.getFechaInicio()));
			stmt.setString(3, Formato.calendarToTimestamp(periodo.getFechaFin()));
			stmt.setInt(4, periodo.getDiasEdicion());
			stmt.setInt(5, periodo.getDiasRevision());
			stmt.setInt(6, periodo.getPersonalizado());

			if (1 != stmt.executeUpdate()) {
				log.error("Error en crear(Periodo periodo)");
				throw new DAOException("No culmino");
			}
			
			result = stmt.getGeneratedKeys();
			result.next();
			id = result.getInt(1);
			
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
		return id;
	}

	public Periodo obtener(Integer idPeriodo) throws DAOException {
		log.info("obtener(Integer idPeriodo)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Periodo p = null;
		try {
			String query = "select idperiodo,nombre, fecha_inicio, fecha_fin,estado,dias_edicion,dias_revision,personalizado " +
					"from cv_periodo where estado=1 and idperiodo=?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idPeriodo);
			result =  stmt.executeQuery();
			if (result.next()) {
				p = new Periodo();
				p.setIdPeriodo(idPeriodo);
				p.setNombre(result.getString("nombre"));
				p.setFechaInicio(Formato.timestampToCalendar(result.getString("fecha_inicio")));
				p.setFechaFin(Formato.timestampToCalendar(result.getString("fecha_fin")));
				p.setEstado(result.getInt("estado"));
				p.setDiasEdicion(result.getInt("dias_edicion"));
				p.setDiasRevision(result.getInt("dias_revision"));
				p.setPersonalizado(result.getInt("personalizado"));
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
		return p;
	}

	public Periodo modificar(Periodo periodo) throws DAOException {
		log.info("modificar(Periodo periodo)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = "update cv_periodo set nombre=?, fecha_inicio=?, fecha_fin=?, dias_edicion=?, dias_revision=? " +
					"where idperiodo=? ";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, periodo.getNombre());
			stmt.setString(2, Formato.calendarToTimestamp(periodo.getFechaInicio()));
			stmt.setString(3, Formato.calendarToTimestamp(periodo.getFechaFin()));
			stmt.setInt(4, periodo.getDiasEdicion());
			stmt.setInt(5, periodo.getDiasRevision());
			stmt.setInt(6, periodo.getIdPeriodo());

			if (1 != stmt.executeUpdate()) {
				log.error("Error en modificar(Periodo periodo)");
				throw new DAOException("No culmino");
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
		return periodo;
	}
	
	public void eliminar(Integer idPeriodo) throws DAOException {
		log.info("eliminar(Integer idPeriodo)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			
			String query = "delete from cv_periodo where idperiodo=?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idPeriodo);
			stmt.executeUpdate();
				
		} catch (SQLException e) {
			try{
				
				String query = "update cv_periodo set estado=0 where idperiodo=?";
				cons = dataSource.getConnection();
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, idPeriodo);
				stmt.executeUpdate();
				
			} catch (SQLException r) {
				log.error(r);
				throw new DAOException(r);
			}
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
/*


	public Collection<Periodo> obtenerUDS() throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		log.info("obtenerTodos()");
		Collection<Periodo> col = new ArrayList<Periodo>();
		Periodo p = null;
		try {
			String query = "select codigo,nombre from general.GEN_PERIODO "
					+ "where estado in(0,1) and tipo='S' order by fecinicio desc";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			result =  stmt.executeQuery();
			while (result.next()) {
				p = new Periodo();
				p.setIdPeriodo(result.getString("CODIGO"));
				p.setNombre(result.getString("NOMBRE"));
				col.add(p);
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

	public Collection<Periodo> obtenerTodo() throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		log.info("obtenerTodo()");
		Collection<Periodo> col = new ArrayList<Periodo>();
		try {
			String query = "SELECT CODIGO,TO_CHAR(FECINICIO,'YYYY')||'-'||NOMBRE AS NOMBRE,SEDE,FECINICIO,FECFIN FROM CV_PERIODO WHERE ESTADO=1 ORDER BY TO_CHAR(FECINICIO,'YYYY') desc, NOMBRE DESC";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			result =  stmt.executeQuery();
			while (result.next()) {
				Periodo p = new Periodo();
				p.setIdPeriodo(result.getString("CODIGO"));
				p.setNombre(result.getString("NOMBRE"));
				p.setSede(Util.getSucursalNombre(result.getString("SEDE")));
				p.setFechaInicio(Formato.getDateDeBaseDatos(result.getString("FECINICIO")));
				p.setFechaFin(Formato.getDateDeBaseDatos(result.getString("FECFIN")));
				p.setFechaInicioToString(Formato.getStringDeDate(p.getFechaInicio()));
				p.setFechaFinToString(Formato.getStringDeDate(p.getFechaFin()));
				col.add(p);
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
	
	public Collection<Periodo> obtenerTodoDeUsuario(String idUsuario) throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		log.info("obtenerTodoDeUsuario()" +idUsuario);
		Collection<Periodo> col = new ArrayList<Periodo>();
		try {
			String query = "SELECT CODIGO,TO_CHAR(FECINICIO,'YYYY')||'-'||NOMBRE AS NOMBRE,SEDE,FECINICIO,FECFIN " +
					"FROM CV_PERIODO " +
					"WHERE ESTADO=1 AND SEDE IN (SELECT DISTINCT SEDE FROM CV_USUARIO_DEPARTAMENTO WHERE TRIM(USUARIO) = ?) " +
					"ORDER BY TO_CHAR(FECINICIO,'YYYY') desc, NOMBRE DESC";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, idUsuario);
			result =  stmt.executeQuery();
			while (result.next()) {
				Periodo p = new Periodo();
				p.setIdPeriodo(result.getString("CODIGO"));
				p.setNombre(result.getString("NOMBRE"));
				p.setSede(Util.getSucursalNombre(result.getString("SEDE")));
				p.setFechaInicio(Formato.getDateDeBaseDatos(result.getString("FECINICIO")));
				p.setFechaFin(Formato.getDateDeBaseDatos(result.getString("FECFIN")));
				p.setFechaInicioToString(Formato.getStringDeDate(p.getFechaInicio()));
				p.setFechaFinToString(Formato.getStringDeDate(p.getFechaFin()));
				col.add(p);
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
	

	
	public Collection<Integer> obtenerFichasPorPeriodo(int idPeriodo) throws DAOException {
		log.info("obtenerFichasPorPeriodo()");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		Collection<Integer> col = new ArrayList<Integer>();
		try {
			String query = "select idficha from cv_ficha where codigo_periodo=? and idetiqueta!=1000 order by idficha";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idPeriodo);
			result =  stmt.executeQuery();
			while (result.next()) {
				col.add(result.getInt("idficha"));
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
*/
}
