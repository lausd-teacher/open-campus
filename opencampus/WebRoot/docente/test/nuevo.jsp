<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<c:set var="aula" value='${sessionScope.aula_actual}' />
<c:set var="contextPath" value='${pageContext.request.contextPath}' />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" /></title>
		<link href="<c:out value='${contextPath}'/>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/docente/test/admin_test.js'></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jPrototype.js"></script>

		<script>
			var imagesDir = "<c:out value='${contextPath}'/>/js/openwysiwyg/icons/";
			var cssDir = "<c:out value='${contextPath}'/>/js/openwysiwyg/styles/";
			var popupsDir = "<c:out value='${contextPath}'/>/js/openwysiwyg/popups/";
		</script>
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/openwysiwyg/wysiwyg.js"></script>
	</head>
	<body>
		<div id="pop_up" style="width: 520px;">
			<div id="prin_01" style="width: 520px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="90%">
							<c:if test="${aula != null}">
								<strong>Curso : <c:out value="${aula.nombreCurso}"></c:out>
								</strong>
							</c:if>
						</td>
						<td width="5%"><a href="#" class="salir" onClick="window.print()">Imprimir</a> </td>
						<td width="3%"><a href="#" class="salir" onClick="window.print()"><img
									src="<%=request.getContextPath()%>/img/impresora.gif" width="13" height="13" border="0" /></a></td>
						<td width="2%">|</td>						
						<td width="5%">
							<a href="#" class="salir" onclick="window.close()">Cerrar</a>
						</td>
						<td width="5%">
							<a href="#" class="salir" onclick="window.close()"> <img
									src="<c:out value='${contextPath}'/>/img/salir_x.gif"
									width="13" height="13" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>
			<div id="pop_cuerpo" style="width: 500px;">
				<s:include value="/error_message.jsp"/>
				<c:set var="test" value="${TEST}"></c:set>
				<c:choose>
					<c:when test="${test == null}">
						<c:set var="action" value="/aulavirtual/test/Crear.action"></c:set>
						<c:set var="title" value="Nueva"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="action" value="/aulavirtual/test/Crear.action"></c:set>
						<c:set var="title" value="Modificar"></c:set>
					</c:otherwise>
				</c:choose>
				<form
					action="<c:out value='${contextPath}'/><c:out value='${action}'/>"
					method="post" enctype="multipart/form-data" onsubmit="return validar(this,'<c:out value="${tipo}"/>');">
					<input type="hidden" name="tipo" value="<c:out value="${tipo}"/>" />
					<input type="hidden" name="idTest" value="<c:out value="${test.idTest}"/>" />
					<table width="500" class="tabla01" border="0" cellspacing="0"
						cellpadding="3" style="table-layout: fixed;">
						<tr align="left" class="fon_cab">
							<th class="tabla01_encabezado" style="padding-left: 5px;">
								<c:out value='${title}'/> Pregunta (<c:out value="${NOMBRE_TIPO}"/>) :
								<c:out value="${NOMBRE_UNIDAD}"></c:out>
							</th>
						</tr>
						<tr>
							<td>
								<table width="100%" cellpadding="3" cellspacing="0" border="0">

									<%--************************* FORMULARIOS **************************--%>
									<f:Constante campo="TEST_NUM_ASIMPLE" var="tipo1" />
									<f:Constante campo="TEST_NUM_AMULTIPLE" var="tipo2" />
									<f:Constante campo="TEST_NUM_VF" var="tipo3" />
									<f:Constante campo="TEST_NUM_RELACIONAR" var="tipo4" />
									<f:Constante campo="TEST_NUM_COMPLETAR" var="tipo5" />
									<f:Constante campo="TEST_NUM_ORDENAR" var="tipo6" />
									<c:choose>
										<c:when test="${tipo==tipo1}">
											<tr>
												<td colspan="3">
													<strong>Pregunta : </strong>
												</td>
											</tr>
											<tr align="left">
												<td align="center" colspan="3">
													<textarea id="pregunta" name="pregunta" rows="4" cols="57" style="width: 99%;"><c:out value="${test.pregunta}"></c:out></textarea>
													<script language="JavaScript1.2" type="text/javascript">generate_wysiwyg('pregunta','484','150');</script>
												</td>
											</tr>
											<tr>
												<td colspan="3">
													<strong>Alternativas : </strong>
												</td>
											</tr>
											<tr>
												<td width="30" align="center">
													<input name="rpt1" type="radio" value="A" <c:if test="${test.alternativas.A.respuesta == '1'}">checked</c:if> />
												</td>
												<td width="20">
													A)
												</td>
												<td>
													<input name="alt1" type="text" class="form_text" size="80"
														maxlength="2048" value="<c:out value="${test.alternativas.A.texto}"/>"/>
												</td>
											</tr>
											<tr>
												<td align="center">
													<input name="rpt1" type="radio" value="B" <c:if test="${test.alternativas.B.respuesta == '1'}">checked</c:if> />
												</td>
												<td>
													B)
												</td>
												<td>
													<input name="alt2" type="text" class="form_text" size="80"
														maxlength="2048" value="<c:out value="${test.alternativas.B.texto}"/>"/>
												</td>
											</tr>
											<tr>
												<td align="center">
													<input name="rpt1" type="radio" value="C" <c:if test="${test.alternativas.C.respuesta == '1'}">checked</c:if> />
												</td>
												<td>
													C)
												</td>
												<td>
													<input name="alt3" type="text" class="form_text" size="80"
														maxlength="2048" value="<c:out value="${test.alternativas.C.texto}"/>"/>
												</td>
											</tr>
											<tr>
												<td align="center">
													<input name="rpt1" type="radio" value="D" <c:if test="${test.alternativas.D.respuesta == '1'}">checked</c:if> />
												</td>
												<td>
													D)
												</td>
												<td>
													<input name="alt4" type="text" class="form_text" size="80"
														maxlength="2048" value="<c:out value="${test.alternativas.D.texto}"/>"/>
												</td>
											</tr>
											<tr>
												<td align="center">
													<input name="rpt1" type="radio" value="E" <c:if test="${test.alternativas.E.respuesta == '1'}">checked</c:if> />
												</td>
												<td>
													E)
												</td>
												<td>
													<input name="alt5" type="text" class="form_text" size="80"
														maxlength="2048" value="<c:out value="${test.alternativas.E.texto}"/>"/>
												</td>
											</tr>
										</c:when>
										<c:when test="${tipo==tipo2}">
											<tr>
												<td colspan="3">
													<strong>Pregunta : </strong>
												</td>
											</tr>
											<tr align="left">
												<td align="center" colspan="3">
													<textarea id="pregunta" name="pregunta" rows="4" cols="57" style="width: 99%;"><c:out value="${test.pregunta}"></c:out></textarea>
													<script language="JavaScript1.2" type="text/javascript">generate_wysiwyg('pregunta','484','150');</script>
												</td>
											</tr>
											<tr>
												<td colspan="3">
													<strong>Alternativas : </strong>
												</td>
											</tr>
											<tr>
												<td width="30" align="center">
													<input name="rpt1" type="checkbox" value="1" <c:if test="${test.alternativas.A.respuesta == '1'}">checked</c:if> />
												</td>
												<td width="20">
													A)
												</td>
												<td>
													<input name="aux1" type="text" class="form_text" size="80"
														maxlength="2048" value="<c:out value="${test.alternativas.A.textoAux}"/>" />
												</td>
											</tr>
											<tr>
												<td align="center">
													<input name="rpt2" type="checkbox" value="1" <c:if test="${test.alternativas.B.respuesta == '1'}">checked</c:if> />
												</td>
												<td>
													B)
												</td>
												<td>
													<input name="aux2" type="text" class="form_text" size="80"
														maxlength="2048" value="<c:out value="${test.alternativas.B.textoAux}"/>" />
												</td>
											</tr>
											<tr>
												<td align="center">
													<input name="rpt3" type="checkbox" value="1" <c:if test="${test.alternativas.C.respuesta == '1'}">checked</c:if> />
												</td>
												<td>
													C)
												</td>
												<td>
													<input name="aux3" type="text" class="form_text" size="80"
														maxlength="2048" value="<c:out value="${test.alternativas.C.textoAux}"/>" />
												</td>
											</tr>
											<tr>
												<td align="center">
													<input name="rpt4" type="checkbox" value="1" <c:if test="${test.alternativas.D.respuesta == '1'}">checked</c:if> />
												</td>
												<td>
													D)
												</td>
												<td>
													<input name="aux4" type="text" class="form_text" size="80"
														maxlength="2048" value="<c:out value="${test.alternativas.D.textoAux}"/>" />
												</td>
											</tr>
											<tr>
												<td align="center">
													<input name="rpt5" type="checkbox" value="1" <c:if test="${test.alternativas.E.respuesta == '1'}">checked</c:if> />
												</td>
												<td>
													E)
												</td>
												<td>
													<input name="aux5" type="text" class="form_text" size="80"
														maxlength="2048" value="<c:out value="${test.alternativas.E.textoAux}"/>" />
												</td>
											</tr>
										</c:when>
										<c:when test="${tipo==tipo3}">
											<tr>
												<td colspan="2">
													<strong>Pregunta : </strong>
												</td>
											</tr>
											<tr align="left">
												<td align="center" colspan="2">
													<textarea id="pregunta" name="pregunta" rows="4" cols="57" style="width: 99%;"><c:out value="${test.pregunta}"></c:out></textarea>
													<script language="JavaScript1.2" type="text/javascript">generate_wysiwyg('pregunta','484','150');</script>
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<strong>Alternativas : </strong>
												</td>
											</tr>
											<tr>
												<td width="8%" align="center">
													<input name="rpt1" type="radio" value="1" <c:if test="${test.alternativas.A.respuesta == '1'}">checked</c:if> />
												</td>
												<td width="92%">
													Verdadero
												</td>
											</tr>
											<tr>
												<td width="8%" align="center">
													<input name="rpt1" type="radio" value="0" <c:if test="${test.alternativas.A.respuesta == '0'}">checked</c:if> />
												</td>
												<td width="92%">
													Falso
												</td>
											</tr>
										</c:when>
										<c:when test="${tipo==tipo4}">
											<tr>
												<td colspan="4">
													<strong>Pregunta : </strong>
												</td>
											</tr>
											<tr align="left">
												<td align="center" colspan="4">
													<textarea id="pregunta" name="pregunta" rows="4" cols="57" style="width: 99%;"><c:out value="${test.pregunta}" default="Relacione:"></c:out></textarea>
													<script language="JavaScript1.2" type="text/javascript">generate_wysiwyg('pregunta','484','150');</script>
												</td>
											</tr>
											<tr>
												<td colspan="4">
													<strong>Alternativas : </strong>
												</td>
											</tr>
											<tr>
								             <td width="30" align="center"> A)</td>
								             <td width="225" align="left"><input name="alt1" type="text" class="form_text" size="33" maxlength="2048" value="<c:out value="${test.alternativas.A.texto}"/>"/></td>
								             <td width="50" align="right"><input name="rpt1" type="text" class="form_text" size="1" maxlength="1" value="<c:out value="${test.alternativas.A.respuesta}"/>" onkeyup="checkAB(this)"/></td>
								             <td width="195" align="right"><input name="aux1" type="text" class="form_text" size="33" maxlength="2048" value="<c:out value="${test.alternativas.A.textoAux}"/>"/></td>
								           </tr>
								           <tr>
								             <td width="30" align="center"> B)</td>
								             <td width="225" align="left"><input name="alt2" type="text" class="form_text" size="33" maxlength="2048" value="<c:out value="${test.alternativas.B.texto}"/>"/></td>
								             <td width="50" align="right"><input name="rpt2" type="text" class="form_text" size="1" maxlength="1" value="<c:out value="${test.alternativas.B.respuesta}"/>" onkeyup="checkAB(this)"/></td>
								             <td width="195" align="right"><input name="aux2" type="text" class="form_text" size="33" maxlength="2048" value="<c:out value="${test.alternativas.B.textoAux}"/>"/></td>
								           </tr>
								           <tr>
								             <td width="30" align="center"> C)</td>
								             <td width="225" align="left"><input name="alt3" type="text" class="form_text" size="33" maxlength="2048" value="<c:out value="${test.alternativas.C.texto}"/>"/></td>
								             <td width="50" align="right"><input name="rpt3" type="text" class="form_text" size="1" maxlength="1" value="<c:out value="${test.alternativas.C.respuesta}"/>" onkeyup="checkAB(this)"/></td>
								             <td width="195" align="right"><input name="aux3" type="text" class="form_text" size="33" maxlength="2048" value="<c:out value="${test.alternativas.C.textoAux}"/>"/></td>
								           </tr>
								           <tr>
								             <td width="30" align="center"> D)</td>
								             <td width="225" align="left"><input name="alt4" type="text" class="form_text" size="33" maxlength="2048" value="<c:out value="${test.alternativas.D.texto}"/>"/></td>
								             <td width="50" align="right"><input name="rpt4" type="text" class="form_text" size="1" maxlength="1" value="<c:out value="${test.alternativas.D.respuesta}"/>" onkeyup="checkAB(this)"/></td>
								             <td width="195" align="right"><input name="aux4" type="text" class="form_text" size="33" maxlength="2048" value="<c:out value="${test.alternativas.D.textoAux}"/>"/></td>
								           </tr>
										</c:when>
										<c:when test="${tipo==tipo5}">
											<tr>
												<td colspan="1">
													<div><strong>Pregunta : </strong><br/></div>
													<div style="text-align: justify; height: 28px;">Debe ingresar como m&aacute;ximo 4 recuadros para completar.
													Cada recuadro debe contener como m&aacute;ximo una palabra, sin espacios vacios.</div>	
												</td>
											</tr>
											<tr align="left">
												<td align="center" colspan="1">
													<textarea id="pregunta" name="pregunta" rows="4" cols="57" style="width: 99%;"><c:out value="${test.pregunta}"></c:out></textarea>
													<script language="JavaScript1.2" type="text/javascript">generate_wysiwyg('pregunta','484','150');</script>
												</td>
											</tr>
										</c:when>
										<c:when test="${tipo==tipo6}">
											<tr>
												<td colspan="3">
													<strong>Pregunta : </strong>
												</td>
											</tr>
											<tr align="left">
												<td align="center" colspan="3">
													<textarea id="pregunta" name="pregunta" rows="4" cols="57" style="width: 99%;"><c:out value="${test.pregunta}" default="Ordene cronológicamente los siguientes sucesos del 1 al 4:"></c:out></textarea>
													<script language="JavaScript1.2" type="text/javascript">generate_wysiwyg('pregunta','484','150');</script>
												</td>
											</tr>
											<tr>
												<td colspan="3">
													<strong>Alternativas : </strong>
												</td>
											</tr>
											<tr>
								              <td width="30" align="center"> A)</td>
								              <td width="420"><input name="aux1" type="text" class="form_text" size="70" maxlength="2048" value="<c:out value="${test.alternativas.A.textoAux}"/>"/></td>
								              <td align="right" width="50"><input name="rpt1" type="text" class="form_text" size="1" maxlength="1" value="<c:out value="${test.alternativas.A.respuesta}"/>" onkeyup="check12(this)"/></td>
								              </tr>
								            <tr>
								              <td align="center">B)</td>
								              <td><input name="aux2" type="text" class="form_text" size="70" maxlength="2048" value="<c:out value="${test.alternativas.B.textoAux}"/>"/></td>
								              <td align="right"><input name="rpt2" type="text" class="form_text" size="1" maxlength="1" value="<c:out value="${test.alternativas.B.respuesta}"/>" onkeyup="check12(this)"/></td>
								              </tr>
								            <tr>
								              <td align="center">C)</td>
								              <td><input name="aux3" type="text" class="form_text" size="70" maxlength="2048" value="<c:out value="${test.alternativas.C.textoAux}"/>"/></td>
								              <td align="right"><input name="rpt3" type="text" class="form_text" size="1" maxlength="1" value="<c:out value="${test.alternativas.C.respuesta}"/>" onkeyup="check12(this)"/></td>
								              </tr>
								            <tr>
								              <td align="center">D)</td>
								              <td><input name="aux4" type="text" class="form_text" size="70" maxlength="2048" value="<c:out value="${test.alternativas.D.textoAux}"/>"/></td>
								              <td align="right"><input name="rpt4" type="text" class="form_text" size="1" maxlength="1" value="<c:out value="${test.alternativas.D.respuesta}"/>" onkeyup="check12(this)"></td>
								              </tr>
										</c:when>
									</c:choose>
									<%--********************* FIN FORMULARIOS **************************--%>

								</table>
							</td>
						</tr>
						<tr>
							<td height="10">
							</td>
						</tr>
						<tr>
							<td class="tabla01_border_top">
								<table width="100%" cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td width="83%" style="background-color: #E5EFF8;" align="right">
											<small>Haga click si desea agregar mayor detalle a la pregunta -> </small>
										</td>
										<td width="17%" valign="top" align="right" style="padding-right: 5px;" onclick="xChangeDisplay('TR_Img'); xChangeDisplay('TR_Exp');"
										 onmouseover="javascript:seleccionBlue(this,true);" style="cursor: pointer;" onmouseout="javascript:seleccionBlue(this,false);">
											<strong>D e t a l l e s</strong>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr id="TR_Img" style="display: none;">
							<td>
								<table width="100%" cellpadding="3" cellspacing="0" border="0">
									<tr>
										<td width="17%" valign="top">
											<strong>Imagen : </strong>
										</td>
										<td width="83%">
											<input name="file" type="file" class="form_text" size="58" onchange="if(!validarAdjunto(this.value)) this.value='';"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr id="TR_Exp" style="display: none;">
							<td>
								<table width="100%" cellpadding="3" cellspacing="0" border="0">
									<tr>
										<td width="17%" valign="top">
											<strong>Explicaci&oacute;n : </strong>
										</td>
										<td width="83%">
											<textarea name="explicacion" cols="60" rows="4" style="width: 394px;"
												class="form_text"><c:out value="${test.explicacion}"></c:out></textarea>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="tabla01_border_top">
								<table width="100%" cellpadding="0" cellspacing="0" height="30">
									<tr>
										<td align="center">
											<input type="button" class="form_button" value="Cancelar"
												style="width: 100px;"
												onclick="document.location.href='<c:out value='${contextPath}'/>/aulavirtual/test/Listar.action?idUnidad=<c:out value="${idUnidad}"></c:out>'" />
										</td>
										<td align="center">
											<input type="submit" class="form_button" value="Guardar"
												style="width: 100px;" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</form>

			</div>
			<div id="pie">
				<%@include file="../../comun/pie.jsp"%>
			</div>
		</div>
	</body>
</html>
