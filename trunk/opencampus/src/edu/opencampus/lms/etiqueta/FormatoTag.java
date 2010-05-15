package edu.opencampus.lms.etiqueta;

import java.util.GregorianCalendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;

public class FormatoTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private GregorianCalendar fecha;

	private boolean completo;
	
	public boolean isCompleto() {
		return completo;
	}

	public void setCompleto(boolean completo) {
		this.completo = completo;
	}

	public GregorianCalendar getFecha() {
		return fecha;
	}

	public void setFecha(GregorianCalendar fecha) {
		this.fecha = fecha;
	}

	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			String fechaToString = "";
			if(completo){
				fechaToString = Formato.calendarToString(fecha, Constante.FECHA_DIA_MES_ANO_HORA_MI);
			}else{
				fechaToString = Formato.calendarToString(fecha, Constante.FECHA_DIA_MES_ANO);
			}
			
			out.print((fechaToString!=null)?fechaToString:"");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

}
