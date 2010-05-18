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
import edu.opencampus.lms.modelo.Dialogo;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;

public class DialogoDAO extends BaseDAO {

	protected DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Dialogo crearPlactica(AulaVirtual aula, int idUnidad,
			Dialogo modelo) throws DAOException {
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
				query = "SELECT OWNER FROM cv_dialogo WHERE IDDIALOGO=?";
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
			
			query = "INSERT INTO cv_dialogo(IDFICHA,IDUNIDAD,"
					+ "IDMATRICULA_ENVIO,ASUNTO,TEXTO,FECHA,OWNER,PREDECESOR,"
					+ "ESTADO) VALUES (?,?,?,?,?,now(),?,?,1)";
						
			stmt = (PreparedStatement) cons.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, aula.getIdFicha());
			stmt.setInt(2, idUnidad);
			stmt.setInt(3, aula.getMatriculaActual().getIdMatricula());
			stmt.setString(4, modelo.getAsunto());
			stmt.setString(5, modelo.getTexto());
			stmt.setInt(6, modelo.getOwner());
			stmt.setInt(7, modelo.getPredecesor());
			if (1 != stmt.executeUpdate()) {
				throw new DAOException("DialogoDAO: crearPlactica(): Error");
			}
			
			result = stmt.getGeneratedKeys();
			result.next();
			Integer id = result.getInt(1);
			
