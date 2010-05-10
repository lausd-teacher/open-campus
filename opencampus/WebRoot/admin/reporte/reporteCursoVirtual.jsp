<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="edu.opencampus.lms.util.Formato"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title><s:text name="titulo.campus.virtual" /></title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />	
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>		
		<script type='text/javascript'
			src='<%=request.getContextPath()%>/admin/reporte/reporte.js'></script>		
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/js/divlista/divlista.js'></script>
		<link href="<%=request.getContextPath()%>/js/divlista/divlista.css"
			rel="stylesheet" type="text/css">
	</head>
	<body>
		<div id="contenedor">
			<s:include value="/comun/bienvenida.jsp"></s:include>
			<div id="cuerpo" style="background-color: white;">
				<div id="principal" style="width: 980px;padding-bottom: 7px;">
			
				<form method="post" id="opencampusVirtual">
					<table width="975" border="0" cellpadding="3" cellspacing="0"
					class="tablaFicha">
						<tr class="fon_cab">
							<td colspan="8" class="tit_cab">
								<s:text name="portal.menu.auditoria_apto"></s:text>
							</td>
						</tr>
						<tr>
							<td align="right">
								Per&iacute;odo :
							</td>
							<td align="left">
								<select name="busquedaPeriodo" xonchange="actualizarFechas()">
									<option value="0">
										Todos
									</option>
									<c:forEach var="periodo" items='${PERIODO_PREDETERMINADA}'>
										<option value="<c:out value='${periodo.idPeriodo}'/>">
											<c:out value='${periodo.nombre}'/>  <>  ( <c:out value='${periodo.fechaInicioToString}'/> / <c:out value='${periodo.fechaFinToString}'/> )
										</option>
									</c:forEach>
								</select>
								&nbsp;
								<input type="button" value="Generar" class="form_button" onclick="if(validarReporte())searchAuditoria('<%=request.getContextPath()%>/reporte/ReporteopencampusVirtual.action')"/>
							</td>
							<td align="right">
								<span id="rango"></span>
							</td>
						</tr>
					</table>
				</form>
				</div>
			</div>
			<div id="pie">
				<%@include file="../../comun/pie.jsp"%>
			</div>
		</div>
		<script type="text/javascript">
			resizeHeight();
		</script>
	</body>
</html>
