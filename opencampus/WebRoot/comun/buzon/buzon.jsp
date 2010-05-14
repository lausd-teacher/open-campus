<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@  page import="edu.opencampus.lms.modelo.Usuario"%>
<%@  page import="edu.opencampus.lms.util.Constante"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	Usuario usuario = (Usuario) request.getSession().getAttribute(
			Constante.USUARIO_ACTUAL);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=iso-8859-1" />
		<title><s:text name="titulo.campus.virtual" /></title>
		<link href="<%=request.getContextPath()%>/comun/buzon/buzonCSS.css"
			rel="stylesheet" type="text/css" />
		
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/comun/buzon/buzonJS.js'></script>
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/js/jComponente.js'></script>
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/js/jPrototype.js'></script>
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/js/util.js'></script>
			<script type="text/javascript"
			src='<%=request.getContextPath()%>/dwr/util.js'></script>

		<script>
			var imagesDir = "<%=request.getContextPath()%>/js/openwysiwyg/icons/";
			var cssDir = "<%=request.getContextPath()%>/js/openwysiwyg/styles/";
			var popupsDir = "<%=request.getContextPath()%>/js/openwysiwyg/popups/";
		</script>
		<script language="JavaScript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/openwysiwyg/wysiwyg.js">
		</script>

	</head>

	<body class="sd" onLoad="javascript:init();"
		onbeforeunload="javascript:cerrarBuzon();" onresize="resize()">

		<table width="100%" border="0" cellpadding="7" cellpadding="0">
			<tr>
				<td>
					<table width="100%" border="0" class="bor_tabla" cellpadding="0"
						cellspacing="0" style="vertical-align: middle">
						<tr class="fon_tit_buzon">
							<td height="20" class="tit_cab">
								Buz&oacute;n
							</td>
						</tr>
						<tr>
							<td>

								<div >
									<table width="100%" cellspacing="0" cellpadding="0">
										<tbody>
											<tr>
												<td>
													<div style="padding-top: 5px;">
														<table width="100%" cellspacing="0" cellpadding="0">
															<tbody>
																<tr bgcolor="#ffffff">
																	<td style="padding-left: 9px;">
																		<form id="s" class="s" style="white-space: nowrap;">
																			<input id="carpeta_input" name="q" maxlength="17"
																				size="20" />
																			<input class="form_button" type="button"
																				value="Crear Carpeta"
																				onClick="crearCarpeta('carpeta_input')" />
																			<input class="form_button" type="button"
																				value="Configuraci&oacute;n de Carpetas"
																				onClick="abrirConfiguration()" />
																		</form>
																	</td>
																	<td width="100%" valign="top"
																		style="padding-left: 5px;">
																		<div id="salir">
																			<span class="texto" style="cursor: pointer"
																				onClick="javascript:window.close();"><strong>Cerrar</strong><a
																				href="#" class="salir"
																				onClick="javascript:window.close();"> <img
																						src="<%=request.getContextPath()%>/img/salir_x.gif"
																						width="13" height="13" border="0" /> </a> </span>
																		</div>
																		<div id="aviso" />
																	</td>
																</tr>
															</tbody>
														</table>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								<br>
								<!--Empieza menú de la izquierda-->
								<div style="margin-top: 0px; vertical-align:top;">
									<div id="nav" style="width: 16.60ex; position:absolute; top: 96px; left: 10px;" >
<div class="nl" id="redactar">
											<span style="cursor: pointer;" id="comp" class="texto"
												onClick="nuevo()"><img
													src="<%=request.getContextPath()%>/img/redactar.gif" />&nbsp;<strong>Redactar</strong>
											</span>
									  </div>
										<br>
										<div id="R" class="nl">
											<span id="comp" class="texto" style="cursor: pointer;"
												onClick="recibidos('R')"><img
													src="<%=request.getContextPath()%>/img/recibidos.gif" /><b
												class="texto">Recibidos (<a id="reci"><s:property
															value="noleidos" /> </a>)</b> </span>
										</div>

										<div class="nl" id="F">
											<span id="ds_starred" class="texto" style="cursor: pointer;"
												onClick="recibidos('F')"><img
													src="<%=request.getContextPath()%>/img/icon_importante_y.gif" /><b
												class="texto">Destacados</b> </span>
										</div>

										<div class="nl" id="E">
											<span id="ds_sent" class="texto" style="cursor: pointer;"
												onClick="recibidos('E')"><img
													src="<%=request.getContextPath()%>/img/enviados.gif" /><b
												class="texto">Enviados</b> </span>
										</div>

										<div class="nl" id="P">
											<span id="ds_trash" class="texto" style="cursor: pointer;"
												onClick="recibidos('P')"><img
													src="<%=request.getContextPath()%>/img/icon_trash.gif" /><b
												class="texto">&nbsp;Papelera</b> </span>
										</div>

										<div class="nl">
											<br />
										</div>


										<c:forEach items="${CARPETAS}" var="carpeta">
											<div class="nl" id="<c:out value="${carpeta.idCarpeta}"/>">
												<span style="cursor: pointer;"
													id="c<c:out value="${carpeta.idCarpeta}"/>" class="texto"
													onClick="recibidos('<c:out value="${carpeta.idCarpeta}"/>')"><img
														src="<%=request.getContextPath()%>/img/carpeta.gif" /><b
													class="texto">&nbsp; <c:out value="${carpeta.nombre}" />
												</b> </span>
								  </div>
