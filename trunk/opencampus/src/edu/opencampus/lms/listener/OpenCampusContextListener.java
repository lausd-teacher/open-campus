package edu.opencampus.lms.listener;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.util.Constante;

public class OpenCampusContextListener implements ServletContextListener, Serializable {

	private static final long serialVersionUID = -7823108631958692061L;
	
	protected Log log = LogFactory.getLog(getClass());
	
	private ServletContext context;
	
	public void contextInitialized(ServletContextEvent sce) {
		try {
			log.info("|-------- " + Constante.PROYECTO + " start --------|");

			context = sce.getServletContext();
			
			//Inicializando constantes hacia aplication context
			inicializarConstantes();
			

		} catch (Exception e) {
			log.error("Error al inicializar el contexto: " + e.toString());
		}
	}
	
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			log.info("|-------- " + Constante.PROYECTO + " start --------|");

		} catch (Exception e) {
			log.error("Error al destruir el contexto: " + e.toString());
		}
	}
	  
	public void inicializarConstantes() {
		log.info("Inicializando constantes...");
		try{
			
			Field[] fields = Constante.class.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				
				int modifier = fields[i].getModifiers();
				if (Modifier.isFinal(modifier) && !Modifier.isPrivate(modifier)){

					try {
						//log.info("Setting constant " + fields[i].getName() + " to ServletContext");
						context.setAttribute(fields[i].getName(), fields[i].get(this));
					}
					catch (IllegalAccessException e) {
					}
					
				}
				
			}
			
		}catch (Exception e) {
			log.error("Error al inicializarConstantes: " +e.toString());
		}
	}
	
}
