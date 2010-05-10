package edu.opencampus.lms.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import edu.opencampus.lms.modelo.Usuario;

public class UsuariosConectados {

	protected static Log log = LogFactory.getLog(new UsuariosConectados()
			.getClass());

	/**
	 * Usuarios conectados
	 */
	public static Map<Integer, Usuario> c = Collections
			.synchronizedMap(new HashMap<Integer, Usuario>());

	/**
	 * ScriptSessions por usuario
	 */
	public static Map<Integer, Collection<ScriptSession>> s = Collections
			.synchronizedMap(new HashMap<Integer, Collection<ScriptSession>>());

	/**
	 * Navegadores por usuario conectado
	 */
	public static Map<Integer, Set<String>> navegadores = Collections
			.synchronizedMap(new HashMap<Integer, Set<String>>());

	/**
	 * Id's de usuarios rindiento Test
	 */
	public static Collection<Integer> noActivos = new ArrayList<Integer>();

	/**
	 * esto solo te lista los usuarios conectados pero no crea un script session (no mostrara en caliente nuevos usuarios)
	 * a menos que crees un arreglo para scriptsessions de conectados y otra de chateadores,
	 * asi mandas alertas a conectasdos y  chateadores pero solo envias mensajes a chateadores.
	 */
//	public void getConectados2() {
//		
//		WebContext wctx = WebContextFactory.get();
//		UtilChat miSession = new UtilChat(wctx.getScriptSession());
//		Usuario yo = null;
//
//		try {
//			yo = (Usuario) wctx.getSession().getAttribute(
//					Constante.USUARIO_ACTUAL);
//			
//			synchronized (c) {
//				for (Iterator<Usuario> iterator = c.values().iterator(); iterator
//						.hasNext();) {
//					Usuario ux = (Usuario) iterator.next();
//					miSession.smartClone("modelo", ux.getIdUsuario(),
//							"rol_", Util
//									.getNombreRol(ux.getRolPredeterminado()),
//							"hora_", ux.getIngreso().getFechaIngreso(),
//							"full_", ux.getNombreCompleto());
//					miSession.setStyle("modelo" + ux.getIdUsuario(), "display",
//							"block");
//				}
//			}
//
//			//miSession.setValue("online", c.size() - 1 + "");
//			/*capricho de sako, yo no me veo*/
//			miSession.removeNode("modelo" + yo.getIdUsuario());
//
//			/*if (c.size() > 1)
//				miSession.setValue("s", "usuarios en línea");
//			else
//				miSession.setValue("s", "usuario en línea");
//			*/
//			miSession.setStyle("modelo", "display", "none");
//			miSession.removeNode("loading");
//			//wctx.getScriptSession().invalidate();
//
//		} catch (Exception e) {
//			log.error(e.toString());
//		}
//	}
	/**
	 * 
	 * Al usuario nuevo lo agrega a la lista de conectados y le crea su lista de scriptSessions, luego 
	 * le lista todos los usuarios conectados a ese script sesion del usuario (wctx.getScriptSession());
	 * 
	 */
	public void getConectados() {
		WebContext wctx = WebContextFactory.get();
		UtilChat miSession = new UtilChat(wctx.getScriptSession());
		Usuario yo = null;

		try {
			yo = (Usuario) wctx.getSession().getAttribute(Constante.USUARIO_ACTUAL);

			Collection<ScriptSession> sessions = s.get(yo.getId());

			if (sessions == null) {
				sessions = new ArrayList<ScriptSession>();
			}

			sessions.add(wctx.getScriptSession());

			s.put(yo.getId(), sessions);
			c.put(yo.getId(), yo);

			if (noActivos.contains(yo.getId())) {
				miSession.setStyle("reja", "display", "block");
			}

			synchronized (c) {
				for (Iterator<Usuario> iterator = c.values().iterator(); iterator.hasNext();) {
					Usuario ux = (Usuario) iterator.next();
					miSession.smartClone("modelo", ux.getId()+"", "name_",
							ux.getNombreCorto(),
							"rol_", (ux.getRolPredeterminado()!=null)?ux.getRolPredeterminado().getNombre():"",
							"hora_", Formato.calendarToString(ux.getIngreso().getFechaIngreso(),Constante.FECHA_DIA_MES_ANO_HORA_MI_SEG),
							"full_", ux.getNombreCompleto());
					miSession.setStyle("modelo" + ux.getId(), "display",
							"block");
				}
			}

			miSession.setValue("online", c.size() - 1 + "");
			/*capricho de sako, yo no me veo*/
			miSession.removeNode("modelo" + yo.getId());

			if (c.size() > 1)
				miSession.setValue("s", "usuarios en línea");
			else
				miSession.setValue("s", "usuario en línea");

			miSession.setStyle("modelo", "display", "none");
			miSession.removeNode("loading");

			//Limpiando a los usuarios muertos (temporal hasta solucionar la vaina del cerrar sesion)
			for (Integer key : s.keySet()) {
				Collection<ScriptSession> ss = s.get(key);
				if(ss.isEmpty()){
					cerrarSessionExterno(key);
					log.warn("Usuario muerto ha sido fumigado: "+key);
				}
			}


		} catch (Exception e) {
			log.error(e.toString());
		}
	}

