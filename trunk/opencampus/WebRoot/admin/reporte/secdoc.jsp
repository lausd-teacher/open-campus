<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="edu.tecsup.lms.util.Constante"%>
<%@page import="edu.tecsup.lms.util.Util"%>
<%@page import="java.util.Collection"%>
<%@page import="edu.tecsup.lms.modelo.Sede"%>
<%@page import="edu.tecsup.lms.modelo.Usuario"%>
<%@  page import="edu.tecsup.lms.modelo.Periodo"%>
<%@page
	import="edu.tecsup.lms.modelo.reportesecdoc.UsuarioReporteSecDoc"%>
<%@page import="edu.tecsup.lms.modelo.reportesecdoc.FichaReporteSecDoc"%>
<%
	Collection<UsuarioReporteSecDoc> usuariosSecDoc = (Collection<UsuarioReporteSecDoc>) request
			.getAttribute("USUARIOS_BUSQUEDA");
	Collection<Periodo> periodos = (Collection<Periodo>) request
			.getAttribute("PERIODO_PREDETERMINADA");
	String busquedaSede = String.valueOf(request
			.getAttribute("busquedaSede"));
	String busquedaFecha1 = String.valueOf(request
			.getAttribute("busquedaFecha1"));
	String busquedaFecha2 = String.valueOf(request
			.getAttribute("busquedaFecha2"));
	String busquedaFamilia = String.valueOf(request
			.getAttribute("busquedaFamilia"));
	String busquedaPeriodo = String.valueOf(request
			.getAttribute("busquedaPeriodo"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title><s:text name="titulo.campus.virtual" /></title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />	
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
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
			src='<%=request.getContextPath()%>/admin/reporte/secdoc.js'></script>		
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/tooltip/tooltip.js"></script>
	</head>
	<body>
		<div id="contenedor">
			<s:include value="/comun/bienvenida.jsp"></s:include>
			<div id="cuerpo" style="background-color: white;">
				<div id="principal" style="padding-bottom: 7px;">
					<table width="975" border="0" cellpadding="0" cellspacing="0"
						class="tablaFicha">
						<tr class="fon_cab">
							<td height="20" colspan="5" class="tit_cab">
								<s:text name="portal.menu.auditoria_secdoc"></s:text>
							</td>
						</tr>
						<tr>
							<td width="975" align="center" height="120">
								<table border="0" cellpadding="0" cellspacing="5" width="80%"
									style="border: 1px solid #7fa9ed; background-color: white;">
									<tr style="display: none">
										<td align="right">
											Familia :
										</td>
										<td align="left">
											<select id="form_busquedaFamilia">
												<option value="0"
													<%if("0".equals(busquedaFamilia)){ out.print("selected=\"selected\"");}%>>
													Todos
												</option>
												<%
													for (Sede forma : Util.getFamilias()) {
												%>
												<option value="<%=forma.getCodigo()%>"
													<%if(forma.getCodigo().equals(busquedaFamilia)){ out.print("selected=\"selected\"");}%>>
													<%=forma.getNombre()%>
												</option>
												<%
													}
												%>
											</select>
										</td>
										<td align="right">
											Sede :
										</td>
										<td colspan="1" align="left">
											<select id="form_busquedaSede">
												<option value="0"
													<%if("0".equals(busquedaSede)){ out.print("selected=\"selected\"");}%>>
													Todos
												</option>
												<%
													for (Sede sede : Util.getSedes()) {
												%>
												<option value="<%=sede.getCodigo()%>"
													<%if(sede.getCodigo().equals(busquedaSede)){ out.print("selected=\"selected\"");}%>>
													<%=sede.getNombre()%>
												</option>
												<%
													}
												%>
											</select>
										</td>
										<td></td>
									</tr>
									<tr>
										<td align="right">
											Per&iacute;odo :
										</td>
										<td colspan="1" align="left">
											<select id="form_busquedaPeriodo">
												<option value="0"
													<%if("0".equals(busquedaPeriodo)){ out.print(" selected=\"selected\" ");}%>>
													Todos
												</option>
												<%
													for (Periodo periodo : periodos) {
												%>
												<option value="<%=periodo.getIdPeriodo()%>"
													<%if(periodo.getIdPeriodo().equals(busquedaPeriodo)){ out.print(" selected=\"selected\" ");}%>>
													<%=periodo.getNombre()%>
												</option>
												<%
													}
												%>
											</select>
										</td>
										<td align="right">
											Fecha Fin :
										</td>
										<td colspan="3" align="left">
											<label style="margin-left: 20px; margin-right: 20px;">
												De
											</label>
											<input type="text" id="form_fechaInicio1" size="8"
												class="form_text" readonly="readonly"
												value="<%=busquedaFecha1%>" />
											<label style="margin-left: 20px; margin-right: 20px;">
												a
											</label>
											<input type="text" id="form_fechaInicio2" size="8"
												class="form_text" readonly="readonly"
												value="<%=busquedaFecha2%>" />
										</td>
									
										<td align="right" colspan="4">
											<input type="button" value="Buscar" class="form_button"
												onclick="javascript:buscarSecDoc();">
										</td>
									</tr>
									<script language="JavaScript" type="text/javascript">
										Calendar.setup({inputField:"form_fechaInicio1", ifFormat:"%d/%m/%Y",  singleClick:true, onUpdate:catcalc});
										Calendar.setup({inputField:"form_fechaInicio2", ifFormat:"%d/%m/%Y",  singleClick:true, onUpdate:catcalc});									
									</script>
								</table>
							</td>
						</tr>
						<tr>
							<td style="background-color: #E5EFF8;" class="arribaAbajo "
								valign="bottom">
								<table border="0" width="100%" align="left"
									class="tabla_sin_layout">
									<tr>
										<td>
											<input type="button" value="Imprimir"
												style="font-weight: bolder; cursor: pointer; margin-left: 10px; margin-right: 5px;"
												onclick="imprimirFichas();" class="form_button">
											<input type="button" value="Excel"
												style="font-weight: bolder; cursor: pointer; margin-left: 10px; margin-right: 5px;"
												onclick="imprimirExcel();" class="form_button">
										</td>
									</tr>
									<tr>
										<td colspan="3" height="20">
											<span style="margin-left: 10px;"> Seleccionar : </span>
											<span class="opcion_selecionar"
												onClick="javascript:checkCheckBox(true);">Todas</span>,
											<span class="opcion_selecionar"
												onClick="javascript:checkCheckBox(false);">Ninguna</span>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td style="background-color: white;" class="moduloAbajo1"
								valign="bottom" height="20">
								<table width="100%" border="0" cellpadding="0" cellspacing="0"
									style="background-color: #EEEEEE;"
									class="tabla_sin_layout moduloAbajo1">
									<tr>
										<td width="5" height="20">
											&nbsp;
										</td>
										<td width="12">
											&nbsp;
										</td>
										<td width="5">

										</td>
										<td width="17">

										</td>
										<td width="220" align="left" valign="bottom">
											<label style="font-weight: bold;">
												Nombre del Curso
											</label>
										</td>
										<td width="50" align="center" valign="bottom">
											<label style="font-weight: bold;">
												Sec.
											</label>
										</td>
										<td width="40" valign="bottom" align="center">
											<label style="font-weight: bold; margin-left: 5px;">
												Cant.
											</label>
										</td>
										<td width="40" valign="bottom">
											<label style="font-weight: bold; margin-left: 5px;">
												Turno
											</label>
										</td>
										<td width="40" valign="bottom">
											<label style="font-weight: bold; margin-left: 5px;">
												Periodo
											</label>
										</td>
										<td width="50" valign="bottom">
											<label style="font-weight: bold;">
												Fecha Inicio
											</label>
										</td>
										<td width="50" valign="bottom">
											<label style="font-weight: bold; margin-left: 5px;">
												Fecha Fin
											</label>
										</td>
										<td width="50" valign="bottom">
											<label style="font-weight: bold; margin-left: 5px;">
												Sede
											</label>
										</td>
										<td width="50">
										</td>
									</tr>
								</table>
								<%
									if (0 < usuariosSecDoc.size()) {
								%>
								<table border="0" width="100%" align="left"
									id="todos_items_busqueda" class="tabla_sin_layout"
									cellpadding="0" cellspacing="0">
									<tr height="1" style="background-color: #EEEEEE;">
										<td width="5" height="1"
											style="border-bottom: solid #7fa9ed 1px;" />
										<td width="12" style="border-bottom: solid #7fa9ed 1px;" />
										<td width="5" style="border-bottom: solid #7fa9ed 1px;" />
										<td width="17" style="border-bottom: solid #7fa9ed 1px;" />
										<td width="220" style="border-bottom: solid #7fa9ed 1px;" />
										<td width="50" style="border-bottom: solid #7fa9ed 1px;" />
										<td width="40" style="border-bottom: solid #7fa9ed 1px;" />
										<td width="40" style="border-bottom: solid #7fa9ed 1px;" />
										<td width="40" style="border-bottom: solid #7fa9ed 1px;" />
										<td width="50" style="border-bottom: solid #7fa9ed 1px;" />
										<td width="50" style="border-bottom: solid #7fa9ed 1px;" />
										<td width="50" style="border-bottom: solid #7fa9ed 1px;" />
										<td width="50" style="border-bottom: solid #7fa9ed 1px;" />
									</tr>
									<%
										int a = 0;
											int b = 0;
											for (UsuarioReporteSecDoc usuarioReporte : usuariosSecDoc) {
												a++;
												b = 0;
									%>
									<tr id="tr_<%=a%>" class="fon_color01" height="25">
										<td width="5">
											&nbsp;
										</td>
										<td width="20">
											<input type="checkbox"
												value="<%=usuarioReporte.getIdUsuario()%>"
												onclick="javascript:seleccionarDIVGrupo('tr_<%=a%>',this)"
												id="usuario_<%=a%>">
										</td>
										<td width="15">
											&nbsp;
										</td>
										<td style="color: black;" colspan="10">
											<label
												onmouseover="verToolTip('<%="<strong>" + usuarioReporte.getIdUsuario()
							+ "</strong> - "
							+ usuarioReporte.getNombreCompleto()%>', this);"
												onmouseout="ocultarToolTip()">
												<strong><%=usuarioReporte.getCodTecsup()%></strong> -
												<%=usuarioReporte.getNombreCompleto()%>
											</label>
										</td>
									</tr>
									<%
										for (FichaReporteSecDoc ficha : usuarioReporte.getFichas()) {
													b++;
									%>
									<tr class="fon_color02" height="25" id="tr_<%=a%>_<%=b%>">
										<td class="ficha_item_link" style="cursor: auto;">

										</td>
										<td class="ficha_item_link" style="cursor: auto;">

										</td>
										<td class="ficha_item_link" style="cursor: auto;">

										</td>
										<td class="ficha_item_link" style="cursor: auto;">
											<input type="checkbox" value="<%=ficha.getIdMatricula()%>"
												onclick="javascript:seleccionarDIV('tr_<%=a%>','tr_<%=a%>_<%=b%>',this)"
												id="ficha_<%=a%>_<%=b%>">
										</td>
										<td class="ficha_item_link"
											style="border-right: 1px solid #7EAAD1; cursor: auto;">
											<label
												onmouseover="verToolTip('<%=ficha.getCodigoCurso() + " - "
								+ ficha.getNombreCurso()%>', this);"
												onmouseout="ocultarToolTip()">
												<%=ficha.getNombreCurso()%>
											</label>

										</td>
										<td class="ficha_item_link" align="right"
											style="border-right: 1px solid #7EAAD1; cursor: auto;">
											<label
												onmouseover="verToolTip('<%="<strong>" + ficha.getSeccion()
								+ "</strong>"%>', this);"
												onmouseout="ocultarToolTip()">
												<%=ficha.getSeccion()%>&nbsp;
											</label>
										</td>
										<td class="ficha_item_link" align="right"
											style="border-right: 1px solid #7EAAD1; cursor: auto;">
											<label style="margin-right: 10px; color: red;">
												<%=ficha.getCantidad()%>
											</label>
										</td>


										<td class="ficha_item_link" align="left"
											style="border-right: 1px solid #7EAAD1; cursor: auto;">
											<label
												onmouseover="verToolTip('<%=ficha.getTurno()%>', this);"
												onmouseout="ocultarToolTip()">
												<%=ficha.getTurno()%>
											</label>
										</td>
										<td class="ficha_item_link"
											style="border-right: 1px solid #7EAAD1; cursor: auto;"
											align="left">
											<label
												onmouseover="verToolTip('<%=ficha.getPeriodo()%>', this);"
												onmouseout="ocultarToolTip()">
												<%=ficha.getPeriodo()%>
											</label>
										</td>
										<td class="ficha_item_link"
											style="border-right: 1px solid #7EAAD1; cursor: auto;"
											align="left">
											<%=ficha.getFechaInicio()%>
										</td>
										<td class="ficha_item_link"
											style="border-right: 1px solid #7EAAD1; cursor: auto;"
											align="left">
											<%=ficha.getFechaFin()%>
										</td>
										<td class="ficha_item_link" align="left" style="cursor: auto;">
											<%=ficha.getSede()%>
										</td>
										<td class="ficha_item_link" style="cursor: auto;">
											&nbsp;
										</td>
									</tr>
									<%
										}
											}
									%>
									<script language="JavaScript" type="text/javascript">
												limpiarCheckBox();
									</script>
								</table>
								<div <%if(10 >a ) { out.print("style=\"height:"+( 10 - a ) * 15 +"px;\"");}%>>
									&nbsp;
								</div>
								<%
									} else {
								%>
								<div
									style="height: 80px; text-align: center; font-weight: bold; margin-top: 60px; background-color: white;">
									No se encontr&oacute; ning&uacute;n registro.
								</div>
								<%
									}
								%>
							</td>
						</tr>
						<tr>
							<td style="background-color: #E5EFF8;" class="moduloAbajo1"
								valign="bottom">
								<table border="0" width="100%" align="left"
									class="tabla_sin_layout">
									<tr>
										<td colspan="3" height="20">
											<span style="margin-left: 10px;"> Seleccionar : </span>
											<span class="opcion_selecionar"
												onClick="javascript:checkCheckBox(true);">Todas</span>,
											<span class="opcion_selecionar"
												onClick="javascript:checkCheckBox(false);">Ninguna</span>
										</td>
									</tr>
									<tr>
										<td>
											<input type="button" value="Imprimir"
												style="font-weight: bolder; cursor: pointer; margin-left: 10px; margin-right: 5px;"
												onclick="" class="form_button">
											<input type="button" value="Excel"
												style="font-weight: bolder; cursor: pointer; margin-left: 10px; margin-right: 5px;"
												onclick="" class="form_button">
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr style="display: none;">
							<td height="0">
								<div style="display: none;">
									<form name="busquedaSecDoc"
										action="<%=request.getContextPath()%>/reporte/AuditoriaSecDoc.action"
										method="post">
										<input type="text" name="busquedaPeriodo"
											value="<%=busquedaPeriodo%>" id="idbusquedaPeriodo">
										<input type="text" name="busquedaSede"
											value="<%=busquedaSede%>" id="idbusquedaSede">
										<input type="text" name="busquedaFecha1"
											value="<%=busquedaFecha1%>" id="idbusquedaFecha1">
										<input type="text" name="busquedaFecha2"
											value="<%=busquedaFecha2%>" id="idbusquedaFecha2">
										<input type="text" name="busquedaFamilia"
											value="<%=busquedaFamilia%>" id="idbusquedaFamilia">
									</form>
								</div>
								<div style="display: none;">
									<form
										action="<%=request.getContextPath()%>/reporte/AuditoriaSecDocImprimir.action"
										enctype="application/x-www-form-urlencoded" method="post"
										name="matricula_imprimir" target="secDoc" >
										<select name="matriculas_array" multiple="multiple"
											id="matriculas_imprimir">
										</select>
									</form>
								</div>
								<div style="display: none;">
									<form
										action="<%=request.getContextPath()%>/reporte/AuditoriaSecDocExcel.action"
										enctype="application/x-www-form-urlencoded" method="post"
										name="matricula_excel" >
										<select name="matriculas_array" multiple="multiple"
											id="matriculas_excel">
										</select>
									</form>
								</div>
							</td>
						</tr>
					</table>
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
