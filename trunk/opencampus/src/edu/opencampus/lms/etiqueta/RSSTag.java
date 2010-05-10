package edu.opencampus.lms.etiqueta;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gnu.stealthp.rsslib.RSSChannel;
import org.gnu.stealthp.rsslib.RSSException;
import org.gnu.stealthp.rsslib.RSSHandler;
import org.gnu.stealthp.rsslib.RSSItem;
import org.gnu.stealthp.rsslib.RSSParser;

public class RSSTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String url;
	private int cantidad;

	protected Log log = LogFactory.getLog(getClass());
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		log.info("doStartTag(): RSS -> "+url);
		JspWriter out = pageContext.getOut();
		try {
			if(url.startsWith("http://")){
				
	    		RSSChannel ch = (RSSChannel)pageContext.getServletContext().getAttribute("RSS_COMERCIO_"+url);
	    		Date date = (Date)pageContext.getServletContext().getAttribute("RSS_COMERCIO_DATE_"+url);
	    		
	    		if(ch == null || (date != null && date.getTime()/43200000 < (new Date()).getTime()/43200000)){
	    			log.info("Cache expired -> reload "+url+" ...");
	    			RSSHandler hand = new RSSHandler();
					
					try{
						URL u = new URL(url);
			            RSSParser.parseXmlFile(u, hand, false);
					} catch (MalformedURLException e) {
		    			log.error(e);
		    		} catch (RSSException e) {
		    			log.error(e);
		    		} 
	    			
		            ch = hand.getRSSChannel(); //ChannelInfo
		            pageContext.getServletContext().setAttribute("RSS_COMERCIO_"+url, ch);
	    		}
	            
	            //String titleRSS = ch.getTitle();
	            Collection<RSSItem> items = ch.getItems();
	            
	            //int iSize = items.size();
	            int iSize = 10;
	            
	            log.info("Cantidad de Noticias: " + iSize);
	            
	            
	            int i = 0;
	            for (RSSItem item : items) {
	            	if(i == this.cantidad){
	            		
	            		break;
	            	}
	            	i++;	            	
	            	if(i%2 == 0)
	            		out.println("<td width=\"2%\" class=\"borde_separador_vert\">&nbsp;</td>");
	            	
	            	out.println("<td width=\"49%\" valign=\"top\" style=\"cursor: pointer;\" " +
	            			"onclick=\"verNoticiaRSS('" + item.getLink() + "')\" " +
	            			"onmouseover=\"seleccionNoticia(this,true)\" onmouseout=\"seleccionNoticia(this,false)\">");
	        	
	            	out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">" +
	            					"<tr>" +
	            						"<td class=\"tituloPortada\">" + item.getTitle() +
	            					"</td></tr>" +
	            					"<tr>" +
	            						"<td class=\"textoNoticia\" valign=\"top\">" + item.getDescription() +
	        						"</td></tr>" +
	        					"</table>");
	        				
	            	out.println("</td>");
	            	
	            	if(i%2 == 0 && iSize != i)
	            		out.println("</tr><tr><td width=\"49%\" class=\"borde_separador_hori\">&nbsp;</td><td width=\"2%\" height=\"10\" class=\"borde_separador_cruce\">&nbsp;</td><td width=\"49%\" class=\"borde_separador_hori\">&nbsp;</td> </tr> <tr>");
	            	
				}
	            
	            if(iSize%2 != 0)
	            	out.println("<td width=\"2%\" class=\"borde_separador_vert\"> &nbsp; </td> <td width=\"49%\"> &nbsp; </td>");
	            
			}else{
				log.error("Protocolo incorrecto.");
			}
		}catch (Exception e) {
			log.error(e);
		}
		return SKIP_BODY;
	}

}
