<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title><s:text name="titulo.campus.virtual" />
</title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/jPrototype.js"></script>
		<script type="text/javascript" src='<%=request.getContextPath()%>/comun/foro/foro_usuario.js'></script>
	    <script>	   
			var imagesDir = "<%=request.getContextPath()%>/js/openwysiwyg/icons/";
			var cssDir = "<%=request.getContextPath()%>/js/openwysiwyg/styles/";
			var popupsDir = "<%=request.getContextPath()%>/js/openwysiwyg/popups/";			
		</script>
		<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/js/openwysiwyg/wysiwyg.js"></script>
	    </head>
	<body>
		<div id="pop_up" style="width: 840px;">
			<div id="prin_01">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="93%">
							<a style="color:#000000;font-weight: bold;" href="<%=request.getContextPath()%>/foro/Foro.action">Foros</a> > <a style="color:#000000;font-weight:bold;" href="<%=request.getContextPath()%>/foro/IngresarForo.action?idForo=<c:out value="${sessionScope.foro.idForo}"/>"><c:out value="${sessionScope.foro.titulo}"/></a> > <span style="color:#cccccc;font-weight:bold;"><c:out value="${sessionScope.tema.titulo}"/></span>
						</td>
						<td width="5%">
							<a href="#" class="salir" onClick="window.print()">Imprimir</a>
						</td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.print()"><img
									src="<%=request.getContextPath()%>/img/impresora.gif"
									width="13" height="13" border="0" />
							</a>
						</td>
						<td width="2%">
							|
						</td>
						<td width="4%">
							<a href="#" class="salir" onClick="window.close()">Cerrar</a>
						</td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13"
									height="13" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>
			<div id="pop_cuerpo">
				
				<table width="100%" border="0" cellspacing="0" cellpadding="4">
					<tr style="background-color: #E5EFF8;">
						<td align="left" width="200">
							<div id="stars"><strong>Valorar Tema :</strong> &nbsp;
								<c:if test="${sessionScope.tema.valoracion > 0}">
			                        <c:forEach begin="1" end="${sessionScope.tema.valoracion}" varStatus="status">
			                          <img style="cursor: pointer;" onclick="tema.valorar(this)" src="<%=request.getContextPath()%>/img/icon_importante_y.gif" width="12" height="12" id="<c:out value='${status.index}'/>">
			                       	</c:forEach>
		                       	</c:if>
		                       	<script type="text/javascript">
		                       		tema.starties('<%=request.getContextPath()%>');
		                       	</script>
	                       	</div>
	                    </td>
	                    <td>&nbsp;</td>
	                    <td align="right" width="150">
	                    	<c:if test="${pagActual!=1}">
	                    	<c:if test="${pagActual!=2}">
	                    	<a style="color:#cccccc;font-weight:bold;" href="<%=request.getContextPath()%>/foro/IngresarTema.action?idTema=<c:out value="${sessionScope.tema.idTema}"/>&pagActual=<c:out value="1"/>""><<</a>                    
	                   		&nbsp;
	                   		</c:if>
	                    	<a style="color:#cccccc;font-weight:bold;" href="<%=request.getContextPath()%>/foro/IngresarTema.action?idTema=<c:out value="${sessionScope.tema.idTema}"/>&pagActual=<c:out value="${pagActual-1}"/>"> < </a> 
	                    	</c:if>
	                    	&nbsp;<c:out value="${start+1}"/> - <c:out value="${end}"/> de <c:out value="${totalMensajes}"/> mensajes
	                    	<c:if test="${end!=totalMensajes}">
	                    	<a style="color:#cccccc;font-weight:bold;" href="<%=request.getContextPath()%>/foro/IngresarTema.action?idTema=<c:out value="${sessionScope.tema.idTema}"/>&pagActual=<c:out value="${pagActual+1}"/>"> > </a>
	                    	<c:if test="${pagActual!=(lastOne-1)}">
	                    	&nbsp;
	                    	<a style="color:#cccccc;font-weight:bold;" href="<%=request.getContextPath()%>/foro/IngresarTema.action?idTema=<c:out value="${sessionScope.tema.idTema}"/>&pagActual=<c:out value="${lastOne}"/>"">>></a>
	                    	</c:if>
	                    	</c:if>
	                    </td>
	                </tr>
				</table>
					
						<c:forEach items="${mensajes}" var="mensaje" varStatus="status">
							<table id="head_<c:out value="${mensaje.idMensaje}"/>" border="0" cellspacing="0" cellpadding="3" width="100%" class="bor_tabla" style="table-layout: fixed;">
								<tr class="tabla01_encabezado" style="cursor:pointer" onclick="xChangeDisplay('pop_<c:out value="${mensaje.idMensaje}"/>')">
									<td align="left" width="50%" style="vertical-align: top;">
											<a name="<c:out value="${mensaje.idMensaje}"/>" /></a><span style="white-space: nowrap;"><c:out value="${mensaje.nombreUsuario}"/></span>
									</td>
									<td align="right" width="50%">
											<c:out value="${mensaje.fechaCreacionToString}"></c:out>
									</td>								
								</tr>
							</table>
							<table id="pop_<c:out value="${mensaje.idMensaje}"/>" border="0" cellspacing="0" cellpadding="8" width="100%" class="bor_tabla" style="table-layout: fixed;">
								<tr <c:if test="${mensaje.moderado == 1}">style="background-color: #FFFBE8;"</c:if> >
									<td width="104" valign="top">
										<div align="center">
										<c:choose>
												<c:when test="${mensaje.sexoUsuario == 'M'}">
														<img name="" src="<%=request.getContextPath()%>/img/man.jpg" width="64" height="64" alt="">
												</c:when>
												<c:otherwise>
														<img name="" src="<%=request.getContextPath()%>/img/woman.jpg" width="58" height="56" alt="">
												</c:otherwise>
											</c:choose>
											<c:if test="${mensaje.moderado == 1}"><strong>Moderador</strong></c:if>
										</div>
									</td>
									<td valign="top" style="padding-right: 10px">
										<c:out value="${mensaje.cuerpo}" escapeXml="false"/>								
									</td>
								</tr>
								
								<tr style="background-color: #E7E8E9;">
									<td colspan="2">
										<div>
											<span style="float:left; cursor:pointer" onclick="enviarCorreo('<c:out value="${mensaje.usuarioCreacion}"/>','[Comentario en el tema: <c:out value="${sessionScope.tema.titulo}"/>]')"">
												<table border="0" cellspacing="0" cellpadding="0">
													<td><strong>Enviar un correo </strong></td><td>&nbsp;<img src="<%=request.getContextPath()%>/img/icon_mail_send.gif" border="0"></td>
												</table>
											</span>
											
											<c:if test="${sessionScope.tema.cerrado==0  && sessionScope.foro.cerrado == 0}">
												<span style="float:right;"><input type="button" value="Responder" class="form_button" style="width: 80px;" 
													style="cursor:pointer" onclick="mensaje.responder('<c:out value="${mensaje.idMensaje}"/>','<%=request.getContextPath()%>')"></span>
											</c:if>
											
											<c:if test="${(sessionScope.foro.yoLoModero || admin) && status.count != 1}">
													<span style="float:right;"><img src="<%=request.getContextPath() %>/img/icon_trash.gif" border="0" alt="Eliminar" style="cursor: pointer;"
													 onclick="mensaje.eliminar('<c:out value="${mensaje.idMensaje}"/>')"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
											</c:if>
										</div>	
										<div style="display: none;" id="div_<c:out value="${mensaje.idMensaje}"/>">			
											<br>&nbsp;
											<form method="post" action="<%=request.getContextPath()%>/foro/NuevoMensaje.action" id="elFormulario_<c:out value="${mensaje.idMensaje}"/>" onsubmit="return valida(this)">
												<table width="100%">
													<tr>
														<td width="100%">&nbsp;&nbsp;<textarea id="cuerpo<c:out value="${mensaje.idMensaje}"/>" name="cuerpo" style="width: 98%" rows="4"></textarea>
														</td>
													</tr>
													<tr>
														<td>
															<input type="reset" class="form_button" value="Cancelar" style="width: 80px;" 
																onclick="mensaje.cancelar('<c:out value="${mensaje.idMensaje}"/>')">
															&nbsp;&nbsp;<input type="submit" value="Enviar" class="form_button" style="width: 80px;">
														</td>
													</tr>
												</table>
												<input type="hidden" name="idMensaje" value="<c:out value="${mensaje.idMensaje}"/>">
												<input type="hidden" name="pagActual" value="<c:out value="${pagActual}"/>">
											</form>
										</div>
										
									</td>
								</tr>
							</table>
							<div id="space_<c:out value="${mensaje.idMensaje}"/>">&nbsp;</div>
						</c:forEach>
						
						<table width="100%">
							<tr><td>
								<a style="color:#000000;font-weight: bold;" href="<%=request.getContextPath()%>/foro/Foro.action">Foros</a> > <a style="color:#000000;font-weight:bold;" href="<%=request.getContextPath()%>/foro/IngresarForo.action?idForo=<c:out value="${sessionScope.foro.idForo}"/>"><c:out value="${sessionScope.foro.titulo}"/></a> > <span style="color:#cccccc;font-weight:bold;"><c:out value="${sessionScope.tema.titulo}"/></span>
								</td><td align="right">
								
		                    </td>
		                    <td align="right">
		                    	<c:if test="${pagActual!=1}">
		                    	<c:if test="${pagActual!=2}">
		                    	<a style="color:#cccccc;font-weight:bold;" href="<%=request.getContextPath()%>/foro/IngresarTema.action?idTema=<c:out value="${sessionScope.tema.idTema}"/>&pagActual=<c:out value="1"/>""><<</a>                    
		                   		&nbsp;
		                   		</c:if>
		                    	<a style="color:#cccccc;font-weight:bold;" href="<%=request.getContextPath()%>/foro/IngresarTema.action?idTema=<c:out value="${sessionScope.tema.idTema}"/>&pagActual=<c:out value="${pagActual-1}"/>"> < </a> 
		                    	</c:if>
		                    	&nbsp;<c:out value="${start+1}"/> - <c:out value="${end}"/> de <c:out value="${totalMensajes}"/> mensajes
		                    	<c:if test="${end!=totalMensajes}">
		                    	<a style="color:#cccccc;font-weight:bold;" href="<%=request.getContextPath()%>/foro/IngresarTema.action?idTema=<c:out value="${sessionScope.tema.idTema}"/>&pagActual=<c:out value="${pagActual+1}"/>"> > </a>
		                    	<c:if test="${pagActual!=(lastOne-1)}">
		                    	&nbsp;
		                    	<a style="color:#cccccc;font-weight:bold;" href="<%=request.getContextPath()%>/foro/IngresarTema.action?idTema=<c:out value="${sessionScope.tema.idTema}"/>&pagActual=<c:out value="${lastOne}"/>"">>></a>
		                    	</c:if>
		                    	</c:if>
		                    </td></tr>
						</table>
			</div>
			
			<div id="pie">
				<%@include file="../pie.jsp"%>
			</div>
		</div>
							
	</body>
</html>
