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
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%@  page import="edu.tecsup.lms.modelo.Usuario"%>
<%@  page import="edu.tecsup.lms.modelo.ficha.Unidad"%>
<%@  page import="edu.tecsup.lms.modelo.AulaVirtual"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%
	String n = (String) session.getAttribute("n");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title><s:text name="titulo.campus.virtual" />
		</title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src='<%=request.getContextPath()%>/js/jComponente.js'></script>	
		<script language="JavaScript"
			src="<%=request.getContextPath()%>/js/menu/menu.js"></script>
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/js/aula_virtual.js'></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/tooltip/tooltip.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/aviso.js"></script>
	</head>
	<body
		onLoad="expand();mensajes('<%out.print(n);%>');" onunload="cerrarAula()"
		onbeforeunload="if(this.exit===true) return 'Si cierra la ventana será calificado con el puntaje obtenido hasta el momento.';" >
		
		<c:out value="${usuario_actual}"></c:out><div id="contenedor" style="z-index: 1;width: 994px;">
			
			<%@include file="../comun/bienvenida_aula.jsp"%>
			<div id="bienvenida" style="width: 992px;">
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
								onclick="javascript:abrirReporte('<%=request.getContextPath()%>/aulavirtual/Reporte.action','Reporte','640','350');">
								<s:text name="aula.alumno.menu.reporte"/> </span>
						</td>
						<td width="10" class="menu_prin01" align="center">
							|
						</td>
						<td width="87" align="center">
							<span class="menu"
								onclick="javascript:abrirReporte('<%=request.getContextPath()%>/aulavirtual/ReporteNotas.action','Reporte de Notas','640','350');">
								<s:text name="aula.alumno.menu.reporte_notas"/> </span>
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
						<td width="10" class="menu_prin01" align="center">
							|
						</td>
						<td width="45" align="center">
							<span class="menu"
								onclick="javascript:abrirInforme('<%=request.getContextPath()%>/aulavirtual/Informe.action');">
								<s:text name="aula.alumno.menu.informe"/> </span>
						</td>
					</tr>
				</table>
			</div>
			<div id="cuerpo" style="width: 992px;">				
				<div id="principal">
					<%
						if (0 < aulaVirtual.getUnidades().size()) {
							int colspan = 14;
							colspan = (aulaVirtual.getCantidadLaboratorios() == 0) ? colspan - 2
									: colspan;
							colspan = (aulaVirtual.getCantidadDialogos() == 0) ? colspan - 2
									: colspan;
							colspan = (aulaVirtual.getCantidadTrabajos() == 0) ? colspan - 2
									: colspan;
							colspan = (aulaVirtual.getCantidadGrupales() == 0) ? colspan - 2
									: colspan;
							colspan = (aulaVirtual.getCantidadTest() == 0) ? colspan - 2
									: colspan;
					%>
					<table width="803" border="0" cellpadding="0" cellspacing="0"
						bgcolor="#E5EFF8" class="tabla01">
<tr class="fon_tit_curso">
							<th height="20" colspan="14" style="font-size: 12px;font-weight: bold;" 
								align="center">
								&nbsp;&nbsp;<s:text name="aula.alumno.unidades"/></th>
					  </tr>
						<tr bgcolor="#FfFfFf">
							<th width="17" class="moduloAbajo">&nbsp;							</th>
							<th class="moduloAbajo" width="43">
								<s:text name="aula.alumno.texto"/>						</th>
							<th height="18" colspan="2" bgcolor="#FfFfFf" class="moduloAbajo">
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
							<th colspan="2" class="moduloAbajo">
								<s:text name="aula.alumno.dialogo"/>
							</th>
							<%
								}
							%>
							<%
								if (aulaVirtual.getCantidadTrabajos() > 0) {
							%>
							<th colspan="2" class="moduloAbajo">
								<s:text name="aula.alumno.trabajo"/>
							</th>
							<%
								}
							%>
							<%
								if (aulaVirtual.getCantidadGrupales() > 0) {
							%>
							<th colspan="2" class="moduloAbajo">
								<s:text name="aula.alumno.grupal"/>
							</th>
							<%
								}
							%>
							<%
								if (aulaVirtual.getCantidadTest() > 0) {
							%>

							<th colspan="2" class="moduloAbajo1" align="center">
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
		out.print("class=\"tabla01_fila1\"");}
	  %>
							<%if(estadoUnidad==0) { %> style="visibility: hidden" <% } %>>

							<td align="center"  class="texto1">
								<%=indiceUnidad%>
							</td>


							<td align="center" class="texto1"
								style="padding-left: 2px;">
								<!-- Texto  -->
								<%
									if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso().get(
													Constante.RECURSO_ID_TEXTO).getEstado()) {
												if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
														.get(Constante.RECURSO_ID_TEXTO)
														.getDeshabilitadoDocente()) {
								%>
								<a href="javascript:void(0);"
									onclick="javascript:procesarTexto('<%=request.getContextPath()%>','<%=unidadTemp.getIdUnidad()%>', '<%=indiceUnidad%>','<%=Constante.RUTA_WEB_TEMPORAL%><%=session.getId()%>/<%=unidadTemp.getIdUnidad()%>/<%=Constante.TEXTO%><%=indiceUnidad%><%=Constante.EXTENSION_PDF%>');"
									class="link_curso"> <img
										src="<%=request.getContextPath()%>/img/icon_libro.gif"
										border="0" />
								</a>
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
														.getDeshabilitadoDocente()) {
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



			  <td width="48" align="right" class="texto1"
								style="padding-right: 5px;">
								<!-- Repaso -->
                                <%
									if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso().get(
													Constante.RECURSO_ID_REPASO).getEstado()) {
												if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
														.get(Constante.RECURSO_ID_REPASO)
														.getDeshabilitadoDocente()) {
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
							<td width="38" align="left" style="padding-left: 2px;">
								<!--  Laboratorio  -->
						    <%
									if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
														.get(Constante.RECURSO_ID_LABORATORIO)
														.getEstado()) {
													if (Constante.ESTADO_ACTIVO == unidadTemp
															.getRecurso().get(
																	Constante.RECURSO_ID_LABORATORIO)
															.getDeshabilitadoDocente()) {
														if (Constante.ESTADO_ACTIVO == unidadTemp
																.getRecurso()
																.get(Constante.RECURSO_ID_LABORATORIO)
																.getCalifica()) {
								%>
								<a href="javascript:void(0);"
									onClick="javascript:abrirLaboratorio('<%=request.getContextPath()%>/aulavirtual/laboratorio/Cargar.action?idUnidad=<%=unidadTemp.getIdUnidad()%>')">
									<img
										src="<%=request.getContextPath()%>/img/icon_lab_informe.gif"
										border="0" />								</a>
						    <%
									} else {
								%>
								<a href="javascript:void(0);"
									onClick="javascript:procesarLaboratorio('<%=request.getContextPath()%>','<%=unidadTemp.getIdUnidad()%>', '<%=indiceUnidad%>','<%=Constante.RUTA_WEB_TEMPORAL%><%=session.getId()%>/<%=unidadTemp.getIdUnidad()%>/<%=Constante.LAB%><%=indiceUnidad%><%=Constante.EXTENSION_PDF%>');">
									<img src="<%=request.getContextPath()%>/img/icon_lab.gif"
										border="0" />
								</a>
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
			  <td width="17" align="left" class="texto1">
								<%
									if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
														.get(Constante.RECURSO_ID_LABORATORIO)
														.getEstado()) {
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
							<td width="38" align="left" style="padding-left: 2px;">
								<!--  Dialogo  -->
					      <%
									if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
														.get(Constante.RECURSO_ID_DIALOGO).getEstado()) {
													if (Constante.ESTADO_ACTIVO == unidadTemp
															.getRecurso().get(
																	Constante.RECURSO_ID_DIALOGO)
															.getDeshabilitadoDocente()) {
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
								
						  </td>
					  <td width="20" align="left" class="texto1">
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

							<td width="38" align="left" style="padding-left: 2px;">
								<!--  Trabajo  -->
						    <%
									if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
														.get(Constante.RECURSO_ID_TRABAJO).getEstado()) {
													if (Constante.ESTADO_ACTIVO == unidadTemp
															.getRecurso().get(
																	Constante.RECURSO_ID_TRABAJO)
															.getDeshabilitadoDocente()) {
								%>
								<a href="javascript:void(0);"
									onClick="javascript:abrirTrabajo('<%=request.getContextPath()%>/aulavirtual/tindividual/Cargar.action?idUnidad=<%=unidadTemp.getIdUnidad()%>')">
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

			  <td width="17" align="left" class="texto1">
<%
									if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso().get(Constante.RECURSO_ID_TRABAJO).getEstado()) {
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

							<td width="38" align="left" style="padding-left: 2px;">
								<!--  Actividad Grupal  -->
						    <%
									if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
														.get(Constante.RECURSO_ID_ACTIVIDAD_GRUPAL)
														.getEstado()) {
													if (Constante.ESTADO_ACTIVO == unidadTemp
															.getRecurso()
															.get(Constante.RECURSO_ID_ACTIVIDAD_GRUPAL)
															.getDeshabilitadoDocente()) {
								%>
								<a href="javascript:void(0);"
									onClick="javascript:abrirActividadGrupal('<%=request.getContextPath()%>/aulavirtual/tgrupal/Cargar.action?idUnidad=<%=unidadTemp.getIdUnidad()%>')">
									<img src="<%=request.getContextPath()%>/img/icon_trab_grup.gif"
										border="0" />								</a>
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


			  <td width="17" align="left" class="texto1">
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
							<td width="20" align="center" >

								<!--  Test  -->
						    <%
									if (Constante.ESTADO_ACTIVO == unidadTemp.getRecurso()
														.get(Constante.RECURSO_ID_TEST).getEstado()) {
													if (Constante.ESTADO_ACTIVO == unidadTemp
															.getRecurso()
															.get(Constante.RECURSO_ID_TEST)
															.getDeshabilitadoDocente()) {
								%>
								<input type="checkbox"
									onClick="docente_cambiar_estado_estudiante('<%=unidadTemp.getIdUnidad()%>','<%=request.getContextPath()%>');"
									<%=(Constante.ESTADO_ACTIVO == unidadTemp
														.getRecurso()
														.get(
																Constante.RECURSO_ID_TEST)
														.getDeshabilitadoEstudiante()) ? "checked"
												: ""%> />

						  </td>
					  <td width="50" ><a href="javascript:void(0);"
									onClick="javascript:abrirTest('<%=request.getContextPath()%>/aulavirtual/test/Listar.action?idUnidad=<%=unidadTemp.getIdUnidad()%>')">
									<img src="<%=request.getContextPath()%>/img/icon_test.gif"
										border="0" /> </a>
								(<%=unidadTemp.getCantidadTest()%>)
									

								<%
									} else {
								%>
								<img
									src="<%=request.getContextPath()%>/img/icon_test_inactivo.gif"
									border="0" />
								<%
									}
												} else {
													out.println("</td><td>");
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
			<div style="z-index: 1">
				<%@include file="../comun/div_publicacion.jsp"%>
			</div>
			<div id="pie">
				<%@include file="../comun/pie.jsp"%>
			</div>
			<script type="text/javascript">
				resizeHeight();
			</script>
		</div>
	</body>
</html>
