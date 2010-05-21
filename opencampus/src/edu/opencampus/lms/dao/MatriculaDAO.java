package edu.opencampus.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.modelo.Matricula;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.modelo.aulavirtual.MatriculaRol;
import edu.opencampus.lms.util.Constante;

public class MatriculaDAO extends BaseDAO {

	protected DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Collection<MatriculaRol> obtenerUsuariosMatriculadosGestionar(
			int idFicha) throws DAOException {
		log.info("obtenerUsuariosMatriculadosGestionar(int idFicha)");
		Collection<MatriculaRol> total = new ArrayList<MatriculaRol>();
		Collection<Matricula> usuarios = new ArrayList<Matricula>();
		Matricula usuarioMatricula = null;
		MatriculaRol matricula = null;
		ResultSet result1 = null;
		ResultSet result = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt = null;
		Connection cons = null;
		try {
			String query = "SELECT idrol,nombre FROM cv_rol order by idrol";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			result = (ResultSet) stmt.executeQuery();
			query = "SELECT cvma.idmatricula,cvma.usuario,gepe.APEPATERNO,gepe.APEMATERNO,"
					+ "gepe.NOMUNO,gepe.NOMDOS,cvma.SECCION,cvma.ESTADO,"
					+ "pkg_cv_com_producto.fx_cv_com_prod_nombreco_id(cvma.codigo_formacion) nomcorto"
					+ ",cvma.principal FROM cv_matricula cvma,seguridad.seg_usuario seus,"
					+ "general.gen_persona gepe WHERE cvma.idficha"
					+ " = ? AND seus.usuario=cvma.usuario AND seus.codsujeto=gepe.codpersona "
					+ "AND cvma.idrol=? AND cvma.eliminado='0' order by apepaterno,APEMATERNO,NOMUNO,NOMDOS,SECCION";
			stmt1 = (PreparedStatement) cons.prepareStatement(query);
			while (result.next()) {
				usuarios = new ArrayList<Matricula>();
				matricula = new MatriculaRol();
				matricula.setNombre(result.getString("nombre"));
				matricula.setIdRol(result.getString("idrol"));
				stmt1.setInt(1, idFicha);
				stmt1.setInt(2, result.getInt("idrol"));
				result1 = (ResultSet) stmt1.executeQuery();
				while (result1.next()) {
					usuarioMatricula = new Matricula();
					usuarioMatricula.setIdMatricula(result1
							.getString("idmatricula"));
					usuarioMatricula.setIdUsuario(result1.getString("usuario"));
					usuarioMatricula
							.setPaterno(result1.getString("APEPATERNO"));
					usuarioMatricula
							.setMaterno(result1.getString("APEMATERNO"));
					usuarioMatricula.setNombre1(result1.getString("NOMUNO"));
					usuarioMatricula.setNombre2(result1.getString("NOMDOS"));
					usuarioMatricula.setSeccion(result1.getString("SECCION"));
					usuarioMatricula.setEstado(result1.getInt("ESTADO"));
					usuarioMatricula.setPrincipal(result1.getInt("principal"));
					usuarioMatricula.setNombreFormacion(result1
							.getString("nomcorto"));
					usuarios.add(usuarioMatricula);
				}
				matricula.setMatriculas(usuarios);
				total.add(matricula);
			}
			result1.close();
			stmt1.close();
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result1);
			closeResultSet(result);
			closeStatement(stmt1);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return total;
	}

