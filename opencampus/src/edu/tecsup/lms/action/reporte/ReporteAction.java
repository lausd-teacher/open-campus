package edu.tecsup.lms.action.reporte;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.servlet.ServletOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.AulaVirtual;
import edu.tecsup.lms.modelo.Especialidad;
import edu.tecsup.lms.modelo.Periodo;
import edu.tecsup.lms.modelo.Sede;
import edu.tecsup.lms.modelo.aulavirtual.reporte.ReporteDetalle;
import edu.tecsup.lms.modelo.reporte.ReporteCollection;
import edu.tecsup.lms.modelo.reporte.ReporteFiltro;
import edu.tecsup.lms.modelo.reporte.TecsupVirtualReporte;
import edu.tecsup.lms.modelo.reportesecdoc.ReporteSecDocFiltro;
import edu.tecsup.lms.modelo.reportesecdoc.UsuarioReporteSecDoc;
import edu.tecsup.lms.service.AulaVirtualService;
import edu.tecsup.lms.service.EspecialidadService;
import edu.tecsup.lms.service.PeriodoService;
import edu.tecsup.lms.service.ReporteService;
import edu.tecsup.lms.service.UsuarioService;
import edu.tecsup.lms.util.Archivo;
import edu.tecsup.lms.util.Constante;
import edu.tecsup.lms.util.Formato;
import edu.tecsup.lms.util.PDFReport;

public class ReporteAction extends BaseAction {

	private static final long serialVersionUID = -918545608538337055L;

	private PeriodoService periodoService;

	private AulaVirtualService aulaVirtualService;

	private EspecialidadService especialidadService;
	
	private UsuarioService usuarioService;

	private ReporteService reporteService;

	private String busquedaUsuario;

	private String busquedaUsuarioNombre;

	private String busquedaGrupo;

	private String busquedaCursoNombre;

	private String busquedaEmpresa;

	private String busquedaContacto;

	private String busquedaCurso;

	private String busquedaExacta;

	private String busquedaFormacion;

	private String busquedaSede;

	private String busquedaPeriodo;

	private String busquedaFecha1;

	private String busquedaFecha2;

	private String busquedaCiclo;

	private String posicionPagina;

	private String totalRegistro;

	private String cantidadRegistro;

	private String busquedaIngreso;

	private String busquedaDepartamento;

	private String busquedaFamilia;

	private String matriculas;
	
	private Integer idMatricula;

	private String[] matriculas_array;

	private String[] rol_array;

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public Integer getIdMatricula() {
		return idMatricula;
	}

	public void setIdMatricula(Integer idMatricula) {
		this.idMatricula = idMatricula;
	}

	public String getBusquedaIngreso() {
		return busquedaIngreso;
	}

	public void setBusquedaIngreso(String busquedaIngreso) {
		this.busquedaIngreso = busquedaIngreso;
	}

	public String[] getRol_array() {
		return rol_array;
	}

	public void setRol_array(String[] rol_array) {
		this.rol_array = rol_array;
	}

	public AulaVirtualService getAulaVirtualService() {
		return aulaVirtualService;
	}

	public void setAulaVirtualService(AulaVirtualService aulaVirtualService) {
		this.aulaVirtualService = aulaVirtualService;
	}

	public String[] getMatriculas_array() {
		return matriculas_array;
	}

	public void setMatriculas_array(String[] matriculas_array) {
		this.matriculas_array = matriculas_array;
	}

	public ReporteService getReporteService() {
		return reporteService;
	}

	public void setReporteService(ReporteService reporteService) {
		this.reporteService = reporteService;
	}

	public EspecialidadService getEspecialidadService() {
		return especialidadService;
	}

	public void setEspecialidadService(EspecialidadService especialidadService) {
		this.especialidadService = especialidadService;
	}

	public PeriodoService getPeriodoService() {
		return periodoService;
	}

	public String getBusquedaFamilia() {
		return busquedaFamilia;
	}

	public String getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(String matriculas) {
		this.matriculas = matriculas;
	}

	public void setBusquedaFamilia(String busquedaFamilia) {
		this.busquedaFamilia = busquedaFamilia;
	}

	public void setPeriodoService(PeriodoService periodoService) {
		this.periodoService = periodoService;
	}

	public String getBusquedaCiclo() {
		return busquedaCiclo;
	}

	public void setBusquedaCiclo(String busquedaCiclo) {
		this.busquedaCiclo = busquedaCiclo;
	}

