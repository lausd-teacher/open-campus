package edu.opencampus.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.modelo.ficha.Publicacion;
import edu.opencampus.lms.modelo.usuario.Persona;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;

public class PublicacionDAO extends BaseDAO {

	Log log = LogFactory.getLog(getClass());

	protected DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Collection<Publicacion> listarMensajes(int idFicha, int idHerramienta) throws DAOException {
		log.info("listarMensajes()");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Collection<Publicacion> publicaciones = new ArrayList<Publicacion>();
		Publicacion pub = null;
		Usuario usuario = null;
		try {
		
			String query = "SELECT pub.IDPUBLICACION, pub.TITULO, pub.CONTENIDO, pub.FECHA, pub.ARCHIVO_NOMBRE, pub.ARCHIVO_TAMANIO, " +
					"pub.IDPUBLICADOR, u.USUARIO,p.APEPATERNO,p.APEMATERNO,p.NOMUNO,p.NOMDOS,p.SEXO " +
					"FROM CV_PUBLICACION pub, CV_USUARIO u, CV_PERSONA p " +
					"WHERE pub.IDFICHA=? AND pub.ESTADO=1 AND pub.IDPUBLICADOR=u.IDUSUARIO AND u.IDUSUARIO=p.IDPERSONA AND pub.IDHERRAMIENTA = ? " +
					"ORDER BY pub.FECHA DESC";
		
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, idFicha);
			stmt.setInt(2, idHerramienta);
			result = stmt.executeQuery();
			while (result.next()) {
				pub = new Publicacion();
				pub.setIdPublicacion(result.getInt("IDPUBLICACION"));
				pub.setContenido(result.getString("CONTENIDO"));
				pub.setTitulo(result.getString("TITULO"));				
				pub.setFecha(Formato.dateToCalendar(result.getString("FECHA")));
				
				usuario = new Usuario();
				usuario.setId(result.getInt("IDPUBLICADOR"));
				usuario.setUsuario(result.getString("USUARIO"));
				usuario.setPersona(new Persona(result.getString("NOMUNO"),result.getString("NOMDOS"),result.getString("APEPATERNO"),result.getString("APEMATERNO"),result.getString("SEXO")));
				
				pub.setUsuario(usuario);
				pub.setArchivoNombre(result.getString("ARCHIVO_NOMBRE"));
				pub.setArchivoTamanio(result.getLong("ARCHIVO_TAMANIO"));
				publicaciones.add(pub);
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
		return publicaciones;
	}
	
	public Integer nuevaPublicacion(Publicacion pub) throws DAOException {	
		log.info("nuevaPublicacion(Publicacion pub)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;

		Integer id = null;
		try {
			String query = "INSERT INTO CV_PUBLICACION(IDHERRAMIENTA,IDFICHA,TITULO,ARCHIVO_NOMBRE,ARCHIVO_TAMANIO,FECHA,ESTADO,CONTENIDO,IDPUBLICADOR) " +
					"VALUES (?,?,?,?,?,NOW(),?,?,?)";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, pub.getIdHerramienta());
			stmt.setInt(2, pub.getIdFicha());
			stmt.setString(3, pub.getTitulo());
			stmt.setString(4, pub.getArchivoNombre());
			stmt.setLong(5, pub.getArchivoTamanio());
			stmt.setInt(6, Constante.ESTADO_ACTIVO);
			stmt.setString(7, pub.getContenido());
			stmt.setInt(8, pub.getUsuario().getId());

			if (1 != stmt.executeUpdate()) {
				throw new DAOException("No se pudo insertar una nueva publicacion");
			}
			
			result = stmt.getGeneratedKeys();
			result.next();
			id = result.getInt(1);
		} catch (DAOException e) {
			log.error(e);
			throw new DAOException(e.toString());
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
		return id;
	}
	
	public void publicarAgain(Integer idPublicacion) throws DAOException {
		log.info("publicarAgain("+idPublicacion+")");
		PreparedStatement stmt = null;
		Connection cons = null;
		
		try {
			String query = "DELETE FROM CV_PUBLICACION_MATRICULA WHERE IDPUBLICACION = ? ";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idPublicacion);
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

	public void eliminarPublicacion(Integer idPublicacion) throws DAOException {
		log.info("eliminarPublicacion("+idPublicacion+")");
		PreparedStatement stmt = null;
		Connection cons = null;

		String query = "update CV_PUBLICACION set ESTADO = '0' where IDPUBLICACION = ?";
		try {
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idPublicacion);
			if (1 != stmt.executeUpdate()) {
				throw new DAOException("No se pudo eliminar CV_PUBLICACION");
			}
		} catch (DAOException e) {
			log.error(e);
			throw new DAOException(e.toString());
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
	
	public void publicacionLeida(Integer idPublicacion, Integer idMatricula) throws DAOException {
		log.info("eliminarPublicacion("+idPublicacion+", "+idMatricula+")");
		PreparedStatement stmt = null;
		Connection cons = null;

		try {
			String query = "INSERT INTO CV_PUBLICACION_MATRICULA (IDPUBLICACION, IDMATRICULA) VALUES (?,?)";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idPublicacion);
			stmt.setInt(2, idMatricula);
			if (1 != stmt.executeUpdate()) {
				throw new DAOException("No se pudo insertar CV_PUBLICACION_MATRICULA");
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
	
	public Collection<Publicacion> listarPublicacionesNoLeidas(Integer idMatricula) throws DAOException {
		log.info("listarPublicacionesNoLeidas("+idMatricula+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		Collection<Publicacion> pubs = new ArrayList<Publicacion>();
		Publicacion pub = null;
		Usuario usuario = null;
		
		try {
			String query = "SELECT pub.IDPUBLICACION, pub.IDHERRAMIENTA, pub.TITULO, pub.CONTENIDO, pub.FECHA,pub.ARCHIVO_NOMBRE, pub.ARCHIVO_TAMANIO, " +
				"pub.IDPUBLICADOR, u.USUARIO, p.APEPATERNO, p.APEMATERNO, p.NOMUNO,p.NOMDOS, p.SEXO " +
				"FROM cv_publicacion pub, cv_usuario u, cv_persona p " +
				"WHERE pub.IDPUBLICADOR=u.IDUSUARIO AND u.IDUSUARIO=p.IDPERSONA AND pub.ESTADO = 1 " +
				"AND pub.IDFICHA=(SELECT IDFICHA FROM cv_matricula WHERE IDMATRICULA=?) AND (SELECT COUNT(*) FROM cv_publicacion_matricula WHERE  IDPUBLICACION=pub.IDPUBLICACION AND IDMATRICULA=? AND ESTADO=1)=0 " +
				"ORDER BY pub.FECHA DESC";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idMatricula);
			stmt.setInt(2, idMatricula);
			result =  stmt.executeQuery();
			while (result.next()) {
				pub = new Publicacion();
				pub.setIdPublicacion(result.getInt("IDPUBLICACION"));
				pub.setContenido(result.getString("CONTENIDO"));
				pub.setTitulo(result.getString("TITULO"));
				pub.setFecha(Formato.dateToCalendar(result.getString("FECHA")));
				
				usuario = new Usuario();
				usuario.setId(result.getInt("IDPUBLICADOR"));
				usuario.setUsuario(result.getString("USUARIO"));
				usuario.setPersona(new Persona(result.getString("NOMUNO"),result.getString("NOMDOS"),result.getString("APEPATERNO"),result.getString("APEMATERNO"),result.getString("SEXO")));
				
				pub.setUsuario(usuario);
				pub.setArchivoNombre(result.getString("ARCHIVO_NOMBRE"));
				pub.setArchivoTamanio(result.getLong("ARCHIVO_TAMANIO"));
				pub.setIdHerramienta(result.getInt("IDHERRAMIENTA"));
				pubs.add(pub);
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
		return pubs;
	}

}