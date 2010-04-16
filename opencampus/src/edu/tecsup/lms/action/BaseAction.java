package edu.tecsup.lms.action;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.dispatcher.ApplicationMap;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.util.Constante;

public abstract class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, SessionAware, ApplicationAware, Preparable{

	protected Log log = LogFactory.getLog(getClass());

	private static final long serialVersionUID = -1887347515902263223L;

	public static final String PORTAL = "portal";
	public static final String CURSO = "curso";
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected SessionMap session;
	protected ApplicationMap application;
	
	public String request_locale;
	
	public String message;

	public String getMessage() {
		String msg = (String)getSession().get("message_bkp");
		if(msg != null){ message = msg; getSession().remove("message_bkp"); }
		return message;
	}

	public void setMessage(String message) {
		getSession().put("message_bkp", message);
		this.message = message;
	}

	public String getRequest_locale() {
		return request_locale;
	}

	public void setRequest_locale(String request_locale) {
		this.request_locale = request_locale;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest(){
		return this.request;
	}
	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletResponse getResponse(){
		return this.response;
	}
	
	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = (SessionMap)session;
	}
	
	public SessionMap getSession() {
		return this.session;
	}

	@SuppressWarnings("unchecked")
	public void setApplication(Map application) {
		this.application = (ApplicationMap)application;
	}
	
	public ApplicationMap getApplication() {
		return this.application;
	}
	
	protected Usuario getUsuarioSession(){
		return (Usuario)session.get(Constante.USUARIO_ACTUAL);
	}
	
	public void setUsuarioSession(Usuario usuario) {
			getSession().put(Constante.USUARIO_ACTUAL, usuario);
	}
	
	
	public String getIdiomaSession() {
		Locale idioma = (Locale) getSession().get(Constante.IDIOMA_ATRIBUTO);
		return (idioma != null)?idioma.toString():Constante.IDIOMA_ESPANOL;
	}
	
	public void setIdiomaSession(String idioma) {
		getSession().put(Constante.IDIOMA_ATRIBUTO,new Locale(idioma));
		
	}

	public void prepare() throws Exception {
		
	}
	
	protected boolean isValid(Object param) {
		if(param != null){
			if (param instanceof Object[]) {
				Object[] values = (Object[]) param;
				if(values.length == 0)return false;
				for (int i = 0; i < values.length; i++) {
					if (!isValid(values[i]))return false;
				}
			} else if (param instanceof String) {
				if (((String) param).length() == 0)return false;
			} else if (param instanceof File) {
				if (!((File) param).exists())return false;
			}
			return true;
		}
		return false;
    }
	
}
