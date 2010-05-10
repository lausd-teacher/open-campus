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
import edu.opencampus.lms.modelo.Jerarquia;
import edu.opencampus.lms.modelo.Periodo;
import edu.opencampus.lms.modelo.Silabo;
import edu.opencampus.lms.modelo.ficha.Unidad;
import edu.opencampus.lms.util.Formato;

public class SilaboDAO extends BaseDAO {

	protected DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void modificarIndiceUnidad(boolean cmd, int idSilabo, int idUnidad) throws DAOException {
		log.info("modificarIndiceUnidad(boolean cmd,String idSilabo, int idUnidad)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null; 
		try {
			cons = dataSource.getConnection();
			cons.setAutoCommit(false);
			
			if(cmd){
				
				String query = "select indice-1 indice from cv_silabo_unidad where idsilabo=? and idunidad=?";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, idSilabo);
				stmt.setInt(2, idUnidad);
				result = stmt.executeQuery();
				int indice = (result.next())?result.getInt("indice"):1;
				
				if(indice != 0){
					
					query = "update cv_silabo_unidad set indice=indice+1 where idsilabo=? and indice=?";
					stmt =  cons.prepareStatement(query);
					stmt.setInt(1, idSilabo);
					stmt.setInt(2, indice);
					if (1 != stmt.executeUpdate()) {
						log.error("Error en cmd=up al bajar elemento");
						throw new DAOException("No culmino");
					}
					
					query = "update cv_silabo_unidad set indice=indice-1 where idsilabo=? and idunidad=?";
					stmt =  cons.prepareStatement(query);
					stmt.setInt(1, idSilabo);
					stmt.setInt(2, idUnidad);
					if (1 != stmt.executeUpdate()) {
						log.error("Error en cmd=down al subir elemento");
						throw new DAOException("No culmino");
					}
					
				}
				
			}else{
				
				String query = "select indice+1 indice from cv_silabo_unidad where idsilabo=? and idunidad=?";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, idSilabo);
				stmt.setInt(2, idUnidad);
				result = stmt.executeQuery();
				int indice = (result.next())?result.getInt("indice"):1;
				
				query = "update cv_silabo_unidad set indice=indice-1 where idsilabo=? and indice=?";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, idSilabo);
				stmt.setInt(2, indice);
				if (1 != stmt.executeUpdate()) {
					log.error("Error en cmd=up al bajar elemento");
					throw new DAOException("No culmino");
				}else{
					
					query = "update cv_silabo_unidad set indice=indice+1 where idsilabo=? and idunidad=?";
					stmt =  cons.prepareStatement(query);
					stmt.setInt(1, idSilabo);
					stmt.setInt(2, idUnidad);
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

	public Collection<Unidad> buscarNuevasUnidades(String nombre, int idSilabo)
			throws DAOException {
		
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		Collection<Unidad> array = new ArrayList<Unidad>();
		try {
			String query = "select idunidad,nombre from cv_unidad " +
					"where replace(replace(replace(replace(replace(replace(upper(nombre),'Á','A'),'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N') like ? " +
					"and idunidad not in (select idunidad from cv_silabo_unidad where idsilabo = ?) " +
					"and estado=1 order by nombre limit 0,16";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, "%" + Formato.matizarFrace(nombre) + "%");
			stmt.setInt(2, idSilabo);
			result =  stmt.executeQuery();
			while (result.next()) {
				Unidad vo = new Unidad();
				vo.setIdUnidad(result.getInt("idunidad"));
				vo.setNombreCompleto(result.getString("nombre"));
				array.add(vo);
			}
		} catch (SQLException e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} finally {
			closeStatement(stmt);
			closeConnection(cons);
		}
		return array;
	}

	public void eliminarUnidad(int idSilabo, int idUnidad) throws DAOException {
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String query = "select indice from cv_silabo_unidad where idsilabo=? and idunidad=?";
			cons = dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idSilabo);
			stmt.setInt(2, idUnidad);
			result = stmt.executeQuery();
			int indice = (result.next())?result.getInt("indice"):1;
			
			query = "update cv_silabo_unidad set indice=indice-1 where idsilabo=? and indice >= ?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idSilabo);
			stmt.setInt(2, indice);
			if (stmt.executeUpdate() == 0) {
				log.error("Error en delete from update cv_silabo_unidad");
				throw new DAOException("No culmino");
			}

			query = "delete from cv_silabo_recurso where idsilabo=? and idunidad=?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idSilabo);
			stmt.setInt(2, idUnidad);
			stmt.executeUpdate();
			
			query = "delete from cv_silabo_unidad where idsilabo=? and idunidad=?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idSilabo);
			stmt.setInt(2, idUnidad);
			if (1 != stmt.executeUpdate()) {
				log.error("Error en delete from cv_silabo_unidad");
				throw new DAOException("No culmino");
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

	public void agregarUnidad(Integer idSilabo, Integer idUnidad) throws DAOException {
		log.info("agregarUnidad(Integer idSilabo, Integer idUnidad)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String query = "select ifnull(max(indice),0)+1 indice from cv_silabo_unidad where idsilabo = ?";
			cons = dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idSilabo);
			result = stmt.executeQuery();
			result.next();
			Integer indice = result.getInt("indice");
			
			query = "insert into cv_silabo_unidad (idsilabo, idunidad, indice) values (?,?,?)";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idSilabo);
			stmt.setInt(2, idUnidad);
			stmt.setInt(3, indice);
			if (1 != stmt.executeUpdate()) {
				log.error("Error en insert into cv_silabo_unidad");
				throw new DAOException("No culmino");
			}
			
			query = "insert into cv_silabo_recurso(idsilabo,idunidad,idrecurso,estado_docente,estado_alumno,calificado,peso,estado) " +
					"select ?,?,idrecurso,1,1,0,0,1 from cv_recurso where estado = 1 order by idrecurso";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idSilabo);
			stmt.setInt(2, idUnidad);
			if (0 == stmt.executeUpdate()) {
				log.error("Error en insert into cv_silabo_recurso");
				throw new DAOException("No culmino");
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

	public Silabo obtener(int idSilabo) throws DAOException {
		log.info("obtener(int idSilabo)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		Silabo temp = null;
		try {
			String query = "select idsilabo, nombre from cv_silabo where idsilabo = ?";

			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idSilabo);
			result =  stmt.executeQuery();
			if (result.next()) {
				temp = new Silabo();
				temp.setIdSilabo(result.getInt("idsilabo"));
				temp.setDescripcion(result.getString("nombre"));
				
				List<Unidad> unidades = new ArrayList<Unidad>();
				Unidad unidad = null;
				
				query = "select u.idunidad,u.nombre,u.idjerarquia,j.nombre jerarquia, " +
						"(select count(*) total from cv_test where estado='1' and idunidad=u.idunidad) cantidadtest " +
						"from cv_silabo_unidad us, cv_unidad u, cv_jerarquia j " +
						"where u.idunidad=us.idunidad and u.idjerarquia=j.idjerarquia and us.idsilabo=? order by indice";
				
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, idSilabo);
				result =  stmt.executeQuery();
				while (result.next()) {
					unidad = new Unidad();
					unidad.setIdUnidad(result.getInt("idunidad"));
					unidad.setNombreCompleto(result.getString("nombre"));
					unidad.setJerarquia(new Jerarquia(result.getInt("idjerarquia"),result.getString("jerarquia")));
					unidad.setCantidadTest(result.getInt("cantidadtest"));
					unidades.add(unidad);
					//Hacer un hashMap de recursos para configurarlo o dejalo en pendientes (falta.txt)
				}
				temp.setUnidades(unidades);
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
		return temp;
	}

	public Collection<Silabo> buscarPorIDCurso(int idCurso) throws DAOException {
		log.info("buscarPorIDCurso(int idCurso)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		ResultSet subresult = null;
		Collection<Silabo> silabos = new ArrayList<Silabo>();
		Collection<AulaVirtual> aulas = null;
		try {
			String query = "select idsilabo, nombre, creado_en from cv_silabo s where idcurso=? and estado=1 order by idsilabo desc";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idCurso);
			result =  stmt.executeQuery();
			while (result.next()) {
				Silabo silabo = new Silabo();
				silabo.setIdCurso(idCurso);
				silabo.setIdSilabo(result.getInt("idsilabo"));
				silabo.setDescripcion(result.getString("nombre"));
				silabo.setFechaCreacion(Formato.timestampToCalendar(result.getString("creado_en")));
				silabos.add(silabo);
				
				aulas = new ArrayList<AulaVirtual>();
				query = "select f.idficha,p.nombre, p.fecha_inicio, p.fecha_fin,p.personalizado from cv_ficha f, cv_periodo p " +
						"where f.idperiodo=p.idperiodo and f.idsilabo=? and f.eliminado=0";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, silabo.getIdSilabo());
				subresult =  stmt.executeQuery();
				while (subresult.next()) {
					AulaVirtual aula = new AulaVirtual();
					aula.setIdFicha(subresult.getInt("idficha"));
					Periodo periodo = new Periodo();
					periodo.setNombre(subresult.getString("nombre"));
					periodo.setFechaInicio(Formato.timestampToCalendar(subresult.getString("fecha_inicio")));
					periodo.setFechaFin(Formato.timestampToCalendar(subresult.getString("fecha_fin")));
					periodo.setPersonalizado(subresult.getInt("personalizado"));
					aula.setPeriodo(periodo);
					aulas.add(aula);
				}
				silabo.setAulas(aulas);
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
		return silabos;
	}

	public void crear(Silabo silabo) throws DAOException {
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "insert into cv_silabo (idcurso, nombre, modificado_por, modificado_en, creado_por, creado_en) values(?,?,?,now(),?,now())";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, silabo.getIdCurso());
			stmt.setString(2, silabo.getDescripcion());
			stmt.setString(3, silabo.getUsuarioModificacion());
			stmt.setString(4, silabo.getUsuarioCreacion());

			if (1 != stmt.executeUpdate()) {
				log.error("Error en crearSilabo(Curso curso)");
				throw new DAOException("No culmino");
			}
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
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

	public void modificar(Silabo silabo) throws DAOException {
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "update cv_silabo set modificado_por=?, modificado_en=now(), nombre=? where idsilabo = ?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setString(1, silabo.getUsuarioModificacion());
			stmt.setString(2, silabo.getDescripcion());
			stmt.setInt(3, silabo.getIdSilabo());
			if (1 != stmt.executeUpdate()) {
				log.error("Error en modificarSilabo(Silabo silabo)");
				throw new DAOException("No culmino");
			}
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
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

	public void eliminar(int idSilabo) throws DAOException {
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			
			String query = "delete from cv_silabo_recurso where idsilabo = ?";
			cons = dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idSilabo);
			stmt.executeUpdate();
			
			query = "delete from cv_silabo_unidad where idsilabo = ?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idSilabo);
			stmt.executeUpdate();
			
			query = "delete from cv_objetivo where idsilabo = ?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idSilabo);
			stmt.executeUpdate();
			
			query = "delete from cv_silabo where idsilabo = ?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idSilabo);
			if (1 != stmt.executeUpdate()) {
				log.error("Error en eliminarSilabo(String idSilabo) - delete from cv_silabo");
				throw new DAOException("No culmino");
			}
			
			cons.commit();
		} catch (SQLException e) {
			log.info("No se pudo eliminar el silabo, solo se ha cambiado de estado.");
			try{
				cons.rollback();
				String query = "update cv_silabo set estado=0 where idsilabo = ?";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, idSilabo);
				if (1 != stmt.executeUpdate()) {
					log.error("Error en eliminarSilabo(String idSilabo) - update cv_silabo");
					throw new DAOException("No culmino");
				}
				cons.commit();
			}catch (Exception er) {
				log.error(er);
				throw new DAOException(er);
			}
		} catch (DAOException e) {
			log.error(e);
			throw e;
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			rollback(cons);
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

}
