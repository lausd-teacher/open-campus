<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="java.util.Collection"%>
<%@page import="edu.opencampus.lms.modelo.portal.Servicio"%>
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
			src="<%=request.getContextPath()%>/js/jPrototype.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/scriptaculous/scriptaculous.js?load=effects,builder,dragdrop"></script>
		<script type="text/javascript" language="javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/tooltip/tooltip.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/admin/portal/configuracion.js"></script>
	</head>
	<body>
		<div id="contenedor">
			<s:include value="/comun/bienvenida.jsp"></s:include>
			<div id="cuerpo" style="z-index: 0;">
				<div id="principal" style="z-index: -1;">
					<div id="principal_col_0"
						style="width: 242px; float: left; min-height: 50px; padding-bottom: 10px;">
					</div>
					<div id="principal_col_1"
						style="width: 242px; float: left; min-height: 50px; padding-bottom: 10px;">
					</div>
					<div id="principal_col_2"
						style="width: 242px; float: left; min-height: 50px; padding-bottom: 10px;">
					</div>
					<div id="principal_col_3"
						style="width: 242px; float: left; min-height: 50px; padding-bottom: 10px;">
					</div>
				</div>
			</div>
			<div id="pie">
				<s:include value="/comun/pie.jsp" />
			</div>
			<s:iterator value="servicios">
				<div id="<s:property value="id"/>"
					style="vertical-align: top; width: 241px; padding-bottom: 5px; float: left; padding-top: 5px;">
					<table width="230" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="23" valign="bottom" class="lin_azul ">
								<table cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td valign="bottom">
											<table border="0" align="left" cellpadding="0"
												cellspacing="0" style="cursor: move;"
												id="tabla_<s:property value="id"/>">
												<tr>
													<td width="3" height="23" valign="top">
														<img
															src="<%=request.getContextPath()%>/img/fon_tit_curso_01.jpg"
															width="3" height="21" style="cursor: move;" />
													</td>
													<td class="fon_tit_curso" align="left">

														<img
															src="<%=request.getContextPath()%><s:property value="imagen"/>"
															alt="<fmt:message key="${nombre}"/>" style="cursor: move;" />
													</td>
													<td class="fon_tit_curso" width="85" align="left">
														<div
															style="padding-left: 5px; padding-right: 10px; font-weight: bold;">
															<fmt:message key="${nombre}"/>
														</div>
													</td>
													<td width="22" height="23" valign="top">
														<img
															src="<%=request.getContextPath()%>/img/fon_tit_curso_03.gif"
															width="22" height="21" style="cursor: move;" />
													</td>
												</tr>
											</table>
										</td>
										<td width="15">
											&nbsp;
										</td>
										<td width="15">
											&nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr id="<s:property value="id"/>_tr">
							<td>
								<table width="100%" border="0" cellpadding="0" cellspacing="0"
									style="table-layout: fixed; border-bottom: 1px solid #cccccc; border-left: 1px solid #cccccc; border-right: 1px solid #cccccc;">
									<tr height="20">
										<td class="textstatic" align="left">
											Default maximizado:
										</td>
										<td width="20">
											<input type="checkbox"
												onclick="modificarCheck(this,'1','<s:property value="id"/>');"
												<s:if test="1==visible">checked="checked"</s:if>>
										</td>
									</tr>
									<tr height="20">
										<td class="textstatic" align="left">
											Default existe:
										</td>
										<td>
											<input type="checkbox"
												onclick="modificarCheck(this,'2','<s:property value="id"/>');"
												<s:if test="1==usuarioEstado">checked="checked"</s:if>>
										</td>
									</tr>
									<tr height="20">
										<td class="textstatic" align="left">
											Permiso Minimizar:
										</td>
										<td>
											<input type="checkbox"
												onclick="modificarCheck(this,'8','<s:property value="id"/>');"
												<s:if test="1==usuarioMinimizar">checked="checked"</s:if>>
										</td>
									</tr>
									<tr height="20">
										<td class="textstatic" align="left">
											Permiso Cerrar:
										</td>
										<td>
											<input type="checkbox"
												onclick="modificarCheck(this,'7','<s:property value="id"/>');"
												<s:if test="1==usuarioEliminar">checked="checked"</s:if>>
										</td>
									</tr>
									<tr height="20">
										<td class="textstatic" align="left">
											Ver descripci&oacute;n:
										</td>
										<td>
											<input type="checkbox"
												onclick="modificarCheck(this,'5','<s:property value="id"/>');"
												<s:if test="1==verDescripcion">checked="checked"</s:if>>
										</td>
									</tr>
									<tr height="20">
										<td class="textstatic" align="left">
											Ver link ingresar:
										</td>
										<td>
											<input type="checkbox"
												onclick="modificarCheck(this,'6','<s:property value="id"/>');"
												<s:if test="1==verIngreso">checked="checked"</s:if>>
										</td>
									</tr>
									<tr height="20">
										<td class="textstatic" align="left">
											Activo/Inactivo:
										</td>
										<td>
											<input type="checkbox"
												onclick="modificarCheck(this,'3','<s:property value="id"/>');"
												<s:if test="1==estado">checked="checked"</s:if>>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr height="20" style="background-color: #E0EAF3;">
							<td align="right">
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
			</s:iterator>
		</div>
	</body>
	<head>
		<script type="text/javascript">
			var portal;
			portal = new CampusVirtual.Portal("#principal div", {onOverWidget:onOverWidget, onOutWidget:onOutWidget, onChange:onChange, removeEffect:Effect.SwitchOff});			
		<%
			Collection<Servicio> portal = (Collection<Servicio>) request
					.getAttribute("servicios");
			for (Servicio serv_p : portal) {				
		%>
			portal.add(new CampusVirtual.Widget('<%=serv_p.getId()%>'), '<%=serv_p.getColumna()%>');			
		<%
		}
		%>
			portal._updateColumnsHeight();		
		</script>
	</head>
</html>