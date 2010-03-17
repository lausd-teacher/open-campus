package edu.tecsup.lms.action.agenda;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.GregorianCalendar;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.Agenda;
import edu.tecsup.lms.service.AgendaService;
import edu.tecsup.lms.util.Constante;
import edu.tecsup.lms.util.Formato;

public class AgendaAction extends BaseAction{

	private static final long serialVersionUID = -2696176347635132658L;
	
	private AgendaService agendaService;
	
	private String date;
	
	private String sumilla;
	
	private String detalle;
	
	private int notificado;
	
	private int minutoAntes;
	
	private String cmd;

	public void setAgendaService(AgendaService agendaService) {
		this.agendaService = agendaService;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public void setSumilla(String sumilla) {
		this.sumilla = sumilla;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public void setNotificado(int notificado) {
		this.notificado = notificado;
	}

	public void setMinutoAntes(int minutoAntes) {
		this.minutoAntes = minutoAntes;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String cargarPortada(){
		log.info("cargarPortada()");
		try {
			Collection<String> fechas = agendaService.cargarPortada(getUsuarioSession().getId());
			
			PrintWriter out = getResponse().getWriter();
			
			for (String fecha : fechas) {
				out.write(fecha+"/");
			}
			out.close();
				
		} catch (Exception e) {
			log.error(e.toString());
		}
		return NONE;
	}
	
	public String cargar() {
		log.info("cargar()");
		return SUCCESS;
	}
	
	public String obtenerEventosAnual() {
		log.info("obtenerEventosAnual(): " + date);
		try {
			if(getUsuarioSession() != null && date != null && date.trim().length()>0){
			
			Collection<GregorianCalendar> fechas = agendaService.obtenerEventosAnual(getUsuarioSession().getId(), Integer.parseInt(date));

				if (!fechas.isEmpty()) {
					getResponse().setContentType("text/xml");
					getResponse().setHeader("Cache-Control", "no-cache");
					PrintWriter out = getResponse().getWriter();
					out.write("<?xml version='1.0' encoding='ISO-8859-1'?>");
					out.write("<fechas>");

					for (GregorianCalendar fecha : fechas) {
						out.write("<fecha>" + Formato.setBaseDatosDeDate(fecha) + "</fecha>");
					}
					out.write("</fechas>");
					out.close();
				}
			}
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (NumberFormatException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
		return NONE;
	}
	
	public String obtenerEventos() {
		log.info("obtenerEventos(): " + date);
		try {
			if(getUsuarioSession() != null && date != null && date.trim().length()>0){
			
			Collection<Agenda> eventos = agendaService.obtenerEventos(getUsuarioSession().getId(), date);

				if (!eventos.isEmpty()) {
					getResponse().setContentType("text/xml");
					getResponse().setHeader("Cache-Control", "no-cache");
					PrintWriter out = getResponse().getWriter();
					out.write("<?xml version='1.0' encoding='ISO-8859-1'?>");
					out.write("<eventos>");

					for (Agenda evento : eventos) {
						out.write("<evento>");
						out.write("<fecha>" + Formato.setBaseDatosDeDateCompleto(evento.getFechaHora()) + "</fecha>");
						out.write("<sumilla><![CDATA[" + evento.getSumilla() + "]]></sumilla>");
						out.write("<detalle><![CDATA[" + evento.getDetalle() + "]]></detalle>");
						out.write("<notify>" + evento.getNotificado() + "</notify>");
						out.write("<minutos>" + evento.getMinutoAntes() + "</minutos>");
						out.write("</evento>");
					}
					out.write("</eventos>");
					out.close();
				}
			}
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (NumberFormatException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
		return NONE;
	}
	
	public String guardarEvento() {
		log.info("guardarEvento(): " + date);
		try {
			if(getUsuarioSession() != null && date != null && date.trim().length()>0 
					 && sumilla != null && sumilla.trim().length()>0  && detalle != null){
			
				Agenda evento = new Agenda();
				evento.setUsuario(getUsuarioSession().getId());
				evento.setFechaHora(Formato.stringToCalendar(date, Constante.FECHA_DIA_MES_ANO_HORA_MI));
				evento.setSumilla(sumilla);
				evento.setDetalle(detalle);
				evento.setNotificado(notificado);
				evento.setMinutoAntes(minutoAntes);
				
				if("0".equals(cmd)){
					agendaService.crearEvento(evento);
				}else{
					agendaService.modificarEvento(cmd, evento);
				}

				PrintWriter out = getResponse().getWriter();
				out.print("OK");
				out.close();
				
			}
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (NumberFormatException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
		return NONE;
	}
	
	public String eliminarEvento() {
		log.info("eliminarEvento(): " + date);
		try {
			if(getUsuarioSession() != null && date != null && date.trim().length()>0 ){
			
				agendaService.eliminarEvento(getUsuarioSession().getId(), date);

				PrintWriter out = getResponse().getWriter();
				out.print("OK");
				out.close();
				
			}
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (NumberFormatException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
		return NONE;
	}

}