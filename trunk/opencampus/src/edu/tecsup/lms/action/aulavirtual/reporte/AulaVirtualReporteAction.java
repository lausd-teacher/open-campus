package edu.tecsup.lms.action.aulavirtual.reporte;

import java.util.Collection;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.modelo.AulaVirtual;
import edu.tecsup.lms.modelo.aulavirtual.reporte.Reporte;
import edu.tecsup.lms.modelo.aulavirtual.reporte.ReporteDetalle;
import edu.tecsup.lms.modelo.aulavirtual.reporte.ReporteNotas;
import edu.tecsup.lms.service.AulaVirtualService;
import edu.tecsup.lms.service.MatriculaService;
import edu.tecsup.lms.util.Constante;

public class AulaVirtualReporteAction extends BaseAction {

	private static final long serialVersionUID = 1657030865663496957L;

	private String idMatricula;

	private String idFicha;

	private String idUnidad;

	private String tipo;

	private AulaVirtualService aulaVirtualService;

	private MatriculaService matriculaService;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public AulaVirtualService getAulaVirtualService() {
		return aulaVirtualService;
	}

	public void setAulaVirtualService(AulaVirtualService aulaVirtualService) {
		this.aulaVirtualService = aulaVirtualService;
	}

	public MatriculaService getMatriculaService() {
		return matriculaService;
	}

	public void setMatriculaService(MatriculaService matriculaService) {
		this.matriculaService = matriculaService;
	}

	public String getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(String idFicha) {
		this.idFicha = idFicha;
	}

