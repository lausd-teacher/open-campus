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

<c:when test="${servicio == 'servicio_chat'}">
	<!-- CHAT -->
	<div style="overflow-x: hidden;overflow-y: auto; max-height: 90px;">

		<table cellpadding="2" cellspacing="0" width="100%" class="tabla_sin_layout" border="0">
			<c:forEach var="conectado" items="${conectados}">
			<tr class="portal_selecionando" 
				style="cursor:pointer; <c:if test="${conectado.value.rolPredeterminado.idrol != 145}">background-color: #F0F8E5;</c:if> <c:if test="${conectado.value.id == '100524'}">background-color: #AEDFFF;</c:if>"
				onclick="enviarCorreo('<c:out value="${conectado.value.usuario}"/>','[Recado]')"
				onmouseout="ocultarToolTip()" onmouseover="verToolTip('Rol: <c:out value="${conectado.value.rolPredeterminado.nombre}"/>', this);">
				<td  height="18" width="20" style="color: #44659B;padding-bottom:1px;" align="center" valign="midle">
					<img src="<c:out value='${contextPath}'/>/img/mas_portal.gif"/>
				</td>
				<td align="left" valign="midle">
					<c:out value="${conectado.value.nombreCompleto}"/>
				</td>
				<td width="20" align="center" valign="midle">
					<img src="<c:out value='${contextPath}'/>/img/icon_mail_send.gif" border="0">
				</td>
				<% if(request.getHeader("user-agent").indexOf("MSIE") != -1){%>
					<td width="10">&nbsp;</td>
				<%} %>
			</tr>
			</c:forEach>	
			<c:if test="${fn:length(conectados)==0}">
				<tr>
					<td style="color:gray;font-size:8pt;" height="22" >
						<span style="padding : 7px;">
							<s:text name="portal.chat.contenido.vacio"/>
						</span>
					</td>
				</tr>
			</c:if>
		</table>
	</div>
	<span id="servicio_chat_descripcion_origen" style="display:none;">
		<strong><span class="text_rojo"><c:out value="${fn:length(conectados)}" /></span> <s:text name="portal.chat.detalle"/></strong>
	</span>
</c:when>

<c:when test="${servicio == 'servicio_noticia'}">
	<!-- NOTICIAS -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed;">
	
		<c:forEach items="${noticias}" var="noticia" varStatus="fila">
			
			<tr style="background-color: #E0EAF3;">
				<td align="left" width="40%" style="padding:3px;">
					<strong><c:out value="${noticia.imagen}"/></strong>
				</td>
				<td align="right" style="padding:3px;">
					<strong style="color: rgb(68, 101, 155);"><f:DateToString fecha="${noticia.fecha}"/></strong>
				</td>
			</tr>
			<tr>
				<td valign="top" colspan="2">

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="tituloNoticia" align="left">
								<c:out value="${noticia.titular}" />
							</td>
						</tr>
						<tr>
							<td  class="textoNoticia" valign="top"  style="padding-bottom:5px;">
								<c:out value="${noticia.cuerpo}" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan=2 />
			</tr>
		</c:forEach>

	</table>
	<span id="servicio_noticia_descripcion_origen" style="display:none;"></span>
</c:when>

<c:when test="${servicio == 'servicio_noticia'}">
	<!-- CURSOS -->
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
</c:when>

<c:when test="${servicio == 'servicio_buzon'}">
	<!-- MENSAJES-->
	<table width="100%" cellpadding="2" cellspacing="0" style="table-layout: fixed;">
		<tr>
			<td colspan="3">
				<table cellpadding="1" cellspacing="0" width="100%" class="tabla_sin_layout" border="0">
				<c:forEach var="mensaje" items="${mensajes}">
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
				<c:if test="${fn:length(mensajes)==0}">
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
		<strong><span class="text_rojo"><c:out value="${fn:length(mensajes)}" /></span> <s:text name="portal.buzon.detalle"/></strong>
	</span>
</c:when>

<c:when test="${servicio == 'servicio_apuntes'}">
	<!-- MENSAJES-->
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
</c:when>

</c:choose>