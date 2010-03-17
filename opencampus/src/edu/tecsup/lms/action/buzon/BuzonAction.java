package edu.tecsup.lms.action.buzon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.modelo.correo.Adjunto;
import edu.tecsup.lms.modelo.correo.Carpeta;
import edu.tecsup.lms.modelo.correo.Mensaje;
import edu.tecsup.lms.modelo.correo.UsuarioCorreo;
import edu.tecsup.lms.modelo.usuario.Persona;
import edu.tecsup.lms.service.BuzonService;
import edu.tecsup.lms.util.Archivo;
import edu.tecsup.lms.util.Constante;
import edu.tecsup.lms.util.SendMail;
import edu.tecsup.lms.util.UsuariosConectados;

public class BuzonAction extends BaseAction {

	private static final long serialVersionUID = 6893025102412864646L;

	public File doc;

	public String docContentType;

	public String docFileName;

	private BuzonService buzonService;
	
	private String titulo;

	private String id;

	private int noleidos;

	private String contenido;

	private String destino;

	private String carpeta;

	private String cc;

	private String aBuscar;

	private String idMensaje;

	private String idCarpeta;

	private String idAdjunto;

	private String archivoNombre;

	private Collection<Carpeta> CARPETAS;

	private int pagActual;

	private Collection<UsuarioCorreo> USUARIOS;

	private Collection<Mensaje> MENSAJES;

	private String incorrectos;

	private String idr;

	private String tipoR;

	private String sem;

	private String an;

	private String fechaAd;

	private String tipo;

	private String aviso;

	private String nombres;

	private String fec_nac;

	private String paterno;

	private String telefono;

	private String materno;

	private String celular;

	private String ubicacion;

	private String sexo;

	private String direccion;

	private int cantidadPorPagina = Constante.MAX_FILA_POR_PAG;

	public String cargarPortada(){
		log.info("cargarPortada()");
		try {
			MENSAJES = buzonService.cargarPortada(getUsuarioSession().getId());
		} catch (Exception e) {
			log.error(e.toString());
			return NONE;
		}
		return SUCCESS;
	}
	
	public String buzon() {
		log.info("buzon()");
		try {
			CARPETAS = buzonService.traerCarpeta(getUsuarioSession().getId());
			noleidos = buzonService.cantidadNoLeidos(getUsuarioSession().getId());
		} catch (ServiceException e) {
			log.error(e.toString());
		}
		return SUCCESS;
	}
	
	public String listarMensajes() {
		log.info("listarMensajes");
		int idUsuario = getUsuarioSession().getId();
		String carpetaPersonal = "0";
		int total = 0;
		int end = (pagActual * cantidadPorPagina);

		int start = (end - cantidadPorPagina);
		try {
			if (!carpeta.equals("F") && !carpeta.equals("R")
					&& !carpeta.equals("E") && !carpeta.equals("P")) {
				carpetaPersonal = carpeta;
				carpeta = "O";
				total = buzonService.totalMensajes(idUsuario, carpeta,carpetaPersonal);
				MENSAJES = buzonService.traerMensajes(idUsuario, carpeta,carpetaPersonal, start, end);
			} else if (carpeta.equals("F")) {
				total = buzonService.totalFavoritos(idUsuario);
				MENSAJES = buzonService.mostrarFavoritos(idUsuario, start, end);
			} else {
				total = buzonService.totalMensajes(idUsuario, carpeta,carpetaPersonal);
				MENSAJES = buzonService.traerMensajes(idUsuario, carpeta,carpetaPersonal, start, end);
			}
			if (total < end) {
				end = total;
			}
			if (start == 0) {
				start = 1;
			}
			if (MENSAJES.size() == 0) {
				start = 0;
			}

			getResponse().setContentType("text/xml");
			getResponse().setHeader("Cache-Control", "no-cache");
			getResponse().getWriter().write(
					"<?xml version='1.0' encoding='ISO-8859-1'?>");
			getResponse().getWriter().write("<mensajes>");
			getResponse().getWriter().write(
					"<total>" + total + "</total><actual>" + pagActual
							+ "</actual><start>" + start + "</start><end>"
							+ end + "</end>");

			if (!MENSAJES.isEmpty()) {
				for (Mensaje mensaje : MENSAJES) {
					String xx = "", xxx = "";
					getResponse().getWriter().write(
							"<mensaje>" + "<id>" + mensaje.getIdMensaje()
									+ "</id>" + "<titulo><![CDATA["
									+ mensaje.getTitulo() + "]]></titulo>"
									+ "<contenido><![CDATA["
									+ mensaje.getContenido()
									+ "]]></contenido>"
									+ "<remitente><![CDATA["
									+ mensaje.getRemitente().getNombreCorto()
									+ "]]></remitente>" + "<idUsuarioEnvio>"
									+ mensaje.getRemitente().getIdUsuario()
									+ "</idUsuarioEnvio>"
									+ "<carpeta><![CDATA["
									+ mensaje.getCarpeta() + "]]></carpeta>"
									+ "<fecha_envio>"
									+ mensaje.getFechaToStringEnvio()
									+ "</fecha_envio>" + "<fecha_leido>"
									+ mensaje.getFechaToStringLeido()
									+ "</fecha_leido>" + "<adjunto>"
									+ mensaje.getAdjunto() + "</adjunto>"
									+ "<favorito>" + mensaje.getFavorito()
									+ "</favorito>" + "<estado>"
									+ mensaje.getEstado() + "</estado>"
									+ "<tipo>" + mensaje.getTipo() + "</tipo>");

					getResponse().getWriter().write("<destinos><![CDATA[");
					if (!mensaje.getDestinos().isEmpty()) {
						for (int p = 0; p < mensaje.getDestinos().size(); p++) {
							xx += mensaje.getDestinos().get(p).getNombreCorto()
									+ "&lt;"
									+ mensaje.getDestinos().get(p).getIdUsuario() + "&gt;,";
						}
						getResponse().getWriter().write(
								xx.substring(0, xx.lastIndexOf(",")));
					}
					getResponse().getWriter().write("]]></destinos>");

					getResponse().getWriter().write("<ccs><![CDATA[");
					if (!(mensaje.getCcs()).isEmpty()) {
						for (int p = 0; p < mensaje.getCcs().size(); p++) {
							xxx += mensaje.getCcs().get(p).getNombreCorto()
									+ "&lt;"
									+ mensaje.getCcs().get(p).getIdUsuario()
									+ "&gt;,";
						}
						getResponse().getWriter().write(
								xxx.substring(0, xxx.lastIndexOf(",")));
					}
					getResponse().getWriter().write("]]></ccs>");

					if (mensaje.getAdjunto() == 1) {
						getResponse().getWriter()
								.write(
										"<adjNom><![CDATA["
												+ mensaje.getAdjuntos().get(0)
														.getNombre()
												+ "]]></adjNom>"
												+ "<adjId>"
												+ mensaje.getAdjuntos().get(0)
														.getIdAdjunto().trim()
												+ "</adjId>"
												+ "<adjSiz>"
												+ mensaje.getAdjuntos().get(0)
														.getTamanio()
												+ "</adjSiz>"
												+ "<adjFe>"
												+ mensaje.getAdjuntos().get(0)
														.getFechaAdjunto()
												+ "</adjFe>");
					}
					getResponse().getWriter().write("</mensaje>");
				}
			}
			getResponse().getWriter().write("</mensajes>");
			getResponse().getWriter().close();

		} catch (Exception e) {
			log.error(e);
			getResponse().reset();
			getResponse().setContentType("text/xml");
			getResponse().setHeader("Cache-Control", "no-cache");
			try {
				getResponse().getWriter().write(
						"<?xml version='1.0' encoding='ISO-8859-1'?>");
				getResponse()
						.getWriter()
						.write(
								"<mensajes><total>1</total>"
										+ "<actual>1</actual><start>1</start><end>1</end>"
										+ "<mensaje>" +
												"<id>1</id><titulo><![CDATA[Error en el Sistema...]]></titulo>" +
												"<contenido><![CDATA[Informe del siguiente error a soporte<br/>"+e+"]]></contenido>" +
												"<remitente><![CDATA[WebApp Reload]]></remitente>" +
												"<idUsuarioEnvio>drodriguez</idUsuarioEnvio><carpeta>R</carpeta><fecha_envio>Ahora</fecha_envio><fecha_leido></fecha_leido><adjunto>0</adjunto><favorito>0</favorito><estado>0</estado><tipo>D</tipo><destinos>"+getUsuarioSession().getId()+"</destinos><ccs><![CDATA[]]></ccs>" +
										"</mensaje>" +
								"</mensajes>");
				getResponse().getWriter().close();
			} catch (IOException se) {
				log.error(se.toString());
			}
		}
		return NONE;
	}
	
