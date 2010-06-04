<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isErrorPage="true" %>
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
		<script type="text/javascript">
			Event.observe(window, 'load', function() {
				mensajeSoporte('Reporte de error en sistema',$('error_action').innerHTML)
			});
		</script>	
	</head>
	<body>
		<div id="container">
		
			<s:include value="/comun/bienvenida.jsp"/>
			
			<div id="body">
			
				<table width="100%" border="0" cellpadding="3" cellspacing="0" class="open_table">
					<caption><s:text name="action.error.title"></s:text></caption>
					<thead>
						<tr>
							<td><s:text name="action.error.info"></s:text><jsp:useBean id="now" class="java.util.Date" /></td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td id="error_action">
                              	Fecha: <fmt:formatDate  value="${now}" type="both" pattern="EEEE, dd 'de' MMMM 'de' yyyy, HH:mm" /><br/>
                              	
                              	Usuario: <c:out value="${usuario_actual}" default="No ha iniciado sesión"></c:out><br/>
                              	<br/>
                              	<div class="actionErrorCSS">
	                              	<span><a onclick="if($(errormsg).visible()){$(errormsg).hide()}else{$(errormsg).show()}">Detalles del problema:</a></span> 
	                              	<div id="errormsg">
		                              	<c:out value="${message}" default="Se ha producido una excepción no clasificada en el servidor."/>
		                              	<s:actionerror/>  <s:actionmessage/> <s:fielderror />
		                              	<s:if test="%{exception != null}">
		                              		<br/>ActionException: <s:property value="%{exception}"/>
		                              	</s:if>
		                              	<%if(exception != null){ %>
		                              		<!-- s:property value="%{exceptionStack}"/-->
		                              		<br/>JSPException: <%=exception %>
		                              	<%} %>
	                              	</div>
	                              </div>
							</td>
						</tr>
						<tr>
							<td><button onclick="history.back(1);">&lsaquo;&lsaquo; Regresar</button></td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td><s:text name="action.error.info2"/></td>
						</tr>
					</tfoot>
				</table>
					
			</div>
			
			<s:include value="/comun/pie.jsp"/>
			
		</div>
	</body>
</html>
