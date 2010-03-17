<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="edu.tecsup.lms.modelo.aulavirtual.reporte.ReporteDetalle"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}' />
<%@  page import="edu.tecsup.lms.util.Formato"%>
<%@  page import="java.util.GregorianCalendar"%>
<%@  page import="java.util.Calendar"%>
<%@  page import="java.text.DateFormatSymbols"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%@  page import="edu.tecsup.lms.modelo.Usuario"%>
<%@  page import="edu.tecsup.lms.modelo.aulavirtual.reporte.ReporteDetalle"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" />
		</title>
		<link href="<c:out value='${contextPath}'/>/estilos/estilos.css" rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/jComponente.js"></script>
<%
Usuario usuario = (Usuario) request.getSession().getAttribute(Constante.USUARIO_ACTUAL);	
ReporteDetalle rdetalle = (ReporteDetalle)request.getAttribute("REPORTE_DETALLE");
%>	
	</head>
	<body>
	
		<c:set var="aula" value='${sessionScope.aula_actual}' />
		<c:set var="reporteDetalle" value='${REPORTE_DETALLE}'></c:set>
		<c:set var="tipo" value='${ATRAS_PAGINA}'></c:set>
		<div id="pop_up" style="width: 100%;">
			<div id="prin_01" style="width: 100%;">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="90%">
							<strong>Curso : <c:out value="${reporteDetalle.nombreCurso}"/></strong>
						</td>
						<td width="5%"><a href="#" class="salir" onClick="window.print()">Imprimir</a> </td>
						<td width="3%"><a href="#" class="salir" onClick="window.print()"><img
									src="<%=request.getContextPath()%>/img/impresora.gif" width="13" height="13" border="0" /></a></td>
						<td width="5%">
							<a href="#" class="salir" onClick="window.close()">Cerrar</a>
						</td>
						<td width="5%">
							<a href="#" class="salir" onClick="window.close()"> <img
									src="<c:out value='${contextPath}'/>/img/salir_x.gif" width="13" height="13" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>
			<div id="pop_cuerpo" style="width: 96%;">
				<table width="100%" align="center" cellpadding="3" cellspacing="0" class="bor_tabla" border="0">
					<tr class="fon_cab">
						<td height="20" class="tit_cab">
							Auditor&iacute;a
						</td>
					</tr>
					<tr>
						<td>
							<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%"
								class="reporte_table">
								<tr>
									<td height="23" colspan="5" class="datos">
										<strong style="margin-left: 5px;">Datos Generales</strong>
									</td>
									
								</tr>
								<tr>
									<td height="23" width="150" class="texto1">
										<label>
											<strong>Fecha Reporte :</strong>
										</label>
									</td>
									<td height="23" >
										<label>

											<%=Formato.getStringDeDateCompleto(new GregorianCalendar())%>

										</label>
									</td>
									<td class="texto1">
									<label>
											<strong>Carrera : </strong>
										</label>
									</td>
									<td>
									<label>
											<c:out value="${reporteDetalle.nombreEspecialidad}" default=""/>
										</label>
									</td>
									<td rowspan="4">									
										<ct:Foto idUsuario="<%=rdetalle.getIdUsuario() %>" />								
									</td>
									
								</tr>
								
								<tr>
									<td height="23" class="texto">
										<label>
											<strong>Nombre : </strong>
										</label>
									</td>
									<td height="23" class="derecha">
										<label>
											<c:out value="${reporteDetalle.nombreCompleto}"/>
										</label>
									</td>
									<td height="23" class="texto">
										<label>
											<strong>Curso : </strong>
										</label>
									</td>
									<td height="23" class="derecha">
										<label>
											<c:out value="${reporteDetalle.nombreCurso}"/>
											<c:out value="${reporteDetalle.formacionSeccion}"/>
										</label>
									</td>
									
								</tr>
					
							
								<tr>
									<td height="23" class="texto1">
										<label>
											<strong>Fecha Inicio : </strong>
										</label>
									</td>
									<td height="23" >
										<label>
											<c:out value="${reporteDetalle.stringFecha1}"/>
										</label>
									</td>
									<td height="23" class="texto1">
										<label>
											<strong>Fecha Fin : </strong>
										</label>
									</td>
									<td height="23">
										<label>
											<c:out value="${reporteDetalle.stringFecha2}"/>
										</label>
									</td>
									
								</tr>

								<tr>
									<td height="23" class="texto">
										<label>
											<strong>Avisos publicados : </strong>
										</label>
									</td>
									<td height="23" class="derecha">
										<label>
											<c:out value="${reporteDetalle.alerta}"/> / <c:out value="${reporteDetalle.totalAlerta}"/>&nbsp;
										</label>
									</td>
									<td height="23" class="texto">
										<label>
											<strong>Lecturas publicadas: </strong>
										</label>
									</td>
									<td height="23" class="derecha">
										<label>
											<c:out value="${reporteDetalle.lectura}"/> / <c:out value="${reporteDetalle.totalLectura}"/>&nbsp;
										</label>
									</td>	
																
								</tr>
								
								<tr>
									<td height="23" class="texto1">
										<label>
											<strong>Ingresos al curso : </strong>
										</label>
									</td>
									<td height="23" >
										<label>
											<c:out value="${reporteDetalle.cantidadIngreso}"/>
										</label>									
										
										</td>
									<td>									
									</td>
									<td>
									</td>
								
									
								</tr>

								<c:if test="${busquedaFecha1 != null}">
									<tr>
										<td height="23" class="texto">
											<label>
												<strong>Auditor&iacute;a Inicio : </strong>
											</label>
										</td>
										<td height="23"  class="derecha">
											<label>
												<c:out value="${busquedaFecha1}"/>
											</label>
										</td>
										<td height="23" class="texto">
											<label>
												<strong>Auditor&iacute;a Fin : </strong>
											</label>
										</td>
										<td height="23" class="derecha">
											<label>
												<c:out value="${busquedaFecha2}"/>
											</label>
										</td>
										
									</tr>
								</c:if>

							</table>
						</td>
						
					</tr>
					<tr>
						<td height="10">&nbsp;
						</td>
					</tr>
					
					<tr>
						<td height="23">
							<table width="100%" border=0 height="100%" cellpadding="0" cellspacing="0" class="reporte_table" style="table-layout: fixed;">
								<tr>
									<td height="0" width="100">
										
									</td>
								</tr>
								
								
								
							<c:if test="${aula.cantidadTrabajos != 0}">
								
								<tr>
									<td height="23" class="moduloFondo">
										<strong style="margin-left: 10px;">Trabajos</strong>
									</td>
									
									<c:forEach begin="1" end="${reporteDetalle.cantidadUnidades}" step="1" var="n">
									
										<td align="center" class="moduloFondo">
											<strong><c:out value="${n}"></c:out> </strong>
										</td>
									
									</c:forEach>
									
