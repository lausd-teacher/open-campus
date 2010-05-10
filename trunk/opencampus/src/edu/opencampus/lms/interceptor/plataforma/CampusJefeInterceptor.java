package edu.opencampus.lms.interceptor.plataforma;

import com.opensymphony.xwork2.ActionInvocation;

import edu.opencampus.lms.action.BaseAction;
import edu.opencampus.lms.interceptor.CampusBaseInterceptor;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.util.Constante;

public class CampusJefeInterceptor extends CampusBaseInterceptor {

	private static final long serialVersionUID = 4838984941008692070L;

	protected String intercepcion(ActionInvocation inv, Usuario user)
			throws Exception {
		if (user.hasRol(Constante.ROL_CAMPUS_JEFE)
				|| user.hasRol(Constante.ROL_CAMPUS_SOPORTE)
				|| user.hasRol(Constante.ROL_CAMPUS_ADMINISTRADOR)) {
			return inv.invoke();
		}
		log.warn("intercepcion: " + getInvocationAction(inv) + " - Usuario no es Jefe de Departamento: " + user);
		return BaseAction.PORTAL;
	}

}