	public Collection<MatriculaRol> obtenerUsuariosMatriculados(int idFicha)
			throws DAOException {
		log.info("obtenerUsuariosMatriculados(int idFicha)");
		Collection<MatriculaRol> total = new ArrayList<MatriculaRol>();
		Collection<Matricula> usuarios = new ArrayList<Matricula>();
		Matricula usuarioMatricula = null;
		MatriculaRol matricula = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		PreparedStatement stmt1 = null;
		ResultSet result1 = null;
		try {
			String query = "SELECT idrol,nombre FROM cv_rol where idrol in ("
					+ Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE + ","
					+ Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE
					+ ") order by idrol ";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			result = (ResultSet) stmt.executeQuery();
			query = "SELECT cvma.idmatricula,cvma.usuario,gepe.APEPATERNO,gepe.APEMATERNO,"
					+ "gepe.NOMUNO,gepe.NOMDOS,cvma.SECCION,cvma.ESTADO FROM "
					+ "cv_matricula cvma,seguridad.seg_usuario seus,general.gen_persona gepe"
					+ " WHERE cvma.idficha = ? AND seus.usuario=cvma.usuario AND seus.codsujeto="
					+ "gepe.codpersona AND cvma.idrol=?  AND cvma.eliminado='0'  order by "
					+ "apepaterno,APEMATERNO,NOMUNO,NOMDOS,SECCION";
			stmt1 = (PreparedStatement) cons.prepareStatement(query);
			while (result.next()) {
				usuarios = new ArrayList<Matricula>();
				matricula = new MatriculaRol();
				matricula.setNombre(result.getString("nombre"));
				matricula.setIdRol(result.getString("idrol"));
				stmt1.setInt(1, idFicha);
				stmt1.setInt(2, result.getInt("idrol"));
				result1 = (ResultSet) stmt1.executeQuery();
				while (result1.next()) {
					usuarioMatricula = new Matricula();
					usuarioMatricula.setIdMatricula(result1
							.getString("idmatricula"));
					usuarioMatricula.setIdUsuario(result1.getString("usuario"));
					usuarioMatricula
							.setPaterno(result1.getString("APEPATERNO"));
					usuarioMatricula
							.setMaterno(result1.getString("APEMATERNO"));
					usuarioMatricula.setNombre1(result1.getString("NOMUNO"));
					usuarioMatricula.setNombre2(result1.getString("NOMDOS"));
					usuarioMatricula.setSeccion(result1.getString("SECCION"));
					usuarioMatricula.setEstado(result1.getInt("ESTADO"));
					usuarios.add(usuarioMatricula);
				}
				matricula.setMatriculas(usuarios);
				total.add(matricula);
			}
			result1.close();
			stmt1.close();
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result1);
			closeResultSet(result);
			closeStatement(stmt);
			closeStatement(stmt1);
			closeConnection(cons);
		}
		return total;
	}

	public Collection<Matricula> obtenerUsuariosMatriculadosEstudiantes(
			int idFicha) throws DAOException {
		Collection<Matricula> usuarios = new ArrayList<Matricula>();
		Matricula usuarioMatricula = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = "SELECT cvma.idmatricula,cvma.usuario,gepe.APEPATERNO,gepe.APEMATERNO,"
					+ "gepe.NOMUNO,gepe.NOMDOS,cvma.SECCION,cvma.ESTADO FROM "
					+ "cv_matricula cvma,seguridad.seg_usuario seus,general.gen_persona gepe"
					+ " WHERE cvma.idficha = ? AND seus.usuario=cvma.usuario AND seus.codsujeto="
					+ "gepe.codpersona AND cvma.eliminado='0' AND cvma.idrol="
					+ Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE
					+ " order by apepaterno,APEMATERNO,NOMUNO,NOMDOS,SECCION";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idFicha);
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				usuarioMatricula = new Matricula();
				usuarioMatricula
						.setIdMatricula(result.getString("idmatricula"));
				usuarioMatricula.setIdUsuario(result.getString("usuario"));
				usuarioMatricula.setPaterno(result.getString("APEPATERNO"));
				usuarioMatricula.setMaterno(result.getString("APEMATERNO"));
				usuarioMatricula.setNombre1(result.getString("NOMUNO"));
				usuarioMatricula.setNombre2(result.getString("NOMDOS"));
				usuarioMatricula.setSeccion(result.getString("SECCION"));
				usuarioMatricula.setEstado(result.getInt("ESTADO"));
				usuarios.add(usuarioMatricula);
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
		return usuarios;
	}

	public Collection<Matricula> obtenerUsuarioNoMatriculados(
			String nombre, int idFicha) throws DAOException {
		log.info("obtenerUsuarioNoMatriculados(String nombre, int idFicha)");
		Collection<Matricula> col = new ArrayList<Matricula>();
		Matricula ob = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String[] palabras = null;
		Connection cons = null;
		try {
			palabras = nombre.split(" ");
			if (null == palabras || 0 == palabras.length) {
				throw new Exception();
			}
		} catch (Exception e) {
			log.error(e);
			throw new DAOException("Error en la busqueda condicion rechazada");
		}
		try {
			String query = "SELECT seus.usuario, gepe.apepaterno, gepe.apematerno, gepe.nomuno,"
					+ "  gepe.nomdos FROM seguridad.seg_usuario seus,  general.gen_persona gepe "
					+ "WHERE gepe.codpersona = seus.codsujeto ";
			for (int u = 0; u < palabras.length; u++) {
				query += "AND UPPER(TRANSLATE(gepe.APEPATERNO||' '|| gepe.APEMATERNO ||' '||gepe."
						+ "NOMUNO ||' '||gepe.NOMDOS,'áéíóúÁÉÍÓÚ','aeiouAEIOU')) LIKE UPPER("
						+ "TRANSLATE(?,'áéíóúÁÉÍÓÚ','aeiouAEIOU'))";
			}
			query += " minus SELECT cvma.usuario,gepe.apepaterno,gepe.apematerno,gepe.nomuno,"
					+ "gepe.nomdos FROM campusv4.cv_matricula cvma,seguridad.seg_usuario seus,"
					+ "general.gen_persona gepe WHERE cvma.idficha = ? AND gepe.codpersona="
					+ "seus.codsujeto AND seus.usuario=cvma.usuario AND cvma.eliminado='0'";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			int u = 1;
			for (int position = 0; position < palabras.length; u = u + 1, position = position + 1) {
				stmt.setString(u, "%" + palabras[position] + "%");
			}
			stmt.setInt(u, idFicha);
			result = (ResultSet) stmt.executeQuery();
			int cantidad = 0;
			while (result.next()) {
				cantidad++;
				if (Constante.BUSQUEDA_CANTIDAD_DIRECTORIO <= cantidad) {
					break;
				}
				ob = new Matricula();
				ob.setIdUsuario(result.getString("usuario"));
				ob.setPaterno(result.getString("apepaterno"));
				ob.setMaterno(result.getString("apematerno"));
				ob.setNombre1(result.getString("nomuno"));
				ob.setNombre2(result.getString("nomdos"));
				col.add(ob);
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

	public void matricularUsuario(String formacion, String rol, String seccion,
			int idFicha, String idUsuario, Usuario usuario) throws DAOException {
		log.info("matricularUsuario(String rol,String "
				+ "seccion,int idFicha,String idUsuario)");
		int int_rol = Integer.parseInt(rol);
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		ResultSet subResult = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		PreparedStatement stmt4 = null;
		PreparedStatement stmt5 = null;
		try {
			cons = (Connection) dataSource.getConnection();
			cons.setAutoCommit(false);
			String query = "SELECT COUNT(*)  FROM cv_matricula WHERE TRIM(USUARIO)=? "
					+ "AND IDFICHA=? AND ELIMINADO='0'";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, idUsuario.trim());
			stmt.setInt(2, idFicha);
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				if (0 == result.getInt(1)) {
					query = "INSERT INTO cv_matricula (IDMATRICULA,USUARIO_CREACION,"
							+ "FECHA_MOD,USUARIO_MOD,FECHA_CREACION,IDFICHA,IDROL,SECCION,"
							+ "USUARIO,ESTADO,ELIMINADO,codigo_formacion,codigo_curricula,PRINCIPAL)"
							+ " VALUES (seqcvmatricula.nextval,?,sysdate,?,sysdate,?,?,?,?"
							+ ",'1','0',?,nvl(PKG_cv_ficha.fx_cv_curricula(?,?),0),0)";
					stmt = (PreparedStatement) cons
							.prepareStatement(query);
					stmt.setString(1, usuario.getIdUsuario());
					stmt.setString(2, usuario.getIdUsuario());
					stmt.setInt(3, idFicha);
					stmt.setInt(4, int_rol);
					stmt.setString(5, seccion);
					stmt.setString(6, idUsuario.trim());
					stmt.setString(7, formacion);
					stmt.setString(8, formacion);
					stmt.setInt(9, idFicha);
					int resultado = stmt.executeUpdate();
					if (1 != resultado) {
						cons.rollback();
						throw new DAOException("No se logro insertar");
					}
					query = "INSERT INTO cv_dialogo_matricula (IDDIALOGO,IDMATRICULA,LEIDO)"
							+ " SELECT IDDIALOGO,seqcvmatricula.CURRVAL,'0' FROM cv_dialogo"
							+ " WHERE IDFICHA=?";
					stmt = (PreparedStatement) cons
							.prepareStatement(query);
					stmt.setInt(1, idFicha);
					stmt.executeUpdate();
					query = "INSERT INTO cv_flag_publicacion_ficha(idmatricula,idpublicacion,idherramienta,idficha,estado,fecha,importante) "
							+ "SELECT seqcvmatricula.currval,idpublicacion,idherramienta,idficha,'0',fecha,'0' "
							+ "FROM cv_publicacion_ficha "
							+ "WHERE idficha = ?";
					stmt = (PreparedStatement) cons
							.prepareStatement(query);
					stmt.setInt(1, idFicha);
					stmt.executeUpdate();
					if (Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE == int_rol
							|| Constante.ROL_CAMPUS_AULAVIRTUAL_MONITOR_DOCENTE == int_rol
							|| Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE == int_rol) {
						query = "INSERT INTO cv_debate_matricula (IDDEBATE,"
								+ "IDMATRICULA,LEIDO) SELECT cvdegr.IDDEBATE,seqcvmatricula."
								+ "CURRVAL,'0' FROM cv_debate cvdegr,CV_GRUPO_TRABAJO "
								+ "cvgrtr,cv_trabajo_grupal cvtrgr WHERE cvdegr.idtrabajo="
								+ "cvgrtr.idtrabajo and cvdegr.idgrupo=cvgrtr.idgrupo and "
								+ "cvgrtr.estado='1' and cvtrgr.idtrabajo=cvgrtr.idtrabajo "
								+ "and cvtrgr.idficha=? ";
						stmt = (PreparedStatement) cons
								.prepareStatement(query);
						stmt.setInt(1, idFicha);
						stmt.executeUpdate();
					}
					if (Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE == int_rol
							|| Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE_MONITOR == int_rol) {
						query = "SELECT U.IDUNIDAD FROM cv_ficha F, cv_ficha_unidad U "
								+ "WHERE F.IDFICHA=U.IDFICHA AND F.IDSILABO=U.IDSILABO"
								+ " AND F.IDFICHA=?";
						stmt = (PreparedStatement) cons
								.prepareStatement(query);
						stmt.setInt(1, idFicha);
						result = (ResultSet) stmt.executeQuery();
						query = "SELECT IDTRABAJO,FECHA_ACTIVACION FROM cv_trabajo_individual "
								+ "WHERE IDFICHA=? AND IDUNIDAD=?";
						stmt = (PreparedStatement) cons
								.prepareStatement(query);
						query = "INSERT INTO cv_trabajo_individual_nota (IDTRABAJO,"
								+ "IDMATRICULA,USUARIO_MOD,FECHA_MOD,ESTADO,EXPIRADO) "
								+ "VALUES (?,seqcvmatricula.CURRVAL,?,SYSDATE,0,0)";
						stmt1 = (PreparedStatement) cons
								.prepareStatement(query);
						query = "INSERT INTO cv_trabajo_individual_inter (IDTRABAJO,"
								+ "IDMATRICULA,INTERACCION,DESCRIPCION,IDMATRICULA_ENVIO,"
								+ "USUARIO_CREACION,FECHA_CREACION,USUARIO_MOD,FECHA_MOD,"
								+ "ARCHIVO_NOMBRE,ARCHIVO_TAMANO) "
								+ "SELECT IDTRABAJO,seqcvmatricula.CURRVAL,1,"
								+ "DESCRIPCION,IDMATRICULA_ENVIO,USUARIO_CREACION,"
								+ "FECHA_CREACION,USUARIO_MOD,FECHA_MOD,ARCHIVO_NOMBRE,"
								+ "ARCHIVO_TAMANO FROM cv_trabajo_individual WHERE IDTRABAJO=?";
						stmt2 = (PreparedStatement) cons
								.prepareStatement(query);
						query = "SELECT IDTRABAJO,FECHA_ACTIVACION FROM CV_LABORATORIO "
								+ "WHERE IDFICHA=? AND IDUNIDAD=?";
						stmt3 = (PreparedStatement) cons
								.prepareStatement(query);
						query = "INSERT INTO CV_LABORATORIO_NOTA (IDTRABAJO,IDMATRICULA,"
								+ "USUARIO_MOD,FECHA_MOD,ESTADO,EXPIRADO) "
								+ "VALUES (?,seqcvmatricula.CURRVAL,?,SYSDATE,0,0)";
						stmt4 = (PreparedStatement) cons
								.prepareStatement(query);
						query = "INSERT INTO CV_LABORATORIO_INTER (IDTRABAJO,IDMATRICULA,"
								+ "INTERACCION,DESCRIPCION,IDMATRICULA_ENVIO,USUARIO_CREACION,"
								+ "FECHA_CREACION,USUARIO_MOD,FECHA_MOD,ARCHIVO_NOMBRE,ARCHIVO_TAMANO) "
								+ "SELECT IDTRABAJO,seqcvmatricula.CURRVAL,1,DESCRIPCION,"
								+ "IDMATRICULA_ENVIO,USUARIO_CREACION,FECHA_CREACION,USUARIO_MOD,"
								+ "FECHA_MOD,ARCHIVO_NOMBRE,ARCHIVO_TAMANO "
								+ "FROM CV_LABORATORIO WHERE IDTRABAJO=?";
						stmt5 = (PreparedStatement) cons
								.prepareStatement(query);
						while (result.next()) {
							stmt.setInt(1, idFicha);
							stmt.setInt(2, result.getInt("IDUNIDAD"));
							subResult = (ResultSet) stmt.executeQuery();
							if (subResult.next()
									&& subResult.getString("FECHA_ACTIVACION") != null) {
								stmt1.setInt(1, subResult.getInt("IDTRABAJO"));
								stmt1.setString(2, usuario.getIdUsuario());
								if (1 != stmt1.executeUpdate()) {
									log.error("Error en matricularUsuario() "
											+ "- cv_trabajo_individual_nota");
									throw new DAOException("No culmino");
								}
								stmt2.setInt(1, subResult.getInt("IDTRABAJO"));
								if (1 != stmt2.executeUpdate()) {
									log.error("Error en matricularUsuario() "
											+ "- cv_trabajo_individual_inter");
									throw new DAOException("No culmino");
								}
							}
							stmt3.setInt(1, idFicha);
							stmt3.setInt(2, result.getInt("IDUNIDAD"));
							subResult = (ResultSet) stmt3.executeQuery();
							if (subResult.next()
									&& subResult.getString("FECHA_ACTIVACION") != null) {
								stmt4.setInt(1, subResult.getInt("IDTRABAJO"));
								stmt4.setString(2, usuario.getIdUsuario());
								if (1 != stmt4.executeUpdate()) {
									log.error("Error en matricularUsuario()"
											+ " - CV_LABORATORIO_NOTA");
									throw new DAOException("No culmino");
								}
								stmt5.setInt(1, subResult.getInt("IDTRABAJO"));
								if (1 != stmt5.executeUpdate()) {
									log.error("Error en matricularUsuario()"
											+ " - CV_LABORATORIO_INTER");
									throw new DAOException("No culmino");
								}

							}
						}
					}
					cons.commit();
				}
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
			try {
				cons.rollback();
			} catch (SQLException f) {
			}
			closeStatement(stmt1);
			closeStatement(stmt2);
			closeStatement(stmt3);
			closeStatement(stmt4);
			closeStatement(stmt5);
			closeResultSet(subResult);
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

	public void eliminarMatricula(String idMatricula, int idFicha,
			Usuario usuario) throws DAOException {
		log.info("eliminarMatricula(String idMatricula, int idFicha)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			cons = (Connection) dataSource.getConnection();
			cons.setAutoCommit(false);
			String query = "SELECT COUNT(*)  FROM cv_matricula WHERE "
					+ "idmatricula=? AND IDFICHA=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, idMatricula);
			stmt.setInt(2, idFicha);
			result = (ResultSet) stmt.executeQuery();
			query = "UPDATE cv_matricula set eliminado='1',usuario_mod=?,"
					+ "fecha_mod=sysdate where idmatricula=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			if (result.next()) {
				if (1 == result.getInt(1)) {
					stmt.setString(1, usuario.getIdUsuario());
					stmt.setString(2, idMatricula);
					int resultado = stmt.executeUpdate();
					if (1 != resultado) {
						cons.rollback();
						throw new DAOException("No se logro insertar");
					}
					cons.commit();
				}
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
			try {
				cons.rollback();
			} catch (SQLException f) {
			}
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

	public void modificarMatricula(String idMatricula, String estado,
			int idFicha, Usuario usuario) throws DAOException {
		log.info("modificarMatricula(String idMatricula,"
				+ "String estado, int idFicha)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			cons = (Connection) dataSource.getConnection();
			cons.setAutoCommit(false);
			String query = "SELECT COUNT(*)  FROM cv_matricula WHERE idmatricula=? AND IDFICHA=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, idMatricula);
			stmt.setInt(2, idFicha);
			result = (ResultSet) stmt.executeQuery();
			query = "UPDATE cv_matricula set estado=?,usuario_mod=?,fecha_mod="
					+ "sysdate where idmatricula=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			if (result.next()) {
				if (1 == result.getInt(1)) {
					stmt.setString(1, estado);
					stmt.setString(2, usuario.getIdUsuario());
					stmt.setString(3, idMatricula);
					int resultado = stmt.executeUpdate();
					if (1 != resultado) {
						cons.rollback();
						throw new DAOException("No se logro insertar");
					}
					cons.commit();
				}
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (DAOException e) {
			log.error(e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.toString());
		} finally {
			try {
				cons.rollback();
			} catch (SQLException f) {
			}
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

	public Collection<Matricula> obtenerTodosUsuariosMatriculados(
			int idFicha) throws DAOException {
		log.info("obtenerTodosUsuariosMatriculados("+idFicha+")");
		Collection<Matricula> usuarios = new ArrayList<Matricula>();
		Matricula usuarioMatricula = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = "SELECT cvma.idmatricula,cvma.usuario,gepe.APEPATERNO,"
					+ "gepe.APEMATERNO,gepe.NOMUNO,gepe.NOMDOS,cvma.SECCION,"
					+ "cvma.ESTADO FROM cv_matricula cvma,seguridad.seg_usuario "
					+ "seus,general.gen_persona gepe WHERE cvma.idficha = ? AND "
					+ "seus.usuario=cvma.usuario AND seus.codsujeto=gepe.codpersona"
					+ " AND cvma.eliminado='0' order by apepaterno,APEMATERNO,"
					+ "NOMUNO,NOMDOS,SECCION";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idFicha);
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				usuarioMatricula = new Matricula();
				usuarioMatricula
						.setIdMatricula(result.getString("idmatricula"));
				usuarioMatricula.setIdUsuario(result.getString("usuario"));
				usuarioMatricula.setPaterno(result.getString("APEPATERNO"));
				usuarioMatricula.setMaterno(result.getString("APEMATERNO"));
				usuarioMatricula.setNombre1(result.getString("NOMUNO"));
				usuarioMatricula.setNombre2(result.getString("NOMDOS"));
				usuarioMatricula.setSeccion(result.getString("SECCION"));
				usuarioMatricula.setEstado(result.getInt("ESTADO"));
				usuarios.add(usuarioMatricula);
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
		return usuarios;
	}

	public void principal(String idMatricula, String estado, Usuario usuario)
			throws DAOException {
		log.info("principal(String idMatricula,"
				+ "String estado, int idFicha)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			cons = (Connection) dataSource.getConnection();
			cons.setAutoCommit(false);
			String query = "UPDATE cv_matricula SET PRINCIPAL=0 WHERE IDROL=? AND " +
					"IDFICHA=(select idficha from cv_matricula where idmatricula=?)";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE);
			stmt.setString(2, idMatricula);
			stmt.executeUpdate();
			if ("1".equals(estado)) {
				query = "UPDATE cv_matricula SET PRINCIPAL=1,usuario_mod=?,fecha_mod="
						+ "sysdate WHERE IDMATRICULA=? and IDROL=2";
				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt.setString(1, usuario.getIdUsuario());
				stmt.setString(2, idMatricula);
				if (1 != stmt.executeUpdate()) {
					cons.rollback();
					throw new DAOException("No se logro insertar");
				}
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
			} catch (SQLException f) {
			}
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

	public boolean matriculaPerteneceAulaUsuario(String idMatricula,
			String idUsuario) throws DAOException {
		log.info("matriculaPerteneceAulaUsuario(String "+idMatricula+", String "+idUsuario+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		boolean tipo = false;
		try {
			cons = (Connection) dataSource.getConnection();
			String query = "select count(idmatricula) from cv_matricula where "
					+ "eliminado=0 and idmatricula=? and estado=1 and idficha"
					+ " in (select idficha from cv_matricula where trim(usuario)"
					+ "=trim(?) and eliminado=0 and estado=1 and not idrol in (?,?))";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, idMatricula);
			stmt.setString(2, idUsuario);
			stmt.setInt(3, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE_MONITOR);
			stmt.setInt(4, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE);
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				if (1 == result.getInt(1)) {
					tipo = true;
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
		return tipo;
	}
	
	public boolean esMiMatricula(String idMatricula,String idUsuario) throws DAOException {
		log.info("esMiMatricula(String "+idMatricula+", String "+idUsuario+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		boolean tipo = false;
		try {
			cons = (Connection) dataSource.getConnection();
			String query = "SELECT IDMATRICULA FROM cv_matricula " +
					"WHERE ELIMINADO=0 AND ESTADO=1 AND IDMATRICULA=? AND TRIM(USUARIO)=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, idMatricula);
			stmt.setString(2, idUsuario);
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				tipo = true;
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
		return tipo;
	}
	
	public boolean esMiEmpleado(String codigo, int idMatricula) throws DAOException {
		log.info("esMiEmpleado(String "+codigo+", int "+idMatricula+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = "SELECT COUNT(*) EXISTE FROM cv_matricula " +
					"WHERE CODIGO_EMPRESA=(SELECT CODINSTITUCION " +
					"FROM GENERAL.GEN_INSTITUCION " +
					"WHERE RUC=? AND ESTADO='A') AND IDMATRICULA=?";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, codigo);
			stmt.setInt(2, idMatricula);
			
			result = (ResultSet) stmt.executeQuery();
			
			if (result.next()) {
				if(result.getInt("EXISTE")>0){
					return true;
				}
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return false;
	}
	
	public int obtenerRolDeAula(String idMatricula,String idUsuario) throws DAOException {
		log.info("obtenerRolDeAula(String "+idMatricula+", String "+idUsuario+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			cons = (Connection) dataSource.getConnection();
			String query = "SELECT IDROL FROM cv_matricula " +
					"WHERE ELIMINADO=0 AND ESTADO=1 AND TRIM(USUARIO)=? " +
					"AND IDFICHA=(SELECT IDFICHA FROM cv_matricula " +
					"WHERE ELIMINADO=0 AND ESTADO=1 AND IDMATRICULA=?)";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, idUsuario);
			stmt.setString(2, idMatricula);
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				return result.getInt("IDROL");
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
		return 0;
	}

}
