<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="edu.tecsup.lms.util.MenuOpciones.Menu"%>
<%@ page import="java.util.Map"%>


<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%
	Map<String, Map<String, Menu>> menus = (Map<String, Map<String, Menu>>) request
			.getAttribute("opciones");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" /></title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/js/menu/menu.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/menu/menu.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>
		<script type='text/javascript'
			src='<%=request.getContextPath()%>/dwr/engine.js'> </script>
		<script type='text/javascript'
			src='<%=request.getContextPath()%>/dwr/util.js'> </script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/tooltip/tooltip.js"></script>
	</head>
	<body>
		<div id="contenedor">

			<s:include value="/comun/bienvenida.jsp"></s:include>

			<div id="cuerpo">
				<div id="principal">
					<table width="960" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="23" colspan="0">
								<table border="0" align="left" cellpadding="0" cellspacing="0"
									width="100%" class="bor_tabla">
									<tr class="fon_tit_curso">
										<td class="tit_cab" height="20">
											<s:text name="portal.menu.opciones" />
										</td>
									</tr>
									<tr>
										<td>

											<%
						for (String nombre : menus.keySet()) {
						%>
											<div style="width: 180px; float: left; margin: 3px;">
												<table cellpadding="0" cellspacing="0">
													<tr class="fon_curso" height="20">
														<td width="180" align="left" class="tit_cab" colspan="2">
															<fmt:message key="<%=nombre%>"/>
														</td>
														<td class="fon_cab_curso_derecha" width="3">
															&nbsp;
														</td>
													</tr>
													<%
									for (Menu menu : menus.get(nombre).values()) {
						%>

													<tr>
														<td colspan="3"
															style="border-left: 1px solid #44659B; border-right: 1px solid #44659B;">
															<table>
																<tr>
																	<td width="7">
																		&nbsp;

																	</td>
																	<td height="23">

																		<a
																			href="<%=request.getContextPath()+ menu.getRutaWeb()%>"
																			style="color: black;"> <fmt:message key="<%=menu.getNombre()%>"/> </a>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<%
								}%>
												</table>
											</div>


											<%
							}
						%>

										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div id="tooltip"></div>
			<div id="pie">
				<%@include file="pie.jsp"%>
			</div>
		</div>
		<script type="text/javascript">
			resizeHeight();
		</script>
	</body>
</html>
