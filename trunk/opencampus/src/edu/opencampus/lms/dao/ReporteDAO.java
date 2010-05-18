package edu.opencampus.lms.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.modelo.Sede;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.modelo.aulavirtual.reporte.MatriculaGDialogo;
import edu.opencampus.lms.modelo.aulavirtual.reporte.MatriculaGLaboratorio;
import edu.opencampus.lms.modelo.aulavirtual.reporte.MatriculaGTrabajo;
import edu.opencampus.lms.modelo.aulavirtual.reporte.MatriculaGTrabajoGrupal;
import edu.opencampus.lms.modelo.aulavirtual.reporte.MatriculaIngreso;
import edu.opencampus.lms.modelo.aulavirtual.reporte.ReporteDetalle;
import edu.opencampus.lms.modelo.reporte.FichaReporte;
import edu.opencampus.lms.modelo.reporte.ReporteCollection;
import edu.opencampus.lms.modelo.reporte.ReporteFiltro;
import edu.opencampus.lms.modelo.reporte.opencampusVirtualReporte;
import edu.opencampus.lms.modelo.reporte.UsuarioReporte;
import edu.opencampus.lms.modelo.reportesecdoc.FichaReporteSecDoc;
import edu.opencampus.lms.modelo.reportesecdoc.ReporteSecDocFiltro;
import edu.opencampus.lms.modelo.reportesecdoc.UsuarioReporteSecDoc;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;
import edu.opencampus.lms.util.Util;

public class ReporteDAO extends BaseDAO {

