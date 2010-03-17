package edu.tecsup.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.modelo.Jerarquia;
import edu.tecsup.lms.modelo.Periodo;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.modelo.usuario.Persona;
import edu.tecsup.lms.modelo.usuario.Rol;
import edu.tecsup.lms.modelo.usuario.Ubigeo;
import edu.tecsup.lms.util.Constante;
import edu.tecsup.lms.util.Formato;

public class UsuarioDAO extends BaseDAO {

	protected DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Usuario validar(Usuario user) throws DAOException {
		log.info("validar(idusuario: "+user.getUsuario()+" - clave: "+user.getClave()+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String query = "select idusuario, clave, estado from cv_usuario where usuario=?";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setString(1, user.getUsuario());
			result = stmt.executeQuery();
			
			if (result.next()) {
				String clave = result.getString("clave");
				int estado = result.getInt("estado");
				if(user.getClave().equals(clave)){
					if(estado == Constante.ESTADO_ACTIVO){
						user.setId(result.getInt("idusuario"));
						user.setEstadoSeguridad(Constante.SEGURIDAD_USUARIO_ACEPTADA);
						
					}else{
						user.setEstadoSeguridad(Constante.SEGURIDAD_USUARIO_DESABILITADO);
					}
					
				}else{
					user.setEstadoSeguridad(Constante.SEGURIDAD_USUARIO_PASSWORD_ERRONEO);
				}
				
			}else{
				user.setEstadoSeguridad(Constante.SEGURIDAD_USUARIO_NO_ENCONTRADO); 
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeConnection(cons);
			closeStatement(stmt);
			closeResultSet(result);
		}
		return user;
	}
	
	public String violar(String usuario) throws DAOException {
		log.info("violar(usuario: "+usuario+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String clave = null;
		try {
			String query = "select clave, estado from cv_usuario where usuario=?";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setString(1, usuario);
			result = stmt.executeQuery();
			
			if (result.next()) {
				if(result.getInt("estado") == Constante.ESTADO_ACTIVO){
					clave = result.getString("clave");			
				}else{
					log.error("Usuario deshabilitado: "+usuario);
				}
			}else{
				log.error("Usuario no encontrado: "+usuario);
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeConnection(cons);
			closeStatement(stmt);
			closeResultSet(result);
		}
		return clave;
	}
	
	public Usuario obtenerUsuario(Integer id) throws DAOException {
		log.info("obtenerUsuario("+id+")");
		
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Usuario user = null;
		try {
			//Datos de Usuario
			String query = "select u.usuario, u.tipo, u.estado, u.idioma, u.estado_foro, u.avatar " +
					"from cv_usuario u where u.idusuario=?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, id);
			result = stmt.executeQuery();
			if (result.next()) {
				user = new Usuario(id);
				user.setUsuario(result.getString("usuario"));
				user.setTipo(result.getString("tipo"));
				user.setEstado(result.getInt("estado"));
				user.setIdioma(result.getString("idioma"));
//				user.setEstadoForo(result.getInt("estado_foro"));
//				user.setAvatar(result.getInt("avatar"));	//Pendiente
			
				if(Constante.TIPO_USUARIO_PERSONA.equals(user.getTipo())){
					
					log.info("Obteniendo Persona ...");
					// Datos Personales
					query = "select u.usuario, u.tipo, u.estado, u.idioma, u.estado_foro, u.avatar, " +
							"p.nomuno, p.nomdos, p.apepaterno, p.apematerno, p.fecnacimiento, p.sexo, p.dirdomicilio, p.teldomicilio, p.telcelular, p.email, p.dni, " +
							"pa.idpais, pa.nombre pais, de.iddepartamento, de.nombre departamento, pr.idprovincia, pr.nombre provincia, di.iddistrito, di.nombre distrito " +
							"from cv_usuario u, cv_persona p, cv_distrito di, cv_provincia pr, cv_departamento de, cv_pais pa " +
							"where u.idusuario=p.idpersona and p.idubigeo=di.iddistrito and di.idprovincia=pr.idprovincia and pr.iddepartamento=de.iddepartamento and de.idpais=pa.idpais  " +
							"and u.idusuario=?";
					stmt =  cons.prepareStatement(query);
					stmt.setInt(1, user.getId());
					result = stmt.executeQuery();
					if (result.next()) {
						Persona p = new Persona();
						p.setNomuno(result.getString("nomuno"));
						p.setNomdos(result.getString("nomdos"));
						p.setApepaterno(result.getString("apepaterno"));
						p.setApematerno(result.getString("apematerno"));
						p.setFecnacimiento(Formato.dateToCalendar(result.getString("fecnacimiento")));
						p.setSexo(result.getString("sexo"));
						p.setDirdomicilio(result.getString("dirdomicilio"));
						p.setTeldomicilio(result.getString("teldomicilio"));
						p.setTelcelular(result.getString("telcelular"));
						p.setEmail(Formato.formatoTexto(result.getString("email")));
						p.setDni(result.getString("dni"));
						
						Ubigeo u = new Ubigeo();
						u.setIdPais(result.getString("idpais"));
						u.setPais(result.getString("pais"));
						u.setIdDepartamento(result.getString("iddepartamento"));
						u.setDepartamento(result.getString("departamento"));
						u.setIdProvincia(result.getString("idprovincia"));
						u.setProvincia(result.getString("provincia"));
						u.setIdDistrito(result.getString("iddistrito"));
						u.setDistrito(result.getString("distrito"));
						
						p.setUbigeo(u);
						
						user.setPersona(p);
						
					}else{
						throw new DAOException("No se encontro datos de Persona.");
					}
					
				}else{
					log.info("Obteniendo Empresa ...");
					//Obtener Empresa
				}
				
				//Roles
				Rol rol = null;
				query = "select r.idrol,r.nombre from cv_usuario_rol u, cv_rol r  " +
						"where u.idrol=r.idrol and u.estado=1 and r.estado=1 " +
						"and u.idusuario=?";
				stmt = cons.prepareStatement(query);
				stmt.setInt(1, user.getId());
				result = stmt.executeQuery();
				while (result.next()) {
					rol = new Rol();
					rol.setIdrol(result.getInt("idrol"));
					rol.setNombre(result.getString("nombre"));
					user.getRoles().add(rol);
				}
				if(user.getRoles().isEmpty()){
					user.getRoles().add(new Rol(Constante.ROL_CAMPUS_USUARIO));
				}
				
				//Permisos
				Jerarquia jerarquia = null;
				query = "select j.idjerarquia, j.idpredecesor,j.nombre from cv_usuario_jerarquia u, cv_jerarquia j " +
						"where u.idjerarquia=j.idjerarquia and j.tipo='G' and j.estado=1 and u.idusuario=?";
				stmt = cons.prepareStatement(query);
				stmt.setInt(1, user.getId());
				result = stmt.executeQuery();
				while (result.next()) {
					jerarquia = new Jerarquia();
					jerarquia.setIdJerarquia(result.getInt("idjerarquia"));
					jerarquia.setPadre(new Jerarquia(result.getInt("idpredecesor")));
					jerarquia.setNombre(result.getString("nombre"));
					user.getPermisos().add(jerarquia);
				}
				
				//Jerarquias
				query = "select distinct j.idjerarquia, j.nombre, j.idpredecesor " +
						"from cv_matricula m, cv_ficha f, cv_curso c, cv_jerarquia j, cv_periodo p " +
						"where m.idficha=f.idficha and c.idjerarquia=j.idjerarquia and f.idcurso=c.idcurso and f.idperiodo=p.idperiodo " +
						"and f.estado=1 and f.eliminado=0 and m.estado=1 and m.eliminado=0 " +
						"and p.fecha_inicio <= curdate() and p.fecha_fin >= curdate() and m.idrol != 1 " +
						"and m.idusuario=?";
				stmt = cons.prepareStatement(query);
				stmt.setInt(1, user.getId());
				result = stmt.executeQuery();
				while (result.next()) {
					jerarquia = new Jerarquia();
					jerarquia.setIdJerarquia(result.getInt("idjerarquia"));
					jerarquia.setPadre(new Jerarquia(result.getInt("idpredecesor")));
					jerarquia.setNombre(result.getString("nombre"));
					user.getJerarquias().add(jerarquia);
				}
				
				//Periodos
				Periodo periodo = null;
				query = "select distinct p.idperiodo, p.nombre " +
						"from cv_matricula m, cv_ficha f, cv_periodo p " +
						"where m.idficha=f.idficha and f.idperiodo=p.idperiodo " +
						"and f.estado=1 and f.eliminado=0 and m.estado=1 and m.eliminado=0 " +
						"and p.fecha_inicio <= curdate() and p.fecha_fin >= curdate() and m.idrol != 1 " +
						"and m.idusuario=?"; //si se desa se puede considerar solo periodos no personalizados
				stmt = cons.prepareStatement(query);
				stmt.setInt(1, user.getId());
				result = stmt.executeQuery();
				while (result.next()) {
					periodo = new Periodo();
					periodo.setIdPeriodo(result.getInt("idperiodo"));
					periodo.setNombre(result.getString("nombre"));
					user.getPeriodos().add(periodo);
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
		return user;
	}
	
	public Usuario obtenerSoloDatos(int id) throws DAOException {
		log.info("obtenerSoloDatos(int id)");
		
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Usuario usuario = null;
		try {
			// Datos Personales
			String query = "select u.idusuario, u.usuario, u.tipo, p.nomuno, p.nomdos, p.apepaterno, p.apematerno, p.fecnacimiento, p.sexo, p.dirdomicilio, p.teldomicilio, p.telcelular, p.email, p.dni, " +
					"pa.idpais, pa.nombre pais, de.iddepartamento, de.nombre departamento, pr.idprovincia, pr.nombre provincia, di.iddistrito, di.nombre distrito " +
					"from cv_usuario u, cv_persona p, cv_distrito di, cv_provincia pr, cv_departamento de, cv_pais pa " +
					"where u.idusuario=p.idpersona and p.idubigeo=di.iddistrito and di.idprovincia=pr.idprovincia and pr.iddepartamento=de.iddepartamento and de.idpais=pa.idpais  " +
					"and p.idpersona=?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, id);
			result = stmt.executeQuery();
			if (result.next()) {
				usuario = new Usuario();
				usuario.setId(id);
				usuario.setUsuario(result.getString("usuario"));
				usuario.setTipo(result.getString("tipo"));
				
				Persona p = new Persona();
				p.setNomuno(result.getString("nomuno"));
				p.setNomdos(result.getString("nomdos"));
				p.setApepaterno(result.getString("apepaterno"));
				p.setApematerno(result.getString("apematerno"));
				p.setFecnacimiento(Formato.dateToCalendar(result.getString("fecnacimiento")));
				p.setSexo(result.getString("sexo"));
				p.setDirdomicilio(result.getString("dirdomicilio"));
				p.setTeldomicilio(result.getString("teldomicilio"));
				p.setTelcelular(result.getString("telcelular"));
				p.setEmail(Formato.formatoTexto(result.getString("email")));
				p.setDni(result.getString("dni"));
				
				Ubigeo u = new Ubigeo();
				u.setIdPais(result.getString("idpais"));
				u.setPais(result.getString("pais"));
				u.setIdDepartamento(result.getString("iddepartamento"));
				u.setDepartamento(result.getString("departamento"));
				u.setIdProvincia(result.getString("idprovincia"));
				u.setProvincia(result.getString("provincia"));
				u.setIdDistrito(result.getString("iddistrito"));
				u.setDistrito(result.getString("distrito"));
				
				p.setUbigeo(u);
				
				usuario.setPersona(p);
			}
			
			//Roles
			Rol rol = null;
			query = "select r.idrol,r.nombre from cv_usuario_rol u, cv_rol r  " +
					"where u.idrol=r.idrol and u.estado=1 and r.estado=1 " +
					"and u.idusuario=?";
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, id);
			result = stmt.executeQuery();
			while (result.next()) {
				rol = new Rol();
				rol.setIdrol(result.getInt("idrol"));
				rol.setNombre(result.getString("nombre"));
				usuario.getRoles().add(rol);
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
	
	public int cambiarClaveUsuario(Usuario user) throws DAOException {
		log.info("cambiarClaveUsuario(String idUsuario)");
		Connection cons = null;
		PreparedStatement stmt = null;
		int r = Constante.PASSWORD_ERROR_DESCONOCIDO;
		try {
			String query = "update cv_usuario set clave=? where idusuario=?";
			cons = dataSource.getConnection();
			stmt = cons.prepareCall(query);
			stmt.setString(1, user.getClave());
			stmt.setInt(2, user.getId());
			if(0 != stmt.executeUpdate())
				r = Constante.PASSWORD_ACEPTADO;
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeConnection(cons);
			closeStatement(stmt);
		}
		return r;
	}
	
	public int cambiarEmail(int idusuario, String email) throws DAOException {
		PreparedStatement stmt = null;
		Connection cons = null;
		int r = 0;
		try {
			String query = "update cv_persona set email=? where idpersona=?";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setString(1, email);
			stmt.setInt(2, idusuario);
			r = stmt.executeUpdate();
		} catch (Exception e) {
			log.error(e.toString());
		}finally {
			closeConnection(cons);
			closeStatement(stmt);
		}
		return r;
	}
	

	public Collection<Usuario> verCumpleanieros() throws DAOException {
		log.info("cargarPortada() - verCumpleanieros()");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		ResultSet subResult = null;
		Collection<Usuario> usuarios = new ArrayList<Usuario>();
		Usuario usuario = null;
		List<Rol> roles = null;
		Persona datos = null;
		try {
			String query = "select apepaterno,apematerno,nomuno,nomdos,fecnacimiento,idpersona " +
					"from cv_persona " +
					"where estado=1 and curdate() = fecnacimiento order by apepaterno,apematerno,nomuno,nomdos " +
					"limit 0,"+Constante.PORTAL_CANTIDAD_CUMPLEANOS;
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			result = stmt.executeQuery();
			
			query = "select r.idrol,r.nombre from cv_usuario_rol u, cv_rol r  " +
					"where u.idrol=r.idrol and u.estado=1 and r.estado=1 " +
					"and u.idusuario=?";
			stmt = cons.prepareStatement(query);
			
			
			while (result.next()) {
				usuario = new Usuario();
				usuario.setTipo(Constante.TIPO_USUARIO_PERSONA);
				usuario.setId(result.getInt("idpersona"));
				
				datos = new Persona();
				datos.setApepaterno(result.getString("apepaterno"));
				datos.setApematerno(result.getString("apematerno"));
				datos.setNomuno(result.getString("nomuno"));
				datos.setNomdos(result.getString("nomdos"));
				datos.setFecnacimiento(Formato.dateToCalendar(result.getString("fecnacimiento")));
				usuario.setPersona(datos);
				
				stmt.setString(1, result.getString("idpersona"));
				subResult = (ResultSet) stmt.executeQuery();
				roles = new ArrayList<Rol>();
				while (subResult.next()) {
					roles.add(new Rol(subResult.getInt("idrol"), subResult.getString("nombre")));
				}
				usuario.setRoles(roles);
				
				usuarios.add(usuario);
			}
						
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
		return usuarios;
	}
	
	public Collection<Rol> listarRoles() throws DAOException {
		log.info("listarRoles()");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Collection<Rol> roles = new ArrayList<Rol>();
		Rol rol = null;
		try {
			String query = "select idrol, nombre from cv_rol where estado = 1";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			result =  stmt.executeQuery();
			while (result.next()) {
				rol = new Rol();
				rol.setIdrol(result.getInt("idrol"));
				rol.setNombre(result.getString("nombre"));
				roles.add(rol);
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
		return roles;
	}
	
	public List<Usuario> buscar(Usuario filtro)throws DAOException {
		log.info("buscar(Usuario filtro) ");
		
		PreparedStatement stmt = null;
		ResultSet result = null;
		ResultSet subresult = null;
		Connection cons = null;
		List<Usuario> usuarios = new ArrayList<Usuario>();
		Usuario usuario = null;
		Rol rol = null;
		try {
			// Datos Personales
			String query = "select u.idusuario, u.usuario, p.nomuno, p.nomdos, p.apepaterno, p.apematerno, u.estado " +
					"from cv_usuario u, cv_persona p " +
					"where u.idusuario = p.idpersona and p.estado=1 ";
			
			if(!"".equals(filtro.getUsuario())){
				query += "and (soundex(u.usuario) = soundex(?) " +
						"or replace(replace(replace(replace(replace(replace(upper(u.usuario),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N') like ?) ";
			}
			if(!"".equals(filtro.getPersona().getNomuno())){
				query += "and (soundex(p.nomuno) = soundex(?) " +
						"or replace(replace(replace(replace(replace(replace(upper(p.nomuno),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N') like ?) ";
			}
			if(!"".equals(filtro.getPersona().getNomdos())){
				query += "and (soundex(p.nomdos) = soundex(?) " +
						"or replace(replace(replace(replace(replace(replace(upper(p.nomdos),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N') like ?) ";
			}
			if(!"".equals(filtro.getPersona().getApepaterno())){
				query += "and (soundex(p.apepaterno) = soundex(?) " +
						"or replace(replace(replace(replace(replace(replace(upper(p.apepaterno),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N') like ?) ";
			}
			if(!"".equals(filtro.getPersona().getApematerno())){
				query += "and (soundex(p.apematerno) = soundex(?) " +
						"or replace(replace(replace(replace(replace(replace(upper(p.apematerno),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N') like ?) ";
			}
			if(0 != filtro.getRolPredeterminado().getIdrol()){
				query += "and 0<(select count(*) from cv_usuario_rol where idusuario=u.idusuario and estado=1 and idrol=?) ";
			}
			query += "order by p.apepaterno, p.apematerno, p.nomuno, p.nomdos";
			//System.out.println(query);
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			int i = 1;
			if(!"".equals(filtro.getUsuario())){
				stmt.setString(i++, filtro.getUsuario());
				stmt.setString(i++, "%" + filtro.getUsuario() + "%");
			}
			if(!"".equals(filtro.getPersona().getNomuno())){
				stmt.setString(i++, filtro.getPersona().getNomuno());
				stmt.setString(i++, "%" + filtro.getPersona().getNomuno() + "%");
			}
			if(!"".equals(filtro.getPersona().getNomdos())){
				stmt.setString(i++, filtro.getPersona().getNomdos());
				stmt.setString(i++, "%" + filtro.getPersona().getNomdos() + "%");
			}
			if(!"".equals(filtro.getPersona().getApepaterno())){
				stmt.setString(i++, filtro.getPersona().getApepaterno());
				stmt.setString(i++, "%" + filtro.getPersona().getApepaterno() + "%");
			}
			if(!"".equals(filtro.getPersona().getApematerno())){
				stmt.setString(i++, filtro.getPersona().getApematerno());
				stmt.setString(i++, "%" + filtro.getPersona().getApematerno() + "%");
			}
			if(0 != filtro.getRolPredeterminado().getIdrol()){
				stmt.setInt(i++, filtro.getRolPredeterminado().getIdrol());
			}
			result = stmt.executeQuery();
			
			query = "select r.idrol,r.nombre from cv_usuario_rol u, cv_rol r  " +
					"where u.idrol=r.idrol and u.estado=1 and r.estado=1 " +
					"and u.idusuario=?";
			stmt = cons.prepareStatement(query);
			
			while (result.next()) {
				usuario = new Usuario();
				usuario.setId(result.getInt("idusuario"));
				usuario.setUsuario(result.getString("usuario"));
				usuario.setEstado(result.getInt("estado"));
				
				Persona p = new Persona();
				p.setNomuno(result.getString("nomuno"));
				p.setNomdos(result.getString("nomdos"));
				p.setApepaterno(result.getString("apepaterno"));
				p.setApematerno(result.getString("apematerno"));
				usuario.setPersona(p);
				
				stmt.setInt(1, usuario.getId());
				subresult = stmt.executeQuery();
				while (subresult.next()) {
					rol = new Rol();
					rol.setIdrol(subresult.getInt("idrol"));
					rol.setNombre(subresult.getString("nombre"));
					usuario.getRoles().add(rol);
				}
				
				usuarios.add(usuario);
				
			}
						
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(subresult);
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return usuarios;
	}
	
	public List<Usuario> buscarUsuarioPorRol(String nombre, Integer rol)throws DAOException {
		log.info("buscarUsuarioPorRol(String nombre, Integer rol) ");
		
		PreparedStatement stmt = null;
		ResultSet result = null;
		ResultSet subresult = null;
		Connection cons = null;
		List<Usuario> usuarios = new ArrayList<Usuario>();
		Usuario usuario = null;
		try {
			// Datos Personales
			String query = "select u.idusuario, u.usuario, p.nomuno, p.nomdos, p.apepaterno, p.apematerno " +
					"from cv_usuario u, cv_persona p " +
					"where u.idusuario = p.idpersona and p.estado=1 and " +
					"(replace(replace(replace(replace(replace(replace(upper(u.usuario),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N') like ? " +
					"or replace(replace(replace(replace(replace(replace(upper(p.nomuno),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N') like ? " +
					"or replace(replace(replace(replace(replace(replace(upper(p.nomdos),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N') like ? " +
					"or replace(replace(replace(replace(replace(replace(upper(p.apepaterno),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N') like ? " +
					"or replace(replace(replace(replace(replace(replace(upper(p.apematerno),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N') like ?) " +
					"and 0<(select count(*) from cv_usuario_rol where idusuario=u.idusuario and estado=1 and idrol=?) " +
					"order by p.apepaterno, p.apematerno, p.nomuno, p.nomdos";
			//System.out.println(query);
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, "%" + nombre + "%");
			stmt.setString(2, "%" + nombre + "%");
			stmt.setString(3, "%" + nombre + "%");
			stmt.setString(4, "%" + nombre + "%");
			stmt.setString(5, "%" + nombre + "%");
			stmt.setInt(6, rol);
			
			result = stmt.executeQuery();
			while (result.next()) {
				usuario = new Usuario();
				usuario.setId(result.getInt("idusuario"));
				usuario.setUsuario(result.getString("usuario"));
				
				Persona p = new Persona();
				p.setNomuno(result.getString("nomuno"));
				p.setNomdos(result.getString("nomdos"));
				p.setApepaterno(result.getString("apepaterno"));
				p.setApematerno(result.getString("apematerno"));
				usuario.setPersona(p);
								
				usuarios.add(usuario);
				
			}
						
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(subresult);
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return usuarios;
	}
	
	public Collection<Ubigeo> listarPaises()throws DAOException {
		log.info("listarPaises() ");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Collection<Ubigeo> paises = new ArrayList<Ubigeo>();
		Ubigeo pais = null;
		try {
			String query = "select idpais,nombre from cv_pais where estado=1 order by nombre";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			result = stmt.executeQuery();
			while (result.next()) {
				pais = new Ubigeo();
				pais.setIdPais(result.getString("idpais"));
				pais.setPais(result.getString("nombre"));
				paises.add(pais);
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
		return paises;
	}
	
	public Collection<Ubigeo> listarDepartamentos(String idpais)throws DAOException {
		log.info("listarDepartamentos(String idpais) ");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Collection<Ubigeo> departamentos = new ArrayList<Ubigeo>();
		Ubigeo departamento = null;
		try {
			String query = "select iddepartamento,nombre from  cv_departamento where estado=1 and idpais=? order by nombre";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1,idpais);
			result = stmt.executeQuery();
			while (result.next()) {
				departamento = new Ubigeo();
				departamento.setIdPais(result.getString("iddepartamento"));
				departamento.setPais(result.getString("nombre"));
				departamentos.add(departamento);
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
		return departamentos;
	}
	
	public Collection<Ubigeo> listarProvincias(String idDepartamento)throws DAOException {
		log.info("listarProvincias(String idDepartamento) ");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Collection<Ubigeo> provincias = new ArrayList<Ubigeo>();
		Ubigeo provincia = null;
		try {
			String query = "select idprovincia,nombre from  cv_provincia where estado=1 and iddepartamento=? order by nombre";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1,idDepartamento);
			result = stmt.executeQuery();
			while (result.next()) {
				provincia = new Ubigeo();
				provincia.setIdPais(result.getString("idprovincia"));
				provincia.setPais(result.getString("nombre"));
				provincias.add(provincia);
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
		return provincias;
	}
	
	public Collection<Ubigeo> listarDistritos(String idProvincia)throws DAOException {
		log.info("listarDistritos(String idProvincia) ");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Collection<Ubigeo> distritos = new ArrayList<Ubigeo>();
		Ubigeo distrito = null;
		try {
			String query = "select iddistrito,nombre from  cv_distrito where estado=1 and idprovincia=? order by nombre";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1,idProvincia);
			result = stmt.executeQuery();
			while (result.next()) {
				distrito = new Ubigeo();
				distrito.setIdPais(result.getString("iddistrito"));
				distrito.setPais(result.getString("nombre"));
				distritos.add(distrito);
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
		return distritos;
	}
	
	public Integer numeroUsuarioRepetido(String usuario)throws DAOException {
		log.info("numeroUsuarioRepetido(String usuario)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Integer n = null;
		try {
			String query = "select count(*) from cv_usuario where usuario = ? or usuario like ?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1,usuario);
			stmt.setString(2,usuario+"__");
			result = stmt.executeQuery();
			if (result.next()) 
				n = result.getInt(1);
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
		return n;
	}
	
	public Integer crear(Usuario usuario)throws DAOException {
		log.info("crear(Usuario usuario) ");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Integer id = null;
		try {
			
			String query = "insert into cv_usuario(usuario,clave,tipo) values (?,?,?)";
			cons = dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt =  cons.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1,usuario.getUsuario());
			stmt.setString(2, usuario.getClave());
			stmt.setString(3, Constante.TIPO_USUARIO_PERSONA);
			if (1 != stmt.executeUpdate()) {
				log.error("Error en insert into cv_usuario");
				throw new DAOException("No culmino");
			}
			
			result = stmt.getGeneratedKeys();
			result.next();
			id = result.getInt(1);
			
			query = "insert  into cv_persona(idpersona,idubigeo,nomuno,nomdos,apepaterno,apematerno,fecnacimiento,sexo," +
					"dirdomicilio,teldomicilio,telcelular,email,dni,creado_en,creado_por,modificado_en,modificado_por) " +
					"values (?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now(),?)";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1,id);
			stmt.setString(2, usuario.getPersona().getUbigeo().getIdPais());
			stmt.setString(3, usuario.getPersona().getNomuno());
			stmt.setString(4, usuario.getPersona().getNomdos());
			stmt.setString(5, usuario.getPersona().getApepaterno());
			stmt.setString(6, usuario.getPersona().getApematerno());
			stmt.setString(7, Formato.calendarToDate(usuario.getPersona().getFecnacimiento()));
			stmt.setString(8, usuario.getPersona().getSexo());
			stmt.setString(9, usuario.getPersona().getDirdomicilio());
			stmt.setString(10, usuario.getPersona().getTeldomicilio());
			stmt.setString(11, usuario.getPersona().getTelcelular());
			stmt.setString(12, usuario.getPersona().getEmail());
			stmt.setString(13, usuario.getPersona().getDni());
			stmt.setString(14, usuario.getPersona().getUsuarioCreacion());
			stmt.setString(15, usuario.getPersona().getUsuarioCreacion());
			if (1 != stmt.executeUpdate()) {
				log.error("Error en insert into cv_persona");
				throw new DAOException("No culmino");
			}
			
			query = "insert  into cv_usuario_rol(idusuario,idrol) values (?,?)";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1,id);
			for (Rol rol : usuario.getRoles()) {
				stmt.setInt(2, rol.getIdrol());
				if (1 != stmt.executeUpdate()) {
					log.error("Error en insert into cv_usuario_rol: "+rol.getIdrol());
					throw new DAOException("No culmino");
				}
			}
			
			query = "insert  into cv_usuario_jerarquia(idusuario,idjerarquia) values (?,?)";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1,id);
			for (Jerarquia j : usuario.getPermisos()) {
				stmt.setInt(2, j.getIdJerarquia());
				if (1 != stmt.executeUpdate()) {
					log.error("Error en insert into cv_usuario_jerarquia: "+j.getIdJerarquia());
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
		return id;
	}
	
	public Collection<Usuario> listarUltimos()throws DAOException {
		log.info("listarUltimos() ");
		PreparedStatement stmt = null;
		ResultSet result = null;
		ResultSet subresult = null;
		Connection cons = null;
		Collection<Usuario> usuarios = new ArrayList<Usuario>();
		Usuario usuario = null;
		Rol rol = null;
		try {
			// Datos Personales
			String query = "select u.idusuario,u.usuario,u.clave,p.nomuno,p.nomdos,p.apepaterno,p.apematerno,p.creado_en " +
					"from cv_usuario u, cv_persona p " +
					"where u.idusuario=p.idpersona and p.estado=1 " +
					"order by creado_en desc limit 0,5";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			result = stmt.executeQuery();
			
			query = "select r.idrol,r.nombre from cv_usuario_rol u, cv_rol r  " +
					"where u.idrol=r.idrol and u.estado=1 and r.estado=1 " +
					"and u.idusuario=?";
			stmt = cons.prepareStatement(query);
			
			while (result.next()) {
				usuario = new Usuario();
				usuario.setId(result.getInt("idusuario"));
				usuario.setUsuario(result.getString("usuario"));
				usuario.setClave(result.getString("clave"));
				
				Persona p = new Persona();
				p.setNomuno(result.getString("nomuno"));
				p.setNomdos(result.getString("nomdos"));
				p.setApepaterno(result.getString("apepaterno"));
				p.setApematerno(result.getString("apematerno"));
				p.setFechaCreacion(Formato.timestampToCalendar(result.getString("creado_en")));
				usuario.setPersona(p);
				
				stmt.setInt(1, usuario.getId());
				subresult = stmt.executeQuery();
				while (subresult.next()) {
					rol = new Rol();
					rol.setIdrol(subresult.getInt("idrol"));
					rol.setNombre(subresult.getString("nombre"));
					usuario.getRoles().add(rol);
				}
				
				usuarios.add(usuario);
				
			}
						
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(subresult);
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return usuarios;
	}
	
}