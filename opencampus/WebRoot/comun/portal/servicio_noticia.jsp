<%
	response.setContentType("text/plain;charset=ISO-8859-1"); //Para no tener problemas con ñs y tildes
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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