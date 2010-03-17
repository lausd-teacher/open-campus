package edu.tecsup.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.modelo.Agenda;
import edu.tecsup.lms.util.Formato;

public class AgendaDAO extends BaseDAO{

	Log log = LogFactory.getLog(getClass());

	protected DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public Collection<String> cargarPortada(int usuario) throws DAOException {
		log.info("cargarPortada("+usuario+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;

		Collection<String> fechas = new ArrayList<String>();
		
		String query = "select distinct date_format(fecha_hora,'%d-%m-%Y') fecha_hora from cv_agenda " +
				"where idusuario=? and date_format(fecha_hora,'%Y-%m') = date_format(curdate(),'%Y-%m') " +
				"order by fecha_hora";
		
		try {
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, usuario);
			
			result = stmt.executeQuery();
			
			while (result.next()) {
				fechas.add(result.getString("FECHA_HORA"));
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return fechas;
	}
	
	public Collection<GregorianCalendar> obtenerEventosAnual(int usuario, int year) throws DAOException {
		log.info("obtenerEventosAnual(int usuario, int year)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;

		Collection<GregorianCalendar> fechas = new ArrayList<GregorianCalendar>();
		
		String query = "select fecha_hora from cv_agenda " +
				"where idusuario=? and date_format(fecha_hora,'%Y') = ? order by fecha_hora;";
		
		try {
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, usuario);
			stmt.setInt(2, year);
			
			result = stmt.executeQuery();
			
			while (result.next()) {
				fechas.add(Formato.getDateDeBaseDatos(result.getString("FECHA_HORA")));
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return fechas;
	}
	
	public Collection<Agenda> obtenerEventos(int usuario, String date) throws DAOException {
		log.info("obtenerEventos(int usuario, String date)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;

		Collection<Agenda> eventos = new ArrayList<Agenda>();
		Agenda agenda = null;
			
		String query = "select fecha_hora,sumilla,detalle,notificado,minutoantes from cv_agenda " +
				"where idusuario=? and date_format(fecha_hora,'%d-%m-%Y') = ? " +
				"order by fecha_hora";
		
		try {
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, usuario);
			stmt.setString(2, date);
			
			result = stmt.executeQuery();
			
			while (result.next()) {
				agenda = new Agenda();
				agenda.setFechaHora(Formato.getDateDeBaseDatos(result.getString("FECHA_HORA")));
				agenda.setSumilla(result.getString("SUMILLA"));
				agenda.setDetalle(result.getString("DETALLE"));
				agenda.setNotificado(result.getInt("NOTIFICADO"));
				agenda.setMinutoAntes(result.getInt("MINUTOANTES"));
				
				eventos.add(agenda);
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return eventos;
	}
	
	public void crearEvento(Agenda evento) throws DAOException {
		log.info("crearEvento(Agenda evento)");
		PreparedStatement stmt = null;
		Connection cons = null;

		String query = "insert into cv_agenda (idusuario,fecha_hora,sumilla,detalle,notificado,minutoantes) " +
				"values (?,str_to_date(?,'%d-%m-%Y %H:%i'),?,?,?,?)";
		
		try {
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, evento.getUsuario());
			stmt.setString(2, Formato.setBaseDatosDeDateCompleto(evento.getFechaHora()));
			stmt.setString(3, evento.getSumilla());
			stmt.setString(4, evento.getDetalle());
			stmt.setInt(5, evento.getNotificado());
			stmt.setInt(6, evento.getMinutoAntes());
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en crearEvento(Agenda evento)");
				throw new DAOException("No culmino");
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	public void modificarEvento(String oldDate, Agenda evento) throws DAOException {
		log.info("modificarEvento(String oldDate, Agenda evento)");
		PreparedStatement stmt = null;
		Connection cons = null;

		String query = "update cv_agenda set fecha_hora=str_to_date(?,'%d-%m-%Y %H:%i'), sumilla=?, detalle=?, notificado=?, minutoantes=? " +
				"where idusuario=? and date_format(fecha_hora,'%d-%m-%Y %H:%i')=?";
		
		try {
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setString(1, Formato.setBaseDatosDeDateCompleto(evento.getFechaHora()));
			stmt.setString(2, evento.getSumilla());
			stmt.setString(3, evento.getDetalle());
			stmt.setInt(4, evento.getNotificado());
			stmt.setInt(5, evento.getMinutoAntes());
			stmt.setInt(6, evento.getUsuario());
			stmt.setString(7, oldDate);
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en modificarEvento(String oldDate, Agenda evento)");
				throw new DAOException("No culmino");
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	public void eliminarEvento(int usuario, String date) throws DAOException {
		log.info("eliminarEvento(int Usuario, String date)");
		PreparedStatement stmt = null;
		Connection cons = null;

		String query = "delete from cv_agenda " +
				"where idusuario=? and date_format(fecha_hora,'%d-%m-%Y %H:%i') = ?";
		
		try {
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, usuario);
			stmt.setString(2, date);
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en eliminarEvento(String Usuario, String date)");
				throw new DAOException("No culmino");
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

}