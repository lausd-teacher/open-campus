package edu.opencampus.lms.dao;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.sql.DataSource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.modelo.AulaVirtual;
import edu.opencampus.lms.modelo.Test;
import edu.opencampus.lms.modelo.test.TestAlternativa;
import edu.opencampus.lms.modelo.test.TestPregunta;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;

public class TestDAO extends BaseDAO {

	protected DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Collection<TestPregunta> listarTestsPorUnidad(int idUnidad)
			throws DAOException {
		log.info("listarTestsPorUnidad(int idUnidad)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;

		Collection<TestPregunta> tests = new ArrayList<TestPregunta>();
		PreparedStatement subStmt = null;
		ResultSet subResult = null;
		Map<String, TestAlternativa> alternativas;
		TestAlternativa alternativa = null;
		TestPregunta test = null;
		try {
			String query = "select idtest,pregunta,respuesta,explicacion," +
					"tipo,grafico,archivo_nombre,archivo_tamanio," +
					"creado_en,creado_por,modificado_en,modificado_por " +
					"from cv_test where idunidad=? and estado='1' order by idtest;";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idUnidad);
			result =  stmt.executeQuery();
			query = "select d.item,d.texto texto_aux,a.texto,d.respuesta " +
					"from cv_test_detalle d left join cv_test_alternativa a " +
					"on d.idunidad = a.idunidad and d.idtest = a.idtest and d.item = a.idalternativa " +
					"where d.idunidad=? and d.idtest=? order by d.item";
			stmt =  cons.prepareStatement(query);
			query = "select idalternativa,texto from cv_test_alternativa " +
					"where idunidad=? and idtest=? order by idalternativa";
			subStmt =  cons.prepareStatement(query);
			while (result.next()) {
				test = new TestPregunta();
				test.setIdUnidad(idUnidad);
				test.setIdTest(result.getInt("IDTEST"));
				test.setPregunta(result.getString("PREGUNTA"));
				test.setExplicacion(result.getString("EXPLICACION"));
				// test.setEstado(result.getInt("ESTADO"));
				test.setGrafico(result.getInt("GRAFICO"));
				test.setArchivoNombre(result.getString("ARCHIVO_NOMBRE"));
				test.setArchivoTamanio(result.getString("ARCHIVO_TAMANIO"));
				test.setUsuarioCreacion(result.getString("creado_por"));
				test.setFechaCreacion(Formato.getDateDeBaseDatos(result
						.getString("creado_en")));
				test.setUsuarioModificacion(result.getString("modificado_por"));
				test.setFechaModificacion(Formato.getDateDeBaseDatos(result
						.getString("modificado_en")));
				test.setTipo(result.getInt("TIPO"));
				if (test.getTipo() == Constante.TEST_NUM_AMULTIPLE
						|| test.getTipo() == Constante.TEST_NUM_RELACIONAR
						|| test.getTipo() == Constante.TEST_NUM_ORDENAR) {
					stmt.setInt(1, idUnidad);
					stmt.setInt(2, test.getIdTest());
					subResult =  stmt.executeQuery();
					alternativas = new HashMap<String, TestAlternativa>(5);
					while (subResult.next()) {
						alternativa = new TestAlternativa();
						alternativa.setTexto(subResult.getString("TEXTO"));
						alternativa.setTextoAux(subResult
								.getString("TEXTO_AUX"));
						alternativa.setRespuesta(subResult.getString(
								"RESPUESTA").charAt(0));
						alternativas.put(subResult.getString("ITEM"),
								alternativa);
					}
					alternativas = new TreeMap<String, TestAlternativa>(
							alternativas);
					test.setAlternativas(alternativas);
				} else if (test.getTipo() == Constante.TEST_NUM_ASIMPLE) {
					subStmt.setInt(1, idUnidad);
					subStmt.setInt(2, test.getIdTest());
					subResult =  subStmt.executeQuery();
					alternativas = new HashMap<String, TestAlternativa>(5);
					while (subResult.next()) {
						alternativa = new TestAlternativa();
						alternativa.setTexto(subResult.getString("TEXTO"));
						if (subResult.getString("IDALTERNATIVA").equals(
								result.getString("RESPUESTA"))) {
							alternativa
									.setRespuesta(Constante.TEST_ITEM_CORRECTO);
						} else {
							alternativa
									.setRespuesta(Constante.TEST_ITEM_INCORRECTO);
						}
						alternativas.put(subResult.getString("IDALTERNATIVA"),
								alternativa);
					}
					alternativas = new TreeMap<String, TestAlternativa>(
							alternativas);
					test.setAlternativas(alternativas);
				} else if (test.getTipo() == Constante.TEST_NUM_VF) {
					alternativas = new HashMap<String, TestAlternativa>(1);
					alternativa = new TestAlternativa();
					alternativa.setRespuesta(result.getString("RESPUESTA")
							.charAt(0));
					alternativas.put(Constante.TEST_ITEM_A, alternativa);
					test.setAlternativas(alternativas);
				}
				tests.add(test);
			}
		} catch (SQLException e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(subResult);
			closeStatement(subStmt);
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);

		}
		return tests;
	}

	public TestPregunta obtenerTest(int idUnidad, int idTest) throws DAOException {
		log.info("obtenerTest(int idUnidad, int idTest)");

		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;

		PreparedStatement subStmt = null;
		ResultSet subResult = null;
		Map<String, TestAlternativa> alternativas;
		TestAlternativa alternativa = null;
		TestPregunta test = null;
		try {
			String query = "select pregunta,respuesta,explicacion,tipo,grafico,archivo_nombre,archivo_tamanio, " +
					"creado_en,creado_por,modificado_en,modificado_por " +
					"from cv_test where idunidad=? and idtest=? and estado='1' order by idtest";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idUnidad);
			stmt.setInt(2, idTest);
			result =  stmt.executeQuery();
			query = "select d.item,d.texto texto_aux,a.texto,d.respuesta " +
					"from cv_test_detalle d left join cv_test_alternativa a " +
					"on d.idunidad = a.idunidad and d.idtest = a.idtest and d.item = a.idalternativa " +
					"where d.idunidad=? and d.idtest=? order by d.item";
			stmt =  cons.prepareStatement(query);
			query = "select idalternativa,texto from cv_test_alternativa " +
					"where idunidad=? and idtest=? order by idalternativa";
			subStmt =  cons.prepareStatement(query);
			if (result.next()) {
				test = new TestPregunta();
				test.setIdUnidad(idUnidad);
				test.setIdTest(idTest);
				test.setPregunta(result.getString("PREGUNTA"));
				test.setExplicacion(result.getString("EXPLICACION"));
				test.setGrafico(result.getInt("GRAFICO"));
				test.setArchivoNombre(result.getString("ARCHIVO_NOMBRE"));
				test.setArchivoTamanio(result.getString("ARCHIVO_TAMANIO"));
				test.setUsuarioCreacion(result.getString("creado_por"));
				test.setFechaCreacion(Formato.getDateDeBaseDatos(result
						.getString("creado_en")));
				test.setUsuarioModificacion(result.getString("modificado_por"));
				test.setFechaModificacion(Formato.getDateDeBaseDatos(result
						.getString("modificado_en")));
				test.setTipo(result.getInt("TIPO"));
				if (test.getTipo() == Constante.TEST_NUM_AMULTIPLE
						|| test.getTipo() == Constante.TEST_NUM_RELACIONAR
						|| test.getTipo() == Constante.TEST_NUM_ORDENAR) {
					stmt.setInt(1, idUnidad);
					stmt.setInt(2, test.getIdTest());
					subResult =  stmt.executeQuery();
					alternativas = new HashMap<String, TestAlternativa>(5);
					while (subResult.next()) {
						alternativa = new TestAlternativa();
						alternativa.setTexto(subResult.getString("TEXTO"));
						alternativa.setTextoAux(subResult
								.getString("TEXTO_AUX"));
						alternativa.setRespuesta(subResult.getString(
								"RESPUESTA").charAt(0));
						alternativas.put(subResult.getString("ITEM"),
								alternativa);
					}
					alternativas = new TreeMap<String, TestAlternativa>(
							alternativas);
					test.setAlternativas(alternativas);
				} else if (test.getTipo() == Constante.TEST_NUM_ASIMPLE) {
					subStmt.setInt(1, idUnidad);
					subStmt.setInt(2, test.getIdTest());
					subResult =  subStmt.executeQuery();
					alternativas = new HashMap<String, TestAlternativa>(5);
					while (subResult.next()) {
						alternativa = new TestAlternativa();
						alternativa.setTexto(subResult.getString("TEXTO"));
						if (subResult.getString("IDALTERNATIVA").equals(
								result.getString("RESPUESTA"))) {
							alternativa
									.setRespuesta(Constante.TEST_ITEM_CORRECTO);
						} else {
							alternativa
									.setRespuesta(Constante.TEST_ITEM_INCORRECTO);
						}
						alternativas.put(subResult.getString("IDALTERNATIVA"),
								alternativa);
					}
					alternativas = new TreeMap<String, TestAlternativa>(
							alternativas);
					test.setAlternativas(alternativas);
				} else if (test.getTipo() == Constante.TEST_NUM_VF) {
					alternativas = new HashMap<String, TestAlternativa>(1);
					alternativa = new TestAlternativa();
					alternativa.setRespuesta(result.getString("RESPUESTA")
							.charAt(0));
					alternativas.put(Constante.TEST_ITEM_A, alternativa);
					test.setAlternativas(alternativas);
				}
			}
		} catch (SQLException e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(subResult);
			closeStatement(subStmt);
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);

		}
		return test;
	}

	public void crearTest(TestPregunta test) throws DAOException {
		log.info("crearTest(Test test)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String query = "select ifnull(max(idtest),0)+1 id from cv_test where idunidad=?";
			cons =  dataSource.getConnection();
			cons.setAutoCommit(false);
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, test.getIdUnidad());
			result =  stmt.executeQuery();
			if (result.next()) {

				test.setIdTest(result.getInt("ID"));

				query = "insert into cv_test (idunidad,idtest,pregunta,explicacion,grafico,archivo_nombre,archivo_tamanio,tipo,estado,creado_por,modificado_por,creado_en,modificado_en,respuesta) " +
						"values (?,?,?,?,?,?,?,?,?,?,?,now(),now(),?)";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(1, test.getIdUnidad());
				stmt.setInt(2, test.getIdTest());
				stmt.setString(3, test.getPregunta());
				stmt.setString(4, test.getExplicacion());
				stmt.setInt(5, test.getGrafico());
				stmt.setString(6, test.getArchivoNombre());
				stmt.setString(7, test.getArchivoTamanio());
				stmt.setInt(8, test.getTipo());
				stmt.setInt(9, test.getEstado());
				stmt.setString(10, test.getUsuarioCreacion());
				stmt.setString(11, test.getUsuarioModificacion());
				if (test.getRespuesta() == 0)
					stmt.setString(12, null);
				else
					stmt.setString(12, String.valueOf(test.getRespuesta()));

				if (1 != stmt.executeUpdate()) {
					throw new DAOException(
							"No se pudo insertar en la tabla cv_test");
				}

				if (Constante.TEST_NUM_ASIMPLE == test.getTipo()
						|| Constante.TEST_NUM_RELACIONAR == test.getTipo()) {

					query = "insert into cv_test_alternativa (idunidad,idtest,idalternativa,texto) values (?,?,?,?)";
					stmt =  cons
							.prepareStatement(query);
					stmt.setInt(1, test.getIdUnidad());
					stmt.setInt(2, test.getIdTest());

					for (Map.Entry<String, TestAlternativa> alternativaMap : test
							.getAlternativas().entrySet()) {

						TestAlternativa alt = alternativaMap.getValue();
						String numAlt = alternativaMap.getKey();

						stmt.setString(3, numAlt);
						stmt.setString(4, alt.getTexto());

						if (1 != stmt.executeUpdate()) {
							throw new DAOException(
									"No se pudo insertar en la tabla CV_ALTERNATIVA_TEST");
						}
					}

				}
				if (Constante.TEST_NUM_AMULTIPLE == test.getTipo()
						|| Constante.TEST_NUM_RELACIONAR == test.getTipo()
						|| Constante.TEST_NUM_ORDENAR == test.getTipo()) {

					query = "insert into cv_test_detalle (idunidad,idtest,item,texto,respuesta) values (?,?,?,?,?)";
					stmt =  cons
							.prepareStatement(query);
					stmt.setInt(1, test.getIdUnidad());
					stmt.setInt(2, test.getIdTest());

					for (Map.Entry<String, TestAlternativa> alternativaMap : test
							.getAlternativas().entrySet()) {

						TestAlternativa alt = alternativaMap.getValue();
						String numAlt = alternativaMap.getKey();

						stmt.setString(3, numAlt);
						stmt.setString(4, alt.getTextoAux());
						stmt.setString(5, String.valueOf(alt.getRespuesta()));

						if (1 != stmt.executeUpdate()) {
							throw new DAOException(
									"No se pudo insertar en la tabla cv_test_detalle");
						}
					}

				}

				cons.commit();
			}
		} catch (SQLException e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
		} finally {
			rollback(cons);
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);

		}
	}

	public void modificarTest(TestPregunta test) throws DAOException {
		log.info("modificarTest(Test test)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String query = "update cv_test set pregunta=?,explicacion=?,modificado_por=?,modificado_en=now(),respuesta=?,grafico=?,archivo_nombre=?,archivo_tamanio=? "
					+ "where idunidad=? and idtest=?";
			if (test.getGrafico() == 0) {
				query = "update cv_test set pregunta=?,explicacion=?,modificado_por=?,modificado_en=now(),respuesta=? "
						+ "where idunidad=? and idtest=?";
			}
			cons =  dataSource.getConnection();
			cons.setAutoCommit(false);

			stmt =  cons.prepareStatement(query);

			stmt.setString(1, test.getPregunta());
			stmt.setString(2, test.getExplicacion());
			stmt.setString(3, test.getUsuarioModificacion());

			if (test.getRespuesta() == 0)
				stmt.setString(4, null);
			else
				stmt.setString(4, String.valueOf(test.getRespuesta()));
			if (test.getGrafico() == 0) {
				stmt.setInt(5, test.getIdUnidad());
				stmt.setInt(6, test.getIdTest());
			} else {
				stmt.setInt(5, test.getGrafico());
				stmt.setString(6, test.getArchivoNombre());
				stmt.setString(7, test.getArchivoTamanio());
				stmt.setInt(8, test.getIdUnidad());
				stmt.setInt(9, test.getIdTest());
			}
			if (1 != stmt.executeUpdate()) {
				throw new DAOException(
						"No se pudo actualizar en la tabla cv_test");
			}

			if (Constante.TEST_NUM_ASIMPLE == test.getTipo()
					|| Constante.TEST_NUM_RELACIONAR == test.getTipo()) {

				query = "update cv_test_alternativa set texto=? "
						+ "where idunidad=? and idtest=? and idalternativa=?";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(2, test.getIdUnidad());
				stmt.setInt(3, test.getIdTest());

				for (Map.Entry<String, TestAlternativa> alternativaMap : test
						.getAlternativas().entrySet()) {

					TestAlternativa alt = alternativaMap.getValue();
					String numAlt = alternativaMap.getKey();

					stmt.setString(4, numAlt);
					stmt.setString(1, alt.getTexto());

					if (1 != stmt.executeUpdate()) {
						throw new DAOException(
								"No se pudo actualizar en la tabla CV_ALTERNATIVA_TEST");
					}
				}

			}
			if (Constante.TEST_NUM_AMULTIPLE == test.getTipo()
					|| Constante.TEST_NUM_RELACIONAR == test.getTipo()
					|| Constante.TEST_NUM_ORDENAR == test.getTipo()) {

				query = "update cv_test_detalle set texto=?,respuesta=? "
						+ "where idunidad=? and idtest=? and item=?";
				stmt =  cons.prepareStatement(query);
				stmt.setInt(3, test.getIdUnidad());
				stmt.setInt(4, test.getIdTest());

				for (Map.Entry<String, TestAlternativa> alternativaMap : test
						.getAlternativas().entrySet()) {

					TestAlternativa alt = alternativaMap.getValue();
					String numAlt = alternativaMap.getKey();

					stmt.setString(5, numAlt);
					stmt.setString(1, alt.getTextoAux());
					stmt.setString(2, String.valueOf(alt.getRespuesta()));

					if (1 != stmt.executeUpdate()) {
						throw new DAOException(
								"No se pudo actualizar en la tabla cv_test_detalle");
					}
				}

			}

			cons.commit();
		} catch (SQLException e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
		} finally {
			rollback(cons);
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);

		}
	}

	public void eliminar(TestPregunta test) throws DAOException {
		log.info("eliminar(Test test)");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "update cv_test set estado=0 where idunidad=? and idtest=?";
			cons =  dataSource.getConnection();

			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, test.getIdUnidad());
			stmt.setInt(2, test.getIdTest());
			if (1 != stmt.executeUpdate()) {
				throw new DAOException(
						"No se pudo eliminar en la tabla cv_test");
			}

		} catch (SQLException e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
		} finally {
			closeStatement(stmt);
			closeConnection(cons);

		}
	}

	public int getMaxIdTest(int idUnidad) throws DAOException {
		log.info("getMaxIdTest(int idUnidad)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String query = "select ifnull(max(idtest),0)+1 id from cv_test where idunidad=?";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idUnidad);
			result =  stmt.executeQuery();
			if (result.next()) {
				return result.getInt("ID");
			}
		} catch (SQLException e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);

		}
		return -1;
	}

	public void guardaInteraccion(Test test, int idTest, int idDetalle, int estado) throws DAOException {
		log.info("guardaInteraccion("+test.getIdNotaTest()+", "+idTest+", "+idDetalle+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		try {
			String query = "INSERT INTO cv_test_nota_detalle(IDTESTNOTA,IDDETALLE,ESTADO,IDTEST,IDUNIDAD) " +
							"VALUES (?,?,?,?,?)";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, test.getIdNotaTest());
			stmt.setInt(2, idDetalle);
			stmt.setInt(3, estado);
			stmt.setInt(4, idTest);
			stmt.setString(5, test.getIdUnidad());
			if (1 != stmt.executeUpdate()) {
				throw new SQLException("No se logro insertar el registro "+ idTest + " de " + test.getIdNotaTest());
			}
		} catch (SQLException e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
		} finally {
			closeStatement(stmt);
			closeConnection(cons);
		}

	}

	public void calificar(Test test, float nota) throws DAOException {
		log.info("calificar(Test "+test.getIdNotaTest()+", float "+nota+")");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			String query = "UPDATE cv_test_nota SET NOTA=?,ESTADO='1',"
					+ "FECHA_FIN=now() WHERE IDTESTNOTA=?";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setFloat(1, nota);
			stmt.setInt(2, test.getIdNotaTest());
			if (1 != stmt.executeUpdate()) {
				throw new SQLException("No se logro actualizar el registro " + test.getIdNotaTest() + " con " + nota);
			}
			query = "SELECT FECHA_FIN FROM cv_test_nota WHERE IDTESTNOTA = ?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, test.getIdNotaTest());
			result =  stmt.executeQuery();
			if (result.next()) {
				test.setFechaModificacion(Formato.getDateDeBaseDatos(result.getString("FECHA_FIN")));
			}
		} catch (SQLException e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
	}

	public Test ejecutarTest(Test test) throws DAOException {
		log.info("ejecutarTest(Test test)");
		
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try {
			String query = "INSERT INTO cv_test_nota(FECHA_INICIO,ESTADO,"
					+ "IDMATRICULA,IDFICHA,IDUNIDAD) VALUES "
					+ "(now(),'0',?,?,?)";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, test.getIdMatricula());
			stmt.setInt(2, test.getIdFicha());
			stmt.setString(3, test.getIdUnidad());
			if (1 != stmt.executeUpdate()) {
				throw new SQLException("No se logro iniciar el test a " + test.getIdMatricula() +"- Unidad "+test.getIdUnidad());
			}
			
			result = stmt.getGeneratedKeys();
			result.next();
			test.setIdNotaTest(result.getInt(1));
			
			query = "SELECT FECHA_INICIO FROM cv_test_nota WHERE IDTESTNOTA = ?";
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, test.getIdNotaTest());
			result =  stmt.executeQuery();
			if (result.next()) {
				test.setFechaCreacion(Formato.getDateDeBaseDatos(result.getString("FECHA_INICIO")));
			}
		} catch (SQLException e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return test;
	}

	public boolean isEnabledTest(AulaVirtual aula, int idUnidad)
			throws DAOException {
		log.info("isEnabledTest(AulaVirtual aula, int idUnidad)");
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection cons = null;
		try {
//			String query = "SELECT DESHABILITADO_DOC,DESHABILITADO_ESTU,ESTADO,"
//					+ "(SELECT COUNT(*) FROM cv_ficha_unidad WHERE IDFICHA=UFR.IDFICHA AND IDUNIDAD=UFR.IDUNIDAD AND ESTADO=1) EXISTEUNIDAD "
//					+ "FROM cv_ficha_unidad_RECURSO UFR "
//					+ "WHERE IDFICHA=? AND IDUNIDAD=? AND IDRECURSO=?";
//			cons =  dataSource.getConnection();
//			stmt =  cons.prepareStatement(query);
//			stmt.setInt(1, aula.getIdFicha());
//			stmt.setInt(2, idUnidad);
//			stmt.setString(3, Constante.RECURSO_ID_TEST);
//			result =  stmt.executeQuery();
//			if (result.next()) {
//				if (0 == result.getInt("ESTADO")) {
//					log.warn("Recurso desactivado");
//					return false;
//				}
//				if (1 != result.getInt("EXISTEUNIDAD")) {
//					log.warn("Unidad desactivada");
//					return false;
//				}
//				if (Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE == aula.getRol()
//						.getIdrol()
//						|| Constante.ROL_CAMPUS_AULAVIRTUAL_MONITOR_DOCENTE == aula
//								.getRol().getIdrol()) {
//					if (0 == result.getInt("DESHABILITADO_DOC")) {
//						log.warn("Recurso deshabilitado para el docente");
//						return false;
//					}
//				}
//				if (Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE == aula
//						.getRol().getIdrol()
//						|| Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE_MONITOR == aula
//								.getRol().getIdrol()) {
//					if (0 == result.getInt("DESHABILITADO_ESTU")) {
//						log.warn("Recurso deshabilitado para el alumno");
//						return false;
//					}
//				}
//			} else {
//				log.warn("Recurso no existe");
//				return false;
//			}
//
//		} catch (SQLException e) {
//			log.error(e);
//			throw new DAOException(e.toString());
		} catch (Exception e) {
			log.error(e);
			throw new DAOException(e.toString());
		} finally {
			closeResultSet(result);
			closeStatement(stmt);
			closeConnection(cons);
		}
		return true;
	}
	
	public Collection<TestPregunta> listarTestsParaEjecucion(int idUnidad)
			throws DAOException {
		log.info("listarTestsParaEjecucion(int idUnidad)");
		Connection cons = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		PreparedStatement subStmt = null;
		ResultSet subResult = null;

		Map<String, TestAlternativa> alternativas;
		TestAlternativa alternativa = null;
		TestPregunta test = null;
		Collection<TestPregunta> tests = new ArrayList<TestPregunta>();

		try {
			// Paso 1: Obtener todos los idtest de la unidad
			String query = "SELECT idtest FROM cv_test WHERE estado=1 and idunidad=?";
			cons =  dataSource.getConnection();
			stmt =  cons.prepareStatement(query);
			stmt.setInt(1, idUnidad);
			result =  stmt.executeQuery();
			int totalPreguntas = 0;
			List<Integer> todasPreguntas = new ArrayList<Integer>();

			while (result.next()) {
				todasPreguntas.add(new Integer(result.getString("idtest")));
				totalPreguntas++;
			}
			log.info("totalPreguntas: " + totalPreguntas);

			// Paso 2: Verificar si hay suficientes preguntas
			if (totalPreguntas < Constante.TEST_CANTIDAD_PREGUNTAS) {
				throw new DAOException("No hay suficientes preguntas");
			}

			// Paso 3: Obtener solo TEST_CANTIDAD_PREGUNTAS
			Collection<Integer> preguntasRequeridas = this.getRandomList(
					todasPreguntas, Constante.TEST_CANTIDAD_PREGUNTAS);

			// Paso 4: Armar el super Collection
			for (Integer idTest : preguntasRequeridas) {
				try {
					query = "SELECT PREGUNTA,RESPUESTA,EXPLICACION,"
							+ "TIPO,GRAFICO,ARCHIVO_NOMBRE,ARCHIVO_TAMANIO "
							+ "FROM cv_test WHERE IDUNIDAD=? AND IDTEST=? AND ESTADO='1' ORDER BY IDTEST";
					stmt =  cons
							.prepareStatement(query);
					stmt.setInt(1, idUnidad);
					stmt.setInt(2, idTest);
					result =  stmt.executeQuery();
					query = "select d.item,d.texto texto_aux,a.texto,d.respuesta " +
							"from cv_test_detalle d left join cv_test_alternativa a " +
							"on d.idunidad = a.idunidad and d.idtest = a.idtest and d.item = a.idalternativa " +
							"where d.idunidad=? and d.idtest=? order by d.item";
					stmt =  cons
							.prepareStatement(query);
					query = "SELECT IDALTERNATIVA,TEXTO FROM cv_test_alternativa "
							+ "WHERE IDUNIDAD=? AND IDTEST=? ORDER BY IDALTERNATIVA";
					subStmt =  cons
							.prepareStatement(query);
					if (result.next()) {
						test = new TestPregunta();
						test.setIdUnidad(idUnidad);
						test.setIdTest(idTest);
						test.setPregunta(result.getString("PREGUNTA"));
						test.setExplicacion(result.getString("EXPLICACION"));
						test.setGrafico(result.getInt("GRAFICO"));
						test.setArchivoNombre(result
								.getString("ARCHIVO_NOMBRE"));
						test.setArchivoTamanio(result
								.getString("ARCHIVO_TAMANIO"));
						test.setTipo(result.getInt("TIPO"));
						if (test.getTipo() == Constante.TEST_NUM_AMULTIPLE
								|| test.getTipo() == Constante.TEST_NUM_RELACIONAR
								|| test.getTipo() == Constante.TEST_NUM_ORDENAR) {
							stmt.setInt(1, idUnidad);
							stmt.setInt(2, test.getIdTest());
							subResult =  stmt.executeQuery();
							alternativas = new HashMap<String, TestAlternativa>(
									5);
							while (subResult.next()) {
								alternativa = new TestAlternativa();
								alternativa.setTexto(subResult
										.getString("TEXTO"));
								alternativa.setTextoAux(subResult
										.getString("TEXTO_AUX"));
								alternativa.setRespuesta(subResult.getString(
										"RESPUESTA").charAt(0));
								alternativas.put(subResult.getString("ITEM"),
										alternativa);
							}
							alternativas = new TreeMap<String, TestAlternativa>(
									alternativas);
							test.setAlternativas(alternativas);
						} else if (test.getTipo() == Constante.TEST_NUM_ASIMPLE) {
							subStmt.setInt(1, idUnidad);
							subStmt.setInt(2, test.getIdTest());
							subResult =  subStmt
									.executeQuery();
							alternativas = new HashMap<String, TestAlternativa>(
									5);
							while (subResult.next()) {
								alternativa = new TestAlternativa();
								alternativa.setTexto(subResult
										.getString("TEXTO"));
								if (subResult.getString("IDALTERNATIVA")
										.equals(result.getString("RESPUESTA"))) {
									alternativa
											.setRespuesta(Constante.TEST_ITEM_CORRECTO);
								} else {
									alternativa
											.setRespuesta(Constante.TEST_ITEM_INCORRECTO);
								}
								alternativas.put(subResult
										.getString("IDALTERNATIVA"),
										alternativa);
							}
							alternativas = new TreeMap<String, TestAlternativa>(
									alternativas);
							test.setAlternativas(alternativas);
						} else if (test.getTipo() == Constante.TEST_NUM_VF) {
							alternativas = new HashMap<String, TestAlternativa>(
									1);
							alternativa = new TestAlternativa();
							alternativa.setRespuesta(result.getString("RESPUESTA").charAt(0));
							alternativas.put(Constante.TEST_ITEM_A, alternativa);
							test.setAlternativas(alternativas);
						} else if (test.getTipo() == Constante.TEST_NUM_COMPLETAR) {
							alternativas = new HashMap<String, TestAlternativa>(4);
							test.setUsuarioModificacion(test.getPregunta()); //Pregunta temporal usada en el review del test
							test.setPregunta(getTestCompleteReFormat(test.getPregunta(), alternativas));
							alternativas = new TreeMap<String, TestAlternativa>(alternativas);
							test.setAlternativas(alternativas);
						}
					}
				} catch (SQLException e) {
					log.error(e.toString());
					throw new DAOException(e.toString());
				}

				tests.add(test);
			}

		} catch (Exception e) {
			log.error(e.toString());
			throw new DAOException(e.toString());
		}

		return tests;
	}
	
	public final String getTestCompleteReFormat(String cadena, Map<String, TestAlternativa> alternativas) throws Exception{
		Tidy tidy = new Tidy();
		tidy.setShowWarnings(false);
		tidy.setQuiet(true);
		log.info("TestCompleteReFormat: Antes: "+cadena);
		
		Document doc = tidy.parseDOM(new ByteArrayInputStream(cadena.getBytes()), null);
		
		NodeList nodes = doc.getElementsByTagName("input");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);
			
			//log.info("Value: "+ element.getAttribute("value"));
			alternativas.put(Constante.TEST_ITEM_A+(i+1), new TestAlternativa(element.getAttribute("value")));
			
			element.setAttribute("value", "");
			element.setAttribute("name", "a"+(i+1));
		}
		
		Node body = doc.getElementsByTagName("body").item(0);
		
		//Node Body to String
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		tf.setOutputProperty(OutputKeys.STANDALONE, "yes");
		StringWriter sw = new StringWriter();
		tf.transform(new DOMSource(body), new StreamResult(sw));
		String theAnswer = sw.toString();
		cadena = theAnswer.substring(6, theAnswer.length()-7);
		
		log.info("TestCompleteReFormat: Despues: "+cadena);
		
		return cadena;
	}
	public final Collection<Integer> getRandomList(List<Integer> universo,
			int cantidad) {
		// 
		Collection<Integer> mundo = new ArrayList<Integer>();
		//System.out.println("universo : " + universo);
		//System.out.println("cantidad : " + cantidad);

		Random r = new Random(System.currentTimeMillis());
		do {
			int nextRand = r.nextInt(universo.size());
			mundo.add(universo.get(nextRand));
			universo.remove(nextRand);
		} while (mundo.size() < cantidad);

		return mundo;
	}
}
