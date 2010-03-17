package edu.tecsup.lms.action.anotacion;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.Anotacion;
import edu.tecsup.lms.service.AnotacionService;

public class AnotacionAction extends BaseAction {

	private static final long serialVersionUID = -4773144837728700744L;

	private AnotacionService anotacionService;

	private String contenido;

	private String titulo;

	private List<Anotacion> anotaciones;

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Anotacion> getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(List<Anotacion> anotaciones) {
		this.anotaciones = anotaciones;
	}

	public void setAnotacionService(AnotacionService anotacionService) {
		this.anotacionService = anotacionService;
	}

	public String cargarPortada() {
		log.info("cargarPortada()");
		try {
			anotaciones = anotacionService.cargarPortada(getUsuarioSession().getId());
		} catch (Exception e) {
			log.error(e.toString());
			return NONE;
		}
		return SUCCESS;
	}

	public String inicio() {
		log.info("inicio()");
		try {
			anotaciones = anotacionService.obtener(getUsuarioSession().getId());
		} catch (ServiceException se) {
			log.error(se.toString());
		}
		return SUCCESS;
	}

	public String insertar() {
		log.info("insertar()");
		Anotacion anotacion = new Anotacion();
		anotacion.setTitulo(titulo);
		anotacion.setUsuario(getUsuarioSession().getId());
		Integer rpt = 0;
		try {
			rpt = anotacionService.insertar(anotacion);
			PrintWriter pw = getResponse().getWriter();
			pw.write(rpt.toString());
			pw.flush();
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
		return NONE;
	}

	public String actualizar() {
		log.info("actualizar()");
		Anotacion anotacion = new Anotacion();
		anotacion.setId(id);
		anotacion.setUsuario(getUsuarioSession().getId());
		try {
			anotacion = anotacionService.obtener(anotacion);
			if (null != contenido) {
				anotacion.setContenido(contenido);
			}
			if (null != titulo) {
				anotacion.setTitulo(titulo);
			}
			anotacion.setFecha_modificacion(new Date());
			PrintWriter pw = getResponse().getWriter();
			int rpt = 1;
			try {
				rpt = anotacionService.actualizar(anotacion);
				pw.write(rpt + "");
			} catch (Exception e) {
				log.error(e.toString());
				pw.write("0");
			}
			pw.flush();
		} catch (ServiceException se) {
			log.error(se.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
		return NONE;
	}

	public String eliminar() {
		log.info("eliminar()");
		Anotacion anotacion = new Anotacion();
		anotacion.setId(id);
		anotacion.setUsuario(getUsuarioSession().getId());
		int rpt = 0;
		try {
			rpt = anotacionService.eliminar(anotacion);
			PrintWriter pw = getResponse().getWriter();
			pw.write(rpt + "");
			pw.flush();
		} catch (ServiceException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
		return NONE;
	}	

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}