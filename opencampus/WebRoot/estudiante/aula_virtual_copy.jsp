<%
	//response.setContentType("text/plain;charset=ISO-8859-1"); //Para no tener problemas con ñs y tildes
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="../../error_action.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  page import="edu.opencampus.lms.util.Constante"%>
<%@  page import="edu.opencampus.lms.modelo.Usuario"%>
<%@  page import="edu.opencampus.lms.modelo.ficha.Unidad"%>
<%@  page import="edu.opencampus.lms.modelo.AulaVirtual"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%
			Usuario usuario = (Usuario) request.getSession().getAttribute(
			Constante.USUARIO_ACTUAL);
	AulaVirtual aulaVirtual = (AulaVirtual) session
			.getAttribute(Constante.AULA_ACTUAL);
	//System.out.println(aulaVirtual);
	String n = (String) session.getAttribute("n");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title><s:text name="titulo.campus.virtual" /></title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src='<%=request.getContextPath()%>/js/jComponente.js'></script>
		<script language="JavaScript"
			src="<%=request.getContextPath()%>/js/menu/menu.js"></script>
		<link href="<%=request.getContextPath()%>/js/menu/menu.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/js/aula_virtual.js'></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>
		<script language="javascript" type="text/javascript"
			src='<%=request.getContextPath()%>/js/vitrina.js'></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/tooltip/tooltip.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/aviso.js"></script>
	</head>
	<%@include file="../comun/capas/reloj.jsp"%>
	<body onLoad="mostrarReloj();mensajes('<%out.print(n);%>');expand(); "  onresize="resize()"  onscroll="scrolling()" onunload="cerrarAula()"
		onbeforeunload="if(this.exit===true) return 'Si cierra la ventana será calificado con el puntaje obtenido hasta el momento.';" >
		<div id="contenedor">
			<%@include file="/comun/bienvenida_aula.jsp"%>
			<div id="bienvenida">
				<table cellspacing="3" border="0">
					<tr>						
						<td width="10" class="menu_prin01" align="center">&nbsp;
							
						</td>
						<td width="65" align="center">
							<span class="menu"
								onClick="javascript:abrirPlanDocente('<%=request.getContextPath()%>/aulavirtual/SilaboDeCurso.action','PlanDocente','530','350');">
								<s:text name="aula.alumno.menu.plan_docente"/> </span>
						</td>
						<td width="10" class="menu_prin01" align="center">
							|
						</td>
						<td width="47" align="center">
							<span class="menu"
								onclick="javascript:abrirReporte('<%=request.getContextPath()%>/aulavirtual/ReporteDetalle.action','MiClase','560','350');">
								<s:text name="aula.alumno.menu.reporte"/> </span>
						</td>
						<td width="10" class="menu_prin01" align="center">
							|
						</td>
						<td width="52" align="center">
							<span class="menu"
								onclick="javascript:abrirMiClase('<%=request.getContextPath()%>/aulavirtual/MiClase.action','MiClase','560','350');">
								<s:text name="aula.alumno.menu.mi_clase"/> </span>
						</td>
						<td width="5" class="menu_prin01" align="center">
							|
						</td>
						<td width="52" align="center">
							<span class="menu"
								onclick="javascript:abrirVitrina('<%=request.getContextPath()%>/aulavirtual/Mensajes.action','Mensajes','560','350');">
								<s:text name="aula.alumno.menu.avisos"/></span>
						</td>
						<td width="10" class="menu_prin01" align="center">
							|
						</td>
						<td width="45" align="center">
							<span class="menu"
								onclick="javascript:abrirLectura('<%=request.getContextPath()%>/aulavirtual/Recursos.action','Recursos','560','350');">
								<s:text name="aula.alumno.menu.lecturas"/> </span>
						</td>
					</tr>
				</table>
			</div>
			<div id="cuerpo">				
				<div id="principal">
					<%
							if (0 < aulaVirtual.getUnidades().size()) {
							int colspan = 13;
							colspan = (aulaVirtual.getCantidadLaboratorios() == 0) ? colspan - 2
							: colspan;
							colspan = (aulaVirtual.getCantidadDialogos() == 0) ? colspan - 2
							: colspan;
							colspan = (aulaVirtual.getCantidadTrabajos() == 0) ? colspan - 2
							: colspan;
							colspan = (aulaVirtual.getCantidadGrupales() == 0) ? colspan - 2
							: colspan;
							colspan = (aulaVirtual.getCantidadTest() == 0) ? colspan - 1
							: colspan;
					%>
					<table width="803" border="0" cellpadding="0" cellspacing="0"
						bgcolor="#E5EFF8" class="tabla01">
						<tr class="fon_tit_curso">
							<th height="20" colspan="<%=colspan%>" style="font-size: 12px;font-weight: bold;" 
								align="center">
								&nbsp;&nbsp;<s:text name="aula.alumno.unidades"/></th>
					  </tr>
						<tr bgcolor="#FfFfFf">
							<th width="20" class="moduloAbajo">&nbsp;</th>
							<th width="43" class="moduloAbajo">
								<s:text name="aula.alumno.texto"/>
							</th>
							<th height="18" colspan="2" class="moduloAbajo">
								<s:text name="aula.alumno.repaso"/>
							</th>
							<%
							if (aulaVirtual.getCantidadLaboratorios() > 0) {
							%>
							<th colspan="2" class="moduloAbajo">
								<s:text name="aula.alumno.lab"/>
							</th>
							<%
							}
							%>
							<%
							if (aulaVirtual.getCantidadDialogos() > 0) {
							%>
							<th colspan="2" class="moduloAbajo" width="50">
								<s:text name="aula.alumno.dialogo"/>
							</th>
							<%
							}
							%>
							<%
							if (aulaVirtual.getCantidadTrabajos() > 0) {
							%>
							<th colspan="2" class="moduloAbajo" width="50">
								<s:text name="aula.alumno.trabajo"/>
							</th>
							<%
							}
							%>

							<%
							if (aulaVirtual.getCantidadGrupales() > 0) {
							%>
							<th colspan="2" class="moduloAbajo" width="50">
								<s:text name="aula.alumno.grupal"/>
							</th>
							<%
							}
							%>
							<%
							if (aulaVirtual.getCantidadTest() > 0) {
							%>
							<th class="moduloAbajo1" align="center" width="40">
								<s:text name="aula.alumno.evaluacion"/>
							</th>
							<%
							}
							%>
						</tr>
						<%
								int indiceUnidad = 0;
								for (Unidad unidadTemp : aulaVirtual.getUnidades()) {
									indiceUnidad++;
									int estadoUnidad = unidadTemp.getEstado();
						%>
						<tr
							<%if(indiceUnidad%2==0){ out.print("class=\"blanco\"");
	  }else{
		out.print("class=\"class=\"tabla01_fila1\"");}
	  %>
							<%if(estadoUnidad==0) { %> style="visibility: hidden" <% } %>>

							<td width="20" height="24" align="center" class="texto1">
								<%=indiceUnidad%>
							</td>


							<td width="43" height="24" align="center" class="texto1">
								<!-- Texto  -->
								<%
											if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso().get(
											Constante.RECURSO_ID_TEXTO).getEstado()) {
										if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
												.get(Constante.RECURSO_ID_TEXTO)
												.getDeshabilitadoEstudiante()) {
								%>
								<a href="javascript:void(0);"
									onclick="javascript:procesarTexto('<%=request.getContextPath()%>','<%=unidadTemp.getIdUnidad()%>', '<%=indiceUnidad%>','<%=Constante.RUTA_WEB_TEMPORAL%><%=session.getId()%>/<%=unidadTemp.getIdUnidad()%>/<%=Constante.TEXTO%><%=indiceUnidad%><%=Constante.EXTENSION_PDF%>');"
									class="link_curso"> <img
										src="<%=request.getContextPath()%>/img/icon_libro.gif"
										border="0" /> </a>
								<%
								} else {
								%>
								<img
									src="<%=request.getContextPath()%>/img/icon_libro_inactivo.gif"
									border="0" />
								<%
											}
											}
								%>
							</td>
							<td width="450" style="padding-left: 2px;">
								<!-- Repaso enlace -->
								<%
											if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso().get(
											Constante.RECURSO_ID_REPASO_ENLACE).getEstado()) {
										if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
												.get(Constante.RECURSO_ID_REPASO_ENLACE)
												.getDeshabilitadoEstudiante()) {
								%>
								<a href="javascript:void(0);"
									onclick="javascript:procesarRepaso('<%=request.getContextPath()%>','<%=unidadTemp.getIdUnidad()%>', '<%=Constante.RUTA_WEB_TEMPORAL%><%=session.getId()%>/<%=unidadTemp.getIdUnidad()%>/','<%=unidadTemp.getAncho()%>','<%=unidadTemp.getAlto()%>');"
									class="link_curso"><%=unidadTemp.getNombreCompleto()%> </a>
								<%
								} else {
								%>
								<%=unidadTemp.getNombreCompleto()%>
								<%
											}
											}
								%>
							</td>
							<td align="right" width="42" class="texto1"
								style="padding-right: 5px;">
								<!-- Repaso -->
								<%
											if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso().get(
											Constante.RECURSO_ID_REPASO).getEstado()) {
										if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
												.get(Constante.RECURSO_ID_REPASO)
												.getDeshabilitadoEstudiante()) {
								%>
								<a
									href="<%=request.getContextPath()%>/aulavirtual/CopiarRepasoOff.action?idunidad=<%=unidadTemp.getIdUnidad()%>&indice=<%=indiceUnidad%>">
									<img border="0"
										src="<%=request.getContextPath()%>/img/icon_download.gif" />
								</a>
								<%
								} else {
								%>
								<img border="0"
									src="<%=request.getContextPath()%>/img/icon_download_inactivo.gif" />
								<%
											}
											}
								%>
							</td>

							<%
							if (aulaVirtual.getCantidadLaboratorios() > 0) {
							%>
							<td align="left" width="20" style="padding-left: 2px;">
								<!--  Laboratorio  -->
								<%
												if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
												.get(Constante.RECURSO_ID_LABORATORIO)
												.getEstado()) {
											if (Constante.ESTADO_ACTIVO == unidadTemp
											.getRecurso().get(
													Constante.RECURSO_ID_LABORATORIO)
											.getDeshabilitadoEstudiante()) {
												if (Constante.ESTADO_ACTIVO == unidadTemp
												.getRecurso()
												.get(Constante.RECURSO_ID_LABORATORIO)
												.getCalifica()) {
								%>
								<a href="javascript:void(0);"
									onClick="javascript:abrirLaboratorio('<%=request.getContextPath()%>/aulavirtual/laboratorio/VerMensajesDeEstudiante.action?idUnidad=<%=unidadTemp.getIdUnidad()%>')">
									<img
										src="<%=request.getContextPath()%>/img/icon_lab_informe.gif"
										border="0" /> </a>
								<%
								} else {
								%>
								<a href="javascript:void(0);"
									onClick="javascript:procesarLaboratorio('<%=request.getContextPath()%>','<%=unidadTemp.getIdUnidad()%>', '<%=indiceUnidad%>','<%=Constante.RUTA_WEB_TEMPORAL%><%=session.getId()%>/<%=unidadTemp.getIdUnidad()%>/<%=Constante.LAB%><%=indiceUnidad%><%=Constante.EXTENSION_PDF%>');">
									<img src="<%=request.getContextPath()%>/img/icon_lab.gif"
										border="0" /> </a>
								<%
								}
								%>
								<%
												} else {
												if (Constante.ESTADO_ACTIVO == unidadTemp
												.getRecurso()
												.get(Constante.RECURSO_ID_LABORATORIO)
												.getCalifica()) {
								%>
								<img
									src="<%=request.getContextPath()%>/img/icon_lab_informe_inactivo.gif"
									border="0" />
								<%
								} else {
								%>
								<img
									src="<%=request.getContextPath()%>/img/icon_lab_inactivo.gif"
									border="0" />
								<%
								}
								%>
								<%
										}
										}
								%>
							</td>

							<td align="left" width="18" class="texto1">
								<%
												if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
												.get(Constante.RECURSO_ID_LABORATORIO)
												.getEstado()) {
								%>
								<%
								if (unidadTemp.getCantidadLaboratorio() > 0) {
								%>
								<img src="<%=request.getContextPath()%>/img/flag.gif" width="8"
									height="11" />
								<%
										}
										}
								%>
							</td>
							<%
							}
							%>


							<%
							if (aulaVirtual.getCantidadDialogos() > 0) {
							%>
							<td align="left" width="20" style="padding-left: 2px;">
								<!--  Dialogo  -->
								<%
												if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
												.get(Constante.RECURSO_ID_DIALOGO).getEstado()) {
											if (Constante.ESTADO_ACTIVO == unidadTemp
											.getRecurso().get(
													Constante.RECURSO_ID_DIALOGO)
											.getDeshabilitadoEstudiante()) {
								%>
								<a href="javascript:void(0);"
									onclick="javascript:abrirDialogo('<%=request.getContextPath()%>/aulavirtual/dialogo/Cargar.action?idUnidad=<%=unidadTemp.getIdUnidad()%>');">
									<img src="<%=request.getContextPath()%>/img/icon_dialog.gif"
										border="0" /> <%
 } else {
 %> <img
										src="<%=request.getContextPath()%>/img/icon_dialog_inactivo.gif"
										border="0" /> <%
 		}
 		}
 %>
								</a>
							</td>

							<td align="left" width="18" class="texto1">

								<%
												if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
												.get(Constante.RECURSO_ID_DIALOGO).getEstado()) {
								%>
								<%
								if (unidadTemp.getCantidadDialogo() > 0) {
								%>
								<img src="<%=request.getContextPath()%>/img/flag.gif" width="8"
									height="11" />
								<%
										}
										}
								%>
							</td>
							<%
							}
							%>

							<%
							if (aulaVirtual.getCantidadTrabajos() > 0) {
							%>
							<td align="left" width="20" style="padding-left: 2px;">
								<!--  Trabajo  -->
								<%
												if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
												.get(Constante.RECURSO_ID_TRABAJO).getEstado()) {
											if (Constante.ESTADO_ACTIVO == unidadTemp
											.getRecurso().get(
													Constante.RECURSO_ID_TRABAJO)
											.getDeshabilitadoEstudiante()) {
								%>
								<a href="javascript:void(0);"
									onClick="javascript:abrirTrabajo('<%=request.getContextPath()%>/aulavirtual/tindividual/VerMensajesDeEstudiante.action?idUnidad=<%=unidadTemp.getIdUnidad()%>')">
									<img src="<%=request.getContextPath()%>/img/icon_trab.gif"
										border="0" /> </a>
								<%
								} else {
								%>
								<img
									src="<%=request.getContextPath()%>/img/icon_trab_inactivo.gif"
									border="0" />
								<%
										}
										}
								%>
							</td>

							<td align="left" width="18" class="texto1">
								<%
												if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
												.get(Constante.RECURSO_ID_TRABAJO).getEstado()) {
								%>
								<%
								if (unidadTemp.getCantidadTIndividual() > 0) {
								%>
								<img src="<%=request.getContextPath()%>/img/flag.gif" width="8"
									height="11" />
								<%
										}
										}
								%>
							</td>
							<%
							}
							%>

							<%
							if (aulaVirtual.getCantidadGrupales() > 0) {
							%>
							<td align="left" width="20" style="padding-left: 2px;">
								<!--  Actividad Grupal  -->
								<%
												if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
												.get(Constante.RECURSO_ID_ACTIVIDAD_GRUPAL)
												.getEstado()) {
											if (Constante.ESTADO_ACTIVO == unidadTemp
											.getRecurso()
											.get(Constante.RECURSO_ID_ACTIVIDAD_GRUPAL)
											.getDeshabilitadoEstudiante()) {
								%>
								<a href="javascript:void(0);"
									onClick="javascript:abrirActividadGrupal('<%=request.getContextPath()%>/aulavirtual/tgrupal/VerTrabajoGrupal.action?idUnidad=<%=unidadTemp.getIdUnidad()%>')">
									<img src="<%=request.getContextPath()%>/img/icon_trab_grup.gif"
										border="0" /> </a>
								<%
								} else {
								%>
								<img
									src="<%=request.getContextPath()%>/img/icon_trab_grup_inactivo.gif"
									border="0" />
								<%
										}
										}
								%>
							</td>
							<td align="left" width="19" class="texto1">
								<%
												if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
												.get(Constante.RECURSO_ID_ACTIVIDAD_GRUPAL)
												.getEstado()) {
								%>
								<%
								if (unidadTemp.getCantidadTGrupal() > 0) {
								%>
								<img src="<%=request.getContextPath()%>/img/flag.gif" width="8"
									height="11" />
								<%
										}
										}
								%>
							</td>
							<%
							}
							%>

							<%
							if (aulaVirtual.getCantidadTest() > 0) {
							%>
							<td align="center" width="20" style="padding-left: 2px;">
								<!--  Test  -->
								<%
												if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
												.get(Constante.RECURSO_ID_TEST).getEstado()) {
											if (Constante.ESTADO_ACTIVO == unidadTemp
											.getRecurso()
											.get(Constante.RECURSO_ID_TEST)
											.getDeshabilitadoEstudiante()) {
								%>
								<a href="javascript:void(0);"
									onClick="javascript:abrirTestEjecucion('<%=request.getContextPath()%>/aulavirtual/test/Cargar.action?idUnidad=<%=unidadTemp.getIdUnidad()%>')">
									<img src="<%=request.getContextPath()%>/img/icon_test.gif"
										border="0" /> </a>
								<%
								} else {
								%>
								<img
									src="<%=request.getContextPath()%>/img/icon_test_inactivo.gif"
									border="0" />
								<%
										}
										}
								%>
							</td>
							<%
							}
							%>
						</tr>
						<%
						}
						%>
					</table>
					<%
					}
					%>
				</div>

			</div>
			
			<div id="pie">
				<%@include file="../comun/pie.jsp"%>
			</div>
			
			<%@include file="../comun/encuesta.jsp"%>
			
		</div>
		<div>
			<%@include file="../comun/div_publicacion.jsp"%>
		</div>
		<div id="blocker" style="display:none; position: absolute; top: 0px; left: 0px; background-color:#ccc; width:0px;height:0px; 
				filter:alpha(opacity=50); -moz-opacity:.50; opacity:.50;"></div>
		<script type="text/javascript">
			resizeHeight();
		</script>
	</body>
</html>
