package edu.opencampus.lms.etiqueta;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import edu.opencampus.lms.modelo.Jerarquia;

public class JerarquiaRutaCompletaTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String aguja;

	private String nombre;

	public String getAguja() {
		return aguja;
	}

	public void setAguja(String aguja) {
		this.aguja = aguja;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			Collection<Jerarquia> j = (Collection<Jerarquia>) request.getAttribute("jerarquias");
			if (j != null) {
				out.print("<select name=\"" + nombre + "\" id=\"" + nombre + "\" >");
				out.print("<option value=\"0\">/");
				
				for (Jerarquia jr : j) {
					if (aguja != null && aguja.equals(String.valueOf(jr.getIdJerarquia()))) {
						out.print("<option value=\"" + jr.getIdJerarquia() + "\" selected>" + jr.getRutaCompleta());
					} else {
						out.print("<option value=\"" + jr.getIdJerarquia() + "\">" + jr.getRutaCompleta());
					}
				}
				
				out.print("</select>");
			} else {
				out.print("No se obtuvieron las \"jerarquias\" del ambito Request!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

}
