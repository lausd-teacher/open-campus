package edu.tecsup.lms.interceptor.plataforma;

import com.opensymphony.xwork2.ActionInvocation;

import edu.tecsup.lms.interceptor.CampusBaseInterceptor;
import edu.tecsup.lms.modelo.Usuario;

public class CampusUsuarioInterceptor extends CampusBaseInterceptor {

	private static final long serialVersionUID = -1784848443022588244L;

	public String intercepcion(ActionInvocation arg0, Usuario user)
			throws Exception {
		return arg0.invoke();
	}

}
