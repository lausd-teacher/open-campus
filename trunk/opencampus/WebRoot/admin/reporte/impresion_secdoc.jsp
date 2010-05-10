<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="edu.opencampus.lms.modelo.Usuario"%>
<%@page import="edu.opencampus.lms.util.Constante"%>
<%@  page import="edu.opencampus.lms.util.Formato"%>
<%@  page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Collection"%>
<%@page import="edu.opencampus.lms.modelo.reportesecdoc.UsuarioReporteSecDoc"%>
<%@page import="edu.opencampus.lms.modelo.reportesecdoc.FichaReporteSecDoc"%>
<%
	Usuario usuario = (Usuario) request.getSession().getAttribute(
			Constante.USUARIO_ACTUAL);
	Collection<UsuarioReporteSecDoc> usuariosSecDoc = (Collection<UsuarioReporteSecDoc>) request .getAttribute("USUARIOS_FICHAS");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title>Auditor&iacute;a : Docente</title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
	</head>
	<body>
		<div id="pop_up"
			style="width: 243mm;margin:0 auto 0 auto; ">
			<div id="prin_01" style="height: 75px;">
			<table border="0" cellpadding="0" cellspacing="0" 
					style="width: 220mm; border-top: 1px #00A2E3 solid;  ">
					<tr>
						<td colspan="2" align="left">
							<img src="<%=request.getContextPath()%>/img/reporte_secdoc.jpg"/>
						</td>
					</tr>
					<tr>
						<td  style="padding-left: 10px;height: 25px;color: #44659B;">
							<strong><%=usuario.getNombreCompleto()%> (<%=usuario.getIdUsuario()%>)</strong>
						</td>
						<td align="right" style="padding-right: 10px;color: #44659B;">
							<strong><%=Formato.getStringDeDateCompleto(new GregorianCalendar())%></strong>
						</td>
					</tr>
				</table>
			</div>
			<div id="pop_cuerpo" style="background-color: white;">
				
				<table>
					<tr>
						<td height="5"/>
					</tr>
				</table>
				<table border="0" cellpadding="3" cellspacing="0"
					style="vertical-align: top; width: 230mm;" class="tabla_sin_layout"
					pop_up align="center">
					<tr style="background-color: #E5EFF8;">
						<td width="40mm"
							style="font-weight: bold; border-bottom: solid #7EAAD1 1px; border-right: solid #7EAAD1 1px; border-left: solid #7EAAD1 1px; border-top: solid #7EAAD1 1px;">
							Codigo
						</td>
						<td width="150mm" align="left"
							style="font-weight: bold; border-bottom: solid #7EAAD1 1px; border-right: solid #7EAAD1 1px; border-top: solid #7EAAD1 1px;">
							Nombre Docente
						</td>
						<td width="40mm"
							style="font-weight: bold; border-bottom: solid #7EAAD1 1px; border-right: solid #7EAAD1 1px; border-top: solid #7EAAD1 1px;">
							Codigo
						</td>
						<td width="200mm" align="left"
							style="font-weight: bold; border-bottom: solid #7EAAD1 1px; border-right: solid #7EAAD1 1px; border-top: solid #7EAAD1 1px;">
							Nombre Curso
						</td>
						<td width="30mm" align="center"
							style="font-weight: bold; border-bottom: solid #7EAAD1 1px; border-right: solid #7EAAD1 1px; border-top: solid #7EAAD1 1px;">
							Cant.
						</td>
						<td width="45mm"
							style="font-weight: bold; border-bottom: solid #7EAAD1 1px; border-right: solid #7EAAD1 1px; border-top: solid #7EAAD1 1px;">
							F. Inicio
						</td>
						<td width="45mm"
							style="font-weight: bold; border-bottom: solid #7EAAD1 1px; border-right: solid #7EAAD1 1px; border-top: solid #7EAAD1 1px;">
							F. Fin
						</td>
					</tr>
					<%
						for (UsuarioReporteSecDoc usuariosReporte : usuariosSecDoc) {
							for (FichaReporteSecDoc fichasReporte : usuariosReporte
									.getFichas()) {
					%>
					<tr>
						<td
							style="border-bottom: solid #7EAAD1 1px; border-right: solid #7EAAD1 1px; border-left: solid #7EAAD1 1px;">
							<%=usuariosReporte.getCodopencampus()%>
						</td>
						<td align="left"
							style="border-bottom: solid #7EAAD1 1px; border-right: solid #7EAAD1 1px;">
							<%=usuariosReporte.getNombreCompleto()%>
						</td>
						<td
							style="border-bottom: solid #7EAAD1 1px; border-right: solid #7EAAD1 1px;">
							<%=fichasReporte.getCodigoCurso()%>
						</td>
						<td align="left"
							style="border-bottom: solid #7EAAD1 1px; border-right: solid #7EAAD1 1px;">
							<%=fichasReporte.getNombreCurso()%>
						</td>
						<td align="center"
							style="border-bottom: solid #7EAAD1 1px; border-right: solid #7EAAD1 1px;">
							<%=fichasReporte.getCantidad()%>
						</td>
						<td align="left"
							style="border-bottom: solid #7EAAD1 1px; border-right: solid #7EAAD1 1px;">
							<%=fichasReporte.getFechaInicio()%>
						</td>
						<td align="left"
							style="border-bottom: solid #7EAAD1 1px; border-right: solid #7EAAD1 1px;">
							<%=fichasReporte.getFechaFin()%>
						</td>
					</tr>
					<%
						}
						}
					%>
					<tr>
						<td colspan="7" height="10"/>
					</tr>
				</table>
			</div>
			<div id="pie">
				&nbsp;
			</div>
		</div>
		<script>
			window.print();
		</script>
	</body>
</html>
