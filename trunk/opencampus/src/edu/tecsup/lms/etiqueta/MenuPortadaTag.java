package edu.tecsup.lms.etiqueta;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.util.Constante;
import edu.tecsup.lms.util.MenuOpciones;
import edu.tecsup.lms.util.MenuOpciones.Menu;

public class MenuPortadaTag extends BodyTagSupport {

	private static final long serialVersionUID = -7105640489850665594L;
	
	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}
	
	public int doAfterBody() throws JspException {
		try {
			String text = bodyContent.getString();
			Properties props = new Properties();
			
			if("en".equals(text.trim()))
				props.load(getClass().getClassLoader().getResourceAsStream("mensajes_en.properties"));
			else
				props.load(getClass().getClassLoader().getResourceAsStream("mensajes_es.properties"));
			
			int position = 0;
			//JspWriter out = pageContext.getOut();
			JspWriter out = bodyContent.getEnclosingWriter();
			Usuario usuario = ((Usuario) pageContext.getAttribute(Constante.USUARIO_ACTUAL, PageContext.SESSION_SCOPE));
			if (null == usuario) {
				return SKIP_BODY;
			}
			Map<String, Menu> menus = new HashMap<String, Menu>();
			int u = 0;
			
			/**
			 * Roles
			 */
			if (usuario.hasRol(Constante.ROL_CAMPUS_ADMINISTRADOR)) {
				for (u = 0; u < MenuOpciones.menuAdministrador.length; u++) {
					menus.put(MenuOpciones.menuAdministrador[u].getNombre(),
							MenuOpciones.menuAdministrador[u]);
				}
			}
			
			if (usuario.hasRol(Constante.ROL_CAMPUS_SOPORTE)) {
				for (u = 0; u < MenuOpciones.menuSoporte.length; u++) {
					menus.put(MenuOpciones.menuSoporte[u].getNombre(),
							MenuOpciones.menuSoporte[u]);
				}
			}
			
			if (usuario.hasRol(Constante.ROL_CAMPUS_JEFE)) {
				for (u = 0; u < MenuOpciones.menuJefe.length; u++) {
					menus.put(MenuOpciones.menuJefe[u].getNombre(),
							MenuOpciones.menuJefe[u]);
				}
			}
			
			if (usuario.hasRol(Constante.ROL_CAMPUS_MONITOR)) {
				for (u = 0; u < MenuOpciones.menuMonitor.length; u++) {
					menus.put(MenuOpciones.menuMonitor[u].getNombre(),
							MenuOpciones.menuMonitor[u]);
				}
			}
			
			if (usuario.hasRol(Constante.ROL_CAMPUS_EMPRESA)) {
				for (u = 0; u < MenuOpciones.menuEmpresa.length; u++) {
					menus.put(MenuOpciones.menuEmpresa[u].getNombre(),
							MenuOpciones.menuEmpresa[u]);
				}
			}
			
			if (usuario.hasRol(Constante.ROL_CAMPUS_DOCENTE)) {
				for (u = 0; u < MenuOpciones.menuDocente.length; u++) {
					menus.put(MenuOpciones.menuDocente[u].getNombre(),
							MenuOpciones.menuDocente[u]);
				}
			}
			
			if (usuario.hasRol(Constante.ROL_CAMPUS_USUARIO)) {
				for (u = 0; u < MenuOpciones.menuUsuario.length; u++) {
					menus.put(MenuOpciones.menuUsuario[u].getNombre(),
							MenuOpciones.menuUsuario[u]);
				}
			}
			
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			StringBuffer contexto = new StringBuffer(request.getContextPath());

			out.println("<table cellspacing=\"3\" border=\"0\"><td width=\"5\" align=\"center\">&nbsp;</td>");
			
			//Opciones Default Inicio: menuGlobalInicio
			for (position = 0; position < MenuOpciones.menuGlobalInicio.length; position++) {
				if (position != 0) {
					out.println("<td width=\"10\" align=\"center\">|</td>");
				}
				
				out.println("<td align=\"center\">" +
						"<a href=\""+ contexto+ MenuOpciones.menuGlobalInicio[position].getRutaWeb()
						+ "\" class=\"menu portal_menu_selecionando\">"
						+ props.getProperty(MenuOpciones.menuGlobalInicio[position].getNombre())
						+ "</a></td>");
			}
			
			//opciones por Roles: menuOpciones
			if (0 != menus.size()) {
				if (MenuOpciones.NUMERO_OPCIONES < menus.size()) {
					
					out.println("<td width=\"10\" align=\"center\">|</td><td align=\"center\">" +
							"<a href=\"" + contexto+ MenuOpciones.menuOpciones.getRutaWeb()
							+ "\" class=\"menu portal_menu_selecionando\">"
							+ props.getProperty(MenuOpciones.menuOpciones.getNombre())
							+ "</a></td>");
					
				} else {
					
					for (Menu me : menus.values()) {
						out.println("<td width=\"10\" align=\"center\">|</td><td  align=\"center\">" +
								"<a href=\""+ contexto + me.getRutaWeb()
								+ "\" class=\"menu portal_menu_selecionando\">" 
								+ props.getProperty(me.getNombre()) 
								+ "</a></td>");
					}
					
				}
			}
			
			//Opciones Default Fin: menuGlobalFin
			for (position = 0; position < MenuOpciones.menuGlobalFin.length; position++) {
				
				out.println("<td width=\"10\" align=\"center\">|</td><td align=\"center\">" +
						"<a href=\"" + contexto+ MenuOpciones.menuGlobalFin[position].getRutaWeb()
						+ "\" class=\"menu portal_menu_selecionando\">"
						+ props.getProperty(MenuOpciones.menuGlobalFin[position].getNombre())
						+ "</a></td>");
					
			}
			
			out.println("</table>");

		} catch (Exception e) {
			throw new JspException("MenuPortadaTag: doStartTag(): "
					+ e.toString());
		}
		return SKIP_BODY;
	}
}