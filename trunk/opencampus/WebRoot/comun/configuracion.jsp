<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="/error_action.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<fmt:setLocale value='${sessionScope["WW_TRANS_I18N_LOCALE"]}' scope="session"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="contextPath" value='${pageContext.request.contextPath}' />
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:include value="/comun/jslibs.jsp"/>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/portal/portal.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/configuracion.js"></script>
			
		<script type="text/javascript">
			var settings = <c:out value="${cadena}"  escapeXml="false"/>;
			Event.observe(window, 'load', function() {
				this.portal = new Portal(settings, options, data);
			});
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
													<input type="checkbox" id="chkbox_<c:out value="${servicio.id}"/>"
														<c:if test="${servicio.estado == 1}">checked="checked"</c:if>
														<c:if test="${servicio.permisoEliminar != 1}">disabled="disabled"</c:if>
														onclick="configService(this,'<c:out value="${servicio.id}"/>')" />
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
							<tr><td class="line"><s:text name="portal.configuracion.help2"/></td></tr>
						</thead>
						<tbody>
							<tr>
								<td>

									<div id="columns" class="clearfix">
									
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
					
												<div class="block" id="block-<c:out value="${servicio.id}"/>" <c:if test='${servicio.estado == 0}'>style="display: none;"</c:if>>
												
														<div class="draghandle">
														
															<!-- Encabezado -->
															<div class="head_icon">
																<img height="15" src="<c:out value='${contextPath}' />/img/icons/<c:out value="${servicio.id}"/>.gif"
																	alt="<fmt:message key="${servicio.nombre}"/>" />
															</div>
															<div class="head_title">
																<b><fmt:message key="${servicio.nombre}"/></b>
															</div>
															<div class="head_btn">
																<c:choose>
																	<c:when test="${servicio.permisoMinimizar == 1}">
																		<c:choose>
																		<c:when test="${servicio.visible == 0}">
																			<img
																				src="<c:out value='${contextPath}'/>/img/mas.jpg"
																				alt="<s:text name="portal.servicio.comentario.maximizar"/>" style="cursor: pointer;"
																				onClick="hideService(this,'<c:out value='${servicio.id}' />','<s:text name="portal.servicio.comentario.maximizar"/>','<s:text name="portal.servicio.comentario.minimizar"/>');">
																		</c:when>
																		<c:otherwise>
																			<img
																				src="<c:out value='${contextPath}'/>/img/menos.jpg"
																				alt="<s:text name="portal.servicio.comentario.minimizar"/>" style="cursor: pointer;"
																				onClick="hideService(this,'<c:out value='${servicio.id}' />','<s:text name="portal.servicio.comentario.maximizar"/>','<s:text name="portal.servicio.comentario.minimizar"/>');">
																		</c:otherwise>
																	</c:choose>	
																	</c:when>
																	<c:otherwise>
																		<img src="<c:out value='${contextPath}'/>/img/nada.jpg">
																	</c:otherwise>
																</c:choose>
															</div>
															<div class="head_btn">
																<c:choose>
																	<c:when test="${servicio.permisoEliminar == 1}">
																		<img src="<c:out value='${contextPath}'/>/img/cerrar.jpg"
																			alt="<s:text name="portal.servicio.comentario.cerrar"/>" style="cursor: pointer;"
																			onClick="removeService('<c:out value='${servicio.id}' />');">
																	</c:when>
																	<c:otherwise>
																		<img src="<c:out value='${contextPath}'/>/img/nada.jpg">
																	</c:otherwise>
																</c:choose>
															</div>
															<!-- Fin encabezado -->
															
														</div>
														
														<!-- Contenido -->
														<div id="box_<c:out value='${servicio.id}' />" class="box" <c:if test='${servicio.visible == 0}'>style="display: none;"</c:if>>
															<div class="demo"><fmt:message key="${servicio.nombre}"/></div>
														</div>
														<!-- Fin Contenido -->
														
														<div class="foot clearfix">
															<div style="float:left;">
																<a href="javascript:void(0);"><s:text name="portal.servicios.link"/></a>
															</div>
															<div style="float: right;"><img src="<c:out value='${contextPath}'/>/img/reload.jpg" class="link"
																alt="<s:text name="portal.servicios.reload"/>" onclick="cargar_servicio('<c:out value='${servicio.id}' />');"/></div>
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