	protected DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public ReporteCollection buscar(ReporteFiltro filtro) throws DAOException {
		log.info("buscar(ReporteFiltro filtro)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		ResultSet result1 = null;
		Connection cons = null;
		String[] palabras = new String[0];
		ReporteCollection collectionUsuarios = new ReporteCollection();
		UsuarioReporte usuarioReporte = null;
		FichaReporte fichaReporte = null;
		int itemsBusqueda = 0;
		if (0 < filtro.getBusquedaUsuarioNombre().trim().length()) {
			palabras = filtro.getBusquedaUsuarioNombre().trim().split(" ");
		}
		// String query = "insert into
		// cv_temp_usuario(usuario,paterno,materno,nombre1,nombre2) "
		// + "SELECT DISTINCT cvma.usuario,genp.apepaterno,genp.apematerno,"
		// + "genp.nomuno,genp.nomdos FROM cv_matricula cvma, cv_ficha
		// cvfi,cv_rol"
		// + " cvro,seguridad.seg_usuario segu,general.gen_persona genp WHERE
		// cvma"
		// + ".eliminado = '0' AND (cvma.idrol = ? OR cvma.idrol = ?) AND cvma."
		// + "estado = '1' AND cvma.idficha = cvfi.idficha AND cvfi.estado = '1'
		// "
		// + "AND cvfi.idetiqueta != ? AND cvma.idrol = cvro.idrol AND TRIM("
		// + "cvma.usuario) = TRIM(segu.usuario) AND segu.codsujeto =
		// genp.codpersona ";

		// Reparacion del Sentencia SQL

		String query = "insert into cv_temp_usuario(usuario,paterno,materno,nombre1,nombre2) "
				+ " SELECT DISTINCT cvma.usuario,genp.apepaterno,genp.apematerno,"
				+ "genp.nomuno,genp.nomdos  FROM general.gen_persona genp,"
				+ " seguridad.seg_usuario segu, cv_matricula cvma,  cv_ficha cvfi"
				+ " WHERE genp.codpersona = segu.codsujeto AND segu.usuario = cvma.usuario "
				+ " AND cvma.estado = '1' AND cvma.eliminado = '0'"
				+ "  AND cvma.idficha = cvfi.idficha AND cvfi.estado = '1' AND cvfi.idetiqueta != ?"
				+ "  AND(cvma.idrol = ? OR cvma.idrol = ?)";

		if (!"0".equals(filtro.getBusquedaDepartamento())) {
			itemsBusqueda++;
			query += " AND cvfi.codigo_departamento=? ";
		}
		if (!"0".equals(filtro.getBusquedaCiclo())) {
			itemsBusqueda++;
			query += " AND cvfi.codigo_ciclo=? ";
		}
		if (0 != filtro.getBusquedaContacto().length()) {
			itemsBusqueda++;
			query += " AND trim(cvma.codigo_auspiciador)= trim(select codinstitucion from "
					+ "general.gen_institucion where UPPER(TRANSLATE(razsocial,'áéíóúÁÉÍÓÚ'"
					+ ",'aeiouAEIOU')) like ?) ";
		}
		if (0 != filtro.getBusquedaEmpresa().length()) {
			itemsBusqueda++;
			query += " AND trim(cvma.codigo_empresa) in (select trim(codinstitucion) from "
					+ "general.gen_institucion where UPPER(TRANSLATE(razsocial,'áéíóúÁÉÍÓÚ'"
					+ ",'aeiouAEIOU')) like UPPER(TRANSLATE(?,'áéíóúÁÉÍÓÚ'"
					+ ",'aeiouAEIOU'))) ";
		}
		if (0 != filtro.getBusquedaCurso().length()) {
			itemsBusqueda++;
			query += " AND cvfi.codigo_curso = ? ";
		}
		if (!"0".equals(filtro.getBusquedaGrupo())) {
			itemsBusqueda++;
			query += " AND cvma.idrol = ? ";
		}
		if (!"-1".equals(filtro.getBusquedaIngreso())) {
			itemsBusqueda++;
			query += " AND (select count(idingreso) from cv_ingreso where usuario=cvma.usuario"
					+ " and elemento=? and valor=cvfi.idficha)<=? ";
		}
		if (0 != filtro.getBusquedaUsuario().length()) {
			itemsBusqueda++;
			query += " AND cvma.usuario like ? ";
		}
		for (int u = 0; u < palabras.length; u++) {
			itemsBusqueda++;
			query += " AND UPPER(TRANSLATE(cvma.usuario ||' '||genp.APEPATERNO||' '|| genp."
					+ "APEMATERNO ||' '||genp.NOMUNO ||' '||genp.NOMDOS,'áéíóúÁÉÍÓÚ',"
					+ "'aeiouAEIOU')) LIKE UPPER(TRANSLATE(?,'áéíóúÁÉÍÓÚ','aeiouAEIOU'))";
		}
		if (!"0".equals(filtro.getBusquedaFormacion())) {
			itemsBusqueda++;
			query += " AND 0!=(select count(*) from cv_ficha_formacion "
					+ "fifo where fifo.idficha=cvfi.idficha and fifo.codigo=? ) ";
		}
		if (!"0".equals(filtro.getBusquedaPeriodo())) {
			itemsBusqueda++;
			query += " AND cvfi.codigo_periodo=?";
		}
		if (!"0".equals(filtro.getBusquedaSede())) {
			itemsBusqueda++;
			query += " AND pkg_cv_ficha_util.fx_cv_ficha_sede_idficha(cvfi.idficha)=? ";
		}
		if (0 < filtro.getBusquedaFecha1().length()
				&& 0 < filtro.getBusquedaFecha2().length()) {
			itemsBusqueda++;
			query += " AND pkg_cv_ficha.fx_cv_ficha_fechainiciolaboral(cvfi.idficha) between ? and ? ";
		}
		query += " ORDER BY genp.apepaterno,genp.apematerno,genp.nomuno,genp.nomdos,cvma.usuario";
		if (0 == itemsBusqueda) {
			return collectionUsuarios;
		}
		try {
			int posicion = 1;
			cons = (Connection) dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(posicion++, Constante.ETIQUETA_PAPELERA);
			stmt.setInt(posicion++, Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE);
			stmt
					.setInt(posicion++,
							Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE);

			if (!"0".equals(filtro.getBusquedaDepartamento())) {
				stmt.setString(posicion++, filtro.getBusquedaDepartamento());
			}
			if (!"0".equals(filtro.getBusquedaCiclo())) {
				stmt.setString(posicion++, filtro.getBusquedaCiclo());
			}
			if (0 != filtro.getBusquedaContacto().length()) {
				stmt.setString(posicion++, "%" + filtro.getBusquedaContacto()
						+ "%");
			}
			if (0 != filtro.getBusquedaEmpresa().length()) {
				stmt.setString(posicion++, "%" + filtro.getBusquedaEmpresa()
						+ "%");
			}
			if (0 != filtro.getBusquedaCurso().length()) {
				stmt.setString(posicion++, filtro.getBusquedaCurso());
			}
			if (!"0".equals(filtro.getBusquedaGrupo())) {
				stmt.setString(posicion++, filtro.getBusquedaGrupo());
			}
			if (!"-1".equals(filtro.getBusquedaIngreso())) {
				stmt.setInt(posicion++, Constante.ELEMENTO_CURSO);
				stmt.setString(posicion++, filtro.getBusquedaIngreso());
			}
			if (0 != filtro.getBusquedaUsuario().length()) {
				stmt.setString(posicion++, "%" + filtro.getBusquedaUsuario()
						+ "%");
			}
			for (int u = 0; u < palabras.length; u++) {
				stmt.setString(posicion++, "%" + palabras[u] + "%");
			}
			if (!"0".equals(filtro.getBusquedaFormacion())) {
				stmt.setString(posicion++, filtro.getBusquedaFormacion());
			}
			if (!"0".equals(filtro.getBusquedaPeriodo())) {
				stmt.setString(posicion++, filtro.getBusquedaPeriodo());
			}
			if (!"0".equals(filtro.getBusquedaSede())) {
				stmt.setString(posicion++, filtro.getBusquedaSede());
			}
			if (0 < filtro.getBusquedaFecha1().length()
					&& 0 < filtro.getBusquedaFecha2().length()) {
				stmt.setString(posicion++, Formato.setBaseDatosDeDate(Formato
						.setDateDeJSP(filtro.getBusquedaFecha1())));
				stmt.setString(posicion++, Formato.setBaseDatosDeDate(Formato
						.setDateDeJSP(filtro.getBusquedaFecha2())));
			}
			stmt.executeUpdate();
			query = "select count(*) from cv_temp_usuario";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				collectionUsuarios.setCantidad(result.getString(1));
			}
			query = "select usuario,paterno,materno,nombre1,nombre2 "
					+ " from cv_temp_usuario where rowid in (SELECT rowid FROM "
					+ "cv_temp_usuario WHERE rownum<=? "
					+ " minus SELECT rowid FROM cv_temp_usuario WHERE rownum<=?)";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, filtro.getRegistroUltimo());
			stmt.setString(2, filtro.getRegistroPrimero());
			result = (ResultSet) stmt.executeQuery();
			query = "SELECT pkg_cv_ficha.fx_cv_nota_reporte_matricula(cvma.idmatricula)"
					+ " estadoreporte,cvma.idmatricula,cofa.nomcorto familia"
					+ ",pkg_cv_com_producto.fx_cv_com_producto_nombre_id"
					+ "(cvfi.codigo_curso) nombre, pkg_cv_ficha.fx_cv_ficha_ciclo_seccion"
					+ "(cvfi.idficha,cvfi.codigo_ciclo) seccion,cvro.nombre rol,cvro.idrol,"
					+ "nvl(cvpe.nombre,   ' ') periodo,pkg_cv_ficha.fx_cv_ficha_estado_"
					+ "matricula(cvma.idmatricula) estado,to_char(pkg_cv_ficha.fx_cv_"
					+ "ficha_fechainicio(cvma.idmatricula),'DD-MM-YYYY') fecinicio,"
					+ " to_char(pkg_cv_ficha.fx_cv_ficha_fechafin(cvma.idmatricula),"
					+ "'DD-MM-YYYY') fecfin,pkg_cv_ficha.fx_cv_ficha_forma(cvfi.idficha)"
					+ " formacion,pkg_cv_ficha_util.fx_cv_ficha_sede_idficha(cvfi."
					+ "idficha) sede,gede.nombre departamento,nvl(genin.razsocial,' ') turno,"
					+ "(select count(idingreso) from cv_ingreso where usuario=cvma.usuario"
					+ " and elemento=? and valor=cvfi.idficha) ingreso FROM comercial.com_familia"
					+ " cofa,general.gen_institucion genin,cv_matricula cvma,cv_ficha cvfi,"
					+ " cv_rol cvro,seguridad.seg_usuario segu,general.gen_persona genp, general."
					+ "gen_departamento gede,cv_periodo cvpe WHERE trim(cvma.usuario)=trim(?) and "
					+ "cvma.eliminado='0' AND(cvma.idrol=? OR cvma.idrol=?) AND cvma.estado='1'"
					+ " AND cvma.idficha=cvfi.idficha AND cvfi.estado='1' AND cvfi.idetiqueta!=? "
					+ "AND cvma.idrol=cvro.idrol AND TRIM(cvma.usuario)=TRIM(segu.usuario) AND "
					+ "segu.codsujeto=genp.codpersona AND cvfi.codigo_departamento=gede.codigo"
					+ " AND cvfi.codigo_periodo=cvpe.codigo(+) and cvfi.codigo_familia=cofa.codigo"
					+ " AND genin.CODINSTITUCION(+)=cvma.codigo_empresa ";
			if (!"0".equals(filtro.getBusquedaGrupo())) {
				query += " AND cvro.idrol=? ";
			}
			if (!"-1".equals(filtro.getBusquedaIngreso())) {
				query += " AND (select count(idingreso) from cv_ingreso where usuario=cvma.usuario"
						+ " and elemento=? and valor=cvfi.idficha)<=? ";
			}
			if (!"0".equals(filtro.getBusquedaDepartamento())) {
				query += " AND cvfi.codigo_departamento=? ";
			}
			if (!"0".equals(filtro.getBusquedaCiclo())) {
				query += " AND cvfi.codigo_ciclo=? ";
			}
			if (0 != filtro.getBusquedaContacto().length()) {
				query += " AND trim(cvma.codigo_auspiciador)= trim(select codinstitucion from "
						+ "general.gen_institucion from UPPER(TRANSLATE(razsocial,'áéíóúÁÉÍÓÚ'"
						+ ",'aeiouAEIOU')) like ?) ";
			}
			if (0 != filtro.getBusquedaEmpresa().length()) {
				itemsBusqueda++;
				query += " AND trim(cvma.codigo_empresa) in (select trim(codinstitucion) from "
						+ "general.gen_institucion where UPPER(TRANSLATE(razsocial,'áéíóúÁÉÍÓÚ'"
						+ ",'aeiouAEIOU')) like UPPER(TRANSLATE(?,'áéíóúÁÉÍÓÚ'"
						+ ",'aeiouAEIOU'))) ";
			}
			if (0 != filtro.getBusquedaCurso().length()) {
				query += " AND cvfi.codigo_curso=? ";
			}
			if (!"0".equals(filtro.getBusquedaFormacion())) {
				query += " AND 0!=(select count(*) from cv_ficha_formacion "
						+ "fifo where fifo.idficha=cvfi.idficha and fifo.codigo=? ) ";
			}
			if (!"0".equals(filtro.getBusquedaPeriodo())) {
				query += " AND cvfi.codigo_periodo=?";
			}
			if (!"0".equals(filtro.getBusquedaSede())) {
				query += " AND pkg_cv_ficha_util.fx_cv_ficha_sede_idficha(cvfi.idficha)=? ";
			}
			if (0 < filtro.getBusquedaFecha1().length()
					&& 0 < filtro.getBusquedaFecha2().length()) {
				query += " AND pkg_cv_ficha.fx_cv_ficha_fechainiciolaboral(cvfi.idficha) between ? and ?";
			}
			query += " order by fecinicio desc,nombre,idrol";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, Constante.ELEMENTO_CURSO);
			stmt.setInt(3, Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE);
			stmt.setInt(4, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE);
			stmt.setString(5, Constante.ETIQUETA_PAPELERA);
			itemsBusqueda = 6;
			if (!"0".equals(filtro.getBusquedaGrupo())) {
				stmt.setString(itemsBusqueda++, filtro.getBusquedaGrupo());
			}
			if (!"-1".equals(filtro.getBusquedaIngreso())) {
				stmt.setInt(itemsBusqueda++, Constante.ELEMENTO_CURSO);
				stmt.setString(itemsBusqueda++, filtro.getBusquedaIngreso());
			}
			if (!"0".equals(filtro.getBusquedaDepartamento())) {
				stmt.setString(itemsBusqueda++, filtro
						.getBusquedaDepartamento());
			}
			if (!"0".equals(filtro.getBusquedaCiclo())) {
				stmt.setString(itemsBusqueda++, filtro.getBusquedaCiclo());
			}
			if (0 != filtro.getBusquedaContacto().length()) {
				stmt.setString(itemsBusqueda++, "%"
						+ filtro.getBusquedaContacto() + "%");
			}
			if (0 != filtro.getBusquedaEmpresa().length()) {
				stmt.setString(itemsBusqueda++, "%"
						+ filtro.getBusquedaEmpresa() + "%");
			}
			if (0 != filtro.getBusquedaCurso().length()) {
				stmt.setString(itemsBusqueda++, filtro.getBusquedaCurso());
			}
			if (!"0".equals(filtro.getBusquedaFormacion())) {
				stmt.setString(itemsBusqueda++, filtro.getBusquedaFormacion());
			}
			if (!"0".equals(filtro.getBusquedaPeriodo())) {
				stmt.setString(itemsBusqueda++, filtro.getBusquedaPeriodo());
			}
			if (!"0".equals(filtro.getBusquedaSede())) {
				stmt.setString(itemsBusqueda++, filtro.getBusquedaSede());
			}
			if (0 < filtro.getBusquedaFecha1().length()
					&& 0 < filtro.getBusquedaFecha2().length()) {
				stmt.setString(itemsBusqueda++, filtro.getBusquedaFecha1());
				stmt.setString(itemsBusqueda++, filtro.getBusquedaFecha2());
			}
			while (result.next()) {
				usuarioReporte = new UsuarioReporte();
				usuarioReporte.setIdUsuario(result.getString("usuario"));
				usuarioReporte.setPaterno(result.getString("paterno"));
				usuarioReporte.setMaterno(result.getString("materno"));
				usuarioReporte.setNombre1(result.getString("nombre1"));
				usuarioReporte.setNombre2(result.getString("nombre2"));
				stmt.setString(2, result.getString("usuario"));
				result1 = (ResultSet) stmt.executeQuery();
				while (result1.next()) {
					fichaReporte = new FichaReporte();
					fichaReporte.setDepartamento(result1
							.getString("departamento"));
					fichaReporte.setCantidadIngreso(result1.getInt("ingreso"));
					fichaReporte.setFamilia(result1.getString("familia"));
					fichaReporte.setIdMatricula(result1
							.getString("idmatricula"));
					fichaReporte.setEstado(result1.getInt("estado"));
					fichaReporte.setEstadoReporte(result1
							.getString("estadoreporte"));
					fichaReporte.setIdRol(result1.getInt("idrol"));
					fichaReporte.setFechaFin(result1.getString("fecfin"));
					fichaReporte.setFechaInicio(result1.getString("fecinicio"));
					fichaReporte.setFormacion(result1.getString("formacion"));
					fichaReporte.setNombre(result1.getString("nombre"));
					fichaReporte.setPeriodo(result1.getString("periodo"));
					fichaReporte.setRol(result1.getString("rol"));
					fichaReporte.setSeccion(result1.getString("seccion"));
					fichaReporte.setSede(Util.getSucursalNombre(result1
							.getString("sede")));
					fichaReporte.setAuspiciador(result1.getString("turno"));
					usuarioReporte.getFichas().add(fichaReporte);
				}
				collectionUsuarios.getUsuarios().add(usuarioReporte);
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return collectionUsuarios;
	}

