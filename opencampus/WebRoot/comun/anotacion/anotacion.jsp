<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:bean name="edu.opencampus.lms.util.Formato" id="fm_formato"></s:bean>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><s:text name="titulo.campus.virtual" /></title>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1" />
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<link
			href="<%=request.getContextPath()%>/comun/anotacion/anotacion.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jquery/jquery.lastest.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jquery/jquery.tooltip.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jquery/jquery.center.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jquery/jquery.dimensions.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jquery/jquery.autogrow.sako.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jquery/jquery.date.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jquery/jquery.template.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jquery/jquery.print.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jquery/jquery.autolineheight.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jquery/jquery.qem.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/comun/anotacion/anotacion.js"></script>
		<script>
			var nota_sin_titulo = '<s:text name="anotacion.sintitulo"></s:text>';
			var nota_mensaje_eliminar = '<s:text name="anotacion.nota.mensaje.eliminar"></s:text>';
			var nota_mensaje_txt_eliminado = '<s:text name="anotacion.nota.mensaje.txt.eliminado"></s:text>';
			var nota_mensaje_txt_creado = '<s:text name="anotacion.nota.mensaje.txt.creado"></s:text>';
			var nota_mensaje_txt_modificado = '<s:text name="anotacion.nota.mensaje.txt.modificado"></s:text>';
			var nota_mensaje_txt_modificado_error = '<s:text name="anotacion.nota.mensaje.txt.modificado_error"></s:text>';
			var nota_mensaje_txt_error = '<s:text name="anotacion.nota.mensaje.txt.error"></s:text>';
			var nota_escribir = '<s:text name="anotacion.escribir"></s:text>';
			var nota_nuevo_agregar='<s:text name="anotacion.nuevo.agregar"></s:text>';
			var nota_fecha_creada ='<s:text name="anotacion.fecha.creada"></s:text>';
			var nota_fecha_ultimo ='<s:text name="anotacion.fecha.ultimo"></s:text>';
			var nota_comentario='<s:text name="anotacion.comentario"></s:text>';
			var nota_imprimiendo='<s:text name="anotacion.imprimiendo"></s:text>';			
			var request_context = '<%=request.getContextPath()%>';
		</script>
	</head>
	<body>
		<div id="divError">
			<div id="div_txtError" style="display: none; z-index: 10000;">
				<table id="tabla_txtError" cellpadding="0" cellspacing="0"
					width="100" align="center" style="">
					<tr>
						<td width="4" height="4" id="td_txtError11"
							style="background-position: left top;">
						</td>
						<td height="4" id="td_txtError12">
						</td>
						<td width="4" align="left" id="td_txtError13"
							style="background-position: right top;">
						</td>
					</tr>
					<tr>
						<td id="td_txtError21" width="4">
						</td>
						<td id="td_txtError22" align="center">
							<div id="txtError">
							</div>
						</td>
						<td id="td_txtError23" width="4">
						</td>
					</tr>
					<tr>
						<td height="4" id="td_txtError31"
							style="background-position: left bottom;">
						</td>
						<td id="td_txtError32"></td>
						<td id="td_txtError33" style="background-position: right bottom;">
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div id="pop_up" style="width: 98%;">
			<div id="prin_01" style="width: 100%;">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td style="font-weight: bold;">
							<label style="font-weight: bold; padding-left: 5px;">
								<s:text name="portal.servicio.titulo.apunte"></s:text>
							</label>
						</td>
						<td width="32">
							<label class="imprimir_general salir s_tooltip cursor_mano"
								title="<s:text name="aula.alumno.pop_up.imprimir"></s:text>"
								onclick="$.jPrintArea();">
								<s:text name="aula.alumno.pop_up.imprimir"></s:text>
							</label>
						</td>
						<td width="15">
							<label class="imprimir_general salir cursor_mano"
								onclick="$.jPrintArea();">
								<img src="<%=request.getContextPath()%>/img/impresora.gif"
									width="13" height="13" border="0" class="s_tooltip"
									title="<s:text name="aula.alumno.pop_up.imprimir"></s:text>" />
							</label>
						</td>
						<td width="5">
							|
						</td>
						<td width="32">
							<label class="salir s_tooltip cursor_mano"
								onclick="window.close()"
								title="<s:text name="aula.alumno.pop_up.cerrar"/>">
								<s:text name="aula.alumno.pop_up.cerrar"></s:text>
							</label>
						</td>
						<td width="15">
							<label class="salir cursor_mano" onclick="window.close()">
								<img src="<%=request.getContextPath()%>/img/salir_x.gif"
									width="13" height="13" border="0" class="s_tooltip"
									title="<s:text name="aula.alumno.pop_up.cerrar"></s:text>" />
							</label>
						</td>
						<td width="5">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<div id="pop_cuerpo" style="width: 95%;">
				<div style="width: 95%; position: relative; height: 20px;">
					<div style="right: 0pt; width: 50px; position: absolute;">
						<input class="boton_formulario_anotacion form_button s_tooltip"
							type="button" value="<s:text name="anotacion.nuevo"/>"
							title="<s:text name="anotacion.nuevo.agregar"/>" 
							onclick="formulario_anotacion_agregar();"/>
					</div>
				</div>
				<div class="anotacion_segundo" id="anotacion_segundo_0">
					<div class="anotacion_segundo_relleno">
					</div>
					<div class="anotacion_segundo_relleno">
					</div>
				</div>
				<div id="anotacion_total" style="position: relative; width: 97%;">
					<div class="anotacion_segundo_nuevo" style="height: 9px;">
					</div>
					<div id="anotacion_pre_nuevo" style="width: 98%; display: none;">
						<div class="anotacion_pre_relleno">
						</div>
						<div class="anotacion_pre_principal">
							<div class="div_center_textarea" style="position: relative;">
								<textarea class="anotacion_titulo_nuevo" lang="0" id="anotacion_titulo_textarea_0"
									style="width: 90%; position: relative; left: 0pt; text-align: left;"></textarea>
							</div>
							<img alt="x" style="display: none;"
								class="s_tooltip cursor_mano anotacion_nuevo_eliminar"
								title="<s:text name="anotacion.nuevo.eliminar.tooltip"/>"
								src="<%=request.getContextPath()%>/img/cerrar_portal.jpg" 
								onclick="formulario_anotacion_esconder();"/>
							<div class="div_anotacion_nuevo_opciones">
								<div class="anotacion_pre_nuevo_instruccion">
									&laquo;
									<s:text name="anotacion.escribir"></s:text>
								</div>
							</div>
						</div>
					</div>
					<div
						style="width: 95%; position: relative; height: 20px; display: none;"
						id="anotacion_pre_nuevo_boton">
						<div
							style="right: 0pt; width: 50px; position: absolute; padding-top: 4px;">
							<input class="boton_crear_anotacion form_button s_tooltip"
								type="button" value="<s:text name="anotacion.nuevo.crear"/>"
								title="<s:text name="anotacion.nuevo.crear.tooltip"/>" 
								onclick="crear_anotacion();"/>
						</div>
					</div>
					<div class="anotacion_segundo_nuevo" style="height: 9px;">
					</div>
					<s:iterator value="anotaciones">
						<div class="anotacion_primero" lang="<s:property value="id"/>"
							id="anotacion_<s:property value="id"/>"
							onclick="selecionar_anotacion(this);">
							<div class="anotacion_border_selecionar"
								id="anotacion_borde_azul_<s:property value="id"/>">
							</div>
							<div class="anotacion_border_eliminar">
								<img alt="x" lang="<s:property value="id"/>"
									class="s_tooltip cursor_mano anotacion_eliminar"
									title="<s:text name="anotacion.nota.eliminar"></s:text>"
									src="<%=request.getContextPath()%>/img/cerrar_portal.jpg" 
								onclick="eliminar_anotacion('<s:property value="id"/>');"/>
							</div>
							<div class="anotacion_titulo">
								<div class="div_center_textarea">
									<textarea class="anotacion_titulo_editar"
										lang="<s:property value="id"/>" style="width: 100%;"
										id="anotacion_titulo_textarea_<s:property value="id"/>"
										><s:property value="titulo" /></textarea>
								</div>
							</div>
							<s:if test="#fm_formato.longitud(contenido)!=0">
								<div class="div_anotacion_comentario_editar"
									id="anotacion_comentario_<s:property value="id"/>"
									style="padding-bottom: 1px;">
									<div class="div_center_textarea">
										<textarea class="anotacion_comentario_editar"
											lang="<s:property value="id"/>" style="width: 100%;"
											id="anotacion_comentario_textarea_<s:property value="id"/>"
											><s:property value="contenido" /></textarea>
									</div>
								</div>
								<div class="div_anotacion_opciones">
									<div title="<s:text name="anotacion.comentario"></s:text>"
										lang="<s:property value="id"/>"
										class="div_div_anotacion_opciones cursor_mano s_tooltip div_anotacion_opciones_selecionado"
										id="anotacion_opcion_<s:property value="id"/>"
										onclick="comentario_ver_anotacion(this);">
										<img alt="+" class="cursor_mano s_tooltip" title=""
											src="<%=request.getContextPath()%>/img/icon_comentario.png"
											style="padding-top: 2px;" />
										<label class="div_anotacion_opciones_label cursor_mano">
											<s:text name="anotacion.comentario"></s:text>
										</label>
									</div>
									<div
										title="<s:text name="aula.alumno.pop_up.imprimir"></s:text>"
										class="div_div_anotacion_imprimir cursor_mano s_tooltip"
										lang="<s:property value="id"/>"
										onclick="imprimir_general('<s:property value="id"/>');"
										id="anotacion_imprimir_<s:property value="id"/>">
										<img alt="*" class="cursor_mano"
											src="<%=request.getContextPath()%>/img/impresora.gif"
											style="padding-top: 2px;" />
										<label class="div_anotacion_opciones_label cursor_mano"
											style="padding-bottom: 2px;">
											<s:text name="aula.alumno.pop_up.imprimir"></s:text>
										</label>
									</div>
									<div class="anotacion_fecha s_tooltip cursor_mano"
										title="<s:text name="anotacion.fecha.creada"></s:text> <s:property value="#fm_formato.getStringDeDate(fecha_creacion)" />, <s:text name="anotacion.fecha.ultimo"></s:text> <s:property	value="#fm_formato.getStringDeDateNull(fecha_modificacion)" />">
										<s:property
											value="#fm_formato.getStringDeDate(fecha_creacion)" />
									</div>
								</div>
							</s:if>
							<s:else>
								<div class="div_anotacion_comentario_editar"
									style="display: none; padding-bottom: 1px;"
									id="anotacion_comentario_<s:property value="id"/>">
									<div class="div_center_textarea">
										<textarea class="anotacion_comentario_editar"
											lang="<s:property value="id"/>" style=""
											id="anotacion_comentario_textarea_<s:property value="id"/>"></textarea>
									</div>
								</div>
								<div class="div_anotacion_opciones">
									<div title="<s:text name="anotacion.comentario"></s:text>"
										class="div_div_anotacion_opciones cursor_mano s_tooltip"
										lang="<s:property value="id"/>"
										onclick="comentario_ver_anotacion(this);"
										id="anotacion_opcion_<s:property value="id"/>">
										<img alt="+" class="cursor_mano"
											src="<%=request.getContextPath()%>/img/icon_comentario.png"
											style="padding-top: 2px;" />
										<label class="div_anotacion_opciones_label cursor_mano">
											<s:text name="anotacion.comentario"></s:text>
										</label>
									</div>
									<div
										title="<s:text name="aula.alumno.pop_up.imprimir"></s:text>"
										class="div_div_anotacion_imprimir cursor_mano s_tooltip"
										lang="<s:property value="id"/>"
										onclick="imprimir_general('<s:property value="id"/>');"
										id="anotacion_imprimir_<s:property value="id"/>">
										<img alt="*" class="cursor_mano"
											src="<%=request.getContextPath()%>/img/impresora.gif"
											style="padding-top: 2px;" />
										<label class="div_anotacion_opciones_label cursor_mano"
											style="padding-left: 2px;">
											<s:text name="aula.alumno.pop_up.imprimir"></s:text>
										</label>
									</div>
									<div class="anotacion_fecha s_tooltip cursor_mano"
										title="<s:text name="anotacion.fecha.creada"></s:text> <s:property value="#fm_formato.getStringDeDate(fecha_creacion)" />, <s:text name="anotacion.fecha.ultimo"></s:text> <s:property	value="#fm_formato.getStringDeDateNull(fecha_modificacion)" />">
										<s:property
											value="#fm_formato.getStringDeDate(fecha_creacion)" />
									</div>
								</div>
							</s:else>
						</div>
						<div class="anotacion_segundo"
							id="anotacion_segundo_<s:property value="id"/>">
							<div class="anotacion_segundo_relleno">
							</div>
							<div class="anotacion_segundo_relleno">
							</div>
						</div>
					</s:iterator>
				</div>
			</div>
			<div id="pie" style="bottom: 0px; width: 100%;">
				<s:include value="../pie.jsp"></s:include>
			</div>
		</div>
	</body>
</html>