<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<c:set var="contextPath" value='${pageContext.request.contextPath}' />
		<title>Noticia</title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
	</head>

	<body>
<div id="pop_up" style="width: 100%;">
			<div id="prin_01">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="93%">
								<a style="color:black;font-weight:bold;" href="<%=request.getContextPath()%>/noticia/Cargar.action" ><strong>Noticias</strong></a>
								>
								<a style="color:#44659B;font-weight: bold;"><c:out value="${noticia.seccion.nombre}"/></a>
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
		<table width="100%" align="center" cellpadding="0" cellspacing="0"
			bgcolor="#FFFFFF" border="0" style="table-layout: fixed;">
			
			<tr>
				<td colspan="4" class="borde">
					<table width="100%" border="0" cellspacing="0" cellpadding="3" style="table-layout: fixed;">
						<tr>
							<td class="titulo_detalle" style="border-top: 1px solid #E0EAF3; background-color: #EEEEEE" >
								<c:out value="${noticia.titular}" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<b>Fecha: <f:DateToString fecha="${noticia.fecha}" /></b>
							</td>
						</tr>
						<tr>
							<td height="8"></td>
						</tr>
						<tr>
							<td>
								
								<c:if test="${noticia.imagen != null}">
								
									<c:choose>
										<c:when test="${noticia.formato == 1}">
											<c:set var="formato" value="formato_imagen_cuadro" />
										</c:when>
										<c:otherwise>
											<c:set var="formato" value="formato_imagen_centrado" />
										</c:otherwise>
									</c:choose>
									
									<div class="<c:out value="${formato}" />">
										<img src="<c:out value='${contextPath}'/>/noticia/VerImagen.action?filename=<c:out value="${noticia.imagen}" />" style="max-width: 520px;"/>
									</div>
									
								</c:if>
								
								<p align="justify">
									<c:out value="${noticia.cuerpo}" escapeXml="false"/>
								</p>
								
								
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
</div>
			<div id="pie">
				<%@include file="../pie.jsp"%>
			</div>
		</div>
	</body>
</html>
