package edu.opencampus.lms.util;

import java.io.IOException;

import javax.servlet.ServletException;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class MisDatos {
	
	public String getInclude() throws ServletException, IOException {
        WebContext wctx = WebContextFactory.get();
        return wctx.forwardToString("/comun/misdatos/modificarDatos.jsp");
    }

}
