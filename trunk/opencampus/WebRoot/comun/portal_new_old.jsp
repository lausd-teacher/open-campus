<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@ taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<%@  page import="edu.tecsup.lms.modelo.Usuario"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%@  page import="java.util.Collection"%>
<%@  page import="java.util.ArrayList"%>
<%@ page import="edu.tecsup.lms.modelo.portal.Servicio"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}' />
<%
	Usuario usuario = (Usuario) request.getSession().getAttribute(
			Constante.USUARIO_ACTUAL);
	Collection<edu.tecsup.lms.modelo.ficha.curso.Ficha> col_cursos = (Collection<edu.tecsup.lms.modelo.ficha.curso.Ficha>)request.getAttribute("cursos");
				if(null==col_cursos){
					col_cursos = new ArrayList<edu.tecsup.lms.modelo.ficha.curso.Ficha>();
				}
%>
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title><s:text name="titulo.campus.virtual" /></title>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jPrototype.js"
			type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/scriptaculous/scriptaculous.js?load=effects,builder,dragdrop"></script>
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/InnerDiv.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/noticia.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/portal.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/tooltip/tooltip.js"></script>
		<script type='text/javascript'
			src='<%=request.getContextPath()%>/dwr/engine.js'> </script>
		<script type='text/javascript'
			src='<%=request.getContextPath()%>/dwr/util.js'> </script>
		<script type='text/javascript'
			src='<%=request.getContextPath()%>/dwr/interface/UsuariosConectados.js'> </script>
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/js/chat.js'></script>
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/js/agenda.js'></script>
		<link rel="stylesheet" type="text/css"
			href="<c:out value='${contextPath}'/>/js/jscalendar/campus.css" />
		<script type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jscalendar/calendar.js"></script>
		<script type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jscalendar/calendar-es.js"></script>
		<script type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jscalendar/calendar-setup.js"></script>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/estilos/chat.css"
			rel="stylesheet" type="text/css">
	</head>
	<%@include file="/comun/capas/reloj.jsp"%>
	<script language="javascript">
		var TIPO_ABRIR_BROWSER =<%=Constante.PORTAL_SERVICIO_BROWSER%>;
		var TIPO_ABRIR_POPUP =<%=Constante.PORTAL_SERVICIO_POPUP%>;
		var TIPO_URL_SIMPLE =<%=Constante.PORTAL_SERVICIO_URL_SIMPLE%>;
		var TIPO_URL_COMPLETA =<%=Constante.PORTAL_SERVICIO_URL_COMPLETA%>;
	</script>
	<body onLoad="mostrarReloj();" onresize="resize();">
		<div id="contenedor">
			<div id="menu_principal">
				<%@include file="bienvenida.jsp"%>
			</div>
			<div id="banner">
			</div>
			<div id="bienvenida">				
				<ct:MenuPortada />
			</div>
			<div id="cuerpo" style="z-index: 0;">
				<div id="principal" style="z-index: -1;">
					<div id="principal_col_0"
						style="width: 242px; float: left; min-height: 50px; padding-bottom: 10px;">
					</div>
					<div id="principal_col_1"
						style="width: 242px; float: left; min-height: 50px; padding-bottom: 10px;">
					</div>
					<div id="principal_col_2"
						style="width: 242px; float: left; min-height: 50px; padding-bottom: 10px;">
					</div>
					<div id="principal_col_3"
						style="width: 242px; float: left; min-height: 50px; padding-bottom: 10px;">
					</div>
				</div>
			</div>
			<div id="pie">
				<%@include file="pie.jsp"%>
			</div>
		</div>		
		<div id="<%=Constante.SERVICIO_AGENDA%>"
			style="vertical-align: top; width: 241px; padding-bottom: 5px; float: left; padding-top: 5px; display: none;">
			<table width="230" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="23" valign="bottom" class="lin_azul ">
						<table cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td valign="bottom">
									<table border="0" align="left" cellpadding="0" cellspacing="0"
										style="cursor: move;"
										id="tabla_<%=Constante.SERVICIO_AGENDA%>">
										<tr>
											<td width="3" height="23" valign="top">
												<img
													src="<%=request.getContextPath()%>/img/fon_tit_curso_01.jpg"
													width="3" height="21" style="cursor: move;" />
											</td>
											<td class="fon_tit_curso" align="left" >
												<img
													src="<%=request.getContextPath()%>/img/agenda_icon.gif"
													alt="Agenda" style="cursor: move;" />
											</td>
											<td class="fon_tit_curso" width="85" align="left">
												<div
													style="padding-left: 5px; padding-right: 10px; font-weight: bold;">
													Agenda
													</div>
											</td>
											<td width="22" height="23" valign="top">
												<img
													src="<%=request.getContextPath()%>/img/fon_tit_curso_03.gif"
													width="22" height="21" style="cursor: move;" />
											</td>
										</tr>
									</table>
								</td>
								<td width="15">
									<img
										src="<%=request.getContextPath()%>/img/comprimir_portal.jpg"
										alt="Minimizar" style="cursor: pointer;"
										onClick="esconderServicio('<%=Constante.SERVICIO_AGENDA%>');">
									
								</td>
								<td width="15">
									<img
										src="<%=request.getContextPath()%>/img/cerrar_portal.jpg"
										alt="Cerrar" style="cursor: pointer;"
										onClick="eliminarServicio('<%=Constante.SERVICIO_AGENDA%>');">	
									
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="<%=Constante.SERVICIO_AGENDA%>_tr" style="display: none;">
					<td>
						<div>							
							<%@include file="/comun/portal/servicio_agenda.jsp"%>
						</div>
					</td>
				</tr>
				<tr height="20" style="background-color: #E0EAF3;">
					<td align="right">
						<label
							style="cursor: pointer; font-weight: bold; font-size: 100%; padding-right: 5px;"
							onclick="abrirAgenda('<%=request.getContextPath()%>/agenda/Cargar.action');"
							class="portal_menu_selecionando">
							Ingresar
						</label>
					</td>
				</tr>
			</table>
		</div>
		<div id="<%=Constante.SERVICIO_BUZON%>"
			style="vertical-align: top; width: 241px; padding-bottom: 5px; float: left; padding-top: 5px; display: none;">
			<table width="230" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="23" valign="bottom" class="lin_azul">
						<table cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td valign="bottom">
									<table border="0" align="left" cellpadding="0" cellspacing="0"
										style="cursor: move;" id="tabla_<%=Constante.SERVICIO_BUZON%>">
										<tr>
											<td width="3" height="23" valign="top">
												<img
													src="<%=request.getContextPath()%>/img/fon_tit_curso_01.jpg"
													width="3" height="21" style="cursor: move;" />
											</td>
											<td class="fon_tit_curso" valign="middle" >
												<img src="<%=request.getContextPath()%>/img/icon_email.gif"
													alt="Email" border="0" style="cursor: move;" />
											</td>
											<td class="fon_tit_curso" valign="middle" align="left" width="85">
												<div
													style="float: left; padding-left: 5px; padding-right: 10px; font-weight: bold;">
													Buz&oacute;n
												</div>
											</td>
											<td width="22" height="23" valign="top">
												<img
													src="<%=request.getContextPath()%>/img/fon_tit_curso_03.gif"
													width="22" height="21" style="cursor: move;" />
											</td>
										</tr>
									</table>
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="<%=Constante.SERVICIO_BUZON%>_tr" style="display: none;">
					<td >
						<%@include file="/comun/portal/servicio_buzon.jsp"%>
					</td>
				</tr>
				<tr height="20" style="background-color: #E0EAF3;">
					<td align="right">
						<label
							style="cursor: pointer; font-weight: bold; font-size: 100%; padding-right: 5px;"
							onclick="abrirBuzon('<%=request.getContextPath()%>/comun/buzon/Buzon.action')"
							class="portal_menu_selecionando">
							Ingresar
						</label>
					</td>
				</tr>
			</table>
		</div>

		<div id="<%=Constante.SERVICIO_FOROS%>"
			style="vertical-align: top; width: 241px; padding-bottom: 5px; float: left; padding-top: 5px; display: none;">
			<table width="230" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="23" valign="bottom" class="lin_azul">
						<table cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td valign="bottom">
									<table border="0" align="left"
										id="tabla_<%=Constante.SERVICIO_FOROS%>" cellpadding="0"
										cellspacing="0" style="cursor: move;">
										<tr>
											<td width="3" height="23" valign="top">
												<img
													src="<%=request.getContextPath()%>/img/fon_tit_curso_01.jpg"
													width="3" height="21" style="cursor: move;" />
											</td>
											<td class="fon_tit_curso">
												<img src="<%=request.getContextPath()%>/img/icon_group.gif"
													alt="Foro" border="0" />
											</td>
											<td class="fon_tit_curso" align="left" width="85">
												<strong style="padding-left: 5px; padding-right: 10px;">Foros</strong>
											</td>
											<td width="22" height="23" valign="top">
												<img
													src="<%=request.getContextPath()%>/img/fon_tit_curso_03.gif"
													width="22" height="21" style="cursor: move;" />
											</td>
										</tr>
									</table>
							</td>
							<td width="15">
								<img
									src="<%=request.getContextPath()%>/img/comprimir_portal.jpg"
									alt="Minimizar" style="cursor: pointer;"
									onClick="esconderServicio('<%=Constante.SERVICIO_FOROS%>');">
							</td>
							<td width="15">
									<img
										src="<%=request.getContextPath()%>/img/cerrar_portal.jpg"
										alt="Cerrar" style="cursor: pointer;"
										onClick="eliminarServicio('<%=Constante.SERVICIO_FOROS%>');">	
									
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="<%=Constante.SERVICIO_FOROS%>_tr" style="display: none;">
					<td>
						<%@include file="/comun/portal/servicio_foros.jsp"%>
					<td>
				</tr>
				<tr height="20" style="background-color: #E0EAF3;">

					<td align="right">
						<label
							style="cursor: pointer; font-weight: bold; font-size: 100%; padding-right: 5px;"
							onclick="abrirForo('<%=request.getContextPath()%>/foro/Foro.action')"
							class="portal_menu_selecionando">
							Ingresar
						</label>
					</td>
				</tr>
			</table>
		</div>
		
		

		<div id="<%=Constante.SERVICIO_CURSO%>"
			style="vertical-align: top; width: 241px; padding-bottom: 5px; float: left; padding-top: 5px; display: none;">
			<table width="230" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="23" valign="bottom" class="lin_azul" colspan="2">
						<table cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td width="219" valign="bottom">
									<table border="0" align="left"
										id="tabla_<%=Constante.SERVICIO_CURSO%>" cellpadding="0"
										cellspacing="0" style="cursor: move;">
										<tr>
											<td width="3" height="23" valign="top">
												<img
													src="<%=request.getContextPath()%>/img/fon_tit_curso_01.jpg"
													width="3" height="21" style="cursor: move;" />
											</td>
											<td class="fon_tit_curso">
												<img src="<%=request.getContextPath()%>/img/icon_libro.gif"
													alt="Curso" />
											</td>
											<td class="fon_tit_curso" align="left" width="85">
												<div
													style="padding-left: 5px; padding-right: 10px; font-weight: bold;">
													Cursos
												</div>
											</td>
											<td width="22" height="23" valign="top">
												<img
													src="<%=request.getContextPath()%>/img/fon_tit_curso_03.gif"
													width="22" height="21" style="cursor: move;" />
											</td>
										</tr>
									</table>
							</td>
							<td width="15">
								&nbsp;
							</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="<%=Constante.SERVICIO_CURSO%>_tr" style="display: none;">
					<td colspan="2">
						<%@include file="/comun/portal/servicio_curso.jsp"%>
					</td>
				</tr>
				<tr height="20" style="background-color: #E0EAF3;">
					<td>
					<strong style="padding-left: 7px;"> <span
							class="text_rojo"><%=col_cursos.size()%></span><span>&nbsp;</span><span>curso<%if(1<col_cursos.size()){out.print("s");}%></span>
						</strong>
					</td>
					
					<td align="right">
						<label
							style="cursor: pointer; font-weight: bold; font-size: 100%; padding-right: 5px;"
							onclick="cambiarPagina('<%=request.getContextPath()%>/Curso.action');"
							class="portal_menu_selecionando">
							Ingresar
						</label>
					</td>
				</tr>
			</table>
		</div>

		<div id="<%=Constante.SERVICIO_CHAT%>"
			style="vertical-align: top; width: 241px; padding-bottom: 5px; float: left; padding-top: 5px; display: none;">
			<table width="230" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="23" valign="bottom" class="lin_azul" colspan="2">
						<table cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td valign="bottom">
									<table border="0" align="left" cellpadding="0" cellspacing="0"
										style="cursor: move;" id="tabla_<%=Constante.SERVICIO_CHAT%>">
										<tr>
											<td width="3" height="23" valign="top">
												<img
													src="<%=request.getContextPath()%>/img/fon_tit_curso_01.jpg"
													width="3" height="21" style="cursor: move;" />
											</td>
											<td class="fon_tit_curso">
												<img src="<%=request.getContextPath()%>/img/icono_chat.gif"
													style="cursor: move;" alt="chat" />

											</td>
											<td class="fon_tit_curso" align="left" width="85">
												<div
													style="padding-left: 5px; padding-right: 10px; font-weight: bold;">
													Chat
												</div>
											</td>
											<td width="22" height="23" valign="top">
												<img
													src="<%=request.getContextPath()%>/img/fon_tit_curso_03.gif"
													width="22" height="21" style="cursor: move;" />
											</td>
										</tr>
									</table>
								</td>
								<td width="15">
									<img
										src="<%=request.getContextPath()%>/img/comprimir_portal.jpg"
										alt="Minimizar" style="cursor: pointer;"
										onClick="esconderServicio('<%=Constante.SERVICIO_CHAT%>');">
								</td>
								<td width="15">
									<img
										src="<%=request.getContextPath()%>/img/cerrar_portal.jpg"
										alt="Cerrar" style="cursor: pointer;"
										onClick="eliminarServicio('<%=Constante.SERVICIO_CHAT%>');">		
									
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="<%=Constante.SERVICIO_CHAT%>_tr" style="display: none;">
					<td colspan="2">

					<table width="100%" style="border-bottom: 1px solid #3355AE; border-left: 1px solid #3355AE; border-right: 1px solid #3355AE;">
						<tr>
							<td style="color:gray;font-size:8pt;" height="22" >
							<span style="padding : 7px;">
								No se encuentra disponible el servicio
								</span>		
							</td>
						</tr>
					</table>

					<td>
				</tr>

				<tr id="ncabzado" height="18" style="background-color: #E0EAF3;">
					<td align="left">
						<strong style="padding-left: 7px;"> <span id="online"
							class="text_rojo"></span><span>&nbsp;</span><span id="s"></span>
						</strong>
					</td>
					<td align="right">
						<label
							style="cursor: pointer; font-weight: bold; font-size: 100%; padding-right: 5px;"
							class="portal_menu_selecionando">
							Ingresar
						</label>
					</td>
				</tr>	

			</table>
		</div>
		<div id="<%=Constante.SERVICIO_NOTICIA%>"
			style="vertical-align: top; width: 241px; padding-bottom: 5px; float: left; padding-top: 5px; display: none;">
			<table width="230" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="23" valign="bottom" class="lin_azul">
						<table cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td valign="bottom">
									<table border="0" align="left" cellpadding="0" cellspacing="0"
										style="cursor: move;"
										id="tabla_<%=Constante.SERVICIO_NOTICIA%>">
										<tr>
											<td width="3" height="23" valign="top">
												<img
													src="<%=request.getContextPath()%>/img/fon_tit_curso_01.jpg"
													width="3" height="21" style="cursor: move;" />
											</td>
											<td class="fon_tit_curso">
												<img src="/campus/img/icon_news.gif" alt="noticia"
													style="cursor: move;" />
											</td>
											<td class="fon_tit_curso" align="left" width="85">
												<strong style="padding-left: 5px; padding-right: 10px;">Noticias</strong>
											</td>
											<td width="22" height="23" valign="top">
												<img
													src="<%=request.getContextPath()%>/img/fon_tit_curso_03.gif"
													width="22" height="21" style="cursor: move;" />
											</td>
										</tr>
									</table>
								</td>
								<td>
									&nbsp;									
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="<%=Constante.SERVICIO_NOTICIA%>_tr" style="display: none;">
					<td>
						<%@include file="/comun/portal/servicio_noticia.jsp"%>
					</td>
				</tr>
				<tr height="20" style="background-color: #E0EAF3;">
					<td align="right">
						<label
							style="cursor: pointer; font-weight: bold; font-size: 100%; padding-right: 5px;"
							onclick="abrirNoticia('<%=request.getContextPath()%>/noticia/Cargar.action')"
							class="portal_menu_selecionando">
							Ingresar
						</label>
					</td>
				</tr>
			</table>
		</div>
		<div id="<%=Constante.SERVICIO_CUMPLEANOS%>"
			style="vertical-align: top; width: 241px; padding-bottom: 5px; float: left; padding-top: 5px; display: none;">
			<table width="230" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="23" valign="bottom" class="lin_azul">
						<table cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td valign="bottom">
									<table border="0" align="left" cellpadding="0" cellspacing="0"
										style="cursor: move;"
										id="tabla_<%=Constante.SERVICIO_CUMPLEANOS%>">
										<tr>
											<td width="3" height="23" valign="top">
												<img
													src="<%=request.getContextPath()%>/img/fon_tit_curso_01.jpg"
													width="3" height="21" style="cursor: move;" />
											</td>
											<td class="fon_tit_curso">
												<img src="<%=request.getContextPath()%>/img/cake_icon.gif"
													alt="Cumplea&ntilde;os" border="0" />
											</td>
											<td class="fon_tit_curso" align="left" width="85">
												<div
													style="padding-left: 5px; padding-right: 10px; font-weight: bold;">
													Cumplea&ntilde;os
												</div>
											</td>
											<td width="22" height="23" valign="top">
												<img
													src="<%=request.getContextPath()%>/img/fon_tit_curso_03.gif"
													width="22" height="21" style="cursor: move;" />
											</td>
										</tr>
									</table>
								</td>
								<td width="15">
									<img
										src="<%=request.getContextPath()%>/img/comprimir_portal.jpg"
										alt="Minimizar" style="cursor: pointer;"
										onclick="esconderServicio('<%=Constante.SERVICIO_CUMPLEANOS%>');">
								</td>
								<td width="15">
									<img
										src="<%=request.getContextPath()%>/img/cerrar_portal.jpg"
										alt="Cerrar" style="cursor: pointer;"
										onClick="eliminarServicio('<%=Constante.SERVICIO_CUMPLEANOS%>');">		
									
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="<%=Constante.SERVICIO_CUMPLEANOS%>_tr" style="display: none;">
					<td>
						<%@include file="/comun/portal/servicio_cumpleanos.jsp"%>
					</td>
				</tr>
				<tr height="20" style="background-color: #E0EAF3;">
					<td align="left">
						<label
							style="font-weight: bold; font-size: 100%; padding-left: 5px;"
							>
							<span class="text_rojo"> <%
 	Collection cmm = (Collection) request.getAttribute("cumples");
 	if (null == cmm) {
 		cmm = new ArrayList();
 	}
 	out.print(cmm.size());
 %> </span> usuario<%if(1<cmm.size()){out.print("s"); }%>
						</label>
						<br>
					</td>
				</tr>
			</table>
		</div>
		<div id="<%=Constante.SERVICIO_APUNTES%>"
			style="vertical-align: top; width: 241px; padding-bottom: 5px; float: left; padding-top: 5px; display: none;">
			<table width="230" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="23" valign="bottom" class="lin_azul">
						<table cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td valign="bottom">
									<table border="0" align="left" cellpadding="0" cellspacing="0"
										style="cursor: move;"
										id="tabla_<%=Constante.SERVICIO_APUNTES%>">
										<tr>
											<td width="3" height="23" valign="top">
												<img
													src="<%=request.getContextPath()%>/img/fon_tit_curso_01.jpg"
													width="3" height="21" style="cursor: move;" />
											</td>
											<td class="fon_tit_curso">
												<img width="15" height="16" border="0" title="Apuntes"
													alt="Apuntes"
													src="<%=request.getContextPath()%>/img/apuntes_icon.gif" />
											</td>
											<td class="fon_tit_curso" align="left" width="85">
												<div
													style="padding-left: 5px; padding-right: 10px; font-weight: bold;">
													Apuntes
												</div>
											</td>
											<td width="22" height="23" valign="top">
												<img
													src="<%=request.getContextPath()%>/img/fon_tit_curso_03.gif"
													width="22" height="21" style="cursor: move;" />
											</td>
										</tr>
									</table>
								</td>
								<td width="15">
									<img
										src="<%=request.getContextPath()%>/img/comprimir_portal.jpg"
										alt="Minimizar" style="cursor: pointer;"
										onclick="esconderServicio('<%=Constante.SERVICIO_APUNTES%>');">
								</td>
								<td width="15">
									<img
										src="<%=request.getContextPath()%>/img/cerrar_portal.jpg"
										alt="Cerrar" style="cursor: pointer;"
										onClick="eliminarServicio('<%=Constante.SERVICIO_APUNTES%>');">											
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="<%=Constante.SERVICIO_APUNTES%>_tr" style="display: none;">
					<td >
						<%@include file="/comun/portal/servicio_apuntes.jsp"%>
					</td>
				</tr>
				<tr height="20" style="background-color: #E0EAF3;">
					<td align="right">
						<label
							style="cursor: pointer; font-weight: bold; font-size: 100%; padding-right: 5px;"
							onclick="abrirApuntes('<%=request.getContextPath()%>/anotacion/Anotacion.action')"
							class="portal_menu_selecionando">
							Ingresar
						</label>
					</td>
				</tr>
			</table>
		</div>
		<div id="<%=Constante.SERVICIO_BIBLIOTECA%>"
			style="vertical-align: top; width: 241px; padding-bottom: 5px; float: left; padding-top: 5px; display: none;">
			<table width="230" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="23" valign="bottom" class="lin_azul">
						<table cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td valign="bottom">
									<table border="0" align="left" cellpadding="0" cellspacing="0"
										style="cursor: move;"
										id="tabla_<%=Constante.SERVICIO_BIBLIOTECA%>">
										<tr>
											<td width="3" height="23" valign="top">
												<img
													src="<%=request.getContextPath()%>/img/fon_tit_curso_01.jpg"
													width="3" height="21" style="cursor: move;" />
											</td>
											<td class="fon_tit_curso">
												<img width="15" height="16" border="0" title="Biblioteca"
													alt="Biblioteca"
													src="<%=request.getContextPath()%>/img/icon_blib.gif" />
											</td>
											<td class="fon_tit_curso" align="left" width="85">
												<div
													style="padding-left: 5px; padding-right: 10px; font-weight: bold;">
													Biblioteca
												</div>
											</td>
											<td width="22" height="23" valign="top">
												<img
													src="<%=request.getContextPath()%>/img/fon_tit_curso_03.gif"
													width="22" height="21" style="cursor: move;" />
											</td>
										</tr>
									</table>
								</td>
								<td width="15">
									<img
										src="<%=request.getContextPath()%>/img/comprimir_portal.jpg"
										alt="Minimizar" style="cursor: pointer;"
										onclick="esconderServicio('<%=Constante.SERVICIO_BIBLIOTECA%>');">
								</td>
								<td width="15">
									<img
										src="<%=request.getContextPath()%>/img/cerrar_portal.jpg"
										alt="Cerrar" style="cursor: pointer;"
										onClick="eliminarServicio('<%=Constante.SERVICIO_BIBLIOTECA%>');">									
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="<%=Constante.SERVICIO_BIBLIOTECA%>_tr" style="display: none;">
					<td>
						<%@include file="/comun/portal/servicio_biblioteca.jsp"%>
					</td>
				</tr>
				<tr height="20" style="background-color: #E0EAF3;">
					<td align="right" >
						<label
							style="cursor: pointer; font-weight: bold; font-size: 100%; padding-right: 5px;"
							onclick="abrirBiblioteca()"
							class="portal_menu_selecionando" >
							Ingresar
						</label>
					</td>
				</tr>
			</table>
		</div>
		<%@include file="/comun/portal/clave_nueva.jsp"%>
	</body>
	<script type="text/javascript">
			var portal;
			portal = new CampusVirtual.Portal("#principal div", {onOverWidget:onOverWidget, onOutWidget:onOutWidget, onChange:onChange, removeEffect:Effect.SwitchOff});
			<%Collection<Servicio> portal =(Collection<Servicio>)request.getAttribute("portal");
			for(Servicio serv_p :portal){
			%>
				portal.add(new CampusVirtual.Widget("<%=serv_p.getId()%>"), "<%=serv_p.getColumna()%>");
				<%if(1==serv_p.getVisible()){%>				
				esconderServicio("<%=serv_p.getId()%>");
				<%}%>
				document.getElementById("<%=serv_p.getId()%>").style.display="";
			<%
			}
			%>
			portal._updateColumnsHeight();
	</script>
</html>

