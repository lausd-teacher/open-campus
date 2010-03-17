<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}' />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" />
		</title>
		<link href="<c:out value='${contextPath}'/>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/docente/reporte/reporte_aulavirtual.js"></script>
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/ordenamiento.js"></script>
		<style>
			a{
				text-decoration: none;
				color: black;
				border-style: none;
			}
			img{
				border-style: none;
			}
		</style>
	</head>
	<body>

		<c:set var="aula" value='${sessionScope.aula_actual}' />
		<c:set var="reporte" value='${REPORTE_MODELO}'></c:set>
		
		<div id="pop_up" style="width: 100%;">
			<div id="prin_01" style="width: 100%;">
			
			
			<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="97%">
							<strong>Curso : <c:out value="${aula.nombreCurso}"></c:out> </strong>						</td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.print()">Imprimir</a>						</td>
						<td width="1%"><a href="#" class="salir" onClick="window.print()"><img
									src="<%=request.getContextPath()%>/img/impresora.gif" width="13" height="13" border="0" /></a></td>
						<td width="2%">|</td>
					  <td width="4%"><a href="#" class="salir" onClick="window.close()">Cerrar</a></td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13" height="13" border="0" /> </a>						</td>
					</tr>
				</table>
		
		  </div>
		  
			<div id="pop_cuerpo" style="width: 96%;">
				<table width="100%" align="center" cellpadding="0" cellspacing="0"
					class="bor_tabla" border="0">
					<tr class="fon_cab">
						<td height="20" colspan="11" class="tit_cab">
							Reporte de Clase
						</td>
					</tr>
					<tr>
						<td colspan="11">
							<table width="100%" height="100%" align="center" cellpadding="3"
								cellspacing="0" class="tabla_sin_layout" border="0">

								<tr class="tabla01_subEncabezado" style="height: 1px;">
									<td align="center" class="texto2" width="20">	
									</td>
									<td class="texto2" align="center" width="90">
									</td>
									<td class="texto2" align="center" width="90">
									</td>
									<td class="texto2" align="center" width="90">
									</td>
									<td class="arribaAbajo" align="center" width="40">
									</td>
									
									<c:set var="numcol" value="0"/>
									<c:if test="${aula.cantidadDialogos != 0 && false}">
										<td class="moduloArriba1" align="center">
											<c:set var="numcol" value="${numcol+1}"/>
										</td>
									</c:if>
									<c:if test="${aula.cantidadLaboratoriosCalificados != 0}">
										<td class="moduloArriba1" align="center" colspan="1">
											<c:set var="numcol" value="${numcol+1}"/>
										</td>
									</c:if>
									<c:if test="${aula.cantidadTrabajos != 0}">
										<td class="moduloArriba1" align="center" colspan="1">
											<c:set var="numcol" value="${numcol+1}"/>
										</td>
									</c:if>
									<c:if test="${aula.cantidadGrupales != 0}">
										<td class="moduloArriba1" align="center" colspan="1">
											<c:set var="numcol" value="${numcol+1}"/>
										</td>
									</c:if>
									<c:if test="${aula.cantidadTest != 0}">
										<td align="center" class="moduloArriba1" colspan="1">
											<c:set var="numcol" value="${numcol+1}"/>
										</td>
									</c:if>
									<c:if test="${numcol > 0}">
										<td align="center" class="moduloAbajo1" colspan="2">
											<c:set var="numcol" value="${numcol+2}"/>
										</td>
									</c:if>
								</tr>

								<c:if test="${fn:length(reporte.usuariosResponsables)>0}">
									<tr>
										<td colspan="<c:out value="${numcol+5}"/>" class="moduloArriba2" height="23">
											<strong>Docente(s)</strong>
										</td>
									</tr>
								</c:if>
								
								<c:forEach items="${reporte.usuariosResponsables}" var="responsable" varStatus="fila">
								
									<c:choose>
										<c:when test="${fila.count%2==0}">
											<c:set var="rowStyle" value="class=fon_color01"></c:set> 
										</c:when>
										<c:otherwise>
											<c:set var="rowStyle" value=""></c:set> 
										</c:otherwise>
									</c:choose>
								
									<tr <c:out value="${rowStyle}" /> style="cursor: pointer;"
									onClick="abrirAuditoria('<c:out value='${contextPath}'/>','<c:out value="${responsable.idMatricula}"/>')"
									onmouseover="seleccionRegistro(this,true)" onmouseout="seleccionRegistro(this,false)"
									>
										<td height="18" class="bor_der_unid" align="center">
											<c:out value="${fila.count}"></c:out>
										</td>
										<td class="bor_der_unid" align="left" style="text-align: left;">
											<c:out value="${responsable.apepaterno}"/>
										</td>
										<td class="bor_der_unid" align="left" style="text-align: left;">
											<c:out value="${responsable.apematerno}"/>
										</td>
										<td class="bor_der_unid" align="left" style="text-align: left;">
											<c:out value="${responsable.nombre1}"/>
										</td>
                                        <td align="center">
											&nbsp;
										</td>
										
										<td align="center" colspan="<c:out value="${numcol}"/>">
											&nbsp;
										</td>
									</tr>
								
								</c:forEach>
								
								<c:if test="${fn:length(reporte.usuarios)>0}">
									<%int po=0;%>
									
									<tr>
										<td colspan="<c:out value="${numcol+5}"/>" class="moduloArriba2" height="23">
											<strong>Estudiante(s)</strong>
										</td>
									</tr>
                                    <tr class="tabla01_subEncabezado">
									<td align="center" class="moduloFondo" height="23" width="20">
										&nbsp;
									</td>
									<td class="moduloFondo" align="center" width="90">
										<strong>Paterno </strong>
										<a href="javascript:arraySort(1,<%=po++%>,<c:out value="${fn:length(reporte.usuarios)}"/>,<c:out value="${numcol+4}"/>)"><img src="<c:out value='${contextPath}'/>/img/up_down.gif"/></a>
									</td>
									<td class="moduloFondo" align="center" width="90">
										<strong>Materno </strong>
										<a href="javascript:arraySort(1,<%=po++%>,<c:out value="${fn:length(reporte.usuarios)}"/>,<c:out value="${numcol+4}"/>)"><img src="<c:out value='${contextPath}'/>/img/up_down.gif"/></a>
									</td>
									<td class="moduloFondo" align="center" width="90">
										<strong>1er. Nombre </strong>
										<a href="javascript:arraySort(1,<%=po++%>,<c:out value="${fn:length(reporte.usuarios)}"/>,<c:out value="${numcol+4}"/>)"><img src="<c:out value='${contextPath}'/>/img/up_down.gif"/></a>
									</td>
									
									<td class="moduloFondo" align="center" width="40">
										<strong>Sec.</strong>
										<a href="javascript:arraySort(1,<%=po++%>,<c:out value="${fn:length(reporte.usuarios)}"/>,<c:out value="${numcol+4}"/>)"><img src="<c:out value='${contextPath}'/>/img/up_down.gif"/></a>
									</td>
									
									<c:if test="${aula.cantidadDialogos != 0 && false}">
										<td class="moduloFondo" align="center">
											<strong>Di&aacute;logo.</strong>
											<a href="javascript:arraySort(1,<%=po++%>,<c:out value="${fn:length(reporte.usuarios)}"/>,<c:out value="${numcol+4}"/>)"><img src="<c:out value='${contextPath}'/>/img/up_down.gif"/></a>
										</td>
									</c:if>
									<c:if test="${aula.cantidadLaboratoriosCalificados != 0}">
										<td class="moduloFondo" align="center" colspan="1">
											<strong>Promedio<br/>Laboratorio</strong>
											<a href="javascript:arraySort(1,<%=po++%>,<c:out value="${fn:length(reporte.usuarios)}"/>,<c:out value="${numcol+4}"/>)"><img src="<c:out value='${contextPath}'/>/img/up_down.gif"/></a>
										</td>
									</c:if>
									<c:if test="${aula.cantidadTrabajos != 0}">
										<td class="moduloFondo" align="center" colspan="1">
											<strong>Promedio<br/>Trabajo</strong>
											<a href="javascript:arraySort(1,<%=po++%>,<c:out value="${fn:length(reporte.usuarios)}"/>,<c:out value="${numcol+4}"/>)"><img src="<c:out value='${contextPath}'/>/img/up_down.gif"/></a>
										</td>
									</c:if>
									<c:if test="${aula.cantidadGrupales != 0}">
										<td class="moduloFondo" align="center" colspan="1">
											<strong>Promedio<br/>Trabj. Grupal</strong>
											<a href="javascript:arraySort(1,<%=po++%>,<c:out value="${fn:length(reporte.usuarios)}"/>,<c:out value="${numcol+4}"/>)"><img src="<c:out value='${contextPath}'/>/img/up_down.gif"/></a>
										</td>
									</c:if>
									<c:if test="${aula.cantidadTest != 0}">
										<td align="center" class="moduloFondo" colspan="1">
											<strong>Promedio<br/>Evaluaci&oacute;n	</strong>
											<a href="javascript:arraySort(1,<%=po++%>,<c:out value="${fn:length(reporte.usuarios)}"/>,<c:out value="${numcol+4}"/>)"><img src="<c:out value='${contextPath}'/>/img/up_down.gif"/></a>
										</td>
									</c:if>
									<c:if test="${numcol > 0}">
										<td align="center" class="moduloFondo" colspan="1">
											<strong>Promedio<br/>Final</strong>
											<a href="javascript:arraySort(1,<%=po++%>,<c:out value="${fn:length(reporte.usuarios)}"/>,<c:out value="${numcol+4}"/>)"><img src="<c:out value='${contextPath}'/>/img/up_down.gif"/></a>
										</td>
										<td align="center" class="moduloFondoAbajo" colspan="1">
											<strong>Estado<br/>Final</strong>
											<a href="javascript:arraySort(1,<%=po++%>,<c:out value="${fn:length(reporte.usuarios)}"/>,<c:out value="${numcol+4}"/>)"><img src="<c:out value='${contextPath}'/>/img/up_down.gif"/></a>
										</td>
									</c:if>
									
								</c:if>

								<c:forEach items="${reporte.usuarios}" var="estudiante" varStatus="fila">
								
									<c:choose>
										<c:when test="${fila.count%2==0}">
											<c:set var="rowStyle" value="class=fon_color01"></c:set> 
										</c:when>
										<c:otherwise>
											<c:set var="rowStyle" value=""></c:set> 
										</c:otherwise>
									</c:choose>
									
									<c:set var="columna" value="0"/>
								
									<tr <c:out value="${rowStyle}" /> style="cursor: pointer;"
									onClick="abrirDetalle('<c:out value='${contextPath}'/>','<c:out value="${estudiante.idMatricula}"/>')"
									
									>
										<td height="18" class="bor_der_unid"">
											<c:out value="${fila.count}"></c:out>
										</td>
										<td align="left" class="bor_der_unid" id="t1l<c:out value="${fila.count}"/>c<c:out value="${columna}"/>" style="text-align: left;">
											<c:set var="columna" value="${columna+1}"/>
											<ct:TextoToNumero texto="${estudiante.apepaterno}"/>
											<c:out value="${estudiante.apepaterno}"/>&nbsp;
										</td>
										<td align="left" class="bor_der_unid" id="t1l<c:out value="${fila.count}"/>c<c:out value="${columna}"/>" style="text-align: left;">
											<c:set var="columna" value="${columna+1}"/>
											<ct:TextoToNumero texto="${estudiante.apematerno}"/>
											<c:out value="${estudiante.apematerno}"/>&nbsp;
										</td>
										<td align="left" class="bor_der_unid" id="t1l<c:out value="${fila.count}"/>c<c:out value="${columna}"/>" style="text-align: left;">
											<c:set var="columna" value="${columna+1}"/>
											<ct:TextoToNumero texto="${estudiante.nombre1}"/>
											<c:out value="${estudiante.nombre1}"/>&nbsp;
										</td>
										
										<td align="center" class="bor_der_unid" id="t1l<c:out value="${fila.count}"/>c<c:out value="${columna}"/>">											
											<c:set var="columna" value="${columna+1}"/>
											<ct:TextoToNumero texto="${estudiante.seccion}"/>
											<c:out value="${estudiante.seccion}"/>&nbsp;
										</td>
										
										<c:if test="${aula.cantidadDialogos != 0 && false}">
                                           <td class="bor_der_unid" align="center" id="t1l<c:out value="${fila.count}"/>c<c:out value="${columna}"/>">
                                           		<c:set var="columna" value="${columna+1}"/>
                                           		<ct:TextoToNumero numero="${estudiante.cantidadCompletaDialogo}"/>
												<c:out value="${estudiante.cantidadCompletaDialogo}"/>/<c:out value="${estudiante.cantidadDialogo}"/>
											</td>
										</c:if>
										
										<c:if test="${aula.cantidadLaboratoriosCalificados != 0}">
											<td align="center" class="bor_der_unid" id="t1l<c:out value="${fila.count}"/>c<c:out value="${columna}"/>">
												<c:set var="columna" value="${columna+1}"/>
												<ct:TextoToNumero numero="${estudiante.notaLaboratorio}"/>
												<c:out value="${estudiante.notaLaboratorio}" default="NP"/>&nbsp;
											</td>
											<!--  td class="bor_der_unid" align="center">
												<c:out value="${estudiante.cantidadCompletaLaboratorio}"/>/<c:out value="${estudiante.cantidadLaboratorio}"/>
											</td-->
										</c:if>
										
										<c:if test="${aula.cantidadTrabajos != 0}">
											<td align="center" class="bor_der_unid" id="t1l<c:out value="${fila.count}"/>c<c:out value="${columna}"/>">
												<c:set var="columna" value="${columna+1}"/>
												<ct:TextoToNumero numero="${estudiante.notaTrabajo}"/>
												<c:out value="${estudiante.notaTrabajo}" default="NP"/>&nbsp;
											</td>
											<!--td class="bor_der_unid" align="center">
												<c:out value="${estudiante.cantidadCompletaTrabajo}"/>/<c:out value="${estudiante.cantidadTrabajo}"/>
											</td-->
										</c:if>
										
										<c:if test="${aula.cantidadGrupales != 0}">
											<td  align="center" class="bor_der_unid" id="t1l<c:out value="${fila.count}"/>c<c:out value="${columna}"/>">
												<c:set var="columna" value="${columna+1}"/>
												<ct:TextoToNumero numero="${estudiante.notaGrupal}"/>
												<c:out value="${estudiante.notaGrupal}" default="NP"/>&nbsp;
											</td>
											<!--td class="bor_der_unid" align="center">
												<c:out value="${estudiante.cantidadCompletaGrupal}"/>/<c:out value="${estudiante.cantidadGrupal}"/>
											</td-->
										</c:if>
										
										<c:if test="${aula.cantidadTest != 0}">
											<td align="center" class="bor_der_unid" id="t1l<c:out value="${fila.count}"/>c<c:out value="${columna}"/>">
												<c:set var="columna" value="${columna+1}"/>
												<ct:TextoToNumero numero="${estudiante.notaTest}"/>
												<c:out value="${estudiante.notaTest}" default="NP"/>&nbsp;
											</td>
											<!--td align="center" class="bor_der_unid">
												<c:out value="${estudiante.cantidadCompletaTest}"/>/<c:out value="${estudiante.cantidadTest}"/>
											</td-->
										</c:if>
										<c:if test="${numcol > 0}">
											<td align="center" class="bor_der_unid" colspan="1" id="t1l<c:out value="${fila.count}"/>c<c:out value="${columna}"/>">
												<c:set var="columna" value="${columna+1}"/>
												<ct:TextoToNumero numero="${estudiante.promedio}"/>
												<strong><c:out value="${estudiante.promedio}"/></strong>&nbsp;
											</td>
											<td align="center" colspan="1" id="t1l<c:out value="${fila.count}"/>c<c:out value="${columna}"/>">
												<c:set var="columna" value="${columna+1}"/>
												<c:choose>
													<c:when test="${estudiante.aprobado == true}">
														<strong><font color="#0000FF">Apto</font></strong>
														<ct:TextoToNumero texto="Apto"/>
													</c:when>
													<c:otherwise>
														<strong><font color="#FF0000">No apto</font></strong>
														<ct:TextoToNumero texto="No apto"/>
													</c:otherwise>
												</c:choose>
													
											</td>
										</c:if>
										
									</tr>
								
								</c:forEach>
								
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div id="pie"></div>
		</div>
	</body>
</html>
