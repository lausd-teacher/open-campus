<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@  page import="edu.tecsup.lms.modelo.Usuario"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<title><s:text name="titulo.campus.virtual" />
		</title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" language="javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>		
	</head>
	
	<%@include file="/comun/capas/reloj.jsp"%>
	<body onload="mostrarReloj();">
		<div id="contenedor">
			<%@include file="../bienvenida.jsp"%>
			<div id="cuerpo">				
				<div id="principal">
				
					<c:choose>
						<c:when test="${fn:length(cursos)>0}">
							
							
							<div id="inicio" style="padding-bottom: 5px;">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="100%">
											<table width="962" border="0" align="left" cellpadding="0" cellspacing="0" class="tabla01">
												<tr class="fon_tit_curso">
													
													<td class="tit_cab" height="20">
														Cursos Virtuales
													</td>
													
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td align="left">
											<div id="otros" style="margin: 0pt 0pt;">
												<center><c:out value="${message}"></c:out></center>
												<table width="98%" border="0" align="left" cellpadding="10" cellspacing="0"
													class="tabla09">
													<tr align="center">
														
														
														<c:forEach items="${cursos}" var="ficha" varStatus="fila">
														
														
														<td width="220px" align="center" class="ficha_tabla" valign="top">
															<div style="border: 2px black;">
																<table width="200" border="0" cellspacing="0" cellpadding="0" class="tabla_sin_layout">
																	<tr class="fon_curso">
																		<td height="21" align="left" >
																			<a
																				href="<%=request.getContextPath()%>/aulavirtual/AulaVirtual.action?id=<c:out value="${ficha.idFicha}"/>"
																				class="tit_cab"> <c:out value="${ficha.curso.nombre}"/> </a>
																		</td>
																		<td class="fon_cab_curso_derecha" width="3">&nbsp;
																		</td>
																	</tr>
																	<tr>
																		<td class="bor_caja_curso" colspan="2">
																			<table width="200" border="0" align="center" cellpadding="0" cellspacing="0"
																				style="padding-left: 3px;padding-right: -3px;">
																				
																				<tr>
																					<td width="85" height="112" align="center" valign="middle">
																						<img src="<%=request.getContextPath()%>/VerFoto.action?id=<c:out value="${ficha.docentePrincipal.usuario.id}"/>" width="85" border="0"/> 
																					</td>
																					<td width="115" align="left">
																						<p>
																							<c:out value="${ficha.docentePrincipal.usuario.persona.nombreCompleto}"/>
																						</p>
																					</td>
																				</tr>
																				<tr>
																					<td colspan="2">
																						<hr align="center" width="170" />
																					</td>
																				</tr>
																				
																				<c:if test="${ficha.matriculaActual.rol.idRol == 2 || ficha.matriculaActual.rol.idRol == 3}">
																						
																				<tr>
																					<td align="left">
																						<s:text name="portal.cursos.fechaacceso"/>
																					</td>
																					<td align="left">
																						:
																						<c:out value="${ficha.periodo.fechaEdicionToString}"></c:out>
																					</td>
																				</tr>
																				
																				</c:if>
																				
																				<tr>
																					<td align="left">
																						<s:text name="portal.cursos.fechainicio"/>
																					</td>
																					<td align="left">
																						:
																						<c:out value="${ficha.periodo.fechaInicioToString}"></c:out>
																					</td>
																				</tr>
																				<tr>
																					<td align="left">
																						<s:text name="portal.cursos.fechafin"/>
																					</td>
																					<td align="left">
																						:
																						<c:out value="${ficha.periodo.fechaFinToString}"></c:out>
																					</td>
																				</tr>
																				
																				<c:if test="${ficha.matriculaActual.rol.idRol == 2 || ficha.matriculaActual.rol.idRol == 3}">
																				
																				<tr>
										
																					<td align="left">
																						<s:text name="portal.cursos.fechacierre"/>
																					</td>
																					<td align="left">
																						:
																						<c:out value="${ficha.periodo.fechaRevisionToString}"></c:out>
																					</td>
																					
																				</tr>
																				
																				</c:if>
																				
																				
																				
																				<tr>
																					<c:if test="${ficha.silabo.flagDialogosTotal > 0}">
																					<td align="left">
																						<s:text name="portal.cursos.dialogos"/>
																					</td>
																					<td align="left">
																						:
																						<c:out value="${ficha.silabo.flagDialogosTotal}"></c:out>
																						<img src="<%=request.getContextPath()%>/img/flag.gif" alt="flag" width="8"
																							height="11" />
																					</td>
																					</c:if>
																				</tr>
																				<tr>
																					<c:if test="${ficha.silabo.flagLaboratoriosTotal > 0}">
																					<td align="left">
																						<s:text name="portal.cursos.laboratorios"/>
																					</td>
																					<td align="left">
																						:
																						<c:out value="${ficha.silabo.flagLaboratoriosTotal}"></c:out>
																						<img src="<%=request.getContextPath()%>/img/flag.gif" alt="flag" width="8"
																							height="11" />
																					</td>
																					</c:if>
																				</tr>
																				<tr>
																					<c:if test="${ficha.silabo.flagTIndividualTotal > 0}">
																					<td align="left">
																						<s:text name="portal.cursos.trabajos"/>
																					</td>
																					<td align="left">
																						:
																						<c:out value="${ficha.silabo.flagTIndividualTotal}"></c:out>
																						<img src="<%=request.getContextPath()%>/img/flag.gif" alt="flag" width="8"
																							height="11" />
																					</td>
																					</c:if>
																				</tr>
																				<tr>
																					<c:if test="${ficha.silabo.flagTGrupalTotal > 0}">
																					<td align="left">
																						<s:text name="portal.cursos.grupales"/>
																					</td>
																					<td align="left">
																						:
																						<c:out value="${ficha.silabo.flagTGrupalTotal}"></c:out>
																						<img src="<%=request.getContextPath()%>/img/flag.gif" alt="flag" width="8"
																							height="11" />
																					</td>
																					</c:if>
																				</tr>
																				<tr>
																					<c:if test="${ficha.silabo.flagDebatesTotal > 0}">
																					<td align="left">
																						<s:text name="portal.cursos.debates"/>
																					</td>
																					<td align="left">
																						:
																						<c:out value="${ficha.silabo.flagDebatesTotal}"></c:out>
																						<img src="<%=request.getContextPath()%>/img/flag.gif" alt="flag" width="8"
																							height="11" />
																					</td>
																					</c:if>
																				</tr>
		
		
																				
																				<tr>
																					<td colspan="2" height="10">&nbsp;
																						
																					</td>
																				</tr>
																				
																				
																			</table>
																		</td>
																	</tr>
																</table>
																<table cellpadding="0" cellspacing="0" width="200">
																	<tr height="20" style="background-color: #E0EAF3;">
																		<td align="right" olspan="2">
																			<a
																				href="<%=request.getContextPath()%>/aulavirtual/AulaVirtual.action?id=<c:out value="${ficha.idFicha}"/>"
																				class="ingr_curso" style="padding-right: 5px;"><s:text name="portal.servicios.link" /></a>
																		</td>
																	</tr>
																</table>
															</div>
														</td>
														
														
														</c:forEach>
														
		
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</c:when>
						<c:otherwise>
							
							
							<table width="750" border="0" cellpadding="3" cellspacing="0" class="bor_tabla">
								<tr class="fon_cab">
									<td height="15" class="tit_cab">
										<s:text name="portal.cursos.titulo"/>
									</td>
								</tr>
								<tr>
									<td align="center" class="moduloAbajo1">
										<strong><s:text name="portal.cursos.vacio"/></strong>
									</td>
								</tr>
							</table>
							
						</c:otherwise>
					</c:choose>
					
				</div>
			</div>
			<div id="pie">
				<%@include file="../pie.jsp"%>
			</div>
		</div>
	</body>
</html>
