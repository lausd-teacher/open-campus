<%@ page import="java.util.*"  %>
<%@ page import="java.io.*"  %>
<%

response.setHeader("Content-type","image/jpeg"); 
%>

<%
			int data;
			String codigo = request.getParameter("iduser");

			String source = "/usr/local/apache2/htdocs/webcampus/fotos/"+codigo+".jpg";
			
			File f = new File(source);
			if(!f.exists()){
			source = "/home/webcampus/apache-tomcat-6.0.16/webapps/campus/img/foto.gif";
			}		
			 


            ServletOutputStream stream = null;
            BufferedInputStream buffer = null;

            try{

                    buffer = new BufferedInputStream(new FileInputStream(source));
                    stream = response.getOutputStream();
                    while((data = buffer.read())!=-1)  stream.write(data);

                    buffer.close();
                    stream.close();
            } catch (Exception e) {            	
            	out.println(e.getMessage());
            }                    
%>                    