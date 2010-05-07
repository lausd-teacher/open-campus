<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}' />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow-x: hidden;">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" /></title>
		<s:include value="/comun/jslibs.jsp"/>
		
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/InnerDiv.js"></script>
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/comun/agenda/agenda.js"></script>	
			
		<link rel="stylesheet" type="text/css"
			href="<c:out value='${contextPath}'/>/js/jscalendar/campus.css" />
		<script type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jscalendar/calendar.js"></script>
		<script type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jscalendar/calendar-es.js"></script>
		<script type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jscalendar/calendar-setup.js"></script>
				
	</head>
	<body onresize="resize()"  onscroll="scrolling()" style="margin-top: 8px;">
		<div id="pop_up" style="width: 300px;">
			<div id="prin_01">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="93%">
							<strong>Agenda</strong>
						</td>
						<td width="5%">
							<a href="#" class="salir" onClick="window.print()">Imprimir</a>
						</td>
						<td width="3%">
							<a href="javascript:void(0);" class="salir" onClick="window.print()"><img
									src="<%=request.getContextPath()%>/img/impresora.gif"
									width="13" height="13" border="0" />
							</a>
						</td>
						<td width="2%">
							|
						</td>
						<td width="4%">
							<a href="#" class="salir" onClick="window.close()">Cerrar</a>
						</td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13"
									height="13" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>
			<div id="pop_cuerpo">
					
				
				<table width="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed;">
					<tr>
						<td valign="top"  align="left">
							<div id="cv_agenda" style="width: 99%;"></div>
						</td>
					</tr>
				</table>
			
				<table id="agenda_table" width="100%" border="0" cellpadding="0" cellspacing="0" class="bor_tabla" style="display:none;">
					<tr>
						<td align="right" class="calendario listStatus">
							<span id="agenda_curDate" class="calendario listStatusText">28 de Noviembre</span>
							<span style="float: right;"><input type="button" value="Agregar" class="form_button" 
								onclick="mostrarAgendaForm()"></span>
						</td>
					</tr>
					<tr>
						<td>
							<div id="agenda_events"></div>
						</td>
					</tr>
				</table>
				
				<script type="text/javascript">
					 	var agendaObject = Calendar.setup(
					    {
					      flat         : "cv_agenda",
					      flatCallback : dateChanged,
					      weekNumbers  : false,
					      showOthers   : true,
					      date		   : new Date(<fmt:formatDate  value="${now}" type="both" pattern="yyyy,MM,dd,HH,mm,ss" />),
					      step		   : 1,
					      //showsTime	   : true,
					      //showRowHead  : false,
					      showToolTips : false, 
					      range        : [2000, 2020],
					      changeFirstDay  : false,
					      specialDaysFunc : ourDateStatusFunc
					      //specialDay   : '25-12-2007/28-07-2007/30-01-2008'
					    }
					  );
				</script>
				
				
				<div id="blocker" style="display:none; position: absolute; top: 0px; left: 0px; background-color:#000000; width:0px;height:0px; 
								filter:alpha(opacity=20); -moz-opacity:.20; opacity:.20;"></div>
								
				<div id="agenda_ver" class="calendario showEvent drsElement">
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						style="table-layout: fixed;">
						<tr>
							<td colspan="4">
								<div id="title_agenda_ver" class="drsMoveHandle" style="cursor: move;">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="5" class="fon_curso">
												&nbsp;
											</td>
											<td class="fon_tit_curso" align="left"
												style="padding-left: 5px; color: #FFFFFF;">
												<strong>Evento</strong>
											</td>
											<td width="14" height="20" class="fon_tit_curso" align="right"
												style="cursor: pointer;">
												<img src="<%=request.getContextPath()%>/img/salir_chat.gif"
													onclick="xHide('agenda_ver'); xHideD('blocker');">
											</td>
											<td width="3" class="fon_cab_curso_derecha"
												style="background-position: right;">
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="4" class="borde_noticas" bgcolor="#FFFFFF">
								<table width="100%" border="0" cellpadding="0" cellspacing="0"
									style="table-layout: fixed;">
									<tr>
										<td style="padding-left: 5px;" class="calendario titleEvent"
											align="left">
											<img src="<%=request.getContextPath()%>/img/agenda/evento.gif"
												alt="Evento" />
											<span id="agenda_ver_head"></span> -
											<span id="agenda_ver_head_hour"></span>
										</td>
									</tr>
									<tr class="fon_color01">
										<td height="20" align="left"
											style="padding: 3px 5px 3px 5px; font-weight: bold;">
											<span id="agenda_ver_title"></span>
										</td>
									</tr>
									<tr>
										<td>
											<div id="agenda_ver_body">
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
				
					</table>
				</div>
				
				<div id="agenda_form" class="calendario formEvent drsElement">
					<form id="evento_form" onsubmit="return guardarEvento(this);" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
							<tr>
								<td colspan="4">
									<div id="title_agenda_form" class="drsMoveHandle" style="cursor: move;">
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td width="5" class="fon_curso">
													&nbsp;
												</td>
												<td class="fon_tit_curso" align="left" style="padding-left: 5px; color: #FFFFFF;">
													<strong>Evento</strong>
												</td>
												<td width="14" height="20" class="fon_tit_curso" align="right" style="cursor: pointer;">
													<img src="<%=request.getContextPath()%>/img/salir_chat.gif" onclick="xHide('agenda_form'); xHideD('blocker');">
												</td>
												<td width="3" class="fon_cab_curso_derecha" style="background-position: right;">
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="4" class="borde_noticas" bgcolor="#FFFFFF">
									<table width="100%" border="0" cellpadding="3" cellspacing="0"
										style="table-layout: fixed; padding-left: 5px;">
										<tr>
											<td width="50" align="left">
												<strong>T&iacute;tulo:</strong>
											</td>
											<td align="left">
												<input name="sumilla" type="text" size="61" maxlength="64" style="width: 280px;" />
												<input name="cmd" type="hidden" value="0" />
											</td>
										</tr>
										<tr>
											<td align="left" valign="top">
												<strong>Detalle:</strong>
											</td>
											<td align="left" rowspan="2">
												<textarea name="detalle" cols="62" rows="4" style="width: 280px;" 
													onkeydown="cuentaCaracteres(this)" onkeyup="cuentaCaracteres(this)"></textarea>
											</td>
										</tr>
										<tr>
											<td align="left" valign="bottom">
												<strong>Limite:</strong><br/><input type="text" name="counter" id="counter" value="512" size="2" readonly="readonly"/>
											</td>
										</tr>
										<tr>
											<td align="left" class="lin_azul">
												<strong>Hora:</strong>
											</td>
											<td align="left" class="lin_azul">
												<table width="100%" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
													<tr>
														<td width="26" align="left">
															<input type="text" id="agenda_hora" name="hora" size="2" maxlength="2" value="00"
																style="width: 20px;" onchange="if(!this.value.isEmpty() && this.value.isInteger() && this.value.length<2) this.value='0'+this.value"/>
														</td>
														<td width="10" align="left">
															<div class="calendario buttonControl" onclick="incrementar('agenda_hora',24)"><img 
																src="<%=request.getContextPath()%>/img/agenda/up.jpg"></div>
															<div class="calendario buttonControl" onclick="decrementar('agenda_hora',24)"><img 
																src="<%=request.getContextPath()%>/img/agenda/dw.jpg"></div>
														</td>
														<td width="10" align="center">
															:
														</td>
														<td width="26" align="left">
															<input type="text" id="agenda_minuto" name="minuto" size="2" maxlength="2" value="00"
																style="width: 20px;" onchange="if(!this.value.isEmpty() && this.value.isInteger() && this.value.length<2) this.value='0'+this.value" />
														</td>
														<td width="10" align="left">
															<div class="calendario buttonControl" onclick="incrementar('agenda_minuto',60)"><img 
																src="<%=request.getContextPath()%>/img/agenda/up.jpg"></div>
															<div class="calendario buttonControl" onclick="decrementar('agenda_minuto',60)"><img 
																src="<%=request.getContextPath()%>/img/agenda/dw.jpg"></div>
														</td>
														<td width="40" align="right" style="display: none;">
															<input type="checkbox" name="notificar" value="1" onclick="changeNotify(this)" />
														</td>
														<td width="60" align="left" style="display: none;">
															<b>Notificar</b>
														</td>
														<td id="agenda_mAntesInput" width="37" align="left" style="display: none; visibility: hidden;">
															<input type="text" name="minutoAntes" size="2" maxlength="3" value="0" style="width: 25px;" />
														</td>
														<td width="110" id="agenda_mAntesSelect" align="left" style="display: none; visibility: hidden;"">
															<select name="escala">
																<option value="m">minutos antes</option>
																<option value="h">horas antes</option>
																<option value="d">dias antes</option>
															</select>
														</td>
														<td></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td align="center" colspan="2">
												<input type="reset" class="form_button" value="Cancelar" style="width: 80px;" onclick="xHide('agenda_form'); xHideD('blocker');" />
												&nbsp; &nbsp;
												<input type="submit" class="form_button" value="Guardar" style="width: 80px;" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</form>
				</div>
					
			</div>
			<div id="pie">
				&nbsp;
			</div>
		</div>
		
		<div id="blocker" style="display:none; position: absolute; top: 0px; left: 0px; background-color:#000000; width:0px;height:0px; 
				filter:alpha(opacity=50); -moz-opacity:.50; opacity:.50;"></div>
				
	</body>
</html>
