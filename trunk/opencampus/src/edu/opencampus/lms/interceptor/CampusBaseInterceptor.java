package edu.opencampus.lms.interceptor;

import java.io.File;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.Interceptor;

import edu.opencampus.lms.action.BaseAction;
import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.util.Constante;

public abstract class CampusBaseInterceptor implements Interceptor {

	private static final long serialVersionUID = -1784848443022588244L;

	protected Log log = LogFactory.getLog(getClass());
	
	public void destroy() {
		log.info("CampusBaseInterceptor: destroy()");
	}

	public void init() {
		log.info("CampusBaseInterceptor: init()");
	}

	public String intercept(ActionInvocation inv) throws Exception {
		Usuario user = (Usuario) ActionContext.getContext().getSession().get(Constante.USUARIO_ACTUAL);
		MDC.put("IP", ServletActionContext.getRequest().getRemoteAddr());
		if (null == user) {
			log.warn("intercept: " + getInvocationAction(inv) + " - Usuario no logeado");
			return BaseAction.LOGIN;
		} else {
			//Logs de seguimiento minucioso a las peticiones del cliente
//			if(!"CargarConectadosEnChat".equals(inv.getInvocationContext().getName())) //*Solo para los que enpiecen con Periodical 
//				log.info("intercept(): " + getInvocationAction(inv) + " - user: "+user);
			
			//Si ocurrio un error en los interceptors anteriores reenviar a error_action.jsp
			ValidationAware validation = (ValidationAware)inv.getAction();
		    if(validation.hasErrors()){
		    	log.error("Se reportaron errores por parte de los interceptors, ha sido reenviado a error_action.jsp ");
		        return BaseAction.ERROR;
		    }
			return intercepcion(inv, user);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected String getInvocationAction(ActionInvocation inv) {
		return "Class = "+inv.getProxy().getAction().getClass()+":"+inv.getProxy().getMethod()+"() " +
				"- Action = "+inv.getProxy().getNamespace()+"/"+inv.getProxy().getActionName()+getParametersToString(inv.getInvocationContext().getParameters());
	}
	
	private String getParametersToString(Map<String,Object> params){
		StringBuffer paramString = new StringBuffer("?");
		try{
			for (String param : params.keySet()) {
				
				paramString.append(param+"=");
				
				if(params.get(param) instanceof String[]){
					String[] value = (String[])params.get(param);
					for (int i = 0; i < value.length; i++) {
						paramString.append("["+((value[i].length()>32)?value[i].substring(0,32):value[i])+"]");
					}
				}else if(params.get(param) instanceof File[]){
					File[] value = (File[])params.get(param);
					for (int i = 0; i < value.length; i++) {
						paramString.append("["+value[i]+" : "+value[i].length()+"bytes]");
					}
				}else{
					Object[] value = (Object[])params.get(param);
					for (int i = 0; i < value.length; i++) {
						paramString.append("["+value[i].toString()+"]");
					}
				}
				paramString.append("&");
			}
			paramString.deleteCharAt(paramString.length()-1);
		}catch (Exception e) {
			log.error(e);
		}
		return paramString.toString();
	}

	protected abstract String intercepcion(ActionInvocation inv, Usuario user)
			throws Exception;

}
