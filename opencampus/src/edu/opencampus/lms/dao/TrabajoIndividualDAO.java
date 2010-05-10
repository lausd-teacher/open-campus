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
import edu.opencampus.lms.modelo.tindividual.TrabajoIndividualInteraccion;
import edu.opencampus.lms.modelo.tindividual.TrabajoIndividualMatricula;
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
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		TrabajoIndividualInteraccion interaccion = null;
		Matricula usuarioEmisor = null;
		try {

			String query = "SELECT T.IDTRABAJO,T.FECHA_ACTIVACION,T.FECHA_ENTREGA,M.USUARIO, "
					+ "T.DESCRIPCION,T.ARCHIVO_NOMBRE,T.ARCHIVO_TAMANO,T.IDMATRICULA_ENVIO,P.APEPATERNO,P.APEMATERNO,P.NOMUNO,P.NOMDOS,D.NOMBRE_COMPLETO "
					+ "FROM CV_TRABAJO_INDIVIDUAL T, CV_MATRICULA M, SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P, CV_UNIDAD D "
					+ "WHERE T.IDUNIDAD=D.IDUNIDAD AND T.IDMATRICULA_ENVIO=M.IDMATRICULA(+) AND U.USUARIO(+)=M.USUARIO AND U.CODSUJETO=P.CODPERSONA(+) AND T.IDFICHA=? AND T.IDUNIDAD=?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, ti.getIdFicha());
			stmt.setInt(2, ti.getIdUnidad());
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				ti.setIdTrabajo(result.getInt("IDTRABAJO"));
				ti.setFechaActivacion(Formato.getDateDeBaseDatos(result
						.getString("FECHA_ACTIVACION")));
				ti.setFechaEntrega(Formato.getDateDeBaseDatos(result
						.getString("FECHA_ENTREGA")));
				ti.setNombreUnidad(result.getString("NOMBRE_COMPLETO"));

				interaccion = new TrabajoIndividualInteraccion();

				interaccion.setDescripcion(result.getString("DESCRIPCION"));
				interaccion
						.setArchivoNombre(result.getString("ARCHIVO_NOMBRE"));
				interaccion.setArchivoTamanio(result
						.getString("ARCHIVO_TAMANO"));

				usuarioEmisor = new Matricula();

				usuarioEmisor.setIdMatricula("IDMATRICULA_ENVIO");
				usuarioEmisor.setIdUsuario("USUARIO");
				usuarioEmisor.setPaterno("APEPATERNO");
				usuarioEmisor.setMaterno("APEMATERNO");
				usuarioEmisor.setNombre1("NOMUNO");
				usuarioEmisor.setNombre2("NOMDOS");

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
			int idTrabajo) throws DAOException {
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Collection<TrabajoIndividualMatricula> matriculas = new ArrayList<TrabajoIndividualMatricula>();
		TrabajoIndividualMatricula matricula = null;
		Matricula usuarioReceptor = null;

		try {

			String query = "SELECT T.IDMATRICULA,T.ESTADO,T.NOTA,T.EXPIRADO,M.USUARIO,PKG_CV_UTIL.fx_cv_string_number(M.SECCION) SECCION,P.APEPATERNO,P.APEMATERNO,P.NOMUNO,P.NOMDOS "
					+ "FROM CV_TRABAJO_INDIVIDUAL_NOTA T, CV_MATRICULA M, SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P "
					+ "WHERE T.IDMATRICULA=M.IDMATRICULA(+) AND U.USUARIO(+)=M.USUARIO AND U.CODSUJETO=P.CODPERSONA(+) AND IDTRABAJO=? AND M.IDROL=? AND M.ELIMINADO='0' AND M.ESTADO='1' ORDER BY P.APEPATERNO,P.APEMATERNO,P.NOMUNO";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idTrabajo);
			stmt.setInt(2, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE);
			result = (ResultSet) stmt.executeQuery();

			while (result.next()) {

				matricula = new TrabajoIndividualMatricula();
				matricula.setIdTrabajo(idTrabajo);
				matricula.setEstado(result.getInt("ESTADO"));
				matricula.setExpirado(result.getInt("EXPIRADO"));

				if (result.getString("NOTA") == null)
					matricula.setNota(null);
				else
					matricula.setNota(result.getInt("NOTA"));

				usuarioReceptor = new Matricula();
				usuarioReceptor.setIdMatricula(result.getString("IDMATRICULA"));
				usuarioReceptor.setIdUsuario(result.getString("USUARIO"));
				usuarioReceptor.setSeccion(result.getString("SECCION"));
				usuarioReceptor.setNombre1(Formato.formatoTexto(result
						.getString("NOMUNO")));
				usuarioReceptor.setNombre2(Formato.formatoTexto(result
						.getString("NOMDOS")));
				usuarioReceptor.setPaterno(Formato.formatoTexto(result
						.getString("APEPATERNO")));
				usuarioReceptor.setMaterno(Formato.formatoTexto(result
						.getString("APEMATERNO")));

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
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			// *** CV_TRABAJO_INDIVIDUAL
			String query = "UPDATE CV_TRABAJO_INDIVIDUAL SET FECHA_ACTIVACION=TO_DATE (?, 'DD-MM-YYYY HH24:MI'), FECHA_ENTREGA=TO_DATE (?, 'DD-MM-YYYY HH24:MI'), DESCRIPCION=?, "
					+ "IDMATRICULA_ENVIO=?, ARCHIVO_NOMBRE=?, ARCHIVO_TAMANO=?, USUARIO_MOD=?, FECHA_MOD=SYSDATE "
					+ "WHERE IDTRABAJO=?";
			cons = (Connection)dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, Formato.setBaseDatosDeDateCompleto(tindividual
					.getFechaActivacion()));
			stmt.setString(2, Formato.setBaseDatosDeDateCompleto(tindividual
					.getFechaEntrega()));
			stmt.setString(3, tindividual.getInteraccion().getDescripcion());
			stmt.setString(4, tindividual.getInteraccion().getUsuarioEmisor()
					.getIdMatricula());
			stmt.setString(5, tindividual.getInteraccion().getArchivoNombre());
			stmt.setString(6, tindividual.getInteraccion().getArchivoTamanio());
			stmt.setString(7, tindividual.getUsuarioModificacion());
			stmt.setInt(8, tindividual.getIdTrabajo());

			if (1 != stmt.executeUpdate()) {
				log.error("Error en publicarTrabajo(TrabajoIndividual tindividual) - CV_TRABAJO_INDIVIDUAL");
				throw new DAOException("No culmino");
			}

			// *** TRABAJO_INDIVIDUAL_NOTA
			query = "INSERT INTO CV_TRABAJO_INDIVIDUAL_NOTA (IDTRABAJO,IDMATRICULA,USUARIO_MOD,FECHA_MOD,ESTADO,EXPIRADO) "
					+ "SELECT ?,IDMATRICULA,?,SYSDATE,?,? FROM CV_MATRICULA WHERE IDFICHA=? AND IDROL IN (?,?) AND ELIMINADO='0' AND ESTADO='1'";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, tindividual.getIdTrabajo());
			stmt.setString(2, tindividual.getUsuarioModificacion());
			stmt.setInt(3, Constante.FLAG_INICIA_PENDIENTE_ESTUDIANTE);
			stmt.setInt(4, Constante.TRABAJO_ESTADO_PENDIENTE);
			stmt.setInt(5, tindividual.getIdFicha());
			stmt.setInt(6, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE);
			stmt.setInt(7, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE_MONITOR);

			if (0 == stmt.executeUpdate()) {
				log.error("Error en publicarTrabajo(TrabajoIndividual tindividual) - TRABAJO_INDIVIDUAL_NOTA");
				throw new DAOException("No culmino");
			}

			// *** TRABAJO_INDIVIDUAL_INTER
			query = "INSERT INTO CV_TRABAJO_INDIVIDUAL_INTER (IDTRABAJO,IDMATRICULA,INTERACCION,DESCRIPCION,IDMATRICULA_ENVIO,USUARIO_CREACION,FECHA_CREACION,USUARIO_MOD,FECHA_MOD,ARCHIVO_NOMBRE,ARCHIVO_TAMANO) "
					+ "SELECT ? IDTRABAJO,IDMATRICULA,'1' INTERACCION,?,?,?,SYSDATE,?,SYSDATE,?,? "
					+ "FROM CV_MATRICULA WHERE IDFICHA=? AND IDROL IN (?,?) AND ELIMINADO='0' AND ESTADO='1'";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, tindividual.getIdTrabajo());
			stmt.setString(2, tindividual.getInteraccion().getDescripcion());
			stmt.setString(3, tindividual.getInteraccion().getUsuarioEmisor()
					.getIdMatricula());
			stmt.setString(4, tindividual.getUsuarioCreacion());
			stmt.setString(5, tindividual.getUsuarioModificacion());
			stmt.setString(6, tindividual.getInteraccion().getArchivoNombre());
			stmt.setString(7, tindividual.getInteraccion().getArchivoTamanio());
			stmt.setInt(8, tindividual.getIdFicha());
			stmt.setInt(9, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE);
			stmt.setInt(10, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE_MONITOR);

			if (0 == stmt.executeUpdate()) {
				log.error("Error en publicarTrabajo(TrabajoIndividual tindividual) - TRABAJO_INDIVIDUAL_NOTA");
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

	public void modificarTrabajo(TrabajoIndividual tindividual)
			throws DAOException {
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			// *** CV_TRABAJO_INDIVIDUAL
			String query = "";
			cons = (Connection)dataSource.getConnection();
			cons.setAutoCommit(false);
			if (tindividual.getInteraccion().getArchivoNombre() != null) {
				query = "UPDATE CV_TRABAJO_INDIVIDUAL SET FECHA_ACTIVACION=TO_DATE (?, 'DD-MM-YYYY HH24:MI'), FECHA_ENTREGA=TO_DATE (?, 'DD-MM-YYYY HH24:MI'), DESCRIPCION=?, "
						+ "IDMATRICULA_ENVIO=?, ARCHIVO_NOMBRE=?, ARCHIVO_TAMANO=?, USUARIO_MOD=?, FECHA_MOD=SYSDATE "
						+ "WHERE IDTRABAJO=?";
				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt.setString(1, Formato
						.setBaseDatosDeDateCompleto(tindividual
								.getFechaActivacion()));
				stmt.setString(2, Formato
						.setBaseDatosDeDateCompleto(tindividual
								.getFechaEntrega()));
				stmt
						.setString(3, tindividual.getInteraccion()
								.getDescripcion());
				stmt.setString(4, tindividual.getInteraccion()
						.getUsuarioEmisor().getIdMatricula());
				stmt.setString(5, tindividual.getInteraccion()
						.getArchivoNombre());
				stmt.setString(6, tindividual.getInteraccion()
						.getArchivoTamanio());
				stmt.setString(7, tindividual.getUsuarioModificacion());
				stmt.setInt(8, tindividual.getIdTrabajo());
			} else {
				query = "UPDATE CV_TRABAJO_INDIVIDUAL SET FECHA_ACTIVACION=TO_DATE (?, 'DD-MM-YYYY HH24:MI'), FECHA_ENTREGA=TO_DATE (?, 'DD-MM-YYYY HH24:MI'), DESCRIPCION=?, "
						+ "IDMATRICULA_ENVIO=?, USUARIO_MOD=?, FECHA_MOD=SYSDATE "
						+ "WHERE IDTRABAJO=?";
				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt.setString(1, Formato
						.setBaseDatosDeDateCompleto(tindividual
								.getFechaActivacion()));
				stmt.setString(2, Formato
						.setBaseDatosDeDateCompleto(tindividual
								.getFechaEntrega()));
				stmt
						.setString(3, tindividual.getInteraccion()
								.getDescripcion());
				stmt.setString(4, tindividual.getInteraccion()
						.getUsuarioEmisor().getIdMatricula());
				stmt.setString(5, tindividual.getUsuarioModificacion());
				stmt.setInt(6, tindividual.getIdTrabajo());
			}
			if (1 != stmt.executeUpdate()) {
				log
						.error("Error modificarTrabajo(TrabajoIndividual tindividual) - CV_TRABAJO_INDIVIDUAL");
				throw new DAOException("No culmino");
			}

			// *** TRABAJO_INDIVIDUAL_INTER
			if (tindividual.getInteraccion().getArchivoNombre() != null) {
				query = "UPDATE CV_TRABAJO_INDIVIDUAL_INTER SET DESCRIPCION=?, IDMATRICULA_ENVIO=?, USUARIO_MOD=?,FECHA_MOD=SYSDATE, ARCHIVO_NOMBRE=?, ARCHIVO_TAMANO=? "
						+ "WHERE IDTRABAJO=? AND INTERACCION='1'";
				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt
						.setString(1, tindividual.getInteraccion()
								.getDescripcion());
				stmt.setString(2, tindividual.getInteraccion()
						.getUsuarioEmisor().getIdMatricula());
				stmt.setString(3, tindividual.getUsuarioModificacion());
				stmt.setString(4, tindividual.getInteraccion()
						.getArchivoNombre());
				stmt.setString(5, tindividual.getInteraccion()
						.getArchivoTamanio());
				stmt.setInt(6, tindividual.getIdTrabajo());
			} else {
				query = "UPDATE CV_TRABAJO_INDIVIDUAL_INTER SET DESCRIPCION=?, IDMATRICULA_ENVIO=?, USUARIO_MOD=?,FECHA_MOD=SYSDATE "
						+ "WHERE IDTRABAJO=? AND INTERACCION='1'";
				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt
						.setString(1, tindividual.getInteraccion()
								.getDescripcion());
				stmt.setString(2, tindividual.getInteraccion()
						.getUsuarioEmisor().getIdMatricula());
				stmt.setString(3, tindividual.getUsuarioModificacion());
				stmt.setInt(4, tindividual.getIdTrabajo());
			}

			if (0 == stmt.executeUpdate()) {
				log
						.error("Error en modificarTrabajo(TrabajoIndividual tindividual) - TRABAJO_INDIVIDUAL_NOTA");
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
			// *** CV_TRABAJO_INDIVIDUAL
			String query = "";
			cons = (Connection)dataSource.getConnection();
			cons.setAutoCommit(false);

			query = "UPDATE CV_TRABAJO_INDIVIDUAL SET ARCHIVO_NOMBRE=?, ARCHIVO_TAMANO=?, USUARIO_MOD=?, FECHA_MOD=SYSDATE "
					+ "WHERE IDTRABAJO=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, null);
			stmt.setString(2, null);
			stmt.setString(3, tindividual.getUsuarioModificacion());
			stmt.setInt(4, tindividual.getIdTrabajo());

			if (1 != stmt.executeUpdate()) {
				log
						.error("Error modificarTrabajo(TrabajoIndividual tindividual) - CV_TRABAJO_INDIVIDUAL");
				throw new DAOException("No culmino");
			}

			// *** TRABAJO_INDIVIDUAL_INTER
			query = "UPDATE CV_TRABAJO_INDIVIDUAL_INTER SET USUARIO_MOD=?,FECHA_MOD=SYSDATE, ARCHIVO_NOMBRE=?, ARCHIVO_TAMANO=? "
					+ "WHERE IDTRABAJO=? AND INTERACCION='1'";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, tindividual.getUsuarioModificacion());
			stmt.setString(2, null);
			stmt.setString(3, null);
			stmt.setInt(4, tindividual.getIdTrabajo());

			if (0 == stmt.executeUpdate()) {
				log
						.error("Error en modificarTrabajo(TrabajoIndividual tindividual) - TRABAJO_INDIVIDUAL_NOTA");
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
		Connection cons = null;
		PreparedStatement stmt = null;
		try {

			String query = "UPDATE CV_TRABAJO_INDIVIDUAL_NOTA SET NOTA=?, USUARIO_MOD=?, FECHA_MOD=SYSDATE "
					+ "WHERE IDTRABAJO=? AND IDMATRICULA=?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			if (tMatricula.getNota() == null)
				stmt.setString(1, null);
			else
				stmt.setInt(1, tMatricula.getNota());
			stmt.setString(2, tMatricula.getUsuarioModificacion());
			stmt.setInt(3, tMatricula.getIdTrabajo());
			stmt.setString(4, tMatricula.getUsuarioReceptor().getIdMatricula());

			if (1 != stmt.executeUpdate()) {
				log
						.error("Error calificarTrabajo(TrabajoIndividualMatricula tMatricula)");
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
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Collection<TrabajoIndividualInteraccion> interacciones = new ArrayList<TrabajoIndividualInteraccion>();
		TrabajoIndividualInteraccion interaccion = null;
		Matricula emisor = null;

		try {

			String query = "SELECT T.IDTRABAJO,T.IDMATRICULA,T.NOTA,T.ESTADO,T.EXPIRADO,P.APEPATERNO,P.APEMATERNO,P.NOMUNO,P.NOMDOS "
					+ "FROM CV_TRABAJO_INDIVIDUAL_NOTA T, CV_MATRICULA M, SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P "
					+ "WHERE T.IDMATRICULA=M.IDMATRICULA(+) AND U.USUARIO(+)=M.USUARIO AND U.CODSUJETO=P.CODPERSONA(+) "
					+ "AND T.IDTRABAJO=? AND T.IDMATRICULA=?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, matricula.getIdTrabajo());
			stmt.setString(2, matricula.getUsuarioReceptor().getIdMatricula());
			result = (ResultSet) stmt.executeQuery();

			if (result.next()) {

				if (result.getString("NOTA") == null)
					matricula.setNota(null);
				else
					matricula.setNota(result.getInt("NOTA"));

				matricula.setEstado(result.getInt("ESTADO"));
				matricula.setExpirado(result.getInt("EXPIRADO"));
				matricula.getUsuarioReceptor().setPaterno(
						Formato.formatoTexto(result.getString("APEPATERNO")));
				matricula.getUsuarioReceptor().setMaterno(
						Formato.formatoTexto(result.getString("APEMATERNO")));
				matricula.getUsuarioReceptor().setNombre1(
						Formato.formatoTexto(result.getString("NOMUNO")));
				matricula.getUsuarioReceptor().setNombre2(
						Formato.formatoTexto(result.getString("NOMDOS")));

				// Crear Colección de interacciones
				query = "SELECT T.IDMATRICULA_ENVIO,T.INTERACCION,T.DESCRIPCION,T.USUARIO_CREACION,T.FECHA_CREACION,T.USUARIO_MOD,T.FECHA_MOD, "
						+ "T.ARCHIVO_NOMBRE,T.ARCHIVO_TAMANO,P.APEPATERNO,P.APEMATERNO,P.NOMUNO,P.NOMDOS "
						+ "FROM CV_TRABAJO_INDIVIDUAL_INTER T, CV_MATRICULA M, SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P "
						+ "WHERE T.IDMATRICULA_ENVIO=M.IDMATRICULA(+) AND U.USUARIO(+)=M.USUARIO AND U.CODSUJETO=P.CODPERSONA(+) "
						+ "AND T.IDTRABAJO=? AND T.IDMATRICULA=? ORDER BY T.INTERACCION";
				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt.setInt(1, matricula.getIdTrabajo());
				stmt.setString(2, matricula.getUsuarioReceptor()
						.getIdMatricula());
				result = (ResultSet) stmt.executeQuery();

				while (result.next()) {

					interaccion = new TrabajoIndividualInteraccion();
					interaccion.setIdMensaje(result.getInt("INTERACCION"));
					interaccion.setDescripcion(result.getString("DESCRIPCION"));
					interaccion.setUsuarioCreacion(result
							.getString("USUARIO_CREACION"));
					interaccion.setUsuarioModificacion(result
							.getString("USUARIO_MOD"));
					interaccion.setFechaCreacion(Formato
							.getDateDeBaseDatos(result
									.getString("FECHA_CREACION")));
					interaccion.setFechaModificacion(Formato
							.getDateDeBaseDatos(result.getString("FECHA_MOD")));
					interaccion.setArchivoNombre(result
							.getString("ARCHIVO_NOMBRE"));
					interaccion.setArchivoTamanio(result
							.getString("ARCHIVO_TAMANO"));

					emisor = new Matricula();
					emisor
							.setIdMatricula(result
									.getString("IDMATRICULA_ENVIO"));
					emisor.setPaterno(Formato.formatoTexto(result
							.getString("APEPATERNO")));
					emisor.setMaterno(Formato.formatoTexto(result
							.getString("APEMATERNO")));
					emisor.setNombre1(Formato.formatoTexto(result
							.getString("NOMUNO")));
					emisor.setNombre2(Formato.formatoTexto(result
							.getString("NOMDOS")));

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

			String query = "SELECT ESTADO,EXPIRADO,NOTA FROM CV_TRABAJO_INDIVIDUAL_NOTA "
					+ "WHERE IDTRABAJO=? AND IDMATRICULA=?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, matricula.getIdTrabajo());
			stmt.setString(2, matricula.getUsuarioReceptor().getIdMatricula());
			result = (ResultSet) stmt.executeQuery();

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
			// *** CV_TRABAJO_INDIVIDUAL_INTER
			String query = "INSERT INTO CV_TRABAJO_INDIVIDUAL_INTER (IDTRABAJO, IDMATRICULA, INTERACCION, "
					+ "IDMATRICULA_ENVIO, DESCRIPCION, USUARIO_CREACION, "
					+ "FECHA_CREACION, USUARIO_MOD, FECHA_MOD, ARCHIVO_NOMBRE, ARCHIVO_TAMANO)  "
					+ "VALUES (?, ?, ? , ?, ?, ?, SYSDATE, ?, SYSDATE, ?, ?)";

			cons = (Connection)dataSource.getConnection();
			cons.setAutoCommit(false);

			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, matricula.getIdTrabajo());
			stmt.setString(2, matricula.getUsuarioReceptor().getIdMatricula());
			stmt.setInt(3, interaccion.getIdMensaje() + 1);
			stmt.setString(4, interaccion.getUsuarioEmisor().getIdMatricula());
			stmt.setString(5, interaccion.getDescripcion());
			stmt.setString(6, interaccion.getUsuarioCreacion());
			stmt.setString(7, interaccion.getUsuarioModificacion());
			stmt.setString(8, interaccion.getArchivoNombre());
			stmt.setString(9, interaccion.getArchivoTamanio());

			if (1 != stmt.executeUpdate()) {
				log
						.error("Error nuevoMensajeDeDocente(TrabajoIndividual tindividual) - CV_TRABAJO_INDIVIDUAL_INTER");
				throw new DAOException("No culmino");
			}

			// *** TRABAJO_INDIVIDUAL_NOTA
			query = "UPDATE CV_TRABAJO_INDIVIDUAL_NOTA SET ESTADO=? WHERE IDTRABAJO=? AND IDMATRICULA=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, Constante.FLAG_PENDIENTE_ESTUDIANTE);
			stmt.setInt(2, matricula.getIdTrabajo());
			stmt.setString(3, matricula.getUsuarioReceptor().getIdMatricula());

			if (1 != stmt.executeUpdate()) {
				log
						.error("Error en nuevoMensajeDeDocente(TrabajoIndividual tindividual) - TRABAJO_INDIVIDUAL_NOTA");
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
		TrabajoIndividualInteraccion interaccion = (TrabajoIndividualInteraccion) matricula
				.getInteracciones().iterator().next();

		try {
			cons = (Connection)dataSource.getConnection();
			cons.setAutoCommit(false);

			// *** CV_TRABAJO_INDIVIDUAL_INTER

			String query = "INSERT INTO CV_TRABAJO_INDIVIDUAL_INTER (IDTRABAJO, IDMATRICULA, INTERACCION, "
					+ "IDMATRICULA_ENVIO, DESCRIPCION, USUARIO_CREACION, "
					+ "FECHA_CREACION, USUARIO_MOD, FECHA_MOD, ARCHIVO_NOMBRE, ARCHIVO_TAMANO)  "
					+ "VALUES (?, ?, ? , ?, ?, ?, SYSDATE, ?, SYSDATE, ?, ?)";

			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, matricula.getIdTrabajo());
			stmt.setString(2, matricula.getUsuarioReceptor().getIdMatricula());
			stmt.setInt(3, interaccion.getIdMensaje() + 1);
			stmt.setString(4, interaccion.getUsuarioEmisor().getIdMatricula());
			stmt.setString(5, interaccion.getDescripcion());
			stmt.setString(6, interaccion.getUsuarioCreacion());
			stmt.setString(7, interaccion.getUsuarioModificacion());
			stmt.setString(8, interaccion.getArchivoNombre());
			stmt.setString(9, interaccion.getArchivoTamanio());

			if (1 != stmt.executeUpdate()) {
				log
						.error("Error nuevoMensajeDeDocente(TrabajoIndividual tindividual) - CV_TRABAJO_INDIVIDUAL_INTER");
				throw new DAOException("No culmino");
			}

			// *** TRABAJO_INDIVIDUAL_NOTA
			if (!"".equals(interaccion.getArchivoNombre())
					&& matricula.getExpirado() != 0) {
				query = "UPDATE CV_TRABAJO_INDIVIDUAL_NOTA SET ESTADO=?, EXPIRADO=? WHERE IDTRABAJO=? AND IDMATRICULA=?";
				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt.setInt(1, Constante.FLAG_PENDIENTE_DOCENTE);
				stmt.setInt(2, matricula.getExpirado());
				stmt.setInt(3, matricula.getIdTrabajo());
				stmt.setString(4, matricula.getUsuarioReceptor()
						.getIdMatricula());
			} else {
				query = "UPDATE CV_TRABAJO_INDIVIDUAL_NOTA SET ESTADO=? WHERE IDTRABAJO=? AND IDMATRICULA=?";
				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt.setInt(1, Constante.FLAG_PENDIENTE_DOCENTE);
				stmt.setInt(2, matricula.getIdTrabajo());
				stmt.setString(3, matricula.getUsuarioReceptor()
						.getIdMatricula());
			}

			if (1 != stmt.executeUpdate()) {
				log
						.error("Error en nuevoMensajeDeDocente(TrabajoIndividual tindividual) - TRABAJO_INDIVIDUAL_NOTA");
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
			cons = (Connection)dataSource.getConnection();

			if (!"".equals(interaccion.getArchivoNombre())) {
				String query = "UPDATE CV_TRABAJO_INDIVIDUAL_INTER "
						+ "SET DESCRIPCION=?, USUARIO_MOD=?, FECHA_MOD=SYSDATE, ARCHIVO_NOMBRE=?, ARCHIVO_TAMANO=? "
						+ "WHERE IDTRABAJO=? AND IDMATRICULA=? AND INTERACCION=?";

				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt.setString(1, interaccion.getDescripcion());
				stmt.setString(2, interaccion.getUsuarioModificacion());
				stmt.setString(3, interaccion.getArchivoNombre());
				stmt.setString(4, interaccion.getArchivoTamanio());
				stmt.setInt(5, matricula.getIdTrabajo());
				stmt.setString(6, matricula.getUsuarioReceptor()
						.getIdMatricula());
				stmt.setInt(7, interaccion.getIdMensaje());
			} else {
				String query = "UPDATE CV_TRABAJO_INDIVIDUAL_INTER "
						+ "SET DESCRIPCION=?, USUARIO_MOD=?, FECHA_MOD=SYSDATE "
						+ "WHERE IDTRABAJO=? AND IDMATRICULA=? AND INTERACCION=?";

				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt.setString(1, interaccion.getDescripcion());
				stmt.setString(2, interaccion.getUsuarioModificacion());
				stmt.setInt(3, matricula.getIdTrabajo());
				stmt.setString(4, matricula.getUsuarioReceptor()
						.getIdMatricula());
				stmt.setInt(5, interaccion.getIdMensaje());
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