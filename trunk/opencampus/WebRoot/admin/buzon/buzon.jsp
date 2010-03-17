<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/js/jscalendar/calendar-style.css" />
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/jscalendar/calendar.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/jscalendar/calendar-es.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/jscalendar/calendar-setup.js"></script>	
		
	</head>
	<%@include file="../../comun/capas/reloj.jsp"%>
	<body onLoad="mostrarReloj();">
		<div id="contenedor">
			<s:include value="/comun/bienvenida.jsp"></s:include>
			<div id="cuerpo" style="background-color: white;">
				<div id="principal" style="width: 980px;padding-bottom: 7px;">
			
				<form method="post" id="tecsupVirtual" action="<%=request.getContextPath()%>/admin/buzon/LimpiarBuzon.action">
					<table width="975" border="0" cellpadding="3" cellspacing="0"
					class="tablaFicha">
						<tr class="fon_cab">
							<td colspan="7" class="tit_cab">
								<s:text name="portal.menu.buzon"></s:text>
							</td>
						</tr>
						<tr>
							<td align="right" width="100">
								<b>Eliminar hasta :</b>
							</td>
							<td align="left" width="100">
								<input type="text" name="fecha" id="fecha" size="13" readonly="readonly"/>
								<script language="JavaScript" type="text/javascript">
									Calendar.setup({inputField:"fecha", ifFormat:"%d/%m/%Y",  singleClick:true, step:1});								
								</script>
							</td>
							<td align="center">
								<b>Espacio en Disco <fmt:setLocale value="en_US"/>
								<fmt:formatNumber value="${CARGA}" maxFractionDigits="2"/> MiB </b>
							</td>
							<td align="right">
								<input type="submit" value="Limpiar Base de Datos" class="form_button" />
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
