package edu.opencampus.lms.action.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import edu.opencampus.lms.action.BaseAction;
import edu.opencampus.lms.excepcion.ActionException;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.AulaVirtual;
import edu.opencampus.lms.modelo.Test;
import edu.opencampus.lms.modelo.test.TestAlternativa;
import edu.opencampus.lms.modelo.test.TestPregunta;
import edu.opencampus.lms.service.TestService;
import edu.opencampus.lms.util.Archivo;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;
import edu.opencampus.lms.util.UsuariosConectados;
import edu.opencampus.lms.util.Util;

public class TestAction extends BaseAction {

	private static final long serialVersionUID = -5996708356304688107L;

	private TestService testService;

	private String idUnidad;
	
	private String imagen;
	
	private int id0;
	private int id1;
	private int id2;
	private int id3;
	private int id4;
	private int id5;
	private int id6;
	private int id7;
	private int id8;
	private int id9;

	private String re0;
	private String re1;
	private String re2;
	private String re3;
	private String re4;
	private String re5;
	private String re6;
	private String re7;
	private String re8;
	private String re9;
		
	public void setId0(int id0) {
		this.id0 = id0;
	}

	public void setId1(int id1) {
		this.id1 = id1;
	}

	public void setId2(int id2) {
		this.id2 = id2;
	}

	public void setId3(int id3) {
		this.id3 = id3;
	}

	public void setId4(int id4) {
		this.id4 = id4;
	}

	public void setId5(int id5) {
		this.id5 = id5;
	}

	public void setId6(int id6) {
		this.id6 = id6;
	}

	public void setId7(int id7) {
		this.id7 = id7;
	}

	public void setId8(int id8) {
		this.id8 = id8;
	}

	public void setId9(int id9) {
		this.id9 = id9;
	}

	public void setRe0(String re0) {
		this.re0 = re0;
	}

	public void setRe1(String re1) {
		this.re1 = re1;
	}

	public void setRe2(String re2) {
		this.re2 = re2;
	}

	public void setRe3(String re3) {
		this.re3 = re3;
	}

	public void setRe4(String re4) {
		this.re4 = re4;
	}

	public void setRe5(String re5) {
		this.re5 = re5;
	}

	public void setRe6(String re6) {
		this.re6 = re6;
	}

	public void setRe7(String re7) {
		this.re7 = re7;
	}

	public void setRe8(String re8) {
		this.re8 = re8;
	}