	public String obtenerRazSocial(String codigo) throws DAOException {
		log.info("obtenerRazSocial(String codigo)");
		String nombre = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = "SELECT RAZSOCIAL FROM GENERAL.GEN_INSTITUCION WHERE RUC=? AND ESTADO='A'";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, codigo);
			
			result = (ResultSet) stmt.executeQuery();
			
			if (result.next()) {
				nombre = result.getString("RAZSOCIAL");
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return nombre;
	}
	
	public boolean esMiEmpleado(String codigo, int idMatricula) throws DAOException {
		log.info("esMiEmpleado(String "+codigo+", int "+idMatricula+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			String query = "SELECT COUNT(*) EXISTE FROM cv_matricula " +
					"WHERE CODIGO_EMPRESA=(SELECT CODINSTITUCION " +
					"FROM GENERAL.GEN_INSTITUCION " +
					"WHERE RUC=? AND ESTADO='A') AND IDMATRICULA=?";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, codigo);
			stmt.setInt(2, idMatricula);
			
			result = (ResultSet) stmt.executeQuery();
			
			if (result.next()) {
				if(result.getInt("EXISTE")>0){
					return true;
				}
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return false;
	}
	
	public Collection<UsuarioReporteSecDoc> busquedaSecDoc(
			ReporteSecDocFiltro filtro) throws DAOException {
		log.info("busquedaSecDoc(ReporteSecDocFiltro filtro)");
		Collection<UsuarioReporteSecDoc> usuarios = new ArrayList<UsuarioReporteSecDoc>();
		UsuarioReporteSecDoc usuario = null;
		FichaReporteSecDoc ficha = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		ResultSet result1 = null;
		Connection cons = null;
		try {
			String query = "SELECT DISTINCT segu.usuario,pere.codopencampus,genpe.apepaterno,"
					+ "genpe.apematerno,genpe.nomuno,genpe.nomdos FROM cv_matricula cvma,"
					+ "cv_ficha cvfi,seguridad.seg_usuario segu,general.gen_persona genpe,"
					+ "personal.per_empleado pere WHERE cvma.idrol=? AND cvma.eliminado"
					+ "='0' AND cvma.estado='1' AND cvma.idficha = cvfi.idficha AND "
					+ "cvfi.estado = '1' AND cvma.usuario = segu.usuario AND segu."
					+ "codsujeto = genpe.codpersona AND genpe.codpersona = pere.codempleado ";
			if (!"0".equals(filtro.getCodigoFamilia())) {
				query += "  AND cvfi.codigo_familia=?  ";
			}
			if (!"0".equals(filtro.getBusquedaSede())) {
				query += "  AND pkg_cv_ficha_util.fx_cv_ficha_sede_idficha(cvfi.idficha)=?  ";
			}
			if (!"0".equals(filtro.getBusquedaPeriodo())) {
				query += "  AND cvfi.codigo_periodo=?  ";
			}
			if (0 != filtro.getFechaFin1().length()
					&& 0 != filtro.getFechaFin2().length()) {
				query += " AND pkg_cv_ficha.fx_cv_ficha_fechafinlaboral(cvfi.idficha) between ? and ?";
			}
			query += " order by genpe.apepaterno,genpe.apematerno,genpe.nomuno,genpe.nomdos ";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE);
			int posicion = 2;
			if (!"0".equals(filtro.getCodigoFamilia())) {
				stmt.setString(posicion++, filtro.getCodigoFamilia());
			}
			if (!"0".equals(filtro.getBusquedaSede())) {
				stmt.setString(posicion++, filtro.getBusquedaSede());
			}
			if (!"0".equals(filtro.getBusquedaPeriodo())) {
				stmt.setString(posicion++, filtro.getBusquedaPeriodo());
			}
			if (0 != filtro.getFechaFin1().length()
					&& 0 != filtro.getFechaFin2().length()) {
				stmt.setString(posicion++, filtro.getFechaFin1());
				stmt.setString(posicion++, filtro.getFechaFin2());
			}
			result = (ResultSet) stmt.executeQuery();
			query = "SELECT cvma.idmatricula,cvfi.idficha,cvfi.codigo_curso,"
					+ "pkg_cv_ficha.fx_cv_ficha_ciclo_seccion(cvfi.idficha,cvfi.codigo_ciclo) seccion,"
					+ "pkg_cv_com_producto.fx_cv_com_producto_nombre_id(cvfi.codigo_curso) nombre_curso,"
					+ "pkg_cv_ficha_util.fx_cv_ficha_sede_idficha(cvfi.idficha) sede,"
					+ "(SELECT COUNT(*) FROM cv_matricula WHERE idficha=cvma.idficha AND "
					+ "eliminado='0' AND estado='1' AND idrol=?) cantidad,cvfi.turno,nvl"
					+ "(cvpe.nombre,   ' ') periodo,to_char(pkg_cv_ficha.fx_cv_ficha_"
					+ "fechainiciolaboral(cvfi.idficha),'DD-MM-YYYY') inicio,to_char("
					+ "pkg_cv_ficha.fx_cv_ficha_fechafinlaboral(cvfi.idficha),"
					+ "'DD-MM-YYYY') fin FROM cv_matricula cvma,cv_ficha cvfi,cv_periodo"
					+ " cvpe WHERE cvma.idrol=? AND cvma.eliminado='0' AND cvma.estado='1'"
					+ " AND cvma.idficha=cvfi.idficha AND cvfi.estado='1' and trim(cvma."
					+ "usuario)=trim(?) AND cvfi.codigo_periodo=cvpe.codigo(+) ";
			if (!"0".equals(filtro.getCodigoFamilia())) {
				query += "  AND cvfi.codigo_familia=?  ";
			}
			if (!"0".equals(filtro.getBusquedaSede())) {
				query += "  AND pkg_cv_ficha_util.fx_cv_ficha_sede_idficha(cvfi.idficha)=?  ";
			}
			if (!"0".equals(filtro.getBusquedaPeriodo())) {
				query += "  AND cvfi.codigo_periodo=?  ";
			}
			if (0 != filtro.getFechaFin1().length()
					&& 0 != filtro.getFechaFin2().length()) {
				query += " AND pkg_cv_ficha.fx_cv_ficha_fechafinlaboral(cvfi.idficha) between ? and ?";
			}
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE);
			stmt.setInt(2, Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE);
			posicion = 4;
			if (!"0".equals(filtro.getCodigoFamilia())) {
				stmt.setString(posicion++, filtro.getCodigoFamilia());
			}
			if (!"0".equals(filtro.getBusquedaSede())) {
				stmt.setString(posicion++, filtro.getBusquedaSede());
			}
			if (!"0".equals(filtro.getBusquedaPeriodo())) {
				stmt.setString(posicion++, filtro.getBusquedaPeriodo());
			}
			if (0 != filtro.getFechaFin1().length()
					&& 0 != filtro.getFechaFin2().length()) {
				stmt.setString(posicion++, filtro.getFechaFin1());
				stmt.setString(posicion++, filtro.getFechaFin2());
			}
			while (result.next()) {
				usuario = new UsuarioReporteSecDoc();
				usuario.setCodopencampus(result.getString("codopencampus"));
				usuario.setIdUsuario(result.getString("usuario"));
				usuario.setMaterno(result.getString("apematerno"));
				usuario.setPaterno(result.getString("apepaterno"));
				usuario.setNombre1(result.getString("nomuno"));
				usuario.setNombre2(result.getString("nomdos"));
				stmt.setString(3, usuario.getIdUsuario());
				result1 = (ResultSet) stmt.executeQuery();
				while (result1.next()) {
					ficha = new FichaReporteSecDoc();
					ficha.setIdMatricula(result1.getString("idmatricula"));
					ficha.setCantidad(result1.getInt("cantidad"));
					ficha.setCodigoCurso(result1.getString("codigo_curso"));
					ficha.setSeccion(result1.getString("seccion"));
					ficha.setFechaFin(result1.getString("inicio"));
					ficha.setFechaInicio(result1.getString("fin"));
					ficha.setIdFicha(result1.getString("idficha"));
					ficha.setNombreCurso(result1.getString("nombre_curso"));
					ficha.setSede(Util.getSucursalNombre(result1
							.getString("sede")));
					ficha.setTurno(result1.getString("turno"));
					ficha.setPeriodo(result1.getString("periodo"));
					usuario.getFichas().add(ficha);
				}
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return usuarios;
	}

	public Collection<UsuarioReporteSecDoc> busquedaSecDoc(String[] idMatricula)
			throws DAOException {
		log.info("busquedaSecDoc(String[] idFicha)");
		Collection<UsuarioReporteSecDoc> usuarios = new ArrayList<UsuarioReporteSecDoc>();
		UsuarioReporteSecDoc usuario = null;
		FichaReporteSecDoc ficha = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		ResultSet result1 = null;
		Connection cons = null;
		try {
			String query = "INSERT INTO CV_TEMP_CODSECPROD (CODSECPROD) VALUES (?)";
			cons = (Connection) dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt = (PreparedStatement) cons.prepareStatement(query);
			for (int u = 0; u < idMatricula.length; u++) {
				stmt.setString(1, idMatricula[u]);
				stmt.executeUpdate();
			}
			query = "SELECT DISTINCT segu.usuario,pere.codopencampus,genpe.apepaterno,"
					+ "genpe.apematerno,genpe.nomuno,genpe.nomdos FROM cv_matricula cvma,"
					+ "cv_ficha cvfi,seguridad.seg_usuario segu,general.gen_persona genpe,"
					+ "personal.per_empleado pere,cv_temp_codsecprod temco WHERE cvma.idrol=?"
					+ " AND cvma.eliminado='0' AND cvma.estado='1' AND cvma.idmatricula=temco."
					+ "CODSECPROD AND cvma.idficha = cvfi.idficha AND cvfi.estado = '1' AND"
					+ " cvma.usuario = segu.usuario AND segu.codsujeto = genpe.codpersona "
					+ "AND genpe.codpersona = pere.codempleado order by genpe.apepaterno,"
					+ "genpe.apematerno,genpe.nomuno,genpe.nomdos";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE);
			result = (ResultSet) stmt.executeQuery();
			query = "SELECT cvfi.idficha,cvfi.codigo_curso,cvfi.codigo_ciclo||' - '||pkg_cv_ficha."
					+ "fx_cv_ficha_seccion(cvfi.idficha) seccion,pkg_cv_com_producto."
					+ "fx_cv_com_producto_nombre_id(cvfi.codigo_curso) nombre_curso,"
					+ "pkg_cv_ficha_util.fx_cv_ficha_sede_idficha(cvfi.idficha) sede,"
					+ "(SELECT COUNT(*) FROM cv_matricula WHERE idficha=cvma.idficha AND "
					+ "eliminado='0' AND estado='1' AND idrol=?) cantidad,cvfi.turno,nvl"
					+ "(cvpe.nombre,   ' ') periodo,to_char(pkg_cv_ficha.fx_cv_ficha_"
					+ "fechainiciolaboral(cvfi.idficha),'DD-MM-YYYY') inicio,to_char("
					+ "pkg_cv_ficha.fx_cv_ficha_fechafinlaboral(cvfi.idficha),"
					+ "'DD-MM-YYYY') fin FROM cv_matricula cvma,cv_ficha cvfi,cv_periodo"
					+ " cvpe,cv_temp_codsecprod temco WHERE cvma.idrol=? AND cvma.eliminado='0' "
					+ "AND cvma.estado='1' AND cvma.idmatricula=temco.CODSECPROD AND cvma.idficha"
					+ "=cvfi.idficha AND cvfi.estado='1' and trim(cvma."
					+ "usuario)=trim(?) AND cvfi.codigo_periodo=cvpe.codigo(+) order by inicio";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE);
			stmt.setInt(2, Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE);
			while (result.next()) {
				usuario = new UsuarioReporteSecDoc();
				usuario.setCodopencampus(result.getString("codopencampus"));
				usuario.setIdUsuario(result.getString("usuario"));
				usuario.setMaterno(result.getString("apematerno"));
				usuario.setPaterno(result.getString("apepaterno"));
				usuario.setNombre1(result.getString("nomuno"));
				usuario.setNombre2(result.getString("nomdos"));
				stmt.setString(3, usuario.getIdUsuario());
				result1 = (ResultSet) stmt.executeQuery();
				while (result1.next()) {
					ficha = new FichaReporteSecDoc();
					ficha.setCantidad(result1.getInt("cantidad"));
					ficha.setCodigoCurso(result1.getString("codigo_curso"));
					ficha.setSeccion(result1.getString("seccion"));
					ficha.setFechaFin(result1.getString("inicio"));
					ficha.setFechaInicio(result1.getString("fin"));
					ficha.setIdFicha(result1.getString("idficha"));
					ficha.setNombreCurso(result1.getString("nombre_curso"));
					ficha.setSede(Util.getSucursalNombre(result1
							.getString("sede")));
					ficha.setTurno(result1.getString("turno"));
					ficha.setPeriodo(result1.getString("periodo"));
					usuario.getFichas().add(ficha);
				}
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return usuarios;
	}

	public void busquedaSecDocExcel(String[] idMatricula, Usuario usuario,
			String imagen, String fileInput, String fileOut)
			throws DAOException {
		log.info("busquedaSecDoc(String[] idFicha)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
			HashMap<String, String> valores = new HashMap<String, String>();
			valores.put("fechasecdoc", Formato
					.getStringDeDateCompleto(new GregorianCalendar()));
			valores.put("usuariosecdoc", usuario.getNombreCompleto() + " ("
					+ usuario.getIdUsuario() + ") ");
			valores.put("imagensecdoc", imagen);
			String query = "INSERT INTO CV_TEMP_CODSECPROD (CODSECPROD) VALUES (?)";
			cons = (Connection) dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt = (PreparedStatement) cons.prepareStatement(query);
			for (int u = 0; u < idMatricula.length; u++) {
				stmt.setString(1, idMatricula[u]);
				stmt.executeUpdate();
			}
			query = "SELECT pere.codopencampus,pkg_cv_util.fx_cv_formato_nombre(cvma.usuario) "
					+ "nombre,cvfi.codigo_curso codigocurso,cvfi.codigo_ciclo||' - '||pkg_cv_ficha."
					+ "fx_cv_ficha_seccion(cvfi.idficha) seccion, pkg_cv_com_producto."
					+ "fx_cv_com_producto_nombre_id(cvfi.codigo_curso) nombrecurso,(SELECT"
					+ " COUNT(*) FROM cv_matricula WHERE idficha=cvma.idficha AND eliminado"
					+ "='0' AND estado='1' AND idrol=?) cantidad,to_char(pkg_cv_ficha."
					+ "fx_cv_ficha_fechainiciolaboral(cvfi.idficha),'DD-MM-YYYY') inicio,"
					+ "to_char(pkg_cv_ficha.fx_cv_ficha_fechafinlaboral(cvfi.idficha),"
					+ "'DD-MM-YYYY') fin FROM cv_matricula cvma,cv_ficha cvfi,seguridad."
					+ "seg_usuario segu,general.gen_persona genpe,personal.per_empleado"
					+ " pere,CV_TEMP_CODSECPROD teco WHERE teco.CODSECPROD=cvma.idmatricula"
					+ " and cvma.idrol=? AND cvma.eliminado='0' AND cvma.estado='1' AND"
					+ " cvma.idficha=cvfi.idficha AND cvfi.estado='1' AND cvma."
					+ "usuario = segu.usuario AND segu.codsujeto=genpe.codpersona AND "
					+ "genpe.codpersona=pere.codempleado ORDER BY genpe.apepaterno,genpe"
					+ ".apematerno,genpe.nomuno,genpe.nomdos,inicio";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE);
			stmt.setInt(2, Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE);
			result = stmt.executeQuery();
			File file = new File(fileOut);
			JRDataSource resultSetDataSource = new JRResultSetDataSource(result);
			JasperPrint jprint = JasperFillManager.fillReport(fileInput,
					valores, resultSetDataSource);
			JExcelApiExporter xlsExporter = new JExcelApiExporter();
			xlsExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,
					jprint);
			xlsExporter.setParameter(
					JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			xlsExporter.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS,
					Boolean.FALSE);
			xlsExporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE, file);
			xlsExporter.setParameter(
					JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
					Boolean.FALSE);
			xlsExporter.setParameter(
					JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
					Boolean.TRUE);
			xlsExporter.setParameter(
					JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
			xlsExporter.exportReport();
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} catch (JRException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet((ResultSet) result);
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	public Collection<opencampusVirtualReporte> obtenerAuditoriaopencampusVirtual(ReporteFiltro filtro) throws DAOException {
		log.info("obtenerAuditoriaopencampusVirtual(ReporteFiltro filtro)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		ResultSet subResult = null;
		Connection cons = null;
		Collection<opencampusVirtualReporte> auditoria = new ArrayList<opencampusVirtualReporte>();
		Collection<GregorianCalendar> ingresos = null;
		opencampusVirtualReporte registro = null;
		
		try {
			
			StringBuffer deptos = new StringBuffer(160);
			if(filtro.getPermisos() != null){
				deptos.append(" AND ( 1=0 ");
				for (Sede depto : filtro.getPermisos()) {
					deptos.append(" OR (F.CODIGO_DEPARTAMENTO = "+depto.getCodigo()
							+ " AND PKG_cv_ficha_UTIL.FX_cv_ficha_SEDE_IDFICHA(F.IDFICHA) = '"+depto.getNombre()+"') ");
				}
				deptos.append(")");
			}
			//System.out.println("C: "+deptos);
			String query = "SELECT F.IDFICHA,PKG_CV_COM_PRODUCTO.FX_CV_COM_PRODUCTO_NOMBRE_ID(F.CODIGO_CURSO) CURSO," +
					"PKG_cv_ficha.FX_cv_ficha_IDDOCENTE_DEFAULT(F.IDFICHA) IDDOCENTE," +
					"PKG_cv_ficha.FX_cv_ficha_DOCENTE_DEFAULT(F.IDFICHA) DOCENTE," +
					"(SELECT NOMBRE FROM GENERAL.GEN_DEPARTAMENTO WHERE CODIGO=F.CODIGO_DEPARTAMENTO) DEPARTAMENTO," +
					"(SELECT COUNT(*) HECHAS FROM cv_ficha_unidad_RECURSO R, cv_dialogo D " +
					"WHERE R.IDFICHA=D.IDFICHA AND R.IDUNIDAD=D.IDUNIDAD AND R.IDRECURSO=D.IDRECURSO  " +
					"AND R.ESTADO='1' AND R.CALIFICA='1' AND D.ESTADO='1' AND R.IDFICHA=F.IDFICHA " +
					"AND D.IDMATRICULA=PKG_cv_ficha.FX_cv_ficha_IDDOCENTE_DEFAULT(F.IDFICHA) " +
					"AND FECHA BETWEEN TO_DATE(?,'DD/MM/YY HH24:MI') AND TO_DATE(?,'DD/MM/YY HH24:MI')) DIALOGOS " +
					"FROM cv_ficha F WHERE CODIGO_PERIODO=? AND IDETIQUETA != 1000 AND NVL(F.NOAUDITABLE,0) = 0 "+deptos+" ORDER BY DEPARTAMENTO, DOCENTE";
			
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, Formato.setBaseDatosDeDateCompleto(Formato.setDateDeJSPCompleto(filtro.getBusquedaFecha1())));
			stmt.setString(2, Formato.setBaseDatosDeDateCompleto(Formato.setDateDeJSPCompleto(filtro.getBusquedaFecha2())));
			stmt.setString(3, filtro.getBusquedaPeriodo());
			result = (ResultSet) stmt.executeQuery();
			
			query = "SELECT DISTINCT TO_CHAR(FECHA_INGRESO,'yyyy-mm-dd') || ' 00:00:00' FECHA_INGRESO " +
					"FROM CV_INGRESO " +
					"WHERE ELEMENTO=2 " +
					"AND FECHA_INGRESO BETWEEN TO_DATE(?,'DD/MM/YY HH24:MI') AND TO_DATE(?,'DD/MM/YY HH24:MI') " +
					"AND TRIM(USUARIO) = PKG_cv_ficha.FX_cv_ficha_CODOCENTE_DEFAULT(?) " +
					"AND VALOR = ? " +
					"ORDER BY FECHA_INGRESO";
			
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, Formato.setBaseDatosDeDateCompleto(Formato.setDateDeJSPCompleto(filtro.getBusquedaFecha1())));
			stmt.setString(2, Formato.setBaseDatosDeDateCompleto(Formato.setDateDeJSPCompleto(filtro.getBusquedaFecha2())));
			
			
			while (result.next()) {
				registro = new opencampusVirtualReporte();
				registro.setIdFicha(result.getInt("IDFICHA"));
				registro.setCurso(result.getString("CURSO"));
				registro.setDocente(result.getString("DOCENTE"));
				registro.setIdDocente(result.getInt("IDDOCENTE"));
				registro.setDebates(result.getInt("DIALOGOS"));
				registro.setDeparatamento(result.getString("DEPARTAMENTO"));
				
				ingresos = new ArrayList<GregorianCalendar>();
				
				stmt.setInt(3, registro.getIdFicha());
				stmt.setInt(4, registro.getIdFicha());
				subResult = (ResultSet) stmt.executeQuery();
				
				while (subResult.next()) {
					ingresos.add(Formato.getDateDeBaseDatos(subResult.getString("FECHA_INGRESO")));
				}
				
				registro.setIngresos(ingresos);
				
				auditoria.add(registro);
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
		return auditoria;
	}
	
	public Collection<opencampusVirtualReporte> obtenerReporteopencampusVirtual(ReporteFiltro filtro) throws DAOException {
		log.info("obtenerReporteopencampusVirtual(ReporteFiltro filtro)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Collection<opencampusVirtualReporte> auditoria = new ArrayList<opencampusVirtualReporte>();
		opencampusVirtualReporte registro = null;
		
		try {
			String query = "SELECT CVMA.IDMATRICULA," +
					"PKG_CV_UTIL.FX_CV_FORMATO_NOMBRE(CVMA.USUARIO) ALUMNO," +
					"(SELECT NOMBRE FROM COMERCIAL.COM_PRODUCTO WHERE CODIGO=CVFI.CODIGO_CURSO) CURSO," +
					"PKG_cv_ficha.FX_CV_NOTA_REPORTE(CVMA.IDMATRICULA,CVMA.IDROL,CVMA.IDFICHA) ESTADO " +
					"FROM " +
					"cv_matricula CVMA, cv_ficha CVFI " +
					"WHERE " +
					"CVMA.ELIMINADO='0' AND CVMA.IDROL=4  AND CVMA.ESTADO='1' AND CVFI.ESTADO='1' " +
					"AND CVMA.IDFICHA=CVFI.IDFICHA AND CVFI.IDETIQUETA!=1000 AND NVL(CVFI.NOAUDITABLE,0) = 0" +
					"AND CVFI.CODIGO_PERIODO=? " +
					"ORDER BY ALUMNO";
			
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setString(1, filtro.getBusquedaPeriodo());
			result = (ResultSet) stmt.executeQuery();
			
			while (result.next()) {
				registro = new opencampusVirtualReporte();
				registro.setIdFicha(result.getInt("IDMATRICULA"));
				registro.setCurso(result.getString("CURSO"));
				registro.setDocente(result.getString("ALUMNO"));
				registro.setEstado((result.getFloat("ESTADO") >= 10.5)?true:false);
								
				auditoria.add(registro);
			}
			
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return auditoria;
	}
	
	public ReporteDetalle obtenerAuditoriaSemanal(int idMatricula, String fecha1, String fecha2) throws DAOException {
		log.info("obtenerAuditoriaSemanal(int idMatricula:"+idMatricula+", String fecha1:"+fecha1+", String fecha2:"+fecha2+")");
		PreparedStatement stmt = null;
		PreparedStatement stmtT2 = null;
		PreparedStatement stmtT3 = null;
		PreparedStatement stmtT4 = null;
		PreparedStatement stmtT5 = null;

		PreparedStatement stmtL1 = null;
		PreparedStatement stmtL2 = null;
		PreparedStatement stmtL3 = null;
		PreparedStatement stmtL4 = null;
		PreparedStatement stmtL5 = null;

		PreparedStatement stmtG1 = null;
		PreparedStatement stmtG2 = null;
		PreparedStatement stmtG3 = null;
		PreparedStatement stmtG4 = null;
		PreparedStatement stmtG5 = null;
		PreparedStatement stmtG6 = null;
		PreparedStatement stmtG7 = null;
		PreparedStatement stmtG8 = null;

		PreparedStatement stmtD1 = null;
		PreparedStatement stmtD2 = null;

		ResultSet result = null;
		ResultSet subResult = null;
		Connection cons = null;

		Collection<MatriculaGLaboratorio> recursoLaboratorio = new ArrayList<MatriculaGLaboratorio>();
		Collection<MatriculaGDialogo> recursoDialogo = new ArrayList<MatriculaGDialogo>();
		Collection<MatriculaGTrabajo> recursoTrabajo = new ArrayList<MatriculaGTrabajo>();
		Collection<MatriculaGTrabajoGrupal> recursoTrabajoGrupal = new ArrayList<MatriculaGTrabajoGrupal>();

		MatriculaGLaboratorio laboratorio = null;
		MatriculaGDialogo dialogo = null;
		MatriculaGTrabajo trabajo = null;
		MatriculaGTrabajoGrupal grupal = null;

		ReporteDetalle reporte = new ReporteDetalle();
		MatriculaIngreso mingreso;
		try {
			String query = "SELECT  COUNT(*) cantidad,to_char(fecha_ingreso,"
					+ "'YYYY/MM/DD')||' 12:00:00' fecha_ingreso FROM cv_ingreso WHERE usuario ="
					+ "(SELECT usuario FROM cv_matricula WHERE idmatricula=?) "
					+ " AND elemento=? AND fecha_ingreso>=pkg_cv_ficha.fx_cv_ficha_fechainicio(?)"
					+ " AND fecha_ingreso <= pkg_cv_ficha.fx_cv_ficha_fechafin(?) "
					+ "and valor=(select idficha from cv_matricula where idmatricula=?) " +
							"AND FECHA_INGRESO BETWEEN TO_DATE(?,'DD/MM/YY HH24:MI') AND TO_DATE(?,'DD/MM/YY HH24:MI')" +
							" GROUP BY "
					+ "to_char(fecha_ingreso, 'YYYY/MM/DD') ORDER BY 2";
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idMatricula);
			stmt.setInt(2, Constante.ELEMENTO_CURSO);
			stmt.setInt(3, idMatricula);
			stmt.setInt(4, idMatricula);
			stmt.setInt(5, idMatricula);
			stmt.setString(6, fecha1);
			stmt.setString(7, fecha2);
			result = (ResultSet) stmt.executeQuery();
			while (result.next()) {
				mingreso = new MatriculaIngreso();
				mingreso.setCantidad(result.getInt("cantidad"));
				mingreso.setFecha(Formato.getDateDeBaseDatos(result
						.getString("fecha_ingreso")));
				reporte.getNumeroIngreso().add(mingreso);
			}
			query = "select trim(ma.usuario) idusuario,gepe.nomuno,gepe.nomdos,gepe."
					+ "apepaterno,gepe.apematerno,pkg_cv_com_producto.fx_cv_com_producto_nombre_id(ma.codigo_formacion) nombreformacion,"
					+ "pkg_cv_com_producto.fx_cv_com_producto_nombre_id(cvfi.codigo_curso) nombrecurso,pkg_cv_ficha.fx_cv_ficha_"
					+ "fechainicio(ma.idmatricula) feini,pkg_cv_ficha.fx_cv_aula_formasec(ma.idficha)"
					+ " formasec,pkg_cv_ficha.fx_cv_ficha_fechafin(ma.idmatricula)"
					+ " fefin,(SELECT  COUNT(*) cantidad FROM cv_ingreso WHERE usuario ="
					+ "(SELECT usuario FROM cv_matricula WHERE idmatricula=ma.idmatricula) "
					+ " AND elemento=? AND fecha_ingreso>=pkg_cv_ficha.fx_cv_ficha_fechainicio(ma.idmatricula)"
					+ " AND fecha_ingreso <= pkg_cv_ficha.fx_cv_ficha_fechafin(ma.idmatricula) "
					+ "and valor=(select idficha from cv_matricula where idmatricula = ma.idmatricula)" +
							"AND FECHA_INGRESO BETWEEN TO_DATE(?,'DD/MM/YY HH24:MI') AND TO_DATE(?,'DD/MM/YY HH24:MI') " +
							") cantidadingreso"
					+ ",pkg_cv_ficha.fx_cv_ficha_cantdias(ma.idmatricula) cantidadclases,(SELECT "
					+ "count(U.IDUNIDAD) FROM CV_UNIDAD U,CV_UNIDAD_SILABO US,cv_ficha_unidad  FU "
					+ "WHERE US.IDSILABO=FU.IDSILABO AND US.IDUNIDAD=FU.IDUNIDAD AND U.IDUNIDAD=US."
					+ "IDUNIDAD AND IDFICHA=ma.idficha AND FU.ESTADO=1) cantidadunidad from "
					+ "cv_matricula ma,seguridad.seg_usuario seus,cv_ficha cvfi,"
					+ "general.gen_persona gepe where ma.idmatricula=? and ma.usuario=seus"
					+ ".usuario and gepe.codpersona=seus.codsujeto and cvfi.idficha=ma.idficha";
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, Constante.ELEMENTO_CURSO);
			stmt.setString(2, fecha1);
			stmt.setString(3, fecha2);
			stmt.setInt(4, idMatricula);
			result = (ResultSet) stmt.executeQuery();
			if (result.next()) {
				reporte.setCantidadIngreso(result.getInt("cantidadingreso"));
				reporte.setFecha1(Formato.getDateDeBaseDatos(result
						.getString("feini")));
				reporte.setFecha2(Formato.getDateDeBaseDatos(result
						.getString("fefin")));
				reporte.setNombreCompleto(Formato.formatoNombreCompletoJSP(
						result.getString("nomuno"), result.getString("nomdos"),
						result.getString("apepaterno"), result
								.getString("apematerno")));
				reporte.setNombreUsuarioReporte(Formato.formatoTexto(result
						.getString("nomuno"))
						+ " "
						+ Formato.formatoTexto(result.getString("apepaterno")));
				reporte.setIdUsuario(result.getString("idusuario"));
				reporte.setNombreCurso(result.getString("nombrecurso"));
				reporte.setNombreEspecialidad(result
						.getString("nombreformacion"));
				reporte.setCantidadClases(result.getInt("cantidadclases"));
				reporte.setFormacionSeccion(result.getString("formasec"));
				reporte.setCantidadUnidades(result.getInt("cantidadunidad"));
			}

			// / NOTAS
			// *************************************************************

			query = "SELECT IDFICHA FROM cv_matricula WHERE IDMATRICULA=?";

			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, idMatricula);
			result = (ResultSet) stmt.executeQuery();

			if (result.next()) {

				int idFicha = result.getInt("IDFICHA");

				query = "SELECT US.IDUNIDAD FROM cv_ficha_unidad FU, CV_UNIDAD_SILABO US  "
						+ "WHERE US.IDSILABO=FU.IDSILABO AND US.IDUNIDAD=FU.IDUNIDAD "
						+ "AND FU.IDFICHA=? AND FU.ESTADO=1 ORDER BY US.INDICE";

				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt.setInt(1, idFicha);
				result = (ResultSet) stmt.executeQuery();

				// INDIVIDUAL ******
				query = "SELECT COUNT(*) TOTAL "
						+ "FROM cv_ficha_unidad_RECURSO R, CV_TRABAJO_INDIVIDUAL_NOTA N, CV_TRABAJO_INDIVIDUAL T "
						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
						+ "AND T.IDTRABAJO=N.IDTRABAJO "
						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";

				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt.setInt(1, idFicha);

				query = "SELECT T.FECHA_ACTIVACION, T.FECHA_ENTREGA "
						+ "FROM cv_ficha_unidad_RECURSO R, CV_TRABAJO_INDIVIDUAL T "
						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";

				stmtT2 = (PreparedStatement) cons.prepareStatement(query);
				stmtT2.setInt(1, idFicha);

				query = "SELECT COUNT(*) CALIFICADOS "
						+ "FROM cv_ficha_unidad_RECURSO R, CV_TRABAJO_INDIVIDUAL_NOTA N, CV_TRABAJO_INDIVIDUAL T "
						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
						+ "AND T.IDTRABAJO=N.IDTRABAJO AND N.NOTA IS NOT NULL "
						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";

				stmtT3 = (PreparedStatement) cons.prepareStatement(query);
				stmtT3.setInt(1, idFicha);

				query = "SELECT COUNT(DISTINCT I.IDMATRICULA) ENTREGADOS "
						+ "FROM cv_ficha_unidad_RECURSO R, CV_TRABAJO_INDIVIDUAL_INTER I, CV_TRABAJO_INDIVIDUAL T "
						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
						+ "AND T.IDTRABAJO=I.IDTRABAJO AND I.IDMATRICULA=I.IDMATRICULA_ENVIO AND I.ARCHIVO_NOMBRE IS NOT NULL "
						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";

				stmtT4 = (PreparedStatement) cons.prepareStatement(query);
				stmtT4.setInt(1, idFicha);

				query = "SELECT COUNT(*) EXPIRADOS "
						+ "FROM cv_ficha_unidad_RECURSO R, CV_TRABAJO_INDIVIDUAL_INTER I, CV_TRABAJO_INDIVIDUAL T "
						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
						+ "AND T.IDTRABAJO=I.IDTRABAJO AND T.FECHA_ENTREGA - I.FECHA_CREACION <0 "
						+ "AND I.INTERACCION=(SELECT MIN(INTERACCION) FROM CV_TRABAJO_INDIVIDUAL_INTER "
						+ "WHERE IDTRABAJO=I.IDTRABAJO AND IDMATRICULA=I.IDMATRICULA AND IDMATRICULA_ENVIO=I.IDMATRICULA AND ARCHIVO_NOMBRE IS NOT NULL) "
						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";

				stmtT5 = (PreparedStatement) cons.prepareStatement(query);
				stmtT5.setInt(1, idFicha);

				// LABORATORIO ******
				query = "SELECT COUNT(*) TOTAL "
						+ "FROM cv_ficha_unidad_RECURSO R, CV_LABORATORIO_NOTA N, CV_LABORATORIO T "
						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
						+ "AND T.IDTRABAJO=N.IDTRABAJO "
						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";

				stmtL1 = (PreparedStatement) cons.prepareStatement(query);
				stmtL1.setInt(1, idFicha);

				query = "SELECT T.FECHA_ACTIVACION, T.FECHA_ENTREGA "
						+ "FROM cv_ficha_unidad_RECURSO R, CV_LABORATORIO T "
						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";

				stmtL2 = (PreparedStatement) cons.prepareStatement(query);
				stmtL2.setInt(1, idFicha);

				query = "SELECT COUNT(*) CALIFICADOS "
						+ "FROM cv_ficha_unidad_RECURSO R, CV_LABORATORIO_NOTA N, CV_LABORATORIO T "
						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
						+ "AND T.IDTRABAJO=N.IDTRABAJO AND N.NOTA IS NOT NULL "
						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";

				stmtL3 = (PreparedStatement) cons.prepareStatement(query);
				stmtL3.setInt(1, idFicha);

				query = "SELECT COUNT(DISTINCT I.IDMATRICULA) ENTREGADOS "
						+ "FROM cv_ficha_unidad_RECURSO R, CV_LABORATORIO_INTER I, CV_LABORATORIO T "
						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
						+ "AND T.IDTRABAJO=I.IDTRABAJO AND I.IDMATRICULA=I.IDMATRICULA_ENVIO AND I.ARCHIVO_NOMBRE IS NOT NULL "
						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";

				stmtL4 = (PreparedStatement) cons.prepareStatement(query);
				stmtL4.setInt(1, idFicha);

				query = "SELECT COUNT(*) EXPIRADOS "
						+ "FROM cv_ficha_unidad_RECURSO R, CV_LABORATORIO_INTER I, CV_LABORATORIO T "
						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
						+ "AND T.IDTRABAJO=I.IDTRABAJO AND T.FECHA_ENTREGA - I.FECHA_CREACION <0 "
						+ "AND I.INTERACCION=(SELECT MIN(INTERACCION) FROM CV_LABORATORIO_INTER "
						+ "WHERE IDTRABAJO=I.IDTRABAJO AND IDMATRICULA=I.IDMATRICULA AND IDMATRICULA_ENVIO=I.IDMATRICULA AND ARCHIVO_NOMBRE IS NOT NULL) "
						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";

				stmtL5 = (PreparedStatement) cons.prepareStatement(query);
				stmtL5.setInt(1, idFicha);

				// GRUPAL ******
				query = "SELECT COUNT(*) TOTAL "
						+ "FROM cv_ficha_unidad_RECURSO R, CV_TRABAJO_GRUPAL T, CV_GRUPO_TRABAJO G "
						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
						+ "AND T.IDTRABAJO=G.IDTRABAJO AND G.ESTADO='1' "
						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";

				stmtG1 = (PreparedStatement) cons.prepareStatement(query);
				stmtG1.setInt(1, idFicha);

				query = "SELECT T.FECHA_ACTIVACION, T.FECHA_ENTREGA "
						+ "FROM cv_ficha_unidad_RECURSO R, CV_TRABAJO_GRUPAL T "
						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";

				stmtG2 = (PreparedStatement) cons.prepareStatement(query);
				stmtG2.setInt(1, idFicha);

				query = "SELECT COUNT(N.NOTA_PROMEDIO) CALIFICADOS, COUNT(*) TOTAL "
						+ "FROM cv_ficha_unidad_RECURSO R, CV_TRABAJO_GRUPAL T, CV_GRUPO_TRABAJO G, CV_GRUPO_TRABAJO_MATRICULA N "
						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
						+ "AND T.IDTRABAJO=G.IDTRABAJO AND G.IDTRABAJO=N.IDTRABAJO AND G.IDGRUPO=N.IDGRUPO AND G.ESTADO='1' "
						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";

				stmtG3 = (PreparedStatement) cons.prepareStatement(query);
				stmtG3.setInt(1, idFicha);

				query = "SELECT COUNT(DISTINCT M.IDGRUPO) ENTREGADOS "
						+ "FROM cv_ficha_unidad_RECURSO R, CV_TRABAJO_GRUPAL T, CV_GRUPO_TRABAJO G, CV_GRUPO_TRABAJO_MSG M "
						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
						+ "AND T.IDTRABAJO=G.IDTRABAJO AND G.IDTRABAJO=M.IDTRABAJO AND G.IDGRUPO=M.IDGRUPO AND G.ESTADO='1' AND M.ARCHIVO_NOMBRE IS NOT NULL "
						+ "AND IDMATRICULA_EMISOR IN (SELECT IDMATRICULA FROM CV_GRUPO_TRABAJO_MATRICULA WHERE IDTRABAJO=G.IDTRABAJO AND IDGRUPO=G.IDGRUPO) "
						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";

				stmtG4 = (PreparedStatement) cons.prepareStatement(query);
				stmtG4.setInt(1, idFicha);

				query = "SELECT COUNT(*) EXPIRADOS "
						+ "FROM cv_ficha_unidad_RECURSO R, CV_TRABAJO_GRUPAL T, CV_GRUPO_TRABAJO G, CV_GRUPO_TRABAJO_MSG M "
						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
						+ "AND T.IDTRABAJO=G.IDTRABAJO AND G.IDTRABAJO=M.IDTRABAJO AND G.IDGRUPO=M.IDGRUPO AND G.ESTADO='1' AND T.FECHA_ENTREGA - M.FECHA_CREACION <0 "
						+ "AND M.IDMENSAJE = (SELECT MIN(IDMENSAJE) FROM CV_GRUPO_TRABAJO_MSG WHERE IDTRABAJO=G.IDTRABAJO AND IDGRUPO=G.IDGRUPO "
						+ "AND M.ARCHIVO_NOMBRE IS NOT NULL AND IDMATRICULA_EMISOR IN (SELECT IDMATRICULA FROM CV_GRUPO_TRABAJO_MATRICULA WHERE IDTRABAJO=G.IDTRABAJO AND IDGRUPO=G.IDGRUPO)) "
						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";

				stmtG5 = (PreparedStatement) cons.prepareStatement(query);
				stmtG5.setInt(1, idFicha);

				query = "SELECT COUNT(*) ASIGNADOS "
						+ "FROM cv_ficha_unidad_RECURSO R, CV_TRABAJO_GRUPAL T, CV_GRUPO_TRABAJO G "
						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1' "
						+ "AND T.IDTRABAJO=G.IDTRABAJO AND G.ESTADO='1' AND G.ARCHIVO_NOMBRE IS NOT NULL "
						+ "AND T.IDFICHA=? AND T.IDUNIDAD=?";

				stmtG6 = (PreparedStatement) cons.prepareStatement(query);
				stmtG6.setInt(1, idFicha);

				query = "SELECT COUNT(*) DEBATE_TOTAL "
						+ "FROM cv_ficha_unidad_RECURSO R, cv_debate D, CV_TRABAJO_GRUPAL T, CV_GRUPO_TRABAJO G "
						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1'  "
						+ "AND T.IDTRABAJO=G.IDTRABAJO AND G.IDTRABAJO=D.IDTRABAJO AND G.IDGRUPO=D.IDGRUPO AND D.ESTADO='1' AND G.ESTADO='1' "
						+ "AND R.IDFICHA=? AND R.IDUNIDAD=? "
						+ "AND D.FECHA BETWEEN TO_DATE(?,'DD/MM/YY HH24:MI') AND TO_DATE(?,'DD/MM/YY HH24:MI')";

				stmtG7 = (PreparedStatement) cons.prepareStatement(query);
				stmtG7.setInt(1, idFicha);
				stmtG7.setString(3, fecha1);
				stmtG7.setString(4, fecha2);

				query = "SELECT COUNT(*) DEBATE_HECHAS "
						+ "FROM cv_ficha_unidad_RECURSO R, cv_debate D, CV_TRABAJO_GRUPAL T, CV_GRUPO_TRABAJO G "
						+ "WHERE R.IDFICHA=T.IDFICHA AND R.IDUNIDAD=T.IDUNIDAD AND R.IDRECURSO=T.IDRECURSO AND R.ESTADO='1' AND R.CALIFICA='1'  "
						+ "AND T.IDTRABAJO=G.IDTRABAJO AND G.IDTRABAJO=D.IDTRABAJO AND G.IDGRUPO=D.IDGRUPO AND D.ESTADO='1' AND G.ESTADO='1' "
						+ "AND R.IDFICHA=? AND IDMATRICULA=? AND R.IDUNIDAD=? "
						+ "AND D.FECHA BETWEEN TO_DATE(?,'DD/MM/YY HH24:MI') AND TO_DATE(?,'DD/MM/YY HH24:MI')";

				stmtG8 = (PreparedStatement) cons.prepareStatement(query);
				stmtG8.setInt(1, idFicha);
				stmtG8.setInt(2, idMatricula);
				stmtG8.setString(4, fecha1);
				stmtG8.setString(5, fecha2);

				// DIALOGO ******
				query = "SELECT COUNT(*) TOTAL "
						+ "FROM cv_ficha_unidad_RECURSO R, cv_dialogo D "
						+ "WHERE R.IDFICHA=D.IDFICHA AND R.IDUNIDAD=D.IDUNIDAD AND R.IDRECURSO=D.IDRECURSO  "
						+ "AND R.ESTADO='1' AND R.CALIFICA='1' AND D.ESTADO='1' AND R.IDFICHA=? AND R.IDUNIDAD=? "
						+ "AND FECHA BETWEEN TO_DATE(?,'DD/MM/YY HH24:MI') AND TO_DATE(?,'DD/MM/YY HH24:MI')";

				stmtD1 = (PreparedStatement) cons.prepareStatement(query);
				stmtD1.setInt(1, idFicha);
				stmtD1.setString(3, fecha1);
				stmtD1.setString(4, fecha2);

				query = "SELECT COUNT(*) HECHAS "
						+ "FROM cv_ficha_unidad_RECURSO R, cv_dialogo D "
						+ "WHERE R.IDFICHA=D.IDFICHA AND R.IDUNIDAD=D.IDUNIDAD AND R.IDRECURSO=D.IDRECURSO  "
						+ "AND R.ESTADO='1' AND R.CALIFICA='1' AND D.ESTADO='1' AND R.IDFICHA=? AND D.IDMATRICULA=? AND R.IDUNIDAD=? "
						+ "AND FECHA BETWEEN TO_DATE(?,'DD/MM/YY HH24:MI') AND TO_DATE(?,'DD/MM/YY HH24:MI')";

				stmtD2 = (PreparedStatement) cons.prepareStatement(query);
				stmtD2.setInt(1, idFicha);
				stmtD2.setInt(2, idMatricula);
				stmtD2.setString(4, fecha1);
				stmtD2.setString(5, fecha2);

				// EL BUCLE MALDITO **********
				while (result.next()) {

					// INDIVIDUAL ******
					trabajo = new MatriculaGTrabajo();

					stmt.setInt(2, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmt.executeQuery();
					if (subResult.next()) {
						trabajo.setTotal(subResult.getInt("TOTAL"));
					}

					stmtT2.setInt(2, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmtT2.executeQuery();
					if (subResult.next()) {
						if (subResult.getString("FECHA_ACTIVACION") != null) {
							trabajo.setPublicado("Pub");
							// trabajo.setFechaActivacion(Formato
							// .getStringDeDate(Formato
							// .getDateDeBaseDatos(subResult
							// .getString("FECHA_ACTIVACION"))));
							// trabajo
							// .setFechaEntrega(Formato
							// .getStringDeDate(Formato
							// .getDateDeBaseDatos(subResult
							// .getString("FECHA_ENTREGA"))));
						} else {
							trabajo.setPublicado("NP");
						}
					}

					stmtT3.setInt(2, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmtT3.executeQuery();
					if (subResult.next()) {
						trabajo.setCalificados(subResult.getInt("CALIFICADOS"));
					}

					stmtT4.setInt(2, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmtT4.executeQuery();
					if (subResult.next()) {
						trabajo.setEntregados(subResult.getInt("ENTREGADOS"));
					}

					stmtT5.setInt(2, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmtT5.executeQuery();
					if (subResult.next()) {
						trabajo.setExpirados(subResult.getInt("EXPIRADOS"));
					}

					recursoTrabajo.add(trabajo);

					// LABORATORIO ******
					laboratorio = new MatriculaGLaboratorio();

					stmtL1.setInt(2, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmtL1.executeQuery();
					if (subResult.next()) {
						laboratorio.setTotal(subResult.getInt("TOTAL"));
					}

					stmtL2.setInt(2, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmtL2.executeQuery();
					if (subResult.next()) {
						if (subResult.getString("FECHA_ACTIVACION") != null) {
							laboratorio.setPublicado("Pub");
							// laboratorio
							// .setFechaActivacion(Formato
							// .getStringDeDate(Formato
							// .getDateDeBaseDatos(subResult
							// .getString("FECHA_ACTIVACION"))));
							// laboratorio
							// .setFechaEntrega(Formato
							// .getStringDeDate(Formato
							// .getDateDeBaseDatos(subResult
							// .getString("FECHA_ENTREGA"))));
						} else {
							laboratorio.setPublicado("NP");
						}
					}

					stmtL3.setInt(2, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmtL3.executeQuery();
					if (subResult.next()) {
						laboratorio.setCalificados(subResult
								.getInt("CALIFICADOS"));
					}

					stmtL4.setInt(2, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmtL4.executeQuery();
					if (subResult.next()) {
						laboratorio.setEntregados(subResult
								.getInt("ENTREGADOS"));
					}

					stmtL5.setInt(2, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmtL5.executeQuery();
					if (subResult.next()) {
						laboratorio.setExpirados(subResult.getInt("EXPIRADOS"));
					}

					recursoLaboratorio.add(laboratorio);

					// GRUPAL ******
					grupal = new MatriculaGTrabajoGrupal();

					stmtG1.setInt(2, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmtG1.executeQuery();
					if (subResult.next()) {
						grupal.setTotal(subResult.getInt("TOTAL"));
					}

					stmtG2.setInt(2, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmtG2.executeQuery();
					if (subResult.next()) {
						if (subResult.getString("FECHA_ACTIVACION") != null) {
							grupal.setPublicado("Pub");
							// grupal
							// .setFechaActivacion(Formato
							// .getStringDeDate(Formato
							// .getDateDeBaseDatos(subResult
							// .getString("FECHA_ACTIVACION"))));
							// grupal
							// .setFechaEntrega(Formato
							// .getStringDeDate(Formato
							// .getDateDeBaseDatos(subResult
							// .getString("FECHA_ENTREGA"))));
						} else {
							grupal.setPublicado("NP");
						}
					}

					stmtG3.setInt(2, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmtG3.executeQuery();
					if (subResult.next()) {
						grupal.setCalificados(subResult.getInt("CALIFICADOS"));
						grupal.setEstudianteTotal(subResult.getInt("TOTAL"));
					}

					stmtG4.setInt(2, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmtG4.executeQuery();
					if (subResult.next()) {
						grupal.setEntregados(subResult.getInt("ENTREGADOS"));
					}

					stmtG5.setInt(2, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmtG5.executeQuery();
					if (subResult.next()) {
						grupal.setExpirados(subResult.getInt("EXPIRADOS"));
					}

					stmtG6.setInt(2, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmtG6.executeQuery();
					if (subResult.next()) {
						grupal.setAsignados(subResult.getInt("ASIGNADOS"));
					}

					stmtG7.setInt(2, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmtG7.executeQuery();
					if (subResult.next()) {
						grupal.setIntervencionTotal(subResult
								.getInt("DEBATE_TOTAL"));
					}

					stmtG8.setInt(3, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmtG8.executeQuery();
					if (subResult.next()) {
						grupal.setIntervencion(subResult
								.getInt("DEBATE_HECHAS"));
					}

					recursoTrabajoGrupal.add(grupal);

					// DIALOGO ******
					dialogo = new MatriculaGDialogo();

					stmtD1.setInt(2, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmtD1.executeQuery();

					if (subResult.next()) {
						dialogo.setIntervencionTotal(subResult.getInt("TOTAL"));
					}
					stmtD2.setInt(3, result.getInt("IDUNIDAD"));
					subResult = (ResultSet) stmtD2.executeQuery();
					if (subResult.next()) {
						dialogo.setIntervencion(subResult.getInt("HECHAS"));
					}
					recursoDialogo.add(dialogo);
				}

				reporte.setRecursoTrabajo(recursoTrabajo);
				reporte.setRecursoLaboratorio(recursoLaboratorio);
				reporte.setRecursoTrabajoGrupal(recursoTrabajoGrupal);
				reporte.setRecursoDialogo(recursoDialogo);

				// Avisos y Lecturas

				query = "SELECT (SELECT COUNT(*) FROM cv_matricula M, CV_PUBLICACION_FICHA P "
						+ "WHERE TRIM(M.USUARIO)=P.USUARIO AND M.IDFICHA=P.IDFICHA AND P.IDHERRAMIENTA=1 AND M.ELIMINADO=0 AND P.ESTADO=0 "
						+ "AND P.IDFICHA=? AND IDMATRICULA=? "
						+ "AND FECHA BETWEEN TO_DATE(?,'DD/MM/YY HH24:MI') AND TO_DATE(?,'DD/MM/YY HH24:MI')) AVISOS, "
						+ "(SELECT COUNT(*) FROM cv_matricula M, CV_PUBLICACION_FICHA P "
						+ "WHERE TRIM(M.USUARIO)=P.USUARIO AND M.IDFICHA=P.IDFICHA AND P.IDHERRAMIENTA=1 AND M.ELIMINADO=0 AND P.ESTADO=0 "
						+ "AND P.IDFICHA=? "
						+ "AND FECHA BETWEEN TO_DATE(?,'DD/MM/YY HH24:MI') AND TO_DATE(?,'DD/MM/YY HH24:MI')) AVISOSTOTAL,"
						+ "(SELECT COUNT(*) FROM cv_matricula M, CV_PUBLICACION_FICHA P "
						+ "WHERE TRIM(M.USUARIO)=P.USUARIO AND M.IDFICHA=P.IDFICHA AND P.IDHERRAMIENTA=2 AND M.ELIMINADO=0 AND P.ESTADO=0 "
						+ "AND P.IDFICHA=? AND IDMATRICULA=? "
						+ "AND FECHA BETWEEN TO_DATE(?,'DD/MM/YY HH24:MI') AND TO_DATE(?,'DD/MM/YY HH24:MI')) LECTURAS,"
						+ "(SELECT COUNT(*) "
						+ "FROM cv_matricula M, CV_PUBLICACION_FICHA P "
						+ "WHERE TRIM(M.USUARIO)=P.USUARIO AND M.IDFICHA=P.IDFICHA AND P.IDHERRAMIENTA=2 AND M.ELIMINADO=0 AND P.ESTADO=0 "
						+ "AND P.IDFICHA=? "
						+ "AND FECHA BETWEEN TO_DATE(?,'DD/MM/YY HH24:MI') AND TO_DATE(?,'DD/MM/YY HH24:MI')) LECTURASTOTAL FROM DUAL";
				stmt = (PreparedStatement) cons.prepareStatement(query);
				stmt.setInt(1, idFicha);
				stmt.setInt(2, idMatricula);
				stmt.setString(3, fecha1);
				stmt.setString(4, fecha2);
				
				stmt.setInt(5, idFicha);
				stmt.setString(6, fecha1);
				stmt.setString(7, fecha2);
				
				stmt.setInt(8, idFicha);
				stmt.setInt(9, idMatricula);
				stmt.setString(10, fecha1);
				stmt.setString(11, fecha2);
				
				stmt.setInt(12, idFicha);
				stmt.setString(13, fecha1);
				stmt.setString(14, fecha2);
				
				result = (ResultSet) stmt.executeQuery();

				if (result.next()) {
					reporte.setAlerta(result.getInt("AVISOS"));
					reporte.setTotalAlerta(result.getInt("AVISOSTOTAL"));
					reporte.setLectura(result.getInt("LECTURAS"));
					reporte.setTotalLectura(result.getInt("LECTURASTOTAL"));
				}

			}

		} catch (SQLException e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(subResult);
			closeResultSet(result);
			closeStatement(stmt);

			closeStatement(stmtT2);
			closeStatement(stmtT3);
			closeStatement(stmtT4);
			closeStatement(stmtT5);
			closeStatement(stmtL1);
			closeStatement(stmtL2);
			closeStatement(stmtL3);
			closeStatement(stmtL4);
			closeStatement(stmtL5);
			closeStatement(stmtG1);
			closeStatement(stmtG2);
			closeStatement(stmtG3);
			closeStatement(stmtG4);
			closeStatement(stmtG5);
			closeStatement(stmtG6);
			closeStatement(stmtG7);
			closeStatement(stmtG8);
			closeStatement(stmtD1);
			closeStatement(stmtD2);
			closeConnection(cons);
		}
		return reporte;
	}

}
