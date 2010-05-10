<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="edu.opencampus.lms.modelo.AulaVirtual"%>
<%@page import="edu.opencampus.lms.modelo.Usuario"%>
<%@page import="edu.opencampus.lms.util.Constante"%>
<%@page import="edu.opencampus.lms.modelo.Periodo"%>
<%@page import="java.util.Collection"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" /></title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/js/jscalendar/calendar-style.css" />
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jPrototype.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jscalendar/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jscalendar/calendar-es.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jscalendar/calendar-setup.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/util.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/admin/aulavirtual/configuracion.js"></script>
		
	</head>
	<body>
		<%
			Usuario usuario = (Usuario)session.getAttribute(Constante.USUARIO_ACTUAL);
			AulaVirtual aula =  usuario.getAulaActual();
		%>
		<div id="pop_up">
			<div id="prin_01">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td>
							<strong>Curso : <%=aula.getCurso().getNombre() %> </strong>
						</td>
						<td width="5%">
							<a href="#" class="salir" onclick="window.close()">Cerrar</a>
						</td>
						<td width="5%">
							<a href="#" class="salir" onclick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13" height="13" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>
			<div id="pop_cuerpo">
					<table width="500" align="center" cellpadding="3" cellspacing="0" bgcolor="#FFFFFF"
						class="bor_tabla">
						<tr align="left" class="fon_cab">
							<td colspan="2" class="tit_cab">
								Configuraci&oacute;n
								<b> </b>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<div id="inicio" style="padding-top: 10px;">
									<table width="95%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="100%" class="lin_azul">
												<table border="0" align="left" cellpadding="0" cellspacing="0">
													<tr>
														<td width="3">
															<img src="<%=request.getContextPath()%>/img/fon_tit_curso_01.jpg" width="3"
																height="21" />
														</td>
														<td class="fon_tit_curso">
															Fechas de ejecuci&oacute;n
														</td>
														<td width="22">
															<img src="<%=request.getContextPath()%>/img/fon_tit_curso_03.jpg" width="22"
																height="21" />
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td
												style="border-bottom: 1px solid #4268C9;border-left:1px solid #4268C9; border-right:1px solid #4268C9;"
												width="100%">
												<div id="otros">
													<%
														Periodo periodo = aula.getPeriodo();
													 %>
													<table width="100%" border="0">
														<tr>
															<td align="right" width="40%">
																Periodo :
															</td>
															<td>
																&nbsp;
															</td>
															<td align="left" width="60%">
																<span id="span_sede"> <b>
																	<%if(periodo.getPersonalizado()==Constante.ESTADO_INACTIVO){ %>
																	<%=periodo.getNombre() %>
																	<%}else{ %>
																	Personalizado
																	<%} %>
																</b></span>
															</td>
														</tr>
														<tr>
															<td align="right" >
																Fecha de inicio :
															</td>
															<td>
																&nbsp;
															</td>
															<td align="left" >
																<span id="span_fecha1"><%=periodo.getFechaInicioToString()%> </span>
															</td>
														</tr>

														<tr>
															<td align="right">
																Fecha de fin :
															</td>
															<td>
																&nbsp;
															</td>
															<td align="left">
																<span id="span_fecha2"><%=periodo.getFechaFinToString()%> </span>
															</td>
														</tr>
														<tr>
															<td align="right">
																Fecha de Inicio de Edici&oacute;n :
															</td>
															<td>
																&nbsp;
															</td>
															<td align="left">
																<span id="span_edicion"><%=periodo.getFechaEdicionToString() %></span>
															</td>
														</tr>
														<tr>
															<td align="right">
																Fecha de Inicio de Revisi&oacute;n :

															</td>
															<td>
																&nbsp;
															</td>
															<td align="left">
																<span id="span_revision"><%=periodo.getFechaRevisionToString()%> </span>&nbsp;d&iacute;as.
															</td>
														</tr>
														<tr>
															<td align="right">
																&nbsp;
															</td>
															<td>
																&nbsp;
															</td>
															<td align="right">
																<input type="button" value="Modificar" class="form_button"
																	onclick="displayModificarPeriodo(this);" style="width: 100px">
															</td>
														</tr>
														
														<tr>
															<td colspan="3">
															
																<form action="<%=request.getContextPath() %>/admin/aulavirtual/Configurar.action" method="post" onsubmit="return validarConfiguracion(this);">
																	<table class="tabla01" id="form_periodo" style="display: none;" width="100%">
																		<tr>
																			<td align="right" width="30%">
																				Periodo Nuevo :
																			</td>
																			<td>
																				&nbsp;
																			</td>
																			<td align="left" width="65%" colspan="6">
																				<select onchange="displayModificarPeriodoPersonalizado(this);" name="idperiodo">
																					<%
																						Collection<Periodo> periodos = (Collection<Periodo>)request.getAttribute("periodos");
																						for(Periodo per : periodos){
																							
																					%>
																							<option value="<%=per.getIdPeriodo()%>" 
																								<%if(per.getIdPeriodo() == periodo.getIdPeriodo()){ %>
																									selected="selected"
																								<%} %>>
																								<%=per.getNombre()%>
																							</option>
																					<%
																						}						
																					%>
																					<option value="0">
																						Personalizado
																					</option>
																					
																				</select>
																			</td>
																		</tr>
																		
																		<tr id="form_nuevo_periodo1" style="display:none;">
																			<td align="right">
																				Fecha de inicio :
																			</td>
																			<td>
																				&nbsp;
																			</td>
																			<td align="left" >
																				<input type="text" id="finicio" name="finicio" readonly="readonly" class="form_text" style="width: 80px">
																			</td>
																			<td>
																				&nbsp;
																			</td>
																			<td align="right" >
																				D&iacute;as de Edici&oacute;n :
																			</td>
																			<td>
																				&nbsp;
																			</td>
																			<td align="left" >
																				<select name="dedicion" style="width:40px">
																					<%
																						for(int i = 1; i <= 31; i++){
																					%>
																							<option value="<%=i%>" 
																								<%if(i == 7){ %>
																									selected="selected"
																								<%} %>>
																								<%=i%>
																							</option>
																					<%
																						}						
																					%>
																				</select>
																			</td>
																		</tr>
				
																		<tr id="form_nuevo_periodo2" style="display:none;">
																			<td align="right">
																				Fecha de fin :
																			</td>
																			<td>
																				&nbsp;
																			</td>
																			<td align="left">
																				<input type="text" id="ffin" name="ffin" readonly="readonly" class="form_text" style="width: 80px">
																			</td>
																			<td>
																				&nbsp;
																			</td>
																			<td align="right" >
																				D&iacute;as de Revsi&oacute;n :
																			</td>
																			<td>
																				&nbsp;
																			</td>
																			<td align="left" >
																				<select name="drevision" style="width:40px">
																					<%
																						for(int i = 1; i <= 31; i++){
																					%>
																							<option value="<%=i%>" 
																								<%if(i == 7){ %>
																									selected="selected"
																								<%} %>>
																								<%=i%>
																							</option>
																					<%
																						}						
																					%>
																				</select>
																			</td>
																		</tr>
																		
																		<tr style="background-color: #7EAAD1">
																			<td align="right" colspan="7">
																				<input type="submit" value="Guardar" class="form_button"">
																			</td>
																		</tr>
																	</table>
																</form>
																
															</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
									</table>
								</div>
								<script language="JavaScript" type="text/javascript">
									Calendar.setup({inputField:"finicio", ifFormat:"%d-%m-%Y", showsTime:false, singleClick:true});
									Calendar.setup({inputField:"ffin", ifFormat:"%d-%m-%Y", showsTime:false, singleClick:true});
								</script>
							</td>
						</tr>
						
					</table>
			</div>
			<div id="pie">
				&nbsp;
			</div>
		</div>
	</body>
</html>
