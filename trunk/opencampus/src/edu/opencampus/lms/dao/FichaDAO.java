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
import edu.opencampus.lms.modelo.Ficha;
import edu.opencampus.lms.modelo.Jerarquia;
import edu.opencampus.lms.modelo.Matricula;
import edu.opencampus.lms.modelo.Periodo;
import edu.opencampus.lms.modelo.Silabo;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.modelo.aulavirtual.MatriculaRol;
import edu.opencampus.lms.modelo.usuario.Rol;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;

public class FichaDAO extends BaseDAO {

	protected DataSource dataSource;

	public Integer crear(AulaVirtual ficha)throws DAOException {
		log.info("crear(AulaVirtual ficha) ");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Integer id = null;
		try {
			
			String query = "insert into cv_ficha (idperiodo, idcurso, idSilabo, estado, creado_en, creado_por, modificado_en, modificado_por) " +
					"values(?,?,?,1,now(),?,now(),?)";
			//** values(?,?,?,-->1<--,now(),?,now(),?) poner estado en 0 y luego en el buscar activar uno por uno
			cons = dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt =  cons.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, ficha.getPeriodo().getIdPeriodo());
			stmt.setInt(2,ficha.getCurso().getIdCurso());
			stmt.setInt(3,ficha.getSilabo().getIdSilabo());
			stmt.setString(4, ficha.getUsuarioCreacion());
			stmt.setString(5, ficha.getUsuarioCreacion());
			
			log.info("Insertando en la tabla cv_ficha ...");
			if (1 != stmt.executeUpdate()) {
				log.error("Error en insert into cv_ficha");
				throw new DAOException("No culmino");
			}
			log.info("Operacion exitosa en la tabla cv_ficha.");
			
			result = stmt.getGeneratedKeys();
			result.next();
			id = result.getInt(1);
			
			query = "insert into cv_ficha_unidad(idficha, idunidad, indice) " +
					"select ?, u.idunidad, u.indice from cv_silabo_unidad u, cv_silabo s " +
					"where u.idsilabo=s.idsilabo and s.estado=1 and s.idsilabo=? order by u.indice";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1,id);
			stmt.setInt(2, ficha.getSilabo().getIdSilabo());
			
			log.info("Insertando en la tabla cv_ficha_unidad ...");
			if (0 == stmt.executeUpdate()) {
				log.error("Error en insert into cv_ficha_unidad");
				throw new DAOException("No culmino");
			}
			log.info("Operacion exitosa en la tabla cv_ficha_unidad.");
			
