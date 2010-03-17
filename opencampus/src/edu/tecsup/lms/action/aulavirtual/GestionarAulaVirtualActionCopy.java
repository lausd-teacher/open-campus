package edu.tecsup.lms.action.aulavirtual;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;

import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.AulaVirtual;
import edu.tecsup.lms.modelo.Especialidad;
import edu.tecsup.lms.modelo.Periodo;
import edu.tecsup.lms.modelo.Silabo;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.modelo.aulavirtual.MatriculaRol;
import edu.tecsup.lms.modelo.ficha.FichaHerramienta;
import edu.tecsup.lms.modelo.ficha.FichaRecurso;
import edu.tecsup.lms.modelo.ficha.FichaUnidad;
import edu.tecsup.lms.modelo.ficha.FichaUnidadRecurso;
import edu.tecsup.lms.modelo.ficha.Publicacion;
import edu.tecsup.lms.service.AulaVirtualService;
import edu.tecsup.lms.service.FichaConfiguracionService;
import edu.tecsup.lms.service.FichaService;
import edu.tecsup.lms.service.PeriodoService;
import edu.tecsup.lms.service.PublicacionService;
import edu.tecsup.lms.util.Archivo;
import edu.tecsup.lms.util.Constante;
import edu.tecsup.lms.util.Formato;

public class GestionarAulaVirtualActionCopy extends AulaVirtualAction {

	private String id;

	private String tipo;

	private PublicacionService publicacionService;

	private PeriodoService periodoService;

	private AulaVirtualService aulaVirtualService;

	public PeriodoService getPeriodoService() {
		return periodoService;
	}

