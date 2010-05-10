package edu.opencampus.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.modelo.correo.Adjunto;
import edu.opencampus.lms.modelo.correo.Carpeta;
import edu.opencampus.lms.modelo.correo.Mensaje;
import edu.opencampus.lms.modelo.correo.UsuarioCorreo;
import edu.opencampus.lms.modelo.usuario.Rol;
import edu.opencampus.lms.util.Archivo;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;

public class BuzonDAO extends BaseDAO {

	Log log = LogFactory.getLog(getClass());

	protected DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Collection<Mensaje> cargarPortada(int idUsuario) throws DAOException {
		log.info("cargarPortada(int idUsuario)");
		
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;

		Collection<Mensaje> mensajes = new ArrayList<Mensaje>();

		try {
			
			String query = "select m.idmensaje, m.titulo, m.fecha, p.apepaterno, p.nomuno " +
					"from cv_mensaje m, cv_mensaje_usuario um, cv_usuario u, cv_persona p " +
					"where um.idmensaje = m.idmensaje and m.usuario_creacion = u.idusuario and u.idusuario = p.idpersona " +
					"and m.estado=1 and um.estado = 1 and um.carpeta = 'r' and um.leido=0 and um.idusuario = ? " +
					"order by m.fecha desc limit 0,"+Constante.PORTAL_CANTIDAD_CORREOS;
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idUsuario);

			result =  stmt.executeQuery();
			while (result.next()) {

				Mensaje mensaje = new Mensaje();
				mensaje.setIdMensaje(result.getString("idmensaje"));
				mensaje.setTitulo(result.getString("titulo"));
				mensaje.setFecha_envio(Formato.getDateDeBaseDatos(result.getString("fecha")));
				mensaje.setUsuario(Formato.formatoTexto(result.getString("nomuno")) +" "+ Formato.formatoTexto(result.getString("apepaterno")));
				
				mensajes.add(mensaje);
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
		return mensajes;
	}
	
	public Collection<Mensaje> buscarMensaje(String aBuscar, String idUsuario)
			throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Collection<Mensaje> mensajes = new ArrayList<Mensaje>();
		try {
			String query = "SELECT m.USUARIO_CREACION, m.IDMENSAJE, m.USUARIO_CREACION, m.TITULO, m.CONTENIDO, m.FECHA, m.ADJUNTO, "
					+ "um.LEIDO, p.APEPATERNO, p.APEMATERNO, p.NOMUNO,p.NOMDOS, um.CARPETA, um.FAVORITO, um.FECHA_LEIDO "
					+ "from CV_MENSAJE m, CV_USUARIO_MENSAJE um, SEGURIDAD.SEG_USUARIO u, GENERAL.GEN_PERSONA p "
					+ "where trim(um.USUARIO_DESTINO) = ? AND um.ESTADO = ? AND um.IDMENSAJE = m.IDMENSAJE "
					+ "AND um.USUARIO_DESTINO = u.USUARIO AND u.CODSUJETO = p.CODPERSONA "
					+ "AND (UPPER(m.TITULO) LIKE UPPER(?) ) "
					+ "ORDER BY m.FECHA desc ";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, idUsuario);
			stmt.setString(2, "0");
			stmt.setString(3, aBuscar);
			result =  stmt.executeQuery();
			while (result.next()) {
				Mensaje m = new Mensaje();
				m.setIdMensaje(result.getString("IDMENSAJE"));
				m.setUsuario(result.getString("USUARIO_CREACION"));
				m.setTitulo(result.getString("TITULO"));
				m.setContenido(result.getString("CONTENIDO"));
				m.setAdjunto(result.getInt("ADJUNTO"));
				m.setEstado(result.getInt("LEIDO"));
				m.setUsuario_envio(Formato.formatoNombreCompletoJSP(result
						.getString("APEPATERNO"), result
						.getString("APEMATERNO"), result.getString("NOMUNO"),
						result.getString("NOMDOS")));
				m.setCarpeta(result.getString("CARPETA"));
				m.setFavorito(result.getInt("FAVORITO"));

				mensajes.add(m);
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
		return mensajes;
	}

	public List<Mensaje> mostrarFavoritos(int idUsuario, int start, int end)
			throws DAOException {
		log.info("mostrarFavoritos(int idUsuario, int start, int end)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;

		PreparedStatement stmt1 = null;
		ResultSet result1 = null;

		List<Mensaje> mensajes = new ArrayList<Mensaje>();
		try {
			String query = "select m.idmensaje, m.usuario_creacion, m.titulo, m.contenido, m.fecha, m.adjunto, " +
					"um.leido,um.tipo, p.apepaterno, p.apematerno, p.nomuno,p.nomdos, um.carpeta, um.favorito, um.fecha_leido " +
					"from cv_mensaje m, cv_mensaje_usuario um, cv_usuario u, cv_persona p  " +
					"where um.idmensaje = m.idmensaje and m.usuario_creacion = u.idusuario and u.idusuario = p.idpersona " +
					"and um.idusuario = ? and um.estado = 1 and um.carpeta <> 'P' and um.favorito = 1 order by m.fecha desc";

			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, idUsuario);

			result = stmt.executeQuery();
			while (result.next()) {
				Mensaje m = new Mensaje();
				List<Adjunto> adjuntos = new ArrayList<Adjunto>();
				List<UsuarioCorreo> destinos = new ArrayList<UsuarioCorreo>();
				List<UsuarioCorreo> ccs = new ArrayList<UsuarioCorreo>();
				UsuarioCorreo remitente = new UsuarioCorreo();

				m.setContenido(result.getString("CONTENIDO"));
				remitente.setIdUsuario(result.getString("USUARIO_CREACION"));
				remitente.setNombreCorto(Formato.formatoTexto(result
						.getString(11)
						+ " "
						+ result.getString(9)
						+ " "
						+ result.getString(10)));
				m.setIdMensaje(result.getString("IDMENSAJE"));
				m.setTitulo(result.getString("TITULO"));
				m.setFecha_envio(Formato.timestampToCalendar(result.getString("FECHA")));
				m.setAdjunto(result.getInt("ADJUNTO"));
				m.setEstado(result.getInt("LEIDO"));
				m.setCarpeta(result.getString("CARPETA"));
				m.setFavorito(result.getInt("FAVORITO"));
				m.setFecha_leido(Formato.timestampToCalendar(result.getString("FECHA_LEIDO")));
				m.setTipo(result.getString("TIPO"));
				m.setRemitente(remitente);

				query = "select idadjunto, nombre, tamanio from cv_adjunto where idmensaje = ?";
				
				stmt1 = cons.prepareStatement(query);
				stmt1.setString(1, result.getString("IDMENSAJE"));
				result1 = stmt1.executeQuery();
				while (result1.next()) {
					Adjunto a = new Adjunto();
					a.setIdAdjunto(result1.getString("IDADJUNTO"));
					a.setNombre(result1.getString("NOMBRE"));
					a.setTamanio(result1.getInt("TAMANIO"));
					a.setFecha(Formato.timestampToCalendar(result.getString("FECHA")));
					adjuntos.add(a);
				}
				if (adjuntos.size() > 0) {
					m.setAdjuntos(adjuntos);
				} else {
					Adjunto a = new Adjunto();
					a.setIdAdjunto("0");
					a.setIdMensaje("0");
					a.setNombre("");
					a.setTamanio(0);
					adjuntos.add(a);
					m.setAdjuntos(adjuntos);
				}

				query = "select um.idusuario, p.apepaterno, p.apematerno, p.nomuno,p.nomdos, um.tipo " +
						"from cv_mensaje_usuario um, cv_usuario u, cv_persona p " +
						"where um.idmensaje = ? and (um.tipo = 'D' or um.tipo = 'C') " +
						"and um.idusuario = u.idusuario and u.idusuario = p.idpersona ";
				stmt1 = cons.prepareStatement(query);
				stmt1.setString(1, result.getString("IDMENSAJE"));
				result1 =  stmt1.executeQuery();
				while (result1.next()) {
					UsuarioCorreo usuario = new UsuarioCorreo();
					usuario.setIdUsuario(result1.getString("idusuario"));
					usuario.setNombreCorto(Formato.formatoTexto(result1
							.getString("NOMUNO")
							+ " " + result1.getString("APEPATERNO")));
					if ((result1.getString("TIPO")).equals("D")) {
						destinos.add(usuario);
					} else if ((result1.getString("TIPO")).equals("C")) {
						ccs.add(usuario);
					}
				}
				m.setDestinos(destinos);
				m.setCcs(ccs);
				mensajes.add(m);
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.toString());
		} finally {
			if (end > mensajes.size()) {
				end = mensajes.size();
			} else {
				end++;
			}
			mensajes = mensajes.subList(start, end);
			closeResultSet(result1);
			closeStatement(stmt1);
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return mensajes;
	}

	public String guardarMensaje(Mensaje m) throws DAOException {
		log.info("guardarMensaje(Mensaje m))");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		String id = null;
		try {
			String query = "insert into cv_mensaje(usuario_creacion,titulo,contenido,fecha,estado,adjunto)"
					+ "values(?, ?, ?, now(), ?, ?)";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, m.getUsuario());
			stmt.setString(2, m.getTitulo());
			stmt.setString(3, m.getContenido());
			stmt.setInt(4, m.getEstado());
			stmt.setInt(5, m.getAdjunto());
			stmt.executeUpdate();
			
			result = stmt.getGeneratedKeys();
			result.next();
			id = result.getString(1);
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return id;
	}

	public int guardarMensajeUsuario(Mensaje m) throws DAOException {
		log.info("guardarMensajeUsuario(Mensaje m))");
		PreparedStatement stmt = null;
		Connection cons = null;
		int x = 0;
		try {
			String query = "INSERT INTO CV_MENSAJE_USUARIO(IDMENSAJE,IDUSUARIO,TIPO,CARPETA,LEIDO,ESTADO,IDCARPETA,FAVORITO)"
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, m.getIdMensaje());
			stmt.setString(2, m.getUsuario());
			stmt.setString(3, m.getTipo());
			stmt.setString(4, m.getCarpeta());
			stmt.setString(5, "0");
			stmt.setString(6, "1");
			stmt.setString(7, null);
			stmt.setString(8, "0");
			if (1 == stmt.executeUpdate()) {
				x = 1;
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
		return x;
	}

	public UsuarioCorreo verificaUsuario(String idUsuario) throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		UsuarioCorreo usuario = new UsuarioCorreo();
		try {
			String query = "select u.idusuario, p.apepaterno, p.apematerno, p.nomuno,p.nomdos,p.email " +
					"from cv_usuario u, cv_persona p " +
					"where u.usuario = ? and u.idusuario = p.idpersona ";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, idUsuario.trim());
			result =  stmt.executeQuery();
			if (result.next()) {
				usuario.setIdUsuario(result.getString("idusuario"));
				usuario.setEmail(result.getString("email"));
				usuario.setNombreCompleto(Formato.formatoTexto(result
						.getString("NOMUNO")
						+ " "
						+ result.getString("NOMDOS")
						+ " "
						+ result.getString("APEPATERNO")
						+ " "
						+ result.getString("APEMATERNO")));

			} else {
				throw new DAOException("No existe el usuario");
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
		return usuario;
	}

	public Adjunto guardarAdjunto(Adjunto a, Mensaje m) throws DAOException {
		log.info("guardarAdjunto() idMensaje:"+m.getIdMensaje());
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = "insert into cv_adjunto(idmensaje, nombre, tamanio) values(?, ?, ?)";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, m.getIdMensaje());
			stmt.setString(2, a.getNombre());
			stmt.setLong(3, a.getTamanio());
			stmt.executeUpdate();
			
			result =  stmt.getGeneratedKeys();
			if (result.next()) {
				a.setIdAdjunto(result.getString(1));
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
		return a;
	}

	/*
	 * fechas, revisar
	 */
	public List<Mensaje> traerMensajes(int idUsuario, String carpeta,
			String carpetaPersonal, int start, int end) throws DAOException {
		log.info("traerMensajes(int idUsuario, String carpeta,String carpetaPersonal, int start, int end)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		PreparedStatement stmt1 = null;
		ResultSet result1 = null;
		PreparedStatement stmt2 = null;
		ResultSet result2 = null;

		List<Mensaje> mensajes = new ArrayList<Mensaje>();

		try {
			String papelera = " and ifnull(um.IDCARPETA,0) = ?  ";
			if (carpeta.equals("P")) {
				papelera = "";
			}

			String query = "select m.contenido, m.idmensaje, m.usuario_creacion, m.titulo,  m.fecha, m.adjunto, " +
					"um.leido, um.tipo, p.apepaterno, p.apematerno, p.nomuno,p.nomdos, um.carpeta, um.favorito, um.fecha_leido " +
					"from cv_mensaje m, cv_mensaje_usuario um, cv_usuario u, cv_persona p " +
					"where um.idmensaje = m.idmensaje and m.usuario_creacion = u.idusuario and u.idusuario = p.idpersona and um.estado = 1 " +
					"and um.idusuario = ? and um.carpeta = ? and (um.favorito = 0 or um.favorito = 1) " +
					papelera +
					"order by m.fecha desc";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			
			stmt.setInt(1, idUsuario);
			stmt.setString(2, carpeta);
			if (!carpeta.equals("P")) {
				stmt.setString(3, carpetaPersonal);
			}

			result = stmt.executeQuery();
			
			//Lista de Mensajes
			while (result.next()) {
				Mensaje m = new Mensaje();
				List<Adjunto> adjuntos = new ArrayList<Adjunto>();
				List<UsuarioCorreo> destinos = new ArrayList<UsuarioCorreo>();
				List<UsuarioCorreo> ccs = new ArrayList<UsuarioCorreo>();
				UsuarioCorreo remitente = new UsuarioCorreo();
				m.setContenido(result.getString(1));
				//log.info("Mensaje: "+result.getString(2));
				remitente.setIdUsuario(result.getString(3));
				remitente.setNombreCorto(Formato.formatoTexto(result
						.getString(11)
						+ " "
						+ result.getString(9)
						+ " "
						+ result.getString(10)));

				m.setIdMensaje(result.getString(2));

				m.setTitulo(result.getString(4));

				m.setFecha_envio(Formato.timestampToCalendar(result.getString(5)));

				m.setAdjunto(result.getInt(6));
				m.setEstado(result.getInt(7));
				m.setCarpeta(result.getString(13));
				m.setFavorito(result.getInt(14));
				m.setFecha_leido(Formato.timestampToCalendar(result.getString(15)));
				m.setTipo(result.getString(8));
				m.setRemitente(remitente);
				// datos del archivo adjunto
				query = "select idadjunto, nombre, tamanio from cv_adjunto where idmensaje = ?";
				stmt1 = cons.prepareStatement(query);
				stmt1.setString(1, result.getString("IDMENSAJE"));
				result1 = stmt1.executeQuery();
				while (result1.next()) {
					//log.info("Adjunto: "+result1.getString("IDADJUNTO"));
					Adjunto a = new Adjunto();
					a.setIdAdjunto(result1.getString("IDADJUNTO"));
					a.setNombre(result1.getString("NOMBRE"));
					a.setTamanio(result1.getInt("TAMANIO"));
					a.setFecha(Formato.timestampToCalendar(result.getString("FECHA")));
					adjuntos.add(a);
				}
				closeResultSet(result1);
				closeStatement(stmt1);
				if (adjuntos.size() > 0) {
					m.setAdjuntos(adjuntos);
				} else {
					Adjunto a = new Adjunto();
					a.setIdAdjunto("0");
					a.setIdMensaje("0");
					a.setNombre("");
					a.setTamanio(0);
					adjuntos.add(a);
					m.setAdjuntos(adjuntos);
				}
				// nombres de los destinos
				query = "select um.idusuario, p.apepaterno, p.apematerno, p.nomuno,p.nomdos, um.tipo " +
						"from cv_mensaje_usuario um, cv_usuario u, cv_persona p " +
						"where um.idmensaje = ? and (um.tipo = 'D' or um.tipo = 'C') " +
						"and um.idusuario = u.idusuario and u.idusuario = p.idpersona ";
				stmt2 = cons.prepareStatement(query);
				stmt2.setString(1, result.getString("IDMENSAJE"));
				result2 = stmt2.executeQuery();
				
				while (result2.next()) {
					//log.info("Usuario Destino: "+result2.getString("USUARIO_DESTINO"));
					UsuarioCorreo usuario = new UsuarioCorreo();
					usuario.setIdUsuario(result2.getString("idusuario"));
					usuario.setNombreCorto(Formato.formatoTexto(result2
							.getString("NOMUNO")
							+ " " + result2.getString("APEPATERNO")));
					if ((result2.getString("TIPO")).equals("D")) {
						destinos.add(usuario);
					} else if ((result2.getString("TIPO")).equals("C")) {
						ccs.add(usuario);
					}
				}
				closeResultSet(result2);
				closeStatement(stmt2);
				m.setDestinos(destinos);
				m.setCcs(ccs);
				mensajes.add(m);
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			if (end > mensajes.size()) {
				end = mensajes.size();
			}
			mensajes = mensajes.subList(start, end);
			closeResultSet(result2);
			closeStatement(stmt2);
			closeResultSet(result1);
			closeStatement(stmt1);
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return mensajes;
	}

	public void enviarPapelera(Mensaje m) throws DAOException {
		log.info("enviarPapelera(Mensaje m)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = "update cv_mensaje_usuario set carpeta = ?, idcarpeta = null " +
					"where idmensaje = ? and idusuario = ? and tipo = ?";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, m.getCarpeta());
			stmt.setString(2, m.getIdMensaje());
			stmt.setString(3, m.getUsuario());
			stmt.setString(4, m.getTipo());
			stmt.executeUpdate();
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
	}

	public int favorito(Mensaje m) throws DAOException {
		log.info("favorito(Mensaje m)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = "select favorito from cv_mensaje_usuario " +
					"where idmensaje = ? and idusuario = ? and tipo = ?";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, m.getIdMensaje());
			stmt.setString(2, m.getUsuario());
			stmt.setString(3, m.getTipo());
			result =  stmt.executeQuery();
			while (result.next()) {
				m.setFavorito(Integer.parseInt(result.getString(1)));
			}
			if (m.getFavorito() == 0) {
				query = "update cv_mensaje_usuario set favorito = ? " +
						"where idmensaje = ? and idusuario = ? and tipo = ?";
				stmt =  cons.prepareStatement(query);

				stmt.setInt(1, 1);
				stmt.setString(2, m.getIdMensaje());
				stmt.setString(3, m.getUsuario());
				stmt.setString(4, m.getTipo());
				stmt.executeUpdate();
				m.setFavorito(1);
			} else {
				query = "update cv_mensaje_usuario set favorito = ? " +
						"where idmensaje = ? and idusuario = ? and tipo = ?";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, 0);
				stmt.setString(2, m.getIdMensaje());
				stmt.setString(3, m.getUsuario());
				stmt.setString(4, m.getTipo());
				stmt.executeUpdate();
				m.setFavorito(0);
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
		return m.getFavorito();
	}

	public void eliminarForEver(Mensaje m) throws DAOException {
		log.info("eliminarForEver(Mensaje m)");
		PreparedStatement stmt = null;
		Connection cons = null;
		try {
			String query = "update cv_mensaje_usuario set estado = ? " +
					"where idmensaje = ? and idusuario = ? and tipo = ?";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, 0);
			stmt.setString(2, m.getIdMensaje());
			stmt.setString(3, m.getUsuario());
			stmt.setString(4, m.getTipo());
			stmt.executeUpdate();
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

	public void cambiarLeido(Mensaje m) throws DAOException {
		log.info("cambiarLeido(Mensaje m)");
		PreparedStatement stmt = null;
		Connection cons = null;
		try {
			String query = "update cv_mensaje_usuario set leido = ?, fecha_leido = now() " +
					"where idmensaje = ? and idusuario = ? and tipo = ?";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, m.getLeido());
			stmt.setString(2, m.getIdMensaje());
			stmt.setString(3, m.getUsuario());
			stmt.setString(4, m.getTipo());
			stmt.executeUpdate();
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

	public Collection<UsuarioCorreo> buscarContactos(String busca) throws DAOException {
		log.info("buscarContactos(String busca)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Collection<UsuarioCorreo> usuarios = new ArrayList<UsuarioCorreo>();
		try {
			String query = "select u.usuario, p.apepaterno,p.apematerno,p.nomuno,p.nomdos " +
					"from cv_usuario u, cv_persona p where  u.idusuario = p.idpersona and " +
					"(replace(replace(replace(replace(replace(replace(upper(concat_ws(' ', p.apepaterno, p.apematerno, p.nomuno, p.nomdos)),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N') " +
					"like ? or " +
					"replace(replace(replace(replace(replace(replace(upper(concat_ws(' ', p.nomuno, p.nomdos, p.apepaterno, p.apematerno)),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N') " +
					"like ?) order by p.apepaterno,p.apematerno,p.nomuno,p.nomdos limit 0,20";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, Formato.matizarFrace(busca));
			stmt.setString(2, Formato.matizarFrace(busca));
			result =  stmt.executeQuery();
			while (result.next()) {
				UsuarioCorreo usuario = new UsuarioCorreo();
				usuario.setIdUsuario(result.getString("usuario"));
				usuario.setNombreCompleto(Formato.formatoTexto(result
						.getString("NOMUNO")
						+ " "
						+ result.getString("NOMDOS")
						+ " "
						+ result.getString("APEPATERNO")
						+ " "
						+ result.getString("APEMATERNO")));
				usuario.setNombreCorto(Formato.formatoTexto(result
						.getString("NOMUNO")
						+ " " + result.getString("APEPATERNO")));
				usuarios.add(usuario);
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

	public String crearCarpeta(Carpeta c) throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			boolean x = true;
			int cant = 0;
			String query = "select count(*) " +
					"from cv_carpeta c, cv_carpeta_usuario cu " +
					"where c.idcarpeta = cu.idcarpeta and cu.idusuario = ? and estado = 1";
			cons =  dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, c.getIdUsuario());
			result = stmt.executeQuery();
			while (result.next()) {
				cant = result.getInt(1);
			}
			if (cant <= 12) {
				query = "select nombre " +
						"from cv_carpeta c, cv_carpeta_usuario cu " +
						"where c.idcarpeta = cu.idcarpeta and upper(c.nombre) = ? and cu.idusuario = ? and estado = 1";
				stmt = cons.prepareStatement(query);
				stmt.setString(1, c.getNombre().toUpperCase().trim());
				stmt.setInt(2, c.getIdUsuario());
				result = stmt.executeQuery();
				while (result.next()) {
					x = false;
					c.setIdCarpeta("0");
				}
				if (x) {
					query = "insert into cv_carpeta(nombre,creado_en,estado) " +
							"values(?, now(),1)";
					stmt = cons.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
					stmt.setString(1, c.getNombre().trim());
					stmt.executeUpdate();

					result = stmt.getGeneratedKeys();
					while (result.next()) {
						c.setIdCarpeta(result.getString(1));
					}
					query = "insert into cv_carpeta_usuario (idcarpeta,idusuario) values(?, ?)";
					stmt = cons.prepareStatement(query);
					stmt.setString(1, c.getIdCarpeta());
					stmt.setInt(2, c.getIdUsuario());
					stmt.executeUpdate();
				}
			} else {
				c.setIdCarpeta("L");
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
		return c.getIdCarpeta();
	}

	/*
	 * Collection de carpetas personales
	 * 
	 */
	public Collection<Carpeta> traerCarpeta(int idUsuario)
			throws DAOException {
		log.info("traerCarpeta(int idUsuario)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Collection<Carpeta> carpetas = new ArrayList<Carpeta>();
		try {
			String query = "select c.idcarpeta, c.nombre, c.creado_en " +
					"from cv_carpeta c, cv_carpeta_usuario cu " +
					"where cu.idusuario = ? and cu.idcarpeta = c.idcarpeta and estado = 1 " +
					"order by c.nombre desc";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, idUsuario);
			result = stmt.executeQuery();
			while (result.next()) {
				Carpeta c = new Carpeta();
				c.setIdCarpeta(result.getString("idcarpeta"));
				c.setNombre(result.getString("nombre"));
				c.setFecha(Formato.getDateDeBaseDatos(result.getString("creado_en")));
				carpetas.add(c);
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
		return carpetas;
	}

	/*
	 * 1: buena movida 0: bad move
	 */
	public int moverCarpeta(Mensaje m) throws DAOException {
		PreparedStatement stmt = null;
		Connection cons = null;
		int estado = 0;
		try {
			String query = "update cv_mensaje_usuario set idcarpeta = ?, carpeta = ? " +
					"where idmensaje = ? and idusuario = ? and carpeta = ? and ifnull(idcarpeta,0) = ? and tipo = ?";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, m.getIdCarpeta());
			stmt.setString(2, m.getCarpeta());
			stmt.setString(3, m.getIdMensaje());
			stmt.setString(4, m.getUsuario());

			stmt.setString(5, m.getOldCarpeta());
			stmt.setInt(6, Integer.parseInt(m.getOldIdCarpeta()));
			stmt.setString(7, m.getTipo());

			if (1 == stmt.executeUpdate()) {
				estado = 1;
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
		return estado;
	}

	/*
	 * 
	 */
	public void eliminarCarpeta(Carpeta c) throws DAOException {
		log.info("eliminarCarpeta(Carpeta c)");
		PreparedStatement stmt = null;
		Connection cons = null;
		try {
			String query = "update cv_carpeta set estado = 0 where idcarpeta = ?";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, c.getIdCarpeta());
			stmt.executeUpdate();
			
			query = "update cv_mensaje_usuario set estado = 0 where idusuario = ? and idcarpeta = ?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, c.getIdUsuario());
			stmt.setString(2, c.getIdCarpeta());
			stmt.executeUpdate();
			
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

	public int cambiarNombreCarpeta(Carpeta c) throws DAOException {
		log.info("cambiarNombreCarpeta(Carpeta c)");
		PreparedStatement stmt = null;
		Connection cons = null;
		ResultSet result = null; 
		int ci = 0;
		try {
			String query = "select nombre " +
				"from cv_carpeta c, cv_carpeta_usuario cu " +
				"where c.idcarpeta = cu.idcarpeta and upper(c.nombre) = ? and cu.idusuario = ? and estado = 1";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setString(1, c.getNombre().toUpperCase().trim());
			stmt.setInt(2, c.getIdUsuario());
			result = stmt.executeQuery();
			while (result.next()) {
				ci = 1;
			}
			if (ci == 0) {
				query = "update cv_carpeta set nombre = ? " +
						"where idcarpeta = (select idcarpeta from cv_carpeta_usuario where idcarpeta=? and idusuario=?) " +
						"and estado = 1";
				stmt = cons.prepareStatement(query);
				stmt.setString(1, c.getNombre().trim());
				stmt.setString(2, c.getIdCarpeta());
				stmt.setInt(3, c.getIdUsuario());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			ci = 1;
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
		return ci;
	}

	public int cantidadNoLeidos(int idUsuario) throws DAOException {
		log.info("cantidadNoLeidos(int idUsuario)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		int cantidad = 0;
		try {
			String query = "select count(*) " +
					"from cv_mensaje_usuario " +
					"where idusuario = ? and leido = 0 and carpeta = 'R' and estado = 1";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, idUsuario);
			result = stmt.executeQuery();
			while (result.next()) {
				cantidad = result.getInt(1);
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
		return cantidad;
	}

	public boolean banderita(String idUsuario) throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		boolean banderita = false;
		try {
			String query = "SELECT COUNT(*) "
					+ "FROM CV_USUARIO_MENSAJE "
					+ "WHERE trim(USUARIO_DESTINO) = ? AND LEIDO = ? and ESTADO = ? and carpeta <> ? and carpeta <> ?";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, idUsuario);
			stmt.setInt(2, 0);
			stmt.setInt(3, 0);
			stmt.setString(4, "P");
			stmt.setString(5, "E");
			result =  stmt.executeQuery();
			while (result.next()) {
				if (result.getInt(1) > 0) {
					banderita = true;
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
		return banderita;
	}

	public int totalMensajes(int idUsuario, String carpeta, String idCarpeta)
			throws DAOException {
		log.info("totalMensajes(int idUsuario, String carpeta, String idCarpeta)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		int total = 0;
		try {
			String papelera = " and ifnull(IDCARPETA,0) = ? ";
			if (carpeta.equals("P")) {
				papelera = "";
			}
			String query = "select count(*) " + "from cv_mensaje_usuario "
					+ "where idusuario = ? and carpeta = ? "
					+ papelera + " and estado = 1 ";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, idUsuario);
			stmt.setString(2, carpeta);
			if (!carpeta.equals("P")) {
				stmt.setString(3, idCarpeta);
			}
			result = stmt.executeQuery();
			while (result.next()) {
				total = result.getInt(1);
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
		return total;
	}

	public int totalFavoritos(int idUsuario) throws DAOException {
		log.info("totalFavoritos(int idUsuario)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		int total = 0;
		try {
			String query = "select count(*) " +
					"from cv_mensaje_usuario " +
					"where idusuario = ? and favorito = 1 and estado = 1 and carpeta <> 'P';";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idUsuario);
			result =  stmt.executeQuery();
			while (result.next()) {
				total = result.getInt(1);
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
		return total;
	}

	public void cambiarNoLeido(Mensaje m) throws DAOException {
		PreparedStatement stmt = null;
		Connection cons = null;
		try {
			String query = "update cv_mensaje_usuario set leido = ?, fecha_leido = null " +
					"where idmensaje = ? and idusuario = ? and tipo = ?";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, m.getLeido());
			stmt.setString(2, m.getIdMensaje());
			stmt.setString(3, m.getUsuario());
			stmt.setString(4, m.getTipo());
			stmt.executeUpdate();
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
	
	public Float verUsoEnDisco() throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Float carga = 0f;
		try {
			String query = "select round((sum(tamanio)/1024)/1024,2) carga from cv_adjunto";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			result =  stmt.executeQuery();
			if (result.next()) {
				carga = result.getFloat("carga");
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
		return carga;
	}
	
	public int limpiarBuzon(String fecha) throws DAOException {
		log.info("limpiarBuzon("+fecha+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		PreparedStatement subStmt = null;
		ResultSet result = null;
		ResultSet subResult = null;
		int total = 0;
		try {
			
			String query = "select idmensaje,fecha from cv_mensaje msj " +
					"where fecha <= str_to_date(?,'%d/%m/%Y') " +
					"or (select count(*) from cv_mensaje_usuario where idmensaje=msj.idmensaje and estado=1)=0 " +
					"order by fecha desc";
			
			cons =  dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, fecha);
			result =  stmt.executeQuery();
			
			while (result.next()) {
				
				log.info("Mensaje: "+result.getInt("idmensaje") + " Fecha: "+result.getString("fecha")+" -> ELIMINADO");
				
				query = "delete from cv_mensaje_usuario where idmensaje=?";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, result.getInt("idmensaje"));
				stmt.executeUpdate();
				stmt.close();
				
				query = "select idadjunto from cv_adjunto where idmensaje=?";
				subStmt =  cons.prepareStatement(query);
				subStmt.setInt(1, result.getInt("idmensaje"));
				subResult =  subStmt.executeQuery();
				
				while (subResult.next()) {
					query = "delete from cv_adjunto where idadjunto=?";
					stmt =  cons.prepareStatement(query);
					stmt.setInt(1, subResult.getInt("idadjunto"));
					stmt.executeUpdate();
					stmt.close();
					
					GregorianCalendar gc = Formato.getDateDeBaseDatos(result.getString("fecha"));
					int mes = gc.get(Calendar.MONTH) + 1;
					int anio = gc.get(Calendar.YEAR) - 2000;
					String anho = "anio";
					if (anio < 10) {
						anho = "0" + anio;
					}
					String semestre;
					if (mes <= 3) {
						semestre = "01";
					} else if (mes <= 6) {
						semestre = "02";
					} else if (mes <= 9) {
						semestre = "03";
					} else
						semestre = "04";
					
					String filename = Constante.RUTA_BUZON +  anho + Constante.SLASH 
									+ semestre + Constante.SLASH + subResult.getInt("idadjunto");
					Archivo.eliminarArchivo(filename);
					log.info("Adjunto Eliminado: "+filename);
				}
				
				query = "delete from cv_mensaje where idmensaje=?";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, result.getInt("idmensaje"));
				stmt.executeUpdate();
				stmt.close();
				
				total++;
				
				subResult.close();
				subStmt.close();
				
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
				log.error(e);
			}
			closeStatement(subStmt);
			closeResultSet(subResult);
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		
		return 0;
	}
	
	public Collection<Usuario> listarSoportes() throws DAOException {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Collection<Usuario> soportes = new ArrayList<Usuario>();
		Usuario usuario = null;
		try {
			String query = "select distinct u.idusuario, u.usuario from cv_usuario u, cv_usuario_rol r " +
					"where u.idusuario = r.idusuario and u.estado=1 and r.estado=1 and (r.idrol=? or r.idrol=?) ";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, Constante.ROL_CAMPUS_ADMINISTRADOR);
			stmt.setInt(2, Constante.ROL_CAMPUS_SOPORTE);
			result =  stmt.executeQuery();
			while (result.next()) {
				usuario = new Usuario();
				usuario.setId(result.getInt("idusuario"));
				usuario.setUsuario(result.getString("usuario"));
				//**Si gustas podes agregar no uno sino todos los roles de cada usuario
				soportes.add(usuario);
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
		return soportes;
	}
}