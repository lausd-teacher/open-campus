package edu.opencampus.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.modelo.Foro;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.modelo.foro.Mensaje;
import edu.opencampus.lms.modelo.foro.Tema;
import edu.opencampus.lms.util.Formato;

public class ForoUsuarioDAO extends BaseDAO {
	
	protected Log log = LogFactory.getLog(getClass());
	
	protected DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	/*
	public Foro obtenerForo(int idForo, String idUsuario) throws DAOException {
		log.info("obtenerForo(int "+idForo+", String "+idUsuario+")");
		Foro foro = null;
		Connection cons = null;
		PreparedStatement stmt = null;		
		ResultSet result = null;		
		try { 
			String query = "SELECT F.TITULO, F.IDFORO, F.CERRADO, " +
					"(SELECT COUNT(*) FROM CV_FORO_MODERADOR WHERE IDFORO = F.IDFORO AND TRIM(USUARIO)=?) MODERADOR " +
					"FROM CV_FORO F WHERE F.IDFORO = ? AND F.ESTADO = 1";
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, idUsuario);
			stmt.setInt(2, idForo);
			result = (ResultSet) stmt.executeQuery();
			if(result.next()){	
				foro = new Foro();
				foro.setIdForo(result.getInt("IDFORO"));
				foro.setTitulo(result.getString("TITULO"));
				foro.setCerrado(result.getInt("CERRADO"));
				if(result.getInt("MODERADOR") > 0)
					foro.setYoLoModero(true);
			}
		}catch (Exception e) {
			log.error(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return foro;
	}
	
	public Collection<Foro> obtenerForos(String idUsuario, Set<Integer> misForos) throws DAOException{		
		log.info("obtenerForos(String "+idUsuario+", Set<Integer> misForos)");
		Collection<Foro> foros = new ArrayList<Foro>();
		Foro foro = null;
		Tema ultimoTema = null;
		Usuario moderador = null;
		Collection<Usuario> moderadores = null;
		Connection cons = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		//OracleCallableStatement callstmt = null;
		ResultSet result = null;
		ResultSet result2 = null;
		
		String query = "{CALL  PKG_CV_SERVICIO.SP_CV_SERVICIO_PRE(?)}";
		try {
			cons = (Connection)dataSource.getConnection();
			cons.setAutoCommit(false);
//			callstmt = (OracleCallableStatement) cons.prepareCall(query);
//			callstmt.setString(1, idUsuario);
//			callstmt.execute();
			
			query = "SELECT DISTINCT F.IDFORO, F.TITULO, F.DESCRIPCION, F.ICONO, F.CERRADO, F.ESTADO, F.USUARIO_CREACION, F.FECHA_CREACION," +
					"(SELECT COUNT(*) FROM CV_FORO_TEMA WHERE IDFORO = F.IDFORO AND ESTADO = 1) TEMAS," +
					"(SELECT COUNT(*) FROM CV_FORO_TEMA T, CV_FORO_MENSAJE M WHERE T.IDTEMA=M.IDTEMA AND M.IDFORO = F.IDFORO AND T.ESTADO=1 AND M.ESTADO = 1" +
					" AND M.IDMENSAJE != (SELECT MIN(IDMENSAJE) FROM CV_FORO_MENSAJE WHERE IDFORO=M.IDFORO AND IDTEMA = M.IDTEMA)) MENSAJES," +
					"(SELECT COUNT(*) FROM CV_FORO_MODERADOR WHERE IDFORO = F.IDFORO AND TRIM(USUARIO)=?) MODERADOR " +
					"FROM  " +
					"(SELECT DISTINCT SEDE,FAMILIA,FORMACION,CICLO,SECCION FROM CV_TEMP_NOTICIA  " +
					"UNION  " +
					"SELECT DISTINCT SEDE,FAMILIA,FORMACION,CICLO,0 FROM CV_TEMP_NOTICIA  " +
					"UNION  " +
					"SELECT DISTINCT SEDE,FAMILIA,FORMACION,0,0 FROM CV_TEMP_NOTICIA  " +
					"UNION  " +
					"SELECT DISTINCT SEDE,FAMILIA,0,0,0 FROM CV_TEMP_NOTICIA  " +
					"UNION  " +
					"SELECT DISTINCT SEDE,0,0,0,0 FROM CV_TEMP_NOTICIA  " +
					"UNION  " +
					"SELECT DISTINCT '0',0,0,0,0 FROM DUAL) R, " +
					"(SELECT F.IDFORO, F.TITULO, F.USUARIO_CREACION, F.FECHA_CREACION, F.ESTADO, F.CERRADO, F.ICONO, F.DESCRIPCION,  " +
					"S.SEDE, S.FAMILIA, S.FORMACION, S.CICLO, S.SECCION  " +
					"FROM CV_FORO F, CV_REGLA_SERVICIO S, CV_FORO_REGLA FR  " +
					"WHERE F.IDFORO = FR.IDFORO AND S.IDREGLA = FR.IDREGLA AND F.ESTADO = 1) F " +
					"WHERE R.SEDE=F.SEDE AND R.FAMILIA=F.FAMILIA AND R.FORMACION=F.FORMACION AND R.CICLO=F.CICLO AND R.SECCION=F.SECCION order by f.fecha_creacion desc";			
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, idUsuario);
			result = (ResultSet) stmt.executeQuery();
			
			//Ultimo tema
			query = "SELECT T.TITULO, T.FECHA_CREACION, P.APEPATERNO, P.APEMATERNO, P.NOMUNO, P.NOMDOS " +
					"FROM CV_FORO_TEMA T, SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P " +
					"WHERE TRIM(U.USUARIO) = T.USUARIO_CREACION AND U.CODSUJETO = P.CODPERSONA AND T.ESTADO = '1' " +
					"AND T.IDFORO=? AND T.FECHA_CREACION = (SELECT MAX(FECHA_CREACION) FROM CV_FORO_TEMA WHERE IDFORO=T.IDFORO AND ESTADO = '1')";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			
			//MODERADORES
			query = "SELECT TRIM(M.USUARIO) USUARIO, P.APEPATERNO, P.APEMATERNO, P.NOMUNO, P.NOMDOS " +
					"FROM CV_FORO_MODERADOR M, SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P " +
					"WHERE M.USUARIO=U.USUARIO AND U.CODSUJETO = P.CODPERSONA AND M.IDFORO=?";
			stmt2 = (PreparedStatement) cons.prepareStatement(query);
			
			while(result.next()){
				foro = new Foro();
				foro.setIdForo(result.getInt("IDFORO"));
				foro.setTitulo(result.getString("TITULO"));
				foro.setDescripcion(result.getString("DESCRIPCION"));
				foro.setIcono(result.getInt("ICONO"));
				foro.setEstado(result.getInt("ESTADO"));
				foro.setCerrado(result.getInt("CERRADO"));
				foro.setUsuarioCreacion(result.getString("USUARIO_CREACION"));
				foro.setTotalTemas(result.getInt("TEMAS"));
				foro.setTotalMensajes(result.getInt("MENSAJES"));
				
				stmt.setInt(1, foro.getIdForo());
				result2 = (ResultSet) stmt.executeQuery();
				if(result2.next()){
					ultimoTema = new Tema();				
					ultimoTema.setTitulo(result2.getString("TITULO"));
					ultimoTema.setFechaCreacion(Formato.getDateDeBaseDatos(result2.getString("FECHA_CREACION")));
					ultimoTema.setNombreUsuario(Formato.formatoTexto(result2.getString("NOMUNO")+" "+result2.getString("APEPATERNO")));
					foro.setUltimoTema(ultimoTema);
				}	
				moderadores = new ArrayList<Usuario>();
				stmt2.setInt(1, foro.getIdForo());
				result2 = (ResultSet) stmt2.executeQuery();
				while(result2.next()){
					moderador = new Usuario();
					moderador.setIdUsuario(result2.getString("USUARIO"));
					moderador.setNombreCompleto(Formato.formatoTexto(result2.getString("NOMUNO")+" "+result2.getString("APEPATERNO")));
					moderadores.add(moderador);
				}
				foro.setModeradores(moderadores);
				
				// Mis Foros
				misForos.add(result.getInt("IDFORO"));
				//Mis Foros a moderar
				if(result.getInt("MODERADOR") > 0)
					foro.setYoLoModero(true);
				
				foros.add(foro);
			}		
		} catch (SQLException e) {
			log.error(e.toString());
		} finally {
			try {
				cons.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeResultSet(result);
			closeResultSet(result2);
			closeStatement(stmt);
			closeStatement(stmt2);
			closeConnection(cons);
		}
		return foros;
	}
	
	public Collection<Foro> obtenerForos(Set<Integer> misForos) throws DAOException{	
		log.info("obtenerForos(Set<Integer> misForos)");
		Collection<Foro> foros = new ArrayList<Foro>();
		Foro foro = null;
		Tema ultimoTema = null;
		Usuario moderador = null;
		Collection<Usuario> moderadores = null;
		Connection cons = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet result = null;
		ResultSet result2 = null;
		
		try {
			cons = (Connection)dataSource.getConnection();
						
			String query = "SELECT DISTINCT F.IDFORO, F.TITULO, F.DESCRIPCION, F.ICONO, F.CERRADO, F.ESTADO, F.FECHA_CREACION, " +
					"(SELECT COUNT(*) FROM CV_FORO_TEMA WHERE IDFORO = F.IDFORO AND ESTADO = 1) TEMAS, " +
					"(SELECT COUNT(*) FROM CV_FORO_TEMA T, CV_FORO_MENSAJE M WHERE T.IDTEMA=M.IDTEMA AND M.IDFORO = F.IDFORO AND T.ESTADO=1 AND M.ESTADO = 1 " +
					"AND M.IDMENSAJE != (SELECT MIN(IDMENSAJE) FROM CV_FORO_MENSAJE WHERE IDFORO=M.IDFORO AND IDTEMA = M.IDTEMA)) MENSAJES " +
					"FROM CV_FORO F WHERE  F.ESTADO = 1 " +
					"AND F.IDFORO != 10 " + //Admin no ve cafeteria
					"ORDER BY F.FECHA_CREACION DESC";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			result = (ResultSet) stmt.executeQuery();
			
			//Ultimo tema
			query = "SELECT T.TITULO, T.FECHA_CREACION, P.APEPATERNO, P.APEMATERNO, P.NOMUNO, P.NOMDOS " +
					"FROM CV_FORO_TEMA T, SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P " +
					"WHERE TRIM(U.USUARIO) = T.USUARIO_CREACION AND U.CODSUJETO = P.CODPERSONA AND T.ESTADO = '1' " +
					"AND T.IDFORO=? AND T.FECHA_CREACION = (SELECT MAX(FECHA_CREACION) FROM CV_FORO_TEMA WHERE IDFORO=T.IDFORO AND ESTADO = '1')";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			
			//MODERADORES
			query = "SELECT TRIM(M.USUARIO) USUARIO, P.APEPATERNO, P.APEMATERNO, P.NOMUNO, P.NOMDOS " +
					"FROM CV_FORO_MODERADOR M, SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P " +
					"WHERE M.USUARIO=U.USUARIO AND U.CODSUJETO = P.CODPERSONA AND M.IDFORO=?";
			stmt2 = (PreparedStatement) cons.prepareStatement(query);
			
			while(result.next()){
				foro = new Foro();
				foro.setIdForo(result.getInt("IDFORO"));
				foro.setTitulo(result.getString("TITULO"));
				foro.setDescripcion(result.getString("DESCRIPCION"));
				foro.setIcono(result.getInt("ICONO"));
				foro.setEstado(result.getInt("ESTADO"));
				foro.setCerrado(result.getInt("CERRADO"));
				foro.setTotalTemas(result.getInt("TEMAS"));
				foro.setTotalMensajes(result.getInt("MENSAJES"));
				
				stmt.setInt(1, foro.getIdForo());
				result2 = (ResultSet) stmt.executeQuery();
				if(result2.next()){
					ultimoTema = new Tema();				
					ultimoTema.setTitulo(result2.getString("TITULO"));
					ultimoTema.setFechaCreacion(Formato.getDateDeBaseDatos(result2.getString("FECHA_CREACION")));
					ultimoTema.setNombreUsuario(Formato.formatoTexto(result2.getString("NOMUNO")+" "+result2.getString("APEPATERNO")));
					foro.setUltimoTema(ultimoTema);
				}	
				moderadores = new ArrayList<Usuario>();
				stmt2.setInt(1, foro.getIdForo());
				result2 = (ResultSet) stmt2.executeQuery();
				while(result2.next()){
					moderador = new Usuario();
					moderador.setIdUsuario(result2.getString("USUARIO"));
					moderador.setNombreCompleto(Formato.formatoTexto(result2.getString("NOMUNO")+" "+result2.getString("APEPATERNO")));
					moderadores.add(moderador);
				}
				foro.setModeradores(moderadores);
				
				// Mis Foros
				misForos.add(result.getInt("IDFORO"));
				//Mis Foros a moderar
				foro.setYoLoModero(true);
				
				foros.add(foro);
			}		
		} catch (SQLException e) {
			log.error(e.toString());
		} finally {
			closeResultSet(result);
			closeResultSet(result2);
			closeStatement(stmt);
			closeStatement(stmt2);
			closeConnection(cons);
		}
		return foros;
	}
	
	public List<Tema> obtenerTemas(int idForo) throws DAOException {
		log.info("obtenerTemas(int "+idForo+")");
		List<Tema> temas = new ArrayList<Tema>();
		Tema tema = null;
		Mensaje ultimoMensaje = null;
		Connection cons = null;
		PreparedStatement stmt = null;		
		ResultSet result = null;		
		ResultSet result2 = null;		
		
		try{			
			String query = "SELECT T.IDTEMA, T.TITULO, T.ICONO, T.CERRADO, T.FECHA_CREACION," +
					"T.USUARIO_CREACION, U.USUARIO, P.APEPATERNO, P.NOMUNO," +
					"(SELECT COUNT(*) FROM CV_FORO_MENSAJE WHERE IDFORO=T.IDFORO AND IDTEMA = T.IDTEMA AND ESTADO = 1) MENSAJES," +
					"(SELECT NVL(AVG(VALOR),0) FROM CV_FORO_TEMA_VALOR WHERE IDFORO=T.IDFORO AND IDTEMA = T.IDTEMA) VALORACION," +
					"(SELECT COUNT(*) FROM CV_FORO_TEMA_VALOR WHERE IDFORO=T.IDFORO AND IDTEMA = T.IDTEMA) TOTALVOTOS " +
					"FROM CV_FORO_TEMA T, SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P " +
					"WHERE T.ESTADO = 1 AND TRIM(U.USUARIO) = T.USUARIO_CREACION AND U.CODSUJETO = P.CODPERSONA " +
					"AND  T.IDFORO = ? ORDER BY T.FECHA_CREACION DESC";
			
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idForo);
			result = (ResultSet) stmt.executeQuery();
			
			query = "SELECT M.CUERPO, M.FECHA_CREACION, P.APEPATERNO, P.NOMUNO " +
					"FROM CV_FORO_MENSAJE M, SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P " +
					"WHERE TRIM(U.USUARIO) = M.USUARIO_CREACION AND U.CODSUJETO = P.CODPERSONA AND M.ESTADO = 1 " +
					"AND M.IDMENSAJE = (SELECT MAX(IDMENSAJE) FROM CV_FORO_MENSAJE WHERE IDFORO=M.IDFORO AND IDTEMA = M.IDTEMA) " +
					"AND M.IDMENSAJE != (SELECT MIN(IDMENSAJE) FROM CV_FORO_MENSAJE WHERE IDFORO=M.IDFORO AND IDTEMA = M.IDTEMA) " +
					"AND IDFORO = ? AND IDTEMA = ?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idForo);
			
			while(result.next()){
				tema = new Tema();
				tema.setIdForo(idForo);
				tema.setIdTema(result.getInt("IDTEMA"));
				tema.setTitulo(result.getString("TITULO"));				
				tema.setIcono(result.getInt("ICONO"));
				tema.setCerrado(result.getInt("CERRADO"));
				tema.setUsuarioCreacion(result.getString("USUARIO_CREACION"));
				tema.setFechaCreacion(Formato.getDateDeBaseDatos(result.getString("FECHA_CREACION")));				
				tema.setNombreUsuario(Formato.formatoTexto(result.getString("NOMUNO")+" "+result.getString("APEPATERNO")));				
				tema.setTotalRespuestas(result.getInt("MENSAJES"));
				tema.setValoracion((result.getInt("VALORACION")));
				tema.setTotalVotos(result.getInt("TOTALVOTOS"));
				
				// Ultimo mensaje
				stmt.setInt(2, tema.getIdTema());
				result2 = (ResultSet) stmt.executeQuery();
				if(result2.next()){
					ultimoMensaje = new Mensaje();	
					ultimoMensaje.setCuerpo(result2.getString("CUERPO"));
					ultimoMensaje.setFechaCreacion(Formato.getDateDeBaseDatos(result2.getString("FECHA_CREACION")));
					ultimoMensaje.setNombreUsuario(Formato.formatoTexto(result2.getString("NOMUNO")+" "+result2.getString("APEPATERNO")));
					tema.setUltimoMensaje(ultimoMensaje);
				}				
				temas.add(tema);				
			}
		}catch (SQLException e) {
			log.error(e.toString());
		} finally {
			closeResultSet(result);
			closeResultSet(result2);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return temas;
	}
	
	public void cambiarEstado(int idForo, int idTema, int estado) throws DAOException {
		log.info("cambiarEstado(int "+idForo+",int "+idTema+", int "+estado+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "UPDATE CV_FORO_TEMA SET CERRADO=? WHERE IDFORO=? AND IDTEMA=?";

			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, estado);
			stmt.setInt(2, idForo);
			stmt.setInt(3, idTema);
			
			if (1 != stmt.executeUpdate()) {
				log.info("cambiarEstado(int "+idForo+",int "+idTema+", int "+estado+")");
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

	public void eliminarTema(int idForo, int idTema) throws DAOException {
		log.info("eliminarTema(int "+idForo+", int "+idTema+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			
			String query = "UPDATE CV_FORO_TEMA SET ESTADO=0 WHERE IDFORO=? AND IDTEMA=?";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idForo);
			stmt.setInt(2, idTema);
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error al intentar eliminar tema de id: " + idTema);
				throw new DAOException("No culmino");
			}
			
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
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	public void eliminarMensaje(int idForo, int idTema, int idMensaje) throws DAOException {
		log.info("eliminarMensaje(int "+idForo+", int "+idTema+", int "+idMensaje+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			
			String query = "UPDATE CV_FORO_MENSAJE SET ESTADO=0 WHERE IDFORO=? AND IDTEMA=? AND IDMENSAJE=?";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idForo);
			stmt.setInt(2, idTema);
			stmt.setInt(3, idMensaje);
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error al intentar eliminar mensaje de id: " + idMensaje);
				throw new DAOException("No culmino");
			}
			
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
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	public void nuevoTema(Tema tema) throws DAOException{
		log.info("nuevoTema(Tema tema)");
		PreparedStatement stmt = null;
		Connection cons = null;
		
		try{
			String query = "INSERT INTO CV_FORO_TEMA(IDFORO,IDTEMA,USUARIO_CREACION,FECHA_CREACION, ESTADO, CERRADO, TITULO, ICONO)" +
					"VALUES(?,SEQCVFOROTEMA.nextval,?,SYSDATE,1,0,?,0)";
			cons = (Connection) dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, tema.getIdForo());
			stmt.setString(2, tema.getUsuarioCreacion());
			stmt.setString(3, tema.getTitulo());
			if (1 != stmt.executeUpdate()) {
				log.error("Error en nuevoTema(Tema tema) - INSERT INTO CV_FORO_TEMA");
				throw new DAOException("No culmino");
			}
			
			query = "INSERT INTO CV_FORO_MENSAJE(IDFORO, IDTEMA, IDMENSAJE, CUERPO, USUARIO_CREACION, FECHA_CREACION, ESTADO) " +
					"VALUES (?,SEQCVFOROTEMA.CURRVAL,SEQCVFOROMENSAJE.nextval,?,?,SYSDATE,1)";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, tema.getIdForo());
			stmt.setString(2, tema.getCuerpo());
			stmt.setString(3, tema.getUsuarioCreacion());
			if (1 != stmt.executeUpdate()) {
				log.error("Error en nuevoTema(Tema tema) - INSERT INTO CV_FORO_MENSAJE");
				throw new DAOException("No culmino");
			}
			// Transaccion exitosa
			cons.commit();
		}catch (SQLException e) {
			log.error(e.toString());
		} finally {
			try {
				cons.rollback();
			} catch (SQLException e) {
				log.error(e.toString());
			}
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	public List<Mensaje> obtenerMensajes(int idForo, int idTema) throws DAOException{
		log.info("obtenerMensajes(int idForo, int idTema)");
		Connection cons = null;
		PreparedStatement stmt = null;		
		ResultSet result = null;		
		
		List<Mensaje> mensajes = new ArrayList<Mensaje>();
		
			String query = "SELECT T.CUERPO, T.IDFORO, T.IDTEMA, T.IDMENSAJE, T.MODERADO,T.USUARIO_CREACION, T.FECHA_CREACION, T.IDMENSAJE_CITA, P.APEPATERNO, P.APEMATERNO, P.NOMUNO, P.SEXO " +
					"FROM CV_FORO_MENSAJE T, SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P " +
					"WHERE IDFORO = ? AND IDTEMA = ? AND T.ESTADO = 1 AND TRIM(U.USUARIO) = USUARIO_CREACION AND U.CODSUJETO = P.CODPERSONA ORDER BY FECHA_CREACION";
			try {
				cons = (Connection)dataSource.getConnection();				
				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt.setInt(1, idForo);
				stmt.setInt(2, idTema);
				result = (ResultSet) stmt.executeQuery();
				while(result.next()){
					Mensaje mensaje = new Mensaje();
					mensaje.setCuerpo(result.getString("CUERPO"));
					mensaje.setIdMensaje(result.getInt("IDMENSAJE"));
					mensaje.setIdForo(result.getInt("IDFORO"));
					mensaje.setIdTema(result.getInt("IDTEMA"));	
					mensaje.setModerado(result.getInt("MODERADO"));
					mensaje.setIdMensaje_cita(result.getInt("IDMENSAJE_CITA"));
					mensaje.setUsuarioCreacion(result.getString("USUARIO_CREACION"));
					mensaje.setFechaCreacion(Formato.getDateDeBaseDatos(result.getString("FECHA_CREACION")));
					mensaje.setNombreUsuario(Formato.formatoTexto(result.getString("NOMUNO")+" "+result.getString("APEPATERNO")+" "+result.getString("APEMATERNO")));						
					mensaje.setSexoUsuario((result.getString("SEXO")));
					mensajes.add(mensaje);
				}
			} catch (SQLException e) {
				log.error(e.toString());
			} finally {				
				closeResultSet(result);
				closeStatement(stmt);
				closeConnection(cons);
			}		
		return mensajes;
	}
	
	public void nuevoMensaje(Mensaje mensaje) throws DAOException {
		log.info("nuevoMensaje(Mensaje mensaje)");
		PreparedStatement stmt = null;
		Connection cons = null;
		
		try{
			String query = "INSERT INTO CV_FORO_MENSAJE (IDFORO,IDTEMA,IDMENSAJE,IDFORO_CITA,IDTEMA_CITA,IDMENSAJE_CITA,CUERPO,ESTADO,MODERADO,USUARIO_CREACION,FECHA_CREACION,USUARIO_MOD,FECHA_MOD) " +
					"VALUES (?,?,SEQCVFOROMENSAJE.NEXTVAL,?,?,?,?,1,?,?,SYSDATE,?,SYSDATE)";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, mensaje.getIdForo());
			stmt.setInt(2, mensaje.getIdTema());
			stmt.setInt(3, mensaje.getIdForo());
			stmt.setInt(4, mensaje.getIdTema());
			stmt.setInt(5, mensaje.getIdMensaje_cita());
			stmt.setString(6, mensaje.getCuerpo());
			stmt.setInt(7, mensaje.getModerado());
			stmt.setString(8, mensaje.getUsuarioCreacion());
			stmt.setString(9, mensaje.getUsuarioCreacion());
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en nuevoMensaje(Mensaje mensaje) - INSERT INTO CV_FORO_MENSAJE");
				throw new DAOException("No culmino");
			}
			
		}catch (SQLException e) {
			log.error(e.toString());
		} finally {
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	public Tema obtenerTema(int idForo, int idTema) throws DAOException {
		Connection cons = null;
		PreparedStatement stmt = null;		
		ResultSet result = null;
		Tema tema = null;
		
		try{
			
			String query = "SELECT TITULO,CERRADO,USUARIO_CREACION FROM CV_FORO_TEMA T WHERE ESTADO=1 AND T.IDFORO=? AND T.IDTEMA = ?";
			
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idForo);
			stmt.setInt(2, idTema);	
			result = (ResultSet)stmt.executeQuery();
			if(result.next()){
				tema = new Tema();
				tema.setIdForo(idForo);
				tema.setIdTema(idTema);	
				tema.setTitulo(result.getString("TITULO"));
				tema.setCerrado(result.getInt("CERRADO"));
				tema.setUsuarioCreacion(result.getString("USUARIO_CREACION"));
			}
		}catch (SQLException e) {
			log.error(e.toString());
		} catch (Exception e){
			log.error(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return tema;
	}
	
	public void calificarTema(Tema tema) throws DAOException{
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;		
		String query ="";
		
		try {
			cons = (Connection)dataSource.getConnection();
			
			query = "select valor from cv_foro_tema_valor where idforo = ? and idtema = ? and trim(usuario) = ?";
			stmt = (PreparedStatement) cons.prepareStatement(query);			
			stmt.setInt(1, tema.getIdForo());
			stmt.setInt(2, tema.getIdTema());
			stmt.setString(3, tema.getUsuarioModificacion());
			result = (ResultSet)stmt.executeQuery();
			if(result.next())
				query = "update cv_foro_tema_valor set valor = ? where idforo = ? and idtema = ? and trim(usuario) = ?";			
			else
				query = "insert into cv_foro_tema_valor(valor,idforo,idtema,usuario) values(?,?,?,?)";			
			
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, tema.getValoracion());
			stmt.setInt(2, tema.getIdForo());
			stmt.setInt(3, tema.getIdTema());
			stmt.setString(4, tema.getUsuarioModificacion());			
			stmt.executeUpdate();
		} catch (SQLException e) {
			log.error(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	public String obtenerEmisor(int idMensaje) throws DAOException {
		log.info("obtenerEmisor(int "+idMensaje+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;		
		try {
			String query = "SELECT USUARIO_CREACION FROM CV_FORO_MENSAJE WHERE IDMENSAJE=?";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idMensaje);
			result = (ResultSet)stmt.executeQuery();
			if(result.next())
				return result.getString("USUARIO_CREACION");
						
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return null;
	}
*/
}