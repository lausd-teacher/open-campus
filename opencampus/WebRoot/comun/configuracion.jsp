<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="edu.tecsup.lms.util.Constante"%>
<%@ page import="edu.tecsup.lms.modelo.Usuario"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="edu.tecsup.lms.modelo.portal.Servicio"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%
	Usuario usuario = (Usuario) request.getSession().getAttribute(Constante.USUARIO_ACTUAL);
	Collection<Servicio> portal = (Collection<Servicio>) request.getAttribute("portal");
	if (null == portal) {
		portal = new ArrayList<Servicio>(0);
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" /></title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />		
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/tooltip/tooltip.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jPrototype.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/configuracion.js"></script>
		<script type="text/javascript">
			var SERVICIO_FALTA=0;
			var TXT_COMPLETO='<s:text name="portal.configuracion.aviso1"/>';
			var TXT_INCOMPLETO='<s:text name="portal.configuracion.aviso2"/>';
		</script>
		
		
	</head>
	<%@include file="/comun/capas/reloj.jsp"%>
	<body onLoad="mostrarReloj()">
		<div id="contenedor">
			<fmt:setLocale value='${sessionScope["WW_TRANS_I18N_LOCALE"]}' scope="session"/>
			<s:include value="/comun/bienvenida.jsp"></s:include>

			<div id="cuerpo">
				<div id="principal">
					<table border="0" cellpadding="3" cellspacing="0" class="tabla01"
						width="99%" >
						<tr class="fon_tit_curso">
							<td height="17" class="tit_cab" colspan="3"> 
								<s:text name="portal.menu.configuracion"/>
							</td>
						</tr>

						<tr>
							<td align="center" class="moduloAbajo1" colspan="3"
								id="td_mensaje_servicio" height="18">
								<s:text name="portal.configuracion.help"/>
							</td>
						</tr>
						
						<tr style="background-color: #E5EFF8">
							<%
							int fila=0;
							
								int total_falta = 0;
								final int TOTAL_MAXIMO = 3;
								int contador = 0;
								for (Servicio servicio : portal) {
									contador++;
							%>

							<td <%if(contador!=TOTAL_MAXIMO){%>style="border-right: 1px #7EAAD1 solid;"<%} %>>
								<table width="310" border="0">
									<tr>
										<td align="left" width="20">
											<%
												if (0 == servicio.getEstado()
															&& 1 == servicio.getUsuarioEliminar()) {
														total_falta++;
													}
											%>
											<input type="checkbox" value="1"
												<%if (1 == servicio.getEstado()) {%> checked="checked"
												<%} %> onclick="anadirServicio('<%=servicio.getId()%>')"
												id="id_<%=servicio.getId()%>"
												<%if(0==servicio.getUsuarioEliminar()){ %>
												disabled="disabled" <%} %> />

										</td>
										<td align="left" width="20">
											<img
												src="<%=request.getContextPath() + servicio.getImagen()%>"
												alt="<fmt:message key="<%=servicio.getNombre()%>"/>
										</td>
										<td width="270">
											<strong><fmt:message key="<%=servicio.getNombre()%>"/></strong>
										</td>
									</tr>
									
									<tr>
										<td align="left" colspan="1">&nbsp;
											
										</td>
										<td align="left" colspan="2">
											<fmt:message key="<%=servicio.getComentario()%>"/>
										</td>
									</tr>
									
								</table>							
							</td>
							<%
								if (contador >= TOTAL_MAXIMO) {
										contador = 0;
										fila++;
										
							%>							
							</tr>	
							<tr <%if(fila%2==0){ %> style="background-color: #E5EFF8"<%} %> > 							
								<% }								
								%>
								
							<%}
								
							if(contador<TOTAL_MAXIMO){
							for(int u=0;u<TOTAL_MAXIMO-contador;u++){
							contador++;
						%>
						
						<td <%if(contador!=TOTAL_MAXIMO){%>style="border-right: 1px #7EAAD1 solid;"<%} %>>
						
							<table>
								<tr>
									<td width="20" align="left"><input type="checkbox" checked="checked" disabled="disabled" /></td>
									<td width="16" align="center"><img src="<%=request.getContextPath()%>/img/icon_idioma.jpg"/></td>
								  <td align="left" colspan="3"><strong><s:text name="idioma.titulo"></s:text></strong></td>
							  </tr>
								<tr>
									<td>&nbsp;<s:text name="idioma" id="id_idioma"/></td>
									<td align="center"><input type="radio" name="idioma" id="es" 
										onClick="cambiarIdioma('es');"
										<s:if test="#id_idioma=='es'">checked="checked"</s:if> /></td>
									<td width="59"><s:text name="idioma.espanol"></s:text></td>
									<td width="20" align="left"><input type="radio" name="idioma" id="en" 
										onClick="cambiarIdioma('en');"
										<s:if test="#id_idioma=='en'">checked="checked"</s:if> /></td>
							        <td width="225" align="left"><s:text name="idioma.ingles"></s:text></td>
							  </tr>
							</table>						
						</td>
						<%} %>
						</tr>
						<%} %>
												
					</table>
					<table width="100%">
					<%
							if (0 != portal.size()) {
						%>
						<tr>
							
							<td colspan="3" align="right" valign="bottom" height="30px" >
								<input value="<s:text name="portal.configuracion.restaurarconfiguracion"/>" type="button"
									class="form_button" style="width: 150px; cursor: pointer;"
									onclick="reiniciarServicio();">
								&nbsp;&nbsp;
							</td>
						</tr>
						<%
							}
						%>
					</table>
					<br>
				</div>
			</div>
			<div id="pie">
				<%@include file="pie.jsp"%>
			</div>
		</div>
		<script type="text/javascript">
			resizeHeight();
			<%if(0==total_falta){%>
			//document.getElementById('td_mensaje_servicio').innerHTML=TXT_COMPLETO;
			<%}else{%>
			//document.getElementById('td_mensaje_servicio').innerHTML=TXT_INCOMPLETO;
			<%}%>
			SERVICIO_FALTA = '<%=total_falta%>';
		</script>
	</body>
</html>