	public String getBusquedaContacto() {
		return busquedaContacto;
	}

	public void setBusquedaContacto(String busquedaContacto) {
		this.busquedaContacto = busquedaContacto;
	}

	public String getBusquedaCurso() {
		return busquedaCurso;
	}

	public void setBusquedaCurso(String busquedaCurso) {
		this.busquedaCurso = busquedaCurso;
	}

	public String getBusquedaDepartamento() {
		return busquedaDepartamento;
	}

	public void setBusquedaDepartamento(String busquedaDepartamento) {
		this.busquedaDepartamento = busquedaDepartamento;
	}

	public String getBusquedaEmpresa() {
		return busquedaEmpresa;
	}

	public void setBusquedaEmpresa(String busquedaEmpresa) {
		this.busquedaEmpresa = busquedaEmpresa;
	}

	public String getBusquedaExacta() {
		return busquedaExacta;
	}

	public void setBusquedaExacta(String busquedaExacta) {
		this.busquedaExacta = busquedaExacta;
	}

	public String getBusquedaFecha1() {
		return busquedaFecha1;
	}

	public void setBusquedaFecha1(String busquedaFecha1) {
		this.busquedaFecha1 = busquedaFecha1;
	}

	public String getBusquedaFecha2() {
		return busquedaFecha2;
	}

	public void setBusquedaFecha2(String busquedaFecha2) {
		this.busquedaFecha2 = busquedaFecha2;
	}

	public String getBusquedaFormacion() {
		return busquedaFormacion;
	}

	public void setBusquedaFormacion(String busquedaFormacion) {
		this.busquedaFormacion = busquedaFormacion;
	}

	public String getBusquedaPeriodo() {
		return busquedaPeriodo;
	}

	public void setBusquedaPeriodo(String busquedaPeriodo) {
		this.busquedaPeriodo = busquedaPeriodo;
	}

	public String getBusquedaSede() {
		return busquedaSede;
	}

	public void setBusquedaSede(String busquedaSede) {
		this.busquedaSede = busquedaSede;
	}

	public String getBusquedaUsuario() {
		return busquedaUsuario;
	}

	public void setBusquedaUsuario(String busquedaUsuario) {
		this.busquedaUsuario = busquedaUsuario;
	}

	public String getCantidadRegistro() {
		return cantidadRegistro;
	}

	public void setCantidadRegistro(String cantidadRegistro) {
		this.cantidadRegistro = cantidadRegistro;
	}

	public String getPosicionPagina() {
		return posicionPagina;
	}

	public void setPosicionPagina(String posicionPagina) {
		this.posicionPagina = posicionPagina;
	}

	public String getTotalRegistro() {
		return totalRegistro;
	}

	public void setTotalRegistro(String totalRegistro) {
		this.totalRegistro = totalRegistro;
	}

	public String getBusquedaUsuarioNombre() {
		return busquedaUsuarioNombre;
	}

	public void setBusquedaUsuarioNombre(String busquedaUsuarioNombre) {
		this.busquedaUsuarioNombre = busquedaUsuarioNombre;
	}

	public String getBusquedaGrupo() {
		return busquedaGrupo;
	}

	public void setBusquedaGrupo(String busquedaGrupo) {
		this.busquedaGrupo = busquedaGrupo;
	}

	public String getBusquedaCursoNombre() {
		return busquedaCursoNombre;
	}

	public void setBusquedaCursoNombre(String busquedaCursoNombre) {
		this.busquedaCursoNombre = busquedaCursoNombre;
	}