	public String crearCarpeta() {
		log.info("crearCarpeta()");
		Carpeta c = new Carpeta();
		if (!carpeta.trim().isEmpty()) {
			c.setNombre(carpeta);
			c.setIdUsuario(getUsuarioSession().getId());
			try {
				String id = buzonService.crearCarpeta(c);
				PrintWriter pw = getResponse().getWriter();
				pw.print(id);
				pw.flush();
				pw.close();
			} catch (ServiceException e) {
				log.error(e.toString());
			} catch (IOException e) {
				log.error(e.toString());
			}
		}
		return NONE;
	}
	
	public String noLeidos() {
		try {
			int n = buzonService.cantidadNoLeidos(getUsuarioSession().getId());
			PrintWriter pw = getResponse().getWriter();
			pw.print(n);
			pw.flush();
			pw.close();
		} catch (ServiceException e) {
			try {
				PrintWriter pw = getResponse().getWriter();
				pw.print(0);
				pw.flush();
			} catch (IOException e1) {
				log.error(e1.toString());
			}
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
		return NONE;
	}
	
	public String configuration() throws Exception{
		log.info("configuration()");
		CARPETAS = buzonService.traerCarpeta(getUsuarioSession().getId());
		return SUCCESS;
	}
	
	public String cambiarNombreCarpeta() {
		log.info("cambiarNombreCarpeta()");
		Carpeta c = new Carpeta();
		c.setIdCarpeta(idCarpeta);
		c.setNombre(titulo);
		c.setIdUsuario(getUsuarioSession().getId());
		try {
			PrintWriter pw;
			pw = getResponse().getWriter();
			pw.print(buzonService.cambiarNombreCarpeta(c));
			pw.flush();
			pw.close();
		} catch (IOException e) {
			log.error(e.toString());
		} catch (ServiceException e) {
			log.error(e.toString());
		}
		return SUCCESS;
	}
	
	public String eliminarCarpeta() {
		log.info("eliminarCarpeta()");
		Carpeta c = new Carpeta();
		c.setIdUsuario(getUsuarioSession().getId());
		c.setIdCarpeta(idCarpeta);
		try {
			buzonService.eliminarCarpeta(c);
		} catch (Exception e) {
			log.error(e.toString());
		}
		return NONE;
	}
	
	public String eliminar() {
		log.info("eliminar()");
		Mensaje m = new Mensaje();
		m.setUsuario(String.valueOf(getUsuarioSession().getId()));
		m.setIdMensaje(idMensaje);
		m.setTipo(tipo);

		try {
			buzonService.eliminarForEver(m);
		} catch (Exception e) {
			log.error(e.toString());
		}
		return NONE;
	}
	
	public String enviarPapelera() {
		log.info("enviarPapelera()");
		Mensaje m = new Mensaje();
		m.setIdMensaje(idMensaje);
		m.setCarpeta("P");
		m.setUsuario(String.valueOf(getUsuarioSession().getId()));
		m.setTipo(tipo);
		try {
			buzonService.enviarPapelera(m);
		} catch (ServiceException e) {
			log.error(e.toString());
		}
		return NONE;
	}
	
	public String marcarLeido() {
		log.info("marcarLeido()");
		Mensaje m = new Mensaje();
		m.setUsuario(String.valueOf(getUsuarioSession().getId()));
		m.setIdMensaje(id);
		m.setLeido(1);
		m.setTipo(tipo);
		try {
			buzonService.cambiarLeido(m);
		} catch (ServiceException e) {
			log.error(e.toString());
		}
		return NONE;
	}

	public String marcarNoLeido() {
		log.info("marcarNoLeido()");
		Mensaje m = new Mensaje();
		m.setUsuario(String.valueOf(getUsuarioSession().getId()));
		m.setIdMensaje(id);
		m.setLeido(0);
		m.setTipo(tipo);
		try {
			buzonService.cambiarNoLeido(m);
		} catch (ServiceException e) {
			log.error(e.toString());
		}
		return NONE;
	}
	
	public String marcarFavorito() {
		log.info("marcarFavorito()");
		Mensaje m = new Mensaje();
		m.setIdMensaje(id);
		m.setCarpeta(carpeta);
		m.setIdCarpeta(carpeta);
		m.setTipo(tipo);

		if (!carpeta.equals("R") && !carpeta.equals("E")
				&& !carpeta.equals("P")) {
			m.setCarpeta("O");
		} else {
			m.setIdCarpeta("0");
		}

		m.setUsuario(String.valueOf(getUsuarioSession().getId()));
		try {
			int r = buzonService.favorito(m);
			PrintWriter pw = getResponse().getWriter();
			pw.print(r);
			pw.flush();
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
		return NONE;
	}
	
	public String moverCarpeta() {
		log.info("moverCarpeta()");
		Mensaje m = new Mensaje();
		m.setUsuario(String.valueOf(getUsuarioSession().getId()));
		m.setIdMensaje(idMensaje);
		m.setTipo(tipo);

		m.setOldCarpeta(carpeta);
		m.setOldIdCarpeta(carpeta);

		m.setIdCarpeta(idCarpeta);

		if (!carpeta.equals("R") && !carpeta.equals("E")
				&& !carpeta.equals("P")) {
			m.setOldCarpeta("O");
		} else {
			m.setOldCarpeta(carpeta);
			m.setOldIdCarpeta("0");
		}

		if (!idCarpeta.equals("R") && !idCarpeta.equals("E")
				&& !idCarpeta.equals("P")) {
			m.setCarpeta("O");
			m.setIdCarpeta(idCarpeta);
		} else {
			m.setCarpeta(idCarpeta);
			m.setIdCarpeta(null);
		}

		try {
			int estado = buzonService.moverCarpeta(m);
			PrintWriter pw = getResponse().getWriter();
			pw.print(estado);
			pw.flush();
			pw.close();
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
		return NONE;
	}
	
	public String directorio() throws Exception{
		return SUCCESS;
	}
	
	public String buscarUsuario() {
		log.info("buscarUsuario");
		try {
			Collection<UsuarioCorreo> usuarios = buzonService.buscarContactos(aBuscar);
			getResponse().setContentType("text/xml");
			getResponse().setHeader("Cache-Control", "no-cache");
			getResponse().getWriter().write(
					"<?xml version='1.0' encoding='ISO-8859-1'?>");
			getResponse().getWriter().write("<coincidencias>");
			if (!usuarios.isEmpty()) {
				for (UsuarioCorreo correo : usuarios) {
					getResponse().getWriter().write(
							"<coincidencia>" + "<id>"
									+ correo.getIdUsuario().trim() + "</id>"
									+ "<nombreLargo>"
									+ correo.getNombreCompleto()
									+ "</nombreLargo>" + "</coincidencia>");
				}
			} else {
				getResponse()
						.getWriter()
						.write(
								"<coincidencia>"
										+ "<id>0</id>"
										+ "<nombreLargo>No se encontraron coincidencias</nombreLargo>"
										+ "<nombreCorto>No se encontraron coincidencias</nombreCorto>"
										+ "</coincidencia>");
			}
			getResponse().getWriter().write("</coincidencias>");
			getResponse().getWriter().close();
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
		return NONE;
	}
	
	public String enviarMensaje() {
		log.info("enviarMensaje()");
		Collection<UsuarioCorreo> usuarioscorreo = new ArrayList<UsuarioCorreo>();
		String[] idUsuario = { String.valueOf(getUsuarioSession().getId()) };
		Mensaje m = new Mensaje();
		if (titulo.isEmpty()) {
			titulo = "(Sin asunto)";
		}
		m.setTitulo(titulo);
		m.setContenido(contenido);
		m.setUsuario(idUsuario[0]);
		m.setEstado(1);
		m.setUsuario_envio(getUsuarioSession().getNombreCompleto()); 
		if (doc != null && doc.exists()) {
			log.info("Mensaje con adjunto");
			m.setAdjunto(1);
		} else {
			log.info("Mensaje sin adjunto");
			m.setAdjunto(0);
		}
		m.setFavorito(0);

		String[] destinos = null, ccs = null;
		String incorrectos = "";

		try {
			m.setIdMensaje(buzonService.guardarMensaje(m));
			if (!destino.trim().isEmpty()) {
				//Destinos
				destinos = destino.split(",");
				for (int i = 0; i < destinos.length; i++) {
					destinos[i] = destinos[i].trim();
					if (destinos[i].contains("<") && destinos[i].contains(">")) {
						m.setUsuario(destinos[i].substring(destinos[i].indexOf("<") + 1,destinos[i].lastIndexOf(">")));
					} else {
						m.setUsuario(destinos[i]);
					}
					m.setCarpeta("R");
					m.setTipo("D");

					UsuarioCorreo xx = null;
					try {
						xx = buzonService.verificaUsuario(m.getUsuario());
						m.setUsuario(xx.getIdUsuario());
					} catch (ServiceException e) {
						log.error(e.toString());
					}
					if (xx == null) {
						incorrectos += m.getUsuario() + ", ";
					} else {
						try {
							buzonService.guardarMensajeUsuario(m);
							usuarioscorreo.add(xx);
							UsuariosConectados.nuevoMensajeBuzon(
									Integer.parseInt(xx.getIdUsuario()), getUsuarioSession().getNombreCorto());
							if (null != xx.getEmail() && xx.getEmail().trim().length()>0) {
								SendMail sm = new SendMail();
								try {
									m.setUsuario(xx.getNombreCompleto());
									sm.envioBuzon(xx.getEmail(), m);
								} catch (ServiceException e) {
									log.error(e.toString());
								}
							}
						} catch (ServiceException e) {
							log.error(e.toString());
						}
					}
				}
				//Con copia
				if (!cc.trim().isEmpty()) {
					cc.replaceAll(";", ",");
					ccs = cc.split(",");
					for (int i = 0; i < ccs.length; i++) {
						ccs[i] = ccs[i].trim();
						if (ccs[i].contains("<")) {
							m.setUsuario(ccs[i].substring(ccs[i].indexOf("<") + 1, ccs[i].lastIndexOf(">")));
						} else {
							m.setUsuario(ccs[i]);
						}
						m.setCarpeta("R");
						m.setTipo("C");
						UsuarioCorreo xx = null;
						try {
							xx = buzonService.verificaUsuario(m.getUsuario());
							m.setUsuario(xx.getIdUsuario());
						} catch (ServiceException e) {
							log.error(e.toString());
						}
						if (xx == null) {
							incorrectos += m.getUsuario() + ", ";
						} else {
							try {
								buzonService.guardarMensajeUsuario(m);
								usuarioscorreo.add(xx);
								UsuariosConectados.nuevoMensajeBuzon(
										Integer.parseInt(xx.getIdUsuario()), getUsuarioSession().getNombreCorto());
								if (null != xx.getEmail() && xx.getEmail().trim().length()>0) {
									SendMail sm = new SendMail();
									try {
										m.setUsuario(xx.getNombreCompleto());
										sm.envioBuzon(xx.getEmail(), m);
									} catch (ServiceException e) {
										log.error(e.toString());
									}
								}
							} catch (ServiceException e) {
								log.error(e.toString());
							}
						}
					}
				}
			}
			//Guardando mensaje al mismo user como enviado
			m.setUsuario(idUsuario[0]);
			m.setCarpeta("E");
			m.setTipo("R");
			buzonService.guardarMensajeUsuario(m);
			
			//Guardando adjunto
			if (doc != null && doc.exists()) {
				log.info("Guardando adjunto ...");
				GregorianCalendar gc = new GregorianCalendar();
				int mes = gc.get(Calendar.MONTH) + 1;
				int anio = gc.get(Calendar.YEAR) - 2000;
				String anho = "" + anio;
				if (anio < 10) {
					anho = "0" + anio;
				}
				String semestre;
				if (mes <= 3) {
					semestre = "01";
				} else if (mes <= 6) {
					semestre = "02";
				} else if (mes <= 9) {
					semestre = "03";
				} else
					semestre = "04";

				Adjunto a = new Adjunto();
				a.setNombre(docFileName);
				a.setTamanio(doc.length());
				String name = doc.getAbsolutePath().replaceAll("/", Constante.SLASH);
				a = buzonService.guardarAdjunto(a, m);
				try {
					log.info("Copiando adjunto "+a.getIdAdjunto()+"...");
					String ruta = Constante.RUTA_BUZON + anho + Constante.SLASH + semestre;
					Archivo.crearDirectorio(ruta);
					Archivo.copiarArchivo(name, ruta + Constante.SLASH + a.getIdAdjunto());
				} catch (IOException e) {
					log.error(e.toString());
				}
			}
			if (incorrectos.length() > 0) {
				incorrectos = incorrectos.substring(0, incorrectos.lastIndexOf(","));
				Mensaje me = new Mensaje();
				me.setContenido(("No responder a este correo porque está desatendido.<br><br>El mensaje no ha podido ser enviado, debido a que no existen los siguientes usuarios: " + incorrectos));
				me.setTitulo("Error, mensaje no enviado.");
				me.setUsuario("campus_postmaster");
				me.setEstado(1);
				me.setAdjunto(0);
				me.setFavorito(0);

				me.setIdMensaje(buzonService.guardarMensaje(me));
				me.setCarpeta("R");
				me.setTipo("D");

				me.setUsuario(idUsuario[0]);
				buzonService.guardarMensajeUsuario(me);
				this.incorrectos = incorrectos;
			}
			USUARIOS = usuarioscorreo;
		} catch (Exception e) {
			log.error(e);
		} finally {
			try {
				CARPETAS = buzonService.traerCarpeta(Integer.parseInt(idUsuario[0]));
			} catch (ServiceException e) {
				log.error(e);
			}
		}
		return SUCCESS;
	}
	
	public String descargarArchivo() {
		String source = Constante.RUTA_BUZON + fechaAd.substring(2, 4) + Constante.SLASH
				+ fechaAd.substring(0, 2) + Constante.SLASH + idAdjunto;
		try {
			Archivo.downloadFile(archivoNombre, source, getResponse());
		} catch (FileNotFoundException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
		return NONE;
	}
	
	public String enviarMensajeSoporte() {
		log.info("enviarMensajeSoporte()");
		Mensaje m = new Mensaje();
		
		m.setContenido(contenido);
		m.setTitulo(titulo);
		m.setUsuario(String.valueOf(getUsuarioSession().getId()));
		m.setEstado(1);
		m.setFavorito(0);
		m.setAdjunto((null != doc && doc.exists()) ? 1 : 0);
		try {
			m.setIdMensaje(buzonService.guardarMensaje(m));
			
			//Guardar mensaje para soporte
			Collection<Usuario> soportes = buzonService.listarSoportes();
			for (Usuario usuario : soportes) {
				log.info("Mensaje para soporte: "+usuario.getUsuario());
				m.setUsuario(String.valueOf(usuario.getId()));
				m.setCarpeta("R");
				m.setTipo("D");
				//**Aqui podria decir que si es admin se guarde con otro estado
				buzonService.guardarMensajeUsuario(m);
			}
			
			//Guardar mensaje para el usuario
			m.setUsuario(String.valueOf(getUsuarioSession().getId()));
			m.setCarpeta("E");
			m.setTipo("R");
			buzonService.guardarMensajeUsuario(m);
			
			//Guardar Adjunto
			if (null != doc && doc.exists()) {
				log.info("Envio de correo con adjunto.");
				GregorianCalendar gc = new GregorianCalendar();
				int mes = gc.get(Calendar.MONTH) + 1;
				int anio = gc.get(Calendar.YEAR) - 2000;
				String anho = "anio";
				if (anio < 10) {
					anho = "0" + anio;
				}
				String semestre;
				if (mes <= 3) {
					semestre = "01";
				} else if (mes <= 6) {
					semestre = "02";
				} else if (mes <= 9) {
					semestre = "03";
				} else
					semestre = "04";
				Adjunto a = new Adjunto();
				a.setNombre(docFileName);
				a.setTamanio(doc.length());
				String name = doc.getAbsolutePath().replaceAll("/",Constante.SLASH);
				a.setIdAdjunto((buzonService.guardarAdjunto(a, m)).getIdAdjunto());
				try {
					log.info("Copiando adjunto "+a.getIdAdjunto()+"...");
					String ruta = Constante.RUTA_BUZON + anho + Constante.SLASH + semestre;
					Archivo.crearDirectorio(ruta);
					Archivo.copiarArchivo(name, ruta + Constante.SLASH + a.getIdAdjunto());
				} catch (IOException e) {
					log.error(e.toString());
				}
			}
			aviso = "portal.soporte.aviso.ok";
		} catch (Exception e) {
			log.error(e.toString());
			aviso = "portal.soporte.aviso.error";
		}
		return SUCCESS;
	}
	
	public String solicitarCambioDatos() throws Exception{
		String idUsuario = ""+getUsuarioSession().getId();
		Mensaje m = new Mensaje();
		Persona ud = getUsuarioSession().getPersona();
		int cont = 0;

		contenido = "El usuario <strong>" + getUsuarioSession().getUsuario()
				+ "</strong> solicita el cambio de sus datos personales:<br> ";
		if (!nombres.equalsIgnoreCase((ud.getNomuno().concat(" "
				+ ud.getNomdos())))) {
			contenido += "<br>Nombres: " + nombres;
			cont++;
		}
		if (!paterno.equalsIgnoreCase(ud.getApepaterno())) {
			contenido += "<br>Apellido paterno: " + paterno;
			cont++;
		}
		if (!materno.equalsIgnoreCase(ud.getApematerno())) {
			contenido += "<br>Apellido materno: " + materno;
			cont++;
		}
		if (!celular.equals(ud.getTelcelular())) {
			contenido += "<br>Celular: " + celular;
			cont++;
		}
		if (!telefono.equals(ud.getTeldomicilio())) {
			contenido += "<br>Telefono: " + telefono;
			cont++;
		}
		if (!direccion.equalsIgnoreCase(ud.getDirdomicilio())) {
			contenido += "<br>Direccion: " + direccion;
			cont++;
		}

		if (cont > 0) {
			m.setContenido(contenido);
			m.setTitulo("Modificación de datos personales");
			m.setUsuario(idUsuario);
			m.setEstado(1);
			m.setFavorito(0);
			m.setAdjunto(0);
			
			m.setIdMensaje(buzonService.guardarMensaje(m));
			
			//Guardar mensaje a soporte
			Collection<Usuario> soportes = buzonService.listarSoportes();
			for (Usuario usuario : soportes) {
				log.info("Mensaje para soporte: "+usuario.getUsuario());
				m.setUsuario(String.valueOf(usuario.getId()));
				m.setCarpeta("R");
				m.setTipo("D");
				buzonService.guardarMensajeUsuario(m);
			}
			//Guardar mensaje al mismo usuario
			m.setUsuario(idUsuario);
			m.setCarpeta("E");
			m.setTipo("R");
			buzonService.guardarMensajeUsuario(m);
			setMessage("La solicitud ha sido enviado a soporte.");
			
		}
		return SUCCESS;
	}
	
	public String mantenimiento() throws Exception{
		log.info("mantenimiento()");
		
		getRequest().setAttribute("CARGA", buzonService.verUsoEnDisco());
		
		return SUCCESS;
	}
	
	public String limpiarBuzon() throws Exception{
		log.info("limpiarBuzon() "+fecha);
		
		if(fecha != null && fecha.trim().length()>0){
			int total = buzonService.limpiarBuzon(fecha);
			log.info(total+" correos eliminados.");
		}
		return SUCCESS;
	}
	
	

	public String masivo() {
		log.info("masivo(): "+destino);
		// Los masivos viene separados por coma
		try {
			UsuarioCorreo usuario = null;
			if(!destino.contains(",")) {
				
				usuario = buzonService.verificaUsuario(destino);
				nombres = usuario.getNombreCompleto();
				
			} else {
				
				String[] nombresArray = destino.split(",");
				nombres = "";
				for (int i = 0; i < nombresArray.length; i++) {
					usuario = buzonService.verificaUsuario(nombresArray[i]);
					nombres += usuario.getNombreCompleto()+",";
				}
				
			}
		} catch (ServiceException e) {
			log.error(e.toString());
			return ERROR;
		}
		return SUCCESS;
	}
/*
	public String enviarMensajeM() {
		Collection<UsuarioCorreo> usuarioscorreo = new ArrayList<UsuarioCorreo>();
		String[] idUsuario = { getUsuarioSession().getId() };
		Mensaje m = new Mensaje();
		if (titulo.isEmpty()) {
			titulo = "(Sin asunto)";
		}
		m.setTitulo(titulo);
		m.setContenido(contenido);
		m.setUsuario(idUsuario[0]);
		m.setEstado(1);
		m.setUsuario_envio(getUsuarioSession().getNombreCompleto());
		if (doc != null && doc.exists()) {
			m.setAdjunto(1);
		} else {
			m.setAdjunto(0);
		}
		m.setEstado(0);
		m.setFavorito(0);

		String[] destinos = null, ccs = null;
		String incorrectos = "";

		try {
			m.setIdMensaje(buzonService.guardarMensaje(m));
			if (!destino.trim().isEmpty()) {
				destinos = destino.split(",");
				for (int i = 0; i < destinos.length; i++) {
					destinos[i] = destinos[i].trim();
					if (destinos[i].contains("<") && destinos[i].contains(">")) {
						m.setUsuario(destinos[i]
								.substring(destinos[i].indexOf("<") + 1,
										destinos[i].lastIndexOf(">")));
					} else {
						m.setUsuario(destinos[i]);
					}
					m.setCarpeta("R");
					m.setTipo("D");

					UsuarioCorreo xx = null;
					try {
						xx = buzonService.verificaUsuario(m.getUsuario());
					} catch (ServiceException e) {
						log.error(e.toString());
					}
					if (xx == null) {
						incorrectos += m.getUsuario() + ", ";
					} else {
						try {
							buzonService.guardarMensajeUsuario(m);
							usuarioscorreo.add(xx);
							UsuariosConectados.nuevoMensajeBuzon(
									m.getUsuario(), getUsuarioSession()
											.getUsuarioDato()
											.getNombreApellidoCorto());
							if (Util.obtenerUsuariosRedireccionCorreo()
									.containsKey(m.getUsuario())) {
								SendMail sm = new SendMail();
								try {
									String usuario = m.getUsuario();
									m.setUsuario(xx.getNombreCompleto());
									sm.envioBuzon(Util
											.obtenerUsuariosRedireccionCorreo()
											.get(usuario), m);
								} catch (ServiceException e) {
									log.error(e.toString());
								}
							}
						} catch (ServiceException e) {
							log.error(e.toString());
						}
					}
				}
				if (!cc.trim().isEmpty()) {
					cc.replaceAll(";", ",");
					ccs = cc.split(",");
					for (int i = 0; i < ccs.length; i++) {
						ccs[i] = ccs[i].trim();
						if (ccs[i].contains("<")) {
							m.setUsuario(ccs[i].substring(
									ccs[i].indexOf("<") + 1, ccs[i]
											.lastIndexOf(">")));
						} else {
							m.setUsuario(ccs[i]);
						}
						m.setCarpeta("R");
						m.setTipo("C");
						UsuarioCorreo xx = null;
						try {
							xx = buzonService.verificaUsuario(m.getUsuario());
						} catch (ServiceException e) {
							log.error(e.toString());
						}
						if (xx == null) {
							incorrectos += m.getUsuario() + ", ";
						} else {
							try {
								buzonService.guardarMensajeUsuario(m);
								usuarioscorreo.add(xx);
								UsuariosConectados.nuevoMensajeBuzon(m
										.getUsuario(), getUsuarioSession()
										.getUsuarioDato()
										.getNombreApellidoCorto());
								if (Util.obtenerUsuariosRedireccionCorreo()
										.containsKey(m.getUsuario())) {
									SendMail sm = new SendMail();
									try {
										String usuario = m.getUsuario();
										m.setUsuario(xx.getNombreCompleto());
										sm
												.envioBuzon(
														Util
																.obtenerUsuariosRedireccionCorreo()
																.get(usuario),
														m);
									} catch (ServiceException e) {
										log.error(e.toString());
									}
								}
							} catch (ServiceException e) {
								log.error(e.toString());
							}
						}
					}
				}
			}
			m.setUsuario(idUsuario[0]);
			m.setCarpeta("E");
			m.setTipo("R");
			buzonService.guardarMensajeUsuario(m);
			if (doc != null && doc.exists()) {
				GregorianCalendar gc = new GregorianCalendar();
				int mes = gc.get(Calendar.MONTH) + 1;
				int anio = gc.get(Calendar.YEAR) - 2000;
				String anho = "anio";
				if (anio < 10) {
					anho = "0" + anio;
				}
				String semestre;
				if (mes <= 3) {
					semestre = "01";
				} else if (mes <= 6) {
					semestre = "02";
				} else if (mes <= 9) {
					semestre = "03";
				} else
					semestre = "04";

				Adjunto a = new Adjunto();
				a.setNombre(docFileName);
				a.setTamanio(doc.length());
				String name = doc.getAbsolutePath().replaceAll("/",
						Constante.SLASH);
				a.setIdAdjunto((buzonService.guardarAdjunto(a, m))
						.getIdAdjunto());
				try {
					Archivo.crearDirectorio(Constante.HOME_BUZON);
					Archivo.crearDirectorio(Constante.HOME_BUZON
							+ Constante.SLASH + anho);
					Archivo.crearDirectorio(Constante.HOME_BUZON
							+ Constante.SLASH + anho + Constante.SLASH
							+ semestre);
					Archivo.copiarArchivo(name, Constante.HOME_BUZON
							+ Constante.SLASH + anho + Constante.SLASH
							+ semestre + Constante.SLASH + a.getIdAdjunto());
				} catch (IOException e) {
					e.toString();
				}
			}
			if (incorrectos.length() > 0) {
				incorrectos = incorrectos.substring(0, incorrectos
						.lastIndexOf(","));
				Mensaje me = new Mensaje();
				me.setContenido(("No responder a este correo porque "
						+ "está desatendido.<br><br>El mensaje no ha "
						+ "podido ser enviado, debido a que no existen"
						+ " los siguientes usuarios: " + incorrectos));
				me.setTitulo("Error, mensaje no enviado.");
				me.setUsuario("campus_postmaster");
				me.setEstado(1);
				me.setAdjunto(0);
				me.setEstado(0);
				me.setFavorito(0);

				me.setIdMensaje(buzonService.guardarMensaje(me));
				me.setCarpeta("R");
				me.setTipo("D");

				me.setUsuario(idUsuario[0]);
				buzonService.guardarMensajeUsuario(me);
				this.incorrectos = incorrectos;
			}
			USUARIOS = usuarioscorreo;
		} catch (ServiceException e) {
			log.error(e.toString());
		}
		return SUCCESS;
	}



	public String buscar() {
		String iUsuario = getUsuarioSession().getId();
		try {
			Collection<Mensaje> mensajes = buzonService.buscarMesaje(aBuscar,
					iUsuario);
			getResponse().setContentType("text/xml");
			getResponse().setHeader("Cache-Control", "no-cache");
			getResponse().getWriter().write(
					"<?xml version='1.0' encoding='ISO-8859-1'?>");
			getResponse().getWriter().write("<mensajes>");

			if (!MENSAJES.isEmpty()) {
				for (Mensaje mensaje : mensajes) {
					String xx = "", xxx = "";
					getResponse().getWriter().write(
							"<mensaje>" + "<id>" + mensaje.getIdMensaje()
									+ "</id>" + "<titulo><![CDATA["
									+ mensaje.getTitulo() + "]]></titulo>"
									+ "<contenido><![CDATA["
									+ mensaje.getContenido()
									+ "]]></contenido>"
									+ "<remitente><![CDATA["
									+ mensaje.getRemitente().getNombreCorto()
									+ "]]></remitente>" + "<idUsuarioEnvio>"
									+ mensaje.getRemitente().getId()
									+ "</idUsuarioEnvio>"
									+ "<carpeta><![CDATA["
									+ mensaje.getCarpeta() + "]]></carpeta>"
									+ "<fecha_envio>"
									+ mensaje.getFechaToStringEnvio()
									+ "</fecha_envio>" + "<fecha_leido>"
									+ mensaje.getFechaToStringLeido()
									+ "</fecha_leido>" + "<adjunto>"
									+ mensaje.getAdjunto() + "</adjunto>"
									+ "<favorito>" + mensaje.getFavorito()
									+ "</favorito>" + "<estado>"
									+ mensaje.getEstado() + "</estado>");

					getResponse().getWriter().write("<destinos><![CDATA[");
					if (!mensaje.getDestinos().isEmpty()) {
						for (int p = 0; p < mensaje.getDestinos().size(); p++) {
							xx += mensaje.getDestinos().get(p).getNombreCorto()
									+ "&lt;"
									+ mensaje.getDestinos().get(p)
											.getId() + "&gt;,";
						}
						getResponse().getWriter().write(
								xx.substring(0, xx.lastIndexOf(",")));
					}
					getResponse().getWriter().write("]]></destinos>");

					getResponse().getWriter().write("<ccs><![CDATA[");
					if (!(mensaje.getCcs()).isEmpty()) {
						for (int p = 0; p < mensaje.getCcs().size(); p++) {
							xxx += mensaje.getCcs().get(p).getNombreCorto()
									+ "&lt;"
									+ mensaje.getCcs().get(p).getId()
									+ "&gt;,";
						}
						getResponse().getWriter().write(
								xxx.substring(0, xxx.lastIndexOf(",")));
					}
					getResponse().getWriter().write("]]></ccs>");

					if (mensaje.getAdjunto() == 1) {
						getResponse().getWriter()
								.write(
										"<adjNom><![CDATA["
												+ mensaje.getAdjuntos().get(0)
														.getNombre()
												+ "]]></adjNom>"
												+ "<adjId>"
												+ mensaje.getAdjuntos().get(0)
														.getIdAdjunto().trim()
												+ "</adjId>"
												+ "<adjSiz>"
												+ mensaje.getAdjuntos().get(0)
														.getTamanio()
												+ "</adjSiz>"
												+ "<adjFe>"
												+ mensaje.getAdjuntos().get(0)
														.getFechaAdjunto()
												+ "</adjFe>");
					}
					getResponse().getWriter().write("</mensaje>");

				}

			}
			getResponse().getWriter().write("</mensajes>");
			getResponse().getWriter().close();

		} catch (ServiceException se) {
			getResponse().setContentType("text/xml");
			getResponse().setHeader("Cache-Control", "no-cache");
			try {
				getResponse().getWriter().write(
						"<?xml version='1.0' encoding='ISO-8859-1'?>");
				getResponse()
						.getWriter()
						.write(
								"<mensajes><total>0</total>"
										+ "<actual>0</actual><start>0</start><end>0</end>"
										+ "<mensaje>" + "</mensaje></mensajes>");
				getResponse().getWriter().close();
			} catch (IOException e) {
				log.error(e.toString());
			}
		} catch (IOException e) {
			log.error(e.toString());
		}
		return NONE;
	}



	public String traerCarpeta() {
		String idUsuario = getUsuarioSession().getId();
		try {
			Collection<Carpeta> carpetas = buzonService.traerCarpeta(idUsuario);
			getResponse().setContentType("text/xml");
			getResponse().setHeader("Cache-Control", "no-cache");
			getResponse().getWriter().write(
					"<?xml version='1.0' encoding='ISO-8859-1'?>");
			getResponse().getWriter().write("<carpetas>");
			for (Carpeta carpeta : carpetas) {
				getResponse().getWriter().write(
						"<carpeta>" + "<id>" + carpeta.getIdCarpeta().trim()
								+ "</id>" + "<nombre><![CDATA["
								+ carpeta.getNombre() + "]]></nombre>"
								+ "</carpeta>");
			}
			getResponse().getWriter().write("</carpetas>");
			getResponse().getWriter().close();
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
		return NONE;
	}
*/
	
	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getFec_nac() {
		return fec_nac;
	}

	public void setFec_nac(String fec_nac) {
		this.fec_nac = fec_nac;
	}

	public String getPaterno() {
		return paterno;
	}

	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getMaterno() {
		return materno;
	}

	public void setMaterno(String materno) {
		this.materno = materno;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String soporte() {
		return SUCCESS;
	}

	public File getDoc() {
		return doc;
	}

	public void setDoc(File doc) {
		this.doc = doc;
	}

	public String getDocContentType() {
		return docContentType;
	}

	public void setDocContentType(String docContentType) {
		this.docContentType = docContentType;
	}

	public String getDocFileName() {
		return docFileName;
	}

	public void setDocFileName(String docFileName) {
		docFileName = docFileName.replaceAll("#", " Nº");
		docFileName = docFileName.replaceAll("&", " y ");
		this.docFileName = docFileName;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido.replaceAll("]]>", "]]->");
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo.replaceAll("]]>", "]]->");
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		destino = destino.toLowerCase();
		this.destino = destino.replaceAll(";", ",").trim();
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getIdAdjunto() {
		return idAdjunto;
	}

	public void setIdAdjunto(String idAdjunto) {
		this.idAdjunto = idAdjunto;
	}

	public int getNoleidos() {
		return noleidos;
	}

	public void setNoleidos(int noleidos) {
		this.noleidos = noleidos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Collection<UsuarioCorreo> getUSUARIOS() {
		return USUARIOS;
	}

	public void setUSUARIOS(Collection<UsuarioCorreo> usuarios) {
		USUARIOS = usuarios;
	}

	public String getTipoR() {
		return tipoR;
	}

	public void setTipoR(String tipoR) {
		this.tipoR = tipoR;
	}

	public String getIdr() {
		return idr;
	}

	public void setIdr(String idr) {
		this.idr = "<" + idr + ">";
	}

	public void setIncorrectos(String incorrectos) {
		this.incorrectos = incorrectos;
	}

	public int getPagActual() {
		return pagActual;
	}

	public void setPagActual(int pagActual) {
		this.pagActual = pagActual;
	}

	public String getArchivoNombre() {
		return archivoNombre;
	}

	public void setArchivoNombre(String archivoNombre) {
		this.archivoNombre = archivoNombre;
	}

	public Collection<Carpeta> getCARPETAS() {
		return CARPETAS;
	}

	public void setCARPETAS(Collection<Carpeta> carpetas) {
		CARPETAS = carpetas;
	}

	public String getIdCarpeta() {
		return idCarpeta;
	}

	public void setIdCarpeta(String idCarpeta) {
		this.idCarpeta = idCarpeta;
	}

	public String getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje;
	}

	public String getABuscar() {
		return aBuscar;
	}

	public void setABuscar(String buscar) {
		this.aBuscar = "%" + buscar.replaceAll(" ", "%") + "%";
	}

	public String getCarpeta() {
		return carpeta;
	}

	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}

	public void setBuzonService(BuzonService buzonService) {
		this.buzonService = buzonService;
	}

	public Collection<Mensaje> getMENSAJES() {
		return MENSAJES;
	}

	public void setMENSAJES(Collection<Mensaje> mensajes) {
		MENSAJES = mensajes;
	}

	public String getIncorrectos() {
		return incorrectos;
	}

	public String getAn() {
		return an;
	}

	public void setAn(String an) {
		this.an = an;
	}

	public String getFechaAd() {
		return fechaAd;
	}

	public void setFechaAd(String fechaAd) {
		this.fechaAd = fechaAd;
	}

	public String getSem() {
		return sem;
	}

	public void setSem(String sem) {
		this.sem = sem;
	}

	public int getCantidadPorPagina() {
		return cantidadPorPagina;
	}

	public void setCantidadPorPagina(int cantidadPorPagina) {
		this.cantidadPorPagina = cantidadPorPagina;
	}

	public String getAviso() {
		return aviso;
	}

	public void setAviso(String aviso) {
		this.aviso = aviso;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	private String fecha;

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFecha() {
		return fecha;
	}


}