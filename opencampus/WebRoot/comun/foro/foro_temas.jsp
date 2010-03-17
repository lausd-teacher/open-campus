<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" /></title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/comun/foro/foro_usuario.js'></script>
			<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/jPrototype.js"></script>
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
							<a style="color:#000000;font-weight:bold;" href="<%=request.getContextPath()%>/foro/Foro.action">
							<strong>Foros</strong></a>
							>
							<a style="color:#44659B;font-weight: bold;"><c:out value="${sessionScope.foro.titulo}"/></a>
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
				
					<table width="810" border="0" cellpadding="0" cellspacing="0">
					
						<c:if test="${sessionScope.foro.cerrado == 0}">
							
							<tr>
								<td height="15" align="right">
									<table width="100%" border="0" cellpadding="3" cellspacing="0" >
										<tr>
											<td align="right">
												<a href="#"> <input type="button" value="Nuevo Tema"
														class="form_button" onClick="tema.nuevo()" id="btnNvoTma">
												</a>
											</td>
										</tr>
										<tr>
											<td align="left">
												<div id="nuevoTema" style="display: none">
													<form method="post"
														action="<%=request.getContextPath()%>/foro/NuevoTema.action" onSubmit="return validaTema();">
														<table width="100%" border="0" cellpadding="3"
															cellspacing="0" class="bor_tabla">
															<tr class="fon_color01" height="25">
																<td colspan="2" style="font-weight: bold;" class="foro_ModuloAbajo" style="padding-left: 10px;">Nuevo Tema</td>
															</tr>
															<tr height="1">
																<td class="foro_bor_der_cur_start"></td>
															</tr>
															<tr>
																<td width="15%" class="foro_bor_der_cur_start">
																	<div align="left">
																		<strong>Nombre: </strong>
																	</div>
																</td>
																<td width="83%" >
																	<input name="titulo" type="text" id="tituloTema"
																		size="80" maxlength="64" style="font-size: 9pt; width: 620px;" autocomplete="off">
																</td>
															</tr>
															<tr>
																<td align="left" valign="top" class="foro_bor_der_cur_start">
																	<strong>Descripción:</strong>
																</td>
																<td>
																	<textarea name="cuerpo" id="cont" style="width: 98%"
																		rows="15"></textarea>
																	<%--	<div style="background-color: white">
																		<script language="JavaScript"> <!--
																	var editor1 = new EDITOR();
																	editor1.iconPath = "<%=request.getContextPath()%>/js/richedit/icons/";
																	editor1.editorBGColor = '#E8EEF7';																	
																	editor1.create("");																	
																	//--> </script>
																	</div>--%>
																</td>
															</tr>
															<tr>
																<td class="foro_bor_der_cur_start">&nbsp;
																	
																</td >
																<td align="right">&nbsp;
																	
																</td>
															</tr>
															<tr>
																<td class="foro_bor_der_cur_start">&nbsp;
																	
																</td>
																<td align="right" style="padding-right: 52px;">
																	<table width="453" border="0" cellpadding="0"
																		cellspacing="0">
																		<tr>
																			<td width="331" style="padding-right: 7px">
																				
																			</td>
																			<td width="15"><input type="reset" value="Cancelar"
																					class="form_button" onClick="tema.cancelar()" style="width: 90px;">
																				
																			</td>
																			<td width="107">
																				<div align="right">
																					<input type="submit" name="btnGuardar" id="button2"
																						value="Enviar" class="form_button" style="width: 90px;">
																			</td>
																		</tr>
																	</table>
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
						</c:if>

						<tr>
							<td height="10" align="right">&nbsp;
								
							</td>
						</tr>
						
						<c:choose>
							<c:when test="${fn:length(temas)>0}">
							
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="3" cellspacing="0"
									class="bor_tabla" style="table-layout: fixed; border-right: 0px none;">
									<tbody>
										<tr class="fon_color01" height="25">
											<td width="34" align="center" class="foro_Modulo">
												&nbsp;
											</td>
											<td colspan="2" align="center" class="foro_Modulo">
												<strong>Temas/Autor</strong>
											</td>
											<td width="230" align="center" class="foro_Modulo">
												<strong>&Uacute;ltimo mensaje</strong>
											</td>
											<td width="60" align="center" class="foro_Modulo">
												<strong>Mensajes</strong>
											</td>
											<td class="foro_Modulo" width="80" align="center">
												<strong>Valoraci&oacute;n</strong>
											</td>	
											<c:if test="${sessionScope.foro.yoLoModero || admin}">
												<td width="34" align="center" class="foro_Modulo">
													&nbsp;
												</td>
											</c:if>									
										</tr>
										
										<c:forEach items="${temas}" var="tema" varStatus="status">
											<tr id="tema_fila_<c:out value="${tema.idTema}"/>">
											
												<td class="foro_bor_der_cur_col" align="center" valign="middle">
												
													<c:choose>
														<c:when test="${sessionScope.foro.cerrado == 0 && tema.cerrado == 0}">
															<img src="<%=request.getContextPath()%>/img/icon_candado_abierto.jpeg" 
																<c:if test="${(sessionScope.foro.yoLoModero || admin) && sessionScope.foro.cerrado == 0}"> 
															 		alt="Cerrar" style="cursor: pointer;" onclick="tema.cerrar(this,'<c:out value="${tema.idTema}"/>')"
															 	</c:if>
															 >
														</c:when>
														<c:otherwise>
															<img src="<%=request.getContextPath()%>/img/icon_candado.jpeg" 
																<c:if test="${sessionScope.foro.yoLoModero || admin && sessionScope.foro.cerrado == 0}"> 
																	alt="Abrir" style="cursor: pointer;" onclick="tema.cerrar(this,'<c:out value="${tema.idTema}"/>')"
																</c:if>
															>
														</c:otherwise>
													</c:choose>
													<input type="hidden" id="estado_<c:out value="${tema.idTema}"/>" value="<c:out value="${tema.cerrado}"/>">
													
												</td>											
												<td colspan="2" class="foro_bor_der_cur_col" valign="top">
													<a style="color:#44659B;font-weight:bold;" href="<%=request.getContextPath()%>/foro/IngresarTema.action?idTema=<c:out value="${tema.idTema}"/>">
													<c:out value="${tema.titulo}"/></a>
													<br><br>
													por <strong><c:out value="${tema.nombreUsuario}" /></strong> el <c:out value="${tema.fechaCreacionToString}"/>
												</td>
												<td width="230" align="left" valign="middle" class="foro_bor_der_cur_col">
													<c:choose>
					                              		<c:when test="${tema.ultimoMensaje != null}">
															<table border="0" cellspacing="0" cellpadding="2" width="100%" height="100%">
							                              		<tr>
							                              			<td colspan="2" style="background-color: #FFF">
								                              			<div style="overflow: hidden;width: 230px;height: 30px;">
								                              				<strong><c:out value="${tema.ultimoMensaje.cuerpo}" escapeXml="false"></c:out></strong>
								                              			</div>
							                              			</td>
							                              		</tr>
							                              		<tr>
								                              		<td width="149" align="left" style="background-color: #E5EFF8"><strong><c:out value="${tema.ultimoMensaje.nombreUsuario}" /></strong>&nbsp;</td>
								                              		<td width="79" align="right" style="background-color: #E5EFF8"><c:out value="${tema.ultimoMensaje.fechaCreacionToString}"/>&nbsp;</td>
							                              		</tr>
							                              	</table>
						                              	</c:when>
						                              	<c:otherwise>
						                              		&nbsp;
						                              	</c:otherwise>
					                              	</c:choose>
												</td>
												<td align="center"  class="foro_bor_der_cur_col">
													<c:out value="${tema.totalRespuestas-1}"></c:out>
												</td>	
												<td align="center"  class="foro_bor_der_cur_col" valign="top">
													<c:if test="${tema.valoracion>0}">
														<table width="100%" border="0" cellpadding="0" cellspacing="0">
															<tr height="24">
																<td width="50%">&nbsp;</td>
																
																	<c:forEach begin="1" end="${tema.valoracion}">
																		<td align="center">
																			<img src="<%=request.getContextPath()%>/img/icon_importante_y.gif" width="12" height="12">
																		</td>
																	</c:forEach>
																
																<td width="50%">&nbsp;</td>
															</tr>
														</table>
														N° votos: <c:out value="${tema.totalVotos}" />
													</c:if>&nbsp;
												</td>	
												<c:if test="${sessionScope.foro.yoLoModero || admin}">
													<td class="foro_bor_der_cur_col" align="center" valign="middle">
														<img src="<%=request.getContextPath() %>/img/icon_trash.gif" border="0" alt="Eliminar" style="cursor: pointer;"
														 onclick="tema.eliminar('<c:out value="${tema.idTema}"/>')"/>
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
							  <tr><td><center><h5>Este foro no contiene ning&uacute;n tema a&uacute;n.<br>¿Quiere ser el primero?</h5></center></td></tr>
							</c:otherwise>
					  </c:choose>
					  
					</table>
			</div>

			<div id="pie">
				<%@include file="../pie.jsp"%>
			</div>
		</div>
		
	</body>
</html>
