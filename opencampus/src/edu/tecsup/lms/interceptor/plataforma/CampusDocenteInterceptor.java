package edu.tecsup.lms.interceptor.plataforma;

import com.opensymphony.xwork2.ActionInvocation;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.interceptor.CampusBaseInterceptor;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.util.Constante;

public class CampusDocenteInterceptor extends CampusBaseInterceptor {

	private static final long serialVersionUID = 6442980203823805562L;

	protected String intercepcion(ActionInvocation inv, Usuario user)
			throws Exception {
		if (user.hasRol(Constante.ROL_CAMPUS_DOCENTE)
				|| user.hasRol(Constante.ROL_CAMPUS_SOPORTE)
				|| user.hasRol(Constante.ROL_CAMPUS_ADMINISTRADOR)) {
			return inv.invoke();
		}
		log.warn("intercepcion(): " + getInvocationAction(inv) + " - Usuario no es Secretaria Docente: " + user);
		return BaseAction.PORTAL;
	}

}
