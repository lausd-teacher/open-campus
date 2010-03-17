<%
	response.setContentType("text/plain;charset=ISO-8859-1"); //Para no tener problemas con ñs y tildes
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<table width="100%" cellpadding="2" cellspacing="0" style="table-layout: fixed;">

	<c:forEach var="anotacion" items="${anotaciones}">
		<tr>
			<td width="20" height="18" style="padding-bottom:2px;" align="center">
				<img src="<%=request.getContextPath()%>/img/mas_portal.gif"/>
			</td>
			<td align="left" style="white-space: nowrap; overflow: hidden;">
				<c:out value="${anotacion.titulo}" default="Sin titulo"></c:out> - <c:out value="${anotacion.contenido}" default="Sin comentario"></c:out>
			</td>
			<td width="2">
				&nbsp;
			</td>
		</tr> 
	</c:forEach>
	
	<c:if test="${fn:length(anotaciones)==0}">
	 	<tr>
	 		<td style="color:gray;font-size:8pt;" height="22">
				<span style="padding : 7px;"> <s:text name="portal.apuntes.contenido.vacio"/></span>
			</td>
		</tr> 
	</c:if>
</table>
<span id="servicio_apuntes_descripcion_origen" style="display:none;"></span>