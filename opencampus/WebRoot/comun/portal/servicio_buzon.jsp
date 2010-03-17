<%
	response.setContentType("text/plain;charset=ISO-8859-1"); //Para no tener problemas con ñs y tildes
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="f" uri="/WEB-INF/tags/FormatoTags.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table width="100%" cellpadding="2" cellspacing="0" style="table-layout: fixed;">
	<tr>
		<td colspan="3">
			<table cellpadding="1" cellspacing="0" width="100%" class="tabla_sin_layout" border="0">
			<c:forEach var="mensaje" items="${MENSAJES}">
				<tr onmouseout="ocultarToolTip()" 
					onmouseover="verToolTip('Enviado por <b><c:out value='${mensaje.usuario}' /></b> el <f:DateToString fecha="${mensaje.fecha_envio}" />', this);">
					<td  height="20" width="20" style="color: #44659B;padding-bottom:2px;" align="center" valign="middle">
						<img src="<%=request.getContextPath()%>/img/mas_portal.gif" />
					</td>
					<td valign="middle" align="left">
						<c:out value="${mensaje.titulo}"></c:out>
					</td>
					<td width="10" align="center" valign="middle">
						<img src="<%=request.getContextPath()%>/img/flag.gif" width="8" height="11" alt="Sin leer"/>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${fn:length(MENSAJES)==0}">
				<tr>
					<td style="color:gray;font-size:8pt;" height="22" >
						<span style="padding : 7px;">
							<s:text name="portal.buzon.contenido.vacio"/>
						</span>
					</td>
				</tr>
			</c:if>
			</table>
		</td>
	</tr>
</table>
<span id="servicio_buzon_descripcion_origen" style="display:none;">
	<strong><span class="text_rojo"><c:out value="${fn:length(MENSAJES)}" /></span> <s:text name="portal.buzon.detalle"/></strong>
</span>