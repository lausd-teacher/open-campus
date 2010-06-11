package edu.opencampus.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.modelo.AulaVirtual;
import edu.opencampus.lms.modelo.Debate;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;

public class DebateDAO extends BaseDAO {

	protected DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Debate crearPlactica(AulaVirtual aula, int idTrabajo, Debate modelo) throws DAOException {
		log.info("crearPlactica()");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			
			String  query = "";
			
			cons =  dataSource.getConnection();
			cons.setAutoCommit(false);
			
			if(modelo.getPredecesor() == 0){
				modelo.setOwner(0);
			}else{
				query = "SELECT OWNER FROM cv_debate WHERE IDDEBATE=?";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, modelo.getPredecesor());
				result =  stmt.executeQuery();
				if(result.next()){
					if(result.getInt("OWNER") == 0)
						modelo.setOwner(modelo.getPredecesor());
					else
						modelo.setOwner(result.getInt("OWNER"));
				}
			}
			query = "INSERT INTO cv_debate(idunidad,idficha,idgrupo,idmatricula_envio,asunto,texto,fecha,OWNER,predecesor,estado) " +
					"VALUES (?,?,?,?,?,?,NOW(),?,?,1)";
						
			stmt =  cons.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, idTrabajo);
			stmt.setInt(2, aula.getIdFicha());
			stmt.setInt(3, aula.getDebateActual());
			stmt.setInt(4, aula.getMatriculaActual().getIdMatricula());
			stmt.setString(5, modelo.getAsunto());
			stmt.setString(6, modelo.getTexto());
			stmt.setInt(7, modelo.getOwner());
			stmt.setInt(8, modelo.getPredecesor());
			if (1 != stmt.executeUpdate()) {
				throw new DAOException("DebateDAO: crearPlactica(): Error en cv_debate");
			}
			log.info("cv_debate ok");
			
			result = stmt.getGeneratedKeys();
			result.next();
			Integer id = result.getInt(1);
			
			query = "INSERT INTO cv_debate_matricula(iddebate, idmatricula,leido)  " +
					"SELECT ?,idmatricula,'0' FROM cv_trabajo_grupal_matricula " +
					"WHERE idgrupo=? AND idunidad=? AND idficha=?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.setInt(2, aula.getDebateActual());
			stmt.setInt(3, idTrabajo);
			stmt.setInt(4, aula.getIdFicha());
			stmt.executeUpdate();
			log.info("cv_debate_matricula (alumnos) ok");
			
			query = "INSERT INTO cv_debate_matricula(iddebate,idmatricula,leido) " +
					"SELECT ?,idmatricula,'0' FROM cv_matricula " +
					"WHERE eliminado=0 AND estado=1 AND idrol IN(1,2,3) " +
					"AND idficha=(SELECT idficha FROM cv_trabajo_grupal WHERE idunidad=? AND idficha=?)";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.setInt(2, idTrabajo);
			stmt.setInt(3, aula.getIdFicha());
			stmt.executeUpdate();
			log.info("cv_debate_matricula (docentes) ok");
			
