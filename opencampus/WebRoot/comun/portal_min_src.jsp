<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@ taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<%@ page import="edu.tecsup.lms.util.Constante"%>
<%@ page import="edu.tecsup.lms.modelo.Usuario"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}' />
<%	Usuario usuarioBienvenida = (Usuario) request.getSession().getAttribute(Constante.USUARIO_ACTUAL);
	//System.out.println(usuarioBienvenida.toStringFull());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title><s:text name="titulo.campus.virtual" /></title>
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/inicio_util.js"></script>
		<script language="javascript" type="text/javascript"
						src="<%=request.getContextPath()%>/js/inicio.js"></script>
		
		<link href="<c:out value='${contextPath}'/>/estilos/inicio.css" rel="stylesheet" type="text/css" />
	</head>
	<s:include value="capas/reloj.jsp" />
	<body onLoad="mostrarReloj(); cargarAviso();">
		<div id="contenedor">
			<div style="background-color: #BFD8EC;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="6" class="fon_login_iz" height="22" align="center">
							&nbsp;
						</td>
						<td class="fon_login_cen">
							<script type="text/javascript">saludo('<s:text name="portal.saludo.dia"/>','<s:text name="portal.saludo.tarde"/>','<s:text name="portal.saludo.noche"/>')</script>:
						  	<strong style="color:#44659B"><%=usuarioBienvenida.getNombreCorto()%> </strong>
					  	</td>
					  	<td class="fon_login_cen" width="140">
					  		<div id="reloj"></div>
					  	</td>
						</td> 
						<td class="fon_login_cen" width="5" align="center">
						 | 
						</td>	
						<td class="fon_login_cen" width="120" align="center" style="cursor: pointer; white-space: nowrap;" onclick="abrirGuiaEstudiante()">
							<strong><s:text name="portal.bienvenida.guia.alt"/></strong>
						</td>
						<td class="fon_login_cen" width="5" align="center">
							|
						</td>
						<s:text name="idioma" id="id_idioma"/>
						<s:if test="#id_idioma=='en'">
							<td class="fon_login_cen" width="60" align="center" style="cursor: pointer; white-space: nowrap;" 
								onclick="window.location='<%=request.getContextPath()%>/GuardarIdioma.action?request_locale=es'">
								<strong><s:text name="idioma.espanol"></s:text></strong>
							</td>
						</s:if>
						<s:if test="#id_idioma=='es'">
							<td class="fon_login_cen" width="60" align="center" style="cursor: pointer; white-space: nowrap;" 
								onclick="window.location='<%=request.getContextPath()%>/GuardarIdioma.action?request_locale=en'">
								<strong><s:text name="idioma.ingles"></s:text></strong>
							</td>
						</s:if>
						<td class="fon_login_cen" width="5" align="center">
							|
						</td>
						<td class="fon_login_cen" width="30" align="center">
							<span style="cursor:pointer;"
								onclick="window.location='<%=request.getContextPath()%>/Salir.action'; cerrarVentanas();" class="salir"><s:text name="portal.bienvenida.salir"/></span>
						</td>
						<td width="6" class="fon_login_de">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<!-- div id="banner"></div-->
			<div class="banner_min"><span>Tecsup Virtu@l</span></div>
			<div id="bienvenida">
				<ct:MenuPortada><s:text name="idioma"/></ct:MenuPortada>
			</div>
						
			<!--  Cuerpo -->
			
			
			
			
			<div id="cuerpo">
				<div id="principal">
				
					<div style="width: 242px; float: left; min-height: 50px; padding-bottom: 10px;">
					
					
						<table width="96%" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
							<tr>
								<td class="titulo_modulo">
									<strong><s:text name="portal.servicio.titulo.curso"></s:text></strong>
								</td>
							</tr>
							<tr>
								<td style="border: 1px solid #3355AE;">
								
								
									<table cellpadding="2" cellspacing="0" width="100%" class="tabla_sin_layout" border="0">
									<c:forEach var="ficha" items="${cursos}">	
										<tr onclick="cambiarPagina('<c:out value='${contextPath}'/>/aulavirtual/AulaVirtual.action?id=<c:out value="${ficha.idFicha}"/>');" class="portal_selecionando" >
									
											<td height="20" width="20" valign="middle"   style="cursor: pointer;padding-bottom:2px;" align="center">
												&raquo;
											</td>
											<td valign="middle" style="font-size: 100%;cursor: pointer;" align="left" >
												<label style="cursor: pointer;"><c:out value="${ficha.nombre}"/></label>						
											</td>
											<td width="35" align="right" valign="middle"  style="cursor: pointer;">
												<c:if test="${0<ficha.trabajoIndividual}">
													<strong style="color: red;"><c:out value="${ficha.trabajoIndividual}"/>&nbsp;</strong>
													<img alt="Pendiente" src="<c:out value='${contextPath}'/>/img/flag.gif"  width="8"
													height="11" />
												</c:if>
											</td>
										</tr>
									</c:forEach>
									<c:if test="${fn:length(cursos)==0}">
										<tr>
											<td style="color:gray;font-size:8pt;" height="22" >
												<span style="padding : 7px;">
													<s:text name="portal.curso.contenido.vacio"/>
												</span>
											</td>
										</tr>	
									</c:if>
									</table>
									
									
								</td>
							</tr>
							<tr height="20" style="background-color: #E0EAF3;">
								<td>
									<span style="float:left; font-weight: bold; font-size: 100%; padding-left: 5px;">
										<strong><span class="text_rojo"><c:out value="${fn:length(cursos)}"/></span> <s:text name="portal.curso.detalle"/></strong>
									</span>
									<span style="float:right; cursor: pointer; font-weight: bold; font-size: 100%; padding-right: 5px;"
										onclick="abrir_servicio_curso();"
										class="portal_menu_selecionando">
										<s:text name="portal.servicios.link"/>
									</span>
								</td>
							</tr>
						</table>
						<br/>
						<table width="96%" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
							<tr>
								<td class="titulo_modulo">
									<strong><s:text name="portal.servicio.titulo.buzon"></s:text></strong>
								</td>
							</tr>
							<tr>
								<td style="border: 1px solid #3355AE;">
								
								
									<table width="100%" cellpadding="2" cellspacing="0" style="table-layout: fixed;">
										<tr>
											<td colspan="3">
												<table cellpadding="1" cellspacing="0" width="100%" class="tabla_sin_layout" border="0">
												<c:forEach var="mensaje" items="${mensajes}">
													<tr>
														<td  height="20" width="20" style="color: #44659B;padding-bottom:2px;" align="center" valign="middle">
															&raquo;
														</td>
														<td valign="middle" align="left">
															<c:out value="${mensaje.titulo}"></c:out>
														</td>
														<td width="10" align="center" valign="middle">
															<img src="<%=request.getContextPath()%>/img/flag.gif" width="8" height="11" alt="Sin leer"/>
														</td>
													</tr>
												</c:forEach>
												<c:if test="${fn:length(mensajes)==0}">
													<tr>
														<td style="color:gray;font-size:8pt;" height="22" >
															<span style="padding : 7px;">
																<s:text name="portal.buzon.contenido.vacio"/>
															</span>
														</td>
													</tr>
												</c:if>
												</table>
											</td>
										</tr>
									</table>
									
									
								</td>
							</tr>
							<tr height="20" style="background-color: #E0EAF3;">
								<td>
									<span style="float:left; font-weight: bold; font-size: 100%; padding-left: 5px;">
										<strong><span class="text_rojo"><c:out value="${fn:length(mensajes)}" /></span> <s:text name="portal.buzon.detalle"/></strong>
									</span>
									<span style="float:right; cursor: pointer; font-weight: bold; font-size: 100%; padding-right: 5px;"
										onclick="abrir_servicio_buzon();"
										class="portal_menu_selecionando">
										<s:text name="portal.servicios.link"/>
									</span>
								</td>
							</tr>
						</table>
						
					</div>
					<div style="width: 242px; float: left; min-height: 50px; padding-bottom: 10px;">
					
						<table width="96%" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
							<tr>
								<td class="titulo_modulo">
									<strong><s:text name="portal.servicio.titulo.noticia"></s:text></strong>
								</td>
							</tr>
							<tr>
								<td style="border: 1px solid #3355AE;">
								
								
									<table width="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed;">
										<c:forEach items="${noticias}" var="noticia" varStatus="fila">
											<tr style="background-color: #E0EAF3;">
												<td align="left" width="40%" style="padding:3px;">
													<strong><c:out value="${noticia.imagen}"/></strong>
												</td>
												<td align="right" style="padding:3px;">
													<strong style="color: rgb(68, 101, 155);"><f:DateToString fecha="${noticia.fecha}"/></strong>
												</td>
											</tr>
											<tr>
												<td valign="top" colspan="2">
								
													<table width="100%" border="0" cellspacing="0" cellpadding="0">
														<tr>
															<td class="tituloNoticia" align="left">
																<c:out value="${noticia.titular}" />
															</td>
														</tr>
														<tr>
															<td  class="textoNoticia" valign="top"  style="padding-bottom:5px;">
																<c:out value="${noticia.cuerpo}" />
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</c:forEach>
									</table>
									
									
								</td>
							</tr>
							<tr height="20" style="background-color: #E0EAF3;">
								<td>
									<span style="float:left; font-weight: bold; font-size: 100%; padding-left: 5px;">
										&nbsp;
									</span>
									<span style="float:right; cursor: pointer; font-weight: bold; font-size: 100%; padding-right: 5px;"
										onclick="abrir_servicio_noticia();"
										class="portal_menu_selecionando">
										<s:text name="portal.servicios.link"/>
									</span>
								</td>
							</tr>
						</table>
					
					</div>
					<div style="width: 242px; float: left; min-height: 50px; padding-bottom: 10px;">
					
						<table width="96%" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
							<tr>
								<td class="titulo_modulo">
									<strong><s:text name="portal.servicio.titulo.foro"></s:text></strong>
								</td>
							</tr>
							<tr height="20" style="background-color: #E0EAF3;">
								<td>
									<span style="float:left; font-weight: bold; font-size: 100%; padding-left: 5px;">
										&nbsp;
									</span>
									<span style="float:right; cursor: pointer; font-weight: bold; font-size: 100%; padding-right: 5px;"
										onclick="abrir_servicio_foros();"
										class="portal_menu_selecionando">
										<s:text name="portal.servicios.link"/>
									</span>
								</td>
							</tr>
						</table>
						<br/>
						<table width="96%" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
							<tr>
								<td class="titulo_modulo">
									<strong><s:text name="portal.servicio.titulo.apunte"></s:text></strong>
								</td>
							</tr>
							<tr height="20" style="background-color: #E0EAF3;">
								<td>
									<span style="float:left; font-weight: bold; font-size: 100%; padding-left: 5px;">
										&nbsp;
									</span>
									<span style="float:right; cursor: pointer; font-weight: bold; font-size: 100%; padding-right: 5px;"
										onclick="abrir_servicio_apuntes();"
										class="portal_menu_selecionando">
										<s:text name="portal.servicios.link"/>
									</span>
								</td>
							</tr>
						</table>
						<br/>
						<table width="96%" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
							<tr>
								<td class="titulo_modulo">
									<strong><s:text name="portal.servicio.titulo.biblioteca"></s:text></strong>
								</td>
							</tr>
							<tr height="20" style="background-color: #E0EAF3;">
								<td>
									<span style="float:left; font-weight: bold; font-size: 100%; padding-left: 5px;">
										&nbsp;
									</span>
									<span style="float:right; cursor: pointer; font-weight: bold; font-size: 100%; padding-right: 5px;"
										onclick="abrir_servicio_biblioteca();"
										class="portal_menu_selecionando">
										<s:text name="portal.servicios.link"/>
									</span>
								</td>
							</tr>
						</table>
						<br/>
						
						<table width="96%" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
							<tr>
								<td class="titulo_modulo">
									<strong><s:text name="portal.servicio.titulo.agenda"></s:text></strong>
								</td>
							</tr>
							<tr height="20" style="background-color: #E0EAF3;">
								<td>
									<span style="float:left; font-weight: bold; font-size: 100%; padding-left: 5px;">
										&nbsp;
									</span>
									<span style="float:right; cursor: pointer; font-weight: bold; font-size: 100%; padding-right: 5px;"
										onclick="abrir_servicio_agenda();"
										class="portal_menu_selecionando">
										<s:text name="portal.servicios.link"/>
									</span>
								</td>
							</tr>
						</table>
						<br/>
						
						<table width="96%" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
							<tr>
								<td class="titulo_modulo">
									<strong><s:text name="portal.servicio.titulo.chat"></s:text></strong>
								</td>
							</tr>
							<tr height="20" style="background-color: #E0EAF3;">
								<td>
									<span style="float:left; font-weight: bold; font-size: 100%; padding-left: 5px;">
										&nbsp;
									</span>
									<span style="float:right; cursor: pointer; font-weight: bold; font-size: 100%; padding-right: 5px;"
										onclick="abrir_servicio_chat();"
										class="portal_menu_selecionando">
										<s:text name="portal.servicios.link"/>
									</span>
								</td>
							</tr>
						</table>
						<br/>
						
					</div>
					<div style="width: 242px; float: left; min-height: 50px; padding-bottom: 10px;">
					
						<table width="96%" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
							<tr>
								<td class="titulo_modulo">
									<strong><s:text name="portal.servicio.titulo.enlace"></s:text></strong>
								</td>
							</tr>
							<tr>
								<td style="border: 1px solid #3355AE;">
								
									<div style="overflow-x: hidden;overflow-y: auto; max-height: 200px;">
										<s:include value="links.jsp" />
									</div>
								
								</td>
							</tr>
							<tr height="20" style="background-color: #E0EAF3;">
								<td>
									<span style="float:left; font-weight: bold; font-size: 100%; padding-left: 5px;">
										&nbsp;
									</span>
								</td>
							</tr>
						</table>
						<br/>
						
						<table width="96%" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
							<tr>
								<td class="titulo_modulo">
									<strong><s:text name="portal.servicio.titulo.cumpleanio"></s:text></strong>
								</td>
							</tr>
							<tr>
								<td style="border: 1px solid #3355AE;">
								
									<div style="overflow-x: hidden; overflow-y: auto; max-height: 90px;">
										<table cellpadding="2" cellspacing="0" width="100%" class="tabla_sin_layout" border="0">
											<c:forEach var="cumple" items="${cumples}">
											<tr class="portal_selecionando" style="cursor:pointer; <c:if test="${cumple.rolPredeterminado != 145}">background-color: #F0F8E5;</c:if>"
												onclick="enviarCorreo('<c:out value="${cumple.idUsuario}"/>','Feliz Cumpleaņos!')">
												<td align="left" valign="midle" style="padding-left: 5px;">
													<c:out value="${cumple.usuarioDato.nombreCompleto}"/>
												</td>
												<td width="20" align="center" valign="midle">
													<a href="javascript:void('<c:out value="${cumple.usuarioDato.anoNacimiento}"/>')">
													<img src="<c:out value='${contextPath}'/>/img/icon_mail_send.gif" border="0" alt="Enviar Correo">
													</a>
												</td>
												<% if(request.getHeader("user-agent").indexOf("MSIE") != -1){%>
													<td width="10">&nbsp;</td>
												<%} %>
												</script>
											</tr>
											</c:forEach>	
											<c:if test="${fn:length(cumples)==0}">
												<tr>
													<td style="color:gray;font-size:8pt;" height="22" >
														<span style="padding : 7px;">
															<s:text name="portal.cumpleanos.contenido.vacio"/>
														</span>
													</td>
												</tr>
											</c:if>
										</table>
									</div>
								
								</td>
							</tr>
							<tr height="20" style="background-color: #E0EAF3;">
								<td>
									<span style="float:left; font-weight: bold; font-size: 100%; padding-left: 5px;">
										<strong><span class="text_rojo"><c:out value="${fn:length(cumples)}" /></span> <s:text name="portal.cumpleanos.detalle"/></strong>
									</span>
								</td>
							</tr>
						</table>
						<br/>
					
					</div>
					
				</div>
			</div>
			<div id="pie">
				<s:include value="pie_copyright.jsp" />
			</div>
		</div>
	
		<s:include value="portal/clave_nueva.jsp" />
		
	</body>
</html>

