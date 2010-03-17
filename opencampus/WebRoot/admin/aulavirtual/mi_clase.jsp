<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}' />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" />
		</title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/js/autocomplete/autocomplete.css" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jPrototype.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/util.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/autocomplete/autocomplete.js"></script>	
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/admin/aulavirtual/mi_clase.js'></script>
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/js/tooltip/tooltip.js'></script>
		
	</head>
	<body>
		<div id="pop_up" style="width: 890px;">
			<div id="prin_01" style="width: 890px;">
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
			<div id="pop_cuerpo" style="width: 870px;">
				<table width="860" align="center" cellpadding="0" cellspacing="0"
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
									<td width="20" align="center" class="texto2">
										&nbsp;
									</td>
									<td width="20" align="center" class="moduloArriba1">
										&nbsp;
									</td>
									<td width="82" height="23" align="center" class="texto2">
										<strong>Usuario</strong>
									</td>
									<td  align="center" class="texto2">
										<strong> Paterno </strong>
									</td>
									<td align="center" class="texto2">
										<strong>Materno</strong>
									</td>
									<td  align="center" class="texto2">
										<strong>1er.Nombre</strong>
									</td>
									<td  align="center" class="texto2">
										<strong>2do.Nombre</strong>
									</td>
									<td width="30" align="center" class="moduloArriba1">
										<input type="checkbox" onClick="todos(this)">
									</td>
									<td width="30" align="center" class="moduloArriba1">
										<img src="<%=request.getContextPath()%>/img/icon_mail_send.gif" alt="Enviar Correo"
												border="0" onClick="masivo()" style="cursor: pointer"/>
									</td>
									<td width="30" align="center" class="moduloArriba1">
										&nbsp;
									</td>
								</tr>

								<c:forEach items="${grupos}" var="grupo" varStatus="status">

									<tr>
										<td height="23" colspan="10" align="left" class="moduloArriba2"><c:out value="${grupo.nombre}"></c:out> </td>
									</tr>
									
									<c:choose>
										<c:when test="${fn:length(grupo.matriculas) == 0}">
											<tr>
												<td height="23" colspan="10" align="center">
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
													<td height="23" align="center">
													
														<c:choose>
													  		<c:when test="${matricula.estado==1}">
													  			<img src="<%=request.getContextPath() %>/img/activo.gif" border="0" alt="Hacer No Disponible" style="cursor: pointer;"
													  			onclick="cambiarEstado(this,<c:out value="${matricula.idMatricula}"/>)" />
													  		</c:when>
													  		<c:otherwise>
													  			<img src="<%=request.getContextPath() %>/img/desactivo.gif" border="0" alt="Hacer Disponible" style="cursor: pointer;"
													  			onclick="cambiarEstado(this,<c:out value="${matricula.idMatricula}"/>)"/>
													  		</c:otherwise>
													  	</c:choose>
													
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
														<img src="<%=request.getContextPath()%>/img/icon_trash.gif" border="0" class="link_negro" style="cursor: pointer;"
															onclick="javascript:eliminarMatricula('<c:out value="${matricula.idMatricula}"/>','<c:out value="${matricula.usuario.persona.nombreCompleto}"/>');" />
													</td>
													<td>
													
													<c:if test="${grupo.idRol == 2}">
														<c:choose>
													  		<c:when test="${matricula.principal==1}">
													  			<img src="<%=request.getContextPath() %>/img/icon_importante_y.gif" border="0" alt="Hacer No Disponible" class="principal" style="cursor: pointer;"
													  			onclick="cambiarPrincipal(this,<c:out value="${matricula.idMatricula}"/>)" />
													  		</c:when>
													  		<c:otherwise>
													  			<img src="<%=request.getContextPath() %>/img/icon_importante_n.gif" border="0" alt="Hacer Disponible" class="principal" style="cursor: pointer;"
													  			onclick="cambiarPrincipal(this,<c:out value="${matricula.idMatricula}"/>)"/>
													  		</c:otherwise>
													  	</c:choose>
													</c:if>
														
													</td>
												</tr>
												
											</c:forEach>
											
										</c:otherwise>
									</c:choose>
									
									<tr>
										<td class="moduloArriba1">&nbsp;</td>
										<td colspan="9" class="moduloArriba1" align="right">
											<div style="display: block; float: left;"></div>
											<div style="display: block; float: right;"><button onclick="mostrarBusqueda(this, <c:out value="${grupo.idRol}"/>)" class="form_button">Matricular</button></div>
										</td>
									</tr>
								
								</c:forEach>
								
							</table>
						</td>
					</tr>
				</table>
				<div id="form_matricula" style="display: none;">
					<form method="post" action="<%=request.getContextPath()%>/admin/aulavirtual/Matricular.action">
						<input type="text" id="usuario" maxlength="255" class="form_text" size="60" 
							onkeyup="$('idusuario').value =  0;">
						<input type="hidden" id="idusuario" name="idusuario" value="0">
						<input type="hidden" id="idrol" name="idrol" value="0">
						<input type="submit" value="Guardar" class="form_button">
					</form>
				</div>
				<script type="text/javascript">
						new Autocomplete('usuario', function() { 
							return '<%=request.getContextPath()%>/admin/aulavirtual/BuscarNoMatriculadosAutocompletar.action?nombre=' + this.value; 
						});
				</script>
			</div>
			<div id="pie" style="width: 890px;">
			</div>
		</div>
	</body>
</html>
