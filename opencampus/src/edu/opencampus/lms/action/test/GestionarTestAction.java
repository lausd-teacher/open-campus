package edu.opencampus.lms.action.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.tidy.Tidy;

import edu.opencampus.lms.action.BaseAction;
import edu.opencampus.lms.excepcion.ActionException;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.AulaVirtual;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.modelo.test.TestAlternativa;
import edu.opencampus.lms.modelo.test.TestPregunta;
import edu.opencampus.lms.service.TestService;
import edu.opencampus.lms.util.Archivo;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;
import edu.opencampus.lms.util.Util;

public class GestionarTestAction extends BaseAction {

	private static final long serialVersionUID = 1056410230584058699L;

	private TestService testService;

	private String tipo;

	private String idUnidad;
	
	private String unidad;

	private String idTest;

	private String pregunta;

	private String explicacion;

	public File file;

	public String contentType;

	public String filename;

	private String alt1;

	private String alt2;

	private String alt3;

	private String alt4;

	private String alt5;

	private String rpt1;

	private String rpt2;

	private String rpt3;

	private String rpt4;

	private String rpt5;

	private String aux1;

	private String aux2;

	private String aux3;

	private String aux4;

	private String aux5;

	public void setTestService(TestService testService) {
		this.testService = testService;
	}

