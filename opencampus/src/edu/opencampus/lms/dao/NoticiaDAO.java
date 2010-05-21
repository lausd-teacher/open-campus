package edu.opencampus.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.modelo.Noticia;
import edu.opencampus.lms.modelo.Periodo;
import edu.opencampus.lms.modelo.ReglaDeServicio;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.modelo.noticia.Seccion;
import edu.opencampus.lms.modelo.usuario.Rol;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;

public class NoticiaDAO extends BaseDAO {

	protected DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Collection<Noticia> cargarPortada(Usuario user) throws DAOException {
		log.info("cargarPortada(Usuario user)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		ResultSet subResult = null;
		Noticia noticia = null;
		Collection<Noticia> noticias = new ArrayList<Noticia>();
		Set<Integer> ids = new HashSet<Integer>();
		try {

			String query = "select n.idnoticia, s.nombre seccion, n.titular, n.fecha_inicio, n.imagen_nombre, n.sumilla " +
					"from cv_noticia n, cv_noticia_seccion s " +
					"where n.idseccion=s.idseccion  " +
					"and n.estado=1 and curdate()>=n.fecha_inicio order by n.fecha_inicio desc";
			
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			result = stmt.executeQuery();
			while (result.next()) {
				
				query = "select idjerarquia, idperiodo, idrol from cv_regla_servicio where idservicio=?";
				
				stmt = cons.prepareStatement(query);
				stmt.setInt(1, result.getInt("idnoticia"));
				subResult = stmt.executeQuery();
				while (subResult.next() && noticias.size()<Constante.PORTAL_CANTIDAD_NOTICIAS) {
					
					Integer idjerarquia = subResult.getInt("idjerarquia");
					Integer idperiodo = subResult.getInt("idperiodo");
					Integer idrol = subResult.getInt("idrol");
					
					if(user.getRoles().contains(new Rol(Constante.ROL_CAMPUS_ADMINISTRADOR))|| (
							( idrol == 0 || user.getRoles().contains(new Rol(idrol)) ) &&
							( idperiodo == 0 || user.getPeriodos().contains(new Periodo(idperiodo)) ) &&
							( idjerarquia == 0 || user.contieneJerarquiaPadre(idjerarquia) 
									|| user.contienePermisoPadre(idjerarquia) || user.contienePermisoHijo(idjerarquia))
					)){
						
						noticia = new Noticia();
						noticia.setIdnoticia(result.getInt("idnoticia"));
						noticia.setTitular(result.getString("titular"));
						noticia.setCuerpo(result.getString("sumilla"));
						noticia.setImagen(result.getString("seccion"));
						noticia.setFecha(Formato.dateToCalendar(result.getString("fecha_inicio")));
						noticias.add(noticia);
						
						ids.add(noticia.getIdnoticia());
						
						break;
					}
				}
				
			}
			
			// Crea Arreglo de Id's permitidos
			user.setMisNoticias(ids);
			
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(subResult);
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return noticias;
	}
		
	public Collection<Seccion> cargarTitulares(Usuario user) throws DAOException {
		log.info("cargarTitulares(Usuario user)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		ResultSet subResult = null;
		Collection<Seccion> secciones = new ArrayList<Seccion>();
		Seccion seccion = null;
		Collection<Noticia> noticias = new ArrayList<Noticia>();
		Noticia noticia = null;
		Set<Integer> ids = new HashSet<Integer>();
		
		try {
	
			// SECCION DE TODOS ************************************** 
			
			String query = "select idnoticia, titular, fecha_inicio, imagen_nombre, sumilla " +
					"from cv_noticia " +
					"where estado=1 and curdate()>=fecha_inicio order by fecha_inicio desc";

			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			
			result = stmt.executeQuery();
			while (result.next()) {

				query = "select idjerarquia, idperiodo, idrol from cv_regla_servicio where idservicio=?";
				
				stmt = cons.prepareStatement(query);
				stmt.setInt(1, result.getInt("idnoticia"));
				subResult = stmt.executeQuery();
				while (subResult.next()) {
					
					Integer idjerarquia = subResult.getInt("idjerarquia");
					Integer idperiodo = subResult.getInt("idperiodo");
					Integer idrol = subResult.getInt("idrol");
					
					if(user.getRoles().contains(new Rol(Constante.ROL_CAMPUS_ADMINISTRADOR))|| (
							( idrol == 0 || user.getRoles().contains(new Rol(idrol)) ) &&
							( idperiodo == 0 || user.getPeriodos().contains(new Periodo(idperiodo)) ) &&
							( idjerarquia == 0 || user.contieneJerarquiaPadre(idjerarquia) 
									|| user.contienePermisoPadre(idjerarquia) || user.contienePermisoHijo(idjerarquia))
					)){
						
						noticia = new Noticia();
						noticia.setIdnoticia(result.getInt("idnoticia"));
						noticia.setTitular(result.getString("titular"));
						noticia.setCuerpo(result.getString("sumilla"));
						noticia.setImagen(result.getString("imagen_nombre"));
						noticia.setFecha(Formato.dateToCalendar(result.getString("fecha_inicio")));
						noticias.add(noticia);
						
						ids.add(noticia.getIdnoticia());
						
						break;
					}
				}
				
			}
			if(!noticias.isEmpty()){
				seccion = new Seccion();
				seccion.setNombre("Todas");
				seccion.setNoticias(noticias);
				
				secciones.add(seccion);
			}
			
			// Crea Arreglo de Id's permitidos
			user.setMisNoticias(ids);
			
			// FIN SECCION *******************************************
			
			
			// LAS DEMAS SECCIONES ***********************************
			
			// Secciones
			query = "select idseccion,nombre from cv_noticia_seccion order by indice";

			stmt = cons.prepareStatement(query);
			result = stmt.executeQuery();
			
			// Noticias por seccion 
			query = "select idnoticia, titular, fecha_inicio, imagen_nombre, sumilla " +
					"from cv_noticia " +
					"where estado=1 and curdate()>=fecha_inicio and idseccion=? order by fecha_inicio desc";
			
			stmt =  cons.prepareStatement(query);
			
			while (result.next()) {
				
				noticias = new ArrayList<Noticia>();
				
				stmt.setInt(1, result.getInt("idseccion"));
				subResult =  stmt.executeQuery();
				while (subResult.next()) {
					noticia = new Noticia();
					noticia.setIdnoticia(subResult.getInt("idnoticia"));
					noticia.setTitular(subResult.getString("titular"));
					noticia.setCuerpo(subResult.getString("sumilla"));
					noticia.setImagen(subResult.getString("imagen_nombre"));
					noticia.setFecha(Formato.dateToCalendar(subResult.getString("fecha_inicio")));
					
					noticias.add(noticia);
				}
				if(!noticias.isEmpty()){
					seccion = new Seccion();
					seccion.setIdSeccion(result.getInt("idseccion"));
					seccion.setNombre(result.getString("nombre"));
					seccion.setNoticias(noticias);
					
					secciones.add(seccion);
				}
			}
			
			// FIN SECCIONES *****************************************
			
			// SECCION RSS *******************************************
			
//			seccion = new Seccion();
//			seccion.setNombre("ElComercio");
//			seccion.setNoticias(new ArrayList<Noticia>());
//			
//			secciones.add(seccion);
			
			// SECCION RSS *******************************************
			
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(subResult);
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return secciones;
	}
	
	//******************************** NOTICIAS ************************************
	
	public Collection<Noticia> listar() throws DAOException {
		log.info("listar()");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Collection<Noticia> noticias = new ArrayList<Noticia>();
		Noticia noticia = null;
		Seccion seccion= null;
		try {
			String query = "select n.idnoticia,n.titular,n.fecha_inicio,n.estado,s.nombre " +
					"from cv_noticia n, cv_noticia_seccion s where n.idseccion=s.idseccion order by n.fecha_inicio desc";

			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			
			result =  stmt.executeQuery();
			while (result.next()) {
				noticia = new Noticia();
				noticia.setIdnoticia(result.getInt("idnoticia"));
				noticia.setTitular(result.getString("titular"));
				noticia.setFecha(Formato.dateToCalendar(result.getString("fecha_inicio")));
				noticia.setEstado(result.getInt("estado"));
				
				seccion = new Seccion();
				seccion.setNombre(result.getString("nombre"));
				
				noticia.setSeccion(seccion);
				
				noticias.add(noticia);
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
		return noticias;
	}
	
	public Noticia verNoticia(int idnoticia) throws DAOException {
		log.info("verNoticia()");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		Noticia noticia = null;
		Seccion seccion= null;
		try {
			String query = "select n.idnoticia,n.titular,n.cuerpo,n.fecha_inicio,n.idseccion,n.imagen_nombre,n.formato,n.estado,s.nombre " +
					"from cv_noticia n, cv_noticia_seccion s where n.idseccion=s.idseccion and idnoticia=?";

			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idnoticia);
			
			result =  stmt.executeQuery();
			while (result.next()) {
				noticia = new Noticia();
				noticia.setIdnoticia(result.getInt("IDNOTICIA"));
				noticia.setTitular(result.getString("TITULAR"));
				noticia.setCuerpo(result.getString("CUERPO"));
				noticia.setFecha(Formato.dateToCalendar(result.getString("FECHA_INICIO")));
				noticia.setImagen(result.getString("IMAGEN_NOMBRE"));
				noticia.setFormato(result.getInt("FORMATO"));
				noticia.setEstado(result.getInt("ESTADO"));
				
				seccion = new Seccion();
				seccion.setIdSeccion(result.getInt("IDSECCION"));
				seccion.setNombre(result.getString("NOMBRE"));
				
				noticia.setSeccion(seccion);
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
		return noticia;
	}
	
	public Noticia obtenerNoticia(int idnoticia) throws DAOException {
		log.info("obtenerNoticia()");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		Noticia noticia = null;
		Seccion seccion = null;
		ReglaDeServicio regla = null;
		Collection<ReglaDeServicio> reglas = new ArrayList<ReglaDeServicio>();
		
		try {
			String query = "select n.idnoticia,n.titular,n.cuerpo,n.sumilla,n.fecha_inicio,n.imagen_nombre,n.formato,n.idseccion " +
					"from cv_noticia n where idnoticia=?";

			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idnoticia);
			
			result =  stmt.executeQuery();
			
			if (result.next()) {
				noticia = new Noticia();
				noticia.setIdnoticia(result.getInt("IDNOTICIA"));
				noticia.setTitular(result.getString("TITULAR"));
				noticia.setCuerpo(result.getString("CUERPO"));
				noticia.setSumilla(result.getString("SUMILLA"));
				noticia.setFecha(Formato.dateToCalendar(result.getString("FECHA_INICIO")));
				noticia.setImagen(result.getString("IMAGEN_NOMBRE"));
				noticia.setFormato(result.getInt("FORMATO"));
				seccion = new Seccion();
				seccion.setIdSeccion(result.getInt("IDSECCION"));
				noticia.setSeccion(seccion);
				
				//Regla de Servicio
				query = "select idjerarquia,idperiodo,idrol from cv_regla_servicio where idservicio=?";
				
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, idnoticia);
				
				result =  stmt.executeQuery();
				
				while (result.next()) {
					regla = new ReglaDeServicio();
					regla.setIdJerarquia(result.getInt("idjerarquia"));
					regla.setIdPeriodo(result.getInt("idperiodo"));
					regla.setIdRol(result.getInt("idrol"));
					
					reglas.add(regla);
				}
				
				noticia.setReglaDeServicio(reglas);
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
		return noticia;
	}
	
	public void cambiarEstado(int idNoticia, int estado) throws DAOException {
		log.info("cambiarEstado(int "+idNoticia+", int "+estado+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "update cv_noticia set estado=? where idnoticia=?";

			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, estado);
			stmt.setInt(2, idNoticia);
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en cambiarEstado(int idNoticia, int estado)");
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
	
	public void crearNoticia(Noticia noticia) throws DAOException {
		log.info("crearNoticia(Noticia noticia)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		int id = 0;
		try {
			//Insert into cv_servicio
			String query = "insert into cv_servicio(tipo) values("+Constante.TIPO_SERVICIO_NOTICIA+")";
			cons = dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt =  cons.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.executeUpdate();
			result = stmt.getGeneratedKeys();
			if(result.next())id = result.getInt(1);
			noticia.setIdnoticia(id);
			
			//Insert into cv_noticia
			if(null != noticia.getImagen()){
				query = "insert into cv_noticia (idnoticia,titular,sumilla,cuerpo,fecha_inicio,idseccion,formato,estado,creado_por,modificado_por,imagen_nombre) values (?,?,?,?,?,?,?,?,?,?,?)";
			}else{
				query = "insert into cv_noticia (idnoticia,titular,sumilla,cuerpo,fecha_inicio,idseccion,formato,estado,creado_por,modificado_por) values (?,?,?,?,?,?,?,?,?,?)";
			}
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, noticia.getIdnoticia());
			stmt.setString(2, noticia.getTitular());
			stmt.setString(3, noticia.getSumilla());
			stmt.setString(4, noticia.getCuerpo());
			stmt.setString(5, Formato.calendarToDate(noticia.getFecha()));
			stmt.setInt(6, noticia.getSeccion().getIdSeccion());
			stmt.setInt(7, noticia.getFormato());
			stmt.setInt(8, noticia.getEstado());
			stmt.setString(9, noticia.getUsuarioCreacion());
			stmt.setString(10, noticia.getUsuarioCreacion());
			
			if(null != noticia.getImagen()){
				noticia.setImagen(Constante.IMAGEN +Constante.UNDERLINE + Formato.completarCeros(id, 10) + noticia.getImagen());
				stmt.setString(11, noticia.getImagen());
			}
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en crearNoticia(Noticia noticia) - INSERT INTO cv_noticia");
				throw new DAOException("No culmino");
			}
			
			// REGLA ************************************************
			
			query = "insert into cv_regla_servicio (idservicio,idjerarquia,idperiodo,idrol) values(?,?,?,?)";
			stmt =  cons.prepareStatement(query);
			
			for (ReglaDeServicio rs: noticia.getReglaDeServicio()) {
				stmt.setInt(1, noticia.getIdnoticia());
				stmt.setString(2, (rs.getIdJerarquia() == null)?null:String.valueOf(rs.getIdJerarquia()));
				stmt.setString(3, (rs.getIdPeriodo() == null)?null:String.valueOf(rs.getIdPeriodo()));
				stmt.setString(4, (rs.getIdRol() == null)?null:String.valueOf(rs.getIdRol()));
				
				if (1 != stmt.executeUpdate()) {
					log.error("Error en crearNoticia(Noticia noticia) - INSERT INTO cv_regla_servicio");
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
				log.error(e);
			}
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	public void modificarNoticia(Noticia noticia) throws DAOException {
		log.info("modificarNoticia(Noticia noticia)");
		Connection cons = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet result = null;
		
		try {
			String query = null;

			if(null != noticia.getImagen()){
				query = "update cv_noticia set titular=?, sumilla=?, cuerpo=?, fecha_inicio=?, idseccion=?, formato=?, " +
						"modificado_por=?, modificado_en=now(), imagen_nombre=? where idnoticia=?";
			}else{
				query = "update cv_noticia set titular=?, sumilla=?, cuerpo=?, fecha_inicio=?, idseccion=?, formato=?, " +
						"modificado_por=?, modificado_en=now() where idnoticia=?";
			}
			cons = dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, noticia.getTitular());
			stmt.setString(2, noticia.getSumilla());
			stmt.setString(3, noticia.getCuerpo());
			stmt.setString(4, Formato.calendarToDate(noticia.getFecha()));
			stmt.setInt(5, noticia.getSeccion().getIdSeccion());
			stmt.setInt(6, noticia.getFormato());
			stmt.setString(7, noticia.getUsuarioModificacion());
			
			if(null != noticia.getImagen()){
				stmt.setString(8, noticia.getImagen());
				stmt.setInt(9, noticia.getIdnoticia());
			}else{
				stmt.setInt(8, noticia.getIdnoticia());
			}
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en modificarNoticia(Noticia noticia) - UPDATE cv_noticia");
				throw new DAOException("No culmino");
			}
			
			// ELIMINAR LAS REGLAS DE LA NOTICIA ************
			
			query = "delete from cv_regla_servicio where idservicio=?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, noticia.getIdnoticia());
			stmt.executeUpdate();
			
			//INSERTAR LA NUEVA RELACION DE REGLA-NOTICIA *********
			
			query = "insert into cv_regla_servicio (idservicio,idjerarquia,idperiodo,idrol) values(?,?,?,?)";
			stmt =  cons.prepareStatement(query);
			
			for (ReglaDeServicio rs: noticia.getReglaDeServicio()) {
				stmt.setInt(1, noticia.getIdnoticia());
				stmt.setString(2, (rs.getIdJerarquia() == null)?null:String.valueOf(rs.getIdJerarquia()));
				stmt.setString(3, (rs.getIdPeriodo() == null)?null:String.valueOf(rs.getIdPeriodo()));
				stmt.setString(4, (rs.getIdRol() == null)?null:String.valueOf(rs.getIdRol()));
				
				if (1 != stmt.executeUpdate()) {
					log.error("Error en crearNoticia(Noticia noticia) - INSERT INTO cv_regla_servicio");
					throw new DAOException("No culmino");
				}
			
			}
			
			//******************************************************
			
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
				log.error(e);
			}
			closeResultSet(result);
			closeStatement(stmt);
			closeStatement(stmt2);
			closeConnection(cons);
		}
	}
	
	public void eliminarNoticia(int idNoticia) throws DAOException {
		log.info("eliminarNoticia(int "+idNoticia+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			
			// ELIMINAR LAS REGLAS DE LA NOTICIA ************
			
			String query = "delete from cv_regla_servicio where idservicio=?";
			cons =  dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idNoticia);
			stmt.executeUpdate();
			
			// ELIMINAR LA NOTICIA ***************************
			
			query = "delete from cv_noticia where idnoticia=?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idNoticia);
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error al intentar eliminar noticia de id: " + idNoticia);
				throw new DAOException("No culmino");
			}
			
			//Transaccion exitosa
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
				log.error(e);
			}
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

	//************************* SECCIONES *****************************************//
	
	public Collection<Seccion> listarSecciones() throws DAOException {
		log.info("listarSecciones()");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Collection<Seccion> secciones = new ArrayList<Seccion>();
		Seccion seccion = null;
		try {
			String query = "select idseccion,nombre,indice from cv_noticia_seccion order by indice";

			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			
			result =  stmt.executeQuery();
			while (result.next()) {
				seccion = new Seccion();
				seccion.setIdSeccion(result.getInt("idseccion"));
				seccion.setNombre(result.getString("nombre"));
				seccion.setIndice(result.getInt("indice"));
				secciones.add(seccion);
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
		return secciones;
	}
	
	public void crearSeccion(Seccion seccion) throws DAOException {
		log.info("crearSeccion(Seccion "+seccion.getNombre()+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String query = "select ifnull(max(indice),0)+1 indice from cv_noticia_seccion";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			result = stmt.executeQuery();
			int indice = (result.next())?result.getInt("indice"):1;
			
			query = "insert into cv_noticia_seccion (nombre,indice) values(?, ?);";
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, seccion.getNombre());
			stmt.setInt(2, indice);
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en crearSeccion(Seccion seccion)");
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
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	public void eliminarSeccion(int idSeccion) throws DAOException {
		log.info("eliminarSeccion(int "+idSeccion+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String query = "select indice from cv_noticia_seccion where idseccion=?";
			cons = dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idSeccion);
			result = stmt.executeQuery();
			int indice = (result.next())?result.getInt("indice"):1;
			
			query = "update cv_noticia_seccion set indice=indice-1 where indice>=?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, indice);

			if (stmt.executeUpdate() == 0) {
				log.error("No fue posible actualizar el indice.");
				throw new DAOException("No fue posible actualizar el indice.");
			}

			query = "delete from cv_noticia_seccion where idseccion=?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idSeccion);

			if (1 != stmt.executeUpdate()) {
				log.error("Error en eliminarUnidad(String idSilabo, String idUnidad)");
				throw new DAOException("No culmino");
			}
			//Transaccion exitosa
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
				log.error(e);
			}
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	public void modificarIndiceSeccion(boolean cmd, int idSeccion) throws DAOException {
		log.info("modificarIndiceSeccion(boolean "+cmd+", int "+idSeccion+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null; 
	
		try {
			cons = dataSource.getConnection();
			cons.setAutoCommit(false);
			
			if(cmd){
				String query = "select indice-1 indice from cv_noticia_seccion where idseccion=?";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, idSeccion);
				result = stmt.executeQuery();
				int indice = (result.next())?result.getInt("indice"):1;
				
				if(indice != 0){
					
					query = "update cv_noticia_seccion set indice=indice+1 where indice=?";
					stmt =  cons.prepareStatement(query);
					stmt.setInt(1, indice);
					if (1 != stmt.executeUpdate()) {
						log.error("Error en cmd=up al bajar elemento");
						throw new DAOException("No culmino");
					}
					
					query = "update cv_noticia_seccion set indice=indice-1 where idseccion=?";
					stmt =  cons.prepareStatement(query);
					stmt.setInt(1, idSeccion);
					if (1 != stmt.executeUpdate()) {
						log.error("Error en cmd=down al subir elemento");
						throw new DAOException("No culmino");
					}
					
				}
				
			}else{
				
				String query = "select indice+1 indice from cv_noticia_seccion where idseccion=?";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, idSeccion);
				result = stmt.executeQuery();
				int indice = (result.next())?result.getInt("indice"):1;
				
				query = "update cv_noticia_seccion set indice=indice-1 where indice=?";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, indice);
				if (1 != stmt.executeUpdate()) {
					log.error("Error en cmd=up al bajar elemento");
					throw new DAOException("No culmino");
				}else{
					
					query = "update cv_noticia_seccion set indice=indice+1 where idseccion=?";
					stmt =  cons.prepareStatement(query);
					stmt.setInt(1, idSeccion);
					if (1 != stmt.executeUpdate()) {
						log.error("Error en cmd=down al subir elemento");
						throw new DAOException("No culmino");
					}
					
				}
				
			}
			// Transaccion exitosa
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
				log.error(e);
			}
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	public void modificarSeccion(Seccion seccion) throws DAOException {
		log.info("modificarSeccion(Seccion "+seccion.getNombre()+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "update cv_noticia_seccion set nombre=? where idseccion=?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, seccion.getNombre());
			stmt.setInt(2, seccion.getIdSeccion());
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en modificarSeccion(Seccion seccion)");
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

	//******************************** NOTICIAS INTRANET *********************************
	
	public Collection<Noticia> listarNoticiasIntranet() throws DAOException {
		log.info("listarNoticiasIntranet()");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Noticia noticia = null;
		Collection<Noticia> noticias = new ArrayList<Noticia>();
		
		try {
			
			String query = "select n.idnoticia,n.titular,n.sumilla, n.fecha_inicio, n.imagen_nombre " +
					"from cv_regla_servicio r, cv_noticia n, cv_servicio s " +
					"where r.idservicio=s.idservicio and s.idservicio=n.idnoticia " +
					"and n.estado=1 and curdate()>=n.fecha_inicio and idjerarquia is null and idperiodo is null and idrol is null " +
					"order by fecha_inicio desc limit 0, 10";

			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			
			result =  stmt.executeQuery();
			while (result.next()) {
				noticia = new Noticia();
				noticia.setIdnoticia(result.getInt("idnoticia"));
				noticia.setTitular(result.getString("titular"));
				noticia.setSumilla(result.getString("sumilla"));
				noticia.setImagen(result.getString("imagen_nombre"));
				noticia.setFecha(Formato.dateToCalendar(result.getString("fecha_inicio")));
				noticias.add(noticia);
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
		return noticias;
	}
	
	
	public Noticia obtenerNoticiaDetalle(String idNoticia) throws DAOException {
		log.info("obtenerNoticiaDetalle()");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Noticia noticia = null;
		
		try {
			
			String query = "select n.idnoticia,n.titular, n.fecha_inicio, n.imagen_nombre, n.cuerpo " +
					"from cv_regla_servicio r, cv_noticia n " +
					"where r.idservicio=n.idnoticia " +
					"and n.estado=1 and curdate()>=n.fecha_inicio and idjerarquia is null and idperiodo is null and idrol is null " +
					"and n.idnoticia=?";

			cons = dataSource.getConnection();			
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, idNoticia);
			
			result =  stmt.executeQuery();
			if (result.next()) {
				noticia = new Noticia();
				noticia.setIdnoticia(result.getInt("idnoticia"));
				noticia.setTitular(result.getString("titular"));
				noticia.setImagen(result.getString("imagen_nombre"));
				noticia.setCuerpo(result.getString("cuerpo"));
				noticia.setFecha(Formato.dateToCalendar(result.getString("fecha_inicio")));
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
		return noticia;
	}
	
	public boolean esPublica(Integer idnoticia) throws DAOException {
		log.info("esPublica(Integer idnoticia)"+idnoticia);
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		boolean esPublica = false;
		try {
			String query = "select idservicio from cv_regla_servicio where idservicio=? and idjerarquia is null and idperiodo is null and idrol is null";
			cons = dataSource.getConnection();			
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idnoticia);
			result =  stmt.executeQuery();
			if (result.next()) {
				esPublica = true;
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
		return esPublica;
	}
	
}