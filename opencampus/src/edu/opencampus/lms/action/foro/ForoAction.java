package edu.opencampus.lms.action.foro;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.opencampus.lms.action.BaseAction;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.Foro;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.modelo.correo.Alerta;
import edu.opencampus.lms.modelo.foro.Mensaje;
import edu.opencampus.lms.modelo.foro.OpcionesBusqueda;
import edu.opencampus.lms.modelo.foro.Tema;
import edu.opencampus.lms.service.BuzonService;
import edu.opencampus.lms.service.ForoService;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;

public class ForoAction extends BaseAction {
	
	private static final long serialVersionUID = -2846591434717555724L;
	
	private ForoService foroService;
	
	private BuzonService buzonService;
	
	private Collection<Foro> foros;
	
	private List<Tema> temas;
	
	private List<Mensaje> mensajes;
	
	private List<Mensaje> allMensajes; 
		
	private int idForo;
	
	private int idTema;
	
	private String cuerpo;
	
	private String titulo;
	
	private int idMensaje;
	
	private String destino;
	
	private int calificacion;
	
	private int pagActual;
	
	private int totalMensajes;
	
	private int end;
	
	private int start;
	
	private int lastOne;
	
	private boolean admin;
			
	public void setBuzonService(BuzonService buzonService) {
		this.buzonService = buzonService;
	}

	public boolean isAdmin() {
		return admin;
	}

	public int getLastOne() {
		return lastOne;
	}

	public void setLastOne(int lastOne) {
		this.lastOne = lastOne;
	}

	public int getTotalMensajes() {
		return totalMensajes;
	}

