package edu.opencampus.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.modelo.Periodo;
import edu.opencampus.lms.modelo.ficha.informe.FichaInforme;
import edu.opencampus.lms.util.Formato;

public class FichaInformeDAO extends HibernateDaoSupport {

	protected Log log = LogFactory.getLog(getClass());

	protected DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void persistir(FichaInforme modelo) throws DAOException {
		log.info("persistir()");
		try {
			getHibernateTemplate().saveOrUpdate(modelo);
			getHibernateTemplate().flush();
		} catch (Exception e) {
			log.error(e.toString());
			throw new DAOException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public FichaInforme obtener(Integer codcurso, Integer codperiodo) throws DAOException {
		log.info("obtener() " + codcurso+" - "+codperiodo);
		FichaInforme modelo = null;
		Object[] values = { codcurso, codperiodo };
		try {
			List informe = getHibernateTemplate().find("from FichaInforme a where a.codcurso = ? and a.codperiodo = ? ", values);
			if(!informe.isEmpty())modelo = (FichaInforme )informe.get(0);
		} catch (Exception e) {
			log.error(e.toString());
			throw new DAOException(e.getMessage());
		}
		return modelo;
	}

	@SuppressWarnings("unchecked")
	public void eliminar(Integer codcurso, Integer codperiodo) throws DAOException {
		log.info("eliminar() " + codcurso+" - "+codperiodo);
		Object[] values = { codcurso, codperiodo };
		try {
			List informe = getHibernateTemplate().find("from FichaInforme a where a.codcurso = ? and a.codperiodo = ? ", values);
			if(!informe.isEmpty())getHibernateTemplate().delete(informe.get(0));
		} catch (Exception e) {
			log.error(e.toString());
			throw new DAOException(e.getMessage());
		}
	}
	
	public Collection<Periodo> listarHistoricos(Integer codcurso) throws DAOException {
		log.info("listarHistoricos() " + codcurso);
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		Collection<Periodo> periodos = new ArrayList<Periodo>();
		Periodo periodo = null;

		String query = "SELECT I.CODPERIODO, P.NOMBRE, P.FECINICIO, P.FECFIN FROM CV_INFORME I,CV_PERIODO P " +
				"WHERE I.CODPERIODO=P.CODIGO AND I.CODCURSO=? ORDER BY P.FECINICIO DESC";
		
		try {
			cons = (Connection) dataSource.getConnection();
			stmt = (PreparedStatement) cons.prepareStatement(query);
			stmt.setInt(1, codcurso);
			result = (ResultSet) stmt.executeQuery();
			
			while (result.next()) {
				periodo = new Periodo();
				periodo.setIdPeriodo(result.getInt("CODPERIODO"));
				periodo.setNombre(result.getString("NOMBRE"));
				periodo.setFechaInicioToString(Formato.setBaseDatosDeDate(Formato.getDateDeBaseDatos(result.getString("FECINICIO"))));
				periodo.setFechaFinToString(Formato.setBaseDatosDeDate(Formato.getDateDeBaseDatos(result.getString("FECFIN"))));
				periodos.add(periodo);
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			try{
				result.close();
				stmt.close();
				cons.close();
			}catch (Exception e) {
			}
		}
		return periodos;
	}
}
