package directwiperemoting.listener;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.extend.RealScriptSession;
import org.directwebremoting.impl.DefaultScriptSessionManager;

import edu.tecsup.lms.util.UsuariosConectados;

public class ScriptSessionListener extends DefaultScriptSessionManager{
	
	protected static Log log = LogFactory.getLog(new ScriptSessionListener().getClass());
	
    @SuppressWarnings("unchecked")
	protected void invalidate(RealScriptSession scriptSession) {
    	synchronized (sessionLock) {
    		sessionMap.remove(scriptSession.getId());
    		for (Iterator it = pageSessionMap.values().iterator(); it.hasNext();) {
                Set pageSessions = (Set) it.next();
                pageSessions.remove(scriptSession);
            }
    		synchronized (UsuariosConectados.s) {
    			try{		    		
    				
		    		Integer usuarioTmp = null;
		    		int size = 0;
    		
    				for (Integer id : UsuariosConectados.s.keySet()) {
						Collection<ScriptSession> scriptSessions = UsuariosConectados.s.get(id);
						
						if(scriptSessions.remove(scriptSession)){
							//System.out.println("Invalidate ...."+element+"-"+(scriptSessions.size())+ "-"+scriptSession);
							usuarioTmp = id;
							size = scriptSessions.size();
							break;
						}//else
							//System.out.println("Invalidate ...."+element+"-"+(scriptSessions.size())+ "-"+scriptSession);
					}
    				
					if(size==0 && !(usuarioTmp == null)){
						/*Set<String> navs = UsuariosConectados.navegadores.get(usuarioTmp);
						
						if(navs != null){
							navs.remove(WebContextFactory.get().getSession().getId());
							UsuariosConectados.navegadores.put(usuarioTmp, navs);
				
							if (navs.size() == 0) {*/
								UsuariosConectados.cerrarSessionExterno(usuarioTmp);
							/*}
						}*/
					}
					
    			}catch (Exception e) {
					log.error(e.toString());
				}
    		}
	    
    	}
    }
}