
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%@  page import="java.util.Collection"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%@  page import="edu.tecsup.lms.util.Util"%>
<%@  page import="edu.tecsup.lms.modelo.Especialidad"%>
<%@  page import="edu.tecsup.lms.modelo.usuario.Rol"%>

<%@  page import="edu.tecsup.lms.modelo.Sede"%>
<%@  page import="edu.tecsup.lms.modelo.Departamento"%>
<%@  page import="edu.tecsup.lms.modelo.Periodo"%>
<%@  page import="edu.tecsup.lms.modelo.Usuario"%>
<%@  page import="edu.tecsup.lms.modelo.ficha.Etiqueta"%>
<%@  page import="edu.tecsup.lms.modelo.Ficha"%>
<%@  page import="edu.tecsup.lms.modelo.Curso"%>
<%@  page import="edu.tecsup.lms.util.Formato"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%
	Usuario usuario = (Usuario) request.getSession().getAttribute(
			Constante.USUARIO_ACTUAL);
	Collection<Especialidad> formacion = (Collection<Especialidad>) request
			.getAttribute("FORMACION_PREDETERMINADA");
	Collection<Periodo> periodos = (Collection<Periodo>) request
			.getAttribute("PERIODO_PREDETERMINADA");
	Collection<Etiqueta> etiquetaPredeterminadas = (Collection<Etiqueta>) request
			.getAttribute("ETIQUETA_PREDETERMINADA");
	Collection<Ficha> fichas = (Collection<Ficha>) request
			.getAttribute("BUSQUEDA_FICHAS");

	String busquedaNombre = String.valueOf(request
			.getAttribute("BUSQUEDA_FICHAS_NOMBRE"));
	String busquedaExacta = String.valueOf(request
			.getAttribute("BUSQUEDA_FICHAS_EXACTA"));
	String busquedaFormacion = String.valueOf(request
			.getAttribute("BUSQUEDA_FICHAS_FORMACION"));
	String busquedaStringFormacion = String.valueOf(request
			.getAttribute("BUSQUEDA_FICHAS_STRINGFORMACION"));
	String busquedaSede = String.valueOf(request
			.getAttribute("BUSQUEDA_FICHAS_SEDE"));
	String busquedaPeriodo = String.valueOf(request
			.getAttribute("BUSQUEDA_FICHAS_PERIODO"));
	String busquedaFecha1 = String.valueOf(request
			.getAttribute("BUSQUEDA_FICHAS_FECHA1"));
	String busquedaFecha2 = String.valueOf(request
			.getAttribute("BUSQUEDA_FICHAS_FECHA2"));
	String busquedaCiclo = String.valueOf(request
			.getAttribute("BUSQUEDA_FICHAS_CICLO"));
	String posicionPagina = String.valueOf(request
			.getAttribute("BUSQUEDA_POSICION_PAGINA"));
	String totalRegistro = String.valueOf(request
			.getAttribute("BUSQUEDA_TOTAL_REGISTRO"));
	String cantidadRegistro = String.valueOf(request
			.getAttribute("BUSQUEDA_CANTIDAD_REGISTRO"));
	String etiquetaAhora = String.valueOf(request
			.getAttribute("ETIQUETA_AHORA"));
	String busquedaDepartamento = String.valueOf(request
			.getAttribute("BUSQUEDA_FICHAS_DEPARTAMENTO"));
	String tipoBusqueda = String.valueOf(request
			.getAttribute("BUSQUEDA_FICHAS_TIPO"));

	boolean igualEtiquetaUDS = false;
	if (Constante.ETIQUETA_UDS.equals(etiquetaAhora)) {
		igualEtiquetaUDS = true;
	}
	boolean igualEtiquetaPapelera = false;
	if (Constante.ETIQUETA_PAPELERA.equals(etiquetaAhora)) {
		igualEtiquetaPapelera = true;
	}
	boolean igualEtiquetaHistorico = false;
	if (Constante.ETIQUETA_HISTORICO.equals(etiquetaAhora)) {
		igualEtiquetaHistorico = true;
	}
	boolean igualEtiquetaVirtual = false;
	if (Constante.ETIQUETA_VIRTUAL.equals(etiquetaAhora)) {
		igualEtiquetaVirtual = true;
	}
	boolean igualEtiquetaImportante = false;
	if (Constante.ETIQUETA_IMPORTANTE.equals(etiquetaAhora)) {
		igualEtiquetaImportante = true;
	}
	boolean igualEtiquetaUDSHistorico = false;
	if (Constante.ETIQUETA_UDS_HISTORICO.equals(etiquetaAhora)) {
		igualEtiquetaUDSHistorico = true;
	}
	boolean isAdmin = usuario.getRol().containsKey(
			Constante.ROL_CAMPUS_ADMINISTRADOR);
	boolean isSoporte = usuario.getRol().containsKey(
			Constante.ROL_CAMPUS_SOPORTE);
	boolean isDirector = usuario.getRol().containsKey(
			Constante.ROL_CAMPUS_DIRECTOR);
	//if(!isAdmin) isAdmin = usuario.getRol().containsKey(Constante.ROL_CAMPUS_SOPORTE);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title><s:text name="titulo.campus.virtual" />
		</title>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/js/jscalendar/calendar-style.css" />
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/jscalendar/calendar.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/jscalendar/calendar-es.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/jscalendar/calendar-setup.js"></script>
		<link href="<%=request.getContextPath()%>/js/menu/menu.css"
			rel="stylesheet" type="text/css" />
		<script language="JavaScript"
			src="<%=request.getContextPath()%>/js/menu/menu.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/admin/ficha/admin_ficha_importar.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>		
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/tooltip/tooltip.js"></script>
		<script>
				var TIPOETIQUETA=<%=etiquetaAhora%>;
				var ETIQUETAUDS = <%=Constante.ETIQUETA_UDS%>;
				var ETIQUETAUDS_HISTORICO = <%=Constante.ETIQUETA_UDS_HISTORICO%>;
				var ETIQUETA_HISTORICO = <%=Constante.ETIQUETA_HISTORICO%>;
				var CANTIDADREGISTRO =<%=cantidadRegistro%>;
				var isADMIN = <%=isAdmin%>;		
				var isSoporte = <%=isSoporte%>
				var isDirector = <%=isDirector%>	
		</script>
	</head>
	<body>
		<div id="contenedor">
		
			<s:include value="/comun/bienvenida.jsp"></s:include>
			
			<div id="cuerpo">
				<div id="principal" style="width: 980px;">
					<table width="975" border="0" cellpadding="0" cellspacing="0"
						class="tablaFicha">
						<tr class="fon_cab">
							<td height="20" colspan="5" class="tit_cab">
								Todos los cursos
							</td>
						</tr>
						<tr>
							<td width="975" align="center">
								<table height="15px">
									<tr>
										<td />
									</tr>
								</table>
								<table id="busqueda_opciones_1" style="display: none;">
									<tr>
										<td>
											Nombre del curso:
											<input type="text" size="20" id="form_busquedaNombre1"
												value="<%if("0".equals(tipoBusqueda)){out.print(busquedaNombre);} %>"
												onkeyup="buscarSimpleKey(event);">
											<input type="button" value="Buscar" class="form_button"
												onclick="javascript:busqueda_simple();">
											<label class="opcion_selecionar"
												style="cursor: pointer; font-weight: bold; text-decoration: underline;"
												onclick="ocultarPanel(document.getElementById('busqueda_opciones_1'),document.getElementById('busqueda_opciones_2'));">
												B&uacute;squeda avanzada
											</label>
										</td>
									</tr>
								</table>

								<table id="busqueda_opciones_2" border="0" cellpadding="0"
									cellspacing="5" width="80%"
									style="border: 1px solid #7fa9ed; display: none;">
									<tr>
										<td width="150">
										</td>
										<td width="50">
										</td>
										<td width="200">
										</td>
										<td width="200" align="right">
											<label class="opcion_selecionar"
												style="font-weight: bold; text-decoration: underline;"
												onclick="ocultarPanel(document.getElementById('busqueda_opciones_2'),document.getElementById('busqueda_opciones_1'));">
												Ocultar b&uacute;squeda avanzada
											</label>
											<br>
										</td>
									</tr>
									<tr>
										<td align="right">
											Nombre del curso :
										</td>
										<td colspan="3" align="left">
											<input type="text" size="35" id="form_busquedaNombre2"
												value="<%if("1".equals(tipoBusqueda)){out.print(busquedaNombre);} %>"
												onkeyup="buscarDosKey(event);">
											&nbsp;&nbsp;&nbsp;
											<em>Exacto</em>&nbsp;
											<input type="checkbox" id="form_busquedaExacta"
												<%if("1".equals(busquedaExacta)){out.print("checked=\"checked\"");}%>>
										</td>
									</tr>
									<tr>
										<td align="right">
											Formaci&oacute;n :
										</td>
										<td colspan="3" align="left">
											<select id="form_busquedaFormacion"
												onchange="seleccionarOtraForma(this);">
												<option value="0"
													<%if("0".equals(busquedaFormacion)){ out.print("selected=\"selected\"");}%>>
													Todos
												</option>
												<%
													for (Especialidad forma : formacion) {
												%>
												<option value="<%=forma.getCodigo()%>"
													<%if(forma.getCodigo().equals(busquedaFormacion)){ out.print("selected=\"selected\"");}%>>
													<%=forma.getNombre()%>
												</option>
												<%
													}
													if (igualEtiquetaUDS || igualEtiquetaUDSHistorico) {
												%>
												<option value="-1"
													<%if(0<busquedaStringFormacion.length()){out.print("selected=\"selected\""); }%>>
													Otro
												</option>
												<%
													}
												%>
											</select>
										</td>
									</tr>
									<%
										if (igualEtiquetaUDS || igualEtiquetaUDSHistorico) {
									%>
									<tr id="tr_busqueda_dos_otro_forma"
										style="display: none; visibility: hidden;">
										<td />
										<td colspan="3" align="left">
											<input type="text" size="40"
												id="form_busquedaStringFormacion"
												value="<%=busquedaStringFormacion%>"
												onkeyup="buscarDosKey(event);">
										</td>
									</tr>
									<%
										}
									%>
									<tr>
										<td align="right">
											Departamento :
										</td>
										<td colspan="1">
											<select id="form_busquedaDepartamento">
												<option value="0"
													<%if("0".equals(busquedaDepartamento)){ out.print("selected=\"selected\"");}%>>
													Todos
												</option>
												<%
													for (Departamento forma : Util.getDepartamentos()) {
												%>
												<option value="<%=forma.getCodigo()%>"
													<%if(forma.getCodigo().equals(busquedaDepartamento)){ out.print("selected=\"selected\"");}%>>
													<%=forma.getNombre()%>
												</option>
												<%
													}
												%>
											</select>
										</td>
										<td align="right">
											Ciclo :
										</td>
										<td colspan="1" align="left">
											<select id="form_busquedaCiclo">
												<option value="0"
													<%if("0".equals(busquedaCiclo)){ out.print("selected=\"selected\"");}%>>
													Todos
												</option>
												<option value="1"
													<%if("1".equals(busquedaCiclo)){ out.print("selected=\"selected\"");}%>>
													1ero.
												</option>
												<option value="2"
													<%if("2".equals(busquedaCiclo)){ out.print("selected=\"selected\"");}%>>
													2do.
												</option>
												<option value="3"
													<%if("3".equals(busquedaCiclo)){ out.print("selected=\"selected\"");}%>>
													3ero.
												</option>
												<option value="4"
													<%if("4".equals(busquedaCiclo)){ out.print("selected=\"selected\"");}%>>
													4to.
												</option>
												<option value="5"
													<%if("5".equals(busquedaCiclo)){ out.print("selected=\"selected\"");}%>>
													5to.
												</option>
												<option value="6"
													<%if("6".equals(busquedaCiclo)){ out.print("selected=\"selected\"");}%>>
													6to.
												</option>
												<option value="7"
													<%if("7".equals(busquedaCiclo)){ out.print("selected=\"selected\"");}%>>
													7mo.
												</option>
												<option value="8"
													<%if("8".equals(busquedaCiclo)){ out.print("selected=\"selected\"");}%>>
													8vo.
												</option>
											</select>
										</td>
									</tr>
									<tr>
										<td align="right">
											Sede :
										</td>
										<td colspan="1" align="left">
											<select id="form_busquedaSede">
												<option value="0"
													<%if("0".equals(busquedaSede)){ out.print("selected=\"selected\"");}%>>
													Todos
												</option>
												<%
													for (Sede sede : Util.getSedes()) {
												%>
												<option value="<%=sede.getCodigo()%>"
													<%if(sede.getCodigo().equals(busquedaSede)){ out.print("selected=\"selected\"");}%>>
													<%=sede.getNombre()%>
												</option>
												<%
													}
												%>
											</select>
										</td>
										<td align="right">
											Per&iacute;odo :
										</td>
										<td colspan="1" align="left">
											<select id="form_busquedaPeriodo">
												<option value="0"
													<%if("0".equals(busquedaPeriodo)){ out.print(" selected=\"selected\" ");}%>>
													Todos
												</option>
												<%
													for (Periodo periodo : periodos) {
												%>
												<option value="<%=periodo.getIdPeriodo()%>"
													<%if(periodo.getIdPeriodo().equals(busquedaPeriodo)){ out.print(" selected=\"selected\" ");}%>>
													<%=periodo.getNombre()%>
												</option>
												<%
													}
												%>
											</select>
										</td>
									</tr>
									<tr>
										<td align="right">
											Fecha Inicio :
										</td>
										<td colspan="3" align="left">
											<label style="margin-left: 20px; margin-right: 20px;">
												De
											</label>
											<input type="text" id="form_fechaInicio1" size="8"
												class="form_text" readonly="readonly" name="fActivacion1"
												value="<%=busquedaFecha1%>" />
											<label style="margin-left: 20px; margin-right: 20px;">
												a
											</label>
											<input type="text" id="form_fechaInicio2" size="8"
												class="form_text" readonly="readonly" name="fActivacion2"
												value="<%=busquedaFecha2%>" />
										</td>
									</tr>
									<tr>
										<td align="right" colspan="4">
											<input type="button" value="Buscar" class="form_button"
												onclick="javascript:busquedaDos();">
										</td>
									</tr>
								</table>
								<table height="5px">
									<tr>
										<td />
									</tr>
								</table>
								<script language="JavaScript" type="text/javascript">
									Calendar.setup({inputField:"form_fechaInicio1", ifFormat:"%d-%m-%Y",  singleClick:true, onUpdate:catcalc});
									Calendar.setup({inputField:"form_fechaInicio2", ifFormat:"%d-%m-%Y",  singleClick:true, onUpdate:catcalc});									
									if('0'==='<%=tipoBusqueda%>'){
										ocultarPanel(document.getElementById('busqueda_opciones_2'),document.getElementById('busqueda_opciones_1'));
									}else{
										ocultarPanel(document.getElementById('busqueda_opciones_1'),document.getElementById('busqueda_opciones_2'));
									}
								</script>

							</td>
						</tr>
						<tr id="tr_mensajes_advertencia"
							style="visibility: hidden; display: none;">
							<td align="center" height="35" style="background-color: white;">
								<table cellpadding="0" cellspacing="0" bgcolor="white">
									<tr>
										<td width="5" height="5" id="td_1_mensaje"
											style="background-color: white ;background-image: url('../../img/punto_rojo.png'); background-position: left top;" />
										<td style="background-color: #CC0000;" id="td_2_mensaje" />
										<td id="td_3_mensaje"
											style="background-image: url('../../img/punto_rojo.png'); background-position: right top;" />
									</tr>
									<tr>
										<td style="background-color: #CC0000;" width="5"
											id="td_4_mensaje" />
										<td style="background-color: #CC0000;" id="td_5_mensaje"
											align="center">
											<b id="tr_mensajes_mensaje"
												style="color: white; padding-left: 10px; text-align: center;">&nbsp;
											</b>
										</td>
										<td style="background-color: #CC0000;" width="5"
											id="td_6_mensaje" />
									</tr>
									<tr>
										<td height="5" id="td_7_mensaje"
											style="background-image: url('../../img/punto_rojo.png'); background-position: left bottom;" />
										<td style="background-color: #CC0000;" id="td_8_mensaje" />
										<td id="td_9_mensaje"
											style="background-image: url('../../img/punto_rojo.png'); background-position: right bottom;" />
									</tr>
								</table>
								<table height="5px">
									<tr>
										<td />
									</tr>
								</table>
							</td>
						</tr>

						<script>
						<%if(igualEtiquetaVirtual){%>						
					mostrarMensajeErrorCompleto("Usted se encuentra en la pesta\xf1a Virtuales.",'0');	
				<%}else{
					if(igualEtiquetaImportante){ %>
					mostrarMensajeErrorCompleto("Usted se encuentra en la pesta\xf1a Importantes.",'0');
				<%}else{
					if(igualEtiquetaHistorico){ %>
					mostrarMensajeErrorCompleto("Usted se encuentra en la pesta\xf1a Hist&oacute;rico.",'0');
				<%}else{
					if(igualEtiquetaUDS){ %>
					mostrarMensajeErrorCompleto("Usted se encuentra en la pesta\xf1a UDS.",'0');
				<%}else{
					if(igualEtiquetaUDSHistorico){ %>
					mostrarMensajeErrorCompleto("Usted se encuentra en la pesta\xf1a UDS Hist&oacute;rico.",'0');
				<%}else{
					if(igualEtiquetaPapelera){ %>
						mostrarMensajeErrorCompleto("Usted se encuentra en la pesta\xf1a Papelera.",'0');		
				<%}}}}}}
				if(null!=request.getAttribute("mensaje_texto")){%>
							mostrarMensajeErrorCompleto('<%=String.valueOf(request
										.getAttribute("mensaje_texto"))%>','<%=String.valueOf(request.getAttribute("mensaje_tipo"))%>');
						
						<%} %>
						</script>


						<tr>
							<td width="975" align="right" valign="bottom" height="20">
								<%
									if (isAdmin) {
								%>
								<table cellpadding="0" cellspacing="0" height="100%"
									style="border-top: 1px #7EAAD1 solid; border-right: 1px #7fa9ed solid; position: relative; right: 0px; bottom: 0px; padding-bottom: -5px;"
									width="540" class="tabla_sin_layout">
									<tr height="100%">
										<%
											for (Etiqueta eti : etiquetaPredeterminadas) {
										%>
										<td align="center" onclick="javascript:cambiarEtiqueta('<%=eti.getIdEtiqueta()%>');"
											style="cursor: pointer; border-left: 1px #7fa9ed solid; <% if (etiquetaAhora.equals(eti.getIdEtiqueta())){out.print("background-color: #E5EFF8;");}%>"
											height="25">
											<%=eti.getNombre()%>
										</td>
										<%
											}
										%>
									</tr>
								</table>
								<%
									} else {
								%>
								<table cellpadding="0" cellspacing="0"
									style="border-top: 1px #7fa9ed solid; border-right: 1px #7fa9ed solid;"
									width="220">
									<tr>
										<td align="center"
											style="cursor: pointer; border-left: 1px #7fa9ed solid; <% if (etiquetaAhora.equals(Constante.ETIQUETA_VIRTUAL)){out.print("background-color: #E5EFF8;");}%>"
											onclick="javascript:cambiarEtiqueta('<%=Constante.ETIQUETA_VIRTUAL%>');"
											height="25">
											Virtuales
										</td>
										<%
											if (isAdmin || isSoporte || isDirector) {
										%>
										<td align="center"
											style="cursor: pointer; border-left: 1px #7fa9ed solid; <% if (etiquetaAhora.equals(Constante.ETIQUETA_HISTORICO)){out.print("background-color: #E5EFF8;");}%>"
											onclick="javascript:cambiarEtiqueta('<%=Constante.ETIQUETA_HISTORICO%>');"
											height="25">
											Hist&oacute;ricos
										</td>
										<%
											}
										%>
									</tr>

								</table>
								<%
									}
								%>
							</td>
						</tr>
						<tr>
							<td style="background-color: #E5EFF8;" class="arribaAbajo "
								valign="bottom">
								<table border="0" width="100%" align="left"
									class="tabla_sin_layout">
									<tr>
										<%
											if (isAdmin) {
										%>
										<td width="360" valign="bottom">
											<%
												if (igualEtiquetaUDS) {
											%>
											<input type="button" value="Virtuales"
												style="font-weight: bolder; cursor: pointer; margin-left: 10px; margin-right: 5px;"
												onclick="javascript:crearVirtual();" class="form_button">
											<input type="button" value="Virtuales Agrupados"
												style="cursor: pointer;"
												onclick="javascript:crearVirtualAgrupado();"
												class="form_button">
											<%
												} else {
														if (igualEtiquetaPapelera) {
											%>
											<input type="button" value="Restaurar"
												style="font-weight: bolder; cursor: pointer; margin-left: 10px;"
												onclick="javascript:setRestaurar();" class="form_button">
											<%
												} else {
															if (igualEtiquetaVirtual || igualEtiquetaImportante) {
											%>
											<input type="button" value="Papelera"
												style="font-weight: bolder; cursor: pointer; margin-left: 10px;"
												onclick="javascript:setPapelera();" class="form_button">
											<input type="button" value="Sincronizar"
												style="font-weight: bolder; cursor: pointer; margin-left: 10px;"
												onclick="javascript:setSincronizar();" class="form_button">
											<input type="button" value="Envio Constancia"
												style="font-weight: bolder; cursor: pointer; margin-left: 10px;"
												onclick="javascript:setConstancia();" class="form_button">
											<%
												}
														}
													}
											%>


										</td>
										<%
											} else {
										%>

										<td>
										</td>
										<%
											}
										%>
										<td valign="bottom">
											<label style="padding-right: 5px;">
												Ver&nbsp;:&nbsp;&nbsp;
											</label>
											<select class="form_button" id="registro"
												onChange="javascript:cambiarCantidadRegistro(this.options[this.selectedIndex].value);">
												<option value="25"
													<%if("25".equals(cantidadRegistro)){out.print(" selected=\"selected\" ");} %>>
													25
												</option>
												<option value="50"
													<%if("50".equals(cantidadRegistro)){out.print(" selected=\"selected\" ");} %>>
													50
												</option>
												<option value="100"
													<%if("100".equals(cantidadRegistro)){out.print(" selected=\"selected\" ");} %>>
													100
												</option>
											</select>
											&nbsp;registros por p&aacute;gina.
										</td>
										<td valign="bottom" align="right">
											<table style="margin-right: 10px;" cellpadding="2"
												cellspacing="2">
												<tr>
													<%
														int int_cantidadRegistro = Integer.parseInt(cantidadRegistro);
														int int_posicionpagina = Integer.parseInt(posicionPagina);
														int int_totalRegistro = Integer.parseInt(totalRegistro);
														int cantidadPaginaFutu = (int) Math
																.ceil((double) (int_totalRegistro - (int_cantidadRegistro * int_posicionpagina))
																		/ int_cantidadRegistro);
														int cantidadRegistroAca = int_totalRegistro
																- (int_posicionpagina * int_cantidadRegistro);
														int int_principal = (int_posicionpagina * int_cantidadRegistro) + 1;
														int int_a = 0;
														if (int_cantidadRegistro <= cantidadRegistroAca) {
															int_a = (int_posicionpagina * int_cantidadRegistro)
																	+ int_cantidadRegistro;
														} else {
															int_a = (int_posicionpagina * int_cantidadRegistro)
																	+ cantidadRegistroAca;
														}
													%>
													<td>
														<%
															if (2 <= int_posicionpagina) {
														%>
														<span class="opcion_selecionar"
															style="text-decoration: underline; font-weight: bold;"
															onclick="javscript:cambiarPagina('0');"> &lt;&lt;
														</span>
														<%
															} else {
														%>
														<span class="opcion_selecionar" style="color: silver;">
															&lt;&lt; </span>
														<%
															}
														%>
													</td>
													<td>
														<%
															if (1 <= int_posicionpagina) {
														%>
														<span class="opcion_selecionar"
															style="text-decoration: underline; font-weight: bold;"
															onclick="javscript:cambiarPagina('<%=int_posicionpagina - 1%>');">
															&lt; </span>
														<%
															} else {
														%>
														<span class="opcion_selecionar" style="color: silver;"}
														>
															&lt; </span>
														<%
															}
														%>
													</td>
													<td>
														<%
															if (0 == int_a) {
																out.print(0);
															} else {
																out.print(int_principal);
															}
														%>
														-
														<%=int_a%>
														de
														<%=totalRegistro%>
														registro(s)
													</td>
													<td>
														<%
															if (1 < cantidadPaginaFutu) {
														%>
														<span class="opcion_selecionar"
															style="text-decoration: underline; font-weight: bold;"
															}
														onclick="javscript:cambiarPagina('<%=int_posicionpagina + 1%>');">
															&gt; </span>
														<%
															} else {
														%>
														<span class="opcion_selecionar" style="color: silver;"}
														>
															&gt; </span>
														<%
															}
														%>
													</td>
													<td>
														<%
															if (2 < cantidadPaginaFutu) {
														%>
														<label class="opcion_selecionar"
															style="text-decoration: underline; font-weight: bold;"
															onclick="javscript:cambiarPagina('<%=int_posicionpagina + cantidadPaginaFutu - 1%>');">
															&gt;&gt;
														</label>
														<%
															} else {
														%>
														<label class="opcion_selecionar" style="color: silver;">
															&gt;&gt;
														</label>
														<%
															}
														%>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<%
										if (!(igualEtiquetaHistorico || igualEtiquetaUDSHistorico)
												&& isAdmin) {
									%>
									<tr>
										<td colspan="3" height="20">
											<span style="margin-left: 10px;"> Seleccionar : </span>
											<span class="opcion_selecionar"
												onClick="javascript:checkCheckBox(true);">Todas</span>,
											<span class="opcion_selecionar"
												onClick="javascript:checkCheckBox(false);">Ninguna</span>
										</td>
									</tr>
									<%
										}
									%>
								</table>
							</td>
						</tr>
						<tr>
							<td class="moduloAbajo1" valign="top" align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0"
									style="border-bottom: solid #7fa9ed 1px; background-color: #EEEEEE;">
									<tr>
										<td width="10" height="25">

										</td>
										<%
											if (isAdmin) {
												if (!(igualEtiquetaHistorico || igualEtiquetaUDSHistorico)) {
										%>

										<td width="20">

										</td>
										<%
											}
												if (!igualEtiquetaUDS && !igualEtiquetaPapelera
														&& !igualEtiquetaHistorico
														&& !igualEtiquetaUDSHistorico) {
										%>

										<td width="20">

										</td>


										<td width="20">

										</td>
										<%
											}
											}
										%>
										<td width="160" align="left">
											<label style="font-weight: bold;">
												Nombre del Curso
											</label>
										</td>

										<td width="40" align="center">
											<label style="font-weight: bold;">
												Sec.
											</label>
										</td>

										<td width="70">
											<label style="font-weight: bold; margin-left: 5px;">
												F.Inicio
											</label>
										</td>
										<td width="50">
											<label style="font-weight: bold;">
												Periodo
											</label>
										</td>
										<td width="35">
											<label style="font-weight: bold;">
												Tipo
											</label>
										</td>

										<td width="230">
											<label style="font-weight: bold; margin-left: 5px;">
												Formaci&oacute;n
											</label>
										</td>

										<%--										<td width="50">--%>
										<%--											<label style="font-weight: bold;">--%>
										<%--												Turno--%>
										<%--											</label>--%>
										<%--										</td>--%>

										<td width="40">
											<label style="font-weight: bold; margin-left: 5px;">
												Sede
											</label>
										</td>
										<td width="90">
											<label style="font-weight: bold; margin-left: 5px;">
												Dpto.
											</label>
										</td>
										<%
											if (isAdmin && !igualEtiquetaUDS && !igualEtiquetaPapelera
													&& !igualEtiquetaHistorico && !igualEtiquetaUDSHistorico) {
										%>
										<td width="20">

										</td>

										<td width="20">

										</td>
										<%
											}
										%>
									</tr>
								</table>
								<%
									if (0 != fichas.size()) {
								%>

								<table width="100%" id="todos_items_busqueda" cellpadding="1"
									cellspacing="0" class="tabla_sin_layout">
									<%
										int a = 0;
											for (Ficha ficha : fichas) {
												a++;
									%>
									<tr id="tr_<%=a%>" class="fon_color02 selecionando_tr" height="25">
										<td width="10" class="ficha_item_link ">
											&nbsp;

										</td>
										<%
											if (isAdmin) {
														if (!(igualEtiquetaHistorico || igualEtiquetaUDSHistorico)) {
										%>
										<td width="20" class="ficha_item_link ">
											<input type="checkbox" value="<%=ficha.getIdFicha()%>"
												onclick="javascript:seleccionarDIV('tr_<%=a%>',this)">
											<input type="hidden" value="<%=ficha.getIdFicha()%>">

										</td>
										<%
											}
														if (!igualEtiquetaUDS && !igualEtiquetaPapelera
																&& !igualEtiquetaHistorico
																&& !igualEtiquetaUDSHistorico) {
										%>
										<td width="20" class="ficha_item_link ">
											<input type="hidden" id="<%=ficha.getIdFicha()%>importante"
												value="<%=ficha.getImportante()%>">
											<%
												if (Constante.ESTADO_ACTIVO == ficha
																		.getImportante()) {
											%>
											<img
												src="<%=request.getContextPath()%>/img/icon_importante_y.gif"
												width="15" height="15"
												onclick="setImportante(this, '<%=ficha.getIdFicha()%>importante', '<%=ficha.getIdFicha()%>','<%=ficha.getNombreCursoUDS()%> (<%=ficha.getIdFicha()%>)');">
											<%
												} else {
											%>
											<img
												src="<%=request.getContextPath()%>/img/icon_importante_n.gif"
												width="15" height="15"
												onclick="setImportante(this, '<%=ficha.getIdFicha()%>importante', '<%=ficha.getIdFicha()%>','<%=ficha.getNombreCursoUDS()%> (<%=ficha.getIdFicha()%>)');">
											<%
												}
											%>
										</td>

										<td width="20" class="ficha_item_link ">
											<img src="<%=request.getContextPath()%>/img/information.gif"
												width="15" height="15"
												onmouseover="verToolTip('<%=ficha.getInformacion()%>', this);"
												onmouseout="ocultarToolTip()">
										</td>
										<%
											}
													}
										%>
										<td class="ficha_item_link " width="150" style="color: black;">
											<label
												onmouseup="javascript:verFichaMenu(event,'menu_<%=ficha.getIdFicha()%>');ocultarNombres()"
												onmouseover="verToolTip(' &nbsp;<%=ficha.getIdFicha()%> - <%=ficha.getNombreCursoUDS()%> C<%=ficha.getCodigoCursoUDS()%>', this);"
												onmouseout="ocultarToolTip()"
												onclick="javascript:entrarAulaVirtual('<%=ficha.getIdFicha()%>','<%=ficha.getNombreCursoUDS()%> (<%=ficha.getIdFicha()%>)');">
												<%=ficha.getNombreCursoUDS()%>
											</label>
											<div id="menu_<%=ficha.getIdFicha()%>" class="menu_principal">
												<div id="menu_item_<%=ficha.getIdFicha()%>"
													onclick="esconder_menu_inmediato();ocultarToolTip();entrarNuevoAulaVirtual('<%=ficha.getIdFicha()%>','<%=ficha.getNombreCursoUDS()%> (<%=ficha.getIdFicha()%>)');"
													class="menu_subitem_out"
													onMouseOut="javascript:esconder_itemmenu(this);ocultarToolTip(); "
													onmouseover="javascript:ver_itemmenu(this);ocultarToolTip();"
													style="width: 80px">
													Nueva p&aacute;gina
												</div>
											</div>
										</td>
										<td class="ficha_item_link " align="right" width="40"
											style="border-right: 1px solid #7EAAD1;"
											onclick="javascript:entrarAulaVirtual('<%=ficha.getIdFicha()%>');"
											onmouseover="verToolTip('<%=ficha.getSecciones()%>', this);"
											onmouseout="ocultarToolTip()">
											<label style="margin-right: 5px;">
												<%
													out.print(ficha.getSecciones());
												%>
											</label>
										</td>
										<td class="ficha_item_link " width="70"
											style="border-right: 1px solid #7EAAD1;"
											onclick="javascript:entrarAulaVirtual('<%=ficha.getIdFicha()%>');">
											<label style="margin-left: 5px;">
												<%=ficha.getStringFechaInicio()%>
											</label>
										</td>
										<td width="50" class="ficha_item_link "
											style="border-right: 1px solid #7EAAD1;"
											onclick="javascript:entrarAulaVirtual('<%=ficha.getIdFicha()%>');"
											onmouseover="verToolTip('<%=ficha.getNombrePeriodo()%>', this);"
											onmouseout="ocultarToolTip()">
											<label >
												<%=ficha.getNombrePeriodo()%>&nbsp;
											</label>
										</td>
										<td class="ficha_item_link " width="35"
											style="border-right: 1px solid #7EAAD1;"
											onclick="javascript:entrarAulaVirtual('<%=ficha.getIdFicha()%>');"
											onmouseover="verToolTip('<%=ficha.getNombreFamilia()%>', this);"
											onmouseout="ocultarToolTip()">
											<label style="margin-left: 5px;">
												<%=ficha.getNombreFamilia()%>
											</label>
										</td>
										<td class="ficha_item_link " width="230"
											style="border-right: 1px solid #7EAAD1;"
											
											onclick="javascript:entrarAulaVirtual('<%=ficha.getIdFicha()%>');"
											onmouseover="verToolTip('<%=ficha.getNombreFormacion()%>', this);"
											onmouseout="ocultarToolTip()">
											<label style="margin-left: 5px;">
												<%=ficha.getNombreFormacion()%>
											</label>
										</td>
										<%--										<td class="ficha_item_link" width="50"--%>
										<%--											style="border-right: 1px solid #7EAAD1;"--%>
										<%--											onclick="javascript:entrarAulaVirtual('<%=ficha.getIdFicha()%>');"--%>
										<%--											onmouseover="verToolTip('<%=ficha.getTurno()%>', this);"--%>
										<%--											onmouseout="ocultarToolTip()">--%>
										<%--											<label style="margin-left: 5px;">--%>
										<%--												<%=ficha.getTurno()%>--%>
										<%--											</label>--%>
										<%--										</td>--%>
										<td class="ficha_item_link " width="40"
											style="border-right: 1px solid #7EAAD1;"
											onclick="javascript:entrarAulaVirtual('<%=ficha.getIdFicha()%>');">
											<label style="margin-left: 5px;">
												<%=ficha.getStringSucursal()%>
											</label>
										</td>
										<td class="ficha_item_link " width="90"
											onclick="javascript:entrarAulaVirtual('<%=ficha.getIdFicha()%>');">
											<label style="margin-left: 5px;">
												<%=ficha.getNombreDepartamento()%>
											</label>
										</td>
										<%
											if (isAdmin && !igualEtiquetaUDS && !igualEtiquetaPapelera
															&& !igualEtiquetaHistorico
															&& !igualEtiquetaUDSHistorico) {
										%>
										<td class="ficha_item_link " width="20">
											<input type="hidden" id="<%=ficha.getIdFicha()%>estado"
												value="<%=ficha.getEstado()%>">
											<%
												if (Constante.ESTADO_ACTIVO == ficha.getEstado()) {
											%>
											<img src="<%=request.getContextPath()%>/img/activo.gif"
												width="14" height="14"
												onclick="setEstado(this, '<%=ficha.getIdFicha()%>estado', '<%=ficha.getIdFicha()%>','<%=ficha.getNombreCursoUDS()%> (<%=ficha.getIdFicha()%>)');"
												onmouseover="verToolTip(mostrarMensajeActivar('<%=ficha.getIdFicha()%>'), this);"
												onmousemove="verToolTip(mostrarMensajeActivar('<%=ficha.getIdFicha()%>'), this);"
												onmouseout="ocultarToolTip()">
											<%
												} else {
											%>
											<img src="<%=request.getContextPath()%>/img/desactivo.gif"
												width="14" height="14"
												onclick="setEstado(this, '<%=ficha.getIdFicha()%>estado', '<%=ficha.getIdFicha()%>','<%=ficha.getNombreCursoUDS()%> (<%=ficha.getIdFicha()%>)');"
												onmouseover="verToolTip(mostrarMensajeActivar('<%=ficha.getIdFicha()%>'), this);"
												onmousemove="verToolTip(mostrarMensajeActivar('<%=ficha.getIdFicha()%>'), this);"
												onmouseout="ocultarToolTip()">
											<%
												}
											%>
										</td>

										<td class="ficha_item_link " width="20">
											<%
												if (!igualEtiquetaUDS
																	&& Constante.ESTADO_ACTIVO == ficha.getNuevo()) {
											%>
											<img src="<%=request.getContextPath()%>/img/flag.gif"
												onMouseOver="verToolTip('Es un curso nuevo', this);"
												onmouseout="ocultarToolTip()">
											<%
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
								<div <%if(10 > ( int_a -int_principal) ) { out.print("style=\"height:"+( 10 -( int_a -int_principal) ) * 15 +"px\"");}%>>
									&nbsp;
								</div>
								<script language="JavaScript" type="text/javascript">
									limpiarCheckBox();
								</script>
								<%
									} else {
								%>
								<div
									style="height: 80px; text-align: center; font-weight: bold; margin-top: 60px;">
									No se encontr&oacute; ning&uacute;n curso.

								</div>
								<%
									}
								%>
							</td>
						</tr>
						<tr>
							<td style="background-color: #E5EFF8;">
								<table border="0" width="100%" align="left"
									class="tabla_sin_layout">
									<tr>
										<%
											if (isAdmin) {
										%>
										<td width="360" valign="bottom">
											<%
												if (igualEtiquetaUDS) {
											%>
											<input type="button" value="Virtuales"
												style="font-weight: bolder; cursor: pointer; margin-left: 10px; margin-right: 5px;"
												onclick="javascript:crearVirtual();" class="form_button">
											<input type="button" value="Virtuales Agrupados"
												style="cursor: pointer;"
												onclick="javascript:crearVirtualAgrupado();"
												class="form_button">
											<%
												} else {
														if (igualEtiquetaPapelera) {
											%>
											<input type="button" value="Restaurar"
												style="font-weight: bolder; cursor: pointer; margin-left: 10px;"
												onclick="javascript:setRestaurar();" class="form_button">
											<%
												} else {
															if (igualEtiquetaVirtual || igualEtiquetaImportante) {
											%>
											<input type="button" value="Papelera"
												style="font-weight: bolder; cursor: pointer; margin-left: 10px;"
												onclick="javascript:setPapelera();" class="form_button">
											<input type="button" value="Sincronizar"
												style="font-weight: bolder; cursor: pointer; margin-left: 10px;"
												onclick="javascript:setSincronizar();" class="form_button">
											<input type="button" value="Envio Constancia"
												style="font-weight: bolder; cursor: pointer; margin-left: 10px;"
												onclick="javascript:setConstancia();" class="form_button">
											<%
												}
														}
													}
											%>


										</td>
										<%
											} else {
										%>

										<td>
										</td>
										<%
											}
										%>
										<td valign="bottom">
											<label style="padding-right: 5px;">
												Ver&nbsp;:&nbsp;&nbsp;
											</label>
											<select id="registro" class="form_button"
												onChange="javascript:cambiarCantidadRegistro(this.options[this.selectedIndex].value);">
												<option value="25"
													<%if("25".equals(cantidadRegistro)){out.print(" selected=\"selected\" ");} %>>
													25
												</option>
												<option value="50"
													<%if("50".equals(cantidadRegistro)){out.print(" selected=\"selected\" ");} %>>
													50
												</option>
												<option value="100"
													<%if("100".equals(cantidadRegistro)){out.print(" selected=\"selected\" ");} %>>
													100
												</option>
											</select>
											&nbsp;registros por p&aacute;gina.
										</td>
										<td valign="bottom" align="right">
											<table style="margin-right: 10px;" cellpadding="2"
												cellspacing="2">
												<tr>
													<td>
														<%
															if (2 <= int_posicionpagina) {
														%>
														<label class="opcion_selecionar"
															style="text-decoration: underline; font-weight: bold;"
															onclick="javscript:cambiarPagina('0');">
															&lt;&lt;
														</label>
														<%
															} else {
														%>
														<label class="opcion_selecionar" style="color: silver;">
															&lt;&lt;
														</label>
														<%
															}
														%>
													</td>
													<td>
														<%
															if (1 <= int_posicionpagina) {
														%>
														<label class="opcion_selecionar"
															style="text-decoration: underline; font-weight: bold;"
															onclick="javscript:cambiarPagina('<%=int_posicionpagina - 1%>');">
															&lt;
														</label>
														<%
															} else {
														%>
														<label class="opcion_selecionar" style="color: silver;"}
														>
															&lt;
														</label>
														<%
															}
														%>
													</td>
													<td>
														<%
															if (0 == int_a) {
																out.print(0);
															} else {
																out.print(int_principal);
															}
														%>
														-
														<%=int_a%>
														de
														<%=totalRegistro%>
														registro(s)
													</td>
													<td>
														<%
															if (1 < cantidadPaginaFutu) {
														%>
														<label class="opcion_selecionar"
															style="text-decoration: underline; font-weight: bold;"
															}
														onclick="javscript:cambiarPagina('<%=int_posicionpagina + 1%>');">
															&gt;
														</label>
														<%
															} else {
														%>
														<label class="opcion_selecionar" style="color: silver;"}
														>
															&gt;
														</label>
														<%
															}
														%>
													</td>
													<td>
														<%
															if (2 < cantidadPaginaFutu) {
														%>
														<label class="opcion_selecionar"
															style="text-decoration: underline; font-weight: bold;"
															onclick="javscript:cambiarPagina('<%=int_posicionpagina + cantidadPaginaFutu - 1%>');">
															&gt;&gt;
														</label>
														<%
															} else {
														%>
														<label class="opcion_selecionar" style="color: silver;">
															&gt;&gt;
														</label>
														<%
															}
														%>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<%
										if (!(igualEtiquetaHistorico || igualEtiquetaUDSHistorico)) {
									%>
									<tr>
										<td colspan="3" height="20">
											<%
												if (isAdmin) {
											%>
											<label style="margin-left: 10px;">
												Seleccionar :
											</label>
											<span class="opcion_selecionar"
												onClick="javascript:checkCheckBox(true);">Todas</span>,
											<span class="opcion_selecionar"
												onClick="javascript:checkCheckBox(false);">Ninguna</span>
											<%
												}
											%>
										</td>
									</tr>
									<%
										}
									%>
								</table>
							</td>
						</tr>
						<tr style="display: none;">
							<td height="0">
								<%
									if (igualEtiquetaUDS) {
								%>
								<div style="display: none;">
									<form name="crear"
										action="<%=request.getContextPath()%>/admin/ficha/Crear.action"
										method="post">
										<input type="hidden" name="busquedaNombre"
											value="<%=busquedaNombre%>">
										<input type="hidden" name="busquedaExacta"
											value="<%=busquedaExacta%>">
										<input type="hidden" name="busquedaPeriodo"
											value="<%=busquedaPeriodo%>">
										<input type="hidden" name="busquedaFormacion"
											value="<%=busquedaFormacion%>">
										<input type="hidden" name="busquedaStringFormacion"
											value="<%=busquedaStringFormacion%>">
										<input type="hidden" name="busquedaSede"
											value="<%=busquedaSede%>">
										<input type="hidden" name="busquedaFecha1"
											value="<%=busquedaFecha1%>">
										<input type="hidden" name="busquedaFecha2"
											value="<%=busquedaFecha2%>">
										<input type="hidden" name="busquedaCiclo"
											value="<%=busquedaCiclo%>">
										<input type="hidden" name="busquedaTipo"
											value="<%=tipoBusqueda%>">
										<input type="hidden" name="busquedaDepartamento"
											value="<%=busquedaDepartamento%>">
										<input type="hidden" name="etiqueta"
											value="<%=etiquetaAhora%>">
										<input type="hidden" name="posicionPagina"
											value="<%=posicionPagina%>">
										<input type="hidden" name="cantidadRegistro"
											value="<%=cantidadRegistro%>">
										<select id="select_crear" name="select_cursos"
											multiple="multiple">
										</select>
										<input id="input_crear" name="accionTipo" value="">
									</form>
								</div>
								<%
									} else {
										if (igualEtiquetaPapelera) {
								%>
								<div style="display: none;">
									<form name="restaurar"
										action="<%=request.getContextPath()%>/admin/ficha/Restaurar.action"
										method="post">
										<input type="hidden" name="busquedaNombre"
											value="<%=busquedaNombre%>">
										<input type="hidden" name="busquedaExacta"
											value="<%=busquedaExacta%>">
										<input type="hidden" name="busquedaPeriodo"
											value="<%=busquedaPeriodo%>">
										<input type="hidden" name="busquedaFormacion"
											value="<%=busquedaFormacion%>">
										<input type="hidden" name="busquedaStringFormacion"
											value="<%=busquedaStringFormacion%>">
										<input type="hidden" name="busquedaSede"
											value="<%=busquedaSede%>">
										<input type="hidden" name="busquedaFecha1"
											value="<%=busquedaFecha1%>">
										<input type="hidden" name="busquedaFecha2"
											value="<%=busquedaFecha2%>">
										<input type="hidden" name="busquedaCiclo"
											value="<%=busquedaCiclo%>">
										<input type="hidden" name="busquedaTipo"
											value="<%=tipoBusqueda%>">
										<input type="hidden" name="busquedaDepartamento"
											value="<%=busquedaDepartamento%>">
										<input type="hidden" name="etiqueta"
											value="<%=etiquetaAhora%>">
										<input type="hidden" name="posicionPagina"
											value="<%=posicionPagina%>">
										<input type="hidden" name="cantidadRegistro"
											value="<%=cantidadRegistro%>">
										<select id="select_restaurar" name="select_cursos"
											multiple="multiple">
										</select>
									</form>
								</div>
								<%
									} else {
								%>
								<div style="display: none;">
									<form name="papelera"
										action="<%=request.getContextPath()%>/admin/ficha/Papelera.action"
										method="post">
										<input type="text" name="busquedaNombre"
											value="<%=busquedaNombre%>">
										<input type="text" name="busquedaExacta"
											value="<%=busquedaExacta%>">
										<input type="text" name="busquedaPeriodo"
											value="<%=busquedaPeriodo%>">
										<input type="text" name="busquedaFormacion"
											value="<%=busquedaFormacion%>">
										<input type="text" name="busquedaStringFormacion"
											value="<%=busquedaStringFormacion%>">
										<input type="text" name="busquedaSede"
											value="<%=busquedaSede%>">
										<input type="text" name="busquedaFecha1"
											value="<%=busquedaFecha1%>">
										<input type="text" name="busquedaFecha2"
											value="<%=busquedaFecha2%>">
										<input type="text" name="busquedaCiclo"
											value="<%=busquedaCiclo%>">
										<input type="text" name="busquedaTipo"
											value="<%=tipoBusqueda%>">
										<input type="text" name="busquedaDepartamento"
											value="<%=busquedaDepartamento%>">
										<input type="text" name="etiqueta" value="<%=etiquetaAhora%>">
										<input type="text" name="posicionPagina"
											value="<%=posicionPagina%>">
										<input type="text" name="cantidadRegistro"
											value="<%=cantidadRegistro%>">
										<select id="select_papelera" name="select_cursos"
											multiple="multiple">
										</select>
									</form>
								</div>

								<div style="display: none;">
									<form name="sincronizar"
										action="<%=request.getContextPath()%>/admin/ficha/Sincronizar.action"
										method="post">
										<input type="text" name="busquedaNombre"
											value="<%=busquedaNombre%>">
										<input type="text" name="busquedaExacta"
											value="<%=busquedaExacta%>">
										<input type="text" name="busquedaPeriodo"
											value="<%=busquedaPeriodo%>">
										<input type="text" name="busquedaFormacion"
											value="<%=busquedaFormacion%>">
										<input type="text" name="busquedaStringFormacion"
											value="<%=busquedaStringFormacion%>">
										<input type="text" name="busquedaSede"
											value="<%=busquedaSede%>">
										<input type="text" name="busquedaFecha1"
											value="<%=busquedaFecha1%>">
										<input type="text" name="busquedaFecha2"
											value="<%=busquedaFecha2%>">
										<input type="text" name="busquedaCiclo"
											value="<%=busquedaCiclo%>">
										<input type="text" name="busquedaTipo"
											value="<%=tipoBusqueda%>">
										<input type="text" name="busquedaDepartamento"
											value="<%=busquedaDepartamento%>">
										<input type="text" name="etiqueta" value="<%=etiquetaAhora%>">
										<input type="text" name="posicionPagina"
											value="<%=posicionPagina%>">
										<input type="text" name="cantidadRegistro"
											value="<%=cantidadRegistro%>">
										<select id="select_sincronizar" name="select_cursos"
											multiple="multiple">
										</select>
									</form>
								</div>
								<div style="display: none;">
									<form name="constancia"
										action="<%=request.getContextPath()%>/admin/ficha/ConstanciaCorreo.action"
										method="post">
										<input type="text" name="busquedaNombre"
											value="<%=busquedaNombre%>">
										<input type="text" name="busquedaExacta"
											value="<%=busquedaExacta%>">
										<input type="text" name="busquedaPeriodo"
											value="<%=busquedaPeriodo%>">
										<input type="text" name="busquedaFormacion"
											value="<%=busquedaFormacion%>">
										<input type="text" name="busquedaStringFormacion"
											value="<%=busquedaStringFormacion%>">
										<input type="text" name="busquedaSede"
											value="<%=busquedaSede%>">
										<input type="text" name="busquedaFecha1"
											value="<%=busquedaFecha1%>">
										<input type="text" name="busquedaFecha2"
											value="<%=busquedaFecha2%>">
										<input type="text" name="busquedaCiclo"
											value="<%=busquedaCiclo%>">
										<input type="text" name="busquedaTipo"
											value="<%=tipoBusqueda%>">
										<input type="text" name="busquedaDepartamento"
											value="<%=busquedaDepartamento%>">
										<input type="text" name="etiqueta" value="<%=etiquetaAhora%>">
										<input type="text" name="posicionPagina"
											value="<%=posicionPagina%>">
										<input type="text" name="cantidadRegistro"
											value="<%=cantidadRegistro%>">
										<select id="select_constancia" name="select_cursos"
											multiple="multiple">
										</select>
									</form>
								</div>
								<%
									}
									}
								%>
								<div style="display: none;">
									<form name="busquedaTipo"
										action="<%=request.getContextPath()%>/admin/ficha/Buscar.action"
										method="post">
										<input type="text" name="busquedaNombre"
											value="<%=busquedaNombre%>" id="idbusquedaNombre">
										<input type="text" name="busquedaExacta"
											value="<%=busquedaExacta%>" id="idbusquedaExacta">
										<input type="text" name="busquedaPeriodo"
											value="<%=busquedaPeriodo%>" id="idbusquedaPeriodo">
										<input type="text" name="busquedaFormacion"
											value="<%=busquedaFormacion%>" id="idbusquedaFormacion">
										<input type="text" name="busquedaStringFormacion"
											value="<%=busquedaStringFormacion%>"
											id="idbusquedaStringFormacion">
										<input type="text" name="busquedaSede"
											value="<%=busquedaSede%>" id="idbusquedaSede">
										<input type="text" name="busquedaFecha1"
											value="<%=busquedaFecha1%>" id="idbusquedaFecha1">
										<input type="text" name="busquedaFecha2"
											value="<%=busquedaFecha2%>" id="idbusquedaFecha2">
										<input type="text" name="busquedaCiclo"
											value="<%=busquedaCiclo%>" id="idbusquedaCiclo">
										<input type="text" name="busquedaTipo"
											value="<%=tipoBusqueda%>" id="idbusquedaTipo">
										<input type="text" name="busquedaDepartamento"
											value="<%=busquedaDepartamento%>" id="idbusquedaDepartamento">
										<input type="text" name="etiqueta" value="<%=etiquetaAhora%>"
											id="idEtiqueta">
										<input type="text" name="posicionPagina"
											value="<%=posicionPagina%>" id="idposicionPagina">
										<input type="text" name="cantidadRegistro"
											value="<%=cantidadRegistro%>" id="id_CantidadRegistro">
									</form>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div id="tooltip">
			</div>
			<div id="pie">
				<%@include file="../../comun/pie.jsp"%>
			</div>
			<script type="text/javascript">
				resizeHeight();
			</script>
		</div>
	</body>


</html>
