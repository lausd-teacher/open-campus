<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="edu.opencampus.lms.util.Constante"%>
<%@page import="java.util.Properties"%>
<%@page import="java.util.GregorianCalendar"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	Properties prop = new Properties();
	try {
	  prop.load(application.getResource("/WEB-INF/classes/mensajes_es.properties").openStream());
	} catch(Exception e) {}
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title><%=prop.getProperty("titulo.campus.virtual") %></title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />		
	</head>
	<body>
		<div id="container">
		
			<div id="top_menu">
				<table width="100%" border="0" cellpadding="2" cellspacing="0">
					<tr>
						<td width="20" align="center">
							<a href="<%=request.getContextPath()%>/Portal.action"><img src="<%=request.getContextPath()%>/img/icon_config.gif" 
								alt="<%=prop.getProperty("portal.menu.inicio") %>" border="0"/></a>
						</td>
						<td width="80">
							<a href="<%=request.getContextPath()%>/Portal.action"><span><%=prop.getProperty("titulo.campus.virtual_corto") %></span></a>
						</td>
						<td></td>
					</tr>
				</table>
			</div>
			<div id="banner"></div>
			
			<div id="body">
			
				<table width="100%" border="0" cellpadding="3" cellspacing="0" class="open_table" height="400">
					<caption>Informaci&oacute;n del sistema</caption>
					<tbody>
						<tr>
							<td class="error_jsp">
								<%
									String param = request.getParameter("id");
									if("404".equals(param)){
								%>
										<h1>ERROR 404</h1><hr/><h2>P&aacute;gina no econtrada.</h2>
										<center><img src="<%=request.getContextPath()%>/img/vista.png" alt="ERROR 404"></center>
								<%
									}else if("403".equals(param)){
								%>
										<h1>ERROR 403</h1><hr/><h2>P&aacute;gina protegida.</h2>
										<center><img src="<%=request.getContextPath()%>/img/padlock.png" alt="ERROR 403"></center>
								<%
									}else if("500".equals(param)){
								%>
										<h1>ERROR 500</h1><hr/><h2>Error interno del sistema.</h2>
										<center><img src="<%=request.getContextPath()%>/img/error.png" alt="ERROR 500"></center>
								<%
									}else{
								%>
										<h1>ERROR DESCONOCIDO</h1><hr/><h2>Error Realmente desconocido.</h2>
								<%
									}
								%>
								<font size="3">&lsaquo;&lsaquo;</font> <a href="javascript:history.back(1)">Regresar</a>
							</td>
						</tr>
					</tbody>
				</table>
						
			</div>
			<div id="pie">
				<div>
					Copyright &copy; <%=new GregorianCalendar().get(GregorianCalendar.YEAR) %> <a href="#"><%=prop.getProperty("titulo.campus.virtual") %></a><br/>
					<%=prop.getProperty("portal.pie.derechos") %>
				</div>
				<div class="right">
					<a href="#"><%=Constante.DIRECCION_CORREO_SALIENTE %></a><br/>
					<%=prop.getProperty("portal.pie.requerimientos") %>
				</div>
			</div>

		</div>
	</body>
</html>
