<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}' />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" /></title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jPrototype.js"
			type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/scriptaculous/scriptaculous.js?load=effects"></script>
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/InnerDiv.js"></script>
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/comun/noticia/noticia.js"></script>
		
	</head>
	<body onresize="resize()"  onscroll="scrolling()" style="margin-top: 8px;">
		<div id="pop_up" style="width: 100%;">
			<div id="prin_01">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="93%">
							<div id="back2_1" style="color:black;">
								<strong>Noticias</strong>
							</div>
							<div id="back2_2" style="display: none;">
								<a style="color:black;font-weight:bold;" href="javascript:void(0);" 
								onclick="regresarPortada()"><strong>Noticias</strong></a>
								>
								<a style="color:#cccccc;font-weight: bold;">Detalle</a>
							</div>
						</td>
						<td width="5%">
							<a href="#" class="salir" onClick="window.print()">Imprimir</a>
						</td>
						<td width="3%">
							<a href="javascript:void(0);" class="salir" onClick="xPrint('noticia')"><img
									src="<%=request.getContextPath()%>/img/impresora.gif"
									width="13" height="13" border="0" />
							</a>
						</td>
						<td width="2%">
							|
						</td>
						<td width="4%">
							<a href="#" class="salir" onClick="window.close()">Cerrar</a>
						</td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13"
									height="13" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>
			<div id="pop_cuerpo">
				<div id="portada">	
				<c:if test="${fn:length(secciones) > 0}">
			
					<table width="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed;">
						
						<tr>
							<td colspan="3" valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="9" style="border-bottom: 1px solid #cccccc;">
											&nbsp;
										</td>
										
										<c:set var="sizeS" value="${fn:length(secciones)}"></c:set>
										
										<c:forEach items="${secciones}" var="seccion" varStatus="fila">
											<c:choose>
												<c:when test="${fila.count == 1}">
													<td id="tabl_<c:out value="${fila.count}"/>" width="9" class="etiqueta_act_iz_afuera" height="27">
														&nbsp;
													</td>
													<td id="tab_<c:out value="${fila.count}"/>" width="72" class="etiqueta_act_centro"
														onClick="verSeccion('<c:out value="${fila.count}"/>','<c:out value="${sizeS}"/>')">
														<c:out value="${seccion.nombre}"/>
													</td>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${fila.count == 2}">
															<td id="tabl_<c:out value="${fila.count}"/>" width="9" class="etiqueta_act_de">
																&nbsp;
															</td>
														</c:when>
														<c:otherwise>
															<td id="tabl_<c:out value="${fila.count}"/>" width="9" class="etiqueta_inact_de">
																&nbsp;
															</td>
														</c:otherwise>
													</c:choose>
													<td id="tab_<c:out value="${fila.count}"/>" width="72" class="etiqueta_inact_centro"
														onClick="verSeccion('<c:out value="${fila.count}"/>','<c:out value="${sizeS}"/>')">
														<c:out value="${seccion.nombre}"/>
													</td>
												</c:otherwise>
											</c:choose>
											
										</c:forEach>
										
										<c:choose>
											<c:when test="${sizeS == 1}">
												<td id="tabd_<c:out value="${sizeS}"/>" width="9" class="etiqueta_act_de_ult">
													&nbsp;
												</td>
											</c:when>
											<c:otherwise>
												<td id="tabd_<c:out value="${sizeS}"/>" width="9" class="etiqueta_inact_de_ult">
													&nbsp;
												</td>
											</c:otherwise>
										</c:choose>
										
										<td style="border-bottom: 1px solid #cccccc;">
											&nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="3" valign="top" class="borde_noticas" style="padding: 5px;">
							
							
								<c:forEach items="${secciones}" var="seccion" varStatus="divSeccion">
									<c:choose>
										<c:when test="${divSeccion.count != 1}">
											<c:set var="displaySeccion" value="display:none;"></c:set>
										</c:when>
										<c:otherwise>
											<c:set var="displaySeccion" value=""></c:set>
										</c:otherwise>
									</c:choose>
									
									<div id="divSeccion_<c:out value="${divSeccion.count}"/>" style="overflow: auto; width: 100%; max-height: 420px; <c:out value="${displaySeccion}"/>">
										
										<table width="644" border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed;">
											<tr>
											
											<c:set var="sizeN" value="${fn:length(seccion.noticias)}"></c:set>
											
											<c:forEach items="${seccion.noticias}" var="noticia" varStatus="fila">
											
												<c:if test="${fila.count%2 == 0}">
													<td width="2%" class="borde_separador_vert">
														&nbsp;
													</td>
												</c:if>
											
												<td width="49%" valign="top" style="cursor: pointer;" onclick="verNoticia('<c:out value="${noticia.idnoticia}"/>')"
													onmouseover="seleccionNoticia(this,true)" onmouseout="seleccionNoticia(this,false)">
												
													<table width="100%" border="0" cellspacing="0" cellpadding="0">
														<tr>
															<td width="64" rowspan="2" valign="top" class="space_img">
																<c:if test="${noticia.imagen != null}">
																	<div style="max-height: 64px; overflow: hidden;"><img src="<c:out value='${contextPath}'/>/noticia/VerImagenPrevia.action?filename=<c:out value="${noticia.imagen}" />" width="64" /></div>
																</c:if>
															</td>
															<td class="tituloPortada">
																<c:out value="${noticia.titular}"/>
															</td>
														</tr>
														<tr>
															<td class="textoNoticia" valign="top">
																<c:out value="${noticia.cuerpo}"/>
																<br/>
																<span style="float:right;"><b>Fecha: <f:DateToString fecha="${noticia.fecha}"/></b></span>
															</td>
														</tr>
													</table>
												</td>
										<c:if test="${fila.count%2 == 0 && sizeN != fila.count}">
											</tr>
											<tr>
												<td width="49%" class="borde_separador_hori">
													&nbsp;
												</td>
												<td width="2%" height="10" class="borde_separador_cruce">
													&nbsp;
												</td>
												<td width="49%" class="borde_separador_hori">
													&nbsp;
												</td>
											</tr>
											<tr>
										</c:if>
											
											</c:forEach>
												
											<c:if test="${sizeN%2 != 0}">
												<td width="2%" class="borde_separador_vert">
													&nbsp;
												</td>
												<td width="49%">
													&nbsp;
												</td>
											</c:if>
											<!-- Para habilitar el comercio tambien descomentar las lineas en 
												NoticiaDAO:cargarPortada() y NoticiaDAO:cargarPortada(idusuario) -->
											<c:if test="${sizeN==0 && false}">
												<!-- RSS de comercio parar  a servlet o action para poner con ajax -->
												<ct:RSS url="http://www.larepublica.com.pe/index2.php?option=com_rss&feed=RSS2.0&no_html=1" cantidad="10"/>
												<iframe src="<c:out value='${contextPath}'/>/comun/noticia/rss.jsp" frameborder="0" marginwidth="0" width="120%" marginheight="0" height="500" scrolling="yes"></iframe>
											</c:if>
												
											</tr>
										</table>
										
									</div>
									
									
								</c:forEach>	
									
									
							</td>
						</tr>
					</table>

				</c:if>
				</div>
				<div id="noticia" style="width: 100%; overflow: auto;"></div>
				<div id="back" style="width: 100%; padding-top: 10px; text-align: right; display: none;">
					<input type="button" value="Regresar" class="form_button" onclick="regresarPortada()">
				</div>
			</div>
			<div id="pie">
				<%@include file="../pie.jsp"%>
			</div>
		</div>
		
		<div id="blocker" style="display:none; position: absolute; top: 0px; left: 0px; background-color:#000000; width:0px;height:0px; 
				filter:alpha(opacity=50); -moz-opacity:.50; opacity:.50;"></div>
				
	</body>
</html>