	public String getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(String idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getIdMatricula() {
		return idMatricula;
	}

	public void setIdMatricula(String idMatricula) {
		this.idMatricula = idMatricula;
	}

	public String auditoria() {
		log.info("auditoria()");
		int int_idMatricula = 0;
		try {
			if (null == idMatricula) {
				if (Constante.ROL_CAMPUS_ADMINISTRADOR == getUsuarioSession()
						.getRolPredeterminado()
						|| Constante.ROL_CAMPUS_SOPORTE == getUsuarioSession()
								.getRolPredeterminado()
						|| Constante.ROL_CAMPUS_DIRECTOR == getUsuarioSession()
								.getRolPredeterminado()
						|| Constante.ROL_CAMPUS_JEFE_DEPARTAMENTO == getUsuarioSession()
								.getRolPredeterminado()
						|| Constante.ROL_CAMPUS_MONITOR_REPORTES == getUsuarioSession()
								.getRolPredeterminado()) {
					int_idMatricula = getAulaVirtualSession().getIdMatricula();
				} else {
					if (Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE == getAulaVirtualSession()
							.getRol().getIdrol()
							|| Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE == getAulaVirtualSession()
									.getRol().getIdrol()
							|| Constante.ROL_CAMPUS_AULAVIRTUAL_MONITOR_DOCENTE == getAulaVirtualSession()
									.getRol().getIdrol()) {
						if (matriculaService.matriculaPerteneceAulaUsuario(idMatricula, getUsuarioSession().getIdUsuario())) {
							int_idMatricula = Integer.parseInt(idMatricula);
						}
					}
					if (Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE == getAulaVirtualSession()
							.getRol().getIdrol()) {
						int_idMatricula = getAulaVirtualSession()
								.getIdMatricula();
					}
				}
			} else {
				if (Constante.ROL_CAMPUS_ADMINISTRADOR == getUsuarioSession()
						.getRolPredeterminado()
						|| Constante.ROL_CAMPUS_SOPORTE == getUsuarioSession()
								.getRolPredeterminado()
						|| Constante.ROL_CAMPUS_DIRECTOR == getUsuarioSession()
								.getRolPredeterminado()
						|| Constante.ROL_CAMPUS_JEFE_DEPARTAMENTO == getUsuarioSession()
								.getRolPredeterminado()
						|| Constante.ROL_CAMPUS_MONITOR_REPORTES == getUsuarioSession()
								.getRolPredeterminado()) {
					int_idMatricula = Integer.parseInt(idMatricula);

					if (getAulaVirtualSession() == null) {
						AulaVirtual aula = aulaVirtualService
								.obtenerAulaTmpParaAuditoria(Integer
										.parseInt(idMatricula));
						getSession().put(Constante.AULA_ACTUAL, aula);
					}

				} else {
					if (getAulaVirtualSession() == null || getAulaVirtualSession().getRol() == null) {
						log.info("Auditoria Fuera de Aula");
						if (matriculaService.esMiMatricula(idMatricula,getUsuarioSession().getIdUsuario())) {
							int_idMatricula = Integer.parseInt(idMatricula);
							AulaVirtual aula = aulaVirtualService
									.obtenerAulaTmpParaAuditoria(int_idMatricula);
							getSession().put(Constante.AULA_ACTUAL,
									aula);
						}
					} else {
						if (Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE == getAulaVirtualSession()
								.getRol().getIdrol()
								|| Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE == getAulaVirtualSession()
										.getRol().getIdrol()
								|| Constante.ROL_CAMPUS_AULAVIRTUAL_MONITOR_DOCENTE == getAulaVirtualSession()
										.getRol().getIdrol()) {
							if (matriculaService.matriculaPerteneceAulaUsuario(idMatricula, getUsuarioSession().getIdUsuario())) {
								int_idMatricula = Integer.parseInt(idMatricula);
							}
						}
						if (Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE == getAulaVirtualSession()
								.getRol().getIdrol()) {
							int_idMatricula = getAulaVirtualSession()
									.getIdMatricula();
						}
					}
				}
			}
			if (0 == int_idMatricula) {
				throw new Exception("Matricula no tiene permiso "
						+ getUsuarioSession().getIdUsuario() + " en "
						+ idMatricula);
			}
			if (null != tipo) {
				getRequest().setAttribute("ATRAS_PAGINA", "si");
			}

			ReporteDetalle rdetalle = aulaVirtualService.obtenerAuditoria(int_idMatricula);
			getRequest().setAttribute("REPORTE_DETALLE", rdetalle);
			
		} catch (Exception e) {
			log.error(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public String reporteDetalle() {
		log.info("reporteDetalle(): idMatricula: "+idMatricula+" - Rol " + getUsuarioSession().getRolPredeterminado());
		int int_idMatricula = 0;
		
		try {
			if (null == idMatricula && null != getAulaVirtualSession()) {
				log.info("Reporte Personal: "+getAulaVirtualSession().getIdMatricula());
				int_idMatricula = getAulaVirtualSession().getIdMatricula();
			} else {
				//*****************************************************************
				if (Constante.ROL_CAMPUS_ADMINISTRADOR == getUsuarioSession().getRolPredeterminado()
					|| Constante.ROL_CAMPUS_SOPORTE == getUsuarioSession().getRolPredeterminado()
					|| Constante.ROL_CAMPUS_DIRECTOR == getUsuarioSession().getRolPredeterminado()
					|| Constante.ROL_CAMPUS_MONITOR_REPORTES == getUsuarioSession().getRolPredeterminado()) {
					
					log.info("Reporte Admin: "+idMatricula);
					int_idMatricula = Integer.parseInt(idMatricula);
						
				} else if(Constante.ROL_CAMPUS_JEFE_DEPARTAMENTO == getUsuarioSession().getRolPredeterminado()){
					//validar
					log.info("Reporte Jefe: "+idMatricula);
					int_idMatricula = Integer.parseInt(idMatricula);
					
				} else if(Constante.ROL_CAMPUS_MONITOR_EMPRESA == getUsuarioSession().getRolPredeterminado()){
					
					log.info("Reporte Empresa: "+idMatricula);
					if (matriculaService.esMiEmpleado(getUsuarioSession().getIdUsuario(), Integer.parseInt(idMatricula))) {
						int_idMatricula = Integer.parseInt(idMatricula);
					}else{
						log.error("No es mi empleado");
					}
					
				} else {
					
					int idRol = (getAulaVirtualSession() == null || getAulaVirtualSession().getRol() == null)?matriculaService.obtenerRolDeAula(idMatricula, getUsuarioSession().getIdUsuario()):getAulaVirtualSession().getRol().getIdrol();
					log.info("Rol de Aula: "+idRol);
					if (Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE == idRol
						|| Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE == idRol
						|| Constante.ROL_CAMPUS_AULAVIRTUAL_MONITOR_DOCENTE == idRol) {
							
						log.info("Reporte Docente: "+idMatricula);
						if (matriculaService.matriculaPerteneceAulaUsuario(idMatricula, getUsuarioSession().getIdUsuario())) {
							int_idMatricula = Integer.parseInt(idMatricula);
						}else{
							log.error("No es mi alulmno");
						}
						
					}else if (Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE == idRol) {
						
						log.info("Reporte Estudiante: "+idMatricula);
						if (matriculaService.esMiMatricula(idMatricula, getUsuarioSession().getIdUsuario())) {
							int_idMatricula = Integer.parseInt(idMatricula);
						}else{
							log.error("No es mi matricula");
						}
						
					}
				}
				//*****************************************************************
			}
			if (0 == int_idMatricula) {
				throw new Exception("Usuario no tiene permiso "+ getUsuarioSession().getIdUsuario() + " en "+ idMatricula);
			}
			
			if (getAulaVirtualSession() == null) {
				log.info("Reporte Fuera de Aula");
				AulaVirtual aula = aulaVirtualService.obtenerAulaTmpParaAuditoria(Integer.parseInt(idMatricula));
				getRequest().setAttribute(Constante.AULA_ACTUAL+"_TMP", aula);
			}
			
			if (null != tipo) {
				getRequest().setAttribute("ATRAS_PAGINA", "si");
			}
			
			ReporteDetalle rdetalle = aulaVirtualService.obtenerReporteDetalle(int_idMatricula);
			if(rdetalle == null){
				throw new Exception("ReporteDetalle no encontrado. Usuario: "+ getUsuarioSession().getIdUsuario() + " - idMatricula: "+ idMatricula);
			}
			getRequest().setAttribute("REPORTE_DETALLE", rdetalle);
			
		} catch (Exception e) {
			log.error(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public String reporte() {
		log.info("reporte()");
		Reporte reporte = null;
		int int_idFicha = 0;
		int int_idUnidad = 0;
		boolean pertenece = false;
		try {
			log.info("ROL en plataforma: "
					+ getUsuarioSession().getRolPredeterminado());
			log.info("ROL en el aula: "
					+ getAulaVirtualSession().getRol().getIdrol());
			if (Constante.ROL_CAMPUS_ADMINISTRADOR == getUsuarioSession()
					.getRolPredeterminado()
					|| Constante.ROL_CAMPUS_SOPORTE == getUsuarioSession()
							.getRolPredeterminado()
					|| Constante.ROL_CAMPUS_DIRECTOR == getUsuarioSession()
							.getRolPredeterminado()
					|| Constante.ROL_CAMPUS_JEFE_DEPARTAMENTO == getUsuarioSession()
							.getRolPredeterminado()
					|| Constante.ROL_CAMPUS_MONITOR_REPORTES == getUsuarioSession()
							.getRolPredeterminado()
					|| Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE == getAulaVirtualSession()
							.getRol().getIdrol()) {
				pertenece = true;

			} else if (!(Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE == getAulaVirtualSession()
					.getRol().getIdrol() || Constante.ROL_CAMPUS_AULAVIRTUAL_MONITOR_DOCENTE == getAulaVirtualSession()
					.getRol().getIdrol())) {

				throw new Exception("Su rol "
						+ getAulaVirtualSession().getRol().getIdrol()
						+ " no tiene permiso para ver reportes. ");

			}

			if (null == idFicha) {
				int_idFicha = getAulaVirtualSession().getIdFicha();
			} else {
				int_idFicha = Integer.parseInt(idFicha);
				// Validar si esta ficha pertenece al usuario (para todos menos
				// para admin, soporte ni director)
				// throw new Exception("Matricula " +
				// getUsuarioSession().getIdUsuario() + " no tiene permiso en "
				// + idFicha);
			}

			boolean isSetIdUnidad = (null != idUnidad);
			if (isSetIdUnidad)
				int_idUnidad = Integer.parseInt(idUnidad);

			reporte = aulaVirtualService.obtenerReporte(int_idFicha, int_idUnidad, isSetIdUnidad,pertenece, getUsuarioSession());

		} catch (Exception e) {
			log.error(e);
			return ERROR;
		}
		getRequest().setAttribute("REPORTE_MODELO", reporte);
		return SUCCESS;
	}
	
	public String reporteNotas() {
		log.info("reporteNotas()"+getAulaVirtualSession().getIdFicha());
		try {
			Collection<ReporteNotas> reporte = aulaVirtualService.listarReporteDeNotas(getAulaVirtualSession().getIdFicha());
			getRequest().setAttribute("REPORTE_NOTAS", reporte);
		} catch (Exception e) {
			log.error(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
}