</c:forEach>

									</div>

								</div>
								<!--fin del menu de la izquierda-->


								<%--comienzo de la parte dinámica--%>
								<div id="co" style="margin-left: 16.6ex;">
									<%--la cabecera--%>
									<div id="tct" class="thc">

										<div class="tbc">
											<div class="tbcr">
												<span> <input id="ac_dd1" onClick="eliminar()"
														type="button" value="Eliminar" class="form_button">
												</span>

											</div>

											<div class="tbcr">
												<form name="form1">
													<select name="select" id="opts"
														onChange="javascript:moverMensajes(this.form.select.value)">
														<option>
															Acciones
														</option>
														<optgroup label="Marcar">
															<option value="L">
																Marcar como le&iacute;do
															</option>
															<option value="N">
																Marcar como no le&iacute;do
															</option>
														</optgroup>
														<optgroup label="Mover a carpeta...">
															<option id="" value="R">
																Recibidos
															</option>
															<option id="nl" value="P">
																Papelera
															</option>
															<c:forEach items="${CARPETAS}" var="carpeta">
																<option value="<c:out value="${carpeta.idCarpeta}"/>">
																	<c:out value="${carpeta.nombre}" />
																</option>
															</c:forEach>
														</optgroup>
													</select>
												</form>
											</div>
											<div class="tbcr">
												<form name="form3">
													<select name="select4" id="mp1"
														onChange="javascript:cambiarMaxPag(this.form.select4.value)">
														<option value="0">
															Ver
														</option>
														<option value="10">
															10
														</option>
														<option value="15">
															15
														</option>
														<option value="20">
															20
														</option>
														<option value="25">
															25
														</option>
													</select>
												</form>
											</div>
											<div class="tbcp">
												<span style="white-space: nowrap;"> <b id="primero1">
														Primero </b><b id="anterior1"> &lt; </b><b id="start"> </b>&nbsp;-&nbsp;<b
													id="end"> </b>&nbsp;de&nbsp;<b id="all"> </b><b
													id="siguiente1"> &gt; </b> <b id="ultimo1"></b> </span>
											</div>
											<div class="selec">
												Seleccionar:
												<span id="sl_a" class="l" onClick="todos()">Todos</span>,&nbsp;
												<span id="sl_n" class="l" onClick="ninguna()">Ninguno</span>,&nbsp;
												<span id="sl_t" class="l" onClick="marcarNoLeidos()">No
													leidos</span>,&nbsp;
												<span id="sl_t" class="l" onClick="marcarLeidos()">Leidos</span>,&nbsp;
												<span id="sl_t" class="l" onClick="marcarDestacados()">Destacados</span>,&nbsp;
												<span id="sl_t" class="l" onClick="marcarNoDestacados()">No
													destacados</span>
												
											</div>
											<table border="0" width="100%" bgcolor="#ffffff" cellpadding="0" cellspacing="0" >
												<tr height="20" >
													
													<td width="1%" style="font-size: 13px;font-weight: bold; background: #E5FEC1 none repeat scroll 0%;">&nbsp;</td>
													<td width="5%" style="font-size: 13px;font-weight: bold; background: #C6CEC4 none repeat scroll 0%; border-bottom: 1px solid #CCCCCC;">&nbsp;</td>
													<td width="30%" colspan="2" align="center" style="font-size: 13px;font-weight: bold; background: #C6CEC4 none repeat scroll 0%; border-bottom: 1px solid #CCCCCC;" >De</td>
													
													<td width="40%" colspan="2" align="center" style="font-size: 13px;font-weight: bold; background: #C6CEC4 none repeat scroll 0%; border-bottom: 1px solid #CCCCCC;" >Asunto</td>
													
													<td width="22%" align="center" style="font-size: 13px;font-weight: bold; background: #C6CEC4 none repeat scroll 0%; border-bottom: 1px solid #CCCCCC;" >Fecha</td>
													
												</tr>
												</table> 
										</div>
									</div>


									<%--fin de la cabecera--%>


									<div class="fs" style="background-color: #ffffff">
										<!--  <table border="0" width="100%" bgcolor="#ffffff" cellpadding="0" cellspacing="0" style="border-bottom: 1px solid #CCCCCC;">
												<tr height="20" >
													
													<td width="4%" style="font-size: 13px;font-weight: bold; background: #C6CEC4 none repeat scroll 0%;"></td>
													<td width="3%" style="font-size: 13px;font-weight: bold; background: #C6CEC4 none repeat scroll 0%;"></td>
													<td style="font-size: 13px;font-weight: bold; background: #C6CEC4 none repeat scroll 0%;" width="30%" colspan="2" align="center">De</td>
													
													<td style="font-size: 13px;font-weight: bold; background: #C6CEC4 none repeat scroll 0%;" width="40%" colspan="2" align="center">Asunto</td>
													
													<td style="font-size: 13px;font-weight: bold; background: #C6CEC4 none repeat scroll 0%;" width="32%" align="center">Fecha</td>
													
												</tr>
										</table> -->
										<div id="tbd" style="min-height: 250px;">
											<%--aqui los mensajes, que no los ves?--%>
											
											<div class="tf" id="mensajeInicial">
												No tiene mensajes en esta carpeta.
											</div>
										</div>
									</div>


									<%--Inicio del pie --%>
									<div id="tcb" class="thc">
										<div style="padding-top: 1px;" />
											<div class="tbc">
												<div class="tbcr">
													<span> <input id="ac_dd" onClick="eliminar()"
															type="button" value="Eliminar" class="form_button">
													</span>

												</div>

												<div class="tbcr">
													<form name="form2">
														<select name="select2" id="bam"
															onChange="javascript:moverMensajes(this.form.select2.value)"
															style="vertical-align: middle; width: 140px">
															<option>
																Acciones
															</option>
															<optgroup label="Marcar">
																<option value="L">
																	Marcar como le&iacute;do
																</option>
																<option value="N">
																	Marcar como no le&iacute;do
																</option>
															</optgroup>
															<optgroup label="Mover a carpeta...">
																<option id="" value="R">
																	Recibidos
																</option>
																<option id="nl" value="P">
																	Papelera
																</option>
																<c:forEach items="${CARPETAS}" var="carpeta">
																	<option value="<c:out value="${carpeta.idCarpeta}"/>">
																		<c:out value="${carpeta.nombre}" />
																	</option>
																</c:forEach>
															</optgroup>
														</select>
													</form>
												</div>
												<div class="tbcr">
													<form name="form4">
														<select name="select3" id="mp2"
														onChange="javascript:cambiarMaxPag(this.form.select3.value)">
															<option value="0">
																Ver
															</option>
															<option value="10">
																10
															</option>
															<option value="15">
																15
															</option>
															<option value="20">
																20
															</option>
															<option value="25">
																25
															</option>
														</select>
													</form>
												</div>
												<div class="tbcp">
													<span style="white-space: nowrap;"> <b id="primero">
															Primero </b><b id="anterior"> &lt; </b><b id="start2"> </b>&nbsp;-&nbsp;<b
														id="end2"> </b>&nbsp;de&nbsp;<b id="all2"> </b><b
														id="siguiente"> &gt; </b> <b id="ultimo"></b> </span>
												</div>
												<div class="selec">
													Seleccionar:
													<span id="sl_a" class="l" onClick="todos()">Todos</span>,&nbsp;
													<span id="sl_n" class="l" onClick="ninguna()">Ninguno</span>,&nbsp;
													<span id="sl_t" class="l" onClick="marcarNoLeidos()">No
														leidos</span>,&nbsp;
													<span id="sl_t" class="l" onClick="marcarLeidos()">Leidos</span>,&nbsp;
													<span id="sl_t" class="l" onClick="marcarDestacados()">Destacados</span>,&nbsp;
													<span id="sl_t" class="l" onClick="marcarNoDestacados()">No
														destacados</span>
												</div>
											</div>
										</div>
									</div>
									<%--fin del pie--%>



									<%--redactar mensaje--%>
									<form enctype="multipart/form-data"
										onSubmit="return enviarMensaje()"
										action="<%=request.getContextPath()%>/comun/buzon/EnviarMensaje.action"
										method="post">
										<div id="redac" style="display: hidden;">
											<table width="100%" border="0" class="thc">
												<tbody>
													<tr>
														<td width="120" style="padding-left: 10px;">
															<input type="button" onClick="abrirDirectorio()"
																autocomplete="off" name="para" id="para" value="Para:"
																class="form_button">
														</td>
														<td>
															<input type="text" name="destino" id="destino" size="70"
																 autocomplete="off" />
														</td>
													</tr>
													<tr>
														<td style="padding-left: 10px;">
															<input type="button" onClick="abrirDirectorio()"
																name="ccbutton" id="ccbutton" value="CC:"
																class="form_button">
														</td>
														<td>
															<input type="text" name="cc" id="cc" size="70"
																autocomplete="off" />
														</td>
													</tr>
													<tr>
														<td style="padding-left: 10px;" class="texto">
															Asunto:
														</td>
														<td>
															<input name="titulo" type="text" id="titulo" size="70"
																autocomplete="off" maxlength="100">
														</td>
													</tr>
													<tr>
														<td style="padding-left: 10px;" class="texto">
															Adjunto:
														</td>
														<td class="texto">
															<input name="doc" type="file" id="doc" size="40">
															M&aacute;ximo 5 MiB.
														</td>
													</tr>
													<tr>
														<td colspan="2" width="546" style="background: white;"
															id="elTexto" />
													</tr>
													<tr>
														<td colspan="2">
															<table width="100%" border="0" height="32">
																<tr>
																	<td>
																		&nbsp;
																	</td>
																	<td width="300">&nbsp;
																		

																	</td>
																	<td width="24" align="right">
																		<input type="submit" align="rigth" class="form_button"
																			name="Submit" id="enviar" value="Enviar">
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</tbody>
											</table>
										</div>
									</form>
									<%--fin redactar mensaje--%>


									<%--el mensaje--%>
									<div id="elMensaje" style="display: hidden; min-height: 410px;width: 100%">
										<table class="mensaje" width="100%" style="min-height: 410px">
											<tr align="right">
												<td width="14" id="ant" />
												<td width="269" align="left" id="tit" class="texto">
												</td>
												<td width="143" align="right" id="moveTo" class="texto">												
													<form name="form3">
														<select name="select3" id="bam2"
															onChange="javascript:moveInside(this.form.select3.value)"
															style="vertical-align: middle; width: 140px">
															<option id="mac" style="color: rgb(119, 119, 119);">
																Mover a carpeta...
															</option>
															<option id="" value="R">
																Recibidos
															</option>
															<option id="nl" value="P">
																Papelera
															</option>
															<optgroup label="Carpetas personales:">
																<c:forEach items="${CARPETAS}" var="carpeta">
																	<option value="<c:out value="${carpeta.idCarpeta}"/>">
																		<c:out value="${carpeta.nombre}" />
																	</option>
																</c:forEach>
															</optgroup>
														</select>
													</form>												
												</td>
												<td width="30" id="sig0" valign="top" align="right">												
													<input id="ac_dd4" onClick="window.print()" type="button"
														value="Imprimir" class="form_button">
												</td>
												<td width="30" id="sig" valign="top" align="right">
													<input id="ac_dd4" onClick="eliminarMensajeIn()"
														type="button" value="Eliminar" class="form_button">
												</td>
												
											</tr>
											<tr>
												<td colspan="5" id="det" />
											</tr>
											<tr>
												<td colspan="3">
													<table id="detallesMensaje" style="display: none">
														<tr>
															<td id="de"></td>
														</tr>
														<tr>
															<td id="to"></td>
														</tr>
														<tr>
															<td id="tocc"></td>
														</tr>
														<tr>
															<td id="fecha"></td>
														</tr>
														<tr>
															<td id="asunto"></td>
														</tr>
													</table>
												</td>
											</tr>

											<tr height="300px">
												<td colspan="5" id="cuerpo"
													style="vertical-align: top; background-color: white; padding-left: 2px; padding-right: 2px;" />
											</tr>
											<tr>
												<td colspan="5" id="acciones">
													<input class="form_button" type="button" value="Responder"
														onClick="reply()">
													<input class="form_button" id="replyAll" type="button"
														value="Responder a todos" onClick="replyAll()">
													<input class="form_button" id="replyAll" type="button"
														value="Reenviar" onClick="reenviar()">
												</td>
											</tr>
										</table>
									</div>
									<%--fin leer mensaje--%>
								</div>
							</td>
						</tr>
						<tr>
							<td id="footerBlanks"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<div id="coincidencias">
		</div>
	</body>
</html>

