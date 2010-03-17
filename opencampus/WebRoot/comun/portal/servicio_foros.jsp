<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/plain;charset=ISO-8859-1"); //Para no tener problemas con ñs y tildes
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}' />

<table cellpadding="0" cellspacing="0" width="100%" class="tabla_sin_layout" border="0">
	
	<c:forEach items="${foros}" var="foro" varStatus="fila">
	
		<tr class="portal_selecionando" style="cursor:pointer;" 
			onclick="abrir_servicio_foros('<c:out value='${contextPath}'/>/foro/IngresarForo.action?idForo=<c:out value='${foro.idForo}'/>')">
			
			<td align="center" valign="middle" width="26" style="background-color: #E0EAF3; padding: 2px;">
				<img src="<c:out value='${contextPath}'/>/img/foro/iconos_foros/foro_img_<c:out value='${foro.icono}'/>.png" width="24"/>
			</td>
			<td>
				<table cellpadding="3" cellspacing="0" width="100%" class="tabla_sin_layout" border="0">
					<tr>
						<td style="white-space: nowrap; background-color: #E0EAF3;">
							<strong><c:out value='${foro.titulo}'/></strong>
						</td>
						<td width="62" style="white-space: nowrap; background-color: #E0EAF3;">
							<strong style="color: rgb(68, 101, 155);white-space: nowrap;"><s:text name="portal.foros.contenido.temas"/>: <c:out value='${foro.totalTemas}'/></strong>
						</td>
					</tr>
					<tr>
						<td style="white-space: nowrap;" colspan="2">
							<s:text name="portal.foros.contenido.participaciones"/>: <strong><c:out value='${foro.estado}'/></strong> 
							<s:text name="portal.foros.contenido.conector"/> <c:out value='${foro.totalMensajes}'/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	
	</c:forEach>
	<c:if test="${fn:length(foros)==0}">
	<tr>
		<td style="color:gray;font-size:8pt;" height="22" >
			<span style="padding : 7px;">
				<s:text name="portal.foros.contenido.vacio"/>
			</span>
		</td>
	</tr>	
</c:if>
</table>
<span id="servicio_foros_descripcion_origen" style="display:none;">
	<strong><span class="text_rojo"><c:out value="${fn:length(foros)}"/></span> <s:text name="portal.foros.detalle"/></strong>
</span>