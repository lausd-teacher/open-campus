<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title><s:text name="titulo.campus.virtual" />
</title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script type="text/javascript" src='<%=request.getContextPath()%>/comun/foro/foro_usuario.js'></script>

<style type="text/css">
#lista {
	position:absolute;
	width:270px;
	background-color:#EAEAEA; color:#000000;
	border:1px dotted; border-color:#000000;
	z-index:1;
	top:88px;
	display:none;
}
.resaltado {
	background-color:#FFFFFF; color:#000000;
	cursor: pointer;
}
.normal {
	background-color:#EAEAEA; color:#000000;
}
</style>

</head>
	<body onLoad="asignaVariables();">
		<div id="pop_up" style="width: 840px;">
			<div id="prin_01">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="93%">
							<strong>Foros</strong>
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
				
				<form action="<%=request.getContextPath()%>/foro/Buscar.action" method="post" onsubmit="return validarBusqueda(this);">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			          <tr >
			            <td class="foro_fon_tit_iz" width="3">&nbsp;</td>
			            <td class="foro_fon_tit" width="140" height="21">B&uacute;squeda Avanzada</td>
			            <td class="foro_fon_tit_de" width="20">&nbsp;</td>
			            <td align="right">&nbsp;</td>
			          </tr>
		            </table>
					<table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="bor_tabla">
				        <tr class="fon_color03" height="50">
				            <td width="50%" align="left" class="moduloAbajo1">
				            	<fieldset style="margin:5px" id="inpTema">
									<legend>Buscar por tema</legend>
				            		<input id="txtTema" name="claveTema" type="text" value="<c:out value="${claveTema}"/>" class="form_text" maxlength="64" size="30" style="width: 70%;" onfocus="setTipoDeBusqueda(this)">
				            		<input type="checkbox" name="clavecompleta" value="1" <c:if test="${clavecompleta==1}">checked="checked"</c:if>>Clave exacta
				            	</fieldset>
				            </td>
				            <td width="50%" align="left" class="moduloAbajo1">
				            	<fieldset style="margin:5px" id="inpUsuario">
									<legend>Buscar por usuario</legend>
									
				            		<input id="txtUsuario" name="titulo" type="text" class="form_text" autocomplete="off" value="<c:out value="${titulo}"/>" maxlength="64" size="30" style="width: 70%;" oonfocus="setTipoDeBusqueda(this)"
				            		 onFocus="setTipoDeBusqueda(this); if(document.getElementById('lista').childNodes[0]!=null && this.value!='') { filtraLista(this.value); formateaLista(this.value); reiniciaSeleccion(); document.getElementById('lista').style.display='block'; }" 
				            		 onBlur="if(v==1) document.getElementById('lista').style.display='none';"  
				            		 onKeyUp="if(navegaTeclado(event)==1) { clearTimeout(ultimoIdentificador); ultimoIdentificador=setTimeout('rellenaLista()', 1000); }">
				            		
				            		<input id="hdnUsuario" name="usuario" type="hidden" value="<c:out value="${usuario}"/>">
				            		<input type="checkbox" name="solotema" value="1" <c:if test="${solotema==1}">checked="checked"</c:if>>Solo en temas
				            		<br><div id="lista" onMouseOut="v=1;" onMouseOver="v=0;"></div>
				            	</fieldset>
				            </td>
				        </tr>
				        <tr class="fon_color03">
				            <td align="left" class="moduloAbajo1" colspan="2">
				            	<fieldset style="margin:5px">
									<legend>Opciones de B&uacute;squeda</legend>
					            	<b>Encontrar mensajes de:</b> &nbsp;
					            	<select name="fechaLimite" class="form_text" style="width: 122px;">
							         	<option value="" selected="selected">Cualquier fecha</option>
										<option value="0" <c:if test="${fechaLimite==0}">selected="selected"</c:if>>Hoy</option>
										<option value="1" <c:if test="${fechaLimite==1}">selected="selected"</c:if>>Ayer</option>
										<option value="7" <c:if test="${fechaLimite==7}">selected="selected"</c:if>>Hace una semana</option>
										<option value="14" <c:if test="${fechaLimite==14}">selected="selected"</c:if>>Hace 2 Semanas</option>
										<option value="30" <c:if test="${fechaLimite==30}">selected="selected"</c:if>>Hace un mes</option>
										<option value="90" <c:if test="${fechaLimite==90}">selected="selected"</c:if>>Hace 3 Meses</option>
										<option value="180" <c:if test="${fechaLimite==180}">selected="selected"</c:if>>Hace 6 Meses</option>
										<option value="365" <c:if test="${fechaLimite==365}">selected="selected"</c:if>>Hace un año</option>
							         </select>
							         &nbsp;&nbsp;&nbsp;
							         <b>En Foro:</b> &nbsp;
							         <select name="enforos" class="form_text" style="width: 430px;">
							         	<option value="0" selected="selected">En todos los foros</option>
										<c:forEach items="${foroEtiquetas}" var="foro">
							         		<option value="<c:out value="${foro.idForo}"/>" <c:if test="${enforos==foro.idForo}">selected="selected"</c:if>><c:out value="${foro.titulo}"/></option>
							         	</c:forEach>
							         </select>
								</fieldset>
							</td>
				        </tr>
				        <tr class="fon_color03">
				            <td align="center" colspan="2">
				            	<input type="button" value="Regresar" class="form_button"  style="width: 100px;" onClick="javascript:history.back();"/>
				            	&nbsp;&nbsp;&nbsp;&nbsp;
				            	<input type="submit" class="form_button" value=" Buscar " style="width: 100px">
							</td>
				        </tr>
					</table>
				</form>
				
				<c:if test="${temas != null}">
					
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td height="15">
								<table width="120" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td class="foro_fon_tit_iz" width="3">
											&nbsp;
										</td>
										<td class="foro_fon_tit" height="21">
											Temas
										</td>
										<td class="foro_fon_tit_de" width="20">
											&nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
						
						<tr>
							<td>
								<c:choose>
								<c:when test="${fn:length(temas) > 0}">
									<table width="100%" border="0" cellpadding="3" cellspacing="0" class="bor_tabla" style="table-layout: fixed;">
										<tr class="fon_color01" height="24">
											<td width="30" align="center" class="foro_ModuloDerecha" style="background-color: #F9F9F9;">
												&nbsp;
											</td>
											<td width="30" align="center" class="foro_ModuloDerecha" style="background-color: #F9F9F9;">
												&nbsp;
											</td>
											<td align="center" class="foro_ModuloDerecha" style="background-color: #F9F9F9;">
												<strong>Temas/Autor</strong>
											</td>
											<td class="foro_ModuloDerecha" width="100" align="center" style="background-color: #F9F9F9;">
												<strong>Valoraci&oacute;n</strong>
											</td>
											<td width="80" align="center" style="background-color: #F9F9F9;">
												<strong>Respuestas</strong>
											</td>										
										</tr>
										
										<c:forEach items="${temas}" var="tema" varStatus="status">
											<c:if test="${fila != tema.idForo}">
												<tr style="background-color: #E5EFF8;" height="20">
													<td align="center" class="foro_ModuloArriba">
														<c:choose>
															<c:when test="${tema.estado == 0}">
																	<img src="<%=request.getContextPath()%>/img/icon_candado_abierto_out.jpg">
															</c:when>
															<c:otherwise>
																	<img src="<%=request.getContextPath()%>/img/icon_candado.jpeg">
															</c:otherwise>
														</c:choose>
													</td>
													<td colspan="4" class="foro_ModuloArriba" style="font-size: 12px; color: #527BD5; border-right: none;">
														<strong><c:out value="${tema.cuerpo}" /></strong>
													</td>
												</tr>
											</c:if>
											<tr>
												<c:choose>
													<c:when test="${fila != tema.idForo}">
														<td align="center" class="foro_ModuloArribaDerecha">&nbsp;</td>
														<c:set var="fila" value="${tema.idForo}"></c:set>
													</c:when>
													<c:otherwise>
														<td align="center" class="foro_ModuloDerecha">&nbsp;</td>
													</c:otherwise>
												</c:choose>
												<td align="center" class="foro_ModuloArribaDerecha foro_fon_degrade">
													<c:choose>
														<c:when test="${tema.cerrado == 0 && tema.estado == 0}">
																<img src="<%=request.getContextPath()%>/img/icon_candado_abierto_out.jpg">
														</c:when>
														<c:otherwise>
																<img src="<%=request.getContextPath()%>/img/icon_candado.jpeg">
														</c:otherwise>
													</c:choose>
												</td>											
												<td class="foro_ModuloArribaDerecha foro_fon_degrade" style="white-space: nowrap;">
													<a style="color: black;" href="<%=request.getContextPath()%>/foro/IngresarTema.action?idForo=<c:out value="${tema.idForo}"/>&idTema=<c:out value="${tema.idTema}"/>">
													<strong><c:out value="${tema.titulo}"/></strong></a>
													<br><br>
													por <c:out value="${tema.nombreUsuario}" /> &nbsp;
													( fecha: <c:out value="${tema.fechaCreacionToString}" /> )
												</td>
												<td align="left"  class="foro_ModuloArribaDerecha foro_fon_degrade">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr>
															<c:if test="${tema.valoracion>0}">
																<c:forEach begin="1" end="${tema.valoracion}">
																	<td align="center" width="16">
																		<img src="<%=request.getContextPath()%>/img/icon_importante_y.gif" width="12" height="12">
																	</td>
																</c:forEach>
																<td>
																	&nbsp;
																</td>
															</c:if>
														</tr>
													</table>
												</td>
												<td align="center"  class="foro_ModuloArriba foro_fon_degrade">
													<c:out value="${tema.totalRespuestas}"></c:out>
												</td>											
											</tr>
											
											<c:forEach items="${tema.mensajes}" var="mensaje" varStatus="status">
												<tr>
													<td>&nbsp;</td>
													<c:choose>
														<c:when test="${1 == status.count}">
															<td class="foro_ModuloArribaDerecha">&nbsp;</td>
														</c:when>
														<c:otherwise>
															<td class="foro_ModuloDerecha">&nbsp;</td>
														</c:otherwise>
													</c:choose>
													<td class="foro_ModuloArriba" style="background-color:#F9F9F9; border-bottom:1px solid #EAF1F7;">
														<a style="color: black;" href="<%=request.getContextPath()%>/foro/IngresarTema.action?idForo=<c:out value="${tema.idForo}"/>&idTema=<c:out value="${tema.idTema}"/>&pagActual=<c:out value="${mensaje.estado}"/>#<c:out value="${mensaje.idMensaje}"/>">
															<strong>Respuesta # <c:out value="${status.count}" /></strong>
														</a>
													</td>
													<td colspan="2" align="right" class="foro_ModuloArriba" style="background-color:#F9F9F9; border-bottom:1px solid #EAF1F7; padding-right: 5px;">
														Enviado: <c:out value="${mensaje.fechaCreacionToString}" />
													</td>
												</tr>
												<tr>
													<td colspan="2" class="foro_ModuloDerecha">&nbsp;</td>
													<td colspan="3" style="padding:15px;">
														<div style="overflow: auto; width: 100%; max-height: 100px;">
															<c:out value="${mensaje.cuerpo}" escapeXml="false" />
														</div>
													</td>
												</tr>
											</c:forEach>
											
										</c:forEach>
										<tr>
											<td>&nbsp;</td>
											<td colspan="4" class="foro_ModuloArriba">&nbsp;												</td>
										</tr>									
									</table>
								</c:when>
								<c:otherwise>
									<table width="100%" border="0" cellpadding="3" cellspacing="0" class="bor_tabla"">
										<td align="center" style="padding: 20px;"><b><i>No se encontraron registros.</i></b></td>
									</table>
								</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</table>
			 
		 		</c:if>
			 
			</div>			
			<div id="pie">
				<%@include file="../pie.jsp"%>
			</div>	
		</div>			
	</body>
</html>
