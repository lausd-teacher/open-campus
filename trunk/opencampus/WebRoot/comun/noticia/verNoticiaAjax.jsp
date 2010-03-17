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

		<table width="97%" align="center" cellpadding="0" cellspacing="0"
			bgcolor="#FFFFFF" border="0" style="table-layout: fixed;">
			<tr align="left" class="cabecera_detalle">
				<td width="6" height="23" class="cabecera_detalle">
					&nbsp;
				</td>
				<td width="16" class="cabecera_detalle">
					<img src="<c:out value='${contextPath}'/>/img/icon_news.gif" />
				</td>
				<td class="cabecera_detalle">
					<span class="tit_not" style="float: left;"> <c:out value="${noticia.seccion.nombre}"/></span>
					<span class="tit_not" style="float: right;"><f:DateToString fecha="${noticia.fecha}" /></span>
					<span class="tit_not" style="float: right;"><a href="javascript:void(0);" class="salir" onClick="xPrint('noticia')"><img
							src="<%=request.getContextPath()%>/img/impresora.gif"
							width="13" height="13" border="0" />
					</a></span>
				</td>
				<td width="6" class="cabecera_detalle">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan="4" class="borde">
					<table width="100%" border="0" cellspacing="0" cellpadding="3" style="table-layout: fixed;">
						<tr>
							<td class="titulo_detalle">
								<c:out value="${noticia.titular}" />
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

	</body>
</html>