<%--									*** columnas adicionales ***--%>
									
								</tr>
								<tr>
									<td class="texto1" height="23">
										<label>
											<strong>Estado</strong>
										</label>
									</td>
									
									
									<c:forEach items="${reporteDetalle.recursoTrabajo}" var="trabajo" varStatus="fila">
									
										<th rowspan="4" valign="top">
										<c:choose>
											<c:when test="${trabajo.publicado != null}">
												<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" style="table-layout: fixed;">
													<tr>
														<td width="100%" height="23" class="unidades1">
															<c:out value="${trabajo.publicado}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades">
															<c:out value="${trabajo.entregados}"/>/
															<c:out value="${trabajo.total}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades1">
															<c:out value="${trabajo.calificados}"/>/
															<c:out value="${trabajo.total}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidadesAbajo">
															<c:out value="${trabajo.expirados}"/>/
															<c:out value="${trabajo.total}"/>&nbsp;
														</td>
													</tr>
<%--													<tr>--%>
<%--														<td height="23" class="unidades1" style="white-space: nowrap; overflow: hidden;">--%>
<%--															<c:out value="${trabajo.fechaActivacion}"/>&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
<%--													<tr>--%>
<%--														<td height="23" class="unidadesAbajo" style="white-space: nowrap;overflow: hidden;">--%>
<%--															<c:out value="${trabajo.fechaEntrega}"/>&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
												</table>
											</c:when>
											<c:otherwise>
												<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" style="table-layout: fixed;">
													<tr>
														<td width="100%" height="23" class="unidades1">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades1">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidadesAbajo">&nbsp;
															
														</td>
													</tr>
