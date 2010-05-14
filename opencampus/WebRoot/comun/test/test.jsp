<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}' />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<script LANGUAGE="JavaScript"><!--
	function do_err(){return true}onerror=do_err;function no_cp(){clipboardData.clearData();setTimeout("no_cp()",100);}
	//no_cp();
	//--></SCRIPT>
	<head>
	<head>
		<NOSCRIPT>
		Su navegador no soporta Javascript, puede habilitarlo y refrescar la p&aacute;gina.
		<META HTTP-EQUIV="refresh" content="5;URL=<%=request.getContextPath()%>/error_recurso.jsp">
		</NOSCRIPT>
		<link href="noprint.txt" rel="alternate" media="print">
		<STYLE media="print">
			BODY {display:none}
		</STYLE>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title><s:text name="titulo.campus.virtual" />
		</title>
		<link href="<c:out value='${contextPath}'/>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/comun/test/test.js"></script>
		<script type='text/javascript'
			src='<%=request.getContextPath()%>/dwr/engine.js'> </script>		
		<script type='text/javascript'
			src='<%=request.getContextPath()%>/dwr/interface/UsuariosConectados.js'> </script>
	</head>
	<body onload="deshabilitarBotonDerecho(); deshabilitarCopiarPegar(); initTest();" 
		onunload="try{UsuariosConectados.activarChat('<c:out value="${usuario_actual.id}"/>');}catch(e){}; if(opener.hideBlock)opener.hideBlock(); cerrarTest(); this.exit=1; window.close();"
		onbeforeunload="if(this.exit!==1) return 'Si cierra la ventana será calificado con el puntaje obtenido hasta el momento.';">
		<div id="pop_up" style="width: 640px; height: 100%">
			<div id="prin_01" style="width: 100%;">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="93%">
							<strong>Curso : <c:out value="${usuario_actual.aulaActual.curso.nombre}"/> </strong>
						</td>
						<td width="5%">

						</td>
						<td width="3%">

						</td>
						<td width="2%">

						</td>
						<td width="4%">
							<a href="#" class="salir" onClick="window.close()">Cerrar</a>
						</td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.close()"> <img
									src="<c:out value='${contextPath}'/>/img/salir_x.gif" width="13"
									height="13" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>
			<div id="pop_cuerpo" style="width: 96%; height: 100%">
			
			<!-- *********** Tabla Test ************** -->
			<table id="table_test" width="100%" align="center" cellpadding="0" cellspacing="0" class="tabla01" border="0">
					<tr class="fon_tit_curso">
						<td height="20" colspan="2" class="tit_cab">
							Evaluación : <c:out value="${usuario_actual.aulaActual.testActual.nombreUnidad}"/></td>
					</tr>
					<tr>
						<td width="20%" valign="top">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<c:forEach items="${usuario_actual.aulaActual.testActual.tests}" var="test" varStatus="status">
									<tr style="cursor: pointer;" onmouseover="seleccion(this,true)" onmouseout="seleccion(this,false)"
										onclick="selectForm(<c:out value="${status.count}" />)" id="tr_<c:out value="${status.count}" />">
										<td height="20" style="font-weight: bold; padding-left: 15px;">
											Pregunta <c:if test="${status.count!=10}">0</c:if><c:out value="${status.count}" />
										</td>
										<td width="20" align="left" style="padding-left: 3px;">
											<img id="flag_<c:out value="${status.count}" />" src="<c:out value='${contextPath}'/>/img/bandera.gif" width="8" height="11" />
										</td>
									</tr>
								</c:forEach>
							</table>
						</td>
						<td width="80%" valign="top" class="tabla_test"  style="border-top:none;">
							<c:forEach items="${usuario_actual.aulaActual.testActual.tests}" var="test" varStatus="status">
								<form id="form_<c:out value="${status.count}" />" name="form_<c:out value="${status.count}" />" onsubmit="return false;" onclick="" style="<c:if test="${status.count != 1}">display: none;</c:if>">
									<table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
										<tr>
											<td height="19" class="fon_color01" style="font-weight: bold; padding-left: 5px;color:#cccccc;" align="left" valign="middle">
												<c:choose>
													<c:when test="${test.tipo == 1}">
														Seleccione una sola alternativa:
													</c:when>
													<c:when test="${test.tipo == 2}">
														Seleccione una o m&aacute;s alternativas:
													</c:when>
													<c:when test="${test.tipo == 3}">
														Indique verdadero o falso la siguiente afirmaci&oacute;n:
													</c:when>
													<c:when test="${test.tipo == 4}">
														Relacione las alternativas con los enunciados:
													</c:when>
													<c:when test="${test.tipo == 5}">
														Escriba la respuesta en los espacios en blanco:
													</c:when>
													<c:when test="${test.tipo == 6}">
														Ordene del 1 a 4 las siguientes alternativas:
													</c:when>
												</c:choose>
											</td>
											<td width="16" class="fon_color01" style="font-weight: bold; padding-left: 5px;cursor: pointer;" align="left" valign="middle">
												<c:if test="${test.grafico==1}">
													<img src="<c:out value='${contextPath}'/>/img/ver_imagen.gif" alt="Ver imagen"
														onclick="verImagen('<c:out value='${contextPath}'/>/aulavirtual/test/VerImagen.action?idUnidad=<c:out value="${test.idUnidad}"/>&imagen=<c:out value="${test.archivoNombre}"/>','<c:out value="${test.archivoTamanio}"/>',this);">
												</c:if>
											</td>
											<td width="70" class="fon_color01" style="font-weight: bold; padding-left: 5px;cursor: pointer; color:#6786AB;" align="left" valign="middle">
												<c:if test="${test.grafico==1}">
													<span onclick="verImagen('<c:out value='${contextPath}'/>/aulavirtual/test/VerImagen.action?idUnidad=<c:out value="${test.idUnidad}"/>&imagen=<c:out value="${test.archivoNombre}"/>','<c:out value="${test.archivoTamanio}"/>',this);"><b>Ver Imagen</b></span>
												</c:if>
											</td>
										</tr>
										<tr>
											<td colspan="3" style="xfont-weight: bold; padding: 5px; text-align: justify;">
												<c:out value="${test.pregunta}" escapeXml="false"/>
											</td>
										</tr>
										<tr>
											<td colspan="3" style="padding: 0px;" valign="top">
												<input type="hidden" name="tipo" value="<c:out value="${test.tipo}" />">
												<input type="hidden" name="id" value="<c:out value="${test.idTest}" />">
												<c:choose>
													<c:when test="${test.tipo == 1}">
												<!-- *********************************** SIMPLE *********************************** -->
														<table border="0" width="100%" cellpadding="5" cellspacing="0" style="table-layout: fixed;"/>
															<tr>
																<td width="16" valign="top">
																	<input type="radio" name="a1" value="A">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.A.texto}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top">
																	<input type="radio" name="a1" value="B">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.B.texto}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top">
																	<input type="radio" name="a1" value="C">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.C.texto}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top">
																	<input type="radio" name="a1" value="D">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.D.texto}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top">
																	<input type="radio" name="a1" value="E">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.E.texto}"/>
																</td>
															</tr>
														</table>
													</c:when>
													<c:when test="${test.tipo == 2}">
													<!-- *********************************** MULTIPLE *********************************** -->
														<table border="0" width="100%" cellpadding="5" cellspacing="0" style="table-layout: fixed;"/>
															<tr>
																<td width="16" valign="top">
																	<input type="checkbox" name="a1" value="A">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.A.textoAux}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top">
																	<input type="checkbox" name="a2" value="B">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.B.textoAux}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top">
																	<input type="checkbox" name="a3" value="C">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.C.textoAux}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top">
																	<input type="checkbox" name="a4" value="D">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.D.textoAux}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top">
																	<input type="checkbox" name="a5" value="E">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.E.textoAux}"/>
																</td>
															</tr>
														</table>
													</c:when>
													<c:when test="${test.tipo == 3}">
													<!-- *********************************** V / F *********************************** -->
														<table border="0" width="100%" cellpadding="5" cellspacing="0" style="table-layout: fixed;"/>
															<tr>
																<td width="16" valign="top">
																	<input type="radio" name="a1" value="1">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	Verdadero
																</td>
															</tr>
															<tr>
																<td width="16" valign="top">
																	<input type="radio" name="a1" value="0">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	Falso
																</td>
															</tr>
														</table>
													</c:when>
													<c:when test="${test.tipo == 4}">
													<!-- *********************************** RELACION *********************************** -->
														<table border="0" width="100%" cellpadding="5" cellspacing="0" style="table-layout: fixed;"/>
															<tr>
																<td width="16" valign="top" style="padding-left: 10px;">
																	<strong>A</strong>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.A.texto}"/>
																</td>
																<td width="30" valign="top">
																	<input type="text" name="a1" size="1" maxlength="1" onkeyup="checkAB(this)" onblur="checkAB(this)">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.A.textoAux}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top" style="padding-left: 10px;">
																	<strong>B</strong>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.B.texto}"/>
																</td>
																<td width="30" valign="top">
																	<input type="text" name="a2" size="1" maxlength="1" onkeyup="checkAB(this)" onblur="checkAB(this)">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.B.textoAux}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top" style="padding-left: 10px;">
																	<strong>C</strong>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.C.texto}"/>
																</td>
																<td width="30" valign="top">
																	<input type="text" name="a3" size="1" maxlength="1" onkeyup="checkAB(this)" onblur="checkAB(this)">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.C.textoAux}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top" style="padding-left: 10px;">
																	<strong>D</strong>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.D.texto}"/>
																</td>
																<td width="30" valign="top">
																	<input type="text" name="a4" size="1" maxlength="1" onkeyup="checkAB(this)" onblur="checkAB(this)">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.D.textoAux}"/>
																</td>
															</tr>
														</table>
													</c:when>
													<c:when test="${test.tipo == 5}">
													<!-- *********************************** COMPLETAR *********************************** -->
														
													</c:when>
													<c:when test="${test.tipo == 6}">
													<!-- *********************************** ORDENAR *********************************** -->
														<table border="0" width="100%" cellpadding="5" cellspacing="0" style="table-layout: fixed;"/>
															<tr>
																<td width="30" valign="top">
																	<input type="text" name="a1" size="1" maxlength="1" onkeyup="check12(this)" onblur="check12(this)">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.A.textoAux}"/>
																</td>
																
															</tr>
															<tr>
																<td width="30" valign="top">
																	<input type="text" name="a2" size="1" maxlength="1" onkeyup="check12(this)" onblur="check12(this)">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.B.textoAux}"/>
																</td>
																
															</tr>
															<tr>
																<td width="30" valign="top">
																	<input type="text" name="a3" size="1" maxlength="1" onkeyup="check12(this)" onblur="check12(this)">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.C.textoAux}"/>
																</td>
																
															</tr>
															<tr>
																<td width="30" valign="top">
																	<input type="text" name="a4" size="1" maxlength="1" onkeyup="check12(this)" onblur="check12(this)">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.D.textoAux}"/>
																</td>
																
															</tr>
														</table>
													</c:when>
												</c:choose>
											</td>
										</tr>
									</table>
								</form>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td class="tabla_test" align="center" valign="center" style="padding: 5px;">
							<table boredr=0 cellpadding="3" cellspacing="0">
								<tr><td><img src="<c:out value='${contextPath}'/>/img/chrono.gif">&nbsp;</td>
								<td><b><span id="timer" style="float: right;">00:00:00</span></b></td></tr>
							</table>
						</td>
						<td>
							<table border="0" width="100%">
								<tr>
									<td height="25">
										<button type="button" class="form_button" onclick="back();" id="back">
											Pregunta Anterior
										</button>
									</td>
									<td height="25">
										<button type="button" class="form_button" onclick="next();" id="next">
											Pregunta Siguiente
										</button>
									</td>
									<td height="25">
										<button type="button" class="form_button" onclick="validar(true)" id="btn_finish">
											Finalizar Evaluación
										</button>
									</td>
								</tr>
							</table>
						</td>
					</tr>

				</table>
				
				<!-- *********** Tabla Resumen ************** -->
				<table id="table_review" width="100%" border="0" cellpadding="0" cellspacing="0" class="tabla01" align="center" style="table-layout: fixed; display: none;">
					<tr class="fon_tit_curso">
					  <td height="20" colspan="2" class="tit_cab">Evaluación : <c:out value='${usuario_actual.aulaActual.testActual.nombreUnidad}'/></td></td>
					</tr>
					<tr>
						<td height="20" colspan="2" align="center">
							&nbsp;
							<br>
							<table width="95%" border="0" cellpadding="0" cellspacing="0">
								<tr class="fon_color01">
									<td height="20" colspan="4" align="left" class="moduloFondo4"
										style="font-weight: bold; padding-left: 3px;">
										Datos
									</td>
								</tr>
								<tr>
									<td height="20" colspan="2" align="left" class="modulo3"
										style="padding-left: 3px;">
										Apellidos y Nombres:
									</td>
									<td colspan="2" align="left" class="modulo"
										style="padding-left: 3px;">
										<c:out value="${usuario_actual.nombreCompleto}"></c:out>
									</td>
								</tr>
								<tr class="fon_color01">
									<td width="26%" height="20" align="left"
										style="padding-left: 3px;" class="moduloFondo3">
										Fecha y hora de inicio:
									</td>
									<td width="26%" align="left" style="padding-left: 3px;"
										class="textoAbajo">
										<span id="fecha1">&nbsp;</span>
									</td>
									<td width="24%" align="left" style="padding-left: 3px;"
										class="textoAbajo">
										Fecha y hora de fin: 
									</td>
									<td width="24%" align="left" style="padding-left: 3px;"
										class="textoAbajo">
										<span id="fecha2">&nbsp;</span>
									</td>
								</tr>
								<tr>
									<td height="20" align="left" style="padding-left: 3px;"
										class="modulo3">
										Tiempo empleado:
									</td>
									<td align="left" class="textoAbajo3" style="padding-left: 3px;">
										<span id="elapsed">&nbsp;</span>
									</td>
									<td align="left" style="padding-left: 3px;" class="textoAbajo3">
										Calificación :
									</td>
									<td align="left"
										style="font-size: 16px; color: #FF0000; font-weight: bold; border-bottom: 1px solid #cccccc; border-right: 1px solid #cccccc;">
										<span id="nota">&nbsp;</span>
									</td>
								</tr>
							</table>
							
							<c:forEach items="${usuario_actual.aulaActual.testActual.tests}" var="test" varStatus="status">
								<br/>
								<table width="95%" border="0" cellpadding="0" cellspacing="0"  class="tabla01">
									<tr class="fon_color01">
										<td height="20" align="left"
											style="padding-left: 3px; font-weight: bold;">
											<c:if test="${status.count!=10}">&nbsp;</c:if><c:out value="${status.count}" />.
										</td>
										<td height="20" align="left" style="padding-left: 3px;">
											<c:out value="${test.pregunta}" escapeXml="false"/>
										</td>
									</tr>
									<tr class="fon_color01">
										<td height="20" align="left" style="padding-left: 3px;">
											&nbsp;
										</td>
										<td height="20" align="left"
											style="padding-left: 3px; color: #0099FF; font-weight: bold;">
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr><td><span id="punto_<c:out value="${status.count}" />">Puntos : &nbsp;</span></td>
													<td width="16" class="fon_color01" style="font-weight: bold; padding-left: 5px;cursor: pointer;" align="left" valign="middle">
														<c:if test="${test.grafico==1}">
															<img src="<c:out value='${contextPath}'/>/img/ver_imagen.gif" alt="Ver imagen"
																onclick="verImagen('<c:out value='${contextPath}'/>/aulavirtual/test/VerImagen.action?idUnidad=<c:out value="${test.idUnidad}"/>&imagen=<c:out value="${test.archivoNombre}"/>','<c:out value="${test.archivoTamanio}"/>',this);">
														</c:if>
													</td>
													<td width="70" class="fon_color01" style="font-weight: bold; padding-left: 5px;cursor: pointer; color:#6786AB;" align="left" valign="middle">
														<c:if test="${test.grafico==1}">
															<span onclick="verImagen('<c:out value='${contextPath}'/>/aulavirtual/test/VerImagen.action?idUnidad=<c:out value="${test.idUnidad}"/>&imagen=<c:out value="${test.archivoNombre}"/>','<c:out value="${test.archivoTamanio}"/>',this);"><b>Ver Imagen</b></span>
														</c:if>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td width="6%" height="20" align="left"
											style="padding-left: 3px;">
											&nbsp;
										</td>
										<td width="94%" align="left" style="padding-left: 3px;">
											<form id="review_<c:out value="${status.count}" />" name="review_<c:out value="${status.count}" />" onsubmit="return false;">
												<c:choose>
													<c:when test="${test.tipo == 1}">
												<!-- *********************************** SIMPLE *********************************** -->
														<table border="0" width="100%" cellpadding="5" cellspacing="0" style="table-layout: fixed;"/>
															<tr>
																<td width="16" valign="top">
																	<input type="radio" name="a1" disabled>
																</td>
																<td width="16" valign="top">
																	<input type="radio" disabled <c:if test="${test.alternativas.A.respuesta == '1'}">checked</c:if>>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.A.texto}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top">
																	<input type="radio" name="a1" disabled>
																</td>
																<td width="16" valign="top">
																	<input type="radio" disabled <c:if test="${test.alternativas.B.respuesta == '1'}">checked</c:if>>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.B.texto}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top">
																	<input type="radio" name="a1" disabled>
																</td>
																<td width="16" valign="top">
																	<input type="radio" disabled <c:if test="${test.alternativas.C.respuesta == '1'}">checked</c:if>>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.C.texto}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top">
																	<input type="radio" name="a1" disabled>
																</td>
																<td width="16" valign="top">
																	<input type="radio" disabled <c:if test="${test.alternativas.D.respuesta == '1'}">checked</c:if>>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.D.texto}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top">
																	<input type="radio" name="a1" disabled>
																</td>
																<td width="16" valign="top">
																	<input type="radio" disabled <c:if test="${test.alternativas.E.respuesta == '1'}">checked</c:if>>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.E.texto}"/>
																</td>
															</tr>
															<tr>
																<td width="16" align="center" style="padding-left: 8px;">
																	<b>E</b>
																</td>
																<td width="16" align="center" style="padding-left: 8px;">
																	<b>S</b>
																</td>
																<td valign="top" align="right" style="font-weight: bold; color: ;">
																	E: Estudiante &nbsp; &nbsp; S: Sistema
																</td>
															</tr>
														</table>
													</c:when>
													<c:when test="${test.tipo == 2}">
													<!-- *********************************** MULTIPLE *********************************** -->
														<table border="0" width="100%" cellpadding="5" cellspacing="0" style="table-layout: fixed;"/>
															<tr>
																<td width="16" valign="top">
																	<input type="checkbox" name="a1" disabled>
																</td>
																<td width="16" valign="top">
																	<input type="checkbox" disabled <c:if test="${test.alternativas.A.respuesta == '1'}">checked</c:if>>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.A.textoAux}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top">
																	<input type="checkbox" name="a2" disabled>
																</td>
																<td width="16" valign="top">
																	<input type="checkbox" disabled <c:if test="${test.alternativas.B.respuesta == '1'}">checked</c:if>>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.B.textoAux}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top">
																	<input type="checkbox" name="a3" disabled>
																</td>
																<td width="16" valign="top">
																	<input type="checkbox" disabled <c:if test="${test.alternativas.C.respuesta == '1'}">checked</c:if>>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.C.textoAux}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top">
																	<input type="checkbox" name="a4" disabled>
																</td>
																<td width="16" valign="top">
																	<input type="checkbox" disabled <c:if test="${test.alternativas.D.respuesta == '1'}">checked</c:if>>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.D.textoAux}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top">
																	<input type="checkbox" name="a5" disabled>
																</td>
																<td width="16" valign="top">
																	<input type="checkbox" disabled <c:if test="${test.alternativas.E.respuesta == '1'}">checked</c:if>>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.E.textoAux}"/>
																</td>
															</tr>
															<tr>
																<td width="16" align="center" style="padding-left: 8px;">
																	<b>E</b>
																</td>
																<td width="16" align="center" style="padding-left: 8px;">
																	<b>S</b>
																</td>
																<td valign="top" align="right" style="font-weight: bold; color: ;">
																	E: Estudiante &nbsp; &nbsp; S: Sistema
																</td>
															</tr>
														</table>
													</c:when>
													<c:when test="${test.tipo == 3}">
													<!-- *********************************** V / F *********************************** -->
														<table border="0" width="100%" cellpadding="5" cellspacing="0" style="table-layout: fixed;"/>
															<tr>
																<td width="16" valign="top">
																	<input type="radio" name="a1" disabled>
																</td>
																<td width="16" valign="top">
																	<input type="radio" disabled <c:if test="${test.alternativas.A.respuesta == '1'}">checked</c:if>>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	Verdadero
																</td>
															</tr>
															<tr>
																<td width="16" valign="top">
																	<input type="radio" name="a1" disabled>
																</td>
																<td width="16" valign="top">
																	<input type="radio" disabled <c:if test="${test.alternativas.A.respuesta == '0'}">checked</c:if>>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	Falso
																</td>
															</tr>
															<tr>
																<td width="16" align="center" style="padding-left: 8px;">
																	<b>E</b>
																</td>
																<td width="16" align="center" style="padding-left: 8px;">
																	<b>S</b>
																</td>
																<td valign="top" align="right" style="font-weight: bold; color: ;">
																	E: Estudiante &nbsp; &nbsp; S: Sistema
																</td>
															</tr>
														</table>
													</c:when>
													<c:when test="${test.tipo == 4}">
													<!-- *********************************** RELACION *********************************** -->
														<table border="0" width="100%" cellpadding="5" cellspacing="0" style="table-layout: fixed;"/>
															<tr>
																<td width="16" valign="top" style="padding-left: 10px;">
																	<strong>A</strong>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.A.texto}"/>
																</td>
																<td width="30" valign="top">
																	<input type="text" size="1" name="a1" disabled>
																</td>
																<td width="30" valign="top">
																	<input type="text" size="1" disabled value="<c:out value="${test.alternativas.A.respuesta}"/>">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.A.textoAux}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top" style="padding-left: 10px;">
																	<strong>B</strong>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.B.texto}"/>
																</td>
																<td valign="top">
																	<input type="text" size="1" name="a2" disabled>
																</td>
																<td valign="top">
																	<input type="text" size="1" disabled value="<c:out value="${test.alternativas.B.respuesta}"/>">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.B.textoAux}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top" style="padding-left: 10px;">
																	<strong>C</strong>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.C.texto}"/>
																</td>
																<td valign="top">
																	<input type="text" size="1" name="a3" disabled>
																</td>
																<td valign="top">
																	<input type="text" size="1" disabled value="<c:out value="${test.alternativas.C.respuesta}"/>">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.C.textoAux}"/>
																</td>
															</tr>
															<tr>
																<td width="16" valign="top" style="padding-left: 10px;">
																	<strong>D</strong>
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.D.texto}"/>
																</td>
																<td valign="top">
																	<input type="text" size="1" name="a4" disabled>
																</td>
																<td valign="top">
																	<input type="text" size="1" disabled value="<c:out value="${test.alternativas.D.respuesta}"/>">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.D.textoAux}"/>
																</td>
															</tr>
															<tr>
																<td>
																	&nbsp;
																</td>
																<td>
																	&nbsp;
																</td>
																<td align="center" style="padding-left: 8px;">
																	<b>E</b>
																</td>
																<td align="center" style="padding-left: 6px;">
																	<b>S</b>
																</td>
																<td align="right" style="font-weight: bold; color: ;">
																	E: Estudiante &nbsp; &nbsp; S: Sistema
																</td>
															</tr>
														</table>
													</c:when>
													<c:when test="${test.tipo == 5}">
													<!-- *********************************** COMPLETAR *********************************** -->
													<table border="0" width="100%" cellpadding="5" cellspacing="0" style="table-layout: fixed;"/>
															<tr>
																<td width="20" valign="middle">
																	<b>E</b>
																</td>
																<td style="padding-top: 7px;">
																	<c:out value="${test.pregunta}" escapeXml="false"/>
																</td>
															</tr>
															<tr>
																<td width="20" valign="middle">
																	<b>S</b>
																</td>
																<td style="padding-top: 7px;">
																	<c:out value="${test.usuarioModificacion}" escapeXml="false"/>
																</td>
															</tr>
															<tr>
																<td>
																	&nbsp;
																</td>
																<td align="right" style="font-weight: bold; color: ;">
																	E: Estudiante &nbsp; &nbsp; S: Sistema
																</td>
															</tr>
														</table>
													</c:when>
													<c:when test="${test.tipo == 6}">
													<!-- *********************************** ORDENAR *********************************** -->
														<table border="0" width="100%" cellpadding="5" cellspacing="0" style="table-layout: fixed;"/>
															<tr>
																<td width="30" valign="top">
																	<input type="text" name="a1" size="1" disabled>
																</td>
																<td width="30" valign="top">
																	<input type="text" size="1" disabled value="<c:out value="${test.alternativas.A.respuesta}"/>">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.A.textoAux}"/>
																</td>
																
															</tr>
															<tr>
																<td width="30" valign="top">
																	<input type="text" name="a2" size="1" disabled>
																</td>
																<td width="30" valign="top">
																	<input type="text" size="1" disabled value="<c:out value="${test.alternativas.B.respuesta}"/>">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.B.textoAux}"/>
																</td>
																
															</tr>
															<tr>
																<td width="30" valign="top">
																	<input type="text" name="a3" size="1" disabled>
																</td>
																<td width="30" valign="top">
																	<input type="text" size="1" disabled value="<c:out value="${test.alternativas.C.respuesta}"/>">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.C.textoAux}"/>
																</td>
																
															</tr>
															<tr>
																<td width="30" valign="top">
																	<input type="text" name="a4" size="1" disabled>
																</td>
																<td width="30" valign="top">
																	<input type="text" size="1" disabled value="<c:out value="${test.alternativas.D.respuesta}"/>">
																</td>
																<td valign="top" style="padding-top: 7px;">
																	<c:out value="${test.alternativas.D.textoAux}"/>
																</td>
															</tr>
															<tr>
																<td align="center" style="padding-left: 8px;">
																	<b>E</b>
																</td>
																<td align="center" style="padding-left: 6px;">
																	<b>S</b>
																</td>
																<td align="right" style="font-weight: bold; color: ;">
																	E: Estudiante &nbsp; &nbsp; S: Sistema
																</td>
															</tr>
														</table>
													</c:when>
												</c:choose>
											</form>
										</td>
									</tr>
									<c:if test="${test.explicacion != null}">
										<tr>
											<td width="6%" height="20" align="left" style="padding-left: 3px; background-color: #ECECEC;">
												&nbsp;
											</td>
											<td width="94%" align="left" style="padding-left: 3px; background-color: #ECECEC;">
												<b>Explicaci&oacute;n: </b>&nbsp;<c:out value="${test.explicacion}"></c:out>
											</td>
										</tr>
									</c:if>
								</table>
							</c:forEach>

						</td>
					</tr>
					<tr>
						<td colspan="2" valign="top">&nbsp;</td>
					</tr>
				</table>
				
				<div id="msg" style="color: red;font-size: 10px;"></div>

			</div>
			<div id="pie">
				<%@include file="../pie.jsp"%>
			</div>
		</div>
		<div id="ampliacion" style="BORDER-RIGHT: #666666 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #666666 1px solid; 
			PADDING-LEFT: 2px; VISIBILITY: hidden; PADDING-BOTTOM: 2px; BORDER-LEFT: #666666 1px solid; PADDING-TOP: 2px; 
			BORDER-BOTTOM: #666666 1px solid; BACKGROUND-REPEAT: no-repeat; POSITION: absolute; WIDTH: 130px; HEIGHT: 54px; TOP: 100px; LEFT: 200px;"> 
			<span id="txtampliacion" style="POSITION: absolute; background-color:#ffffff;"></span> 
			<div id="loading">
				<img src="<c:out value='${contextPath}'/>/img/cargando.gif" border="0">
			</div> 
			<div id="cerrarampliacion" style="background-color:#333333; font-family:arial,verdana; font-size:8pt; line-height:20px; 
			text-align:right;float:right; height: 20px; WIDTH: 124px; padding-right:5px;"> 
			<span onclick="ocultarImagen();" style="color:#ffffff; cursor:pointer; text-decoration:underline;">[X] Cerrar</span> 
			</div> 
		</div>
	</body>
</html>