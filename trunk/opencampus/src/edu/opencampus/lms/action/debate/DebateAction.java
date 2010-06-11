package edu.opencampus.lms.action.debate;

import java.io.PrintWriter;
import java.util.List;

import edu.opencampus.lms.action.BaseAction;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.AulaVirtual;
import edu.opencampus.lms.modelo.Debate;
import edu.opencampus.lms.modelo.TrabajoGrupal;
import edu.opencampus.lms.service.DebateService;
import edu.opencampus.lms.service.TrabajoGrupalService;
import edu.opencampus.lms.util.Constante;

public class DebateAction extends BaseAction {

	private static final long serialVersionUID = -5343701698983923596L;

	private DebateService debateService;

	private TrabajoGrupalService tGrupalService;

	public void setDebateService(DebateService debateService) {
		this.debateService = debateService;
	}

	public void setTrabajoGrupalService(TrabajoGrupalService tGrupalService) {
		this.tGrupalService = tGrupalService;
	}
	
	private Integer idDebate;
	
	private Integer idGrupo;
	
	private List<Debate> debates;
	
	private boolean esInvitado;
	
	private boolean esAdmin;
	
	private Debate debate;
	
	private String asunto;
	
	private String texto;

	private int owner;
	
	private int predecesor;

	public Integer getIdDebate() {
		return idDebate;
	}

	public void setIdDebate(Integer idDebate) {
		this.idDebate = idDebate;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}
	
	public List<Debate> getDebates() {
		return debates;
	}

	public void setDebates(List<Debate> debates) {
		this.debates = debates;
	}

	public boolean isEsInvitado() {
		return esInvitado;
	}

	public void setEsInvitado(boolean esInvitado) {
		this.esInvitado = esInvitado;
	}

	public boolean isEsAdmin() {
		return esAdmin;
	}

	public void setEsAdmin(boolean esAdmin) {
		this.esAdmin = esAdmin;
	}

	public Debate getDebate() {
		return debate;
	}

