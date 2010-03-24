<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="../../error_action.jsp" %>
<%@  page import="edu.tecsup.lms.modelo.Usuario"%>
<%@  page import="edu.tecsup.lms.modelo.usuario.Persona"%>
<%@  page import="edu.tecsup.lms.modelo.usuario.Rol"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%@  page import="edu.tecsup.lms.util.Formato"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Usuario usuario = (Usuario)request.getAttribute("usuario");
	Persona p = usuario.getPersona();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title><s:text name="titulo.campus.virtual" />
		</title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script type='text/javascript'
			src='<%=request.getContextPath()%>/dwr/util.js'> </script>
		<link href="<%=request.getContextPath()%>/comun/misdatos/misdatos.css"
			rel="stylesheet" type="text/css">
		<script type='text/javascript'
			src='<%=request.getContextPath()%>/admin/usuario/admin_usuario_datos.js'> </script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/tooltip/tooltip.js"></script>
	</head>

	<body>
		<div id="pop_up" style="width: 100%;">
			<div id="prin_01" style="width: 100%;">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td>
							<strong>Datos Personales </strong>
						</td>
						<td width="5%">
							<a href="#" class="salir" onclick="window.close()">Cerrar</a>
						</td>
						<td width="5%">
							<a href="#" class="salir" onclick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13"
									height="13" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>
			<div id="pop_cuerpo" style="width: 98%;">
				<div id="message"></div>
				<table width="100%" cellpadding="3" cellspacing="0" class="open_table">
					<caption>Generales </caption> 
					<tbody>
					<tr>
						<td width="100">
							<strong>Usuario :</strong>
						</td>
						<td>
							<%=usuario.getUsuario()%>&nbsp;&nbsp;&nbsp;
							<label class="link"
								onClick="javascript:cambioPassword('<%=usuario.getId()%>');">
								Cambiar Clave
							</label>
						</td>
						<td width="100">
							<strong>Fecha Nac. : </strong>
						</td>
						<td>
							<%=Formato.calendarToString(p.getFecnacimiento(),Constante.FECHA_DIA_MES_ANO)%>
						</td>
						<td rowspan="6" align="center" valign="top" width="90">
							<img src="<%=request.getContextPath() %>/VerFoto.action?id=<%=usuario.getId()%>" 
								width="85" border="0" class="open_border">
							<table cellpadding="0" cellspacing="0" border="0" width="90">
								<tr>
									<td><img src="<%=request.getContextPath()%>/img/icon_mail_send.gif" border="0" alt="Enviar Correo"
										style="cursor:pointer" onclick="nuevoMensaje('<%=request.getContextPath()%>/comun/buzon/NuevoMensaje.action?destino=<%=usuario.getUsuario()%>')"></td>
									<%if(false){ %>
									<td><img src="<%=request.getContextPath()%>/img/icon_mail_send.gif" border="0" alt="Ver Cursos"
										style="cursor:pointer" onclick=""></td>
									<%} %>
									<td><a href="javascript:void(0);" onclick="nuevoMensaje('<%=request.getContextPath()%>/comun/buzon/NuevoMensaje.action?destino=<%=usuario.getUsuario()%>')">Enviar E-mail</a></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr class="line">
						<td>
							<strong>Nombres :</strong>
						</td>
						<td>
							<%=Formato.formatoTexto(p.getNomuno())%>
							<%=Formato.formatoTexto(p.getNomdos())%>
						</td>
						<td>
							<strong>Teléfono : </strong>
						</td>
						<td id="telefono">
							<%=Formato.formatoTextoNull(p.getTeldomicilio())%>
						</td>
					</tr>
					<tr>
						<td>
							<strong>Paterno :</strong>
						</td>
						<td>
							<%=Formato.formatoTexto(p.getApepaterno())%>
						</td>
						<td align="left">
							<strong>Celular : </strong>
						</td>
						<td align="left" id="celular">
							<%=Formato.formatoTextoNull(p.getTelcelular())%>&nbsp;
						</td>
					</tr>
					<tr class="line">
						<td>
							<strong>Materno :</strong>
						</td>
						<td>
							<%=Formato.formatoTexto(p.getApematerno())%>
						</td>
						<td align="left" >
							<strong>Ubicación : </strong>
						</td>
						<td align="left" >
							<%=Formato.formatoTexto(p.getUbigeo().getNombreCompleto())%>
						</td>
					</tr>
					<tr>
						<td>
							<strong >Sexo : </strong>
						</td>
						<td>
							<%=Formato.formatoTexto(p.getSexoCompleto())%>
						</td>
						<td>
							<strong >Dirección: </strong>
						</td>
						<td>
						<%=Formato.formatoTextoNull(p.getDirdomicilio())%>
						</td>
					</tr>
					<tr class="line">
						<td>
							<strong >Roles:</strong>
						</td>
						<td>
							<%
							if(usuario.getRoles().isEmpty())out.print("&nbsp;");
							for (Rol rol : usuario.getRoles()) {
								out.println(rol.getNombre() + "<br>");
							}
							%>
						</td>
						<td align="left">
							<strong>Correo : </strong>
						</td>
						<td align="left">
									<%
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
									%> 
						</td>
					</tr> 
					</tbody>
				</table>
			</div>
		</div>
		<div id="pie" style="width: 100%;">
			&nbsp;
		</div>
	</body>
</html>
