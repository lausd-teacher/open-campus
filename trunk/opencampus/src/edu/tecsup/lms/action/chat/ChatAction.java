package edu.tecsup.lms.action.chat;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.util.Constante;
import edu.tecsup.lms.util.UsuariosConectados;

public class ChatAction extends BaseAction {

	private static final long serialVersionUID = 8943228775461009505L;
	
	public String cargar() {
		log.info("cargar()");

		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String cargarUsuariosOffLine() {
		try {
		
			Collection<Usuario> allUsers = ((Map<String,Usuario>)getApplication().get(Constante.USUARIOS_ACTUAL)).values();
			Collection<Usuario> usuarios = new ArrayList<Usuario>(allUsers);
			usuarios.removeAll(UsuariosConectados.c.values());
			
			getResponse().setContentType("text/xml");
			getResponse().setHeader("Cache-Control", "no-cache");
			PrintWriter out = getResponse().getWriter();
			out.write("<?xml version='1.0' encoding='ISO-8859-1'?>");
			out.write("<usuarios>");
			
			for (Usuario u: usuarios) {
				out.write("<usuario>");

				out.write("<id>" + u.getId()+ "</id>");
				out.write("<nombre>" + u.getNombreCorto()+ "</nombre>");
				out.write("<nombrecomp>" + u.getNombreCompleto()+ "</nombrecomp>");
				out.write("<fecha>" + u.getIngreso().getFechaIngresoAsString()+ "</fecha>");
				out.write("<rol>" + u.getRolPredeterminado().getNombre()+ "</rol>");
				
				out.write("</usuario>");
			}
			out.write("</usuarios>");
			out.close();
			
		} catch (NumberFormatException e) {
			log.info(e);
		} catch (Exception e) {
			log.info(e);
		}
		
		return NONE;
	}
	
}