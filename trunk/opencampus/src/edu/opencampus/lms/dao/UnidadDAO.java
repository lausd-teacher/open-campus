package edu.opencampus.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.modelo.Ficha;
import edu.opencampus.lms.modelo.Jerarquia;
import edu.opencampus.lms.modelo.ficha.Recurso;
import edu.opencampus.lms.modelo.ficha.Unidad;
import edu.opencampus.lms.util.Formato;

public class UnidadDAO extends BaseDAO {

	Log log = LogFactory.getLog(getClass());

	protected DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Collection<Unidad> buscarPorNombre(String nombre, String exacto) throws DAOException {
		log.info("buscarPorNombre(String nombre, String exacto)");

		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;

		Collection<Unidad> unidades = new ArrayList<Unidad>();
		List<Recurso> recursos;
		ResultSet subResult = null;
		PreparedStatement subStmt = null;
		PreparedStatement subStmt2 = null;
		Recurso recurso;
		int numero = 0;
		int totalTest = 0;
		try {
			String query = "select u.idunidad,u.idjerarquia,j.nombre jerarquia,u.nombre,u.creado_en,u.estado,u.modificado_en  " +
					"from cv_unidad u, cv_jerarquia j where u.idjerarquia=j.idjerarquia " +
					"and replace(replace(replace(replace(replace(replace(upper(u.nombre),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N') ";

			if (exacto != null) {
				query += "= ? ";
			} else {
				query += "like ? ";
			}
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			
			if (exacto != null) {
				stmt.setString(1, Formato.matizarFrace(nombre));
			} else {
				stmt.setString(1, "%" + Formato.matizarFrace(nombre) + "%");
			}

			result =  stmt.executeQuery();
			Unidad unidad = null;
			query = "select idrecurso, nombre from cv_recurso";
			stmt =  cons.prepareStatement(query);

			query = "select count(*) total from cv_test where estado='1' and idunidad=?";
			subStmt2 =  cons.prepareStatement(query);

			while (result.next()) {

				// **** SUBQUERY RECURSOS COLLECION ****//
				subResult =  stmt.executeQuery();
				recursos = new ArrayList<Recurso>();
				numero = 0;
				while (subResult.next()) {
					recurso = new Recurso();
					recurso.setIdRecurso(subResult.getInt("idrecurso"));
					recurso.setNombre(subResult.getString("nombre"));
					recursos.add(recurso);
					numero++;
				}
			
				// ************ Cantidad de Test **************//

				subStmt2.setInt(1, result.getInt("idunidad"));
				subResult =  subStmt2.executeQuery();

				if (subResult.next()) {
					totalTest = subResult.getInt("total");
				}

				// ********************************************//

				unidad = new Unidad();
				unidad.setFechaCreacion(Formato.timestampToCalendar(result.getString("creado_en")));
				unidad.setFechaModificacion(Formato.timestampToCalendar(result.getString("modificado_en")));
				unidad.setIdUnidad(result.getInt("idunidad"));
				unidad.setNombreCompleto(result.getString("nombre"));
				unidad.setEstado(result.getInt("estado"));
				unidad.setCantidadTest(totalTest);
				unidad.setJerarquia(new Jerarquia(result.getInt("idjerarquia"),result.getString("jerarquia")));
				unidad.setRecursos(recursos);
				unidades.add(unidad);
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(subResult);
			closeStatement(subStmt);
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return unidades;
	}

	public boolean existeUnidad(String nombre) throws DAOException {
		log.info("int existeUnidad(String nombre)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		boolean existe = false;
		try {
			String query = "select idunidad from cv_unidad " +
					"where replace(replace(replace(replace(replace(replace(upper(nombre),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N') = ?";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, Formato.matizarFrace(nombre));
			result =  stmt.executeQuery();
			if (result.next()) {
				existe = true;
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return existe;
	}

	public Collection<Ficha> listarFichas(int idUnidad) throws DAOException {
		log.info("Collection listarFichas()");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Collection<Ficha> array = new ArrayList<Ficha>();
		Ficha ficha = null;

		try {
			String query = "SELECT CVFI.CODIGO_CURSO,CVFI.IDFICHA,CVFI.ESTADO_FECHA,CVFI.ESTADO,CVFI.FECHA_INICIO,CVFI.FECHA_FIN,"
					+ "COFA.NOMCORTO NOMBREFAMILIA,COPR.NOMBRE NOMBRECURSO,GEDE.NOMBRE NOMBREDEPARTAMENTO,"
					+ "PKG_cv_ficha.FX_cv_ficha_FORMA(CVFI.IDFICHA) NOMBREFORMACION,GEPE.FECINICIO FECHAINICIOPERIODO,GEPE.FECFIN FECHAFINPERIODO, "
					+ "PKG_cv_ficha.FX_cv_ficha_SECCION(CVFI.IDFICHA) SECCIONES,CVFI.CODIGO_CICLO "
					+ "FROM cv_ficha CVFI,GENERAL.GEN_DEPARTAMENTO GEDE,comercial.COM_PRODUCTO COPR, CV_UNIDAD_SILABO S, CV_UNIDAD U,COMERCIAL.COM_FAMILIA COFA,CV_PERIODO GEPE "
					+ "WHERE CVFI.IDSILABO=S.IDSILABO AND S.IDUNIDAD=U.IDUNIDAD AND CVFI.CODIGO_DEPARTAMENTO=GEDE.CODIGO AND CVFI.CODIGO_CURSO=COPR.CODIGO AND CVFI.CODIGO_FAMILIA=COFA.CODIGO AND CVFI.CODIGO_PERIODO=GEPE.CODIGO "
					+ "AND U.IDUNIDAD=? ORDER BY NOMBREFAMILIA,NOMBREDEPARTAMENTO,NOMBRECURSO";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idUnidad);
			result =  stmt.executeQuery();

			while (result.next()) {
				ficha = new Ficha();
//				ficha.setCodigoCursoUDS(result.getInt("CODIGO_CURSO"));
//				ficha.setIdFicha(result.getInt("IDFICHA"));
//				ficha.setEstado(result.getInt("ESTADO"));
//				if (result.getInt("ESTADO_FECHA") != 0) {
//					ficha.setFechaInicio(Formato.getDateDeBaseDatos(result
//							.getString("FECHA_INICIO")));
//					ficha.setFechaFin(Formato.getDateDeBaseDatos(result
//							.getString("FECHA_FIN")));
//				} else {
//					ficha.setFechaInicio(Formato.getDateDeBaseDatos(result
//							.getString("FECHAINICIOPERIODO")));
//					ficha.setFechaFin(Formato.getDateDeBaseDatos(result
//							.getString("FECHAFINPERIODO")));
//				}
//				ficha.setNombreFamilia(result.getString("NOMBREFAMILIA"));
//				ficha.setNombreCursoUDS(result.getString("NOMBRECURSO"));
//				ficha.setNombreDepartamento(result
//						.getString("NOMBREDEPARTAMENTO"));
//				ficha.setNombreFormacion(result.getString("NOMBREFORMACION"));
//				ficha.setSecciones(result.getString("SECCIONES"));
//				ficha.setNivel(result.getInt("CODIGO_CICLO"));

				array.add(ficha);
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return array;
	}
	
	public void eliminar(Integer id) throws DAOException {
		log.info("eliminar(Integer id)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			cons =  dataSource.getConnection();
			String query = "delete from cv_unidad where idunidad = ?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			try {
				String query = "update cv_unidad set estado=0 where idunidad = ?";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, id);
				stmt.executeUpdate();
			} catch (Exception er) {
				log.error(er.toString());
				throw new DAOException(er.toString());
			}
		} catch (Exception e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} finally {
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	public Integer crear(Unidad unidad) throws DAOException {
		log.info("crear(Unidad unidad)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Integer id = null;
		try {
	
			String query = "insert into cv_unidad (idjerarquia,nombre,creado_en,modificado_en) " +
					"values(?,?,now(),now())";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, unidad.getJerarquia().getIdJerarquia());
			stmt.setString(2, unidad.getNombreCompleto());
			stmt.executeUpdate();
			result = stmt.getGeneratedKeys();
			if(result.next())id = result.getInt(1);
	
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
	
		return id;
	}
	
	public Collection<Unidad> listarUltimas() throws DAOException {
		log.info("listarUltimas()");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Collection<Unidad> array = new ArrayList<Unidad>();
		Unidad vo = null;
		try {
			String query = "select u.idunidad,u.idjerarquia,j.nombre jerarquia,u.nombre,u.creado_en,u.estado  " +
					"from cv_unidad u, cv_jerarquia j where u.idjerarquia = j.idjerarquia order by creado_en desc limit 0,5";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			result =  stmt.executeQuery();
			
			while (result.next()) {
				vo = new Unidad();
				vo.setIdUnidad(result.getInt("idunidad"));
				vo.setNombreCompleto(result.getString("nombre"));
				vo.setEstado(result.getInt("estado"));
				vo.setFechaCreacion(Formato.timestampToCalendar(result.getString("creado_en")));
				vo.setJerarquia(new Jerarquia(result.getInt("idjerarquia"),result.getString("jerarquia")));
				array.add(vo);
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return array;
	}

	public void modificar(Unidad unidad) throws DAOException {
		log.info("modificar(Unidad unidad)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "update cv_unidad set nombre=?,idjerarquia=?,modificado_en=now() where idunidad=?";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, unidad.getNombreCompleto());
			stmt.setInt(2, unidad.getJerarquia().getIdJerarquia());
			stmt.setInt(3, unidad.getIdUnidad());
			if (1 != stmt.executeUpdate()) {
				log.error("No se pudo insertar comando");
				throw new DAOException("No se pudo insertar comando");
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
	
	public void cambiarEstado(int id, int estado) throws DAOException {
		log.info("cambiarEstado(int id, int estado)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "update cv_unidad set estado=? where idunidad=?";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, estado);
			stmt.setInt(2, id);
			if (1 != stmt.executeUpdate()) {
				log.error("No se pudo insertar comando");
				throw new DAOException("No se pudo insertar comando");
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
//
//	public Unidad buscarPorId(int id) throws DAOException {
//		log.info("Unidad buscarPorId(int id)");
//		Connection cons = null;
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//
//		Unidad unidad = null;
//		Map<String, Recurso> recursos;
//		ResultSet subResult = null;
//		PreparedStatement subStmt = null;
//		Recurso recurso;
//		String general;
//		int numero = 0;
//		try {
//			String query = "SELECT U.IDUNIDAD,U.NOMBRE_CORTO,U.NOMBRE_COMPLETO,U.FECHA_CREACION, "
//					+ "U.USUARIO_CREACION,U.ESTADO,U.IDJERARQUIA,U.ALTO,U.ANCHO,U.FECHA_MOD,U.USUARIO_MOD,J.RUTA "
//					+ "FROM CV_UNIDAD U, CV_JERARQUIA J "
//					+ "WHERE U.IDJERARQUIA=J.IDJERARQUIA AND IDUNIDAD = ?";
//			cons =  dataSource.getConnection();
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, id);
//			result =  stmt.executeQuery();
//
//			query = "SELECT R.IDRECURSO, R.NOMBRE FROM CV_RECURSO R, CV_UNIDAD_RECURSO U "
//					+ "WHERE R.IDRECURSO=U.IDRECURSO AND IDUNIDAD=?";
//			stmt =  cons.prepareStatement(query);
//
//			query = "SELECT NOMBRE FROM CV_JERARQUIA WHERE IDJERARQUIA=?";
//			subStmt =  cons.prepareStatement(query);
//			if (result.next()) {
//
//				// **** SUBQUERY RECURSOS COLLECION ****//
//				stmt.setInt(1, result.getInt("IDUNIDAD"));
//				subResult =  stmt.executeQuery();
//				recursos = new HashMap<String, Recurso>();
//				numero = 0;
//				while (subResult.next()) {
//					recurso = new Recurso();
//					recurso.setIdRecurso(subResult.getInt("IDRECURSO"));
//					recurso.setNombre(subResult.getString("NOMBRE"));
//					recursos.put(numero + "", recurso);
//					numero++;
//				}
//				recursos = new TreeMap<String, Recurso>(recursos);
//				// *************************************//
//				// **** SUBQUERY JERARQUIA ****//
//				general = "/ ";
//				if ("/".equals(result.getString("RUTA"))) {
//					general += "Ra&iacute;z";
//				} else {
//					String[] array = result.getString("RUTA").split("/");
//					for (int o = 0; o < array.length - 1; o++) {
//						subStmt.setString(1, array[o]);
//						subResult =  subStmt.executeQuery();
//						if (subResult.next()) {
//							general += subResult.getString("NOMBRE") + " / ";
//						}
//					}
//				}
//				// *************************************//
//				unidad = new Unidad();
//				unidad.setFechaCreacion(Formato.getDateDeBaseDatos(result
//						.getString("FECHA_CREACION")));
//				unidad.setUsuarioCreacion(result.getString("USUARIO_CREACION"));
//				unidad.setFechaModificacion(Formato.getDateDeBaseDatos(result
//						.getString("FECHA_MOD")));
//				unidad.setUsuarioModificacion(result.getString("USUARIO_MOD"));
//				unidad.setIdUnidad(result.getString("IDUNIDAD"));
//				unidad.setNombreCorto(result.getString("NOMBRE_CORTO"));
//				unidad.setNombreCompleto(result.getString("NOMBRE_COMPLETO"));
//				unidad.setEstado(result.getInt("ESTADO"));
//				unidad.setIdJerarquia(result.getString("IDJERARQUIA"));
//				unidad.setAlto(result.getString("ALTO"));
//				unidad.setAncho(result.getString("ANCHO"));
//				unidad.setJerarquia(general);
//				unidad.setRecurso(recursos);
//				// unidades.add(unidad);
//			}
//			subStmt.close();
//		} catch (SQLException e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} catch (Exception e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} finally {
//			closeResultSet(subResult);
//			closeStatement(subStmt);
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//		return unidad;
//	}

}