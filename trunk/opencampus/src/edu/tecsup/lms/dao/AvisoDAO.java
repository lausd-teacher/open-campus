package edu.tecsup.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;


import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.modelo.Aviso;

public class AvisoDAO extends BaseDAO {

	protected DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Aviso cargarAviso() throws DAOException {
		log.info("cargarAviso()");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Aviso aviso = null;
		
		try {
			
			String query = "SELECT TITULAR,TIPO FROM CV_AVISO " +
					"WHERE ESTADO=1 AND ((FECHA IS NOT NULL AND FECHA > curdate()) OR FECHA IS NULL)";

			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				aviso = new Aviso();
				aviso.setTitular(result.getString("TITULAR"));
				aviso.setTipo(result.getString("TIPO"));
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
		return aviso;
	}
		
	//******************************** ADMIN ************************************
	
//	public Collection<Noticia> listar() throws DAOException {
//		log.info("listar()");
//		Connection cons = null;
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		Collection<Noticia> noticias = new ArrayList<Noticia>();
//		Noticia noticia = null;
//		Seccion seccion= null;
//		try {
//			String query = "SELECT N.IDNOTICIA,N.TITULAR,N.FECHA_INICIO,N.ESTADO,S.NOMBRE " +
//					"FROM CV_NOTICIA N, CV_NOTICIA_SECCION S WHERE N.IDSECCION=S.IDSECCION ORDER BY N.FECHA_INICIO DESC";
//
//			cons = (Connection)dataSource.getConnection();
//			stmt = (PreparedStatement) cons.prepareStatement(query);
//			
//			result = (ResultSet) stmt.executeQuery();
//			while (result.next()) {
//				noticia = new Noticia();
//				noticia.setIdnoticia(result.getInt("IDNOTICIA"));
//				noticia.setTitular(result.getString("TITULAR"));
//				noticia.setFecha(Formato.getDateDeBaseDatos(result.getString("FECHA_INICIO")));
//				noticia.setEstado(result.getInt("ESTADO"));
//				
//				seccion = new Seccion();
//				seccion.setNombre(result.getString("NOMBRE"));
//				
//				noticia.setSeccion(seccion);
//				
//				noticias.add(noticia);
//			}
//		} catch (SQLException e) {
//			log.error(e);
//			throw new DAOException(e.toString());
//		} catch (Exception e) {
//			log.error(e);
//			throw new DAOException(e.toString());
//		} finally {
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//		return noticias;
//	}
//		
//	public void cambiarEstado(int idNoticia, int estado) throws DAOException {
//		log.info("cambiarEstado(int "+idNoticia+", int "+estado+")");
//		Connection cons = null;
//		PreparedStatement stmt = null;
//		try {
//			String query = "UPDATE CV_NOTICIA SET ESTADO=? WHERE IDNOTICIA=?";
//
//			cons = (Connection)dataSource.getConnection();
//			stmt = (PreparedStatement) cons.prepareStatement(query);
//			stmt.setInt(1, estado);
//			stmt.setInt(2, idNoticia);
//			
//			if (1 != stmt.executeUpdate()) {
//				log.error("Error en cambiarEstado(int idNoticia, int estado)");
//				throw new DAOException("No culmino");
//			}
//			
//		} catch (SQLException e) {
//			log.error(e);
//			throw new DAOException(e.toString());
//		} catch (Exception e) {
//			log.error(e);
//			throw new DAOException(e.toString());
//		} finally {
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//	}
//	
//	public int crearNoticia(Noticia noticia) throws DAOException {
//		log.info("crearNoticia(Noticia noticia)");
//		Connection cons = null;
//		PreparedStatement stmt = null;
//		PreparedStatement stmt2 = null;
//		ResultSet result = null;
//		int id = 0;
//		try {
//			String query = null;
//
//			if(null != noticia.getImagen()){
//				query = "INSERT INTO CV_NOTICIA (IDNOTICIA,TITULAR,SUMILLA,CUERPO,FECHA_INICIO,IDSECCION,FORMATO,ESTADO,USUARIO_CREACION,FECHA_CREACION,USUARIO_MOD,FECHA_MOD,IMAGEN_NOMBRE) " +
//				"VALUES (SEQCVNOTICIA.NEXTVAL,?,?,?,?,?,?,?,?,sysdate,?,sysdate,? || LPAD(SEQCVNOTICIA.CURRVAL,10,0) || ?)";
//			}else{
//				query = "INSERT INTO CV_NOTICIA (IDNOTICIA,TITULAR,SUMILLA,CUERPO,FECHA_INICIO,IDSECCION,FORMATO,ESTADO,USUARIO_CREACION,FECHA_CREACION,USUARIO_MOD,FECHA_MOD) " +
//				"VALUES (SEQCVNOTICIA.NEXTVAL,?,?,?,?,?,?,?,?,sysdate,?,sysdate)";
//			}
//				
//			cons = (Connection)dataSource.getConnection();
//			cons.setAutoCommit(false);
//			stmt = (PreparedStatement) cons.prepareStatement(query);
//			stmt.setString(1, noticia.getTitular());
//			stmt.setString(2, noticia.getSumilla());
//			stmt.setString(3, noticia.getCuerpo());
//			stmt.setString(4, Formato.setBaseDatosDeDate(noticia.getFecha()));
//			stmt.setInt(5, noticia.getSeccion().getIdSeccion());
//			stmt.setInt(6, noticia.getFormato());
//			stmt.setInt(7, noticia.getEstado());
//			stmt.setString(8, noticia.getUsuarioCreacion());
//			stmt.setString(9, noticia.getUsuarioCreacion());
//			
//			if(null != noticia.getImagen()){
//				stmt.setString(10, Constante.IMAGEN +Constante.UNDERLINE);
//				stmt.setString(11, noticia.getImagen());
//			}
//			
//			if (1 != stmt.executeUpdate()) {
//				log.error("Error en crearNoticia(Noticia noticia) - INSERT INTO CV_NOTICIA");
//				throw new DAOException("No culmino");
//			}
//			
//			// REGLA ************************************************
//			
//			query = "INSERT INTO CV_NOTICIA_REGLA (IDREGLA,IDNOTICIA) " +
//					"VALUES(SEQCVREGLASERVICIO.CURRVAL,SEQCVNOTICIA.CURRVAL)";
//			stmt2 = (PreparedStatement) cons.prepareStatement(query);
//			
//			query = "INSERT INTO CV_REGLA_SERVICIO (IDREGLA,SEDE,FAMILIA,FORMACION,CICLO,SECCION) " +
//					"VALUES(SEQCVREGLASERVICIO.NEXTVAL,?,?,?,?,?)";
//			stmt = (PreparedStatement) cons.prepareStatement(query);
//			
//			for (ReglaDeServicio rs: noticia.getReglaDeServicio()) {
//				stmt.setString(1, String.valueOf(rs.getSede()));
//				stmt.setInt(2, rs.getFamilia());
//				stmt.setInt(3, rs.getFormacion());
//				stmt.setInt(4, rs.getCiclo());
//				stmt.setInt(5, rs.getSeccion());
//				
//				if (1 != stmt.executeUpdate()) {
//					log.error("Error en crearNoticia(Noticia noticia) - INSERT INTO CV_REGLA_SERVICIO");
//					throw new DAOException("No culmino");
//				}
//				
//				if (1 != stmt2.executeUpdate()) {
//					log.error("Error en crearNoticia(Noticia noticia) - INSERT INTO CV_NOTICIA_REGLA");
//					throw new DAOException("No culmino");
//				}
//				
//			}
//			
//			// IDNOTICIA ********************************************
//			
//			query = "SELECT SEQCVNOTICIA.CURRVAL ID FROM DUAL";
//			stmt = (PreparedStatement) cons.prepareStatement(query);
//			result = (ResultSet) stmt.executeQuery();
//			if(result.next())
//				id = result.getInt("ID");
//			
//			// Transaccion exitosa
//			cons.commit();
//		} catch (SQLException e) {
//			log.error(e);
//			throw new DAOException(e.toString());
//		} catch (Exception e) {
//			log.error(e);
//			throw new DAOException(e.toString());
//		} finally {
//			try {
//				cons.rollback();
//			} catch (SQLException e) {
//				log.error(e);
//			}
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeStatement(stmt2);
//			closeConnection(cons);
//		}
//		return id;
//	}
//	
//	public void modificarNoticia(Noticia noticia) throws DAOException {
//		log.info("modificarNoticia(Noticia noticia)");
//		Connection cons = null;
//		PreparedStatement stmt = null;
//		PreparedStatement stmt2 = null;
//		ResultSet result = null;
//		
//		try {
//			String query = null;
//
//			if(null != noticia.getImagen()){
//				query = "UPDATE CV_NOTICIA SET TITULAR=?, SUMILLA=?, CUERPO=?, FECHA_INICIO=?, IDSECCION=?, FORMATO=?, " +
//						"USUARIO_MOD=?, FECHA_MOD=SYSDATE, IMAGEN_NOMBRE=? " +
//						"WHERE IDNOTICIA=?";
//			}else{
//				query = "UPDATE CV_NOTICIA SET TITULAR=?, SUMILLA=?, CUERPO=?, FECHA_INICIO=?, IDSECCION=?, FORMATO=?, " +
//						"USUARIO_MOD=?, FECHA_MOD=SYSDATE " +
//						"WHERE IDNOTICIA=?";
//			}
//				
//			cons = (Connection)dataSource.getConnection();
//			cons.setAutoCommit(false);
//			stmt = (PreparedStatement) cons.prepareStatement(query);
//			stmt.setString(1, noticia.getTitular());
//			stmt.setString(2, noticia.getSumilla());
//			stmt.setString(3, noticia.getCuerpo());
//			stmt.setString(4, Formato.setBaseDatosDeDate(noticia.getFecha()));
//			stmt.setInt(5, noticia.getSeccion().getIdSeccion());
//			stmt.setInt(6, noticia.getFormato());
//			stmt.setString(7, noticia.getUsuarioModificacion());
//			
//			if(null != noticia.getImagen()){
//				stmt.setString(8, noticia.getImagen());
//				stmt.setInt(9, noticia.getIdnoticia());
//			}else{
//				stmt.setInt(8, noticia.getIdnoticia());
//			}
//			
//			if (1 != stmt.executeUpdate()) {
//				log.error("Error en crearNoticia(Noticia noticia) - UPDATE CV_NOTICIA");
//				throw new DAOException("No culmino");
//			}
//			
//			// REGLA ************************************************
//			
//			query = "SELECT IDREGLA FROM CV_NOTICIA_REGLA WHERE IDNOTICIA=?";
//			
//			stmt = (PreparedStatement) cons.prepareStatement(query);
//			stmt.setInt(1, noticia.getIdnoticia());
//			
//			result = (ResultSet) stmt.executeQuery();
//			
//			// ELIMINAR LA RELACION DE REGLA-NOTICIA *********
//			
//			query = "DELETE FROM CV_NOTICIA_REGLA WHERE IDNOTICIA=?";
//
//			stmt = (PreparedStatement) cons.prepareStatement(query);
//			stmt.setInt(1, noticia.getIdnoticia());
//			
//			int row = stmt.executeUpdate();
//			log.info("CV_NOTICIA_REGLA: Registros eliminados: "+row);
//			
//			// ELIMINAR LAS REGLAS DE LA NOTICIA ************
//			
//			query = "DELETE FROM CV_REGLA_SERVICIO WHERE IDREGLA=?";
//			stmt = (PreparedStatement) cons.prepareStatement(query);
//			
//			while (result.next()) {
//				row = result.getInt("IDREGLA");
//				stmt.setInt(1, row);
//				if (1 != stmt.executeUpdate()) {
//					log.error("Error al intentar eliminar regla de id: " + row);
//					throw new DAOException("No culmino");
//				}
//			}
//			
//			//INSERTAR LA NUEVA RELACION DE REGLA-NOTICIA *********
//			
//			query = "INSERT INTO CV_NOTICIA_REGLA (IDREGLA,IDNOTICIA) " +
//					"VALUES(SEQCVREGLASERVICIO.CURRVAL,?)";
//			stmt2 = (PreparedStatement) cons.prepareStatement(query);
//			stmt2.setInt(1, noticia.getIdnoticia());
//			
//			query = "INSERT INTO CV_REGLA_SERVICIO (IDREGLA,SEDE,FAMILIA,FORMACION,CICLO,SECCION) " +
//					"VALUES(SEQCVREGLASERVICIO.NEXTVAL,?,?,?,?,?)";
//			stmt = (PreparedStatement) cons.prepareStatement(query);
//			
//			for (ReglaDeServicio rs: noticia.getReglaDeServicio()) {
//				stmt.setString(1, String.valueOf(rs.getSede()));
//				stmt.setInt(2, rs.getFamilia());
//				stmt.setInt(3, rs.getFormacion());
//				stmt.setInt(4, rs.getCiclo());
//				stmt.setInt(5, rs.getSeccion());
//				
//				if (1 != stmt.executeUpdate()) {
//					log.error("Error en crearNoticia(Noticia noticia) - INSERT INTO CV_REGLA_SERVICIO");
//					throw new DAOException("No culmino");
//				}
//				
//				if (1 != stmt2.executeUpdate()) {
//					log.error("Error en crearNoticia(Noticia noticia) - INSERT INTO CV_NOTICIA_REGLA");
//					throw new DAOException("No culmino");
//				}
//				
//			}
//			
//			//******************************************************
//			
//			// Transaccion exitosa
//			cons.commit();
//		} catch (SQLException e) {
//			log.error(e);
//			throw new DAOException(e.toString());
//		} catch (Exception e) {
//			log.error(e);
//			throw new DAOException(e.toString());
//		} finally {
//			try {
//				cons.rollback();
//			} catch (SQLException e) {
//				log.error(e);
//			}
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeStatement(stmt2);
//			closeConnection(cons);
//		}
//	}
//	
//	public void eliminarNoticia(int idNoticia) throws DAOException {
//		log.info("eliminarNoticia(int "+idNoticia+")");
//		Connection cons = null;
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		try {
//			
//			// CONSULTA DE REGLAS PORA LA NOTICIA *************
//			
//			String query = "SELECT IDREGLA FROM CV_NOTICIA_REGLA WHERE IDNOTICIA=?";
//			cons = (Connection) dataSource.getConnection();
//			cons.setAutoCommit(false);
//
//			stmt = (PreparedStatement) cons.prepareStatement(query);
//			stmt.setInt(1, idNoticia);
//			
//			result = (ResultSet) stmt.executeQuery();
//			
//			// ELIMINAR LA RELACION DE REGLA-NOTICIA *********
//			
//			query = "DELETE FROM CV_NOTICIA_REGLA WHERE IDNOTICIA=?";
//
//			stmt = (PreparedStatement) cons.prepareStatement(query);
//			stmt.setInt(1, idNoticia);
//			
//			int row = stmt.executeUpdate();
//			log.info("CV_NOTICIA_REGLA: Registros eliminados: "+row);
//			
//			// ELIMINAR LAS REGLAS DE LA NOTICIA ************
//			
//			query = "DELETE FROM CV_REGLA_SERVICIO WHERE IDREGLA=?";
//			stmt = (PreparedStatement) cons.prepareStatement(query);
//			
//			while (result.next()) {
//				row = result.getInt("IDREGLA");
//				stmt.setInt(1, row);
//				if (1 != stmt.executeUpdate()) {
//					log.error("Error al intentar eliminar regla de id: " + row);
//					throw new DAOException("No culmino");
//				}
//			}
//			// ELIMINAR LA NOTICIA ***************************
//			
//			query = "DELETE FROM CV_NOTICIA WHERE IDNOTICIA=?";
//
//			stmt = (PreparedStatement) cons.prepareStatement(query);
//			stmt.setInt(1, idNoticia);
//			
//			if (1 != stmt.executeUpdate()) {
//				log.error("Error al intentar eliminar noticia de id: " + idNoticia);
//				throw new DAOException("No culmino");
//			}
//			
//			//Transaccion exitosa
//			cons.commit();
//		} catch (SQLException e) {
//			log.error(e);
//			throw new DAOException(e.toString());
//		} catch (DAOException e) {
//			log.error(e.getMessage());
//			throw e;
//		} catch (Exception e) {
//			log.error(e);
//			throw new DAOException(e.toString());
//		} finally {
//			try {
//				cons.rollback();
//			} catch (SQLException e) {
//				log.error(e);
//			}
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//	}

}