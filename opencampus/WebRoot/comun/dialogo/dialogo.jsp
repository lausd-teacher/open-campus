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
			src="<c:out value='${contextPath}'/>/comun/dialogo/dialogo.js"></script>
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
							<strong>Curso: <c:out value="${usuario.aulaActual.curso.nombre}"/> </strong>
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
								<td height="15" align="right">
									<table width="100%" border="0" cellpadding="3" cellspacing="0" >
									<c:if test="${!esInvitado || esAdmin}">
										<tr>
											<td align="right">
												<a href="#"> <input type="button" value="Nuevo Di&aacute;logo" class="form_button" onClick="nuevoPadre()" id="btnNvoTma">
												</a>
											</td>
										</tr>
									</c:if>
										<tr>
											<td align="left">
												<div id="nuevoTema" style="display: none">
												
													<form method="post"
														action="<%=request.getContextPath()%>/aulavirtual/dialogo/Crear.action" onSubmit="return validaTema();">
														<input type="hidden" name="owner" value="0"/>
														<input type="hidden" name="predecesor" value="0"/>
														<table width="100%" border="0" cellpadding="3"
															cellspacing="0">
															<tr>
																<td width="17%">
																	<div align="left">
																		<strong>T&iacute;tulo: </strong>
																	</div>
																</td>
																<td width="83%">
																	<input name="asunto" type="text" id="tituloTema"
																		 maxlength="50" style="width: 99%; font-size: 9pt" autocomplete="off">
																</td>
															</tr>
															<tr>
																<td align="left" valign="top">
																	<strong>Contenido:</strong>
																</td>
																<td>
																	<textarea name="texto" id="cuerpo" style="width: 98%"
																		rows="15"></textarea>
																</td>
															</tr>
															<tr>
																<td>&nbsp;
																	
																</td>
																<td align="right">&nbsp;
																	<input type="reset" value="Cancelar"
																					class="form_button" onClick="cancelarPadre()">
																	&nbsp;					
																<input type="submit" name="btnGuardar" id="button2"
																	value="Enviar" class="form_button" >
																</td>
															</tr>
														</table>
													</form>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						
						<tr>
							<td height="10" align="right">&nbsp;
								
							</td>
						</tr>
						
						<c:choose>
							<c:when test="${fn:length(dialogos)>0}">
							
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="4" cellspacing="0"
									class="bor_tabla" style="table-layout: fixed; border-right: 0px none;">
									
									<tbody>
										<caption class="fon_cab tit_cab">
												Di&aacute;logo: <c:out value="${UNIDAD_NOMBRE_TEMP}"></c:out>
										</caption>
										<tr class="fon_color01" height="18">
											<td width="34" align="center" class="foro_Modulo">
												&nbsp;
											</td>
											<td align="center" class="foro_Modulo">
												<strong>T&iacute;tulo</strong>
											</td>
											<td width="160" align="center" class="foro_Modulo">
												<strong>Autor</strong>
											</td>
											<td width="100" align="center" class="foro_Modulo">
												<strong>Fecha</strong>
											</td>
											<td class="foro_Modulo" width="40" align="center">
												<strong>Resp</strong>
											</td>
											<c:if test="${esAdmin}">
												<td width="34" align="center" class="foro_Modulo">
													&nbsp;
												</td>
											</c:if>									
										</tr>
										
										<c:forEach items="${dialogos}" var="dialogo" varStatus="status">
											<tr id="tema_fila_<c:out value="${tema.idTema}"/>" style="cursor: pointer;" 
												onmouseover="seleccionNoticia(this, true)" onmouseout="seleccionNoticia(this, false)"
												>
											
												<td class="foro_bor_der_cur_col" align="center" valign="middle"
onclick="window.document.location =xGetContextPath() +'/aulavirtual/dialogo/CargarSala.action?idDialogo=<c:out value="${dialogo.idDialogo}"></c:out>';"												
												>
													<c:choose>
														<c:when test="${(dialogo.leido) == 0 || dialogo.cantidadNoSubPlacticas > 0}">
															<img src="<%=request.getContextPath() %>/img/flag.gif" /> (<c:out value="${dialogo.cantidadNoSubPlacticas + (1 - dialogo.leido)}"/>)
														</c:when>
														<c:otherwise>
															&nbsp;
														</c:otherwise>
													</c:choose>
												</td>											
												<td class="foro_bor_der_cur_col" valign="top" style="white-space: nowrap;"
onclick="window.document.location =xGetContextPath() +'/aulavirtual/dialogo/CargarSala.action?idDialogo=<c:out value="${dialogo.idDialogo}"></c:out>';"													
												>
													<strong><c:out value="${dialogo.asunto}" /></strong>
												</td>
												<td align="left" valign="middle" class="foro_bor_der_cur_col" style="white-space: nowrap;"
onclick="window.document.location =xGetContextPath() +'/aulavirtual/dialogo/CargarSala.action?idDialogo=<c:out value="${dialogo.idDialogo}"></c:out>';"													
												>
													<c:out value="${dialogo.nombreUsuario}" />
												</td>
												<td align="center"  class="foro_bor_der_cur_col"
onclick="window.document.location =xGetContextPath() +'/aulavirtual/dialogo/CargarSala.action?idDialogo=<c:out value="${dialogo.idDialogo}"></c:out>';"													
												>
													<c:out value="${dialogo.fecha}"></c:out>
												</td>	
												<td align="center"  class="foro_bor_der_cur_col" valign="top"
onclick="window.document.location =xGetContextPath() +'/aulavirtual/dialogo/CargarSala.action?idDialogo=<c:out value="${dialogo.idDialogo}"></c:out>';"													
												>
													<c:out value="${dialogo.cantidadSubPlactica}"></c:out>
												</td>	
												<c:if test="${esAdmin}">
													<td class="foro_bor_der_cur_col" align="center" valign="middle">
													<a 
													onclick="return confirm('¿Está seguro que desea eliminar el diálogo?');"
													href="<%=request.getContextPath() %>/aulavirtual/dialogo/Eliminar.action?idDialogo=<c:out value="${dialogo.idDialogo}"></c:out>" >
														<img src="<%=request.getContextPath() %>/img/icon_trash.gif" border="0" alt="Eliminar" style="cursor: pointer;" border="0"/>
													</a>			 
													</td>
												</c:if>
																			
											</tr>
										</c:forEach>
									
									</tbody>									
								</table>
							</td>
						</tr>
						
					  		</c:when>
							<c:otherwise>
							  <tr><td><center><h5>Este di&aacute;logo no contiene ning&uacute;n tema a&uacute;n.<br>¿Quiere ser el primero?</h5></center></td></tr>
							</c:otherwise>
					  </c:choose>
					  
					</table>
					
					
				
				</div>
			</div>
			<div id="pie">
				<%@include file="../pie.jsp"%>
			</div>
		</div>
			
	</body>
</html>
