<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="/error_action.jsp"%>
<%@page import="edu.opencampus.lms.modelo.Usuario"%>
<%@page import="edu.opencampus.lms.modelo.usuario.Persona"%>
<%@page import="edu.opencampus.lms.util.Formato"%>
<%@page import="edu.opencampus.lms.modelo.usuario.Rol"%>
<%@page import="edu.opencampus.lms.util.Constante"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="contextPath" value='${pageContext.request.contextPath}' />

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:include value="/comun/jslibs.jsp"/>
		
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/passwordmeter.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/cambioclave.js"></script>
		<script language="javascript" type="text/javascript" 
			src='<%=request.getContextPath()%>/comun/misdatos/misdatos.js'></script>
			
	</head>
	<body>
		<div id="container">
		
			<s:include value="/comun/bienvenida.jsp"/>
			
			<div id="body">
			
					<%
						Usuario usuario = (Usuario) request.getSession().getAttribute(Constante.USUARIO_ACTUAL);
						Persona p = usuario.getPersona();
						//Empresa e = usuario.getEmpresa(); 
					%>
			
					<s:include value="/error_message.jsp"/>
			
					<table width="100%" cellpadding="3" cellspacing="0" class="open_table">
						<caption><s:text name="portal.misdatos.titulo"/> </caption> 
						<tbody>
							<tr>
								<td class="border-right" width="100" >
									<b><s:text name="portal.misdatos.contenido.usuario"/></b>
								</td>
								<td class="border-right">
									<%=usuario.getUsuario()%>&nbsp;&nbsp;&nbsp;<font color="#cccccc">(<%=usuario.getIdToString()%>)</font>
								</td>
								<td class="border-right" width="100">
									<b><s:text name="portal.misdatos.contenido.fechanac"/></b>
								</td>
								<td class="border-right">
									<%=Formato.calendarToString(p.getFecnacimiento(),Constante.FECHA_DIA_MES_ANO)%>
								</td>
								<td rowspan="6" align="center" valign="top" width="89">
									<img src="<%=request.getContextPath() %>/MiFoto.action" width="85" border="0"> 
								</td>
							</tr>
							<tr class="line">
								<td class="border-right">
									<b><s:text name="portal.misdatos.contenido.nombres"/></b>
								</td>
								<td class="border-right">
									<%=Formato.formatoTexto(p.getNomuno())%>
									<%=Formato.formatoTexto(p.getNomdos())%>
								</td>
								<td class="border-right">
									<b><s:text name="portal.misdatos.contenido.telefono"/></b>
								</td>
								<td class="border-right" id="telefono">
									<%=Formato.formatoTextoNull(p.getTeldomicilio())%>
								</td>
							</tr>
							<tr >
								<td class="border-right">
									<b><s:text name="portal.misdatos.contenido.paterno"/></b>
								</td>
								<td class="border-right">
									<%=Formato.formatoTexto(p.getApepaterno())%>
								</td>
								<td class="border-right">
									<b><s:text name="portal.misdatos.contenido.celular"/></b>
								</td>
								<td class="border-right">
									<%=Formato.formatoTextoNull(p.getTelcelular())%>&nbsp;
								</td>
							</tr>
							<tr  class="line">
								<td class="border-right">
									<b><s:text name="portal.misdatos.contenido.materno"/></b>
								</td>
								<td class="border-right">
									<%=Formato.formatoTexto(p.getApematerno())%>
								</td>
								<td class="border-right">
									<b><s:text name="portal.misdatos.contenido.direccion"/></b>
								</td>
								<td class="border-right">
									<%=Formato.formatoTextoNull(p.getDirdomicilio())%>
								</td>

							</tr>
							<tr >
								<td class="border-right">
									<b><s:text name="portal.misdatos.contenido.sexo"/></b>
								</td>
								<td class="border-right">
									<%=Formato.formatoTexto(p.getSexoCompleto())%>
								</td>
								<td class="border-right">
									<b><s:text name="portal.misdatos.contenido.ubicacion"/></b>
								</td>
								<td class="border-right">
									<%=Formato.formatoTexto(p.getUbigeo().getNombreCompleto())%>
								</td>
							</tr>
							<tr  class="line">
								<td class="border-right" valign="top">
									<b><s:text name="portal.misdatos.contenido.grupo"/></b>
								</td>
								<td class="border-right" valign="top">
									<%
										for (Rol rol : usuario.getRoles()) {
											out.println(rol.getNombre() + "<br>");
										}
									%>&nbsp;
								</td>
								<td  class="border-right" valign="top">
									<b><s:text name="portal.misdatos.contenido.correo"/></b>
								</td>
								<td  class="border-right" valign="top">
									<div id="theMail"><%
												String[] correos = new String[0];
												if (p.getEmail() != null) {
													correos = p.getEmail().split(",");
													if (correos.length == 1) {
														out.println(correos[0].trim());
													} else {
														for (int u = 0; u < correos.length; u++) {
															out.println(correos[u].trim() + "<br/>");
														}
													}
												}
											%></div>
								</td>
							</tr>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="5" align="right">
										<input value="<s:text name="portal.misdatos.botones.solicitar"/>"
											type="button" onClick="verPanelCambioDatos(this)" />
									
										<input value="<s:text name="portal.misdatos.botones.cambiar"/>" type="button"
											onClick="verPanelCambioPassword();"/>
									
										<input onClick="verPanelCambioCorreo();"
											type="button" value="<s:text name="portal.misdatos.botones.modificar"/>" />
									</td>
								</tr>
							</tfoot>
						</table>
							
							
			</div>
			
			<s:include value="/comun/pie.jsp"/>
			
		</div>
		
		<!--  *******************************  CAMBIAR CLAVE *********************************** -->
		
		<div id="form_div_reinicio" style="display: none; width: 260px; margin-left: -130px;" class="modal" >
			<form action="<%=request.getContextPath()%>/DatosClave.action" method="post">
			<table width="100%" cellpadding="3" cellspacing="0" class="open_table noborder">
				<caption><s:text name="portal.misdatos.botones.cambiar.titulo"/></caption>
				<tbody>
					<tr>
						<td width="120" align="right">
							<b><s:text name="portal.misdatos.botones.cambiar.contenido.clave_actual"/></b>
						</td>
						<td>
							<input type="password" size="17" maxlength="32"
								id="form_pass1_reinicio" value="" class="required">
						</td>
					</tr>
					<tr>
						<td align="right">
							<b><s:text name="portal.misdatos.botones.cambiar.contenido.nueva_clave"/></b>
						</td>
						<td>
							<input type="password" size="17" maxlength="32"
								id="form_pass2_reinicio" value=""  class="required"
								onkeyup="validarClave(this.value);">
						</td>
					</tr>
					<tr>
						<td align="right">
							<b><s:text name="portal.misdatos.botones.cambiar.contenido.confirmar_nueva_clave"/></b>
						</td>
						<td>
							<input type="password" size="17" maxlength="32" name="clave"
								id="form_pass3_reinicio" class="required">
						</td>
					</tr>
					<tr>
						<td align="right">
							<b><s:text name="portal.misdatos.botones.cambiar.contenido.nivel_seguridad"/></b>
						</td>
						<td>
							<div id="password_level" style="display:none;"></div>&nbsp;
						</td>
					</tr>
					<tr style="display: none">
						<td colspan="2" align="center">
							<label id="form_error" style="color: red;"></label>
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="2" align="right">
							<input type="button" value="<s:text name="portal.misdatos.botones.cambiar.botones.cancelar"/>" 
								onclick="ocultarPanelCambioPassword();">
							<input type="submit" value="<s:text name="portal.misdatos.botones.cambiar.botones.aceptar"/>" 
								onclick="javascript:passwordnuevo();">
						</td>
					</tr>
				</tfoot>
			</table>
			</form>
		</div>
		
		FUSIONAR MISDATOS CON CAMBIOCLAVE.JS
		<!-- ************************************  MODIFICAR DATOS *************************************************** -->
		
		<div id="form_div_datos">
			<form action="<%=request.getContextPath()%>/SolicitarCambioDatos.action" method="post">
				<table width="500" cellpadding="3" cellspacing="0" class="open_table">
					<caption><s:text name="portal.misdatos.botones.solicitar.titulo"/></caption>
					<colgroup>
						<col width="80"/>
						<col/>
						<col width="80"/>
						<col />
					</colgroup>
					<thead>
						<tr>
							<!-- td width="80"></td><td></td><td width="80"></td><td></td></tr><tr-->
							<td colspan="4" align="center"><b><s:text name="portal.misdatos.botones.solicitar.comentario"/></b></td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
								<b><s:text name="portal.misdatos.botones.solicitar.contenido.nombres"/></b>
							</td>
							<td>
								<input style="font-size: 9pt" name="nombres" size="20"
									value="<%=Formato.formatoTexto(p.getNomuno())%> <%=Formato.formatoTexto(p.getNomdos())%>" />
							</td>
							<td>
								<b><s:text name="portal.misdatos.botones.solicitar.contenido.telefono"/></b>
							</td>
							<td>
								<input style="font-size: 9pt" name="telefono" type="text"
									size="20"
									value="<%=Formato.formatoTextoNull(p.getTeldomicilio())%>" />
							</td>
							<%-- <td><span class="textstatic">Fecha Nacimiento</span></td>
							<td><input style="font-size:9pt" name="fec_nac" size="10" value="<%=Formato.getStringDeDate(usuarioDato.getFechaNacimiento())%>"/></td>--%>
						</tr>
						<tr class="line">
							<td>
								<b><s:text name="portal.misdatos.botones.solicitar.contenido.paterno"/></b>
							</td>
							<td>
								<input style="font-size: 9pt" name="paterno" size="20"
									value="<%=Formato.formatoTexto(p.getApepaterno())%>" />
							</td>
							<td>
								<b><s:text name="portal.misdatos.botones.solicitar.contenido.celular"/></b>
							</td>
							<td>
								<input style="font-size: 9pt" name="celular" type="text"
									size="12"
									value="<%=Formato.formatoTextoNull(p.getTelcelular())%>" />
							</td>
						</tr>
						<tr>
							<td>
								<b><s:text name="portal.misdatos.botones.solicitar.contenido.materno"/></b>
							</td>
							<td align="left">
								<input style="font-size: 9pt" name="materno" size="20"
									value="<%=Formato.formatoTexto(p.getApematerno())%>" />
							</td>
							<td colspan="2">&nbsp;</td>
						</tr>
						<%--<tr>
						<td><span class="textstatic">Ubicaci&oacute;n</span></td>
							<td><input name="ubicacion" style="font-size:9pt" type="text" size="40" value="<%=Formato.formatoTexto(usuario.getUsuarioDato().getUbigeo().getNombreCompleto())%>"/></td>
							<td><span class="textstatic">Sexo</span></td>
							<td><input <%if(usuarioDato.getSexo().equals("M"))out.print("checked");%> type="radio" name="sexo" value="M">Masculino<input <%if(usuarioDato.getSexo().equals("F"))out.print("checked");%> type="radio" name="sexo" value="F">Femenino						
						</tr>--%>
						<tr class="line">
							<td>
								<b><s:text name="portal.misdatos.botones.solicitar.contenido.direccion"/></b>
							</td>
							<td  colspan="3">
								<input name="direccion" style="font-size: 9pt;width: 318px;"
									value="<%=Formato.formatoTextoNull(p.getDirdomicilio())%>" />
							</td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="4" align="right">
								<input onClick="noVerCambioDatos(this)"
									type="button" value="<s:text name="portal.misdatos.botones.solicitar.botones.cancelar"/>">
								<input type="submit" value="<s:text name="portal.misdatos.botones.solicitar.botones.enviar"/>">
							</td>
						</tr>
					</tfoot>
				</table>
			</form>
		</div>
		
		<!-- ********************************************* CORREO ******************************************************* -->
		
		<div id="form_div_correo">
			<table cellpadding="0" cellspacing="0" class="bor_tabla" width="350"
				background="#FFFFFF" border="0"
				style="background-color: #FFFFFF; border: 2px outset;">
				<tr class="fon_cab" height="20">
					<td class="tit_cab" colspan="4">
						<s:text name="portal.misdatos.botones.modificar.titulo"/>
					</td>
				</tr>
				<tr height="22">
					<td colspan="2" width="100" align="left">
						<span class="textstatic"><b style="padding-left: 13px;"><s:text name="portal.misdatos.botones.modificar.contenido.correo"/>
						</b> </span>
					</td>
					<td colspan="2" width="250">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<textarea rows="5" cols="53" id="newMail"><%
								if (p.getEmail() != null) {
									out.print(p.getEmail());
								}
							%></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<strong><s:text name="portal.misdatos.botones.modificar.comentario"/></strong>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<input class="form_button" onClick="ocultarPanelCambioCorreo()"
							type="button" value="<s:text name="portal.misdatos.botones.modificar.botones.cancelar"/>">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input class="form_button" type="button" value="<s:text name="portal.misdatos.botones.modificar.botones.grabar"/>" onclick="grabar('<%=request.getContextPath() %>')">
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
		</div>
		
	</body>
</html>