	public void setTotalMensajes(int totalMensajes) {
		this.totalMensajes = totalMensajes;
	}

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public int getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(int idMensaje) {
		this.idMensaje = idMensaje;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	// Busqueda
	
	private Collection<Foro> foroEtiquetas;
	private String claveTema;
	private String clavecompleta;
	private String usuario;
	private String solotema;
	private String fechaLimite;
	private String enforos;
	
	public Collection<Foro> getForoEtiquetas() {
		return this.foroEtiquetas;
	}

	public void setFechaLimite(String fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	public void setClaveTema(String claveTema) {
		this.claveTema = claveTema;
	}

	public void setClavecompleta(String clavecompleta) {
		this.clavecompleta = clavecompleta;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public void setSolotema(String solotema) {
		this.solotema = solotema;
	}

	public void setEnforos(String enforos) {
		this.enforos = enforos;
	}

	public String getClaveTema() {
		return claveTema;
	}

	public String getClavecompleta() {
		return clavecompleta;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getSolotema() {
		return solotema;
	}

	public String getFechaLimite() {
		return fechaLimite;
	}

	public String getEnforos() {
		return enforos;
	}

	public String cargarPortada(){
		log.info("cargarPortada()");
		try {
			foros = foroService.cargarPortada(getUsuarioSession());
		} catch (Exception e) {
			log.error(e.toString());
			return NONE;
		}
		return SUCCESS;
	}
	
	public String foros(){
		log.info("foros()");
		String idUsuario = getUsuarioSession().getIdUsuario();
		try {
			//Permisos a Foros
			Set<Integer> misForos = new HashSet<Integer>();
			
			if (getUsuarioSession().getRolPredeterminado() == Constante.ROL_CAMPUS_ADMINISTRADOR) {
				foros = foroService.obtenerForos(misForos);
			}else{
				foros = foroService.obtenerForos(idUsuario,misForos);
			}
			getUsuarioSession().setMisForos(misForos);	
			
		} catch (Exception e) {
			log.error(e.toString());
		}
		return SUCCESS;
	}
	
	public String temas(){
		log.info("temas()");
		try {
			if(getUsuarioSession().getMisForos() == null){
				foros();
			}
			if(getUsuarioSession().getRolPredeterminado()==Constante.ROL_CAMPUS_ADMINISTRADOR 
					|| getUsuarioSession().getMisForos().contains(idForo)){
				
				Foro foro = foroService.obtenerForo(idForo,getUsuarioSession().getIdUsuario());
				temas = foroService.obtenerTemas(idForo);
				getSession().put("foro", foro);
				
				if(getUsuarioSession().getRolPredeterminado()==Constante.ROL_CAMPUS_ADMINISTRADOR)
					this.admin = true; 
				
			}else{
				log.error("No tiene Permiso para acceder a este foro: "+idForo);
			}
		} catch (Exception e) {
			log.error(e.toString());
		}
		return SUCCESS;
	}
	
	public String buscar(){
		log.info("buscar()");
		
		if(getUsuarioSession().getMisForos() == null || getUsuarioSession().getMisForos().isEmpty()){
			foros();
		}
		try {
			
			foroEtiquetas = foroService.listarTitulosDeForos(getUsuarioSession().getMisForos());
			
			if(claveTema != null && usuario != null && (claveTema.trim().length()>0 || usuario.trim().length()>0)){
				log.info("Proceso de busqueda: Tema: "+claveTema+" - Usuario: "+usuario);
				
				OpcionesBusqueda opciones = new OpcionesBusqueda();
				opciones.setTema(Formato.matizarFrace(claveTema).split(" "));
				opciones.setUsuario(usuario);
				opciones.setClaveCompleta((clavecompleta != null));
				opciones.setSoloTema((solotema != null));
				
				int enforosInt = Integer.parseInt(enforos);
				if(enforosInt == 0){
					opciones.setMisForos(getUsuarioSession().getMisForos());
				}else{
					Set<Integer> misForos = new HashSet<Integer>();
					misForos.add(enforosInt);
					opciones.setMisForos(misForos);
				}
				
				GregorianCalendar date = null;
				if("".equals(fechaLimite)){
					date = new GregorianCalendar(1,0,1);
				}else{
					date = new GregorianCalendar();
					date.add(Calendar.DATE, (-1)*(Integer.parseInt(fechaLimite)));
				}
				opciones.setFechaLimite(date);
				
				temas = (List<Tema>)foroService.buscarPorTema(opciones);
				
			}
			
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (NumberFormatException e) {
			log.error(e.toString());
		}
		return SUCCESS;
	}
	
	public String buscarUsuarios() {
		log.info("buscarUsuarios(): " + usuario);
		try {
			getResponse().setContentType("text/html; charset=ISO-8859-1");
			PrintWriter pw = getResponse().getWriter();

			if (usuario != null && usuario.trim().length() > 0) {
				Collection<Usuario> array = foroService.buscarUsuarios(Formato.matizarFrace(usuario).replace(' ', '%'));
				if (array.isEmpty())
					pw.print("0&vacio");
				else {
					if (array.size() > 19)
						pw.print("1&");
					else
						pw.print("0&");
					int n = 0;
					for (Usuario usuario : array) {
						pw.print("<div id=\"" + usuario.getIdUsuario()
								+ "\" onClick=\"clickLista(this);\" "
								+ "onMouseOver=\"mouseDentro(this,'" + (n++)
								+ "');\">" + usuario.getNombreCompleto()
								+ "</div>");
					}
				}
				if (array.size() > 19)
					pw.print("<div><b><i>Existe muchas coincidencias, sea más específico.</i></b></div>");
				pw.flush();
			}
			pw.close();

		} catch (ServiceException e) {
			log.error(e.getMessage());
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return NONE;
	}
	
	public String cambiarEstado() {
		log.info("cambiarEstado(): " + destino);
		try {
			Foro foro = (Foro)getSession().get("foro");
			if(idTema != 0 && destino != null && foro != null){
				
				if(getUsuarioSession().getRolPredeterminado()==Constante.ROL_CAMPUS_ADMINISTRADOR 
						|| foro.isYoLoModero()){
					
					if("cerrar".equals(destino))
						foroService.cambiarEstadoDeTema(foro.getIdForo(), idTema, 1);
					else
						foroService.cambiarEstadoDeTema(foro.getIdForo(), idTema, 0);
					
					PrintWriter out = getResponse().getWriter();
					out.print("OK");
					out.close();
					
				}else{
					log.error("No tiene Permiso para moderar este foro: "+foro.getIdForo());
				}
			}else{
				log.error("Error en la recuperacion de parametros");
			}
		} catch (ServiceException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		return NONE;
	}
	
	public String calificar(){
		log.info("calificar()");
		
		try {
			Tema tema = (Tema)getSession().get("tema");
			if(tema.getUsuarioCreacion().equals(getUsuarioSession().getIdUsuario())){
				PrintWriter out = getResponse().getWriter();
				out.print("-1");
				out.close();
				return NONE;
			}
			tema.setValoracion(calificacion);
			tema.setUsuarioModificacion(getUsuarioSession().getIdUsuario());
			foroService.calificar(tema);
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
		return NONE;
	}
	
	public String eliminarTema(){
		log.info("eliminarTema()");
		try {
			Foro foro = (Foro)getSession().get("foro");
			if(idTema != 0 && foro != null){
				
				if(getUsuarioSession().getRolPredeterminado()==Constante.ROL_CAMPUS_ADMINISTRADOR 
						|| foro.isYoLoModero()){
					foroService.eliminarTema(foro.getIdForo(), idTema);
					
					PrintWriter out = getResponse().getWriter();
					out.print("OK");
					out.close();
					
				}else{
					log.error("No tiene Permiso para moderar este foro: "+foro.getIdForo());
				}
			}else{
				log.error("Error en la recuperacion de parametros");
			}
		} catch (ServiceException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		return NONE;
	}
	
	public String eliminarMensaje(){
		log.info("eliminarMensaje()");
		try {
			Foro foro = (Foro)getSession().get("foro");
			Tema tema = (Tema)getSession().get("tema");
			if(idMensaje != 0 && foro != null && tema != null){
				
				if(getUsuarioSession().getRolPredeterminado()==Constante.ROL_CAMPUS_ADMINISTRADOR 
						|| foro.isYoLoModero()){
					
					foroService.eliminarMensaje(foro.getIdForo(), tema.getIdTema(), idMensaje);
					
					Alerta a = new Alerta();
					a.setTitulo("Acerca de su participación en el Foro");
					a.setContenido("Debido a una violación del reglamento en el foro su mensaje ha sido retirado.");
					a.setEmisor(getUsuarioSession().getIdUsuario());
					a.setDestino(foroService.obtenerEmisor(idMensaje));
					buzonService.enviarAlerta(a);
					
					PrintWriter out = getResponse().getWriter();
					out.print("OK");
					out.close();
					
				}else{
					log.error("No tiene Permiso para moderar este foro: "+foro.getIdForo());
				}
			}else{
				log.error("Error en la recuperacion de parametros");
			}
		} catch (ServiceException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		return NONE;
	}
	
	public String mensajes(){
		log.info("mensajes()");
		try {
			
			if(idForo != 0){
				temas();
			}
			
			Foro foro = (Foro)getSession().get("foro");
			
			Tema tema = foroService.obtenerTema(foro.getIdForo(), idTema);
			
			if(tema != null){
				
				if(pagActual==0)pagActual=1;
				end = (pagActual * Constante.MAX_FILA_POR_PAG);
				start = (end - Constante.MAX_FILA_POR_PAG);
				
				allMensajes = foroService.obtenerMensajes(foro.getIdForo(), tema.getIdTema());
				if(end > allMensajes.size()){
					end = allMensajes.size();
				}
				
				totalMensajes = allMensajes.size();
				lastOne = (int)Math.ceil((totalMensajes / (double)Constante.MAX_FILA_POR_PAG));
				
				mensajes = allMensajes.subList(start, end);
				
				getSession().put("tema", tema);
				
				if(getUsuarioSession().getRolPredeterminado()==Constante.ROL_CAMPUS_ADMINISTRADOR)
					this.admin = true; 
				
			}else{
				log.error("Tema no encontrado");
				return ERROR;
			}
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
		}
		return SUCCESS;
	}
	
	public String nuevoTema(){
		log.info("nuevoTema()");
		
		Foro foro = (Foro)getSession().get("foro");
		
		try {
			setIdForo(foro.getIdForo());
		
			if(foro.getCerrado()==0 && titulo.trim().length() > 0 && cuerpo.trim().length() > 0){
				Tema tema = new Tema();
				tema.setTitulo(titulo);
				tema.setCuerpo(cuerpo);
				tema.setIdForo(foro.getIdForo());
				tema.setUsuarioCreacion(getUsuarioSession().getIdUsuario());			
				
				foroService.nuevoTema(tema);
			}
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
		}
		
		return SUCCESS;
	}
	
	public String nuevoMensaje(){
		log.info("nuevoMensaje()");
		
		Foro foro = (Foro)getSession().get("foro");
		Tema tema = (Tema)getSession().get("tema");
		
		try {	
			setIdTema(tema.getIdTema());
			setPagActual(pagActual);
		
			if(foro.getCerrado()==0 && tema.getCerrado()==0 && cuerpo.trim().length()>0){
				Mensaje mensaje = new Mensaje();
				mensaje.setIdForo(foro.getIdForo());
				mensaje.setIdTema(tema.getIdTema());
				mensaje.setCuerpo(cuerpo);
				mensaje.setUsuarioCreacion(getUsuarioSession().getIdUsuario());
				mensaje.setIdMensaje_cita(idMensaje);
				if(getUsuarioSession().getRolPredeterminado()==Constante.ROL_CAMPUS_ADMINISTRADOR 
						|| foro.isYoLoModero()){
					mensaje.setModerado(Constante.ESTADO_ACTIVO);
				}	
				foroService.nuevoMensaje(mensaje);
			}else{
				log.info("Foro cerrado, no es posible publicar un mensaje.");
			}
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (Exception e) {
			log.error(e.toString());
		}
		
		return SUCCESS;
	}

	public int getIdForo() {
		return idForo;
	}

	public void setIdForo(int idForo) {
		this.idForo = idForo;
	}
	
	public List<Tema> getTemas() {
		return temas;
	}

	public void setTemas(List<Tema> temas) {
		this.temas = temas;
	}

	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	public Collection<Foro> getForos() {
		return foros;
	}

	public void setForoService(ForoService foroService) {
		this.foroService = foroService;
	}

	public void setForos(Collection<Foro> foros) {
		this.foros = foros;
	}

	public int getIdTema() {
		return idTema;
	}

	public void setIdTema(int idTema) {
		this.idTema = idTema;
	}

	public int getPagActual() {
		return pagActual;
	}

	public void setPagActual(int pagActual) {
		this.pagActual = pagActual;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public List<Mensaje> getAllMensajes() {
		return allMensajes;
	}

	public void setAllMensajes(List<Mensaje> allMensajes) {
		this.allMensajes = allMensajes;
	}

}