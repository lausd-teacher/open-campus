package edu.tecsup.lms.action.dialogo;

import java.io.PrintWriter;
import java.util.List;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.AulaVirtual;
import edu.tecsup.lms.modelo.Dialogo;
import edu.tecsup.lms.modelo.usuario.Ingreso;
import edu.tecsup.lms.service.DialogoService;
import edu.tecsup.lms.service.IngresoService;
import edu.tecsup.lms.util.Constante;
import edu.tecsup.lms.util.Util;

public class DialogoAction extends BaseAction {

	private static final long serialVersionUID = -6119998737497405589L;

	private DialogoService dialogoService;

	private IngresoService ingresoService;

	private int idDialogo;
	
	private String idUnidad;
	
	private boolean esInvitado;
	
	private boolean esAdmin;
	
	private Dialogo dialogo;
	
	private List<Dialogo> dialogos;
	
	private String asunto;
	
	private String texto;

	private int owner;
	
	private int predecesor;

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public int getPredecesor() {
		return predecesor;
	}

	public void setPredecesor(int predecesor) {
		this.predecesor = predecesor;
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

	public void setDialogoService(DialogoService dialogoService) {
		this.dialogoService = dialogoService;
	}

	public Dialogo getDialogo() {
		return dialogo;
	}

	public int getIdDialogo() {
		return idDialogo;
	}

	public void setIdDialogo(int idDialogo) {
		this.idDialogo = idDialogo;
	}

	public boolean isEsAdmin() {
		return esAdmin;
	}

	public boolean isEsInvitado() {
		return esInvitado;
	}

	public List<Dialogo> getDialogos() {
		return dialogos;
	}

	public void setIdUnidad(String idUnidad) {
		this.idUnidad = idUnidad;
	}

	public void setIngresoService(IngresoService ingresoService) {
		this.ingresoService = ingresoService;
	}

	public String cargar() {
		log.info("cargar() "+idUnidad);
		try {
			
//			Ingreso ingreso = new Ingreso(0,GETUSUARIOSESSION().GETIDUSUARIO(),CONSTANTE.ELEMENTO_DIALOGO, GETAULAVIRTUALSESSION().GETIDFICHA()
//							+ "", IDUNIDAD, "", "", GETREQUEST().GETREMOTEADDR(), GETREQUEST().GETHEADER("USER-AGENT"));
//			INGRESOSERVICE.INSCRIBIRINGRESO(INGRESO);
		
			AulaVirtual aula = getUsuarioSession().getAulaActual();
			
			if(aula != null && idUnidad != null && aula.getSilabo().esMiUnidad(Integer.parseInt(idUnidad)) &&
					dialogoService.isEnabledDialogo(aula, Integer.parseInt(idUnidad))){
				
				dialogos = dialogoService.obtenerPlactica(aula, Integer.parseInt(idUnidad));
				
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
				
				aula.setDialogoActual(Integer.parseInt(idUnidad));
				
				getRequest().setAttribute("UNIDAD_NOMBRE_TEMP",aula.getSilabo().getNombreUnidad(Integer.parseInt(idUnidad)));
				
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
	
	@Deprecated
	public AulaVirtual getAulaVirtualSession(){
		return getUsuarioSession().getAulaActual();
	}
	
	public String cargarSala() {
		log.info("cargarSala() "+idDialogo);
		try {
			
			AulaVirtual aula = getUsuarioSession().getAulaActual();
			String idUnidad = String.valueOf(getUsuarioSession().getAulaActual().getDialogoActual());
			
			if(aula != null && idUnidad != null && idDialogo != 0){
				
				dialogo = dialogoService.obtenerDialogo(aula, idDialogo, false);
				
				dialogos = dialogoService.obtenerSubPlactica(aula, idDialogo);
				
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
				
				this.idUnidad = String.valueOf(getAulaVirtualSession().getDialogoActual());
								
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
		log.info("cargarTertuliaXML() "+idDialogo);
		try {
			
			AulaVirtual aula = getAulaVirtualSession();
			String idUnidad = String.valueOf(getAulaVirtualSession().getDialogoActual());
			
			if(aula != null && idUnidad != null){
				
				dialogo = dialogoService.obtenerDialogo(aula, idDialogo, true);
				
				dialogos = dialogoService.obtenerSubPlactica(aula, idDialogo);
				
				getResponse().setContentType("text/xml");
				getResponse().setHeader("Cache-Control", "no-cache");
				PrintWriter out = getResponse().getWriter();
				out.write("<?xml version='1.0' encoding='ISO-8859-1'?>");
				out.write("<dialogos>");
					
				out.write("<cuerpo><![CDATA[" + dialogo.getTexto()+ "]]></cuerpo>");

				for (Dialogo dialogo: dialogos) {
					out.write("<dialogo>");

					out.write("<idDialogo>" + dialogo.getIdDialogo()+ "</idDialogo>");
					out.write("<asunto><![CDATA[" + dialogo.getAsunto()+ "]]></asunto>");
					out.write("<fecha>" + dialogo.getFecha()+ "</fecha>");
					out.write("<nombreUsuario>" + dialogo.getNombreUsuario()+ "</nombreUsuario>");
					out.write("<leido>" + dialogo.getLeido()+ "</leido>");
					out.write("<owner>" + dialogo.getOwner()+ "</owner>");
					out.write("<predecesor>" + dialogo.getPredecesor()+ "</predecesor>");
					out.write("<cantidadSubPlactica>" + dialogo.getCantidadSubPlactica()+ "</cantidadSubPlactica>");
					
					out.write("</dialogo>");
				}
				out.write("</dialogos>");
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
		log.info("crearPlactica() p:"+predecesor+" a:"+asunto);
		
		if(getAulaVirtualSession() != null){
			idUnidad = String.valueOf(getAulaVirtualSession().getDialogoActual());
		
			if(getAulaVirtualSession().getDialogoActual() != 0 && asunto != null && asunto.trim().length()>0 && texto != null && texto.trim().length()>0
					&& getAulaVirtualSession().getSilabo().esMiUnidad(Integer.parseInt(idUnidad))){
				
				if(asunto.length()>50) asunto = asunto.substring(0,47)+"...";
				
				Dialogo modelo = new Dialogo();
				modelo.setAsunto(asunto.replaceAll("'", "\"").replaceAll("\\\\", "/"));
				modelo.setTexto(texto);
				modelo.setPredecesor(predecesor);
				try { 
					modelo = dialogoService.crearPlactica(getAulaVirtualSession(), Integer.parseInt(idUnidad), modelo);
					
				} catch (ServiceException e) {
					log.info(e);
				}
			}else{
				log.error("No tiene permiso para acceder al recurso o no esta listo.");
			}
		
			setIdDialogo(owner);
			if(predecesor != 0){
				return "goSala";
			}
		}else{
			log.error("Aula no existe.");
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String crear() {
		log.info("crear() p:"+predecesor+" a:"+asunto);
		if(getAulaVirtualSession() != null){
			idUnidad = String.valueOf(getAulaVirtualSession().getDialogoActual());
			
			if(asunto != null && asunto.trim().length()>0 && texto != null && texto.trim().length()>0){
			
				if(getAulaVirtualSession().getDialogoActual() != 0 && getAulaVirtualSession().getSilabo().esMiUnidad(Integer.parseInt(idUnidad))){
					
					if(asunto.length()>50) asunto = asunto.substring(0,47)+"...";
					
					Dialogo modelo = new Dialogo();
					modelo.setAsunto(asunto.replaceAll("'", "\"").replaceAll("\\\\", "/"));
					modelo.setTexto(texto);
					modelo.setPredecesor(predecesor);
					try { 
						
						modelo = dialogoService.crearPlactica(getAulaVirtualSession(), Integer.parseInt(idUnidad), modelo);
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
		}else{
			log.error("Aula no existe.");
			return ERROR;
		}
		return NONE;
	}

	public String eliminarPlactica() {
		log.info("eliminarPlactica() "+idDialogo);
		try {
			dialogoService.eliminarPlactica(""+this.getUsuarioSession().getId(), idDialogo);
		} catch (ServiceException e) {
			log.info(e);
		}
		idUnidad = String.valueOf(getAulaVirtualSession().getDialogoActual());
		
		return SUCCESS;
	}
	
	public String eliminar() {
		log.info("eliminar() "+idDialogo);
		try {
			dialogoService.eliminarPlactica(""+this.getUsuarioSession().getId(), idDialogo);
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
		log.info("marcarLeido() "+idDialogo);
		try {
			AulaVirtual aula = getAulaVirtualSession();
			
			if(idDialogo != 0 && aula != null){
				dialogoService.marcarLeido(idDialogo, aula.getMatriculaActual().getIdMatricula(), owner);
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

	public String getIdUnidad() {
		return idUnidad;
	}

}
