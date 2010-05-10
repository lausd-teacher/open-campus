<%
	//response.setContentType("text/plain;charset=ISO-8859-1"); //Para no tener problemas con ñs y tildes
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="../../error_action.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@  page import="edu.opencampus.lms.util.Constante"%>
<%@  page import="edu.opencampus.lms.modelo.Usuario"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%
	String n = (String) session.getAttribute("n");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title><s:text name="titulo.campus.virtual" />
		</title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src='<%=request.getContextPath()%>/js/jComponente.js'></script>	
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/js/aula_virtual.js'></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/aviso.js"></script>
	</head>
	<body onunload="cerrarAula()">
		
			<div id="contenedor">
			
				<%@include file="../comun/bienvenida_aula.jsp"%>
				
				<div id="cuerpo">
					<div id="bienvenida" Xstyle="display: none;">
						<table cellspacing="3" border="0">
							<tr>						
								<td width="10" class="menu_prin01" align="center">&nbsp;
									
								</td>
								<td width="65" align="center">
									<span class="menu"
										onClick="javascript:abrirPlanDocente('<%=request.getContextPath()%>/aulavirtual/SilaboDeCurso.action','PlanDocente','530','350');">
										<s:text name="aula.alumno.menu.plan_docente"/> </span>
								</td>
								<td width="10" class="menu_prin01" align="center">
									|
								</td>
								<td width="47" align="center">
									<span class="menu"
										onclick="javascript:abrirReporte('<%=request.getContextPath()%>/aulavirtual/Reporte.action','Reporte','640','350');">
										<s:text name="aula.alumno.menu.reporte"/> </span>
								</td>
								<td width="10" class="menu_prin01" align="center">
									|
								</td>
								<td width="87" align="center" style="display: none">
									<span class="menu"
										onclick="javascript:abrirReporte('<%=request.getContextPath()%>/aulavirtual/ReporteNotas.action','Reporte de Notas','640','350');">
										<s:text name="aula.alumno.menu.reporte_notas"/> </span>
								</td>
								<td width="10" class="menu_prin01" align="center" style="display: none">
									|
								</td>
								<td width="52" align="center">
									<span class="menu"
										onclick="javascript:abrirMiClase('<%=request.getContextPath()%>/aulavirtual/MiClase.action','MiClase','560','350');">
										<s:text name="aula.alumno.menu.mi_clase"/> </span>
								</td>
								<td width="5" class="menu_prin01" align="center">
									|
								</td>
								<td width="52" align="center">
									<span class="menu"
										onclick="javascript:abrirVitrina('<%=request.getContextPath()%>/aulavirtual/Mensajes.action','Mensajes','560','350');">
										<s:text name="aula.alumno.menu.avisos"/></span>
								</td>
								<td width="10" class="menu_prin01" align="center">
									|
								</td>
								<td width="45" align="center">
									<span class="menu"
										onclick="javascript:abrirLectura('<%=request.getContextPath()%>/aulavirtual/Recursos.action','Recursos','560','350');">
										<s:text name="aula.alumno.menu.lecturas"/> </span>
								</td>
								<td width="10" class="menu_prin01" align="center">
									|
								</td>
								<td width="45" align="center" style="display: none">
									<span class="menu"
										onclick="javascript:abrirInforme('<%=request.getContextPath()%>/aulavirtual/Informe.action');">
										<s:text name="aula.alumno.menu.informe"/> </span>
								</td>
							</tr>
						</table>
					</div>
					<div id="pop_cuerpo">

						<%-- EXISTEN UNIDADES --%>

						<c:if test="${aula!=null && fn:length(aula.silabo.unidades)>0}">

							<table width="100%" align="center" cellpadding="3" cellspacing="0" class="bor_tabla" border="0" style="table-layout: fixed;">
								<caption class="fon_cab tit_cab"><s:text name="aula.alumno.unidades"/></caption>
								<c:forEach items="${aula.silabo.unidades}" var="unidad" varStatus="fila">
									<c:if test="${unidad.estado == 1}">
										<tr class="tabla01_fila<c:out value="${fila.count%2 + 1}"/>">
											<td align="center" width="16" class="texto1"><c:out value="${fila.count}"/> </td>
											
											<!-- ********** Texto ********** -->
											<c:set var="texto" value="${unidad.recursos[0]}" />
											
											<c:if test="${aula.cantidadTextos > 0}">
												<td align="center" class="texto1" width="24">
													<img src="<%=request.getContextPath()%>/img/icon_libro.gif" style="cursor: pointer;" alt="Ver Texto"
														onclick="abrirTexto('<%=request.getContextPath()%>/aulavirtual/VerRecurso.action?ruta=1&id=<c:out value="${unidad.idUnidad}"/>');"/>
												</td>
											</c:if>
											
											<!-- ********** Repaso ********** -->
											<c:set var="repaso" value="${unidad.recursos[1]}" />
											
											<c:if test="${aula.cantidadRepasos > 0}">
												<td align="left">
													<span style="cursor: pointer; text-decoration: underline; white-space: nowrap;" 
														onclick="abrirRepaso('<%=request.getContextPath()%>/aulavirtual/VerRecurso.action?ruta=3&id=<c:out value="${unidad.idUnidad}"/>');">
														<c:out value="${unidad.nombreCompleto}"/>
													</span>
												</td>
												
												<td align="center" class="texto1" width="24">
													<img src="<%=request.getContextPath()%>/img/icon_download.gif" style="cursor: pointer;" alt="Descargar Repaso"
														onclick=""/>
												</td>
											</c:if>
											
											<!-- ********** Laboratorio ********** -->
											<c:set var="lab" value="${unidad.recursos[2]}" />
											
											<c:if test="${aula.cantidadLaboratorios > 0}">
												<td align="center" width="10">
													&nbsp;
												</td>
												
												<td align="center" width="22">
													<img src="<%=request.getContextPath()%>/img/icon_lab.gif" style="cursor: pointer;" alt="Ver Laboratorio"
														onClick="abrirLaboratorioPdf('<%=request.getContextPath()%>/aulavirtual/VerRecurso.action?ruta=2&id=<c:out value="${unidad.idUnidad}"/>');"/>
												</td>
												
												<td align="left" class="texto1" width="10">
													<c:choose>
														<c:when test="${unidad.flagLaboratorios > 0}">
															<img src="<%=request.getContextPath()%>/img/flag.gif" alt="Pendiente"/>
														</c:when>
														<c:otherwise>&nbsp;</c:otherwise>
													</c:choose>
												</td>
											</c:if>
											
											<!-- ********** Dialogo ********** -->
											<c:set var="dia" value="${unidad.recursos[3]}" />
											
											<c:if test="${aula.cantidadDialogos > 0}">
												<td align="center" width="10">
													&nbsp;
												</td>
												
												<td align="center" width="22">
													<img src="<%=request.getContextPath()%>/img/icon_dialog.gif" style="cursor: pointer;" alt="Sala de Diálogo"
														onClick="abrirDialogo('<%=request.getContextPath()%>/aulavirtual/dialogo/Cargar.action?idUnidad=<c:out value="${unidad.idUnidad}"/>','Dialogo','620','400');"/>
												</td>
												
												<td align="left" class="texto1" width="10">
													<c:choose>
														<c:when test="${unidad.flagDialogos > 0}">
															<img src="<%=request.getContextPath()%>/img/flag.gif" alt="Pendiente"/>
														</c:when>
														<c:otherwise>&nbsp;</c:otherwise>
													</c:choose>
												</td>
											</c:if>
											
											<!-- ********** Trabajo ********** -->
											<c:set var="trabajo" value="${unidad.recursos[4]}" />
											
											<c:if test="${aula.cantidadTrabajos > 0}">
												<td align="center" width="10">
													&nbsp;
												</td>
												
												<td align="center" width="22">
													<img src="<%=request.getContextPath()%>/img/icon_trab.gif" style="cursor: pointer;" alt="Trabajo Individual"
														onClick="abrirTrabajo('<%=request.getContextPath()%>/aulavirtual/tindividual/Cargar.action?idUnidad=<c:out value="${unidad.idUnidad}"/>')"/>
												</td>
												
												<td align="left" class="texto1" width="10">
													<c:choose>
														<c:when test="${unidad.flagTIndividual > 0}">
															<img src="<%=request.getContextPath()%>/img/flag.gif" alt="Pendiente"/>
														</c:when>
														<c:otherwise>&nbsp;</c:otherwise>
													</c:choose>
												</td>
											</c:if>
											
											<!-- ********** Grupal ********** -->
											<c:set var="trabajo" value="${unidad.recursos[5]}" />
											
											<c:if test="${aula.cantidadGrupales > 0}">
												<td align="center" width="10">
													&nbsp;
												</td>
												
												<td align="center" width="22">
													<img src="<%=request.getContextPath()%>/img/icon_trab_grup.gif" style="cursor: pointer;" alt="Trabajo Grupal"
														onClick="abrirTrabajo('<%=request.getContextPath()%>/aulavirtual/tgrupal/Cargar.action?idUnidad=<c:out value="${unidad.idUnidad}"/>')"/>
												</td>
												
												<td align="left" class="texto1" width="10">
													<c:choose>
														<c:when test="${unidad.flagTGrupal > 0 || unidad.flagDebates > 0}">
															<img src="<%=request.getContextPath()%>/img/flag.gif" alt="Pendiente"/>
														</c:when>
														<c:otherwise>&nbsp;</c:otherwise>
													</c:choose>
												</td>
											</c:if>
											
											<!-- ********** Test ********** -->
											<c:set var="test" value="${unidad.recursos[6]}" />
											
											<c:if test="${aula.cantidadTest > 0}">
												<td align="center" width="22">
													<img src="<%=request.getContextPath()%>/img/icon_test.gif" style="cursor: pointer;" alt="Evaluación"
														onClick="abrirTestEjecucion('<%=request.getContextPath()%>/aulavirtual/test/Cargar.action?idUnidad=<c:out value="${unidad.idUnidad}"/>')"/>
												</td>
											</c:if>
											
										</tr>
									</c:if>
								</c:forEach>
							</table>
						
						</c:if>
						
					</div>
				</div>
				<div id="pie">
					<%@include file="../../comun/pie.jsp"%>
				</div>
			</div>
		
	</body>
</html>
