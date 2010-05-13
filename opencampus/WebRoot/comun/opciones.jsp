<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="/error_action.jsp"%>
<%@ page import="edu.opencampus.lms.util.MenuOpciones.Menu"%>
<%@ page import="java.util.Map"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="contextPath" value='${pageContext.request.contextPath}' />

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:include value="/comun/jslibs.jsp"/>
					
	</head>
	<body>
		<div id="container">
		
			<s:include value="/comun/bienvenida.jsp"/>
			
			<div id="body">
				
					<table width="100%" cellpadding="3" cellspacing="0" class="open_table nogrid with_hover" style="display: none">
						<caption>Ejemplo de Tabla con opencampus</caption>
						<thead>
							<tr><td>ID</td><td>NAME</td><th>email</th></tr>
						</thead>
						<tbody>
							<tr><td>1</td><td>ercik</td><td><a href="#">567457</a></td></tr>
							<tr><td>2</td><td>sdasd</td><td><a href="#">235325</a></td></tr>
							<tr><td>3</td><td>adasd</td><td><a href="#">245325</a></td></tr>
						</tbody>
						<tfoot>
							<tr><td>AAAA</td><td>demoidemo frswe</td><td><input type="button" value="Click Me"> </td></tr>
						</tfoot>
					</table>
									
					<%
						Map<String, Map<String, Menu>> menus = (Map<String, Map<String, Menu>>) request
								.getAttribute("opciones");
					%>
				
					<table width="100%" cellpadding="3" cellspacing="0" class="open_table nogrid">
						<caption><s:text name="portal.menu.opciones" /></caption>
						<tbody>
						<tr>
							
						<%
						for (String nombre : menus.keySet()) {
						%>
											<td valign="top">
												<table width="100%" cellpadding="3" cellspacing="0" class="open_table">
													<caption><fmt:message key="<%=nombre%>"/></caption>
													<%
									for (Menu menu : menus.get(nombre).values()) {
						%>

													<tr>
														<td>
															<a href="<%=request.getContextPath()+ menu.getRutaWeb()%>"><fmt:message key="<%=menu.getNombre()%>"/></a>
														</td>
													</tr>
													<%
								}%>
												</table>
											</td>


											<%
							}
						%>

						</tr>
						</tbody>
					</table>
		
		
		
			</div>
			
			<s:include value="/comun/pie.jsp"/>
			
		</div>
	</body>
</html>
