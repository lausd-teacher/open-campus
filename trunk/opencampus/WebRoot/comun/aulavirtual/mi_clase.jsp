<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}' />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" />
		</title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jPrototype.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/util.js"></script>
		<script type="text/javascript" src='<%=request.getContextPath()%>/admin/aulavirtual/mi_clase.js'></script>
		
		<style>
#ampliacion {
	border-right: #666666 1px solid;
	padding-right: 2px;
	border-top: #666666 1px solid;
	background-color:#ffffff;
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
	background-color:#333333;
	font-family:arial,verdana;
	font-size:8pt;
	line-height:20px;
	text-align:left;
	float:right;
	height: 20px;
	width: 124px;
	padding-right:5px;
	padding-left:5px;
}
</style>
<script type="text/javascript">
	// ********** Mostrar Imagen de Usuario (existe una copia en admin_usuario_buscar.js) ************//
var imagen;
var x;
function verImagen(img, archivo) {
	if (xStr(archivo)) {
		x = xPageX(img);
		imagen = new Image();
		imagen.src = archivo;
		xMoveTo("ampliacion", xPageX(img) - xWidth("ampliacion"), xPageY(img) + xHeight(img));
		pruebaCarga(imagen);
		xShow("ampliacion");
		
		$(cerrarampliacion).update('ID: '+img.id);
	}
}
function pruebaCarga() {
	if (imagen.complete == true) {
		var ancho = imagen.width;
		var alto = imagen.height;
		xResizeTo("ampliacion", ancho + 6, alto + 6 + 20);
		xResizeTo("c1", ancho, alto);
		xWidth("cerrarampliacion", ancho); 
		xLeft("ampliacion",x-xWidth("ampliacion"));
		xInnerHtml("c1", "<img src=\"" + imagen.src + "\" width=\"" + ancho + "\" height=\"" + alto + "\" border=\"0\">");
	} else {
		setTimeout("pruebaCarga()", 500);
	}
}
function ocultarImagen() {
	xHide("ampliacion");
}

