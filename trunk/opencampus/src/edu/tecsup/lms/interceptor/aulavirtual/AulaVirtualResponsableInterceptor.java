package edu.tecsup.lms.interceptor.aulavirtual;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.interceptor.CampusBaseInterceptor;
import edu.tecsup.lms.modelo.AulaVirtual;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.util.Constante;

public class AulaVirtualResponsableInterceptor extends CampusBaseInterceptor {

	private static final long serialVersionUID = 4838984941008692070L;

	protected String intercepcion(ActionInvocation inv, Usuario user)
			throws Exception {
		if (user.getRol().containsKey(Constante.ROL_CAMPUS_ADMINISTRADOR)) {
			return inv.invoke();
		}
		AulaVirtual aula = (AulaVirtual) ((Usuario)ActionContext.getContext().getSession().get(Constante.USUARIO_ACTUAL)).getAulaActual();
		if (aula != null && (aula.getMatriculaActual().getRol().getIdrol() == Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE)) {
			return inv.invoke();
		}
		log.warn("intercepcion(): " + getInvocationAction(inv) + " - No es Responsable: "+ user);
		return BaseAction.PORTAL;
	}
}
