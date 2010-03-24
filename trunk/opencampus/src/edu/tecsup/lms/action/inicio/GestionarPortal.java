package edu.tecsup.lms.action.inicio;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.portal.Servicio;
import edu.tecsup.lms.service.PortalService;

public class GestionarPortal extends BaseAction {

	private static final long serialVersionUID = 7995015653587998501L;

	private Collection<Servicio> servicios;

	private PortalService portalService;

	private String servicio;

	private String estado;
	
	

	private String[] principal_col_0;

	private String[] principal_col_1;

	private String[] principal_col_2;

	private String[] principal_col_3;

	private Integer accion;

	public void setAccion(Integer accion) {
		this.accion = accion;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setPrincipal_col_0(String[] principal_col_0) {
		this.principal_col_0 = principal_col_0;
	}

	public void setPrincipal_col_1(String[] principal_col_1) {
		this.principal_col_1 = principal_col_1;
	}

	public void setPrincipal_col_2(String[] principal_col_2) {
		this.principal_col_2 = principal_col_2;
	}

	public void setPrincipal_col_3(String[] principal_col_3) {
		this.principal_col_3 = principal_col_3;
	}

	public Collection<Servicio> getServicios() {
		return servicios;
	}

	public void setPortalService(PortalService portalService) {
		this.portalService = portalService;
	}

	public String obtenerServicios() {
		log.info("obtenerServicios()");
		try {
			//servicios = portalService.obtenerServicioGestionar();
		} catch (Exception e) {
			log.info(e.toString());
		}
		return SUCCESS;
	}

	public String grabarPortal() {
		log.info("grabarPortal()");
		if (null == principal_col_0) {
			principal_col_0 = new String[0];
		}
		if (null == principal_col_1) {
			principal_col_1 = new String[0];
		}
		if (null == principal_col_2) {
			principal_col_2 = new String[0];
		}
		if (null == principal_col_3) {
			principal_col_3 = new String[0];
		}
		Collection<String[]> col_final = new ArrayList<String[]>();
		col_final.add(principal_col_0);
		col_final.add(principal_col_1);
		col_final.add(principal_col_2);
		col_final.add(principal_col_3);
		try {
			portalService.guardarGestionar(col_final);
		} catch (ServiceException e) {
			log.error(e.toString());
		}
		return NONE;
	}

	public String grabarPortalAccion() {
		log.info("grabarPortalAccion()");
		PrintWriter out;
		try {
			out = getResponse().getWriter();
			int valor = 0;
			try {
				switch (accion) {
				case 1:
					valor = portalService.guardarPortalVisibleGestionar(
							servicio, estado);
					break;
				case 2:
					valor = portalService.guardarPortalEliminadoGestionar(
							servicio, estado);
					break;
				case 3:
					valor = portalService.guardarPortalEstadoGestionar(
							servicio, estado);
					break;
				case 4:
					valor = portalService.guardarPortalComentarioGestionar(
							servicio, estado);
					break;
				case 5:
					valor = portalService.guardarPortalDescripcionGestionar(
							servicio, estado);
					break;
				case 6:
					valor = portalService.guardarPortalIngresarGestionar(
							servicio, estado);
					break;
				case 7:
					valor = portalService
							.guardarPortalUsuarioEliminarGestionar(servicio,
									estado);
					break;
				case 8:
					valor = portalService
							.guardarPortalUsuarioMinimizarGestionar(servicio,
									estado);
					break;
				}

				out.print(valor);
			} catch (NumberFormatException e) {
				out.print(valor);
			} catch (ServiceException e) {
				out.print(valor);
			}
			out.close();
		} catch (IOException e) {
		}
		return NONE;
	}

}
