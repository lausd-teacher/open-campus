<%
	response.setContentType("text/plain;charset=ISO-8859-1"); //Para no tener problemas con ñs y tildes
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}' />
<!-- //http://blog.tremend.ro/2006/08/07/accessing-java-constants-from-jstl/ -->
<c:choose>
<c:when test="${servicio == 'servicio_curso'}">
	<!-- CURSOS -->
	<table cellpadding="2" cellspacing="0" width="100%" class="tabla_sin_layout" border="0">
	<c:forEach var="ficha" items="${cursos}">	
		<tr onclick="cambiarPagina('<c:out value='${contextPath}'/>/aulavirtual/AulaVirtual.action?id=<c:out value="${ficha.idFicha}"/>');" class="portal_selecionando" style="cursor: pointer;<c:if test="${ficha.matriculaActual.rol.idRol == 2}"> background-color: #E0F8E5;</c:if>">
	
			<td height="20" width="20" valign="middle" align="center">
				<img src="<c:out value='${contextPath}'/>/img/mas_portal.gif" />
			</td>
			<td valign="middle" align="left" >
				<label style="cursor: pointer;"><c:out value="${ficha.curso.nombre}"/></label>	
				<!-- BUSCAR EL TOOLTIP QUE ACONSEJASTE AL ALUMNO DE DAVID -->					
			</td>
			<td width="35" align="right" valign="middle">
				<c:if test="${0<trabajoIndividual}">
					<strong style="color: red;"><c:out value="${trabajoIndividual}"/>&nbsp;</strong>
					<img alt="Pendiente" src="<c:out value='${contextPath}'/>/img/flag.gif"  width="8"
					height="11" />
				</c:if>
			</td>
		</tr>
	</c:forEach>
	<c:if test="${fn:length(cursos)==0}">
		<tr>
			<td style="color:gray;font-size:8pt;" height="22" >
				<span style="padding : 7px;">
					<s:text name="portal.curso.contenido.vacio"/>
				</span>
			</td>
		</tr>	
	</c:if>
	</table>
	
	<span id="servicio_curso_descripcion_origen" style="display:none;">
		<strong><span class="text_rojo"><c:out value="${fn:length(cursos)}"/></span> <s:text name="portal.curso.detalle"/></strong>
	</span>
	
</c:when>

</c:choose>