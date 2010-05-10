package edu.opencampus.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.modelo.AulaVirtual;
import edu.opencampus.lms.modelo.Curso;
import edu.opencampus.lms.modelo.Jerarquia;
import edu.opencampus.lms.modelo.Matricula;
import edu.opencampus.lms.modelo.Periodo;
import edu.opencampus.lms.modelo.Silabo;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.modelo.aulavirtual.MatriculaRol;
import edu.opencampus.lms.modelo.ficha.Recurso;
import edu.opencampus.lms.modelo.ficha.Unidad;
import edu.opencampus.lms.modelo.usuario.Persona;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;

public class AulaVirtualDAO extends BaseDAO {

	protected DataSource dataSource;

	public AulaVirtual obtenerAulaVirtual(Integer idFicha, Usuario usuario, boolean asAdmin) throws DAOException {
		log.info("obtenerAulaVirtual("+idFicha+", "+usuario+", "+asAdmin+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		ResultSet subresult = null;
		Connection cons = null;
		AulaVirtual aula = null;
		try {
			String query = "select f.idficha, f.idsilabo, p.idperiodo, p.nombre periodo, p.fecha_inicio, p.fecha_fin, p.dias_edicion, p.dias_revision, p.personalizado, " +
					"c.idcurso, c.nombre curso, j.idjerarquia, j.idpredecesor, j.nombre jerarquia, f.estado " +
					"from cv_ficha f, cv_curso c, cv_jerarquia j, cv_periodo p " +
					"where f.idcurso=c.idcurso and c.idjerarquia=j.idjerarquia and f.idperiodo=p.idperiodo and f.idficha=? and f.eliminado=0";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idFicha);
			result =  stmt.executeQuery();
			if (result.next()) {
				aula = new AulaVirtual();
				aula.setIdFicha(result.getInt("idficha"));
				aula.setEstado(result.getInt("estado"));
								
				Periodo periodo = new Periodo();
				periodo.setIdPeriodo(result.getInt("idperiodo"));
				periodo.setNombre(result.getString("periodo"));
				periodo.setFechaInicio(Formato.timestampToCalendar(result.getString("fecha_inicio")));
				periodo.setFechaFin(Formato.timestampToCalendar(result.getString("fecha_fin")));
				periodo.setDiasEdicion(result.getInt("dias_edicion"));
				periodo.setDiasRevision(result.getInt("dias_revision"));
				periodo.setPersonalizado(result.getInt("personalizado"));
				
				Curso curso = new Curso();
				curso.setIdCurso(result.getInt("idcurso"));
				curso.setNombre(result.getString("curso"));
				
				Jerarquia jerarquia = new Jerarquia();
				jerarquia.setIdJerarquia(result.getInt("idjerarquia"));
				jerarquia.setPadre(new Jerarquia(result.getInt("idpredecesor")));
				jerarquia.setNombre(result.getString("jerarquia"));
				
				curso.setJerarquia(jerarquia);
				
				Silabo silabo = new Silabo();
				silabo.setIdSilabo(result.getInt("idsilabo"));
				
				aula.setSilabo(silabo);
				
				aula.setCurso(curso);
				aula.setPeriodo(periodo);
				
				
				//Matricula 
				if(asAdmin){
					//AulaVirtual para Administrar 
					if(usuario.getRolPredeterminado().getIdrol() == Constante.ROL_CAMPUS_ADMINISTRADOR){
						Matricula miMatricula = new Matricula();
						miMatricula.setIdMatricula(null);
						MatriculaRol rol = new MatriculaRol(Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE, "Responsable");
						miMatricula.setRol(rol);
						miMatricula.setUsuario(usuario);
						aula.setMatriculaActual(miMatricula);
						usuario.setAulaActual(aula);
						log.info("Usuario responsable del curso");
					}else{
						log.error("El usuario "+usuario+" no tiene permiso para administrar el recurso.");
						throw new DAOException("El usuario "+usuario+" no tiene permiso para administrar el recurso.");
					}
				}else{
					//AulaVirtual 
					query = "SELECT m.idmatricula, r.idrol, r.nombre, m.idempresa " +
							"FROM cv_matricula m, cv_matricula_rol r " +
							"WHERE m.idrol=r.idrol AND r.estado=1 AND m.estado=1 AND m.eliminado=0 AND m.idusuario=? AND m.idficha=?";
					stmt =  cons.prepareStatement(query);
					stmt.setInt(1, usuario.getId());
					stmt.setInt(2, idFicha);
					result =  stmt.executeQuery();
					if (result.next()) {
						// Si está matriculado con rol 2,3,4 o 5
						Matricula miMatricula = new Matricula();
						miMatricula.setIdMatricula(result.getInt("idmatricula"));
						MatriculaRol rol = new MatriculaRol(result.getInt("idrol"),result.getString("nombre"));
						miMatricula.setRol(rol);
						miMatricula.setUsuario(usuario);
						aula.setMatriculaActual(miMatricula);
						usuario.setAulaActual(aula);
						log.info("Usuario matriculado. ROL: "+rol);
						
					}else if(usuario.getRolPredeterminado().getIdrol() == Constante.ROL_CAMPUS_ADMINISTRADOR
							|| usuario.contienePermisoHijo(aula.getCurso().getJerarquia().getIdJerarquia())){
						//Si es responsable (admin o jefe)
						Matricula miMatricula = new Matricula();
						miMatricula.setIdMatricula(null);
						MatriculaRol rol = new MatriculaRol(Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE, "Responsable");
						miMatricula.setRol(rol);
						miMatricula.setUsuario(usuario);
						aula.setMatriculaActual(miMatricula);
						usuario.setAulaActual(aula);
						log.info("Usuario responsable del curso");
						
					}else{
						//Sin rol
						log.error("El usuario "+usuario+" no se ha matriculado al curso.");
						throw new DAOException("El usuario "+usuario+" no se ha matriculado al curso.");
					}
					
				}
				
				
				//Docentes
				List<Matricula> docentes = new ArrayList<Matricula>();
				query = "select m.idmatricula, r.idrol, r.nombre rol, m.estado, m.principal, m.constancia, " +
						"u.idusuario, u.usuario, p.nomuno, p.nomdos, p.apepaterno, p.apematerno, p.email " +
						"from cv_matricula m, cv_persona p, cv_usuario u, cv_matricula_rol r " +
						"where m.idusuario=u.idusuario and u.idusuario=p.idpersona and m.idrol=r.idrol and p.estado=1 and m.eliminado=0 and r.idrol=2 and r.estado=1 and m.idficha=? " +
						"order by p.apepaterno, p.apematerno, p.nomuno, p.nomdos";
				cons =  dataSource.getConnection();
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, idFicha);
				result =  stmt.executeQuery();
				while (result.next()) {
					Matricula docente = new Matricula();
					docente.setIdMatricula(result.getInt("idmatricula"));
					docente.setEstado(result.getInt("estado"));
					docente.setPrincipal(result.getInt("principal"));
					docente.setConstancia(result.getInt("constancia"));
					MatriculaRol rol = new MatriculaRol(result.getInt("idrol"),result.getString("rol"));
					docente.setRol(rol);
					Usuario usuari0 = new Usuario();
					usuari0.setId(result.getInt("idusuario"));
					usuari0.setUsuario(result.getString("usuario"));
					Persona persona = new Persona();
					persona.setNomuno(result.getString("nomuno"));
					persona.setNomdos(result.getString("nomdos"));
					persona.setApepaterno(result.getString("apepaterno"));
					persona.setApematerno(result.getString("apematerno"));
					persona.setEmail(Formato.formatoTexto(result.getString("email")));
					usuari0.setPersona(persona);
					docente.setUsuario(usuari0);
					
					docentes.add(docente);
				}
				aula.setDocentes(docentes);
				
				//Silabo
				List<Unidad> unidades = new ArrayList<Unidad>();
				query = "select f.indice, u.idunidad, u.nombre, j.idjerarquia, j.nombre jerarquia, f.estado, " +
						"(select count(*) total from cv_test where estado='1' and idunidad=u.idunidad) test " +
						"from cv_ficha_unidad f, cv_unidad u, cv_jerarquia j " +
						"where f.idunidad=u.idunidad and u.idjerarquia=j.idjerarquia " +
						"and f.idficha=? order by f.indice";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, idFicha);
				result =  stmt.executeQuery();
				while (result.next()) {
					Unidad unidad = new Unidad();
					unidad.setIdUnidad(result.getInt("idunidad"));
					unidad.setNombreCompleto(result.getString("nombre"));
					unidad.setIndice(result.getInt("indice"));
					unidad.setEstado(result.getInt("estado"));
					unidad.setCantidadTest(result.getInt("test"));
					unidad.setJerarquia(new Jerarquia(result.getInt("idjerarquia"),result.getString("jerarquia")));
					
					if(!asAdmin){
						/*
						 * AQUI COLOCAR LOGICA DE FLAGS, ver antiguo AulaVirtualDAO de la línea 469 a 534
						 */
						
						
						unidad.setFlagLaboratorios(0);
						unidad.setFlagDialogos(0);
						unidad.setFlagTIndividual(0);
						unidad.setFlagTGrupal(0);
						unidad.setFlagDebates(0);
						unidad.setFlagTest(0);
					}
					
					unidades.add(unidad);
					
					//Recursos
					List<Recurso> recursos = new ArrayList<Recurso>();
					query = "select r.idrecurso,r.nombre,r.nombre_corto,f.estado, f.estado_docente,f.estado_alumno,f.calificado,f.peso " +
							"from cv_ficha_recurso f, cv_recurso r " +
							"where r.idrecurso=f.idrecurso and idficha=? and idunidad=? order by r.indice";
					stmt =  cons.prepareStatement(query);
					stmt.setInt(1, idFicha);
					stmt.setInt(2, unidad.getIdUnidad());
					subresult =  stmt.executeQuery();
					while (subresult.next()) {
						Recurso recurso = new Recurso();
						recurso.setIdRecurso(subresult.getInt("idrecurso"));
						recurso.setNombre(subresult.getString("nombre"));
						recurso.setNombreCorto(subresult.getString("nombre_corto"));
						recurso.setEstado(subresult.getInt("estado"));
						recurso.setEstadoDocente(subresult.getInt("estado_docente"));
						recurso.setEstadoAlumno(subresult.getInt("estado_alumno"));
						recurso.setCalificado(subresult.getInt("calificado"));
						recurso.setPeso(subresult.getInt("peso"));
						
						recursos.add(recurso);
					}
					unidad.setRecursos(recursos);
					
				}
				aula.getSilabo().setUnidades(unidades);
			} 
			System.out.println(usuario.toStringFull());
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
		return aula;
	}
	
	public AulaVirtual verDisponibilidadDeFicha(Integer idFicha, Usuario usuario) throws DAOException {
		log.info("verDisponibilidadDeFicha("+idFicha+", "+usuario+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		AulaVirtual aula = null;
		Periodo periodo = null;
		Matricula matricula = null;
		try {
			String query = "SELECT p.idperiodo, p.nombre, p.fecha_inicio, p.fecha_fin, p.dias_edicion, p.dias_revision, p.personalizado, m.idmatricula, m.idrol " +
					"FROM cv_matricula m, cv_ficha f, cv_periodo p  " +
					"WHERE f.idperiodo=p.idperiodo AND m.idficha=f.IDFICHA AND f.eliminado=0 AND f.estado=1 AND m.eliminado=0 AND m.estado=1 AND f.idficha=? AND m.idusuario=?";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idFicha);
			stmt.setInt(2, usuario.getId());
			result =  stmt.executeQuery();
			if (result.next()) {
				aula = new AulaVirtual();
				aula.setIdFicha(idFicha);
				
				periodo = new Periodo();
				periodo.setIdPeriodo(result.getInt("idperiodo"));
				periodo.setNombre(result.getString("nombre"));
				periodo.setFechaInicio(Formato.timestampToCalendar(result.getString("fecha_inicio")));
				periodo.setFechaFin(Formato.timestampToCalendar(result.getString("fecha_fin")));
				periodo.setDiasEdicion(result.getInt("dias_edicion"));
				periodo.setDiasRevision(result.getInt("dias_revision"));
				periodo.setPersonalizado(result.getInt("personalizado"));
				
				aula.setPeriodo(periodo);
				
				matricula = new Matricula();
				matricula.setIdMatricula(result.getInt("idmatricula"));
				matricula.setRol(new MatriculaRol(result.getInt("idrol")));
				matricula.setUsuario(usuario);
				
				aula.setMatriculaActual(matricula);
								
			}
			
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
		return aula;
	}
	
	public Silabo obtenerElSilabo(Integer idsilabo) throws DAOException {
		log.info("obtenerElSilabo("+idsilabo+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Silabo silabo = null;
		try {

			//Silabo
			String query = "SELECT s.IDSILABO, s.IDSISTEMA, s.NOMBRE SILABO, s.INTRODUCCION, j.idjerarquia, j.NOMBRE jerarquia, c.IDCURSO, c.NOMBRE CURSO " +
					"FROM cv_silabo s, cv_curso c, cv_jerarquia j  " +
					"WHERE c.idjerarquia=j.idjerarquia AND s.IDCURSO=c.IDCURSO AND s.IDSILABO=?";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idsilabo);
			result =  stmt.executeQuery();
			if (result.next()) {
				silabo = new Silabo();
				silabo.setIdSilabo(result.getInt("IDSILABO"));
				silabo.setDescripcion(result.getString("SILABO"));
				silabo.setIntroduccion(result.getString("INTRODUCCION"));
				
				Curso curso = new Curso();
				curso.setIdCurso(result.getInt("IDCURSO"));
				curso.setNombre(result.getString("CURSO"));
				curso.setJerarquia(new Jerarquia(result.getInt("idjerarquia"),result.getString("jerarquia")));
				
				silabo.setCurso(curso);
				
				Integer sistema = result.getInt("IDSISTEMA");
				//SISTEMA
				if(sistema != null){
					//Buscar el sistema y cargarlo al silabo (falta atributo sistema para silabo)
				}
				
				//UNIDADES
				List<Unidad> unidades = new ArrayList<Unidad>();
				query = "SELECT u.IDUNIDAD, u.NOMBRE, su.INDICE " +
						"FROM cv_unidad u, cv_silabo_unidad su " +
						"WHERE u.IDUNIDAD=su.IDUNIDAD " +
						"AND su.IDSILABO=? ORDER BY su.INDICE";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, idsilabo);
				result =  stmt.executeQuery();
				while (result.next()) {
					Unidad unidad = new Unidad();
					unidad.setIdUnidad(result.getInt("idunidad"));
					unidad.setNombreCompleto(result.getString("nombre"));
					unidad.setIndice(result.getInt("indice"));
					unidades.add(unidad);
				}
				silabo.setUnidades(unidades);
				
			}
			
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
		return silabo;
	}

	public Collection<MatriculaRol> obtenerMatriculasAdministrar(Integer idFicha) throws DAOException {
		log.info("obtenerMatriculasAdministrar("+idFicha+")");
		ResultSet result1 = null;
		ResultSet result = null;
		PreparedStatement stmt = null;
		Connection cons = null;
		
		Collection<MatriculaRol> grupos = new ArrayList<MatriculaRol>();
		List<Matricula> matriculas = null;
		Matricula matricula = null;
		Persona persona = null;
		Usuario usuario = null;
		MatriculaRol grupo = null;
		try {
			String query = "SELECT idrol,nombre FROM cv_matricula_rol WHERE idrol != 1 order by idrol";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			result =  stmt.executeQuery();
			query = "SELECT " +
					"cv_matricula.IDMATRICULA, cv_matricula.ESTADO, cv_matricula.PRINCIPAL, cv_matricula.CONSTANCIA, cv_usuario.USUARIO, " +
					"cv_usuario.IDUSUARIO, cv_persona.NOMUNO, cv_persona.NOMDOS, cv_persona.APEPATERNO, cv_persona.APEMATERNO " +
					"FROM opencampus.cv_matricula " +
					"INNER JOIN opencampus.cv_usuario ON (cv_matricula.IDUSUARIO = cv_usuario.IDUSUARIO) " +
					"INNER JOIN opencampus.cv_persona ON (cv_persona.IDPERSONA = cv_usuario.IDUSUARIO) " +
					"WHERE (cv_matricula.ELIMINADO = 0 AND cv_matricula.IDFICHA=? AND cv_matricula.IDROL =?) " +
					"ORDER BY cv_persona.APEPATERNO ASC, cv_persona.APEMATERNO ASC, cv_persona.NOMUNO ASC, cv_persona.NOMDOS ASC";
			stmt =  cons.prepareStatement(query);
			while (result.next()) {
				grupo = new MatriculaRol(result.getInt("idrol"), result.getString("nombre"));
				stmt.setInt(1, idFicha);
				stmt.setInt(2, result.getInt("idrol"));
				result1 =  stmt.executeQuery();
				matriculas = new ArrayList<Matricula>();
				while (result1.next()) {
					matricula = new Matricula();
					matricula.setIdMatricula(result1.getInt("IDMATRICULA"));
					matricula.setEstado(result1.getInt("ESTADO"));
					matricula.setPrincipal(result1.getInt("PRINCIPAL"));
					matricula.setConstancia(result1.getInt("CONSTANCIA"));
					
					usuario = new Usuario();
					usuario.setId(result1.getInt("IDUSUARIO"));
					usuario.setUsuario(result1.getString("USUARIO"));
					
					persona = new Persona();
					persona.setNomuno(result1.getString("NOMUNO"));
					persona.setNomdos(result1.getString("NOMDOS"));
					persona.setApepaterno(result1.getString("APEPATERNO"));
					persona.setApematerno(result1.getString("APEMATERNO"));
					
					usuario.setPersona(persona);
					
					matricula.setUsuario(usuario);
					
					matriculas.add(matricula);
				}
				
				grupo.setMatriculas(matriculas);
				grupos.add(grupo);
			}
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
			closeConnection(cons);
		}
		return grupos;
	}
	
	public Collection<Usuario> buscarNoMatriculados(String[] nombres, int idFicha) throws DAOException {
		log.info("obtenerUsuarioNoMatriculados("+nombres+", "+idFicha+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		Collection<Usuario> usuarios = new ArrayList<Usuario>();
		Usuario usuario = null;
		Persona persona = null;
		
		try {
			String query = "SELECT u.idusuario, u.usuario, p.apepaterno, p.apematerno, p.nomuno, p.nomdos, p.sexo " +
					"FROM cv_usuario u,  cv_persona p " +
					"WHERE p.idpersona = u.idusuario AND (SELECT COUNT(*) FROM cv_matricula WHERE IDFICHA=? AND ELIMINADO=0 AND idusuario=u.idusuario)=0 AND u.estado=1 AND p.estado=1";
			for (int u = 0; u < nombres.length; u++) {
				query += " AND replace(replace(replace(replace(replace(replace(upper(concat_ws(' ', p.apepaterno, p.apematerno, p.nomuno, p.nomdos)),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N') like ? ";
			}
			query += " ORDER BY p.APEPATERNO, p.APEMATERNO, p.NOMUNO, p.NOMDOS limit 0,20 ";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idFicha);
			for (int u = 0; u < nombres.length; u++) {
				stmt.setString(u+2, "%"+ Formato.matizarFrace(nombres[u]) + "%");
			}
			result =  stmt.executeQuery();
			while (result.next()) {
				usuario = new Usuario();
				usuario.setId(result.getInt("idusuario"));
				usuario.setUsuario(result.getString("usuario"));
				
				persona = new Persona();
				persona.setApepaterno(result.getString("apepaterno"));
				persona.setApematerno(result.getString("apematerno"));
				persona.setNomuno(result.getString("nomuno"));
				persona.setNomdos(result.getString("nomdos"));
				persona.setSexo(result.getString("sexo"));
				
				usuario.setPersona(persona);
				
				usuarios.add(usuario);
			}
			log.info("Fin de obtenerUsuarioNoMatriculados");
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
	
	public boolean esMiAula(Integer idficha,Integer idusuario) throws DAOException {
		log.info("esMiAula("+idficha+", "+idusuario+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		boolean tipo = false;
		try {
			cons =  dataSource.getConnection();
			String query = "SELECT IDMATRICULA FROM CV_MATRICULA WHERE ELIMINADO=0 AND IDFICHA=? AND IDUSUARIO=?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idficha);
			stmt.setInt(2, idusuario);
			result =  stmt.executeQuery();
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
	
	public List<Matricula> listarDocentes(Integer idficha) throws DAOException {
		log.info("listarDocentes("+idficha+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;

		List<Matricula> matriculas = new ArrayList<Matricula>();
		Matricula matricula = null;
		Usuario usuario = null;
		
		try {
			cons =  dataSource.getConnection();
			String query = "SELECT m.IDMATRICULA, m.IDUSUARIO, m.ESTADO, m.PRINCIPAL, u.USUARIO, p.APEPATERNO, p.APEMATERNO, p.NOMUNO, p.NOMDOS, p.SEXO " +
					"FROM cv_matricula m, cv_usuario u, cv_persona p " +
					"WHERE m.IDUSUARIO=u.IDUSUARIO AND u.IDUSUARIO=p.IDPERSONA " +
					"AND m.ELIMINADO=0 AND m.IDROL=2 AND m.IDFICHA=?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idficha);
			result =  stmt.executeQuery();
			while (result.next()) {
				matricula = new Matricula();
				matricula.setIdMatricula(result.getInt("IDMATRICULA"));
				matricula.setEstado(result.getInt("ESTADO"));
				matricula.setPrincipal(result.getInt("PRINCIPAL"));
				
				usuario = new Usuario();
				usuario.setId(result.getInt("IDUSUARIO"));
				usuario.setUsuario(result.getString("USUARIO"));
				usuario.setPersona(new Persona(result.getString("NOMUNO"), result.getString("NOMDOS"), result.getString("APEPATERNO"), result.getString("APEMATERNO"), result.getString("SEXO")));
				
				matricula.setUsuario(usuario);
				
				matriculas.add(matricula);
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
		return matriculas;
	}
	
	public Integer matricularUsuario(Integer idFicha, Matricula matricula) throws DAOException {
		log.info("matricularUsuario("+idFicha+","+matricula+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		Integer id = null;
		
		ResultSet subResult = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		PreparedStatement stmt4 = null;
		PreparedStatement stmt5 = null;
		try {
			
			String query = "INSERT INTO cv_matricula (IDROL, IDUSUARIO, IDFICHA, ESTADO, PRINCIPAL, CREADO_EN, CREADO_POR, MODIFICADO_EN, MODIFICADO_POR) " +
					"VALUES(?,?,?,1,?,NOW(),?,NOW(),?)";
			cons =  dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt =  cons.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, matricula.getRol().getIdRol());
			stmt.setInt(2, matricula.getUsuario().getId());
			stmt.setInt(3, idFicha);
			stmt.setInt(4, matricula.getPrincipal());
			stmt.setString(5, matricula.getUsuarioCreacion());
			stmt.setString(6, matricula.getUsuarioCreacion());
			log.info("Insertando en la tabla cv_matricula ...");
			if (1 != stmt.executeUpdate()) {
				throw new DAOException("No se logro insertar en cv_matricula");
			}
			log.info("Operacion exitosa en la tabla cv_ficha.");
			
			result = stmt.getGeneratedKeys();
			result.next();
			id = result.getInt(1);
			
//			query = "INSERT INTO CV_DIALOGO_MATRICULA (IDDIALOGO,IDMATRICULA,LEIDO)"
//					+ " SELECT IDDIALOGO,?,'0' FROM CV_DIALOGO"
//					+ " WHERE IDFICHA=?";
//			stmt =  cons
//					.prepareStatement(query);
//			stmt.setInt(1, id);
//			stmt.setInt(2, idFicha);
//			stmt.executeUpdate();
//			query = "INSERT INTO cv_flag_publicacion_ficha(idmatricula,idpublicacion,idherramienta,idficha,estado,fecha,importante) "
//					+ "SELECT seqcvmatricula.currval,idpublicacion,idherramienta,idficha,'0',fecha,'0' "
//					+ "FROM cv_publicacion_ficha "
//					+ "WHERE idficha = ?";
//			stmt =  cons
//					.prepareStatement(query);
//			stmt.setInt(1, idFicha);
//			stmt.executeUpdate();
//			if (Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE == int_rol
//					|| Constante.ROL_CAMPUS_AULAVIRTUAL_MONITOR_DOCENTE == int_rol
//					|| Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE == int_rol) {
//				query = "INSERT INTO CV_DEBATE_MATRICULA (IDDEBATE,"
//						+ "IDMATRICULA,LEIDO) SELECT cvdegr.IDDEBATE,seqcvmatricula."
//						+ "CURRVAL,'0' FROM CV_DEBATE cvdegr,CV_GRUPO_TRABAJO "
//						+ "cvgrtr,cv_trabajo_grupal cvtrgr WHERE cvdegr.idtrabajo="
//						+ "cvgrtr.idtrabajo and cvdegr.idgrupo=cvgrtr.idgrupo and "
//						+ "cvgrtr.estado='1' and cvtrgr.idtrabajo=cvgrtr.idtrabajo "
//						+ "and cvtrgr.idficha=? ";
//				stmt =  cons
//						.prepareStatement(query);
//				stmt.setInt(1, idFicha);
//				stmt.executeUpdate();
//			}
//			if (Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE == int_rol
//					|| Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE_MONITOR == int_rol) {
//				query = "SELECT U.IDUNIDAD FROM CV_FICHA F, CV_FICHA_UNIDAD U "
//						+ "WHERE F.IDFICHA=U.IDFICHA AND F.IDSILABO=U.IDSILABO"
//						+ " AND F.IDFICHA=?";
//				stmt =  cons
//						.prepareStatement(query);
//				stmt.setInt(1, idFicha);
//				result =  stmt.executeQuery();
//				query = "SELECT IDTRABAJO,FECHA_ACTIVACION FROM CV_TRABAJO_INDIVIDUAL "
//						+ "WHERE IDFICHA=? AND IDUNIDAD=?";
//				stmt =  cons
//						.prepareStatement(query);
//				query = "INSERT INTO CV_TRABAJO_INDIVIDUAL_NOTA (IDTRABAJO,"
//						+ "IDMATRICULA,USUARIO_MOD,FECHA_MOD,ESTADO,EXPIRADO) "
//						+ "VALUES (?,seqcvmatricula.CURRVAL,?,SYSDATE,0,0)";
//				stmt1 =  cons
//						.prepareStatement(query);
//				query = "INSERT INTO CV_TRABAJO_INDIVIDUAL_INTER (IDTRABAJO,"
//						+ "IDMATRICULA,INTERACCION,DESCRIPCION,IDMATRICULA_ENVIO,"
//						+ "USUARIO_CREACION,FECHA_CREACION,USUARIO_MOD,FECHA_MOD,"
//						+ "ARCHIVO_NOMBRE,ARCHIVO_TAMANO) "
//						+ "SELECT IDTRABAJO,seqcvmatricula.CURRVAL,1,"
//						+ "DESCRIPCION,IDMATRICULA_ENVIO,USUARIO_CREACION,"
//						+ "FECHA_CREACION,USUARIO_MOD,FECHA_MOD,ARCHIVO_NOMBRE,"
//						+ "ARCHIVO_TAMANO FROM CV_TRABAJO_INDIVIDUAL WHERE IDTRABAJO=?";
//				stmt2 =  cons
//						.prepareStatement(query);
//				query = "SELECT IDTRABAJO,FECHA_ACTIVACION FROM CV_LABORATORIO "
//						+ "WHERE IDFICHA=? AND IDUNIDAD=?";
//				stmt3 =  cons
//						.prepareStatement(query);
//				query = "INSERT INTO CV_LABORATORIO_NOTA (IDTRABAJO,IDMATRICULA,"
//						+ "USUARIO_MOD,FECHA_MOD,ESTADO,EXPIRADO) "
//						+ "VALUES (?,seqcvmatricula.CURRVAL,?,SYSDATE,0,0)";
//				stmt4 =  cons
//						.prepareStatement(query);
//				query = "INSERT INTO CV_LABORATORIO_INTER (IDTRABAJO,IDMATRICULA,"
//						+ "INTERACCION,DESCRIPCION,IDMATRICULA_ENVIO,USUARIO_CREACION,"
//						+ "FECHA_CREACION,USUARIO_MOD,FECHA_MOD,ARCHIVO_NOMBRE,ARCHIVO_TAMANO) "
//						+ "SELECT IDTRABAJO,seqcvmatricula.CURRVAL,1,DESCRIPCION,"
//						+ "IDMATRICULA_ENVIO,USUARIO_CREACION,FECHA_CREACION,USUARIO_MOD,"
//						+ "FECHA_MOD,ARCHIVO_NOMBRE,ARCHIVO_TAMANO "
//						+ "FROM CV_LABORATORIO WHERE IDTRABAJO=?";
//				stmt5 =  cons
//						.prepareStatement(query);
//				while (result.next()) {
//					stmt.setInt(1, idFicha);
//					stmt.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmt.executeQuery();
//					if (subResult.next()
//							&& subResult.getString("FECHA_ACTIVACION") != null) {
//						stmt1.setInt(1, subResult.getInt("IDTRABAJO"));
//						stmt1.setString(2, usuario.getIdUsuario());
//						if (1 != stmt1.executeUpdate()) {
//							log.error("Error en matricularUsuario() "
//									+ "- CV_TRABAJO_INDIVIDUAL_NOTA");
//							throw new DAOException("No culmino");
//						}
//						stmt2.setInt(1, subResult.getInt("IDTRABAJO"));
//						if (1 != stmt2.executeUpdate()) {
//							log.error("Error en matricularUsuario() "
//									+ "- CV_TRABAJO_INDIVIDUAL_INTER");
//							throw new DAOException("No culmino");
//						}
//					}
//					stmt3.setInt(1, idFicha);
//					stmt3.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmt3.executeQuery();
//					if (subResult.next()
//							&& subResult.getString("FECHA_ACTIVACION") != null) {
//						stmt4.setInt(1, subResult.getInt("IDTRABAJO"));
//						stmt4.setString(2, usuario.getIdUsuario());
//						if (1 != stmt4.executeUpdate()) {
//							log.error("Error en matricularUsuario()"
//									+ " - CV_LABORATORIO_NOTA");
//							throw new DAOException("No culmino");
//						}
//						stmt5.setInt(1, subResult.getInt("IDTRABAJO"));
//						if (1 != stmt5.executeUpdate()) {
//							log.error("Error en matricularUsuario()"
//									+ " - CV_LABORATORIO_INTER");
//							throw new DAOException("No culmino");
//						}
//
//					}
//					}
//				}
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
		return id;
	}
	
	public void cambiarEstadoMatricula(Matricula matricula) throws DAOException {
		log.info("cambiarEstadoMatricula("+matricula+")");
		PreparedStatement stmt = null;
		Connection cons = null;
		try {
			
			String query = "UPDATE cv_matricula SET estado=?,modificado_por=?,modificado_en=NOW() WHERE idmatricula=?";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, matricula.getEstado());
			stmt.setString(2, matricula.getUsuarioModificacion());
			stmt.setInt(3, matricula.getIdMatricula());
			if (1 != stmt.executeUpdate()) {
				throw new DAOException("No se logro actualizar cv_matricula");
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.toString());
		} finally {
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	public void eliminarMatricula(Matricula matricula) throws DAOException {
		log.info("eliminarMatricula("+matricula+")");
		PreparedStatement stmt = null;
		Connection cons = null;
		try {
			String query = "UPDATE cv_matricula SET eliminado='1',modificado_por=?, modificado_en=NOW() WHERE idmatricula=?";
			cons = dataSource.getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setString(1, matricula.getUsuarioModificacion());
			stmt.setInt(2, matricula.getIdMatricula());
			if (1 != stmt.executeUpdate()) {
				throw new DAOException("No se logro eliminar en cv_matricula");
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
	
	public void principal(Integer idFicha, Matricula matricula) throws DAOException {
		log.info("principal("+idFicha+", "+matricula+")");
		PreparedStatement stmt = null;
		Connection cons = null;
		try {
			String query = "UPDATE cv_matricula SET PRINCIPAL=0 WHERE IDROL=2 AND IDFICHA=?";
			cons = dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, idFicha);
			stmt.executeUpdate();
			
			query = "UPDATE cv_matricula SET PRINCIPAL=1,modificado_por=?, modificado_en=NOW() WHERE IDMATRICULA=? and IDROL=2";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, matricula.getUsuarioModificacion());
			stmt.setInt(2, matricula.getIdMatricula());
			if (1 != stmt.executeUpdate()) {
				cons.rollback();
				throw new DAOException("No se logro actualizar cv_matricula");
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
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	/*public AulaVirtual obtenerAulaVirtual(AulaVirtual aula, Usuario usuario)throws DAOException {
		log.info("obtenerAulaVirtual(AulaVirtual aula)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Rol rolTemp = null;
		try {
			"select f.idficha, f.idsilabo, p.idperiodo, p.nombre periodo, p.fecha_inicio, p.fecha_fin, p.dias_edicion, p.dias_revision, p.personalizado, " +
			"c.idcurso, c.nombre curso, j.idjerarquia, j.idpredecesor, j.nombre jerarquia, f.estado " +
			"from cv_ficha f, cv_curso c, cv_jerarquia j, cv_periodo p " +
			"where f.idcurso=c.idcurso and c.idjerarquia=j.idjerarquia and f.idperiodo=p.idperiodo and f.idficha=? and f.eliminado=0";
			
			String query = "SELECT CVFI.IDFICHA, CVFI.ESTADO_FECHA, CVFI.FECHA_INICIO AS  FECHA_INICIO_FICHA,GEPE.FECINICIO AS FECINICIO_PERIODO, " +
					"CVFI.FECHA_FIN AS FECHA_FIN_FICHA,GEPE.FECFIN AS FECFIN_PERIODO, NVL(CVFI.NOAUDITABLE,0) NOAUDITABLE," +
					"CVFI.DIAS_REVISION AS DIAS_REVISION_FICHA, CVFI.DIAS_EDICION AS DIAS_EDICION_FICHA, " +
					"CVFI.CODIGO_CICLO, GEDE.NOMBRE NOMBREDEPARTAMENTO,CVFI.CODIGO_DEPARTAMENTO, " +
					"COFA.NOMCORTO NOMBREFAMILIA,CVFI.CODIGO_FAMILIA,COPR.NOMBRE NOMBRECURSO,CVFI.CODIGO_CURSO, " +
					"CVFI.CODIGO_PERIODO,GEPE.NOMBRE PERIODO,CVFI.IDSILABO,CVFI.TURNO, GEPE.DIAS_EDICION AS DIAS_EDICION_PERIODO, " +
					"GEPE.DIAS_REVISION AS DIAS_REVISION_PERIODO, CVMA.IDROL,CVRO.NOMBRE ROLNOMBRE,CVMA.IDMATRICULA, " +
					"PKG_CV_FICHA.FX_CV_AULA_FORMASEC(CVFI.IDFICHA) AS FORMASEC, CVFI.PERIODO_MAESTRO, " +
					"(SELECT CODIGO FROM CV_FICHA_FORMACION F WHERE IDFICHA=CVFI.IDFICHA AND CODIGO=(SELECT MAX(CODIGO) FROM CV_FICHA_FORMACION WHERE IDFICHA=F.IDFICHA)) CODIGO_FORMACION,  " +
					"PKG_CV_COM_PRODUCTO.FX_CV_COM_PRODUCTO_NOMBRE_ID((SELECT CODIGO FROM CV_FICHA_FORMACION F WHERE IDFICHA=CVFI.IDFICHA AND CODIGO=(SELECT MAX(CODIGO) FROM CV_FICHA_FORMACION WHERE IDFICHA=F.IDFICHA))) NOMBRE_FORMACION, " +
					"PKG_CV_FICHA.FX_CV_FICHA_CODOCENTE_DEFAULT(CVFI.IDFICHA) COD_DOCENTE, PKG_CV_FICHA.FX_CV_FICHA_DOCENTE_DEFAULT(CVFI.IDFICHA) DOCENTE " +
					"FROM CV_FICHA CVFI,GENERAL.GEN_DEPARTAMENTO GEDE,COMERCIAL.COM_PRODUCTO COPR, " +
					"COMERCIAL.COM_FAMILIA COFA,CV_PERIODO GEPE,CV_MATRICULA CVMA,CV_ROL CVRO " +
					"WHERE CVFI.CODIGO_DEPARTAMENTO=GEDE.CODIGO AND CVFI.CODIGO_CURSO=COPR.CODIGO " +
					"AND CVFI.CODIGO_FAMILIA=COFA.CODIGO AND CVFI.CODIGO_PERIODO=GEPE.CODIGO(+) " +
					"AND CVFI.IDFICHA=? AND CVFI.IDFICHA=CVMA.IDFICHA AND TRIM(CVMA.USUARIO)=? " +
					"AND CVMA.ELIMINADO=0 AND CVRO.IDROL=CVMA.IDROL AND CVMA.ESTADO=1";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, aula.getIdFicha());
			stmt.setString(2, usuario.getIdUsuario());
			result =  stmt.executeQuery();
			if (result.next()) {
				rolTemp = new Rol();
				rolTemp.setIdrol(result.getInt("idrol"));
				rolTemp.setNombre(result.getString("rolnombre"));
				aula.setRol(rolTemp);
				// Estado_fecha = 0 significa que se usa las fechas de la tabla
				// cv_periodo
				if (0 == result.getInt("estado_fecha")) {
					aula.setFechaInicio(Formato.getDateDeBaseDatos(result
							.getString("fecinicio_periodo")));
					aula.setFechaFin(Formato.getDateDeBaseDatos(result
							.getString("fecfin_periodo")));
					aula.setFechaEdicion(Formato.getDateDeBaseDatos(result
							.getString("fecinicio_periodo")));
					aula.setFechaRevision(Formato.getDateDeBaseDatos(result
							.getString("fecfin_periodo")));
					aula.getFechaEdicion().add(Calendar.DATE,
							-result.getInt("dias_edicion_periodo"));
					aula.getFechaRevision().add(Calendar.DATE,
							result.getInt("dias_revision_periodo"));
					// Estado fecha = 1 significa que se usa las fechas de la
					// misma tabla cv_ficha
				} else {
					aula.setFechaInicio(Formato.getDateDeBaseDatos(result
							.getString("fecha_inicio_ficha")));
					aula.setFechaFin(Formato.getDateDeBaseDatos(result
							.getString("fecha_fin_ficha")));
					aula.setFechaEdicion(Formato.getDateDeBaseDatos(result
							.getString("fecha_inicio_ficha")));
					aula.setFechaRevision(Formato.getDateDeBaseDatos(result
							.getString("fecha_fin_ficha")));
					aula.getFechaEdicion().add(Calendar.DATE,
							-result.getInt("dias_edicion_ficha"));
					aula.getFechaRevision().add(Calendar.DATE,
							result.getInt("dias_revision_ficha"));
				}
				// Validación de las fechas para que no ingrese por la URL
				GregorianCalendar hoy = new GregorianCalendar();
				// Validamos al responsable dentro de fecha de revision? para admin y director
				if (Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE == rolTemp
						.getIdrol()
						|| Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE == rolTemp
						.getIdrol()
						|| Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE == rolTemp
								.getIdrol()
						|| Constante.ROL_CAMPUS_AULAVIRTUAL_MONITOR_DOCENTE == rolTemp
								.getIdrol()) {
					if (aula.getFechaEdicion().after(hoy)
							|| aula.getFechaRevision().before(hoy)) {
						return null;
					}
				} else if(ROL_RESPONS != rolTemp) {
					if (aula.getFechaInicio().after(hoy)
							|| aula.getFechaFin().before(hoy)) {
						return null;
					}
				}
				aula.setPeriodoMaestro(result.getInt("periodo_maestro"));
				aula.setFormacionSeccion(result.getString("formasec"));
				aula.setNombreCurso(result.getString("nombrecurso"));
				aula.setTurno(result.getString("turno"));
				aula.setIdMatricula(result.getInt("idmatricula"));
				aula.setIdPeriodo(result.getInt("codigo_periodo"));
				aula.setNombreDepartamento(result.getString("nombredepartamento"));
				aula.setIdDepartamento(result.getInt("codigo_departamento"));
				aula.setNombreFamilia(result.getString("nombrefamilia"));
				aula.setIdFamilia(result.getInt("codigo_familia"));
				aula.setIdCurso(result.getInt("codigo_curso"));
				aula.setNombrePeriodo(result.getString("PERIODO"));
				if (result.getInt("codigo_formacion") != 0) {
					aula.setIdFormacion(result.getInt("codigo_formacion"));
					aula.setNombreFormacion(result.getString("nombre_formacion"));
				}
				aula.setDocenteCodigo(result.getString("COD_DOCENTE"));
				aula.setDocenteNombre(result.getString("DOCENTE"));
				aula.setNoAuditable((result.getInt("NOAUDITABLE") == 1)?true:false) ;

				// Leer toda la información del sílabo
				if (0 != result.getInt("idsilabo")) {
					aula.setIdSilabo(result.getString("idsilabo"));
					PreparedStatement stmt2;
					ResultSet subresult2 = null;
					Collection<Unidad> unidades = new ArrayList<Unidad>();
					Map<String, Recurso> recursos;
					Unidad unidad;
					Recurso recurso;
					query = "SELECT US.INDICE,U.IDUNIDAD,U.NOMBRE_CORTO,U.NOMBRE_COMPLETO,U.ALTO,U.ANCHO,FU.ESTADO"
							+ " FROM CV_UNIDAD U, CV_UNIDAD_SILABO US,CV_FICHA_UNIDAD FU WHERE "
							+ " US.IDSILABO=FU.IDSILABO AND US.IDUNIDAD=FU.IDUNIDAD AND U.IDUNIDAD=US.IDUNIDAD "
							+ "AND IDFICHA=? AND FU.ESTADO=1 ORDER BY INDICE";
					stmt =  cons
							.prepareStatement(query);
					stmt.setInt(1, aula.getIdFicha());
					result =  stmt.executeQuery();
					query = "SELECT R.IDRECURSO,R.NOMBRE,FUR.ESTADO,FUR.DESHABILITADO_DOC,FUR."
							+ "DESHABILITADO_ESTU,FUR.CALIFICA,FUR.PESO FROM CV_RECURSO R,"
							+ "CV_UNIDAD_RECURSO UR, CV_FICHA_UNIDAD_RECURSO FUR WHERE FUR."
							+ "IDUNIDAD=UR.IDUNIDAD AND FUR.IDRECURSO=UR.IDRECURSO AND R.IDRECURSO"
							+ "=UR.IDRECURSO AND FUR.IDFICHA=? AND FUR.IDUNIDAD=? ORDER BY IDRECURSO";
					stmt =  cons
							.prepareStatement(query);
					if (Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE == rolTemp
							.getIdrol()
							|| Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE == rolTemp
									.getIdrol()) {
						query = "SELECT "
								+ "(SELECT COUNT(IDTEST) FROM CV_TEST WHERE IDUNIDAD=? AND ESTADO=1 ) as cantTest,"
								+ "(SELECT COUNT(T.IDTRABAJO) FROM CV_TRABAJO_INDIVIDUAL T, "
								+ "CV_TRABAJO_INDIVIDUAL_NOTA N, CV_FICHA_UNIDAD_RECURSO U "
								+ "WHERE T.IDTRABAJO=N.IDTRABAJO AND T.IDUNIDAD=U.IDUNIDAD "
								+ "AND T.IDFICHA=U.IDFICHA AND U.IDRECURSO=T.IDRECURSO AND "
								+ "U.ESTADO='1' AND U.DESHABILITADO_DOC='1' AND N.ESTADO='1'"
								+ " AND N.NOTA IS NULL AND SYSDATE >= T.FECHA_ACTIVACION "
								+ "AND T.IDFICHA=? AND T.IDUNIDAD=?) as cantTIndi,"
								+ "(SELECT COUNT(cvdi.iddialogo) FROM cv_dialogo cvdi,cv_"
								+ "dialogo_matricula cvdima,cv_ficha_unidad_recurso fiunre"
								+ " WHERE cvdima.iddialogo=cvdi.iddialogo AND cvdima.leido"
								+ "='0' AND cvdi.estado='1' AND fiunre.estado='1' AND cvdima"
								+ ".idmatricula=? AND fiunre.idficha=cvdi.idficha AND fiunre"
								+ ".idunidad=cvdi.idunidad AND fiunre.idrecurso=cvdi.idrecurso"
								+ " AND fiunre.idficha=? AND fiunre.idunidad=? AND fiunre."
								+ "idrecurso=? and fiunre.DESHABILITADO_DOC='1') as cantDialogo,"
								+ "(SELECT COUNT(T.IDTRABAJO) FROM CV_LABORATORIO T,"
								+ "CV_LABORATORIO_NOTA N,CV_FICHA_UNIDAD_RECURSO U WHERE T."
								+ "IDTRABAJO=N.IDTRABAJO AND T.IDUNIDAD=U.IDUNIDAD AND T.IDFICHA="
								+ "U.IDFICHA AND U.IDRECURSO=T.IDRECURSO AND U.ESTADO='1' AND U."
								+ "DESHABILITADO_DOC='1' AND N.ESTADO='1' AND N.NOTA IS NULL AND "
								+ "SYSDATE >= T.FECHA_ACTIVACION AND T.IDFICHA=? AND T.IDUNIDAD=?"
								+ ") as cantLaboratorio,"
								+ "(SELECT (SELECT COUNT(CVDI.IDDEBATE) FROM CV_DEBATE"
								+ " CVDI,CV_DEBATE_MATRICULA CVDIMA,CV_TRABAJO_GRUPAL CVTRGR,"
								+ "CV_GRUPO_TRABAJO CVGRTR, CV_FICHA_UNIDAD_RECURSO U WHERE CVDIMA."
								+ "IDDEBATE=CVDI.IDDEBATE AND CVGRTR.IDTRABAJO=CVDI."
								+ "IDTRABAJO AND CVGRTR.IDGRUPO=CVDI.IDGRUPO AND CVGRTR.IDTRABAJO="
								+ "CVTRGR.IDTRABAJO AND U.IDRECURSO=CVTRGR.IDRECURSO AND CVTRGR."
								+ "IDUNIDAD=U.IDUNIDAD AND CVTRGR.IDFICHA=U.IDFICHA AND CVDIMA.LEIDO"
								+ "=0 AND CVDI.ESTADO=1 AND CVGRTR.ESTADO =1 AND U.ESTADO='1' AND "
								+ "CVGRTR.ESTADO='1' AND U.DESHABILITADO_DOC='1' AND CVTRGR.IDFICHA=?"
								+ " AND CVTRGR.IDUNIDAD=? AND CVDIMA.IDMATRICULA = ?)+(SELECT COUNT"
								+ "(T.IDTRABAJO) FROM CV_TRABAJO_GRUPAL T,CV_GRUPO_TRABAJO G,CV_FICHA"
								+ "_UNIDAD_RECURSO U WHERE T.IDFICHA=U.IDFICHA AND T.IDUNIDAD=U.IDUNIDAD"
								+ " AND U.IDRECURSO=T.IDRECURSO AND T.IDTRABAJO=G.IDTRABAJO AND U.ESTADO"
								+ "='1' AND U.DESHABILITADO_DOC='1' AND G.ESTADO='1' AND G.BANDERA = '1'"
								+ " AND (SELECT COUNT(IDMATRICULA)-COUNT(NOTA_PROMEDIO) FROM CV_GRUPO_TRABAJO"
								+ "_MATRICULA WHERE IDTRABAJO=G.IDTRABAJO AND IDGRUPO=G.IDGRUPO)!=0 AND "
								+ "SYSDATE >= T.FECHA_ACTIVACION AND T.IDFICHA=? AND T.IDUNIDAD=?) BANDERA"
								+ " FROM DUAL) as cantTGrup from dual";
					} else {
						if (Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE == rolTemp
								.getIdrol()) {
							query = "SELECT (SELECT COUNT(T.IDTRABAJO) FROM CV_TRABAJO_INDIVIDUAL T,"
									+ "CV_TRABAJO_INDIVIDUAL_NOTA N, CV_FICHA_UNIDAD_RECURSO U WHERE"
									+ " T.IDTRABAJO=N.IDTRABAJO AND T.IDUNIDAD=U.IDUNIDAD AND T.IDFICHA"
									+ "=U.IDFICHA AND U.IDRECURSO=T.IDRECURSO AND U.ESTADO='1' AND U."
									+ "DESHABILITADO_ESTU='1' AND N.ESTADO IN (0,2) AND N.NOTA IS NULL"
									+ " AND SYSDATE >= T.FECHA_ACTIVACION AND T.IDFICHA=? AND T.IDUNIDAD"
									+ "=? AND N.IDMATRICULA=?) as cantTIndi,"
									+ "(SELECT COUNT(cvdi.iddialogo) FROM cv_dialogo cvdi,cv_dialogo_matricula"
									+ " cvdima,cv_ficha_unidad_recurso fiunre WHERE cvdima.iddialogo = cvdi."
									+ "iddialogo AND cvdima.leido = '0'  AND cvdi.estado = '1' AND fiunre."
									+ "estado = '1' AND cvdima.idmatricula = ? AND fiunre.idficha = cvdi.idficha"
									+ " AND fiunre.idunidad = cvdi.idunidad AND fiunre.idrecurso = cvdi.idrecurso"
									+ " AND fiunre.idficha = ? AND fiunre.idunidad = ? AND fiunre.idrecurso = ?"
									+ " and fiunre.DESHABILITADO_ESTU='1')as cantDialogo,"
									+ "(SELECT COUNT(T.IDTRABAJO) FROM CV_LABORATORIO T, CV_LABORATORIO_NOTA N,"
									+ "CV_FICHA_UNIDAD_RECURSO U WHERE T.IDTRABAJO=N.IDTRABAJO AND T.IDUNIDAD="
									+ "U.IDUNIDAD AND T.IDFICHA=U.IDFICHA AND U.IDRECURSO=T.IDRECURSO AND U."
									+ "ESTADO='1' AND U.DESHABILITADO_ESTU='1' AND N.ESTADO IN (0,2) AND N.NOTA"
									+ " IS NULL AND SYSDATE >= T.FECHA_ACTIVACION AND T.IDFICHA=? AND T.IDUNIDAD"
									+ "=? AND N.IDMATRICULA=?) as cantLaboratorio,"
									+ "(SELECT (SELECT COUNT(CVDI.IDDEBATE) FROM CV_DEBATE CVDI,"
									+ "CV_DEBATE_MATRICULA CVDIMA,CV_TRABAJO_GRUPAL CVTRGR,CV_GRUPO_TRABAJO"
									+ " CVGRTR, CV_FICHA_UNIDAD_RECURSO U WHERE CVDIMA.IDDEBATE = CVDI."
									+ "IDDEBATE AND CVGRTR.IDTRABAJO = CVDI.IDTRABAJO AND CVGRTR.IDGRUPO "
									+ "= CVDI.IDGRUPO AND CVGRTR.IDTRABAJO = CVTRGR.IDTRABAJO AND U.IDRECURSO="
									+ "CVTRGR.IDRECURSO AND CVTRGR.IDUNIDAD=U.IDUNIDAD AND CVTRGR.IDFICHA=U."
									+ "IDFICHA AND CVDIMA.LEIDO = 0 AND CVDI.ESTADO = 1  AND CVGRTR.ESTADO =1 "
									+ "AND U.ESTADO='1' AND CVGRTR.ESTADO='1' AND U.DESHABILITADO_ESTU='1' "
									+ "AND CVTRGR.IDFICHA=? AND CVTRGR.IDUNIDAD=? AND CVDIMA.IDMATRICULA = ? "
									+ ") + (SELECT COUNT(T.IDTRABAJO) FROM CV_TRABAJO_GRUPAL T,CV_GRUPO_TRABAJO G,"
									+ " CV_GRUPO_TRABAJO_MATRICULA M, CV_FICHA_UNIDAD_RECURSO U "
									+ "WHERE T.IDFICHA=U.IDFICHA AND T.IDUNIDAD=U.IDUNIDAD AND T.IDRECURSO=U."
									+ "IDRECURSO AND T.IDTRABAJO=G.IDTRABAJO AND G.IDTRABAJO=M.IDTRABAJO AND G."
									+ "IDGRUPO=M.IDGRUPO AND U.ESTADO='1' AND U.DESHABILITADO_ESTU='1' AND G."
									+ "ESTADO='1' AND G.BANDERA = '2' AND (SELECT COUNT(IDMATRICULA)-COUNT("
									+ "NOTA_PROMEDIO) FROM CV_GRUPO_TRABAJO_MATRICULA WHERE IDTRABAJO=G.IDTRABAJO"
									+ " AND IDGRUPO=G.IDGRUPO)!=0 AND SYSDATE >= T.FECHA_ACTIVACION AND T.IDFICHA"
									+ "=? AND T.IDUNIDAD=? AND IDMATRICULA=?) BANDERA FROM DUAL) as cantTGrup,"
									+ " '0' as cantTest from dual";
						} else {
							query = "SELECT 0 as cantLaboratorio,0 as cantDialogo,0 as cantTIndi"
									+ ",0 as cantTGrup, 0 as cantTest from dual";
						}
					}
					stmt2 =  cons
							.prepareStatement(query);
					while (result.next()) {
						unidad = new Unidad();
						unidad.setIdUnidad(result.getString("IDUNIDAD"));
						unidad.setNombreCorto(result.getString("NOMBRE_CORTO"));
						unidad.setNombreCompleto(result
								.getString("NOMBRE_COMPLETO"));
						unidad.setAlto(result.getString("ALTO"));
						unidad.setAncho(result.getString("ANCHO"));
						unidad.setEstado(result.getInt("ESTADO"));

						if (Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE == rolTemp
								.getIdrol()
								|| Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE == rolTemp
										.getIdrol()) {
							stmt2.setString(1, result.getString("IDUNIDAD"));
							stmt2.setInt(2, aula.getIdFicha());
							stmt2.setString(3, result.getString("IDUNIDAD"));
							stmt2.setInt(4, aula.getIdMatricula());
							stmt2.setInt(5, aula.getIdFicha());
							stmt2.setString(6, result.getString("IDUNIDAD"));
							stmt2.setString(7, Constante.RECURSO_ID_DIALOGO);
							stmt2.setInt(8, aula.getIdFicha());
							stmt2.setString(9, result.getString("IDUNIDAD"));
							stmt2.setInt(10, aula.getIdFicha());
							stmt2.setString(11, result.getString("IDUNIDAD"));
							stmt2.setInt(12, aula.getIdMatricula());
							stmt2.setInt(13, aula.getIdFicha());
							stmt2.setString(14, result.getString("IDUNIDAD"));

						} else {
							if (Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE == rolTemp
									.getIdrol()) {
								stmt2.setInt(1, aula.getIdFicha());
								stmt2
										.setString(2, result
												.getString("IDUNIDAD"));
								stmt2.setInt(3, aula.getIdMatricula());

								stmt2.setInt(4, aula.getIdMatricula());
								stmt2.setInt(5, aula.getIdFicha());
								stmt2
										.setString(6, result
												.getString("IDUNIDAD"));
								stmt2
										.setString(7,
												Constante.RECURSO_ID_DIALOGO);

								stmt2.setInt(8, aula.getIdFicha());
								stmt2
										.setString(9, result
												.getString("IDUNIDAD"));
								stmt2.setInt(10, aula.getIdMatricula());

								stmt2.setInt(11, aula.getIdFicha());
								stmt2.setString(12, result
										.getString("IDUNIDAD"));
								stmt2.setInt(13, aula.getIdMatricula());
								stmt2.setInt(14, aula.getIdFicha());
								stmt2.setString(15, result
										.getString("IDUNIDAD"));
								stmt2.setInt(16, aula.getIdMatricula());
							}
						}
						subresult2 =  stmt2.executeQuery();
						if (subresult2.next()) {
							unidad.setCantidadTest(subresult2
									.getInt("cantTest"));
							unidad.setCantidadDialogo(subresult2
									.getInt("cantDialogo"));
							unidad.setCantidadLaboratorio(subresult2
									.getInt("cantLaboratorio"));
							unidad.setCantidadTGrupal(subresult2
									.getInt("cantTGrup"));
							unidad.setCantidadTIndividual(subresult2
									.getInt("cantTIndi"));
						}
						stmt.setInt(1, aula.getIdFicha());
						stmt.setString(2, result.getString("IDUNIDAD"));
						subresult2 =  stmt.executeQuery();
						recursos = new HashMap<String, Recurso>(
								Constante.RECURSOS_TOTAL);
						while (subresult2.next()) {
							recurso = new Recurso();
							recurso
									.setIdRecurso(subresult2
											.getInt("IDRECURSO"));
							recurso.setNombre(subresult2.getString("NOMBRE"));
							recurso.setEstado(subresult2.getInt("ESTADO"));
							recurso.setDeshabilitadoDocente(subresult2
									.getInt("DESHABILITADO_DOC"));
							recurso.setDeshabilitadoEstudiante(subresult2
									.getInt("DESHABILITADO_ESTU"));
							recurso.setCalifica(subresult2.getInt("CALIFICA"));
							recurso.setPeso(subresult2.getString("PESO"));
							recursos.put(subresult2.getString("IDRECURSO"),
									recurso);
						}
						recursos = new TreeMap<String, Recurso>(recursos);
						unidad.setRecurso(recursos);
						unidades.add(unidad);
					}
					aula.setUnidades(unidades);
					stmt2.close();
					subresult2.close();
				}
			} else {
				return null;
			}
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
		return aula;
	}*/
//
//	public AulaVirtual obtenerAulaTmpParaAuditoria(int idMatricula)
//			throws DAOException {
//		log.info("obtenerAulaTmpParaAuditoria(int idMatricula)");
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		ResultSet subResult = null;
//		Connection cons = null;
//		AulaVirtual aula = new AulaVirtual();
//		Collection<Unidad> unidades = new ArrayList<Unidad>();
//		Unidad unidad = null;
//		Recurso recurso = null;
//		Map<String, Recurso> recursos = null;
//
//		try {
//			String query = "SELECT FU.IDUNIDAD, FU.IDFICHA "
//					+ "FROM CV_FICHA_UNIDAD FU, CV_MATRICULA M "
//					+ "WHERE M.IDFICHA=FU.IDFICHA AND FU.ESTADO=1 AND M.IDMATRICULA=?";
//			
////			Class.forName("oracle.jdbc.driver.OracleDriver");
////			cons = DriverManager.getConnection("jdbc:oracle:thin:@192.168.68.101:1521:bdopencampus", "campusv4", "campusv4");
//
//			cons =  dataSource.getConnection();
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, idMatricula);
//			result =  stmt.executeQuery();
//
//			query = "SELECT IDRECURSO,ESTADO,CALIFICA FROM CV_FICHA_UNIDAD_RECURSO  "
//					+ "WHERE IDFICHA=? AND IDUNIDAD=? ORDER BY IDRECURSO";
//
//			stmt =  cons.prepareStatement(query);
//
//			while (result.next()) {
//				unidad = new Unidad();
//
//				stmt.setInt(1, result.getInt("IDFICHA"));
//				stmt.setInt(2, result.getInt("IDUNIDAD"));
//				subResult =  stmt.executeQuery();
//
//				recursos = new HashMap<String, Recurso>();
//
//				while (subResult.next()) {
//					recurso = new Recurso();
//					recurso.setEstado(subResult.getInt("ESTADO"));
//					recurso.setCalifica(subResult.getInt("CALIFICA"));
//					recursos.put(subResult.getString("IDRECURSO"), recurso);
//				}
//				recursos = new TreeMap<String, Recurso>(recursos);
//				unidad.setRecurso(recursos);
//
//				unidades.add(unidad);
//			}
//			aula.setUnidades(unidades);
//
//		} catch (SQLException e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} catch (Exception e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} finally {
//			closeResultSet(subResult);
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//
//		return aula;
//	}
//
//	public Silabo obtenerSilaboPorIDCurso(int idCurso) throws DAOException {
//		log.info("obtenerSilaboPorIDCurso(int "+idCurso+")");
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		Connection cons = null;
//		Silabo silabo = null;
//		try {
//			String query = "SELECT IDSILABO, DESCRIPCION FROM CV_SILABO "
//					+ "WHERE CODIGO_CURSO=? ORDER BY FECHA_CREACION DESC";
//			cons =  dataSource.getConnection();
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, idCurso);
//			result =  stmt.executeQuery();
//			if (result.next()) {
//				silabo = new Silabo();
//				silabo.setIdCurso(idCurso);
//				silabo.setIdSilabo(result.getInt("IDSILABO"));
//				silabo.setDescripcion(result.getString("DESCRIPCION"));
//				query = "SELECT US.INDICE,U.IDUNIDAD,NOMBRE_CORTO,NOMBRE_COMPLETO "
//						+ "FROM CV_UNIDAD U, CV_UNIDAD_SILABO US WHERE U.IDUNIDAD"
//						+ "=US.IDUNIDAD AND US.IDSILABO=? ORDER BY INDICE";
//				stmt =  cons.prepareStatement(query);
//				stmt.setInt(1, result.getInt("IDSILABO"));
//				result =  stmt.executeQuery();
//				Collection<UnidadSilabo> uscol = new ArrayList<UnidadSilabo>();
//				while (result.next()) {
//					Unidad unidad = new Unidad();
//					unidad.setIdUnidad(result.getString("IDUNIDAD"));
//					unidad.setNombreCorto(result.getString("NOMBRE_CORTO"));
//					unidad.setNombreCompleto(result
//							.getString("NOMBRE_COMPLETO"));
//					UnidadSilabo us = new UnidadSilabo();
//					us.setIndice(result.getString("INDICE"));
//					us.setUnidad(unidad);
//					uscol.add(us);
//				}
//				silabo.setUnidadSilabo(uscol);
//			}
//		} catch (SQLException e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} catch (Exception e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} finally {
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//
//		return silabo;
//	}
//
//	public void asignarSilabo(AulaVirtual aulaVirtual) throws DAOException {
//		log.info("asignarSilabo(AulaVirtual aulaVirtual)");
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		Connection cons = null;
//		try {
//			String query = "UPDATE CV_FICHA SET IDSILABO=? WHERE IDFICHA=?";
//			cons =  dataSource.getConnection();
//			cons.setAutoCommit(false);
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, aulaVirtual.getIdCurso());
//			stmt.setInt(2, aulaVirtual.getIdFicha());
//			if (stmt.executeUpdate() == 0) {
//				throw new DAOException("No se pudo insertar comando:"
//						+ " asignarSilabo()-UPDATE CV_FICHA");
//			}
//			query = "INSERT INTO CV_FICHA_UNIDAD (IDFICHA,IDUNIDAD,ESTADO,USUARIO_MOD,"
//					+ "FECHA_MOD,IDSILABO) SELECT ?,IDUNIDAD,1,?,SYSDATE,IDSILABO "
//					+ "FROM CV_UNIDAD_SILABO WHERE IDSILABO=? ORDER BY INDICE";
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, aulaVirtual.getIdFicha());
//			stmt.setString(2, aulaVirtual.getUsuarioModificacion());
//			stmt.setInt(3, aulaVirtual.getIdCurso());
//			if (0 == stmt.executeUpdate()) {
//				cons.rollback();
//				throw new DAOException("No se pudo insertar comando: "
//						+ "asignarSilabo()-INSERT INTO CV_FICHA_UNIDAD");
//			}
//			// INICIALIZACION DE CV_FICHA_UNIDAD_RECURSO
//			query = "INSERT INTO CV_FICHA_UNIDAD_RECURSO(IDFICHA,IDUNIDAD,IDRECURSO,USUARIO_MOD,FECHA_MOD,"
//					+ "ESTADO,DESHABILITADO_DOC,DESHABILITADO_ESTU,PESO,CALIFICA) "
//					+ "SELECT ?,UR.IDUNIDAD,UR.IDRECURSO,?,SYSDATE,?,?,?,?,? "
//					+ "FROM CV_UNIDAD_RECURSO UR,CV_UNIDAD_SILABO US "
//					+ "WHERE US.IDUNIDAD=UR.IDUNIDAD AND US.IDSILABO=? AND IDRECURSO=? "
//					+ "ORDER BY US.INDICE";
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, aulaVirtual.getIdFicha());
//			stmt.setString(2, aulaVirtual.getUsuarioModificacion());
//			stmt.setInt(8, aulaVirtual.getIdCurso());
//
//			// RECURSO_ID_TEXTO
//			stmt.setInt(3, Constante.ESTADO_ACTIVO);// ESTADO
//			stmt.setInt(4, Constante.ESTADO_ACTIVO);// DESHABILITADO_DOC
//			stmt.setInt(5, Constante.ESTADO_ACTIVO);// DESHABILITADO_ESTU
//			stmt.setInt(6, Constante.ESTADO_ACTIVO);// PESO
//			stmt.setInt(7, Constante.ESTADO_ACTIVO);// CALIFICA
//			stmt.setString(9, Constante.RECURSO_ID_TEXTO);
//			if (0 == stmt.executeUpdate()) {
//				throw new DAOException(
//						"Ningun registro insertado: CV_FICHA_UNIDAD_RECURSO");
//			}
//
//			// RECURSO_ID_REPASO
//			stmt.setInt(3, Constante.ESTADO_ACTIVO);// ESTADO
//			stmt.setInt(4, Constante.ESTADO_ACTIVO);// DESHABILITADO_DOC
//			stmt.setInt(5, Constante.ESTADO_ACTIVO);// DESHABILITADO_ESTU
//			stmt.setInt(6, Constante.ESTADO_ACTIVO);// PESO
//			stmt.setInt(7, Constante.ESTADO_ACTIVO);// CALIFICA
//			stmt.setString(9, Constante.RECURSO_ID_REPASO);
//			if (0 == stmt.executeUpdate()) {
//				throw new DAOException(
//						"Ningun registro insertado: CV_FICHA_UNIDAD_RECURSO");
//			}
//
//			// RECURSO_ID_LABORATORIO
//			stmt.setInt(3, Constante.ESTADO_INACTIVO);// ESTADO
//			stmt.setInt(4, Constante.ESTADO_ACTIVO);// DESHABILITADO_DOC
//			stmt.setInt(5, Constante.ESTADO_ACTIVO);// DESHABILITADO_ESTU
//			stmt.setInt(6, Constante.ESTADO_ACTIVO);// PESO
//			stmt.setInt(7, Constante.ESTADO_INACTIVO);// CALIFICA
//			stmt.setString(9, Constante.RECURSO_ID_LABORATORIO);
//			if (0 == stmt.executeUpdate()) {
//				throw new DAOException(
//						"Ningun registro insertado: CV_FICHA_UNIDAD_RECURSO");
//			}
//
//			// RECURSO_ID_DIALOGO
//			stmt.setInt(3, Constante.ESTADO_ACTIVO);// ESTADO
//			stmt.setInt(4, Constante.ESTADO_ACTIVO);// DESHABILITADO_DOC
//			stmt.setInt(5, Constante.ESTADO_ACTIVO);// DESHABILITADO_ESTU
//			stmt.setInt(6, Constante.ESTADO_ACTIVO);// PESO
//			stmt.setInt(7, Constante.ESTADO_ACTIVO);// CALIFICA
//			stmt.setString(9, Constante.RECURSO_ID_DIALOGO);
//			if (0 == stmt.executeUpdate()) {
//				throw new DAOException(
//						"Ningun registro insertado: CV_FICHA_UNIDAD_RECURSO");
//			}
//
//			// RECURSO_ID_TRABAJO
//			stmt.setInt(3, Constante.ESTADO_INACTIVO);// ESTADO
//			stmt.setInt(4, Constante.ESTADO_ACTIVO);// DESHABILITADO_DOC
//			stmt.setInt(5, Constante.ESTADO_ACTIVO);// DESHABILITADO_ESTU
//			stmt.setInt(6, Constante.ESTADO_ACTIVO);// PESO
//			stmt.setInt(7, Constante.ESTADO_ACTIVO);// CALIFICA
//			stmt.setString(9, Constante.RECURSO_ID_TRABAJO);
//			if (0 == stmt.executeUpdate()) {
//				throw new DAOException(
//						"Ningun registro insertado: CV_FICHA_UNIDAD_RECURSO");
//			}
//
//			// RECURSO_ID_ACTIVIDAD_GRUPAL
//			stmt.setInt(3, Constante.ESTADO_INACTIVO);// ESTADO
//			stmt.setInt(4, Constante.ESTADO_ACTIVO);// DESHABILITADO_DOC
//			stmt.setInt(5, Constante.ESTADO_ACTIVO);// DESHABILITADO_ESTU
//			stmt.setInt(6, Constante.ESTADO_ACTIVO);// PESO
//			stmt.setInt(7, Constante.ESTADO_ACTIVO);// CALIFICA
//			stmt.setString(9, Constante.RECURSO_ID_ACTIVIDAD_GRUPAL);
//			if (0 == stmt.executeUpdate()) {
//				throw new DAOException(
//						"Ningun registro insertado: CV_FICHA_UNIDAD_RECURSO");
//			}
//
//			// RECURSO_ID_TEST
//			stmt.setInt(3, Constante.ESTADO_ACTIVO);// ESTADO
//			stmt.setInt(4, Constante.ESTADO_ACTIVO);// DESHABILITADO_DOC
//			stmt.setInt(5, Constante.ESTADO_INACTIVO);// DESHABILITADO_ESTU
//			stmt.setInt(6, Constante.ESTADO_ACTIVO);// PESO
//			stmt.setInt(7, Constante.ESTADO_ACTIVO);// CALIFICA
//			stmt.setString(9, Constante.RECURSO_ID_TEST);
//			if (0 == stmt.executeUpdate()) {
//				throw new DAOException(
//						"Ningun registro insertado: CV_FICHA_UNIDAD_RECURSO");
//			}
//
//			// RECURSO_ID_PRACTICA
//			stmt.setInt(3, Constante.ESTADO_INACTIVO);// ESTADO
//			stmt.setInt(4, Constante.ESTADO_ACTIVO);// DESHABILITADO_DOC
//			stmt.setInt(5, Constante.ESTADO_ACTIVO);// DESHABILITADO_ESTU
//			stmt.setInt(6, Constante.ESTADO_ACTIVO);// PESO
//			stmt.setInt(7, Constante.ESTADO_ACTIVO);// CALIFICA
//			stmt.setString(9, Constante.RECURSO_ID_PRACTICA);
//			if (0 == stmt.executeUpdate()) {
//				throw new DAOException(
//						"Ningun registro insertado: CV_FICHA_UNIDAD_RECURSO");
//			}
//
//			// RECURSO_ID_REPASO_ENLACE
//			stmt.setInt(3, Constante.ESTADO_ACTIVO);// ESTADO
//			stmt.setInt(4, Constante.ESTADO_ACTIVO);// DESHABILITADO_DOC
//			stmt.setInt(5, Constante.ESTADO_ACTIVO);// DESHABILITADO_ESTU
//			stmt.setInt(6, Constante.ESTADO_ACTIVO);// PESO
//			stmt.setInt(7, Constante.ESTADO_ACTIVO);// CALIFICA
//			stmt.setString(9, Constante.RECURSO_ID_REPASO_ENLACE);
//			if (0 == stmt.executeUpdate()) {
//				throw new DAOException(
//						"Ningun registro insertado: CV_FICHA_UNIDAD_RECURSO");
//			}
//
//			// RECURSO_ID_TEXTO_FUENTES
//			stmt.setInt(3, Constante.ESTADO_INACTIVO);// ESTADO
//			stmt.setInt(4, Constante.ESTADO_ACTIVO);// DESHABILITADO_DOC
//			stmt.setInt(5, Constante.ESTADO_ACTIVO);// DESHABILITADO_ESTU
//			stmt.setInt(6, Constante.ESTADO_ACTIVO);// PESO
//			stmt.setInt(7, Constante.ESTADO_ACTIVO);// CALIFICA
//			stmt.setString(9, Constante.RECURSO_ID_TEXTO_FUENTES);
//			if (0 == stmt.executeUpdate()) {
//				throw new DAOException(
//						"Ningun registro insertado: CV_FICHA_UNIDAD_RECURSO");
//			}
//
//			// RECURSO_ID_REPASO_FUENTES
//			stmt.setInt(3, Constante.ESTADO_INACTIVO);// ESTADO
//			stmt.setInt(4, Constante.ESTADO_ACTIVO);// DESHABILITADO_DOC
//			stmt.setInt(5, Constante.ESTADO_ACTIVO);// DESHABILITADO_ESTU
//			stmt.setInt(6, Constante.ESTADO_ACTIVO);// PESO
//			stmt.setInt(7, Constante.ESTADO_ACTIVO);// CALIFICA
//			stmt.setString(9, Constante.RECURSO_ID_REPASO_FUENTES);
//			if (0 == stmt.executeUpdate()) {
//				throw new DAOException(
//						"Ningun registro insertado: CV_FICHA_UNIDAD_RECURSO");
//			}
//
//			// RECURSO_ID_VIDEO
//			stmt.setInt(3, Constante.ESTADO_INACTIVO);// ESTADO
//			stmt.setInt(4, Constante.ESTADO_ACTIVO);// DESHABILITADO_DOC
//			stmt.setInt(5, Constante.ESTADO_ACTIVO);// DESHABILITADO_ESTU
//			stmt.setInt(6, Constante.ESTADO_ACTIVO);// PESO
//			stmt.setInt(7, Constante.ESTADO_ACTIVO);// CALIFICA
//			stmt.setString(9, Constante.RECURSO_ID_VIDEO);
//			if (0 == stmt.executeUpdate()) {
//				throw new DAOException(
//						"Ningun registro insertado: CV_FICHA_UNIDAD_RECURSO");
//			}
//
//			// PESOS PARA CADA RECURSO ****
//			query = "UPDATE CV_FICHA_RECURSO SET PESO=?, USUARIO_MOD=?, FECHA_MOD=sysdate "
//				+ " WHERE IDFICHA=? AND IDRECURSO=?";		
//			stmt =  cons.prepareStatement(query);
//			stmt.setString(2, aulaVirtual.getUsuarioModificacion());		
//			stmt.setInt(3, aulaVirtual.getIdFicha());	
//			
//			// RECURSO_ID_LABORATORIO
//			stmt.setInt(1, 0);
//			stmt.setString(4, Constante.RECURSO_ID_LABORATORIO);
//			if (1 != stmt.executeUpdate()) {
//				throw new DAOException("Error en pesos para cada recurso CV_FICHA_RECURSO");
//			}
//			
//			// RECURSO_ID_DIALOGO
//			stmt.setInt(1, 0);
//			stmt.setString(4, Constante.RECURSO_ID_DIALOGO);
//			if (1 != stmt.executeUpdate()) {
//				throw new DAOException("Error en pesos para cada recurso CV_FICHA_RECURSO");
//			}
//			
//			// RECURSO_ID_TRABAJO
//			stmt.setInt(1, 0);
//			stmt.setString(4, Constante.RECURSO_ID_TRABAJO);
//			if (1 != stmt.executeUpdate()) {
//				throw new DAOException("Error en pesos para cada recurso CV_FICHA_RECURSO");
//			}
//			
//			// RECURSO_ID_ACTIVIDAD_GRUPAL
//			stmt.setInt(1, 0);
//			stmt.setString(4, Constante.RECURSO_ID_ACTIVIDAD_GRUPAL);
//			if (1 != stmt.executeUpdate()) {
//				throw new DAOException("Error en pesos para cada recurso CV_FICHA_RECURSO");
//			}
//			
//			// RECURSO_ID_TEST
//			stmt.setInt(1, 100);
//			stmt.setString(4, Constante.RECURSO_ID_TEST);
//			if (1 != stmt.executeUpdate()) {
//				throw new DAOException("Error en pesos para cada recurso CV_FICHA_RECURSO");
//			}
//			
//			// INICIALIZACION DE TRABAJOS ****
//			query = "INSERT INTO CV_TRABAJO_INDIVIDUAL (IDTRABAJO,IDFICHA,IDUNIDAD,IDRECURSO,"
//					+ "FECHA_CREACION,USUARIO_CREACION,FECHA_MOD,USUARIO_MOD) SELECT "
//					+ "SEQCVTRABAJOINDIVIDUAL.NEXTVAL,IDFICHA,IDUNIDAD,IDRECURSO,SYSDATE,?,"
//					+ "SYSDATE,? FROM CV_FICHA_UNIDAD_RECURSO WHERE IDFICHA=? AND IDRECURSO=?";
//			stmt =  cons.prepareStatement(query);
//			stmt.setString(1, aulaVirtual.getUsuarioModificacion());
//			stmt.setString(2, aulaVirtual.getUsuarioModificacion());
//			stmt.setInt(3, aulaVirtual.getIdFicha());
//			stmt.setString(4, Constante.RECURSO_ID_TRABAJO);
//			if (0 == stmt.executeUpdate()) {
//				cons.rollback();
//				throw new DAOException(
//						"No se pudo insertar comando: asignarSilabo()-INSERT INTO CV_TRABAJO_INDIVIDUAL");
//			}
//			query = "INSERT INTO CV_LABORATORIO (IDTRABAJO,IDFICHA,IDUNIDAD,IDRECURSO,"
//					+ "FECHA_CREACION,USUARIO_CREACION,FECHA_MOD,USUARIO_MOD) SELECT "
//					+ "SEQCVLABORATORIO.NEXTVAL,IDFICHA,IDUNIDAD,IDRECURSO,SYSDATE,?,"
//					+ "SYSDATE,? FROM CV_FICHA_UNIDAD_RECURSO WHERE IDFICHA=? AND IDRECURSO=?";
//			stmt =  cons.prepareStatement(query);
//			stmt.setString(1, aulaVirtual.getUsuarioModificacion());
//			stmt.setString(2, aulaVirtual.getUsuarioModificacion());
//			stmt.setInt(3, aulaVirtual.getIdFicha());
//			stmt.setString(4, Constante.RECURSO_ID_LABORATORIO);
//			if (0 == stmt.executeUpdate()) {
//				cons.rollback();
//				throw new DAOException("No se pudo insertar comando: "
//						+ "asignarSilabo()-INSERT INTO CV_LABORATORIO");
//			}
//			query = "INSERT INTO CV_TRABAJO_GRUPAL (IDTRABAJO,IDFICHA,IDUNIDAD,IDRECURSO,"
//					+ "FECHA_CREACION,USUARIO_CREACION,FECHA_MOD,USUARIO_MOD) SELECT "
//					+ "SEQCVTRABAJOGRUPAL.NEXTVAL,IDFICHA,IDUNIDAD,IDRECURSO,SYSDATE,?,"
//					+ "SYSDATE,? FROM CV_FICHA_UNIDAD_RECURSO WHERE IDFICHA=? AND IDRECURSO=?";
//			stmt =  cons.prepareStatement(query);
//			stmt.setString(1, aulaVirtual.getUsuarioModificacion());
//			stmt.setString(2, aulaVirtual.getUsuarioModificacion());
//			stmt.setInt(3, aulaVirtual.getIdFicha());
//			stmt.setString(4, Constante.RECURSO_ID_ACTIVIDAD_GRUPAL);
//			if (0 == stmt.executeUpdate()) {
//				cons.rollback();
//				throw new DAOException("No se pudo insertar comando: "
//						+ "asignarSilabo()-INSERT INTO CV_LABORATORIO");
//			}
//			cons.commit();
//		} catch (SQLException e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} catch (DAOException e) {
//			log.error(e.toString());
//			throw e;
//		} catch (Exception e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} finally {
//			try {
//				cons.rollback();
//			} catch (SQLException e1) {
//				log.error(e1.toString());
//			}
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//	}
//
//	public PlanDocente obtenerPlanDocente(AulaVirtual aula) throws DAOException {
//		log.info("obtenerPlanDocente(AulaVirtual aula)");
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		Connection cons = null;
//		PlanDocente plan = new PlanDocente();
//		try {
//			String query = "SELECT CVFI.IDSILABO,DOPLTEPE.OBJETIVO,DOPLTEPE.INTRODUCCION,DOPLTEPE.BIBLIOGRAFIA,METODOLOGIA,OBJGEN,TEVA.NOMBRE TEVALUACION "
//					+ "FROM CV_FICHA CVFI,DOCENCIA.DOC_PLAN_TEMA_PERIODO DOPLTEPE,DOCENCIA.DOC_TIPO_EVALUACION TEVA,COMERCIAL.COM_PRODUCTO COM "
//					+ "WHERE DOPLTEPE.CODCURSO=CVFI.CODIGO_CURSO AND TEVA.CODIGO=CODTIPEVAL "
//					+ "AND DOPLTEPE.CODPERIODO=pkg_cv_ficha_util.fx_cv_ficha_periodo_uds(CVFI.IDFICHA) "
//					+ "AND CVFI.IDFICHA=?";
//			cons =  dataSource.getConnection();
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, aula.getIdFicha());
//			result =  stmt.executeQuery();
//			if (result.next()) {
//				plan.setIntroduccion(result.getString("introduccion"));
//				plan.setObjetivo(result.getString("objetivo"));
//				plan.setBibliografia(result.getString("bibliografia"));
//				plan.setMetodologia(result.getString("METODOLOGIA"));
//				plan.setObjetivoGeneral(result.getString("OBJGEN"));
//				plan.setTipoEvaluacion(result.getString("TEVALUACION"));
//			}
//
//			query = "SELECT U.NOMBRE_COMPLETO FROM CV_UNIDAD U,CV_UNIDAD_SILABO US,"
//					+ "CV_FICHA_UNIDAD FU WHERE US.IDSILABO=FU.IDSILABO AND US.IDUNIDAD"
//					+ "=FU.IDUNIDAD AND FU.ESTADO=1 AND U.IDUNIDAD=US.IDUNIDAD AND IDFICHA=? ORDER BY INDICE";
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, aula.getIdFicha());
//			result =  stmt.executeQuery();
//			while (result.next()) {
//				plan.getUnidades().add(result.getString("NOMBRE_COMPLETO"));
//			}
//
//			// Contar cuantos recursos tiene una ficha
//			query = "SELECT COUNT(*) AS RECURSOS_TOTAL FROM CV_FICHA_RECURSO WHERE  IDFICHA=?";
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, aula.getIdFicha());
//			result =  stmt.executeQuery();
//			result.next();
//			int recursos_total = result.getInt("RECURSOS_TOTAL");
//
//			// Obtener los pesos de las actividades
//			query = "SELECT FR.IDFICHA, FR.IDRECURSO, FR.PESO, FR.USUARIO_MOD, FR.FECHA_MOD, R.NOMBRE "
//					+ "FROM CV_FICHA_RECURSO FR, CV_RECURSO R "
//					+ "WHERE FR.IDRECURSO=R.IDRECURSO AND FR.IDFICHA=? AND FR.IDRECURSO=?";
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, aula.getIdFicha());
//			Collection<FichaRecurso> fichasRecursos = new ArrayList<FichaRecurso>();
//			for (int i = 0; i < recursos_total; i++) {
//				stmt.setInt(2, i);
//				result =  stmt.executeQuery();
//				while (result.next()) {
//					FichaRecurso vo = new FichaRecurso();
//					vo.setIdFicha(String.valueOf(aula.getIdFicha()));
//					vo.setIdRecurso(result.getString("IDRECURSO"));
//					vo.setPeso(result.getString("PESO"));
//					vo.setNombreRecurso(result.getString("NOMBRE"));
//					fichasRecursos.add(vo);
//				}
//			}
//			plan.setFichasRecursos(fichasRecursos);
//
//			// 
//			// query = "SELECT ";
//
//			query = "SELECT DCR.CODCURSO, DCR.CODPLANCURRICULAR, DCR.CODRESULTADO, DRC.RESULTADO, DRC.INDICE "
//					+ "FROM DOCENCIA.DOC_CURSO_RESULTADO DCR, DOCENCIA.DOC_RESULTADO_CARRERA DRC "
//					+ "WHERE DRC.CODRESULTADO=DCR.CODRESULTADO AND DRC.CODPLANCURRICULAR=DCR.CODPLANCURRICULAR "
//					+ "AND DCR.CODCURSO =? AND DRC.CODESPECIALIDAD=?";
//			// System.out.println(aula.getIdCurso()+"-"+aula.getIdFormacion());
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, aula.getIdCurso());
//			stmt.setInt(2, aula.getIdFormacion());
//			result =  stmt.executeQuery();
//			Collection<CursoResultado> c = new ArrayList<CursoResultado>();
//			while (result.next()) {
//				CursoResultado vo = new CursoResultado();
//				vo.setIdCurso(result.getString("CODCURSO"));
//				vo.setIdPlanCurricular(result.getString("CODPLANCURRICULAR"));
//				vo.setIdResultado(result.getString("CODRESULTADO"));
//				vo.setResultado(result.getString("RESULTADO"));
//				vo.setIndice(result.getString("INDICE"));
//				c.add(vo);
//			}
//
//			plan.setCursoResultados(c);
//		} catch (SQLException e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} catch (Exception e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} finally {
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//		return plan;
//	}
//	
//	public Collection<ReporteNotas> listarReporteDeNotas(int idFicha)throws DAOException {
//		log.info("listarReporteDeNotas(int "+idFicha+")");
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		ResultSet result2 = null;
//		ResultSet result3 = null;
//		Connection cons = null;
//		Collection<MatriculaGTest> tests = null;
//		MatriculaGTest test = null;
//		MatriculaGTest promedio = null;
//		ReporteNotas reporteNotas = null;
//		Collection<ReporteNotas> reporte = new ArrayList<ReporteNotas>();
//		Float minimo = null;
//		Float maximo = null;
//		Float avgInt = null;
//		Integer veces  = null;
//		
//		try {
//			String query = "select pkg_cv_util.fx_cv_formato_nombre(m.usuario) estudiante,idmatricula  " +
//					"from cv_matricula m  " +
//					"where idrol=4 and estado=1 and eliminado=0 and idficha=? " +
//					"order by estudiante";
//			cons =  dataSource.getConnection();
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, idFicha);
//			result =  stmt.executeQuery();
//			
//			query = "SELECT US.IDUNIDAD FROM CV_FICHA_UNIDAD FU, CV_UNIDAD_SILABO US  "
//				+ "WHERE US.IDSILABO=FU.IDSILABO AND US.IDUNIDAD=FU.IDUNIDAD "
//				+ "AND FU.IDFICHA=? AND FU.ESTADO=1 ORDER BY US.INDICE";
//
//			stmt =  cons.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
//			stmt.setInt(1, idFicha);
//			result2 =  stmt.executeQuery();
//			
//			
//			while (result.next()) {
//				log.info("ReporteDeNotas() Estudiante: "+result.getString("estudiante"));
//				reporteNotas = new ReporteNotas();
//				reporteNotas.setEstudiante(result.getString("estudiante"));
//				tests = new ArrayList<MatriculaGTest>();
//				promedio = new MatriculaGTest();
//				minimo = 0f;
//				maximo = 0f;
//				avgInt = 0f;
//				veces  = 0;
//				
//				//************ Seleccion de Notas por unidad ************//
//				query = "SELECT MIN(D.NOTA) MINIMO,MAX(D.NOTA) MAXIMO,ROUND(AVG(D.NOTA),2) PROMEDIO,COUNT(*) VECES "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_NOTA_TEST D "
//						+ "WHERE R.IDFICHA=D.IDFICHA AND R.IDUNIDAD=D.IDUNIDAD AND R.IDRECURSO=D.IDRECURSO "
//						+ "AND R.ESTADO='1' AND R.CALIFICA='1' AND D.ESTADO='1' AND R.IDFICHA=? AND D.IDMATRICULA=? AND R.IDUNIDAD=?";
//	
//				stmt =  cons.prepareStatement(query);
//				stmt.setInt(1, idFicha);
//				stmt.setInt(2, result.getInt("idmatricula"));
//				while (result2.next()) {
//					//log.info("\tUnidad: "+result2.getInt("IDUNIDAD"));
//					test = new MatriculaGTest();
//					
//					stmt.setInt(3, result2.getInt("IDUNIDAD"));
//					result3 =  stmt.executeQuery();
//					if (result3.next()){
//						test.setNotaMinima(result3.getString("MINIMO"));
//						test.setNotaMaxima(result3.getString("MAXIMO"));
//						test.setNotaPromedio(result3.getString("PROMEDIO"));
//						test.setVeces(result3.getInt("VECES"));
//					}
//					//------------- Promedio ----------
//					minimo += ((test.getNotaMinima()!=null)?Float.parseFloat(test.getNotaMinima()):0);
//					maximo += ((test.getNotaMinima()!=null)?Float.parseFloat(test.getNotaMaxima()):0);
//					avgInt += ((test.getNotaMinima()!=null)?Float.parseFloat(test.getNotaPromedio()):0);
//					veces  += test.getVeces();
//					//---------------------------------
//					tests.add(test);
//				}
//				//******************************************************//
//				
//				int total = tests.size();
//				promedio.setNotaMinima(String.valueOf(Math.round(minimo/total)));
//				promedio.setNotaMaxima(String.valueOf(Math.round(maximo/total)));
//				promedio.setNotaPromedio(String.valueOf(Math.round(avgInt/total)));
//				promedio.setVeces(Math.round(veces/total));
//				
//				reporteNotas.setTests(tests);
//				reporteNotas.setPromedio(promedio);
//				
//				reporte.add(reporteNotas);
//				
//				result2.beforeFirst();
//				
//			}
//		} catch (SQLException e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} catch (Exception e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} finally {
//			closeResultSet(result3);
//			closeResultSet(result2);
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//		return reporte;
//	}
//	
//	public ReporteDetalle obtenerReporteDetalle(int idMatricula)
//			throws DAOException {
//		log.info("obtenerReporteDetalle(int idMatricula)");
//		PreparedStatement stmt = null;
//		PreparedStatement stmt1 = null;
//		PreparedStatement stmt2 = null;
//		PreparedStatement stmt3 = null;
//		PreparedStatement stmt4 = null;
//		PreparedStatement stmt5 = null;
//		PreparedStatement stmt6 = null;
//		ResultSet result = null;
//		ResultSet subResult = null;
//		Connection cons = null;
//		Collection<MatriculaGLaboratorio> recursoLaboratorio = new ArrayList<MatriculaGLaboratorio>();
//		Collection<MatriculaGDialogo> recursoDialogo = new ArrayList<MatriculaGDialogo>();
//		Collection<MatriculaGTrabajo> recursoTrabajo = new ArrayList<MatriculaGTrabajo>();
//		Collection<MatriculaGTrabajoGrupal> recursoTrabajoGrupal = new ArrayList<MatriculaGTrabajoGrupal>();
//		Collection<MatriculaGTest> recursoTest = new ArrayList<MatriculaGTest>();
//		MatriculaGLaboratorio laboratorio = null;
//		MatriculaGDialogo dialogo = null;
//		MatriculaGTrabajo trabajo = null;
//		MatriculaGTrabajoGrupal grupal = null;
//		MatriculaGTest test = null;
//
//		RecursoNotaParcial notaTest = null;
//		RecursoNotaParcial notaTrabajo = null;
//		RecursoNotaParcial notaLaboratorio = null;
//		RecursoNotaParcial notaGrupal = null;
//		float notaFinal = 0;
//
//		ReporteDetalle reporte = new ReporteDetalle();
//		MatriculaIngreso mingreso;
//		try {
//			String query = "SELECT  COUNT(*) cantidad,to_char(fecha_ingreso,"
//					+ "'YYYY/MM/DD')||' 12:00:00' fecha_ingreso FROM cv_ingreso WHERE usuario ="
//					+ "(SELECT usuario FROM cv_matricula WHERE idmatricula=?) "
//					+ " AND elemento=? AND fecha_ingreso>=pkg_cv_ficha.fx_cv_ficha_fechainicio(?)"
//					+ " AND fecha_ingreso <= pkg_cv_ficha.fx_cv_ficha_fechafin(?) "
//					+ "and valor=(select idficha from cv_matricula where idmatricula = ?) GROUP BY "
//					+ "to_char(fecha_ingreso, 'YYYY/MM/DD') ORDER BY 2";
//			
////			Class.forName("oracle.jdbc.driver.OracleDriver");
////			cons = DriverManager.getConnection("jdbc:oracle:thin:@192.168.68.101:1521:bdopencampus", "campusv4", "campusv4");
//			
//			cons =  dataSource.getConnection();
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, idMatricula);
//			stmt.setInt(2, Constante.ELEMENTO_CURSO);
//			stmt.setInt(3, idMatricula);
//			stmt.setInt(4, idMatricula);
//			stmt.setInt(5, idMatricula);
//			result =  stmt.executeQuery();
//			while (result.next()) {
//				mingreso = new MatriculaIngreso();
//				mingreso.setCantidad(result.getInt("cantidad"));
//				mingreso.setFecha(Formato.getDateDeBaseDatos(result.getString("fecha_ingreso")));
//				reporte.getNumeroIngreso().add(mingreso);
//			}
//			query = "select trim(ma.usuario) idusuario,gepe.nomuno,gepe.nomdos,gepe."
//					+ "apepaterno,gepe.apematerno,pkg_cv_com_producto."
//					+ "fx_cv_com_producto_nombre_id(ma.codigo_formacion) nombreformacion,"
//					+ "pkg_cv_com_producto.fx_cv_com_producto_nombre_id(cvfi.codigo_curso)"
//					+ " nombrecurso,pkg_cv_ficha.fx_cv_ficha_"
//					+ "fechainicio(ma.idmatricula) feini,pkg_cv_ficha.fx_cv_aula_formasec(ma.idficha)"
//					+ " formasec,pkg_cv_ficha.fx_cv_ficha_fechafin(ma.idmatricula)"
//					+ " fefin,(SELECT  COUNT(*) cantidad FROM cv_ingreso WHERE usuario ="
//					+ "(SELECT usuario FROM cv_matricula WHERE idmatricula=ma.idmatricula) "
//					+ " AND elemento=? AND fecha_ingreso>=pkg_cv_ficha.fx_cv_ficha_fechainicio(ma.idmatricula)"
//					+ " AND fecha_ingreso <= pkg_cv_ficha.fx_cv_ficha_fechafin(ma.idmatricula) "
//					+ " and valor=(select idficha from cv_matricula where idmatricula = ma.idmatricula)) cantidadingreso"
//					+ ",pkg_cv_ficha.fx_cv_ficha_cantdias(ma.idmatricula) cantidadclases,(SELECT "
//					+ "count(U.IDUNIDAD) FROM CV_UNIDAD U,CV_UNIDAD_SILABO US,CV_FICHA_UNIDAD  FU "
//					+ "WHERE US.IDSILABO=FU.IDSILABO AND US.IDUNIDAD=FU.IDUNIDAD AND U.IDUNIDAD=US."
//					+ "IDUNIDAD AND IDFICHA=ma.idficha AND FU.ESTADO=1) cantidadunidad from "
//					+ "cv_matricula ma,seguridad.seg_usuario seus,cv_ficha cvfi,"
//					+ "general.gen_persona gepe where ma.idmatricula=? and ma.usuario=seus"
//					+ ".usuario and gepe.codpersona=seus.codsujeto and cvfi.idficha=ma.idficha";
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, Constante.ELEMENTO_CURSO);
//			stmt.setInt(2, idMatricula);
//			result =  stmt.executeQuery();
//			if (result.next()) {
//				reporte.setCantidadIngreso(result.getInt("cantidadingreso"));
//				reporte.setFecha1(Formato.getDateDeBaseDatos(result
//						.getString("feini")));
//				reporte.setFecha2(Formato.getDateDeBaseDatos(result
//						.getString("fefin")));
//				reporte.setNombreCompleto(Formato.formatoNombreCompletoJSP(
//						result.getString("nomuno"), result.getString("nomdos"),
//						result.getString("apepaterno"), result
//								.getString("apematerno")));
//				reporte.setNombreUsuarioReporte(Formato.formatoTexto(result
//						.getString("nomuno"))
//						+ " "
//						+ Formato.formatoTexto(result.getString("apepaterno")));
//				reporte.setIdUsuario(result.getString("idusuario"));
//				reporte.setNombreCurso(result.getString("nombrecurso"));
//				reporte.setNombreEspecialidad(result
//						.getString("nombreformacion"));
//				reporte.setCantidadClases(result.getInt("cantidadclases"));
//				reporte.setFormacionSeccion(result.getString("formasec"));
//				reporte.setCantidadUnidades(result.getInt("cantidadunidad"));
//			}
//
//			// / NOTAS
//			// *************************************************************
//
//			query = "SELECT IDFICHA FROM CV_MATRICULA WHERE IDMATRICULA=?";
//
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, idMatricula);
//			result =  stmt.executeQuery();
//
//			if (result.next()) {
//
//				int idFicha = result.getInt("IDFICHA");
//
//				query = "SELECT US.IDUNIDAD FROM CV_FICHA_UNIDAD FU, CV_UNIDAD_SILABO US  "
//						+ "WHERE US.IDSILABO=FU.IDSILABO AND US.IDUNIDAD=FU.IDUNIDAD "
//						+ "AND FU.IDFICHA=? AND FU.ESTADO=1 ORDER BY US.INDICE";
//
//				stmt =  cons.prepareStatement(query);
//				stmt.setInt(1, idFicha);
//				result =  stmt.executeQuery();
//
//				// TEST ******
//				query = "SELECT MIN(D.NOTA) MINIMO,MAX(D.NOTA) MAXIMO,ROUND(AVG(D.NOTA),2) PROMEDIO,COUNT(*) VECES "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_NOTA_TEST D "
//						+ "WHERE R.IDFICHA=D.IDFICHA AND R.IDUNIDAD=D.IDUNIDAD AND R.IDRECURSO=D.IDRECURSO "
//						+ "AND R.ESTADO='1' AND R.CALIFICA='1' AND D.ESTADO='1' AND R.IDFICHA=? AND D.IDMATRICULA=? AND R.IDUNIDAD=?";
//
//				stmt1 =  cons.prepareStatement(query);
//				stmt1.setInt(1, idFicha);
//				stmt1.setInt(2, idMatricula);
//
//				// INDIVIDUAL ******
//				query = "SELECT FECHA_ACTIVACION," +
//						"(SELECT NOTA " +
//						"FROM CV_TRABAJO_INDIVIDUAL_NOTA WHERE IDTRABAJO=TI.IDTRABAJO AND IDMATRICULA=?) NOTA, " +
//						"(SELECT COUNT(*)  " +
//						"FROM CV_TRABAJO_INDIVIDUAL_INTER I, CV_TRABAJO_INDIVIDUAL T  " +
//						"WHERE I.IDTRABAJO=T.IDTRABAJO AND T.FECHA_ENTREGA - I.FECHA_CREACION <0  " +
//						"AND T.IDUNIDAD=R.IDUNIDAD AND T.IDFICHA=R.IDFICHA AND I.IDMATRICULA=? " +
//						"AND I.INTERACCION=(SELECT MIN(INTERACCION) FROM CV_TRABAJO_INDIVIDUAL_INTER  " +
//						"WHERE IDTRABAJO=I.IDTRABAJO AND IDMATRICULA=I.IDMATRICULA AND IDMATRICULA_ENVIO=I.IDMATRICULA AND ARCHIVO_NOMBRE IS NOT NULL)) EXPIRADO, " +
//						"(SELECT COUNT(*) " +
//						"FROM CV_TRABAJO_INDIVIDUAL_INTER I, CV_TRABAJO_INDIVIDUAL T " +
//						"WHERE I.IDTRABAJO=T.IDTRABAJO " +
//						"AND T.IDUNIDAD=R.IDUNIDAD AND T.IDFICHA=R.IDFICHA AND I.IDMATRICULA=?  " +
//						"AND I.INTERACCION=(SELECT MIN(INTERACCION) FROM CV_TRABAJO_INDIVIDUAL_INTER " +
//						"WHERE IDTRABAJO=I.IDTRABAJO AND IDMATRICULA=I.IDMATRICULA AND IDMATRICULA_ENVIO=I.IDMATRICULA AND ARCHIVO_NOMBRE IS NOT NULL)) ENTREGADO " +
//						"FROM CV_FICHA_UNIDAD_RECURSO R, CV_TRABAJO_INDIVIDUAL TI " +
//						"WHERE R.IDFICHA=TI.IDFICHA AND R.IDUNIDAD=TI.IDUNIDAD AND R.IDRECURSO=TI.IDRECURSO " +
//						"AND R.ESTADO='1' AND R.CALIFICA='1' AND R.IDFICHA=? AND R.IDUNIDAD=?";
//
//				stmt2 =  cons.prepareStatement(query);
//				stmt2.setInt(1, idMatricula);
//				stmt2.setInt(2, idMatricula);
//				stmt2.setInt(3, idMatricula);
//				stmt2.setInt(4, idFicha);
//				
//				// LABORATORIO ******
//				query = "SELECT FECHA_ACTIVACION, " +
//						"(SELECT NOTA " +
//						"FROM CV_LABORATORIO_NOTA WHERE IDTRABAJO=TI.IDTRABAJO AND IDMATRICULA=?) NOTA, " +
//						"(SELECT COUNT(*) " +
//						"FROM CV_LABORATORIO_INTER I, CV_LABORATORIO T " +
//						"WHERE I.IDTRABAJO=T.IDTRABAJO AND T.FECHA_ENTREGA - I.FECHA_CREACION <0 " +
//						"AND T.IDUNIDAD=R.IDUNIDAD AND T.IDFICHA=R.IDFICHA AND I.IDMATRICULA=? " +
//						"AND I.INTERACCION=(SELECT MIN(INTERACCION) FROM CV_LABORATORIO_INTER WHERE IDTRABAJO=I.IDTRABAJO AND IDMATRICULA=I.IDMATRICULA AND IDMATRICULA_ENVIO=I.IDMATRICULA AND ARCHIVO_NOMBRE IS NOT NULL)) EXPIRADO, " +
//						"(SELECT COUNT(*) " +
//						"FROM CV_LABORATORIO_INTER I, CV_LABORATORIO T " +
//						"WHERE I.IDTRABAJO=T.IDTRABAJO " +
//						"AND T.IDUNIDAD=R.IDUNIDAD AND T.IDFICHA=R.IDFICHA AND I.IDMATRICULA=? " +
//						"AND I.INTERACCION=(SELECT MIN(INTERACCION) FROM CV_LABORATORIO_INTER " +
//						"WHERE IDTRABAJO=I.IDTRABAJO AND IDMATRICULA=I.IDMATRICULA AND IDMATRICULA_ENVIO=I.IDMATRICULA AND ARCHIVO_NOMBRE IS NOT NULL)) ENTREGADO " +
//						"FROM CV_FICHA_UNIDAD_RECURSO R, CV_LABORATORIO TI " +
//						"WHERE R.IDFICHA=TI.IDFICHA AND R.IDUNIDAD=TI.IDUNIDAD AND R.IDRECURSO=TI.IDRECURSO " +
//						"AND R.ESTADO='1' AND R.CALIFICA='1' AND R.IDFICHA=? AND R.IDUNIDAD=?";
//
//				stmt3 =  cons.prepareStatement(query);
//				stmt3.setInt(1, idMatricula);
//				stmt3.setInt(2, idMatricula);
//				stmt3.setInt(3, idMatricula);
//				stmt3.setInt(4, idFicha);
//
//				// GRUPAL ******
//				query = "SELECT TG.FECHA_ACTIVACION, " +
//						"(SELECT N.NOTA_PROMEDIO FROM CV_GRUPO_TRABAJO G, CV_GRUPO_TRABAJO_MATRICULA N " +
//						"WHERE G.IDTRABAJO=N.IDTRABAJO AND G.IDGRUPO=N.IDGRUPO AND G.IDTRABAJO=TG.IDTRABAJO AND G.ESTADO='1' AND N.IDMATRICULA=?) NOTA, " +
//						"(SELECT COUNT(*) " +
//						"FROM CV_TRABAJO_GRUPAL T, CV_GRUPO_TRABAJO G, CV_GRUPO_TRABAJO_MSG I,CV_GRUPO_TRABAJO_MATRICULA N " +
//						"WHERE G.IDTRABAJO=T.IDTRABAJO AND G.IDTRABAJO=N.IDTRABAJO AND G.IDGRUPO=N.IDGRUPO " +
//						"AND G.IDTRABAJO=I.IDTRABAJO AND G.IDGRUPO=I.IDGRUPO AND G.IDTRABAJO=TG.IDTRABAJO AND G.ESTADO='1' " +
//						"AND N.IDMATRICULA = ? AND T.FECHA_ENTREGA - I.FECHA_CREACION <0 " +
//						"AND I.IDMENSAJE=(SELECT MIN(IDMENSAJE) FROM CV_GRUPO_TRABAJO_MSG " +
//						"WHERE IDTRABAJO=I.IDTRABAJO AND IDGRUPO=I.IDGRUPO AND IDMATRICULA_EMISOR=N.IDMATRICULA AND ARCHIVO_NOMBRE IS NOT NULL)) EXPIRADO, " +
//						"(SELECT COUNT(*) " +
//						"FROM CV_TRABAJO_GRUPAL T, CV_GRUPO_TRABAJO G, CV_GRUPO_TRABAJO_MSG I,CV_GRUPO_TRABAJO_MATRICULA N " +
//						"WHERE G.IDTRABAJO=T.IDTRABAJO AND G.IDTRABAJO=N.IDTRABAJO AND G.IDGRUPO=N.IDGRUPO " +
//						"AND G.IDTRABAJO=I.IDTRABAJO AND G.IDGRUPO=I.IDGRUPO AND G.IDTRABAJO=TG.IDTRABAJO AND G.ESTADO='1' " +
//						"AND N.IDMATRICULA = ? " +
//						"AND I.IDMENSAJE=(SELECT MIN(IDMENSAJE) FROM CV_GRUPO_TRABAJO_MSG " +
//						"WHERE IDTRABAJO=I.IDTRABAJO AND IDGRUPO=I.IDGRUPO AND IDMATRICULA_EMISOR=N.IDMATRICULA AND ARCHIVO_NOMBRE IS NOT NULL)) ENTREGADO," +
//						"(SELECT COUNT(*) FROM CV_GRUPO_TRABAJO G, CV_DEBATE D, CV_GRUPO_TRABAJO_MATRICULA N " +
//						"WHERE G.IDTRABAJO=N.IDTRABAJO AND G.IDGRUPO=N.IDGRUPO AND G.IDTRABAJO=D.IDTRABAJO AND G.IDGRUPO=D.IDGRUPO " +
//						"AND D.IDTRABAJO=TG.IDTRABAJO AND G.ESTADO='1'  AND D.ESTADO='1' AND N.IDMATRICULA=?) DEBATE_TOTAL, " +
//						"(SELECT COUNT(*) FROM CV_GRUPO_TRABAJO G, CV_DEBATE D, CV_GRUPO_TRABAJO_MATRICULA N " +
//						"WHERE G.IDTRABAJO=N.IDTRABAJO AND G.IDGRUPO=N.IDGRUPO AND G.IDTRABAJO=D.IDTRABAJO AND G.IDGRUPO=D.IDGRUPO " +
//						"AND D.IDTRABAJO=TG.IDTRABAJO AND G.ESTADO='1'  AND D.ESTADO='1' AND D.IDMATRICULA=N.IDMATRICULA AND N.IDMATRICULA=?) DEBATE_HECHAS " +
//						"FROM CV_FICHA_UNIDAD_RECURSO R, CV_TRABAJO_GRUPAL TG " +
//						"WHERE R.IDFICHA=TG.IDFICHA AND R.IDUNIDAD=TG.IDUNIDAD AND R.IDRECURSO=TG.IDRECURSO " +
//						"AND R.ESTADO='1' AND R.CALIFICA='1' AND R.IDFICHA=? AND R.IDUNIDAD=?";
//
//				stmt4 =  cons.prepareStatement(query);
//				stmt4.setInt(1, idMatricula);
//				stmt4.setInt(2, idMatricula);
//				stmt4.setInt(3, idMatricula);
//				stmt4.setInt(4, idMatricula);
//				stmt4.setInt(5, idMatricula);
//				stmt4.setInt(6, idFicha);
//				
//
//				// DIALOGO ******
//				query = "SELECT COUNT(*) TOTAL "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_DIALOGO D "
//						+ "WHERE R.IDFICHA=D.IDFICHA AND R.IDUNIDAD=D.IDUNIDAD AND R.IDRECURSO=D.IDRECURSO  "
//						+ "AND R.ESTADO='1' AND R.CALIFICA='1' AND R.IDFICHA=? AND R.IDUNIDAD=?";
//
//				stmt5 =  cons.prepareStatement(query);
//				stmt5.setInt(1, idFicha);
//
//				query = "SELECT COUNT(*) HECHAS "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_DIALOGO D "
//						+ "WHERE R.IDFICHA=D.IDFICHA AND R.IDUNIDAD=D.IDUNIDAD AND R.IDRECURSO=D.IDRECURSO  "
//						+ "AND R.ESTADO='1' AND R.CALIFICA='1' AND R.IDFICHA=? AND D.IDMATRICULA=? AND R.IDUNIDAD=?";
//
//				stmt6 =  cons.prepareStatement(query);
//				stmt6.setInt(1, idFicha);
//				stmt6.setInt(2, idMatricula);
//
//				float promedioTestSuma = 0;
//				int totalTest = 0;
//
//				float promedioTrabajoSuma = 0;
//				int totalTrabajo = 0;
//
//				float promedioLaboratorioSuma = 0;
//				int totalLaboratorio = 0;
//
//				float promedioGrupalSuma = 0;
//				int totalGrupal = 0;
//
//				while (result.next()) {
//					// TEST ******
//					stmt1.setInt(3, result.getInt("IDUNIDAD"));
//					subResult =  stmt1.executeQuery();
//					test = new MatriculaGTest();
//					if (subResult.next()) {
//						test.setNotaMinima(subResult.getString("MINIMO"));
//						test.setNotaMaxima(subResult.getString("MAXIMO"));
//						test.setNotaPromedio(subResult.getString("PROMEDIO"));
//						test.setVeces(subResult.getInt("VECES"));
//						promedioTestSuma += subResult.getFloat("PROMEDIO");
//						totalTest++;
//					}
//					recursoTest.add(test);
//
//					// INDIVIDUAL ******
//					stmt2.setInt(5, result.getInt("IDUNIDAD"));
//					subResult =  stmt2.executeQuery();
//					trabajo = new MatriculaGTrabajo();
//					if (subResult.next()) {
//						if(subResult.getString("FECHA_ACTIVACION") != null){
//							if (subResult.getString("NOTA") == null){
//								if(subResult.getInt("ENTREGADO") == 0)
//									trabajo.setEstado(2);
//								else{
//									if(subResult.getInt("EXPIRADO") == 0){
//										trabajo.setEstado(3);
//									}else{
//										trabajo.setEstado(1);
//									}
//								}
//							}else{
//								trabajo.setNota(subResult.getString("NOTA"));
//								if(subResult.getFloat("NOTA") >= 10.5)
//									trabajo.setEstado(5);
//								else
//									trabajo.setEstado(6);
//							}
//							promedioTrabajoSuma += subResult.getFloat("NOTA");
//							totalTrabajo++; //Promediar solo con trabajos publicados
//						}else{
//							trabajo.setEstado(4);
//						}
//						//totalTrabajo++; //promediar con trabajos publicados y no publicados
//						recursoTrabajo.add(trabajo); //no cosiderar unidades sin trabajos (estilo actual: trabajos por filas)
//					}
//					//recursoTrabajo.add(trabajo); //Considerar unidades sin trabajos (estilo: trabajos por columna)
//
//					// LABORATORIO ******
//					stmt3.setInt(5, result.getInt("IDUNIDAD"));
//					subResult =  stmt3.executeQuery();
//					laboratorio = new MatriculaGLaboratorio();
//					if (subResult.next()) {
//						if(subResult.getString("FECHA_ACTIVACION") != null){
//							if (subResult.getString("NOTA") == null){
//								if(subResult.getInt("ENTREGADO") == 0)
//									laboratorio.setEstado(2);
//								else{
//									if(subResult.getInt("EXPIRADO") == 0){
//										laboratorio.setEstado(3);
//									}else{
//										laboratorio.setEstado(1);
//									}
//								}
//							}else{
//								laboratorio.setNota(subResult.getString("NOTA"));
//								if(subResult.getFloat("NOTA") >= 10.5)
//									laboratorio.setEstado(5);
//								else
//									laboratorio.setEstado(6);
//							}
//							promedioLaboratorioSuma += subResult.getFloat("NOTA");
//							totalLaboratorio++;
//						}else{
//							laboratorio.setEstado(4);
//						}
//					}
//					recursoLaboratorio.add(laboratorio);
//
//					// GRUPAL ******
//					stmt4.setInt(7, result.getInt("IDUNIDAD"));
//					subResult =  stmt4.executeQuery();
//					grupal = new MatriculaGTrabajoGrupal();
//					if (subResult.next()) {
//						if(subResult.getString("FECHA_ACTIVACION") != null){
//							if (subResult.getString("NOTA") == null){
//								if(subResult.getInt("ENTREGADO") == 0)
//									grupal.setEstado(2);
//								else{
//									if(subResult.getInt("EXPIRADO") == 0){
//										grupal.setEstado(3);
//									}else{
//										grupal.setEstado(1);
//									}
//								}
//							}else{
//								grupal.setNota(subResult.getString("NOTA"));
//								if(subResult.getFloat("NOTA") >= 10.5)
//									grupal.setEstado(5);
//								else
//									grupal.setEstado(6);
//							}
//							grupal.setIntervencion(subResult.getInt("DEBATE_HECHAS"));
//							grupal.setIntervencionTotal(subResult.getInt("DEBATE_TOTAL"));
//							promedioGrupalSuma += subResult.getFloat("NOTA");
//							totalGrupal++;
//						}else{
//							grupal.setEstado(4);
//						}
//						recursoTrabajoGrupal.add(grupal);
//					}
//					//recursoTrabajoGrupal.add(grupal);
//
//					// DIALOGO ******
//					stmt5.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmt5.executeQuery();
//					dialogo = new MatriculaGDialogo();
//					if (subResult.next()) {
//						dialogo.setIntervencionTotal(subResult.getInt("TOTAL"));
//					}
//					stmt6.setInt(3, result.getInt("IDUNIDAD"));
//					subResult =  stmt6.executeQuery();
//					if (subResult.next()) {
//						dialogo.setIntervencion(subResult.getInt("HECHAS"));
//					}
//					recursoDialogo.add(dialogo);
//				}
//
//				// Promedio
//
//				query = "SELECT IDRECURSO,PESO FROM CV_FICHA_RECURSO "
//						+ "WHERE IDFICHA=?";
//				stmt =  cons.prepareStatement(query);
//				stmt.setInt(1, idFicha);
//				result =  stmt.executeQuery();
//				int numRecurso = 0;
//				while (result.next()) {
//					switch (result.getInt("IDRECURSO")) {
//					case 7:
//						notaTest = new RecursoNotaParcial();
//						float nota = 0;
//						int peso = 0;
//						float parcial = 0;
//						if (totalTest != 0) {
//							if (promedioTestSuma != 0)
//								nota = promedioTestSuma / totalTest;
//							peso = result.getInt("PESO");
//							notaTest.setPeso(peso);
//							// if(peso == 0)
//							// peso = 100;
//							parcial = (nota * peso) / 100;
//							notaTest.setNota(String.valueOf((float) Math.round((nota) * 100) / 100));
//							parcial = (float) Math.round((parcial) * 100) / 100;
//							notaTest.setPromedioParcial(String.valueOf(parcial));
//							notaFinal += parcial;
//							numRecurso++;
//						}
//						break;
//
//					case 5:
//						notaTrabajo = new RecursoNotaParcial();
//						if (totalTrabajo != 0) {
//							if (promedioTrabajoSuma != 0)
//								nota = promedioTrabajoSuma / totalTrabajo;
//							else
//								nota = 0;
//							peso = result.getInt("PESO");
//							notaTrabajo.setPeso(peso);
//							// if(peso == 0)
//							// peso = 100;
//							parcial = (nota * peso) / 100;
//							notaTrabajo.setNota(String.valueOf((float) Math
//									.round((nota) * 100) / 100));
//							parcial = (float) Math.round((parcial) * 100) / 100;
//							notaTrabajo.setPromedioParcial(String.valueOf(parcial));
//							notaFinal += parcial;
//							numRecurso++;
//						}
//						break;
//
//					case 3:
//						notaLaboratorio = new RecursoNotaParcial();
//						if (totalLaboratorio != 0) {
//							if (promedioLaboratorioSuma != 0)
//								nota = promedioLaboratorioSuma
//										/ totalLaboratorio;
//							else
//								nota = 0;
//							peso = result.getInt("PESO");
//							notaLaboratorio.setPeso(peso);
//							// if(peso == 0)
//							// peso = 100;
//							parcial = (nota * peso) / 100;
//							notaLaboratorio.setNota(String.valueOf((float) Math
//									.round((nota) * 100) / 100));
//							parcial = (float) Math.round((parcial) * 100) / 100;
//							notaLaboratorio.setPromedioParcial(String.valueOf(parcial));
//							notaFinal += parcial;
//							numRecurso++;
//						}
//						break;
//
//					case 6:
//						notaGrupal = new RecursoNotaParcial();
//						if (totalGrupal != 0) {
//							if (promedioGrupalSuma != 0)
//								nota = promedioGrupalSuma / totalGrupal;
//							else
//								nota = 0;
//							peso = result.getInt("PESO");
//							notaGrupal.setPeso(peso);
//							// if(peso == 0)
//							// peso = 100;
//							parcial = (nota * peso) / 100;
//							notaGrupal.setNota(String.valueOf((float) Math
//									.round(nota * 100) / 100));
//							parcial = (float) Math.round((parcial) * 100) / 100;
//							notaGrupal.setPromedioParcial(String.valueOf(parcial));
//							notaFinal += parcial;
//							numRecurso++;
//						}
//						break;
//
//					default:
//						break;
//					}
//				}
//
//				reporte.setNotaFinal(String.valueOf(((float)Math
//						.round((notaFinal) * 100)) / 100));
//				
//				reporte.setNotaTest(notaTest);
//				reporte.setNotaTrabajo(notaTrabajo);
//				reporte.setNotaLaboratorio(notaLaboratorio);
//				reporte.setNotaGrupal(notaGrupal);
//
//				reporte.setRecursoTest(recursoTest);
//				reporte.setRecursoTrabajo(recursoTrabajo);
//				reporte.setRecursoLaboratorio(recursoLaboratorio);
//				reporte.setRecursoTrabajoGrupal(recursoTrabajoGrupal);
//				reporte.setRecursoDialogo(recursoDialogo);
//			}
//
//		} catch (SQLException e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} catch (Exception e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} finally {
//			closeResultSet(subResult);
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeStatement(stmt1);
//			closeStatement(stmt2);
//			closeStatement(stmt3);
//			closeStatement(stmt4);
//			closeStatement(stmt5);
//			closeStatement(stmt6);
//			closeConnection(cons);
//		}
//		return reporte;
//	}
//
//	public ReporteDetalle obtenerAuditoria(int idMatricula) throws DAOException {
//		log.info("obtenerAuditoria(int idMatricula)");
//		PreparedStatement stmt = null;
//		PreparedStatement stmtT2 = null;
//		PreparedStatement stmtT3 = null;
//		PreparedStatement stmtT4 = null;
//		PreparedStatement stmtT5 = null;
//
//		PreparedStatement stmtL1 = null;
//		PreparedStatement stmtL2 = null;
//		PreparedStatement stmtL3 = null;
//		PreparedStatement stmtL4 = null;
//		PreparedStatement stmtL5 = null;
//
//		PreparedStatement stmtG1 = null;
//		PreparedStatement stmtG2 = null;
//		PreparedStatement stmtG3 = null;
//		PreparedStatement stmtG4 = null;
//		PreparedStatement stmtG5 = null;
//		PreparedStatement stmtG6 = null;
//		PreparedStatement stmtG7 = null;
//		PreparedStatement stmtG8 = null;
//
//		PreparedStatement stmtD1 = null;
//		PreparedStatement stmtD2 = null;
//
//		ResultSet result = null;
//		ResultSet subResult = null;
//		Connection cons = null;
//
//		Collection<MatriculaGLaboratorio> recursoLaboratorio = new ArrayList<MatriculaGLaboratorio>();
//		Collection<MatriculaGDialogo> recursoDialogo = new ArrayList<MatriculaGDialogo>();
//		Collection<MatriculaGTrabajo> recursoTrabajo = new ArrayList<MatriculaGTrabajo>();
//		Collection<MatriculaGTrabajoGrupal> recursoTrabajoGrupal = new ArrayList<MatriculaGTrabajoGrupal>();
//
//		MatriculaGLaboratorio laboratorio = null;
//		MatriculaGDialogo dialogo = null;
//		MatriculaGTrabajo trabajo = null;
//		MatriculaGTrabajoGrupal grupal = null;
//		
//		ReporteDetalle reporte = new ReporteDetalle();
//		MatriculaIngreso mingreso;
//		try {
//			String query = "SELECT  COUNT(*) cantidad,to_char(fecha_ingreso,"
//					+ "'YYYY/MM/DD')||' 12:00:00' fecha_ingreso FROM cv_ingreso WHERE usuario ="
//					+ "(SELECT usuario FROM cv_matricula WHERE idmatricula=?) "
//					+ " AND elemento=? AND fecha_ingreso>=pkg_cv_ficha.fx_cv_ficha_fechainicio(?)"
//					+ " AND fecha_ingreso <= pkg_cv_ficha.fx_cv_ficha_fechafin(?) "
//					+ "and valor=(select idficha from cv_matricula where idmatricula=?) GROUP BY "
//					+ "to_char(fecha_ingreso, 'YYYY/MM/DD') ORDER BY 2";
//			cons =  dataSource.getConnection();
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, idMatricula);
//			stmt.setInt(2, Constante.ELEMENTO_CURSO);
//			stmt.setInt(3, idMatricula);
//			stmt.setInt(4, idMatricula);
//			stmt.setInt(5, idMatricula);
//			result =  stmt.executeQuery();
//			while (result.next()) {
//				mingreso = new MatriculaIngreso();
//				mingreso.setCantidad(result.getInt("cantidad"));
//				mingreso.setFecha(Formato.getDateDeBaseDatos(result
//						.getString("fecha_ingreso")));
//				reporte.getNumeroIngreso().add(mingreso);
//			}
//			query = "select trim(ma.usuario) idusuario,gepe.nomuno,gepe.nomdos,gepe."
//					+ "apepaterno,gepe.apematerno,pkg_cv_com_producto.fx_cv_com_producto_nombre_id(ma.codigo_formacion) nombreformacion,"
//					+ "pkg_cv_com_producto.fx_cv_com_producto_nombre_id(cvfi.codigo_curso) nombrecurso,pkg_cv_ficha.fx_cv_ficha_"
//					+ "fechainicio(ma.idmatricula) feini,pkg_cv_ficha.fx_cv_aula_formasec(ma.idficha)"
//					+ " formasec,pkg_cv_ficha.fx_cv_ficha_fechafin(ma.idmatricula)"
//					+ " fefin,(SELECT  COUNT(*) cantidad FROM cv_ingreso WHERE usuario ="
//					+ "(SELECT usuario FROM cv_matricula WHERE idmatricula=ma.idmatricula) "
//					+ " AND elemento=? AND fecha_ingreso>=pkg_cv_ficha.fx_cv_ficha_fechainicio(ma.idmatricula)"
//					+ " AND fecha_ingreso <= pkg_cv_ficha.fx_cv_ficha_fechafin(ma.idmatricula) "
//					+ "and valor=(select idficha from cv_matricula where idmatricula = ma.idmatricula)) cantidadingreso"
//					+ ",pkg_cv_ficha.fx_cv_ficha_cantdias(ma.idmatricula) cantidadclases,(SELECT "
//					+ "count(U.IDUNIDAD) FROM CV_UNIDAD U,CV_UNIDAD_SILABO US,CV_FICHA_UNIDAD  FU "
//					+ "WHERE US.IDSILABO=FU.IDSILABO AND US.IDUNIDAD=FU.IDUNIDAD AND U.IDUNIDAD=US."
//					+ "IDUNIDAD AND IDFICHA=ma.idficha AND FU.ESTADO=1) cantidadunidad from "
//					+ "cv_matricula ma,seguridad.seg_usuario seus,cv_ficha cvfi,"
//					+ "general.gen_persona gepe where ma.idmatricula=? and ma.usuario=seus"
//					+ ".usuario and gepe.codpersona=seus.codsujeto and cvfi.idficha=ma.idficha";
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, Constante.ELEMENTO_CURSO);
//			stmt.setInt(2, idMatricula);
//			result =  stmt.executeQuery();
//			if (result.next()) {
//				reporte.setCantidadIngreso(result.getInt("cantidadingreso"));
//				reporte.setFecha1(Formato.getDateDeBaseDatos(result
//						.getString("feini")));
//				reporte.setFecha2(Formato.getDateDeBaseDatos(result
//						.getString("fefin")));
//				reporte.setNombreCompleto(Formato.formatoNombreCompletoJSP(
//						result.getString("nomuno"), result.getString("nomdos"),
//						result.getString("apepaterno"), result
//								.getString("apematerno")));
//				reporte.setNombreUsuarioReporte(Formato.formatoTexto(result
//						.getString("nomuno"))
//						+ " "
//						+ Formato.formatoTexto(result.getString("apepaterno")));
//				reporte.setIdUsuario(result.getString("idusuario"));
//				reporte.setNombreCurso(result.getString("nombrecurso"));
//				reporte.setNombreEspecialidad(result
//						.getString("nombreformacion"));
//				reporte.setCantidadClases(result.getInt("cantidadclases"));
//				reporte.setFormacionSeccion(result.getString("formasec"));
//				reporte.setCantidadUnidades(result.getInt("cantidadunidad"));
//			}
//
//			// / NOTAS
//			// *************************************************************
//
//			query = "SELECT IDFICHA FROM CV_MATRICULA WHERE IDMATRICULA=?";
//
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, idMatricula);
//			result =  stmt.executeQuery();
//			
//			if (result.next()) {
//
//				int idFicha = result.getInt("IDFICHA");
//
//				query = "SELECT US.IDUNIDAD FROM CV_FICHA_UNIDAD FU, CV_UNIDAD_SILABO US  "
//						+ "WHERE US.IDSILABO=FU.IDSILABO AND US.IDUNIDAD=FU.IDUNIDAD "
//						+ "AND FU.IDFICHA=? AND FU.ESTADO=1 ORDER BY US.INDICE";
//
//				stmt =  cons.prepareStatement(query);
//				stmt.setInt(1, idFicha);
//				result =  stmt.executeQuery();
//				
//				// INDIVIDUAL ******
//				query = "SELECT COUNT(*) TOTAL "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_TRABAJO_INDIVIDUAL_NOTA N, CV_TRABAJO_INDIVIDUAL T "
//						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
//						+ "AND T.IDTRABAJO=N.IDTRABAJO "
//						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";
//
//				stmt =  cons.prepareStatement(query);
//				stmt.setInt(1, idFicha);
//
//				query = "SELECT T.FECHA_ACTIVACION, T.FECHA_ENTREGA "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_TRABAJO_INDIVIDUAL T "
//						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
//						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";
//
//				stmtT2 =  cons.prepareStatement(query);
//				stmtT2.setInt(1, idFicha);
//
//				query = "SELECT COUNT(*) CALIFICADOS "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_TRABAJO_INDIVIDUAL_NOTA N, CV_TRABAJO_INDIVIDUAL T "
//						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
//						+ "AND T.IDTRABAJO=N.IDTRABAJO AND N.NOTA IS NOT NULL "
//						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";
//
//				stmtT3 =  cons.prepareStatement(query);
//				stmtT3.setInt(1, idFicha);
//
//				query = "SELECT COUNT(DISTINCT I.IDMATRICULA) ENTREGADOS "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_TRABAJO_INDIVIDUAL_INTER I, CV_TRABAJO_INDIVIDUAL T "
//						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
//						+ "AND T.IDTRABAJO=I.IDTRABAJO AND I.IDMATRICULA=I.IDMATRICULA_ENVIO AND I.ARCHIVO_NOMBRE IS NOT NULL "
//						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";
//
//				stmtT4 =  cons.prepareStatement(query);
//				stmtT4.setInt(1, idFicha);
//
//				query = "SELECT COUNT(*) EXPIRADOS "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_TRABAJO_INDIVIDUAL_INTER I, CV_TRABAJO_INDIVIDUAL T "
//						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
//						+ "AND T.IDTRABAJO=I.IDTRABAJO AND T.FECHA_ENTREGA - I.FECHA_CREACION <0 "
//						+ "AND I.INTERACCION=(SELECT MIN(INTERACCION) FROM CV_TRABAJO_INDIVIDUAL_INTER "
//						+ "WHERE IDTRABAJO=I.IDTRABAJO AND IDMATRICULA=I.IDMATRICULA AND IDMATRICULA_ENVIO=I.IDMATRICULA AND ARCHIVO_NOMBRE IS NOT NULL) "
//						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";
//
//				stmtT5 =  cons.prepareStatement(query);
//				stmtT5.setInt(1, idFicha);
//
//				// LABORATORIO ******
//				query = "SELECT COUNT(*) TOTAL "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_LABORATORIO_NOTA N, CV_LABORATORIO T "
//						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
//						+ "AND T.IDTRABAJO=N.IDTRABAJO "
//						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";
//
//				stmtL1 =  cons.prepareStatement(query);
//				stmtL1.setInt(1, idFicha);
//
//				query = "SELECT T.FECHA_ACTIVACION, T.FECHA_ENTREGA "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_LABORATORIO T "
//						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
//						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";
//
//				stmtL2 =  cons.prepareStatement(query);
//				stmtL2.setInt(1, idFicha);
//
//				query = "SELECT COUNT(*) CALIFICADOS "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_LABORATORIO_NOTA N, CV_LABORATORIO T "
//						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
//						+ "AND T.IDTRABAJO=N.IDTRABAJO AND N.NOTA IS NOT NULL "
//						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";
//
//				stmtL3 =  cons.prepareStatement(query);
//				stmtL3.setInt(1, idFicha);
//
//				query = "SELECT COUNT(DISTINCT I.IDMATRICULA) ENTREGADOS "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_LABORATORIO_INTER I, CV_LABORATORIO T "
//						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
//						+ "AND T.IDTRABAJO=I.IDTRABAJO AND I.IDMATRICULA=I.IDMATRICULA_ENVIO AND I.ARCHIVO_NOMBRE IS NOT NULL "
//						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";
//
//				stmtL4 =  cons.prepareStatement(query);
//				stmtL4.setInt(1, idFicha);
//
//				query = "SELECT COUNT(*) EXPIRADOS "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_LABORATORIO_INTER I, CV_LABORATORIO T "
//						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
//						+ "AND T.IDTRABAJO=I.IDTRABAJO AND T.FECHA_ENTREGA - I.FECHA_CREACION <0 "
//						+ "AND I.INTERACCION=(SELECT MIN(INTERACCION) FROM CV_LABORATORIO_INTER "
//						+ "WHERE IDTRABAJO=I.IDTRABAJO AND IDMATRICULA=I.IDMATRICULA AND IDMATRICULA_ENVIO=I.IDMATRICULA AND ARCHIVO_NOMBRE IS NOT NULL) "
//						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";
//
//				stmtL5 =  cons.prepareStatement(query);
//				stmtL5.setInt(1, idFicha);
//
//				// GRUPAL ******
//				query = "SELECT COUNT(*) TOTAL "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_TRABAJO_GRUPAL T, CV_GRUPO_TRABAJO G "
//						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
//						+ "AND T.IDTRABAJO=G.IDTRABAJO AND G.ESTADO='1' "
//						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";
//
//				stmtG1 =  cons.prepareStatement(query);
//				stmtG1.setInt(1, idFicha);
//
//				query = "SELECT T.FECHA_ACTIVACION, T.FECHA_ENTREGA "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_TRABAJO_GRUPAL T "
//						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
//						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";
//
//				stmtG2 =  cons.prepareStatement(query);
//				stmtG2.setInt(1, idFicha);
//
//				query = "SELECT COUNT(N.NOTA_PROMEDIO) CALIFICADOS, COUNT(*) TOTAL "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_TRABAJO_GRUPAL T, CV_GRUPO_TRABAJO G, CV_GRUPO_TRABAJO_MATRICULA N "
//						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
//						+ "AND T.IDTRABAJO=G.IDTRABAJO AND G.IDTRABAJO=N.IDTRABAJO AND G.IDGRUPO=N.IDGRUPO AND G.ESTADO='1' "
//						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";
//
//				stmtG3 =  cons.prepareStatement(query);
//				stmtG3.setInt(1, idFicha);
//
//				query = "SELECT COUNT(DISTINCT M.IDGRUPO) ENTREGADOS "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_TRABAJO_GRUPAL T, CV_GRUPO_TRABAJO G, CV_GRUPO_TRABAJO_MSG M "
//						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
//						+ "AND T.IDTRABAJO=G.IDTRABAJO AND G.IDTRABAJO=M.IDTRABAJO AND G.IDGRUPO=M.IDGRUPO AND G.ESTADO='1' AND M.ARCHIVO_NOMBRE IS NOT NULL "
//						+ "AND IDMATRICULA_EMISOR IN (SELECT IDMATRICULA FROM CV_GRUPO_TRABAJO_MATRICULA WHERE IDTRABAJO=G.IDTRABAJO AND IDGRUPO=G.IDGRUPO) "
//						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";
//
//				stmtG4 =  cons.prepareStatement(query);
//				stmtG4.setInt(1, idFicha);
//
//				query = "SELECT COUNT(*) EXPIRADOS "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_TRABAJO_GRUPAL T, CV_GRUPO_TRABAJO G, CV_GRUPO_TRABAJO_MSG M "
//						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
//						+ "AND T.IDTRABAJO=G.IDTRABAJO AND G.IDTRABAJO=M.IDTRABAJO AND G.IDGRUPO=M.IDGRUPO AND G.ESTADO='1' AND T.FECHA_ENTREGA - M.FECHA_CREACION <0 "
//						+ "AND M.IDMENSAJE = (SELECT MIN(IDMENSAJE) FROM CV_GRUPO_TRABAJO_MSG WHERE IDTRABAJO=G.IDTRABAJO AND IDGRUPO=G.IDGRUPO "
//						+ "AND M.ARCHIVO_NOMBRE IS NOT NULL AND IDMATRICULA_EMISOR IN (SELECT IDMATRICULA FROM CV_GRUPO_TRABAJO_MATRICULA WHERE IDTRABAJO=G.IDTRABAJO AND IDGRUPO=G.IDGRUPO)) "
//						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";
//
//				stmtG5 =  cons.prepareStatement(query);
//				stmtG5.setInt(1, idFicha);
//
//				query = "SELECT COUNT(*) ASIGNADOS "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_TRABAJO_GRUPAL T, CV_GRUPO_TRABAJO G "
//						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
//						+ "AND T.IDTRABAJO=G.IDTRABAJO AND G.ESTADO='1' AND G.ARCHIVO_NOMBRE IS NOT NULL "
//						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";
//
//				stmtG6 =  cons.prepareStatement(query);
//				stmtG6.setInt(1, idFicha);
//
//				query = "SELECT COUNT(*) DEBATE_TOTAL "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_DEBATE D, CV_TRABAJO_GRUPAL T, CV_GRUPO_TRABAJO G "
//						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1'  "
//						+ "AND T.IDTRABAJO=G.IDTRABAJO AND G.IDTRABAJO=D.IDTRABAJO AND G.IDGRUPO=D.IDGRUPO AND D.ESTADO='1' AND G.ESTADO='1' "
//						+ "AND R.IDFICHA=? AND R.IDUNIDAD=?";
//
//				stmtG7 =  cons.prepareStatement(query);
//				stmtG7.setInt(1, idFicha);
//
//				query = "SELECT COUNT(*) DEBATE_HECHAS "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_DEBATE D, CV_TRABAJO_GRUPAL T, CV_GRUPO_TRABAJO G "
//						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1'  "
//						+ "AND T.IDTRABAJO=G.IDTRABAJO AND G.IDTRABAJO=D.IDTRABAJO AND G.IDGRUPO=D.IDGRUPO AND D.ESTADO='1' AND G.ESTADO='1' "
//						+ "AND R.IDFICHA=? AND IDMATRICULA=? AND R.IDUNIDAD=?";
//
//				stmtG8 =  cons.prepareStatement(query);
//				stmtG8.setInt(1, idFicha);
//				stmtG8.setInt(2, idMatricula);
//
//				// DIALOGO ******
//				query = "SELECT COUNT(*) TOTAL "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_DIALOGO D "
//						+ "WHERE R.IDFICHA=D.IDFICHA AND R.IDUNIDAD=D.IDUNIDAD AND R.IDRECURSO=D.IDRECURSO  "
//						+ "AND R.ESTADO='1' AND R.CALIFICA='1' AND D.ESTADO='1' AND R.IDFICHA=? AND R.IDUNIDAD=?";
//
//				stmtD1 =  cons.prepareStatement(query);
//				stmtD1.setInt(1, idFicha);
//
//				query = "SELECT COUNT(*) HECHAS "
//						+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_DIALOGO D "
//						+ "WHERE R.IDFICHA=D.IDFICHA AND R.IDUNIDAD=D.IDUNIDAD AND R.IDRECURSO=D.IDRECURSO  "
//						+ "AND R.ESTADO='1' AND R.CALIFICA='1' AND D.ESTADO='1' AND R.IDFICHA=? AND D.IDMATRICULA=? AND R.IDUNIDAD=?";
//
//				stmtD2 =  cons.prepareStatement(query);
//				stmtD2.setInt(1, idFicha);
//				stmtD2.setInt(2, idMatricula);
//				
//				// EL BUCLE MALDITO **********
//				while (result.next()) {
//
//					// INDIVIDUAL ******
//					trabajo = new MatriculaGTrabajo();
//
//					stmt.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmt.executeQuery();
//					if (subResult.next()) {
//						trabajo.setTotal(subResult.getInt("TOTAL"));
//					}
//
//					stmtT2.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmtT2.executeQuery();
//					if (subResult.next()) {
//						if (subResult.getString("FECHA_ACTIVACION") != null) {
//							trabajo.setPublicado("Pub");
//							// trabajo.setFechaActivacion(Formato
//							// .getStringDeDate(Formato
//							// .getDateDeBaseDatos(subResult
//							// .getString("FECHA_ACTIVACION"))));
//							// trabajo
//							// .setFechaEntrega(Formato
//							// .getStringDeDate(Formato
//							// .getDateDeBaseDatos(subResult
//							// .getString("FECHA_ENTREGA"))));
//						} else {
//							trabajo.setPublicado("NP");
//						}
//					}
//
//					stmtT3.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmtT3.executeQuery();
//					if (subResult.next()) {
//						trabajo.setCalificados(subResult.getInt("CALIFICADOS"));
//					}
//
//					stmtT4.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmtT4.executeQuery();
//					if (subResult.next()) {
//						trabajo.setEntregados(subResult.getInt("ENTREGADOS"));
//					}
//
//					stmtT5.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmtT5.executeQuery();
//					if (subResult.next()) {
//						trabajo.setExpirados(subResult.getInt("EXPIRADOS"));
//					}
//
//					recursoTrabajo.add(trabajo);
//
//					// LABORATORIO ******
//					laboratorio = new MatriculaGLaboratorio();
//
//					stmtL1.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmtL1.executeQuery();
//					if (subResult.next()) {
//						laboratorio.setTotal(subResult.getInt("TOTAL"));
//					}
//
//					stmtL2.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmtL2.executeQuery();
//					if (subResult.next()) {
//						if (subResult.getString("FECHA_ACTIVACION") != null) {
//							laboratorio.setPublicado("Pub");
//							// laboratorio
//							// .setFechaActivacion(Formato
//							// .getStringDeDate(Formato
//							// .getDateDeBaseDatos(subResult
//							// .getString("FECHA_ACTIVACION"))));
//							// laboratorio
//							// .setFechaEntrega(Formato
//							// .getStringDeDate(Formato
//							// .getDateDeBaseDatos(subResult
//							// .getString("FECHA_ENTREGA"))));
//						} else {
//							laboratorio.setPublicado("NP");
//						}
//					}
//
//					stmtL3.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmtL3.executeQuery();
//					if (subResult.next()) {
//						laboratorio.setCalificados(subResult
//								.getInt("CALIFICADOS"));
//					}
//
//					stmtL4.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmtL4.executeQuery();
//					if (subResult.next()) {
//						laboratorio.setEntregados(subResult
//								.getInt("ENTREGADOS"));
//					}
//
//					stmtL5.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmtL5.executeQuery();
//					if (subResult.next()) {
//						laboratorio.setExpirados(subResult.getInt("EXPIRADOS"));
//					}
//
//					recursoLaboratorio.add(laboratorio);
//
//					// GRUPAL ******
//					grupal = new MatriculaGTrabajoGrupal();
//
//					stmtG1.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmtG1.executeQuery();
//					if (subResult.next()) {
//						grupal.setTotal(subResult.getInt("TOTAL"));
//					}
//
//					stmtG2.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmtG2.executeQuery();
//					if (subResult.next()) {
//						if (subResult.getString("FECHA_ACTIVACION") != null) {
//							grupal.setPublicado("Pub");
//							// grupal
//							// .setFechaActivacion(Formato
//							// .getStringDeDate(Formato
//							// .getDateDeBaseDatos(subResult
//							// .getString("FECHA_ACTIVACION"))));
//							// grupal
//							// .setFechaEntrega(Formato
//							// .getStringDeDate(Formato
//							// .getDateDeBaseDatos(subResult
//							// .getString("FECHA_ENTREGA"))));
//						} else {
//							grupal.setPublicado("NP");
//						}
//					}
//
//					stmtG3.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmtG3.executeQuery();
//					if (subResult.next()) {
//						grupal.setCalificados(subResult.getInt("CALIFICADOS"));
//						grupal.setEstudianteTotal(subResult.getInt("TOTAL"));
//					}
//
//					stmtG4.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmtG4.executeQuery();
//					if (subResult.next()) {
//						grupal.setEntregados(subResult.getInt("ENTREGADOS"));
//					}
//
//					stmtG5.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmtG5.executeQuery();
//					if (subResult.next()) {
//						grupal.setExpirados(subResult.getInt("EXPIRADOS"));
//					}
//
//					stmtG6.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmtG6.executeQuery();
//					if (subResult.next()) {
//						grupal.setAsignados(subResult.getInt("ASIGNADOS"));
//					}
//
//					stmtG7.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmtG7.executeQuery();
//					if (subResult.next()) {
//						grupal.setIntervencionTotal(subResult
//								.getInt("DEBATE_TOTAL"));
//					}
//
//					stmtG8.setInt(3, result.getInt("IDUNIDAD"));
//					subResult =  stmtG8.executeQuery();
//					if (subResult.next()) {
//						grupal.setIntervencion(subResult
//								.getInt("DEBATE_HECHAS"));
//					}
//
//					recursoTrabajoGrupal.add(grupal);
//
//					// DIALOGO ******
//					dialogo = new MatriculaGDialogo();
//
//					stmtD1.setInt(2, result.getInt("IDUNIDAD"));
//					subResult =  stmtD1.executeQuery();
//
//					if (subResult.next()) {
//						dialogo.setIntervencionTotal(subResult.getInt("TOTAL"));
//					}
//					stmtD2.setInt(3, result.getInt("IDUNIDAD"));
//					subResult =  stmtD2.executeQuery();
//					if (subResult.next()) {
//						dialogo.setIntervencion(subResult.getInt("HECHAS"));
//					}
//					recursoDialogo.add(dialogo);
//				}
//
//				reporte.setRecursoTrabajo(recursoTrabajo);
//				reporte.setRecursoLaboratorio(recursoLaboratorio);
//				reporte.setRecursoTrabajoGrupal(recursoTrabajoGrupal);
//				reporte.setRecursoDialogo(recursoDialogo);
//
//				// Avisos y Lecturas
//
//				query = "SELECT (SELECT COUNT(*) FROM CV_MATRICULA M, CV_PUBLICACION_FICHA P "
//						+ "WHERE TRIM(M.USUARIO)=P.USUARIO AND M.IDFICHA=P.IDFICHA AND P.IDHERRAMIENTA=1 AND M.ELIMINADO=0 AND P.ESTADO=0 "
//						+ "AND P.IDFICHA=? AND IDMATRICULA=?) AVISOS, "
//						+ "(SELECT COUNT(*) FROM CV_MATRICULA M, CV_PUBLICACION_FICHA P "
//						+ "WHERE TRIM(M.USUARIO)=P.USUARIO AND M.IDFICHA=P.IDFICHA AND P.IDHERRAMIENTA=1 AND M.ELIMINADO=0 AND P.ESTADO=0 "
//						+ "AND P.IDFICHA=?) AVISOSTOTAL,"
//						+ "(SELECT COUNT(*) FROM CV_MATRICULA M, CV_PUBLICACION_FICHA P "
//						+ "WHERE TRIM(M.USUARIO)=P.USUARIO AND M.IDFICHA=P.IDFICHA AND P.IDHERRAMIENTA=2 AND M.ELIMINADO=0 AND P.ESTADO=0 "
//						+ "AND P.IDFICHA=? AND IDMATRICULA=?) LECTURAS,"
//						+ "(SELECT COUNT(*) "
//						+ "FROM CV_MATRICULA M, CV_PUBLICACION_FICHA P "
//						+ "WHERE TRIM(M.USUARIO)=P.USUARIO AND M.IDFICHA=P.IDFICHA AND P.IDHERRAMIENTA=2 AND M.ELIMINADO=0 AND P.ESTADO=0 "
//						+ "AND P.IDFICHA=?) LECTURASTOTAL FROM DUAL";
//				stmt =  cons.prepareStatement(query);
//				stmt.setInt(1, idFicha);
//				stmt.setInt(2, idMatricula);
//				stmt.setInt(3, idFicha);
//				stmt.setInt(4, idFicha);
//				stmt.setInt(5, idMatricula);
//				stmt.setInt(6, idFicha);
//				result =  stmt.executeQuery();
//
//				if (result.next()) {
//					reporte.setAlerta(result.getInt("AVISOS"));
//					reporte.setTotalAlerta(result.getInt("AVISOSTOTAL"));
//					reporte.setLectura(result.getInt("LECTURAS"));
//					reporte.setTotalLectura(result.getInt("LECTURASTOTAL"));
//				}
//
//			}
//
//		} catch (SQLException e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} catch (Exception e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} finally {
//			closeResultSet(subResult);
//			closeResultSet(result);
//			closeStatement(stmt);
//
//			closeStatement(stmtT2);
//			closeStatement(stmtT3);
//			closeStatement(stmtT4);
//			closeStatement(stmtT5);
//			closeStatement(stmtL1);
//			closeStatement(stmtL2);
//			closeStatement(stmtL3);
//			closeStatement(stmtL4);
//			closeStatement(stmtL5);
//			closeStatement(stmtG1);
//			closeStatement(stmtG2);
//			closeStatement(stmtG3);
//			closeStatement(stmtG4);
//			closeStatement(stmtG5);
//			closeStatement(stmtG6);
//			closeStatement(stmtG7);
//			closeStatement(stmtG8);
//			closeStatement(stmtD1);
//			closeStatement(stmtD2);
//			closeConnection(cons);
//		}
//		return reporte;
//	}
//
//	public Reporte obtenerReporte(int idFicha, int idUnidad, boolean tipo,
//			boolean pertenece, Usuario usuario) throws DAOException {
//		log.info("obtenerReporte(int idFicha,int idUnidad,boolean tipo, Usuario usuario)");
//		PreparedStatement stmt = null;
//		PreparedStatement stmt1 = null;
//		PreparedStatement stmt2 = null;
//		PreparedStatement stmt3 = null;
//		PreparedStatement stmt4 = null;
//		PreparedStatement stmt5 = null;
//		PreparedStatement stmt6 = null;
//		ResultSet result = null;
//		ResultSet subResult = null;
//		Connection cons = null;
//		Reporte reporte = new Reporte();
//		UsuarioReporte usuarioReporte = null;
//		try {
//			String query = "SELECT CVMA.IDMATRICULA,GEPE.APEPATERNO,GEPE.APEMATERNO,GEPE.NOMUNO,GEPE.NOMDOS,"
//					+ "PKG_CV_UTIL.FX_CV_STRING_NUMBER(CVMA.SECCION) SECCION,pkg_cv_com_producto."
//					+ "fx_cv_com_producto_nombre_id(CVMA.CODIGO_FORMACION) ESPECIALIDAD "
//					+ "FROM CV_MATRICULA CVMA,SEGURIDAD.SEG_USUARIO SEUS,GENERAL.GEN_PERSONA GEPE "
//					+ "WHERE SEUS.USUARIO=CVMA.USUARIO AND SEUS.CODSUJETO=GEPE.CODPERSONA "
//					+ "AND CVMA.IDFICHA=? AND CVMA.IDROL=? AND CVMA.ELIMINADO='0' "
//					+ "ORDER BY APEPATERNO,APEMATERNO,NOMUNO,NOMDOS,SECCION";
//			
//			cons =  dataSource.getConnection();
//			stmt =  cons.prepareStatement(query);
//			if (!pertenece) {
//				query = "SELECT CVMA.IDMATRICULA,GEPE.APEPATERNO,GEPE.APEMATERNO,GEPE.NOMUNO,GEPE.NOMDOS,"
//						+ "PKG_CV_UTIL.FX_CV_STRING_NUMBER(CVMA.SECCION) SECCION,"
//						+ "pkg_cv_com_producto.fx_cv_com_producto_nombre_id(CVMA.CODIGO_FORMACION) ESPECIALIDAD "
//						+ "FROM CV_MATRICULA CVMA,SEGURIDAD.SEG_USUARIO SEUS,GENERAL.GEN_PERSONA GEPE "
//						+ "WHERE SEUS.USUARIO=CVMA.USUARIO AND SEUS.CODSUJETO=GEPE.CODPERSONA "
//						+ "AND IDFICHA=? AND TRIM(CVMA.USUARIO)=TRIM(?) AND CVMA.ELIMINADO='0' "
//						+ "ORDER BY APEPATERNO,APEMATERNO,NOMUNO,NOMDOS,SECCION";
//				stmt1 =  cons.prepareStatement(query);
//				stmt1.setInt(1, idFicha);
//				stmt1.setString(2, usuario.getIdUsuario());
//				result =  stmt1.executeQuery();
//				while (result.next()) {
//					usuarioReporte = new UsuarioReporte();
//					usuarioReporte.setIdMatricula(result
//							.getString("idmatricula"));
//					usuarioReporte.setApepaterno(Formato.formatoTexto(result
//							.getString("APEPATERNO")));
//					usuarioReporte.setApematerno(Formato.formatoTexto(result
//							.getString("APEMATERNO")));
//					usuarioReporte.setNombre1(Formato.formatoTexto(result
//							.getString("NOMUNO")));
//					usuarioReporte.setNombre2(Formato.formatoTexto(result
//							.getString("NOMDOS")));
//					usuarioReporte.setSeccion(Formato.formatoTexto(result
//							.getString("SECCION")));
//					usuarioReporte.setEspecialidad(result
//							.getString("especialidad"));
//					reporte.getUsuariosResponsables().add(usuarioReporte);
//				}
//			} else {
//				stmt.setInt(1, idFicha);
//				stmt.setInt(2, Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE);
//				result =  stmt.executeQuery();
//				while (result.next()) {
//					usuarioReporte = new UsuarioReporte();
//					usuarioReporte.setIdMatricula(result
//							.getString("idmatricula"));
//					usuarioReporte.setApepaterno(Formato.formatoTexto(result
//							.getString("APEPATERNO")));
//					usuarioReporte.setApematerno(Formato.formatoTexto(result
//							.getString("APEMATERNO")));
//					usuarioReporte.setNombre1(Formato.formatoTexto(result
//							.getString("NOMUNO")));
//					usuarioReporte.setNombre2(Formato.formatoTexto(result
//							.getString("NOMDOS")));
//					usuarioReporte.setSeccion(Formato.formatoTexto(result
//							.getString("SECCION")));
//					usuarioReporte.setEspecialidad(result
//							.getString("especialidad"));
//					reporte.getUsuariosResponsables().add(usuarioReporte);
//				}
//			}
//			stmt.setInt(1, idFicha);
//			stmt.setInt(2, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE);
//			result =  stmt.executeQuery();
//
//			// LABORATORIO ******
//			query = "SELECT ROUND(SUM(NVL(N.NOTA,0)*R.PESO)/(SUM(R.PESO)),2) PROMEDIO,COUNT(NOTA) HECHAS,COUNT(*) TOTAL "
//					+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_LABORATORIO T, CV_LABORATORIO_NOTA N "
//					+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND T.IDTRABAJO=N.IDTRABAJO "
//					+ "AND R.ESTADO='1' AND R.CALIFICA='1' AND R.IDFICHA=? "
//					+ "GROUP BY N.IDMATRICULA HAVING N.IDMATRICULA=?";
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, idFicha);
//
//			// DIALOGO ******
//			query = "SELECT COUNT(*) HECHAS "
//					+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_DIALOGO D "
//					+ "WHERE R.IDFICHA=D.IDFICHA AND R.IDUNIDAD=D.IDUNIDAD AND R.IDRECURSO=D.IDRECURSO "
//					+ "AND R.ESTADO='1' AND R.CALIFICA='1' AND R.IDFICHA=? "
//					+ "GROUP BY D.IDMATRICULA HAVING D.IDMATRICULA=?";
//			stmt1 =  cons.prepareStatement(query);
//			stmt1.setInt(1, idFicha);
//
//			// INDIVIDUAL ******
//			query = "SELECT ROUND(SUM(NVL(N.NOTA,0)*R.PESO)/(SUM(R.PESO)),2) PROMEDIO,COUNT(NOTA) HECHAS,COUNT(*) TOTAL "
//					+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_TRABAJO_INDIVIDUAL T, CV_TRABAJO_INDIVIDUAL_NOTA N "
//					+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND T.IDTRABAJO=N.IDTRABAJO "
//					+ "AND R.ESTADO='1' AND R.CALIFICA='1' AND R.IDFICHA=? "
//					+ "GROUP BY N.IDMATRICULA HAVING N.IDMATRICULA=?";
//			stmt2 =  cons.prepareStatement(query);
//			stmt2.setInt(1, idFicha);
//
//			// GRUPAL ******
//			query = "SELECT ROUND(SUM(NVL(N.NOTA_PROMEDIO,0)*R.PESO)/(SUM(R.PESO)),2) PROMEDIO,COUNT(NOTA_PROMEDIO) HECHAS,COUNT(*) TOTAL "
//					+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_TRABAJO_GRUPAL T, CV_GRUPO_TRABAJO G, CV_GRUPO_TRABAJO_MATRICULA N "
//					+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND T.IDTRABAJO=G.IDTRABAJO AND G.IDTRABAJO=N.IDTRABAJO AND G.IDGRUPO=N.IDGRUPO "
//					+ "AND R.ESTADO='1' AND R.CALIFICA='1' AND G.ESTADO='1' AND R.IDFICHA=? "
//					+ "GROUP BY N.IDMATRICULA HAVING N.IDMATRICULA=?";
//			stmt3 =  cons.prepareStatement(query);
//			stmt3.setInt(1, idFicha);
//
//			// TEST ******
//			query = "SELECT ROUND(AVG(NOTA),2) PROMEDIO,COUNT(*) HECHAS FROM " +
//					"(SELECT  NVL((SELECT ROUND(AVG(D.NOTA),2) " +
//					"FROM CV_NOTA_TEST D  " +
//					"WHERE D.IDFICHA=R.IDFICHA AND D.IDRECURSO=R.IDRECURSO AND D.ESTADO='1' AND D.IDMATRICULA=? " +
//					"GROUP BY D.IDUNIDAD HAVING D.IDUNIDAD=R.IDUNIDAD),0) NOTA " +
//					"FROM CV_FICHA_UNIDAD_RECURSO R , CV_FICHA_UNIDAD FU " +
//					"WHERE R.IDFICHA=FU.IDFICHA AND R.IDUNIDAD=FU.IDUNIDAD AND FU.ESTADO=1 AND R.ESTADO='1' AND R.CALIFICA='1' AND R.IDFICHA=? AND R.IDRECURSO=7)";
//			stmt4 =  cons.prepareStatement(query);
//			stmt4.setInt(2, idFicha);
//
//			// TOTAL DIALOGO ******
//			int totalDialogo = 0, totalTest = 0;
//			query = "SELECT COUNT(*) TOTAL "
//					+ "FROM CV_FICHA_UNIDAD_RECURSO R, CV_DIALOGO D "
//					+ "WHERE R.IDFICHA=D.IDFICHA AND R.IDUNIDAD=D.IDUNIDAD AND R.IDRECURSO=D.IDRECURSO "
//					+ "AND R.ESTADO='1' AND R.CALIFICA='1' AND R.IDFICHA=?";
//
//			stmt5 =  cons.prepareStatement(query);
//			stmt5.setInt(1, idFicha);
//			subResult =  stmt5.executeQuery();
//
//			if (subResult.next()) {
//				totalDialogo = subResult.getInt("TOTAL");
//			}
//			// ********
//			query = "SELECT COUNT(*) TOTAL FROM CV_FICHA_UNIDAD_RECURSO "
//					+ "WHERE ESTADO='1' AND CALIFICA='1' AND IDFICHA=? AND IDRECURSO='7'";
//
//			stmt5 =  cons.prepareStatement(query);
//			stmt5.setInt(1, idFicha);
//			subResult =  stmt5.executeQuery();
//
//			if (subResult.next()) {
//				totalTest = subResult.getInt("TOTAL");
//			}
//
//			// PESOS
//			query = "SELECT PESO FROM CV_FICHA_RECURSO WHERE IDFICHA=? AND IDRECURSO=?";
//
//			stmt6 =  cons.prepareStatement(query);
//			stmt6.setInt(1, idFicha);
//
//			float promedio = 0;
//			float nota = 0;
//
//			while (result.next()) {
//				usuarioReporte = new UsuarioReporte();
//				usuarioReporte.setIdMatricula(result.getString("IDMATRICULA"));
//				usuarioReporte.setApepaterno(Formato.formatoTexto(result
//						.getString("APEPATERNO")));
//				usuarioReporte.setApematerno(Formato.formatoTexto(result
//						.getString("APEMATERNO")));
//				usuarioReporte.setNombre1(Formato.formatoTexto(result
//						.getString("NOMUNO")));
//				usuarioReporte.setNombre2(Formato.formatoTexto(result
//						.getString("NOMDOS")));
//				usuarioReporte.setSeccion(Formato.formatoTexto(result
//						.getString("SECCION")));
//				usuarioReporte
//						.setEspecialidad(result.getString("especialidad"));
//
//				// Obtener Notas
//
//				promedio = 0;
//				nota = 0;
//
//				// LABORATORIO ******
//				stmt.setString(2, result.getString("IDMATRICULA"));
//				subResult =  stmt.executeQuery();
//
//				if (subResult.next()) {
//					usuarioReporte.setNotaLaboratorio(subResult
//							.getString("PROMEDIO"));
//					usuarioReporte.setCantidadCompletaLaboratorio(subResult
//							.getInt("HECHAS"));
//					usuarioReporte.setCantidadLaboratorio(subResult
//							.getInt("TOTAL"));
//
//					// PROMEDIO
//					nota = subResult.getFloat("PROMEDIO");
//					stmt6.setString(2, Constante.RECURSO_ID_LABORATORIO);
//					subResult =  stmt6.executeQuery();
//					subResult.next();
//					promedio += nota * subResult.getInt("PESO");
//				}
//
//				// DIALOGO ******
//				stmt1.setString(2, result.getString("IDMATRICULA"));
//				subResult =  stmt1.executeQuery();
//
//				if (subResult.next()) {
//					usuarioReporte.setCantidadCompletaDialogo(subResult
//							.getInt("HECHAS"));
//				}
//				usuarioReporte.setCantidadDialogo(totalDialogo);
//
//				// INDIVIDUAL ******
//				stmt2.setString(2, result.getString("IDMATRICULA"));
//				subResult =  stmt2.executeQuery();
//
//				if (subResult.next()) {
//					usuarioReporte.setNotaTrabajo(subResult
//							.getString("PROMEDIO"));
//					usuarioReporte.setCantidadCompletaTrabajo(subResult
//							.getInt("HECHAS"));
//					usuarioReporte
//							.setCantidadTrabajo(subResult.getInt("TOTAL"));
//					// PROMEDIO
//					nota = subResult.getFloat("PROMEDIO");
//					stmt6.setString(2, Constante.RECURSO_ID_TRABAJO);
//					subResult =  stmt6.executeQuery();
//					subResult.next();
//					promedio += nota * subResult.getInt("PESO");
//				}
//				
//				// GRUPAL ******
//				stmt3.setString(2, result.getString("IDMATRICULA"));
//				subResult =  stmt3.executeQuery();
//
//				if (subResult.next()) {
//					usuarioReporte.setNotaGrupal(subResult
//							.getString("PROMEDIO"));
//					usuarioReporte.setCantidadCompletaGrupal(subResult
//							.getInt("HECHAS"));
//					usuarioReporte.setCantidadGrupal(subResult.getInt("TOTAL"));
//					// PROMEDIO
//					nota = subResult.getFloat("PROMEDIO");
//					stmt6.setString(2, Constante.RECURSO_ID_ACTIVIDAD_GRUPAL);
//					subResult =  stmt6.executeQuery();
//					subResult.next();
//					promedio += nota * subResult.getInt("PESO");
//				}
//
//				// TEST ******
//				stmt4.setString(1, result.getString("IDMATRICULA"));
//				subResult =  stmt4.executeQuery();
//
//				if (subResult.next() && subResult.getString("PROMEDIO") != null) {
//					usuarioReporte.setNotaTest(subResult.getString("PROMEDIO"));
//					usuarioReporte.setCantidadCompletaTest(subResult
//							.getInt("HECHAS"));
//				} else {
//					usuarioReporte.setNotaTest("0");
//				}
//				// PROMEDIO
//				stmt6.setString(2, Constante.RECURSO_ID_TEST);
//				subResult =  stmt6.executeQuery();
//				subResult.next();
//				promedio += Float.parseFloat(usuarioReporte.getNotaTest())
//						* subResult.getInt("PESO");
//				
//				promedio = (promedio / 100);
//
//				promedio = (float) (Math.round(promedio * 100)) / 100;
//
//				usuarioReporte.setPromedio(String.valueOf(promedio));
//				
//				if(promedio>10.5)
//					usuarioReporte.setAprobado(true);
//				else
//					usuarioReporte.setAprobado(false);
//				
//				usuarioReporte.setCantidadTest(totalTest);
//				
//				reporte.getUsuarios().add(usuarioReporte);
//			}
//		} catch (SQLException e) {
//			log.error(e);
//			throw new DAOException(e.toString());
//		} catch (Exception e) {
//			log.error(e);
//			throw new DAOException(e.toString());
//		} finally {
//			closeResultSet(subResult);
//			closeResultSet(result);
//			closeStatement(stmt1);
//			closeStatement(stmt2);
//			closeStatement(stmt3);
//			closeStatement(stmt4);
//			closeStatement(stmt5);
//			closeStatement(stmt6);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//		return reporte;
//	}
//
//	public void modificarPeriodo(String idPeriodo, int idFicha, String usuario)
//			throws DAOException {
//		log.info("modificarPeriodo(String idPeriodo)");
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		Connection cons = null;
//		try {
//			String query = "UPDATE CV_FICHA SET ESTADO_FECHA='0',CODIGO_PERIODO=?,"
//					+ "FECHA_MOD=SYSDATE,USUARIO_MOD=? WHERE IDFICHA=? ";
//			cons =  dataSource.getConnection();
//			stmt =  cons.prepareStatement(query);
//			stmt.setString(1, idPeriodo);
//			stmt.setString(2, usuario);
//			stmt.setInt(3, idFicha);
//			if (1 != stmt.executeUpdate()) {
//				log.error("Error en modificar " + idFicha);
//				throw new DAOException("No culmino");
//			}
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
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//
//	}
//
//	public void modificarFechas(String sede, String fecha1, String fecha2,
//			int diasEdiccion, int diasRevision, int idFicha, String usuario)
//			throws DAOException {
//		log.info("modificarFechas(String idPeriodo)");
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		Connection cons = null;
//		try {
//			String query = "UPDATE CV_FICHA SET ESTADO_FECHA='1',CODIGO_PERIODO='',"
//					+ "FECHA_MOD=SYSDATE,USUARIO_MOD=?,FECHA_INICIO=?,"
//					+ "FECHA_FIN=?,DIAS_EDICION=?,DIAS_REVISION=?,SEDE=?"
//					+ "  WHERE IDFICHA=? ";
//			cons =  dataSource.getConnection();
//			stmt =  cons.prepareStatement(query);
//			stmt.setString(1, usuario);
//			stmt.setString(2, fecha1);
//			stmt.setString(3, fecha2);
//			stmt.setInt(4, diasEdiccion);
//			stmt.setInt(5, diasRevision);
//			stmt.setString(6, sede);
//			stmt.setInt(7, idFicha);
//			if (1 != stmt.executeUpdate()) {
//				log.error("Error en modificar " + idFicha);
//				throw new DAOException("No culmino");
//			}
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
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//
//	}
//
//	public void guardarNoAuditable(int idFicha, int noauditable)
//			throws DAOException {
//		log.info("guardarNoAuditable(int "+idFicha+", int "+noauditable+")");
//		PreparedStatement stmt = null;
//		Connection cons = null;
//		try {
//			String query = "UPDATE CV_FICHA SET NOAUDITABLE=? WHERE IDFICHA=?";
//			cons =  dataSource.getConnection();
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, noauditable);
//			stmt.setInt(2, idFicha);
//			if (1 != stmt.executeUpdate()) {
//				log.error("Error en guardarNoAuditable " + idFicha);
//				throw new DAOException("No culmino");
//			}
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
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//
//	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
