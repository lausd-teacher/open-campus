package edu.opencampus.lms.interceptor.aulavirtual;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

import edu.opencampus.lms.action.BaseAction;
import edu.opencampus.lms.interceptor.CampusBaseInterceptor;
import edu.opencampus.lms.modelo.AulaVirtual;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.util.Constante;

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
