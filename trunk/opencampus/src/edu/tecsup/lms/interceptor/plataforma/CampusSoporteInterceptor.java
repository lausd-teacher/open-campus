package edu.tecsup.lms.interceptor.plataforma;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionInvocation;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.interceptor.CampusBaseInterceptor;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.util.Constante;

public class CampusSoporteInterceptor extends CampusBaseInterceptor {

	private static final long serialVersionUID = 4838984941008692070L;

	private Log log = LogFactory.getLog(getClass());

	protected String intercepcion(ActionInvocation inv, Usuario user)
			throws Exception {
		if (user.hasRol(Constante.ROL_CAMPUS_SOPORTE)
				|| user.hasRol(Constante.ROL_CAMPUS_ADMINISTRADOR)) {
			return inv.invoke();
		}
		log.warn("intercepcion(): " + getInvocationAction(inv) + " - Usuario no es Soporte: " + user);
		return BaseAction.PORTAL;
	}

}