	public String reporte() {
		log.info("reporte()");
		if (null == busquedaUsuarioNombre) {
			busquedaUsuarioNombre = "";
		}
		if (null == busquedaCursoNombre) {
			busquedaCursoNombre = "";
		}
		if (null == busquedaGrupo) {
			busquedaGrupo = "0";
		}
		if (null == busquedaIngreso) {
			busquedaIngreso = "-1";
		}
		if (null == busquedaCiclo) {
			busquedaCiclo = "0";
		}
		if (null == busquedaFecha1) {
			busquedaFecha1 = "";
		}
		if (null == busquedaFecha2) {
			busquedaFecha2 = "";
		}
		if (null == busquedaFormacion) {
			busquedaFormacion = "0";
		}
		if (null == busquedaUsuario) {
			busquedaUsuario = "";
		}
		if (null == busquedaEmpresa) {
			busquedaEmpresa = "";
		}
		if (null == busquedaContacto) {
			busquedaContacto = "";
		}
		if (null == busquedaCurso) {
			busquedaCurso = "";
		}
		if (null == posicionPagina) {
			posicionPagina = "0";
		}
		if (null == busquedaPeriodo) {
			busquedaPeriodo = "0";
		}
		if (null == busquedaSede) {
			busquedaSede = "0";
		}
		if (null == cantidadRegistro) {
			cantidadRegistro = "25";
		}
		if (null == busquedaDepartamento) {
			busquedaDepartamento = "0";
		}
		if (null == busquedaExacta) {
			busquedaExacta = "0";
		}
		if (null == totalRegistro) {
			totalRegistro = "0";
		}
		if (null == posicionPagina) {
			posicionPagina = "0";
		}
		Collection<Especialidad> formacion = new ArrayList<Especialidad>();
		Collection<Periodo> periodos = new ArrayList<Periodo>();
		ReporteCollection usuarios = new ReporteCollection();
		ReporteFiltro filtro = new ReporteFiltro();
		filtro.setBusquedaCiclo(busquedaCiclo);
		filtro.setBusquedaContacto(busquedaContacto);
		filtro.setBusquedaCurso(busquedaCurso);
		filtro.setBusquedaDepartamento(busquedaDepartamento);
		filtro.setBusquedaExacta(busquedaExacta);
		filtro.setBusquedaFecha1(busquedaFecha1);
		filtro.setBusquedaIngreso(busquedaIngreso);
		filtro.setBusquedaFecha2(busquedaFecha2);
		filtro.setBusquedaFormacion(busquedaFormacion);
		filtro.setBusquedaPeriodo(busquedaPeriodo);
		filtro.setBusquedaSede(busquedaSede);
		filtro.setBusquedaUsuario(busquedaUsuario);
		filtro.setCantidadRegistro(cantidadRegistro);
		filtro.setPosicionPagina(posicionPagina);
		filtro.setBusquedaUsuarioNombre(busquedaUsuarioNombre);
		filtro.setBusquedaGrupo(busquedaGrupo);
		filtro.setRegistroPrimero(String.valueOf(Integer.parseInt(posicionPagina) * Integer.parseInt(cantidadRegistro)));
		filtro.setRegistroUltimo(String.valueOf((Integer.parseInt(posicionPagina) + 1) * Integer.parseInt(cantidadRegistro)));
		try {
			periodos = periodoService.obtenerTodo();
		} catch (ServiceException e) {
			log.error(e);
			return ERROR;
		}
		try {
			formacion = especialidadService.obtenerTodos();
		} catch (ServiceException e) {
			log.error(e);
			return ERROR;
		}
		try {
			if(getUsuarioSession().getRolPredeterminado() == Constante.ROL_CAMPUS_MONITOR_EMPRESA){
				busquedaEmpresa = reporteService.obtenerRazSocial(getUsuarioSession().getIdUsuario());
				if(busquedaEmpresa == null){
					log.error("El Usuario no tiene registro en la tabla Institucion: "+getUsuarioSession());
					return ERROR;
				}
			}
			filtro.setBusquedaEmpresa(busquedaEmpresa);
			usuarios = reporteService.buscar(filtro);
			
		} catch (ServiceException e) {
			log.error(e);
			return ERROR;
		}
		getRequest().setAttribute("USUARIOS_BUSQUEDA", usuarios.getUsuarios());
		getRequest().setAttribute("FORMACION_PREDETERMINADA", formacion);
		getRequest().setAttribute("PERIODO_PREDETERMINADA", periodos);
		getRequest().setAttribute("busquedaCiclo", busquedaCiclo);
		getRequest().setAttribute("busquedaContacto", busquedaContacto);
		getRequest().setAttribute("busquedaCurso", busquedaCurso);
		getRequest().setAttribute("busquedaDepartamento", busquedaDepartamento);
		getRequest().setAttribute("busquedaEmpresa", busquedaEmpresa);
		getRequest().setAttribute("busquedaExacta", busquedaExacta);
		getRequest().setAttribute("busquedaFecha1", busquedaFecha1);
		getRequest().setAttribute("busquedaFecha2", busquedaFecha2);
		getRequest().setAttribute("busquedaFormacion", busquedaFormacion);
		getRequest().setAttribute("busquedaPeriodo", busquedaPeriodo);
		getRequest().setAttribute("busquedaSede", busquedaSede);
		getRequest().setAttribute("busquedaUsuario", busquedaUsuario);
		getRequest().setAttribute("cantidadRegistro", cantidadRegistro);
		getRequest().setAttribute("posicionPagina", posicionPagina);
		getRequest().setAttribute("busquedaUsuarioNombre", busquedaUsuarioNombre);
		getRequest().setAttribute("busquedaCursoNombre", busquedaCursoNombre);
		getRequest().setAttribute("busquedaGrupo", busquedaGrupo);
		getRequest().setAttribute("totalRegistro", usuarios.getCantidad());
		return SUCCESS;
	}
	
