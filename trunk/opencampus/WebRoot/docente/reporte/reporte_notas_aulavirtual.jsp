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
		<style>
			a{
				text-decoration: none;
				color: black;
				border-style: none;
			}
		</style>
	</head>
	<body>

		<c:set var="aula" value='${sessionScope.aula_actual}' />
		<c:set var="reporte" value='${REPORTE_NOTAS}'></c:set>
		
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
							<s:text name="aula.alumno.menu.reporte_notas"/> 
						</td>
					</tr>
					<tr>
						<td colspan="11" style="padding: 3px;">
							
							<c:forEach var="reporteNotas" items="${reporte}" varStatus="row">
								
								<table width="100%" height="100%" align="center" cellpadding="2"
									cellspacing="0" class="tabla_sin_layout" border="0" style="border: 1px solid #44659B;"> 
									<caption style="text-align: left; font-weight: bold; background-color: #E5EFF8; padding:5px;">
										<img src="<%=request.getContextPath()%>/img/icon_user.png"/> <c:out value="${reporteNotas.estudiante}"></c:out>
									</caption>
									<tr>
										<td>
											<table border="0" cellpadding="2" cellspacing="0" width="100%" style="font-weight: bold;">
												<tr><td class="fon_color01">Unidad:</td></tr>
												<tr><td>Min:</td></tr><tr>
												<td class="fon_color01">Max:</td></tr><tr>
												<td>Promedio:</td></tr>
												<tr><td class="fon_color01">Veces:</td></tr>
											</table>
										</td>
										<c:forEach var="test" items="${reporteNotas.tests}" varStatus="col">
											<td width="40">
												<table border="0" cellpadding="2" cellspacing="0" width="100%" style="text-align: center;">
													<tr><td class="fon_color01"><c:out value="${col.count}" default="&nbsp;" escapeXml="false"></c:out></td></tr>
													<tr><td><c:out value="${test.notaMinima}" default="&nbsp;" escapeXml="false"></c:out></td></tr>
													<tr><td class="fon_color01"><c:out value="${test.notaMaxima}" default="&nbsp;" escapeXml="false"></c:out></td></tr>
													<tr><td><b><c:out value="${test.notaPromedio}" default="&nbsp;" escapeXml="false"></c:out></b></td></tr>
													<tr><td class="fon_color01"><c:out value="${test.veces}" default="&nbsp;" escapeXml="false"></c:out></td></tr>
												</table>
											</td>
										</c:forEach>
										<td width="60">
											<table border="0" cellpadding="2" cellspacing="0" width="100%" style="text-align: center;">
												<tr><td class="fon_color01"><b>Promedio</b></td></tr>
												<tr><td><c:out value="${reporteNotas.promedio.notaMinima}" default="&nbsp;" escapeXml="false"></c:out></td></tr>
												<tr><td class="fon_color01"><c:out value="${reporteNotas.promedio.notaMaxima}" default="&nbsp;" escapeXml="false"></c:out></td></tr>
												<tr><td><b><c:out value="${reporteNotas.promedio.notaPromedio}" default="&nbsp;" escapeXml="false"></c:out></b></td></tr>
												<tr><td class="fon_color01"><c:out value="${reporteNotas.promedio.veces}" default="&nbsp;" escapeXml="false"></c:out></td></tr>
											</table>
										</td>
									</tr>
								</table>
								&nbsp;
							</c:forEach>
							
						</td>
					</tr>
				</table>
			</div>
			<div id="pie"></div>
		</div>
	</body>
</html>
