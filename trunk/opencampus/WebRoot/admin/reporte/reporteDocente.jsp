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
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/js/jscalendar/calendar-style.css" />
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/jscalendar/calendar.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/jscalendar/calendar-es.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/jscalendar/calendar-setup.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>		
		<script type='text/javascript'
			src='<%=request.getContextPath()%>/admin/reporte/reporte.js'></script>		
		
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
								<s:text name="portal.menu.auditoria_dialogo"></s:text>
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
							</td>
							<td align="right">
								<span id="rango"></span>
							</td>
							<td width="20">
								De
							</td>
							<td width="60">
								<%
									GregorianCalendar antes = new GregorianCalendar();
									antes.add(Calendar.DATE,-7);
									GregorianCalendar hoy = new GregorianCalendar();
								 %>
								<input type="text" id="boxFecha1" size="13"
									class="form_text" readonly="readonly" name="busquedaFecha1" 
									value="<%=Formato.getStringDeDateCompleto(antes).replaceAll("-","/") %>"/>
							</td>
							<td width="12">
								a
							</td>
							<td width="60">
								<input type="text" id="boxFecha2" size="13"
									class="form_text" readonly="readonly" name="busquedaFecha2" 
									value="<%=Formato.getStringDeDateCompleto(hoy).replaceAll("-","/") %>"/>
							</td>
							<td>
								<input type="button" value="Generar" class="form_button" onclick="if(validarAuditoria())searchAuditoria('<%=(usuario.getRolPredeterminado()==Constante.ROL_CAMPUS_JEFE_DEPARTAMENTO)?request.getContextPath()+"/reporte/AuditoriaDepartamento.action":request.getContextPath()+"/reporte/AuditoriaopencampusVirtual.action"%>')"/>
							</td>
						</tr>
					</table>
				</form>
				<script language="JavaScript" type="text/javascript">
					Calendar.setup({inputField:"boxFecha1", ifFormat:"%d/%m/%Y %H:%M",  singleClick:true, showsTime:true});
					Calendar.setup({inputField:"boxFecha2", ifFormat:"%d/%m/%Y %H:%M",  singleClick:true, showsTime:true});									
				</script>
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