	/**
	 * Esto se ejecuta primero al entrar al chat, envia un alert y muestra el modelo a todos(wctx.getAllScriptSessions()) 
	 * menos a si mismo, if (!c.containsKey(yo.getIdUsuario())): siempre y cuando no haya estado conectado antes.
	 * No agrega al usuario a la lista de conectados aun ni crea su script sesion, eso lo hace get Conectados.
	 */
	@SuppressWarnings("unchecked")
	public void nuevoConectado() {
		WebContext wctx = WebContextFactory.get();
		Usuario yo = null;
		
		try {
			yo = (Usuario) wctx.getSession().getAttribute(Constante.USUARIO_ACTUAL);
			Set<String> navs = navegadores.get(yo.getId());

			if (noActivos.contains(yo.getId())) {
				if(s.get(yo.getId()) != null){
					UtilChat misSession = new UtilChat(s.get(yo.getId()));
					misSession.setStyle("reja", "display", "block");
				}
				
			}

			if (navs == null) {
				navs = new HashSet<String>();
			}

			navs.add(wctx.getSession().getId());
			navegadores.put(yo.getId(), navs);

			if (!c.containsKey(yo.getId())) {
				c.put(yo.getId(), yo);

				Collection<ScriptSession> losConectados = wctx.getAllScriptSessions();
				/*capricho de sako, yo no me veo*/
				losConectados.remove(wctx.getScriptSession());

				UtilChat todos = new UtilChat(losConectados);

				/*aparece mi nombre en los demas navegadores*/
				todos.smartClone("modelo", yo.getId()+"", 
						"name_",yo.getNombreCorto(), 
						"rol_",(yo.getRolPredeterminado()!=null)?yo.getRolPredeterminado().getNombre():"", 
						"hora_",Formato.calendarToString(yo.getIngreso().getFechaIngreso(),Constante.FECHA_DIA_MES_ANO_HORA_MI_SEG), 
						"full_", yo.getNombreCompleto());
				todos.setStyle("modelo" + yo.getId(), "display", "block");
				todos.setStyle("modelo", "display", "none");

				todos.setValue("online", c.size() - 1 + "");

				if (c.size() > 1)
					todos.setValue("s", "usuarios en línea");
				else
					todos.setValue("s", "usuario en línea");

				todos.setValue("newOnline", yo.getNombreCorto() + " ha iniciado sesión.");
				todos.desaparecemelo(5000);
				todos.setStyle("avisito", "visibility", "visible");
			}

		} catch (Exception e) {
			log.error(e.toString());
		}
	}