			query = "INSERT INTO cv_dialogo_matricula(IDDIALOGO,IDMATRICULA"
					+ ",LEIDO) SELECT ?,IDMATRICULA,'0' FROM"
					+ " cv_matricula WHERE IDFICHA=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.setInt(2, aula.getIdFicha());
			stmt.executeUpdate();
			query = "SELECT ?,date_format(now(),'DD-MM-YYYY "
					+ "HH24:MI') fecha FROM DUAL";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, id);
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				modelo.setIdDialogo(result.getInt(1));
				modelo.setFecha(result.getString(2));
			}
			query = "UPDATE cv_dialogo_matricula SET LEIDO=1,FECHA=now() "
					+ "WHERE IDDIALOGO=? AND IDMATRICULA=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, modelo.getIdDialogo());
			stmt.setInt(2, aula.getMatriculaActual().getIdMatricula());
			if (1 != stmt.executeUpdate()) {
				throw new DAOException("DialogoDAO: crearPlactica(): Error 2");
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

	public void eliminarPlacticaSub(String usuario, int idDialogo,
			Connection conec) throws DAOException {
		log.info("eliminarPlacticaSub()");
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String query = "UPDATE cv_dialogo SET estado=0"
					+ "  WHERE IDDialogo=?";
			stmt = (PreparedStatement) conec.prepareStatement(query);
			stmt.setString(1, usuario);
			stmt.setInt(2, idDialogo);
			if (1 != stmt.executeUpdate()) {
				throw new DAOException(
						"DialogoDAO: eliminarPlacticaSub(): Error");
			}
			query = "SELECT iddialogo from cv_dialogo where predecesor=?";
			stmt = (PreparedStatement) conec.prepareStatement(query);
			stmt.setInt(1, idDialogo);
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				eliminarPlacticaSub(usuario, result.getInt("iddialogo"), conec);
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
		}
	}

	public void eliminarPlactica(String idUsuario, int idDialogo)
			throws DAOException {
		log.info("eliminarPlactica(): " + idUsuario);
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = "UPDATE cv_dialogo SET estado=0"
					+ "  WHERE IDDialogo=?";
			cons = (Connection) dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, idUsuario);
			stmt.setInt(2, idDialogo);
			if (1 != stmt.executeUpdate()) {
				throw new DAOException("DialogoDAO: eliminarPlactica(): Error");
			}
			query = "SELECT iddialogo from cv_dialogo where predecesor=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idDialogo);
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				eliminarPlacticaSub(idUsuario, result.getInt("iddialogo"), cons);
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


	public List<Dialogo> obtenerPlactica(AulaVirtual aula, int idUnidad)
			throws DAOException {
		log.info("obtenerPlactica(AulaVirtual aula, int idUnidad)");
		List<Dialogo> list = new ArrayList<Dialogo>();
		Dialogo dia;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = "SELECT CVDI.IDDIALOGO, CVDI.ASUNTO, DATE_FORMAT(CVDI.FECHA,'%d-%m-%Y %H:%i') FECHA, " +
					"CVDI.OWNER, CVDI.PREDECESOR, CVDIMA.LEIDO, " +
					"GEN.NOMUNO,GEN.NOMDOS,GEN.APEPATERNO,GEN.APEMATERNO,SEU.USUARIO,CVR.IDROL, " +
					"(SELECT COUNT(*) FROM cv_dialogo CD,cv_dialogo_matricula CDM  " +
					"WHERE CD.OWNER=CVDI.IDDIALOGO AND CD.IDDIALOGO=CDM.IDDIALOGO AND CDM.IDMATRICULA=CVDIMA.IDMATRICULA AND CD.ESTADO=1) SUBPLACTICA, " +
					"(SELECT COUNT(*) FROM cv_dialogo CD,cv_dialogo_matricula CDM " +
					"WHERE CD.OWNER=CVDI.IDDIALOGO AND CD.IDDIALOGO=CDM.IDDIALOGO AND CDM.IDMATRICULA=CVDIMA.IDMATRICULA AND CD.ESTADO=1 AND CDM.LEIDO='0') NOSUBPLACTICA " +
					"FROM cv_matricula_rol CVR, cv_matricula CVM, cv_dialogo CVDI,cv_dialogo_matricula CVDIMA,CV_PERSONA GEN,CV_USUARIO SEU " +
					"WHERE CVM.IDMATRICULA=CVDI.IDMATRICULA_ENVIO AND CVM.IDUSUARIO=SEU.IDUSUARIO AND SEU.IDUSUARIO=GEN.IDPERSONA " +
					"AND CVDI.ESTADO=1 AND CVDI.IDDIALOGO=CVDIMA.IDDIALOGO AND CVDI.OWNER=0  AND CVR.IDROL=CVM.IDROL "
					+ "AND CVDI.PREDECESOR=0 AND CVDIMA.IDMATRICULA=? AND CVDI.IDFICHA=? AND CVDI.IDUNIDAD=? "
					+ "ORDER BY CVDI.IDDIALOGO DESC";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, aula.getMatriculaActual().getIdMatricula());
			stmt.setInt(2, aula.getIdFicha());
			stmt.setInt(3, idUnidad);
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				dia = new Dialogo();
				dia.setAsunto(result.getString("asunto"));
				dia.setFecha(result.getString("fecha"));
				dia.setIdDialogo(result.getInt("iddialogo"));
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

	public List<Dialogo> obtenerSubPlactica(AulaVirtual aula,
			int idDialogo) throws DAOException {
		log
				.info("obtenerSubPlactica(AulaVirtual aula, int idDialogo) - idMatricula: "
						+ aula.getMatriculaActual().getIdMatricula() + " idDialogo: " + idDialogo);
		List<Dialogo> list = new ArrayList<Dialogo>();
		Dialogo dia;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = "SELECT CVDI.IDDIALOGO,DATE_FORMAT(CVDI.FECHA,'%d-%m-%Y %H:%i') FECHA, " +
					"IFNULL(CVDI.ASUNTO,(SELECT CONCAT('RE: ',ASUNTO) FROM cv_dialogo WHERE IDDIALOGO=CVDI.OWNER)) ASUNTO," +
					"CVDI.OWNER,CVDI.PREDECESOR,CVDIMA.LEIDO," +
					"GEN.NOMUNO,GEN.NOMDOS,GEN.APEPATERNO,GEN.APEMATERNO,SEU.USUARIO,CVR.IDROL, " +
					"(SELECT COUNT(*) FROM cv_dialogo WHERE PREDECESOR=CVDI.IDDIALOGO AND ESTADO=1) SUBPLACTICA " +
					"FROM cv_matricula_rol CVR,cv_matricula CVM,cv_dialogo CVDI,cv_dialogo_matricula CVDIMA," +
					"CV_PERSONA GEN,CV_USUARIO SEU " +
					"WHERE CVM.IDMATRICULA=CVDI.IDMATRICULA_ENVIO AND CVM.IDUSUARIO=SEU.IDUSUARIO AND SEU.IDUSUARIO=GEN.IDPERSONA  " +
					"AND CVDI.ESTADO=1 AND CVDI.IDDIALOGO=CVDIMA.IDDIALOGO AND CVR.IDROL=CVM.IDROL  "
					+ "AND CVDIMA.IDMATRICULA=? AND CVDI.PREDECESOR=? ORDER BY CVDI.IDDIALOGO";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, aula.getMatriculaActual().getIdMatricula());
			stmt.setInt(2, idDialogo);
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				dia = new Dialogo();
				dia.setFecha(result.getString("fecha"));
				dia.setAsunto(result.getString("asunto"));
				dia.setIdDialogo(result.getInt("iddialogo"));
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

	public Dialogo obtenerDialogo(AulaVirtual aula, int idDialogo,
			boolean soloTexto) throws DAOException {
		log.info("obtenerDialogo(AulaVirtual aula, int idDialogo, boolean soloTexto)");
		Dialogo dia = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = null;

			if (!soloTexto) {
				query = "SELECT CVDI.IDDIALOGO,CVDI.ASUNTO,CVDI.TEXTO,DATE_FORMAT(CVDI.FECHA,'%d-%m-%Y %H:%i') FECHA," +
						"CVDI.OWNER,CVDI.PREDECESOR,CVDIMA.LEIDO," +
						"GEN.NOMUNO,GEN.APEPATERNO,SEU.USUARIO,CVM.IDROL " +
						"FROM cv_matricula CVM,cv_dialogo CVDI,cv_dialogo_matricula CVDIMA," +
						"CV_PERSONA GEN,CV_USUARIO SEU " +
						"WHERE CVM.IDMATRICULA=CVDI.IDMATRICULA_ENVIO AND CVM.IDUSUARIO=SEU.IDUSUARIO AND SEU.IDUSUARIO=GEN.IDPERSONA " +
						"AND CVDI.ESTADO=1 AND CVDI.IDDIALOGO=CVDIMA.IDDIALOGO   "
						+ "AND CVDIMA.IDMATRICULA=? AND CVDI.IDDIALOGO=?";
			} else {
				query = "SELECT CVDI.TEXTO "
						+ "FROM cv_dialogo CVDI,cv_dialogo_matricula CVDIMA "
						+ "WHERE CVDI.ESTADO=1 AND CVDI.IDDIALOGO=CVDIMA.IDDIALOGO "
						+ "AND CVDIMA.IDMATRICULA=? AND CVDI.IDDIALOGO=?";
			}
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, aula.getMatriculaActual().getIdMatricula());
			stmt.setInt(2, idDialogo);
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				dia = new Dialogo();
				dia.setTexto(result.getString("TEXTO"));
				if (!soloTexto) {
					dia.setIdDialogo(result.getInt("IDDIALOGO"));
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

	public boolean isEnabledDialogo(AulaVirtual aula, int idUnidad)
			throws DAOException {
		log.info("isEnabledDialogo(AulaVirtual aula, int idUnidad)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
//			String query = "SELECT DESHABILITADO_DOC,DESHABILITADO_ESTU,ESTADO,"
//					+ "(SELECT COUNT(*) FROM cv_ficha_unidad WHERE IDFICHA=UFR.IDFICHA AND IDUNIDAD=UFR.IDUNIDAD AND ESTADO=1) EXISTEUNIDAD "
//					+ "FROM cv_ficha_unidad_RECURSO UFR "
//					+ "WHERE IDFICHA=? AND IDUNIDAD=? AND IDRECURSO=?";
//			cons = (Connection) dataSource.getConnection();
//			stmt = (PreparedStatement) cons.prepareStatement(query);
//			stmt.setInt(1, aula.getIdFicha());
//			stmt.setInt(2, idUnidad);
//			stmt.setString(3, Constante.RECURSO_ID_DIALOGO);
//			result = (ResultSet) stmt.executeQuery();
//			if (result.next()) {
//				if (0 == result.getInt("ESTADO")) {
//					log.warn("Recurso desactivado");
//					return false;
//				}
//				if (1 != result.getInt("EXISTEUNIDAD")) {
//					log.warn("Unidad desactivada");
//					return false;
//				}
//				if (Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE == aula.getRol()
//						.getIdrol()
//						|| Constante.ROL_CAMPUS_AULAVIRTUAL_MONITOR_DOCENTE == aula
//								.getRol().getIdrol()) {
//					if (0 == result.getInt("DESHABILITADO_DOC")) {
//						log.warn("Recurso para el docente");
//						return false;
//					}
//				}
//				if (Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE == aula
//						.getRol().getIdrol()
//						|| Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE_MONITOR == aula
//								.getRol().getIdrol()) {
//					if (0 == result.getInt("DESHABILITADO_ESTU")) {
//						log.warn("Recurso para el alumno");
//						return false;
//					}
//				}
//			} else {
//				log.warn("Recurso no existe");
//				return false;
//			}
//
//		} catch (SQLException e) {
//			log.error(e);
//			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return true;
	}

	public void leerPlactica(int idDialogo, int idMatricula, int estado)
			throws DAOException {
		log.info("leerPlactica() - idDialogo: "+idDialogo + " - idMatricula: "+idMatricula + " - estado: "+estado);
		PreparedStatement stmt = null;
		Connection cons = null;
		try {
			String query = "UPDATE cv_dialogo_matricula SET LEIDO=?,FECHA=now() "
					+ "WHERE IDDIALOGO=? AND IDMATRICULA=?";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, estado);
			stmt.setInt(2, idDialogo);
			stmt.setInt(3, idMatricula);
			if (1 != stmt.executeUpdate()) {
				throw new DAOException("DialogoDAO: leerPlactica(): Error");
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
