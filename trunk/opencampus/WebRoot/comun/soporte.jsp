<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="edu.opencampus.lms.util.Constante"%>
<%@ page import="edu.opencampus.lms.modelo.Usuario"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	Usuario usuario = (Usuario) session
			.getAttribute(Constante.USUARIO_ACTUAL);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" /></title>
		<style>
#ampliacion {
	border-right: #666666 1px solid;
	padding-right: 2px;
	border-top: #666666 1px solid;
	background-color: #ffffff;
	padding-left: 2px;
	visibility: hidden;
	padding-bottom: 2px;
	border-left: #666666 1px solid;
	padding-top: 2px;
	border-bottom: #666666 1px solid;
	background-repeat: no-repeat;
	position: absolute;
	width: 130px;
	height: 54px;
	top: 0px;
	left: 0px;
}

#cerrarampliacion {
	background-color: #333333;
	font-family: arial, verdana;
	font-size: 8pt;
	line-height: 20px;
	text-align: right;
	float: right;
	height: 20px;
	width: 124px;
	padding-right: 5px;
}
</style>

		<s:include value="/comun/jslibs.jsp"/>
		
		<script>	   
			var imagesDir = "<%=request.getContextPath()%>/js/openwysiwyg/icons/";
			var cssDir = "<%=request.getContextPath()%>/js/openwysiwyg/styles/";
			var popupsDir = "<%=request.getContextPath()%>/js/openwysiwyg/popups/";
		</script>

		<script language="JavaScript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/openwysiwyg/wysiwyg.js"></script>
	</head>
	<body>
		<div id="container">
		
			<s:include value="/comun/bienvenida.jsp"/>
			
			<div id="body">
			
			
<form name="form1" method="post" enctype="multipart/form-data"
						action="<%=request.getContextPath()%>/comun/buzon/EnviarMensajeSoporte.action"
						onsubmit="if(!xGetElementById('wysiwygcontenido').contentWindow.document.body.innerHTML.hasContenido()){ alert('Ingrese su consulta.'); return false;}else{return true;}">
<input type="hidden" name="destino" id="destino" value="soporte_campus"  style="visibility: hidden"/>

						<table width="600" cellpadding="3" cellspacing="0" class="open_table nogrid " align="center">
							<caption><s:text name="portal.menu.soporte"/></caption>
							<tbody>
							<tr>
								<td height="18" colspan="6" align="center" class="moduloAbajo1" >
								<s:text name="portal.soporte.mensaje"></s:text><br/>
									<c:if test="${aviso != null}">
										<strong><fmt:message key="${aviso}"/></strong>
									</c:if>
								</td>
							</tr>


							<tr class="fon_color01">
							
								<td>
									&nbsp;&nbsp;<s:text name="portal.soporte.asunto"></s:text>
								</td>
									
								<td colspan="5">
									<input name="titulo" type="text" id="titulo" size="58"
										maxlength="100" value="<c:out value="${titulo}"/>">
								</td>
							</tr>

							
							<tr class="fon_color01">
								<td>
									&nbsp;&nbsp;<s:text name="portal.soporte.adjunto"></s:text>
								</td>
								<td colspan="5">
									<div style="float: left; padding-right: 5px;">
										<input name="doc" type="file" id="doc" size="30">
									</div>
									<div
										style="float: left; vertical-align: middle; padding-top: 5px;">
										<s:text name="portal.soporte.maximo"></s:text> 5 MiB.
									</div>
								</td>

							</tr>
							<tr class="fon_color01">
								<td colspan="6" height="220">
									<textarea name="contenido" id="contenido" type="_moz">
       									<c:out value="${contenido}" default="" />
        							</textarea>
									<script language="javascript1.2">			
										generate_wysiwyg('contenido', '588', '150');			
									</script>
								</td>
							</tr>
							<tr class="fon_color01">
								<td colspan="6" align="right" height="25" valign="top">
									<input class="form_button" type="submit"
													id="enviar" value="<s:text name="portal.soporte.enviar"></s:text>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							</tbody>
						</table>

					</form>
					
					
			</div>
			
			<s:include value="/comun/pie.jsp"/>
			
		</div>			
	</body>
</html>
