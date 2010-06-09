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
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.modelo.tgrupal.TrabajoGrupalGrupo;
import edu.opencampus.lms.modelo.tgrupal.TrabajoGrupalIntegrante;
import edu.opencampus.lms.modelo.tgrupal.TrabajoGrupalMensaje;
import edu.opencampus.lms.modelo.usuario.Persona;
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
			String query = "SELECT t.idunidad, t.idficha,t.fecha_activacion,t.fecha_entrega,t.descripcion,t.idmatricula_envio,d.nombre,p.apepaterno,p.apematerno,p.nomuno,p.nomdos  " +
					"FROM cv_trabajo_grupal t, cv_unidad d, cv_matricula m, cv_usuario u, cv_persona p " +
					"WHERE t.idmatricula_envio=m.idmatricula AND m.idusuario=u.idusuario AND u.idusuario=p.idpersona " +
					"AND t.idunidad=d.idunidad AND t.idficha=? AND t.idunidad=?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, tg.getIdFicha());
			stmt.setInt(2, tg.getIdUnidad());
			result =  stmt.executeQuery();
			if (result.next()) {
				tg.setIdTrabajo(tg.getIdUnidad());
				tg.setFechaActivacion(Formato.getDateDeBaseDatos(result.getString("fecha_activacion")));
				tg.setFechaEntrega(Formato.getDateDeBaseDatos(result.getString("fecha_entrega")));
				tg.setDescripcion(result.getString("DESCRIPCION"));
				tg.setNombreUnidad(result.getString("nombre"));
				Matricula publicador = new Matricula();
				publicador.setIdMatricula(result.getInt("idmatricula_envio"));
				
				Usuario usuario = new Usuario();
				Persona persona = new Persona();
				persona.setApepaterno(Formato.formatoTexto(result.getString("APEPATERNO")));
				persona.setApematerno(Formato.formatoTexto(result.getString("APEMATERNO")));
				persona.setNomuno(Formato.formatoTexto(result.getString("NOMUNO")));
				persona.setNomdos(Formato.formatoTexto(result.getString("NOMDOS")));
				usuario.setPersona(persona);
				publicador.setUsuario(usuario);
				
				tg.setPublicador(publicador);
			}else{
				try{
				query = "INSERT INTO cv_trabajo_grupal(idunidad, idficha) VALUE(?,?)";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, tg.getIdUnidad());
				stmt.setInt(2, tg.getIdFicha());
				stmt.executeUpdate();
				}catch (Exception e) {}
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
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, tg.getIdTrabajo());
			result =  stmt.executeQuery();
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
//				publicador.setIdMatricula(result.getString("PUBLICADOR"));
//				publicador.setPaterno(Formato.formatoTexto(result.getString("APEPATERNO")));
//				publicador.setMaterno(Formato.formatoTexto(result.getString("APEMATERNO")));
//				publicador.setNombre1(Formato.formatoTexto(result.getString("NOMUNO")));
//				publicador.setNombre2(Formato.formatoTexto(result.getString("NOMDOS")));
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
	
	public int obtenerIdGrupo(TrabajoGrupal tg, int idMatricula) throws DAOException {
		log.info("obtenerIdGrupo("+tg+", "+idMatricula+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {

			String query = "SELECT NVL((SELECT M.IDGRUPO FROM CV_GRUPO_TRABAJO_MATRICULA M, CV_GRUPO_TRABAJO G WHERE M.IDTRABAJO=G.IDTRABAJO AND M.IDGRUPO=G.IDGRUPO AND M.IDTRABAJO=? AND M.IDMATRICULA=? AND G.ESTADO=?),-1) IDGRUPO FROM DUAL";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, tg.getIdTrabajo());
			stmt.setInt(2, idMatricula);
			stmt.setInt(3, Constante.ESTADO_ACTIVO);
			result =  stmt.executeQuery();
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

	public Collection<Matricula> verIntegrantes(TrabajoGrupal tg, int idMatricula) throws DAOException {
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
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, tg.getIdTrabajo());
			stmt.setInt(2, idMatricula);
			result =  stmt.executeQuery();
			while (result.next()) {
				colega = new Matricula();
//				colega.setPaterno(Formato.formatoTexto(result.getString("APEPATERNO")));
//				colega.setMaterno(Formato.formatoTexto(result.getString("APEMATERNO")));
//				colega.setNombre1(Formato.formatoTexto(result.getString("NOMUNO")));
//				colega.setNombre2(Formato.formatoTexto(result.getString("NOMDOS")));
				
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
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, grupo.getIdTrabajo());
			stmt.setInt(2, grupo.getIdGrupo());
			result =  stmt.executeQuery();

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
			String query = "UPDATE cv_trabajo_grupal_grupo SET archivo_nombre=?, archivo_tamanio=? " +
					"WHERE idunidad=? AND idficha=? AND idgrupo=?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, grupo.getArchivoNombre());
			stmt.setString(2, grupo.getArchivoTamanio());
			stmt.setInt(3, grupo.getIdTrabajo());
			stmt.setInt(4, grupo.getIdFicha());
			stmt.setInt(5, grupo.getIdGrupo());

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

	public void calificarTrabajo(TrabajoGrupal tg,TrabajoGrupalIntegrante integrante) throws DAOException {
		log.info("calificarTrabajo(int tg,TrabajoGrupalIntegrante integrante)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "UPDATE CV_GRUPO_TRABAJO_MATRICULA SET USUARIO_MOD=?, FECHA_MOD=SYSDATE, NOTA_OPORTUNIDAD=?, "
					+ "NOTA_COHERENCIA=?, NOTA_INNOVACION=?, NOTA_PARTICIPACION=?, NOTA_PROMEDIO=? "
					+ "WHERE IDTRABAJO=? AND IDMATRICULA=?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, integrante.getUsuarioModificacion());
			stmt.setString(2, integrante.getNotaOportunidad());
			stmt.setString(3, integrante.getNotaCoherencia());
			stmt.setString(4, integrante.getNotaInnovacion());
			stmt.setString(5, integrante.getNotaParticipacion());
			stmt.setString(6, integrante.getNotaPromedio());
			stmt.setInt(7, tg.getIdTrabajo());
			stmt.setInt(8, integrante.getUsuarioMatricula().getIdMatricula());

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

	public void publicarTrabajo(TrabajoGrupal trabajo) throws DAOException {
		log.info("publicarTrabajo("+trabajo.getIdTrabajo()+","+trabajo.getIdFicha()+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			cons = dataSource.getConnection();
			cons.setAutoCommit(false);
			String query = "UPDATE cv_trabajo_grupal SET IDMATRICULA_ENVIO=?, fecha_activacion=?, fecha_entrega=?, descripcion=? " +
					"WHERE idunidad=? AND idficha=?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, trabajo.getPublicador().getIdMatricula());
			stmt.setString(2, Formato.calendarToTimestamp(trabajo.getFechaActivacion()));
			stmt.setString(3, Formato.calendarToTimestamp(trabajo.getFechaEntrega()));
			stmt.setString(4, trabajo.getDescripcion());
			stmt.setInt(5, trabajo.getIdTrabajo());
			stmt.setInt(6, trabajo.getIdFicha());
			int n = stmt.executeUpdate();
			if (1 != n) {
				log.error("Error en publicarTrabajo(TrabajoGrupal trabajo) - Row Updates: "+n);
				throw new DAOException("No culmino");
			}

			query = "UPDATE cv_trabajo_grupal_grupo SET bandera=? WHERE idunidad=? AND idficha=? AND bandera=?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, Constante.FLAG_PENDIENTE_ESTUDIANTE);
			stmt.setInt(2, trabajo.getIdTrabajo());
			stmt.setInt(3, trabajo.getIdFicha());
			stmt.setInt(4, Constante.FLAG_NO_INICIADO);
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

			String query = "SELECT m.idmatricula,m.idrol,m.idusuario,p.apepaterno,p.apematerno,p.nomuno,p.nomdos  " +
					"FROM cv_matricula m, cv_usuario u, cv_persona p " +
					"WHERE u.idusuario=m.idusuario AND u.idusuario=p.idpersona AND m.idrol=4 AND m.idficha=? " +
					"AND m.eliminado='0' AND m.estado='1' " +
					"ORDER BY p.apepaterno,p.apematerno,p.nomuno";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idFicha);
			result =  stmt.executeQuery();
			while (result.next()) {
				estudiante = new Matricula();
				estudiante.setIdMatricula(result.getInt("IDMATRICULA"));

				Usuario usuario = new Usuario();
				Persona persona = new Persona();
				persona.setApepaterno(Formato.formatoTexto(result.getString("APEPATERNO")));
				persona.setApematerno(Formato.formatoTexto(result.getString("APEMATERNO")));
				persona.setNomuno(Formato.formatoTexto(result.getString("NOMUNO")));
				persona.setNomdos(Formato.formatoTexto(result.getString("NOMDOS")));
				usuario.setPersona(persona);
				estudiante.setUsuario(usuario);

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
			String query = "SELECT  " +
					"(SELECT IFNULL(MAX(idgrupo),0)+1 FROM cv_trabajo_grupal_grupo WHERE idunidad=? AND idficha=?) id,  " +
					"(SELECT fecha_activacion FROM cv_trabajo_grupal WHERE idunidad=? AND idficha=?) flag;";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, grupo.getIdTrabajo());
			stmt.setInt(2, grupo.getIdFicha());
			stmt.setInt(3, grupo.getIdTrabajo());
			stmt.setInt(4, grupo.getIdFicha());
			result =  stmt.executeQuery();
			if (result.next()) {
				grupo.setIdGrupo(result.getInt("ID"));
				if (result.getString("FLAG") == null)
					grupo.setBandera(Constante.FLAG_NO_INICIADO);
				else
					grupo.setBandera(Constante.FLAG_PENDIENTE_ESTUDIANTE);
			}
			
			query = "INSERT INTO cv_trabajo_grupal_grupo (idunidad,idficha,idgrupo,nombre,estado,bandera) VALUES (?,?,?,?,?,?)";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, grupo.getIdTrabajo());
			stmt.setInt(2, grupo.getIdFicha());
			stmt.setInt(3, grupo.getIdGrupo());
			stmt.setString(4, grupo.getNombre());
			stmt.setInt(5, grupo.getEstado());
			stmt.setInt(6, grupo.getBandera());
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
			String query = "INSERT INTO cv_trabajo_grupal_matricula (idgrupo,idunidad,idficha,idmatricula,modificado_por,modificado_en) " +
					"VALUES (?,?,?,?,?,NOW())";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, grupo.getIdGrupo());
			stmt.setInt(2, grupo.getIdTrabajo());
			stmt.setInt(3, grupo.getIdFicha());
			stmt.setInt(4, idMatricula);
			stmt.setString(5, grupo.getUsuarioCreacion());
			if (1 != stmt.executeUpdate()) {
				log.error("Error en asignarGrupo(TrabajoGrupalGrupo grupo, int idMatricula)");
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
			String query = "DELETE FROM cv_trabajo_grupal_matricula WHERE idunidad=? AND idficha=? AND idgrupo=? AND idmatricula=?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, grupo.getIdTrabajo());
			stmt.setInt(2, grupo.getIdFicha());
			stmt.setInt(3, grupo.getIdGrupo());
			stmt.setInt(4, idMatricula);
			if (1 != stmt.executeUpdate()) {
				log.error("Error en desasignarGrupo(TrabajoGrupalGrupo grupo, int idMatricula)");
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
			String query = "UPDATE cv_trabajo_grupal_grupo SET nombre=? WHERE idunidad=? AND idficha=? AND idgrupo=?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, grupo.getNombre());
			stmt.setInt(2, grupo.getIdTrabajo());
			stmt.setInt(3, grupo.getIdFicha());
			stmt.setInt(4, grupo.getIdGrupo());
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
			String query = "DELETE FROM cv_trabajo_grupal_matricula WHERE idunidad=? AND idficha=? AND idgrupo=?";
			cons = dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, grupo.getIdTrabajo());
			stmt.setInt(2, grupo.getIdFicha());
			stmt.setInt(3, grupo.getIdGrupo());
			stmt.executeUpdate();

			query = "UPDATE cv_trabajo_grupal_grupo SET estado=? WHERE idunidad=? AND idficha=? AND idgrupo=?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, grupo.getEstado());
			stmt.setInt(2, grupo.getIdTrabajo());
			stmt.setInt(3, grupo.getIdFicha());
			stmt.setInt(4, grupo.getIdGrupo());
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
	
	public void eliminarGrupos(TrabajoGrupal tg) throws DAOException {
		log.info("eliminarGrupos(int idTrabajo)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "delete from cv_trabajo_grupal_matricula where idunidad=? AND idficha=?";
			cons = dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, tg.getIdTrabajo());
			stmt.setInt(2, tg.getIdFicha());
			stmt.executeUpdate();
			
			query = "delete from cv_trabajo_grupal_mensaje where idunidad=? AND idficha=?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, tg.getIdTrabajo());
			stmt.setInt(2, tg.getIdFicha());
			stmt.executeUpdate();

			query = "delete from cv_trabajo_grupal_grupo where idunidad=? AND idficha=?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, tg.getIdTrabajo());
			stmt.setInt(2, tg.getIdFicha());
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
			int idunidad) throws DAOException {
		log.info("obtenerGrupos(int "+idFicha+",int "+idunidad+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Collection<TrabajoGrupalGrupo> grupos = new ArrayList<TrabajoGrupalGrupo>();
		TrabajoGrupalGrupo grupo = null;
		Collection<TrabajoGrupalIntegrante> integrantes = null;
		TrabajoGrupalIntegrante integrante = null;
		Matricula matricula = null;

		try {
			String query = "SELECT idgrupo,nombre FROM cv_trabajo_grupal_grupo WHERE idficha=? AND idunidad=? AND estado=1 ORDER BY nombre";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idFicha);
			stmt.setInt(2, idunidad);
			result =  stmt.executeQuery();

			query = "SELECT g.idmatricula,m.idusuario,p.apepaterno,p.apematerno,p.nomuno,p.nomdos " +
					"FROM cv_trabajo_grupal_matricula g, cv_matricula m, cv_usuario u, cv_persona p " +
					"WHERE g.idmatricula=m.idmatricula AND u.idusuario=m.idusuario AND u.idusuario=p.idpersona " +
					"AND g.idficha=? AND idunidad=? AND g.idgrupo=?  " +
					"AND m.eliminado='0' AND m.estado='1' ORDER BY p.apepaterno,p.apematerno,p.nomuno";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idFicha);
			stmt.setInt(2, idunidad);
			ResultSet subResult = null;

			while (result.next()) {
				grupo = new TrabajoGrupalGrupo();
				grupo.setIdGrupo(result.getInt("IDGRUPO"));
				grupo.setNombre(result.getString("NOMBRE"));

				stmt.setInt(3, result.getInt("IDGRUPO"));
				subResult =  stmt.executeQuery();

				integrantes = new ArrayList<TrabajoGrupalIntegrante>();

				while (subResult.next()) {
					integrante = new TrabajoGrupalIntegrante();
					matricula = new Matricula();
					matricula.setIdMatricula(subResult.getInt("IDMATRICULA"));

					Usuario usuario = new Usuario();
					Persona persona = new Persona();
					persona.setApepaterno(Formato.formatoTexto(subResult.getString("APEPATERNO")));
					persona.setApematerno(Formato.formatoTexto(subResult.getString("APEMATERNO")));
					persona.setNomuno(Formato.formatoTexto(subResult.getString("NOMUNO")));
					persona.setNomdos(Formato.formatoTexto(subResult.getString("NOMDOS")));
					usuario.setPersona(persona);
					matricula.setUsuario(usuario);
					
					integrante.setUsuarioMatricula(matricula);

					integrantes.add(integrante);
				}
				grupo.setIntegrantes(integrantes);
				grupos.add(grupo);
			}

			query = "SELECT m.idmatricula,m.idusuario,p.apepaterno,p.apematerno,p.nomuno,p.nomdos  " +
					"FROM cv_matricula m, cv_usuario u, cv_persona p  " +
					"WHERE u.idusuario=m.idusuario AND u.idusuario=p.idpersona AND m.idrol=4  " +
					"AND m.idficha=1  " +
					"AND idmatricula NOT IN (SELECT m.idmatricula FROM cv_trabajo_grupal_grupo g, cv_trabajo_grupal_matricula m  " +
					"WHERE g.idficha=m.idficha AND g.IDUNIDAD=m.idunidad AND g.idgrupo=m.idgrupo  " +
					"AND g.idficha=? AND g.idunidad=? AND g.estado='1')  " +
					"AND m.eliminado='0' AND m.estado='1' ORDER BY p.apepaterno,p.apematerno,p.nomuno";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idFicha);
			stmt.setInt(2, idunidad);
			result =  stmt.executeQuery();

			grupo = new TrabajoGrupalGrupo();
			grupo.setIdGrupo(0);

			integrantes = new ArrayList<TrabajoGrupalIntegrante>();
			while (result.next()) {
				integrante = new TrabajoGrupalIntegrante();
				matricula = new Matricula();
				matricula.setIdMatricula(result.getInt("IDMATRICULA"));

				Usuario usuario = new Usuario();
				Persona persona = new Persona();
				persona.setApepaterno(Formato.formatoTexto(result.getString("APEPATERNO")));
				persona.setApematerno(Formato.formatoTexto(result.getString("APEMATERNO")));
				persona.setNomuno(Formato.formatoTexto(result.getString("NOMUNO")));
				persona.setNomdos(Formato.formatoTexto(result.getString("NOMDOS")));
				usuario.setPersona(persona);
				matricula.setUsuario(usuario);
				
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

	public Collection<TrabajoGrupalGrupo> obtenerGruposParaEvaluacion(TrabajoGrupal tg, int idMatricula) throws DAOException {
		log.info("obtenerGruposParaEvaluacion("+tg.getIdTrabajo()+","+tg.getIdFicha()+", int "+idMatricula+")");
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
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE);
			stmt.setInt(2, idMatricula);
			stmt.setInt(3, tg.getIdTrabajo());
			stmt.setInt(4, Constante.ESTADO_ACTIVO);
			result =  stmt.executeQuery();

			query = "SELECT G.IDMATRICULA,G.NOTA_OPORTUNIDAD,NOTA_COHERENCIA,NOTA_INNOVACION,NOTA_PARTICIPACION,NOTA_PROMEDIO,"
					+ "PKG_CV_UTIL.fx_cv_string_number(M.SECCION) SECCION, P.APEPATERNO,P.APEMATERNO,P.NOMUNO,P.NOMDOS," 
					+ "(SELECT COUNT(*) DEBATE_TOTAL FROM cv_debate WHERE ESTADO='1' AND IDTRABAJO=G.IDTRABAJO AND IDGRUPO=G.IDGRUPO AND IDMATRICULA=G.IDMATRICULA) DEBATES "
					+ "FROM CV_GRUPO_TRABAJO_MATRICULA G, cv_matricula M, SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P "
					+ "WHERE G.IDMATRICULA=M.IDMATRICULA(+) AND U.USUARIO(+)=M.USUARIO AND U.CODSUJETO=P.CODPERSONA(+) AND G.IDTRABAJO=? AND G.IDGRUPO=? "
					+ "AND M.ELIMINADO='0' AND M.ESTADO='1' ORDER BY P.APEPATERNO,P.APEMATERNO,P.NOMUNO";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, tg.getIdTrabajo());
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
				subResult =  stmt.executeQuery();

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
//					matricula.setIdMatricula(subResult.getString("IDMATRICULA"));
//					matricula.setSeccion(subResult.getString("SECCION"));
//					matricula.setPaterno(Formato.formatoTexto(subResult.getString("APEPATERNO")));
//					matricula.setMaterno(Formato.formatoTexto(subResult.getString("APEMATERNO")));
//					matricula.setNombre1(Formato.formatoTexto(subResult.getString("NOMUNO")));
//					matricula.setNombre2(Formato.formatoTexto(subResult.getString("NOMDOS")));
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

	public Collection<TrabajoGrupalGrupo> listarGrupos(TrabajoGrupal tg) throws DAOException {
		log.info("listarGrupos(int idTrabajo)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Collection<TrabajoGrupalGrupo> grupos = new ArrayList<TrabajoGrupalGrupo>();
		TrabajoGrupalGrupo grupo = null;

		try {
			String query = "SELECT idgrupo,nombre FROM cv_trabajo_grupal_grupo WHERE idunidad=? AND idficha=? AND estado=1 ORDER BY nombre";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, tg.getIdTrabajo());
			stmt.setInt(2, tg.getIdFicha());
			result =  stmt.executeQuery();

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
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, grupo.getIdTrabajo());
			stmt.setInt(2, grupo.getIdGrupo());
			stmt.setInt(3, Constante.ESTADO_ACTIVO);
			result =  stmt.executeQuery();
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
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, grupo.getIdTrabajo());
				stmt.setInt(2, grupo.getIdGrupo());
				result =  stmt.executeQuery();
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
//					matricula.setIdMatricula(result.getString("IDMATRICULA_EMISOR"));
//					matricula.setRol(result.getInt("IDROL"));
//					matricula.setPaterno(Formato.formatoTexto(result.getString("APEPATERNO")));
//					matricula.setMaterno(Formato.formatoTexto(result.getString("APEMATERNO")));
//					matricula.setNombre1(Formato.formatoTexto(result.getString("NOMUNO")));
//					matricula.setNombre2(Formato.formatoTexto(result.getString("NOMDOS")));

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
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, datos.getIdTrabajo());
			stmt.setInt(2, datos.getIdGrupo());
			stmt.setInt(3, datos.getIdTrabajo());
			stmt.setInt(4, datos.getIdGrupo());
			stmt.setInt(5, datos.getEstado());
			result =  stmt.executeQuery();
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
			cons = dataSource.getConnection();

			// *** CV_GRUPO_TRABAJO_MSG

			String query = "INSERT INTO CV_GRUPO_TRABAJO_MSG (IDTRABAJO,IDGRUPO,IDMENSAJE,DESCRIPCION,IDMATRICULA_EMISOR,ARCHIVO_NOMBRE,ARCHIVO_TAMANO,USUARIO_CREACION,USUARIO_MOD,FECHA_CREACION,FECHA_MOD) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE)";

			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, grupo.getIdTrabajo());
			stmt.setInt(2, grupo.getIdGrupo());
			stmt.setInt(3, mensaje.getIdMensaje());
			stmt.setString(4, mensaje.getDescripcion());
			stmt.setInt(5, mensaje.getUsuarioEmisor().getIdMatricula());
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
			stmt =  cons.prepareStatement(query);
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
			cons = dataSource.getConnection();

			if (mensaje.getArchivoNombre() != null) {
				String query = "UPDATE CV_GRUPO_TRABAJO_MSG SET DESCRIPCION=?, ARCHIVO_NOMBRE=?, ARCHIVO_TAMANO=?, "
						+ "USUARIO_MOD=?, FECHA_MOD=SYSDATE WHERE IDTRABAJO=? AND IDGRUPO=? AND IDMENSAJE=?";

				stmt =  cons.prepareStatement(query);
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

				stmt =  cons.prepareStatement(query);
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
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idMatricula);
			stmt.setInt(2, idGrupo);
			stmt.setInt(3, idTrabajo);
			result =  stmt.executeQuery();

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