// ********** Fin Mostrar Imagen de Usuario ***********//
</script>
	</head>
	<body>
		<div id="pop_up" style="width: 520px;">
			<div id="prin_01" style="width: 520px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="90%">
							<strong>Curso : <c:out value="${sessionScope.aula.curso.nombre}"></c:out> </strong>
						</td>
						<td width="5%">
							<a href="#" class="salir" onClick="window.close()">Cerrar</a>
						</td>
						<td width="5%">
							<a href="#" class="salir" onClick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13"
									height="13" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>
			<div id="pop_cuerpo" style="width: 500px;">
				<table width="500" align="center" cellpadding="0" cellspacing="0"
					class="bor_tabla tabla_sin_layout" border="0">
					<tr class="fon_cab ">
						<td height="20" class="tit_cab">
							Mi Clase
						</td>

					</tr>
					<tr>
						<td align="left">
							<table width="100%" align="center" cellpadding="3" cellspacing="0"
								class="tabla_sin_layout" border="0">

								<tr class="fon_color01">
									<td width="16" align="center" class="texto2">
										&nbsp;
									</td>
									<td width="80" height="23" align="center" class="texto2">
										<strong>Usuario</strong>
									</td>
									<td  align="center" class="texto2">
										<strong> Paterno </strong>
									</td>
									<td align="center" class="texto2">
										<strong>Materno</strong>
									</td>
									<td  align="center" class="texto2">
										<strong>1er.Nom</strong>
									</td>
									<td  align="center" class="texto2">
										<strong>2do.Nom</strong>
									</td>
									<td width="20" align="center" class="moduloArriba1">
										<input type="checkbox" onClick="todos(this)">
									</td>
									<td width="20" align="center" class="moduloArriba1">
										<img src="<%=request.getContextPath()%>/img/icon_mail_send.gif" alt="Enviar Correo"
												border="0" onClick="masivo()" style="cursor: pointer"/>
									</td>
								</tr>

								<c:forEach items="${grupos}" var="grupo" varStatus="status">

									<tr>
										<td height="23" colspan="8" align="left" class="moduloArriba2"><c:out value="${grupo.nombre}"></c:out> </td>
									</tr>
									
									<c:choose>
										<c:when test="${fn:length(grupo.matriculas) == 0}">
											<tr>
												<td height="23" colspan="8" align="center">
													<table cellpadding="0" cellspacing="0">
														<tr>
															<td width="5" height="5" id="td_1_mensaje"
																style="background-image: url('<%=request.getContextPath()%>/img/punto_amarillo.png'); background-position: left top;" />
															<td style="background-color: #FFF1A8;" id="td_2_mensaje" />
															<td id="td_3_mensaje"
																style="background-image: url('<%=request.getContextPath()%>/img/punto_amarillo.png'); background-position: right top;" />
														</tr>
														<tr>
															<td style="background-color: #FFF1A8;" width="5"
																id="td_4_mensaje" />
															<td style="background-color: #FFF1A8;" id="td_5_mensaje"
																align="center">
																<b id="tr_mensajes_mensaje"
																	style="color: black; text-align: center;">No existen matriculados como <c:out value="${grupo.nombre}"></c:out>
																</b>
															</td>
															<td style="background-color: #FFF1A8;" width="5"
																id="td_6_mensaje" />
														</tr>
														<tr>
															<td height="5" id="td_7_mensaje"
																style="background-image: url('<%=request.getContextPath()%>/img/punto_amarillo.png'); background-position: left bottom;" />
															<td style="background-color: #FFF1A8;" id="td_8_mensaje" />
															<td id="td_9_mensaje"
																style="background-image: url('<%=request.getContextPath()%>/img/punto_amarillo.png'); background-position: right bottom;" />
														</tr>
													</table>
												</td>
											</tr>
										</c:when>
									
										<c:otherwise>
																						
											<c:forEach items="${grupo.matriculas}" var="matricula" varStatus="statusm">
											
												<tr <c:if test="${statusm.count%2==0}"> class="fon_color02" </c:if> height="23">
													<td width="20" align="right" class="bor_der_unid">
														<c:out value="${statusm.count}"></c:out>.
													</td>
													<td class="bor_der_unid">
														<c:out value="${matricula.usuario.usuario}"></c:out>
													</td>
				
													<td class="bor_der_cur">
														<c:out value="${matricula.usuario.persona.apepaterno}" default="&nbsp;" escapeXml="false"></c:out>
													</td>
													<td class="bor_der_cur">
														<c:out value="${matricula.usuario.persona.apematerno}" default="&nbsp;" escapeXml="false"></c:out>
													</td>
													<td class="bor_der_cur">
														<c:out value="${matricula.usuario.persona.nomuno}" default="&nbsp;" escapeXml="false"></c:out>
													</td>
													<td class="bor_der_cur">
														<c:out value="${matricula.usuario.persona.nomdos}" default="&nbsp;" escapeXml="false"></c:out>
													</td>
													<td align="center">
														<input type="checkbox" class="chkmail" value="<c:out value="${matricula.usuario.usuario}"></c:out>" />
													</td>
													<td align="center">
														<img src="<%=request.getContextPath()%>/img/icon_user.png" border="0" class="link_negro" style="cursor: pointer;"
															onmouseover="verImagen(this,'<%=request.getContextPath()%>/VerFoto.action?id=<c:out value="${matricula.usuario.id}"/>');"
															onmouseout=" ocultarImagen();" id="<c:out value="${matricula.usuario.usuario}"></c:out>" />
													</td>
												</tr>
												
											</c:forEach>
											
										</c:otherwise>
									</c:choose>
																	
								</c:forEach>
								
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div id="pie" style="width: 520px;">
			</div>
			
			
			<div id="ampliacion">
				<div id="c1">
					<img src="<%=request.getContextPath()%>/img/cargando.gif"
						border="0">
				</div>
				<div id="cerrarampliacion" style="color:#ffffff;font-weight: bold;">
				</div>
			</div>
		</div>
	</body>
</html>
