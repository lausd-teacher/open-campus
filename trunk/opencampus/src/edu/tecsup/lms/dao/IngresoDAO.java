package edu.tecsup.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import edu.tecsup.lms.modelo.usuario.Ingreso;
import edu.tecsup.lms.util.Conexion;
import edu.tecsup.lms.util.Formato;

public class IngresoDAO extends BaseDAO {

	protected DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Ingreso inscribirIngreso(Ingreso ingreso) {
		log.info("Inscribir Ingreso :"+ingreso);
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = "insert into cv_ingreso (idusuario,elemento,valor,fecha_ingreso,ip,navegador) values (?,?,?,now(),?,?)";
			cons = (dataSource!=null)?dataSource.getConnection():Conexion.getConnection();
			stmt = cons.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, ingreso.getUsuario().getId());
			stmt.setInt(2, ingreso.getElemento());
			stmt.setString(3, ingreso.getValor());
			stmt.setString(4, ingreso.getUsuario().getIp());
			stmt.setString(5, ingreso.getUsuario().getNavegador());
			stmt.executeUpdate();
			
			result = stmt.getGeneratedKeys();
			result.next();
			ingreso.setIdIngreso(result.getInt(1));
			
			query = "select fecha_ingreso from cv_ingreso where idingreso=?";
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, ingreso.getIdIngreso());
			result = stmt.executeQuery();
			if (result.next()) {
				ingreso.setFechaIngreso(Formato.timestampToCalendar(result.getString(1)));
			}
			
			log.info("Ingreso Inscrito, ID:"+ingreso.getIdIngreso());
		} catch (Exception e) {
			log.error(e);
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return ingreso;
	}

	public void inscribirSalida(Ingreso ingreso) {
		log.info("Inscribir Salida: "+ingreso);
		PreparedStatement stmt = null;
		Connection cons = null;
		try {
			String query = "update cv_ingreso set fecha_salida=now() where idingreso=?";
			cons = (dataSource!=null)?dataSource.getConnection():Conexion.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, ingreso.getIdIngreso());
			stmt.executeUpdate();
			
			log.info("Salida Inscrita, ID:" + ingreso.getIdIngreso());
		} catch (Exception e) {
			log.error(e);
		} finally {
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	private static IngresoDAO instance = null;
	
	public static IngresoDAO getInstance(){
		if(instance == null){
			instance = new IngresoDAO();
		}
		return instance;
	}

}