	public String reportesecdoc() {
		log.info("reportesecdoc()");
		boolean noData = false;
		if (null == busquedaFamilia) {
			noData = true;
			busquedaFamilia = "0";
		}
		if (null == busquedaFecha1) {
			noData = true;
			busquedaFecha1 = "";
		}
		if (null == busquedaFecha2) {
			noData = true;
			busquedaFecha2 = "";
		}
		if (null == busquedaSede) {
			noData = true;
			busquedaSede = "0";
		}
		if (null == busquedaPeriodo) {
			noData = true;
			busquedaPeriodo = "0";
		}
		ReporteSecDocFiltro filtro = new ReporteSecDocFiltro();
		Collection<Periodo> periodos = new ArrayList<Periodo>();
		filtro.setCodigoFamilia(busquedaFamilia);
		filtro.setFechaFin1(busquedaFecha1);
		filtro.setFechaFin2(busquedaFecha2);
		filtro.setBusquedaPeriodo(busquedaPeriodo);
		filtro.setBusquedaSede(busquedaSede);
		Collection<UsuarioReporteSecDoc> usuarios = new ArrayList<UsuarioReporteSecDoc>();
		try {
			if (!noData) {
				usuarios = reporteService.busquedaSecDoc(filtro);
			}
		} catch (ServiceException e) {
		}
		try {
			if(getUsuarioSession().getRolPredeterminado() == Constante.ROL_CAMPUS_DIRECTOR 
					|| getUsuarioSession().getRolPredeterminado() == Constante.ROL_CAMPUS_JEFE_DEPARTAMENTO){
				periodos = periodoService.obtenerTodoDeUsuario(getUsuarioSession().getIdUsuario());
			}else{
				periodos = periodoService.obtenerTodo();
			}
			
		} catch (ServiceException e) {
		}
		getRequest().setAttribute("PERIODO_PREDETERMINADA", periodos);
		getRequest().setAttribute("USUARIOS_BUSQUEDA", usuarios);
		getRequest().setAttribute("busquedaFecha1", busquedaFecha1);
		getRequest().setAttribute("busquedaFecha2", busquedaFecha2);
		getRequest().setAttribute("busquedaFamilia", busquedaFamilia);
		getRequest().setAttribute("busquedaSede", busquedaSede);
		getRequest().setAttribute("busquedaPeriodo", busquedaPeriodo);
		return SUCCESS;
	}

