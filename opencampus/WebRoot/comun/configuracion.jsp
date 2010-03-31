<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/scriptaculous/scriptaculous.js?load=effects,dragdrop"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/portal/portal.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/configuracion.js"></script>
			
		<script type="text/javascript">
			var settings = {'column-1':['block-1','block-2'], 'column-2':['block-3']};
		</script>

	</head>
	<body>
	
		<div id="container">
		
			<s:include value="/comun/bienvenida.jsp"/>
			
			<div id="body">
			
			
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
										<c:out value="</tr><tr>" escapeXml="false"></c:out>
									</c:if>
									
								</c:forEach>
									
							</tr>
						</tbody>
					</table>
				
				<table border="0" cellpadding="3" cellspacing="0" class="open_grid" width="100%">
						<thead>
							<tr><td class="line">Arrastre cada elemento y despl&aacute;celo hasta la hubicaci&oacute;n deseada.</td></tr>
						</thead>
						<tbody>
							<tr>
								<td>

									<div id="columns">
									
										<div id="column-1" class="column"></div>
										
										<div id="column-2" class="column"></div>
										
										<div id="column-3" class="column"></div>
										
										<div id="column-4" class="column"></div>
										
										<div class="portal-column" id="portal-column-block-list" style="display: none;">
											<!-- div class="block" id="block-1">
												<h1 class="draghandle">Block 1</h1>
												<p>Block 1 data</p>
											</div-->
											
											<c:forEach var="servicio" items="${portal}" varStatus="status">
					
												<div class="block" id="block-<c:out value='${status.count}' />">
												
														<div class="draghandle">
														
															<!-- Encabezado -->
															<!-- 
																http://www.ajaxupdates.com/igoogle-like-drag-drop-portal-v2-0/
																* Exconder y mostrar los divs con scriptaculus (Effect.Apear)
																* puede susar apply_settings para refrescar los bloques cuando hagan click en eliminar y agregar servicios, en lugar de refrezcar toda la pantalla
															 -->
															<table cellpadding="0" cellspacing="0" width="100%" border="0" >
																<tr>
																	<td align="left" width="20">
																		<img height="15" src="<c:out value='${contextPath}' />/img/icons/<c:out value="${servicio.id}"/>.gif"
																			alt="<fmt:message key="${servicio.nombre}"/>" style="cursor: move;" />
																	</td>
																	<td align="left">
																		<div
																			style="padding-left: 5px; padding-right: 10px; font-weight: bold;">
																			<fmt:setLocale value='${sessionScope["WW_TRANS_I18N_LOCALE"]}' scope="session"/>
																			<fmt:bundle basename="mensajes">
																				<fmt:message key="${servicio.nombre}"/>
																			</fmt:bundle>
																		</div>
																	</td>
																	<td width="15">
																		<c:choose>
																			<c:when test="${servicio.permisoMinimizar == 1}">
																				<img
																					src="<c:out value='${contextPath}'/>/img/comprimir_portal.jpg"
																					alt="Minimizar" style="cursor: pointer;"
																					onClick="esconderServicio('<c:out value='${servicio.id}' />');">
																			</c:when>
																			<c:otherwise>
																				<img src="<c:out value='${contextPath}'/>/img/inac_comprimir_portal.jpg">
																			</c:otherwise>
																		</c:choose>
																	</td>
																	<td width="15">
																		<c:choose>
																			<c:when test="${servicio.permisoEliminar == 1}">
																				<img src="<c:out value='${contextPath}'/>/img/cerrar_portal.jpg"
																					alt="Cerrar" style="cursor: pointer;"
																					onClick="eliminarServicio('<c:out value='${servicio.id}' />');">
																			</c:when>
																			<c:otherwise>
																				<img src="<c:out value='${contextPath}'/>/img/inac_cerrar_portal.jpg">
																			</c:otherwise>
																		</c:choose>
																	</td>
																</tr>
															</table>
															<!-- Fin encabezado -->
															
														</div>
														
														<div class="body1" <c:if test='${servicio.visible == 0}'>style="display: none; visibility: hidden;"</c:if>>
															<!-- Contenido -->
															<center><strong><fmt:message key="${servicio.nombre}"/></strong></center>
															<img src="<c:out value='${contextPath}'/>/img/cargando.gif" />
															<!-- Fin Contenido -->
														</div>
														
														<div style="background-color: #cccccc; height: 20px;">
														
															El pie debe ser parte del contenido
															
															<!-- table width="100%" cellpadding="0" cellspacing="0">
																<tr>
																	<td width="70%" align="left">
																			<label id="<c:out value='${servicio.id}' />_descripcion"
																				style="font-weight: bold; font-size: 100%; padding-left: 5px;">
																				&nbsp;
																			</label>
																	<br></td>
																	<td align="right">
																			<label
																				style="cursor: pointer; font-weight: bold; font-size: 100%; padding-right: 5px;"
																				onclick="abrir_<c:out value='${servicio.id}' />();"
																				class="portal_menu_selecionando">
																				<s:text name="portal.servicios.link"/>
																			</label>
																	<br></td>
																</tr>
															</table-->
																
														</div>
															
												</div>		
								
											</c:forEach>
											
											
										</div>

									</div>
									
								</td>
							</tr>
						</tbody>
					</table>
					
					
					
			</div>
			
			<s:include value="/comun/pie.jsp"/>
			
		</div>
		
	</body>
</html>