	public String listar() throws ActionException{
		log.info("listar()");
		try {
			Usuario usuario = (Usuario) getSession().get(Constante.USUARIO_ACTUAL);
			AulaVirtual aula = usuario.getAulaActual();
			
			boolean permitido = false;
			
			if(aula != null){
				permitido = Util.esMiUnidad(aula.getSilabo().getUnidades(), idUnidad);
				setUnidad(Util.getNombreUnidad(aula.getSilabo().getUnidades(), idUnidad));
			}
			if(!permitido && Constante.ROL_CAMPUS_ADMINISTRADOR == usuario.getRolPredeterminado().getIdrol()){ //temporal
				//o valida en los interceptors o aqui (esadmin, docente)
				if(unidad == null)
					setUnidad((String)getSession().get("TEST_UNIDAD_TMP"));
				getSession().put("TEST_UNIDAD_TMP", unidad);
				permitido = true;
				aux1 = "admin"; //si accede desde afuera del aula no se vera el botn de probar test
			}
			if (permitido) {
				Collection<TestPregunta> tests = testService.listarTestsPorUnidad(Integer.parseInt(idUnidad));
				getRequest().setAttribute("TESTS", tests);
				getSession().put("TEST_IDUNIDAD_TMP", idUnidad);
			} else {
				log.error("Unidad no permitida");
				return ERROR;
			}

		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}

	public String descargarImagen() {
		log.info("descargarImagen()");
				
		try {
		
			Usuario usuario = (Usuario) getSession().get(Constante.USUARIO_ACTUAL);
			AulaVirtual aula = usuario.getAulaActual();
			
			boolean permitido = false;
			
			if(aula != null){
				permitido = Util.esMiUnidad(aula.getSilabo().getUnidades(), idUnidad);
			}
			if(!permitido && Constante.ROL_CAMPUS_ADMINISTRADOR == usuario.getRolPredeterminado().getIdrol()){
				//o valida en los interceptors o aqui (esadmin, docente)
				permitido = true;
			}
			
			if (permitido) {
				String source = Constante.RUTA_UNIDADES
						+ Formato.completarCeros(idUnidad, 8)
						+ Constante.SLASH + Constante.PATH_TEST + filename;
				
				Archivo.downloadImage(filename, source, getResponse());
			} else {
				log.error("Unidad no permitida");
				return ERROR;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

	public String eliminar() {
		log.info("eliminar()");
		try {
			
			Usuario usuario = (Usuario) getSession().get(Constante.USUARIO_ACTUAL);
			AulaVirtual aula = usuario.getAulaActual();
			
			boolean permitido = false;
			
			if(aula != null){
				permitido = Util.esMiUnidad(aula.getSilabo().getUnidades(), idUnidad);
			}
			if(!permitido && Constante.ROL_CAMPUS_ADMINISTRADOR == usuario.getRolPredeterminado().getIdrol()){
				//o valida en los interceptors o aqui (esadmin, docente)
				permitido = true;
			}
			
			if (permitido) {
				
				TestPregunta test = new TestPregunta();
				test.setIdUnidad(Integer.parseInt(idUnidad));
				test.setIdTest(Integer.parseInt(idTest));

				testService.eliminar(test);

			} else {
				log.error("Unidad no permitida");
				return ERROR;
			}

		} catch (ServiceException e) {
			log.error(e);
		} catch (NumberFormatException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		return SUCCESS;
	}

	/*
	 * public String eliminarImagen() { log.info("eliminarImagen()"); try {
	 * AulaVirtual aula =
	 * (AulaVirtual)getSession().getAttribute(Constante.AULA_ACTUAL);
	 * if(Util.esMiUnidad(aula.getUnidades(),idUnidad) && idTest != null){
	 * 
	 * Test test = new Test(); test.setIdUnidad(Integer.parseInt(idUnidad));
	 * test.setIdTest(Integer.parseInt(idTest));
	 * test.setGrafico(Constante.ESTADO_INACTIVO);
	 * 
	 * //testService.eliminarImagen(test);
	 * 
	 * }else{ log.error("Unidad no permitida"); return ERROR; } } catch
	 * (ServiceException e) { log.error(e); } catch (NumberFormatException e){
	 * log.error(e); } catch (Exception e){ log.error(e); } return SUCCESS; }
	 */

	public String nuevo() {
		log.info("nuevo()");

		Usuario usuario = (Usuario) getSession().get(Constante.USUARIO_ACTUAL);
		AulaVirtual aula = usuario.getAulaActual();
		
		idUnidad = (String) getSession().get("TEST_IDUNIDAD_TMP");
		
		boolean permitido = false;
		
		if(aula != null){
			permitido = Util.esMiUnidad(aula.getSilabo().getUnidades(), idUnidad);
		}
		if(!permitido && Constante.ROL_CAMPUS_ADMINISTRADOR == usuario.getRolPredeterminado().getIdrol()){
			//o valida en los interceptors o aqui (esadmin, docente)
			setUnidad((String)getSession().get("TEST_UNIDAD_TMP"));
			permitido = true;
		}
		
		if (permitido && tipo != null) {

			getRequest().setAttribute("NOMBRE_TIPO",Util.getTestTipos().get(String.valueOf(tipo)));

		} else {
			log.error("Unidad no permitida");
			return ERROR;
		}

		return SUCCESS;
	}

	public String modificado() {
		log.info("modificado()");

		try {
			
			Usuario usuario = (Usuario) getSession().get(Constante.USUARIO_ACTUAL);
			AulaVirtual aula = usuario.getAulaActual();
			
			idUnidad = (String) getSession().get("TEST_IDUNIDAD_TMP");
			
			boolean permitido = false;
			
			if(aula != null){
				permitido = Util.esMiUnidad(aula.getSilabo().getUnidades(), idUnidad);
				setUnidad(Util.getNombreUnidad(aula.getSilabo().getUnidades(), idUnidad));
			}
			if(!permitido && Constante.ROL_CAMPUS_ADMINISTRADOR == usuario.getRolPredeterminado().getIdrol()){
				//o valida en los interceptors o aqui (esadmin, docente)
				setUnidad((String)getSession().get("TEST_UNIDAD_TMP"));
				permitido = true;
			}
			
			if (permitido && idTest != null && idUnidad != null) {

				TestPregunta test = testService.obtenerTest(Integer.parseInt(idUnidad),Integer.parseInt(idTest));

				if (test != null) {
					getRequest().setAttribute("NOMBRE_TIPO",test.getTipoToString());
					tipo = String.valueOf(test.getTipo());
					getRequest().setAttribute("TEST", test);
				} else {
					log.error("Test no encontrado");
					return ERROR;
				}
			} else {
				log.error("Unidad no permitida");
				return ERROR;
			}
		} catch (ServiceException e) {
			log.error(e);
		} catch (NumberFormatException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}

		return SUCCESS;
	}

	public String crear() throws ActionException{
		log.info("crear()");

		try {
			Usuario usuario = (Usuario) getSession().get(Constante.USUARIO_ACTUAL);
			AulaVirtual aula = usuario.getAulaActual();
			idUnidad = (String) getSession().get("TEST_IDUNIDAD_TMP");
			
			boolean permitido = false;
			
			if(aula != null){
				permitido = Util.esMiUnidad(aula.getSilabo().getUnidades(), idUnidad);
			}
			if(!permitido && Constante.ROL_CAMPUS_ADMINISTRADOR == usuario.getRolPredeterminado().getIdrol()){
				//o valida en los interceptors o aqui (esadmin, docente)
				permitido = true;
			}
			
			if (permitido && usuario != null && tipo != null && idUnidad != null && idTest != null) {

				TestPregunta test = new TestPregunta();
				test.setIdUnidad(Integer.parseInt(idUnidad));
				if (idTest != null && idTest.trim().length() > 0) {
					test.setIdTest(Integer.parseInt(idTest));
				} else {
					test.setIdTest(testService.getMaxIdTest(Integer.parseInt(idUnidad)));
				}

				test.setTipo(Integer.parseInt(tipo));
				
				//Tidy validate
				if(Integer.parseInt(tipo) == Constante.TEST_NUM_COMPLETAR){
					Tidy tidy = new Tidy();
					tidy.setShowWarnings(false);
					tidy.setQuiet(true);
					
					Document doc = tidy.parseDOM(new ByteArrayInputStream(pregunta.getBytes()), null);
					Node body = doc.getElementsByTagName("body").item(0);
					
					//Node Body to String
					Transformer tf = TransformerFactory.newInstance().newTransformer();
					tf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
					tf.setOutputProperty(OutputKeys.STANDALONE, "yes");
					tf.transform(new DOMSource(body), new StreamResult(new StringWriter()));
				}
				// Fin tidy
							
				test.setPregunta(pregunta);
				test.setExplicacion(explicacion);
				test.setEstado(Constante.ESTADO_ACTIVO);
				test.setUsuarioCreacion(String.valueOf(usuario.getId()));
				test.setUsuarioModificacion(String.valueOf(usuario.getId()));

				// File
				// **********************************************************

				if (file != null && file.exists()) {
					log.info("Envío Pregunta con imagen: " + filename + " - "
							+ file.length() + " bytes");

					String extension = filename.substring(filename
							.lastIndexOf("."));
					String origen = file.getAbsolutePath();
					if (!"/".equals(Constante.SLASH))
						origen = origen.replaceAll("/", Constante.SLASH);
					String directorioDestino = Constante.RUTA_UNIDADES
							+ Formato.completarCeros(idUnidad, 8)
							+ Constante.SLASH + Constante.PATH_TEST;
					String nombreDestino = Constante.IMAGEN + "_"
							+ test.getIdTest() + extension;

					Archivo.copiarArchivo(origen, directorioDestino + nombreDestino);

					test.setGrafico(Constante.ESTADO_ACTIVO);
					test.setArchivoNombre(nombreDestino);
					test.setArchivoTamanio(String.valueOf(file.length()));

				} else {
					log.info("Envío Pregunta sin imagen");
					test.setGrafico(Constante.ESTADO_INACTIVO);
				}

				// **************************************************************

				switch (Integer.parseInt(tipo)) {
				case Constante.TEST_NUM_ASIMPLE:
					crearTestAsimple(test);
					break;
				case Constante.TEST_NUM_AMULTIPLE:
					crearTestAmultiple(test);
					break;
				case Constante.TEST_NUM_VF:
					crearTestVF(test);
					break;
				case Constante.TEST_NUM_RELACIONAR:
					crearTestRelacionar(test);
					break;
				case Constante.TEST_NUM_COMPLETAR:
					crearTestCompletar(test);
					break;
				case Constante.TEST_NUM_ORDENAR:
					crearTestOrdenar(test);
					break;
				default:
					return ERROR;
				}
				if (idTest != null && idTest.trim().length() > 0) {
					log.info("Modificación de Pregunta");
					testService.modificarTest(test);
				} else {
					log.info("Creación de Pregunta");
					testService.crearTest(test);
				}

			} else {
				log.error("Unidad no permitida");
				return ERROR;
			}
		} catch (ServiceException e) {
			log.error(e);
			throw new ActionException(e);
		} catch (NumberFormatException e) {
			log.error(e);
			throw new ActionException(e);
		} catch (TransformerException e) {
			log.error(e + tipo+"-"+idUnidad +"-pregunta:"+pregunta);
			addActionError("El formato de la pregunta ingresada no cumple el estándar W3C.");
			addActionError("Se recomienda no copiar y pegar de otros procesadores de texto.");
			//addActionError(getText("Mensaje del properties"));
			if (idTest != null && idTest.trim().length() > 0) {
				setMessage("Error en el formato de la pregunta");
				throw new ActionException(e);
			} else {
				return INPUT;
			}
		} catch (IOException e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}

	private void crearTestAsimple(TestPregunta test) throws ServiceException {
		log.info("Nuevo Test de Alternativa Simple");

		Map<String, TestAlternativa> alternativas = new HashMap<String, TestAlternativa>(
				5);

		alternativas.put(Constante.TEST_ITEM_A, new TestAlternativa(alt1));
		alternativas.put(Constante.TEST_ITEM_B, new TestAlternativa(alt2));
		alternativas.put(Constante.TEST_ITEM_C, new TestAlternativa(alt3));
		alternativas.put(Constante.TEST_ITEM_D, new TestAlternativa(alt4));
		alternativas.put(Constante.TEST_ITEM_E, new TestAlternativa(alt5));
		alternativas = new TreeMap<String, TestAlternativa>(alternativas);

		test.setAlternativas(alternativas);
		test.setRespuesta(rpt1.charAt(0));

	}

	private void crearTestAmultiple(TestPregunta test) throws ServiceException {
		log.info("Nuevo Test de Alternativa Multiple");

		Map<String, TestAlternativa> alternativas = new HashMap<String, TestAlternativa>(
				5);

		char rpt = Constante.TEST_ITEM_INCORRECTO;
		if (rpt1 != null)
			rpt = Constante.TEST_ITEM_CORRECTO;
		alternativas.put(Constante.TEST_ITEM_A, new TestAlternativa(aux1, rpt));

		if (rpt2 != null)
			rpt = Constante.TEST_ITEM_CORRECTO;
		else
			rpt = Constante.TEST_ITEM_INCORRECTO;
		alternativas.put(Constante.TEST_ITEM_B, new TestAlternativa(aux2, rpt));

		if (rpt3 != null)
			rpt = Constante.TEST_ITEM_CORRECTO;
		else
			rpt = Constante.TEST_ITEM_INCORRECTO;
		alternativas.put(Constante.TEST_ITEM_C, new TestAlternativa(aux3, rpt));

		if (rpt4 != null)
			rpt = Constante.TEST_ITEM_CORRECTO;
		else
			rpt = Constante.TEST_ITEM_INCORRECTO;
		alternativas.put(Constante.TEST_ITEM_D, new TestAlternativa(aux4, rpt));

		if (rpt5 != null)
			rpt = Constante.TEST_ITEM_CORRECTO;
		else
			rpt = Constante.TEST_ITEM_INCORRECTO;
		alternativas.put(Constante.TEST_ITEM_E, new TestAlternativa(aux5, rpt));

		alternativas = new TreeMap<String, TestAlternativa>(alternativas);

		test.setAlternativas(alternativas);

	}

	private void crearTestVF(TestPregunta test) throws ServiceException {
		log.info("Nuevo Test de Verdadero / Falso");

		test.setRespuesta(rpt1.charAt(0));

	}

	private void crearTestRelacionar(TestPregunta test) throws ServiceException {
		log.info("Nuevo Test de Relacionar");

		Map<String, TestAlternativa> alternativas = new HashMap<String, TestAlternativa>(
				5);

		alternativas.put(Constante.TEST_ITEM_A, new TestAlternativa(aux1, rpt1.toUpperCase()
				.charAt(0), alt1));
		alternativas.put(Constante.TEST_ITEM_B, new TestAlternativa(aux2, rpt2.toUpperCase()
				.charAt(0), alt2));
		alternativas.put(Constante.TEST_ITEM_C, new TestAlternativa(aux3, rpt3.toUpperCase()
				.charAt(0), alt3));
		alternativas.put(Constante.TEST_ITEM_D, new TestAlternativa(aux4, rpt4.toUpperCase()
				.charAt(0), alt4));
		alternativas = new TreeMap<String, TestAlternativa>(alternativas);

		test.setAlternativas(alternativas);

	}

	private void crearTestCompletar(TestPregunta test) throws ServiceException {
		log.info("Nuevo Test de Completar");

	}

	private void crearTestOrdenar(TestPregunta test) throws ServiceException {
		log.info("Nuevo Test de Ordenar");

		Map<String, TestAlternativa> alternativas = new HashMap<String, TestAlternativa>(
				5);

		alternativas.put(Constante.TEST_ITEM_A, new TestAlternativa(aux1, rpt1
				.charAt(0)));
		alternativas.put(Constante.TEST_ITEM_B, new TestAlternativa(aux2, rpt2
				.charAt(0)));
		alternativas.put(Constante.TEST_ITEM_C, new TestAlternativa(aux3, rpt3
				.charAt(0)));
		alternativas.put(Constante.TEST_ITEM_D, new TestAlternativa(aux4, rpt4
				.charAt(0)));
		alternativas = new TreeMap<String, TestAlternativa>(alternativas);

		test.setAlternativas(alternativas);

	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(String idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setFileFileName(String filename) {
		this.filename = filename;
	}

	public String getAlt1() {
		return alt1;
	}

	public void setAlt1(String alt1) {
		this.alt1 = alt1;
	}

	public String getAlt2() {
		return alt2;
	}

	public void setAlt2(String alt2) {
		this.alt2 = alt2;
	}

	public String getAlt3() {
		return alt3;
	}

	public void setAlt3(String alt3) {
		this.alt3 = alt3;
	}

	public String getAlt4() {
		return alt4;
	}

	public void setAlt4(String alt4) {
		this.alt4 = alt4;
	}

	public String getAlt5() {
		return alt5;
	}

	public void setAlt5(String alt5) {
		this.alt5 = alt5;
	}

	public String getAux1() {
		return aux1;
	}

	public void setAux1(String aux1) {
		this.aux1 = aux1;
	}

	public String getAux2() {
		return aux2;
	}

	public void setAux2(String aux2) {
		this.aux2 = aux2;
	}

	public String getAux3() {
		return aux3;
	}

	public void setAux3(String aux3) {
		this.aux3 = aux3;
	}

	public String getAux4() {
		return aux4;
	}

	public void setAux4(String aux4) {
		this.aux4 = aux4;
	}

	public String getAux5() {
		return aux5;
	}

	public void setAux5(String aux5) {
		this.aux5 = aux5;
	}

	public String getExplicacion() {
		return explicacion;
	}

	public void setExplicacion(String explicacion) {
		this.explicacion = explicacion;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String getRpt1() {
		return rpt1;
	}

	public void setRpt1(String rpt1) {
		this.rpt1 = rpt1;
	}

	public String getRpt2() {
		return rpt2;
	}

	public void setRpt2(String rpt2) {
		this.rpt2 = rpt2;
	}

	public String getRpt3() {
		return rpt3;
	}

	public void setRpt3(String rpt3) {
		this.rpt3 = rpt3;
	}

	public String getRpt4() {
		return rpt4;
	}

	public void setRpt4(String rpt4) {
		this.rpt4 = rpt4;
	}

	public String getRpt5() {
		return rpt5;
	}

	public void setRpt5(String rpt5) {
		this.rpt5 = rpt5;
	}

	public String getIdTest() {
		return idTest;
	}

	public void setIdTest(String idTest) {
		this.idTest = idTest;
	}

}
