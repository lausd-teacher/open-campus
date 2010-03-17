package edu.tecsup.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.modelo.Curso;
import edu.tecsup.lms.modelo.Jerarquia;
import edu.tecsup.lms.util.Formato;

public class CursoDAO extends BaseDAO {

	protected DataSource dataSource;

	public Collection<Curso> buscar(String nombre) throws DAOException {
		log.info("buscar(String nombre, Integer idJerarquia)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Collection<Curso> cursos = new ArrayList<Curso>();
		Curso curso = null;
		String query = "select c.idcurso,c.idjerarquia,j.nombre jerarquia,c.nombre,c.creado_en " +
				"from cv_curso c, cv_jerarquia j " +
				"where c.idjerarquia=j.idjerarquia and c.estado=1 " +
				"and replace(replace(replace(replace(replace(replace(upper(c.nombre),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N') like ? order by c.nombre";
		try {
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, "%"+ Formato.matizarFrace(nombre) + "%");
			result =  stmt.executeQuery();
			while (result.next()) {
				curso = new Curso();
				curso.setIdCurso(result.getInt("idcurso"));
				curso.setNombre(result.getString("nombre"));
				curso.setJerarquia(new Jerarquia(result.getInt("idjerarquia"),result.getString("jerarquia")));
				curso.setFechaCreacion(Formato.timestampToCalendar(result.getString("creado_en")));
				cursos.add(curso);
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return cursos;
	}

	public Collection<Curso> buscarPorID(int idCurso) throws DAOException {
		log.info("buscarPorID(int idCcurso)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Collection<Curso> cursos = new ArrayList<Curso>();
		Curso curso = null;
		String query = "select c.idcurso,c.idjerarquia,j.nombre jerarquia,c.nombre,c.creado_en " +
				"from cv_curso c, cv_jerarquia j " +
				"where c.idjerarquia=j.idjerarquia and c.estado=1 " +
				"and c.idcurso=?";

		try {
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idCurso);
			result =  stmt.executeQuery();
			while (result.next()) {
				curso = new Curso();
				curso.setIdCurso(result.getInt("idcurso"));
				curso.setNombre(result.getString("nombre"));
				curso.setJerarquia(new Jerarquia(result.getInt("idjerarquia"),result.getString("jerarquia")));
				curso.setFechaCreacion(Formato.timestampToCalendar(result.getString("creado_en")));
				cursos.add(curso);
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return cursos;
	}
	
	public void eliminar(int idCurso) throws DAOException {
		log.info("eliminar(int idCurso)");
		PreparedStatement stmt = null;
		Connection cons = null;
		try {
			String query = "update cv_curso set estado=0 where idcurso=?";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idCurso);
			stmt.executeUpdate();
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	public void crear(Curso curso) throws DAOException {
		log.info("crear(Curso curso)");
		PreparedStatement stmt = null;
		Connection cons = null;
		try {
			String query = "insert into cv_curso(nombre,idjerarquia,creado_en) values(?,?,now())";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, curso.getNombre());
			stmt.setInt(2, curso.getJerarquia().getIdJerarquia());
			stmt.executeUpdate();
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}