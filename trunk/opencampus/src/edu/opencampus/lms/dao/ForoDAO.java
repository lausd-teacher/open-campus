package edu.opencampus.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.modelo.Foro;
import edu.opencampus.lms.modelo.ReglaDeServicio;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.modelo.foro.Mensaje;
import edu.opencampus.lms.modelo.foro.OpcionesBusqueda;
import edu.opencampus.lms.modelo.foro.Tema;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;

public class ForoDAO extends BaseDAO{

	Log log = LogFactory.getLog(getClass());

	protected DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	/*
	public Collection<Foro> cargarPortada(String idUsuario,Set<Integer> misForos) throws DAOException {
		log.info("cargarPortada(String idUsuario,Set<Integer> misForos)");
		Collection<Foro> foros = new ArrayList<Foro>();
		Foro foro = null;
		Connection cons = null;
		PreparedStatement stmt = null;
		//OracleCallableStatement callstmt = null;
		ResultSet result = null;
		
		String query = "{CALL  PKG_cv_servicio.SP_cv_servicio_PRE(?)}";
		try {
			cons = (Connection)dataSource.getConnection();
			cons.setAutoCommit(false);
//			callstmt = (OracleCallableStatement) cons.prepareCall(query);
//			callstmt.setString(1, idUsuario);
//			callstmt.execute();
			
			query = "SELECT * FROM (" +
					"SELECT DISTINCT F.IDFORO, F.TITULO, F.ICONO, " +
					"(SELECT COUNT(*) FROM cv_foro_tema WHERE IDFORO = F.IDFORO AND ESTADO = 1) TEMAS, " +
					"(SELECT COUNT(*) FROM cv_foro_tema T, cv_foro_mensaje M WHERE T.IDTEMA=M.IDTEMA AND M.IDFORO = F.IDFORO AND T.ESTADO=1 AND M.ESTADO = 1 " +
					"AND M.IDMENSAJE != (SELECT MIN(IDMENSAJE) FROM cv_foro_mensaje WHERE IDFORO=M.IDFORO AND IDTEMA = M.IDTEMA)) MENSAJES, " +
					"(SELECT COUNT(*) FROM cv_foro_tema T, cv_foro_mensaje M WHERE T.IDTEMA=M.IDTEMA AND M.IDFORO = F.IDFORO AND T.ESTADO=1 AND M.ESTADO = 1 " +
					"AND M.USUARIO_CREACION=?) MISMENSAJES " +
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
					"(SELECT F.IDFORO, F.TITULO, F.ICONO, " +
					"S.SEDE, S.FAMILIA, S.FORMACION, S.CICLO, S.SECCION  " +
					"FROM cv_foro F, cv_regla_servicio S, cv_foro_REGLA FR  " +
					"WHERE F.IDFORO = FR.IDFORO AND S.IDREGLA = FR.IDREGLA AND F.ESTADO=1 AND F.CERRADO=0) F " +
					"WHERE R.SEDE=F.SEDE AND R.FAMILIA=F.FAMILIA AND R.FORMACION=F.FORMACION AND R.CICLO=F.CICLO AND R.SECCION=F.SECCION " +
					"ORDER BY MISMENSAJES DESC,MENSAJES DESC" +
					") WHERE ROWNUM<=4";
			
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, idUsuario);
			result = (ResultSet) stmt.executeQuery();
			
			while(result.next()){
				foro = new Foro();
				foro.setIdForo(result.getInt("IDFORO"));
				foro.setTitulo(result.getString("TITULO"));
				foro.setIcono(result.getInt("ICONO"));
				foro.setTotalTemas(result.getInt("TEMAS"));
				foro.setTotalMensajes(result.getInt("MENSAJES"));
				foro.setEstado(result.getInt("MISMENSAJES"));
				
				// Mis Foros
				misForos.add(foro.getIdForo());
				
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
			closeStatement(stmt);
			closeConnection(cons);
		}
		return foros;
	}
	
	public Collection<Foro> cargarPortadaAdmin(String idUsuario,Set<Integer> misForos) throws DAOException {
		log.info("cargarPortadaAdmin(String idUsuario,Set<Integer> misForos)");
		Collection<Foro> foros = new ArrayList<Foro>();
		Foro foro = null;
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try {
			String query =  "SELECT * FROM (" +
							"SELECT F.IDFORO, F.TITULO, F.ICONO, " +
							"(SELECT COUNT(*) FROM cv_foro_tema WHERE IDFORO = F.IDFORO AND ESTADO = 1) TEMAS, " +
							"(SELECT COUNT(*) FROM cv_foro_tema T, cv_foro_mensaje M WHERE T.IDTEMA=M.IDTEMA AND M.IDFORO = F.IDFORO AND T.ESTADO=1 AND M.ESTADO = 1 " +
							"AND M.IDMENSAJE != (SELECT MIN(IDMENSAJE) FROM cv_foro_mensaje WHERE IDFORO=M.IDFORO AND IDTEMA = M.IDTEMA)) MENSAJES, " +
							"(SELECT COUNT(*) FROM cv_foro_tema T, cv_foro_mensaje M WHERE T.IDTEMA=M.IDTEMA AND M.IDFORO = F.IDFORO AND T.ESTADO=1 AND M.ESTADO = 1 " +
							"AND M.USUARIO_CREACION=?) MISMENSAJES " +
							"FROM cv_foro F WHERE  F.ESTADO = 1 " +
							"AND F.IDFORO != 10 " + //Admin no ve cafeteria
							"ORDER BY MISMENSAJES DESC,MENSAJES DESC" +
							") WHERE ROWNUM<=4";
			
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, idUsuario);
			
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				foro = new Foro();
				foro.setIdForo(result.getInt("IDFORO"));
				foro.setTitulo(result.getString("TITULO"));
				foro.setIcono(result.getInt("ICONO"));
				foro.setTotalTemas(result.getInt("TEMAS"));
				foro.setTotalMensajes(result.getInt("MENSAJES"));
				foro.setEstado(result.getInt("MISMENSAJES"));
				
				// Mis Foros
				misForos.add(foro.getIdForo());
				
				foros.add(foro);
			}
			
			cons.rollback();
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
		return foros;
	}
	
	public Collection<Foro> listarForos() throws DAOException {
		log.info("listarForos()");
		PreparedStatement stmt = null;
		ResultSet result = null;
		ResultSet subResult = null;
		Connection cons = null;

		Collection<Foro> foros = new ArrayList<Foro>();
		Collection<Usuario> moderadores = null;
		Usuario usuario = null;
		UsuarioDato usuarioDato = null;
		Foro foro = null;

		String query = "SELECT F.IDFORO,F.TITULO,F.DESCRIPCION,F.ICONO,F.ESTADO,F.CERRADO, " +
				"(SELECT COUNT(*) FROM cv_foro_tema WHERE IDFORO=F.IDFORO AND ESTADO = 1) TEMAS, " +
				"(SELECT COUNT(*) FROM cv_foro_tema T, cv_foro_mensaje M WHERE T.IDTEMA=M.IDTEMA AND M.IDFORO = F.IDFORO AND T.ESTADO=1 AND M.ESTADO = 1 " +
				"AND M.IDMENSAJE != (SELECT MIN(IDMENSAJE) FROM cv_foro_mensaje WHERE IDFORO=M.IDFORO AND IDTEMA = M.IDTEMA)) MENSAJES " +
				"FROM cv_foro F WHERE F.ELIMINADO=0" +
				"AND F.IDFORO != 10 " + //Admin no ve cafeteria
				"ORDER BY TITULO";
		
		try {
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			
			result = (ResultSet) stmt.executeQuery();
			
			query = "SELECT TRIM(U.USUARIO) USUARIO,P.NOMUNO,P.NOMDOS,P.APEPATERNO,P.APEMATERNO " +
					"FROM GENERAL.GEN_PERSONA P, SEGURIDAD.SEG_USUARIO U,cv_foro_moderador M " +
					"WHERE M.USUARIO=U.USUARIO AND U.CODSUJETO=P.CODPERSONA AND M.IDFORO=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			
			while (result.next()) {
				foro = new Foro();
				foro.setIdForo(result.getInt("IDFORO"));
				foro.setTitulo(result.getString("TITULO"));
				foro.setDescripcion(result.getString("DESCRIPCION"));
				foro.setIcono(result.getInt("ICONO"));
				foro.setEstado(result.getInt("ESTADO"));
				foro.setCerrado(result.getInt("CERRADO"));
				foro.setTotalTemas(result.getInt("TEMAS"));
				foro.setTotalMensajes(result.getInt("MENSAJES"));
				
				moderadores = new ArrayList<Usuario>();
				
				stmt.setInt(1, result.getInt("IDFORO"));
				subResult = (ResultSet) stmt.executeQuery();
				while (subResult.next()) {
					usuario = new Usuario();
					usuario.setIdUsuario(subResult.getString("USUARIO"));
					usuarioDato = new UsuarioDato();
					usuarioDato.setNombre1(Formato.formatoTexto(subResult.getString("NOMUNO")));
					usuarioDato.setNombre2(Formato.formatoTexto(subResult.getString("NOMDOS")));
					usuarioDato.setPaterno(Formato.formatoTexto(subResult.getString("APEPATERNO")));
					usuarioDato.setMaterno(Formato.formatoTexto(subResult.getString("APEMATERNO")));
					usuario.setUsuarioDato(usuarioDato);
					moderadores.add(usuario);
				}
				
				foro.setModeradores(moderadores);
				
				foros.add(foro);
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(subResult);
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return foros;
	}
	
	public Collection<Usuario> listarPotencialesMod(Collection<ReglaDeServicio> reglas) throws DAOException {
		log.info("listarPotencialesMod(Collection<ReglaDeServicio> reglas)");
		Connection cons = null;
		PreparedStatement stmt = null;
		//OracleCallableStatement callstmt = null;
		ResultSet result = null;
		Collection<Usuario> potenciales = new ArrayList<Usuario>();
		Usuario usuario = null;
		UsuarioDato usuarioDato = null;
		
		try {
			
			// Llamada de Procedimiento ***********************
			
			String query = "{ CALL  PKG_cv_servicio.SP_cv_servicio_PRE()}";
			cons = (Connection)dataSource.getConnection();
			cons.setAutoCommit(false);
//			callstmt = (OracleCallableStatement) cons.prepareCall(query);
//			callstmt.execute();
			
			// FIN de Llamada de Procedimiento ***********************
			
			
			query = "SELECT DISTINCT TRIM(U.USUARIO) USUARIO,P.NOMUNO,P.NOMDOS,P.APEPATERNO,P.APEMATERNO " +
					"FROM GENERAL.GEN_PERSONA P, SEGURIDAD.SEG_USUARIO U,CV_TEMP_NOTICIA N " +
					"WHERE N.USUARIO=U.USUARIO AND U.CODSUJETO=P.CODPERSONA ";

			StringBuffer where = new StringBuffer(256);
			int i = 0;
			for (ReglaDeServicio regla : reglas) {
				i++;
				if(regla.getSede() != '0')
					where.append("( SEDE=?");
				if(regla.getFamilia() != 0)
					where.append(" AND FAMILIA=?");
				if(regla.getFormacion() != 0)
					where.append(" AND FORMACION=?");
				if(regla.getCiclo() != 0)
					where.append(" AND CICLO=?");
				if(regla.getSeccion() != 0)
					where.append(" AND SECCION=?");
				if(i != reglas.size())
					where.append(") OR ");
			}
			
			if(where.indexOf("?") != -1)
				query = query + "AND (" + where.toString() + ")) ORDER BY APEPATERNO,APEMATERNO,NOMUNO";
			else
				query = query + "ORDER BY APEPATERNO,APEMATERNO,NOMUNO";
			
			//log.info("Consulta: "+query);

			stmt = (PreparedStatement) cons.prepareStatement(query);
			
			i=0;
			for (ReglaDeServicio regla : reglas) {
				if(regla.getSede() != '0')
					stmt.setString(++i, String.valueOf(regla.getSede()));
				if(regla.getFamilia() != 0)
					stmt.setInt(++i, regla.getFamilia());
				if(regla.getFormacion() != 0)
					stmt.setInt(++i, regla.getFormacion());
				if(regla.getCiclo() != 0)
					stmt.setInt(++i, regla.getCiclo());
				if(regla.getSeccion() != 0)
					stmt.setInt(++i, regla.getSeccion());
			}
			
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				usuario = new Usuario();
				usuario.setIdUsuario(result.getString("USUARIO"));
				usuarioDato = new UsuarioDato();
				usuarioDato.setNombre1(result.getString("NOMUNO"));
				usuarioDato.setNombre2(result.getString("NOMDOS"));
				usuarioDato.setPaterno(result.getString("APEPATERNO"));
				usuarioDato.setMaterno(result.getString("APEMATERNO"));
				usuario.setUsuarioDato(usuarioDato);
				
				potenciales.add(usuario);
			}
			
			cons.rollback();
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
		return potenciales;
	}
	
	public Collection<Tema> buscarPorTema(OpcionesBusqueda opciones) throws DAOException {
		log.info("buscarPorTema(OpcionesBusqueda opciones)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		ResultSet subResult = null;
		
		Collection<Tema> temas = new ArrayList<Tema>();
		Tema tema = null;
		Collection<Mensaje> mensajes = new ArrayList<Mensaje>();
		Mensaje mensaje = null;
		
		try {
			StringBuffer query  = new StringBuffer(768);
			query.append("SELECT F.IDFORO,F.TITULO FORO_TITULO,F.CERRADO FORO_CERRADO,T.IDTEMA,T.TITULO,T.CERRADO,T.FECHA_CREACION," +
					"P.APEPATERNO, P.NOMUNO," +
					"(SELECT COUNT(IDMENSAJE)-1 FROM cv_foro_mensaje WHERE IDFORO=F.IDFORO AND IDTEMA=T.IDTEMA) TOTAL, " +
					"(SELECT ROUND(AVG(VALOR),0) FROM cv_foro_tema_valor WHERE IDFORO=F.IDFORO AND IDTEMA=T.IDTEMA) VALOR " +
					"FROM SEGURIDAD.SEG_USUARIO U, GENERAL.GEN_PERSONA P, cv_foro_tema T, cv_foro F " +
					"WHERE TRIM(U.USUARIO)=T.USUARIO_CREACION AND U.CODSUJETO=P.CODPERSONA AND F.IDFORO=T.IDFORO AND F.ESTADO='1' AND T.ESTADO='1'" +
					"AND (");
			
			int i = 0;
			for (@SuppressWarnings("unused")
					Integer n : opciones.getMisForos()) {
				if(0 == i){
					query.append("T.IDFORO=? ");
					i++;
				}else{
					query.append("OR T.IDFORO=? ");
				}
			}

			if(opciones.getTema().length != 0 && !opciones.getTema()[0].equals("")){
				query.append(") AND (");
				
				if(opciones.isClaveCompleta()){
					query.append("TRANSLATE(UPPER(T.TITULO),'¡…Õ”⁄','AEIOU') LIKE ? ");
				}else{
					for (int j = 0; j < opciones.getTema().length; j++) {
						if(j==0){
							query.append("TRANSLATE(UPPER(T.TITULO),'¡…Õ”⁄—','AEIOUN') LIKE ? ");
						}else{
							query.append("OR TRANSLATE(UPPER(T.TITULO),'¡…Õ”⁄—','AEIOUN') LIKE ? ");
						}
					}
				}
			}
			
			if(opciones.getUsuario().length()>0){
				query.append(") AND (" +
						"T.USUARIO_CREACION = ? ");
				if(!opciones.isSoloTema()){
					query.append("OR (SELECT COUNT(IDMENSAJE) FROM cv_foro_mensaje " +
							"WHERE IDFORO=T.IDFORO AND IDTEMA=T.IDTEMA AND ROWNUM=1 AND USUARIO_CREACION = ?)>0");
				}
			}
			
			query.append(") AND T.FECHA_CREACION >= TO_DATE(?,'DD-MM-YYYY') " +
					"ORDER BY F.TITULO,T.FECHA_CREACION");

			//log.info("Consulta: "+query);
			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query.toString());
			
			for (Integer n : opciones.getMisForos()) {
				stmt.setInt(i++, n);
			}
			
			if(opciones.getTema().length != 0 && !opciones.getTema()[0].equals("")){
				if(opciones.isClaveCompleta()){
					StringBuffer patron = new StringBuffer(64);
					for (int j = 0; j < opciones.getTema().length; j++) {
						patron.append("%"+opciones.getTema()[j]);
					}
					stmt.setString(i++, patron.append("%").toString());
				}else{
					for (int j = 0; j < opciones.getTema().length; j++) {
						stmt.setString(i++, "%"+opciones.getTema()[j]+"%");
					}
				}
			}
			
			if(opciones.getUsuario().length()>0){
				stmt.setString(i++, opciones.getUsuario());
				if(!opciones.isSoloTema()){
					stmt.setString(i++, opciones.getUsuario());
				}
			}
			
			stmt.setString(i++, Formato.setBaseDatosDeDate(opciones.getFechaLimite()));
			
			result = (ResultSet) stmt.executeQuery();
			
			// Mensajes dentro de un tema
			if(opciones.getUsuario().length()>0){
				String query2 = "SELECT IDMENSAJE,CUERPO,FECHA_CREACION," +
						"(SELECT FILA FROM (SELECT ROWNUM FILA,IDMENSAJE " +
						"FROM cv_foro_mensaje " +
						"WHERE ESTADO=1 " +
						"AND IDFORO=? AND IDTEMA=? " +
						"ORDER BY FECHA_CREACION) WHERE IDMENSAJE=M.IDMENSAJE) FILA " +
						"FROM cv_foro_mensaje M " +
						"WHERE ESTADO=1 AND IDMENSAJE != (SELECT MIN(IDMENSAJE) FROM cv_foro_mensaje WHERE IDFORO=M.IDFORO AND IDTEMA=M.IDTEMA) " +
						"AND USUARIO_CREACION=? AND IDFORO=? AND IDTEMA=? " +
						"ORDER BY FECHA_CREACION";
				
				stmt = (PreparedStatement) cons.prepareStatement(query2);
			}
			
			while (result.next()) {
				tema = new Tema();
				tema.setIdForo(result.getInt("IDFORO"));
				tema.setCuerpo(result.getString("FORO_TITULO"));
				tema.setEstado(result.getInt("FORO_CERRADO"));
				tema.setIdTema(result.getInt("IDTEMA"));
				tema.setTitulo(result.getString("TITULO"));
				tema.setCerrado(result.getInt("CERRADO"));
				tema.setFechaCreacion(Formato.getDateDeBaseDatos(result.getString("FECHA_CREACION")));
				tema.setTotalRespuestas(result.getInt("TOTAL"));
				tema.setValoracion(result.getInt("VALOR"));
				tema.setNombreUsuario(Formato.formatoTexto(result.getString("NOMUNO")+" "+result.getString("APEPATERNO")));
				
				if(opciones.getUsuario().length()>0 && !opciones.isSoloTema()){
					mensajes = new ArrayList<Mensaje>();
					
					stmt.setInt(1, result.getInt("IDFORO"));
					stmt.setInt(2, result.getInt("IDTEMA"));
					stmt.setString(3, opciones.getUsuario());
					stmt.setInt(4, result.getInt("IDFORO"));
					stmt.setInt(5, result.getInt("IDTEMA"));
					
					subResult = (ResultSet) stmt.executeQuery();
					
					while (subResult.next()) {
						mensaje = new Mensaje();
						mensaje.setIdMensaje(subResult.getInt("IDMENSAJE"));
						mensaje.setCuerpo(subResult.getString("CUERPO"));
						mensaje.setFechaCreacion(Formato.getDateDeBaseDatos(subResult.getString("FECHA_CREACION")));
						mensaje.setEstado(((subResult.getInt("FILA")-1)/Constante.MAX_FILA_POR_PAG)+1);
						mensajes.add(mensaje);
					}
					
					tema.setMensajes(mensajes);
				}
				
				temas.add(tema);
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeResultSet(subResult);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return temas;
	}
	
	public Collection<Usuario> buscarUsuarios(String usuario) throws DAOException {
		log.info("buscarUsuarios(String usuario)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Collection<Usuario> usuarios = new ArrayList<Usuario>();
		Usuario user = null;
		try {
			String query = "SELECT TRIM(u.USUARIO) USUARIO, p.APEPATERNO,p.APEMATERNO,p.NOMUNO,p.NOMDOS "
					+ "FROM SEGURIDAD.SEG_USUARIO u, GENERAL.GEN_PERSONA p "
					+ "WHERE  "
					+ "(UPPER(TRANSLATE((p.APEPATERNO||' '||p.APEMATERNO||' '||p.NOMUNO||' '||p.NOMDOS),'·ÈÌÛ˙Ò¡…Õ”⁄—','aeiounAEIOUN'))) "
					+ "LIKE upper(translate(?,'·ÈÌÛ˙¡…Õ”⁄','aeiouAEIOU'))AND (u.CODSUJETO = p.CODPERSONA) AND ROWNUM <= 20 "
					+ "union "
					+ "SELECT TRIM(u.USUARIO) USUARIO, p.APEPATERNO,p.APEMATERNO,p.NOMUNO,p.NOMDOS "
					+ "FROM SEGURIDAD.SEG_USUARIO u, GENERAL.GEN_PERSONA p "
					+ "where "
					+ ""
					+ "(UPPER(TRANSLATE((p.NOMUNO||' '||p.NOMDOS||' '||p.APEPATERNO||' '||p.APEMATERNO),'·ÈÌÛ˙Ò¡…Õ”⁄—','aeiounAEIOUN')) "
					+ "LIKE UPPER(?))AND (u.CODSUJETO = p.CODPERSONA) AND ROWNUM <= 20 ";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, "%"+usuario+"%");
			stmt.setString(2, "%"+usuario+"%");
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				user = new Usuario();
				user.setIdUsuario(result.getString("USUARIO"));
				user.setNombreCompleto(Formato.formatoTexto(result
						.getString("NOMUNO")
						+ " "
						+ Formato.formatoTextoNull(result.getString("NOMDOS"))
						+ " "
						+ result.getString("APEPATERNO")
						+ " "
						+ result.getString("APEMATERNO")));
				usuarios.add(user);
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
	
	public Collection<Foro> listarTitulosDeForos(Collection<Integer> forosIDs) throws DAOException {
		log.info("listarTitulosDeForos(Collection<Integer> forosIDs)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;

		Collection<Foro> foros = new ArrayList<Foro>();
		Foro foro = null;

		String in = "";
		
		for (Integer n : forosIDs) {
			if(in.equals(""))
				in+=n;
			else
				in+=","+n;
		}
		
		String query = "SELECT IDFORO,TITULO FROM cv_foro " +
				"WHERE IDFORO IN ("+in+") ORDER BY TITULO";
		
		try {
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			
			result = (ResultSet) stmt.executeQuery();
			
			while (result.next()) {
				foro = new Foro();
				foro.setIdForo(result.getInt("IDFORO"));
				foro.setTitulo(result.getString("TITULO"));
				foros.add(foro);
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return foros;
	}
	
	public Foro obtenerForoPaModificar(int idForo) throws DAOException {
		log.info("obtenerForoPaModificar()");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		Foro foro = null;
		ReglaDeServicio regla = null;
		Usuario usuario = null;
		UsuarioDato datos = null;
		Collection<ReglaDeServicio> reglas = new ArrayList<ReglaDeServicio>();
		Collection<Usuario> moderadores = new ArrayList<Usuario>();
		
		try {
			String query = "SELECT TITULO,DESCRIPCION,ICONO " +
					"FROM cv_foro F WHERE IDFORO=?";

			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idForo);
			
			result = (ResultSet) stmt.executeQuery();
			
			if (result.next()) {
				foro = new Foro();
				foro.setIdForo(idForo);
				foro.setTitulo(result.getString("TITULO"));
				foro.setDescripcion(result.getString("DESCRIPCION"));
				foro.setIcono(result.getInt("ICONO"));
				
				//Regla de Servicio
				query = "SELECT S.IDREGLA, S.SEDE, S.FAMILIA, S.FORMACION, S.CICLO, S.SECCION " +
						"FROM cv_regla_servicio S, cv_foro_REGLA N WHERE N.IDREGLA=S.IDREGLA AND IDFORO=?";
				
				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt.setInt(1, idForo);
				
				result = (ResultSet) stmt.executeQuery();
				
				while (result.next()) {
					regla = new ReglaDeServicio();
					regla.setIdRegla(result.getInt("IDREGLA"));
					regla.setSede(result.getString("SEDE").charAt(0));
					regla.setFamilia(result.getInt("FAMILIA"));
					regla.setFormacion(result.getInt("FORMACION"));
					regla.setCiclo(result.getInt("CICLO"));
					regla.setSeccion(result.getInt("SECCION"));
					
					reglas.add(regla);
				}
				
				foro.setReglaDeServicio(reglas);
				
				//Moderadores
				query = "SELECT TRIM(M.USUARIO) USUARIO,P.NOMUNO,P.NOMDOS,P.APEPATERNO,P.APEMATERNO " +
						"FROM GENERAL.GEN_PERSONA P, SEGURIDAD.SEG_USUARIO U,cv_foro_moderador M " +
						"WHERE M.USUARIO=U.USUARIO AND U.CODSUJETO=P.CODPERSONA AND IDFORO=?";
				
				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt.setInt(1, idForo);
				
				result = (ResultSet) stmt.executeQuery();
				
				while (result.next()) {
					usuario = new Usuario();
					usuario.setIdUsuario(result.getString("USUARIO"));
					datos = new UsuarioDato();
					datos.setNombre1(result.getString("NOMUNO"));
					datos.setNombre2(result.getString("NOMDOS"));
					datos.setPaterno(result.getString("APEPATERNO"));
					datos.setMaterno(result.getString("APEMATERNO"));
					usuario.setUsuarioDato(datos);
					moderadores.add(usuario);
				}
				
				foro.setModeradores(moderadores);
				
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
		return foro;
	}
	
	public void crearForo(Foro foro) throws DAOException {
		log.info("crearForo(Foro foro)");
		Connection cons = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try {
			String query = "INSERT INTO cv_foro (IDFORO,TITULO,DESCRIPCION,ICONO,ESTADO,CERRADO,USUARIO_CREACION,FECHA_CREACION) " +
							"VALUES (SEQCVFORO.NEXTVAL,?,?,?,?,?,?,SYSDATE)";
				
			cons = (Connection)dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, foro.getTitulo());
			stmt.setString(2, foro.getDescripcion());
			stmt.setInt(3, foro.getIcono());
			stmt.setInt(4, foro.getEstado());
			stmt.setInt(5, foro.getCerrado());
			stmt.setString(6, foro.getUsuarioCreacion());
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en crearForo(Foro foro) - INSERT INTO cv_foro");
				throw new DAOException("No culmino");
			}
			
			// REGLA ************************************************
			
			query = "INSERT INTO cv_regla_servicio (IDREGLA,SEDE,FAMILIA,FORMACION,CICLO,SECCION) " +
					"VALUES(SEQCVREGLASERVICIO.NEXTVAL,?,?,?,?,?)";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			
			query = "INSERT INTO cv_foro_REGLA (IDREGLA,IDFORO) " +
					"VALUES(SEQCVREGLASERVICIO.CURRVAL,SEQCVFORO.CURRVAL)";
			stmt2 = (PreparedStatement) cons.prepareStatement(query);
			
			for (ReglaDeServicio rs: foro.getReglaDeServicio()) {
				stmt.setString(1, String.valueOf(rs.getSede()));
				stmt.setInt(2, rs.getFamilia());
				stmt.setInt(3, rs.getFormacion());
				stmt.setInt(4, rs.getCiclo());
				stmt.setInt(5, rs.getSeccion());
				
				if (1 != stmt.executeUpdate()) {
					log.error("Error en crearForo(Foro foro) - INSERT INTO cv_regla_servicio");
					throw new DAOException("No culmino");
				}
				
				if (1 != stmt2.executeUpdate()) {
					log.error("Error en crearForo(Foro foro) - INSERT INTO cv_foro_REGLA");
					throw new DAOException("No culmino");
				}
				
			}
			
			// MODERADOR ************************************************
			
			query = "INSERT INTO cv_foro_moderador (IDFORO,USUARIO) " +
					"VALUES(SEQCVFORO.CURRVAL,?)";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			
			for (Usuario usuario: foro.getModeradores()) {
				stmt.setString(1, usuario.getIdUsuario());
				
				if (1 != stmt.executeUpdate()) {
					log.error("Error en crearForo(Foro foro) - INSERT INTO cv_foro_moderador");
					throw new DAOException("No culmino");
				}
			}
						
			// Transaccion exitosa
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
			closeStatement(stmt2);
			closeConnection(cons);
		}
	}
	
	public void modificarForo(Foro foro) throws DAOException {
		log.info("modificarForo(Foro foro)");
		Connection cons = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet result = null;
		Collection<ReglaDeServicio> reglasAntiguas = new ArrayList<ReglaDeServicio>();
		ReglaDeServicio reglaAntigua = null;
		Collection<Usuario> moderadoresAntiguos = new ArrayList<Usuario>();
		Usuario moderadorAntiguo = null; 
		try {
			String query = "UPDATE cv_foro SET TITULO=?,DESCRIPCION=?,ICONO=? " +
					"WHERE IDFORO=?";
				
			cons = (Connection)dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, foro.getTitulo());
			stmt.setString(2, foro.getDescripcion());
			stmt.setInt(3, foro.getIcono());
			stmt.setInt(4, foro.getIdForo());
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en modificarForo(Foro foro) - UPDATE cv_foro");
				throw new DAOException("No culmino");
			}
			
			// REGLA ************************************************
			
			// Recuperando lista de reglas antiguas
			query = "SELECT R.IDREGLA,R.SEDE,R.FAMILIA,R.FORMACION,R.CICLO,R.SECCION " +
					"FROM cv_regla_servicio R,cv_foro_REGLA F " +
					"WHERE R.IDREGLA=F.IDREGLA AND IDFORO=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, foro.getIdForo());
			
			result = (ResultSet)stmt.executeQuery();
			
			while (result.next()) {
				reglaAntigua = new ReglaDeServicio();
				reglaAntigua.setIdRegla(result.getInt("IDREGLA"));
				reglaAntigua.setSede(result.getString("SEDE").charAt(0));
				reglaAntigua.setFamilia(result.getInt("FAMILIA"));
				reglaAntigua.setFormacion(result.getInt("FORMACION"));
				reglaAntigua.setCiclo(result.getInt("CICLO"));
				reglaAntigua.setSeccion(result.getInt("SECCION"));
				
				reglasAntiguas.add(reglaAntigua);
			}
			
			// Comparando si la regla antigua aun permanece
			query = "DELETE FROM cv_foro_REGLA WHERE IDREGLA=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			
			query = "DELETE FROM cv_regla_servicio WHERE IDREGLA=?";
			stmt2 = (PreparedStatement) cons.prepareStatement(query);
			
			for (ReglaDeServicio regla : reglasAntiguas) {
				if(!foro.getReglaDeServicio().contains(regla)){
					
					stmt.setInt(1, regla.getIdRegla());
					if (1 != stmt.executeUpdate()) {
						log.error("Error al intentar eliminar regla de cv_foro_REGLA de id: " + regla.getIdRegla());
						throw new DAOException("No culmino");
					}
					stmt2.setInt(1, regla.getIdRegla());
					if (1 != stmt2.executeUpdate()) {
						log.error("Error al intentar eliminar regla de cv_regla_servicio de id: " + regla.getIdRegla());
						throw new DAOException("No culmino");
					}
					log.info("Regla Eliminada: "+regla.toString());
					
				}
			}
			
			// Insertando las nuevas reglas
			foro.getReglaDeServicio().removeAll(reglasAntiguas);
			
			query = "INSERT INTO cv_regla_servicio (IDREGLA,SEDE,FAMILIA,FORMACION,CICLO,SECCION) " +
					"VALUES(SEQCVREGLASERVICIO.NEXTVAL,?,?,?,?,?)";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			
			query = "INSERT INTO cv_foro_REGLA (IDREGLA,IDFORO) " +
					"VALUES(SEQCVREGLASERVICIO.CURRVAL,?)";
			stmt2 = (PreparedStatement) cons.prepareStatement(query);
			stmt2.setInt(1, foro.getIdForo());
			
			for (ReglaDeServicio regla: foro.getReglaDeServicio()) {
				stmt.setString(1, String.valueOf(regla.getSede()));
				stmt.setInt(2, regla.getFamilia());
				stmt.setInt(3, regla.getFormacion());
				stmt.setInt(4, regla.getCiclo());
				stmt.setInt(5, regla.getSeccion());
				
				if (1 != stmt.executeUpdate()) {
					log.error("Error en modificarForo(Foro foro) - INSERT INTO cv_regla_servicio");
					throw new DAOException("No culmino");
				}
				
				if (1 != stmt2.executeUpdate()) {
					log.error("Error en modificarForo(Foro foro) - INSERT INTO cv_foro_REGLA");
					throw new DAOException("No culmino");
				}
				log.info("Regla Creada: "+regla.toString());
			}
			
			
			// MODERADOR ************************************************
			
			query = "SELECT TRIM(USUARIO) USUARIO FROM cv_foro_moderador WHERE IDFORO=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, foro.getIdForo());
			
			result = (ResultSet)stmt.executeQuery();
			
			while (result.next()) {
				moderadorAntiguo = new Usuario();
				moderadorAntiguo.setIdUsuario(result.getString("USUARIO"));
				moderadoresAntiguos.add(moderadorAntiguo);
			}
			
			// Comparando si el moderador antiguo aun permanece
			query = "DELETE FROM cv_foro_moderador WHERE IDFORO=? AND TRIM(USUARIO)=?";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, foro.getIdForo());
			
			for (Usuario usuario : moderadoresAntiguos) {
				if(!foro.getModeradores().contains(usuario)){
					
					stmt.setString(2, usuario.getIdUsuario());
					if (1 != stmt.executeUpdate()) {
						log.error("Error al intentar eliminar usuario de cv_foro_moderador de id: " + usuario.getIdUsuario());
						throw new DAOException("No culmino");
					}
					log.info("Moderador Eliminado: "+usuario.getIdUsuario());
					
				}
			}
			
			// Insertando los nuevos moderadores
			foro.getModeradores().removeAll(moderadoresAntiguos);
			
			query = "INSERT INTO cv_foro_moderador (IDFORO,USUARIO) " +
					"VALUES(?,?)";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, foro.getIdForo());
			
			for (Usuario usuario: foro.getModeradores()) {
				
				stmt.setString(2, usuario.getIdUsuario());
				if (1 != stmt.executeUpdate()) {
					log.error("Error en modificarForo(Foro foro) - INSERT INTO cv_foro_moderador");
					throw new DAOException("No culmino");
				}
				log.info("Regla Creada: "+usuario.getIdUsuario());
				
			}
					
			// Transaccion exitosa
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
			closeResultSet(result);
			closeStatement(stmt);
			closeStatement(stmt2);
			closeConnection(cons);
		}
	}
	
	public void cambiarEstado(int idForo, int estado) throws DAOException {
		log.info("cambiarEstado(int "+idForo+", int "+estado+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "UPDATE cv_foro SET ESTADO=? WHERE IDFORO=?";

			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, estado);
			stmt.setInt(2, idForo);
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en cambiarEstado(int idForo, int estado)");
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
	
	public void cambiarCerrado(int idForo, int cerrado) throws DAOException {
		log.info("cambiarEstado(int "+idForo+", int "+cerrado+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "UPDATE cv_foro SET CERRADO=? WHERE IDFORO=?";

			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, cerrado);
			stmt.setInt(2, idForo);
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en cambiarCerrado(int idForo, int cerrado)");
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
	
	public void eliminar(int idForo) throws DAOException {
		log.info("eliminar(int "+idForo+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "UPDATE cv_foro SET ELIMINADO=1, ESTADO=0 WHERE IDFORO=?";

			cons = (Connection)dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idForo);
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en eliminar(int idForo)");
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
	*/
}