	public void setRe9(String re9) {
		this.re9 = re9;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(String idUnidad) {
		this.idUnidad = idUnidad;
	}

	public void setTestService(TestService testService) {
		this.testService = testService;
	}

	@Deprecated
	public AulaVirtual getAulaVirtualSession(){
		return getUsuarioSession().getAulaActual();
	}
	
	public String cargar() throws Exception{
		log.info("cargar(): usuario: "+getUsuarioSession()+" - idUnidad: "+idUnidad);
		
		AulaVirtual aula = getAulaVirtualSession();
		
		try {
			
			if(aula != null && idUnidad != null && aula.getSilabo().esMiUnidad(Integer.parseInt(idUnidad)) &&
					testService.isEnabledTest(aula, Integer.parseInt(idUnidad))){
				
				Test test = new Test();
				test.setIdUnidad(idUnidad);
				test.setIdMatricula(aula.getMatriculaActual().getIdMatricula());
				test.setIdFicha(aula.getIdFicha());
				test.setNombreUnidad(aula.getSilabo().getNombreUnidad(Integer.parseInt(idUnidad)));
				aula.setTestActual(test);
				
			}else{
				log.error("No tiene permiso para acceder al recurso o no esta listo.");
				throw new ActionException("No tiene permiso para acceder al recurso o no esta listo.");
			}
			
		} catch (NumberFormatException e) {
			log.error(e);
			throw new ActionException(e);
		} catch (ServiceException e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}
	
	public String ingresar(){
		log.info("ingresar(): usuario: "+getUsuarioSession());
		
		AulaVirtual aula = getAulaVirtualSession();
		
		try {
			
			if(aula != null && aula.getTestActual() != null && aula.getSilabo().esMiUnidad(Integer.parseInt(aula.getTestActual().getIdUnidad())) &&
					testService.isEnabledTest(aula, Integer.parseInt(aula.getTestActual().getIdUnidad()))){
				
//				UsuariosConectados.desactivarChat(getUsuarioSession().getId());
				
				Collection<TestPregunta> tests = testService.listarTestsParaEjecucion(Integer.parseInt(aula.getTestActual().getIdUnidad()));
				Test test = testService.ejecutarTest(aula.getTestActual());
				aula.setTestActual(test);
				aula.getTestActual().setTests(tests);
//				for (Test test : tests) {
//					if(test.getTipo() == 5){
//						System.out.println(test.getIdTest());
//						System.out.println(test.getPregunta());
//						if(test.getAlternativas() != null){
//							for (String key : test.getAlternativas().keySet()) {
//								System.out.println("\t"+key +" - "+test.getAlternativas().get(key).getTexto());
//							}
//						}
//						System.out.println("***********");
//					}
//				}		
				
			}else{
				log.error("No tiene permiso para acceder al recurso o no esta listo.");
				throw new ActionException("No tiene permiso para acceder al recurso o no esta listo.");
			}
			
		} catch (NumberFormatException e) {
			log.error(e);
			return ERROR;
		} catch (ServiceException e) {
			log.error(e);
			return ERROR;
		} catch (Exception e) {
			log.error(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String descargarImagen() {
		log.info("descargarImagen(): "+idUnidad);
		
		AulaVirtual aula = getAulaVirtualSession();
		
		try {
			
			if(aula != null && aula.getTestActual() != null && aula.getSilabo().esMiUnidad(Integer.parseInt(aula.getTestActual().getIdUnidad())) &&
					testService.isEnabledTest(aula, Integer.parseInt(aula.getTestActual().getIdUnidad()))){
				String source = Constante.RUTA_UNIDADES + Formato.completarCeros(idUnidad, 8)
				+ Constante.SLASH + Constante.PATH_TEST+imagen;;

				
				Archivo.downloadFile(imagen, source, getResponse());
			} else {
				log.error("No tiene permiso para acceder al recurso o no esta listo.");
				return ERROR;
			}
		} catch (NumberFormatException e) {
			log.error(e);
			return ERROR;
		} catch (FileNotFoundException e) {
			log.error(e);
			return ERROR;
		} catch (IOException e) {
			log.error(e);
			return ERROR;
		} catch (Exception e) {
			log.error(e);
			return ERROR;
		}
		return NONE;
	}

	public String salir() {
		log.info("salir(): usuario: "+getUsuarioSession());
		if (null != getAulaVirtualSession()) {
			 getAulaVirtualSession().setTestActual(null);
		}
		return NONE;
	}
	
	public String calificar() {
		log.info("calificar(): usuario: "+getUsuarioSession());
		
		AulaVirtual aula = getAulaVirtualSession();
		
		try {
			
			if(getUsuarioSession() != null && aula != null && aula.getTestActual() != null){
				if(aula.getSilabo().esMiUnidad(Integer.parseInt(aula.getTestActual().getIdUnidad()))){
					if(testService.isEnabledTest(aula, Integer.parseInt(aula.getTestActual().getIdUnidad()))){
				
//						UsuariosConectados.activarChat(getUsuarioSession().getId());
						
						//Corregir Test ***
						Test test = aula.getTestActual();
						log.info("calificar() "+test.getIdNotaTest());
						
						float nota = 0;
						
						float p0 = corregirPregunta(id0, re0);
						nota += p0;
						testService.guardaInteraccion(test,id0,0,(p0>0)?Constante.ESTADO_ACTIVO:Constante.ESTADO_INACTIVO);
						
						float p1 = corregirPregunta(id1, re1);
						nota += p1;
						testService.guardaInteraccion(test,id1,1,(p1>0)?Constante.ESTADO_ACTIVO:Constante.ESTADO_INACTIVO);
						
						float p2 = corregirPregunta(id2, re2);
						nota += p2;
						testService.guardaInteraccion(test,id2,2,(p2>0)?Constante.ESTADO_ACTIVO:Constante.ESTADO_INACTIVO);
						
						float p3 = corregirPregunta(id3, re3);
						nota += p3;
						testService.guardaInteraccion(test,id3,3,(p3>0)?Constante.ESTADO_ACTIVO:Constante.ESTADO_INACTIVO);
						
						float p4 = corregirPregunta(id4, re4);
						nota += p4;
						testService.guardaInteraccion(test,id4,4,(p4>0)?Constante.ESTADO_ACTIVO:Constante.ESTADO_INACTIVO);
						
						float p5 = corregirPregunta(id5, re5);
						nota += p5;
						testService.guardaInteraccion(test,id5,5,(p5>0)?Constante.ESTADO_ACTIVO:Constante.ESTADO_INACTIVO);
						
						float p6 = corregirPregunta(id6, re6);
						nota += p6;
						testService.guardaInteraccion(test,id6,6,(p6>0)?Constante.ESTADO_ACTIVO:Constante.ESTADO_INACTIVO);
						
						float p7 = corregirPregunta(id7, re7);
						nota += p7;
						testService.guardaInteraccion(test,id7,7,(p7>0)?Constante.ESTADO_ACTIVO:Constante.ESTADO_INACTIVO);
						
						float p8 = corregirPregunta(id8, re8);
						nota += p8;
						testService.guardaInteraccion(test,id8,8,(p8>0)?Constante.ESTADO_ACTIVO:Constante.ESTADO_INACTIVO);
						
						float p9 = corregirPregunta(id9, re9);
						nota += p9;
						testService.guardaInteraccion(test,id9,9,(p9>0)?Constante.ESTADO_ACTIVO:Constante.ESTADO_INACTIVO);
						
						nota = (Math.round(nota*100))/100f;
						
						log.info("Nota: "+nota);
						
						testService.calificar(test, nota);
						
						getResponse().setContentType("text/xml");
						getResponse().setHeader("Cache-Control", "no-cache");
						PrintWriter out = getResponse().getWriter();
						out.write("<?xml version='1.0' encoding='ISO-8859-1'?>");
						out.write("<test>");
						
						out.write("<fecha1><![CDATA["+Formato.setBaseDatosDeDateSuperCompleto(test.getFechaCreacion())+"]]></fecha1>");
						out.write("<fecha2><![CDATA["+Formato.setBaseDatosDeDateSuperCompleto(test.getFechaModificacion())+"]]></fecha2>");
						long elapsed = Math.round((test.getFechaModificacion().getTimeInMillis()-test.getFechaCreacion().getTimeInMillis())/1000);
						out.write("<elapsed><![CDATA["+((elapsed>60)?(elapsed/60)+" minutos "+(elapsed%60)+" segundos":(elapsed%60)+" segundos")+"]]></elapsed>");
						out.write("<nota><![CDATA["+nota+"]]></nota>");
						
						out.write("<p0>"+p0+"</p0>");
						out.write("<p1>"+p1+"</p1>");
						out.write("<p2>"+p2+"</p2>");
						out.write("<p3>"+p3+"</p3>");
						out.write("<p4>"+p4+"</p4>");
						out.write("<p5>"+p5+"</p5>");
						out.write("<p6>"+p6+"</p6>");
						out.write("<p7>"+p7+"</p7>");
						out.write("<p8>"+p8+"</p8>");
						out.write("<p9>"+p9+"</p9>");
						
						out.write("</test>");
						out.close();
						
						aula.setTestActual(null);
				
					}else{
						//Parece imposible, el testActual siempre pertenecerá a la ficha y si se cargó en un incio es por que si estubo activa
						throw new Exception("La unidad se encuentra inactiva temporalmente. idFicha: "+aula.getIdFicha()+" idUnidad: "+aula.getTestActual().getIdUnidad());
					}
				}else{
					//Parece imposible, el testActual siempre pertenecerá al aula.
					throw new Exception("No tiene permiso para acceder al recurso. \n" + aula.getTestActual().getIdUnidad() +"\n\n"+ aula);
				}
			}else{
				throw new Exception("No existe el objeto aula o el objeto Test. \n" + getUsuarioSession() +"\n\n"+ aula);
			}
			
		} catch (ServiceException e) {
			log.error("TESTEJECUCIONERROR "+e);
			return ERROR;
		} catch (Exception e) {
			log.error("TESTEJECUCIONERROR "+e);
			return ERROR;
		}

		return NONE;
	}
	
	private float corregirPregunta(int id, String re) throws Exception{
		log.info("corregirPregunta("+id+", "+re+"): usuario: "+getUsuarioSession());
		AulaVirtual aula = getAulaVirtualSession();
		float puntos = 0;
		
		if(aula != null){
			Test test = aula.getTestActual();
			if(test != null && test.getTests() != null){
				boolean isIdContained = false;
				for (TestPregunta p : test.getTests()) {
					if(p.getIdTest() == id){
						log.info(id+" - Tipo: "+p.getTipo());
						isIdContained = true;
						switch (p.getTipo()) {
						case 1:
							TestAlternativa a = p.getAlternativas().get(re);
							if(a != null && '1' == p.getAlternativas().get(re).getRespuesta()){
								puntos = 2;
							}
							log.info(id+" - Simple: "+((p.getAlternativas().get(re)!=null)?p.getAlternativas().get(re).getRespuesta():"0")+"-"+re);
							break;
						case 2:
							String res ="";
							for (String key : p.getAlternativas().keySet()) {
								res += p.getAlternativas().get(key).getRespuesta()+"/";
							}
							if(re.equals(res)){
								puntos = 2;
							}
							log.info(id+" - Multiple: "+res+"-"+re);
						break;
						case 3:
							if(re.equals(String.valueOf(p.getAlternativas().get("A").getRespuesta()))){
								puntos = 2;
							}
							log.info(id+" - V / F : "+p.getAlternativas().get("A").getRespuesta()+"-"+re);
						break;
						case 4:
							res ="";
							String[] buffer = re.split("/");
							int i = 0;
							for (String key : p.getAlternativas().keySet()) {
								res += p.getAlternativas().get(key).getRespuesta()+"/";
								if(String.valueOf(p.getAlternativas().get(key).getRespuesta()).equals(buffer[i])){
									puntos += 0.5;
								}
								i++;
							}
							log.info(id+" - Relacion: "+res+"-"+re);
						break;
						case 5:
							res ="";
							buffer = re.split("/pm/");
							i = 0;
							int size = p.getAlternativas().size();
							for (String key : p.getAlternativas().keySet()) {
								res += Formato.matizarFrace(p.getAlternativas().get(key).getTexto()+"/pm/");
								if(Formato.matizarFrace(String.valueOf(p.getAlternativas().get(key).getTexto())).equals(Formato.matizarFrace(buffer[i]))){
									puntos += (Math.round((2f/size)*100))/100f;
								}
								i++;
							}
							puntos = (puntos>2)?2.00f:puntos; //Correccion del error de redondeo a 2.01
							log.info(id+" - Completar: "+res+"-"+Formato.matizarFrace(re));
						break;
						case 6:
							res ="";
							buffer = re.split("/");
							i = 0;
							for (String key : p.getAlternativas().keySet()) {
								res += p.getAlternativas().get(key).getRespuesta()+"/";
								if(String.valueOf(p.getAlternativas().get(key).getRespuesta()).equals(buffer[i])){
									puntos += 0.5;
								}
								i++;
							}
							log.info(id+" - Ordenar: "+res+"-"+re);
						break;
				
						default:
							throw new Exception("Tipo de pregunta desconocida");
						}
						if(puntos>0){
							log.info(id+" - Correcto: "+puntos);	
						}else{
							log.info(id+" - incorrecto:"+puntos);	
						}
						return puntos;
					}
				}
				if(!isIdContained){
					throw new Exception(id+" - El idTest enviado desde el cliente no pertenece a la lista de test en session: "+id);
				}
				
			}else{
				throw new Exception(id+" - Objeto Test no existe en la sesion");
			}
		}else{
			throw new Exception(id+" - Objeto AulaVirtual no existe en la sesion");
		}
		return -20;
	}

}