<%--													<tr>--%>
<%--														<td height="23" class="unidades1" style="white-space: nowrap; overflow: hidden;">--%>
<%--															&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
<%--													<tr>--%>
<%--														<td height="23" class="unidadesAbajo" style="white-space: nowrap;overflow: hidden;">--%>
<%--															&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
												</table>
											</c:otherwise>
											</c:choose>
										</th>
										
									</c:forEach>
								
									
								</tr>

								<tr>
									<td class="texto" height="23">
										<label>
											<strong>Entregados</strong>
										</label>
									</td>
								</tr>
								<tr>
									<td class="texto1" height="23">
										<label>
											<strong>Calificados</strong>
										</label>
									</td>
								</tr>
								<tr>
									<td class="textoAbajo" height="23">
										<label>
											<strong>Extemp.</strong>
										</label>
									</td>
								</tr>
<%--								<tr>--%>
<%--									<td class="texto1" height="23">--%>
<%--										<label>--%>
<%--											<strong>Activaci&oacute;n</strong>--%>
<%--										</label>--%>
<%--									</td>--%>
<%--								</tr>--%>
<%--								<tr>--%>
<%--									<td class="textoAbajo" height="23">--%>
<%--										<label>--%>
<%--											<strong>Entrega</strong>--%>
<%--										</label>--%>
<%--									</td>--%>
<%--								</tr>--%>
								
							</c:if>
							<tr>
									<td class="datosAbajo" colspan="<c:out value="${reporteDetalle.cantidadUnidades+1}"/>" height="23">
										&nbsp;
									</td>
								</tr>
							
							<c:if test="${aula.cantidadLaboratoriosCalificados != 0}">
							
							
								<tr>
									<td height="23" class="moduloFondo">
										<strong style="margin-left: 10px;">Laboratorios</strong>
									</td>
									
									<c:forEach begin="1" end="${reporteDetalle.cantidadUnidades}" step="1" var="n">
									
										<td align="center" class="moduloFondo">
											<strong><c:out value="${n}"></c:out> </strong>
										</td>
									
									</c:forEach>
									
<%--									*** columnas adicionales ***--%>
									
								</tr>
								<tr>
									<td class="texto1" height="23">
										<label>
											<strong>Estado</strong>
										</label>
									</td>
									
									
									<c:forEach items="${reporteDetalle.recursoLaboratorio}" var="laboratorio" varStatus="fila">
									
										<th rowspan="4" valign="top">
											<c:choose>
											<c:when test="${laboratorio.publicado != null}">
												<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" style="table-layout: fixed;">
													<tr>
														<td width="100%" height="23" class="unidades1">
															<c:out value="${laboratorio.publicado}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades">
															<c:out value="${laboratorio.entregados}"/>/
															<c:out value="${laboratorio.total}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades1">
															<c:out value="${laboratorio.calificados}"/>/
															<c:out value="${laboratorio.total}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidadesAbajo">
															<c:out value="${laboratorio.expirados}"/>/
															<c:out value="${laboratorio.total}"/>&nbsp;
														</td>
													</tr>
<%--													<tr>--%>
<%--														<td height="23" class="unidades1" style="white-space: nowrap; overflow: hidden;">--%>
<%--															<c:out value="${laboratorio.fechaActivacion}"/>&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
<%--													<tr>--%>
<%--														<td height="23" class="unidadesAbajo" style="white-space: nowrap;overflow: hidden;">--%>
<%--															<c:out value="${laboratorio.fechaEntrega}"/>&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
												</table>
											</c:when>
											<c:otherwise>
												<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" style="table-layout: fixed;">
													<tr>
														<td width="100%" height="23" class="unidades1">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades1">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidadesAbajo">&nbsp;
															
														</td>
													</tr>
<%--													<tr>--%>
<%--														<td height="23" class="unidades1" style="white-space: nowrap; overflow: hidden;">--%>
<%--															&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
<%--													<tr>--%>
<%--														<td height="23" class="unidadesAbajo" style="white-space: nowrap;overflow: hidden;">--%>
<%--															&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
												</table>
											</c:otherwise>
											</c:choose>
										</th>
										
									</c:forEach>
								
									
								</tr>

								<tr>
									<td class="texto" height="23">
										<label>
											<strong>Entregados</strong>
										</label>
									</td>
								</tr>
								<tr>
									<td class="texto1" height="23">
										<label>
											<strong>Calificados</strong>
										</label>
									</td>
								</tr>
								<tr>
									<td class="textoAbajo" height="23">
										<label>
											<strong>Extemp.</strong>
										</label>
									</td>
								</tr>
