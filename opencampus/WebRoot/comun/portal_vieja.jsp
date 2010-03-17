<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  page import="edu.tecsup.lms.modelo.Usuario, edu.tecsup.lms.modelo.usuario.Rol"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%
			Usuario usuario = (Usuario) request.getSession().getAttribute(
			Constante.USUARIO_ACTUAL);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><s:text name="titulo.campus.virtual" />
		</title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript"
			src="<%=request.getContextPath()%>/js/login.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/fecha.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/noticias.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>			
		<script language="javascript" >
			function newWin() {
				NewWin0 = open('<c:out value="${pageContext.request.contextPath}"/>/comun/buzon/buzon_listar.jsp','showwindow0','alwaysRaised=yes,dependent=yes,toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=no,resizable=yes,width=840,height=500,left=20,top=6');
			}
		</script>	
	</head>
	<%@include file="/comun/capas/reloj.jsp" %>
	<body onLoad="mostrarReloj()">	
		<div id="contenedor">
			<div id="menu_principal">
				<ct:MenuPortada />
			</div>
			<div id="banner">
				<table width="100%" style="height:60px;" border="0" cellpadding="3"
					cellspacing="0">
					<tr>
						<td>&nbsp;
							
						</td>
					</tr>
					<tr>
						<td align="right" valign="bottom">
							<div id="reloj"></div>
						</td>
					</tr>
				</table>
			</div>
			<div id="bienvenida">
				<table width="100%" border="0" cellpadding="3" cellspacing="0">
					<tr>
						<td width="1%">&nbsp;
							
						</td>
						<td width="77%">
							<script type="text/javascript">saludo()</script>
							:
							<strong><%=usuario.getNombreCompleto()%> </strong>
						</td>
						<td width="3%">
							<a href="<%=request.getContextPath()%>/alumno/cursos.html"><img
									src="<%=request.getContextPath()%>/img/icon_libro.gif"
									alt="Cursos" width="16" height="15" border="0" title="Cursos" />
							</a>
						</td>
						<td width="3%">
							<a href="#"><img
									src="<%=request.getContextPath()%>/img/icon_blib.gif"
									alt="Biblioteca Virtual" width="15" height="16" border="0"
									title="Biblioteca Virtual" /> </a>
						</td>
						<td width="3%">
							<a href="<%=request.getContextPath()%>/alumno/buzon.html"><img
									src="<%=request.getContextPath()%>/img/icon_mail.gif"
									alt="Correo" width="16" height="11" border="0" title="Correo" />
							</a>
						</td>
						<td width="3%">
							<a href="#"><img
									src="<%=request.getContextPath()%>/img/icon_guia.gif"
									alt="Gu&iacute;a del Estudiante"
									title="Gu&iacute;a del Estudiante" width="16" height="16"
									border="0" /> </a>
						</td>
						<td width="3%" align="center">
							|
						</td>
						<td width="4%">
							<a href="<%=request.getContextPath()%>/Salir.action"
								class="salir">Salir</a>
						</td>
						<td width="3%">
							<img src="<%=request.getContextPath()%>/img/icon_salir.gif"
								alt="Salir" title="Salir" width="16" height="16" />
						</td>
					</tr>
				</table>
			</div>
			<div id="cuerpo">
				<div id="principal">
					<div id="inicio">
						<table width="100%" border="0" cellpadding="0" cellspacing="7">
							<tr>
								<td colspan="4" class="lin_azul">
									<img src="<%=request.getContextPath()%>/img/fon_cab_inicio.jpg"
										alt="Inicio" width="92" height="21" />
								</td>
							</tr>
							<tr>
								<td width="25%" align="center" class="bor_inicio">
									<img src="<%=request.getContextPath()%>/img/ini_cursos.jpg"
										alt="Cursos" width="48" height="47" />
								</td>
								<td width="25%" align="center" class="bor_inicio">
									<img src="<%=request.getContextPath()%>/img/ini_correo.jpg"
										alt="Correo" width="64" height="48" />
								</td>
								<td width="25%" align="center" class="bor_inicio">
									<img src="<%=request.getContextPath()%>/img/ini_bib.jpg"
										alt="Biblioteca Virtual" width="44" height="49" />
								</td>
								<td width="25%" align="center" class="bor_inicio">
									<img src="<%=request.getContextPath()%>/img/ini_cafe.jpg"
										alt="Cafeter&iacute;a" width="48" height="46" />
								</td>
							</tr>
							<tr>
								<td align="center" class="fon_cab_ini">
										<a href="<%=request.getContextPath() %>/Curso.action" class="tit_cab">Cursos</a>
								</td>
								<td align="center" class="fon_cab_ini">
									<a class="tit_cab" href="javascript:newWin()" onEnter="javascript:newWin()">Buz&oacute;n de Correo</a>
								</td>
								<td align="center" class="fon_cab_ini">
									<a href="#" class="tit_cab">Biblioteca Virtual</a>
								</td>
								<td align="center" class="fon_cab_ini">
									<a href="#" class="tit_cab">Cafeter&iacute;a</a>
								</td>
							</tr>
						</table>
					</div>
					<div id="noticias">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="73%">
									<table width="100%" border="0" cellpadding="0" cellspacing="5">
										<tr class="alto21">
											<td class="lin_rojo">
												<table width="100%" border="0" cellpadding="0"
													cellspacing="0">
													<tr>
														<td width="15%">
															<img
																src="<%=request.getContextPath()%>/img/tit_cab_noticias_pfr.jpg"
																alt="Noticias PFR" width="150" height="21" />
														</td>
														<td width="85%" align="right">
															<a href="#" class="link_rojo">ver m&aacute;s&gt;&gt;
															</a>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td class="bor_plomo" height="100">
												<p>
													<script type="text/javascript">noticias()</script>
												</p>
											</td>
										</tr>
									</table>
								</td>
								<td width="27%">
									<table width="150" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td height="21" class="fon_cab_noticias">
												<img src="<%=request.getContextPath()%>/img/icon_news.gif"
													alt="new" width="18" height="16" hspace="5" align="middle" />
												<b class="text_blanco">Noticias</b>
											</td>
										</tr>
										<tr>
											<td class="bor_amarillo">
												<p>
													<a href="#" class="text_amarillo"><strong>Titulo
															Noticia 1</strong> </a>
												</p>
												<p>
													<a href="#" class="text_amarillo"><strong>Titulo
															Noticia 2</strong> </a>
												</p>
												<p>
													<a href="#" class="text_amarillo"><strong>Titulo
															Noticia 3</strong> </a>
												</p>
												<p>
													<a href="#" class="text_amarillo"><strong>Titulo
															Noticia 4</strong> </a>
												</p>
												<br />
												<p>
													<span class="ver_mas80"><a href="#"
														class="link_amarillo">ver m&aacute;s&gt;&gt;</a> </span>
												</p>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
					<div id="otros">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="50%" align="left">
									<table width="275" border="0" cellpadding="0" cellspacing="5">
										<tr>
											<td>
												<img
													src="<%=request.getContextPath()%>/img/tit_cab_objetivos.jpg"
													alt="Objetivos y Resultados" title="Obejtivos y Resultados"
													width="275" height="21" />
											</td>
										</tr>
										<tr>
											<td class="bor_plomo">
												<p>
													<strong><a href="#" class="text_rojo">T&iacute;tulo
															Objetivos y Resultados 1</a> </strong>
												</p>
												<p>
													<strong><a href="#" class="text_rojo">T&iacute;tulo
															Objetivos y Resultados 1</a> </strong>
												</p>
												<p>
													<strong><a href="#" class="text_rojo">T&iacute;tulo
															Objetivos y Resultados 1</a> </strong>
												</p>
												<p>
													<strong><a href="#" class="text_rojo">T&iacute;tulo
															Objetivos y Resultados 1</a> </strong>
												</p>
												<p>
													<span class="ver_mas200"><a href="#"
														class="link_rojo">ver m&aacute;s&gt;&gt; </a> </span>
												</p>
											</td>
										</tr>
									</table>
								</td>
								<td width="50%" align="right">
									<table width="275" border="0" cellpadding="0" cellspacing="5">
										<tr>
											<td>
												<img
													src="<%=request.getContextPath()%>/img/tit_cab_boletin.jpg"
													alt="Boletines" width="275" height="21" />
											</td>
										</tr>
										<tr>
											<td class="bor_plomo">
												<p>
													<strong><a href="#" class="text_azul">T&iacute;tulo
															Bolet&iacute;n 1</a> </strong>
												</p>
												<p>
													<strong><a href="#" class="text_azul">T&iacute;tulo
															Bolet&iacute;n 2</a> </strong>
												</p>
												<p>
													<strong><a href="#" class="text_azul">T&iacute;tulo
															Bolet&iacute;n 3</a> </strong>
												</p>
												<p>
													<strong><a href="#" class="text_azul">T&iacute;tulo
															Bolet&iacute;n 4</a> </strong>
												</p>
												<p>
													<span class="ver_mas200"><a href="#"
														class="link_azul">ver m&aacute;s&gt;&gt;</a> </span>
												</p>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
					<div id="foros">
						<table width="100%" border="0" cellpadding="0" cellspacing="5">
							<tr>
								<td class="lin_verde">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="26%">
												<img
													src="<%=request.getContextPath()%>/img/tit_cab_foros.jpg"
													alt="foros" width="150" height="21" />
											</td>
											<td width="74%" align="right">
												<a href="#" class="link_verde">ver mas&gt;&gt; </a>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="bor_plomo">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="50%" valign="top">
												<p>
													<strong><a href="#" class="text_verde">Aplicaci&oacute;n
															de Reles Digitales en Redes</a> </strong>
												</p>
												<p>
													<strong><a href="#" class="text_verde">Computaci&oacute;n</a>
													</strong>
												</p>
												<p>
													<strong><a href="#" class="text_verde">Formaci&oacute;n
															y Recursos Humanos </a> </strong>
												</p>
											</td>
											<td width="50%" valign="top">
												<p>
													<strong><a href="#" class="text_verde">Procesos
															Metal&uacute;rgicos </a> </strong>
												</p>
												<p>
													<strong><a href="#" class="text_verde">Turismo</a>
													</strong><strong><a href="#" class="text_verde"></a> </strong>
												</p>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div id="derecha">
					<%@ include file="/comun/capas/derecha.jsp" %>
				</div>
			</div>
			<div id="pie">
				<p class="pie">
					Todos los Derechos Reservados &copy; 2007 www.tecsup.edu.pe
				</p>
			</div>
		</div>
	</body>
</html>


