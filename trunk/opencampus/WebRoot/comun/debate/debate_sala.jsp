<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}' />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" /></title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/comun/debate/debate.js"></script>
			<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/jPrototype.js"></script>
			<script>	   
			var imagesDir = "<%=request.getContextPath()%>/js/openwysiwyg/icons/";
			var cssDir = "<%=request.getContextPath()%>/js/openwysiwyg/styles/";
			var popupsDir = "<%=request.getContextPath()%>/js/openwysiwyg/popups/";			
		</script>
		<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/js/openwysiwyg/wysiwyg.js"></script>
		
	</head>
	<body>
		<div id="pop_up" style="width: 700px;">
			<div id="prin_01">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="93%">
							
								<strong>Curso: <c:out value="${aula_actual.nombreCurso}"/> </strong>
							
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
				<div id="portada">	
				
					
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="3" cellspacing="0" class="bor_tabla">
									<caption class="fon_cab tit_cab">
										<strong>Tema: <c:out value="${debate.asunto}"></c:out></strong>&nbsp;
										<c:choose>
											<c:when test="${debate.leido == 0}">
												<img id="flag_<c:out value="${debate.idDebate}" />"  src="<%=request.getContextPath() %>/img/flag.gif" width="8"/>
											</c:when>
											<c:otherwise>
												<img id="flag_<c:out value="${debate.idDebate}" />"  src="<%=request.getContextPath() %>/img/cal.gif" width="8"/>
											</c:otherwise>
										</c:choose>
									</caption>
									<tr class="fon_color01">
										<td style="padding: 5px;"><strong>Enviado por <c:out value="${debate.nombreUsuario}"></c:out></strong> </td>
										<td align="right" style="padding: 5px;"><strong>Fecha: <c:out value="${debate.fecha}"></c:out></strong> </td>
									</tr>
									<tr style="background-color: #F9F9F9;">
										<td colspan="2" style="padding: 8px;"> <c:out value="${debate.texto}" escapeXml="false"></c:out> </td>
									</tr>
									
								</table>
							</td>
						</tr>
						
							<tr>
								<td height="15" align="right">
									<table width="100%" border="0" cellpadding="0" cellspacing="0" class="paddingForm">
										<tr>
											<td align="left">
												<input type="button" value="Regresar" class="form_button" onClick="window.document.location=xGetContextPath()+'/aulavirtual/debate/Cargar.action?idGrupo=<c:out value="${aula_actual.debateActual}"/>';" style="width: 110px;">
											</td>
											<td align="right">
												<input type="button" value="Marcar no leído" class="form_button" onclick="marcarLeido(this,'<c:out value="${debate.idDebate}"/>')" style="width: 110px;">
												<c:if test="${!esInvitado || esAdmin}">
												<a href="#"> <input type="button" value="Responder"
														class="form_button" onClick="nuevo('<c:out value="${debate.idDebate}"/>','<c:out value="${fn:replace(debate.asunto,'\\'', '`')}"/>')" style="width: 110px;">
												</a>
												</c:if>
											</td>
										</tr>
										<tr>
											<td align="left" colspan="2">
												<div id="nuevoTema"></div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						
						<tr>
							<td height="10" align="right">&nbsp;
								<input type="hidden" id="admin" value="<c:out value="${esAdmin}"/>">
								<input type="hidden" id="invitado" value="<c:out value="${esInvitado}"/>">
							</td>
						</tr>
						
						<c:choose>
							<c:when test="${fn:length(debates)>0}">
							
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="3" cellspacing="0"
									class="bor_tabla" style="table-layout: fixed;">
									<tbody>
										<tr id="fila_sub_<c:out value="${debate.idDebate}" />">
											<td colspan="6"><c:forEach items="${debates}" var="dia" varStatus="status"><table width="100%" border="0" cellpadding="4" cellspacing="0"
														class="bor_tabla" style="table-layout: fixed;" id="dialogo_<c:out value="${dia.idDebate}" />">
														<tbody>
															<tr style="cursor: pointer;" onclick="listarSubTertulias('<c:out value="${dia.idDebate}" />')"
																onmouseover="seleccionNoticia(this, true)" onmouseout="seleccionNoticia(this, false)">
																<td width="14" align="center" style="white-space: nowrap;">
																	<c:choose>
																		<c:when test="${dia.cantidadSubPlactica > 0}">
																			<img id="mas_<c:out value="${dia.idDebate}" />" src="<%=request.getContextPath() %>/img/mas.jpg" alt="Desplegar"/>
																		</c:when>
																		<c:otherwise>
																			<img id="nada_<c:out value="${dia.idDebate}" />" src="<%=request.getContextPath() %>/img/nada.jpg" />
																		</c:otherwise>
																	</c:choose>
																</td>
																<td width="10" align="center" style="white-space: nowrap;">
																	<c:choose>
																		<c:when test="${dia.leido == 0}">
																			<img id="flag_<c:out value="${dia.idDebate}" />"  src="<%=request.getContextPath() %>/img/flag.gif" width="8"/>
																		</c:when>
																		<c:otherwise>
																			<img id="flag_<c:out value="${dia.idDebate}" />"  src="<%=request.getContextPath() %>/img/cal.gif" width="8"/>
																		</c:otherwise>
																	</c:choose>
																</td>											
																<td style="white-space: nowrap;">
																	<strong><c:out value="${dia.asunto}" /></strong> 
																</td>
																<td width="160" style="white-space: nowrap;">
																	<c:out value="${dia.nombreUsuario}" />
																</td>
																<td width="100" align="center">
																	<c:out value="${dia.fecha}"></c:out>
																</td>	
																<td width="50" align="center">
																	<c:out value="${dia.cantidadSubPlactica}"></c:out>
																</td>
															</tr>
															<tr style="display: none;" id="fila_cuerpo_<c:out value="${dia.idDebate}" />">
																<td class="dialogoPuntoV">&nbsp;</td>
																<td colspan="5" class="paddingCuerpo">
																</td>
															</tr>
															<tr style="display: none;" id="fila_form_<c:out value="${dia.idDebate}" />">
																<td class="dialogoPuntoV">&nbsp;</td>
																<td colspan="5" class="paddingForm" align="right">
																	<c:if test="${esAdmin}">
																		<input type="button" value="Eliminar" class="form_button" onclick="eliminar('<c:out value="${dia.idDebate}"/>')" style="width: 110px;">
																	</c:if>
																	<input type="button" value="Marcar no leído" class="form_button" onclick="marcarLeido(this,'<c:out value="${dia.idDebate}"/>')" style="width: 110px;">
																	<c:if test="${!esInvitado || esAdmin}">
																		<input type="button" value="Responder" class="form_button" onclick="responder('<c:out value="${dia.idDebate}"/>','<c:out value="${dia.asunto}" />')" style="width: 110px;">
																	</c:if>
																	<br>
																	<div id="form_dia_<c:out value="${dia.idDebate}" />"></div>
																</td>
															</tr>
															<tr style="display: none;" id="fila_sub_<c:out value="${dia.idDebate}" />">
																<td colspan="6">
																</td>
															</tr>
														</tbody>
													</table><img src="<%=request.getContextPath() %>/img/cal.gif" height="2"/></c:forEach></td>							
										</tr>
									</tbody>									
								</table>
							</td>
						</tr>
						
					  		</c:when>
							<c:otherwise>
							  <tr><td><center><h5>Este di&aacute;logo no contiene ninguna respuesta a&uacute;n.<br>¿Quiere ser el primero?</h5></center></td></tr>
							</c:otherwise>
					  </c:choose>
					  
					</table>
					
					<div id="div_form">
						<form method="post" action="<%=request.getContextPath()%>/aulavirtual/debate/Crear.action" onsubmit="return valida(this)">
							<table width="100%" style="table-layout: fixed;" border="0">
								<tr>
									<td width="10%" style="padding-left: 5px;" align="left">
										<strong>Asunto: </strong>
									</td>
									<td width="90%">
										<input type="text" id="form_asunto" name="asunto" value="" maxlength="50" style="width: 99%;">
									</td>
								</tr>
								<tr>
									<td width="100%" colspan="2">
										<textarea id="texto" name="texto" style="width: 98%" rows="4"></textarea>
										<script type="text/javascript">
											generate_wysiwyg('texto', '610', '100');  xHideD('div_form');
										</script>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<input type="reset" class="form_button" value="Cancelar" style="width: 80px;" 
											onclick="cancelar()">
										&nbsp;&nbsp;<input type="submit" name="submit" value="Enviar" class="form_button" style="width: 80px;">
									</td>
								</tr>
							</table>
							<input type="hidden" id="form_predecesor" name="predecesor" value="">
							<input type="hidden" name="owner" value="<c:out value="${debate.idDebate}"/>">
						</form>
					</div>
				
				</div>
			</div>
			<div id="pie">
				<%@include file="../pie.jsp"%>
			</div>
		</div>
			
	</body>
</html>