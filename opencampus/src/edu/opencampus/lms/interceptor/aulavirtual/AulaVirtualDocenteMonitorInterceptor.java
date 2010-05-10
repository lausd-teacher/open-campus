package edu.opencampus.lms.interceptor.aulavirtual;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

import edu.opencampus.lms.action.BaseAction;
import edu.opencampus.lms.interceptor.CampusBaseInterceptor;
import edu.opencampus.lms.modelo.AulaVirtual;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.util.Constante;

public class AulaVirtualDocenteMonitorInterceptor extends CampusBaseInterceptor {

	private static final long serialVersionUID = 4838984941008692070L;

	protected String intercepcion(ActionInvocation inv, Usuario user)
			throws Exception {
		AulaVirtual aula = (AulaVirtual) ActionContext.getContext()
				.getSession().get(Constante.AULA_ACTUAL);
		if (aula != null && (aula.getRol().getIdrol() == Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE
				|| aula.getRol().getIdrol() == Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE
				|| aula.getRol().getIdrol() == Constante.ROL_CAMPUS_AULAVIRTUAL_MONITOR_DOCENTE)) {
			return inv.invoke();
		}
		log.warn("intercepcion(): " + getInvocationAction(inv) + " - Usuario no es Docente Monitor: "+user);
		return BaseAction.PORTAL;
	}
}