<%--								<tr>--%>
<%--									<td class="texto1" height="23">--%>
<%--										<label>--%>
<%--											<strong>Activaci&oacute;n</strong>--%>
<%--										</label>--%>
<%--									</td>--%>
<%--								</tr>--%>
<%--								<tr>--%>
<%--									<td class="textoAbajo" height="23">--%>
<%--										<label>--%>
<%--											<strong>Entrega</strong>--%>
<%--										</label>--%>
<%--									</td>--%>
<%--								</tr>--%>
							
							</c:if>
							<tr>
									<td class="datosAbajo" colspan="<c:out value="${reporteDetalle.cantidadUnidades+1}"/>" height="23">
										&nbsp;
									</td>
								</tr>
							
							<c:if test="${aula.cantidadGrupales != 0}">
							
								
								<tr>
									<td height="23" class="moduloFondo">
										<strong style="margin-left: 10px;">Grupales</strong>
									</td>
									
									<c:forEach begin="1" end="${reporteDetalle.cantidadUnidades}" step="1" var="n">
									
										<td align="center" class="moduloFondo">
											<strong><c:out value="${n}"></c:out> </strong>
										</td>
									
									</c:forEach>
									
<%--									*** columnas adicionales ***--%>
									
								</tr>
								<tr>
									<td class="texto1" height="23">
										<label>
											<strong>Estado</strong>
										</label>
									</td>
									
									
									<c:forEach items="${reporteDetalle.recursoTrabajoGrupal}" var="grupal" varStatus="fila">
									
										<th rowspan="6" valign="top">
											<c:choose>
											<c:when test="${grupal.publicado != null}">
												<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" style="table-layout: fixed;">
													<tr>
														<td width="100%" height="23" class="unidades1">
															<c:out value="${grupal.publicado}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades">
															<c:out value="${grupal.entregados}"/>/
															<c:out value="${grupal.total}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades1">
															<c:out value="${grupal.calificados}"/>/
															<c:out value="${grupal.estudianteTotal}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades">
															<c:out value="${grupal.asignados}"/>/
															<c:out value="${grupal.total}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades1">
															<c:out value="${grupal.expirados}"/>/
															<c:out value="${grupal.total}"/>&nbsp;
														</td>
													</tr>
<%--													<tr>--%>
<%--														<td height="23" class="unidades" style="white-space: nowrap; overflow: hidden;">--%>
<%--															<c:out value="${grupal.fechaActivacion}"/>&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
<%--													<tr>--%>
<%--														<td height="23" class="unidades1" style="white-space: nowrap;overflow: hidden;">--%>
<%--															<c:out value="${grupal.fechaEntrega}"/>&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
													<tr>
														<td height="23" class="unidadesAbajo">
															<c:out value="${grupal.intervencion}"/>/
															<c:out value="${grupal.intervencionTotal}"/>&nbsp;
														</td>
													</tr>
												</table>
											</c:when>
											<c:otherwise>
												<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" style="table-layout: fixed;">
													<tr>
														<td width="100%" height="23" class="unidades1">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades1">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades1" style="white-space: nowrap; overflow: hidden;">&nbsp;
															
														</td>
													</tr>
<%--													<tr>--%>
<%--														<td height="23" class="unidades" style="white-space: nowrap;overflow: hidden;">--%>
<%--															&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
<%--													<tr>--%>
<%--														<td height="23" class="unidades1" style="white-space: nowrap;overflow: hidden;">--%>
<%--															&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
													<tr>
														<td height="23" class="unidadesAbajo" style="white-space: nowrap;overflow: hidden;">&nbsp;
															
														</td>
													</tr>
												</table>
											</c:otherwise>
											</c:choose>
										</th>
										
									</c:forEach>
								
									
								</tr>

								<tr>
									<td class="texto" height="23">
										<label>
											<strong>Entregados</strong>
										</label>
									</td>
								</tr>
								<tr>
									<td class="texto1" height="23">
										<label>
											<strong>Calificados</strong>
										</label>
									</td>
								</tr>
								<tr>
									<td class="texto" height="23">
										<label>
											<strong>Asignados</strong>
										</label>
									</td>
								</tr>
								<tr>
									<td class="texto1" height="23">
										<label>
											<strong>Extemp.</strong>
										</label>
									</td>
								</tr>
