package edu.tecsup.lms.filtro;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.RequestUtils;
import org.apache.struts2.dispatcher.FilterDispatcher;

import edu.tecsup.lms.util.Constante;

public class FiltroCampus extends FilterDispatcher {

	private static Log log = LogFactory.getLog(FiltroCampus.class.getClass());

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;

		super.doFilter(arg0, arg1, arg2);

		final String recurso = RequestUtils.getServletPath(request);
		if (null != recurso
				&& recurso.endsWith(".jsp")
				&& (recurso.startsWith("/admin/")
						|| recurso.startsWith("/jefe/")
						|| recurso.startsWith("/estudiante/")
						|| recurso.startsWith("/comun/aulavirtual") || recurso
						.startsWith("/docente/"))) {
			if (null == request.getSession()) {
				try {
					log.error("VIOLATED -" + request.getRemoteAddr()
							+ "- (IP) session nula(" + recurso + ");");
					response.sendRedirect(request.getContextPath()
							+ "/Inicio.action");
				} catch (Exception e) {
				}
				return;
			} else {
				Object ob = request.getSession().getAttribute(
						Constante.USUARIO_ACTUAL);
				if (null == ob) {
					log.error("VIOLATED -" + request.getRemoteAddr()
							+ "- (IP) sin logearse(" + recurso + ");");
					response.sendRedirect(request.getContextPath()
							+ "/Inicio.action");
					return;
				}
			}
		}
	}
}
