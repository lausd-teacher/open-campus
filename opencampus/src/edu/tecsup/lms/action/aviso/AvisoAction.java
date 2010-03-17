package edu.tecsup.lms.action.aviso;

import java.io.PrintWriter;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.modelo.Aviso;
import edu.tecsup.lms.service.AvisoService;

public class AvisoAction extends BaseAction{

	private static final long serialVersionUID = -4152989311696301336L;
	
	private AvisoService avisoService;
	
	public void setAvisoService(AvisoService avisoService) {
		this.avisoService = avisoService;
	}
	
	public String cargarAviso(){
		//log.info("cargarAviso()");
		try {
			Aviso aviso = avisoService.cargarAviso();
			
			if (aviso != null) {
				getResponse().setContentType("text/xml");
				getResponse().setHeader("Cache-Control", "no-cache");
				PrintWriter out = getResponse().getWriter();
				out.write("<?xml version='1.0' encoding='ISO-8859-1'?>");
				out.write("<aviso>");
				out.write("<titular><![CDATA[" + aviso.getTitular()+ "]]></titular>");
				out.write("<tipo>" + aviso.getTipo()+ "</tipo>");
				out.write("</aviso>");
				out.close();
			}
			
		} catch (Exception e) {
			log.error(e.toString());
			return NONE;
		}
		return SUCCESS;
	}
	
}