	public String reportePDF() {
		log.info("reportePDF()");
		
		ReporteDetalle reporte = null;
		AulaVirtual aula = null;
		try {
			if (null != matriculas_array && getUsuarioSession() != null) {
				
				Archivo.crearDirectorio(Constante.RUTA_REPOSITORIO_TEMPORAL + Constante.SLASH + getRequest().getSession().getId());
				String source = Constante.RUTA_REPOSITORIO_TEMPORAL
								+ Constante.SLASH
								+ getRequest().getSession().getId()
								+ Constante.SLASH
								+ "reporte"
								+ Formato.getStringDeDateArchivo(new GregorianCalendar());
				String filenameReporte = null;
				//String filenameAuditoria = null;
										
				String filenameZip = "Reportes-"
									+ Formato.getStringDeDateArchivoReporte(new GregorianCalendar())
									//+ Constante.EXTENSION_ZIP;
									+ Constante.EXTENSION_PDF;
				
				if (1 == matriculas_array.length) {
					
					source += Constante.EXTENSION_PDF;
					
					if(getUsuarioSession().getRolPredeterminado() == Constante.ROL_CAMPUS_MONITOR_EMPRESA
							&& !reporteService.esMiEmpleado(getUsuarioSession().getIdUsuario(), Integer.parseInt(matriculas_array[0]))){
						
						log.error("EL Monitor de empresa: "+getUsuarioSession().getCodigo()+", no tiene permiso para el idMatricula: "+matriculas_array[0]);
					
					}else{
					
						if (Integer.parseInt(rol_array[0]) == Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE) {
							log.info("Auditoria Docente: "+matriculas_array[0]);
							
							/*reporte = aulaVirtualService.obtenerAuditoria(Integer.parseInt(matriculas_array[0]));
							
							filenameAuditoria = "Auditoria-"
											+ Formato.getStringEspacioReporte(reporte.getNombreUsuarioReporte()
											+ "-"
											+ reporte.getNombreCurso())
											+ "-"
											+ Formato.getStringDeDateArchivoReporte(new GregorianCalendar())
											+ Constante.EXTENSION_PDF;
							Archivo.downloadFile(filenameAuditoria, source, getResponse());*/
						} else {
							log.info("Reporte Alumno: "+matriculas_array[0]);
							
							reporte = aulaVirtualService.obtenerReporteDetalle(Integer.parseInt(matriculas_array[0]));
							aula = aulaVirtualService.obtenerAulaTmpParaAuditoria(Integer.parseInt(matriculas_array[0]));
							
							filenameReporte = "Reporte-" 
											+ Formato.getStringEspacioReporte(reporte.getNombreUsuarioReporte()
											+ "-"
											+ reporte.getNombreCurso())
											+ "-"
											+ Formato.getStringDeDateArchivoReporte(new GregorianCalendar())
											+ Constante.EXTENSION_PDF;
							PDFReport.generarReportePDF(aula, reporte, new FileOutputStream(source));
							Archivo.downloadFile(filenameReporte, source, getResponse());
							
						}
						
					}
					
				} else {
					
					source += Constante.EXTENSION_PDF;
					
					Document doc = PDFReport.getNewDocumentPDF();
					PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(source));
					doc.open();
					
					for (int i = 0; i < matriculas_array.length; i++) {
						
						if(getUsuarioSession().getRolPredeterminado() == Constante.ROL_CAMPUS_MONITOR_EMPRESA
								&& !reporteService.esMiEmpleado(getUsuarioSession().getIdUsuario(), Integer.parseInt(matriculas_array[i]))){
							
							log.error("EL Monitor de empresa: "+getUsuarioSession().getCodigo()+", no tiene permiso para el idMatricula: "+matriculas_array[i]);
							doc.add(new Paragraph("No tiene permiso para el matricula "+matriculas_array[i]));
							doc.newPage();
						}else{
						
							if (Integer.parseInt(rol_array[i]) == Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE) {
								log.info("Auditoria Docente: "+matriculas_array[i]);
								doc.add(new Paragraph("Auditoria para docente no implementada"));
								doc.newPage();
															
							} else {
								log.info("Reporte Alumno: "+matriculas_array[i]);
								
								reporte = aulaVirtualService.obtenerReporteDetalle(Integer.parseInt(matriculas_array[i]));
								aula = aulaVirtualService.obtenerAulaTmpParaAuditoria(Integer.parseInt(matriculas_array[i]));
								
								PDFReport.dibujarReportePDF(aula, reporte,doc);
								
							}
							
						}
						
					}
					
					doc.close();
					writer.close();
					
					
					Archivo.downloadFile(filenameZip, source, getResponse());
					
/*					source += Constante.EXTENSION_ZIP;
					ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(source)));
					ZipEntry entry = null;
					
					for (int i = 0; i < matriculas_array.length; i++) {
						
						if (Integer.parseInt(rol_array[i]) == Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE) {
							log.info("Auditoria Docente: "+matriculas_array[i]);
							
//							reporte = aulaVirtualService.obtenerAuditoria(Integer.parseInt(matriculas_array[i]));
//							 filenameAuditoria = "Auditoria-"
//							 		+ Formato.getStringEspacioReporte(reporte.getNombreUsuarioReporte()
//									+ "-"
//									+ reporte.getNombreCurso())
//									+ "-"
//									+ Formato.getStringDeDateArchivoReporte(new GregorianCalendar())
//									+ Constante.EXTENSION_PDF;
//							entry = new ZipEntry(filenameAuditoria);
//							zipOut.putNextEntry(entry);
							
						} else {
							log.info("Reporte Alumno: "+matriculas_array[i]);
							
							reporte = aulaVirtualService.obtenerReporteDetalle(Integer.parseInt(matriculas_array[i]));
							aula = aulaVirtualService.obtenerAulaTmpParaAuditoria(Integer.parseInt(matriculas_array[i]));
							
							filenameReporte = "Reporte-"
									+ Formato.getStringEspacioReporte(reporte.getNombreUsuarioReporte()
									+ "-"
									+ reporte.getNombreCurso())
									+ "-"
									+ Formato.getStringDeDateArchivoReporte(new GregorianCalendar())
									+ Constante.EXTENSION_PDF;
							entry = new ZipEntry(filenameReporte);
							zipOut.putNextEntry(entry);
							
							PDFReport.generarReportePDF(aula, reporte, zipOut);
						}
					}
					zipOut.close();
					Archivo.downloadFile(filenameZip, source, getResponse());
*/					
				}

			}else{
				log.error("No existe lista de codigos de matriculas o finalizo la sesion.");
				return ERROR;
			}
		} catch (ServiceException e) {
			log.error(e.toString());
			return ERROR;
		} catch (Exception e) {
			log.error(e.toString());
			return ERROR;
		} catch (Throwable e) {
			log.error(e.toString());
			return ERROR;
		}
		
