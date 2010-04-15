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
		<script type="text/javascript" src="<c:out value='${contextPath}'/>/js/configuracion.js"></script>
	</head>
	<body>
		<div id="container">
		
			<s:include value="/comun/bienvenida.jsp"/>
			
			<div id="body">
			
				
				<c:forEach var="servicio" items="${portal}" varStatus="status">
					
					<div class="block" id="block-<c:out value="${servicio.id}"/>" <c:if test='${servicio.estado == 0}'>style="display: none;"</c:if>>
					
							<div class="draghandle">
							
								<!-- Encabezado -->
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
										</td>
										<td width="15">
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
										</td>
									</tr>
								</table>
								<!-- Fin encabezado -->
								
							</div>
							
							<div class="body1" <c:if test='${servicio.visible == 0}'>style="display: none;"</c:if>>
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
			
			<s:include value="/comun/pie.jsp"/>
			
		</div>
		
	</body>
		<script type="text/javascript">
		
			function loadServices(){
			<c:forEach var="servicio" items="${portal}">
				portal.add(new CampusVirtual.Widget('<c:out value='${servicio.id}' />'), '<c:out value='${servicio.columna}' />');
				cargar_<c:out value='${servicio.id}' />();
			</c:forEach>
				portal._updateColumnsHeight();	
			
			}	
		</script>
</html>