	public void setPeriodoService(PeriodoService periodoService) {
		this.periodoService = periodoService;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public PublicacionService getPublicacionService() {
		return publicacionService;
	}

	public void setPublicacionService(PublicacionService publicacionService) {
		this.publicacionService = publicacionService;
	}

	public String ingresar() {
		log.info("ingresar()");
		String idFicha = id;
		AulaVirtual aula = null;
		Collection<Publicacion> pubs = new ArrayList<Publicacion>();
		try {
			if (null != idFicha && 0 < idFicha.length()) {
				aula = new AulaVirtual();
				aula.setIdFicha(Integer.parseInt(idFicha));
				aula = getAulaVirtualService().obtenerAulaVirtualAdministrar(
						aula, getUsuarioSession());
				if (null == aula) {
					return PORTAL;
				}
				if (0 == aula.getUnidades().size()
						&& Constante.ETIQUETA_VIRTUAL.equals(String
								.valueOf(aula.getIdEtiqueta()))) {
					Silabo silabo = getAulaVirtualService()
							.obtenerSilaboPorIDCurso(aula.getIdCurso());
					getRequest().setAttribute("SILABO_DISPONIBLE", silabo);
				}
				GregorianCalendar hoy = new GregorianCalendar();
				if (aula.getFechaRevision().before(hoy)) {
					aula.getRol().setIdrol(Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE);
					pubs = publicacionService.listarPublicacionesNoLeidas(aula
							.getIdMatricula(), Integer.parseInt(id));
					getRequest().getSession().setAttribute("publicaciones",
							pubs);
					getRequest().getSession().setAttribute("n",
							"" + pubs.size());

					getRequest().getSession().setAttribute(
							Constante.AULA_ACTUAL, aula);
					return "RESPONSABLE";
				}
				getRequest().getSession().setAttribute(Constante.AULA_ACTUAL,
						aula);
			}
		} catch (ServiceException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return SUCCESS;
	}
	
	public String salir() {
		log.info("salir() ");
		if (null != getAulaVirtualSession()) {
			getSession().remove(Constante.AULA_ACTUAL);
		}
		return NONE;
	}
	
	public String guardarNoAuditable() throws Exception{
		log.info("guardarNoAuditable() noauditable:"+tipo);
		if(tipo != null && "0".equals(tipo) || "1".equals(tipo)){
			PrintWriter out = getResponse().getWriter();
			aulaVirtualService.guardarNoAuditable(getAulaVirtualSession().getIdFicha(), Integer.parseInt(tipo));
			if("1".equals(tipo))
				getAulaVirtualSession().setNoAuditable(true);
			else
				getAulaVirtualSession().setNoAuditable(false);
			out.print("OK");
		}
		
		return NONE;
	}
	
	public String asignarSilabo() {
		String idFicha = getRequest().getParameter("idFicha");
		String idSilabo = getRequest().getParameter("idSilabo");
		Usuario usuario = (Usuario) getRequest().getSession().getAttribute(
				Constante.USUARIO_ACTUAL);

		try {
			if (null != idFicha && 0 < idFicha.length() && null != idSilabo
					&& 0 < idSilabo.length() && null != usuario) {
				AulaVirtual aulaVirtual = new AulaVirtual();
				aulaVirtual.setIdFicha(Integer.parseInt(idFicha));
				aulaVirtual.setIdCurso(Integer.parseInt(idSilabo));
				aulaVirtual.setUsuarioModificacion(usuario.getIdUsuario());
				getAulaVirtualService().asignarSilabo(aulaVirtual);

				aulaVirtual = getAulaVirtualService().obtenerAulaVirtual(
						aulaVirtual, getUsuarioSession());

				getRequest().getSession().setAttribute(Constante.AULA_ACTUAL,
						aulaVirtual);

			}
		} catch (ServiceException e) {
			log.error(e.getMessage());
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		id = getRequest().getParameter("idFicha");
		return SUCCESS;
	}

	public String ingresarPre() {
		log.info("ingresar() ");
		int idMatricula = 0;
		AulaVirtual aula = null;
		Collection<Publicacion> pubs = new ArrayList<Publicacion>();
		try {
			if (null != id && 0 < id.length()) {
				aula = new AulaVirtual();
				aula.setIdFicha(Integer.parseInt(id));
				aula = getAulaVirtualService().obtenerAulaVirtual(aula,getUsuarioSession());
				if (null == aula) {
					log.error("Aula no existe");
					return NONE;
				}
				idMatricula = aula.getIdMatricula();
				pubs = publicacionService.listarPublicacionesNoLeidas(
						idMatricula, Integer.parseInt(id));
				getRequest().setAttribute(Constante.AULA_ACTUAL + "PRE", aula);
				getRequest().setAttribute("publicaciones", pubs);
				getRequest().setAttribute("n", "" + pubs.size());
				return "ESTUDIANTE";
			}
		} catch (ServiceException e) {
			log.error(e.getMessage());
			return CURSO;
		} catch (Exception e) {
			log.error(e.getMessage());
			return CURSO;
		}
		return CURSO;
	}

	public String miClase() {
		AulaVirtual aula = (AulaVirtual) getSession().get(
				Constante.AULA_ACTUAL);
		Collection<MatriculaRol> usuarios = new ArrayList<MatriculaRol>();
		Collection<String> seccion = new ArrayList<String>();
		Collection<Especialidad> formaciones = new ArrayList<Especialidad>();
		try {
			if (null != aula && 0 != aula.getIdFicha()) {
				usuarios = getMatriculaService()
						.obtenerUsuarioMatriculaGestionar(aula.getIdFicha());
				seccion = getFichaService().obtenerSeccionesFicha(
						aula.getIdFicha());
				formaciones = getFichaService().obtenerFormacionFicha(
						aula.getIdFicha());
			}
		} catch (ServiceException e) {
		}
		getRequest().setAttribute("USUARIOS_MATRICULADOS", usuarios);
		getRequest().setAttribute("USUARIOS_SECCIONES", seccion);
		getRequest().setAttribute("USUARIOS_FORMACIONES", formaciones);
		return SUCCESS;
	}

	public String mostrar() {
		log.info("mostrar()");
		String idFicha = String.valueOf(((AulaVirtual) getSession()
				.get(Constante.AULA_ACTUAL)).getIdFicha());
		try {
			getRequest().setAttribute("aula_pesos",
					fichaConfiguracionService.obtenerFichaRecurso(idFicha));
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}

		try {
			getRequest().setAttribute("aula_pesos_herramientas",
					fichaConfiguracionService.obtenerFichaHerramienta(idFicha));
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}
		try {
			getRequest().setAttribute("periodos",
					periodoService.buscarPorTipo());
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}
		return SUCCESS;
	}

	public String guardarPesos() {
		log.info("guardarPesos()");
		FichaRecurso fr = new FichaRecurso();
		fr.setIdFicha(String.valueOf(((AulaVirtual) getSession().get(
				Constante.AULA_ACTUAL)).getIdFicha()));
		fr.setUsuarioModificacion(((Usuario) (getSession()
				.get(Constante.USUARIO_ACTUAL))).getIdUsuario());

		// Recurso Laboratorio
		fr.setIdRecurso(Constante.RECURSO_ID_LABORATORIO);
		fr.setPeso(pl);
		try {
			fichaConfiguracionService.actualizarFichaRecurso(fr);
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}

		// Recurso Dialogo
		fr.setIdRecurso(Constante.RECURSO_ID_DIALOGO);
		fr.setPeso(pd);
		try {
			fichaConfiguracionService.actualizarFichaRecurso(fr);
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}

		// Recurso Trabajo
		fr.setIdRecurso(Constante.RECURSO_ID_TRABAJO);
		fr.setPeso(pt);
		try {
			fichaConfiguracionService.actualizarFichaRecurso(fr);
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}

		// Recurso Actividad grupal
		fr.setIdRecurso(Constante.RECURSO_ID_ACTIVIDAD_GRUPAL);
		fr.setPeso(pg);
		try {
			fichaConfiguracionService.actualizarFichaRecurso(fr);
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}

		// Recurso Test
		fr.setIdRecurso(Constante.RECURSO_ID_TEST);
		fr.setPeso(pe);
		try {
			fichaConfiguracionService.actualizarFichaRecurso(fr);
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}

		return null;
	}

	public String guardarHerramientas() {

		FichaHerramienta fh = new FichaHerramienta();
		fh.setIdFicha(String.valueOf(((AulaVirtual) getSession().get(
				Constante.AULA_ACTUAL)).getIdFicha()));
		fh.setUsuarioModificacion(((Usuario) (getSession()
				.get(Constante.USUARIO_ACTUAL))).getIdUsuario());

		// Herramienta mensajes
		fh.setIdHerramienta(Constante.HERRAMIENTA_ID_MENSAJES);
		fh.setEstado(this.mensajesExiste);
		fh.setDeshabilitado_doc(this.mensajesDocente);
		fh.setDeshabilitado_estu(this.mensajesEstudiante);
		try {
			fichaConfiguracionService.actualizarFichaHerramienta(fh);
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}

		// Herramienta recursos
		fh.setIdHerramienta(Constante.HERRAMIENTA_ID_RECURSOS);
		fh.setEstado(this.recursosExiste);
		fh.setDeshabilitado_doc(this.recursosDocente);
		fh.setDeshabilitado_estu(this.recursosEstudiante);
		try {
			fichaConfiguracionService.actualizarFichaHerramienta(fh);
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}

		// Herramienta chat
		fh.setIdHerramienta(Constante.HERRAMIENTA_ID_CHAT);
		fh.setEstado(this.chatExiste);
		fh.setDeshabilitado_doc(this.chatDocente);
		fh.setDeshabilitado_estu(this.chatEstudiante);
		try {
			fichaConfiguracionService.actualizarFichaHerramienta(fh);
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}

		return null;
	}

	public String fichaUnidadCambiarEstado() {
		String idFicha = String.valueOf(((AulaVirtual) getSession()
				.get(Constante.AULA_ACTUAL)).getIdFicha());
		String idSilabo = String.valueOf(((AulaVirtual) getSession()
				.get(Constante.AULA_ACTUAL)).getIdSilabo());
		FichaUnidad vo1 = null;
		try {
			vo1 = fichaConfiguracionService.obtenerFichaUnidad(idFicha,
					idSilabo, idUnidad);
		} catch (ServiceException e1) {
			log.error(e1.getMessage());
		}
		FichaUnidad vo = new FichaUnidad();
		vo.setIdFicha(idFicha);
		vo.setIdSilabo(idSilabo);
		vo.setIdUnidad(idUnidad);
		vo.setEstado((vo1.getEstado().equals("1")) ? "0" : "1");
		vo.setUsuarioModificacion(((Usuario) (getSession()
				.get(Constante.USUARIO_ACTUAL))).getIdUsuario());
		try {
			fichaConfiguracionService.actualizarFichaUnidad(vo);
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public String fichaUnidadRecursoCambiarEstado() {
		String idFicha = String.valueOf(((AulaVirtual) getSession()
				.get(Constante.AULA_ACTUAL)).getIdFicha());
		FichaUnidadRecurso vo1 = null;
		try {
			vo1 = fichaConfiguracionService.obtenerFichaUnidadRecurso(idFicha,
					idUnidad, idRecurso);
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}

		FichaUnidadRecurso vo = new FichaUnidadRecurso();
		vo.setIdFicha(idFicha);
		vo.setIdUnidad(idUnidad);
		vo.setIdRecurso(idRecurso);
		vo.setUsuarioModificacion(((Usuario) (getSession()
				.get(Constante.USUARIO_ACTUAL))).getIdUsuario());
		vo.setPeso(vo1.getPeso());
		vo.setCalifica(vo1.getCalifica());
		vo.setDeshabilitadoDoc(vo1.getDeshabilitadoDoc());
		vo.setDeshabilitadoEstu(vo1.getDeshabilitadoEstu());
		String nuevoEstado = (vo1.getEstado().equals("1")) ? "0" : "1";
		vo.setEstado(nuevoEstado);

		try {
			fichaConfiguracionService.actualizarFichaUnidadRecurso(vo);
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}

		return null;
	}

	public String fichaUnidadRecursoCambiarEstadoDocente() {
		String idFicha = String.valueOf(((AulaVirtual) getSession()
				.get(Constante.AULA_ACTUAL)).getIdFicha());
		FichaUnidadRecurso vo1 = null;
		try {
			vo1 = fichaConfiguracionService.obtenerFichaUnidadRecurso(idFicha,
					idUnidad, idRecurso);
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}

		FichaUnidadRecurso vo = new FichaUnidadRecurso();
		vo.setIdFicha(idFicha);
		vo.setIdUnidad(idUnidad);
		vo.setIdRecurso(idRecurso);
		vo.setUsuarioModificacion(((Usuario) (getSession()
				.get(Constante.USUARIO_ACTUAL))).getIdUsuario());
		vo.setPeso(vo1.getPeso());
		vo.setCalifica(vo1.getCalifica());
		String nuevoEstado = (vo1.getDeshabilitadoDoc().equals("1")) ? "0"
				: "1";
		vo.setDeshabilitadoDoc(nuevoEstado);
		vo.setDeshabilitadoEstu(vo1.getDeshabilitadoEstu());
		vo.setEstado(vo1.getEstado());

		try {
			fichaConfiguracionService.actualizarFichaUnidadRecurso(vo);
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}

		return null;
	}

	public String fichaUnidadRecursoCambiarEstadoEstudiante() {
		String idFicha = String.valueOf(((AulaVirtual) getSession()
				.get(Constante.AULA_ACTUAL)).getIdFicha());
		FichaUnidadRecurso vo1 = null;
		try {
			vo1 = fichaConfiguracionService.obtenerFichaUnidadRecurso(idFicha,
					idUnidad, idRecurso);
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}

		FichaUnidadRecurso vo = new FichaUnidadRecurso();
		vo.setIdFicha(idFicha);
		vo.setIdUnidad(idUnidad);
		vo.setIdRecurso(idRecurso);
		vo.setUsuarioModificacion(((Usuario) (getSession()
				.get(Constante.USUARIO_ACTUAL))).getIdUsuario());
		vo.setPeso(vo1.getPeso());
		vo.setCalifica(vo1.getCalifica());
		vo.setDeshabilitadoDoc(vo1.getDeshabilitadoDoc());
		String nuevoEstado = (vo1.getDeshabilitadoEstu().equals("1")) ? "0"
				: "1";
		vo.setDeshabilitadoEstu(nuevoEstado);
		vo.setEstado(vo1.getEstado());

		try {
			fichaConfiguracionService.actualizarFichaUnidadRecurso(vo);
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}

		return null;
	}

	public String fichaUnidadRecursoCambiarCalifica() {
		String idFicha = String.valueOf(((AulaVirtual) getSession()
				.get(Constante.AULA_ACTUAL)).getIdFicha());
		FichaUnidadRecurso vo1 = null;
		try {
			vo1 = fichaConfiguracionService.obtenerFichaUnidadRecurso(idFicha,
					idUnidad, idRecurso);
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}

		FichaUnidadRecurso vo = new FichaUnidadRecurso();
		vo.setIdFicha(idFicha);
		vo.setIdUnidad(idUnidad);
		vo.setIdRecurso(idRecurso);
		vo.setUsuarioModificacion(((Usuario) (getSession()
				.get(Constante.USUARIO_ACTUAL))).getIdUsuario());
		vo.setPeso(vo1.getPeso());
		String nuevoEstado = (vo1.getCalifica().equals("1")) ? "0" : "1";
		vo.setCalifica(nuevoEstado);
		vo.setDeshabilitadoDoc(vo1.getDeshabilitadoDoc());
		vo.setDeshabilitadoEstu(vo1.getDeshabilitadoEstu());
		vo.setEstado(vo1.getEstado());

		try {
			fichaConfiguracionService.actualizarFichaUnidadRecurso(vo);
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}

		return null;
	}

	public String copiarTexto() {
		log.info("GestionarAulaVirtualAction: copiarTexto: inicio");
		String idunidad = getRequest().getParameter("idunidad");

		String indice = getRequest().getParameter("indice");
		String idunidadconceros = Formato.completarCeros(idunidad,
				8);
		String pathsession = Constante.RUTA_REPOSITORIO_TEMPORAL
				+ Constante.SLASH + getRequest().getSession().getId();

		String origen = Constante.RUTA_REPOSITORIO_UNITS + Constante.SLASH
				+ idunidadconceros + Constante.SLASH + Constante.HOME_TEXTO
				+ Constante.SLASH + Constante.TEXTO_PDF;
		try {
			Archivo.crearDirectorio(pathsession);
			Archivo.crearDirectorio(pathsession + Constante.SLASH + idunidad);
			Archivo.copiarArchivo(origen, pathsession + Constante.SLASH
					+ idunidad + Constante.SLASH + Constante.TEXTO + indice
					+ Constante.EXTENSION_PDF);
		} catch (IOException e) {
			log.info("GestionarAulaVirtualAction: copiarTexto: IOException: "
					+ e);
		}
		log.info("GestionarAulaVirtualAction: copiarTexto: origen: " + origen);
		return null;
	}

	public String copiarRepaso() {
		log.info("GestionarAulaVirtualAction: copiarRepaso: inicio");
		String idunidad = getRequest().getParameter("idunidad");

		String idunidadconceros = Formato.completarCeros(idunidad,
				8);

		String pathsession = Constante.RUTA_REPOSITORIO_TEMPORAL
				+ Constante.SLASH + getRequest().getSession().getId();

		String origen = Constante.RUTA_REPOSITORIO_UNITS + Constante.SLASH
				+ idunidadconceros + Constante.SLASH + Constante.HOME_REPASO;

		Archivo.copiarRepaso(origen, idunidad, pathsession);
		log
				.info("GestionarAulaVirtualAction: copiarRepaso: Directorio copiado: "
						+ pathsession + "/" + idunidad);
		log.info("GestionarAulaVirtualAction: copiarRepaso: origen: " + origen);
		return NONE;
	}

	public String copiarLaboratorio() {
		log.info("GestionarAulaVirtualAction: copiarLaboratorio: inicio");
		String idunidad = getRequest().getParameter("idunidad");

		String indice = getRequest().getParameter("indice");
		String idunidadconceros = Formato.completarCeros(idunidad,
				8);
		String pathsession = Constante.RUTA_REPOSITORIO_TEMPORAL
				+ Constante.SLASH + getRequest().getSession().getId();
		String origen = Constante.RUTA_REPOSITORIO_UNITS + Constante.SLASH
				+ idunidadconceros + Constante.SLASH
				+ Constante.HOME_LABORATORIO + Constante.SLASH
				+ Constante.LAB_PDF;
		try {
			Archivo.crearDirectorio(pathsession);
			Archivo.crearDirectorio(pathsession + Constante.SLASH + idunidad);
			Archivo.copiarArchivo(origen, pathsession + Constante.SLASH
					+ idunidad + Constante.SLASH + Constante.LAB + indice
					+ Constante.EXTENSION_PDF);
		} catch (IOException e) {
			log
					.info("GestionarAulaVirtualAction: copiarLaboratorio: IOException: "
							+ e);
		}
		log.info("GestionarAulaVirtualAction: copiarLaboratorio: origen: "
				+ origen);
		return null;
	}

	public String guardarConfiguracion() {
		log.info("guardarConfiguracion()");
		PrintWriter out;
		try {
			out = getResponse().getWriter();
			try {
				if (tipo != null) {
					if ("1".equals(tipo)) {
						if (null != fecha1 && !"0".equals(fecha1)
								&& null != fecha2 && !"0".equals(fecha2)
								&& null != diasEdiccion
								&& !"0".equals(diasEdiccion)
								&& null != diasRevision
								&& !"0".equals(diasRevision) && null != sede) {
							aulaVirtualService.modificarFechas(sede, fecha1,
									fecha2, Integer.parseInt(diasEdiccion),
									Integer.parseInt(diasRevision),
									getAulaVirtualSession().getIdFicha(),
									getUsuarioSession().getIdUsuario());
							AulaVirtual aula = getAulaVirtualSession();
							aula.setFechaInicio(Formato.setDateDeJSP(fecha1));
							aula.setFechaFin(Formato.setDateDeJSP(fecha2));
							aula.setDiasEdicion(Integer.parseInt(diasEdiccion));
							aula
									.setDiasRevision(Integer
											.parseInt(diasEdiccion));
							aula.setSede(sede);
							aula.setIdPeriodo(0);
							getSession().put(Constante.AULA_ACTUAL,
									aula);
							out.print("1");
						} else {
							out.print("0");
						}
					} else {
						if (null != idPeriodo && !"0".equals(idPeriodo)) {
							aulaVirtualService.modificarPeriodo(idPeriodo,
									getAulaVirtualSession().getIdFicha(),
									getUsuarioSession().getIdUsuario());
							AulaVirtual aula = getAulaVirtualSession();
							Periodo periodo = periodoService.obtener(idPeriodo);
							aula.setSede(periodo.getSede());
							aula.setFechaInicio(periodo.getFechaInicio());
							aula.setFechaFin(periodo.getFechaFin());
							aula.setDiasEdicion(periodo.getDiasEdicion());
							aula.setDiasRevision(periodo.getDiasRevision());
							getSession().put(Constante.AULA_ACTUAL,
									aula);
							out.print("1");
						} else {
							out.print("0");
						}
					}
				}
			} catch (NumberFormatException e) {
				out.print("0");
			} catch (ServiceException e) {
				out.print("0");
			}
			out.close();
		} catch (IOException e) {
		}
		return NONE;
	}

	public String fichaRecursoCambiarEstado() {
		String idFicha = String.valueOf(((AulaVirtual) getSession()
				.get(Constante.AULA_ACTUAL)).getIdFicha());

		FichaUnidadRecurso vo = new FichaUnidadRecurso();
		vo.setIdFicha(idFicha);
		vo.setIdRecurso(idRecurso);
		vo.setUsuarioModificacion(((Usuario) (getSession()
				.get(Constante.USUARIO_ACTUAL))).getIdUsuario());

		try {
			fichaConfiguracionService.actualizarFichaRecurso(vo, tipo, Integer
					.parseInt(estado));
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}

		return null;
	}

	private String sede;

	private String fecha1;

	private String fecha2;

	private String diasEdiccion;

	private String diasRevision;

	private String idPeriodo;

	private String pl;

	private String pd;

	private String pt;

	private String pg;

	private String pe;

	private String chatExiste;

	private String chatDocente;

	private String chatEstudiante;

	private String mensajesExiste;

	private String mensajesDocente;

	private String mensajesEstudiante;

	private String recursosExiste;

	private String recursosDocente;

	private String recursosEstudiante;

	private String idUnidad;

	private String estado;

	private String idRecurso;

	private String tipoUsuario;

	private static final long serialVersionUID = 1L;

	private FichaService fichaService;

	private FichaConfiguracionService fichaConfiguracionService;

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getIdRecurso() {
		return idRecurso;
	}

	public void setIdRecurso(String idRecurso) {
		this.idRecurso = idRecurso;
	}

	public String getChatDocente() {
		return chatDocente;
	}

	public void setChatDocente(String chatDocente) {
		this.chatDocente = chatDocente;
	}

	public String getChatEstudiante() {
		return chatEstudiante;
	}

	public void setChatEstudiante(String chatEstudiante) {
		this.chatEstudiante = chatEstudiante;
	}

	public String getChatExiste() {
		return chatExiste;
	}

	public void setChatExiste(String chatExiste) {
		this.chatExiste = chatExiste;
	}

	public String getMensajesDocente() {
		return mensajesDocente;
	}

	public void setMensajesDocente(String mensajesDocente) {
		this.mensajesDocente = mensajesDocente;
	}

	public String getMensajesEstudiante() {
		return mensajesEstudiante;
	}

	public void setMensajesEstudiante(String mensajesEstudiante) {
		this.mensajesEstudiante = mensajesEstudiante;
	}

	public String getMensajesExiste() {
		return mensajesExiste;
	}

	public void setMensajesExiste(String mensajesExiste) {
		this.mensajesExiste = mensajesExiste;
	}

	public String getRecursosDocente() {
		return recursosDocente;
	}

	public void setRecursosDocente(String recursosDocente) {
		this.recursosDocente = recursosDocente;
	}

	public String getRecursosEstudiante() {
		return recursosEstudiante;
	}

	public void setRecursosEstudiante(String recursosEstudiante) {
		this.recursosEstudiante = recursosEstudiante;
	}

	public String getRecursosExiste() {
		return recursosExiste;
	}

	public void setRecursosExiste(String recursosExiste) {
		this.recursosExiste = recursosExiste;
	}

	public FichaConfiguracionService getFichaConfiguracionService() {
		return fichaConfiguracionService;
	}

	public void setFichaConfiguracionService(
			FichaConfiguracionService fichaConfiguracionService) {
		this.fichaConfiguracionService = fichaConfiguracionService;
	}

	public String getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(String idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}

	public String getPe() {
		return pe;
	}

	public void setPe(String pe) {
		this.pe = pe;
	}

	public String getPg() {
		return pg;
	}

	public void setPg(String pg) {
		this.pg = pg;
	}

	public String getPl() {
		return pl;
	}

	public void setPl(String pl) {
		this.pl = pl;
	}

	public String getPt() {
		return pt;
	}

	public void setPt(String pt) {
		this.pt = pt;
	}

	public FichaService getFichaService() {
		return fichaService;
	}

	public void setFichaService(FichaService fichaService) {
		this.fichaService = fichaService;
	}

	public AulaVirtualService getAulaVirtualService() {
		return aulaVirtualService;
	}

	public void setAulaVirtualService(AulaVirtualService aulaVirtualService) {
		this.aulaVirtualService = aulaVirtualService;
	}

	public String getDiasEdiccion() {
		return diasEdiccion;
	}

	public void setDiasEdiccion(String diasEdiccion) {
		this.diasEdiccion = diasEdiccion;
	}

	public String getDiasRevision() {
		return diasRevision;
	}

	public void setDiasRevision(String diasRevision) {
		this.diasRevision = diasRevision;
	}

	public String getFecha1() {
		return fecha1;
	}

	public void setFecha1(String fecha1) {
		this.fecha1 = fecha1;
	}

	public String getFecha2() {
		return fecha2;
	}

	public void setFecha2(String fecha2) {
		this.fecha2 = fecha2;
	}

	public String getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(String idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}
}
