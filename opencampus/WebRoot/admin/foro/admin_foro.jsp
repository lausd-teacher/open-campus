<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%@  page import="edu.tecsup.lms.modelo.Usuario"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<c:set var="contextPath" value='${pageContext.request.contextPath}' />
		<title><s:text name="titulo.campus.virtual" />
		</title>

		<link href="<c:out value='${contextPath}'/>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jPrototype.js"></script>
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/InnerDiv.js"></script>
		<script language="javascript" type="text/javascript"
			src='<c:out value='${contextPath}'/>/admin/foro/admin_foro.js'></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>

	</head>
	<body>

		<div id="contenedor">

			<%@include file="../../comun/bienvenida.jsp"%>

			<!-- *************** CONTENIDO ********************* -->
			<div id="loading" style="display: none;">
				***** demo *****
			</div>
			<div id="cuerpo">
				<div id="pop_cuerpo">

					<form>
						<table width="950" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="foro_fon_tit_iz" width="3">
									&nbsp;
								</td>
								<td class="foro_fon_tit" width="100" height="21">
									Nuevo Foro
								</td>
								<td class="foro_fon_tit_de" width="20">
									&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
						<table width="950" border="0" align="center" cellpadding="3"
							cellspacing="0" class="bor_tabla" style="border-bottom: 0px;">
							<tr class="fon_color03" height="23">
								<td align="left" class="moduloAbajo1"
									style="padding-left: 10px;">
									<input type="button" value="Nuevo foro" class="form_button"
										onClick="mostrarNuevo();" />
								<td align="right" class="moduloAbajo1">
									<input id="close_form_nuevo" type="button" value="X"
										class="form_button" style="width: 24px; display: none;"
										onClick="xHideD('form_nuevo'); xHideD('close_form_nuevo');" />
								</td>
							</tr>
						</table>
					</form>

					<div id="form_nuevo" style="display: none;">

						<div id="divMod">
							<form id="formNuevo" onSubmit="return crearForo(this)"
								method="post"
								action="<c:out value='${contextPath}'/>/admin/foro/Crear.action">
								<table width="950" border="0" align="center" cellpadding="3"
									cellspacing="0" class="bor_tabla" style="table-layout: fixed;">
									<tr height="32">
										<td width="75" style="padding-left: 10px;">
											<strong>Titulo :</strong>
											<input type="hidden" name="idForo" value="">
										</td>
										<td width="350">
											<input name="titular" type="text" class="form_text" value=""
												maxlength="64" size="68">
										</td>
										<td width="75" style="padding-left: 10px;">
											<strong>Descripci&oacute;n :</strong>
										</td>
										<td width="350">
											<input name="descripcion" type="text" class="form_text"
												value="" maxlength="100" size="68">
										</td>
									</tr>

									<tr>
										<td colspan="4">
											<table width="100%" border="0" cellpadding="6"
												cellspacing="0">
												<tr>
													<td width="300"
														style="padding-left: 5px; background-color: #F9F9F9;"
														align="center">
														<strong>Dirigido a:</strong>
													</td>
													<td width="280"
														style="padding-left: 5px; background-color: #F9F9F9;"
														align="center" class="tabla01_border_left">
														<strong>Potencial moderador:</strong>
													</td>
													<td style="padding-left: 5px; background-color: #F9F9F9;"
														align="center">

													</td>
													<td width="280"
														style="padding-left: 5px; background-color: #F9F9F9;"
														align="center">
														<strong>Moderador:</strong>
													</td>
												</tr>

												<tr>
													<td style="padding-left: 5px;">
														<select class="form_text" id="s_sede"
															onchange="changeSede(this)">
															<option value="0">
																Todos
															</option>
															<c:forEach items="${SEDES}" var="sede">
																<option value="<c:out value="${sede.codigo}"/>">
																	<c:out value="${sede.nombre}" />
																</option>
															</c:forEach>
														</select>

														<select class="form_text" id="s_familia"
															onchange="changeFamilia(this)"
															style="visibility: hidden;">
															<option value="0">
																Todos
															</option>
															<c:forEach items="${FAMILIAS}" var="familia">
																<option value="<c:out value="${familia.codigo}"/>">
																	<c:out value="${familia.nombre}" />
																</option>
															</c:forEach>
														</select>

														<select class="form_text" id="s_formacion"
															onchange="changeFormacion(this)"
															style="visibility: hidden;">
															<option value="0">
																Todos
															</option>
															<c:forEach items="${FORMACIONES}" var="formacion">
																<option value="<c:out value="${formacion.codigo}"/>">
																	<c:out value="${formacion.nombre}" />
																</option>
															</c:forEach>
														</select>

														<select class="form_text" id="s_ciclo"
															onchange="changeCiclo(this)" style="visibility: hidden;">
															<option value="0">
																*
															</option>
															<c:forEach begin="1" end="8" step="1" var="ciclo">
																<option value="<c:out value="${ciclo}"/>">
																	<c:out value="${ciclo}" />
																</option>
															</c:forEach>
														</select>

														<select class="form_text" id="s_seccion"
															style="visibility: hidden;">
															<option value="0">
																*
															</option>
															<c:forEach items="${SECCIONES}" var="seccion">
																<option value="<c:out value="${seccion.codigo}"/>">
																	<c:out value="${seccion.nombre}" />
																</option>
															</c:forEach>
														</select>

													</td>

													<td rowspan="4" class="tabla01_border_left">
														<select size="11" id="SelectPotenciales"
															multiple="multiple" style="width: 100%;"
															ondblclick="agregarModerador()">
														</select>
													</td>

													<td rowspan="4" align="center">
														<input type="button" value=" >> " class="form_button"
															onClick="agregarModerador()" />
														<br />
														&nbsp;
														<br />
														<input type="button" value=" << " class="form_button"
															onClick="quitarModerador()" />
													</td>

													<td rowspan="4">
														<select size="11" id="SelectModeradores"
															multiple="multiple" style="width: 100%;"
															ondblclick="quitarModerador()">
														</select>
														<input type="hidden" name="moderadores" value="">
													</td>

												</tr>

												<tr>
													<td style="padding-left: 5px;">
														<input type="button" value="Agregar Regla"
															class="form_button" onclick="agregarRegla()"
															style="width: 100%;" />
													</td>
												</tr>

												<tr>
													<td style="padding-left: 5px;">
														<select size="5" id="SelectGrupos" multiple="multiple"
															style="width: 100%;" ondblclick="quitarRegla()">
														</select>
														<input type="hidden" name="reglas" value="">
													</td>
												</tr>

												<tr>
													<td style="padding-left: 5px;">
														<input type="button" value="Quitar Regla"
															class="form_button" onclick="quitarRegla()"
															style="width: 100%;" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td colspan="4" align="center" class="tabla01_border_top"
											style="padding-left: 5px; background-color: #F9F9F9;">
											<strong>Icono del foro</strong>
										</td>
									</tr>
									<tr>
										<td colspan="4" class="tabla01_border_top" height="70">
											<div style="height: 100%; overflow: auto;">
												<table border="0" cellpadding="0" cellspacing="0"
													style="table-layout: fixed;" width="100%">
													<tr>
														<c:forEach var="i" begin="1" end="145">
															<td id="i_<c:out value="${i}"/>" height="50" width="50"
																style="cursor: pointer; border: 1px solid #E5EFF8;"
																onclick="selectIcon(this,'<c:out value="${i}"/>')"
																align="center" valign="middle"
																onmouseover="seleccionIcon(this,true)"
																onmouseout="seleccionIcon(this,false)">
																<img
																	src="<%=request.getContextPath()%>/img/foro/iconos_foros/foro_img_<c:out value="${i}"/>.png"
																	alt="i<c:out value="${i}"/>" />
															</td>
														</c:forEach>
														<td>
														</td>
													</tr>
												</table>
											</div>
											<input type="hidden" id="foro_icono" name="icono" value="">
										</td>
									</tr>
									<tr>
										<td colspan="4" align="center" class="tabla01_border_top"
											height="30">
											<input type="submit" value="Guardar" class="form_button"
												style="width: 100px;">
											&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="reset" value="Cancelar" class="form_button"
												style="width: 100px;" onClick="cerrarNuevo()">
										</td>
									</tr>
								</table>
							</form>
						</div>

					</div>



					<br />

					<table width="950" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td class="foro_fon_tit_iz" width="3">
								&nbsp;
							</td>
							<td class="foro_fon_tit" width="100" height="21">
								Foro
							</td>
							<td class="foro_fon_tit_de" width="20">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>

					<table width="950" border="0" align="center" cellpadding="3"
						cellspacing="0" class="tabla01" style="table-layout: fixed;">
						<tr class="fon_color01">
							<td width="25" align="center" class="foro_Modulo">
								&nbsp;
							</td>
							<td width="25" align="center" class="foro_Modulo">
								&nbsp;
							</td>
							<td height="23" align="center" class="foro_Modulo">
								<strong>Foros</strong>
							</td>
							<td width="125" align="center" class="foro_Modulo">
								<strong>Moderador(es)</strong>
							</td>
							<td width="50" align="center" class="foro_Modulo">
								<strong>Temas</strong>
							</td>
							<td width="75" align="center" class="foro_Modulo">
								<strong>Mensajes</strong>
							</td>
							<td width="25" align="center" class="foro_Modulo">
								<strong></strong>
							</td>
							<td width="25" align="center" class="foro_ModuloAbajo1">
								<strong></strong>
							</td>
						</tr>

						<c:forEach items="${foros}" var="foro" varStatus="fila">

							<tr onmouseover="seleccion(this, true);"
								onmouseout="seleccion(this, false);">
								<td align="center" class="foro_bor_der_unid_col">
									<c:choose>
										<c:when test="${foro.estado == 1}">
											<img src="<%=request.getContextPath()%>/img/activo.gif"
												style="cursor: pointer;"
												onclick="cambiarEstado(this,'<c:out value="${foro.idForo}"/>')" />
										</c:when>
										<c:otherwise>
											<img src="<%=request.getContextPath()%>/img/desactivo.gif"
												style="cursor: pointer;"
												onclick="cambiarEstado(this,'<c:out value="${foro.idForo}"/>')" />
										</c:otherwise>
									</c:choose>
									<input type="hidden"
										id="estado_<c:out value="${foro.idForo}"/>"
										value="<c:out value="${foro.estado}"/>">
								</td>
								<td align="center" class="foro_bor_der_unid_col">
									<c:choose>
										<c:when test="${foro.cerrado == 0}">
											<img
												src="<%=request.getContextPath()%>/img/icon_candado_abierto_out.jpg"
												style="cursor: pointer;"
												onclick="cambiarCerrado(this,'<c:out value="${foro.idForo}"/>')" />
										</c:when>
										<c:otherwise>
											<img
												src="<%=request.getContextPath()%>/img/icon_candado.jpeg"
												style="cursor: pointer;"
												onclick="cambiarCerrado(this,'<c:out value="${foro.idForo}"/>')" />
										</c:otherwise>
									</c:choose>
									<input type="hidden"
										id="cerrado_<c:out value="${foro.idForo}"/>"
										value="<c:out value="${foro.cerrado}"/>">
								</td>
								<td align="center" class="foro_bor_der_unid_col">
									<table width="100%" border="0" cellpadding="3" cellspacing="0"
										style="table-layout: fixed;">
										<tr>
											<td rowspan="2" valign="top" width="50">
												<a target="_blank"
													href="<%=request.getContextPath()%>/foro/IngresarForo.action?idForo=<c:out value="${foro.idForo}"/>"
													onmouseover="seleccionOverIcon(this,true)"
													onmouseout="seleccionOverIcon(this,false)"> <img
														src="<%=request.getContextPath()%>/img/foro/iconos_foros/foro_img_<c:out value="${foro.icono}"/>.png"
														border="0" /> </a>
											</td>
											<td style="padding-left: 6px;" align="left">
												<a target="_blank"
													href="<%=request.getContextPath()%>/foro/IngresarForo.action?idForo=<c:out value="${foro.idForo}"/>"
													onmouseover="seleccionOverIcon(this,true)"
													onmouseout="seleccionOverIcon(this,false)" style="color: black;"> <strong><c:out
															value="${foro.titulo}" />
												</strong> </a>
											</td>
										</tr>
										<tr>
											<td style="padding-left: 6px;" align="left">
												<c:out value="${foro.descripcion}" />
											</td>
										</tr>
									</table>
								</td>
								<td align="center" class="foro_bor_der_unid_col">
									<div style="overflow: auto; max-height: 50px;">
										<c:forEach items="${foro.moderadores}" var="moderador"
											varStatus="fila">
											<span style="color: black;"> <c:out
													value="${moderador.usuarioDato.nombreApellidoCorto}" /> </span>
											<br />
										</c:forEach>
										<c:set var="sizeMod" value="${fn:length(foro.moderadores)}"></c:set>
										<c:if test="${sizeMod == 0}">
											<b><i>Sin moderador</i>
											</b>
										</c:if>
									</div>
								</td>
								<td align="center" class="foro_bor_der_unid_col">
									<c:out value="${foro.totalTemas}" />
								</td>
								<td align="center" class="foro_bor_der_unid_col">
									<c:out value="${foro.totalMensajes}" />
								</td>
								<td align="center" class="foro_bor_der_unid_col">
									<img src="<%=request.getContextPath()%>/img/icon_trab.gif"
										border="0" alt="Modificar" style="cursor: pointer;"
										onclick="mostrarModificar('<c:out value="${foro.idForo}"/>')" />
								</td>
								<td align="center" class="foro_bor_der_cur_fe">
									<img src="<%=request.getContextPath()%>/img/icon_trash.gif"
										border="0" alt="Eliminar" style="cursor: pointer;"
										onclick="eliminar('<c:out value="${foro.idForo}"/>')" />
								</td>
							</tr>

							<tr id="TR_<c:out value="${foro.idForo}"/>"
								style="display: none;">
								<td colspan="7" align="center">
									<div id="DIV_<c:out value="${foro.idForo}"/>">
									</div>
								</td>
							</tr>

						</c:forEach>

					</table>

				</div>
			</div>
			<div id="pie">
				<%@include file="../../comun/pie.jsp"%>
			</div>

			<!-- *************** FIN CONTENIDO ********************* -->

		</div>
	</body>
</html>
