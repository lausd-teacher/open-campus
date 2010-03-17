<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="edu.tecsup.lms.util.Formato"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%@  page import="edu.tecsup.lms.util.Util"%>
<%@  page import="java.util.Collection"%>
<%@ page import="edu.tecsup.lms.modelo.Usuario"%>
<%@  page import="edu.tecsup.lms.modelo.Especialidad"%>
<%@  page import="edu.tecsup.lms.modelo.Sede"%>
<%@  page import="edu.tecsup.lms.modelo.Departamento"%>
<%@  page import="edu.tecsup.lms.modelo.Periodo"%>
<%@  page import="edu.tecsup.lms.modelo.reporte.UsuarioReporte"%>
<%@  page import="edu.tecsup.lms.modelo.reporte.FichaReporte"%>
<%
			Usuario usuario = (Usuario) request.getSession().getAttribute(
			Constante.USUARIO_ACTUAL);
	Collection<Especialidad> formacion = (Collection<Especialidad>) request
			.getAttribute("FORMACION_PREDETERMINADA");
	Collection<Periodo> periodos = (Collection<Periodo>) request
			.getAttribute("PERIODO_PREDETERMINADA");
	Collection<UsuarioReporte> usuarios = (Collection<UsuarioReporte>) request
			.getAttribute("USUARIOS_BUSQUEDA");
	String busquedaFecha1 = String.valueOf(request
			.getAttribute("busquedaFecha1"));
	String busquedaFecha2 = String.valueOf(request
			.getAttribute("busquedaFecha2"));
	String busquedaExacta = String.valueOf(request
			.getAttribute("busquedaExacta"));
	String busquedaFormacion = String.valueOf(request
			.getAttribute("busquedaFormacion"));
	String busquedaDepartamento = String.valueOf(request
			.getAttribute("busquedaDepartamento"));
	String busquedaCiclo = String.valueOf(request
			.getAttribute("busquedaCiclo"));
	String busquedaSede = String.valueOf(request
			.getAttribute("busquedaSede"));
	String busquedaPeriodo = String.valueOf(request
			.getAttribute("busquedaPeriodo"));
	String busquedaUsuario = String.valueOf(request
			.getAttribute("busquedaUsuario"));
	String busquedaUsuarioNombre = String.valueOf(request
			.getAttribute("busquedaUsuarioNombre"));
	String busquedaEmpresa = String.valueOf(request
			.getAttribute("busquedaEmpresa"));
	String busquedaContacto = String.valueOf(request
			.getAttribute("busquedaContacto"));
	String busquedaCurso = String.valueOf(request
			.getAttribute("busquedaCurso"));
	String busquedaCursoNombre = String.valueOf(request
			.getAttribute("busquedaCursoNombre"));
	String busquedaGrupo = String.valueOf(request
			.getAttribute("busquedaGrupo"));
	String posicionPagina = String.valueOf(request
			.getAttribute("posicionPagina"));
	String totalRegistro = String.valueOf(request
			.getAttribute("totalRegistro"));
	String cantidadRegistro = String.valueOf(request
			.getAttribute("cantidadRegistro"));
	String busquedaIngreso = String.valueOf(request
			.getAttribute("busquedaIngreso"));
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
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/tooltip/tooltip.js"></script>
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/js/divlista/divlista.js'></script>
		<link href="<%=request.getContextPath()%>/js/divlista/divlista.css"
			rel="stylesheet" type="text/css">
		<script>
				var CANTIDADREGISTRO =<%=cantidadRegistro%>;
		</script>
	</head>
	<%@include file="../../comun/capas/reloj.jsp"%>
	<body onLoad="mostrarReloj();">
		<div id="contenedor">
			<s:include value="/comun/bienvenida.jsp"></s:include>
			<div id="cuerpo" style="background-color: white;">
				<div id="principal" style="width: 980px;padding-bottom: 7px;">
				<!-- form action="http://www.google.com.pe" method="post" target="Nuevo"
					onsubmit="window.open(xGetContextPath()+'/comun/buzon/nuevo.jsp', 'Nuevo', 'height=350,width=510,scrollbars=no');">
					<input type="submit" value="Hola">
				</form -->
				
				<%
					if(false && usuario.getRolPredeterminado() != Constante.ROL_CAMPUS_MONITOR_EMPRESA){
				 %>
				
				<form method="post" id="tecsupVirtual">
					<table width="975" border="0" cellpadding="3" cellspacing="0"
					class="tablaFicha">
						<tr class="fon_cab">
							<td colspan="8" class="tit_cab">
								Auditor&iacute;a Tecsup Virtual
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
								<input type="button" value="Reporte" class="form_button" onclick="if(validarReporte())searchAuditoria('<%=request.getContextPath()%>/reporte/ReporteTecsupVirtual.action')"/>
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
								<input type="button" value="Auditoría" class="form_button" onclick="if(validarAuditoria())searchAuditoria('<%=request.getContextPath()%>/reporte/AuditoriaTecsupVirtual.action')"/>
							</td>
						</tr>
					</table>
				</form>
					
				<%
					}
				 %>
				
				<form onsubmit="return busquedaReporte();">
					<table width="975" border="0" cellpadding="0" cellspacing="0"
						class="tablaFicha">
						<tr class="fon_cab">
							<td height="20" colspan="5" class="tit_cab">
								<s:text name="portal.menu.auditoria"></s:text>
							</td>
						</tr>
						<tr>
							<td width="975" align="center" valign="top">								
								<table border="0" cellpadding="0" cellspacing="5" width="95%"
									style=" background-color: white;">
									<tr>
										<td align="right">
											Nombre del curso :
										</td>
										<td  align="left">
											<input type="text" style="font-size: 10pt;" size="48" id="form_busquedaCurso"
												value="<%out.print(busquedaCurso);%>" onkeyup="buscarPorKey(event);">
										</td>
										
										<%
											if(usuario.getRolPredeterminado() != Constante.ROL_CAMPUS_MONITOR_EMPRESA){
										 %>
										
										<td align="right">
											Formaci&oacute;n :
										</td>
										<td colspan="1" align="left">
											<div style="z-index:-1000;">
												<select id="form_busquedaFormacion" >
													<option value="0"
														<%if("0".equals(busquedaFormacion)){ out.print("selected=\"selected\"");}%>>
														Todos
													</option>
													<%
													for (Especialidad forma : formacion) {
													%>
													<option value="<%=forma.getCodigo()%>"
														<%if(forma.getCodigo().equals(busquedaFormacion)){ out.print("selected=\"selected\"");}%>>
														<%=forma.getNombre()%>
													</option>
													<%
													}
													%>
												</select>
											</div>
										</td>
										
										<%
											}
										 %>
										
									</tr>
									<tr>
										<td align="right">
											Usuario :
										</td>
										<td  align="left">
											<input type="text" size="15" id="form_busquedaUsuario"
												value="<%out.print(busquedaUsuario);%>" onkeyup="buscarPorKey(event);">
										</td>
										
										<%
											if(usuario.getRolPredeterminado() != Constante.ROL_CAMPUS_MONITOR_EMPRESA){
										 %>
										
										<td align="right">
											Grupo :
										</td>
										<td align="left">
											<SELECT id="form_busquedaGrupo">
												<option value="0" <%if("0".equals(busquedaGrupo)){out.print("selected=\"selected\"");} %>>Todos</option>
												<option value="<%=Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE%>" <%if(String.valueOf(Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE).equals(busquedaGrupo)){out.print("selected=\"selected\"");} %>>Docente</option>
												<option value="<%=Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE%>" <%if(String.valueOf(Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE).equals(busquedaGrupo)){out.print("selected=\"selected\"");} %>>Estudiante</option>
											</SELECT>
										</td>
										
										<%
											}
										 %>
										
									</tr>
									<tr>
										<td align="right">
											Nombres y/o Apellidos :
										</td>
										<td colspan="1" align="left">
											<input type="text" size="48"  style="font-size: 10pt;" id="form_busquedaUsuarioNombre"
												value="<%out.print(busquedaUsuarioNombre);%>" onkeyup="buscarPorKey(event);">
										</td>	
										
										<%
											if(usuario.getRolPredeterminado() != Constante.ROL_CAMPUS_MONITOR_EMPRESA){
										 %>
																		
										<td align="right">
											Departamento :
										</td>
										<td colspan="1" align="left">
											<select id="form_busquedaDepartamento">
												<option value="0"
													<%if("0".equals(busquedaDepartamento)){ out.print("selected=\"selected\"");}%>>
													Todos
												</option>
												<%
												for (Departamento forma : Util.getDepartamentos()) {
												%>
												<option value="<%=forma.getCodigo()%>"
													<%if(forma.getCodigo().equals(busquedaDepartamento)){ out.print("selected=\"selected\"");}%>>
													<%=forma.getNombre()%>
												</option>
												<%
												}
												%>
											</select>
										</td>
										
										<%
											}
										 %>
										
									</tr>									
									<tr>
									
										<td align="right">
											Empresa Auspiciadora :
										</td>
										<td colspan="1" align="left">
										<%
											if(usuario.getRolPredeterminado() != Constante.ROL_CAMPUS_MONITOR_EMPRESA){
										 %>
											<input type="text" size="40" id="form_busquedaEmpresa"
												value="<%out.print(busquedaEmpresa);%>" onkeyup="buscarPorKey(event);">
										<%
											}else{
									 	%>
									 		<input type="text" size="40" id="form_busquedaEmpresa"
												value="<%out.print(busquedaEmpresa);%>" readonly="readonly">
									 	<%
									 		}
									 	 %>
										
										</td>
										
										<%
											if(usuario.getRolPredeterminado() != Constante.ROL_CAMPUS_MONITOR_EMPRESA){
										 %>
										
										<td align="right">
											Ciclo :
										</td>
										<td colspan="1" align="left">
											<select id="form_busquedaCiclo">
												<option value="0"
													<%if("0".equals(busquedaCiclo)){ out.print("selected=\"selected\"");}%>>
													Todos
												</option>
												<option value="1"
													<%if("1".equals(busquedaCiclo)){ out.print("selected=\"selected\"");}%>>
													1ero.
												</option>
												<option value="2"
													<%if("2".equals(busquedaCiclo)){ out.print("selected=\"selected\"");}%>>
													2do.
												</option>
												<option value="3"
													<%if("3".equals(busquedaCiclo)){ out.print("selected=\"selected\"");}%>>
													3ero.
												</option>
												<option value="4"
													<%if("4".equals(busquedaCiclo)){ out.print("selected=\"selected\"");}%>>
													4to.
												</option>
												<option value="5"
													<%if("5".equals(busquedaCiclo)){ out.print("selected=\"selected\"");}%>>
													5to.
												</option>
												<option value="6"
													<%if("6".equals(busquedaCiclo)){ out.print("selected=\"selected\"");}%>>
													6to.
												</option>
												<option value="7"
													<%if("7".equals(busquedaCiclo)){ out.print("selected=\"selected\"");}%>>
													7mo.
												</option>
												<option value="8"
													<%if("8".equals(busquedaCiclo)){ out.print("selected=\"selected\"");}%>>
													8vo.
												</option>
											</select>
										</td>
										
										<%
											}
										 %>
										
									</tr>
									<tr>
										<td align="right">
											Fecha Inicio :
										</td>
										<td colspan="1" align="left">
											<label style="margin-left: 20px; margin-right: 20px;">
												De
											</label>
											<input type="text" id="form_fechaInicio1" size="8"
												class="form_text" readonly="readonly" name="fActivacion1"
												value="<%=busquedaFecha1%>" />
											<label style="margin-left: 20px; margin-right: 20px;">
												a
											</label>
											<input type="text" id="form_fechaInicio2" size="8"
												class="form_text" readonly="readonly" name="fActivacion2"
												value="<%=busquedaFecha2%>" />
										</td>
										
										<%
											if(usuario.getRolPredeterminado() != Constante.ROL_CAMPUS_MONITOR_EMPRESA){
										 %>
										
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
										
										<%
											}
										 %>
										
									</tr>
									
										<%
											if(usuario.getRolPredeterminado() != Constante.ROL_CAMPUS_MONITOR_EMPRESA){
										 %>
									
									<tr>
										<td align="right">
											Ingresos :
										</td>
										<td align="left">
											<select id="form_busquedaIngreso" style="z-index:-1">
												<option value="-1" <%if("-1".equals(busquedaIngreso)){ out.print("selected=\"selected\"");}%>>Todos</option>
												<option value="0" <%if("0".equals(busquedaIngreso)){ out.print("selected=\"selected\"");}%>>0 ingresos</option>
												<option value="10" <%if("10".equals(busquedaIngreso)){ out.print("selected=\"selected\"");}%>>Menos de 10</option>
												<option value="20" <%if("20".equals(busquedaIngreso)){ out.print("selected=\"selected\"");}%>>Menos de 20</option>
												<option value="50" <%if("50".equals(busquedaIngreso)){ out.print("selected=\"selected\"");}%>>Menos de 50</option>
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
									</tr>
									
									<%
										}
									 %>
									
									<tr>
										<td align="right" colspan="4">
											<input type="button" value="Buscar" class="form_button"
												onclick="javascript:busquedaReporte();">
										</td>
									</tr>
									<script language="JavaScript" type="text/javascript">
										Calendar.setup({inputField:"form_fechaInicio1", ifFormat:"%d/%m/%Y",  singleClick:true, onUpdate:catcalc});
										Calendar.setup({inputField:"form_fechaInicio2", ifFormat:"%d/%m/%Y",  singleClick:true, onUpdate:catcalc});									
										if(xGetElementById('boxFecha1'))Calendar.setup({inputField:"boxFecha1", ifFormat:"%d/%m/%Y %H:%M",  singleClick:true, showsTime:true, onUpdate:catcalc});
										if(xGetElementById('boxFecha2'))Calendar.setup({inputField:"boxFecha2", ifFormat:"%d/%m/%Y %H:%M",  singleClick:true, showsTime:true, onUpdate:catcalc});									
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
											<input type="button" value="Reporte"
												style="font-weight: bolder; cursor: pointer; margin-left: 10px; margin-right: 5px;"
												onclick="crearReportePDF();" class="form_button">
										</td>
										<td valign="bottom">
											<label style="padding-right: 5px;">
												Ver&nbsp;:&nbsp;&nbsp;
											</label>
											<select class="form_button" id="registro"
												onChange="javascript:cambiarCantidadRegistro(this.options[this.selectedIndex].value);">
												<option value="25"
													<%if("25".equals(cantidadRegistro)){out.print(" selected=\"selected\" ");} %>>
													25
												</option>
												<option value="50"
													<%if("50".equals(cantidadRegistro)){out.print(" selected=\"selected\" ");} %>>
													50
												</option>
												<option value="100"
													<%if("100".equals(cantidadRegistro)){out.print(" selected=\"selected\" ");} %>>
													100
												</option>
												<option value="1000"
													<%if("1000".equals(cantidadRegistro)){out.print(" selected=\"selected\" ");} %>>
													1000
												</option>
											</select>
											&nbsp;registros por p&aacute;gina.
										</td>
										<td valign="bottom" align="right">
											<table style="margin-right: 10px;" cellpadding="2"
												cellspacing="2" border="0">
												<tr>
													<%
														int int_cantidadRegistro = Integer.parseInt(cantidadRegistro);
														int int_posicionpagina = Integer.parseInt(posicionPagina);
														int int_totalRegistro = Integer.parseInt(totalRegistro);
														int cantidadPaginaFutu = (int) Math
																.ceil((double) (int_totalRegistro - (int_cantidadRegistro * int_posicionpagina))
																/ int_cantidadRegistro);
														int cantidadRegistroAca = int_totalRegistro
																- (int_posicionpagina * int_cantidadRegistro);
														int int_principal = (int_posicionpagina * int_cantidadRegistro) + 1;
														int int_a = 0;
														if (int_cantidadRegistro <= cantidadRegistroAca) {
															int_a = (int_posicionpagina * int_cantidadRegistro)
															+ int_cantidadRegistro;
														} else {
															int_a = (int_posicionpagina * int_cantidadRegistro)
															+ cantidadRegistroAca;
														}
													%>
													<td>
														<%
														if (2 <= int_posicionpagina) {
														%>
														<span class="opcion_selecionar"
															style="text-decoration: underline; font-weight: bold;"
															onclick="javscript:cambiarPagina('0');"> &lt;&lt;
														</span>
														<%
														} else {
														%>
														<span class="opcion_selecionar" style="color: silver;">
															&lt;&lt; </span>
														<%
														}
														%>
													</td>
													<td>
														<%
														if (1 <= int_posicionpagina) {
														%>
														<span class="opcion_selecionar"
															style="text-decoration: underline; font-weight: bold;"
															onclick="javscript:cambiarPagina('<%=int_posicionpagina - 1%>');">
															&lt; </span>
														<%
														} else {
														%>
														<span class="opcion_selecionar" style="color: silver;"}
														>
															&lt; </span>
														<%
														}
														%>
													</td>
													<td>
														<%
																if (0 == int_a) {
																out.print(0);
															} else {
																out.print(int_principal);
															}
														%>
														-
														<%=int_a%>
														de
														<%=totalRegistro%>
														registro(s)
													</td>
													<td>
														<%
														if (1 < cantidadPaginaFutu) {
														%>
														<span class="opcion_selecionar"
															style="text-decoration: underline; font-weight: bold;"
															}
														onclick="javscript:cambiarPagina('<%=int_posicionpagina + 1%>');">
															&gt; </span>
														<%
														} else {
														%>
														<span class="opcion_selecionar" style="color: silver;"}
														>
															&gt; </span>
														<%
														}
														%>
													</td>
													<td>
														<%
														if (2 < cantidadPaginaFutu) {
														%>
														<label class="opcion_selecionar"
															style="text-decoration: underline; font-weight: bold;"
															onclick="javscript:cambiarPagina('<%=int_posicionpagina + cantidadPaginaFutu - 1%>');">
															&gt;&gt;
														</label>
														<%
														} else {
														%>
														<label class="opcion_selecionar" style="color: silver;">
															&gt;&gt;
														</label>
														<%
														}
														%>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td colspan="3" height="20">
											<span style="margin-left: 10px;"> Seleccionar : </span>
											<span class="opcion_selecionar"
												onClick="javascript:checkCheckBox(true);">Todas</span>,
											<span class="opcion_selecionar"
												onClick="javascript:checkCheckBox(false);">Ninguna</span>,
											<span class="opcion_selecionar"
												onClick="javascript:desplegarTRS(true);">Expandir Todo</span>,
											<span class="opcion_selecionar"
												onClick="javascript:desplegarTRS(false);">Contraer Todo</span>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="moduloAbajo1" valign="top" align="left" colspan="2">
								<table width="100%" border="0" cellpadding="0" cellspacing="0"
									style="background-color: #EEEEEE;" class="">
									<tr>
										<td width="10" height="20">
											&nbsp;
										</td>
										<td width="15">
											&nbsp;
										</td>
										<td width="190" align="left" valign="bottom">
											<label style="font-weight: bold;">
												Nombre del Curso
											</label>
										</td>
										<td width="40" align="center" valign="bottom">
											<label style="font-weight: bold;">
												Sec.
											</label>
										</td>
										<td width="25" align="center" valign="bottom">
											<label style="font-weight: bold;">
												Ingresos
											</label>
										</td>
										<!-- td width="40" valign="bottom">
											<label style="font-weight: bold; margin-left: 5px;">
												Grupo
											</label>
										</td-->
										<td width="65" valign="bottom">
											<label style="font-weight: bold; margin-left: 5px;">
												Estado
											</label>
										</td>
										<td width="40" valign="bottom">
											<label style="font-weight: bold; margin-left: 5px;">
												Promedio
											</label>
										</td>
										<td width="65" valign="bottom">
											<label style="font-weight: bold; margin-left: 5px;">
												Reporte
											</label>
										</td>
										<td width="50" valign="bottom">
											<label style="font-weight: bold;">
												Periodo
											</label>
										</td>
										<!-- td width="25" valign="bottom" align="center">
											<label style="font-weight: bold;">
												Tipo
											</label>
										</td-->

										<td width="135" valign="bottom">
											<label style="font-weight: bold; margin-left: 5px;">
												Formaci&oacute;n
											</label>
										</td>

										<td width="35" valign="bottom">
											<label style="font-weight: bold;">
												Auspiciador
											</label>
										</td>

										<td width="25" valign="bottom">
											<label style="font-weight: bold; margin-left: 5px;">
												Sede
											</label>
										</td>
										<!-- td width="70" valign="bottom" align="center">
											<label style="font-weight: bold; margin-left: 5px;">
												Dpto.
											</label>
										</td-->
									</tr>
								</table>
								<%
								if (0 != usuarios.size()) {
								%>
								<table width="100%" id="todos_items_busqueda" cellpadding="1"
									cellspacing="0" class="tabla_sin_layout" border="0">
									<tr height="1"
										style=" background-color: #EEEEEE;">
										<td width="15"  height="1" style="border-bottom: solid #7fa9ed 1px;"/>
										<td width="15" style="border-bottom: solid #7fa9ed 1px;">
										<td width="180" style="border-bottom: solid #7fa9ed 1px;"/>
										<td width="40" style="border-bottom: solid #7fa9ed 1px;"/>
										<td width="25" style="border-bottom: solid #7fa9ed 1px;"/>
										<!--  td width="40" style="border-bottom: solid #7fa9ed 1px;"/-->
										<td width="65" style="border-bottom: solid #7fa9ed 1px;"/>
										<td width="40" style="border-bottom: solid #7fa9ed 1px;"/>
										<td width="65" style="border-bottom: solid #7fa9ed 1px;"/>
										<td width="50" style="border-bottom: solid #7fa9ed 1px;"/>
										<!-- td width="25" style="border-bottom: solid #7fa9ed 1px;"/-->
										<td width="135" style="border-bottom: solid #7fa9ed 1px;"/>
										<td width="85" style="border-bottom: solid #7fa9ed 1px;"/>
										<td width="25" style="border-bottom: solid #7fa9ed 1px;"/>
										<!-- td width="70" style="border-bottom: solid #7fa9ed 1px;"/-->
									</tr>
									<%
											int a = 0;
											int b = 0;
											for (UsuarioReporte usuarioReporte : usuarios) {
												a++;
												b = 0;
									%>
									<tr id="tr_<%=a%>"  height="20" >
										
										<td style="border-bottom: 1px solid #7EAAD1;background-color: #f9f9f9;">
											<input type="checkbox" id="abcdefgh"
												value="<%=usuarioReporte.getIdUsuario()%>"
												onclick="javascript:seleccionarDIVGrupo('tr_<%=a%>',this)">
										</td>
										<td style="color: black;border-bottom: 1px solid #7EAAD1;background-color: #f9f9f9;" colspan="10" >
											<label
												onmouseover="verToolTip('<%="<strong>" + usuarioReporte.getIdUsuario()
							+ "</strong> - "
							+ usuarioReporte.getNombreCompleto()%>', this);"
												onmouseout="ocultarToolTip()"
												onclick="abrirTDS('tr_<%=a%>_');" style="cursor: pointer;font-weight: bold;color: #0E6BC9;">
												&nbsp;&nbsp;&nbsp;<%=usuarioReporte.getNombreCompleto()%>
											</label>
										</td>

									</tr>
									<%
											for (FichaReporte reporte : usuarioReporte.getFichas()) {
											b++;
									%>

									<tr id="tr_<%=a%>_<%=b%>" class="fon_color02" height="20" style="display: none;visibility: hidden;" >
										
										<td width="20" style="border-bottom: 1px solid #7EAAD1;">
											<input type="hidden" value="<%=reporte.getIdMatricula()%>">
										</td>
										<td width="20"  style="border-bottom: 1px solid #7EAAD1;">
												<input type="checkbox"  name="<%=reporte.getIdRol()%>" 
												value="<%=reporte.getIdMatricula()%>"
												onclick="javascript:seleccionarDIV('tr_<%=a%>','tr_<%=a%>_<%=b%>',this)">
										</td>
										<td  style="color: black;cursor: pointer;border-bottom: 1px solid #7EAAD1;">
											<label style="cursor: pointer;"
												onmouseover="verToolTip('<%=reporte.getNombre()%>', this);"
												onmouseout="ocultarToolTip()"
												<%if(Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE==reporte.getIdRol() ){ %>
												onclick="javascript:abrirAuditoria('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
												<%}else{ %>
												onclick="javascript:abrirReporte('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
												<%} %>>
												<%=reporte.getNombre()%>
											</label>
										</td>
										<td  align="right"
											style="border-right: 1px solid #7EAAD1;border-bottom: 1px solid #7EAAD1;"
											onmouseover="verToolTip('<%=reporte.getSeccion()%>', this);"
											onmouseout="ocultarToolTip()"
											<%if(Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE==reporte.getIdRol() ){ %>
											onclick="javascript:abrirAuditoria('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%}else{ %>
											onclick="javascript:abrirReporte('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%} %>>
											<label style="margin-right: 5px;cursor: pointer;">
												<%=reporte.getSeccion()%>
											</label>
										</td>
										<td  align="right"
											style="border-right: 1px solid #7EAAD1;border-bottom: 1px solid #7EAAD1;"
											onmouseover="verToolTip('<%=reporte.getCantidadIngreso()%>', this);"
											onmouseout="ocultarToolTip()"
											<%if(Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE==reporte.getIdRol() ){ %>
											onclick="javascript:abrirAuditoria('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%}else{ %>
											onclick="javascript:abrirReporte('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%} %>>
											<label style="margin-right: 5px;cursor: pointer;font-weight: bold;">
												<%=reporte.getCantidadIngreso()%>
											</label>
										</td>
										<% if(false){ %>
										<td 
											style="border-right: 1px solid #7EAAD1;border-bottom: 1px solid #7EAAD1;"
											onmouseover="verToolTip('<%=reporte.getRol()%>', this);"
											onmouseout="ocultarToolTip()"
											<%if(Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE==reporte.getIdRol() ){ %>
											onclick="javascript:abrirAuditoria('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%}else{ %>
											onclick="javascript:abrirReporte('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%} %>>
											<label style="margin-left: 5px;cursor: pointer;">
												<%=reporte.getRol()%>
											</label>
										</td>
										<%} %>
										<td 
											style="border-right: 1px solid #7EAAD1;border-bottom: 1px solid #7EAAD1;"
											onmouseover="verToolTip('<%=Util.getFichaEstadoFecha(reporte
												.getEstado())%>', this);"
											onmouseout="ocultarToolTip()"
											<%if(Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE==reporte.getIdRol() ){ %>
											onclick="javascript:abrirAuditoria('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%}else{ %>
											onclick="javascript:abrirReporte('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%} %>>
											<label style="margin-left: 5px;cursor: pointer;">
												<%=Util.getFichaEstadoFecha(reporte
												.getEstado())%>
											</label>
										</td>
										<td 
											style="border-right: 1px solid #7EAAD1;border-bottom: 1px solid #7EAAD1;"
											onmouseover="verToolTip('<%=reporte.getEstadoReporte()%>', this);"
											onmouseout="ocultarToolTip()"
											<%if(Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE==reporte.getIdRol() ){ %>
											onclick="javascript:abrirAuditoria('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%}else{ %>
											onclick="javascript:abrirReporte('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%} %>>
											<label style="margin-left: 5px;cursor: pointer;">
												<%=reporte.getEstadoReporte()%>
											</label>
										</td>
										<td 
											style="border-right: 1px solid #7EAAD1;border-bottom: 1px solid #7EAAD1;"
											onmouseover="verToolTip('<%=reporte.getPeriodo() + " ( <strong>"
								+ reporte.getFechaInicio()
								+ "</strong> a <strong>"
								+ reporte.getFechaFin() + "</strong> )"%>', this);"
											onmouseout="ocultarToolTip()"
											<%if(Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE==reporte.getIdRol() ){ %>
											onclick="javascript:abrirAuditoria('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%}else{ %>
											onclick="javascript:abrirReporte('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%} %>>
											<label style="margin-left: 5px;cursor: pointer;">
												<%=reporte.getPeriodo()%>
											</label>
										</td>
										<% if(false){ %>
										<td 
										onmouseover="verToolTip('<%=reporte.getFamilia()%>', this);"
											onmouseout="ocultarToolTip()"
											style="border-right: 1px solid #7EAAD1;border-bottom: 1px solid #7EAAD1;"
											<%if(Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE==reporte.getIdRol() ){ %>
											onclick="javascript:abrirAuditoria('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%}else{ %>
											onclick="javascript:abrirReporte('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%} %>>
											<label style="margin-left: 5px;cursor: pointer;">
												<%=reporte.getFamilia()%>
											</label>
										</td>
										<%} %>
										<td 
											style="border-right: 1px solid #7EAAD1;border-bottom: 1px solid #7EAAD1;"
											onmouseover="verToolTip('<%=reporte.getFormacion()%>', this);"
											onmouseout="ocultarToolTip()"
											<%if(Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE==reporte.getIdRol() ){ %>
											onclick="javascript:abrirAuditoria('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%}else{ %>
											onclick="javascript:abrirReporte('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%} %>>
											<label style="margin-left: 5px;cursor: pointer;">
												<%=reporte.getFormacion()%>
											</label>
										</td>
										<td 
											style="border-right: 1px solid #7EAAD1;border-bottom: 1px solid #7EAAD1;"
											onmouseover="verToolTip('<%=reporte.getAuspiciador() %>', this);"
											onmouseout="ocultarToolTip()"
											<%if(Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE==reporte.getIdRol() ){ %>
											onclick="javascript:abrirAuditoria('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%}else{ %>
											onclick="javascript:abrirReporte('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%} %>>
											<label style="margin-left: 5px;cursor: pointer;">
												<%=reporte.getAuspiciador() %>
											</label>
										</td>
										<td 
											style="border-right: 1px solid #7EAAD1;border-bottom: 1px solid #7EAAD1;"
											<%if(Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE==reporte.getIdRol() ){ %>
											onclick="javascript:abrirAuditoria('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%}else{ %>
											onclick="javascript:abrirReporte('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%} %>>
											<label style="margin-left: 5px;cursor: pointer;">
												<%=reporte.getSede()%>
											</label>
										</td>
										<% if(false){ %>
										<td   style="border-bottom: 1px solid #7EAAD1;"
											<%if(Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE==reporte.getIdRol() ){ %>
											onclick="javascript:abrirAuditoria('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%}else{ %>
											onclick="javascript:abrirReporte('<%=request.getContextPath()%>','<%=reporte.getIdMatricula()%>');"
											<%} %>>
											<label style="margin-left: 5px;cursor: pointer;">
												<%=reporte.getDepartamento()%>
											</label>
										</td>
										<%} %>
									</tr>

									<%
											}
											}
									%>
								</table>
								<div <%if(10 > ( int_a -int_principal) ) { out.print("style=\"height:"+( 10 -( int_a -int_principal) ) * 15 +"px\"");}%>>
									&nbsp;
								</div>
								<script language="JavaScript" type="text/javascript">
												limpiarCheckBox();
								</script>
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
							<td style="background-color: #E5EFF8;"
								valign="bottom">
								<table border="0" width="100%" align="left"
									class="tabla_sin_layout">
									<tr>
										<td>
											<input type="button" value="Reporte"
												style="font-weight: bolder; cursor: pointer; margin-left: 10px; margin-right: 5px;"
												onclick="crearReportePDF();" class="form_button">
										</td>
										<td valign="bottom">
											<label style="padding-right: 5px;">
												Ver&nbsp;:&nbsp;&nbsp;
											</label>
											<select class="form_button" id="registro"
												onChange="javascript:cambiarCantidadRegistro(this.options[this.selectedIndex].value);">
												<option value="25"
													<%if("25".equals(cantidadRegistro)){out.print(" selected=\"selected\" ");} %>>
													25
												</option>
												<option value="50"
													<%if("50".equals(cantidadRegistro)){out.print(" selected=\"selected\" ");} %>>
													50
												</option>
												<option value="100"
													<%if("100".equals(cantidadRegistro)){out.print(" selected=\"selected\" ");} %>>
													100
												</option>
												<option value="1000"
													<%if("1000".equals(cantidadRegistro)){out.print(" selected=\"selected\" ");} %>>
													1000
												</option>
											</select>
											&nbsp;registros por p&aacute;gina.
										</td>
										<td valign="bottom" align="right">
											<table style="margin-right: 10px;" cellpadding="2"
												cellspacing="2" border="0">
												<tr>
													<td>
														<%
														if (2 <= int_posicionpagina) {
														%>
														<span class="opcion_selecionar"
															style="text-decoration: underline; font-weight: bold;"
															onclick="javscript:cambiarPagina('0');"> &lt;&lt;
														</span>
														<%
														} else {
														%>
														<span class="opcion_selecionar" style="color: silver;">
															&lt;&lt; </span>
														<%
														}
														%>
													</td>
													<td>
														<%
														if (1 <= int_posicionpagina) {
														%>
														<span class="opcion_selecionar"
															style="text-decoration: underline; font-weight: bold;"
															onclick="javscript:cambiarPagina('<%=int_posicionpagina - 1%>');">
															&lt; </span>
														<%
														} else {
														%>
														<span class="opcion_selecionar" style="color: silver;"}
														>
															&lt; </span>
														<%
														}
														%>
													</td>
													<td>
														<%
																if (0 == int_a) {
																out.print(0);
															} else {
																out.print(int_principal);
															}
														%>
														-
														<%=int_a%>
														de
														<%=totalRegistro%>
														registro(s)
													</td>
													<td>
														<%
														if (1 < cantidadPaginaFutu) {
														%>
														<span class="opcion_selecionar"
															style="text-decoration: underline; font-weight: bold;"
															}
														onclick="javscript:cambiarPagina('<%=int_posicionpagina + 1%>');">
															&gt; </span>
														<%
														} else {
														%>
														<span class="opcion_selecionar" style="color: silver;"}
														>
															&gt; </span>
														<%
														}
														%>
													</td>
													<td>
														<%
														if (2 < cantidadPaginaFutu) {
														%>
														<label class="opcion_selecionar"
															style="text-decoration: underline; font-weight: bold;"
															onclick="javscript:cambiarPagina('<%=int_posicionpagina + cantidadPaginaFutu - 1%>');">
															&gt;&gt;
														</label>
														<%
														} else {
														%>
														<label class="opcion_selecionar" style="color: silver;">
															&gt;&gt;
														</label>
														<%
														}
														%>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td colspan="3" height="20">
											<span style="margin-left: 10px;"> Seleccionar : </span>
											<span class="opcion_selecionar"
												onClick="javascript:checkCheckBox(true);">Todas</span>,
											<span class="opcion_selecionar"
												onClick="javascript:checkCheckBox(false);">Ninguna</span>,
											<span class="opcion_selecionar"
												onClick="javascript:desplegarTRS(true);">Expandir Todo</span>,
											<span class="opcion_selecionar"
												onClick="javascript:desplegarTRS(false);">Contraer Todo</span>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</form>
					<div style="display: none;">
						<form name="busquedaTipo"
							action="<%=request.getContextPath()%>/reporte/Auditoria.action"
							method="post">
							<input type="text" name="busquedaUsuario"
								value="<%=busquedaUsuario%>" id="idbusquedaUsuario">
							<input type="text" name="busquedaUsuarioNombre"
								value="<%=busquedaUsuarioNombre%>" id="idbusquedaUsuarioNombre">
							<input type="text" name="busquedaEmpresa"
								value="<%=busquedaEmpresa%>" id="idbusquedaEmpresa">
							<input type="text" name="busquedaContacto"
								value="<%=busquedaContacto%>" id="idbusquedaContacto">
							<input type="text" name="busquedaCurso"
								value="<%=busquedaCurso%>" id="idbusquedaCurso">
							<input type="text" name="busquedaCursoNombre"
								value="<%=busquedaCursoNombre%>" id="idbusquedaCursoNombre">
							<input type="text" name="busquedaGrupo"
								value="<%=busquedaGrupo%>" id="idbusquedaGrupo">
							<input type="text" name="busquedaExacta"
								value="<%=busquedaExacta%>" id="idbusquedaExacta">
							<input type="text" name="busquedaPeriodo"
								value="<%=busquedaPeriodo%>" id="idbusquedaPeriodo">
							<input type="text" name="busquedaFormacion"
								value="<%=busquedaFormacion%>" id="idbusquedaFormacion">
							<input type="text" name="busquedaSede"
								value="<%=busquedaSede%>" id="idbusquedaSede">
							<input type="text" name="busquedaFecha1"
								value="<%=busquedaFecha1%>" id="idbusquedaFecha1">
							<input type="text" name="busquedaFecha2"
								value="<%=busquedaFecha2%>" id="idbusquedaFecha2">
							<input type="text" name="busquedaCiclo"
								value="<%=busquedaCiclo%>" id="idbusquedaCiclo">
							<input type="text" name="busquedaIngreso"
								value="<%=busquedaIngreso%>" id="idbusquedaIngreso">
							<input type="text" name="busquedaDepartamento"
								value="<%=busquedaDepartamento%>" id="idbusquedaDepartamento">
							<input type="text" name="posicionPagina"
								value="<%=posicionPagina%>" id="idposicionPagina">
							<input type="text" name="cantidadRegistro"
								value="<%=cantidadRegistro%>" id="id_CantidadRegistro">
						</form>
						<form name="reportePDF"
							action="<%=request.getContextPath()%>/reporte/AuditoriaPDF.action"
							method="post" enctype="application/x-www-form-urlencoded">
							<select name="matriculas_array" id="reportePDF_matri" multiple="multiple"></select>
							<select name="rol_array" id="reportePDF_roles" multiple="multiple"></select>
						</form>
					</div>
				</div>
			</div>
			<div id="pie">
				<%@include file="../../comun/pie.jsp"%>
			</div>
		</div>
		<script type="text/javascript">
			resizeHeight();
			instanceDivLista('form_busquedaCurso','<%=request.getContextPath()
					+ "/admin/ficha/BuscarFichasCurso.action"%>','<%out.print(busquedaCursoNombre);%>');
		</script>
	</body>
</html>
