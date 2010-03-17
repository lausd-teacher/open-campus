package edu.tecsup.lms.action.ficha;

import java.io.File;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.excepcion.ActionException;
import edu.tecsup.lms.modelo.AulaVirtual;
import edu.tecsup.lms.modelo.Curso;
import edu.tecsup.lms.modelo.Jerarquia;
import edu.tecsup.lms.modelo.Periodo;
import edu.tecsup.lms.modelo.Silabo;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.service.FichaService;
import edu.tecsup.lms.service.JerarquiaService;
import edu.tecsup.lms.service.PeriodoService;
import edu.tecsup.lms.util.Constante;
import edu.tecsup.lms.util.Formato;

public class FichaAction extends BaseAction {

	private static final long serialVersionUID = -2806447233308937307L;

	public File doc;
	public String docContentType;
	public String docFileName;
	
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
	
	private String nombre;
	
	private Integer idFicha;
	
	private Integer idSilabo;
	
	private Integer idCurso;
	
	private Integer idPeriodo;
	
	private Integer idJerarquia;
	
	private String fechaInicio;
	
	private String fechaFin;
	
	private Collection<Periodo> periodos;
	
	private Collection<Jerarquia> jerarquias;
	
	private Collection<AulaVirtual> cursos;
	
	private Integer etiqueta;
	
	private FichaService fichaService;
	
	private PeriodoService periodoService;
	
	private JerarquiaService jerarquiaService; 
	
	public Integer getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(Integer etiqueta) {
		this.etiqueta = etiqueta;
	}

	public Integer getIdJerarquia() {
		return idJerarquia;
	}

	public void setIdJerarquia(Integer idJerarquia) {
		this.idJerarquia = idJerarquia;
	}

	public Collection<AulaVirtual> getCursos() {
		return cursos;
	}

	public void setCursos(Collection<AulaVirtual> cursos) {
		this.cursos = cursos;
	}

	public Integer getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(Integer idFicha) {
		this.idFicha = idFicha;
	}

	public Integer getIdSilabo() {
		return idSilabo;
	}

	public void setIdSilabo(Integer idSilabo) {
		this.idSilabo = idSilabo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}

	public Integer getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Collection<Periodo> getPeriodos() {
		return periodos;
	}

	public Collection<Jerarquia> getJerarquias() {
		return jerarquias;
	}

	public void setFichaService(FichaService fichaService) {
		this.fichaService = fichaService;
	}

	public void setPeriodoService(PeriodoService periodoService) {
		this.periodoService = periodoService;
	}

	public void setJerarquiaService(JerarquiaService jerarquiaService) {
		this.jerarquiaService = jerarquiaService;
	}

	public String cargarPortada() throws ActionException{
		log.info("cargarPortada()");
		try {
			cursos = fichaService.cargarPortada(getUsuarioSession().getId());
		} catch (Exception e) {
			log.error(e.toString());
			return NONE;
		}
		return SUCCESS;
	}
	