		return NONE;
	}

	public String reportesecdocImprimir() {
		log.info("reportesecdocImprimir()");
		Collection<UsuarioReporteSecDoc> usuarios = new ArrayList<UsuarioReporteSecDoc>();
		try {
			if (null != matriculas_array) {
				usuarios = reporteService.busquedaSecDoc(matriculas_array);
			}
		} catch (ServiceException e) {
		}
		getRequest().setAttribute("USUARIOS_FICHAS", usuarios);
		return SUCCESS;
	}

	public String reportesecdocExcel() {
		log.info("reportesecdocExcel()");
		String fileInput = Constante.HOME_JASPER + Constante.SLASH
				+ "reportSecDoc.jasper";
		try {
			Archivo.crearDirectorio(Constante.RUTA_REPOSITORIO_TEMPORAL
					+ Constante.SLASH + getRequest().getSession().getId());
		} catch (IOException e2) {
			log.error(e2.toString());
		}
		String fileOut = Constante.RUTA_REPOSITORIO_TEMPORAL + Constante.SLASH
				+ getRequest().getSession().getId() + Constante.SLASH + "excel"
				+ Constante.EXTENSION_EXCEL;
		try {
			if (null != matriculas_array) {
				reporteService.busquedaSecDocExcel(matriculas_array,
						getUsuarioSession(), Constante.HOME_JASPER
								+ Constante.SLASH + "logo_index.jpg",
						fileInput, fileOut);
			}
		} catch (ServiceException e) {
			return NONE;
		}
		int data;
		ServletOutputStream stream = null;
		BufferedInputStream buffer = null;
		try {
			getResponse()
					.setHeader(
							"Content-Disposition",
							"attachment; filename=\"reporteSecdoc"
									+ Formato
											.getStringDeDateArchivo(new GregorianCalendar())
									+ Constante.EXTENSION_EXCEL + "\"");
			buffer = new BufferedInputStream(new FileInputStream(fileOut));
			stream = getResponse().getOutputStream();
			while ((data = buffer.read()) != -1) {
				stream.write(data);
			}
			buffer.close();
			stream.flush();
			stream.close();
		} catch (FileNotFoundException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
		} finally {
			getResponse().setHeader("Content-Disposition", null);
			if (stream != null) {
				try {
					stream.flush();
				} catch (IOException e1) {
					log.error(e1.getMessage());
				}
				try {
					stream.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
			if (buffer != null) {
				try {
					buffer.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
		}
		return NONE;
	}
	
	public String auditoriaTecsupVirtual() {
		log.info("auditoriaTecsupVirtual() Periodo:"+busquedaPeriodo + " De "+busquedaFecha1+" a "+busquedaFecha2);
				
		try {
			
			if(busquedaPeriodo != null && busquedaFecha1 != null && busquedaFecha2 != null 
					&& Integer.parseInt(busquedaPeriodo) != 0 && Formato.setDateDeJSPCompleto(busquedaFecha1) != null
					&& Formato.setDateDeJSPCompleto(busquedaFecha2) != null){
				
				ReporteFiltro filtro = new ReporteFiltro();
				filtro.setBusquedaFecha1(busquedaFecha1);
				filtro.setBusquedaFecha2(busquedaFecha2);
				filtro.setBusquedaPeriodo(busquedaPeriodo);
				
				if(getUsuarioSession().getRolPredeterminado() == Constante.ROL_CAMPUS_JEFE_DEPARTAMENTO){
					log.info("Obteniendo permisos de Jefe a departamentos.");
					Collection<Sede> departamentos = usuarioService.obtenerPermisoDepartamento(getUsuarioSession().getIdUsuario());
					filtro.setPermisos(departamentos);
				}
				
				Collection<TecsupVirtualReporte> auditoria = reporteService.obtenerAuditoriaTecsupVirtual(filtro);
				Periodo periodo = periodoService.obtener(busquedaPeriodo);
				
				getRequest().setAttribute("AUDITORIA", auditoria);
				getRequest().setAttribute("PERIODO", periodo);
				
			}else{
				throw new Exception("Uno de los parametros requeridos es nulo. "
						+busquedaFecha1+"-"+busquedaFecha2+"-"+busquedaPeriodo);
			}
			
		} catch (Exception e) {
			log.error(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String reporteTecsupVirtual() {
		log.info("reporteTecsupVirtual() Periodo:"+busquedaPeriodo);
				
		try {
			
			if(busquedaPeriodo != null && Integer.parseInt(busquedaPeriodo) != 0){
				
				ReporteFiltro filtro = new ReporteFiltro();
				filtro.setBusquedaPeriodo(busquedaPeriodo);
				
				Collection<TecsupVirtualReporte> reporte = reporteService.obtenerReporteTecsupVirtual(filtro);
				Periodo periodo = periodoService.obtener(busquedaPeriodo);
				
				getRequest().setAttribute("REPORTE", reporte);
				getRequest().setAttribute("PERIODO", periodo);
				
			}else{
				throw new Exception("Periodo no válido. "+busquedaPeriodo);
			}
			
		} catch (Exception e) {
			log.error(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String obtenerAuditoriaSemanal() {
		log.info("auditoriaTecsupVirtual() IDMatricula:"+idMatricula + " De "+busquedaFecha1+" a "+busquedaFecha2);
				
		try {
			
			if(idMatricula != null && busquedaFecha1 != null && busquedaFecha2 != null 
					&& idMatricula > 0 && Formato.setDateDeJSPCompleto(busquedaFecha1) != null
					&& Formato.setDateDeJSPCompleto(busquedaFecha2) != null){
				
				
				ReporteDetalle reporte = reporteService.obtenerAuditoriaSemanal(idMatricula, busquedaFecha1, busquedaFecha2);
				AulaVirtual aula = aulaVirtualService.obtenerAulaTmpParaAuditoria(idMatricula);
				
				getRequest().setAttribute("REPORTE_DETALLE", reporte);
				getSession().put(Constante.AULA_ACTUAL, aula);
				
			}else{
				throw new Exception("Matricula o fecha no valida. "+idMatricula+ " De "+busquedaFecha1+" a "+busquedaFecha2);
			}
			
		} catch (Exception e) {
			log.error(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String auditoriaDeCurso() throws Exception{
		log.info("auditoriaDeCurso()");
		if(getUsuarioSession().getRolPredeterminado() == Constante.ROL_CAMPUS_DIRECTOR 
				|| getUsuarioSession().getRolPredeterminado() == Constante.ROL_CAMPUS_JEFE_DEPARTAMENTO){
			getRequest().setAttribute("PERIODO_PREDETERMINADA", periodoService.obtenerTodoDeUsuario(getUsuarioSession().getIdUsuario()));
		}else{
			getRequest().setAttribute("PERIODO_PREDETERMINADA", periodoService.obtenerTodo());
		}
		return SUCCESS;
	}
	
	public String auditoriaDeDocente() throws Exception{
		log.info("auditoriaDeDocente()");
		if(getUsuarioSession().getRolPredeterminado() == Constante.ROL_CAMPUS_DIRECTOR 
				|| getUsuarioSession().getRolPredeterminado() == Constante.ROL_CAMPUS_JEFE_DEPARTAMENTO){
			getRequest().setAttribute("PERIODO_PREDETERMINADA", periodoService.obtenerTodoDeUsuario(getUsuarioSession().getIdUsuario()));
		}else{
			getRequest().setAttribute("PERIODO_PREDETERMINADA", periodoService.obtenerTodo());
		}
		return SUCCESS;
	}

}