			query = "insert into cv_ficha_recurso(idficha, idunidad, idrecurso, estado_docente, estado_alumno, calificado, peso, estado) " +
					"select ?, r.idunidad, r.idrecurso, r.estado_docente, r.estado_alumno, r.calificado, r.peso, r.estado " +
					"from cv_silabo_recurso r, cv_silabo s " +
					"where r.idsilabo=s.idsilabo and s.estado=1 and s.idsilabo=? order by r.idunidad, r.idrecurso";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1,id);
			stmt.setInt(2, ficha.getSilabo().getIdSilabo());
			
			log.info("Insertando en la tabla cv_ficha_recurso ...");
			if (0 == stmt.executeUpdate()) {
				log.error("Error en insert into cv_ficha_recurso");
				throw new DAOException("No culmino");
			}
			log.info("Operacion exitosa en la tabla cv_ficha_recurso.");
						
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
	
	public void modificar(Integer idficha, Integer idperiodo)throws DAOException {
		log.info("modificar("+idficha+", "+idperiodo+") ");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			
			String query = "UPDATE cv_ficha SET IDPERIODO=? WHERE IDFICHA=?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idperiodo);
			stmt.setInt(2,idficha);
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en insert into cv_ficha");
				throw new DAOException("No se actualizÛ ning˙n registro");
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
	}
	
	public Collection<AulaVirtual> cargarPortada(Integer idusuario) throws DAOException {
		log.info("cargarPortada("+idusuario+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		Collection<AulaVirtual> cursos = new ArrayList<AulaVirtual>();
		AulaVirtual aula = null;
		Curso curso = null;
		Matricula matricula = null;
		try {
			cons = dataSource.getConnection();
			String query = "SELECT m.IDROL, f.IDFICHA, c.NOMBRE CURSO " +
					"FROM cv_matricula m, cv_ficha f, cv_curso c, cv_periodo p " +
					"WHERE f.IDCURSO=c.IDCURSO AND f.IDFICHA=m.IDFICHA AND p.IDPERIODO=f.IDPERIODO " +
					"AND f.ESTADO=1 AND f.eliminado=0 AND m.ESTADO=1 AND m.ELIMINADO=0 " +
					"AND (((m.IDROL=2 OR m.IDROL=3) AND p.FECHA_INICIO-p.DIAS_EDICION < NOW() AND NOW() < p.FECHA_FIN+p.DIAS_REVISION)  " +
					"OR ((m.IDROL=4 OR m.IDROL=5) AND p.FECHA_INICIO < NOW() AND NOW() < p.FECHA_FIN)) " +
					"AND m.IDUSUARIO=?";
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, idusuario);
			result = stmt.executeQuery();
			while (result.next()) {
				aula = new AulaVirtual();
				aula.setIdFicha(result.getInt("IDFICHA"));
				
				curso = new Curso();
				curso.setNombre(result.getString("CURSO"));
				aula.setCurso(curso);
				
				matricula = new Matricula();
				matricula.setRol(new MatriculaRol(result.getInt("IDROL")));
				aula.setMatriculaActual(matricula);
				
				//aula.setTrabajoIndividual(result.getInt("cantidad"));
				
				cursos.add(aula);
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
		return cursos;
	}
	
	public List<AulaVirtual> verCursos(Integer idusuario) throws DAOException {
		log.info("verCursos("+idusuario+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		List<AulaVirtual> grupos = new ArrayList<AulaVirtual>();
		AulaVirtual aula = null;
		Curso curso = null; 
		Periodo periodo = null;
		Matricula matricula = null;
		
		try {
			String query = "SELECT c.IDJERARQUIA, c.IDCURSO, c.NOMBRE CURSO, f.IDFICHA, f.IDSILABO, m.IDMATRICULA, m.IDROL, " +
					"p.IDPERIODO, p.NOMBRE PERIODO, p.PERSONALIZADO, p.FECHA_INICIO, p.FECHA_FIN, p.DIAS_EDICION, p.DIAS_REVISION " +
					"FROM cv_matricula m, cv_ficha f, cv_curso c, cv_periodo p " +
					"WHERE f.IDCURSO=c.IDCURSO AND f.IDFICHA=m.IDFICHA AND p.IDPERIODO=f.IDPERIODO " +
					"AND f.ESTADO=1 AND f.eliminado=0 AND m.ESTADO=1 AND m.ELIMINADO=0 " +
					"AND (((m.IDROL=2 OR m.IDROL=3) AND p.FECHA_INICIO-p.DIAS_EDICION < NOW() AND NOW() < p.FECHA_FIN+p.DIAS_REVISION)  " +
					"OR ((m.IDROL=4 OR m.IDROL=5) AND p.FECHA_INICIO < NOW() AND NOW() < p.FECHA_FIN)) " +
					"AND m.IDUSUARIO=?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idusuario);
			result =  stmt.executeQuery();
			while (result.next()) {
				aula = new AulaVirtual();
				aula.setIdFicha(result.getInt("IDFICHA"));
				aula.setSilabo(new Silabo(result.getInt("IDSILABO")));
				
				matricula = new Matricula();
				matricula.setIdMatricula(result.getInt("IDMATRICULA"));
				matricula.setRol(new MatriculaRol(result.getInt("IDROL")));
				
				aula.setMatriculaActual(matricula);
				
				curso = new Curso();
				curso.setIdCurso(result.getInt("IDCURSO"));
				curso.setNombre(result.getString("CURSO"));
				curso.setJerarquia(new Jerarquia(result.getInt("IDJERARQUIA")));
				
				aula.setCurso(curso);
				
				periodo = new Periodo();
				periodo.setIdPeriodo(result.getInt("IDPERIODO"));
				periodo.setNombre(result.getString("PERIODO"));
				periodo.setPersonalizado(result.getInt("PERSONALIZADO"));
				periodo.setFechaInicio(Formato.timestampToCalendar(result.getString("FECHA_INICIO")));
				periodo.setFechaFin(Formato.timestampToCalendar(result.getString("FECHA_FIN")));
				periodo.setDiasEdicion(result.getInt("DIAS_EDICION"));
				periodo.setDiasRevision(result.getInt("DIAS_REVISION"));
				
				aula.setPeriodo(periodo);
				
				grupos.add(aula);
				
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
		return grupos;
	}
	
	public List<AulaVirtual> buscarFichas(AulaVirtual aulaFiltro) throws DAOException {
		log.info("buscarFichas("+aulaFiltro+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		List<AulaVirtual> aulas = new ArrayList<AulaVirtual>();
		AulaVirtual aula = null;
		Curso curso = null; 
		Jerarquia jerarquia = null;
		Periodo periodo = null;
		Matricula matricula = null;
		Usuario usuario = null;
		
		try {
			String query = "SELECT f.idficha, c.nombre curso, p.nombre periodo, p.fecha_inicio, p.fecha_fin, p.personalizado, " +
					"j.idjerarquia, j.idpredecesor, j.nombre jerarquia, f.estado " +
					"FROM cv_ficha f, cv_curso c, cv_jerarquia j, cv_periodo p " +
					"WHERE f.idcurso=c.idcurso AND c.idjerarquia=j.idjerarquia AND f.idperiodo=p.idperiodo AND f.eliminado=0 ";
			if(aulaFiltro.getCurso().getJerarquia().getIdJerarquia() != null){
				query += "AND j.idjerarquia=? ";
			}
			if(aulaFiltro.getPeriodo().getIdPeriodo() != null){
				query += "AND p.idperiodo=? ";
			}
			if(aulaFiltro.getPeriodo().getFechaInicio() != null && aulaFiltro.getPeriodo().getFechaFin() != null){
				query += "AND p.FECHA_INICIO BETWEEN ? AND ? ";
			}else if(aulaFiltro.getPeriodo().getFechaInicio() != null){
				query += "AND p.FECHA_INICIO > ? ";
			}else if(aulaFiltro.getPeriodo().getFechaFin() != null){
				query += "AND p.FECHA_FIN-1 < ? ";
			}
			if(aulaFiltro.getCurso().getNombre() != null){
				query += "AND REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(UPPER(c.nombre),'¡','A'),'…','E'),'Õ','I'),'”','O'),'⁄','U'),'—','N') LIKE ? ";
			}
			if(aulaFiltro.getPrioridad() != 0 && aulaFiltro.getPrioridad() == 1){
				query += "AND NOW() < p.FECHA_FIN ";
			}
			if(aulaFiltro.getPrioridad() != 0 && aulaFiltro.getPrioridad() == 2){
				query += "AND NOW() >= p.FECHA_FIN ";
			}
			query += "order by f.idficha";

			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			
			int i = 0;
			if(aulaFiltro.getCurso().getJerarquia().getIdJerarquia() != null){
				stmt.setInt(++i, aulaFiltro.getCurso().getJerarquia().getIdJerarquia());
			}
			if(aulaFiltro.getPeriodo().getIdPeriodo() != null){
				stmt.setInt(++i, aulaFiltro.getPeriodo().getIdPeriodo());
			}
			if(aulaFiltro.getPeriodo().getFechaInicio() != null && aulaFiltro.getPeriodo().getFechaFin() != null){
				stmt.setString(++i, Formato.calendarToDate(aulaFiltro.getPeriodo().getFechaInicio()));
				stmt.setString(++i, Formato.calendarToDate(aulaFiltro.getPeriodo().getFechaFin()));
			}else if(aulaFiltro.getPeriodo().getFechaInicio() != null){
				stmt.setString(++i, Formato.calendarToDate(aulaFiltro.getPeriodo().getFechaInicio()));
			}else if(aulaFiltro.getPeriodo().getFechaFin() != null){
				stmt.setString(++i, Formato.calendarToDate(aulaFiltro.getPeriodo().getFechaFin()));
			}
			if(aulaFiltro.getCurso().getNombre() != null){
				stmt.setString(++i, "%" + Formato.matizarFrace(aulaFiltro.getCurso().getNombre()) + "%");
			}
			
			result =  stmt.executeQuery();
			while (result.next()) {
				
				//*********************FALTA CARGAR AL DOCENTE PRINCIPAL
//				SELECT u.USUARIO, p.APEPATERNO, p.APEMATERNO, p.NOMUNO, p.NOMDOS, p.SEXO 
//				FROM cv_matricula m, cv_usuario u, cv_persona p 
//				WHERE m.IDUSUARIO=u.IDUSUARIO AND u.IDUSUARIO=p.IDPERSONA 
//				AND m.ELIMINADO=0 AND m.IDROL=2 AND m.PRINCIPAL=1 AND m.IDFICHA=1;
				
				aula = new AulaVirtual();
				aula.setIdFicha(result.getInt("IDFICHA"));
				aula.setEstado(result.getInt("estado"));
				
				curso = new Curso();
				curso.setNombre(result.getString("curso"));
				
				jerarquia = new Jerarquia();
				jerarquia.setIdJerarquia(result.getInt("idjerarquia"));
				jerarquia.setNombre(result.getString("jerarquia"));
				curso.setJerarquia(jerarquia);
				
				aula.setCurso(curso);
								
				periodo = new Periodo();
				periodo.setNombre(result.getString("PERIODO"));
				periodo.setPersonalizado(result.getInt("PERSONALIZADO"));
				periodo.setFechaInicio(Formato.timestampToCalendar(result.getString("FECHA_INICIO")));
				periodo.setFechaFin(Formato.timestampToCalendar(result.getString("FECHA_FIN")));
				
				aula.setPeriodo(periodo);
				
				aulas.add(aula);
				
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
		return aulas;
	}
	
//	public FichaCollection obtenerFichas(FichaFiltro filtro, Usuario usuario)
//			throws DAOException {
//		log.info("obtenerFichas(FichaFiltro filtro)");
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		Connection cons = null;
//		FichaCollection collection = new FichaCollection();
//		Ficha ficha = new Ficha();
//		int tempIndice = 1;
//		try {
//			String query = "insert into CV_TEMP_FICHA(codigo_curso,idficha,estado_fecha,estado,"
//					+ "idsilabo,fecha_inicio,turno,nuevo,importante,nombrefamilia,nombrecurso"
//					+ ",nombredepartamento,nombreformacion,fechainicioperiodo,secciones,"
//					+ "codigo_familia,codigo_ciclo,sede,nombre_periodo,informacion) SELECT cvfi.codigo_curso,"
//					+ "cvfi.idficha,cvfi.estado_fecha,cvfi.estado,cvfi.idsilabo,pkg_cv_ficha.fx_cv_ficha_"
//					+ "fechainiciolaboral(cvfi.idficha),cvfi.turno,"
//					+ "cvfi.nuevo,cvfi.importante,cofa.nomcorto nombrefamilia,copr.nombre "
//					+ "nombrecurso,gede.nombre nombredepartamento,pkg_cv_ficha.fx_cv_ficha_forma(cvfi.idficha)"
//					+ " nombreformacion,gepe.fecinicio fechainicioperiodo,pkg_cv_ficha.fx_cv_ficha_ciclo_seccion"
//					+ "(cvfi.idficha,cvfi.codigo_ciclo) secciones,cvfi.codigo_familia,cvfi.codigo_ciclo,pkg_cv_ficha_util."
//					+ "fx_cv_ficha_sede_idficha(cvfi.idficha),nvl(gepe.nombre,' '),"
//					+ "pkg_cv_ficha_util.fx_cv_ficha_pendientes(cvfi.idficha) informacion "
//					+ "FROM cv_ficha cvfi,general.gen_departamento gede,comercial.com_producto "
//					+ "copr,comercial.com_familia cofa,cv_periodo gepe WHERE cvfi.codigo_departamento"
//					+ "=gede.codigo AND cvfi.codigo_curso=copr.codigo AND cvfi.codigo_familia="
//					+ "cofa.codigo AND cvfi.codigo_periodo=gepe.codigo(+) ";
//			
//			if (Constante.ROL_CAMPUS_SOPORTE != usuario.getRolPredeterminado()) 
//				query += "and 0!=pkg_cv_ficha.fx_CV_ADMINISTRAR_FICHA(cvfi.idficha,?) ";
//				
//			if (Constante.ETIQUETA_IMPORTANTE.equals(filtro.getEtiqueta())) {
//				query += " AND cvfi.importante=1 AND cvfi.idetiqueta="
//						+ Constante.ETIQUETA_VIRTUAL
//						+ " AND pkg_cv_ficha.fx_cv_ficha_fechafintotal(cvfi.idficha)>sysdate ";
//			} else {
//				if (!Constante.ETIQUETA_HISTORICO.equals(filtro.getEtiqueta())) {
//					if(Constante.ETIQUETA_PAPELERA.equals(filtro.getEtiqueta())){
//						query += " AND cvfi.idetiqueta=?  ";
//					}else{
//						query += " AND cvfi.idetiqueta=? AND pkg_cv_ficha.fx_cv_ficha_fechafintotal(cvfi.idficha)>sysdate  ";
//					}
//				}
//			}
//			if (!"0".equals(filtro.getBusquedaDepartamento())) {
//				query += " AND cvfi.codigo_departamento=? ";
//			}
//			if (!"0".equals(filtro.getBusquedaCiclo())) {
//				query += " AND cvfi.codigo_ciclo=? ";
//			}
//			if (Constante.ROL_CAMPUS_ADMINISTRADOR != usuario
//					.getRolPredeterminado()) {
//				query += " AND cvfi.estado='1' ";
//			}
//			if (0 < filtro.getBusquedaStringFormacion().length()) {
//				query += " AND 0!=(select count(*) from cv_ficha_formacion "
//						+ "fifo where fifo.idficha=cvfi.idficha and "
//						+ "UPPER(TRANSLATE(pkg_cv_com_producto."
//						+ "fx_cv_com_producto_nombre_id(fifo.codigo) ,'·ÈÌÛ˙¡…Õ”⁄','aeiouAEIOU'))"
//						+ " like UPPER(TRANSLATE(? ,'·ÈÌÛ˙¡…Õ”⁄','aeiouAEIOU')) ) ";
//			} else {
//				if (!"0".equals(filtro.getBusquedaFormacion())) {
//					query += " AND 0!=(select count(*) from cv_ficha_formacion "
//							+ "fifo where fifo.idficha=cvfi.idficha and fifo.codigo=? ) ";
//				}
//			}
//			if (0 < filtro.getBusquedaNombre().length()) {
//				query += " AND UPPER(TRANSLATE(copr.nombre,'·ÈÌÛ˙¡…Õ”⁄','aeiouAEIOU')) "
//						+ "like UPPER(TRANSLATE(? ,'·ÈÌÛ˙¡…Õ”⁄','aeiouAEIOU')) ";
//			}
//
//			if (!"0".equals(filtro.getBusquedaPeriodo())) {
//				query += " AND gepe.codigo=?";
//			}
//			if (!"0".equals(filtro.getBusquedaSede())) {
//				query += " AND pkg_cv_ficha_util.fx_cv_ficha_sede_idficha(cvfi.idficha)=? ";
//			}
//			if (0 < filtro.getBusquedaFecha1().length()
//					&& 0 < filtro.getBusquedaFecha2().length()) {
//				query += " AND pkg_cv_ficha.fx_cv_ficha_fechainiciolaboral(cvfi.idficha) between ? and ? ";
//			}
//			if (Constante.ETIQUETA_HISTORICO.equals(filtro.getEtiqueta())) {
//				query += " AND pkg_cv_ficha.fx_cv_ficha_fechafintotal(cvfi.idficha)<sysdate and  cvfi.idetiqueta=? ";
//			}
//			query += " order by idficha desc";
//			cons = dataSource.getConnection();
//			cons.setAutoCommit(false);
//			stmt =  cons.prepareStatement(query);
//			
//			if (Constante.ROL_CAMPUS_SOPORTE != usuario.getRolPredeterminado()) 
//				stmt.setString(tempIndice++, usuario.getIdUsuario());
//				
//			if (!Constante.ETIQUETA_IMPORTANTE.equals(filtro.getEtiqueta())
//					&& !Constante.ETIQUETA_HISTORICO.equals(filtro
//							.getEtiqueta())) {
//				stmt.setString(tempIndice++, filtro.getEtiqueta());
//			}
//			if (!"0".equals(filtro.getBusquedaDepartamento())) {
//				stmt.setString(tempIndice++, filtro.getBusquedaDepartamento());
//			}
//			if (!"0".equals(filtro.getBusquedaCiclo())) {
//				stmt.setString(tempIndice++, filtro.getBusquedaCiclo());
//			}
//			if (0 < filtro.getBusquedaStringFormacion().length()) {
//				stmt.setString(tempIndice++, "%"
//						+ filtro.getBusquedaStringFormacion().toUpperCase()
//						+ "%");
//			} else {
//				if (!"0".equals(filtro.getBusquedaFormacion())) {
//					stmt.setString(tempIndice++, filtro.getBusquedaFormacion());
//				}
//			}
//			if (0 < filtro.getBusquedaNombre().length()) {
//				if ("0".equals(filtro.getBusquedaExacta())) {
//					stmt.setString(tempIndice++, "%"
//							+ filtro.getBusquedaNombre().toUpperCase() + "%");
//				} else {
//					stmt.setString(tempIndice++, filtro.getBusquedaNombre()
//							.toUpperCase());
//				}
//			}
//			if (!"0".equals(filtro.getBusquedaPeriodo())) {
//				stmt.setString(tempIndice++, filtro.getBusquedaPeriodo());
//			}
//			if (!"0".equals(filtro.getBusquedaSede())) {
//				stmt.setString(tempIndice++, filtro.getBusquedaSede());
//				//stmt.setString(tempIndice++, filtro.getBusquedaSede());
//			}
//			if (0 < filtro.getBusquedaFecha1().length()
//					&& 0 < filtro.getBusquedaFecha2().length()) {
//				stmt.setString(tempIndice++, Formato.setBaseDatosDeDate(Formato
//						.setDateDeJSP(filtro.getBusquedaFecha1())));
//				stmt.setString(tempIndice++, Formato.setBaseDatosDeDate(Formato
//						.setDateDeJSP(filtro.getBusquedaFecha2())));
//			}
//			if (Constante.ETIQUETA_HISTORICO.equals(filtro.getEtiqueta())) {
//				stmt.setString(tempIndice++, Constante.ETIQUETA_VIRTUAL);
//			}
//			stmt.executeUpdate();
//			query = "select count(*) from CV_TEMP_FICHA";
//			stmt =  cons.prepareStatement(query);
//			result =  stmt.executeQuery();
//			if (result.next()) {
//				collection.setCantidad(result.getString(1));
//			}
//			query = "select codigo_curso,idficha,estado_fecha,estado,idsilabo,"
//					+ "fecha_inicio,turno,nuevo,importante,nombrefamilia,nombrecurso"
//					+ ",nombredepartamento,nombreformacion,fechainicioperiodo,"
//					+ "secciones,codigo_familia,codigo_ciclo,sede,nombre_periodo,informacion"
//					+ " from CV_TEMP_FICHA where rowid in (SELECT rowid FROM CV_TEMP_FICHA WHERE rownum<=?"
//					+ " minus SELECT rowid FROM CV_TEMP_FICHA WHERE rownum<=?)";
//			stmt =  cons.prepareStatement(query);
//			stmt.setString(1, filtro.getRegistroUltimo());
//			stmt.setString(2, filtro.getRegistroPrimero());
//			result =  stmt.executeQuery();
//			while (result.next()) {
//				ficha = new Ficha();
//				ficha.setIdFicha(result.getInt("idficha"));
//				if (0 == result.getInt("estado_fecha")) {
//					ficha.setFechaInicio(Formato.getDateDeBaseDatos(result
//							.getString("fechainicioperiodo")));
//				} else {
//					ficha.setFechaInicio(Formato.getDateDeBaseDatos(result
//							.getString("fecha_inicio")));
//				}
//				ficha.setNombrePeriodo(result.getString("nombre_periodo"));
//				ficha.setImportante(result.getInt("importante"));
//				ficha.setCodigoCursoUDS(result.getInt("codigo_curso"));
//				ficha.setSucursal(result.getString("sede"));
//				ficha.setNuevo(result.getInt("nuevo"));
//				ficha.setEstado(result.getInt("estado"));
//				ficha.setNombreFormacion(result.getString("nombreformacion"));
//				ficha.setNivel(result.getInt("codigo_ciclo"));
//				ficha.setTurno(result.getString("turno"));
//				ficha.getSilabo().setIdSilabo(result.getInt("idsilabo"));
//				ficha.setNombreFamilia(result.getString("nombrefamilia"));
//				ficha.setNombreCursoUDS(result.getString("nombrecurso"));
//				ficha.setCodigoFamilia(result.getInt("codigo_familia"));
//				ficha.setNombreDepartamento(result
//						.getString("nombredepartamento"));
//				ficha.setSecciones(result.getString("secciones"));
//				ficha.setInformacion(result.getString("informacion"));
//				collection.getFichas().add(ficha);
//			}
//		} catch (SQLException e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} catch (Exception e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} finally {
//			try {
//				cons.rollback();
//			} catch (SQLException e) {
//			}
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//		return collection;
//	}
//
//	public FichaCollection obtenerFichasUDS(FichaFiltro filtro, String etiqueta)
//			throws DAOException {
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		Connection cons = null;
//
//		FichaCollection fichas = new FichaCollection();
//		Ficha ficha = new Ficha();
//		int tempIndice = 1;
//		try {
//			cons = dataSource.getConnection();
//			cons.setAutoCommit(false);
//			//*** Cursos PCC ***//
//			String query = "INSERT into CV_TEMP_FICHA(codigo_curso,idficha,estado_fecha,estado,idsilabo"
//					+ ",fecha_inicio,turno,nuevo,importante,nombrefamilia,nombrecurso,nombredepartamento"
//					+ ",nombreformacion,fechainicioperiodo,secciones,codigo_familia,codigo_ciclo,sede,nombre_periodo)"
//					+ " select evasecpro.codproductoh codigocurso,evasecpro.codseccprod"
//					+ ",0,0,0,genper.fecinicio,comercial.buscarhorarioaula(comproact.codproactividad),0,0,comfam."
//					+ "nomcorto,compro.nombre,gendep.nombre,pkg_cv_ficha.fx_CV_STRING_FORMACION(0,pkg_cv_ficha.fx_CV_MACROENLACE(evasecpro"
//					+ ".codproductop,evasecpro.codproductoh,comproact.codfamilia),0,0,'PFR') as "
//					+ "nombreformacion,genper.fecinicio,pkg_cv_ficha.fx_cv_ficha_ciclo_seccion_pcc(comproactmod.orden),comproact.codfamilia,''"
//					+ ",genper.sede,genper.nombre FROM comercial.com_producto_actividad comproact,comercial.com_producto"
//					+ " compro,comercial.com_prod_act_modulo comproactmod,general.gen_departamento gendep,"
//					+ "comercial.com_familia comfam,evaluacion.EVA_SECCION_PRODUCTO evasecpro,general."
//					+ "gen_periodo genper WHERE comproact.codproductoh=evasecpro.codproductoh AND comproact"
//					+ ".codproductop=evasecpro.codproductop AND comproact.codperiodo=evasecpro.codperiodo "
//					+ "AND compro.codigo=comproact.codproductoh AND compro.coddepartamento=gendep.codigo "
//					+ "AND comproactmod.codproactcurso(+)=comproact.codproactividad AND comfam.codigo="
//					+ "comproact.codfamilia AND genper.codigo=evasecpro.codperiodo and comproact.estado='A' AND  evasecpro.estado='A'"
//					+ " AND not evasecpro.codseccprod in (select cvfise.codsecprod from cv_ficha_seva cvfise"
//					+ ",cv_ficha cvfi where cvfi.idficha=cvfise.idficha and cvfi.idetiqueta!="
//					+ Constante.ETIQUETA_PAPELERA
//					+ ") AND evasecpro" 
//					+ ".origen like 'PCC'   ";
//			if (!"0".equals(filtro.getBusquedaDepartamento())) {
//				query += " AND gendep.codigo=? ";
//			}
//			if (!"0".equals(filtro.getBusquedaCiclo())) {
//				query += " AND comproactmod.orden=? ";
//			}
//			if (0 < filtro.getBusquedaStringFormacion().length()) {
//				query += " AND UPPER(TRANSLATE(pkg_cv_ficha.fx_CV_STRING_FORMACION(0,pkg_cv_ficha.fx_CV_MACROENLACE(evasecpro"
//						+ ".codproductop,evasecpro.codproductoh,comproact.codfamilia),0,0,'PFR'),"
//						+ "'·ÈÌÛ˙¡…Õ”⁄','aeiouAEIOU')) like ?  ";
//			} else {
//				if (!"0".equals(filtro.getBusquedaFormacion())) {
//					query += " AND UPPER(TRANSLATE(pkg_cv_ficha.fx_CV_STRING_FORMACION(0,pkg_cv_ficha.fx_CV_MACROENLACE(evasecpro"
//							+ ".codproductop,evasecpro.codproductoh,comproact.codfamilia),0,0,'PFR'),"
//							+ "'·ÈÌÛ˙¡…Õ”⁄','aeiouAEIOU')) like UPPER(TRANSLATE(? ,'·ÈÌÛ˙¡…Õ”⁄','aeiouAEIOU')) ";
//				}
//			}
//			if (0 < filtro.getBusquedaNombre().length()) {
//				query += " AND UPPER(TRANSLATE(compro.nombre,'·ÈÌÛ˙¡…Õ”⁄','aeiouAEIOU')) like "
//						+ "UPPER(TRANSLATE(? ,'·ÈÌÛ˙¡…Õ”⁄','aeiouAEIOU')) ";
//			}
//			if (!"0".equals(filtro.getBusquedaPeriodo())) {
//				query += " AND genper.codigo=?";
//			}
//			if (!"0".equals(filtro.getBusquedaSede())) {
//				query += " AND genper.sede=? ";
//			}
//			if (0 < filtro.getBusquedaFecha1().length()
//					&& 0 < filtro.getBusquedaFecha2().length()) {
//				query += " AND genper.fecinicio between ? and ? ";
//			}
//			if (Constante.ETIQUETA_UDS_HISTORICO.equals(etiqueta)) {
//				query += " AND genper.fecfin<=sysdate ";
//				query += " order by genper.fecinicio desc";
//			}else if (Constante.ETIQUETA_UDS.equals(etiqueta)) {
//				query += " AND genper.fecfin>sysdate ";
//				query += " order by compro.nombre";
//			}
//			
//			stmt =  cons.prepareStatement(query);
//			if (!"0".equals(filtro.getBusquedaDepartamento())) {
//				stmt.setString(tempIndice++, filtro.getBusquedaDepartamento());
//			}
//			if (!"0".equals(filtro.getBusquedaCiclo())) {
//				stmt.setString(tempIndice++, filtro.getBusquedaCiclo());
//			}
//			if (0 < filtro.getBusquedaStringFormacion().length()) {
//				stmt.setString(tempIndice++, "%"
//						+ filtro.getBusquedaStringFormacion().toUpperCase()
//						+ "%");
//			} else {
//				if (!"0".equals(filtro.getBusquedaFormacion())) {
//					stmt
//							.setString(tempIndice++, "%"
//									+ filtro.getBusquedaFormacion()
//											.toUpperCase() + "%");
//				}
//			}
//			if (0 < filtro.getBusquedaNombre().length()) {
//				if ("0".equals(filtro.getBusquedaExacta())) {
//					stmt.setString(tempIndice++, "%"
//							+ filtro.getBusquedaNombre().toUpperCase() + "%");
//				} else {
//					stmt.setString(tempIndice++, filtro.getBusquedaNombre()
//							.toUpperCase());
//				}
//			}
//			if (!"0".equals(filtro.getBusquedaPeriodo())) {
//				stmt.setString(tempIndice++, filtro.getBusquedaPeriodo());
//			}
//			if (!"0".equals(filtro.getBusquedaSede())) {
//				stmt.setString(tempIndice++, filtro.getBusquedaSede());
//			}
//			if (0 < filtro.getBusquedaFecha1().length()
//					&& 0 < filtro.getBusquedaFecha2().length()) {
//				stmt.setString(tempIndice++, filtro.getBusquedaFecha1());
//				stmt.setString(tempIndice++, filtro.getBusquedaFecha2());
//			}
//			stmt.executeUpdate();
//			//*** Cursos PFR ***//
//			query = "insert into CV_TEMP_FICHA(codigo_curso,idficha,estado_fecha,estado,idsilabo,fecha_inicio"
//					+ ",turno,nuevo,importante,nombrefamilia,nombrecurso,nombredepartamento,nombreformacion,"
//					+ "fechainicioperiodo,secciones,codigo_familia,codigo_ciclo,sede,nombre_periodo) SELECT "
//					+ "docseccur.codcurso"
//					+ ",docseccur.codigo,0,1,0,genper.FECINICIO,pkg_cv_util.fx_CV_STRING_TURNO('PFR',docseccur.codigo),0,0,"
//					+ "comfam.nomcorto,compro.nombre,gendep.nombre,pkg_cv_ficha.fx_CV_STRING_FORMACION(docnivper.nivel,docnivper."
//					+ "codespecialidad,docnivper.codespbase,evasecpro.codseccprod,'PFR') as nombreformacion,genper"
//					+ ".FECINICIO,pkg_cv_util.fx_cv_formato_ciclo(docnivper.nivel) || ' '|| pkg_cv_util.fx_cv_string_number(docseccur.codseccion),'100',0,genper.sede,"
//					+ "genper.nombre FROM "
//					+ "docencia.doc_seccion_curso docseccur,docencia.doc_nivel_periodo docnivper,general.gen_periodo"
//					+ " genper,comercial.com_producto compro,general.gen_departamento gendep,comercial.com_familia"
//					+ " comfam,evaluacion.eva_seccion_producto evasecpro WHERE docseccur.codigo=evasecpro.codseccprod"
//					+ " AND docnivper.codigo=docseccur.codnivel AND genper.codigo=docnivper.codperiodo AND comfam."
//					+ "codigo='100' AND compro.codigo=docseccur.codcurso AND compro.coddepartamento=gendep.codigo "
//					+ "AND evasecpro.estado = 'A' AND not evasecpro.codseccprod in (select cvfise.codsecprod from "
//					+ "cv_ficha_seva cvfise,cv_ficha cvfi where cvfi.idficha=cvfise.idficha and cvfi.idetiqueta!="
//					+ Constante.ETIQUETA_PAPELERA
//					+ ")"
//					+ " AND evasecpro.origen like 'PFR'";
//			if (!"0".equals(filtro.getBusquedaDepartamento())) {
//				query += " AND gendep.codigo=? ";
//			}
//			if (!"0".equals(filtro.getBusquedaCiclo())) {
//				query += " AND docnivper.nivel=? ";
//			}
//			if (0 < filtro.getBusquedaStringFormacion().length()) {
//				query += " AND UPPER(TRANSLATE(pkg_cv_ficha.fx_CV_STRING_FORMACION(docnivper.nivel,docnivper."
//						+ "codespecialidad,docnivper.codespbase,evasecpro.codseccprod,'PFR'),"
//						+ "'·ÈÌÛ˙¡…Õ”⁄','aeiouAEIOU')) like UPPER(TRANSLATE(? ,'·ÈÌÛ˙¡…Õ”⁄','aeiouAEIOU'))  ";
//			} else {
//				if (!"0".equals(filtro.getBusquedaFormacion())) {
//					query += " AND UPPER(TRANSLATE(pkg_cv_ficha.fx_CV_STRING_FORMACION(docnivper.nivel,docnivper."
//							+ "codespecialidad,docnivper.codespbase,evasecpro.codseccprod,'PFR'),"
//							+ "'·ÈÌÛ˙¡…Õ”⁄','aeiouAEIOU')) like UPPER(TRANSLATE(? ,'·ÈÌÛ˙¡…Õ”⁄','aeiouAEIOU')) ";
//				}
//			}
//			if (0 < filtro.getBusquedaNombre().length()) {
//				query += " AND UPPER(TRANSLATE(compro.nombre,'·ÈÌÛ˙¡…Õ”⁄','aeiouAEIOU')) "
//						+ "like UPPER(TRANSLATE(? ,'·ÈÌÛ˙¡…Õ”⁄','aeiouAEIOU')) ";
//			}
//			if (!"0".equals(filtro.getBusquedaPeriodo())) {
//				query += " AND genper.codigo=?";
//			}
//			if (!"0".equals(filtro.getBusquedaSede())) {
//				query += " AND genper.sede=? ";
//			}
//			if (0 < filtro.getBusquedaFecha1().length()
//					&& 0 < filtro.getBusquedaFecha2().length()) {
//				query += " AND genper.fecinicio between ? and ? ";
//			}
//			if (Constante.ETIQUETA_UDS_HISTORICO.equals(etiqueta)) {
//				query += " AND genper.fecfin<=sysdate ";
//			}
//			if (Constante.ETIQUETA_UDS.equals(etiqueta)) {
//				query += " AND genper.fecfin>sysdate ";
//			}
//			query += " order by genper.fecinicio desc";
//			stmt =  cons.prepareStatement(query);
//			tempIndice = 1;
//			if (!"0".equals(filtro.getBusquedaDepartamento())) {
//				stmt.setString(tempIndice++, filtro.getBusquedaDepartamento());
//			}
//			if (!"0".equals(filtro.getBusquedaCiclo())) {
//				stmt.setString(tempIndice++, filtro.getBusquedaCiclo());
//			}
//			if (0 < filtro.getBusquedaStringFormacion().length()) {
//				stmt.setString(tempIndice++, "%"
//						+ filtro.getBusquedaStringFormacion().toUpperCase()
//						+ "%");
//			} else {
//				if (!"0".equals(filtro.getBusquedaFormacion())) {
//					stmt
//							.setString(tempIndice++, "%"
//									+ filtro.getBusquedaFormacion()
//											.toUpperCase() + "%");
//				}
//			}
//			if (0 < filtro.getBusquedaNombre().length()) {
//				if ("0".equals(filtro.getBusquedaExacta())) {
//					stmt.setString(tempIndice++, "%"
//							+ filtro.getBusquedaNombre().toUpperCase() + "%");
//				} else {
//					stmt.setString(tempIndice++, filtro.getBusquedaNombre()
//							.toUpperCase());
//				}
//			}
//			if (!"0".equals(filtro.getBusquedaPeriodo())) {
//				stmt.setString(tempIndice++, filtro.getBusquedaPeriodo());
//			}
//			if (!"0".equals(filtro.getBusquedaSede())) {
//				stmt.setString(tempIndice++, filtro.getBusquedaSede());
//			}
//			if (0 < filtro.getBusquedaFecha1().length()
//					&& 0 < filtro.getBusquedaFecha2().length()) {
//				stmt.setString(tempIndice++, filtro.getBusquedaFecha1());
//				stmt.setString(tempIndice++, filtro.getBusquedaFecha2());
//			}
//			stmt.executeUpdate();
//			query = "select count(*) from CV_TEMP_FICHA";
//			stmt =  cons.prepareStatement(query);
//			result =  stmt.executeQuery();
//			if (result.next()) {
//				fichas.setCantidad(result.getString(1));
//			}
//			query = "select codigo_curso,idficha,estado_fecha,estado,idsilabo,"
//					+ "fecha_inicio,turno,nuevo,importante,nombrefamilia,nombrecurso"
//					+ ",nombredepartamento,nombreformacion,fechainicioperiodo,"
//					+ "secciones,codigo_familia,codigo_ciclo,sede,nombre_periodo from CV_TEMP_FICHA"
//					+ " where rowid in (SELECT rowid FROM CV_TEMP_FICHA WHERE rownum<=?"
//					+ " minus SELECT rowid FROM CV_TEMP_FICHA WHERE rownum<=?)";
//			stmt =  cons.prepareStatement(query);
//			stmt.setString(1, filtro.getRegistroUltimo());
//			stmt.setString(2, filtro.getRegistroPrimero());
//			result =  stmt.executeQuery();
//			while (result.next()) {
//				ficha = new Ficha();
//				ficha.setIdFicha(result.getInt("idficha"));
//				ficha.setFechaInicio(Formato.getDateDeBaseDatos(result
//						.getString("fechainicioperiodo")));
//				ficha.setNombrePeriodo(result.getString("nombre_periodo"));
//				ficha.setImportante(result.getInt("importante"));
//				ficha.setCodigoCursoUDS(result.getInt("codigo_curso"));
//				ficha.setSucursal(result.getString("sede"));
//				ficha.setNuevo(result.getInt("nuevo"));
//				ficha.setEstado(result.getInt("estado"));
//				ficha.setNombreFormacion(result.getString("nombreformacion"));
//				ficha.setNivel(result.getInt("codigo_ciclo"));
//				ficha.setTurno(result.getString("turno"));
//				ficha.getSilabo().setIdSilabo(result.getInt("idsilabo"));
//				ficha.setNombreFamilia(result.getString("nombrefamilia"));
//				ficha.setNombreCursoUDS(result.getString("nombrecurso"));
//				ficha.setCodigoFamilia(result.getInt("codigo_familia"));
//				ficha.setNombreDepartamento(result
//						.getString("nombredepartamento"));
//				ficha.setSecciones(result.getString("secciones"));
//				fichas.getFichas().add(ficha);
//			}
//		} catch (SQLException e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} catch (Exception e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} finally {
//			try {
//				cons.rollback();
//			} catch (SQLException e) {
//			}
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//		return fichas;
//	}
//
//	public int crearExternos(Usuario usuario, String tipo, String[] valores)
//			throws DAOException {
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		Connection cons = null;
//		//OracleCallableStatement callstmt = null;
//		int cantidad = 0;
//		log.info("crearExternos(Usuario usuario, "
//				+ "String tipo, String[] valores)");
//		try {
//			cons = dataSource.getConnection();
//			cons.setAutoCommit(false);
//			String query = "INSERT INTO CV_TEMP_CODSECPROD (CODSECPROD) VALUES (?)";
//			stmt =  cons.prepareStatement(query);
//			for (int u = 0; u < valores.length; u++) {
//				stmt.setString(1, valores[u]);
//				stmt.executeUpdate();
//			}
//			query = "{CALL pkg_cv_ficha_crear.sp_crearcurso(?,?,?)}";
////			callstmt =  cons.prepareCall(query);
////			callstmt.setString(1, tipo);
////			callstmt.setString(2, usuario.getIdUsuario());
////			callstmt.registerOutParameter(3, OracleTypes.INTEGER);
////			callstmt.execute();
////			cantidad = callstmt.getInt(3);
//			cons.commit();
//		} catch (SQLException e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} catch (Exception e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} finally {
//			try {
//				cons.rollback();
//			} catch (SQLException e1) {
//			}
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//		return cantidad;
//	}
//

//

//
//	public Collection<FichaHistorica> obtenerFichasPorUsuarioHistorica(
//			Usuario usuario) throws DAOException {
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		Connection cons = null;
//		log.info("obtenerFichasPorUsuarioHistorica(Usuario usuario)");
//		Collection<FichaHistorica> formacion = new ArrayList<FichaHistorica>();
//		FichaHistorica temp = null;
//		try {
//			cons = dataSource.getConnection();
//			String query = "SELECT cvma.idmatricula,cvma.idrol,cvfi.idficha,pkg_cv_com_producto."
//					+ "fx_cv_com_producto_nombre_id(cvfi.codigo_curso) nombre,cofa.nomcorto familia,pkg_cv_com_producto.fx_cv_com_producto_nombre_id(cvma.codigo_formacion) formacion, "
//					+ " nvl(cvpe.nombre,' ') periodo,pkg_cv_ficha."
//					+ "fx_cv_ficha_seccion(cvfi.idficha) secciones,"
//					+ "cvfi.codigo_ciclo,  pkg_cv_ficha."
//					+ "fx_cv_ficha_fechainicio(cvma.idmatricula) fechainicio,  pkg_cv_ficha."
//					+ "fx_cv_ficha_fechafin(cvma.idmatricula) fechafin FROM cv_matricula cvma,"
//					+ "  cv_ficha cvfi,  cv_periodo cvpe,comercial.com_familia cofa WHERE cvma."
//					+ "eliminado = '0' AND cvma.estado = '1' AND cvma.idrol != 1 AND cvma."
//					+ "idficha = cvfi.idficha AND cvfi.estado = '1' AND cvfi.idetiqueta != "
//					+ "? AND pkg_cv_ficha.fx_cv_ficha_fechafin(cvma.idmatricula) < "
//					+ "sysdate AND TRIM(cvma.usuario) = ? "
//					+ " AND cvpe.codigo(+) = cvfi.codigo_periodo and cvfi.codigo_familia=cofa.codigo";
//			stmt =  cons.prepareStatement(query);
//			stmt.setString(1, Constante.ETIQUETA_PAPELERA);
//			stmt.setString(2, usuario.getIdUsuario());
//			result =  stmt.executeQuery();
//
//			while (result.next()) {
//				temp = new FichaHistorica();
//				temp.setIdMatricula(result.getString("idmatricula"));
//				temp.setFormacion(result.getString("formacion"));
//				temp.setIdFicha(result.getInt("idficha"));
//				temp.setCiclo(result.getInt("codigo_ciclo"));
//				temp.setSecciones(result.getString("secciones"));
//				temp.setNombreFicha(result.getString("nombre"));
//				temp.setIdRol(result.getInt("idrol"));
//				temp.setNombrePeriodo(result.getString("periodo"));
//				temp.setFechaInicio(Formato.getDateDeBaseDatos(result
//						.getString("fechainicio")));
//				temp.setFechaFin(Formato.getDateDeBaseDatos(result
//						.getString("fechafin")));
//				temp.setFamilia(result.getString("familia"));
//				formacion.add(temp);
//			}
//		} catch (SQLException e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} catch (Exception e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} finally {
//			try {
//				cons.commit();
//			} catch (SQLException e) {
//			}
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//		return formacion;
//	}
//
//	public Collection<String> obtenerSeccionPorFicha(int idFicha)
//			throws DAOException {
//		log.info("obtenerSeccionPorFicha(int idFicha)");
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		Connection cons = null;
//
//		Collection<String> secciones = new ArrayList<String>();
//		try {
//			String query = "SELECT CODSECCION FROM CV_FICHA_SEVA WHERE  idficha=?";
//			cons = dataSource.getConnection();
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, idFicha);
//			result =  stmt.executeQuery();
//			while (result.next()) {
//				secciones.add(result.getString(1));
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
//		return secciones;
//	}
//
//	public Collection<Especialidad> obtenerFormacionPorFicha(int idFicha)
//			throws DAOException {
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		Connection cons = null;
//		log.info("obtenerFormacionPorFicha(int idFicha)");
//		Collection<Especialidad> secciones = new ArrayList<Especialidad>();
//		Especialidad forma;
//		try {
//			String query = "SELECT cvfi.codigo,pkg_cv_com_producto."
//					+ "fx_cv_com_producto_nombre_id(cvfi.codigo) nombre "
//					+ "FROM cv_ficha_formacion cvfi WHERE cvfi.idficha=?";
//			cons = dataSource.getConnection();
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, idFicha);
//			result =  stmt.executeQuery();
//			while (result.next()) {
//				forma = new Especialidad();
//				forma.setCodigo(result.getString("codigo"));
//				forma.setNombre(result.getString("NOMBRE"));
//				secciones.add(forma);
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
//		return secciones;
//	}
//
//	public void setImportanteFicha(Usuario usuario, int idFicha, int importante)
//			throws DAOException {
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		Connection cons = null;
//
//		log.info("setImportanteFicha(int idFicha, int importante)");
//		try {
//			String query = "UPDATE CV_FICHA SET USUARIO_MOD=?, FECHA_MOD"
//					+ "=sysdate,IMPORTANTE=? WHERE IDFICHA=?";
//			cons = dataSource.getConnection();
//			stmt =  cons.prepareStatement(query);
//			stmt.setString(1, usuario.getIdUsuario());
//			stmt.setInt(2, importante);
//			stmt.setInt(3, idFicha);
//			if (1 != stmt.executeUpdate()) {
//				throw new DAOException("Error");
//			}
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
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//	}
//
//	public void setEstadoFicha(Usuario usuario, int idFicha, int estado)
//			throws DAOException {
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		Connection cons = null;
//		log.info("setEstadoFicha(int idFicha, int estado)");
//		try {
//			String query = "UPDATE CV_FICHA SET USUARIO_MOD=?, "
//					+ "FECHA_MOD=sysdate, ESTADO=? WHERE IDFICHA=?";
//			cons = dataSource.getConnection();
//			stmt =  cons.prepareStatement(query);
//			stmt.setString(1, usuario.getIdUsuario());
//			stmt.setInt(2, estado);
//			stmt.setInt(3, idFicha);
//			if (1 != stmt.executeUpdate()) {
//				throw new DAOException("Error");
//			}
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
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//	}
//
//	public int setPapelera(Usuario usuario, String[] valores)
//			throws DAOException {
//		int cantidad = 0;
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		Connection cons = null;
//		log.info("setPapelera(Usuario usuario, String[] valores)");
//		try {
//			String query = "UPDATE CV_FICHA SET USUARIO_MOD=?, FECHA_MOD=sysdate"
//					+ " ,IDETIQUETA=?,ESTADO=0,IMPORTANTE=0 WHERE IDFICHA=?";
//			cons = dataSource.getConnection();
//			cons.setAutoCommit(false);
//			stmt =  cons.prepareStatement(query);
//			for (int i = 0; i < valores.length; i++) {
//				stmt.setString(1, usuario.getIdUsuario());
//				stmt.setString(2, Constante.ETIQUETA_PAPELERA);
//				stmt.setString(3, valores[i]);
//				if (1 != stmt.executeUpdate()) {
//					throw new DAOException("error" + valores[i]);
//				}
//				cantidad++;
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
//			} catch (SQLException e) {
//			}
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//		return cantidad;
//	}
//
//	public int[] setSincronizar(Usuario usuario, String[] valores)
//			throws DAOException {
//		log.info("setSincronizar(Usuario usuario, "
//				+ "String tipo, String[] valores)");
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		Connection cons = null;
//		//OracleCallableStatement callstmt = null;
//		int[] numeros = new int[2];
//		try {
//			cons = dataSource.getConnection();
//			cons.setAutoCommit(false);
//			
//			//--------------------------------------
//			
//			if("100".equals(valores[0])){
//				String sql = "SELECT cvfi.codigo_curso,cvfi.idficha " +
//						"FROM cv_ficha cvfi,cv_periodo gepe " +
//						"WHERE cvfi.codigo_periodo=gepe.codigo(+) " +
//						"AND cvfi.idetiqueta=1 " +
//						"AND pkg_cv_ficha.fx_cv_ficha_fechafintotal(cvfi.idficha)>sysdate order by cvfi.idficha";
//				stmt =  cons.prepareStatement(sql);
//				result =  stmt.executeQuery();
//				Collection<String> cursos = new ArrayList<String>();
//				while (result.next()) {
//					cursos.add(result.getString("idficha"));
//				}
//				valores = new String[cursos.size()];
//				int i = 0;
//				for (String s : cursos) {
//					valores[i] = s;
//					i++;
//				}
//			}
//			//---------------------------------------
//			
//			String query = "INSERT INTO CV_TEMP_CODSECPROD (CODSECPROD) VALUES (?)";
//			stmt =  cons.prepareStatement(query);
//			for (int u = 0; u < valores.length; u++) {
//				stmt.setString(1, valores[u]);
//				stmt.executeUpdate();
//				log.info("Actualizando el curso: "+valores[u]+" ...");
//				
//			}
//			
//			query = "{CALL pkg_cv_ficha_sincro.sp_cv_ficha_sincro_estud(?,?,?)}";
////			callstmt =  cons.prepareCall(query);
////			callstmt.setString(1, usuario.getIdUsuario());
////			callstmt.registerOutParameter(2, OracleTypes.INTEGER);
////			callstmt.registerOutParameter(3, OracleTypes.INTEGER);
//			System.out.println("se ejecuta");
////			callstmt.execute();
////			numeros[0] = callstmt.getInt(2);
////			numeros[1] = callstmt.getInt(3);
//			System.out.println(numeros[0]+" - "+numeros[1]);
//			cons.commit();
//		} catch (SQLException e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} catch (Exception e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} finally {
//			try {
//				cons.rollback();
//			} catch (SQLException e1) {
//			}
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//		return numeros;
//	}
//
//	public int[] setConstancia(Usuario usuario, String[] valores) throws DAOException {
//		log.info("setConstancia(Usuario usuario, String[] valores)");
//		//Constancia de porrazo
//		valores = new String[1];
//		//
//		PreparedStatement stmt = null;
//		PreparedStatement stmt1 = null;
//		PreparedStatement stmt2 = null;
//		PreparedStatement stmt3 = null;
//		PreparedStatement stmt4 = null;
//		PreparedStatement stmt5 = null;
//		ResultSet result = null;
//		ResultSet result2 = null;
//		Connection cons = null;
//		//OracleCallableStatement callstmt = null;
//		int[] numeros = new int[2];
//		String claveTemp = "";
//		int totalEmailsNuevo = 0;
//		int totalEmails = 0;
//		String[] correos = null;
//		SendMail mail = new SendMail();
//		Map<String,String> usrWithNewPass = new HashMap<String, String>(); 
//		String nombreCompleto = "";
//		try {
//			cons = dataSource.getConnection();
//			String query = "SELECT TRIM(cvma.usuario) usuario,cvfi.idficha," +
//					"genper.nomuno,genper.nomdos,genper.apepaterno,genper.apematerno," +
//					"cvma.idmatricula,genper.email,compro.nombre curso," +
//					"pkg_cv_ficha.fx_cv_ficha_fechainiciolaboral(cvfi.idficha) fecha1," +
//					"pkg_cv_ficha.fx_cv_ficha_fechafinlaboral(cvfi.idficha) fecha2," +
//					"round((pkg_cv_ficha.fx_cv_ficha_fechafinlaboral(cvfi.idficha)-pkg_cv_ficha.fx_cv_ficha_fechainiciolaboral(cvfi.idficha))/7) fecha3  " +
//					"FROM " +
//					"cv_matricula cvma,seguridad.seg_usuario segusu,general.gen_persona genper," +
//					"cv_ficha cvfi,comercial.com_producto compro " +
//					"WHERE " +
//					"    cvma.idficha=cvfi.idficha AND cvfi.codigo_curso=compro.codigo " +
//					"AND TRIM(cvma.usuario)=TRIM(segusu.usuario) AND segusu.codsujeto=genper.codpersona " +
//					"AND cvma.eliminado='0' AND cvma.estado='1' AND cvfi.estado=1 " +
//					"AND cvma.n_correo_const=0 " +
//					"AND sysdate>=pkg_cv_ficha.fx_cv_ficha_fechainiciolaboral(cvfi.idficha)-1  " +
//					"AND sysdate<=pkg_cv_ficha.fx_cv_ficha_fechafinlaboral(cvfi.idficha) " +
//					"AND cvma.idrol=? AND cvfi.codigo_familia=? " +
//					//"AND cvma.idficha=?" +
//					" ";
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE);
//			stmt.setInt(2, Constante.PRODUCTO_opencampus_VIRTUAL);
//			
//			query = "UPDATE cv_matricula SET n_correo_const=1 WHERE idmatricula=?";
//			stmt1 =  cons.prepareStatement(query);
//			
//			query = "SELECT COUNT(*) TOTAL " +
//					"FROM cv_matricula m, cv_ficha f " +
//					"WHERE m.idficha = f.idficha " +
//					"AND m.eliminado = '0' AND m.estado = '1'  AND f.estado = '1' AND f.IDETIQUETA != 1000 " +
//					"AND pkg_cv_ficha.fx_cv_ficha_fechainicio(m.idmatricula) <= sysdate " +
//					"AND pkg_cv_ficha.fx_cv_ficha_fechafin(m.idmatricula) >= sysdate " +
//					"AND 0 < (SELECT COUNT(*) FROM CV_INGRESO WHERE ELEMENTO=2 AND TRIM(USUARIO)=TRIM(M.USUARIO) AND VALOR=F.IDFICHA) " +
//					"AND F.IDFICHA != ? AND TRIM(USUARIO)=?";
//			stmt2 =  cons.prepareStatement(query);
//			
//			query = "UPDATE CV_USUARIO_CONFIG SET CLAVE=?, FECHA_GENERADA=SYSDATE WHERE TRIM(USUARIO)=?";
//			stmt3 =  cons.prepareStatement(query);
//			
//			query = "INSERT INTO CV_USUARIO_CONFIG(CLAVE,USUARIO,FECHA_GENERADA) VALUES(?,?,SYSDATE)";
//			stmt4 =  cons.prepareStatement(query);
//			
//			query = "{CALL SEGURIDAD.PCKSEGURIDAD.GrabaFirma(?,?,?)}";
////			callstmt =  cons.prepareCall(query);
////			callstmt.registerOutParameter(3, OracleTypes.INTEGER);
//			
//			query = "SELECT COUNT(*) TOTAL FROM SEGURIDAD.SEG_USUARIO_ROL WHERE TRIM(USUARIO)=? AND ESACTIVO='S' AND CODROL>=49 AND CODROL<=59";
//			stmt5 =  cons.prepareStatement(query);
//			
//			for (int o = 0; o < valores.length; o++) {
//				//stmt.setString(3, valores[o]);
//				result =  stmt.executeQuery();
//				while (result.next()) {
//					
//					nombreCompleto = Formato.formatoNombreCompletoJSP( result.getString("nomuno"), result.getString("nomdos"), result.getString("apepaterno"), result.getString("apematerno"));
//					
//					//--------------- PERSONAL -----------------------------
//					
//					stmt5.setString(1, result.getString("usuario"));
//					result2 =  stmt5.executeQuery();
//					result2.next();
//					int esPersonal = result2.getInt("TOTAL");
//					//------------------------------------------------------
//					
//					stmt2.setInt(1, result.getInt("idficha"));
//					stmt2.setString(2, result.getString("usuario"));
//					result2 =  stmt2.executeQuery();
//					result2.next();
//					int esUsuario = result2.getInt("TOTAL");
//					
//					//SI EL USUARIO TIENE UNA FICHA ACTIVA NO SE RESETEA LA CLAVE 
//					if (esUsuario > 0 || esPersonal > 0) {
//						if(esUsuario > 0)
//							log.info("Envio de constancia: "+ result.getString("usuario")+ " - Usuario ya posee ficha activa");
//						if(esPersonal > 0)
//							log.info("                   : "+ result.getString("usuario")+ " - Usuario es un personal de opencampus");
//							
//						//AGREGAR O ACTUALIZAR CLAVE EN CV_USUARIO_CONFIG (CON VALORES: #MISMA) ***********
//						stmt3.setString(1, "#MISMA");
//						stmt3.setString(2, result.getString("usuario"));
//						if(1 != stmt3.executeUpdate()){
//							stmt4.setString(1, "#MISMA");
//							stmt4.setString(2, result.getString("usuario"));
//							stmt4.executeUpdate();
//						}
//						
//						//Envio de la clave a todos sus correos
//						if (null != result.getString("email")) {
//							
//							correos = result.getString("email").replaceAll(";", ",").split(",");
//							for (int y = 0; y < correos.length; y++) {
//								try {
//									mail.envioInicioClases(correos[y],
//											nombreCompleto, result.getString("usuario"),
//											result.getString("curso"),
//											Formato.getDateDeBaseDatos(result.getString("fecha1")),
//											Formato.getDateDeBaseDatos(result.getString("fecha2")),
//											result.getInt("fecha3"));
//								} catch (Exception e) {
//								}
//							}
//							totalEmails++;
//							
//						}
//						
//					} else {
//						
//						//Valido si el usuario ya fue generado su clave
//						//********************** LEER DE BASE DE DATOS JUNTO AL SELECT DE LAS MATRICULAS **************************
////						SELECT CLAVE 
////						FROM CV_USUARIO_CONFIG 
////						WHERE 
////						--FECHA_GENERADA esta entre fecha de inicio-1 y fin de algun curso que tenga el usuario
////						AND TRIM(USUARIO)='lechevarria01';
//						if(usrWithNewPass.get(result.getString("usuario")) == null){
//							
//							//El usuario no se le genero la clave aun
//							claveTemp = Generador.generarClave();
//							
//							log.info("Envio de constancia: "+ result.getString("usuario")+ " - Clave Generada: "+claveTemp);
//							
////							callstmt.setString(1, result.getString("usuario"));
////							callstmt.setString(2, claveTemp);
////							callstmt.execute();
//							
//							//Recuerdo que el usuario ya se le genero la clave y si tiene otra ficha ya no se le generara.
//							usrWithNewPass.put(result.getString("usuario"), claveTemp);
//							
//							//AGREGAR O ACTUALIZAR CLAVE EN CV_USUARIO_CONFIG (CON VALORES: claveTemp) **********
//							stmt3.setString(1, claveTemp);
//							stmt3.setString(2, result.getString("usuario"));
//							if(1 != stmt3.executeUpdate()){
//								stmt4.setString(1, claveTemp);
//								stmt4.setString(2, result.getString("usuario"));
//								stmt4.executeUpdate();
//							}
//							
//							//Envio de la clave a todos sus correos
//							if (null != result.getString("email")) {
//								
//								correos = result.getString("email").replaceAll(";",",").split(",");
//								for (int y = 0; y < correos.length; y++) {
//									try {
//										mail.envioInicioClasesNuevo(correos[y],
//												nombreCompleto, result.getString("usuario"),
//												claveTemp, 
//												result.getString("curso"),
//												Formato.getDateDeBaseDatos(result.getString("fecha1")),
//												Formato.getDateDeBaseDatos(result.getString("fecha2")),
//												result.getInt("fecha3"));
//									} catch (Exception e) {
//									}
//								}
//								totalEmails++;
//								totalEmailsNuevo++;
//								
//							}
//							
//						}else{
//							log.info("Envio de constancia: "+ result.getString("usuario")+ " - Usuario tiene varias matriculas");
//							
//							//Recupero la clave generada anteriormente
//							claveTemp = usrWithNewPass.get(result.getString("usuario"));
//							
//							//Envio de la clave a todos sus correos
//							if (null != result.getString("email")) {
//								
//								correos = result.getString("email").replaceAll(";",",").split(",");
//								for (int y = 0; y < correos.length; y++) {
//									try {
//										mail.envioInicioClasesNuevo(correos[y],
//												nombreCompleto, result.getString("usuario"),
//												claveTemp, 
//												result.getString("curso"),
//												Formato.getDateDeBaseDatos(result.getString("fecha1")),
//												Formato.getDateDeBaseDatos(result.getString("fecha2")),
//												result.getInt("fecha3"));
//									} catch (Exception e) {
//									}
//								}
//								totalEmails++;
//								
//							}
//							
//						}
//						
//					}
//					
//					//Registrar que ya se le envio la constancia
//					stmt1.setString(1, result.getString("idmatricula"));
//					stmt1.executeUpdate();
//					
//				}
//			}
//			numeros[0] = totalEmailsNuevo;
//			numeros[1] = totalEmails;
//		} catch (SQLException e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} catch (Exception e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} finally {
//			closeResultSet(result);
//			closeResultSet(result2);
//			closeStatement(stmt);
//			closeStatement(stmt1);
//			closeStatement(stmt2);
//			closeStatement(stmt3);
//			closeStatement(stmt4);
//			closeStatement(stmt5);
//			closeConnection(cons);
//		}
//		return numeros;
//	}
//
//	public int setRestaurar(Usuario usuario, String[] valores) throws DAOException {
//		int cantidad = 0;
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		Connection cons = null;
//		log.info("setRestaurar(Usuario usuario, String[] valores)");
//		try {
//			String query = "UPDATE CV_FICHA SET USUARIO_MOD=?, FECHA_MOD=sysdate"
//					+ " ,IDETIQUETA=1 WHERE IDFICHA=? AND 0=(select count(*) from"
//					+ " cv_ficha_seva fise,cv_ficha fi  where fise.CODSECPROD in"
//					+ " (SELECT CODSECPROD FROM cv_ficha_seva WHERE idficha = ?) "
//					+ "and fise.idficha=fi.idficha and fi.IDETIQUETA!=?)";
//			cons = dataSource.getConnection();
//			cons.setAutoCommit(false);
//			stmt =  cons.prepareStatement(query);
//			for (int i = 0; i < valores.length; i++) {
//				stmt.setString(1, usuario.getIdUsuario());
//				stmt.setString(2, valores[i]);
//				stmt.setString(3, valores[i]);
//				stmt.setString(4, Constante.ETIQUETA_PAPELERA);
//				if (1 != stmt.executeUpdate()) {
//					throw new DAOException("error " + valores[i]);
//				}
//				cantidad++;
//			}
//			cons.commit();
//		} catch (SQLException e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} catch (DAOException e) {
//			log.error(e.getMessage());
//			throw e;
//		} catch (Exception e) {
//			log.error(e.toString());
//			throw new DAOException(e.toString());
//		} finally {
//			try {
//				cons.rollback();
//			} catch (SQLException e) {
//			}
//			closeResultSet(result);
//			closeStatement(stmt);
//			closeConnection(cons);
//		}
//		return cantidad;
//	}
//
//	public Collection<Curso> obtenerCursoDeFicha(String nombre)
//			throws DAOException {
//		PreparedStatement stmt = null;
//		ResultSet result = null;
//		Connection cons = null;
//		log.info("obtenerCursoDeFicha(String nombre)");
//		Collection<Curso> cursos = new ArrayList<Curso>();
//		Curso curso;
//		try {
//			String query = "select distinct cvfi.codigo_curso codigo,pkg_cv_com_producto."
//					+ "fx_cv_com_producto_nombre_id(cvfi.codigo_curso) nombre from"
//					+ " cv_ficha cvfi where UPPER(TRANSLATE(pkg_cv_com_producto."
//					+ "fx_cv_com_producto_nombre_id(cvfi.codigo_curso),'·ÈÌÛ˙¡…Õ”⁄'"
//					+ ",'aeiouAEIOU')) like UPPER(TRANSLATE(? ,'·ÈÌÛ˙¡…Õ”⁄','aeiouAEIOU')) and cvfi.idetiqueta!=?";
//			cons = dataSource.getConnection();
//			stmt =  cons.prepareStatement(query);
//			stmt.setString(1, "%" + nombre + "%");
//			stmt.setString(2, Constante.ETIQUETA_PAPELERA);
//			result =  stmt.executeQuery();
//			while (result.next()) {
//				curso = new Curso();
//				curso.setIdCurso(result.getInt("codigo"));
//				curso.setNombre(result.getString("NOMBRE"));
//				cursos.add(curso);
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
//		return cursos;
//	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
