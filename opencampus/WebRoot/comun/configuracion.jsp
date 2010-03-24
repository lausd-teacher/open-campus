<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}' />

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
			src="<c:out value='${contextPath}'/>/js/protostaculus.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/configuracion.js"></script>
				
	</head>
	<%@include file="/comun/capas/reloj.jsp"%>
	<body onLoad="mostrarReloj()">
		<div id="contenedor">
			<fmt:setLocale value='${sessionScope["WW_TRANS_I18N_LOCALE"]}' scope="session"/>
			<s:include value="/comun/bienvenida.jsp"></s:include>

			<div id="cuerpo">
				<div id="principal">
				
					<table border="0" cellpadding="3" cellspacing="0" class="open_table" width="100%">
						<caption><s:text name="portal.menu.configuracion"/></caption>
						<tbody>
							<tr>
								
								<c:forEach items="${portal}" var="servicio" varStatus="status">
									<td>
										<table width="100%" border="0" cellpadding="3" cellspacing="0">
											<tr>
												<td width="20">
													<input type="checkbox" <c:if test="${servicio.estado == 1}">checked="checked"</c:if>
														<c:if test="${servicio.permisoEliminar != 1}">disabled="disabled"</c:if>
														onclick="anadirServicio(this,'<c:out value="${servicio.id}"/>')" />
												</td>
												<td width="20">
													<img src="<%=request.getContextPath()%>/img/icons/<c:out value="${servicio.id}"/>.gif" alt='' />
												</td>
												<td>
													<strong><fmt:message key="${servicio.nombre}"/></strong>
												</td>
											</tr>
										</table>
									</td>
									
									<c:if test="${status.count==5}">
										<td rowspan="2">
											<table width="100%" border="0" cellpadding="3" cellspacing="0" style="table-layout: fixed;">
												<tr>
													<td width="20"><input type="checkbox" checked="checked" disabled="disabled" /></td>
													<td width="20"><img src="<%=request.getContextPath()%>/img/icon_idioma.jpg"/></td>
													<td><strong><s:text name="idioma.titulo"></s:text></strong></td>
												 </tr>
												<tr>
													<td colspan="3">
														<table width="100%" border="0" cellpadding="0" cellspacing="0">
															<tr>
																<td width="20"><s:text name="idioma" id="id_idioma"/>
																	<input type="radio" name="idioma" id="es"  onClick="cambiarIdioma('es');"
																<s:if test="#id_idioma=='es'">checked="checked"</s:if> /></td>
																<td width="55"><s:text name="idioma.espanol"></s:text></td>
																<td width="20" ><input type="radio" name="idioma" id="en" 
																	onClick="cambiarIdioma('en');"
																<s:if test="#id_idioma=='en'">checked="checked"</s:if> /></td>
															    <td><s:text name="idioma.ingles"></s:text></td>
														    </tr>
												    	</table>
												    </td>
												</tr>
											</table>
										</td>
										</tr><tr>
									</c:if>
									
								</c:forEach>
									
							</tr>
						</tbody>
					</table>
				
					<br>
					
					
					
					
					<c:forEach var="servicio" items="${portal}">
		
		<br><div id="<c:out value='${servicio.id}' />"
			style="vertical-align: top; width: 241px; padding-bottom: 5px; float: left; padding-top: 5px; display: none;">
			<table width="230" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
				<tr>
					<td height="18" valign="top"  style="background-image: url(img/fon_menu.jpg); background-position-x:center; border-left: 1px solid #cccccc; border-right: 1px solid #cccccc;">
						<table cellpadding="0" cellspacing="0" width="100%" border="0" >
							<tr>
								<td valign="bottom" style="cursor: move;" id="tabla_<c:out value='${servicio.id}' />">									
									<table border="0" align="left" cellpadding="3" cellspacing="0" width="100%" >
										<tr>
											<td align="left" width="20">
												<img height="15"
													src="<c:out value='${contextPath}' /><c:out value='${servicio.id}' />"
													alt="<fmt:message key="${servicio.comentario}"/>" style="cursor: move;" />
											<br></td>
											<td align="left">
												<div
													style="padding-left: 5px; padding-right: 10px; font-weight: bold;">
													
													<fmt:bundle basename="mensajes">
														
													</fmt:bundle>
												</div>
											<br></td>
										</tr>
									</table>
								<br></td>
								<td width="15">
									<c:choose>
										<c:when test="${servicio.usuarioMinimizar == 1}">
											<img
												src="<c:out value='${contextPath}'/>/img/comprimir_portal.jpg"
												alt="Minimizar" style="cursor: pointer;"
												onClick="esconderServicio('<c:out value='${servicio.id}' />');">
										</c:when>
										<c:otherwise>
											<img src="<c:out value='${contextPath}'/>/img/inac_comprimir_portal.jpg">
										</c:otherwise>
									</c:choose>
								<br></td>
								<td width="15">
									<c:choose>
										<c:when test="${servicio.usuarioEliminar == 1}">
											<img src="<c:out value='${contextPath}'/>/img/cerrar_portal.jpg"
												alt="Cerrar" style="cursor: pointer;"
												onClick="eliminarServicio('<c:out value='${servicio.id}' />');">
										</c:when>
										<c:otherwise>
											<img src="<c:out value='${contextPath}'/>/img/inac_cerrar_portal.jpg">
										</c:otherwise>
									</c:choose>
								<br></td>
							</tr>
						</table>
					<br></td>
				</tr>
				<tr id="<c:out value='${servicio.id}' />_tr" <c:if test='${servicio.visible == 0}'>style="display: none; visibility: hidden;"</c:if>>
					<td id="<c:out value='${servicio.id}' />_td" style="text-align: left; background-color:#FFF; border: 1px solid #cccccc;">
						<img src="<c:out value='${contextPath}'/>/img/cargando.gif" />
					<br></td>
				</tr>
				<tr height="20" style="background-color: #cccccc;">
					<td align="right"><br>
						<table width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td width="70%" align="left">
									<c:if test="${servicio.verDescripcion == 1}">
										<label id="<c:out value='${servicio.id}' />_descripcion"
											style="font-weight: bold; font-size: 100%; padding-left: 5px;">
											&nbsp;
										</label>
									</c:if>
								<br></td>
								<td align="right">
									<c:if test="${servicio.verIngreso == 1}">
										
									</c:if>
								<br></td>
							</tr>
						</table>
					<br><br></td>
				</tr>
			</table>
		</div>		
		
		</c:forEach>
					
					
					
					
					
				</div>
			</div>
			<div id="pie">
				<%@include file="pie.jsp"%>
			</div>
		</div>
	</body>
</html>