<%--								<tr>--%>
<%--									<td class="texto" height="23">--%>
<%--										<label>--%>
<%--											<strong>Activaci&oacute;n</strong>--%>
<%--										</label>--%>
<%--									</td>--%>
<%--								</tr>--%>
<%--								<tr>--%>
<%--									<td class="texto1" height="23">--%>
<%--										<label>--%>
<%--											<strong>Entrega</strong>--%>
<%--										</label>--%>
<%--									</td>--%>
<%--								</tr>--%>
								<tr>
									<td class="textoAbajo" height="23">
										<label>
											<strong>Debates</strong>
										</label>
									</td>
								</tr>
							
							</c:if>
								<tr>
									<td class="datosAbajo" colspan="<c:out value="${reporteDetalle.cantidadUnidades+1}"/>" height="23">
										&nbsp;
									</td>
								</tr>
							
							<c:if test="${aula.cantidadDialogos != 0}">
							
								
								<tr>
									<td height="23" class="moduloFondo">
										<strong style="margin-left: 10px;">Diálogos</strong>
									</td>
									
									<c:forEach begin="1" end="${reporteDetalle.cantidadUnidades}" step="1" var="n">
									
										<td align="center" class="moduloFondo">
											<strong><c:out value="${n}"></c:out> </strong>
										</td>
									
									</c:forEach>
									
