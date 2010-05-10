package edu.opencampus.lms.service;

import java.util.Collection;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.dao.AgendaDAO;
import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.Agenda;

public class AgendaService {

	Log log = LogFactory.getLog(getClass());
	
	private AgendaDAO agendaDAO;

	public void setAgendaDAO(AgendaDAO agendaDAO) {
		this.agendaDAO = agendaDAO;
	}
	
	public Collection<String> cargarPortada(int usuario) throws ServiceException{
		Collection<String> fechas;
		try{
			fechas = agendaDAO.cargarPortada(usuario);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return fechas;
	}
	
	public Collection<GregorianCalendar> obtenerEventosAnual(int usuario, int year) throws ServiceException{
		Collection<GregorianCalendar> fechas;
		try{
			fechas = agendaDAO.obtenerEventosAnual(usuario, year);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return fechas;
	}
	
	public Collection<Agenda> obtenerEventos(int usuario, String date) throws ServiceException{
		Collection<Agenda> eventos;
		try{
			eventos = agendaDAO.obtenerEventos(usuario, date);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return eventos;
	}
	
	public void crearEvento(Agenda evento) throws ServiceException{
		try{
			agendaDAO.crearEvento(evento);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public void modificarEvento(String oldDate, Agenda evento) throws ServiceException{
		try{
			agendaDAO.modificarEvento(oldDate, evento);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public void eliminarEvento(int usuario, String date) throws ServiceException{
		try{
			agendaDAO.eliminarEvento(usuario, date);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
}