	public String cargar() throws ActionException{
		log.info("cargar()");
		try {
			periodos = periodoService.listarPeriodos();
			jerarquias = jerarquiaService.listarJerarquias();
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}
	
	public String nuevo() throws ActionException{
		log.info("nuevo()");
		try {
			periodos = periodoService.listarVigentes(false);
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}
	
	
	
	public String buscar() throws ActionException{
		log.info("buscar()");
		try {
			periodos = periodoService.listarPeriodos();
			jerarquias = jerarquiaService.listarJerarquias();
			
//			if (null != pagina) {
				
				if(null != nombre || idPeriodo != null || idJerarquia != null || fechaInicio != null || fechaFin != null || etiqueta != null){
					
					AulaVirtual filtro = new AulaVirtual();
					filtro.setPrioridad((etiqueta==null)?0:etiqueta);
					
					Curso curso = new Curso();
					if(nombre != null && nombre.trim().length()>0)
						curso.setNombre(nombre);
					
					Jerarquia jerarquia = new Jerarquia();
					if(idJerarquia != null && idJerarquia != 0)
						jerarquia.setIdJerarquia(idJerarquia);
					curso.setJerarquia(jerarquia);
					
					filtro.setCurso(curso);
					
					Periodo periodo = new Periodo();
					if(idPeriodo != null && idPeriodo != 0)
						periodo.setIdPeriodo(idPeriodo);
					
					if(fechaInicio != null && fechaInicio.trim().length()>0)
						periodo.setFechaInicio(Formato.stringToCalendar(fechaInicio, Constante.FECHA_DIA_MES_ANO));
					
					if(fechaFin != null && fechaFin.trim().length()>0)
						periodo.setFechaFin(Formato.stringToCalendar(fechaFin, Constante.FECHA_DIA_MES_ANO));
					
					filtro.setPeriodo(periodo);
					
//					getUsuarioSession().setMisUsuarios(usuarioService.buscar(filtro)); 
					
					
					this.cursos = fichaService.buscarFichas(filtro);
				}
				
				List<Usuario> usuarios = getUsuarioSession().getMisUsuarios();
				
//				if(usuarios != null){
//					total = usuarios.size();
//					paginas = total / Constante.BUSQUEDA_CANTIDAD_DIRECTORIO;
//					
//					int inicio = pagina * Constante.BUSQUEDA_CANTIDAD_DIRECTORIO;
//					int fin = inicio + Constante.BUSQUEDA_CANTIDAD_DIRECTORIO;
//					this.usuarios = usuarios.subList(inicio, (fin>usuarios.size())?usuarios.size():fin);
//				}
//			}
			
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}

	
	public String crear() throws ActionException{
		log.info("crear()");
		try {
			
			GregorianCalendar fInicio = Formato.stringToCalendar(fechaInicio, Constante.FECHA_DIA_MES_ANO);
			GregorianCalendar fFin = Formato.stringToCalendar(fechaFin, Constante.FECHA_DIA_MES_ANO);
			if(fFin != null) fFin.add(GregorianCalendar.SECOND, 86399); //hasta las 23:59:59
			
			if(idSilabo != null && idCurso != null && idPeriodo != null 
					&& idCurso != 0 && idSilabo != 0 
					&& (idPeriodo != 0 || fInicio != null && fFin != null)){
				
				AulaVirtual aula = new AulaVirtual();
				aula.setSilabo(new Silabo(idSilabo));
				aula.setCurso(new Curso(idCurso));
				Periodo periodo = new Periodo();
				if(idPeriodo != 0){
					periodo.setIdPeriodo(idPeriodo);
				}else{
					periodo.setFechaInicio(fInicio);
					periodo.setFechaFin(fFin);
					periodo.setDiasEdicion(GregorianCalendar.DAY_OF_WEEK);
					periodo.setDiasRevision(GregorianCalendar.DAY_OF_WEEK);
					periodo.setNombre("CUSTOM_"+periodo.getFechaInicioToString());
					periodo.setPersonalizado(Constante.ESTADO_ACTIVO);
				
					periodo.setIdPeriodo(periodoService.crear(periodo));
				}
				aula.setPeriodo(periodo);
				aula.setUsuarioCreacion(String.valueOf(getUsuarioSession().getId()));
				
				idFicha = fichaService.crear(aula);
								
			}else{
				nuevo();
				addActionError("Algunos de los parametros del formulario son incorrectos.");
				log.error("Parametros incorrectos.");
				return INPUT;
			}
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}
	
	public String verCursos() throws ActionException{
		log.info("verCursos() usuario:"+getUsuarioSession());
		try {
			cursos = fichaService.verCursos(getUsuarioSession().getId());
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}
	
	
/*
	public String papelera() {
		log.info("papelera()");
		if (null != select_cursos) {
			int cnt = 0;
			try {
				cnt = fichaService.papelera(getUsuarioSession(), select_cursos);

			} catch (ServiceException e) {
			}
			if (cnt == select_cursos.length) {
				mensaje_texto = "Eureka!! se envió a la papelera " + cnt
						+ " cursos satisfactoriamente.";
				mensaje_tipo = "0";
			} else {
				if (cnt == 0) {
					mensaje_texto = "Wahhhh! la petición no logró enviar a la papelera "
							+ select_cursos.length + " curso(s).";
					mensaje_tipo = "1";
				} else {
					mensaje_texto = "Upsss! se envió solamente " + cnt
							+ " cursos a la papelera. Error en "
							+ (select_cursos.length - cnt) + " curso(s).";
					mensaje_tipo = "1";
				}
			}
		}
		getRequest().setAttribute("mensaje_texto", mensaje_texto);
		getRequest().setAttribute("mensaje_tipo", mensaje_tipo);
		return SUCCESS;
	}

	public String constancia() {
		log.info("constancia()");
		if (null != select_cursos) {
			int[] numeros = null;
			try {
				numeros = fichaService.constancia(getUsuarioSession(),
						select_cursos);
				if (numeros[0] != 0 || numeros[1] != 0) {
					mensaje_texto = "Se ha enviado a " + numeros[1]
							+ " usuario(s) correo(s)";
					switch (numeros[0]) {
					case 0:
						mensaje_texto += ".";
						break;
					case 1:
						mensaje_texto += " y se ha generado una nueva clave.";
						break;
					default:
						mensaje_texto += " y se han generado " + numeros[0]
								+ " nuevas claves.";
						break;
					}
					mensaje_tipo = "0";
				} else {
					mensaje_texto = "Upsss! no ha existido cambio alguno desde la última acción.";
					mensaje_tipo = "0";
				}
			} catch (ServiceException e) {
				mensaje_texto = "Wahhhh! la petición no logró concluirse, intentelo en breves momentos.";
				mensaje_tipo = "1";
			}
		}
		getRequest().setAttribute("mensaje_texto", mensaje_texto);
		getRequest().setAttribute("mensaje_tipo", mensaje_tipo);
		
		if(select_cursos != null && "100".equals(select_cursos[0])){
			return "buscar";
		}
		
		return SUCCESS;
	}

	public String restaurar() {
		log.info("restaurar()");
		if (null != select_cursos) {
			int cnt = 0;
			try {
				cnt = fichaService.resturar(getUsuarioSession(), select_cursos);
			} catch (ServiceException e) {
			}
			if (cnt == select_cursos.length) {
				mensaje_texto = "Eureka!! se envió a virtual " + cnt
						+ " cursos satisfactoriamente.";
				mensaje_tipo = "0";
			} else {
				if (cnt == 0) {
					mensaje_texto = "Wahhhh! la petición no logró enviar a virtual "
							+ select_cursos.length + " curso(s).";
					mensaje_tipo = "1";
				} else {
					mensaje_texto = "Upsss! se envió solamente " + cnt
							+ " cursos a virtual. Error en "
							+ (select_cursos.length - cnt) + " curso(s).";
					mensaje_tipo = "1";
				}
			}
		}
		getRequest().setAttribute("mensaje_texto", mensaje_texto);
		getRequest().setAttribute("mensaje_tipo", mensaje_tipo);
		return SUCCESS;
	}

	public String sincronizar() {
		log.info("sincronizar()");
		if (null != select_cursos) {
			int[] numeros = null;
			try {
				numeros = fichaService.sincronizar(getUsuarioSession(),
						select_cursos);
			} catch (ServiceException e) {
			}
			if (null != numeros) {
				if (numeros[0] == 0 && numeros[1] == 0) {
					mensaje_texto = "Upsss! no ha existido cambio alguno desde la última sincronización.";
					mensaje_tipo = "0";
				} else {
					mensaje_texto = "Eureka!! ";
					if (numeros[1] != 0) {
						mensaje_texto += "se han actualizado " + numeros[1]
								+ " cursos satisfactoriamente";
					}
					if (numeros[0] != 0) {
						if (numeros[1] != 0) {
							mensaje_texto += ", ";
						}
						mensaje_texto += "se han matriculado " + numeros[0]
								+ " usuarios.";
						;
					}
					mensaje_tipo = "0";
				}
			} else {
				mensaje_texto = "Wahhhh! la petición no logró concluirse, intentelo en breves momentos.";
				mensaje_tipo = "1";
			}
		}
		getRequest().setAttribute("mensaje_texto", mensaje_texto);
		getRequest().setAttribute("mensaje_tipo", mensaje_tipo);
		
		if(select_cursos != null && "100".equals(select_cursos[0])){
			return "buscar";
		}
		
		return SUCCESS;
	}

	

	public String obtenerFichaHistorica() {
		log.info("obtenerFichaHistorica()");
		Collection<FichaHistorica> cursos = new ArrayList<FichaHistorica>();
		try {
			cursos = fichaService
					.obtenerFichasPorUsuarioHistorica(getUsuarioSession());
		} catch (ServiceException e) {
		}
		getRequest().setAttribute("cursos", cursos);
		return SUCCESS;
	}

	public String importante() {
		log.info("importante()");
		PrintWriter out;
		try {
			out = getResponse().getWriter();
			try {
				fichaService.setImportanteFicha(getUsuarioSession(), Integer
						.parseInt(idFicha), Integer.parseInt(estado));
				out.print("1");
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

	public String estado() {
		log.info("estado()");
		PrintWriter out;
		try {
			out = getResponse().getWriter();
			try {
				fichaService.setEstadoFicha(getUsuarioSession(), Integer
						.parseInt(idFicha), Integer.parseInt(estado));
				out.print("1");
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

	public String buscarFichasCurso() {
		log.info("buscarFichasCurso()");
		Collection<Curso> col = new ArrayList<Curso>();
		try {
			getResponse().setContentType("text/html; charset=UTF-8");
			PrintWriter pw = getResponse().getWriter();
			if (null != nombre && 0 < nombre.trim().length()) {
				col = getFichaService().obtenerFichaCurso(nombre);
				if (col.isEmpty()) {
					pw.print("0&vacio");
				} else {
					if (col.size() >= Constante.BUSQUEDA_CANTIDAD_DIRECTORIO - 1) {
						pw.print("1&");
					} else {
						pw.print("0&");
					}
					int n = 0;
					for (Curso curso : col) {
						pw.print("<div id=\"" + curso.getIdCurso()
								+ "\" onClick=\"clickLista(this);\" "
								+ "onMouseOver=\"mouseDentro(this,'" + (n++)
								+ "');\">" + curso.getNombre() + " ("
								+ curso.getIdCurso() + ")</div>");
					}
				}
				if (col.size() >= Constante.BUSQUEDA_CANTIDAD_DIRECTORIO - 1)
					pw.print("<div class=\"ultimo_lista\">Existe muchas coincidencias, sea más espec&iacute;fico.</div>");
				pw.flush();
			}
		} catch (ServiceException e) {
		} catch (IOException e) {
		}
		return NONE;
	}
	
	public String campana() throws Exception{
		log.info("campana()");
		getRequest().setAttribute("PERIODO_PREDETERMINADA", periodoService.obtenerVigentes());
		return SUCCESS;
	}
	
	public String publicarMensaje() throws Exception{
		log.info("publicarMensaje()"+ busquedaPeriodo);
		if(titulo != null && contenido != null && (titulo.trim().length()>0)&&(contenido.trim().length()>0)){
			Collection<Integer> fichas = periodoService.obtenerFichasPorPeriodo(Integer.parseInt(busquedaPeriodo));
			
			for (Integer f : fichas) {
				Publicacion pub = new Publicacion();
				pub.setIdHerramienta("1");
				pub.setIdFicha(f);
				pub.setTitulo(titulo);
				pub.setContenido(contenido);		
				pub.setIdUsuario(getUsuarioSession().getId());
				publicacionService.nuevaPublicacion(pub,"0");
				log.info("Publicado Ficha: "+f);
			}
			mensaje_texto="Mensaje Publicado Satisfactoriamente.";
		}else{
			mensaje_texto="Debe tener titulo y contenido";
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String publicarLectura() throws Exception{	
		log.info("publicarLectura() P: "+busquedaPeriodo +" - T: "+titulo+" - F: "+docFileName);
		int idPeriodo = Integer.parseInt( busquedaPeriodo);
		if(titulo != null && (titulo.trim().length()>0)&&doc != null && doc.exists()){
			Collection<Integer> fichas = periodoService.obtenerFichasPorPeriodo(Integer.parseInt(busquedaPeriodo));
			for (Integer idFicha : fichas) {
				Publicacion pub = new Publicacion();
				pub.setIdHerramienta("2");
				pub.setArchivoNombre(docFileName);
				pub.setArchivoTamanio(doc.length());
				pub.setIdUsuario(getUsuarioSession().getIdUsuario());
				pub.setIdFicha(idFicha);
				pub.setTitulo(titulo);
				String idPublicacion = publicacionService.nuevaPublicacion(pub,"0");			
				log.info("Publicado Ficha: "+idFicha);
				try {
					String extension = docFileName.substring(docFileName.lastIndexOf("."));
					String name = doc.getAbsolutePath().replaceAll("/", Constante.SLASH);
					Archivo.crearDirectorio(Constante.HOME_RECURSOS);
					Archivo.crearDirectorio(Constante.HOME_RECURSOS + Constante.SLASH + idPeriodo);
					Archivo.crearDirectorio(Constante.HOME_RECURSOS + Constante.SLASH + idPeriodo + Constante.SLASH + idFicha);
					Archivo.copiarArchivo(name,Constante.HOME_RECURSOS +Constante.SLASH + idPeriodo + Constante.SLASH + 
														idFicha + Constante.SLASH+idPublicacion+extension);
				} catch (IOException e) {			
					log.error(e);
				}
				log.info("Publicado Documento Ficha: "+idFicha +" - IdPublicacion:"+idPublicacion);
			}
			mensaje_texto="Lectura Publicado Satisfactoriamente.";
		}
		return SUCCESS;
	}*/

}
