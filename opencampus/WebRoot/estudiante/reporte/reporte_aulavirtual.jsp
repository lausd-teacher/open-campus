<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@  page import="edu.opencampus.lms.util.Constante"%>
<%@  page import="edu.opencampus.lms.util.Formato"%>
<%@  page import="java.util.GregorianCalendar"%>
<%@  page import="java.util.Calendar"%>
<%@  page import="java.text.DateFormatSymbols"%>
<%@  page import="java.util.Collection"%>
<%@  page import="java.util.Iterator"%>
<%@  page
	import="edu.opencampus.lms.modelo.aulavirtual.reporte.ReporteDetalle"%>
<%@  page
	import="edu.opencampus.lms.modelo.aulavirtual.reporte.MatriculaGTest"%>
<%@  page
	import="edu.opencampus.lms.modelo.aulavirtual.reporte.MatriculaGTrabajo"%>
<%@  page
	import="edu.opencampus.lms.modelo.aulavirtual.reporte.MatriculaGLaboratorio"%>
<%@  page
	import="edu.opencampus.lms.modelo.aulavirtual.reporte.MatriculaGTrabajoGrupal"%>
<%@  page
	import="edu.opencampus.lms.modelo.aulavirtual.reporte.MatriculaGDialogo"%>
<%@  page import="edu.opencampus.lms.modelo.AulaVirtual"%>