//			query = "SELECT SEQCVDEBATE.CURRVAL,TO_CHAR(SYSDATE,'DD-MM-YYYY HH24:MI') FECHA FROM DUAL";
//			stmt =  cons.prepareStatement(query);
//			result =  stmt.executeQuery();
//			if (result.next()) {
//				modelo.setIdDebate(result.getInt(1));
//				modelo.setFecha(result.getString(2));
//			}
			query = "UPDATE cv_debate_matricula SET LEIDO=1,FECHA=now() " +
					"WHERE IDDEBATE=? AND IDMATRICULA=?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.setInt(2, aula.getMatriculaActual().getIdMatricula());
			if (1 != stmt.executeUpdate()) {
				throw new DAOException("DebateDAO: crearPlactica(): Error en cv_debate_GRUPAL_MATRICULA");
			}
			log.info("cv_debate_matricula update myself ok");
			cons.commit();
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (DAOException e) {
			log.error(e);
			throw e;
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			try {
				cons.rollback();
			} catch (SQLException e) {
			}
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return modelo;
	}

	public void eliminarPlacticaSub(String usuario, int idDebate,
			Connection conec) throws DAOException {
		log.info("eliminarPlacticaSub()");
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String query = "UPDATE cv_debate SET estado=0 "
					+ " WHERE IDDEBATE=?";
			stmt =  conec.prepareStatement(query);
			stmt.setInt(1, idDebate);
			if (1 != stmt.executeUpdate()) {
				throw new DAOException(
						"DebateDAO: eliminarPlacticaSub(): Error");
			}
			query = "SELECT IDDEBATE from cv_debate where predecesor=?";
			stmt =  conec.prepareStatement(query);
			stmt.setInt(1, idDebate);
			result =  stmt.executeQuery();
			while (result.next()) {
				eliminarPlacticaSub(usuario, result.getInt("IDDEBATE"), conec);
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
		}
	}

	public void eliminarPlactica(String idUsuario, int idDebate)
			throws DAOException {
		log.info("eliminarPlactica(): " + idUsuario);
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = "UPDATE cv_debate SET estado=0 "
					+ " WHERE IDDEBATE=?";
			cons =  dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idDebate);
			if (1 != stmt.executeUpdate()) {
				throw new DAOException("DebateDAO: eliminarPlactica(): Error");
			}
			query = "SELECT IDDEBATE from cv_debate where predecesor=?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idDebate);
			result =  stmt.executeQuery();
			while (result.next()) {
				eliminarPlacticaSub(idUsuario, result.getInt("IDDEBATE"), cons);
			}
			cons.commit();
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
			try {
				cons.rollback();
			} catch (SQLException e) {
			}
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
	}


	public List<Debate> obtenerPlactica(AulaVirtual aula, int idTrabajo)
			throws DAOException {
		log.info("obtenerPlactica(AulaVirtual aula, int idUnidad)");
		List<Debate> list = new ArrayList<Debate>();
		Debate dia;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = "SELECT cvdi.iddebate, cvdi.asunto, cvdi.texto, cvdi.fecha ,cvdi.owner, cvdi.predecesor,cvdi.estado,cvdima.leido,cvdima.importante, " +
					"gen.nomuno,gen.nomdos,gen.apepaterno,gen.apematerno,seu.idusuario,cvm.idrol, " +
					"(SELECT COUNT(*) FROM cv_debate cdg, cv_debate_matricula cdgm " +
					"WHERE cdg.owner=cvdi.iddebate AND cdg.iddebate=cdgm.iddebate AND cdgm.idmatricula=cvdima.idmatricula AND cdg.estado='1') subplactica," +
					"(SELECT COUNT(*) FROM cv_debate cdg,cv_debate_matricula cdgm " +
					"WHERE cdg.owner=cvdi.iddebate AND cdg.iddebate=cdgm.iddebate AND cdgm.idmatricula=cvdima.idmatricula AND cdg.estado='1' AND cdgm.leido='0') nosubplactica " +
					"FROM cv_debate cvdi,cv_debate_matricula cvdima,cv_persona gen,cv_usuario seu,cv_matricula cvm " +
					"WHERE cvm.idmatricula=cvdi.idmatricula_envio AND cvm.idusuario=seu.idusuario AND seu.idusuario=gen.idpersona AND cvdi.estado='1' AND cvdi.iddebate=cvdima.iddebate " +
					"AND cvdima.idmatricula=? AND cvdi.owner=0 AND cvdi.predecesor=0 AND cvdi.idunidad=? AND cvdi.idficha=? AND cvdi.idgrupo=? ORDER BY fecha desc";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, aula.getMatriculaActual().getIdMatricula());
			stmt.setInt(2, idTrabajo);
			stmt.setInt(3, aula.getIdFicha());
			stmt.setInt(4, aula.getDebateActual());
			result =  stmt.executeQuery();

			while (result.next()) {
				dia = new Debate();
				dia.setAsunto(result.getString("asunto"));
				dia.setFecha(Formato.calendarToString(Formato.timestampToCalendar(result.getString("fecha")),Constante.FECHA_DIA_MES_ANO_HORA_MI));
				dia.setIdDebate(result.getInt("iddebate"));
				dia.setLeido(result.getInt("leido"));
				dia.setNombreUsuario(Formato.formatoTexto(result
						.getString("nomuno"))
						+ " "
						+ Formato.formatoTexto(result.getString("apepaterno")));
				if (Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE == result
						.getInt("idrol")) {
					dia.setNombreUsuario("Docente : " + dia.getNombreUsuario());

				}
				dia.setOwner(result.getInt("owner"));
				dia.setPredecesor(result.getInt("predecesor"));
				dia.setCantidadSubPlactica(result.getInt("subplactica"));
				dia.setCantidadNoSubPlacticas(result.getInt("nosubplactica"));
				list.add(dia);
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
		return list;
	}

	public List<Debate> obtenerSubPlactica(AulaVirtual aula,
			int idDebate) throws DAOException {
		log
				.info("obtenerSubPlactica(AulaVirtual aula, int idDebate) - idMatricula: "
						+ aula.getMatriculaActual().getIdMatricula() + " idDebate: " + idDebate);
		List<Debate> list = new ArrayList<Debate>();
		Debate dia;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = "SELECT cvdi.iddebate,cvdi.fecha, " +
					"IFNULL(cvdi.asunto,(SELECT 're: '||asunto FROM cv_debate WHERE iddebate=cvdi.owner)) asunto," +
					"cvdi.owner,cvdi.predecesor,cvdima.leido," +
					"gen.nomuno,gen.nomdos,gen.apepaterno,gen.apematerno,seu.usuario,cvm.idrol," +
					"(SELECT COUNT(*) FROM cv_debate WHERE predecesor=cvdi.iddebate AND estado=1) subplactica " +
					"FROM cv_matricula cvm,cv_debate cvdi,cv_debate_matricula cvdima," +
					"cv_persona gen,cv_usuario seu " +
					"WHERE cvm.idmatricula=cvdi.idmatricula_envio AND cvm.idusuario=seu.idusuario AND seu.idusuario=gen.idpersona " +
					"AND cvdi.estado='1' AND cvdi.iddebate=cvdima.iddebate " +
					"AND cvdima.idmatricula=? AND cvdi.predecesor=? ORDER BY cvdi.iddebate";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, aula.getMatriculaActual().getIdMatricula());
			stmt.setInt(2, idDebate);
			result =  stmt.executeQuery();
			while (result.next()) {
				dia = new Debate();
				dia.setFecha(Formato.calendarToString(Formato.timestampToCalendar(result.getString("fecha")),Constante.FECHA_DIA_MES_ANO_HORA_MI));
				dia.setAsunto(result.getString("asunto"));
				dia.setIdDebate(result.getInt("IDDEBATE"));
				dia.setLeido(result.getInt("leido"));
				dia.setNombreUsuario(Formato.formatoTexto(result
						.getString("nomuno"))
						+ " "
						+ Formato.formatoTexto(result.getString("apepaterno")));
				if (Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE == result
						.getInt("idrol")) {
					dia.setNombreUsuario("Docente : " + dia.getNombreUsuario());

				}
				dia.setOwner(result.getInt("owner"));
				dia.setPredecesor(result.getInt("predecesor"));
				dia.setCantidadSubPlactica(result.getInt("subplactica"));
				list.add(dia);
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
		return list;
	}

	public Debate obtenerDebate(AulaVirtual aula, int idDebate,
			boolean soloTexto) throws DAOException {
		log.info("obtenerDebate(AulaVirtual aula, int idDebate, boolean soloTexto)");
		Debate dia = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = null;

			if (!soloTexto) {
				query = "SELECT cvdi.iddebate,cvdi.asunto,cvdi.texto,cvdi.fecha,cvdi.owner,cvdi.predecesor,cvdima.leido," +
						"gen.nomuno,gen.apepaterno,seu.usuario,cvm.idrol  " +
						"FROM cv_matricula cvm,cv_debate cvdi,cv_debate_matricula cvdima, " +
						"cv_persona gen,cv_usuario seu " +
						"WHERE cvm.idmatricula=cvdi.idmatricula_envio AND cvm.idusuario=seu.idusuario AND seu.idusuario=gen.idpersona " +
						"AND cvdi.estado=1 AND cvdi.iddebate=cvdima.iddebate " +
						"AND cvdima.idmatricula=? AND cvdi.iddebate=?";
			} else {
				query = "SELECT cvdi.texto " +
						"FROM cv_debate cvdi,cv_debate_matricula cvdima " +
						"WHERE cvdi.estado=1 AND cvdi.iddebate=cvdima.iddebate AND cvdima.idmatricula=? AND cvdi.iddebate=?";
			}
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, aula.getMatriculaActual().getIdMatricula());
			stmt.setInt(2, idDebate);
			result =  stmt.executeQuery();
			if (result.next()) {
				dia = new Debate();
				dia.setTexto(result.getString("TEXTO"));
				if (!soloTexto) {
					dia.setIdDebate(result.getInt("IDDEBATE"));
					dia.setAsunto(result.getString("ASUNTO"));
					dia.setFecha(Formato.calendarToString(Formato.timestampToCalendar(result.getString("fecha")),Constante.FECHA_DIA_MES_ANO_HORA_MI));
					dia.setOwner(result.getInt("OWNER"));
					dia.setPredecesor(result.getInt("PREDECESOR"));
					dia.setLeido(result.getInt("LEIDO"));
					dia.setNombreUsuario(Formato.formatoTexto(result
							.getString("NOMUNO"))
							+ " "
							+ Formato.formatoTexto(result
									.getString("APEPATERNO")));
					if (Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE == result
							.getInt("IDROL")) {
						dia.setNombreUsuario("Docente : "
								+ dia.getNombreUsuario());

					}
				}

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
		return dia;
	}

	public void leerPlactica(int idDebate, int idMatricula, int estado)
			throws DAOException {
		log.info("leerPlactica() - idDebate: "+idDebate + " - idMatricula: "+idMatricula + " - estado: "+estado);
		PreparedStatement stmt = null;
		Connection cons = null;
		try {
			String query = "UPDATE cv_debate_matricula SET LEIDO=?,FECHA=NOW() "
					+ "WHERE IDDEBATE=? AND IDMATRICULA=?";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, estado);
			stmt.setInt(2, idDebate);
			stmt.setInt(3, idMatricula);
			if (1 != stmt.executeUpdate()) {
				throw new DAOException("DebateDAO: leerPlactica(): Error");
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (DAOException e) {
			log.error(e);
			throw e;
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
}
