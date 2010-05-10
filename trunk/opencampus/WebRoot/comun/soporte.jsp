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

		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/js/menu/menu.css"
			rel="stylesheet" type="text/css" />

		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/menu/menu.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jPrototype.js"></script>

		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>
		<script type='text/javascript'
			src='<%=request.getContextPath()%>/dwr/engine.js'> </script>
		<script type='text/javascript'
			src='<%=request.getContextPath()%>/dwr/util.js'> </script>
		<script>	   
			var imagesDir = "<%=request.getContextPath()%>/js/openwysiwyg/icons/";
			var cssDir = "<%=request.getContextPath()%>/js/openwysiwyg/styles/";
			var popupsDir = "<%=request.getContextPath()%>/js/openwysiwyg/popups/";
			function enviar(){
				var titulo=xGetElementById("titulo").value;
				var contenido=xGetElementById("wysiwygcontenido").body.innerHTML;
				alert(contenido);
				/*var ajax = nuevoAjax();
				var url = xGetContextPath() + "/comun/buzon/EnviarMensajeSoporte.action";
				ajax.open("POST", url, true);
				ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				ajax.send("titulo=" + );
				ajax.onreadystatechange = function () {
					if (ajax.readyState === 4) {
						noLeidos();
					}
				};	*/
			}
		</script>

		<script language="JavaScript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/openwysiwyg/wysiwyg.js"></script>
	</head>
	<body>
	
		
		<div id="contenedor">
			
			<s:include value="/comun/bienvenida.jsp"></s:include>
			
			<div id="cuerpo">			
				<div id="principal" style="padding-bottom: 10px;">
<form name="form1" method="post" enctype="multipart/form-data"
						action="<%=request.getContextPath()%>/comun/buzon/EnviarMensajeSoporte.action"
						onsubmit="if(!xGetElementById('wysiwygcontenido').contentWindow.document.body.innerHTML.hasContenido()){ alert('Ingrese su consulta.'); return false;}else{return true;}">
<input type="hidden" name="destino" id="destino" value="soporte_campus"  style="visibility: hidden"/>

					
					
						<table width="600" border="0" cellpadding="3" cellspacing="0" class="tabla01" >

	
							
							<tr class="fon_tit_curso">
								<td height="20" align="left" colspan="6"
									style="color: #FFFFFF; font-size: 11px; font-weight: bold; vertical-align: top; font-family: Tahoma; padding-left: 10px;"
									class="tit_cab">
									<s:text name="portal.menu.soporte"/>
								</td>
							</tr>
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
						</table>

					</form>
				</div>
			</div>
			<div id="pie">
				<%@include file="pie.jsp"%>
			</div>
			<div id="ampliacion">
				<div id="c1">
					<img src="<%=request.getContextPath()%>/img/cargando.gif"
						border="0">
				</div>
				<div id="cerrarampliacion">
					<a href="javascript:ocultarImagen()" style="color: #ffffff;">[X]
						Cerrar</a>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			resizeHeight();
		</script>
	</body>
</html>