<%
	AulaVirtual aulaVirtual = (AulaVirtual) session
			.getAttribute(Constante.AULA_ACTUAL);
	if (aulaVirtual == null) {
		aulaVirtual = (AulaVirtual) request
				.getAttribute(Constante.AULA_ACTUAL + "_TMP");
	}
	//System.out.println(aulaVirtual);

	ReporteDetalle reporteDetalle = (ReporteDetalle) request
			.getAttribute("REPORTE_DETALLE");
	String tipo = (String) request.getAttribute("ATRAS_PAGINA");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" /></title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>

	</head>
	<body>
		<div id="pop_up" style="width: 100%;">
			<div id="prin_01" style="width: 100%;">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="97%">
							<strong><s:text name="aula.alumno.pop_up.curso"/><%=reporteDetalle.getNombreCurso()%> </strong>
						</td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.print()"><s:text name="aula.alumno.pop_up.imprimir"/></a>
						</td>
						<td width="1%">
							<a href="#" class="salir" onClick="window.print()"><img
									src="<%=request.getContextPath()%>/img/impresora.gif"
									width="13" height="13" border="0" />
							</a>
						</td>
						<td width="2%">
							|
						</td>
						<td width="4%">
							<a href="#" class="salir" onClick="window.close()"><s:text name="aula.alumno.pop_up.cerrar"/></a>
						</td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13"
									height="13" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>
			<div id="pop_cuerpo" style="width: 96%;">
				<table width="100%" align="center" cellpadding="3" cellspacing="0"
					class="bor_tabla" border="0">
					<tr class="fon_cab">
						<td class="tit_cab">
							<s:text name="aula.alumno.reporte.titulo"/>
							<%
							if (null != tipo) {
						%>
							<s:text name="aula.alumno.reporte.titulo.alumno"/>
							<%
							}
						%>
						</td>
					</tr>
					<tr>
						<td>
							<table cellpadding="0" cellspacing="0" border="0" width="100%"
								class="reporte_table">
								<tr>
									<td height="23" colspan="10" class="moduloFondo" style="border-right:none;">
										<strong style="margin-left: 5px;"><s:text name="aula.alumno.reporte.datos_generales"/></strong>
									</td>
								</tr>
								<tr>
									<td height="23" colspan="1" width="15%" class="texto1">
										<label>
											<strong><s:text name="aula.alumno.reporte.nombre"/> </strong>
										</label>
									</td>
									<td height="23" colspan="4" width="35%" class="texto1">
										<label>

											<%=reporteDetalle.getNombreCompleto()%>
										</label>
									</td>
									<td height="23" colspan="1" width="15%" class="texto1">
										<label>
											<strong><s:text name="aula.alumno.reporte.fecha_inicio"/> </strong>
										</label>
									</td>
									<td height="23" colspan="4" width="35%">
										<label>
											<%=reporteDetalle.getStringFecha1()%>
										</label>
									</td>
								</tr>

								<tr>
									<td height="23" colspan="1" class="texto">
										<label>
											<strong><s:text name="aula.alumno.reporte.curso"/> </strong>
										</label>
									</td>
									<td height="23" colspan="4" class="texto">
										<label>
											<%=reporteDetalle.getNombreCurso()%>
											<%=reporteDetalle.getFormacionSeccion()%>
										</label>
									</td>
									<td height="23" colspan="1" class="texto">
										<label>
											<strong><s:text name="aula.alumno.reporte.fecha_fin"/> </strong>
										</label>
									</td>
									<td height="23" colspan="4" class="derecha">
										<label>
											<%=reporteDetalle.getStringFecha2()%>
										</label>
									</td>
								</tr>
								<tr>
									<td height="23" colspan="1" class="texto1">
										<label>
											<strong><s:text name="aula.alumno.reporte.programa"/> </strong>
										</label>
									</td>
									<td height="23" colspan="4" class="texto1">
										<label>
											<%=reporteDetalle.getNombreEspecialidad()%>
										</label>
									</td>
									<td height="23" colspan="1" class="texto1">
										<label>
											<strong><s:text name="aula.alumno.reporte.ingresos"/> </strong>
										</label>
									</td>
									<td height="23" colspan="4">
										<label>
											<%=reporteDetalle.getCantidadIngreso()%>
										</label>
									</td>
								</tr>
								<tr>
									<td height="23" colspan="1" class="texto">
										<label>
											<strong><s:text name="aula.alumno.reporte.fecha_reporte"/></strong>
										</label>
									</td>
									<td height="23" colspan="4" class="texto">
										<label>
											<%=Formato.getStringDeDateCompleto(new GregorianCalendar())%>
										</label>
									</td>
									<td height="23" colspan="1" class="texto">
										&nbsp;
									</td>
									<td height="23" colspan="4" class="derecha">
										&nbsp;
									</td>
							</table>
						</td>
					</tr>

					
					<tr>
						<td height="5">
						</td>
					</tr>
					

					<%
						Collection recurso = null;
						Iterator i = null;
						if (true) {
						
							if (aulaVirtual.getCantidadTest() != 0) {
								
					%>
					<tr>
						<td height="23">
							<table width="100%" cellpadding="0" cellspacing="0"
								class="reporte_table" border="0" style="border-bottom: 0px none;">
								<tr>
									<td height="23" class="moduloFondo">
										<strong style="margin-left: 10px;"><s:text name="aula.alumno.reporte.evaluacion"/></strong>
									</td>
									<%
										for (int o = 1; o <= reporteDetalle.getCantidadUnidades(); o++) {
									%>
									<td width="39" align="center" class="moduloFondo">
										<strong><%=o%> </strong>
									</td>
									<%
										}
									%>
									<td width="50" align="center" class="moduloFondo">
										<strong><s:text name="aula.alumno.reporte.Pr_Pa"/></strong>
									</td>
									<td width="50" align="center" class="moduloFondo">
										<strong><s:text name="aula.alumno.reporte.Peso"/></strong>
									</td>
									<td width="60" align="center" class="notaParcial1">
										<strong><s:text name="aula.alumno.reporte.Pr_Ac"/></strong>
									</td>
								</tr>
								<tr>
									<td class="texto1" height="23">
										<label>
											<strong><s:text name="aula.alumno.reporte.nota_min"/></strong>
										</label>
									</td>
									<%
										recurso = reporteDetalle.getRecursoTest();
												i = recurso.iterator();
												for (int o = 1; o <= reporteDetalle.getCantidadUnidades(); o++) {
													if (i.hasNext()) {
														MatriculaGTest test = (MatriculaGTest) i.next();
									%>
									<th width="40" rowspan="4" valign="top">
										<table cellpadding="0" cellspacing="0" border="0" width="100%"
											height="100%">
											<tr>
												<td width="100%" height="23" class="unidades1">
													<%=Formato.formatoTextoNull(test
											.getNotaMinima())%>&nbsp;
												</td>
											</tr>
											<tr>
												<td height="23" class="unidades">
													<%=Formato.formatoTextoNull(test
											.getNotaMaxima())%>&nbsp;
												</td>
											</tr>
											<tr>
												<td height="23" class="unidades1">
													<%=Formato.formatoTextoNull(test
											.getNotaPromedio())%>&nbsp;
												</td>
											</tr>
											<tr>
												<td height="23" class="unidadesAbajo">
													<%=test.getVeces()%>&nbsp;
												</td>
											</tr>
										</table>
									</th>
									<%
										}
												}
									%>
									<td width="50" align="center" class="unidades1">
										&nbsp;
									</td>
									<td width="50" align="center" class="unidades1">
										&nbsp;
									</td>
									<td width="60" align="center" class="notaParcial">
										&nbsp;
									</td>
								</tr>

								<tr>
									<td class="texto" height="23">
										<label>
											<strong><s:text name="aula.alumno.reporte.nota_max"/></strong>
										</label>
									</td>
									<td width="50" align="center" class="unidades">
										&nbsp;
									</td>
									<td width="50" align="center" class="unidades">
										&nbsp;
									</td>
									<td width="60" align="center" class="notaParcial">
										&nbsp;
									</td>
								</tr>
								<tr>
									<td class="texto1" height="23">
										<label>
											<strong><s:text name="aula.alumno.reporte.promedio"/></strong>
										</label>
									</td>
									<td width="50" align="center" class="unidades1">
										<%=Formato.formatoTextoNull(reporteDetalle
									.getNotaTest().getNota())%>&nbsp;
									</td>
									<td width="50" align="center" class="unidades1">
										<%=reporteDetalle.getNotaTest().getPeso()%>%&nbsp;
									</td>
									<td width="60" align="center" class="notaParcial">
										<%=Formato.formatoTextoNull(reporteDetalle
									.getNotaTest().getPromedioParcial())%>&nbsp;
									</td>
								</tr>
								<tr>
									<td class="textoAbajo" height="23">
										<label>
											<strong><s:text name="aula.alumno.reporte.veces"/></strong>
										</label>
									</td>
									<td width="50" align="center" class="unidadesAbajo">
										&nbsp;
									</td>
									<td width="50" align="center" class="unidadesAbajo">
										&nbsp;
									</td>
									<td width="60" align="center" class="notaParcialAbajo">
										&nbsp;
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td height="5">
						</td>
					</tr>
						<%
							}if (aulaVirtual.getCantidadLaboratoriosCalificados() != 0) {
						%>
					<tr>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0"
								class="reporte_table" border="0" style="border-bottom: 0px none;">
								<tr>
									<td height="23" class="moduloFondo">
										<strong style="margin-left: 10px;"><s:text name="aula.alumno.reporte.laboratorio"/></strong>
									</td>
									<%
										for (int o = 1; o <= reporteDetalle.getCantidadUnidades(); o++) {
									%>
									<td width="39" align="center" class="moduloFondo">
										<strong><%=o%> </strong>
									</td>
									<%
										}
									%>
									<td width="50" align="center" class="moduloFondo">
										<strong><s:text name="aula.alumno.reporte.laboratorio.Pr._Pa"/></strong>
									</td>
									<td width="50" align="center" class="moduloFondo">
										<strong><s:text name="aula.alumno.reporte.laboratorio.peso"/></strong>
									</td>
									<td width="60" align="center" class="notaParcial1">
										<strong><s:text name="aula.alumno.reporte.laboratorio.Pr._Ac"/></strong>
									</td>
								</tr>
								<tr>
									<td class="texto1" height="23">
										<label>
											<strong><s:text name="aula.alumno.reporte.laboratorio.estado"/></strong>
										</label>
									</td>
									<%
										recurso = reporteDetalle.getRecursoLaboratorio();
												i = recurso.iterator();
												for (int o = 1; o <= reporteDetalle.getCantidadUnidades(); o++) {
													if (i.hasNext()) {
														MatriculaGLaboratorio lab = (MatriculaGLaboratorio) i
																.next();
									%>
									<td width="40" rowspan="2" valign="top">
										<table cellpadding="0" cellspacing="0" border="0" width="100%"
											height="100%">
											<tr>
												<th height="23" class="unidades1">
													<%=lab.getEstadoString()%>&nbsp;
												</th>
											</tr>
											<tr>
												<th height="23" class="unidadesAbajo">
													<%=Formato.formatoTextoNull(lab.getNota())%>&nbsp;
												</th>
											</tr>
										</table>
									</td>
									<%
										}
												}
									%>
									<th width="50" align="center" class="unidades1">
										&nbsp;
									</th>
									<th width="50" align="center" class="unidades1">
										&nbsp;
									</th>
									<th width="60" align="center" class="notaParcial">
										&nbsp;
									</th>
								</tr>
								<tr>
									<td class="textoAbajo" height="23">
										<label>
											<strong><s:text name="aula.alumno.reporte.laboratorio.nota"/></strong>
										</label>
									</td>
									<td width="50" align="center" class="unidadesAbajo">
										<%=Formato.formatoTextoNull(reporteDetalle
									.getNotaLaboratorio().getNota())%>&nbsp;
									</td>
									<td width="50" align="center" class="unidadesAbajo">
										<%=reporteDetalle.getNotaLaboratorio().getPeso()%>%&nbsp;
									</td>
									<td width="60" align="center" class="notaParcialAbajo">
										<%=Formato.formatoTextoNull(reporteDetalle
											.getNotaLaboratorio()
											.getPromedioParcial())%>&nbsp;
									</td>
								</tr>
								
							</table>
						</td>
					</tr>
					<tr>
						<td height="5">
						</td>
					</tr>
						<%
							}if (aulaVirtual.getCantidadDialogos() != 0) {
						%>
					<tr>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0"
								class="reporte_table" border="0">
								<tr>
									<td height="23" class="moduloFondo">
										<strong style="margin-left: 10px;"><s:text name="aula.alumno.reporte.dialogo"/></strong>
									</td>
									<%
										for (int o = 1; o <= reporteDetalle.getCantidadUnidades(); o++) {
									%>
									<td width="39" align="center" class="moduloFondo">
										<strong><%=o%></strong>
									</td>
									<%
										}
									%>
									<td width="160" align="center" class="moduloFondo" colspan="3" style="border-right: none;">
										&nbsp;

									</td>
								</tr>
								<tr>
									<td height="23" class="texto1">
										<label>
											<strong><s:text name="aula.alumno.reporte.cantidad"/></strong>
										</label>
									</td>
									<%
										recurso = reporteDetalle.getRecursoDialogo();
												i = recurso.iterator();
												for (int o = 1; o <= reporteDetalle.getCantidadUnidades(); o++) {
													if (i.hasNext()) {
														MatriculaGDialogo dialogo = (MatriculaGDialogo) i
																.next();
									%>
									<td width="40" rowspan="1" valign="middle">
										<table width="100%" height="100%" border="0" cellpadding="0"
											cellspacing="0">
											<tr>
												<th height="23" class="unidades1">
													<%=dialogo.getIntervencion()%>/<%=dialogo.getIntervencionTotal()%>&nbsp;
												</th>
											</tr>
										</table>
									</td>
									<%
										}
												}
									%>
									<td width="50" align="center">
										&nbsp;
									</td>
									<td width="50" align="center">
										&nbsp;
									</td>
									<td width="60" align="center">
										&nbsp;
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td height="5">
						</td>
					</tr>		
						<%
							}if (aulaVirtual.getCantidadTrabajos() != 0) {
						%>
					<tr>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0"
								class="reporte_table" border="0">
								<tr>
									<td height="23" class="moduloFondo">
										<strong style="margin-left: 10px;"><s:text name="aula.alumno.reporte.cantidad_trabajo"/></strong>
									</td>
									<td width="60" align="center" class="moduloFondo">
										<strong><s:text name="aula.alumno.reporte.trabajo.nota"/></strong>
									</td>
									<td width="60" align="center" class="moduloFondo">
										<strong><s:text name="aula.alumno.reporte.trabajo.estado"/></strong>
									</td>
									<td width="50" align="center" class="moduloFondo">
										<strong><s:text name="aula.alumno.reporte.trabajo.Pr._Pa."/></strong>
									</td>
									<td width="50" align="center" class="moduloFondo">
										<strong><s:text name="aula.alumno.reporte.trabajo.peso"/></strong>
									</td>
									<td width="60" align="center" class="notaParcial1">
										<strong><s:text name="aula.alumno.reporte.trabajo.Pr._Ac."/></strong>
									</td>
								</tr>
									
								<%
									recurso = reporteDetalle.getRecursoTrabajo();
											i = recurso.iterator();
											for (int o = 1; o <= reporteDetalle.getRecursoTrabajo().size(); o++) {
												if (i.hasNext()) {
													MatriculaGTrabajo trabajo = (MatriculaGTrabajo) i.next();
								%>
								<tr>
									<td height="23" class="<%if(o%2 == 1){out.print("texto1");}else{out.println("texto");} %>">
										<label>
											<strong><s:text name="aula.alumno.reporte.trabajo.Trabajo_N°"/><%=o%></strong>
										</label>
									</td>
									<td class="<%if(o%2 == 1){out.print("unidades1");}else{out.println("unidades");} %>">
										<strong><%=Formato.formatoTextoNull(trabajo.getNota())%></strong>&nbsp;
									</td>
									<td align="center" height="23" class="<%if(o%2 == 1){out.print("texto1");}else{out.println("texto");} %>">
										<strong><%=trabajo.getEstadoString()%></strong>&nbsp;
									</td>
									<%
										if(o==1){
									 %>
									<td width="50" align="center" class="unidades1" rowspan="<%=reporteDetalle.getRecursoTrabajo().size() %>">
										<%=Formato.formatoTextoNull(reporteDetalle.getNotaTrabajo().getNota())%>&nbsp;
									</td>
									<td width="50" align="center" class="unidades1" rowspan="<%=reporteDetalle.getRecursoTrabajo().size() %>">
										<%=reporteDetalle.getNotaTrabajo().getPeso()%>%&nbsp;
									</td>
									<td width="60" align="center" class="notaParcial" rowspan="<%=reporteDetalle.getRecursoTrabajo().size() %>">
										<%=Formato.formatoTextoNull(reporteDetalle.getNotaTrabajo().getPromedioParcial())%>&nbsp;
									</td>
									<%
										}
									 %>
								</tr>
								<%
										}
									}
								%>
							</table>
						</td>
					</tr>
					<tr>
						<td height="5">
						</td>
					</tr>
						<%
							}if (aulaVirtual.getCantidadGrupales() != 0) {
						%>
					<tr>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0"
								class="reporte_table" border="0">
								<tr>
									<td height="23" class="moduloFondo">
										<strong style="margin-left: 10px;"><s:text name="aula.alumno.reporte.trabajo_grupal"/></strong>
									</td>
									<td width="60" align="center" class="moduloFondo">
										<strong><s:text name="aula.alumno.reporte.trabajo_grupal.debates"/></strong>
									</td>
									<td width="60" align="center" class="moduloFondo">
										<strong><s:text name="aula.alumno.reporte.trabajo_grupal.nota"/></strong>
									</td>
									<td width="60" align="center" class="moduloFondo">
										<strong><s:text name="aula.alumno.reporte.trabajo_grupal.estado"/></strong>
									</td>
									<td width="50" align="center" class="moduloFondo">
										<strong><s:text name="aula.alumno.reporte.trabajo_grupal.Pr._Pa."/></strong>
									</td>
									<td width="50" align="center" class="moduloFondo">
										<strong><s:text name="aula.alumno.reporte.trabajo_grupal.peso"/></strong>
									</td>
									<td width="60" align="center" class="notaParcial1">
										<strong><s:text name="aula.alumno.reporte.trabajo_grupal.Pr._Ac."/></strong>
									</td>
								</tr>
									<%
										recurso = reporteDetalle.getRecursoTrabajoGrupal();
												i = recurso.iterator();
												for (int o = 1; o <= reporteDetalle.getRecursoTrabajoGrupal().size(); o++) {
													if (i.hasNext()) {
														MatriculaGTrabajoGrupal grupal = (MatriculaGTrabajoGrupal) i
																.next();
									%>
								<tr>
									<td height="23" class="<%if(o%2 == 1){out.print("texto1");}else{out.println("texto");} %>">
										<label>
											<strong><s:text name="aula.alumno.reporte.trabajo_grupal_N°"/><%=o%></strong>
										</label>
									</td>
									<td class="<%if(o%2 == 1){out.print("unidades1");}else{out.println("unidades");} %>">
										<strong><%=grupal.getIntervencion()%>/<%=grupal.getIntervencionTotal()%></strong>
									</td>
									<td class="<%if(o%2 == 1){out.print("unidades1");}else{out.println("unidades");} %>">
										<strong><%=Formato.formatoTextoNull(grupal.getNota())%></strong>&nbsp;
									</td>
									<td align="center" height="23" class="<%if(o%2 == 1){out.print("texto1");}else{out.println("texto");} %>">
										<strong><%=grupal.getEstadoString()%></strong>&nbsp;
									</td>
									<%
										if(o==1){
									 %>
									<td width="50" align="center" class="unidades1" rowspan="<%=reporteDetalle.getRecursoTrabajoGrupal().size() %>">
										<%=Formato.formatoTextoNull(reporteDetalle.getNotaGrupal().getNota())%>&nbsp;
									</td>
									<td width="50" align="center" class="unidades1" rowspan="<%=reporteDetalle.getRecursoTrabajoGrupal().size() %>">
										<%=reporteDetalle.getNotaGrupal().getPeso()%>%&nbsp;
									</td>
									<td width="60" align="center" class="notaParcial" rowspan="<%=reporteDetalle.getRecursoTrabajoGrupal().size() %>">
										<%=Formato.formatoTextoNull(reporteDetalle.getNotaGrupal().getPromedioParcial())%>&nbsp;
									</td>
									<%
										}
									 %>
								</tr>
								<%
									}
								}
								%>
							</table>
						</td>
					</tr>
					<tr>
						<td height="5">
						</td>
					</tr>
						<%
							}
						%>
						
					<tr>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0"
								class="reporte_table" border="0" style="border-right:none;">
								<tr>
									<td height="23" class="texto1">
										&nbsp;
									</td>
									<td align="right" width="90" class="texto" style="padding-right: 15px;">
										<label>
											<strong><s:text name="aula.alumno.reporte.nota_final"/></strong>
										</label>
									</td>
									<td align="center" width="110" class="texto1">
										<strong style="font-size: 15px;">
											<%
												/*String l = reporteDetalle.getNotaLaboratorio()
															.getPromedioParcial();
													String t = reporteDetalle.getNotaTrabajo().getPromedioParcial();
													String g = reporteDetalle.getNotaGrupal().getPromedioParcial();
													String s = reporteDetalle.getNotaTest().getPromedioParcial();
													float total = 0;
													if (l != null)
														total += Float.parseFloat(l);
													if (t != null)
														total += Float.parseFloat(t);
													if (g != null)
														total += Float.parseFloat(g);
													if (s != null)
														total += Float.parseFloat(s);
													*/
													out.print(reporteDetalle.getNotaFinal());
											%>
										</strong>
									</td>
									
									</tr><tr>
									<td height="23" class="texto1">
										&nbsp;
									</td>
									
									<td align="right" width="90" class="texto" style="padding-right: 15px;">
										<label>
											<strong><s:text name="aula.alumno.reporte.estado"/></strong>
										</label>
									</td>
									<td align="center" width="110" class="texto1">
										<strong style="font-size: 13px;">
											<%
												if(Float.parseFloat(reporteDetalle.getNotaFinal())>10.5){
													out.print("<strong><font color=\"#0000FF\">Apto</font></strong>");
												}else{
													out.print("<strong><font color=\"#FF0000\">No apto</font></strong>");
												}
											%>
										</strong>
									</td>
								</tr>
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
									<td height="23" class="moduloFondo" style="border-right:none;">
										<strong style="margin-left: 10px;"><s:text name="aula.alumno.reporte.calendario"/></strong>
									</td>
								</tr>
								<tr>
									<td align="center">
										
										<%
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
												<table border="0" style="border: 1px solid #cccccc;" cellspacing="0" cellpadding="2">
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
					<tr>
						<td height="5">
						</td>
					</tr>

					<%
						}
						
						if (false) {
					%>
					<tr>
						<td height="5">
						</td>
					</tr>
					<tr>
						<td height="23">
							<table cellpadding="0" cellspacing="0" border="0" width="100%"
								class="reporte_table">
								<tr>
									<td height="23" class="datos">
										<strong style="margin-left: 5px;">Reportes
											gr&aacute;ficos</strong>
									</td>
									<td class="datos" align="right">
										<input type="button" class="form_button"
											value="Mostrar/Ocultar"
											onclick="xChangeDisplay('tit_g'); xChangeDisplay('gra_g'); xChangeDisplay('spc_g');">
									</td>
								</tr>
								<tr id="tit_g" style="display: none;">
									<td height="23">
										<label>
											<strong>Ingresos al curso:</strong>
										</label>
									</td>
									<td>
										&nbsp;

									</td>

								</tr>
								<tr id="gra_g" style="display: none;">
									<td align="center" colspan="2">
										<img
											src="<%=Constante.RUTA_WEB_TEMPORAL%><%=request.getSession().getId()%>/<%=Constante.RUTA_REPORTE%>/reporte1.png"
											width="470" height="200">
									</td>
								</tr>
								<%
									if (false) {
								%>
								<tr>
									<td height="23" class="fon_color01">
										<label>
											<strong>Ingresos a los recursos</strong>
										</label>
									</td>
									<td class="fon_color01">
										&nbsp;

									</td>
								</tr>
								<tr>
									<td height="23" colspan="2" align="center" class="fon_color01">
										<img
											src="<%=Constante.RUTA_WEB_TEMPORAL%><%=request.getSession().getId()%>/<%=Constante.RUTA_REPORTE%>/reporte2.png"
											width="470" height="200">
									</td>
								</tr>
								<%
									}
								%>
								<tr id="spc_g" style="display: none;">
									<td height="23" colspan="2" class="fon_color01">
										&nbsp;

									</td>
								</tr>
							</table>
						</td>

					</tr>

					<%
						}
						if (null != tipo) {
					%>
					<tr>
						<td height="10">
							&nbsp;

						</td>
					</tr>
					<tr>
						<td height="23" align="center">
							<input type="button" value="Regresar"
								onClick="javascript:history.back();" class="form_button">
						</td>
					</tr>

					<%
						}
					%>
					<tr>
						<td height="10">
							&nbsp;

						</td>
					</tr>

				</table>
			</div>
			<div id="pie">
				<%@include file="../../comun/pie.jsp"%>
			</div>
		</div>
	</body>
</html>
