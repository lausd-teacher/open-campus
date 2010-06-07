package edu.opencampus.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.modelo.TrabajoGrupal;
import edu.opencampus.lms.modelo.Matricula;
import edu.opencampus.lms.modelo.tgrupal.TrabajoGrupalGrupo;
import edu.opencampus.lms.modelo.tgrupal.TrabajoGrupalIntegrante;
import edu.opencampus.lms.modelo.tgrupal.TrabajoGrupalMensaje;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;

public class TrabajoGrupalDAO extends BaseDAO {

	protected DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public TrabajoGrupal obtenerTrabajoGrupal(TrabajoGrupal tg) throws DAOException {
		log.info("obtenerTrabajoGrupal(TrabajoGrupal tg)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String query = "SELECT T.IDTRABAJO,T.FECHA_ACTIVACION,T.FECHA_ENTREGA,T.DESCRIPCION,T.PUBLICADOR,D.NOMBRE_COMPLETO,P.APEPATERNO,P.APEMATERNO,P.NOMUNO,P.NOMDOS "
					+ "FROM cv_trabajo_grupal T, cv_unidad D, cv_matricula M, SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P "
					+ "WHERE T.PUBLICADOR=M.IDMATRICULA(+) AND M.USUARIO=U.USUARIO(+) AND U.CODSUJETO=P.CODPERSONA(+) AND T.IDUNIDAD=D.IDUNIDAD(+) AND T.IDFICHA=? AND T.IDUNIDAD=?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, tg.getIdFicha());
			stmt.setInt(2, tg.getIdUnidad());
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				tg.setIdTrabajo(result.getInt("IDTRABAJO"));
				tg.setFechaActivacion(Formato.getDateDeBaseDatos(result
						.getString("FECHA_ACTIVACION")));
				tg.setFechaEntrega(Formato.getDateDeBaseDatos(result
						.getString("FECHA_ENTREGA")));
				tg.setDescripcion(result.getString("DESCRIPCION"));
				tg.setNombreUnidad(result.getString("NOMBRE_COMPLETO"));
				Matricula publicador = new Matricula();
				publicador.setIdMatricula(result.getString("PUBLICADOR"));
				publicador.setPaterno(Formato.formatoTexto(result
						.getString("APEPATERNO")));
				publicador.setMaterno(Formato.formatoTexto(result
						.getString("APEMATERNO")));
				publicador.setNombre1(Formato.formatoTexto(result
						.getString("NOMUNO")));
				publicador.setNombre2(Formato.formatoTexto(result
						.getString("NOMDOS")));
				tg.setPublicador(publicador);
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
		return tg;

	}

	public TrabajoGrupal obtenerTrabajoGrupalPorID(TrabajoGrupal tg) throws DAOException {
		log.info("obtenerTrabajoGrupalPorID(TrabajoGrupal tg)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try {

			String query = "SELECT T.IDUNIDAD,T.IDFICHA,T.FECHA_ACTIVACION,T.FECHA_ENTREGA,T.DESCRIPCION,T.PUBLICADOR,D.NOMBRE_COMPLETO,P.APEPATERNO,P.APEMATERNO,P.NOMUNO,P.NOMDOS "
					+ "FROM cv_trabajo_grupal T, cv_unidad D, cv_matricula M, SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P "
					+ "WHERE T.PUBLICADOR=M.IDMATRICULA(+) AND M.USUARIO=U.USUARIO(+) AND U.CODSUJETO=P.CODPERSONA(+) AND T.IDUNIDAD=D.IDUNIDAD(+) AND T.IDTRABAJO=?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, tg.getIdTrabajo());
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				tg.setIdUnidad(result.getInt("IDUNIDAD"));
				tg.setIdFicha(result.getInt("IDFICHA"));
				tg.setFechaActivacion(Formato.getDateDeBaseDatos(result
						.getString("FECHA_ACTIVACION")));
				tg.setFechaEntrega(Formato.getDateDeBaseDatos(result
						.getString("FECHA_ENTREGA")));
				tg.setDescripcion(result.getString("DESCRIPCION"));
				tg.setNombreUnidad(result.getString("NOMBRE_COMPLETO"));
				Matricula publicador = new Matricula();
				publicador.setIdMatricula(result.getString("PUBLICADOR"));
				publicador.setPaterno(Formato.formatoTexto(result
						.getString("APEPATERNO")));
				publicador.setMaterno(Formato.formatoTexto(result
						.getString("APEMATERNO")));
				publicador.setNombre1(Formato.formatoTexto(result
						.getString("NOMUNO")));
				publicador.setNombre2(Formato.formatoTexto(result
						.getString("NOMDOS")));
				tg.setPublicador(publicador);
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
		return tg;

	}
	
	public int obtenerIdGrupo(TrabajoGrupal idTrabajo, int idMatricula) throws DAOException {
		log.info("obtenerIdGrupo("+idTrabajo+", "+idMatricula+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {

			String query = "SELECT NVL((SELECT M.IDGRUPO FROM CV_GRUPO_TRABAJO_MATRICULA M, CV_GRUPO_TRABAJO G WHERE M.IDTRABAJO=G.IDTRABAJO AND M.IDGRUPO=G.IDGRUPO AND M.IDTRABAJO=? AND M.IDMATRICULA=? AND G.ESTADO=?),-1) IDGRUPO FROM DUAL";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idTrabajo);
			stmt.setInt(2, idMatricula);
			stmt.setInt(3, Constante.ESTADO_ACTIVO);
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				return result.getInt("IDGRUPO");
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
		return 0;
	}

	public Collection<Matricula> verIntegrantes(TrabajoGrupal idTrabajo, int idMatricula) throws DAOException {
		log.info("verIntegrantes(int idTrabajo, int idMatricul)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Collection<Matricula> integrantes = new ArrayList<Matricula>();
		Matricula colega = null;
		
		try {

			String query = "SELECT P.APEPATERNO, P.APEMATERNO, P.NOMUNO, P.NOMDOS " +
					"FROM GENERAL.GEN_PERSONA P, SEGURIDAD.SEG_USUARIO U, CV_GRUPO_TRABAJO_MATRICULA TM, CV_GRUPO_TRABAJO G, cv_matricula M " +
					"WHERE G.IDTRABAJO=TM.IDTRABAJO AND G.IDGRUPO=TM.IDGRUPO AND TM.IDMATRICULA=M.IDMATRICULA " +
					"AND U.USUARIO=M.USUARIO AND U.CODSUJETO=P.CODPERSONA AND G.ESTADO='1' " +
					"AND G.IDTRABAJO=? AND G.IDGRUPO=(SELECT IDGRUPO FROM CV_GRUPO_TRABAJO_MATRICULA " +
					"WHERE IDTRABAJO=G.IDTRABAJO AND IDMATRICULA=?) ORDER BY P.APEPATERNO, P.APEMATERNO, P.NOMUNO, P.NOMDOS";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idTrabajo);
			stmt.setInt(2, idMatricula);
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				colega = new Matricula();
				colega.setPaterno(Formato.formatoTexto(result.getString("APEPATERNO")));
				colega.setMaterno(Formato.formatoTexto(result.getString("APEMATERNO")));
				colega.setNombre1(Formato.formatoTexto(result.getString("NOMUNO")));
				colega.setNombre2(Formato.formatoTexto(result.getString("NOMDOS")));
				
				integrantes.add(colega);
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
		return integrantes;

	}
	
	public TrabajoGrupalGrupo verTrabajo(TrabajoGrupalGrupo grupo) throws DAOException {
		log.info("verTrabajo(TrabajoGrupalGrupo grupo)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try {
			String query = "SELECT ARCHIVO_NOMBRE,ARCHIVO_TAMANO FROM CV_GRUPO_TRABAJO WHERE IDTRABAJO=? AND IDGRUPO=?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, grupo.getIdTrabajo());
			stmt.setInt(2, grupo.getIdGrupo());
			result = (ResultSet) stmt.executeQuery();

			if (result.next()) {
				grupo.setArchivoNombre(result.getString("ARCHIVO_NOMBRE"));
				grupo.setArchivoTamanio(result.getString("ARCHIVO_TAMANO"));
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
		return grupo;

	}

	public void subirTrabajo(TrabajoGrupalGrupo grupo) throws DAOException {
		log.info("subirTrabajo(TrabajoGrupalGrupo grupo)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "UPDATE CV_GRUPO_TRABAJO SET ARCHIVO_NOMBRE=?, ARCHIVO_TAMANO=? "
					+ "WHERE IDTRABAJO=? AND IDGRUPO=?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, grupo.getArchivoNombre());
			stmt.setString(2, grupo.getArchivoTamanio());
			stmt.setInt(3, grupo.getIdTrabajo());
			stmt.setInt(4, grupo.getIdGrupo());

			if (1 != stmt.executeUpdate()) {
				log.error("Error subirTrabajo(TrabajoGrupalGrupo grupo)");
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

	public void calificarTrabajo(int idTrabajoGrupal,TrabajoGrupalIntegrante integrante) throws DAOException {
		log.info("calificarTrabajo(int idTrabajoGrupal,TrabajoGrupalIntegrante integrante)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "UPDATE CV_GRUPO_TRABAJO_MATRICULA SET USUARIO_MOD=?, FECHA_MOD=SYSDATE, NOTA_OPORTUNIDAD=?, "
					+ "NOTA_COHERENCIA=?, NOTA_INNOVACION=?, NOTA_PARTICIPACION=?, NOTA_PROMEDIO=? "
					+ "WHERE IDTRABAJO=? AND IDMATRICULA=?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, integrante.getUsuarioModificacion());
			stmt.setString(2, integrante.getNotaOportunidad());
			stmt.setString(3, integrante.getNotaCoherencia());
			stmt.setString(4, integrante.getNotaInnovacion());
			stmt.setString(5, integrante.getNotaParticipacion());
			stmt.setString(6, integrante.getNotaPromedio());
			stmt.setInt(7, idTrabajoGrupal);
			stmt.setString(8, integrante.getUsuarioMatricula().getIdMatricula());

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

	public void publicarTrabajo(TrabajoGrupal trabajo) throws DAOException {
		log.info("publicarTrabajo(TrabajoGrupal trabajo)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			cons = (Connection)dataSource.getConnection();
			cons.setAutoCommit(false);
			String query = "UPDATE cv_trabajo_grupal SET PUBLICADOR=?, FECHA_ACTIVACION=TO_DATE (?, 'DD-MM-YYYY HH24:MI'), FECHA_ENTREGA=TO_DATE (?, 'DD-MM-YYYY HH24:MI'), DESCRIPCION=?, USUARIO_MOD=?, FECHA_MOD=SYSDATE "
					+ "WHERE IDTRABAJO=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, trabajo.getPublicador().getIdMatricula());
			stmt.setString(2, Formato.setBaseDatosDeDateCompleto(trabajo
					.getFechaActivacion()));
			stmt.setString(3, Formato.setBaseDatosDeDateCompleto(trabajo
					.getFechaEntrega()));
			stmt.setString(4, trabajo.getDescripcion());
			stmt.setString(5, trabajo.getUsuarioModificacion());
			stmt.setInt(6, trabajo.getIdTrabajo());
			int n = stmt.executeUpdate();
			if (1 != n) {
				log.error("Error en publicarTrabajo(TrabajoGrupal trabajo) - Row Updates: "+n);
				throw new DAOException("No culmino");
			}

			query = "UPDATE CV_GRUPO_TRABAJO SET BANDERA=? WHERE IDTRABAJO=? AND BANDERA=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, Constante.FLAG_PENDIENTE_ESTUDIANTE);
			stmt.setInt(2, trabajo.getIdTrabajo());
			stmt.setInt(3, Constante.FLAG_NO_INICIADO);
			stmt.executeUpdate();

			cons.commit();
		} catch (SQLException e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
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

	public Collection<Matricula> obtenerEstudiantes(int idFicha)
			throws DAOException {
		log.info("obtenerEstudiantes(int idFicha)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		Collection<Matricula> estudiantes = new ArrayList<Matricula>();
		Matricula estudiante = null;

		try {

			String query = "SELECT M.IDMATRICULA,M.IDROL,M.USUARIO,P.APEPATERNO,P.APEMATERNO,P.NOMUNO,P.NOMDOS  "
					+ "FROM cv_matricula M, SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P "
					+ "WHERE U.USUARIO(+)=M.USUARIO AND U.CODSUJETO=P.CODPERSONA(+) AND M.IDROL=? AND M.IDFICHA=? AND M.ELIMINADO='0' AND M.ESTADO='1' ORDER BY P.APEPATERNO,P.APEMATERNO,P.NOMUNO";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE);
			stmt.setInt(2, idFicha);
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				estudiante = new Matricula();
				estudiante.setIdMatricula(result.getString("IDMATRICULA"));
				estudiante.setRol(result.getInt("IDROL"));
				estudiante.setIdUsuario(result.getString("USUARIO"));
				estudiante.setPaterno(result.getString("APEPATERNO"));
				estudiante.setMaterno(result.getString("APEMATERNO"));
				estudiante.setNombre1(result.getString("NOMUNO"));
				estudiante.setNombre2(result.getString("NOMDOS"));

				estudiantes.add(estudiante);
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
		return estudiantes;

	}

	public TrabajoGrupalGrupo crearGrupo(TrabajoGrupalGrupo grupo)
			throws DAOException {
		log.info("crearGrupo(TrabajoGrupalGrupo grupo)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String query = "SELECT "
					+ "(SELECT NVL(MAX(IDGRUPO),0)+1 FROM CV_GRUPO_TRABAJO WHERE IDTRABAJO=?) ID, "
					+ "(SELECT FECHA_ACTIVACION FROM cv_trabajo_grupal WHERE IDTRABAJO=?) FLAG "
					+ "FROM DUAL";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, grupo.getIdTrabajo());
			stmt.setInt(2, grupo.getIdTrabajo());
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				grupo.setIdGrupo(result.getInt("ID"));
				if (result.getString("FLAG") == null)
					grupo.setBandera(Constante.FLAG_NO_INICIADO);
				else
					grupo.setBandera(Constante.FLAG_PENDIENTE_ESTUDIANTE);
			}
			
			query = "INSERT INTO CV_GRUPO_TRABAJO (IDTRABAJO,IDGRUPO,NOMBRE,ESTADO,BANDERA) VALUES (?,?,?,?,?)";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, grupo.getIdTrabajo());
			stmt.setInt(2, grupo.getIdGrupo());
			stmt.setString(3, grupo.getNombre());
			stmt.setInt(4, grupo.getEstado());
			stmt.setInt(5, grupo.getBandera());
			if (1 != stmt.executeUpdate()) {
				log.error("Error en crearGrupo(TrabajoGrupalGrupo grupo)");
				throw new DAOException("No culmino");
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
		return grupo;

	}

	public void asignarGrupo(TrabajoGrupalGrupo grupo, int idMatricula)
			throws DAOException {
		log.info("asignarGrupo(TrabajoGrupalGrupo grupo, int idMatricula)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "INSERT INTO CV_GRUPO_TRABAJO_MATRICULA (IDGRUPO,IDTRABAJO,IDMATRICULA,USUARIO_MOD,FECHA_MOD) "
					+ "VALUES (?,?,?,?,SYSDATE)";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, grupo.getIdGrupo());
			stmt.setInt(2, grupo.getIdTrabajo());
			stmt.setInt(3, idMatricula);
			stmt.setString(4, grupo.getUsuarioCreacion());
			if (1 != stmt.executeUpdate()) {
				log
						.error("Error en asignarGrupo(TrabajoGrupalGrupo grupo, int idMatricula)");
				throw new DAOException("No culmino");
			}

		} catch (SQLException e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} finally {
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

	public void desasignarGrupo(TrabajoGrupalGrupo grupo, int idMatricula)
			throws DAOException {
		log.info("desasignarGrupo(TrabajoGrupalGrupo grupo, int idMatricula)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "DELETE FROM CV_GRUPO_TRABAJO_MATRICULA WHERE IDTRABAJO=? AND IDGRUPO=? AND IDMATRICULA=?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, grupo.getIdTrabajo());
			stmt.setInt(2, grupo.getIdGrupo());
			stmt.setInt(3, idMatricula);
			if (1 != stmt.executeUpdate()) {
				log
						.error("Error en desasignarGrupo(TrabajoGrupalGrupo grupo, int idMatricula)");
				throw new DAOException("No culmino");
			}

		} catch (SQLException e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} finally {
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

	public void renombrarGrupo(TrabajoGrupalGrupo grupo) throws DAOException {
		log.info("renombrarGrupo(TrabajoGrupalGrupo grupo)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "UPDATE CV_GRUPO_TRABAJO SET NOMBRE=? WHERE IDTRABAJO=? AND IDGRUPO=?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, grupo.getNombre());
			stmt.setInt(2, grupo.getIdTrabajo());
			stmt.setInt(3, grupo.getIdGrupo());
			if (1 != stmt.executeUpdate()) {
				log.error("Error en renombrarGrupo(TrabajoGrupalGrupo grupo)");
				throw new DAOException("No culmino");
			}

		} catch (SQLException e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} finally {
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

	public void eliminarGrupo(TrabajoGrupalGrupo grupo) throws DAOException {
		log.info("eliminarGrupo(TrabajoGrupalGrupo grupo)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "DELETE FROM CV_GRUPO_TRABAJO_MATRICULA WHERE IDTRABAJO=? AND IDGRUPO=?";
			cons = (Connection)dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, grupo.getIdTrabajo());
			stmt.setInt(2, grupo.getIdGrupo());
			stmt.executeUpdate();

			query = "UPDATE CV_GRUPO_TRABAJO SET ESTADO=? WHERE IDTRABAJO=? AND IDGRUPO=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, grupo.getEstado());
			stmt.setInt(2, grupo.getIdTrabajo());
			stmt.setInt(3, grupo.getIdGrupo());
			if (1 != stmt.executeUpdate()) {
				log.error("Error en eliminarGrupo(TrabajoGrupalGrupo grupo)");
				throw new DAOException("No culmino");
			}
			cons.commit();
		} catch (SQLException e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
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
	
	public void eliminarGrupos(int idTrabajo) throws DAOException {
		log.info("eliminarGrupos(int idTrabajo)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "DELETE FROM CV_GRUPO_TRABAJO_MATRICULA WHERE IDTRABAJO=?";
			cons = (Connection)dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idTrabajo);
			stmt.executeUpdate();
			
			query = "DELETE FROM CV_GRUPO_TRABAJO_MSG WHERE IDTRABAJO=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idTrabajo);
			stmt.executeUpdate();

			query = "DELETE FROM CV_GRUPO_TRABAJO WHERE IDTRABAJO=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idTrabajo);
			stmt.executeUpdate();
			cons.commit();
		} catch (SQLException e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
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

	public Collection<TrabajoGrupalGrupo> obtenerGrupos(int idFicha,
			int idTrabajo) throws DAOException {
		log.info("obtenerGrupos(int idFicha,int idTrabajo)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Collection<TrabajoGrupalGrupo> grupos = new ArrayList<TrabajoGrupalGrupo>();
		TrabajoGrupalGrupo grupo = null;
		Collection<TrabajoGrupalIntegrante> integrantes = null;
		TrabajoGrupalIntegrante integrante = null;
		Matricula matricula = null;

		try {
			String query = "SELECT IDGRUPO,NOMBRE FROM CV_GRUPO_TRABAJO WHERE IDTRABAJO=? AND ESTADO=? ORDER BY NOMBRE";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idTrabajo);
			stmt.setInt(2, Constante.ESTADO_ACTIVO);
			result = (ResultSet) stmt.executeQuery();

			query = "SELECT G.IDMATRICULA,M.USUARIO,P.APEPATERNO,P.APEMATERNO,P.NOMUNO,P.NOMDOS "
					+ "FROM CV_GRUPO_TRABAJO_MATRICULA G, cv_matricula M, SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P "
					+ "WHERE G.IDMATRICULA=M.IDMATRICULA(+) AND U.USUARIO(+)=M.USUARIO AND U.CODSUJETO=P.CODPERSONA(+) AND G.IDTRABAJO=? AND G.IDGRUPO=? "
					+ "AND M.ELIMINADO='0' AND M.ESTADO='1' ORDER BY P.APEPATERNO,P.APEMATERNO,P.NOMUNO";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idTrabajo);
			ResultSet subResult = null;

			while (result.next()) {
				grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(result.getInt("IDGRUPO"));
				grupo.setNombre(result.getString("NOMBRE"));

				stmt.setInt(2, result.getInt("IDGRUPO"));
				subResult = (ResultSet) stmt.executeQuery();

				integrantes = new ArrayList<TrabajoGrupalIntegrante>();

				while (subResult.next()) {
					integrante = new TrabajoGrupalIntegrante();
					matricula = new Matricula();
					matricula
							.setIdMatricula(subResult.getString("IDMATRICULA"));
					matricula.setIdUsuario(subResult.getString("USUARIO")
							.trim());
					matricula.setPaterno(subResult.getString("APEPATERNO"));
					matricula.setMaterno(subResult.getString("APEMATERNO"));
					matricula.setNombre1(subResult.getString("NOMUNO"));
					matricula.setNombre2(subResult.getString("NOMDOS"));
					integrante.setUsuarioMatricula(matricula);

					integrantes.add(integrante);
				}
				grupo.setIntegrantes(integrantes);
				grupos.add(grupo);
			}

			query = "SELECT M.IDMATRICULA,M.USUARIO,P.APEPATERNO,P.APEMATERNO,P.NOMUNO,P.NOMDOS "
					+ "FROM cv_matricula M, SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P "
					+ "WHERE U.USUARIO(+)=M.USUARIO AND U.CODSUJETO=P.CODPERSONA(+) AND M.IDROL=? AND M.IDFICHA=? "
					+ "AND IDMATRICULA NOT IN (SELECT M.IDMATRICULA FROM CV_GRUPO_TRABAJO G, CV_GRUPO_TRABAJO_MATRICULA M "
					+ "WHERE G.IDTRABAJO=M.IDTRABAJO AND G.IDGRUPO=M.IDGRUPO AND G.IDTRABAJO=? AND G.ESTADO=?) "
					+ "AND M.ELIMINADO='0' AND M.ESTADO='1' ORDER BY P.APEPATERNO,P.APEMATERNO,P.NOMUNO";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE);
			stmt.setInt(2, idFicha);
			stmt.setInt(3, idTrabajo);
			stmt.setInt(4, Constante.ESTADO_ACTIVO);
			result = (ResultSet) stmt.executeQuery();

			grupo = new TrabajoGrupalGrupo();
			grupo.setIdGrupo(0);

			integrantes = new ArrayList<TrabajoGrupalIntegrante>();
			while (result.next()) {
				integrante = new TrabajoGrupalIntegrante();
				matricula = new Matricula();
				matricula.setIdMatricula(result.getString("IDMATRICULA"));
				matricula.setIdUsuario(result.getString("USUARIO").trim());
				matricula.setPaterno(result.getString("APEPATERNO"));
				matricula.setMaterno(result.getString("APEMATERNO"));
				matricula.setNombre1(result.getString("NOMUNO"));
				matricula.setNombre2(result.getString("NOMDOS"));
				integrante.setUsuarioMatricula(matricula);
				integrantes.add(integrante);
			}
			grupo.setIntegrantes(integrantes);
			grupos.add(grupo);

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
		return grupos;

	}

	public Collection<TrabajoGrupalGrupo> obtenerGruposParaEvaluacion(int idTrabajo, int idMatricula) throws DAOException {
		log.info("obtenerGruposParaEvaluacion(int idTrabajo, int idMatricula)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		Collection<TrabajoGrupalGrupo> grupos = new ArrayList<TrabajoGrupalGrupo>();
		TrabajoGrupalGrupo grupo = null;
		Collection<TrabajoGrupalIntegrante> integrantes = null;
		TrabajoGrupalIntegrante integrante = null;
		Matricula matricula = null;

		try {
			String query = "SELECT G.IDGRUPO,G.NOMBRE,G.ARCHIVO_NOMBRE,G.ARCHIVO_TAMANO,G.BANDERA,"
					+ "NVL((SELECT T.FECHA_ENTREGA - S.FECHA_CREACION "
					+ "FROM CV_GRUPO_TRABAJO_MSG S, cv_trabajo_grupal T "
					+ "WHERE S.IDTRABAJO=T.IDTRABAJO AND S.IDTRABAJO=G.IDTRABAJO AND S.IDGRUPO=G.IDGRUPO AND S.IDMENSAJE=(SELECT MIN(S.IDMENSAJE) "
					+ "FROM CV_GRUPO_TRABAJO_MSG S, cv_matricula M "
					+ "WHERE S.IDMATRICULA_EMISOR=M.IDMATRICULA AND S.IDTRABAJO=G.IDTRABAJO AND S.IDGRUPO=G.IDGRUPO  "
					+ "AND M.IDROL=? AND S.ARCHIVO_NOMBRE IS NOT NULL)),0) EXPIRADO, "
					+ "(SELECT COUNT(*) DEBATE_TOTAL FROM cv_debate WHERE ESTADO='1' AND IDTRABAJO=G.IDTRABAJO AND IDGRUPO=G.IDGRUPO) DEBATES,"
					+ "(SELECT COUNT(cvdi.iddebate) FROM cv_debate cvdi,cv_debate_matricula cvdima,"
					+ "cv_trabajo_grupal cvtrgr,cv_grupo_trabajo cvgrtr "
					+ "WHERE cvdima.iddebate = cvdi.iddebate AND cvdima.leido = 0 AND cvdi.estado = 1 "
					+ "AND cvgrtr.idtrabajo = cvtrgr.idtrabajo AND cvgrtr.estado =1 AND cvdima.idmatricula = ? "
					+ "AND cvgrtr.idgrupo = cvdi.idgrupo AND cvgrtr.idtrabajo = cvdi.idtrabajo AND cvgrtr.idgrupo = G.IDGRUPO "
					+ "AND cvgrtr.idtrabajo =G.IDTRABAJO) BANDERADEBATE "
					+ "FROM CV_GRUPO_TRABAJO G WHERE G.IDTRABAJO=? AND G.ESTADO=? ORDER BY G.IDGRUPO";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE);
			stmt.setInt(2, idMatricula);
			stmt.setInt(3, idTrabajo);
			stmt.setInt(4, Constante.ESTADO_ACTIVO);
			result = (ResultSet) stmt.executeQuery();

			query = "SELECT G.IDMATRICULA,G.NOTA_OPORTUNIDAD,NOTA_COHERENCIA,NOTA_INNOVACION,NOTA_PARTICIPACION,NOTA_PROMEDIO,"
					+ "PKG_CV_UTIL.fx_cv_string_number(M.SECCION) SECCION, P.APEPATERNO,P.APEMATERNO,P.NOMUNO,P.NOMDOS," 
					+ "(SELECT COUNT(*) DEBATE_TOTAL FROM cv_debate WHERE ESTADO='1' AND IDTRABAJO=G.IDTRABAJO AND IDGRUPO=G.IDGRUPO AND IDMATRICULA=G.IDMATRICULA) DEBATES "
					+ "FROM CV_GRUPO_TRABAJO_MATRICULA G, cv_matricula M, SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P "
					+ "WHERE G.IDMATRICULA=M.IDMATRICULA(+) AND U.USUARIO(+)=M.USUARIO AND U.CODSUJETO=P.CODPERSONA(+) AND G.IDTRABAJO=? AND G.IDGRUPO=? "
					+ "AND M.ELIMINADO='0' AND M.ESTADO='1' ORDER BY P.APEPATERNO,P.APEMATERNO,P.NOMUNO";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idTrabajo);
			ResultSet subResult = null;

			while (result.next()) {
				grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(result.getInt("IDGRUPO"));
				grupo.setNombre(result.getString("NOMBRE"));
				grupo.setArchivoNombre(result.getString("ARCHIVO_NOMBRE"));
				grupo.setArchivoTamanio(result.getString("ARCHIVO_TAMANO"));
				grupo.setBandera(result.getInt("BANDERA"));
				grupo.setBanderaDebate(result.getInt("BANDERADEBATE"));
				grupo.setDebates(result.getInt("DEBATES"));
				
				float exp = result.getFloat("EXPIRADO");
				if (exp < 0)
					grupo.setExpirado(Constante.FLAG_TRABAJO_EXPIRADO);
				else if (exp > 0)
					grupo.setExpirado(Constante.FLAG_TRABAJO_NOEXPIRADO);
				else
					grupo.setExpirado(Constante.FLAG_TRABAJO_PENDIENTE);

				stmt.setInt(2, result.getInt("IDGRUPO"));
				subResult = (ResultSet) stmt.executeQuery();

				integrantes = new ArrayList<TrabajoGrupalIntegrante>();

				while (subResult.next()) {
					integrante = new TrabajoGrupalIntegrante();
					integrante.setNotaOportunidad(subResult.getString("NOTA_OPORTUNIDAD"));
					integrante.setNotaCoherencia(subResult.getString("NOTA_COHERENCIA"));
					integrante.setNotaInnovacion(subResult.getString("NOTA_INNOVACION"));
					integrante.setNotaParticipacion(subResult.getString("NOTA_PARTICIPACION"));
					integrante.setNotaPromedio(subResult.getString("NOTA_PROMEDIO"));
					integrante.setDebates(subResult.getInt("DEBATES"));

					matricula = new Matricula();
					matricula.setIdMatricula(subResult.getString("IDMATRICULA"));
					matricula.setSeccion(subResult.getString("SECCION"));
					matricula.setPaterno(Formato.formatoTexto(subResult.getString("APEPATERNO")));
					matricula.setMaterno(Formato.formatoTexto(subResult.getString("APEMATERNO")));
					matricula.setNombre1(Formato.formatoTexto(subResult.getString("NOMUNO")));
					matricula.setNombre2(Formato.formatoTexto(subResult.getString("NOMDOS")));
					integrante.setUsuarioMatricula(matricula);

					integrantes.add(integrante);
				}
				grupo.setIntegrantes(integrantes);
				grupos.add(grupo);
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
		return grupos;

	}

	public Collection<TrabajoGrupalGrupo> listarGrupos(int idTrabajo) throws DAOException {
		log.info("listarGrupos(int idTrabajo)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Collection<TrabajoGrupalGrupo> grupos = new ArrayList<TrabajoGrupalGrupo>();
		TrabajoGrupalGrupo grupo = null;

		try {
			String query = "SELECT IDGRUPO,NOMBRE FROM CV_GRUPO_TRABAJO WHERE IDTRABAJO=? AND ESTADO=? ORDER BY NOMBRE";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idTrabajo);
			stmt.setInt(2, Constante.ESTADO_ACTIVO);
			result = (ResultSet) stmt.executeQuery();

			while (result.next()) {
				grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(result.getInt("IDGRUPO"));
				grupo.setNombre(result.getString("NOMBRE"));
				grupos.add(grupo);
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
		return grupos;

	}
	
	public TrabajoGrupalGrupo verMensajes(TrabajoGrupalGrupo grupo)
			throws DAOException {
		log.info("verMensajes(TrabajoGrupalGrupo grupo)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		Collection<TrabajoGrupalMensaje> mensajes = new ArrayList<TrabajoGrupalMensaje>();
		TrabajoGrupalMensaje mensaje = null;
		Matricula matricula = null;
		try {
			String query = "SELECT NOMBRE,ARCHIVO_NOMBRE,ARCHIVO_TAMANO,BANDERA, "
					+ "(SELECT COUNT(NVL(NOTA_PROMEDIO,0))-COUNT(NOTA_PROMEDIO) FROM CV_GRUPO_TRABAJO_MATRICULA WHERE IDGRUPO=T.IDGRUPO AND IDTRABAJO=T.IDTRABAJO) NOCALIFICADOS "
					+ "FROM CV_GRUPO_TRABAJO T WHERE IDTRABAJO=? AND IDGRUPO=? AND ESTADO=?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, grupo.getIdTrabajo());
			stmt.setInt(2, grupo.getIdGrupo());
			stmt.setInt(3, Constante.ESTADO_ACTIVO);
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				grupo.setNombre(result.getString("NOMBRE"));
				grupo.setArchivoNombre(result.getString("ARCHIVO_NOMBRE"));
				grupo.setArchivoTamanio(result.getString("ARCHIVO_TAMANO"));
				grupo.setBandera(result.getInt("BANDERA"));
				grupo.setEstado(result.getInt("NOCALIFICADOS"));

				// Lista de Mensajes
				query = "SELECT T.IDMENSAJE,T.DESCRIPCION,T.IDMATRICULA_EMISOR,M.IDROL,T.ARCHIVO_NOMBRE,T.ARCHIVO_TAMANO,"
						+ "P.APEPATERNO,P.APEMATERNO,P.NOMUNO,P.NOMDOS,T.USUARIO_CREACION,T.FECHA_CREACION,T.USUARIO_MOD,T.FECHA_MOD "
						+ "FROM CV_GRUPO_TRABAJO_MSG T, cv_matricula M, SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P "
						+ "WHERE T.IDMATRICULA_EMISOR=M.IDMATRICULA(+) AND U.USUARIO(+)=M.USUARIO AND U.CODSUJETO=P.CODPERSONA(+) "
						+ "AND T.IDTRABAJO=? AND T.IDGRUPO=? ORDER BY T.IDMENSAJE";
				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt.setInt(1, grupo.getIdTrabajo());
				stmt.setInt(2, grupo.getIdGrupo());
				result = (ResultSet) stmt.executeQuery();
				while (result.next()) {
					mensaje = new TrabajoGrupalMensaje();
					mensaje.setIdMensaje(result.getInt("IDMENSAJE"));
					mensaje.setDescripcion(result.getString("DESCRIPCION"));
					mensaje
							.setArchivoNombre(result
									.getString("ARCHIVO_NOMBRE"));
					mensaje.setArchivoTamanio(result
							.getString("ARCHIVO_TAMANO"));
					mensaje.setUsuarioCreacion(result
							.getString("USUARIO_CREACION"));
					mensaje.setUsuarioModificacion(result
							.getString("USUARIO_MOD"));
					mensaje.setFechaCreacion(Formato.getDateDeBaseDatos(result
							.getString("FECHA_CREACION")));
					mensaje.setFechaModificacion(Formato
							.getDateDeBaseDatos(result.getString("FECHA_MOD")));

					matricula = new Matricula();
					matricula.setIdMatricula(result
							.getString("IDMATRICULA_EMISOR"));
					matricula.setRol(result.getInt("IDROL"));
					matricula.setPaterno(Formato.formatoTexto(result
							.getString("APEPATERNO")));
					matricula.setMaterno(Formato.formatoTexto(result
							.getString("APEMATERNO")));
					matricula.setNombre1(Formato.formatoTexto(result
							.getString("NOMUNO")));
					matricula.setNombre2(Formato.formatoTexto(result
							.getString("NOMDOS")));

					mensaje.setUsuarioEmisor(matricula);

					mensajes.add(mensaje);
				}
				grupo.setMensajes(mensajes);
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
		return grupo;
	}

	public int[] obtenerUltimoIdMensaje(TrabajoGrupalGrupo datos)
			throws DAOException {
		log.info("obtenerUltimoIdMensaje(TrabajoGrupalGrupo datos)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		int[] idMensajeEstado = new int[2];

		try {

			String query = "SELECT (SELECT MAX(IDMENSAJE) FROM CV_GRUPO_TRABAJO_MSG WHERE IDTRABAJO=? AND IDGRUPO=?) LASTMESSAGE, "
					+ "NVL((SELECT IDMENSAJE FROM CV_GRUPO_TRABAJO_MSG M WHERE IDTRABAJO=? AND IDGRUPO=? AND IDMATRICULA_EMISOR=? "
					+ "AND IDMENSAJE=(SELECT MAX(IDMENSAJE) FROM CV_GRUPO_TRABAJO_MSG WHERE IDTRABAJO=M.IDTRABAJO AND IDGRUPO=M.IDGRUPO)),0) ACTION "
					+ "FROM DUAL";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, datos.getIdTrabajo());
			stmt.setInt(2, datos.getIdGrupo());
			stmt.setInt(3, datos.getIdTrabajo());
			stmt.setInt(4, datos.getIdGrupo());
			stmt.setInt(5, datos.getEstado());
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				idMensajeEstado[0] = result.getInt("LASTMESSAGE");
				idMensajeEstado[1] = result.getInt("ACTION");
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
		return idMensajeEstado;

	}

	public void nuevoMensaje(TrabajoGrupalGrupo grupo) throws DAOException {
		log.info("nuevoMensaje(TrabajoGrupalGrupo grupo)");
		Connection cons = null;
		PreparedStatement stmt = null;
		
		TrabajoGrupalMensaje mensaje = (TrabajoGrupalMensaje) grupo.getMensajes().iterator().next();

		try {
			cons = (Connection)dataSource.getConnection();

			// *** CV_GRUPO_TRABAJO_MSG

			String query = "INSERT INTO CV_GRUPO_TRABAJO_MSG (IDTRABAJO,IDGRUPO,IDMENSAJE,DESCRIPCION,IDMATRICULA_EMISOR,ARCHIVO_NOMBRE,ARCHIVO_TAMANO,USUARIO_CREACION,USUARIO_MOD,FECHA_CREACION,FECHA_MOD) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE)";

			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, grupo.getIdTrabajo());
			stmt.setInt(2, grupo.getIdGrupo());
			stmt.setInt(3, mensaje.getIdMensaje());
			stmt.setString(4, mensaje.getDescripcion());
			stmt.setString(5, mensaje.getUsuarioEmisor().getIdMatricula());
			stmt.setString(6, mensaje.getArchivoNombre());
			stmt.setString(7, mensaje.getArchivoTamanio());
			stmt.setString(8, mensaje.getUsuarioCreacion());
			stmt.setString(9, mensaje.getUsuarioModificacion());

			if (1 != stmt.executeUpdate()) {
				log
						.error("Error nuevoMensaje(TrabajoGrupalGrupo grupo) - CV_GRUPO_TRABAJO_MSG");
				throw new DAOException("No culmino");
			}

			// *** CV_GRUPO_TRABAJO
			query = "UPDATE CV_GRUPO_TRABAJO SET BANDERA=? WHERE IDTRABAJO=? AND IDGRUPO=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, grupo.getBandera());
			stmt.setInt(2, grupo.getIdTrabajo());
			stmt.setInt(3, grupo.getIdGrupo());

			if (1 != stmt.executeUpdate()) {
				log
						.error("Error en nuevoMensaje(TrabajoGrupalGrupo grupo) - CV_GRUPO_TRABAJO");
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

	public void modificarMensaje(TrabajoGrupalGrupo grupo) throws DAOException {
		log.info("modificarMensaje(TrabajoGrupalGrupo grupo)");
		Connection cons = null;
		PreparedStatement stmt = null;
		
		TrabajoGrupalMensaje mensaje = (TrabajoGrupalMensaje) grupo.getMensajes().iterator().next();

		try {
			cons = (Connection)dataSource.getConnection();

			if (mensaje.getArchivoNombre() != null) {
				String query = "UPDATE CV_GRUPO_TRABAJO_MSG SET DESCRIPCION=?, ARCHIVO_NOMBRE=?, ARCHIVO_TAMANO=?, "
						+ "USUARIO_MOD=?, FECHA_MOD=SYSDATE WHERE IDTRABAJO=? AND IDGRUPO=? AND IDMENSAJE=?";

				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt.setString(1, mensaje.getDescripcion());
				stmt.setString(2, mensaje.getArchivoNombre());
				stmt.setString(3, mensaje.getArchivoTamanio());
				stmt.setString(4, mensaje.getUsuarioModificacion());
				stmt.setInt(5, grupo.getIdTrabajo());
				stmt.setInt(6, grupo.getIdGrupo());
				stmt.setInt(7, mensaje.getIdMensaje());
			} else {
				String query = "UPDATE CV_GRUPO_TRABAJO_MSG SET DESCRIPCION=?, "
						+ "USUARIO_MOD=?, FECHA_MOD=SYSDATE WHERE IDTRABAJO=? AND IDGRUPO=? AND IDMENSAJE=?";

				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt.setString(1, mensaje.getDescripcion());
				stmt.setString(2, mensaje.getUsuarioModificacion());
				stmt.setInt(3, grupo.getIdTrabajo());
				stmt.setInt(4, grupo.getIdGrupo());
				stmt.setInt(5, mensaje.getIdMensaje());
			}

			if (1 != stmt.executeUpdate()) {
				log.error("Error modificarMensaje(TrabajoGrupalGrupo grupo)");
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


	public int obtenerEstadoDebate(int idTrabajo, int idGrupo, int idMatricula) throws DAOException {
		log.info("obtenerEstadoDebate(int idTrabajo, int idGrupo, int idMatricula)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String query = "SELECT COUNT(cvdi.iddebate) DEBATE FROM cv_debate cvdi,cv_debate_matricula cvdima,"
					+ "cv_trabajo_grupal cvtrgr,cv_grupo_trabajo cvgrtr "
					+ "WHERE cvdima.iddebate = cvdi.iddebate AND cvdima.leido = 0 AND cvdi.estado = 1 "
					+ "AND cvgrtr.idtrabajo = cvtrgr.idtrabajo AND cvgrtr.estado =1 AND cvdima.idmatricula = ? "
					+ "AND cvgrtr.idgrupo = cvdi.idgrupo AND cvgrtr.idtrabajo = cvdi.idtrabajo AND cvgrtr.idgrupo = ? "
					+ "AND cvgrtr.idtrabajo =?";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idMatricula);
			stmt.setInt(2, idGrupo);
			stmt.setInt(3, idTrabajo);
			result = (ResultSet) stmt.executeQuery();

			if (result.next()) {
				return result.getInt("DEBATE");
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
		return 0;

	}

}
