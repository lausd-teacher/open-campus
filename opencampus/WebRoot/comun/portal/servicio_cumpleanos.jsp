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

<div style="overflow-x: hidden; overflow-y: auto; max-height: 90px;">

	<table cellpadding="2" cellspacing="0" width="100%" class="tabla_sin_layout" border="0">
		<c:forEach var="cumple" items="${usuarios}">
		<tr class="portal_selecionando" style="cursor:pointer; <c:if test="${cumple.rolPredeterminado.idrol != 0 && cumple.rolPredeterminado.idrol != 7}">background-color: #E0F8E5;</c:if>"
			onclick="enviarCorreo('<c:out value="${cumple.usuario}"/>','Feliz Cumpleaños!')"
			onmouseout="ocultarToolTip()" onmouseover="verToolTip('Rol: <c:out value="${cumple.rolPredeterminado.nombre}"/>', this);">
			<td  height="18" width="20" style="color: #44659B;padding-bottom:1px;" align="center" valign="middle">
				<img src="<c:out value='${contextPath}'/>/img/mas_portal.gif"/>
			</td>
			<td align="left" valign="middle">
				<c:out value="${cumple.nombreCompleto}"/>
			</td>
			<td width="20" align="center" valign="middle">
				<a href="javascript:void('<c:out value="${cumple.persona.fecnacimientoAsString}"/>')">
				<img src="<c:out value='${contextPath}'/>/img/icon_mail_send.gif" border="0">
				</a>
			</td>
			<% if(request.getHeader("user-agent").indexOf("MSIE") != -1){%>
				<td width="10">&nbsp;</td>
			<%} %>
		</tr>
		</c:forEach>	
		<c:if test="${fn:length(usuarios)==0}">
			<tr>
				<td style="color:gray;font-size:8pt;" height="22" >
					<span style="padding : 7px;">
						<s:text name="portal.cumpleanos.contenido.vacio"/>
					</span>
				</td>
			</tr>
		</c:if>
	</table>
</div>
<span id="servicio_cumpleanos_descripcion_origen" style="display:none;">
	<strong><span class="text_rojo"><c:out value="${fn:length(usuarios)}" /></span> <s:text name="portal.cumpleanos.detalle"/></strong>
</span>