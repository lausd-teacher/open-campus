<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
	<div id="pop_up" style="width: 580px;">
			<div id="prin_01">
			<table width="100%" cellpadding="3" cellspacing="0" border="0" >
			<tr align="left" >
			  <td width="810" height="23" ><strong>Noticias </strong></td>
				<td width="63"><a href="#" class="salir" onClick="window.print()">Imprimir</a> </td>
				<td width="24"><a href="#" class="salir" onClick="window.print()"><img
									src="<%=request.getContextPath()%>/img/impresora.gif"
									width="13" height="13" border="0" /> </a> </td>
				<td width="7"> | </td>
				<td width="46"><a href="#" class="salir" onClick="window.close()">Cerrar</a> </td>
				<td width="19"><a href="#" class="salir" onClick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13"
									height="13" border="0" /> </a> </td>
			</tr>
			</table>
          </div>
          <div id="pop_cuerpo">
					<table width="100%" border="0" cellspacing="0" cellpadding="3" style="table-layout: fixed;">
						<tr>
							<td class="titulo_detalle">
								<c:out value="${noticia.titular}" />							</td>
						</tr>
						<tr>
							<td height="8"></td>
						</tr>
						<tr>
							<td>
								
								<c:if test="${noticia.imagen != null}">								
										<!-- o sino usar estilos "tabla img {display:one;}" o hacer replace intranet/ por /webcampus -->
										<img width="180" vspace="4" hspace="4" border="0" align="left" src="<%=request.getContextPath()%>/VerImagen.action?filename=<c:out value="${noticia.imagen}"/>" alt=""/>								</c:if>
								
								<p align="justify">
									<c:out value="${noticia.cuerpo}" escapeXml="false"/>
								</p>							</td>
						</tr>
					</table>
		</div>
		<div id="pie">
			<s:include value="/comun/pie.jsp"/>
		</div>
	</div>
</body>
</html>