	/**
	 * 
	 * @param text
	 * @param ella
	 */
	@SuppressWarnings("unchecked")
	public void sendMessage(String text, Integer ella) {
		WebContext wctx = WebContextFactory.get();
		Usuario io = null;

		if (text.length() > 255) {
			text = text.substring(0, 255);
		}

		try {
			io = (Usuario) wctx.getSession().getAttribute(Constante.USUARIO_ACTUAL);
			
			if(io != null){
				UtilChat suSession = new UtilChat(s.get(ella));
	
				if (text.startsWith("alert-->")) {
					suSession.alert(text.substring(8));
				} else if (text.startsWith("alert--->")) {
					UtilChat todos = new UtilChat(wctx.getAllScriptSessions());
					todos.alert(text.substring(9));
				} else if (text.startsWith("zumbido-->")) {
					suSession.zumbido();
					suSession.existe(io.getId()+"");
					String random = "-" + Math.random() * Integer.MAX_VALUE + "";
					suSession.cloneNode("mensaje_" + io.getId(), "", random);
					suSession.setStyle("mensaje_" + io.getId() + random,"color", "#0099FF");
					
					suSession.setTitle(Formato.formatoTexto(io.getNombreCorto())+ " dice: Responde!!!");
					suSession.seeMe(io.getId()+"");
					suSession.setStyle("modelo_chat_" + io.getId(),"visibility", "visible");
					suSession.setValue("lastMessage_"+io.getId(), Formato.formatoTexto(io.getNombreCorto()) + " ha enviado un zumbido.");	
				} else if (text.startsWith("zumbido--->")) {
					UtilChat todos = new UtilChat(wctx.getAllScriptSessions());
					todos.zumbido();
				} else if (noActivos.contains(io.getId())) {
					String random = "-" + Math.random() * Integer.MAX_VALUE;
					UtilChat u = new UtilChat(wctx.getScriptSession());
					u.setStyle("mensaje_" + ella, "color", "gray");
					u.cloneNvalue("mensaje_" + ella, random,
							"No puede utilizar el chat mientras rinde un Test.");
					u.scrollAtBottom("chat_" + ella);
				} else if (noActivos.contains(ella)) {
					String random = "-" + Math.random() * Integer.MAX_VALUE;
					UtilChat u = new UtilChat(wctx.getScriptSession());
					u.setStyle("mensaje_" + ella, "color", "gray");
					u.cloneNvalue("mensaje_" + ella, random,
									"El usuario se encuentra rindiendo un Test, su mensaje no ha sido recibido.");
					u.scrollAtBottom("chat_" + ella);
				} else {
					suSession.existe(io.getId()+"");
					String random = "-" + Math.random() * Integer.MAX_VALUE + "";
					suSession.cloneNode("mensaje_" + io.getId(), "", random);
					suSession.setStyle("mensaje_" + io.getId() + random,"color", "#0099FF");
					suSession.setValue("mensaje_" + io.getId() + random,
							Formato.formatoTexto(io.getNombreCorto())+ ": " + text);
					suSession.scrollAtBottom("chat_" + io.getId());
					String shorty = (text.length() > 20) ? text.substring(0, 20)+ "..." : text;
					suSession.setTitle(Formato.formatoTexto(io.getNombreCorto())+ " dice: " + shorty);
					suSession.seeMe(io.getId()+"");
					suSession.setStyle("modelo_chat_" + io.getId(),"visibility", "visible");
					suSession.alert();
					suSession.setValue("lastMessage_"+io.getId(), "Último mensaje recibido a: " + Formato.getHora(new GregorianCalendar()));				
				}
			}else{
				log.info("Usuario no logueado");
				String random = "-" + Math.random() * Integer.MAX_VALUE;
				UtilChat u = new UtilChat(wctx.getScriptSession());
				u.setStyle("mensaje_" + ella, "color", "gray");
				u.cloneNvalue("mensaje_" + ella, random, "Ud. ha finalizado sessión.");
				u.scrollAtBottom("chat_" + ella);
				u.cierrame();
			}
		} catch (Exception e) {
			String random = "-" + Math.random() * Integer.MAX_VALUE;
			UtilChat u = new UtilChat(wctx.getScriptSession());
			u.setStyle("mensaje_" + ella, "color", "gray");
			u.cloneNvalue("mensaje_" + ella, random, "Su mensaje no ha sido enviado, reinicie su ventana de chat.");
			u.scrollAtBottom("chat_" + ella);
			log.error("El mensaje no ha sido enviado ("+e+")");
		}
	}

