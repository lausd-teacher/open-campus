package edu.opencampus.lms.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.dao.IngresoDAO;
import edu.opencampus.lms.modelo.usuario.Ingreso;

public class IngresoService {

	Log log = LogFactory.getLog(getClass());

	private IngresoDAO ingresoDAO;

	public void setIngresoDAO(IngresoDAO ingresoDAO) {
		this.ingresoDAO = ingresoDAO;
	}

	public Ingreso inscribirIngreso(Ingreso ingreso) {
		return ingresoDAO.inscribirIngreso(ingreso);
	}

	public void inscribirSalida(Ingreso ingreso) {
		ingresoDAO.inscribirSalida(ingreso);
	}

}
