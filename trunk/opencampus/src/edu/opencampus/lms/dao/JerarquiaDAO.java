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
import edu.opencampus.lms.modelo.Jerarquia;

public class JerarquiaDAO extends BaseDAO{

	Log log = LogFactory.getLog(getClass());
	protected DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Collection<Jerarquia> obtenerHijos(int idPredecesor) throws DAOException {
		log.info("obtenerHijos("+idPredecesor+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Collection<Jerarquia> jerarquias = new ArrayList<Jerarquia>();
		Jerarquia jerarquia = null;
		try {
			
			String query = "select idjerarquia,nombre from cv_jerarquia " +
					"where tipo='G' and estado=1 and idpredecesor=? order by nombre";
			cons = dataSource.getConnection();
			//cons = getConnection();
			stmt = cons.prepareStatement(query);
			stmt.setInt(1, idPredecesor);
			result = stmt.executeQuery();
			while (result.next()) {
				jerarquia = new Jerarquia();
				jerarquia.setIdJerarquia(result.getInt("idjerarquia"));
				jerarquia.setNombre(result.getString("nombre"));
				jerarquias.add(jerarquia);
			}
			
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e);
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e);
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		
		//Traer a los hijos de sus hijos
		for (Jerarquia j : jerarquias) {
			j.setHijos(obtenerHijos(j.getIdJerarquia()));
		}
		
		return jerarquias;
	}
	
	public Jerarquia obtenerPadre(int idPredecesor) throws DAOException {
		log.info("obtenerPadre("+idPredecesor+")");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		Jerarquia jerarquia = null;
		
		if(idPredecesor != 0){
			
			try {
				
				String query = "select idjerarquia, idpredecesor, nombre from cv_jerarquia " +
						"where tipo='G' and estado=1 and idjerarquia=? order by nombre";
				cons = dataSource.getConnection();
				//cons = getConnection();
				stmt = cons.prepareStatement(query);
				stmt.setInt(1, idPredecesor);
				result = stmt.executeQuery();
				if (result.next()) {
					jerarquia = new Jerarquia();
					jerarquia.setIdJerarquia(result.getInt("idjerarquia"));
					jerarquia.setPadre(new Jerarquia(result.getInt("idpredecesor")));
					jerarquia.setNombre(result.getString("nombre"));
				}
				
			} catch (SQLException e) {
				log.error(e);
				throw new DAOException(e);
			} catch (Exception e) {
				log.error(e);
				throw new DAOException(e);
			} finally {
				closeResultSet(result);
				closeStatement(stmt);
				closeConnection(cons);
			}
			
			//Traer al padre de su padre
			jerarquia.setPadre(obtenerPadre(jerarquia.getPadre().getIdJerarquia()));
			
		}
		
		return jerarquia;
	}
	
	public void crear(Jerarquia jerarquia) throws DAOException {
		log.info("crear(Jerarquia jerarquia)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
	
		try {
			String query = "insert into cv_jerarquia (idpredecesor,nombre,creado_en) values (?,?,now())";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, jerarquia.getPadre().getIdJerarquia());
			stmt.setString(2, jerarquia.getNombre());		
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en crearJerarquia(Jerarquia jerarquia)");
				throw new DAOException("No culmino");
			}

		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e);
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e);
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	public Jerarquia obtener(Integer idJerarquia) throws DAOException {
		log.info("obtener(Integer idJerarquia)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		Jerarquia temp = null;
		try {
			String query = "select idjerarquia,idpredecesor, nombre, tipo " +
					"from cv_jerarquia where estado=1 and idjerarquia=?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idJerarquia);
			result =  stmt.executeQuery();	
			
			if (result.next()) {
				temp = new Jerarquia();
				temp.setIdJerarquia(result.getInt("idjerarquia"));
				temp.setPadre(new Jerarquia(result.getInt("idpredecesor")));
				temp.setNombre(result.getString("nombre"));
				temp.setTipo(result.getString("tipo"));
			}
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e);
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e);
		} finally {
			closeResultSet(result);
			closeStatement(stmt);			
			closeConnection(cons);
		}
		return temp;
	}
	
	public void modificar(Jerarquia jerarquia) throws DAOException {
		log.info("modificar(Jerarquia jerarquia)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		try {
			String query = "update cv_jerarquia set idpredecesor=?, nombre=?, modificado_en = now() where idjerarquia=?";
					
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, jerarquia.getPadre().getIdJerarquia());
			stmt.setString(2, jerarquia.getNombre());		
			stmt.setInt(3, jerarquia.getIdJerarquia());
			
			if (1 != stmt.executeUpdate()) {
				log.error("Error en modificar(Jerarquia jerarquia)");
				throw new DAOException("No culmino");
			}
			
		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e);
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e);
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	public void eliminar(Integer idJerarquia) throws DAOException {
		log.info("eliminar(Integer idJerarquia)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		try {
			String query = "delete from cv_jerarquia where idjerarquia=?";
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idJerarquia);
			stmt.executeUpdate();
		} catch (SQLException e) {

			try{
				String query = "update cv_jerarquia set estado=0 where idjerarquia=?";
				cons = dataSource.getConnection();
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, idJerarquia);
				stmt.executeUpdate();
				
			} catch (SQLException r) {
				log.error(r);
				throw new DAOException(r);
			}
			
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e);
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
	}
	
	/*public Collection<Jerarquia> listarPorNivel(int nivel, boolean desde) throws DAOException {
		log.info("listarPorNivel(int nivel, boolean desde)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		
		Collection<Jerarquia> col = new ArrayList<Jerarquia>();
		ResultSet subResult = null;
		Jerarquia temp = null;
		try {
			String query = "select idjerarquia, idpredecesor, nombre, nivel " +
					"from cv_jerarquia where tipo='G' and estado=1 and nivel "+((desde)?">=":"<=")+" ? order by nivel, nombre";
			
			cons = dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, nivel);
			
			result =  stmt.executeQuery();
			while (result.next()) {
				temp = new Jerarquia();
				temp.setIdJerarquia(result.getString("IDJERARQUIA"));
				temp.setRuta(result.getString("RUTA"));
				//temp.setProfundidad(result.getString("PROFUNDIDAD"));
				
				// Sub2: Obtener rutaCompleta
				String general="/ ";
				
				if("/".equals(result.getString("RUTA"))){
					general += "Ra&iacute;z";
				} else {
					query = "SELECT NOMBRE FROM cv_jerarquia WHERE IDJERARQUIA=?";			
					stmt =  cons.prepareStatement(query);
					String[] array = result.getString("RUTA").split("/"); 
					for(int o =profundidad;o<array.length-1;o++){
						stmt.setString(1, array[o]);
						subResult = stmt.executeQuery();
						if(subResult.next()){
							general+=subResult.getString("NOMBRE")+" / ";
						}
					}
					general+=result.getString("NOMBRE");
				}

				temp.setRutaCompleta(general);	
				//
				col.add(temp);
			}

		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e);
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e);
		} finally {
			closeResultSet(subResult);
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return col;
	}*/
	
}