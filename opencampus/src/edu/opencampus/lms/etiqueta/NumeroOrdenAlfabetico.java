package edu.opencampus.lms.etiqueta;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import edu.opencampus.lms.util.Formato;

public class NumeroOrdenAlfabetico extends TagSupport {
	
	private static final long serialVersionUID = 4075271393030125145L;
	
	String texto;
	double numero;
	
	public double getNumero() {
		return numero;
	}

	public void setNumero(double numero) {
		this.numero = numero;
	}

	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			String resultado = "";
			if(texto!=null)
				resultado = Formato.numeroOrdenAlfabetico(texto);
			else
				resultado = numero+10+"";
			out.print("<!--"+resultado+"-->");			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}