package edu.tecsup.lms.interceptor.plataforma;

import com.opensymphony.xwork2.ActionInvocation;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.interceptor.CampusBaseInterceptor;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.util.Constante;

public class CampusEmpresaInterceptor extends CampusBaseInterceptor {

	private static final long serialVersionUID = -2262190500947971028L;

	protected String intercepcion(ActionInvocation inv, Usuario user)
			throws Exception {
		if (user.hasRol(Constante.ROL_CAMPUS_EMPRESA) || 
				user.hasRol(Constante.ROL_CAMPUS_SOPORTE) || 
				user.hasRol(Constante.ROL_CAMPUS_ADMINISTRADOR)) {
			return inv.invoke();
		}
		log.warn("intercepcion(): " + getInvocationAction(inv) + " - Usuario no es Monitor Empresa: " + user);
		return BaseAction.PORTAL;
	}

}