	/**
	 * Cierrra la propia sesión
	 */
	public static void cerrarSession() {
		WebContext wctx = WebContextFactory.get();

		try {
			Usuario yo = (Usuario) wctx.getSession().getAttribute(Constante.USUARIO_ACTUAL);
			
			if(yo != null){
				//Eliminando el scriptSession actual (pagina actual)
				Collection<ScriptSession> scriptSessions = UsuariosConectados.s.get(yo.getId());
				
				if(scriptSessions != null){
					
					scriptSessions.remove(wctx.getScriptSession());
					System.out.print(yo.getId()+"- 1 un script session eliminado, quedan: ");
					System.out.println(scriptSessions.size());
					if(scriptSessions.size()==0){
						System.out.println(yo.getId()+"- 2 Usuario no tien script session");
						//recupero la lista de navegadores que tiene el usuario (varias sesiones)
						/*Set<String> navs = navegadores.get(yo.getIdUsuario());
						
						//Si tiene algun navegador
						if(navs != null){
							navs.remove(wctx.getSession().getId());
							navegadores.put(yo.getIdo(), navs);
							System.out.println(yo.getId()+"- 3 un navegador eliminado");
							if (navs.size() == 0) {
								System.out.println(yo.getId()+"- 4 Usuario no tiene ningun browser");*/
								cerrarSessionExterno(yo.getId());
							/*}
						}*/
					}
				}
				
			}
			//wctx.getScriptSession().invalidate();
		} catch (Exception e) {
			log.error(e.toString());
		}
	}

	/**
	 * Cierrra la sesión de cualquier usuario, recibe <code>idUsuario<code> 
	 * @param idUsuario
	 */
	@SuppressWarnings("unchecked")
	public static void cerrarSessionExterno(Integer idUsuario) {
		WebContext wctx = WebContextFactory.get();

		UtilChat todos = new UtilChat(wctx.getAllScriptSessions());

		try {
			System.out.println(idUsuario+"- 3 refrescando en demas users");
			todos.refreshNumeroOnline(String.valueOf(idUsuario));
			todos.noSeeMe(String.valueOf(idUsuario));
			todos.removeNode("modelo" + idUsuario);
			
			System.out.println(idUsuario+"- 4 Eliminando user en c,s y navegadores");
			c.remove(idUsuario);
			navegadores.remove(idUsuario);
			s.remove(idUsuario);
			//noActivos.remove(idUsuario); //no borrar debe estar hasta q finalice el test y no el chat
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * 
	 * @param usuario
	 */
	public static void desactivarChat(Integer idUsuario) {
		log.info("Usuario desactivado: "+idUsuario);
		try{
			
			if (!noActivos.contains(idUsuario)) {
				noActivos.add(idUsuario);
			}
			
			if(s.get(idUsuario) != null){
				UtilChat misSession = new UtilChat(s.get(idUsuario));
				misSession.setStyle("reja", "display", "block");
			}
		
		}catch(Exception e){
			log.error(e.toString());
		}
	}

	/**
	 * 
	 * @param usuario
	 */
	public static void activarChat(Integer idUsuario) {
		log.info("Usuario activado: "+idUsuario);
		try{
		
			noActivos.remove(idUsuario);
		
			if(s.get(idUsuario) != null){
				UtilChat misSession = new UtilChat(s.get(idUsuario));
				misSession.setStyle("reja", "display", "none");
			}
		
		}catch(Exception e){
			log.error(e.toString());
		}
	}

	/**
	 * 
	 * @param usuario
	 * @param remitente
	 */
	public static void nuevoMensajeBuzon(Integer idUsuario, String remitente) {		
		if (c.containsKey(idUsuario)) {
			UtilChat u = new UtilChat(s.get(idUsuario));
			u.setValue("newOnline",
					"Ha recibido un nuevo mensaje de correo de " + remitente);
			u.desaparecemelo(60000);
			u.alert();
			u.setStyle("avisito", "visibility", "visible");
		}

	}
}