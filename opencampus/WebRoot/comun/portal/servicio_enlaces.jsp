<%
	response.setContentType("text/plain;charset=ISO-8859-1"); //Para no tener problemas con ñs y tildes
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}' />

<div style="overflow-x: hidden;overflow-y: auto; max-height: 200px;">
	<s:include value="../links.jsp" />
</div>
<span id="servicio_enlaces_descripcion_origen" style="display:none;"></span>