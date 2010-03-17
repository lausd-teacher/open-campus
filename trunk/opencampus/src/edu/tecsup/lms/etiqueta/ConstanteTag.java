package edu.tecsup.lms.etiqueta;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import edu.tecsup.lms.util.Constante;

public class ConstanteTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String campo;
	
	private String var;
	
	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}
	
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			
			Object value = Constante.class.getField(campo).get(null);
			//Util.getScope(scope); request, session, application
			if(var != null)
				pageContext.setAttribute(var, value, 1);
			else
				out.print(value);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
}
