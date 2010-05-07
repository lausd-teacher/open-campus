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
		<script type="text/javascript" src="<c:out value='${contextPath}'/>/js/configuracion.js"></script>
		<script type="text/javascript">
			Event.observe(window, 'load', function() {
				<c:forEach var="servicio" items="${portal}">
					<c:if test="${servicio.visible == 1}">
						cargar_servicio('<c:out value='${servicio.id}' />');
					</c:if>
				</c:forEach>
			});
		</script>
	</head>
	<body>
		<div id="container">
		
			<s:include value="/comun/bienvenida.jsp"/>
			
			<div id="body">
			
				<div id="columns">
				
					<div class="column">
				
						<c:forEach var="servicio" items="${portal}" varStatus="status">
							
							<c:if test="${status.count!= 1 && servicio.columna != columna}">
								</div>
								<div class="column">
							</c:if>
							<c:set var="columna" value="${servicio.columna}"></c:set>
							
							<div class="block">
							
									<div class="draghandle" style="cursor:default; ">
									
										<!-- Encabezado -->
										<table cellpadding="0" cellspacing="0" width="100%" border="0" >
											<tr>
												<td align="left" width="20">
													<img height="15" src="<c:out value='${contextPath}' />/img/icons/<c:out value="${servicio.id}"/>.gif"
														alt="<fmt:message key="${servicio.nombre}"/>"/>
												</td>
												<td align="left">
													<b><fmt:message key="${servicio.nombre}"/></b>
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
									
									<!-- Contenido -->
									<div id="box_<c:out value='${servicio.id}' />" class="box" <c:if test='${servicio.visible == 0}'>style="display: none;"</c:if>>
									</div>
									<!-- Fin Contenido -->
									
									<div class="foot clearfix">
										<div style="float:left;">
											<c:choose>
											<c:when test="${servicio.id == 'servicio_curso'}">
												<!-- CURSOS -->
												<a href="<c:out value='${contextPath}'/>/Curso.action"><s:text name="portal.servicios.link"/></a>
											</c:when>
											<c:when test="${servicio.id == 'servicio_chat'}">
												<!-- CHAT -->
												<a href="<c:out value='${contextPath}'/>/chat/Cargar.action" toptions="width= 730, height = 560,<c:out value="${modal_config1}"/>"><s:text name="portal.servicios.link"/></a>
											</c:when>
											<c:when test="${servicio.id == 'servicio_noticia'}">
												<!-- NOTICIAS -->
												<a href="<c:out value='${contextPath}'/>/noticia/Cargar.action" toptions="width= 740, height = 550,<c:out value="${modal_config1}"/>"><s:text name="portal.servicios.link"/></a>
											</c:when>
											<c:when test="${servicio.id == 'servicio_cumpleanos'}">
												<!-- HONOMASTICO -->
												
											</c:when>
											<c:when test="${servicio.id == 'servicio_buzon'}">
												<!-- MENSAJES-->
												<a href="<c:out value='${contextPath}'/>/comun/buzon/Buzon.action" toptions="width= 730, height = 550,<c:out value="${modal_config1}"/>"><s:text name="portal.servicios.link"/></a>
											</c:when>
											<c:when test="${servicio.id == 'servicio_apuntes'}">
												<!-- ANOTACIONES-->
												<a href="<c:out value='${contextPath}'/>/anotacion/Anotacion.action" toptions="width= 420, height = 550,<c:out value="${modal_config1}"/>"><s:text name="portal.servicios.link"/></a>
											</c:when>
											<c:when test="${servicio.id == 'servicio_foros'}">
												<!-- FOROS-->
												<a href="<c:out value='${contextPath}'/>/foro/Foro.action" toptions="width= 866, height = 560,<c:out value="${modal_config1}"/>"><s:text name="portal.servicios.link"/></a>
											</c:when>
											<c:when test="${servicio.id == 'servicio_agenda'}">
												<!-- AGENDA-->
												<a href="<c:out value='${contextPath}'/>/agenda/Cargar.action" toptions="width= 400, height = 400,<c:out value="${modal_config1}"/>"><s:text name="portal.servicios.link"/></a>
											</c:when>
											<c:when test="${servicio.id == 'servicio_enlaces'}">
												<!-- ENLACES-->
												
											</c:when>
											<c:when test="${servicio.id == 'servicio_biblioteca'}">
												<!-- BIBLIOTECA-->
												
											</c:when>
											</c:choose>
											
										</div>
										<div style="float: right;"><img src="<c:out value='${contextPath}'/>/img/reload.jpg" class="link"
											alt="<s:text name="portal.servicios.reload"/>" onclick="cargar_servicio('<c:out value='${servicio.id}' />');"/></div>
									</div>
										
							</div>		
			
						</c:forEach>
				
					</div>
				
				</div>
				
			</div>
			
			<s:include value="/comun/pie.jsp"/>
			
		</div>
		
	</body>
</html>