<%--									*** columnas adicionales ***--%>
									
								</tr>
								<tr>
									<td class="texto1" height="23" >
										<label>
											<strong>Intervenci&oacute;n:</strong>
										</label>
									</td>
									
									
									<c:forEach items="${reporteDetalle.recursoDialogo}" var="dialogo" varStatus="fila">
									
										<th  valign="top">
											<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" style="table-layout: fixed;">
												<tr>
													<td height="23" class="unidades1" >
														<c:out value="${dialogo.intervencion}"/>/
														<c:out value="${dialogo.intervencionTotal}"/>&nbsp;
													</td>
												</tr>
											</table>
										</th>
										
									</c:forEach>
								
									
								</tr>

							
							</c:if>
								
							</table>
						</td>
					</tr>
					
					<tr>
						<td height="5">
						</td>
					</tr>
					<tr>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="reporte_table" border="0">
								<tr>
									<td height="23" class="moduloFondo">
										<strong style="margin-left: 10px;">Calendario de Ingresos</strong>
									</td>
								</tr>
								<tr>
									<td align="center">
										
										<%
										
											ReporteDetalle reporteDetalle = (ReporteDetalle) request.getAttribute("REPORTE_DETALLE");
											
											DateFormatSymbols dfs = new DateFormatSymbols();
											String[] monthNames = dfs.getMonths();
											String[] dayNames = dfs.getShortWeekdays();
										
											GregorianCalendar rightNow = (GregorianCalendar) Calendar.getInstance();
											
											int firstDay = Integer.parseInt(reporteDetalle.getStringFecha1().substring(0,2));
											int firstMonth = Integer.parseInt(reporteDetalle.getStringFecha1().substring(3,5)); 
											int firstYear = Integer.parseInt(reporteDetalle.getStringFecha1().substring(6,10));
											
											int lastDay = Integer.parseInt(reporteDetalle.getStringFecha2().substring(0,2));
											int lastMonth = Integer.parseInt(reporteDetalle.getStringFecha2().substring(3,5)); 
											int lastYear = Integer.parseInt(reporteDetalle.getStringFecha2().substring(6,10));  
											
											rightNow.set(2,firstMonth-1);
											rightNow.set(1,firstYear);
											
											for(int c=1;c<=(12-(firstMonth-1))+(lastYear-firstYear-1)*12+(lastMonth);c++){
										
												//int day = rightNow.get(5);
												int mon = rightNow.get(2);
												int year = rightNow.get(1);
											
												String monthName = monthNames[mon];
												rightNow.set(5, 1);
											%>
											<div style="float: left; padding: 5px;">
												<table border="0" style="border: 1px solid #7EAAD1;" cellspacing="0" cellpadding="2">
													<caption class="fon_tit_curso"><strong><%=monthName.toUpperCase()%> <%=year %></strong></caption>
											<%
												out.println("<tr style='background-color: #E5EFF8;'>");
												for (int d = 1; d < 8; d++){
													out.print("<td><strong>" + String.valueOf(dayNames[d].charAt(0)).toUpperCase() + "</strong></td>");
												}
												out.println("</tr>");
												
												int maxDays = rightNow.getActualMaximum(5) + 1;
												int firstDayOfWeek = rightNow.get(Calendar.DAY_OF_WEEK);
												int curDay = 0;
												
												for(int week=1;week<=6;week++){
													out.println("<tr>");
													for(int dayOfWeek=1;dayOfWeek<=7;dayOfWeek++){
														if((week == 1 && dayOfWeek < firstDayOfWeek) || (curDay >= maxDays-1)){
															out.println("<td width='16' height='16'>&nbsp;</td>");
														}else{
															curDay++;
															if(reporteDetalle.isDiaDeIngreso(Formato.setBaseDatosDeDate(new GregorianCalendar(year,mon,curDay)))){
																out.println("<td width='16' height='16' style='background-color: #789BE0;'>");
															}else if((new GregorianCalendar(year,mon,curDay).after(new GregorianCalendar(firstYear,firstMonth-1,firstDay-1)))
																&& (new GregorianCalendar(year,mon,curDay).before(new GregorianCalendar(lastYear,lastMonth-1,lastDay+1)))){
																out.println("<td width='16' height='16' style='background-color: #F7FAFC;'>");
															}else{
																out.println("<td width='16' height='16'>");
															}
															if(dayOfWeek==1){
																out.println("<font color='red'>"+curDay+"</font>");
															}else{
																out.println(curDay);
															}
															out.println("</td>");
														}
														
													}
													out.println("</tr>");
												}
												
												rightNow.set(2,mon+1);
												%>
												</table>
											</div>
												<%
											}
										%>
										
									</td>
								</tr>
							</table>
						</td>
					</tr>
					
					<c:if test="${false}">
					
					<tr>
						<td height="23">&nbsp;
							

						</td>
					</tr>
					
					<tr>
						<td height="23">
							<table cellpadding="0" cellspacing="0" border="0" width="95%" height="95%"
								class="reporte_table">
								<tr>
									<td height="23" class="datos">
										<strong style="margin-left:5px;">Reportes gr&aacute;ficos</strong>
									</td>
									<td class="datos" align="right">
										<input type="button" class="form_button" value="Mostrar/Ocultar" 
										onclick="xChangeDisplay('tit_g'); xChangeDisplay('gra_g'); xChangeDisplay('spc_g');">
									</td>
								</tr>
								<tr id="tit_g" style="display: none;">
									<td height="23" class="fon_color01">
										<label>
											<strong>Ingresos al curso</strong>
										</label>
									</td>
									<td height="23" class="fon_color01">&nbsp;
										
									</td>
								</tr>
								<tr id="gra_g" style="display: none;">
									<td align="center" colspan="2" class="fon_color01">
										<img
											src="<%=Constante.RUTA_WEB_TEMPORAL%><%=request.getSession().getId()%>/<%=Constante.RUTA_REPORTE%>/reporte1.png"
											width="470" height="200">
									</td>
								</tr>
								
								<tr id="spc_g" style="display: none;">
									<td height="23" colspan="2" class="fon_color01">&nbsp;
										
									</td>
								</tr>
							</table>
						</td>

					</tr>
					</c:if>
					
					
					<c:if test="${null != tipo}">

						<tr>
							<td height="23">&nbsp;
								
							</td>
						</tr>
						<tr>
							<td height="23" align="center">
								<input type="button" value="Regresar" onClick="javascript:history.back();" class="form_button">
							</td>
						</tr>
						<tr>
							<td height="23">&nbsp;
								
							</td>
						</tr>
					
					</c:if>
					
				</table>
			</div>
			<div id="pie">
				<%@include file="../../comun/pie.jsp"%>
			</div>
		</div>
	</body>
</html>