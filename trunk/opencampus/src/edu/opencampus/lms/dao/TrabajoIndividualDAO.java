package edu.opencampus.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.modelo.TrabajoIndividual;
import edu.opencampus.lms.modelo.Matricula;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.modelo.tindividual.TrabajoIndividualInteraccion;
import edu.opencampus.lms.modelo.tindividual.TrabajoIndividualMatricula;
import edu.opencampus.lms.modelo.usuario.Persona;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;

public class TrabajoIndividualDAO extends BaseDAO {
	
	protected DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public TrabajoIndividual obtenerTrabajoIndividual(TrabajoIndividual ti)
			throws DAOException {
		log.info(ti.getIdUnidad()+"-"+ti.getIdFicha());
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		TrabajoIndividualInteraccion interaccion = null;
		Matricula usuarioEmisor = null;
		try {

			String query = "SELECT T.IDUNIDAD,T.FECHA_ACTIVACION,T.FECHA_ENTREGA,M.IDUSUARIO,  " +
					"T.DESCRIPCION,T.ARCHIVO_NOMBRE,T.ARCHIVO_TAMANIO,T.IDMATRICULA_ENVIO,P.APEPATERNO,P.APEMATERNO,P.NOMUNO,P.NOMDOS,D.NOMBRE  " +
					"FROM cv_trabajo_individual T, cv_matricula M, cv_usuario U, cv_persona P, cv_unidad D  " +
					"WHERE T.IDUNIDAD=D.IDUNIDAD AND T.IDMATRICULA_ENVIO=M.IDMATRICULA AND U.IDUSUARIO=M.IDUSUARIO AND U.IDUSUARIO=P.IDPERSONA " +
					"AND T.IDFICHA=? AND T.IDUNIDAD=?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, ti.getIdFicha());
			stmt.setInt(2, ti.getIdUnidad());
			result =  stmt.executeQuery();
			if (result.next()) {
				ti.setIdTrabajo(result.getInt("IDUNIDAD"));
				ti.setFechaActivacion(Formato.getDateDeBaseDatos(result.getString("FECHA_ACTIVACION")));
				ti.setFechaEntrega(Formato.getDateDeBaseDatos(result.getString("FECHA_ENTREGA")));
				ti.setNombreUnidad(result.getString("NOMBRE"));

				interaccion = new TrabajoIndividualInteraccion();
				interaccion.setDescripcion(result.getString("DESCRIPCION"));
				interaccion.setArchivoNombre(result.getString("ARCHIVO_NOMBRE"));
				interaccion.setArchivoTamanio(result.getString("ARCHIVO_TAMANIO"));

				usuarioEmisor = new Matricula();

				usuarioEmisor.setIdMatricula(result.getInt("IDMATRICULA_ENVIO"));
				Usuario usuario = new Usuario();
				usuario.setId(result.getInt("IDUSUARIO"));
				
				Persona persona = new Persona();
				persona.setApepaterno(result.getString("APEPATERNO"));
				persona.setApematerno(result.getString("APEMATERNO"));
				persona.setNomuno(result.getString("NOMUNO"));
				persona.setNomdos(result.getString("NOMDOS"));
				
				usuario.setPersona(persona);
				
				usuarioEmisor.setUsuario(usuario);

				interaccion.setUsuarioEmisor(usuarioEmisor);

				ti.setInteraccion(interaccion);

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
		return ti;

	}

	public Collection<TrabajoIndividualMatricula> listarTrabajoPorMatricula(
			TrabajoIndividual trabajo) throws DAOException {
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Collection<TrabajoIndividualMatricula> matriculas = new ArrayList<TrabajoIndividualMatricula>();
		TrabajoIndividualMatricula matricula = null;
		Matricula usuarioReceptor = null;

		try {

			String query = "SELECT T.IDMATRICULA,T.ESTADO,T.NOTA,T.EXPIRADO,M.IDUSUARIO,P.APEPATERNO,P.APEMATERNO,P.NOMUNO,P.NOMDOS  " +
					"FROM cv_trabajo_individual_nota T, cv_matricula M, cv_usuario U, cv_persona P  " +
					"WHERE T.IDMATRICULA=M.IDMATRICULA AND U.IDUSUARIO=M.IDUSUARIO AND U.IDUSUARIO=P.IDPERSONA AND T.IDUNIDAD=? AND T.IDFICHA=? AND M.IDROL=? AND M.ELIMINADO='0' AND M.ESTADO='1' " +
					"ORDER BY P.APEPATERNO,P.APEMATERNO,P.NOMUNO";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, trabajo.getIdUnidad());
			stmt.setInt(2, trabajo.getIdFicha());
			stmt.setInt(3, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE);
			result =  stmt.executeQuery();

			while (result.next()) {

				matricula = new TrabajoIndividualMatricula();
				matricula.setIdTrabajo(trabajo.getIdUnidad());
				matricula.setEstado(result.getInt("ESTADO"));
				matricula.setExpirado(result.getInt("EXPIRADO"));

				if (result.getString("NOTA") == null)
					matricula.setNota(null);
				else
					matricula.setNota(result.getInt("NOTA"));

				usuarioReceptor = new Matricula();
				usuarioReceptor.setIdMatricula(result.getInt("IDMATRICULA"));
				
				Usuario usuario = new Usuario();
				usuario.setId(result.getInt("IDUSUARIO"));
				
				Persona persona = new Persona();
				persona.setApepaterno(result.getString("APEPATERNO"));
				persona.setApematerno(result.getString("APEMATERNO"));
				persona.setNomuno(result.getString("NOMUNO"));
				persona.setNomdos(result.getString("NOMDOS"));
				
				usuario.setPersona(persona);
				usuarioReceptor.setUsuario(usuario);
				matricula.setUsuarioReceptor(usuarioReceptor);

				matriculas.add(matricula);
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
		return matriculas;

	}

	public void publicarTrabajo(TrabajoIndividual tindividual)
			throws DAOException {
		log.info(tindividual.getIdUnidad()+"-"+tindividual.getIdFicha());
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			// *** cv_trabajo_individual
			String query = "INSERT INTO cv_trabajo_individual (IDUNIDAD, IDFICHA, IDMATRICULA_ENVIO, DESCRIPCION, FECHA_ACTIVACION, FECHA_ENTREGA, ARCHIVO_NOMBRE, ARCHIVO_TAMANIO, ESTADO) " +
					"VALUES (?,?,?,?,?,?,?,?,?)";
			cons = dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, tindividual.getIdUnidad());
			stmt.setInt(2, tindividual.getIdFicha());
			stmt.setInt(3, tindividual.getInteraccion().getUsuarioEmisor().getIdMatricula());
			stmt.setString(4, tindividual.getInteraccion().getDescripcion());
			stmt.setString(5, Formato.calendarToTimestamp(tindividual.getFechaActivacion()));
			stmt.setString(6, Formato.calendarToTimestamp(tindividual.getFechaEntrega()));
			stmt.setString(7, tindividual.getInteraccion().getArchivoNombre());
			stmt.setString(8, tindividual.getInteraccion().getArchivoTamanio());
			stmt.setInt(9, Constante.ESTADO_ACTIVO);

			if (1 != stmt.executeUpdate()) {
				log.error("Error en publicarTrabajo(TrabajoIndividual tindividual) - cv_trabajo_individual");
				throw new DAOException("No culmino");
			}

			// *** TRABAJO_INDIVIDUAL_NOTA
			query = "INSERT INTO cv_trabajo_individual_nota (IDUNIDAD,IDFICHA,IDMATRICULA,MODIFICADO_POR,MODIFICADO_EN,ESTADO,EXPIRADO)  " +
					"SELECT ?,?,IDMATRICULA,?,NOW(),?,? FROM cv_matricula WHERE IDFICHA=? AND IDROL IN (?,?) AND ELIMINADO='0' AND ESTADO='1'";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, tindividual.getIdUnidad());
			stmt.setInt(2, tindividual.getIdFicha());
			stmt.setString(3, tindividual.getUsuarioModificacion());
			stmt.setInt(4, Constante.FLAG_INICIA_PENDIENTE_ESTUDIANTE);
			stmt.setInt(5, Constante.FLAG_TRABAJO_PENDIENTE);
			stmt.setInt(6, tindividual.getIdFicha());
			stmt.setInt(7, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE);
			stmt.setInt(8, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE_MONITOR);

			int afec = stmt.executeUpdate();
			log.info(afec+" filas insertadas en cv_trabajo_individual_nota");
			
			// *** TRABAJO_INDIVIDUAL_INTER
			query = "INSERT INTO cv_trabajo_individual_inter (IDUNIDAD,IDFICHA,IDMATRICULA,INTERACCION,DESCRIPCION,CREADO_POR,CREADO_EN,MODIFICADO_POR,MODIFICADO_EN,ARCHIVO_NOMBRE,ARCHIVO_TAMANIO)  " +
					"SELECT ?,?,IDMATRICULA,'1' INTERACCION,?,?,NOW(),?,NOW(),?,?  " +
					"FROM cv_matricula WHERE IDFICHA=? AND IDROL IN (?,?) AND ELIMINADO='0' AND ESTADO='1'";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, tindividual.getIdUnidad());
			stmt.setInt(2, tindividual.getIdFicha());
			stmt.setString(3, tindividual.getInteraccion().getDescripcion());
			stmt.setString(4, tindividual.getUsuarioCreacion());
			stmt.setString(5, tindividual.getUsuarioModificacion());
			stmt.setString(6, tindividual.getInteraccion().getArchivoNombre());
			stmt.setString(7, tindividual.getInteraccion().getArchivoTamanio());
			stmt.setInt(8, tindividual.getIdFicha());
			stmt.setInt(9, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE);
			stmt.setInt(10, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE_MONITOR);

			afec = stmt.executeUpdate();
			log.info(afec+" filas insertadas en cv_trabajo_individual_inter");

			cons.commit();
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			try {
				cons.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

	public void modificarTrabajo(TrabajoIndividual tindividual)
			throws DAOException {
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			// *** cv_trabajo_individual
			String query = "";
			cons = dataSource.getConnection();
			cons.setAutoCommit(false);
			if (tindividual.getInteraccion().getArchivoNombre() != null) {
				query = "UPDATE cv_trabajo_individual SET FECHA_ACTIVACION=?, FECHA_ENTREGA=?, DESCRIPCION=?,  " +
						"IDMATRICULA_ENVIO=?, ARCHIVO_NOMBRE=?, ARCHIVO_TAMANIO=? " +
						"WHERE IDUNIDAD=? AND IDFICHA=?";
				stmt =  cons.prepareStatement(query);
				stmt.setString(1, Formato.calendarToTimestamp(tindividual.getFechaActivacion()));
				stmt.setString(2, Formato.calendarToTimestamp(tindividual.getFechaEntrega()));
				stmt.setString(3, tindividual.getInteraccion().getDescripcion());
				stmt.setInt(4, tindividual.getInteraccion().getUsuarioEmisor().getIdMatricula());
				stmt.setString(5, tindividual.getInteraccion().getArchivoNombre());
				stmt.setString(6, tindividual.getInteraccion().getArchivoTamanio());
				stmt.setInt(7, tindividual.getIdTrabajo());
				stmt.setInt(8, tindividual.getIdFicha());
			} else {
				query = "UPDATE cv_trabajo_individual SET FECHA_ACTIVACION=?, FECHA_ENTREGA=?, DESCRIPCION=?,  " +
						"IDMATRICULA_ENVIO=?  " +
						"WHERE IDUNIDAD=? AND IDFICHA=?";
				stmt =  cons.prepareStatement(query);
				stmt.setString(1, Formato.calendarToTimestamp(tindividual.getFechaActivacion()));
				stmt.setString(2, Formato.calendarToTimestamp(tindividual.getFechaEntrega()));
				stmt.setString(3, tindividual.getInteraccion().getDescripcion());
				stmt.setInt(4, tindividual.getInteraccion().getUsuarioEmisor().getIdMatricula());
				stmt.setInt(5, tindividual.getIdTrabajo());
				stmt.setInt(6, tindividual.getIdFicha());
			}
			if (1 != stmt.executeUpdate()) {
				log.error("Error modificarTrabajo(TrabajoIndividual tindividual) - cv_trabajo_individual");
				throw new DAOException("No culmino");
			}

			// *** TRABAJO_INDIVIDUAL_INTER
			if (tindividual.getInteraccion().getArchivoNombre() != null) {
				query = "UPDATE cv_trabajo_individual_inter SET DESCRIPCION=?, MODIFICADO_POR=?,MODIFICADO_EN=NOW(), ARCHIVO_NOMBRE=?, ARCHIVO_TAMANIO=?  " +
						"WHERE IDUNIDAD=? AND IDFICHA=? AND INTERACCION='1'";
				stmt =  cons.prepareStatement(query);
				stmt.setString(1, tindividual.getInteraccion().getDescripcion());
				stmt.setString(2, tindividual.getUsuarioModificacion());
				stmt.setString(3, tindividual.getInteraccion().getArchivoNombre());
				stmt.setString(4, tindividual.getInteraccion().getArchivoTamanio());
				stmt.setInt(5, tindividual.getIdTrabajo());
				stmt.setInt(6, tindividual.getIdFicha());
			} else {
				query = "UPDATE cv_trabajo_individual_inter SET DESCRIPCION=?, MODIFICADO_POR=?,MODIFICADO_EN=NOW()  " +
						"WHERE IDUNIDAD=? AND IDFICHA=? AND INTERACCION='1'";
				stmt =  cons.prepareStatement(query);
				stmt.setString(1, tindividual.getInteraccion().getDescripcion());
				stmt.setString(2, tindividual.getUsuarioModificacion());
				stmt.setInt(3, tindividual.getIdTrabajo());
				stmt.setInt(4, tindividual.getIdFicha());
			}

			if (0 == stmt.executeUpdate()) {
				log.error("Error en modificarTrabajo(TrabajoIndividual tindividual) - TRABAJO_INDIVIDUAL_NOTA");
				throw new DAOException("No culmino");
			}

			cons.commit();
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			try {
				cons.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

	public void eliminarTrabajo(TrabajoIndividual tindividual)
			throws DAOException {
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			// *** cv_trabajo_individual
			String query = "";
			cons = dataSource.getConnection();
			cons.setAutoCommit(false);

			query = "UPDATE cv_trabajo_individual SET ARCHIVO_NOMBRE=?, ARCHIVO_TAMANIO=? " +
					"WHERE IDUNIDAD=? AND IDFICHA=?";
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, null);
			stmt.setString(2, null);
			stmt.setInt(3, tindividual.getIdTrabajo());
			stmt.setInt(4, tindividual.getIdFicha());

			if (1 != stmt.executeUpdate()) {
				log.error("Error modificarTrabajo(TrabajoIndividual tindividual) - cv_trabajo_individual");
				throw new DAOException("No culmino");
			}

			// *** TRABAJO_INDIVIDUAL_INTER
			query = "UPDATE cv_trabajo_individual_inter SET MODIFICADO_POR=?,MODIFICADO_EN=NOW(), ARCHIVO_NOMBRE=?, ARCHIVO_TAMANIO=? "
					+ "WHERE IDUNIDAD=? AND IDFICHA=? AND INTERACCION='1'";
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, tindividual.getUsuarioModificacion());
			stmt.setString(2, null);
			stmt.setString(3, null);
			stmt.setInt(4, tindividual.getIdTrabajo());
			stmt.setInt(5, tindividual.getIdFicha());

			if (0 == stmt.executeUpdate()) {
				log.error("Error en modificarTrabajo(TrabajoIndividual tindividual) - TRABAJO_INDIVIDUAL_NOTA");
				throw new DAOException("No culmino");
			}

			cons.commit();
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			try {
				cons.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

	public void calificarTrabajo(TrabajoIndividualMatricula tMatricula)
			throws DAOException {
		log.info(tMatricula.getIdTrabajo()+"-"+tMatricula.getIdFicha());
		Connection cons = null;
		PreparedStatement stmt = null;
		try {

			String query = "UPDATE cv_trabajo_individual_nota SET NOTA=?, MODIFICADO_POR=?, MODIFICADO_EN=now() "
					+ "WHERE IDUNIDAD=? AND IDFICHA=? AND IDMATRICULA=?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			if (tMatricula.getNota() == null)
				stmt.setString(1, null);
			else
				stmt.setInt(1, tMatricula.getNota());
			stmt.setString(2, tMatricula.getUsuarioModificacion());
			stmt.setInt(3, tMatricula.getIdTrabajo());
			stmt.setInt(4, tMatricula.getIdFicha());
			stmt.setInt(5, tMatricula.getUsuarioReceptor().getIdMatricula());

			if (1 != stmt.executeUpdate()) {
				log.error("Error calificarTrabajo(TrabajoIndividualMatricula tMatricula)");
				throw new DAOException("No culmino");
			}

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

	public TrabajoIndividualMatricula obtenerInteracciones(
			TrabajoIndividualMatricula matricula) throws DAOException {
		log.info(matricula.getIdTrabajo()+"-"+matricula.getIdFicha());
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Collection<TrabajoIndividualInteraccion> interacciones = new ArrayList<TrabajoIndividualInteraccion>();
		TrabajoIndividualInteraccion interaccion = null;
		Matricula emisor = null;

		try {

			String query = "SELECT T.IDUNIDAD,T.IDMATRICULA,T.NOTA,T.ESTADO,T.EXPIRADO,P.APEPATERNO,P.APEMATERNO,P.NOMUNO,P.NOMDOS " +
					"FROM cv_trabajo_individual_nota T, cv_matricula M, cv_usuario U, cv_persona P " +
					"WHERE T.IDMATRICULA=M.IDMATRICULA AND U.IDUSUARIO=M.IDUSUARIO AND U.IDUSUARIO=P.IDPERSONA " +
					"AND T.IDUNIDAD=? AND T.IDFICHA=? AND T.IDMATRICULA=?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, matricula.getIdTrabajo());
			stmt.setInt(2, matricula.getIdFicha());
			stmt.setInt(3, matricula.getUsuarioReceptor().getIdMatricula());
			result =  stmt.executeQuery();

			if (result.next()) {

				if (result.getString("NOTA") == null)
					matricula.setNota(null);
				else
					matricula.setNota(result.getInt("NOTA"));

				matricula.setEstado(result.getInt("ESTADO"));
				matricula.setExpirado(result.getInt("EXPIRADO"));
				
				Usuario usuario = new Usuario();

				Persona persona = new Persona();
				persona.setApepaterno(result.getString("APEPATERNO"));
				persona.setApematerno(result.getString("APEMATERNO"));
				persona.setNomdos(result.getString("NOMDOS"));
				persona.setNomuno(result.getString("NOMUNO"));
				
				usuario.setPersona(persona);
				
				Matricula receptor = matricula.getUsuarioReceptor();
				receptor.setUsuario(usuario);
				matricula.setUsuarioReceptor(receptor);

				// Crear Colección de interacciones
				query = "SELECT M.IDMATRICULA,T.INTERACCION,T.DESCRIPCION,T.CREADO_POR,T.CREADO_EN,T.MODIFICADO_POR,T.MODIFICADO_EN,  " +
						"T.ARCHIVO_NOMBRE,T.ARCHIVO_TAMANIO,P.APEPATERNO,P.APEMATERNO,P.NOMUNO,P.NOMDOS  " +
						"FROM cv_trabajo_individual_inter T, cv_matricula M, cv_usuario U, cv_persona P  " +
						"WHERE U.IDUSUARIO=T.CREADO_POR AND U.IDUSUARIO=P.IDPERSONA AND M.IDUSUARIO=U.IDUSUARIO " +
						"AND T.IDUNIDAD=? AND T.IDFICHA=? AND T.IDMATRICULA=? ORDER BY T.INTERACCION";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, matricula.getIdTrabajo());
				stmt.setInt(2, matricula.getIdFicha());
				stmt.setInt(3, matricula.getUsuarioReceptor().getIdMatricula());
				result =  stmt.executeQuery();

				while (result.next()) {

					interaccion = new TrabajoIndividualInteraccion();
					interaccion.setIdMensaje(result.getInt("INTERACCION"));
					interaccion.setDescripcion(result.getString("DESCRIPCION"));
					interaccion.setUsuarioCreacion(result.getString("CREADO_POR"));
					interaccion.setUsuarioModificacion(result.getString("MODIFICADO_POR"));
					interaccion.setFechaCreacion(Formato.getDateDeBaseDatos(result.getString("CREADO_EN")));
					interaccion.setFechaModificacion(Formato.getDateDeBaseDatos(result.getString("MODIFICADO_EN")));
					interaccion.setArchivoNombre(result.getString("ARCHIVO_NOMBRE"));
					interaccion.setArchivoTamanio(result.getString("ARCHIVO_TAMANIO"));

					emisor = new Matricula();
					emisor.setIdMatricula(result.getInt("IDMATRICULA"));
					
					Usuario usuario2 = new Usuario();

					Persona persona2 = new Persona();
					persona2.setApepaterno(result.getString("APEPATERNO"));
					persona2.setApematerno(result.getString("APEMATERNO"));
					persona2.setNomdos(result.getString("NOMDOS"));
					persona2.setNomuno(result.getString("NOMUNO"));
					
					usuario2.setPersona(persona2);
					
					emisor.setUsuario(usuario2);

					interaccion.setUsuarioEmisor(emisor);

					interacciones.add(interaccion);
				}

				matricula.setInteracciones(interacciones);

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
		return matricula;

	}

	public TrabajoIndividualMatricula obtenerMensajeEstadoYExpiro(
			TrabajoIndividualMatricula matricula) throws DAOException {
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {

			String query = "SELECT ESTADO,EXPIRADO,NOTA FROM cv_trabajo_individual_nota  " +
					"WHERE IDUNIDAD=? AND IDFICHA=? AND IDMATRICULA=?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, matricula.getIdTrabajo());
			stmt.setInt(2, matricula.getIdFicha());
			stmt.setInt(3, matricula.getUsuarioReceptor().getIdMatricula());
			result =  stmt.executeQuery();

			if (result.next()) {
				if (result.getString("NOTA") == null)
					matricula.setEstado(result.getInt("ESTADO"));
				else
					matricula.setEstado(0);
				matricula.setExpirado(result.getInt("EXPIRADO"));
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
		return matricula;

	}

	public void nuevoMensajeDeDocente(TrabajoIndividualMatricula matricula)
			throws DAOException {
		Connection cons = null;
		PreparedStatement stmt = null;
		TrabajoIndividualInteraccion interaccion = (TrabajoIndividualInteraccion) matricula
				.getInteracciones().iterator().next();

		try {
			// *** cv_trabajo_individual_inter
			String query = "INSERT INTO cv_trabajo_individual_inter (IDUNIDAD, IDFICHA, IDMATRICULA, INTERACCION, DESCRIPCION,  " +
					"CREADO_POR, CREADO_EN, MODIFICADO_POR, MODIFICADO_EN, ARCHIVO_NOMBRE, ARCHIVO_TAMANIO)   " +
					"VALUES (?, ?, ? , ?, ?, ?, NOW(), ?, NOW(), ?, ?)";

			cons = dataSource.getConnection();
			cons.setAutoCommit(false);

			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, matricula.getIdTrabajo());
			stmt.setInt(2, matricula.getIdFicha());
			stmt.setInt(3, matricula.getUsuarioReceptor().getIdMatricula());
			stmt.setInt(4, interaccion.getIdMensaje() + 1);
			stmt.setString(5, interaccion.getDescripcion());
			stmt.setString(6, interaccion.getUsuarioCreacion());
			stmt.setString(7, interaccion.getUsuarioModificacion());
			stmt.setString(8, interaccion.getArchivoNombre());
			stmt.setString(9, interaccion.getArchivoTamanio());

			if (1 != stmt.executeUpdate()) {
				log.error("Error nuevoMensajeDeDocente(TrabajoIndividual tindividual) - cv_trabajo_individual_inter");
				throw new DAOException("No culmino");
			}

			// *** TRABAJO_INDIVIDUAL_NOTA
			query = "UPDATE cv_trabajo_individual_nota SET ESTADO=? WHERE IDUNIDAD=? AND IDFICHA=? AND IDMATRICULA=?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, Constante.FLAG_PENDIENTE_ESTUDIANTE);
			stmt.setInt(2, matricula.getIdTrabajo());
			stmt.setInt(3, matricula.getIdFicha());
			stmt.setInt(4, matricula.getUsuarioReceptor().getIdMatricula());

			if (1 != stmt.executeUpdate()) {
				log.error("Error en nuevoMensajeDeDocente(TrabajoIndividual tindividual) - TRABAJO_INDIVIDUAL_NOTA");
				throw new DAOException("No culmino");
			}

			cons.commit();
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			try {
				cons.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeStatement(stmt);
			closeConnection(cons);
		}

	}

	public void nuevoMensajeDeEstudiante(TrabajoIndividualMatricula matricula)
			throws DAOException {
		Connection cons = null;
		PreparedStatement stmt = null;
		TrabajoIndividualInteraccion interaccion = (TrabajoIndividualInteraccion) matricula.getInteracciones().iterator().next();

		try {
			cons = dataSource.getConnection();
			cons.setAutoCommit(false);

			// *** cv_trabajo_individual_inter

			String query = "INSERT INTO cv_trabajo_individual_inter (IDUNIDAD, IDFICHA, IDMATRICULA, INTERACCION, DESCRIPCION,  " +
					"CREADO_POR, CREADO_EN, MODIFICADO_POR, MODIFICADO_EN, ARCHIVO_NOMBRE, ARCHIVO_TAMANIO)   " +
					"VALUES (?, ?, ? , ?, ?, ?, NOW(), ?, NOW(), ?, ?)";

			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, matricula.getIdTrabajo());
			stmt.setInt(2, matricula.getIdFicha());
			stmt.setInt(3, matricula.getUsuarioReceptor().getIdMatricula());
			stmt.setInt(4, interaccion.getIdMensaje() + 1);
			stmt.setString(5, interaccion.getDescripcion());
			stmt.setString(6, interaccion.getUsuarioCreacion());
			stmt.setString(7, interaccion.getUsuarioModificacion());
			stmt.setString(8, interaccion.getArchivoNombre());
			stmt.setString(9, interaccion.getArchivoTamanio());

			if (1 != stmt.executeUpdate()) {
				log.error("Error nuevoMensajeDeDocente(TrabajoIndividual tindividual) - cv_trabajo_individual_inter");
				throw new DAOException("No culmino");
			}

			// *** TRABAJO_INDIVIDUAL_NOTA
			if (!"".equals(interaccion.getArchivoNombre())
					&& matricula.getExpirado() != 0) {
				query = "UPDATE cv_trabajo_individual_nota SET ESTADO=?, EXPIRADO=? WHERE IDUNIDAD=? AND IDFICHA=? AND IDMATRICULA=?";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, Constante.FLAG_PENDIENTE_DOCENTE);
				stmt.setInt(2, matricula.getExpirado());
				stmt.setInt(3, matricula.getIdTrabajo());
				stmt.setInt(4, matricula.getIdFicha());
				stmt.setInt(5, matricula.getUsuarioReceptor().getIdMatricula());
			} else {
				query = "UPDATE cv_trabajo_individual_nota SET ESTADO=? WHERE IDUNIDAD=? AND IDFICHA=? AND IDMATRICULA=?";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, Constante.FLAG_PENDIENTE_DOCENTE);
				stmt.setInt(2, matricula.getIdTrabajo());
				stmt.setInt(3, matricula.getIdFicha());
				stmt.setInt(4, matricula.getUsuarioReceptor().getIdMatricula());
			}

			if (1 != stmt.executeUpdate()) {
				log.error("Error en nuevoMensajeDeDocente(TrabajoIndividual tindividual) - TRABAJO_INDIVIDUAL_NOTA");
				throw new DAOException("No culmino");
			}

			cons.commit();
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			try {
				cons.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeStatement(stmt);
			closeConnection(cons);
		}

	}

	public void modificarMensaje(TrabajoIndividualMatricula matricula)
			throws DAOException {
		Connection cons = null;
		PreparedStatement stmt = null;
		TrabajoIndividualInteraccion interaccion = (TrabajoIndividualInteraccion) matricula
				.getInteracciones().iterator().next();

		try {
			cons = dataSource.getConnection();

			if (!"".equals(interaccion.getArchivoNombre())) {
				String query = "UPDATE cv_trabajo_individual_inter "
						+ "SET DESCRIPCION=?, MODIFICADO_POR=?, MODIFICADO_EN=NOW(), ARCHIVO_NOMBRE=?, ARCHIVO_TAMANIO=? "
						+ "WHERE IDUNIDAD=? AND IDFICHA=? AND IDMATRICULA=? AND INTERACCION=?";

				stmt =  cons.prepareStatement(query);
				stmt.setString(1, interaccion.getDescripcion());
				stmt.setString(2, interaccion.getUsuarioModificacion());
				stmt.setString(3, interaccion.getArchivoNombre());
				stmt.setString(4, interaccion.getArchivoTamanio());
				stmt.setInt(5, matricula.getIdTrabajo());
				stmt.setInt(6, matricula.getIdFicha());
				stmt.setInt(7, matricula.getUsuarioReceptor().getIdMatricula());
				stmt.setInt(8, interaccion.getIdMensaje());
			} else {
				String query = "UPDATE cv_trabajo_individual_inter "
						+ "SET DESCRIPCION=?, MODIFICADO_POR=?, MODIFICADO_EN=NOW() "
						+ "WHERE IDUNIDAD=? AND IDFICHA=? AND IDMATRICULA=? AND INTERACCION=?";

				stmt =  cons.prepareStatement(query);
				stmt.setString(1, interaccion.getDescripcion());
				stmt.setString(2, interaccion.getUsuarioModificacion());
				stmt.setInt(3, matricula.getIdTrabajo());
				stmt.setInt(4, matricula.getIdFicha());
				stmt.setInt(5, matricula.getUsuarioReceptor().getIdMatricula());
				stmt.setInt(6, interaccion.getIdMensaje());
			}

			if (1 != stmt.executeUpdate()) {
				log.error("Error modificarMensajeDeDocente(TrabajoIndividual tindividual)");
				throw new DAOException("No culmino");
			}
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