	public void setDebate(Debate debate) {
		this.debate = debate;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public int getPredecesor() {
		return predecesor;
	}

	public void setPredecesor(int predecesor) {
		this.predecesor = predecesor;
	}
	
	TrabajoGrupal tg = null;
	AulaVirtual aula = null;
	@Override
	public void prepare() throws Exception {
		super.prepare();
		tg = (TrabajoGrupal)getSession().get("GRUPAL");
		aula = getUsuarioSession().getAulaActual();
	}

	public String cargar() {
		log.info("cargar()");
		try {
			if (Constante.ROL_CAMPUS_AULAVIRTUAL_MONITOR_DOCENTE != aula.getMatriculaActual().getRol().getIdRol()
					&& Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE != aula.getMatriculaActual().getRol().getIdRol()
					&& Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE != aula.getMatriculaActual().getRol().getIdRol()) {
				this.idGrupo = tGrupalService.obtenerIdGrupo(tg,aula.getMatriculaActual().getIdMatricula());
			}
			if(this.idGrupo != null && this.idGrupo > 0){
				
				aula.setDebateActual(idGrupo);
				
				debates = debateService.obtenerPlactica(aula, tg.getIdTrabajo());
				
				if (getUsuarioSession().getRolPredeterminado().getIdrol() == Constante.ROL_CAMPUS_ADMINISTRADOR) {
					this.esAdmin = true;
				}
//				if (Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE == aula.getRol().getIdrol()
//						|| Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE == aula.getRol().getIdrol()
//						|| Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE == aula.getRol().getIdrol()) {
//					this.esInvitado = false;
//				}else{
//					this.esInvitado = true;
//				}
				
			}else{
				log.error("No tiene permiso para acceder al presente debate.");
				return ERROR;
			}
			
		} catch (NumberFormatException e) {
			log.info(e);
		} catch (ServiceException e) {
			log.info(e);
		} catch (Exception e) {
			log.info(e);
		}
		
		return SUCCESS;
	}
	
	public String cargarSala() {
		log.info("cargarSala() "+idDebate);
		try {
			
			if(idDebate != null){
				
				debate = debateService.obtenerDebate(aula, idDebate, false);
				
				debates = debateService.obtenerSubPlactica(aula, idDebate);
				
				if (getUsuarioSession().getRolPredeterminado().getIdrol() == Constante.ROL_CAMPUS_ADMINISTRADOR) {
					this.esAdmin = true;
				}
//				if (Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE == aula.getRol().getIdrol()
//						|| Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE == aula.getRol().getIdrol()
//						|| Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE == aula.getRol().getIdrol()) {
//					this.esInvitado = false;
//				}else{
//					this.esInvitado = true;
//				}
								
			}else{
				log.error("No tiene permiso para acceder al recurso o no esta listo.");
				return ERROR;
			}
			
		} catch (NumberFormatException e) {
			log.info(e);
		} catch (ServiceException e) {
			log.info(e);
		} catch (Exception e) {
			log.info(e);
		}
		
		return SUCCESS;
	}
	
	public String cargarTertuliaXML() {
		log.info("cargarTertuliaXML() "+idDebate);
		try {
			
			String iddebate = String.valueOf(aula.getDebateActual());
			
			if(aula != null && iddebate != null){
				
				debate = debateService.obtenerDebate(aula, idDebate, true);
				
				debates = debateService.obtenerSubPlactica(aula, idDebate);
				
				getResponse().setContentType("text/xml");
				getResponse().setHeader("Cache-Control", "no-cache");
				PrintWriter out = getResponse().getWriter();
				out.write("<?xml version='1.0' encoding='ISO-8859-1'?>");
				out.write("<debates>");
					
				out.write("<cuerpo><![CDATA[" + debate.getTexto()+ "]]></cuerpo>");

				for (Debate debate: debates) {
					out.write("<debate>");

					out.write("<idDebate>" + debate.getIdDebate()+ "</idDebate>");
					out.write("<asunto><![CDATA[" + debate.getAsunto()+ "]]></asunto>");
					out.write("<fecha>" + debate.getFecha()+ "</fecha>");
					out.write("<nombreUsuario>" + debate.getNombreUsuario()+ "</nombreUsuario>");
					out.write("<leido>" + debate.getLeido()+ "</leido>");
					out.write("<owner>" + debate.getOwner()+ "</owner>");
					out.write("<predecesor>" + debate.getPredecesor()+ "</predecesor>");
					out.write("<cantidadSubPlactica>" + debate.getCantidadSubPlactica()+ "</cantidadSubPlactica>");
					
					out.write("</debate>");
				}
				out.write("</debates>");
				out.close();
						
			}else{
				log.error("No tiene permiso para acceder al recurso o no esta listo.");
			}
			
		} catch (NumberFormatException e) {
			log.info(e);
		} catch (ServiceException e) {
			log.info(e);
		} catch (Exception e) {
			log.info(e);
		}
		return NONE;
	}

	public String crearPlactica() {
		log.info("crearPlactica() "+predecesor);
		
		try {
			if(tg != null && asunto != null && asunto.trim().length()>0 && texto != null && texto.trim().length()>0){
				
				this.idGrupo = aula.getDebateActual();
				
				if(this.idGrupo != null && this.idGrupo > 0){
				
					if(asunto.length()>50) asunto = asunto.substring(0,47)+"...";
					
					Debate modelo = new Debate();
					modelo.setAsunto(asunto.replaceAll("'", "\"").replaceAll("\\\\", "/"));
					modelo.setTexto(texto);
					modelo.setPredecesor(predecesor);
					
					modelo = debateService.crearPlactica(aula, tg.getIdTrabajo(), modelo);
				
				}else{
					log.error("No tiene permiso para acceder al recurso o no esta listo.");
				}
			}else{
				log.error("Uno de los parametros requeridos no existen.");
			}
			
			setIdDebate(owner);
			if(predecesor != 0){
				return "goSala";
			}
			
		} catch (Exception e) {
			log.info(e);
		}
		return SUCCESS;
	}
	
	crear <-----
	eliminarPlactica
	eliminar

	--yata
	cargarTertuliaXML
	public String crear() {
		log.info("crear() "+predecesor);
		
		try {
			if(tg != null && asunto != null && asunto.trim().length()>0 && texto != null && texto.trim().length()>0){
			
				this.idGrupo = aula.getDebateActual();
				
				if(this.idGrupo != null && this.idGrupo > 0){
					
					if(asunto.length()>50) asunto = asunto.substring(0,47)+"...";
					
					Debate modelo = new Debate();
					modelo.setAsunto(asunto.replaceAll("'", "\"").replaceAll("\\\\", "/"));
					modelo.setTexto(texto);
					modelo.setPredecesor(predecesor);
					try { 
						
						modelo = debateService.crearPlactica(aula, idGrupo, modelo);
						PrintWriter out = getResponse().getWriter();
						out.print("OK");
						out.close();
						
					} catch (ServiceException e) {
						log.info(e);
					} catch (Exception e) {
						log.info(e);
					}
				}else{
					log.error("No tiene permiso para acceder al recurso o no esta listo.");
				}
				
			}else{
				log.error("Uno de los parametros requeridos no existen.");
			}
			
		} catch (Exception e) {
			log.info(e);
		}
		return NONE;
	}

	public String eliminarPlactica() {
		log.info("eliminarPlactica() "+idDebate);
		try {
			debateService.eliminarPlactica(this.getUsuarioSession().getId()+"", idDebate);
		} catch (ServiceException e) {
			log.info(e);
		}
		this.idGrupo = aula.getDebateActual();
		return SUCCESS;
	}
	
	public String eliminar() {
		log.info("eliminar() "+idDebate);
		try {
			debateService.eliminarPlactica(this.getUsuarioSession().getId()+"", idDebate);
			PrintWriter out = getResponse().getWriter();
			out.print("OK");
			out.close();
		} catch (ServiceException e) {
			log.info(e);
		} catch (Exception e) {
			log.info(e);
		}
		return NONE;
	}

	public String marcarLeido() {
		log.info("marcarLeido() "+idDebate);
		try {
			if(idDebate != 0 && aula != null){
				debateService.marcarLeido(idDebate, aula.getMatriculaActual().getIdMatricula(), owner);
				PrintWriter out = getResponse().getWriter();
				out.print("OK");
				out.close();
			}
		} catch (ServiceException e) {
			log.info(e);
		} catch (Exception e) {
			log.info(e);
		}
		return NONE;
	}

}
