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

	public Debate crearPlactica(AulaVirtual aula, int idGrupo, Debate modelo) throws DAOException {
		log.info("crearPlactica()");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			
			String  query = "";
			
			cons = (Connection) dataSource.getConnection();
			cons.setAutoCommit(false);
			
			if(modelo.getPredecesor() == 0){
				modelo.setOwner(0);
			}else{
				query = "SELECT OWNER FROM cv_debate WHERE IDDEBATE=?";
				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt.setInt(1, modelo.getPredecesor());
				result = (ResultSet) stmt.executeQuery();
				if(result.next()){
					if(result.getInt("OWNER") == 0)
						modelo.setOwner(modelo.getPredecesor());
					else
						modelo.setOwner(result.getInt("OWNER"));
				}
			}
			query = "INSERT INTO cv_debate(IDDEBATE,IDTRABAJO,"
					+ "IDGRUPO,IDMATRICULA,ASUNTO,TEXTO,FECHA,OWNER,PREDECESOR,"
					+ "ESTADO) VALUES (SEQCVDEBATE.NEXTVAL,?,?,?,?,?,SYSDATE,?,?,1)";
						
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, aula.getTrabajoGrupalActual());
			stmt.setInt(2, idGrupo);
			stmt.setInt(3, aula.getIdMatricula());
			stmt.setString(4, modelo.getAsunto());
			stmt.setString(5, modelo.getTexto());
			stmt.setInt(6, modelo.getOwner());
			stmt.setInt(7, modelo.getPredecesor());
			if (1 != stmt.executeUpdate()) {
				throw new DAOException("DebateDAO: crearPlactica(): Error en cv_debate");
			}
			query = "INSERT INTO cv_debate_matricula(IDDEBATE, IDMATRICULA,LEIDO) " +
					"SELECT SEQCVDEBATE.CURRVAL,IDMATRICULA,'0' FROM CV_GRUPO_TRABAJO_MATRICULA " +
					"WHERE IDGRUPO=? AND IDTRABAJO=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idGrupo);
			stmt.setInt(2, aula.getTrabajoGrupalActual());
			stmt.executeUpdate();
			query = "INSERT INTO cv_debate_matricula(IDDEBATE,IDMATRICULA,LEIDO) " +
					"SELECT SEQCVDEBATE.CURRVAL,IDMATRICULA,'0' FROM cv_matricula " +
					"WHERE ELIMINADO=0 AND ESTADO=1 AND IDROL IN(1,2,3) " +
					"AND IDFICHA=(SELECT IDFICHA FROM cv_trabajo_grupal WHERE IDTRABAJO=?)";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, aula.getTrabajoGrupalActual());
			stmt.executeUpdate();
			query = "SELECT SEQCVDEBATE.CURRVAL,TO_CHAR(SYSDATE,'DD-MM-YYYY HH24:MI') FECHA FROM DUAL";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				modelo.setIdDebate(result.getInt(1));
				modelo.setFecha(result.getString(2));
			}
			query = "UPDATE cv_debate_matricula SET LEIDO=1,FECHA=SYSDATE " +
					"WHERE IDDEBATE=? AND IDMATRICULA=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, modelo.getIdDebate());
			stmt.setInt(2, aula.getIdMatricula());
			if (1 != stmt.executeUpdate()) {
				throw new DAOException("DebateDAO: crearPlactica(): Error en cv_debate_GRUPAL_MATRICULA");
			}
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
			String query = "UPDATE cv_debate SET estado=0,"
					+ "FECHA_eliminacion=SYSDATE,usuario_eliminacion=?"
					+ "  WHERE IDDEBATE=?";
			stmt = (PreparedStatement) conec.prepareStatement(query);
			stmt.setString(1, usuario);
			stmt.setInt(2, idDebate);
			if (1 != stmt.executeUpdate()) {
				throw new DAOException(
						"DebateDAO: eliminarPlacticaSub(): Error");
			}
			query = "SELECT IDDEBATE from cv_debate where predecesor=?";
			stmt = (PreparedStatement) conec.prepareStatement(query);
			stmt.setInt(1, idDebate);
			result = (ResultSet) stmt.executeQuery();
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
			String query = "UPDATE cv_debate SET estado=0,"
					+ "FECHA_eliminacion=SYSDATE,usuario_eliminacion=?"
					+ "  WHERE IDDEBATE=?";
			cons = (Connection) dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, idUsuario);
			stmt.setInt(2, idDebate);
			if (1 != stmt.executeUpdate()) {
				throw new DAOException("DebateDAO: eliminarPlactica(): Error");
			}
			query = "SELECT IDDEBATE from cv_debate where predecesor=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idDebate);
			result = (ResultSet) stmt.executeQuery();
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


	public List<Debate> obtenerPlactica(AulaVirtual aula, int idGrupo)
			throws DAOException {
		log.info("obtenerPlactica(AulaVirtual aula, int idUnidad)");
		List<Debate> list = new ArrayList<Debate>();
		Debate dia;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = "SELECT CVDI.IDDEBATE, CVDI.ASUNTO, CVDI.TEXTO, TO_CHAR(CVDI.FECHA,'DD-MM-YYYY HH24:MI') FECHA,CVDI.OWNER, CVDI.PREDECESOR,CVDI.ESTADO,CVDIMA.LEIDO,CVDIMA.IMPORTANTE, " +
					"GEN.NOMUNO,GEN.NOMDOS,GEN.APEPATERNO,GEN.APEMATERNO,SEU.USUARIO,CVR.IDROL, " +
					"(SELECT COUNT(*) FROM cv_debate CDG, cv_debate_matricula CDGM " +
					"WHERE CDG.OWNER=CVDI.IDDEBATE AND CDG.IDDEBATE=CDGM.IDDEBATE AND CDGM.IDMATRICULA=CVDIMA.IDMATRICULA AND CDG.ESTADO=1) SUBPLACTICA," +
					"(SELECT COUNT(*) FROM cv_debate CDG,cv_debate_matricula CDGM " +
					"WHERE CDG.OWNER=CVDI.IDDEBATE AND CDG.IDDEBATE=CDGM.IDDEBATE AND CDGM.IDMATRICULA=CVDIMA.IDMATRICULA AND CDG.ESTADO=1 AND CDGM.LEIDO='0') NOSUBPLACTICA " +
					"FROM cv_debate CVDI,cv_debate_matricula CVDIMA,GENERAL.GEN_PERSONA GEN,SEGURIDAD.SEG_USUARIO SEU,cv_matricula CVM,cv_rol CVR " +
					"WHERE CVM.IDMATRICULA=CVDI.IDMATRICULA AND CVM.USUARIO=SEU.USUARIO AND SEU.CODSUJETO=GEN.CODPERSONA AND CVDI.ESTADO=1 AND CVDI.IDDEBATE=CVDIMA.IDDEBATE " +
					"AND CVDIMA.IDMATRICULA=? AND CVDI.OWNER=0 AND CVDI.PREDECESOR=0 AND CVR.IDROL=CVM.IDROL AND CVDI.IDTRABAJO=? AND CVDI.IDGRUPO=? ORDER BY FECHA DESC";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, aula.getIdMatricula());
			stmt.setInt(2, aula.getTrabajoGrupalActual());
			stmt.setInt(3, idGrupo);
			result = (ResultSet) stmt.executeQuery();

			while (result.next()) {
				dia = new Debate();
				dia.setAsunto(result.getString("asunto"));
				dia.setFecha(result.getString("fecha"));
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
						+ aula.getIdMatricula() + " idDebate: " + idDebate);
		List<Debate> list = new ArrayList<Debate>();
		Debate dia;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = "SELECT CVDI.IDDEBATE,TO_CHAR(CVDI.FECHA,'DD-MM-YYYY HH24:MI') FECHA,"
					+ "NVL(CVDI.ASUNTO,(SELECT 'RE: '||ASUNTO FROM cv_debate WHERE IDDEBATE=CVDI.OWNER)) ASUNTO,"
					+ "CVDI.OWNER,CVDI.PREDECESOR,CVDIMA.LEIDO,"
					+ "GEN.NOMUNO,GEN.NOMDOS,GEN.APEPATERNO,GEN.APEMATERNO,SEU.USUARIO,CVR.IDROL,"
					+ "(SELECT COUNT(*) FROM cv_debate WHERE PREDECESOR=CVDI.IDDEBATE AND ESTADO=1) SUBPLACTICA "
					+ "FROM cv_rol CVR,cv_matricula CVM,cv_debate CVDI,cv_debate_matricula CVDIMA,"
					+ "GENERAL.GEN_PERSONA GEN,SEGURIDAD.SEG_USUARIO SEU "
					+ "WHERE CVM.IDMATRICULA=CVDI.IDMATRICULA AND CVM.USUARIO=SEU.USUARIO AND SEU.CODSUJETO=GEN.CODPERSONA "
					+ "AND CVDI.ESTADO=1 AND CVDI.IDDEBATE=CVDIMA.IDDEBATE AND CVR.IDROL=CVM.IDROL "
					+ "AND CVDIMA.IDMATRICULA=? AND CVDI.PREDECESOR=? ORDER BY CVDI.IDDEBATE";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, aula.getIdMatricula());
			stmt.setInt(2, idDebate);
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				dia = new Debate();
				dia.setFecha(result.getString("fecha"));
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
				query = "SELECT CVDI.IDDEBATE,CVDI.ASUNTO,CVDI.TEXTO,TO_CHAR(CVDI.FECHA,'DD-MM-YYYY HH24:MI') FECHA,"
						+ "CVDI.OWNER,CVDI.PREDECESOR,CVDIMA.LEIDO,"
						+ "GEN.NOMUNO,GEN.APEPATERNO,SEU.USUARIO,CVM.IDROL "
						+ "FROM cv_matricula CVM,cv_debate CVDI,cv_debate_matricula CVDIMA,"
						+ "GENERAL.GEN_PERSONA GEN,SEGURIDAD.SEG_USUARIO SEU "
						+ "WHERE CVM.IDMATRICULA=CVDI.IDMATRICULA AND CVM.USUARIO=SEU.USUARIO AND SEU.CODSUJETO=GEN.CODPERSONA "
						+ "AND CVDI.ESTADO=1 AND CVDI.IDDEBATE=CVDIMA.IDDEBATE "
						+ "AND CVDIMA.IDMATRICULA=? AND CVDI.IDDEBATE=?";
			} else {
				query = "SELECT CVDI.TEXTO "
						+ "FROM cv_debate CVDI,cv_debate_matricula CVDIMA "
						+ "WHERE CVDI.ESTADO=1 AND CVDI.IDDEBATE=CVDIMA.IDDEBATE "
						+ "AND CVDIMA.IDMATRICULA=? AND CVDI.IDDEBATE=?";
			}
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, aula.getIdMatricula());
			stmt.setInt(2, idDebate);
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				dia = new Debate();
				dia.setTexto(result.getString("TEXTO"));
				if (!soloTexto) {
					dia.setIdDebate(result.getInt("IDDEBATE"));
					dia.setAsunto(result.getString("ASUNTO"));
					dia.setFecha(result.getString("FECHA"));
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
			String query = "UPDATE cv_debate_matricula SET LEIDO=?,FECHA=SYSDATE "
					+ "WHERE IDDEBATE=? AND IDMATRICULA=?";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
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
